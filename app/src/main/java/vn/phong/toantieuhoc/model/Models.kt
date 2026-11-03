package vn.phong.toantieuhoc.model

/** Một câu trắc nghiệm được sinh ngẫu nhiên trong một lượt làm bài.
 *  Không có câu cố định: mỗi lần sinh, số, thứ tự câu và thứ tự đáp án đều khác.
 */
data class Question(
    val text: String,
    val options: List<String>,
    val correctIndex: Int,
    val explanation: String,
    val form: String
) {
    val correctAnswer: String get() = options[correctIndex]
}

data class QuizResult(
    val grade: Int,
    val title: String,
    val total: Int,
    val correct: Int,
    val wrongQuestions: List<Question>,
    val chosenAnswers: List<Int?>
) {
    val score10: Double get() = if (total == 0) 0.0 else correct * 10.0 / total
    val stars: Int
        get() = when {
            total == 0 -> 0
            correct * 10 >= total * 9 -> 3
            correct * 10 >= total * 7 -> 2
            correct * 10 >= total * 5 -> 1
            else -> 0
        }
}

/** Một tuần học. [estimated] = true khi báo cáo nghiên cứu ghi tuần đó là
 *  ước tính theo giai đoạn (dấu ~), không phải tuần chính xác trong PPCT.
 */
data class WeekDef(
    val grade: Int,
    val week: Int,
    val title: String,
    val estimated: Boolean,
    val forms: List<String>
)

enum class ExamKind(val title: String, val weeks: IntRange) {
    MID_HK1("Đề Giữa học kỳ 1", 1..10),
    END_HK1("Đề Cuối học kỳ 1", 1..18),
    MID_HK2("Đề Giữa học kỳ 2", 19..26),
    END_YEAR("Đề Cuối năm", 1..35);

    /** Lớp 1-3 không có kiểm tra viết giữa kỳ bắt buộc (Thông tư 27),
     *  nên đề giữa kỳ của các lớp này chỉ là đề luyện. */
    fun isPracticeFor(grade: Int): Boolean =
        (this == MID_HK1 || this == MID_HK2) && grade <= 3
}
