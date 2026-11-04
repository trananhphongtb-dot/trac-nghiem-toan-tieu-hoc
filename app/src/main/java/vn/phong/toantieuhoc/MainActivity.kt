package vn.phong.toantieuhoc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import vn.phong.toantieuhoc.data.HistoryStore
import vn.phong.toantieuhoc.data.generateExamSet
import vn.phong.toantieuhoc.data.generateWeeklySet
import vn.phong.toantieuhoc.data.weekDef
import vn.phong.toantieuhoc.model.ExamKind
import vn.phong.toantieuhoc.model.HistoryEntry
import vn.phong.toantieuhoc.model.Question
import vn.phong.toantieuhoc.model.QuizResult
import vn.phong.toantieuhoc.model.WeekDef
import vn.phong.toantieuhoc.model.WrongQuestion
import vn.phong.toantieuhoc.ui.HistoryScreen
import vn.phong.toantieuhoc.ui.HomeScreen
import vn.phong.toantieuhoc.ui.QuizScreen
import vn.phong.toantieuhoc.ui.ResultScreen
import vn.phong.toantieuhoc.ui.WeekScreen
import vn.phong.toantieuhoc.ui.WrongReviewScreen

private sealed interface Screen {
    data object Home : Screen
    data object Weeks : Screen
    data object Quiz : Screen
    data object Result : Screen
    data object History : Screen
    data object WrongReview : Screen
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val historyStore = HistoryStore(this)
        setContent {
            MaterialTheme {
                var screen by remember { mutableStateOf<Screen>(Screen.Home) }
                var grade by remember { mutableStateOf(1) }
                var quizTitle by remember { mutableStateOf("") }
                var questions by remember { mutableStateOf<List<Question>>(emptyList()) }
                var index by remember { mutableStateOf(0) }
                var answers by remember { mutableStateOf<List<Int?>>(emptyList()) }
                var result by remember { mutableStateOf<QuizResult?>(null) }
                var historyEntries by remember { mutableStateOf(historyStore.load()) }
                // Phạm vi của đề đang làm, dùng cho Làm lại ở màn kết quả và
                // cho entry lịch sử (không phụ thuộc vào việc phân tích tiêu đề).
                var currentWeek by remember { mutableStateOf<Int?>(null) }
                var currentExam by remember { mutableStateOf<ExamKind?>(null) }
                // Id entry lịch sử nguồn khi lượt này được mở bằng Làm lại từ
                // 1 dòng lịch sử; null với các lượt làm bình thường.
                var redoSourceId by remember { mutableStateOf<Long?>(null) }
                var reviewEntry by remember { mutableStateOf<HistoryEntry?>(null) }

                fun startWith(newQuestions: List<Question>, newTitle: String) {
                    questions = newQuestions
                    answers = List(newQuestions.size) { null }
                    index = 0
                    result = null
                    quizTitle = newTitle
                    screen = Screen.Quiz
                }

                fun startWeek(w: WeekDef, redoId: Long? = null) {
                    grade = w.grade
                    currentWeek = w.week
                    currentExam = null
                    redoSourceId = redoId
                    startWith(
                        generateWeeklySet(w.grade, w.week),
                        "Lớp ${w.grade} • Tuần ${w.week}: ${w.title}" + if (w.estimated) " (ước tính)" else ""
                    )
                }

                fun startExam(exam: ExamKind, redoId: Long? = null) {
                    val note = if (exam.isPracticeFor(grade)) " • Đề luyện" else ""
                    currentWeek = null
                    currentExam = exam
                    redoSourceId = redoId
                    startWith(
                        generateExamSet(grade, exam),
                        "Lớp $grade • ${exam.title}$note • Tuần ${exam.weeks.first}–${exam.weeks.last}"
                    )
                }

                fun finishQuiz() {
                    if (screen != Screen.Quiz) return
                    val correct = questions.indices.count { i -> answers.getOrNull(i) == questions[i].correctIndex }
                    val wrong = questions.filterIndexed { i, _ -> answers.getOrNull(i) != questions[i].correctIndex }
                    val r = QuizResult(grade, quizTitle, questions.size, correct, wrong, answers)
                    result = r
                    // Xây entry lịch sử có cấu trúc, kèm danh sách câu sai đầy đủ
                    // để màn lịch sử xem lại được về sau.
                    val wrongList = questions.mapIndexedNotNull { i, q ->
                        val chosen = answers.getOrNull(i)
                        if (chosen == q.correctIndex) null else WrongQuestion(
                            text = q.text,
                            options = q.options,
                            correctIndex = q.correctIndex,
                            chosenIndex = chosen ?: -1,
                            explanation = q.explanation,
                            form = q.form
                        )
                    }
                    val newEntry = HistoryEntry(
                        id = System.currentTimeMillis(),
                        grade = grade,
                        week = currentWeek,
                        examTitle = currentExam?.title,
                        correct = correct,
                        total = questions.size,
                        wrong = wrongList
                    )
                    // Lưu câu sai cũ và xóa khi làm lại đúng:
                    // nếu lượt này là Làm lại từ 1 entry, xóa khỏi danh sách
                    // câu sai của entry nguồn bất kỳ câu nào mà lượt này đã
                    // trả lời đúng với cùng nội dung câu hỏi; nếu lượt này
                    // đúng hết thì xóa sạch danh sách câu sai của entry nguồn.
                    // Entry nguồn được giữ lại (chỉ cập nhật danh sách câu sai),
                    // đồng thời vẫn ghi thêm 1 entry mới cho chính lượt này.
                    val sourceId = redoSourceId
                    if (sourceId != null) {
                        val source = historyStore.load().firstOrNull { it.id == sourceId }
                        if (source != null) {
                            val correctlyAnsweredTexts = questions.mapIndexedNotNull { i, q ->
                                if (answers.getOrNull(i) == q.correctIndex) q.text else null
                            }.toSet()
                            val remainingWrong = if (correct == questions.size) {
                                emptyList()
                            } else {
                                source.wrong.filter { it.text !in correctlyAnsweredTexts }
                            }
                            historyStore.update(source.copy(wrong = remainingWrong))
                        }
                    }
                    redoSourceId = null
                    historyStore.save(newEntry)
                    historyEntries = historyStore.load()
                    screen = Screen.Result
                }

                when (screen) {
                    Screen.Home -> HomeScreen(
                        onGradeSelected = { grade = it; screen = Screen.Weeks },
                        onHistory = { historyEntries = historyStore.load(); screen = Screen.History }
                    )
                    Screen.Weeks -> WeekScreen(
                        grade = grade,
                        onWeek = { startWeek(it) },
                        onExam = { startExam(it) },
                        onBack = { screen = Screen.Home }
                    )
                    Screen.Quiz -> QuizScreen(
                        questions = questions,
                        index = index,
                        answers = answers,
                        onAnswer = { pick ->
                            if (answers.getOrNull(index) == null) {
                                answers = answers.toMutableList().also { it[index] = pick }
                            }
                        },
                        onIndex = { index = it },
                        onSubmit = { finishQuiz() },
                        onHome = { screen = Screen.Home },
                        onTimeUp = { finishQuiz() }
                    )
                    Screen.Result -> {
                        val r = result
                        if (r != null) {
                            ResultScreen(
                                result = r,
                                questions = questions,
                                onRetry = {
                                    // Làm đề mới với cùng phạm vi đang làm, sinh
                                    // đề ngẫu nhiên mới, không phải đề cũ.
                                    val w = currentWeek
                                    val e = currentExam
                                    if (w != null) {
                                        startWeek(weekDef(grade, w))
                                    } else if (e != null) {
                                        startExam(e)
                                    } else {
                                        screen = Screen.Weeks
                                    }
                                },
                                onHome = { screen = Screen.Home }
                            )
                        } else {
                            screen = Screen.Home
                        }
                    }
                    Screen.History -> HistoryScreen(
                        entries = historyEntries,
                        onRedo = { entry ->
                            // Làm lại đúng lớp và đúng tuần/đề kỳ của entry đó,
                            // sinh đề mới ngẫu nhiên, đồng thời ghi nhớ id nguồn
                            // để khi nộp bài thì xóa bớt câu sai cũ đã làm đúng.
                            grade = entry.grade
                            val w = entry.week
                            if (w != null) {
                                startWeek(weekDef(entry.grade, w), redoId = entry.id)
                            } else {
                                val exam = ExamKind.values().firstOrNull { it.title == entry.examTitle }
                                if (exam != null) {
                                    startExam(exam, redoId = entry.id)
                                } else {
                                    screen = Screen.Weeks
                                }
                            }
                        },
                        onReview = { entry ->
                            reviewEntry = entry
                            screen = Screen.WrongReview
                        },
                        onBack = { screen = Screen.Home }
                    )
                    Screen.WrongReview -> {
                        val entry = reviewEntry
                        if (entry != null) {
                            WrongReviewScreen(
                                entry = entry,
                                onBack = { historyEntries = historyStore.load(); screen = Screen.History }
                            )
                        } else {
                            screen = Screen.History
                        }
                    }
                }
            }
        }
    }
}
