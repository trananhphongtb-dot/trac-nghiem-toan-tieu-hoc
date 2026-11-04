package vn.phong.toantieuhoc.data

import vn.phong.toantieuhoc.model.ExamKind
import vn.phong.toantieuhoc.model.Question
import kotlin.random.Random

// Phần 2: các dạng phân số, thập phân, hình học, đo lường, lời văn, thống kê.
// Các hàm mk / fmt / gcd / simpFrac / numDistractors / decDistractors / strDistractors
// và lớp Ctx được định nghĩa ở WeeklyGenerators.kt, cùng package nên dùng chung được.

private fun mk2(ctx: Ctx, form: String, text: String, correct: String, distractors: List<String>, explanation: String): Question? {
    val clean = distractors.filter { it != correct }.distinct()
    if (clean.size < 3) return null
    val opts = (listOf(correct) + clean.take(3)).shuffled(ctx.random)
    val idx = opts.indexOf(correct)
    if (opts.size != 4 || idx < 0) return null
    return Question(text, opts, idx, explanation, form)
}

private fun fFracPart(ctx: Ctx): Question? {
    val den = ctx.ri(2, 9); val num = ctx.ri(1, den - 1)
    val correct = "$num/$den"
    val pool = listOf("$den/$num", "${num + 1}/$den", "$num/${den + 1}", "1/$den")
    return mk2(ctx, "FRAC_PART", "Phân số nào chỉ $num phần trong $den phần bằng nhau?", correct, pool, "Phân số là $correct (tử số $num, mẫu số $den).")
}

private fun fFracAdd(ctx: Ctx): Question? {
    val den = ctx.ri(2, 12); val a = ctx.ri(1, den - 1); val b = ctx.ri(1, den - a)
    val c = a + b
    val correct = simpFrac(c, den)
    val pool = listOf(simpFrac(c + 1, den), simpFrac(maxOf(1, c - 1), den), simpFrac(a + b, den + 1))
    return mk2(ctx, "FRAC_ADD", "$a/$den + $b/$den = ?", correct, pool, "Cùng mẫu số: $a/$den + $b/$den = $c/$den = $correct.")
}

private fun fFracSub(ctx: Ctx): Question? {
    val den = ctx.ri(2, 12); val a = ctx.ri(2, den); val b = ctx.ri(1, a - 1)
    val c = a - b
    val correct = simpFrac(c, den)
    val pool = listOf(simpFrac(c + 1, den), simpFrac(maxOf(1, c - 1), den), "$a/$den")
    return mk2(ctx, "FRAC_SUB", "$a/$den - $b/$den = ?", correct, pool, "Cùng mẫu số: $a/$den - $b/$den = $c/$den = $correct.")
}

private fun fFracMul(ctx: Ctx): Question? {
    val a = ctx.ri(1, 9); val b = ctx.ri(2, 9); val c = ctx.ri(1, 9); val d = ctx.ri(2, 9)
    val num = a * c; val den = b * d
    val correct = simpFrac(num, den)
    val pool = listOf(simpFrac(num + 1, den), simpFrac(num, den + 1), simpFrac(a * c, b + d))
    return mk2(ctx, "FRAC_MUL", "$a/$b × $c/$d = ?", correct, pool, "Nhân phân số: tử nhân tử, mẫu nhân mẫu, rồi rút gọn được $correct.")
}

private fun fFracCmp(ctx: Ctx): Question? {
    val den = ctx.ri(2, 12); val a = ctx.ri(1, den - 1); val b = ctx.ri(1, den - 1)
    if (a == b) return null
    val bigger = maxOf(a, b); val smaller = minOf(a, b)
    val correct = "$bigger/$den"
    val pool = listOf("$smaller/$den", simpFrac(bigger, den + 1), simpFrac(bigger + 1, den), "$smaller/${den + 1}")
    return mk2(ctx, "FRAC_CMP", "Phân số nào lớn hơn trong các phân số cùng mẫu số $den?", correct, pool, "Cùng mẫu số, phân số nào có tử số lớn hơn thì lớn hơn.")
}

private fun fFracSimp(ctx: Ctx): Question? {
    val g = ctx.ri(2, 9); val a = ctx.ri(1, 9); val b = ctx.ri(a + 1, 12)
    val num = a * g; val den = b * g
    val correct = simpFrac(num, den)
    val pool = listOf("$num/$den", simpFrac(num + 1, den), simpFrac(num, den + 1))
    return mk2(ctx, "FRAC_SIMP", "Rút gọn phân số $num/$den được phân số nào?", correct, pool, "Chia cả tử và mẫu cho $g được $correct.")
}

private fun fFracOf(ctx: Ctx): Question? {
    val den = ctx.ri(2, 9); val num = ctx.ri(1, den - 1); val q = ctx.ri(2, 20)
    val total = den * q; val part = num * q
    return mk2(ctx, "FRAC_OF", "$num/$den của $total là bao nhiêu?", part.toString(), numDistractors(part, ctx), "$num/$den của $total là $total : $den × $num = $part.")
}

private fun decVal(ctx: Ctx): Double {
    return when (ctx.grade) {
        5 -> ctx.ri(11, 999) / 10.0
        else -> ctx.ri(11, 99) / 10.0
    }
}

private fun fDecAdd(ctx: Ctx): Question? {
    val a = decVal(ctx); val b = decVal(ctx); val c = a + b
    return mk2(ctx, "DEC_ADD", "${fmt(a)} + ${fmt(b)} = ?", fmt(c), decDistractors(c, ctx), "${fmt(a)} + ${fmt(b)} = ${fmt(c)}.")
}

private fun fDecSub(ctx: Ctx): Question? {
    val a = decVal(ctx); val b = decVal(ctx)
    val big = maxOf(a, b); val small = minOf(a, b); val c = big - small
    return mk2(ctx, "DEC_SUB", "${fmt(big)} - ${fmt(small)} = ?", fmt(c), decDistractors(c, ctx), "${fmt(big)} - ${fmt(small)} = ${fmt(c)}.")
}

private fun fDecMul(ctx: Ctx): Question? {
    val a = decVal(ctx); val b = ctx.ri(2, 9); val c = a * b
    return mk2(ctx, "DEC_MUL", "${fmt(a)} × $b = ?", fmt(c), decDistractors(c, ctx), "${fmt(a)} × $b = ${fmt(c)}.")
}

private fun fDecCmp(ctx: Ctx): Question? {
    val a = decVal(ctx); val b = decVal(ctx)
    if (a == b) return null
    val bigger = maxOf(a, b); val smaller = minOf(a, b)
    val pool = listOf(fmt(smaller), fmt(bigger + 0.1), fmt(bigger - 0.1), fmt(bigger + 1.0))
    return mk2(ctx, "DEC_CMP", "Số thập phân nào lớn hơn?", fmt(bigger), pool, "So sánh từng hàng, ${fmt(bigger)} là số lớn hơn.")
}

private fun fDecPlace(ctx: Ctx): Question? {
    val a = decVal(ctx)
    val s = fmt(a)
    val parts = s.split(".")
    if (parts.size != 2) return null
    val tenth = parts[1].first().toString()
    val pool = listOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9").filter { it != tenth }.shuffled(ctx.random).take(3)
    return mk2(ctx, "DEC_PLACE", "Trong số $s, chữ số ở hàng phần mười là chữ số nào?", tenth, pool, "Số $s có chữ số hàng phần mười là $tenth.")
}

private fun fPercent(ctx: Ctx): Question? {
    val p = (5..95 step 5).toList().random(ctx.random)
    val base = (100..1000 step 100).toList().random(ctx.random)
    val v = base * p / 100
    return mk2(ctx, "PERCENT", "$p% của $base là bao nhiêu?", v.toString(), numDistractors(v, ctx), "$p% của $base là $base × $p : 100 = $v.")
}

private fun fPerimRect(ctx: Ctx): Question? {
    val a = ctx.ri(3, 30); val b = ctx.ri(3, 30); val c = 2 * (a + b)
    return mk2(ctx, "PERIM_RECT", "Hình chữ nhật dài ${a}cm, rộng ${b}cm. Chu vi là bao nhiêu?", "$c cm", listOf("${c + 2} cm", "${a * b} cm", "${a + b} cm"), "Chu vi hình chữ nhật là ($a + $b) × 2 = $c cm.")
}

private fun fPerimSq(ctx: Ctx): Question? {
    val a = ctx.ri(3, 30); val c = a * 4
    return mk2(ctx, "PERIM_SQ", "Hình vuông có cạnh ${a}cm. Chu vi là bao nhiêu?", "$c cm", listOf("${c + 4} cm", "${a * a} cm", "${a + 4} cm"), "Chu vi hình vuông là $a × 4 = $c cm.")
}

private fun fPerimTri(ctx: Ctx): Question? {
    val a = ctx.ri(3, 20); val b = ctx.ri(3, 20); val c2 = ctx.ri(3, 20); val c = a + b + c2
    return mk2(ctx, "PERIM_TRI", "Hình tam giác có 3 cạnh ${a}cm, ${b}cm, ${c2}cm. Chu vi là bao nhiêu?", "$c cm", listOf("${c + 3} cm", "${a * b} cm", "${a + b} cm"), "Chu vi hình tam giác là $a + $b + $c2 = $c cm.")
}

private fun fAreaRect(ctx: Ctx): Question? {
    val a = ctx.ri(3, 30); val b = ctx.ri(3, 30); val c = a * b
    return mk2(ctx, "AREA_RECT", "Hình chữ nhật dài ${a}cm, rộng ${b}cm. Diện tích là bao nhiêu?", "$c cm²", listOf("${c + a} cm²", "${2 * (a + b)} cm²", "${a + b} cm²"), "Diện tích hình chữ nhật là $a × $b = $c cm².")
}

private fun fAreaSq(ctx: Ctx): Question? {
    val a = ctx.ri(3, 30); val c = a * a
    return mk2(ctx, "AREA_SQ", "Hình vuông có cạnh ${a}cm. Diện tích là bao nhiêu?", "$c cm²", listOf("${a * 4} cm²", "${c + a} cm²", "${a + a} cm²"), "Diện tích hình vuông là $a × $a = $c cm².")
}

private fun fAreaTri(ctx: Ctx): Question? {
    val base = (4..30 step 2).toList().random(ctx.random); val h = ctx.ri(2, 20); val c = base * h / 2
    return mk2(ctx, "AREA_TRI", "Hình tam giác có đáy ${base}cm, chiều cao ${h}cm. Diện tích là bao nhiêu?", "$c cm²", listOf("${base * h} cm²", "${c + base} cm²", "${base + h} cm²"), "Diện tích hình tam giác là $base × $h : 2 = $c cm².")
}

private fun fAreaTrap(ctx: Ctx): Question? {
    val big = ctx.ri(10, 30); val small = ctx.ri(3, 9); val h = (2..20 step 2).toList().random(ctx.random)
    val c = (big + small) * h / 2
    return mk2(ctx, "AREA_TRAP", "Hình thang có đáy lớn ${big}cm, đáy bé ${small}cm, chiều cao ${h}cm. Diện tích là bao nhiêu?", "$c cm²", listOf("${big * h} cm²", "${c + 10} cm²", "${big + small + h} cm²"), "Diện tích hình thang là ($big + $small) × $h : 2 = $c cm².")
}

private fun fAreaCirc(ctx: Ctx): Question? {
    val r = ctx.ri(2, 20); val c = r * r * 3.14
    return mk2(ctx, "AREA_CIRC", "Hình tròn có bán kính ${r}cm. Diện tích là bao nhiêu? (lấy π = 3,14)", "${fmt(c)} cm²", listOf("${fmt(c + 3.14)} cm²", "${fmt(r * 2 * 3.14)} cm²", "${fmt(c - 3.14)} cm²"), "Diện tích hình tròn là $r × $r × 3,14 = ${fmt(c)} cm².")
}

private fun fVolBox(ctx: Ctx): Question? {
    val a = ctx.ri(2, 20); val b = ctx.ri(2, 20); val c2 = ctx.ri(2, 20); val v = a * b * c2
    return mk2(ctx, "VOL_BOX", "Hình hộp chữ nhật dài ${a}cm, rộng ${b}cm, cao ${c2}cm. Thể tích là bao nhiêu?", "$v cm³", listOf("${v + a} cm³", "${a * b} cm³", "${2 * (a + b)} cm³"), "Thể tích hình hộp là $a × $b × $c2 = $v cm³.")
}

private fun fVolCube(ctx: Ctx): Question? {
    val a = ctx.ri(2, 20); val v = a * a * a
    return mk2(ctx, "VOL_CUBE", "Hình lập phương có cạnh ${a}cm. Thể tích là bao nhiêu?", "$v cm³", listOf("${a * a} cm³", "${a * 6} cm³", "${v + a} cm³"), "Thể tích hình lập phương là $a × $a × $a = $v cm³.")
}

private fun fConvLen(ctx: Ctx): Question? {
    val m = ctx.ri(1, 20)
    return if (ctx.random.nextBoolean())
        mk2(ctx, "CONV_LEN", "${m}m = ? cm", "${m * 100} cm", listOf("${m * 10} cm", "${m * 1000} cm", "$m cm"), "1m = 100cm, nên ${m}m = ${m * 100}cm.")
    else
        mk2(ctx, "CONV_LEN", "${m * 100}cm = ? m", "${m}m", listOf("${m * 10}m", "${m * 100}m", "${m + 1}m"), "${m * 100}cm = ${m}m vì 100cm = 1m.")
}

private fun fConvMass(ctx: Ctx): Question? {
    val kg = ctx.ri(1, 20)
    return mk2(ctx, "CONV_MASS", "${kg}kg = ? g", "${kg * 1000} g", listOf("${kg * 100} g", "${kg * 10} g", "$kg g"), "1kg = 1000g, nên ${kg}kg = ${kg * 1000}g.")
}

private fun fConvArea(ctx: Ctx): Question? {
    val v = ctx.ri(1, 20)
    return if (ctx.random.nextBoolean())
        mk2(ctx, "CONV_AREA", "${v}m² = ? dm²", "${v * 100} dm²", listOf("${v * 10} dm²", "${v * 1000} dm²", "$v dm²"), "1m² = 100dm², nên ${v}m² = ${v * 100}dm².")
    else
        mk2(ctx, "CONV_AREA", "${v}dm³ = ? cm³", "${v * 1000} cm³", listOf("${v * 100} cm³", "${v * 10} cm³", "$v cm³"), "1dm³ = 1000cm³, nên ${v}dm³ = ${v * 1000}cm³.")
}

private fun fConvTime(ctx: Ctx): Question? {
    val h = ctx.ri(1, 12)
    return mk2(ctx, "CONV_TIME", "${h} giờ = ? phút", "${h * 60} phút", listOf("${h * 100} phút", "${h * 10} phút", "$h phút"), "1 giờ = 60 phút, nên ${h} giờ = ${h * 60} phút.")
}

private fun fMoney(ctx: Ctx): Question? {
    val price = (1000..40000 step 1000).toList().random(ctx.random)
    val pay = ((price / 10000) + 1) * 10000
    val change = pay - price
    return mk2(ctx, "MONEY", "Mua một món hàng giá $price đồng, trả $pay đồng. Được trả lại bao nhiêu tiền?", "$change đồng", listOf("${change + 1000} đồng", "${change - 1000} đồng", "$price đồng"), "Tiền trả lại là $pay - $price = $change đồng.")
}

private fun fClock(ctx: Ctx): Question? {
    val start = ctx.ri(6, 18); val dur = ctx.ri(1, 5); val end = (start + dur) % 24
    return mk2(ctx, "CLOCK", "Bắt đầu lúc $start giờ, sau $dur giờ nữa là mấy giờ?", "$end giờ", listOf("${(end + 1) % 24} giờ", "$start giờ", "${if (end == 0) 23 else end - 1} giờ"), "Bắt đầu lúc $start giờ, cộng thêm $dur giờ được $end giờ.")
}

private fun fWeekday(ctx: Ctx): Question? {
    val days = listOf("Thứ hai", "Thứ ba", "Thứ tư", "Thứ năm", "Thứ sáu", "Thứ bảy", "Chủ nhật")
    val start = ctx.ri(0, 6); val k = ctx.ri(2, 10); val end = (start + k) % 7
    return mk2(ctx, "WEEKDAY", "Hôm nay là ${days[start]}, $k ngày nữa là thứ mấy?", days[end], days.filter { it != days[end] }.shuffled(ctx.random).take(3), "Cộng $k ngày vào ${days[start]}, đếm vòng 7 ngày được ${days[end]}.")
}

private fun fWordAdd(ctx: Ctx): Question? {
    val name = pickName(ctx); val obj = pickObj(ctx); val a = ctx.ri(2, 50); val b = ctx.ri(2, 50); val c = a + b
    return mk2(ctx, "WORD_ADD", "$name có $a $obj, mẹ cho thêm $b $obj. Hỏi $name có tất cả bao nhiêu $obj?", "$c $obj", listOf("${c + 1} $obj", "${c - 1} $obj", "${a + b + 10} $obj"), "$a + $b = $c, nên $name có tất cả $c $obj.")
}

private fun fWordSub(ctx: Ctx): Question? {
    val name = pickName(ctx); val obj = pickObj(ctx); val a = ctx.ri(10, 99); val b = ctx.ri(2, a - 1); val c = a - b
    return mk2(ctx, "WORD_SUB", "$name có $a $obj, $name cho bạn $b $obj. Hỏi $name còn lại bao nhiêu $obj?", "$c $obj", listOf("${c + 1} $obj", "${c - 1} $obj", "${a + b} $obj"), "$a - $b = $c, nên $name còn lại $c $obj.")
}

private fun fWordMul(ctx: Ctx): Question? {
    val name = pickName(ctx); val obj = pickObj(ctx); val a = ctx.ri(2, 12); val b = ctx.ri(2, 12); val c = a * b
    return mk2(ctx, "WORD_MUL", "Mỗi hộp có $a $obj. Hỏi $b hộp như thế có bao nhiêu $obj?", "$c $obj", listOf("${c + a} $obj", "${c - a} $obj", "${a + b} $obj"), "$a × $b = $c, nên $b hộp có $c $obj.")
}

private fun fWordDiv(ctx: Ctx): Question? {
    val name = pickName(ctx); val obj = pickObj(ctx); val b = ctx.ri(2, 12); val q = ctx.ri(2, 12); val a = b * q
    return mk2(ctx, "WORD_DIV", "$name có $a $obj, chia đều vào $b hộp. Hỏi mỗi hộp có bao nhiêu $obj?", "$q $obj", listOf("${q + 1} $obj", "${q - 1} $obj", "$b $obj"), "$a : $b = $q, nên mỗi hộp có $q $obj.")
}

private fun fWord2Step(ctx: Ctx): Question? {
    val name = pickName(ctx); val obj = pickObj(ctx); val a = ctx.ri(5, 30); val b = ctx.ri(2, 20); val c = ctx.ri(2, 10)
    val total = (a + b) * c
    return mk2(ctx, "WORD_2STEP", "$name mua $c túi, mỗi túi có $a $obj đỏ và $b $obj xanh. Hỏi $name mua tất cả bao nhiêu $obj?", "$total $obj", listOf("${total + c} $obj", "${total - c} $obj", "${a + b} $obj"), "Mỗi túi có ${a + b} $obj, $c túi có ${a + b} × $c = $total $obj.")
}

private fun fChart(ctx: Ctx): Question? {
    val cats = listOf("Lớp 1A", "Lớp 1B", "Lớp 1C", "Lớp 1D")
    val vals = mutableSetOf<Int>()
    while (vals.size < 4) vals += ctx.ri(5, 40)
    val list = vals.toList()
    val data = cats.zip(list).joinToString(", ") { "${it.first}: ${it.second} học sinh" }
    val maxIdx = list.indexOf(list.max())
    return mk2(ctx, "CHART", "Biểu đồ cho biết: $data. Lớp nào có nhiều học sinh nhất?", cats[maxIdx], cats.filterIndexed { i, _ -> i != maxIdx }, "Lớp ${cats[maxIdx]} có ${list[maxIdx]} học sinh, nhiều nhất trong các lớp.")
}

private fun fProb(ctx: Ctx): Question? {
    return when (ctx.ri(0, 2)) {
        0 -> mk2(ctx, "PROB", "Trong hộp chỉ có bi đỏ. Lấy ngẫu nhiên 1 viên bi, điều gì xảy ra?", "chắc chắn được bi đỏ", listOf("có thể được bi đỏ", "không thể được bi đỏ", "chắc chắn được bi xanh"), "Trong hộp chỉ có bi đỏ nên chắc chắn được bi đỏ.")
        1 -> mk2(ctx, "PROB", "Trong hộp có bi đỏ và bi xanh. Lấy ngẫu nhiên 1 viên bi, điều gì xảy ra?", "có thể được bi đỏ", listOf("chắc chắn được bi đỏ", "không thể được bi đỏ", "chắc chắn được bi xanh"), "Trong hộp có cả bi đỏ và bi xanh nên có thể được bi đỏ.")
        else -> mk2(ctx, "PROB", "Trong hộp chỉ có bi đỏ. Lấy ngẫu nhiên 1 viên bi xanh, điều gì xảy ra?", "không thể được bi xanh", listOf("chắc chắn được bi xanh", "có thể được bi xanh", "chắc chắn được bi đỏ"), "Trong hộp không có bi xanh nên không thể được bi xanh.")
    }
}

private fun fAvg(ctx: Ctx): Question? {
    val a = ctx.ri(5, 30); val b = ctx.ri(5, 30); val c = ctx.ri(5, 30)
    val sum = a + b + c
    if (sum % 3 != 0) return null
    val avg = sum / 3
    return mk2(ctx, "AVG", "Trung bình cộng của $a, $b, $c là bao nhiêu?", avg.toString(), numDistractors(avg, ctx), "Trung bình cộng là ($a + $b + $c) : 3 = $avg.")
}

private fun fSumDiff(ctx: Ctx): Question? {
    val sum = (20..200 step 2).toList().random(ctx.random); val diff = (2..40 step 2).toList().random(ctx.random)
    if (diff >= sum) return null
    val big = (sum + diff) / 2; val small = (sum - diff) / 2
    return mk2(ctx, "SUM_DIFF", "Hai số có tổng là $sum, hiệu là $diff. Số lớn là số nào?", big.toString(), numDistractors(big, ctx), "Số lớn là ($sum + $diff) : 2 = $big.")
}

private fun fMul10(ctx: Ctx): Question? {
    val a = ctx.ri(2, 999); val factor = listOf(10, 100, 1000).random(ctx.random)
    return if (ctx.random.nextBoolean())
        mk2(ctx, "MUL10", "$a × $factor = ?", (a * factor).toString(), numDistractors(a * factor, ctx), "$a × $factor = ${a * factor}.")
    else {
        val big = a * factor
        mk2(ctx, "MUL10", "$big : $factor = ?", a.toString(), numDistractors(a, ctx), "$big : $factor = $a.")
    }
}

private fun fExpr(ctx: Ctx): Question? {
    val a = ctx.ri(2, 20); val b = ctx.ri(2, 9); val c = ctx.ri(2, 20)
    val correct = a + b * c
    return mk2(ctx, "EXPR", "$a + $b × $c = ?", correct.toString(), numDistractors(correct, ctx), "Nhân trước, cộng sau: $b × $c = ${b * c}, rồi $a + ${b * c} = $correct.")
}

private fun fRoman(ctx: Ctx): Question? {
    val map = mapOf(1 to "I", 2 to "II", 3 to "III", 4 to "IV", 5 to "V", 6 to "VI", 7 to "VII", 8 to "VIII", 9 to "IX", 10 to "X", 11 to "XI", 12 to "XII")
    val n = ctx.ri(1, 12); val correct = map[n] ?: return null
    val pool = map.values.filter { it != correct }.shuffled(ctx.random).take(3)
    return mk2(ctx, "ROMAN", "Số La Mã của số $n là gì?", correct, pool, "Số $n viết bằng chữ số La Mã là $correct.")
}

private fun fAngle(ctx: Ctx): Question? {
    val deg = listOf(30, 45, 60, 90, 120, 150, 180).random(ctx.random)
    val correct = when {
        deg < 90 -> "góc nhọn"
        deg == 90 -> "góc vuông"
        deg < 180 -> "góc tù"
        else -> "góc bẹt"
    }
    val pool = listOf("góc nhọn", "góc vuông", "góc tù", "góc bẹt").filter { it != correct }
    return mk2(ctx, "ANGLE", "Góc có số đo $deg° là góc gì?", correct, pool, "Góc $deg° là $correct.")
}

private fun fSpeed(ctx: Ctx): Question? {
    val v = ctx.ri(10, 60); val t = ctx.ri(2, 5); val s = v * t
    return when (ctx.ri(0, 2)) {
        0 -> mk2(ctx, "SPEED", "Một ô tô đi với vận tốc $v km/giờ trong $t giờ. Quãng đường ô tô đi được là bao nhiêu?", "$s km", listOf("${s + v} km", "${s - v} km", "$v km"), "Quãng đường là $v × $t = $s km.")
        1 -> mk2(ctx, "SPEED", "Một ô tô đi được $s km trong $t giờ. Vận tốc của ô tô là bao nhiêu?", "$v km/giờ", listOf("${v + 5} km/giờ", "${v - 5} km/giờ", "$s km/giờ"), "Vận tốc là $s : $t = $v km/giờ.")
        else -> mk2(ctx, "SPEED", "Một ô tô đi với vận tốc $v km/giờ, đi được $s km. Thời gian ô tô đi là bao nhiêu?", "$t giờ", listOf("${t + 1} giờ", "${t - 1} giờ", "$v giờ"), "Thời gian là $s : $v = $t giờ.")
    }
}

private fun fTimeAdd(ctx: Ctx): Question? {
    val h = ctx.ri(1, 12); val m = ctx.ri(0, 59); val add = ctx.ri(15, 120)
    val total = h * 60 + m + add
    val nh = (total / 60) % 24; val nm = total % 60
    val correct = "$nh giờ $nm phút"
    val pool = listOf("${(nh + 1) % 24} giờ $nm phút", "$nh giờ ${(nm + 10) % 60} phút", "$h giờ $m phút")
    return mk2(ctx, "TIME_ADD", "Bắt đầu lúc $h giờ $m phút, sau $add phút nữa là mấy giờ?", correct, pool, "Cộng thêm $add phút vào $h giờ $m phút được $correct.")
}

// ---------- Điều phối ----------
private fun generateOne(ctx: Ctx, form: String): Question? {
    return when (form) {
        "ADD" -> fAdd(ctx); "SUB" -> fSub(ctx); "ADD3" -> fAdd3(ctx)
        "MUL" -> fMul(ctx); "DIV" -> fDiv(ctx)
        "MISS_ADD" -> fMissAdd(ctx); "MISS_SUB" -> fMissSub(ctx)
        "MISS_MUL" -> fMissMul(ctx); "MISS_DIV" -> fMissDiv(ctx)
        "COMP_MAX" -> fCompMax(ctx); "COMP_MIN" -> fCompMin(ctx)
        "PRED_SUCC" -> fPredSucc(ctx); "PLACE" -> fPlace(ctx); "DECOMP" -> fDecomp(ctx)
        "EVEN_ODD" -> fEvenOdd(ctx); "DOUBLE_HALF" -> fDoubleHalf(ctx); "ROUND" -> fRound(ctx)
        "SEQ_NEXT" -> fSeqNext(ctx); "SEQ_MISS" -> fSeqMiss(ctx); "READ_NUM" -> fReadNum(ctx)
        "SHAPE" -> fShape(ctx); "COMP_SUM" -> fCompSum(ctx)
        "FRAC_PART" -> fFracPart(ctx); "FRAC_ADD" -> fFracAdd(ctx); "FRAC_SUB" -> fFracSub(ctx)
        "FRAC_MUL" -> fFracMul(ctx); "FRAC_CMP" -> fFracCmp(ctx); "FRAC_SIMP" -> fFracSimp(ctx); "FRAC_OF" -> fFracOf(ctx)
        "DEC_ADD" -> fDecAdd(ctx); "DEC_SUB" -> fDecSub(ctx); "DEC_MUL" -> fDecMul(ctx)
        "DEC_CMP" -> fDecCmp(ctx); "DEC_PLACE" -> fDecPlace(ctx); "PERCENT" -> fPercent(ctx)
        "PERIM_RECT" -> fPerimRect(ctx); "PERIM_SQ" -> fPerimSq(ctx); "PERIM_TRI" -> fPerimTri(ctx)
        "AREA_RECT" -> fAreaRect(ctx); "AREA_SQ" -> fAreaSq(ctx); "AREA_TRI" -> fAreaTri(ctx)
        "AREA_TRAP" -> fAreaTrap(ctx); "AREA_CIRC" -> fAreaCirc(ctx)
        "VOL_BOX" -> fVolBox(ctx); "VOL_CUBE" -> fVolCube(ctx)
        "CONV_LEN" -> fConvLen(ctx); "CONV_MASS" -> fConvMass(ctx); "CONV_AREA" -> fConvArea(ctx); "CONV_TIME" -> fConvTime(ctx)
        "MONEY" -> fMoney(ctx); "CLOCK" -> fClock(ctx); "WEEKDAY" -> fWeekday(ctx)
        "WORD_ADD" -> fWordAdd(ctx); "WORD_SUB" -> fWordSub(ctx); "WORD_MUL" -> fWordMul(ctx)
        "WORD_DIV" -> fWordDiv(ctx); "WORD_2STEP" -> fWord2Step(ctx)
        "CHART" -> fChart(ctx); "PROB" -> fProb(ctx); "AVG" -> fAvg(ctx); "SUM_DIFF" -> fSumDiff(ctx)
        "MUL10" -> fMul10(ctx); "EXPR" -> fExpr(ctx); "ROMAN" -> fRoman(ctx); "ANGLE" -> fAngle(ctx)
        "SPEED" -> fSpeed(ctx); "TIME_ADD" -> fTimeAdd(ctx)
        "HARD_HONEY" -> fHardHoney(ctx); "HARD_RICE" -> fHardRice(ctx)
        "HARD_REVERSE_MORE" -> fHardReverseMore(ctx); "HARD_REVERSE_LESS" -> fHardReverseLess(ctx)
        "HARD_CONV_MIXED" -> fHardConvMixed(ctx); "HARD_TABLE_SUM" -> fHardTableSum(ctx)
        "HARD_PIE" -> fHardPie(ctx); "HARD_FINDX" -> fHardFindX(ctx)
        "HARD_HIDDEN_RECT" -> fHardHiddenRect(ctx); "HARD_ANNULUS" -> fHardAnnulus(ctx)
        "HARD_AVG_REVERSE" -> fHardAvgReverse(ctx); "HARD_WORK" -> fHardWork(ctx)
        "HARD_CANO" -> fHardCano(ctx); "HARD_SUMDIFF_MIDDLE" -> fHardSumDiffMiddle(ctx)
        "HARD_DEC_REMAINDER" -> fHardDecRemainder(ctx); "HARD_WORD2_REVERSE" -> fHardWord2Reverse(ctx)
        "HARD_TIME_BACKWARD" -> fHardTimeBackward(ctx); "HARD_PERCENT_REVERSE" -> fHardPercentReverse(ctx)
        "HARD_VOLUME_TANK" -> fHardVolumeTank(ctx); "HARD_TIME_CHAIN" -> fHardTimeChain(ctx)
        "COMP_LIST_MAX" -> fCompListMax(ctx); "COMP_LIST_MIN" -> fCompListMin(ctx)
        "EVENODD_LIST" -> fEvenOddList(ctx); "PLACE_TENS_UNITS" -> fPlaceTensUnits(ctx)
        "GROUP_COUNT" -> fGroupCount(ctx); "EXPR_BIGGEST" -> fExprBiggest(ctx)
        "MONEY_CHANGE" -> fMoneyChange(ctx); "UNIT_CONV_SIMPLE" -> fUnitConvSimple(ctx)
        "WORD_GROUP" -> fWordGroup(ctx); "PLACE_DIGIT_VALUE" -> fPlaceDigitValue(ctx)
        "ADD_SUB_CHAIN" -> fAddSubChain(ctx); "TRIPLE_PART" -> fTriplePart(ctx)
        else -> null
    }
}

private fun newRandom(): Random = Random(System.nanoTime() xor System.currentTimeMillis())

/** Sinh một bộ 40 câu cho một danh sách dạng. Mỗi lần gọi đều ra bộ mới.
 *  Dạng của tuần (hoặc phạm vi đề kỳ) chiếm phần đầu; 8 câu cuối luôn là
 *  dạng khó/nâng cao phù hợp lớp (xem hardFormsForGrade), để độ khó tăng
 *  dần trong đề như đề tuần thật.
 *
 *  Hai lỗi cũ đã sửa ở đây (lớp 1-4 trước đây thiếu câu vì chúng):
 *  - Vòng lặp cũ chọn dạng theo số câu đã sinh được (first.size / hard.size),
 *    nên khi một dạng đã cạn (ví dụ dạng có câu hỏi cố định) thì số câu
 *    không tăng, lần thử sau lại chọn đúng dạng đó, lặp hết 20000 lần rồi
 *    trả về thiếu câu, các dạng còn lại không bao giờ được thử. Nay chọn
 *    dạng theo số lần thử (attempts), dạng cạn không chặn được dạng khác.
 *  - Khóa trùng cũ chỉ là nội dung câu hỏi (q.text). Các dạng COMP_MAX,
 *    COMP_MIN, EVEN_ODD, DEC_CMP có câu hỏi cố định, không chứa số, nên
 *    mỗi dạng chỉ ra được đúng 1 câu trong cả đề. Nay khóa trùng là
 *    text + đáp án đã sắp xếp, nên cùng một câu hỏi nhưng bộ đáp án khác
 *    nhau vẫn được tính là câu khác nhau.
 *  - Vòng bù cuối dùng các dạng số học vô hạn (ADD/SUB, lớp lớn thêm
 *    MUL/DIV) để bảo đảm không bao giờ trả về dưới count câu. */
private fun generateSet(grade: Int, week: Int, forms: List<String>, count: Int = 40): List<Question> {
    val random = newRandom()
    val ctx = Ctx(grade, week, random)
    val safeForms = if (forms.isEmpty()) listOf("ADD", "SUB") else forms
    val seen = HashSet<String>()
    fun key(q: Question): String = q.text + "|" + q.options.sorted().joinToString(",")
    fun valid(q: Question): Boolean {
        if (key(q) in seen) return false
        if (q.options.size != 4 || q.options.distinct().size != 4) return false
        if (q.correctAnswer.isBlank() || q.correctAnswer == "NaN" || q.options.any { it == "NaN" || it.isBlank() }) return false
        return true
    }
    fun accept(q: Question?, out: MutableList<Question>): Boolean {
        if (q == null || !valid(q)) return false
        seen += key(q)
        out += q
        return true
    }
    val hardCount = if (count >= 16) 8 else 0
    val firstTarget = count - hardCount
    val first = mutableListOf<Question>()
    var attempts = 0
    // Xoay vòng dạng theo số lần thử, không theo số câu đã sinh được,
    // để một dạng cạn không kẹt cả vòng lặp.
    val shuffledForms = safeForms.shuffled(random)
    while (first.size < firstTarget && attempts < 20000) {
        attempts++
        val form = shuffledForms[(attempts - 1) % shuffledForms.size]
        accept(generateOne(ctx, form), first)
    }
    val hard = mutableListOf<Question>()
    attempts = 0
    val shuffledHard = hardFormsForGrade(grade).shuffled(random)
    while (hard.size < hardCount && attempts < 10000) {
        attempts++
        val form = shuffledHard[(attempts - 1) % shuffledHard.size]
        accept(generateOne(ctx, form), hard)
    }
    // Bù đủ bằng dạng tuần nếu dạng khó sinh thiếu.
    attempts = 0
    while (first.size + hard.size < count && attempts < 20000) {
        attempts++
        val form = shuffledForms[(attempts - 1) % shuffledForms.size]
        accept(generateOne(ctx, form), hard)
    }
    // Bù cuối bằng mọi giá: các dạng số học vô hạn, số ngẫu nhiên theo lớp.
    // ADD/SUB có đủ biến thể cho lớp 1 (phạm vi cộng trong 100 trở lên);
    // lớp 3-4 thêm MUL/DIV để không cạn.
    val fallbackForms = if (grade >= 3) listOf("ADD", "SUB", "MUL", "DIV") else listOf("ADD", "SUB")
    attempts = 0
    while (first.size + hard.size < count && attempts < 100000) {
        attempts++
        val form = fallbackForms[(attempts - 1) % fallbackForms.size]
        accept(generateOne(ctx, form), hard)
    }
    return first.shuffled(random) + hard.shuffled(random)
}

fun generateWeeklySet(grade: Int, week: Int): List<Question> {
    val def = weekDef(grade, week)
    return generateSet(grade, week, def.forms)
}

fun generateExamSet(grade: Int, exam: ExamKind): List<Question> {
    // Đề kỳ dùng tuần giữa phạm vi làm tuần tham chiếu cho phạm vi số.
    val midWeek = exam.weeks.first + (exam.weeks.last - exam.weeks.first) / 2
    return generateSet(grade, midWeek, examForms(grade, exam))
}
