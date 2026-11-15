package vn.phong.baitaptuan

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import vn.phong.baitaptuan.ui.AppTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import vn.phong.baitaptuan.data.HistoryStore
import vn.phong.baitaptuan.data.generateExamSet
import vn.phong.baitaptuan.data.mixToanTuDuy
import vn.phong.baitaptuan.data.generateWeeklySet
import vn.phong.baitaptuan.data.generateSubjectExam
import vn.phong.baitaptuan.data.generateSubjectWeekly
import vn.phong.baitaptuan.data.weekDefFor
import vn.phong.baitaptuan.model.ExamKind
import vn.phong.baitaptuan.model.HistoryEntry
import vn.phong.baitaptuan.model.Question
import vn.phong.baitaptuan.model.QuizResult
import vn.phong.baitaptuan.model.Subject
import vn.phong.baitaptuan.model.WeekDef
import vn.phong.baitaptuan.model.WrongQuestion
import vn.phong.baitaptuan.ui.HistoryScreen
import vn.phong.baitaptuan.ui.GradeScreen
import vn.phong.baitaptuan.ui.HomeScreen
import vn.phong.baitaptuan.ui.ModeScreen
import vn.phong.baitaptuan.ui.QuizScreen
import vn.phong.baitaptuan.ui.ResultScreen
import vn.phong.baitaptuan.ui.WeekScreen
import vn.phong.baitaptuan.ui.WrongReviewScreen

private sealed interface Screen {
    data object Home : Screen
    data object Grades : Screen
    data object Weeks : Screen
    data object Mode : Screen
    data object Quiz : Screen
    data object Result : Screen
    data object History : Screen
    data object WrongReview : Screen
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        val historyStore = HistoryStore(this)
        setContent {
            AppTheme {
                var screen by remember { mutableStateOf<Screen>(Screen.Home) }
                var subject by remember { mutableStateOf(Subject.TOAN) }
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
                // Chế độ làm bài chọn lúc vào đề, không đổi giữa chừng:
                // true = biết đúng/sai ngay, không đổi đáp án; false = đổi được, nộp mới biết.
                var instant by remember { mutableStateOf(true) }
                var modeBack by remember { mutableStateOf<Screen>(Screen.Home) }

                fun startWith(newQuestions: List<Question>, newTitle: String) {
                    questions = newQuestions
                    answers = List(newQuestions.size) { null }
                    index = 0
                    result = null
                    quizTitle = newTitle
                    modeBack = if (screen == Screen.Mode || screen == Screen.Quiz || screen == Screen.Result) Screen.Weeks else screen
                    screen = Screen.Mode
                }

                fun startWeek(w: WeekDef, redoId: Long? = null) {
                    grade = w.grade
                    currentWeek = w.week
                    currentExam = null
                    redoSourceId = redoId
                    startWith(
                        if (subject == Subject.TOAN) mixToanTuDuy(generateWeeklySet(w.grade, w.week), w.grade)
                        else generateSubjectWeekly(subject, w.grade, w.week),
                        "${subject.titleFor(w.grade)} • Lớp ${w.grade} • Tuần ${w.week}: ${w.title}" + if (w.estimated) " (ước tính)" else ""
                    )
                }

                fun startExam(exam: ExamKind, redoId: Long? = null) {
                    val note = if (exam.isPracticeFor(grade)) " • Đề luyện" else ""
                    currentWeek = null
                    currentExam = exam
                    redoSourceId = redoId
                    startWith(
                        if (subject == Subject.TOAN) mixToanTuDuy(generateExamSet(grade, exam), grade, 8)
                        else generateSubjectExam(subject, grade, exam),
                        "${subject.titleFor(grade)} • Lớp $grade • ${exam.title}$note • Tuần ${exam.weeks.first}–${exam.weeks.last}"
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
                        wrong = wrongList,
                        subject = subject
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
                        onSubjectSelected = { subject = it; screen = Screen.Grades },
                        onHistory = { historyEntries = historyStore.load(); screen = Screen.History }
                    )
                    Screen.Grades -> GradeScreen(
                        subject = subject,
                        onGradeSelected = { grade = it; screen = Screen.Weeks },
                        onBack = { screen = Screen.Home }
                    )
                    Screen.Weeks -> WeekScreen(
                        subject = subject,
                        grade = grade,
                        entries = historyEntries,
                        onWeek = { startWeek(it) },
                        onExam = { startExam(it) },
                        onBack = { screen = Screen.Grades }
                    )
                    Screen.Mode -> ModeScreen(
                        title = quizTitle,
                        onPick = { instant = it; screen = Screen.Quiz },
                        onBack = { screen = modeBack }
                    )
                    Screen.Quiz -> QuizScreen(
                        subject = subject,
                        instant = instant,
                        questions = questions,
                        index = index,
                        answers = answers,
                        onAnswer = { pick ->
                            if (!instant || answers.getOrNull(index) == null) {
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
                                        startWeek(weekDefFor(subject, grade, w))
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
                            subject = entry.subject
                            grade = entry.grade
                            val w = entry.week
                            if (w != null) {
                                startWeek(weekDefFor(entry.subject, entry.grade, w), redoId = entry.id)
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
