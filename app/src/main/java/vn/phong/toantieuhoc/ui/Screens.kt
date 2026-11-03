package vn.phong.toantieuhoc.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import vn.phong.toantieuhoc.data.minBankSize
import vn.phong.toantieuhoc.model.Difficulty
import vn.phong.toantieuhoc.model.Question
import vn.phong.toantieuhoc.model.QuizResult
import vn.phong.toantieuhoc.model.Topic

@Composable
fun HomeScreen(
    onGradeSelected: (Int) -> Unit,
    onHistory: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(20.dp).verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Text("Trắc Nghiệm Toán", fontSize = 32.sp, fontWeight = FontWeight.Bold)
        Text("Tiểu học • Lớp 1 đến lớp 5", fontSize = 20.sp, color = Color.Gray)
        Text("Con chọn lớp của mình nhé:", fontSize = 22.sp, fontWeight = FontWeight.SemiBold)
        (1..5).forEach { grade ->
            Button(
                onClick = { onGradeSelected(grade) },
                modifier = Modifier.fillMaxWidth().height(64.dp)
            ) {
                Text("Lớp $grade", fontSize = 24.sp)
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedButton(onClick = onHistory, modifier = Modifier.fillMaxWidth().height(56.dp)) {
            Text("Xem lịch sử làm bài", fontSize = 20.sp)
        }
        Text(
            "Không quảng cáo, không đăng nhập, làm bài ngay trên máy.",
            fontSize = 16.sp, color = Color.Gray
        )
    }
}

@Composable
fun SetupScreen(
    grade: Int,
    topic: Topic,
    difficulty: Difficulty,
    modeCount: Int,
    onTopic: (Topic) -> Unit,
    onDifficulty: (Difficulty) -> Unit,
    onMode: (Int) -> Unit,
    onStart: () -> Unit,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(20.dp).verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Lớp $grade", fontSize = 30.sp, fontWeight = FontWeight.Bold)
        Text("Ngân hàng lớp $grade: hơn ${minBankSize(grade)} câu khác nhau, không lặp trong 1 lượt.", fontSize = 18.sp, color = Color.Gray)
        Text("1. Chọn chủ đề", fontSize = 22.sp, fontWeight = FontWeight.SemiBold)
        listOf(Topic.ALL, Topic.SO_PHEP_TINH, Topic.HINH_HOC, Topic.DO_LUONG, Topic.LOI_VAN, Topic.PHAN_SO_THAP_PHAN, Topic.NANG_CAO)
            .forEach { t ->
                ChoiceButton(t.title, t == topic) { onTopic(t) }
            }
        Text("2. Chọn mức độ", fontSize = 22.sp, fontWeight = FontWeight.SemiBold)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Difficulty.values().forEach { d ->
                Button(
                    onClick = { onDifficulty(d) },
                    colors = if (d == difficulty) ButtonDefaults.buttonColors() else ButtonDefaults.outlinedButtonColors()
                ) { Text(d.title, fontSize = 18.sp) }
            }
        }
        Text("3. Chọn chế độ", fontSize = 22.sp, fontWeight = FontWeight.SemiBold)
        ChoiceButton("Luyện tập • 10 câu, không tính giờ", modeCount == 10) { onMode(10) }
        ChoiceButton("Kiểm tra • 20 câu", modeCount == 20) { onMode(20) }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onStart, modifier = Modifier.fillMaxWidth().height(64.dp)) {
            Text("Bắt đầu làm bài", fontSize = 24.sp)
        }
        OutlinedButton(onClick = onBack, modifier = Modifier.fillMaxWidth().height(56.dp)) {
            Text("Quay lại", fontSize = 20.sp)
        }
    }
}

@Composable
private fun ChoiceButton(text: String, selected: Boolean, onClick: () -> Unit) {
    if (selected) {
        Button(onClick = onClick, modifier = Modifier.fillMaxWidth().height(56.dp)) {
            Text(text, fontSize = 19.sp)
        }
    } else {
        OutlinedButton(onClick = onClick, modifier = Modifier.fillMaxWidth().height(56.dp)) {
            Text(text, fontSize = 19.sp)
        }
    }
}

@Composable
fun QuizScreen(
    questions: List<Question>,
    index: Int,
    selected: Int?,
    score: Int,
    onSelect: (Int) -> Unit,
    onNext: () -> Unit
) {
    if (questions.isEmpty()) {
        Column(modifier = Modifier.fillMaxSize().padding(20.dp)) {
            Text("Chưa có câu hỏi cho lựa chọn này.", fontSize = 22.sp)
        }
        return
    }
    val q = questions[index]
    val answered = selected != null
    Column(
        modifier = Modifier.fillMaxSize().padding(20.dp).verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Text("Câu ${index + 1}/${questions.size} • Đúng: $score", fontSize = 18.sp, color = Color.Gray)
        LinearProgressIndicator(
            progress = { (index + 1).toFloat() / questions.size.toFloat() },
            modifier = Modifier.fillMaxWidth()
        )
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFEAF2FF))
        ) {
            Text(q.text, fontSize = 26.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(20.dp))
        }
        q.options.forEachIndexed { optionIndex, option ->
            val color = when {
                !answered -> null
                optionIndex == q.correctIndex -> Color(0xFF2E7D32)
                optionIndex == selected -> Color(0xFFC62828)
                else -> null
            }
            Button(
                onClick = { if (!answered) onSelect(optionIndex) },
                modifier = Modifier.fillMaxWidth().height(64.dp),
                colors = if (color != null) ButtonDefaults.buttonColors(containerColor = color) else ButtonDefaults.buttonColors()
            ) {
                Text("${'A' + optionIndex}. $option", fontSize = 22.sp)
            }
        }
        if (answered) {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(
                        if (selected == q.correctIndex) "Đúng rồi! Tuyệt vời!" else "Chưa đúng, đáp án là ${q.correctAnswer}.",
                        fontSize = 20.sp, fontWeight = FontWeight.Bold
                    )
                    Text(q.explanation, fontSize = 18.sp)
                }
            }
            Button(onClick = onNext, modifier = Modifier.fillMaxWidth().height(60.dp)) {
                Text(if (index == questions.lastIndex) "Xem kết quả" else "Câu tiếp theo", fontSize = 22.sp)
            }
        }
    }
}

@Composable
fun ResultScreen(
    result: QuizResult,
    onRetry: () -> Unit,
    onHome: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(20.dp).verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Text("Kết quả", fontSize = 32.sp, fontWeight = FontWeight.Bold)
        Text("Lớp ${result.grade} • ${result.topic.title}", fontSize = 20.sp, color = Color.Gray)
        Text("${result.correct}/${result.total} câu đúng", fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Text("⭐".repeat(result.stars) + if (result.stars == 0) "Cố gắng thêm nhé!" else "", fontSize = 28.sp)
        if (result.wrongQuestions.isNotEmpty()) {
            Text("Xem lại câu sai:", fontSize = 22.sp, fontWeight = FontWeight.SemiBold)
            result.wrongQuestions.forEach { q ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text(q.text, fontSize = 19.sp, fontWeight = FontWeight.SemiBold)
                        Text("Đáp án đúng: ${q.correctAnswer}", fontSize = 18.sp, color = Color(0xFF2E7D32))
                        Text(q.explanation, fontSize = 17.sp)
                    }
                }
            }
        } else {
            Text("Xuất sắc! Con làm đúng hết rồi!", fontSize = 22.sp)
        }
        Button(onClick = onRetry, modifier = Modifier.fillMaxWidth().height(60.dp)) {
            Text("Làm đề mới", fontSize = 22.sp)
        }
        OutlinedButton(onClick = onHome, modifier = Modifier.fillMaxWidth().height(56.dp)) {
            Text("Về trang chính", fontSize = 20.sp)
        }
    }
}

@Composable
fun HistoryScreen(lines: List<String>, onBack: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(20.dp).verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Lịch sử làm bài", fontSize = 30.sp, fontWeight = FontWeight.Bold)
        if (lines.isEmpty()) {
            Text("Chưa có lượt làm bài nào. Con làm thử 1 đề nhé!", fontSize = 20.sp)
        } else {
            lines.forEach { line ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Text(line, fontSize = 18.sp, modifier = Modifier.padding(16.dp))
                }
            }
        }
        OutlinedButton(onClick = onBack, modifier = Modifier.fillMaxWidth().height(56.dp)) {
            Text("Quay lại", fontSize = 20.sp)
        }
    }
}
