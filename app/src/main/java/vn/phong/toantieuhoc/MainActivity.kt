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
import vn.phong.toantieuhoc.model.ExamKind
import vn.phong.toantieuhoc.model.Question
import vn.phong.toantieuhoc.model.QuizResult
import vn.phong.toantieuhoc.model.WeekDef
import vn.phong.toantieuhoc.ui.BgTheme
import vn.phong.toantieuhoc.ui.HistoryScreen
import vn.phong.toantieuhoc.ui.HomeScreen
import vn.phong.toantieuhoc.ui.QuizScreen
import vn.phong.toantieuhoc.ui.ResultScreen
import vn.phong.toantieuhoc.ui.WeekScreen
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private sealed interface Screen {
    data object Home : Screen
    data object Weeks : Screen
    data object Quiz : Screen
    data object Result : Screen
    data object History : Screen
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val historyStore = HistoryStore(this)
        setContent {
            MaterialTheme {
                var screen by remember { mutableStateOf<Screen>(Screen.Home) }
                var grade by remember { mutableStateOf(1) }
                var theme by remember { mutableStateOf(BgTheme.BLUE) }
                var quizTitle by remember { mutableStateOf("") }
                var questions by remember { mutableStateOf<List<Question>>(emptyList()) }
                var index by remember { mutableStateOf(0) }
                var answers by remember { mutableStateOf<List<Int?>>(emptyList()) }
                var result by remember { mutableStateOf<QuizResult?>(null) }
                var historyLines by remember { mutableStateOf(historyStore.load()) }

                fun startWith(newQuestions: List<Question>, newTitle: String) {
                    questions = newQuestions
                    answers = List(newQuestions.size) { null }
                    index = 0
                    result = null
                    quizTitle = newTitle
                    screen = Screen.Quiz
                }

                fun startWeek(w: WeekDef) {
                    grade = w.grade
                    startWith(
                        generateWeeklySet(w.grade, w.week),
                        "Lớp ${w.grade} • Tuần ${w.week}: ${w.title}" + if (w.estimated) " (ước tính)" else ""
                    )
                }

                fun startExam(exam: ExamKind) {
                    val note = if (exam.isPracticeFor(grade)) " • Đề luyện" else ""
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
                    val date = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale("vi", "VN")).format(Date())
                    historyStore.save("$date • $quizTitle • ${r.correct}/${r.total} đúng • ${"%.1f".format(r.score10)}/10 • ${r.stars} sao")
                    historyLines = historyStore.load()
                    screen = Screen.Result
                }

                when (screen) {
                    Screen.Home -> HomeScreen(
                        theme = theme,
                        onTheme = { theme = it },
                        onGradeSelected = { grade = it; screen = Screen.Weeks },
                        onHistory = { historyLines = historyStore.load(); screen = Screen.History }
                    )
                    Screen.Weeks -> WeekScreen(
                        grade = grade,
                        theme = theme,
                        onWeek = { startWeek(it) },
                        onExam = { startExam(it) },
                        onBack = { screen = Screen.Home }
                    )
                    Screen.Quiz -> QuizScreen(
                        title = quizTitle,
                        questions = questions,
                        index = index,
                        answers = answers,
                        theme = theme,
                        onAnswer = { pick ->
                            if (answers.getOrNull(index) == null) {
                                answers = answers.toMutableList().also { it[index] = pick }
                            }
                        },
                        onIndex = { index = it },
                        onSubmit = { finishQuiz() },
                        onTimeUp = { finishQuiz() }
                    )
                    Screen.Result -> {
                        val r = result
                        if (r != null) {
                            ResultScreen(
                                result = r,
                                questions = questions,
                                theme = theme,
                                onRetry = {
                                    // Làm đề mới: sinh lại từ đầu với cùng lựa chọn gần nhất.
                                    // Tiêu đề giữ nguyên, nhưng bộ câu hỏi là bộ mới hoàn toàn
                                    // vì generateWeeklySet / generateExamSet dùng Random mới.
                                    val title = quizTitle
                                    val weekMatch = Regex("Tuần (\\d+):").find(title)
                                    val week = weekMatch?.groupValues?.get(1)?.toIntOrNull()
                                    if (week != null && !title.contains("Đề")) {
                                        startWeek(vn.phong.toantieuhoc.data.weekDef(grade, week))
                                    } else {
                                        val exam = ExamKind.values().firstOrNull { title.contains(it.title) }
                                        if (exam != null) startExam(exam) else screen = Screen.Weeks
                                    }
                                },
                                onHome = { screen = Screen.Home }
                            )
                        } else {
                            screen = Screen.Home
                        }
                    }
                    Screen.History -> HistoryScreen(lines = historyLines, theme = theme, onBack = { screen = Screen.Home })
                }
            }
        }
    }
}
