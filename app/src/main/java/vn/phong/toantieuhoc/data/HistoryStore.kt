package vn.phong.toantieuhoc.data

import android.content.Context

/** Lưu lịch sử làm bài đơn giản trên máy, không cần tài khoản, không cần mạng. */
class HistoryStore(context: Context) {
    private val prefs = context.getSharedPreferences("math_quiz_history", Context.MODE_PRIVATE)

    fun save(line: String) {
        val old = prefs.getString("lines", "") ?: ""
        val updated = (listOf(line) + old.split("\n").filter { it.isNotBlank() }).take(50)
        prefs.edit().putString("lines", updated.joinToString("\n")).apply()
    }

    fun load(): List<String> {
        return (prefs.getString("lines", "") ?: "").split("\n").filter { it.isNotBlank() }
    }
}
