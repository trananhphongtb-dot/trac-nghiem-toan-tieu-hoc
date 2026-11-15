package vn.phong.baitaptuan.ui

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import vn.phong.baitaptuan.data.weekDefFor
import vn.phong.baitaptuan.model.ExamKind
import vn.phong.baitaptuan.model.HistoryEntry
import vn.phong.baitaptuan.model.Question
import vn.phong.baitaptuan.model.QuizResult
import vn.phong.baitaptuan.model.Subject
import vn.phong.baitaptuan.model.WeekDef

private val Good = Color(0xFF2E7D32)
private val Bad = Color(0xFFC62828)

// ---------- Khung chung ----------

/** Nền phẳng theo theme (sáng/tối), nội dung nằm dưới thanh trạng thái và trên thanh điều hướng. */
@Composable
fun AppBox(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .systemBarsPadding()
    ) {
        content()
    }
}

@Composable
private fun TopBar(title: String, subtitle: String? = null, onBack: (() -> Unit)? = null) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (onBack != null) {
            TextButton(onClick = onBack) { Text("←", fontSize = 26.sp, fontWeight = FontWeight.Bold) }
        } else {
            Spacer(modifier = Modifier.width(12.dp))
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(title, fontSize = 22.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground, maxLines = 2, overflow = TextOverflow.Ellipsis)
            if (subtitle != null) {
                Text(subtitle, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}

@Composable
private fun Badge(text: String, color: Color, size: Int = 52, textSize: Int = 26) {
    Box(
        modifier = Modifier.size(size.dp).clip(CircleShape).background(color.copy(alpha = 0.16f)),
        contentAlignment = Alignment.Center
    ) {
        Text(text, fontSize = textSize.sp, fontWeight = FontWeight.Bold, color = color)
    }
}

@Composable
private fun SectionTitle(text: String) {
    Text(
        text,
        fontSize = 16.sp,
        fontWeight = FontWeight.SemiBold,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(top = 8.dp, bottom = 2.dp)
    )
}

@Composable
private fun AppCard(modifier: Modifier = Modifier, onClick: (() -> Unit)? = null, content: @Composable () -> Unit) {
    val m = if (onClick != null) modifier.clip(RoundedCornerShape(20.dp)).clickable { onClick() } else modifier
    Card(
        modifier = m,
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) { content() }
}

@Composable
private fun Pill(text: String, color: Color = MaterialTheme.colorScheme.onSurface) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(horizontal = 14.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text, fontSize = 15.sp, fontWeight = FontWeight.SemiBold, color = color)
    }
}

// ---------- Màn chính ----------

@Composable
fun HomeScreen(
    onSubjectSelected: (Subject) -> Unit,
    onHistory: () -> Unit
) {
    AppBox {
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(bottom = 8.dp)) {
                Box(
                    modifier = Modifier.size(60.dp).clip(RoundedCornerShape(18.dp)).background(MaterialTheme.colorScheme.primary),
                    contentAlignment = Alignment.Center
                ) { Text("📝", fontSize = 30.sp) }
                Spacer(modifier = Modifier.width(14.dp))
                Column {
                    Text("Bài Tập Tuần", fontSize = 30.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
                    Text("Tiểu học • Lớp 1–5 • 35 tuần", fontSize = 15.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
            SectionTitle("Chọn môn học")
            Subject.values().forEach { subject ->
                AppCard(modifier = Modifier.fillMaxWidth(), onClick = { onSubjectSelected(subject) }) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Badge(subject.emoji(), subject.accent())
                        Spacer(modifier = Modifier.width(14.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(subject.title, fontSize = 18.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onSurface)
                            Text(subject.tagline(), fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        Text("›", fontSize = 28.sp, color = subject.accent())
                    }
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedButton(
                onClick = onHistory,
                modifier = Modifier.fillMaxWidth().height(54.dp),
                shape = RoundedCornerShape(16.dp)
            ) { Text("🕘  Lịch sử làm bài", fontSize = 17.sp) }
        }
    }
}

@Composable
fun GradeScreen(
    subject: Subject,
    onGradeSelected: (Int) -> Unit,
    onBack: () -> Unit
) {
    AppBox {
        Column(modifier = Modifier.fillMaxSize()) {
            TopBar(subject.title, "Chọn lớp", onBack)
            Column(
                modifier = Modifier.weight(1f).verticalScroll(rememberScrollState()).padding(horizontal = 20.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                subject.grades.forEach { grade ->
                    AppCard(modifier = Modifier.fillMaxWidth(), onClick = { onGradeSelected(grade) }) {
                        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                            Badge("$grade", subject.accent(), 52, 24)
                            Spacer(modifier = Modifier.width(14.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text("Lớp $grade", fontSize = 19.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onSurface)
                                if (subject == Subject.TU_NHIEN) {
                                    Text(subject.titleFor(grade), fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                }
                            }
                            Text("›", fontSize = 28.sp, color = subject.accent())
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun WeekScreen(
    subject: Subject,
    grade: Int,
    entries: List<HistoryEntry>,
    onWeek: (WeekDef) -> Unit,
    onExam: (ExamKind) -> Unit,
    onBack: () -> Unit
) {
    // Điểm tốt nhất đã làm của từng tuần, để đánh dấu tuần đã làm.
    val best: Map<Int, HistoryEntry> = entries
        .filter { it.subject == subject && it.grade == grade && it.week != null && it.total > 0 }
        .groupBy { it.week!! }
        .mapValues { (_, list) -> list.maxByOrNull { it.correct.toFloat() / it.total }!! }
    val accent = subject.accent()
    AppBox {
        Column(modifier = Modifier.fillMaxSize()) {
            TopBar("${subject.titleFor(grade)} • Lớp $grade", "Đã làm ${best.size}/35 tuần", onBack)
            Column(
                modifier = Modifier.weight(1f).verticalScroll(rememberScrollState()).padding(horizontal = 16.dp, vertical = 4.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    if (subject == Subject.TOAN) "Khung tuần theo bộ Kết nối tri thức. Tuần ghi “ước tính” có thể lệch 1 tuần theo trường."
                    else "Chủ đề theo mạch kiến thức của chương trình. Mỗi lần làm ra bộ câu hỏi khác nhau.",
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                SectionTitle("Học kỳ 1 • Tuần 1–18")
                WeekGrid(subject, grade, 1..18, best, accent, onWeek)
                SectionTitle("Học kỳ 2 • Tuần 19–35")
                WeekGrid(subject, grade, 19..35, best, accent, onWeek)
                SectionTitle("Đề kiểm tra")
                ExamKind.values().forEach { exam ->
                    AppCard(modifier = Modifier.fillMaxWidth(), onClick = { onExam(exam) }) {
                        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                            Badge("📋", accent, 44, 22)
                            Spacer(modifier = Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(exam.title, fontSize = 17.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onSurface)
                                Text(
                                    if (exam.isPracticeFor(grade)) "Đề luyện • tuần ${exam.weeks.first}–${exam.weeks.last} • lớp $grade không có kiểm tra viết bắt buộc"
                                    else "Tuần ${exam.weeks.first}–${exam.weeks.last} • tối đa 40 câu",
                                    fontSize = 13.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun WeekGrid(
    subject: Subject,
    grade: Int,
    range: IntRange,
    best: Map<Int, HistoryEntry>,
    accent: Color,
    onWeek: (WeekDef) -> Unit
) {
    val weeks = range.map { weekDefFor(subject, grade, it) }
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        weeks.chunked(2).forEach { pair ->
            Row(
                modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                pair.forEach { w ->
                    val done = best[w.week]
                    AppCard(modifier = Modifier.weight(1f).fillMaxHeight(), onClick = { onWeek(w) }) {
                        Column(modifier = Modifier.padding(12.dp).heightIn(min = 84.dp), verticalArrangement = Arrangement.SpaceBetween) {
                            Column {
                                Text("Tuần ${w.week}", fontSize = 17.sp, fontWeight = FontWeight.Bold, color = accent)
                                Text(
                                    w.title + if (w.estimated) " (ước tính)" else "",
                                    fontSize = 13.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    maxLines = 3,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            if (done != null) {
                                Text("✓ Tốt nhất ${done.correct}/${done.total}", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = Good)
                            } else {
                                Text("Chưa làm", fontSize = 13.sp, color = MaterialTheme.colorScheme.outline)
                            }
                        }
                    }
                }
                if (pair.size == 1) Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}

// ---------- Màn làm bài ----------

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun QuizScreen(
    subject: Subject,
    questions: List<Question>,
    index: Int,
    answers: List<Int?>,
    onAnswer: (Int) -> Unit,
    onIndex: (Int) -> Unit,
    onSubmit: () -> Unit,
    onHome: () -> Unit,
    onTimeUp: () -> Unit
) {
    val context = LocalContext.current
    val prefs = remember { context.getSharedPreferences("settings", Context.MODE_PRIVATE) }
    val speaker = remember { Speaker(context) }
    DisposableEffect(Unit) { onDispose { speaker.shutdown() } }
    var autoRead by remember { mutableStateOf(prefs.getBoolean("auto_read", false)) }

    var remaining by remember(questions) { mutableStateOf(60 * 60) }
    LaunchedEffect(questions) {
        while (remaining > 0) {
            delay(1000)
            remaining -= 1
        }
        onTimeUp()
    }
    LaunchedEffect(index, autoRead, questions) {
        speaker.stop()
        if (autoRead && questions.isNotEmpty() && index in questions.indices) {
            delay(700)
            speaker.speakQuestion(subject, index + 1, questions[index])
        }
    }
    if (questions.isEmpty()) {
        AppBox {
            Text(
                "Chưa sinh được đề. Con bấm Làm đề mới nhé.",
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxSize().padding(24.dp)
            )
        }
        return
    }
    var showMap by remember(questions) { mutableStateOf(false) }
    val q = questions[index]
    val chosen = answers.getOrNull(index)
    val answered = chosen != null
    val answeredCount = answers.count { it != null }
    val timeText = "%02d:%02d".format(remaining / 60, remaining % 60)
    val accent = subject.accent()

    AppBox {
        Column(modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp, vertical = 8.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(onClick = onHome) { Text("✕ Thoát", fontSize = 16.sp, color = MaterialTheme.colorScheme.onSurfaceVariant) }
                Box(modifier = Modifier.clip(RoundedCornerShape(50)).clickable { showMap = !showMap }) {
                    Pill("Đã làm $answeredCount/${questions.size} ▾")
                }
                Pill("⏰ $timeText", color = if (remaining < 300) Bad else MaterialTheme.colorScheme.onSurface)
            }
            Spacer(modifier = Modifier.height(6.dp))
            LinearProgressIndicator(
                progress = { (index + 1).toFloat() / questions.size.toFloat() },
                modifier = Modifier.fillMaxWidth().height(8.dp).clip(RoundedCornerShape(50)),
                color = accent,
                trackColor = MaterialTheme.colorScheme.surfaceVariant
            )
            Spacer(modifier = Modifier.height(10.dp))
            Column(
                modifier = Modifier.weight(1f).verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                if (showMap) {
                    AppCard(modifier = Modifier.fillMaxWidth()) {
                        FlowRow(
                            modifier = Modifier.padding(12.dp),
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            questions.indices.forEach { i ->
                                val bg = when {
                                    i == index -> accent
                                    answers.getOrNull(i) != null -> Good
                                    else -> MaterialTheme.colorScheme.outline
                                }
                                Box(
                                    modifier = Modifier.size(38.dp).clip(CircleShape).background(bg).clickable {
                                        onIndex(i)
                                        showMap = false
                                    },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text("${i + 1}", fontSize = 15.sp, color = Color.White, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }
                AppCard(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                "Câu ${index + 1}/${questions.size}",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = accent,
                                modifier = Modifier.weight(1f)
                            )
                            FilledTonalButton(
                                onClick = {
                                    if (speaker.speaking) speaker.stop()
                                    else speaker.speakQuestion(subject, index + 1, q)
                                },
                                shape = RoundedCornerShape(50)
                            ) { Text(if (speaker.speaking) "⏹ Dừng" else "🔊 Đọc", fontSize = 15.sp) }
                        }
                        Text(
                            q.text,
                            fontSize = 23.sp,
                            lineHeight = 31.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("Tự đọc khi sang câu mới", fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.weight(1f))
                            Switch(
                                checked = autoRead,
                                onCheckedChange = {
                                    autoRead = it
                                    prefs.edit().putBoolean("auto_read", it).apply()
                                }
                            )
                        }
                        if (speaker.viMissing && subject != Subject.TIENG_ANH) {
                            Text(
                                "Máy chưa có giọng tiếng Việt. Vào Cài đặt › Chuyển văn bản thành giọng nói để cài giọng Việt của Google.",
                                fontSize = 12.sp,
                                color = Bad
                            )
                        }
                    }
                }
                q.options.forEachIndexed { optionIndex, option ->
                    val color = when {
                        !answered -> MaterialTheme.colorScheme.outline
                        optionIndex == q.correctIndex -> Good
                        optionIndex == chosen -> Bad
                        else -> MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
                    }
                    val tint = when {
                        !answered -> MaterialTheme.colorScheme.surface
                        optionIndex == q.correctIndex -> Good.copy(alpha = 0.14f)
                        optionIndex == chosen -> Bad.copy(alpha = 0.14f)
                        else -> MaterialTheme.colorScheme.surface
                    }
                    val badgeColor = when {
                        !answered -> accent
                        optionIndex == q.correctIndex -> Good
                        optionIndex == chosen -> Bad
                        else -> MaterialTheme.colorScheme.outline
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(tint)
                            .border(2.dp, color, RoundedCornerShape(16.dp))
                            .clickable { if (!answered) onAnswer(optionIndex) }
                            .padding(horizontal = 14.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier.size(36.dp).clip(CircleShape).background(badgeColor),
                            contentAlignment = Alignment.Center
                        ) { Text("${'A' + optionIndex}", fontSize = 17.sp, fontWeight = FontWeight.Bold, color = Color.White) }
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(option, fontSize = 20.sp, color = MaterialTheme.colorScheme.onSurface, modifier = Modifier.weight(1f))
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedButton(
                    onClick = { if (index > 0) onIndex(index - 1) },
                    modifier = Modifier.weight(1f).height(52.dp),
                    shape = RoundedCornerShape(16.dp),
                    enabled = index > 0
                ) { Text("‹ Trước", fontSize = 16.sp) }
                FilledTonalButton(
                    onClick = onSubmit,
                    modifier = Modifier.weight(1f).height(52.dp),
                    shape = RoundedCornerShape(16.dp)
                ) { Text("Nộp bài", fontSize = 16.sp) }
                Button(
                    onClick = { if (index < questions.lastIndex) onIndex(index + 1) else onSubmit() },
                    modifier = Modifier.weight(1f).height(52.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = accent, contentColor = Color.White)
                ) { Text(if (index == questions.lastIndex) "Nộp bài" else "Tiếp ›", fontSize = 16.sp) }
            }
        }
    }
}

// ---------- Kết quả ----------

@Composable
private fun WrongCard(number: String?, text: String, chosen: String, correct: String, explanation: String) {
    AppCard(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                if (number != null) "Câu $number: $text" else text,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text("✗ Con chọn: $chosen", fontSize = 16.sp, color = Bad)
            Text("✓ Đáp án đúng: $correct", fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = Good)
            if (explanation.isNotBlank()) {
                Box(
                    modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(12.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant).padding(12.dp)
                ) {
                    Text(explanation, fontSize = 15.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
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
    val score = result.score10
    val message = when {
        score >= 9.0 -> "Xuất sắc!"
        score >= 7.0 -> "Tốt lắm!"
        score >= 5.0 -> "Khá, cố gắng thêm nhé!"
        else -> "Cần luyện thêm, con làm lại nhé!"
    }
    val scoreColor = if (score >= 7.0) Good else if (score >= 5.0) Color(0xFFEF6C00) else Bad
    AppBox {
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Kết quả", fontSize = 26.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
            Text(result.title, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, textAlign = TextAlign.Center)
            AppCard(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("%.1f".format(score), fontSize = 64.sp, fontWeight = FontWeight.Bold, color = scoreColor)
                    Text("điểm / 10", fontSize = 15.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text("${result.correct}/${result.total} câu đúng", fontSize = 20.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onSurface)
                    if (result.stars > 0) Text("⭐".repeat(result.stars), fontSize = 28.sp)
                    Text(message, fontSize = 18.sp, fontWeight = FontWeight.SemiBold, color = scoreColor)
                }
            }
            if (result.wrongQuestions.isNotEmpty()) {
                Text(
                    "Xem lại câu sai",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.fillMaxWidth()
                )
                questions.forEachIndexed { i, q ->
                    val chosen = result.chosenAnswers.getOrNull(i)
                    if (chosen != null && chosen != q.correctIndex) {
                        WrongCard("${i + 1}", q.text, q.options.getOrNull(chosen) ?: "?", q.correctAnswer, q.explanation)
                    }
                }
            }
            Button(
                onClick = onRetry,
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(16.dp)
            ) { Text("Làm đề mới (đề khác)", fontSize = 18.sp) }
            OutlinedButton(
                onClick = onHome,
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape = RoundedCornerShape(16.dp)
            ) { Text("Về trang chính", fontSize = 17.sp) }
        }
    }
}

// ---------- Lịch sử ----------

@Composable
fun HistoryScreen(
    entries: List<HistoryEntry>,
    onRedo: (HistoryEntry) -> Unit,
    onReview: (HistoryEntry) -> Unit,
    onBack: () -> Unit
) {
    AppBox {
        Column(modifier = Modifier.fillMaxSize()) {
            TopBar("Lịch sử làm bài", "${entries.size} lượt", onBack)
            Column(
                modifier = Modifier.weight(1f).verticalScroll(rememberScrollState()).padding(horizontal = 16.dp, vertical = 4.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                if (entries.isEmpty()) {
                    Text(
                        "Chưa có lượt làm bài nào. Con làm thử 1 đề nhé!",
                        fontSize = 17.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(20.dp)
                    )
                } else {
                    // entries lưu mới nhất lên đầu; số Lần tăng theo thời gian.
                    entries.forEachIndexed { displayIndex, entry ->
                        val attemptNumber = entries.size - displayIndex
                        AppCard(modifier = Modifier.fillMaxWidth()) {
                            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Badge(entry.subject.emoji(), entry.subject.accent(), 44, 22)
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            "${entry.subject.titleFor(entry.grade)} • Lớp ${entry.grade}",
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                        Text(
                                            "Lần $attemptNumber • ${entry.scopeLabel}",
                                            fontSize = 13.sp,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                    Text(
                                        "${entry.correct}/${entry.total}",
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (entry.total > 0 && entry.correct * 10 >= entry.total * 7) Good else MaterialTheme.colorScheme.onSurface
                                    )
                                }
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                    Button(
                                        onClick = { onRedo(entry) },
                                        modifier = Modifier.weight(1f).height(46.dp),
                                        shape = RoundedCornerShape(14.dp)
                                    ) { Text("Làm lại", fontSize = 16.sp) }
                                    FilledTonalButton(
                                        onClick = { onReview(entry) },
                                        modifier = Modifier.weight(1f).height(46.dp),
                                        shape = RoundedCornerShape(14.dp)
                                    ) { Text("Xem câu sai", fontSize = 16.sp) }
                                }
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Composable
fun WrongReviewScreen(
    entry: HistoryEntry,
    onBack: () -> Unit
) {
    AppBox {
        Column(modifier = Modifier.fillMaxSize()) {
            TopBar(
                "Xem lại câu sai",
                "${entry.subject.titleFor(entry.grade)} • Lớp ${entry.grade} • ${entry.scopeLabel} • ${entry.correct}/${entry.total} câu đúng",
                onBack
            )
            Column(
                modifier = Modifier.weight(1f).verticalScroll(rememberScrollState()).padding(horizontal = 16.dp, vertical = 4.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                if (entry.wrong.isEmpty()) {
                    Text(
                        "Không còn câu sai. 🎉",
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(20.dp)
                    )
                } else {
                    entry.wrong.forEach { w ->
                        WrongCard(null, w.text, w.chosenAnswer, w.correctAnswer, w.explanation)
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}
