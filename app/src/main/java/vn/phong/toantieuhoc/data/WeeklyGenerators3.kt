package vn.phong.toantieuhoc.data

import vn.phong.toantieuhoc.model.Question

// Phần 3: các dạng khó theo đề tuần thật (VietJack/HoaTieu/VnDoc/Thư Viện Đề Thi).
// - Bài 2-3 bước có bước trung gian không hỏi trực tiếp.
// - Bẫy chữ ngược, đổi đơn vị ngay trong bài, đọc bảng/biểu đồ, tìm ngược, hình ẩn.
// - Dùng chung mk() / fmt() / numDistractors() ở WeeklyGenerators.kt (cùng package,
//   internal nên dùng được). Không dùng mk2() vì nó là private của WeeklyGenerators2.kt.
// - Mọi dãy có bước nhảy đều dùng (a..b step n).toList().random(random).

private fun fmtTime(totalMinutes: Int): String {
    val h = totalMinutes / 60
    val m = totalMinutes % 60
    return if (m == 0) "$h giờ" else "$h giờ $m phút"
}

internal fun fHardHoney(ctx: Ctx): Question? {
    val sold2 = ctx.ri(80, 300)
    val diff = ctx.ri(20, 80)
    val sold1 = sold2 + diff
    val totalSold = sold1 + sold2
    val remain = ctx.ri(50, 200)
    val total = totalSold + remain
    return mk(
        ctx, "HARD_HONEY",
        "Một cửa hàng có $total lít mật ong. Tháng 1 bán được $sold1 lít, nhiều hơn tháng 2 là $diff lít. Hỏi cửa hàng còn lại bao nhiêu lít mật ong?",
        "$remain lít",
        listOf("$totalSold lít", "$sold2 lít", "${total - sold1} lít"),
        "Tháng 2 bán được $sold1 - $diff = $sold2 lít. Cả hai tháng bán được $sold1 + $sold2 = $totalSold lít. Còn lại $total - $totalSold = $remain lít."
    )
}

internal fun fHardRice(ctx: Ctx): Question? {
    val k = ctx.ri(5, 30)
    val total = 16 * k
    val first = 8 * k
    val second = 5 * k
    val final = 3 * k
    return mk(
        ctx, "HARD_RICE",
        "Một cửa hàng có $total kg gạo. Buổi sáng bán 1/2 số gạo, buổi chiều bán 5/8 số gạo còn lại. Hỏi cửa hàng còn lại bao nhiêu ki-lô-gam gạo?",
        "$final kg",
        listOf("$second kg", "$first kg", "${final + 10} kg"),
        "Buổi sáng bán $total : 2 = $first kg, còn lại $first kg. Buổi chiều bán $first × 5 : 8 = $second kg. Còn lại $first - $second = $final kg."
    )
}

internal fun fHardReverseMore(ctx: Ctx): Question? {
    val a = if (ctx.grade <= 2) ctx.ri(20, 90) else ctx.ri(100, 900)
    val diff = if (ctx.grade <= 2) ctx.ri(5, 20) else ctx.ri(20, 150)
    if (diff >= a) return null
    val b = a - diff
    return mk(
        ctx, "HARD_REVERSE_MORE",
        "Thùng to có $a lít nước, nhiều hơn thùng bé $diff lít. Hỏi thùng bé có bao nhiêu lít nước?",
        "$b lít",
        listOf("${a + diff} lít", "$a lít", "${b + 10} lít"),
        "Thùng to nhiều hơn thùng bé, nên thùng bé có $a - $diff = $b lít (không phải cộng thêm)."
    )
}

internal fun fHardReverseLess(ctx: Ctx): Question? {
    val a = if (ctx.grade <= 2) ctx.ri(20, 90) else ctx.ri(100, 900)
    val diff = if (ctx.grade <= 2) ctx.ri(5, 20) else ctx.ri(20, 150)
    val b = a + diff
    return mk(
        ctx, "HARD_REVERSE_LESS",
        "An có $a quyển vở, ít hơn Bình $diff quyển. Hỏi Bình có bao nhiêu quyển vở?",
        "$b quyển vở",
        listOf("${a - diff} quyển vở", "$a quyển vở", "${b + 10} quyển vở"),
        "An ít hơn Bình, nên Bình có $a + $diff = $b quyển vở (không phải trừ đi)."
    )
}

internal fun fHardConvMixed(ctx: Ctx): Question? {
    return when (ctx.ri(0, 2)) {
        0 -> {
            val aCm = (10..90 step 10).toList().random(ctx.random)
            val bDm = ctx.ri(10, 50)
            val cDm = ctx.ri(10, 50)
            val totalDm = aCm / 10 + bDm + cDm
            mk(
                ctx, "HARD_CONV_MIXED",
                "Một đường gấp khúc gồm 3 đoạn: đoạn thứ nhất dài ${aCm}cm, đoạn thứ hai dài ${bDm}dm, đoạn thứ ba dài ${cDm}dm. Hỏi đường gấp khúc dài bao nhiêu đề-xi-mét?",
                "$totalDm dm",
                listOf("${aCm + bDm + cDm} dm", "${bDm + cDm} dm", "${totalDm + 10} dm"),
                "Đổi ${aCm}cm = ${aCm / 10}dm, rồi cộng: ${aCm / 10} + $bDm + $cDm = $totalDm dm."
            )
        }
        1 -> {
            val aKg = ctx.ri(2, 9)
            val bG = ctx.ri(100, 900)
            val cKg = ctx.ri(2, 9)
            val totalG = (aKg + cKg) * 1000 + bG
            mk(
                ctx, "HARD_CONV_MIXED",
                "Một bao gạo nặng ${aKg}kg, một túi đường nặng ${bG}g, một bao bột nặng ${cKg}kg. Hỏi tất cả nặng bao nhiêu gam?",
                "$totalG g",
                listOf("${(aKg + cKg) * 100 + bG} g", "${aKg + cKg + bG} g", "${totalG + 1000} g"),
                "Đổi ${aKg}kg và ${cKg}kg ra gam rồi cộng với ${bG}g: ${(aKg + cKg) * 1000} + $bG = $totalG g."
            )
        }
        else -> {
            val aM2 = ctx.ri(1, 9)
            val bCm2 = ctx.ri(10, 99)
            val totalCm2 = aM2 * 10000 + bCm2
            mk(
                ctx, "HARD_CONV_MIXED",
                "Một mảnh giấy có diện tích ${aM2}m² và một mảnh nhỏ có diện tích ${bCm2}cm². Hỏi tổng diện tích là bao nhiêu xăng-ti-mét vuông?",
                "$totalCm2 cm²",
                listOf("${aM2 * 100 + bCm2} cm²", "${aM2 + bCm2} cm²", "${totalCm2 + 100} cm²"),
                "Đổi ${aM2}m² = ${aM2 * 10000}cm², rồi cộng thêm ${bCm2}cm² được $totalCm2 cm²."
            )
        }
    }
}

internal fun fHardTableSum(ctx: Ctx): Question? {
    // Lớp 1 giữ trong phạm vi 100, lớp 2-3 dùng 100-999, lớp 4-5 dùng 1000-9999.
    val range = when (ctx.grade) {
        1 -> 10..99
        in 2..3 -> 100..999
        else -> 1000..9999
    }
    val a = ctx.ri(range.first, range.last)
    val b = ctx.ri(range.first, range.last)
    val c = ctx.ri(range.first, range.last)
    val total = a + b + c
    return mk(
        ctx, "HARD_TABLE_SUM",
        "Bảng số liệu cho biết số sách của thư viện: sách giáo khoa: $a quyển, sách tham khảo: $b quyển, truyện: $c quyển. Hỏi thư viện có tất cả bao nhiêu quyển sách?",
        "$total quyển",
        listOf("${a + b} quyển", "${b + c} quyển", "${total + 100} quyển"),
        "Cộng cả ba loại: $a + $b + $c = $total quyển."
    )
}

internal fun fHardPie(ctx: Ctx): Question? {
    val missing = (10..40 step 5).toList().random(ctx.random)
    val rem = 100 - missing
    var p1 = 0
    var p2 = 0
    var p3 = 0
    var ok = false
    var tries = 0
    while (!ok && tries < 100) {
        tries++
        p1 = (10..(rem - 20) step 5).toList().random(ctx.random)
        p2 = (10..(rem - p1 - 10) step 5).toList().random(ctx.random)
        p3 = rem - p1 - p2
        ok = p3 >= 10 && p3 % 5 == 0
    }
    if (!ok) return null
    val total = (120..300 step 20).toList().random(ctx.random)
    val count = total * missing / 100
    return mk(
        ctx, "HARD_PIE",
        "Biểu đồ hình quạt cho biết tỉ lệ học sinh thích các môn thể thao: bóng đá $p1%, cầu lông $p2%, bơi $p3%, còn lại thích chạy bộ. Trường có $total học sinh. Hỏi có bao nhiêu học sinh thích chạy bộ?",
        "$count học sinh",
        listOf("${total * p1 / 100} học sinh", "${total * p2 / 100} học sinh", "${count + 10} học sinh"),
        "Tỉ lệ thích chạy bộ là 100 - $p1 - $p2 - $p3 = $missing%. Số học sinh thích chạy bộ là $total × $missing : 100 = $count học sinh."
    )
}

internal fun fHardFindX(ctx: Ctx): Question? {
    return if (ctx.random.nextBoolean()) {
        val a = ctx.ri(2, 9)
        val x = ctx.ri(2, 12)
        val b = ctx.ri(2, 30)
        val c = a * x + b
        mk(
            ctx, "HARD_FINDX",
            "Tìm x, biết: x × $a + $b = $c",
            x.toString(),
            numDistractors(x, ctx),
            "x × $a = $c - $b = ${a * x}, nên x = ${a * x} : $a = $x."
        )
    } else {
        val a = ctx.ri(2, 9)
        val b = ctx.ri(2, 20)
        val c = ctx.ri(2, 12)
        val y = (c + b) * a
        mk(
            ctx, "HARD_FINDX",
            "Tìm số chưa biết, biết: ? : $a - $b = $c",
            y.toString(),
            numDistractors(y, ctx),
            "? : $a = $c + $b = ${c + b}, nên số cần tìm là ${c + b} × $a = $y."
        )
    }
}

internal fun fHardHiddenRect(ctx: Ctx): Question? {
    val side = ctx.ri(3, 9)
    val length = ctx.ri(5, 20)
    val area = side * length
    val perimSq = side * 4
    val perimRect = 2 * (side + length)
    return mk(
        ctx, "HARD_HIDDEN_RECT",
        "Một hình chữ nhật có diện tích ${area}cm², chiều rộng bằng cạnh của một hình vuông có chu vi ${perimSq}cm. Hỏi chu vi hình chữ nhật là bao nhiêu?",
        "$perimRect cm",
        listOf("$area cm", "${perimRect + 2} cm", "${2 * length} cm"),
        "Cạnh hình vuông là $perimSq : 4 = $side cm, đó là chiều rộng. Chiều dài là $area : $side = $length cm. Chu vi là ($length + $side) × 2 = $perimRect cm."
    )
}

internal fun fHardAnnulus(ctx: Ctx): Question? {
    val rOut = ctx.ri(5, 10)
    val rIn = ctx.ri(2, rOut - 1)
    val outer = rOut * rOut * 3.14
    val inner = rIn * rIn * 3.14
    val annulus = outer - inner
    return mk(
        ctx, "HARD_ANNULUS",
        "Một hình vành khăn có bán kính ngoài ${rOut}cm, bán kính trong ${rIn}cm. Diện tích phần vành khăn là bao nhiêu? (lấy π = 3,14)",
        "${fmt(annulus)} cm²",
        listOf("${fmt(outer)} cm²", "${fmt(inner)} cm²", "${fmt(annulus + 3.14)} cm²"),
        "Diện tích hình tròn ngoài là ${fmt(outer)}cm², hình tròn trong là ${fmt(inner)}cm², phần vành khăn là ${fmt(outer)} - ${fmt(inner)} = ${fmt(annulus)}cm²."
    )
}

internal fun fHardAvgReverse(ctx: Ctx): Question? {
    val avg = ctx.ri(10, 50)
    val a = ctx.ri(5, 90)
    val other = 2 * avg - a
    if (other <= 0 || other == a) return null
    return mk(
        ctx, "HARD_AVG_REVERSE",
        "Trung bình cộng của hai số là $avg. Một số là $a. Hỏi số còn lại là số nào?",
        other.toString(),
        numDistractors(other, ctx),
        "Tổng hai số là $avg × 2 = ${2 * avg}, số còn lại là ${2 * avg} - $a = $other."
    )
}

internal fun fHardWork(ctx: Ctx): Question? {
    val pairs = listOf(
        Triple(2, 3, 72), Triple(3, 6, 120), Triple(4, 12, 180),
        Triple(5, 10, 200), Triple(2, 6, 90), Triple(4, 4, 120)
    )
    val (a, b, minutes) = pairs.random(ctx.random)
    return mk(
        ctx, "HARD_WORK",
        "Anh A làm xong một công việc trong $a giờ, anh B làm xong công việc đó trong $b giờ. Hỏi nếu cả hai cùng làm thì sau bao nhiêu phút sẽ xong công việc?",
        "$minutes phút",
        listOf("${minutes + 30} phút", "${if (minutes > 30) minutes - 30 else minutes + 60} phút", "${a * b} phút"),
        "Trong 1 giờ, cả hai cùng làm được 1/$a + 1/$b = ${a + b}/${a * b} công việc, nên thời gian xong là ${a * b} : ${a + b} giờ = $minutes phút."
    )
}

internal fun fHardCano(ctx: Ctx): Question? {
    val pairs = listOf(
        Triple(32, 48, 192), Triple(20, 30, 120), Triple(24, 40, 120),
        Triple(30, 60, 120), Triple(15, 20, 120), Triple(40, 60, 240)
    )
    val (down, up, leaf) = pairs.random(ctx.random)
    return mk(
        ctx, "HARD_CANO",
        "Một ca nô đi xuôi dòng mất $down phút, đi ngược dòng mất $up phút trên cùng một quãng sông. Hỏi một chiếc lá trôi theo dòng nước trên quãng sông đó mất bao nhiêu phút?",
        "$leaf phút",
        listOf("${leaf / 2} phút", "${leaf + 60} phút", "${up - down} phút"),
        "Thời gian lá trôi là 2 × $down × $up : ($up - $down) = $leaf phút."
    )
}

internal fun fHardSumDiffMiddle(ctx: Ctx): Question? {
    val small = ctx.ri(100, 500)
    val k = ctx.ri(4, 24)
    val big = small + k + 1
    val sum = small + big
    return mk(
        ctx, "HARD_SUMDIFF_MIDDLE",
        "Hai số có tổng là $sum, giữa hai số đó có $k số tự nhiên. Hỏi số lớn là số nào?",
        big.toString(),
        numDistractors(big, ctx),
        "Hiệu hai số là $k + 1 = ${k + 1} (vì giữa chúng có $k số). Số lớn là ($sum + ${k + 1}) : 2 = $big."
    )
}

internal fun fHardDecRemainder(ctx: Ctx): Question? {
    val d = ctx.ri(11, 25)
    val qCents = ctx.ri(150, 400)
    val r = ctx.ri(1, d - 1)
    val dividendCents = d * qCents + r
    val dividend = dividendCents / 100.0
    val quotient = qCents / 100.0
    val remainder = r / 100.0
    return mk(
        ctx, "HARD_DEC_REMAINDER",
        "Phép chia ${fmt(dividend)} : $d, thương lấy đến 2 chữ số thập phân. Hỏi số dư là bao nhiêu?",
        fmt(remainder),
        listOf(fmt(r.toDouble()), fmt(r / 10.0), fmt(remainder + 0.01)),
        "${fmt(dividend)} : $d = ${fmt(quotient)} dư ${fmt(remainder)} vì $d × ${fmt(quotient)} + ${fmt(remainder)} = ${fmt(dividend)}."
    )
}

internal fun fHardWord2Reverse(ctx: Ctx): Question? {
    val name = pickName(ctx)
    val obj = pickObj(ctx)
    val a = ctx.ri(20, 80)
    val b = ctx.ri(5, a - 1)
    val c = ctx.ri(5, 30)
    val final = a - b + c
    return mk(
        ctx, "HARD_WORD2_REVERSE",
        "$name có $a $obj, cho bạn $b $obj, rồi mẹ cho thêm $c $obj. Hỏi $name có tất cả bao nhiêu $obj?",
        "$final $obj",
        listOf("${a - b} $obj", "${a + c} $obj", "${final + 5} $obj"),
        "Sau khi cho bạn còn $a - $b = ${a - b} $obj, mẹ cho thêm $c $obj nên có ${a - b} + $c = $final $obj."
    )
}

internal fun fHardTimeBackward(ctx: Ctx): Question? {
    val s1 = (10..30 step 10).toList().random(ctx.random)
    val s2 = (10..30 step 10).toList().random(ctx.random)
    val s3 = (10..30 step 10).toList().random(ctx.random)
    val totalD = s1 + s2 + s3
    val endH = ctx.ri(11, 13)
    val endM = (0..30 step 30).toList().random(ctx.random)
    val endTotal = endH * 60 + endM
    val startTotal = endTotal - totalD
    if (startTotal < 7 * 60) return null
    val endStr = fmtTime(endTotal)
    val startStr = fmtTime(startTotal)
    return mk(
        ctx, "HARD_TIME_BACKWARD",
        "Sau khi về nhà, An nghỉ $s1 phút, xem ti vi $s2 phút, ăn trưa $s3 phút thì xong lúc $endStr. Hỏi An về nhà lúc mấy giờ?",
        startStr,
        listOf(fmtTime(startTotal + 10), fmtTime(startTotal - 10), endStr),
        "Tổng thời gian là $s1 + $s2 + $s3 = $totalD phút, nên về nhà lúc $endStr trừ đi $totalD phút là $startStr."
    )
}

internal fun fHardPercentReverse(ctx: Ctx): Question? {
    val total = (100..5000 step 100).toList().random(ctx.random)
    val p = (10..90 step 2).toList().random(ctx.random)
    val value = total * p / 100
    val perOne = value / p
    return mk(
        ctx, "HARD_PERCENT_REVERSE",
        "$p% số gạo của cửa hàng là $value kg. Hỏi cửa hàng có tất cả bao nhiêu ki-lô-gam gạo?",
        "$total kg",
        listOf("$value kg", "${total + 100} kg", "${total - 100} kg"),
        "1% là $value : $p = $perOne kg, 100% là $perOne × 100 = $total kg."
    )
}

internal fun fHardVolumeTank(ctx: Ctx): Question? {
    val litres = (2000..4000 step 20).toList().random(ctx.random)
    val m3 = litres / 1000.0
    val need = litres * 11 / 20
    return mk(
        ctx, "HARD_VOLUME_TANK",
        "Một bể nước có thể tích ${fmt(m3)}m³, lúc đầu bể chứa 1/4 bể nước. Người ta bơm thêm nước cho đến khi bể chứa 4/5 bể. Hỏi đã bơm thêm bao nhiêu lít nước? (1m³ = 1000 lít)",
        "$need lít",
        listOf("${litres / 4} lít", "${litres * 4 / 5} lít", "${need + 100} lít"),
        "Bể chứa được $litres lít. Cần bơm thêm $litres × (4/5 - 1/4) = $litres × 11/20 = $need lít."
    )
}

internal fun fHardTimeChain(ctx: Ctx): Question? {
    val startH = ctx.ri(7, 15)
    val startM = (0..50 step 10).toList().random(ctx.random)
    val d1 = (10..30 step 10).toList().random(ctx.random)
    val d2 = (10..30 step 10).toList().random(ctx.random)
    val d3 = (10..30 step 10).toList().random(ctx.random)
    val startTotal = startH * 60 + startM
    val endTotal = startTotal + d1 + d2 + d3
    val startStr = fmtTime(startTotal)
    val endStr = fmtTime(endTotal)
    return mk(
        ctx, "HARD_TIME_CHAIN",
        "An bắt đầu học lúc $startStr, học $d1 phút, nghỉ $d2 phút, rồi học tiếp $d3 phút. Hỏi An học xong lúc mấy giờ?",
        endStr,
        listOf(fmtTime(endTotal + 10), fmtTime(endTotal - 10), startStr),
        "Tổng thời gian là $d1 + $d2 + $d3 = ${d1 + d2 + d3} phút, cộng vào $startStr được $endStr."
    )
}

// ---------- Dạng mới cho lớp 1-4 (bổ sung 2026-11-04) ----------
// Quy tắc chung: mọi dạng đều đưa tham số (số) vào trong câu hỏi, nên
// không bị cạn như các dạng câu hỏi cố định (COMP_MAX, EVEN_ODD...).
// Tất cả dùng mk() ở WeeklyGenerators.kt, không dùng mk2() (private của
// WeeklyGenerators2.kt). Dãy có bước nhảy dùng (a..b step n).toList().random.

private fun newNumRange(ctx: Ctx): IntRange {
    return when (ctx.grade) {
        1 -> 2..99
        2 -> 10..999
        3 -> 100..9999
        else -> 1000..99999
    }
}

internal fun fCompListMax(ctx: Ctx): Question? {
    val range = newNumRange(ctx)
    val vals = mutableSetOf<Int>()
    while (vals.size < 4) vals += ctx.ri(range.first, range.last)
    val list = vals.toList()
    val max = list.maxOrNull() ?: return null
    return mk(
        ctx, "COMP_LIST_MAX",
        "Trong các số ${list.joinToString(", ")}, số nào lớn nhất?",
        max.toString(),
        list.filter { it != max }.map { it.toString() },
        "So sánh các số ${list.joinToString(", ")}, số lớn nhất là $max."
    )
}

internal fun fCompListMin(ctx: Ctx): Question? {
    val range = newNumRange(ctx)
    val vals = mutableSetOf<Int>()
    while (vals.size < 4) vals += ctx.ri(range.first, range.last)
    val list = vals.toList()
    val min = list.minOrNull() ?: return null
    return mk(
        ctx, "COMP_LIST_MIN",
        "Trong các số ${list.joinToString(", ")}, số nào bé nhất?",
        min.toString(),
        list.filter { it != min }.map { it.toString() },
        "So sánh các số ${list.joinToString(", ")}, số bé nhất là $min."
    )
}

internal fun fEvenOddList(ctx: Ctx): Question? {
    val range = if (ctx.grade <= 2) 2..99 else 10..999
    val wantEven = ctx.random.nextBoolean()
    val correctSet = mutableSetOf<Int>()
    while (correctSet.size < 1) {
        val v = ctx.ri(range.first, range.last)
        if ((v % 2 == 0) == wantEven) correctSet += v
    }
    val correct = correctSet.first()
    val others = mutableSetOf<Int>()
    while (others.size < 3) {
        val v = ctx.ri(range.first, range.last)
        if ((v % 2 == 0) != wantEven && v != correct) others += v
    }
    val list = (others + correct).shuffled(ctx.random)
    return mk(
        ctx, "EVENODD_LIST",
        if (wantEven) "Trong các số ${list.joinToString(", ")}, số nào là số chẵn?"
        else "Trong các số ${list.joinToString(", ")}, số nào là số lẻ?",
        correct.toString(),
        others.map { it.toString() },
        if (wantEven) "$correct chia hết cho 2 nên là số chẵn duy nhất trong các số đã cho."
        else "$correct không chia hết cho 2 nên là số lẻ duy nhất trong các số đã cho."
    )
}

internal fun fPlaceTensUnits(ctx: Ctx): Question? {
    return if (ctx.grade >= 3) {
        val h = ctx.ri(1, 9); val t = ctx.ri(0, 9); val u = ctx.ri(0, 9)
        val n = h * 100 + t * 10 + u
        mk(
            ctx, "PLACE_TENS_UNITS",
            "Số gồm $h trăm, $t chục và $u đơn vị là số nào?",
            n.toString(),
            numDistractors(n, ctx),
            "$h trăm là ${h * 100}, $t chục là ${t * 10}, $u đơn vị là $u, nên số đó là $n."
        )
    } else {
        val t = ctx.ri(1, 9); val u = ctx.ri(0, 9)
        val n = t * 10 + u
        mk(
            ctx, "PLACE_TENS_UNITS",
            "Số gồm $t chục và $u đơn vị là số nào?",
            n.toString(),
            numDistractors(n, ctx),
            "$t chục là ${t * 10}, $u đơn vị là $u, nên số đó là $n."
        )
    }
}

internal fun fGroupCount(ctx: Ctx): Question? {
    val a = if (ctx.grade <= 2) ctx.ri(2, 9) else ctx.ri(3, 12)
    val b = if (ctx.grade <= 2) ctx.ri(2, 9) else ctx.ri(3, 12)
    val total = a * b
    return mk(
        ctx, "GROUP_COUNT",
        "Có $a nhóm, mỗi nhóm có $b chấm. Hỏi tất cả có bao nhiêu chấm?",
        total.toString(),
        numDistractors(total, ctx),
        "Mỗi nhóm có $b chấm, $a nhóm có $a × $b = $total chấm."
    )
}

internal fun fExprBiggest(ctx: Ctx): Question? {
    val a = ctx.ri(2, 50); val b = ctx.ri(2, 50)
    val c = ctx.ri(2, 12); val d = ctx.ri(2, 12)
    val e = ctx.ri(20, 99); val f = ctx.ri(2, e - 1)
    val exprs = listOf("$a + $b" to a + b, "$c × $d" to c * d, "$e - $f" to e - f)
    val results = exprs.map { it.second }
    if (results.distinct().size != 3) return null
    val biggest = exprs.maxByOrNull { it.second } ?: return null
    return mk(
        ctx, "EXPR_BIGGEST",
        "Phép tính nào có kết quả lớn nhất: ${exprs[0].first}, ${exprs[1].first} hay ${exprs[2].first}?",
        biggest.first,
        exprs.filter { it != biggest }.map { it.first },
        "${exprs[0].first} = ${exprs[0].second}, ${exprs[1].first} = ${exprs[1].second}, ${exprs[2].first} = ${exprs[2].second}, nên ${biggest.first} lớn nhất."
    )
}

internal fun fMoneyChange(ctx: Ctx): Question? {
    return if (ctx.grade >= 3) {
        val step = 1000
        val price = (5..40 step 5).toList().random(ctx.random) * step
        val change = (5..20 step 5).toList().random(ctx.random) * step
        val pay = price + change
        mk(
            ctx, "MONEY_CHANGE",
            "Lan đưa $pay đồng để mua một món đồ giá $price đồng. Hỏi Lan được trả lại bao nhiêu đồng?",
            "$change đồng",
            listOf("${change + step} đồng", "$price đồng", "$pay đồng"),
            "$pay - $price = $change, nên Lan được trả lại $change đồng."
        )
    } else {
        val pay = if (ctx.grade == 1) 50 else 100
        val price = ctx.ri(10, pay - 5)
        val change = pay - price
        mk(
            ctx, "MONEY_CHANGE",
            "Lan đưa $pay đồng để mua một món đồ giá $price đồng. Hỏi Lan được trả lại bao nhiêu đồng?",
            "$change đồng",
            listOf("${change + 5} đồng", "$price đồng", "$pay đồng"),
            "$pay - $price = $change, nên Lan được trả lại $change đồng."
        )
    }
}

internal fun fUnitConvSimple(ctx: Ctx): Question? {
    // Triple: đơn vị đầu, đơn vị cuối, hệ số đổi.
    val conv = when (ctx.grade) {
        1 -> listOf(Triple("dm", "cm", 10), Triple("m", "dm", 10))
        2 -> listOf(Triple("m", "cm", 100), Triple("dm", "cm", 10), Triple("m", "dm", 10))
        3 -> listOf(Triple("km", "m", 1000), Triple("m", "cm", 100), Triple("kg", "g", 1000), Triple("l", "ml", 1000))
        else -> listOf(Triple("tấn", "kg", 1000), Triple("m²", "dm²", 100), Triple("giờ", "phút", 60), Triple("km", "m", 1000))
    }.random(ctx.random)
    val v = ctx.ri(2, 9)
    val correct = v * conv.third
    return mk(
        ctx, "UNIT_CONV_SIMPLE",
        "${v}${conv.first} = ? ${conv.second}",
        "$correct ${conv.second}",
        listOf("$v ${conv.second}", "${correct + conv.third} ${conv.second}", "${correct - conv.third} ${conv.second}"),
        "1${conv.first} = ${conv.third}${conv.second}, nên ${v}${conv.first} = $v × ${conv.third} = $correct ${conv.second}."
    )
}

internal fun fWordGroup(ctx: Ctx): Question? {
    return if (ctx.random.nextBoolean()) {
        val b = if (ctx.grade == 1) ctx.ri(2, 3) else if (ctx.grade == 2) ctx.ri(2, 5) else ctx.ri(2, 9)
        val q = if (ctx.grade == 1) ctx.ri(2, 5) else if (ctx.grade == 2) ctx.ri(2, 9) else ctx.ri(3, 12)
        val total = b * q
        val obj = pickObj(ctx)
        mk(
            ctx, "WORD_GROUP",
            "Có $total $obj chia đều cho $b bạn. Hỏi mỗi bạn được bao nhiêu $obj?",
            "$q $obj",
            listOf("${q + 1} $obj", "${q - 1} $obj", "$total $obj"),
            "$total : $b = $q, nên mỗi bạn được $q $obj."
        )
    } else {
        val a = if (ctx.grade == 1) ctx.ri(2, 10) else if (ctx.grade == 2) ctx.ri(2, 20) else ctx.ri(5, 50)
        val times = if (ctx.grade == 1) ctx.ri(2, 3) else ctx.ri(2, 5)
        val total = a * times
        val name = pickName(ctx)
        mk(
            ctx, "WORD_GROUP",
            "An có $a quyển vở, $name có số vở gấp $times lần số vở của An. Hỏi $name có bao nhiêu quyển vở?",
            "$total quyển vở",
            numDistractors(total, ctx).map { "$it quyển vở" },
            "$a × $times = $total, nên $name có $total quyển vở."
        )
    }
}

internal fun fPlaceDigitValue(ctx: Ctx): Question? {
    // Chọn hàng (10, 100, 1000, 10000) theo lớp, dựng số sao cho chữ số ở hàng đó khác 0.
    val place = when (ctx.grade) {
        1 -> 10
        2 -> if (ctx.random.nextBoolean()) 10 else 100
        3 -> if (ctx.random.nextBoolean()) 100 else 1000
        else -> if (ctx.random.nextBoolean()) 1000 else 10000
    }
    val placeName = when (place) {
        10 -> "chục"
        100 -> "trăm"
        1000 -> "nghìn"
        else -> "chục nghìn"
    }
    val d = ctx.ri(1, 9)
    val low = ctx.ri(0, place - 1)
    // Giữ số trong phạm vi lớp: lớp 1 chỉ tới 99, lớp 2 chỉ tới 999.
    val high = when {
        ctx.grade == 1 -> 0
        ctx.grade == 2 && place == 100 -> 0
        place == 10 -> ctx.ri(0, 8)
        else -> ctx.ri(0, 9)
    }
    val n = high * place * 10 + d * place + low
    val value = d * place
    return mk(
        ctx, "PLACE_DIGIT_VALUE",
        "Trong số $n, chữ số $d ở hàng $placeName có giá trị là bao nhiêu?",
        value.toString(),
        listOf(d.toString(), "${value * 10}", "${value + place}"),
        "Chữ số $d ở hàng $placeName có giá trị là $d × $place = $value."
    )
}

internal fun fAddSubChain(ctx: Ctx): Question? {
    val a = if (ctx.grade == 1) ctx.ri(2, 20) else ctx.ri(10, 90)
    val b = if (ctx.grade == 1) ctx.ri(2, 20) else ctx.ri(10, 90)
    // Lớp 1 dùng số trừ nhỏ để kết quả không âm (a + b nhỏ nhất là 4).
    val c = if (ctx.grade == 1) ctx.ri(2, a + b - 1) else ctx.ri(5, a + b - 1)
    val result = a + b - c
    return mk(
        ctx, "ADD_SUB_CHAIN",
        "$a + $b - $c = ?",
        result.toString(),
        numDistractors(result, ctx),
        "$a + $b = ${a + b}, rồi trừ $c được ${a + b} - $c = $result."
    )
}

internal fun fTriplePart(ctx: Ctx): Question? {
    return if (ctx.random.nextBoolean()) {
        val a = if (ctx.grade <= 2) ctx.ri(2, 30) else ctx.ri(5, 60)
        mk(
            ctx, "TRIPLE_PART",
            "Gấp 3 lần số $a là số nào?",
            (a * 3).toString(),
            numDistractors(a * 3, ctx),
            "Gấp 3 lần số $a là $a × 3 = ${a * 3}."
        )
    } else {
        val q = if (ctx.grade <= 2) ctx.ri(2, 20) else ctx.ri(3, 30)
        val a = q * 3
        mk(
            ctx, "TRIPLE_PART",
            "Một phần ba của số $a là số nào?",
            q.toString(),
            numDistractors(q, ctx),
            "Một phần ba của $a là $a : 3 = $q."
        )
    }
}

/** Dạng khó dùng cho 8 câu cuối, theo lớp. Lớp 1-2 chỉ dùng dạng 2 bước
 *  đơn giản và bẫy ngược nhẹ; lớp 3-5 dùng đầy đủ dạng khó của lớp đó. */
internal fun hardFormsForGrade(grade: Int): List<String> {
    return when (grade) {
        1, 2 -> listOf(
            "HARD_REVERSE_MORE", "HARD_REVERSE_LESS", "HARD_WORD2_REVERSE",
            "HARD_TABLE_SUM", "HARD_CONV_MIXED", "HARD_TIME_CHAIN"
        )
        3 -> listOf(
            "HARD_HONEY", "HARD_REVERSE_MORE", "HARD_REVERSE_LESS", "HARD_CONV_MIXED",
            "HARD_TABLE_SUM", "HARD_FINDX", "HARD_TIME_BACKWARD", "HARD_TIME_CHAIN",
            "HARD_HIDDEN_RECT", "HARD_WORD2_REVERSE"
        )
        4 -> listOf(
            "HARD_HONEY", "HARD_REVERSE_MORE", "HARD_CONV_MIXED", "HARD_TABLE_SUM",
            "HARD_FINDX", "HARD_HIDDEN_RECT", "HARD_AVG_REVERSE", "HARD_SUMDIFF_MIDDLE",
            "HARD_TIME_BACKWARD", "HARD_TIME_CHAIN"
        )
        else -> listOf(
            "HARD_HONEY", "HARD_RICE", "HARD_PIE", "HARD_FINDX", "HARD_HIDDEN_RECT",
            "HARD_ANNULUS", "HARD_AVG_REVERSE", "HARD_PERCENT_REVERSE", "HARD_VOLUME_TANK",
            "HARD_WORK", "HARD_CANO", "HARD_DEC_REMAINDER", "HARD_TIME_BACKWARD",
            "HARD_SUMDIFF_MIDDLE"
        )
    }
}
