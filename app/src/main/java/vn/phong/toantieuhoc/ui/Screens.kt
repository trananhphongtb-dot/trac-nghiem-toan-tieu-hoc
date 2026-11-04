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
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import vn.phong.toantieuhoc.data.weekDef
import vn.phong.toantieuhoc.model.ExamKind
import vn.phong.toantieuhoc.model.HistoryEntry
import vn.phong.toantieuhoc.model.Question
import vn.phong.toantieuhoc.model.QuizResult
import vn.phong.toantieuhoc.model.WeekDef

// ---------- Nền cố định duy nhất cho toàn app ----------
// Gradient xanh dương đậm trên xuống nhạt dưới + vài hình tròn mờ vẽ bằng
// Canvas. Không tải ảnh ngoài, không có màn chọn nền nào nữa.
private val FixedTop = Color(0xFF0D47A1)
private val FixedMid = Color(0xFF1976D2)
private val FixedBottom = Color(0xFFBBDEFB)
private val FixedAccent = Color(0xFF1565C0)
private val FixedGreen = Color(0xFF2E7D32)
private val FixedRed = Color(0xFFC62828)
private val FixedMuted = Color(0xFF90A4AE)

@Composable
fun BgBox(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(FixedTop, FixedMid, FixedBottom)))
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(Color.White.copy(alpha = 0.35f), radius = size.width * 0.35f, center = Offset(size.width * 0.9f, size.height * 0.08f))
            drawCircle(Color.White.copy(alpha = 0.12f), radius = size.width * 0.45f, center = Offset(size.width * 0.05f, size.height * 0.55f))
            drawCircle(Color.White.copy(alpha = 0.3f), radius = size.width * 0.18f, center = Offset(size.width * 0.15f, size.height * 0.92f))
        }
        content()
    }
}

/** Viên thuốc nhỏ dùng cho số câu và đồng hồ trên đầu màn làm bài. */
@Composable
private fun Pill(text: String, color: Color = Color(0xFF0D47A1)) {
    Box(
        modifier = Modifier.background(Color.White, shape = RoundedCornerShape(50)).padding(horizontal = 14.dp, vertical = 6.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = color, textAlign = TextAlign.Center)
    }
}

@Composable
fun HomeScreen(
    onGradeSelected: (Int) -> Unit,
    onHistory: () -> Unit
) {
    BgBox {
        Column(
            modifier = Modifier.fillMaxSize().padding(20.dp).verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Màn chính tối giản theo bản chốt: chỉ tên app, 5 nút lớp và nút lịch sử,
            // không còn khối chữ hướng dẫn ở trên, không còn dòng chú thích ở dưới.
            Text("Bài Tập Tuần", fontSize = 36.sp, fontWeight = FontWeight.Bold, color = Color.White, textAlign = TextAlign.Center)
            (1..5).forEach { grade ->
                Button(
                    onClick = { onGradeSelected(grade) },
                    modifier = Modifier.fillMaxWidth().height(64.dp),
                    shape = RoundedCornerShape(18.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = FixedAccent)
                ) { Text("Lớp $grade", fontSize = 24.sp, textAlign = TextAlign.Center) }
            }
            Button(
                onClick = onHistory,
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White)
            ) { Text("Xem lịch sử làm bài", fontSize = 20.sp, color = FixedAccent, textAlign = TextAlign.Center) }
        }
    }
}

@Composable
fun WeekScreen(
    grade: Int,
    onWeek: (WeekDef) -> Unit,
    onExam: (ExamKind) -> Unit,
    onBack: () -> Unit
) {
    BgBox {
        Column(modifier = Modifier.fillMaxSize().padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Lớp $grade • Bài tập tuần", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color.White, textAlign = TextAlign.Center)
            Text("Khung tuần theo bộ Kết nối tri thức. Tuần ghi “ước tính” là tuần theo giai đoạn, trường có thể lệch 1 tuần.", fontSize = 16.sp, color = Color.White, textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(8.dp))
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Học kỳ 1 • Tuần 1–18", fontSize = 22.sp, fontWeight = FontWeight.SemiBold, color = Color.White, textAlign = TextAlign.Center)
                WeekGrid(grade, 1..18, onWeek)
                Text("Học kỳ 2 • Tuần 19–35", fontSize = 22.sp, fontWeight = FontWeight.SemiBold, color = Color.White, textAlign = TextAlign.Center)
                WeekGrid(grade, 19..35, onWeek)
                // Đề kiểm tra ở dưới cùng, sau danh sách tuần.
                Text("Đề kiểm tra", fontSize = 22.sp, fontWeight = FontWeight.SemiBold, color = Color.White, textAlign = TextAlign.Center)
                ExamKind.values().forEach { exam ->
                    Card(
                        modifier = Modifier.fillMaxWidth().clickable { onExam(exam) },
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(exam.title, fontSize = 20.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                            Text(
                                if (exam.isPracticeFor(grade)) "Đề luyện • Phạm vi tuần ${exam.weeks.first}–${exam.weeks.last} • Lớp $grade không có kiểm tra viết giữa kỳ bắt buộc"
                                else "Phạm vi tuần ${exam.weeks.first}–${exam.weeks.last} • 40 câu",
                                fontSize = 16.sp, color = Color.Gray, textAlign = TextAlign.Center
                            )
                        }
                    }
                }
                OutlinedButton(
                    onClick = onBack,
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    shape = RoundedCornerShape(18.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = FixedAccent)
                ) {
                    Text("Quay lại", fontSize = 20.sp, textAlign = TextAlign.Center)
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
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Tuần ${w.week}", fontSize = 20.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                    Text(w.title + if (w.estimated) " (ước tính)" else "", fontSize = 15.sp, color = Color.Gray, textAlign = TextAlign.Center)
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun QuizScreen(
    questions: List<Question>,
    index: Int,
    answers: List<Int?>,
    onAnswer: (Int) -> Unit,
    onIndex: (Int) -> Unit,
    onSubmit: () -> Unit,
    onHome: () -> Unit,
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
        BgBox {
            Column(modifier = Modifier.fillMaxSize().padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Chưa sinh được đề. Con bấm Làm đề mới nhé.", fontSize = 22.sp, color = Color.White, textAlign = TextAlign.Center)
            }
        }
        return
    }
    // Sơ đồ nhảy câu chỉ hiện khi bấm nút pill "Đã làm X/40", bình thường ẩn.
    var showMap by remember(questions) { mutableStateOf(false) }
    val q = questions[index]
    val chosen = answers.getOrNull(index)
    val answered = chosen != null
    val answeredCount = answers.count { it != null }
    val minutes = remaining / 60
    val seconds = remaining % 60
    val timeText = "%02d:%02d".format(minutes, seconds)
    BgBox {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            // Trên cùng, tối giản: chỉ thanh tiến trình, đồng hồ và nút pill tiến trình.
            // Không còn tiêu đề "Lớp mấy - Tuần mấy".
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(modifier = Modifier.clickable { showMap = !showMap }) {
                    Pill("Đã làm $answeredCount/${questions.size}")
                }
                Pill("⏰ $timeText", color = if (remaining < 300) FixedRed else FixedAccent)
            }
            Spacer(modifier = Modifier.height(8.dp))
            LinearProgressIndicator(
                progress = { answeredCount.toFloat() / questions.size.toFloat() },
                modifier = Modifier.fillMaxWidth().height(10.dp),
                color = Color.White,
                trackColor = Color.White.copy(alpha = 0.35f)
            )
            Spacer(modifier = Modifier.height(12.dp))
            // Vùng cuộn ở giữa: sơ đồ (chỉ khi mở) + khung câu hỏi chỉ có nội dung.
            Column(
                modifier = Modifier.weight(1f).verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (showMap) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        FlowRow(
                            modifier = Modifier.padding(12.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            questions.indices.forEach { i ->
                                val bg = when {
                                    i == index -> FixedAccent
                                    answers.getOrNull(i) != null -> FixedGreen
                                    else -> Color.LightGray
                                }
                                Box(
                                    modifier = Modifier.size(40.dp).padding(3.dp).background(bg, shape = androidx.compose.foundation.shape.CircleShape).clickable {
                                        onIndex(i)
                                        showMap = false
                                    },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text("${i + 1}", fontSize = 16.sp, color = Color.White, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                                }
                            }
                        }
                    }
                }
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(28.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    // Khung câu hỏi chỉ hiện mỗi nội dung, bỏ nhãn "Câu x/40" trong khung.
                    // Lời giải không hiện ở màn làm bài nữa, chỉ hiện ở màn xem lại câu sai.
                    Text(q.text, fontSize = 26.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, modifier = Modifier.padding(24.dp))
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            // Đáy màn, cố định không cuộn: khung đáp án riêng, 2 hàng x 2 nút.
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // 2 hàng đáp án, mỗi hàng 2 nút ngang nhau, dễ bấm.
                    for (row in 0..1) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            for (col in 0..1) {
                                val optionIndex = row * 2 + col
                                val option = q.options[optionIndex]
                                // Chỉ tô màu sau khi chọn, không hiện lời giải ở đây.
                                val color = when {
                                    !answered -> FixedAccent
                                    optionIndex == q.correctIndex -> FixedGreen
                                    optionIndex == chosen -> FixedRed
                                    else -> FixedMuted
                                }
                                Button(
                                    onClick = { if (!answered) onAnswer(optionIndex) },
                                    modifier = Modifier.weight(1f).height(58.dp),
                                    shape = RoundedCornerShape(16.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = color)
                                ) {
                                    Text("${'A' + optionIndex}. $option", fontSize = 21.sp, textAlign = TextAlign.Center)
                                }
                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            // Giữ nguyên 2 nút Câu trước / Câu tiếp theo bản chốt.
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlinedButton(
                    onClick = { if (index > 0) onIndex(index - 1) },
                    modifier = Modifier.weight(1f).height(56.dp),
                    shape = RoundedCornerShape(18.dp),
                    enabled = index > 0,
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = FixedAccent)
                ) { Text("Câu trước", fontSize = 19.sp, textAlign = TextAlign.Center) }
                Button(
                    onClick = { if (index < questions.lastIndex) onIndex(index + 1) else onSubmit() },
                    modifier = Modifier.weight(1f).height(56.dp),
                    shape = RoundedCornerShape(18.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = FixedAccent)
                ) { Text(if (index == questions.lastIndex) "Nộp bài" else "Câu tiếp", fontSize = 19.sp, textAlign = TextAlign.Center) }
            }
            Spacer(modifier = Modifier.height(10.dp))
            // Dưới cùng, luôn hiện, cố định không cuộn: Nộp bài ngay + Về trang chủ.
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Button(
                    onClick = onSubmit,
                    modifier = Modifier.weight(1f).height(56.dp),
                    shape = RoundedCornerShape(18.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = FixedAccent)
                ) { Text("Nộp bài ngay", fontSize = 19.sp, textAlign = TextAlign.Center) }
                Button(
                    onClick = onHome,
                    modifier = Modifier.weight(1f).height(56.dp),
                    shape = RoundedCornerShape(18.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                ) { Text("Về trang chủ", fontSize = 19.sp, color = FixedAccent, textAlign = TextAlign.Center) }
            }
        }
    }
}

@Composable
fun ResultScreen(
    result: QuizResult,
    questions: List<Question>,
    onRetry: () -> Unit,
    onHome: () -> Unit
) {
    BgBox {
        Column(
            modifier = Modifier.fillMaxSize().padding(20.dp).verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Kết quả", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = Color.White, textAlign = TextAlign.Center)
            Text(result.title, fontSize = 20.sp, color = Color.White, textAlign = TextAlign.Center)
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("${result.correct}/${result.total} câu đúng • Điểm ${"%.1f".format(result.score10)}/10", fontSize = 26.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                    Text("⭐".repeat(result.stars) + if (result.stars == 0) " Cố gắng thêm nhé!" else "", fontSize = 28.sp, textAlign = TextAlign.Center)
                }
            }
            if (result.wrongQuestions.isNotEmpty()) {
                Text("Xem lại câu sai:", fontSize = 22.sp, fontWeight = FontWeight.SemiBold, color = Color.White, textAlign = TextAlign.Center)
                questions.forEachIndexed { i, q ->
                    val chosen = result.chosenAnswers.getOrNull(i)
                    if (chosen != null && chosen != q.correctIndex) {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(20.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(6.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text("Câu ${i + 1}: ${q.text}", fontSize = 19.sp, fontWeight = FontWeight.SemiBold, textAlign = TextAlign.Center)
                                // Con chọn và đáp án đúng gộp thành 1 hàng duy nhất, 1 Text duy nhất.
                                Text("Con chọn: ${q.options.getOrNull(chosen) ?: "?"} • Đáp án đúng: ${q.correctAnswer}", fontSize = 18.sp, textAlign = TextAlign.Center)
                                Text(q.explanation, fontSize = 17.sp, textAlign = TextAlign.Center)
                            }
                        }
                    }
                }
            } else {
                Text("Xuất sắc! Con làm đúng hết rồi!", fontSize = 22.sp, color = Color.White, textAlign = TextAlign.Center)
            }
            Button(
                onClick = onRetry,
                modifier = Modifier.fillMaxWidth().height(60.dp),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(containerColor = FixedAccent)
            ) {
                Text("Làm đề mới (đề khác)", fontSize = 22.sp, textAlign = TextAlign.Center)
            }
            OutlinedButton(
                onClick = onHome,
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = FixedAccent)
            ) {
                Text("Về trang chính", fontSize = 20.sp, textAlign = TextAlign.Center)
            }
        }
    }
}

@Composable
fun HistoryScreen(
    entries: List<HistoryEntry>,
    onRedo: (HistoryEntry) -> Unit,
    onReview: (HistoryEntry) -> Unit,
    onBack: () -> Unit
) {
    BgBox {
        Column(
            modifier = Modifier.fillMaxSize().padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Lịch sử làm bài", fontSize = 30.sp, fontWeight = FontWeight.Bold, color = Color.White, textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(12.dp))
            // Danh sách cuộn được, chiếm phần còn lại; nút Về trang chủ ở dưới
            // nằm ngoài vùng cuộn nên luôn hiện, không bị trôi khi lịch sử dài.
            Column(
                modifier = Modifier.weight(1f).verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (entries.isEmpty()) {
                    Text("Chưa có lượt làm bài nào. Con làm thử 1 đề nhé!", fontSize = 20.sp, color = Color.White, textAlign = TextAlign.Center)
                } else {
                    // entries lưu mới nhất lên đầu, nhưng số Lần tăng theo thời gian:
                    // lần đầu tiên là Lần 1, lần mới nhất có số lớn nhất.
                    entries.forEachIndexed { displayIndex, entry ->
                        val attemptNumber = entries.size - displayIndex
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(20.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(10.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                // Mỗi entry chỉ 1 dòng ngắn gọn duy nhất.
                                Text(entry.shortLine(attemptNumber), fontSize = 18.sp, fontWeight = FontWeight.SemiBold, textAlign = TextAlign.Center)
                                // Ngay trong thẻ mỗi dòng lịch sử: 1 hàng 2 nút ngang nhau.
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                    Button(
                                        onClick = { onRedo(entry) },
                                        modifier = Modifier.weight(1f).height(52.dp),
                                        shape = RoundedCornerShape(16.dp),
                                        colors = ButtonDefaults.buttonColors(containerColor = FixedAccent)
                                    ) { Text("Làm lại", fontSize = 19.sp, textAlign = TextAlign.Center) }
                                    Button(
                                        onClick = { onReview(entry) },
                                        modifier = Modifier.weight(1f).height(52.dp),
                                        shape = RoundedCornerShape(16.dp),
                                        colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                                    ) { Text("Xem câu sai", fontSize = 19.sp, color = FixedAccent, textAlign = TextAlign.Center) }
                                }
                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            // Nút đặc, không dùng OutlinedButton mờ nữa, luôn hiện ở đáy.
            Button(
                onClick = onBack,
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(containerColor = FixedAccent)
            ) {
                Text("Về trang chủ", fontSize = 20.sp, textAlign = TextAlign.Center)
            }
        }
    }
}

@Composable
fun WrongReviewScreen(
    entry: HistoryEntry,
    onBack: () -> Unit
) {
    BgBox {
        Column(
            modifier = Modifier.fillMaxSize().padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Xem lại câu sai", fontSize = 30.sp, fontWeight = FontWeight.Bold, color = Color.White, textAlign = TextAlign.Center)
            Text("Lớp ${entry.grade} - ${entry.scopeLabel} - ${entry.correct}/${entry.total} câu đúng", fontSize = 18.sp, color = Color.White, textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(12.dp))
            Column(
                modifier = Modifier.weight(1f).verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (entry.wrong.isEmpty()) {
                    Text("Không còn câu sai.", fontSize = 20.sp, color = Color.White, textAlign = TextAlign.Center)
                } else {
                    entry.wrong.forEach { w ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(20.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(6.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(w.text, fontSize = 19.sp, fontWeight = FontWeight.SemiBold, textAlign = TextAlign.Center)
                                // Con chọn và đáp án đúng gộp thành 1 hàng duy nhất, 1 Text duy nhất.
                                Text("Con chọn: ${w.chosenAnswer} • Đáp án đúng: ${w.correctAnswer}", fontSize = 18.sp, textAlign = TextAlign.Center)
                                // Lời giải chỉ hiện ở màn xem lại câu sai, không hiện ở màn làm bài.
                                Text(w.explanation, fontSize = 17.sp, textAlign = TextAlign.Center)
                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Button(
                onClick = onBack,
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(containerColor = FixedAccent)
            ) {
                Text("Quay lại lịch sử", fontSize = 20.sp, textAlign = TextAlign.Center)
            }
        }
    }
}
