package vn.phong.baitaptuan.data

import vn.phong.baitaptuan.model.Question
import kotlin.random.Random

/**
 * Sinh câu Toán tư duy theo tham số: số liệu đổi mỗi lần nên không lặp lại.
 * Mỗi lớp có nhiều dạng (dãy số, tìm x, tuổi, chu vi, diện tích, trung bình cộng...).
 * Toàn bộ do app tự viết.
 */
private fun dec(x: Double): String {
    val r = Math.round(x * 1000.0) / 1000.0
    val s = if (r == r.toLong().toDouble()) r.toLong().toString() else r.toString()
    return s.replace('.', ',')
}

private class Gen(val random: Random) {
    fun int(a: Int, b: Int) = random.nextInt(a, b + 1)
    fun <T> pick(l: List<T>) = l[random.nextInt(l.size)]

    /** Câu trả lời số nguyên với đáp án nhiễu gần đúng. */
    fun num(text: String, c: Int, expl: String, unit: String = ""): Question? {
        val cand = listOf(c + 1, c - 1, c + 2, c - 2, c + 10, c - 10, c * 2, c + 5, c - 5)
            .filter { it > 0 && it != c }.distinct().shuffled(random).take(3)
        if (cand.size < 3) return null
        return build(text, "$c$unit", cand.map { "$it$unit" }, expl)
    }

    fun decNum(text: String, c: Double, expl: String, unit: String = ""): Question? {
        val steps = listOf(0.1, -0.1, 0.5, -0.5, 1.0, -1.0, 10.0, c)
        val cand = steps.map { if (it == c) c * 10 else c + it }
            .filter { it > 0 && Math.abs(it - c) > 1e-9 }.map { dec(it) }.distinct().shuffled(random).take(3)
        if (cand.size < 3) return null
        return build(text, dec(c) + unit, cand.map { it + unit }, expl)
    }

    fun text(text: String, correct: String, wrongs: List<String>, expl: String): Question? {
        val w = wrongs.filter { it != correct }.distinct()
        if (w.size < 3) return null
        return build(text, correct, w.shuffled(random).take(3), expl)
    }

    private fun build(text: String, correct: String, wrongs: List<String>, expl: String): Question {
        val opts = (listOf(correct) + wrongs).shuffled(random)
        return Question(text, opts, opts.indexOf(correct), expl, "TUDUY")
    }
}

private fun grade1(g: Gen): Question? = when (g.int(0, 6)) {
    0 -> { val a = g.int(1, 8); val d = g.int(1, 3); val s = (0..3).map { a + it * d }
        g.num("Số tiếp theo của dãy ${s.joinToString(", ")}, … là:", a + 4 * d, "Mỗi số hơn số đứng trước $d đơn vị.") }
    1 -> { val a = g.int(5, 12); val b = g.int(2, 6); val c = g.int(1, a + b - 1)
        val name = g.pick(listOf("Lan", "Nam", "Hà", "Bình", "Mai", "Tuấn"))
        g.num("$name có $a cái kẹo, mẹ cho thêm $b cái, rồi $name ăn $c cái. $name còn mấy cái kẹo?", a + b - c, "$a + $b − $c = ${a + b - c}.") }
    2 -> { val n = g.int(11, 98)
        if (g.random.nextBoolean()) g.num("Số liền sau của $n là:", n + 1, "$n + 1 = ${n + 1}.") else g.num("Số liền trước của $n là:", n - 1, "$n − 1 = ${n - 1}.") }
    3 -> { val n = g.int(0, 7); g.num("Có bao nhiêu số có một chữ số lớn hơn $n?", 9 - n, "Đó là các số từ ${n + 1} đến 9, có ${9 - n} số.") }
    4 -> { val b = g.int(8, 20); val a = g.int(2, b - 2)
        g.num("$a + … = $b. Số cần điền vào chỗ chấm là:", b - a, "$b − $a = ${b - a}.") }
    5 -> { val x = g.int(10, 99); val y = g.int(10, 99); val z = g.int(10, 99)
        if (x == y || y == z || x == z) null else
        g.num("Số lớn nhất trong ba số $x, $y, $z là:", maxOf(x, y, z), "So sánh các số, số lớn nhất là ${maxOf(x, y, z)}.") }
    else -> { val names = listOf("An", "Bình", "Chi").shuffled(g.random)
        g.text("${names[0]} cao hơn ${names[1]}, ${names[1]} cao hơn ${names[2]}. Ai thấp nhất?", names[2], listOf(names[0], names[1], "Cả ba bằng nhau"), "${names[2]} thấp hơn ${names[1]} và ${names[0]}.") }
}

private fun grade2(g: Gen): Question? = when (g.int(0, 7)) {
    0 -> { val a = g.int(1, 15); val d = g.int(2, 9); val s = (0..3).map { a + it * d }
        g.num("Số tiếp theo của dãy ${s.joinToString(", ")}, … là:", a + 4 * d, "Mỗi số hơn số đứng trước $d đơn vị.") }
    1 -> { val a = g.int(1, 5); val s = (0..3).map { a * (1 shl it) }
        g.num("Số tiếp theo của dãy ${s.joinToString(", ")}, … là:", a * 16, "Mỗi số gấp đôi số đứng trước.") }
    2 -> { val x = g.int(10, 60); val a = g.int(5, 30)
        when (g.int(0, 2)) {
            0 -> g.num("Tìm x: x + $a = ${x + a}", x, "x = ${x + a} − $a = $x.")
            1 -> g.num("Tìm x: x − $a = $x", x + a, "x = $x + $a = ${x + a}.")
            else -> g.num("Tìm x: ${x + a} − x = $a", x, "x = ${x + a} − $a = $x.")
        } }
    3 -> { val a = g.int(2, 9); val b = g.int(2, 9); val c = g.int(1, 9)
        g.num("Mỗi hộp có $a cái bút. Có $b hộp, thêm $c cái bút lẻ. Tất cả có bao nhiêu cái bút?", a * b + c, "$a × $b = ${a * b}, rồi ${a * b} + $c = ${a * b + c}.") }
    4 -> { val k = g.int(10, 30); val m = g.int(4, 12)
        g.num("Bố hơn con $k tuổi. Con $m tuổi thì bố bao nhiêu tuổi?", m + k, "$m + $k = ${m + k}.") }
    5 -> { val a = g.int(3, 15)
        if (g.random.nextBoolean()) g.num("Hình vuông có cạnh $a cm. Chu vi hình vuông là:", 4 * a, "$a × 4 = ${4 * a} (cm).", " cm")
        else { val b = g.int(2, 12); g.num("Hình chữ nhật dài $a cm, rộng $b cm. Chu vi là:", (a + b) * 2, "($a + $b) × 2 = ${(a + b) * 2} (cm).", " cm") } }
    6 -> { val b = g.int(2, 9); val k = g.int(2, 9); val a = b * k
        g.num("Có $a quả cam chia đều cho $b bạn. Mỗi bạn được mấy quả?", k, "$a : $b = $k.") }
    else -> { val a = g.int(2, 6); val b = g.int(2, 6)
        g.num("Có $a cái áo khác nhau và $b cái quần khác nhau. Có bao nhiêu cách chọn một bộ gồm một áo và một quần?", a * b, "$a × $b = ${a * b} cách.") }
}

private fun grade3(g: Gen): Question? = when (g.int(0, 8)) {
    0 -> { val a = g.int(2, 6); val r = g.int(2, 3); val s = (0..3).map { a * Math.pow(r.toDouble(), it.toDouble()).toInt() }
        g.num("Số tiếp theo của dãy ${s.joinToString(", ")}, … là:", a * Math.pow(r.toDouble(), 4.0).toInt(), "Mỗi số gấp $r lần số đứng trước.") }
    1 -> { val n = g.int(2, 6)
        g.num("Số tiếp theo của dãy ${(1..4).joinToString(", ") { ((it + n - 1) * (it + n - 1)).toString() }}, … là:", (n + 4) * (n + 4), "Đây là dãy các số chính phương.") }
    2 -> { val a = g.int(3, 20); val b = g.int(2, a - 1)
        if (g.random.nextBoolean()) g.num("Hình chữ nhật dài $a m, rộng $b m. Diện tích là:", a * b, "$a × $b = ${a * b} (m²).", " m²")
        else g.num("Hình chữ nhật dài $a m, rộng $b m. Chu vi là:", (a + b) * 2, "($a + $b) × 2 = ${(a + b) * 2} (m).", " m") }
    3 -> { val w = g.int(3, 12); val l = w + g.int(1, 10); val p = (l + w) * 2
        g.num("Hình chữ nhật có chu vi $p cm, chiều rộng $w cm. Chiều dài là:", l, "Nửa chu vi là ${p / 2} cm, chiều dài là ${p / 2} − $w = $l (cm).", " cm") }
    4 -> { val a = g.int(3, 9); val x = g.int(3, 12)
        when (g.int(0, 2)) {
            0 -> g.num("Tìm x: x × $a = ${x * a}", x, "x = ${x * a} : $a = $x.")
            1 -> g.num("Tìm x: x : $a = $x", x * a, "x = $x × $a = ${x * a}.")
            else -> g.num("Tìm x: ${x * a} : x = $a", x, "x = ${x * a} : $a = $x.")
        } }
    5 -> { val n = g.int(5, 12)
        g.num("Tính nhanh: ${(1..n).joinToString(" + ")} = ?", n * (n + 1) / 2, "Ghép cặp: ${n + 1} × $n : 2 = ${n * (n + 1) / 2}.") }
    6 -> { val k = g.int(3, 5); val s = g.int(6, 12)
        g.num("Con $s tuổi, mẹ gấp $k lần tuổi con. Mẹ bao nhiêu tuổi?", k * s, "$s × $k = ${k * s}.") }
    7 -> { val a = g.int(3, 9); val b = g.int(3, 9)
        g.num("Giảm ${a * b * 4} đi 4 lần được số nào?", a * b, "${a * b * 4} : 4 = ${a * b}.") }
    else -> { val a = g.int(2, 6); val b = g.int(2, 5); val c = g.int(2, 4)
        g.num("Có $a cái mũ, $b cái áo và $c đôi giày khác nhau. Có bao nhiêu cách chọn một bộ gồm mũ, áo và giày?", a * b * c, "$a × $b × $c = ${a * b * c} cách.") }
}

private fun grade4(g: Gen): Question? = when (g.int(0, 9)) {
    0 -> { val s = g.int(20, 90); val h = g.int(2, 10) * 2; val t = s + (if ((s + h) % 2 == 1) 1 else 0)
        g.num("Tổng hai số là $t, hiệu hai số là $h. Số lớn là:", (t + h) / 2, "Số lớn = ($t + $h) : 2 = ${(t + h) / 2}.") }
    1 -> { val a = g.int(2, 6) * 6; val b = a + g.int(1, 5) * 6; val c = b + g.int(1, 5) * 6
        g.num("Trung bình cộng của $a, $b và $c là:", (a + b + c) / 3, "($a + $b + $c) : 3 = ${(a + b + c) / 3}.") }
    2 -> { val d = g.int(3, 9); val n = d * g.int(2, 12); val t = g.int(1, d - 1)
        g.num("$t/$d của $n là:", n / d * t, "$n : $d × $t = ${n / d * t}.") }
    3 -> { val k = g.int(2, 9); val p = listOf(4 to 25, 8 to 125, 5 to 20, 2 to 50).random(g.random)
        g.num("Tính nhanh: $k × ${p.first} × ${p.second} = ?", k * p.first * p.second, "${p.first} × ${p.second} = ${p.first * p.second}, rồi nhân với $k được ${k * p.first * p.second}.") }
    4 -> { val k = g.int(2, 5); val small = g.int(5, 30); val sum = small * (k + 1)
        g.num("Tổng hai số là $sum. Số này gấp $k lần số kia. Số lớn là:", small * k, "Tổng gồm ${k + 1} phần bằng nhau, mỗi phần $small, số lớn là $small × $k = ${small * k}.") }
    5 -> { val c = g.int(34, 330) * 3
        val wrongs = ArrayList<Int>()
        var guard = 0
        while (wrongs.size < 3 && guard++ < 50) { val w = g.int(100, 999); if (w % 3 != 0 && w !in wrongs) wrongs.add(w) }
        g.text("Số nào sau đây chia hết cho 3?", "$c", wrongs.map { "$it" }, "Tổng các chữ số của $c là ${c.toString().map { it - '0' }.sum()}, chia hết cho 3.") }
    6 -> { val a = g.int(1, 5); val s = (0..4).scan(a) { acc, i -> acc + i + 1 }
        g.num("Số tiếp theo của dãy ${s.take(5).joinToString(", ")}, … là:", s[4] + 5, "Các số hơn nhau lần lượt 1, 2, 3, 4, … nên số tiếp theo hơn ${5} đơn vị.") }
    7 -> { val a = g.int(4, 20); val b = g.int(2, 18)
        g.num("Hình thoi có hai đường chéo dài ${a * 2} cm và ${b * 2} cm. Diện tích là:", a * b * 2, "(${a * 2} × ${b * 2}) : 2 = ${a * b * 2} (cm²).", " cm²") }
    8 -> { val a = g.int(1, 9); val b = g.int(1, 9) * 100 + g.int(0, 9) * 10
        g.num("$a km $b m = … m", a * 1000 + b, "$a km = ${a * 1000} m, cộng $b m được ${a * 1000 + b} m.", " m") }
    else -> { val n = g.int(4, 9); val a = g.int(10, 30); val total = n * a; val others = (1 until n).map { a + (it - n / 2) * 2 }
        val last = total - others.sum()
        g.num("Trung bình cộng của $n số là $a. Biết ${n - 1} số đầu là ${others.joinToString(", ")}. Số còn lại là:", last, "Tổng $n số là $n × $a = $total, trừ tổng các số đã biết được $last.") }
}

private fun grade5(g: Gen): Question? = when (g.int(0, 9)) {
    0 -> { val p = g.pick(listOf(10, 20, 25, 40, 50, 75)); val n = g.int(2, 20) * 20
        g.num("$p% của $n là:", n * p / 100, "$n : 100 × $p = ${n * p / 100}.") }
    1 -> { val p = g.pick(listOf(10, 20, 25, 50)); val x = g.int(2, 12) * 20; val part = x * p / 100
        g.num("$part là $p% của số nào?", x, "$part : $p × 100 = $x.") }
    2 -> { val v = g.int(3, 12) * 5; val t = g.pick(listOf(2, 3, 4)); 
        g.num("Một xe đi với vận tốc $v km/giờ trong $t giờ. Quãng đường đi được là:", v * t, "$v × $t = ${v * t} (km).", " km") }
    3 -> { val t = g.pick(listOf(2, 3, 4, 5)); val v = g.int(4, 16) * 5; val s = v * t
        g.num("Một xe đi quãng đường $s km trong $t giờ. Vận tốc của xe là:", v, "$s : $t = $v (km/giờ).", " km/giờ") }
    4 -> { val r = g.int(2, 12)
        if (g.random.nextBoolean()) g.decNum("Hình tròn bán kính $r cm có chu vi (lấy π = 3,14) là:", r * 2 * 3.14, "$r × 2 × 3,14 = ${dec(r * 2 * 3.14)} (cm).", " cm")
        else g.decNum("Hình tròn bán kính $r cm có diện tích (lấy π = 3,14) là:", r * r * 3.14, "$r × $r × 3,14 = ${dec(r * r * 3.14)} (cm²).", " cm²") }
    5 -> { val a = g.int(2, 9); val b = g.int(2, 9); val c = g.int(2, 9)
        g.num("Hình hộp chữ nhật dài $a cm, rộng $b cm, cao $c cm. Thể tích là:", a * b * c, "$a × $b × $c = ${a * b * c} (cm³).", " cm³") }
    6 -> { val a = g.int(11, 99) / 10.0; val k = g.pick(listOf(10, 100))
        g.decNum("${dec(a)} × $k = ?", a * k, "Nhân với $k thì dấu phẩy dịch sang phải ${if (k == 10) 1 else 2} chữ số.") }
    7 -> { val a = g.int(1, 4); val b = g.int(1, 5); val s = mutableListOf(a, b); repeat(4) { s.add(s[s.size - 1] + s[s.size - 2]) }
        g.num("Số tiếp theo của dãy ${s.joinToString(", ")}, … là:", s[5] + s[4], "Mỗi số bằng tổng hai số đứng trước nó.") }
    8 -> { val x = g.int(2, 9) / 2.0 + 1; val a = g.pick(listOf(2.0, 2.5, 4.0, 5.0))
        g.decNum("Tìm x: x × ${dec(a)} = ${dec(x * a)}", x, "x = ${dec(x * a)} : ${dec(a)} = ${dec(x)}.") }
    else -> { val k = g.int(2, 4); val m = g.int(5, 25); val sum = m * (k + 1)
        g.num("Tổng hai số là $sum, số lớn gấp $k lần số bé. Số bé là:", m, "Tổng gồm ${k + 1} phần bằng nhau, số bé là $sum : ${k + 1} = $m.") }
}

/** Sinh [count] câu Toán tư duy khác nhau cho lớp [grade]. */
fun generateToanTuDuy(grade: Int, count: Int, random: Random = Random(System.nanoTime())): List<Question> {
    val g = Gen(random)
    val out = ArrayList<Question>()
    val seen = HashSet<String>()
    var tries = 0
    while (out.size < count && tries < count * 20) {
        tries++
        val q = when (grade) { 1 -> grade1(g); 2 -> grade2(g); 3 -> grade3(g); 4 -> grade4(g); else -> grade5(g) } ?: continue
        if (q.options.distinct().size == 4 && seen.add(q.text)) out.add(q)
    }
    return out
}
