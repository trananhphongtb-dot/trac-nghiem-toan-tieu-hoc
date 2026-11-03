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
import vn.phong.toantieuhoc.data.generateQuestions
import vn.phong.toantieuhoc.model.Difficulty
import vn.phong.toantieuhoc.model.Question
import vn.phong.toantieuhoc.model.QuizResult
import vn.phong.toantieuhoc.model.Topic
import vn.phong.toantieuhoc.ui.HistoryScreen
import vn.phong.toantieuhoc.ui.HomeScreen
import vn.phong.toantieuhoc.ui.QuizScreen
import vn.phong.toantieuhoc.ui.ResultScreen
import vn.phong.toantieuhoc.ui.SetupScreen
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.random.Random

private sealed interface Screen {
    data object Home : Screen
    data object Setup : Screen
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
                var topic by remember { mutableStateOf(Topic.ALL) }
                var difficulty by remember { mutableStateOf(Difficulty.VUA) }
                var modeCount by remember { mutableStateOf(10) }
                var questions by remember { mutableStateOf<List<Question>>(emptyList()) }
                var index by remember { mutableStateOf(0) }
                var selected by remember { mutableStateOf<Int?>(null) }
                var score by remember { mutableStateOf(0) }
                var chosen by remember { mutableStateOf<List<Int>>(emptyList()) }
                var result by remember { mutableStateOf<QuizResult?>(null) }
                var historyLines by remember { mutableStateOf(historyStore.load()) }

                fun startQuiz() {
                    questions = generateQuestions(grade, topic, difficulty, modeCount, Random(System.currentTimeMillis()))
                    index = 0
                    selected = null
                    score = 0
                    chosen = emptyList()
                    result = null
                    screen = Screen.Quiz
                }

                fun finishQuiz() {
                    val wrong = questions.filterIndexed { i, q ->
                        chosen.getOrNull(i) != q.correctIndex
                    }
                    val r = QuizResult(grade, topic, difficulty, questions.size, score, wrong, chosen)
                    result = r
                    val date = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale("vi", "VN")).format(Date())
                    historyStore.save("$date • Lớp $grade • ${topic.title} • ${r.correct}/${r.total} đúng • ${r.stars} sao")
                    historyLines = historyStore.load()
                    screen = Screen.Result
                }

                when (screen) {
                    Screen.Home -> HomeScreen(
                        onGradeSelected = { grade = it; topic = Topic.ALL; screen = Screen.Setup },
                        onHistory = { historyLines = historyStore.load(); screen = Screen.History }
                    )
                    Screen.Setup -> SetupScreen(
                        grade = grade,
                        topic = topic,
                        difficulty = difficulty,
                        modeCount = modeCount,
                        onTopic = { topic = it },
                        onDifficulty = { difficulty = it },
                        onMode = { modeCount = it },
                        onStart = { startQuiz() },
                        onBack = { screen = Screen.Home }
                    )
                    Screen.Quiz -> QuizScreen(
                        questions = questions,
                        index = index,
                        selected = selected,
                        score = score,
                        onSelect = { pick ->
                            if (selected == null) {
                                selected = pick
                                chosen = chosen + pick
                                if (pick == questions[index].correctIndex) score += 1
                            }
                        },
                        onNext = {
                            if (index == questions.lastIndex) finishQuiz()
                            else { index += 1; selected = null }
                        }
                    )
                    Screen.Result -> {
                        val r = result
                        if (r != null) {
                            ResultScreen(result = r, onRetry = { startQuiz() }, onHome = { screen = Screen.Home })
                        } else {
                            screen = Screen.Home
                        }
                    }
                    Screen.History -> HistoryScreen(lines = historyLines, onBack = { screen = Screen.Home })
                }
            }
        }
    }
}
