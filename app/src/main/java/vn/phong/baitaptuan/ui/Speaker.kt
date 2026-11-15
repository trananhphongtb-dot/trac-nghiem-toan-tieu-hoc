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
 * Dùng 2 bộ đọc riêng: tiếng Việt và tiếng Anh, để môn Tiếng Anh đọc đúng giọng.
 */
class Speaker(context: Context) {
    private var vi: TextToSpeech? = null
    private var en: TextToSpeech? = null
    private var viOk = false
    private var enOk = false
    private var token = 0

    /** true khi máy chưa có giọng tiếng Việt, để app nhắc người dùng cài. */
    var viMissing by mutableStateOf(false)
        private set

    var speaking by mutableStateOf(false)
        private set

    init {
        create(context.applicationContext, Locale("vi", "VN"), 0.9f, GOOGLE, { vi = it }) { ok ->
            viOk = ok
            viMissing = !ok
        }
        create(context.applicationContext, Locale.US, 0.85f, GOOGLE, { en = it }) { ok -> enOk = ok }
    }

    /**
     * Ưu tiên bộ đọc của Google. Nếu không có hoặc không có đúng giọng của ngôn ngữ cần đọc
     * thì thử bộ đọc mặc định; vẫn không đúng ngôn ngữ thì coi như chưa có giọng,
     * tránh việc máy đọc nhầm bằng giọng ngôn ngữ khác (ví dụ tiếng Trung).
     */
    private fun create(
        ctx: Context, locale: Locale, rate: Float, enginePkg: String?,
        assign: (TextToSpeech) -> Unit, ready: (Boolean) -> Unit
    ) {
        var tts: TextToSpeech? = null
        val listener = TextToSpeech.OnInitListener { status ->
            val e = tts
            val ok = status == TextToSpeech.SUCCESS && e != null && setup(e, locale, rate)
            if (!ok && enginePkg != null) {
                try { e?.shutdown() } catch (_: Exception) {}
                create(ctx, locale, rate, null, assign, ready)
            } else {
                ready(ok)
            }
        }
        tts = if (enginePkg != null) TextToSpeech(ctx, listener, enginePkg) else TextToSpeech(ctx, listener)
        assign(tts)
    }

    private fun setup(e: TextToSpeech, locale: Locale, rate: Float): Boolean {
        val r = e.setLanguage(locale)
        if (r == TextToSpeech.LANG_MISSING_DATA || r == TextToSpeech.LANG_NOT_SUPPORTED) return false
        try {
            val v = e.voices?.firstOrNull {
                it.locale.language == locale.language && !it.features.contains("notInstalled")
            }
            if (v != null) e.voice = v
        } catch (_: Exception) {}
        val cur = e.voice?.locale?.language
        if (cur != null && cur != locale.language) return false
        e.setSpeechRate(rate)
        return true
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
        val engine = if (seg.english) (if (enOk) en else null) else (if (viOk) vi else null)
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
        vi?.stop()
        en?.stop()
        speaking = false
    }

    fun shutdown() {
        token++
        vi?.stop(); vi?.shutdown(); vi = null
        en?.stop(); en?.shutdown(); en = null
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
