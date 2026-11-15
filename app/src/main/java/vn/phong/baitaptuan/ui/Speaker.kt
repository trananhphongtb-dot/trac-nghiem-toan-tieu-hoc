package vn.phong.baitaptuan.ui

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import vn.phong.baitaptuan.model.Question
import vn.phong.baitaptuan.model.Subject
import java.util.Locale

/**
 * Đọc câu hỏi bằng bộ đọc có sẵn của Android (TextToSpeech), không cần mạng.
 * Dùng 2 kênh riêng: tiếng Việt và tiếng Anh. Mỗi kênh thử lần lượt bộ đọc Google,
 * bộ đọc mặc định, rồi các bộ đọc khác trên máy, cho tới khi tìm được bộ có đúng ngôn ngữ.
 */
class Speaker(context: Context) {
    private val app = context.applicationContext

    private inner class Lane(val locale: Locale, val rate: Float, val onStatus: (Boolean) -> Unit) {
        var tts: TextToSpeech? = null
        var ready = false
        private val tried = HashSet<String>()
        private var pending: List<String> = emptyList()
        private var shut = false

        fun start() = connect(GOOGLE)

        private fun connect(engine: String?) {
            if (shut) return
            tried.add(engine ?: "")
            var created: TextToSpeech? = null
            var earlyStatus: Int? = null
            val listener = TextToSpeech.OnInitListener { status ->
                val t = created
                if (t == null) earlyStatus = status else onInit(t, status)
            }
            created = try {
                if (engine != null) TextToSpeech(app, listener, engine) else TextToSpeech(app, listener)
            } catch (e: Exception) {
                null
            }
            tts = created
            val made = created
            val early = earlyStatus
            if (made == null) tryNext() else if (early != null) onInit(made, early)
        }

        private fun onInit(t: TextToSpeech, status: Int) {
            if (shut) {
                t.shutdown()
                return
            }
            if (status == TextToSpeech.SUCCESS) {
                val r = try { t.setLanguage(locale) } catch (e: Exception) { TextToSpeech.LANG_NOT_SUPPORTED }
                if (r != TextToSpeech.LANG_MISSING_DATA && r != TextToSpeech.LANG_NOT_SUPPORTED) {
                    t.setSpeechRate(rate)
                    ready = true
                    onStatus(true)
                    return
                }
                val others = try { t.engines.map { it.name } } catch (e: Exception) { emptyList() }
                t.shutdown()
                pending = others.filter { it !in tried }
            } else {
                t.shutdown()
            }
            tryNext()
        }

        private fun tryNext() {
            ready = false
            if ("" !in tried) {
                connect(null)
                return
            }
            val next = pending.firstOrNull { it !in tried }
            if (next != null) {
                connect(next)
            } else {
                tts = null
                onStatus(false)
            }
        }

        fun stop() {
            try { tts?.stop() } catch (e: Exception) {}
        }

        fun shutdown() {
            shut = true
            ready = false
            try { tts?.stop(); tts?.shutdown() } catch (e: Exception) {}
            tts = null
        }
    }

    private var token = 0

    /** true khi máy chưa có giọng tiếng Việt, để app nhắc người dùng cài. */
    var viMissing by mutableStateOf(false)
        private set

    var speaking by mutableStateOf(false)
        private set

    private val vi = Lane(Locale("vi", "VN"), 0.9f) { ok -> viMissing = !ok }
    private val en = Lane(Locale.US, 0.85f) { }

    init {
        vi.start()
        en.start()
    }

    private class Seg(val text: String, val english: Boolean)

    /** Đọc câu hỏi rồi lần lượt 4 đáp án. */
    fun speakQuestion(subject: Subject, number: Int, q: Question) {
        val segs = ArrayList<Seg>()
        fun add(text: String) {
            val english = subject == Subject.TIENG_ANH && !hasVietnamese(text)
            val clean = if (english) cleanEnglish(text) else cleanVietnamese(text)
            if (clean.isNotBlank()) segs.add(Seg(clean, english))
        }
        add("Câu $number.")
        add(q.text)
        q.options.forEachIndexed { i, o -> add("${'A' + i}. $o") }
        play(segs)
    }

    private fun play(segs: List<Seg>) {
        stop()
        val t = token
        speaking = true
        next(segs, 0, t)
    }

    private fun next(segs: List<Seg>, i: Int, t: Int) {
        if (t != token) return
        if (i >= segs.size) {
            speaking = false
            return
        }
        val seg = segs[i]
        val lane = if (seg.english) en else vi
        val engine = if (lane.ready) lane.tts else null
        if (engine == null) {
            next(segs, i + 1, t)
            return
        }
        engine.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
            override fun onStart(utteranceId: String?) {}
            override fun onDone(utteranceId: String?) {
                next(segs, i + 1, t)
            }

            @Deprecated("Deprecated in Java")
            override fun onError(utteranceId: String?) {
                next(segs, i + 1, t)
            }
        })
        engine.speak(seg.text, TextToSpeech.QUEUE_FLUSH, null, "u$t-$i")
    }

    fun stop() {
        token++
        vi.stop()
        en.stop()
        speaking = false
    }

    fun shutdown() {
        token++
        vi.shutdown()
        en.shutdown()
    }

    private fun hasVietnamese(s: String): Boolean = VI_CHARS.containsMatchIn(s.lowercase())

    private fun cleanVietnamese(s: String): String {
        var t = s.replace("\n", ". ")
        t = t.replace("×", " nhân ").replace("÷", " chia ").replace("=", " bằng ")
        t = t.replace("+", " cộng ").replace("−", " trừ ")
        t = Regex("(?<=\\d)\\s*-\\s*(?=\\d)").replace(t, " trừ ")
        t = t.replace("<", " bé hơn ").replace(">", " lớn hơn ")
        t = t.replace("≤", " bé hơn hoặc bằng ").replace("≥", " lớn hơn hoặc bằng ")
        t = t.replace("%", " phần trăm ")
        t = Regex("(_{2,}|…+|\\.{3,})").replace(t, " chỗ trống ")
        t = t.replace("?", " ?")
        return t.replace(Regex("\\s+"), " ").trim()
    }

    private fun cleanEnglish(s: String): String {
        var t = s.replace("\n", ". ")
        t = Regex("(_{2,}|…+|\\.{3,})").replace(t, " blank ")
        return t.replace(Regex("\\s+"), " ").trim()
    }

    private companion object {
        const val GOOGLE = "com.google.android.tts"
        val VI_CHARS = Regex("[àáạảãâầấậẩẫăằắặẳẵèéẹẻẽêềếệểễìíịỉĩòóọỏõôồốộổỗơờớợởỡùúụủũưừứựửữỳýỵỷỹđ]")
    }
}
