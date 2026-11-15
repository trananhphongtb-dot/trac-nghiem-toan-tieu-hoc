package vn.phong.baitaptuan.data

import vn.phong.baitaptuan.model.Question

/**
 * Các dạng bổ sung sau khi đối chiếu với phiếu bài tập cuối tuần phổ biến trên mạng
 * (Toán 3 Kết nối tri thức tuần 2, 25, 26, 30...):
 *  - FIND_X: tìm x, ví dụ 243 + x = 254 + 142, x - 207 = 312.
 *  - TIMES: gấp lên / giảm đi một số lần, gấp mấy lần, bằng một phần mấy.
 *  - DIGIT_EXT: số lớn nhất, nhỏ nhất có n chữ số (kể cả các chữ số khác nhau).
 *  - CALC_RIGHT: chọn phép tính đúng trong 4 phép tính.
 */

private fun nonNeg(list: List<String>): List<String> = list.filter { !it.startsWith("-") && it != "0" }

internal fun fFindX(ctx: Ctx): Question? {
    val g = ctx.grade
    val addHi = when { g <= 2 -> 99; g == 3 -> 900; g == 4 -> 90000; else -> 9000 }
    val mulMax = when { g <= 3 -> 9; g == 4 -> 12; else -> 25 }
    val variant = if (g <= 2) ctx.ri(0, 3) else ctx.ri(0, 8)
    val unknown = "x"
    return when (variant) {
        0 -> { // x + a = b
            val a = ctx.ri(2, addHi / 2); val x = ctx.ri(2, addHi / 2); val b = x + a
            mk(ctx, "FIND_X", "Tìm $unknown: $unknown + $a = $b", x.toString(),
                nonNeg(listOf((b + a).toString()) + numDistractors(x, ctx)),
                "$unknown = $b - $a = $x.")
        }
        1 -> { // a + x = b
            val a = ctx.ri(2, addHi / 2); val x = ctx.ri(2, addHi / 2); val b = x + a
            mk(ctx, "FIND_X", "Tìm $unknown: $a + $unknown = $b", x.toString(),
                nonNeg(listOf((b + a).toString()) + numDistractors(x, ctx)),
                "$unknown = $b - $a = $x.")
        }
        2 -> { // x - a = b
            val a = ctx.ri(2, addHi / 2); val b = ctx.ri(2, addHi / 2); val x = a + b
            mk(ctx, "FIND_X", "Tìm $unknown: $unknown - $a = $b", x.toString(),
                nonNeg(listOf((b - a).toString(), (x + 2 * a).toString()) + numDistractors(x, ctx)),
                "$unknown = $b + $a = $x (số bị trừ bằng hiệu cộng số trừ).")
        }
        3 -> { // a - x = b
            val b = ctx.ri(2, addHi / 2); val x = ctx.ri(2, addHi / 2); val a = x + b
            mk(ctx, "FIND_X", "Tìm $unknown: $a - $unknown = $b", x.toString(),
                nonNeg(listOf((a + b).toString()) + numDistractors(x, ctx)),
                "$unknown = $a - $b = $x (số trừ bằng số bị trừ trừ hiệu).")
        }
        4 -> { // x × a = b
            val a = ctx.ri(2, mulMax); val x = ctx.ri(2, mulMax); val b = x * a
            mk(ctx, "FIND_X", "Tìm $unknown: $unknown × $a = $b", x.toString(),
                nonNeg(listOf((b - a).toString(), (b * a).toString()) + numDistractors(x, ctx)),
                "$unknown = $b : $a = $x.")
        }
        5 -> { // a × x = b
            val a = ctx.ri(2, mulMax); val x = ctx.ri(2, mulMax); val b = x * a
            mk(ctx, "FIND_X", "Tìm $unknown: $a × $unknown = $b", x.toString(),
                nonNeg(listOf((b - a).toString(), (b * a).toString()) + numDistractors(x, ctx)),
                "$unknown = $b : $a = $x.")
        }
        6 -> { // x : a = b
            val a = ctx.ri(2, mulMax); val b = ctx.ri(2, mulMax); val x = a * b
            mk(ctx, "FIND_X", "Tìm $unknown: $unknown : $a = $b", x.toString(),
                nonNeg(listOf((b / a + 1).toString(), (b + a).toString()) + numDistractors(x, ctx)),
                "$unknown = $b × $a = $x (số bị chia bằng thương nhân số chia).")
        }
        7 -> { // a : x = b
            val b = ctx.ri(2, mulMax); val x = ctx.ri(2, mulMax); val a = x * b
            mk(ctx, "FIND_X", "Tìm $unknown: $a : $unknown = $b", x.toString(),
                nonNeg(listOf((a * b).toString(), (a - b).toString()) + numDistractors(x, ctx)),
                "$unknown = $a : $b = $x (số chia bằng số bị chia chia cho thương).")
        }
        else -> { // x + a = b + c  (đúng dạng trong phiếu bài tập tuần)
            val a = ctx.ri(2, addHi / 3); val b = ctx.ri(2, addHi / 2); val c = ctx.ri(2, addHi / 2)
            val x = b + c - a
            if (x <= 0) return null
            mk(ctx, "FIND_X", "Tìm $unknown: $unknown + $a = $b + $c", x.toString(),
                nonNeg(listOf((b + c + a).toString(), (b + c).toString()) + numDistractors(x, ctx)),
                "$b + $c = ${b + c}; $unknown = ${b + c} - $a = $x.")
        }
    }
}

internal fun fTimes(ctx: Ctx): Question? {
    val g = ctx.grade
    if (g < 3) return null
    val q = if (g == 3) ctx.ri(2, 12) else ctx.ri(2, 60)
    val n = ctx.ri(2, 9)
    return when (ctx.ri(0, 3)) {
        0 -> {
            val r = q * n
            mk(ctx, "TIMES", "Gấp $q lên $n lần được số nào?", r.toString(),
                nonNeg(listOf((q + n).toString(), (r + q).toString(), (r - q).toString()) + numDistractors(r, ctx)),
                "$q × $n = $r.")
        }
        1 -> {
            val r = q * n
            mk(ctx, "TIMES", "Giảm $r đi $n lần được số nào?", q.toString(),
                nonNeg(listOf((r - n).toString(), (q + 1).toString()) + numDistractors(q, ctx)),
                "$r : $n = $q.")
        }
        2 -> {
            val big = q * n
            mk(ctx, "TIMES", "Số $big gấp mấy lần số $q?", n.toString(),
                nonNeg(listOf((big - q).toString(), (n + 1).toString(), (n - 1).toString()) + numDistractors(n, ctx)),
                "$big : $q = $n nên $big gấp $q số $n lần.")
        }
        else -> {
            val big = q * n
            mk(ctx, "TIMES", "Số $q bằng một phần mấy của số $big?", "1/$n",
                listOf("1/${n + 1}", "1/${n + 2}", "1/${n + 3}", "1/${n + 4}"),
                "$big : $q = $n nên $q bằng 1/$n của $big.")
        }
    }
}

private fun pow10(k: Int): Int { var r = 1; repeat(k) { r *= 10 }; return r }

internal fun fDigitExt(ctx: Ctx): Question? {
    val g = ctx.grade
    if (g < 2) return null
    val k = when (g) { 2 -> ctx.ri(2, 3); 3 -> ctx.ri(3, 5); 4 -> ctx.ri(4, 7); else -> ctx.ri(5, 8) }
    return when (ctx.ri(0, 4)) {
        0 -> {
            val c = pow10(k) - 1
            mk(ctx, "DIGIT_EXT", "Số lớn nhất có $k chữ số là số nào?", c.toString(),
                listOf(pow10(k).toString(), (c - 1).toString(), pow10(k - 1).toString(), (c - 9).toString()),
                "Số lớn nhất có $k chữ số gồm $k chữ số 9: $c.")
        }
        1 -> {
            val c = pow10(k - 1)
            mk(ctx, "DIGIT_EXT", "Số nhỏ nhất có $k chữ số là số nào?", c.toString(),
                listOf((c - 1).toString(), (c + 1).toString(), pow10(k).toString(), (c / 10).toString()),
                "Số nhỏ nhất có $k chữ số là 1 và $k - 1 chữ số 0: $c.")
        }
        2 -> {
            val kk = minOf(k, 6)
            val c = "9876543210".take(kk)
            val d = c.toLong().toInt()
            mk(ctx, "DIGIT_EXT", "Số lớn nhất có $kk chữ số khác nhau là số nào?", c,
                listOf("9".repeat(kk), (d - 1).toString(), "9876543210".take(kk).reversed(), "8765432109".take(kk)),
                "Chọn các chữ số lớn nhất, khác nhau, xếp từ lớn đến bé: $c.")
        }
        3 -> {
            val kk = minOf(k, 6).coerceAtLeast(2)
            val c = "1023456789".take(kk)
            mk(ctx, "DIGIT_EXT", "Số nhỏ nhất có $kk chữ số khác nhau là số nào?", c,
                listOf("1" + "0".repeat(kk - 1), "1234567890".take(kk), "0123456789".take(kk).drop(1) + "0", "1".repeat(kk)),
                "Chữ số hàng cao nhất là 1, các hàng sau lần lượt 0, 2, 3, ...: $c.")
        }
        else -> {
            if (k < 3) return null
            val c = pow10(k) - 100
            mk(ctx, "DIGIT_EXT", "Số tròn trăm lớn nhất có $k chữ số là số nào?", c.toString(),
                listOf((pow10(k) - 1).toString(), (pow10(k) - 10).toString(), (pow10(k) - 1000).toString(), pow10(k).toString()),
                "Số tròn trăm lớn nhất có $k chữ số là $c.")
        }
    }
}

internal fun fCalcRight(ctx: Ctx): Question? {
    val g = ctx.grade
    val ops = if (g >= 3) 4 else 2
    val op = ctx.ri(0, ops - 1)
    val text: (Int, Int, Int) -> String
    val a: Int; val b: Int; val c: Int
    when (op) {
        0 -> { val p = addPair(ctx); a = p.first; b = p.second; c = a + b; text = { x, y, z -> "$x + $y = $z" } }
        1 -> { val p = addPair(ctx); a = maxOf(p.first, p.second); b = minOf(p.first, p.second); c = a - b; text = { x, y, z -> "$x - $y = $z" } }
        2 -> { val p = mulPair(ctx); a = p.first; b = p.second; c = a * b; text = { x, y, z -> "$x × $y = $z" } }
        else -> { val p = mulPair(ctx); b = p.second; c = p.first; a = b * c; text = { x, y, z -> "$x : $y = $z" } }
    }
    val right = text(a, b, c)
    val wrongs = listOf(c + 1, c - 1, c + 10, c - 10, c + 2).filter { it >= 0 && it != c }.distinct().take(3).map { text(a, b, it) }
    return mk(ctx, "CALC_RIGHT", "Phép tính nào sau đây đúng?", right, wrongs, "$right là phép tính đúng.")
}

/** Điều phối các dạng bổ sung. Trả null nếu không phải dạng ở file này. */
internal fun generateExtra(ctx: Ctx, form: String): Question? = when (form) {
    "FIND_X" -> fFindX(ctx)
    "TIMES" -> fTimes(ctx)
    "DIGIT_EXT" -> fDigitExt(ctx)
    "CALC_RIGHT" -> fCalcRight(ctx)
    else -> null
}
