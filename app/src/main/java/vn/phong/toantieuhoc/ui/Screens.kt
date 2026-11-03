package vn.phong.toantieuhoc.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import vn.phong.toantieuhoc.data.weekDef
import vn.phong.toantieuhoc.model.ExamKind
import vn.phong.toantieuhoc.model.Question
import vn.phong.toantieuhoc.model.QuizResult
import vn.phong.toantieuhoc.model.WeekDef

enum class BgTheme(val title: String, val top: Color, val bottom: Color, val accent: Color) {
    BLUE("Xanh dương", Color(0xFF1565C0), Color(0xFFE3F2FD), Color(0xFF1565C0)),
    ORANGE("Hồng cam", Color(0xFFE64A19), Color(0xFFFFF3E0), Color(0xFFE64A19)),
    GREEN("Xanh lá", Color(0xFF2E7D32), Color(0xFFE8F5E9), Color(0xFF2E7D32))
}

/** Ảnh nền dọc: gradient + hình vẽ vector (tròn, tam giác) vẽ bằng Canvas,
 *  không tải ảnh ngoài nên APK vẫn nhẹ. */
@Composable
fun BgBox(theme: BgTheme, content: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(theme.top.copy(alpha = 0.18f), theme.bottom)))
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(Color.White.copy(alpha = 0.35f), radius = size.width * 0.35f, center = Offset(size.width * 0.9f, size.height * 0.08f))
            drawCircle(theme.accent.copy(alpha = 0.12f), radius = size.width * 0.45f, center = Offset(size.width * 0.05f, size.height * 0.55f))
            drawCircle(Color.White.copy(alpha = 0.3f), radius = size.width * 0.18f, center = Offset(size.width * 0.15f, size.height * 0.92f))
        }
        content()
    }
}

@Composable
fun HomeScreen(
    theme: BgTheme,
    onTheme: (BgTheme) -> Unit,
    onGradeSelected: (Int) -> Unit,
    onHistory: () -> Unit
) {
    BgBox(theme) {
        Column(
            modifier = Modifier.fillMaxSize().padding(20.dp).verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Text("Bài Tập Tuần", fontSize = 34.sp, fontWeight = FontWeight.Bold, color = theme.accent)
            Text("Trắc nghiệm Toán tiểu học • Lớp 1 đến lớp 5", fontSize = 20.sp, color = Color.Gray)
            Text("Mỗi tuần 40 câu trắc nghiệm, mỗi lần làm là một đề mới, không cố định.", fontSize = 18.sp)
            Text("Con chọn lớp của mình nhé:", fontSize = 22.sp, fontWeight = FontWeight.SemiBold)
            (1..5).forEach { grade ->
                Button(
                    onClick = { onGradeSelected(grade) },
                    modifier = Modifier.fillMaxWidth().height(64.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = theme.accent)
                ) { Text("Lớp $grade", fontSize = 24.sp) }
            }
            Text("Chọn ảnh nền:", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                BgTheme.values().forEach { t ->
                    if (t == theme) {
                        Button(onClick = { onTheme(t) }) { Text(t.title, fontSize = 16.sp) }
                    } else {
                        OutlinedButton(onClick = { onTheme(t) }) { Text(t.title, fontSize = 16.sp) }
                    }
                }
            }
            OutlinedButton(onClick = onHistory, modifier = Modifier.fillMaxWidth().height(56.dp)) {
                Text("Xem lịch sử làm bài", fontSize = 20.sp)
            }
            Text("Không quảng cáo, không đăng nhập, không cần mạng. Đề sinh ngẫu nhiên, không lặp lại bộ cũ.", fontSize = 16.sp, color = Color.Gray)
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun WeekScreen(
    grade: Int,
    theme: BgTheme,
    onWeek: (WeekDef) -> Unit,
    onExam: (ExamKind) -> Unit,
    onBack: () -> Unit
) {
    BgBox(theme) {
        Column(modifier = Modifier.fillMaxSize().padding(20.dp)) {
            Text("Lớp $grade • Bài tập tuần", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = theme.accent)
            Text("Khung tuần theo bộ Kết nối tri thức. Tuần ghi “ước tính” là tuần theo giai đoạn, trường có thể lệch 1 tuần.", fontSize = 16.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(8.dp))
            Column(modifier = Modifier.verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text("Đề kiểm tra", fontSize = 22.sp, fontWeight = FontWeight.SemiBold)
                ExamKind.values().forEach { exam ->
                    Card(
                        modifier = Modifier.fillMaxWidth().clickable { onExam(exam) },
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text(exam.title, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                            Text(
                                if (exam.isPracticeFor(grade)) "Đề luyện • Phạm vi tuần ${exam.weeks.first}–${exam.weeks.last} • Lớp $grade không có kiểm tra viết giữa kỳ bắt buộc"
                                else "Phạm vi tuần ${exam.weeks.first}–${exam.weeks.last} • 40 câu",
                                fontSize = 16.sp, color = Color.Gray
                            )
                        }
                    }
                }
                Text("Học kỳ 1 • Tuần 1–18", fontSize = 22.sp, fontWeight = FontWeight.SemiBold)
                WeekGrid(grade, 1..18, onWeek)
                Text("Học kỳ 2 • Tuần 19–35", fontSize = 22.sp, fontWeight = FontWeight.SemiBold)
                WeekGrid(grade, 19..35, onWeek)
                OutlinedButton(onClick = onBack, modifier = Modifier.fillMaxWidth().height(56.dp)) {
                    Text("Quay lại", fontSize = 20.sp)
                }
            }
        }
    }
}

@Composable
private fun WeekGrid(grade: Int, range: IntRange, onWeek: (WeekDef) -> Unit) {
    val weeks = range.map { weekDef(grade, it) }
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxWidth().height((((weeks.size + 1) / 2) * 118).dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        userScrollEnabled = false
    ) {
        items(weeks) { w ->
            Card(
                modifier = Modifier.fillMaxWidth().height(108.dp).clickable { onWeek(w) },
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text("Tuần ${w.week}", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Text(w.title + if (w.estimated) " (ước tính)" else "", fontSize = 15.sp, color = Color.Gray)
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun QuizScreen(
    title: String,
    questions: List<Question>,
    index: Int,
    answers: List<Int?>,
    theme: BgTheme,
    onAnswer: (Int) -> Unit,
    onIndex: (Int) -> Unit,
    onSubmit: () -> Unit,
    onTimeUp: () -> Unit
) {
    var remaining by remember(questions) { mutableStateOf(60 * 60) }
    LaunchedEffect(questions) {
        while (remaining > 0) {
            delay(1000)
            remaining -= 1
        }
        onTimeUp()
    }
    if (questions.isEmpty()) {
        BgBox(theme) {
            Column(modifier = Modifier.fillMaxSize().padding(20.dp)) {
                Text("Chưa sinh được đề. Con bấm Làm đề mới nhé.", fontSize = 22.sp)
            }
        }
        return
    }
    val q = questions[index]
    val chosen = answers.getOrNull(index)
    val answered = chosen != null
    val answeredCount = answers.count { it != null }
    val minutes = remaining / 60
    val seconds = remaining % 60
    val timeText = "%02d:%02d".format(minutes, seconds)
    BgBox(theme) {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Câu ${index + 1}/${questions.size} • Đã làm $answeredCount", fontSize = 17.sp, fontWeight = FontWeight.SemiBold)
                Text("⏰ $timeText", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = if (remaining < 300) Color.Red else theme.accent)
            }
            Spacer(modifier = Modifier.height(6.dp))
            LinearProgressIndicator(
                progress = { answeredCount.toFloat() / questions.size.toFloat() },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(10.dp))
            Column(modifier = Modifier.weight(1f).verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color.White)) {
                    Text(q.text, fontSize = 24.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(20.dp))
                }
                q.options.forEachIndexed { optionIndex, option ->
                    val color = when {
                        !answered -> null
                        optionIndex == q.correctIndex -> Color(0xFF2E7D32)
                        optionIndex == chosen -> Color(0xFFC62828)
                        else -> null
                    }
                    Button(
                        onClick = { if (!answered) onAnswer(optionIndex) },
                        modifier = Modifier.fillMaxWidth().height(60.dp),
                        colors = if (color != null) ButtonDefaults.buttonColors(containerColor = color) else ButtonDefaults.buttonColors(containerColor = theme.accent)
                    ) {
                        Text("${'A' + optionIndex}. $option", fontSize = 21.sp)
                    }
                }
                if (answered) {
                    Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color.White)) {
                        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            Text(
                                if (chosen == q.correctIndex) "Đúng rồi! Tuyệt vời!" else "Chưa đúng, đáp án là ${q.correctAnswer}.",
                                fontSize = 20.sp, fontWeight = FontWeight.Bold
                            )
                            Text(q.explanation, fontSize = 18.sp)
                        }
                    }
                }
                Text("Sơ đồ 40 câu (bấm vào số để nhảy tới câu đó):", fontSize = 17.sp, fontWeight = FontWeight.SemiBold)
                FlowRow(horizontalArrangement = Arrangement.spacedBy(6.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    questions.indices.forEach { i ->
                        val bg = when {
                            i == index -> theme.accent
                            answers.getOrNull(i) != null -> Color(0xFF2E7D32)
                            else -> Color.LightGray
                        }
                        Box(
                            modifier = Modifier.size(40.dp).background(bg, shape = androidx.compose.foundation.shape.CircleShape).clickable { onIndex(i) },
                            contentAlignment = Alignment.Center
                        ) {
                            Text("${i + 1}", fontSize = 16.sp, color = Color.White, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlinedButton(
                    onClick = { if (index > 0) onIndex(index - 1) },
                    modifier = Modifier.weight(1f).height(56.dp),
                    enabled = index > 0
                ) { Text("Câu trước", fontSize = 19.sp) }
                Button(
                    onClick = { if (index < questions.lastIndex) onIndex(index + 1) else onSubmit() },
                    modifier = Modifier.weight(1f).height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = theme.accent)
                ) { Text(if (index == questions.lastIndex) "Nộp bài" else "Câu tiếp", fontSize = 19.sp) }
            }
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedButton(onClick = onSubmit, modifier = Modifier.fillMaxWidth().height(52.dp)) {
                Text("Nộp bài ngay", fontSize = 19.sp)
            }
        }
    }
}

@Composable
fun ResultScreen(
    result: QuizResult,
    questions: List<Question>,
    theme: BgTheme,
    onRetry: () -> Unit,
    onHome: () -> Unit
) {
    BgBox(theme) {
        Column(modifier = Modifier.fillMaxSize().padding(20.dp).verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(14.dp)) {
            Text("Kết quả", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = theme.accent)
            Text(result.title, fontSize = 20.sp, color = Color.Gray)
            Text("${result.correct}/${result.total} câu đúng • Điểm ${"%.1f".format(result.score10)}/10", fontSize = 26.sp, fontWeight = FontWeight.Bold)
            Text("⭐".repeat(result.stars) + if (result.stars == 0) " Cố gắng thêm nhé!" else "", fontSize = 28.sp)
            if (result.wrongQuestions.isNotEmpty()) {
                Text("Xem lại câu sai:", fontSize = 22.sp, fontWeight = FontWeight.SemiBold)
                questions.forEachIndexed { i, q ->
                    val chosen = result.chosenAnswers.getOrNull(i)
                    if (chosen != null && chosen != q.correctIndex) {
                        Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color.White)) {
                            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                Text("Câu ${i + 1}: ${q.text}", fontSize = 19.sp, fontWeight = FontWeight.SemiBold)
                                Text("Con chọn: ${q.options.getOrNull(chosen) ?: "?"}", fontSize = 18.sp, color = Color(0xFFC62828))
                                Text("Đáp án đúng: ${q.correctAnswer}", fontSize = 18.sp, color = Color(0xFF2E7D32))
                                Text(q.explanation, fontSize = 17.sp)
                            }
                        }
                    }
                }
            } else {
                Text("Xuất sắc! Con làm đúng hết rồi!", fontSize = 22.sp)
            }
            Button(onClick = onRetry, modifier = Modifier.fillMaxWidth().height(60.dp), colors = ButtonDefaults.buttonColors(containerColor = theme.accent)) {
                Text("Làm đề mới (đề khác)", fontSize = 22.sp)
            }
            OutlinedButton(onClick = onHome, modifier = Modifier.fillMaxWidth().height(56.dp)) {
                Text("Về trang chính", fontSize = 20.sp)
            }
        }
    }
}

@Composable
fun HistoryScreen(lines: List<String>, theme: BgTheme, onBack: () -> Unit) {
    BgBox(theme) {
        Column(modifier = Modifier.fillMaxSize().padding(20.dp).verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text("Lịch sử làm bài", fontSize = 30.sp, fontWeight = FontWeight.Bold, color = theme.accent)
            if (lines.isEmpty()) {
                Text("Chưa có lượt làm bài nào. Con làm thử 1 đề nhé!", fontSize = 20.sp)
            } else {
                lines.forEach { line ->
                    Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color.White)) {
                        Text(line, fontSize = 18.sp, modifier = Modifier.padding(16.dp))
                    }
                }
            }
            OutlinedButton(onClick = onBack, modifier = Modifier.fillMaxWidth().height(56.dp)) {
                Text("Quay lại", fontSize = 20.sp)
            }
        }
    }
}
