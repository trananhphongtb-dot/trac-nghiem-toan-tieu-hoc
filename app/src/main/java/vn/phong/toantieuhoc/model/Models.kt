package vn.phong.toantieuhoc.model

enum class Topic(val title: String) {
    ALL("Tất cả chủ đề"),
    SO_PHEP_TINH("Số và phép tính"),
    HINH_HOC("Hình học"),
    DO_LUONG("Đo lường"),
    LOI_VAN("Toán có lời văn"),
    PHAN_SO_THAP_PHAN("Phân số - Thập phân"),
    NANG_CAO("Toán nâng cao (Khá - Giỏi)")
}

enum class Difficulty(val title: String) {
    DE("Dễ"),
    VUA("Vừa"),
    KHO("Khó")
}

data class Question(
    val id: String,
    val grade: Int,
    val topic: Topic,
    val level: Difficulty,
    val text: String,
    val options: List<String>,
    val correctIndex: Int,
    val explanation: String
) {
    val correctAnswer: String get() = options[correctIndex]
}

data class QuizResult(
    val grade: Int,
    val topic: Topic,
    val difficulty: Difficulty,
    val total: Int,
    val correct: Int,
    val wrongQuestions: List<Question>,
    val chosenAnswers: List<Int>
) {
    val stars: Int
        get() = when {
            total == 0 -> 0
            correct * 10 >= total * 9 -> 3
            correct * 10 >= total * 7 -> 2
            correct * 10 >= total * 5 -> 1
            else -> 0
        }
}
