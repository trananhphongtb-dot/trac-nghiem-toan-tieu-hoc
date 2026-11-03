package vn.phong.toantieuhoc.data

import vn.phong.toantieuhoc.model.Question
import kotlin.math.abs
import kotlin.random.Random

/**
 * Bộ sinh đề tuần KHÔNG CỐ ĐỊNH.
 * - Mỗi lần gọi generateWeeklySet / generateExamSet đều dùng một Random mới,
 *   gieo bằng nanoTime (không seed cố định), nên số, thứ tự câu, thứ tự đáp án
 *   và đáp án nhiễu đều khác nhau giữa các lượt.
 * - Không có ngân hàng câu cố định: câu được tạo từ dạng (form) + tham số ngẫu nhiên.
 * - Trong một lượt: không lặp câu (so sánh theo nội dung câu hỏi), 4 đáp án
 *   không trùng nhau, đáp án đúng luôn hợp lệ (kiểm tra ở mk()).
 * - LƯU Ý BIÊN DỊCH: (a..b step n).random(random) là LỖI trong Kotlin, nên mọi
 *   chỗ cần dãy có bước nhảy đều dùng (a..b step n).toList().random(random).
 */

internal class Ctx(val grade: Int, val week: Int, val random: Random)

internal fun Ctx.ri(from: Int, to: Int): Int {
    if (to <= from) return from
    return random.nextInt(from, to + 1)
}

internal fun fmt(d: Double): String {
    if (d == kotlin.math.floor(d) && abs(d) < 1_000_000_000.0) return d.toLong().toString()
    val s = "%.2f".format(java.util.Locale.US, d).trimEnd('0').trimEnd('.')
    return s
}

internal fun fmtInt(v: Int): String = v.toString()

internal fun gcd(a: Int, b: Int): Int {
    var x = abs(a); var y = abs(b)
    while (y != 0) { val t = x % y; x = y; y = t }
    return if (x == 0) 1 else x
}

internal fun simpFrac(num: Int, den: Int): String {
    val g = gcd(num, den)
    val n = num / g; val d = den / g
    return if (d == 1) n.toString() else "$n/$d"
}

internal val ONES = listOf("không", "một", "hai", "ba", "bốn", "năm", "sáu", "bảy", "tám", "chín")

internal fun readUnder100(n: Int): String {
    if (n < 10) return ONES[n]
    if (n < 20) {
        return when (n) {
            10 -> "mười"
            15 -> "mười lăm"
            else -> "mười " + ONES[n - 10]
        }
    }
    val tens = n / 10; val ones = n % 10
    val t = if (tens == 1) "mười" else ONES[tens] + " mươi"
    return when (ones) {
        0 -> t
        1 -> "$t mốt"
        5 -> "$t lăm"
        else -> "$t " + ONES[ones]
    }
}

internal fun readNum(n: Int): String {
    if (n < 0) return "âm " + readNum(-n)
    if (n < 100) return readUnder100(n)
    if (n < 1000) {
        val h = n / 100; val r = n % 100
        return if (r == 0) ONES[h] + " trăm"
        else if (r < 10) ONES[h] + " trăm lẻ " + ONES[r]
        else ONES[h] + " trăm " + readUnder100(r)
    }
    return n.toString()
}

internal fun mk(ctx: Ctx, form: String, text: String, correct: String, distractors: List<String>, explanation: String): Question? {
    val clean = distractors.filter { it != correct }.distinct()
    if (clean.size < 3) return null
    val opts = (listOf(correct) + clean.take(3)).shuffled(ctx.random)
    val idx = opts.indexOf(correct)
    if (opts.size != 4 || idx < 0) return null
    return Question(text, opts, idx, explanation, form)
}

internal fun numDistractors(correct: Int, ctx: Ctx): List<String> {
    val pool = mutableListOf<Int>()
    pool += correct + 1; pool += correct - 1; pool += correct + 2; pool += correct - 2
    pool += correct + 10; pool += correct - 10; pool += correct + 5; pool += correct - 5
    if (correct >= 10) {
        val s = correct.toString()
        val swapped = (s.substring(1, 2) + s.substring(0, 1) + s.substring(2)).toIntOrNull()
        if (swapped != null) pool += swapped
    }
    if (correct >= 100) { pool += correct + 100; pool += correct - 100 }
    return pool.filter { it != correct }.distinct().shuffled(ctx.random).take(3).map { it.toString() }
}

internal fun decDistractors(correct: Double, ctx: Ctx): List<String> {
    val c = fmt(correct)
    val pool = listOf(correct + 0.1, correct - 0.1, correct + 1.0, correct - 1.0, correct + 0.5, correct - 0.5, correct + 10.0, correct - 10.0)
    return pool.map { fmt(it) }.filter { it != c }.distinct().shuffled(ctx.random).take(3)
}

internal fun strDistractors(correct: String, pool: List<String>, ctx: Ctx): List<String> {
    return pool.filter { it != correct }.distinct().shuffled(ctx.random).take(3)
}

internal val NAMES = listOf("Lan", "Minh", "Hùng", "Mai", "An", "Nam", "Hoa", "Tuấn", "Linh", "Đức")
internal val OBJECTS = listOf("quyển vở", "viên bi", "quả táo", "cái bánh", "bông hoa", "chiếc bút", "quả cam", "cái kẹo")

internal fun pickName(ctx: Ctx): String = NAMES.random(ctx.random)
internal fun pickObj(ctx: Ctx): String = OBJECTS.random(ctx.random)

// ---------- Phạm vi số theo lớp / giai đoạn ----------
internal fun addPair(ctx: Ctx): Pair<Int, Int> {
    return when (ctx.grade) {
        1 -> {
            if (ctx.week <= 18) Pair(ctx.ri(2, 9), ctx.ri(1, 9))
            else {
                // Cộng không nhớ trong 100: chọn chục và đơn vị sao cho đơn vị không nhớ.
                val aT = ctx.ri(1, 8); val bT = ctx.ri(1, 8 - aT + 1)
                val aO = ctx.ri(0, 8); val bO = ctx.ri(0, 9 - aO)
                Pair(aT * 10 + aO, bT * 10 + bO)
            }
        }
        2 -> if (ctx.week >= 24) Pair(ctx.ri(100, 899), ctx.ri(100, 899)) else Pair(ctx.ri(2, 99), ctx.ri(2, 99))
        3 -> if (ctx.week >= 23) Pair(ctx.ri(1000, 8999), ctx.ri(1000, 8999)) else if (ctx.week >= 14) Pair(ctx.ri(100, 999), ctx.ri(100, 999)) else Pair(ctx.ri(2, 99), ctx.ri(2, 99))
        4 -> Pair(ctx.ri(1000, 99999), ctx.ri(1000, 99999))
        else -> Pair(ctx.ri(100, 9999), ctx.ri(100, 9999))
    }
}

internal fun mulPair(ctx: Ctx): Pair<Int, Int> {
    return when (ctx.grade) {
        1 -> Pair(ctx.ri(2, 5), ctx.ri(2, 5))
        2 -> Pair((2..5).toList().random(ctx.random), ctx.ri(2, 9))
        3 -> if (ctx.week >= 23) Pair(ctx.ri(100, 999), ctx.ri(2, 9)) else if (ctx.week >= 14) Pair(ctx.ri(100, 999), ctx.ri(2, 9)) else Pair(ctx.ri(2, 9), ctx.ri(2, 9))
        4 -> if (ctx.week >= 21) Pair(ctx.ri(11, 99), ctx.ri(11, 99)) else Pair(ctx.ri(2, 99), ctx.ri(2, 9))
        else -> Pair(ctx.ri(11, 99), ctx.ri(2, 9))
    }
}

// ---------- Các dạng câu ----------
internal fun fAdd(ctx: Ctx): Question? {
    val (a, b) = addPair(ctx)
    val c = a + b
    return mk(ctx, "ADD", "$a + $b = ?", c.toString(), numDistractors(c, ctx), "$a + $b = $c.")
}

internal fun fSub(ctx: Ctx): Question? {
    val (a, b) = addPair(ctx)
    val big = maxOf(a, b); val small = minOf(a, b)
    val c = big - small
    return mk(ctx, "SUB", "$big - $small = ?", c.toString(), numDistractors(c, ctx), "$big - $small = $c.")
}

internal fun fAdd3(ctx: Ctx): Question? {
    val a = ctx.ri(2, 30); val b = ctx.ri(2, 30); val c2 = ctx.ri(2, 30)
    val c = a + b + c2
    return mk(ctx, "ADD3", "$a + $b + $c2 = ?", c.toString(), numDistractors(c, ctx), "Cộng lần lượt: $a + $b = ${a + b}, rồi cộng thêm $c2 được $c.")
}

internal fun fMul(ctx: Ctx): Question? {
    val (a, b) = mulPair(ctx)
    val c = a * b
    return mk(ctx, "MUL", "$a × $b = ?", c.toString(), numDistractors(c, ctx), "$a × $b = $c.")
}

internal fun fDiv(ctx: Ctx): Question? {
    val b = if (ctx.grade <= 2) ctx.ri(2, 5) else ctx.ri(2, 12)
    val q = ctx.ri(2, 12)
    val a = b * q
    return mk(ctx, "DIV", "$a : $b = ?", q.toString(), numDistractors(q, ctx), "$a : $b = $q vì $q × $b = $a.")
}

internal fun fMissAdd(ctx: Ctx): Question? {
    val (a, b) = addPair(ctx)
    val c = a + b
    return if (ctx.random.nextBoolean())
        mk(ctx, "MISS_ADD", "? + $a = $c", b.toString(), numDistractors(b, ctx), "Số cần tìm là $c - $a = $b.")
    else
        mk(ctx, "MISS_ADD", "$a + ? = $c", b.toString(), numDistractors(b, ctx), "Số cần tìm là $c - $a = $b.")
}

internal fun fMissSub(ctx: Ctx): Question? {
    val (a, b) = addPair(ctx)
    val big = maxOf(a, b); val small = minOf(a, b); val c = big - small
    return if (ctx.random.nextBoolean())
        mk(ctx, "MISS_SUB", "? - $small = $c", big.toString(), numDistractors(big, ctx), "Số bị trừ là $c + $small = $big.")
    else
        mk(ctx, "MISS_SUB", "$big - ? = $c", small.toString(), numDistractors(small, ctx), "Số trừ là $big - $c = $small.")
}

internal fun fMissMul(ctx: Ctx): Question? {
    val (a, b) = mulPair(ctx)
    val c = a * b
    return mk(ctx, "MISS_MUL", "? × $a = $c", b.toString(), numDistractors(b, ctx), "Thừa số cần tìm là $c : $a = $b.")
}

internal fun fMissDiv(ctx: Ctx): Question? {
    val b = ctx.ri(2, 9); val q = ctx.ri(2, 12); val a = b * q
    return mk(ctx, "MISS_DIV", "$a : ? = $q", b.toString(), numDistractors(b, ctx), "Số chia là $a : $q = $b.")
}

internal fun fCompMax(ctx: Ctx): Question? {
    val max = when (ctx.grade) {
        1 -> ctx.ri(5, 100)
        2 -> ctx.ri(50, 1000)
        else -> ctx.ri(100, 9999)
    }
    val vals = mutableSetOf<Int>()
    vals += max
    while (vals.size < 4) vals += ctx.ri(1, max - 1)
    val list = vals.toList().shuffled(ctx.random)
    return mk(ctx, "COMP_MAX", "Số nào lớn nhất?", max.toString(), list.filter { it != max }.map { it.toString() }, "$max là số lớn nhất trong các số đã cho.")
}

internal fun fCompMin(ctx: Ctx): Question? {
    val min = when (ctx.grade) {
        1 -> ctx.ri(2, 50)
        else -> ctx.ri(10, 999)
    }
    val vals = mutableSetOf<Int>()
    vals += min
    while (vals.size < 4) vals += ctx.ri(min + 1, min + 500)
    val list = vals.toList().shuffled(ctx.random)
    return mk(ctx, "COMP_MIN", "Số nào bé nhất?", min.toString(), list.filter { it != min }.map { it.toString() }, "$min là số bé nhất trong các số đã cho.")
}

internal fun fPredSucc(ctx: Ctx): Question? {
    val n = when (ctx.grade) {
        1 -> ctx.ri(5, 99)
        2 -> ctx.ri(20, 999)
        else -> ctx.ri(100, 9999)
    }
    return if (ctx.random.nextBoolean())
        mk(ctx, "PRED_SUCC", "Số liền sau của $n là số nào?", (n + 1).toString(), numDistractors(n + 1, ctx), "Số liền sau của $n là ${n + 1}.")
    else
        mk(ctx, "PRED_SUCC", "Số liền trước của $n là số nào?", (n - 1).toString(), numDistractors(n - 1, ctx), "Số liền trước của $n là ${n - 1}.")
}

internal fun fPlace(ctx: Ctx): Question? {
    val n = when (ctx.grade) {
        1 -> ctx.ri(11, 99)
        2 -> ctx.ri(101, 999)
        3 -> ctx.ri(1001, 9999)
        else -> ctx.ri(10001, 999999)
    }
    val s = n.toString()
    return if (ctx.grade == 1 || ctx.random.nextBoolean()) {
        val tens = s[s.length - 2].toString().toInt()
        mk(ctx, "PLACE", "Trong số $n, chữ số hàng chục là chữ số nào?", tens.toString(), listOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9").filter { it != tens.toString() }.shuffled(ctx.random).take(3), "Số $n có chữ số hàng chục là $tens.")
    } else {
        val hundreds = if (s.length >= 3) s[s.length - 3].toString().toInt() else 0
        mk(ctx, "PLACE", "Trong số $n, chữ số hàng trăm là chữ số nào?", hundreds.toString(), listOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9").filter { it != hundreds.toString() }.shuffled(ctx.random).take(3), "Số $n có chữ số hàng trăm là $hundreds.")
    }
}

internal fun fDecomp(ctx: Ctx): Question? {
    val n = when (ctx.grade) {
        1 -> ctx.ri(11, 99)
        else -> ctx.ri(101, 999)
    }
    val s = n.toString()
    val parts = mutableListOf<String>()
    for (i in s.indices) {
        val d = s[i].toString().toInt()
        if (d != 0) parts += (d * Math.pow(10.0, (s.length - 1 - i).toDouble()).toInt()).toString()
    }
    val correct = parts.joinToString(" + ")
    val wrong = listOf(
        (n + 10).toString(), (n - 10).toString(),
        parts.reversed().joinToString(" + ")
    ).filter { it != correct }.distinct()
    return mk(ctx, "DECOMP", "Số $n được phân tích thành tổng nào?", correct, wrong, "Số $n = $correct.")
}

internal fun fEvenOdd(ctx: Ctx): Question? {
    val wantEven = ctx.random.nextBoolean()
    val correct = if (wantEven) (2..98 step 2).toList().random(ctx.random) else (3..99 step 2).toList().random(ctx.random)
    val pool = if (wantEven) (3..99 step 2).toList() else (2..98 step 2).toList()
    val distractors = pool.shuffled(ctx.random).take(3).map { it.toString() }
    return mk(ctx, "EVEN_ODD", if (wantEven) "Số nào là số chẵn?" else "Số nào là số lẻ?", correct.toString(), distractors, if (wantEven) "$correct chia hết cho 2 nên là số chẵn." else "$correct không chia hết cho 2 nên là số lẻ.")
}

internal fun fDoubleHalf(ctx: Ctx): Question? {
    val a = ctx.ri(2, 50)
    return if (ctx.random.nextBoolean())
        mk(ctx, "DOUBLE_HALF", "Gấp đôi số $a là số nào?", (a * 2).toString(), numDistractors(a * 2, ctx), "Gấp đôi số $a là $a × 2 = ${a * 2}.")
    else {
        val even = a * 2
        mk(ctx, "DOUBLE_HALF", "Một nửa của số $even là số nào?", a.toString(), numDistractors(a, ctx), "Một nửa của $even là $even : 2 = $a.")
    }
}

internal fun fRound(ctx: Ctx): Question? {
    val n = ctx.ri(11, 9999)
    return if (ctx.random.nextBoolean()) {
        val r = ((n + 5) / 10) * 10
        mk(ctx, "ROUND", "Làm tròn số $n đến hàng chục được số nào?", r.toString(), numDistractors(r, ctx), "Làm tròn $n đến hàng chục được $r.")
    } else {
        val r = ((n + 50) / 100) * 100
        mk(ctx, "ROUND", "Làm tròn số $n đến hàng trăm được số nào?", r.toString(), numDistractors(r, ctx), "Làm tròn $n đến hàng trăm được $r.")
    }
}

internal fun fSeqNext(ctx: Ctx): Question? {
    val start = ctx.ri(2, 50); val step = ctx.ri(2, 12)
    val seq = listOf(start, start + step, start + 2 * step, start + 3 * step)
    val next = start + 4 * step
    return mk(ctx, "SEQ_NEXT", "Điền số tiếp theo: ${seq.joinToString(", ")}, ?", next.toString(), numDistractors(next, ctx), "Dãy số tăng đều $step mỗi lần, số tiếp theo là $next.")
}

internal fun fSeqMiss(ctx: Ctx): Question? {
    val start = ctx.ri(2, 40); val step = ctx.ri(2, 10)
    val missing = start + 2 * step
    val text = "$start, ${start + step}, ?, ${start + 3 * step}"
    return mk(ctx, "SEQ_MISS", "Điền số còn thiếu: $text", missing.toString(), numDistractors(missing, ctx), "Dãy số tăng đều $step mỗi lần, số còn thiếu là $missing.")
}

internal fun fReadNum(ctx: Ctx): Question? {
    val n = when (ctx.grade) {
        1 -> ctx.ri(0, 99)
        2 -> ctx.ri(10, 999)
        else -> ctx.ri(10, 999)
    }
    val correct = readNum(n)
    val pool = listOf(readNum(n + 1), readNum(maxOf(0, n - 1)), readNum(n + 10), readNum(n + 2))
    return mk(ctx, "READ_NUM", "Số $n đọc là gì?", correct, pool.filter { it != correct }.distinct(), "Số $n đọc là \"$correct\".")
}

internal fun fShape(ctx: Ctx): Question? {
    val shapes = listOf(
        Triple("hình vuông", "Hình nào có 4 cạnh bằng nhau và 4 góc vuông?", listOf("hình tròn", "hình tam giác", "hình chữ nhật")),
        Triple("hình tròn", "Hình nào không có cạnh thẳng?", listOf("hình vuông", "hình tam giác", "hình chữ nhật")),
        Triple("hình tam giác", "Hình nào có 3 cạnh?", listOf("hình vuông", "hình tròn", "hình chữ nhật")),
        Triple("khối lập phương", "Khối nào có 6 mặt đều là hình vuông?", listOf("khối hộp chữ nhật", "khối trụ", "khối cầu")),
        Triple("khối cầu", "Khối nào tròn như quả bóng?", listOf("khối lập phương", "khối hộp chữ nhật", "khối trụ")),
        Triple("khối trụ", "Khối nào giống hình lon nước?", listOf("khối cầu", "khối lập phương", "khối hộp chữ nhật"))
    )
    val (correct, q, distractors) = shapes.random(ctx.random)
    return mk(ctx, "SHAPE", q, correct, distractors, "Đáp án đúng là $correct.")
}

internal fun fCompSum(ctx: Ctx): Question? {
    val a = ctx.ri(2, 50); val b = ctx.ri(2, 50); val c = ctx.ri(2, 50); val d = ctx.ri(2, 50)
    val s1 = a + b; val s2 = c + d
    if (s1 == s2) return null
    val bigger = if (s1 > s2) "$a + $b" else "$c + $d"
    val pool = listOf("$a + $b", "$c + $d", "$a + $c", "$b + $d")
    return mk(ctx, "COMP_SUM", "Tổng nào lớn hơn: $a + $b hay $c + $d?", bigger, pool.filter { it != bigger }.distinct(), "$a + $b = $s1, $c + $d = $s2, nên $bigger lớn hơn.")
}
