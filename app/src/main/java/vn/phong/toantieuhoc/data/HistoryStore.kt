package vn.phong.toantieuhoc.data

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject
import vn.phong.toantieuhoc.model.HistoryEntry
import vn.phong.toantieuhoc.model.WrongQuestion

/** Lưu lịch sử làm bài có cấu trúc trên máy, không cần tài khoản, không cần mạng.
 *
 *  Dùng org.json có sẵn trong Android, không thêm dependency.
 *  Lưu bằng key mới "entries_v2" dưới dạng JSONArray trong SharedPreferences.
 *  KHÔNG giữ tương thích với dòng chữ cũ (key "lines" của bản trước): các lần
 *  làm trước bản này sẽ không hiện trong lịch sử mới và không xem được câu sai
 *  cũ, điều này đã được người dùng chấp nhận khi chốt bản lịch sử mới.
 *  Các entry lưu mới nhất lên đầu danh sách trả về; số Lần do màn lịch sử
 *  tính ngược từ tổng số entry (lần đầu tiên là Lần 1).
 */
class HistoryStore(context: Context) {
    private val prefs = context.getSharedPreferences("math_quiz_history", Context.MODE_PRIVATE)

    fun load(): List<HistoryEntry> {
        val raw = prefs.getString("entries_v2", null) ?: return emptyList()
        return try {
            val arr = JSONArray(raw)
            val out = mutableListOf<HistoryEntry>()
            for (i in 0 until arr.length()) {
                val obj = arr.optJSONObject(i) ?: continue
                out += entryFromJson(obj) ?: continue
            }
            out
        } catch (_: Exception) {
            emptyList()
        }
    }

    /** Ghi thêm 1 entry mới lên đầu, giữ tối đa 50 entry. */
    fun save(entry: HistoryEntry) {
        val updated = (listOf(entry) + load()).take(50)
        persist(updated)
    }

    /** Cập nhật 1 entry có sẵn theo id (dùng khi làm lại xóa bớt câu sai cũ). */
    fun update(entry: HistoryEntry) {
        val updated = load().map { if (it.id == entry.id) entry else it }
        persist(updated)
    }

    private fun persist(entries: List<HistoryEntry>) {
        val arr = JSONArray()
        for (e in entries) arr.put(entryToJson(e))
        prefs.edit().putString("entries_v2", arr.toString()).apply()
    }

    private fun entryToJson(e: HistoryEntry): JSONObject {
        val obj = JSONObject()
        obj.put("id", e.id)
        obj.put("grade", e.grade)
        if (e.week != null) obj.put("week", e.week) else obj.put("week", JSONObject.NULL)
        if (e.examTitle != null) obj.put("examTitle", e.examTitle) else obj.put("examTitle", JSONObject.NULL)
        obj.put("correct", e.correct)
        obj.put("total", e.total)
        val wrongArr = JSONArray()
        for (w in e.wrong) {
            val wObj = JSONObject()
            wObj.put("text", w.text)
            val opts = JSONArray()
            for (o in w.options) opts.put(o)
            wObj.put("options", opts)
            wObj.put("correctIndex", w.correctIndex)
            wObj.put("chosenIndex", w.chosenIndex)
            wObj.put("explanation", w.explanation)
            wObj.put("form", w.form)
            wrongArr.put(wObj)
        }
        obj.put("wrong", wrongArr)
        return obj
    }

    private fun entryFromJson(obj: JSONObject): HistoryEntry? {
        return try {
            val wrong = mutableListOf<WrongQuestion>()
            val wrongArr = obj.optJSONArray("wrong")
            if (wrongArr != null) {
                for (i in 0 until wrongArr.length()) {
                    val wObj = wrongArr.optJSONObject(i) ?: continue
                    val opts = mutableListOf<String>()
                    val optsArr = wObj.optJSONArray("options")
                    if (optsArr != null) {
                        for (j in 0 until optsArr.length()) opts += optsArr.optString(j)
                    }
                    if (opts.size != 4) continue
                    wrong += WrongQuestion(
                        text = wObj.optString("text"),
                        options = opts,
                        correctIndex = wObj.optInt("correctIndex", 0),
                        chosenIndex = wObj.optInt("chosenIndex", -1),
                        explanation = wObj.optString("explanation"),
                        form = wObj.optString("form")
                    )
                }
            }
            HistoryEntry(
                id = obj.optLong("id", 0L),
                grade = obj.optInt("grade", 1),
                week = if (obj.isNull("week")) null else obj.optInt("week"),
                examTitle = if (obj.isNull("examTitle")) null else obj.optString("examTitle"),
                correct = obj.optInt("correct", 0),
                total = obj.optInt("total", 0),
                wrong = wrong
            )
        } catch (_: Exception) {
            null
        }
    }
}
