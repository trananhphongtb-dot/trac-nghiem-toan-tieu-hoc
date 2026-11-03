package vn.phong.toantieuhoc.data

import vn.phong.toantieuhoc.model.Difficulty
import vn.phong.toantieuhoc.model.Question
import vn.phong.toantieuhoc.model.Topic
import kotlin.random.Random

// ---------------------------------------------------------------------------
// PHẦN 2: LỜI VĂN, PHÂN SỐ - THẬP PHÂN, NÂNG CAO MỚI, DẠNG LỚP ĐẦU
// ---------------------------------------------------------------------------

val realFormsPart2: List<FormDef> = listOf(
    // --- Lời văn: 12 dạng ---
    FormDef("W01", Topic.LOI_VAN, 1, 5) { grade, level, random ->
        val a = nWord(grade, random); val b = nWord(grade, random)
        val big = maxOf(a, b); val small = minOf(a, b)
        if (random.nextBoolean()) {
            makeQuestion(grade, Topic.LOI_VAN, level, "Lan có $big viên bi, Hùng có $small viên bi. Hỏi Lan có nhiều hơn Hùng bao nhiêu viên bi?",
                "${big - small} viên bi", listOf("${big - small + 1} viên bi", "${big + small} viên bi", "$small viên bi"),
                "Lan nhiều hơn Hùng $big - $small = ${big - small} viên bi.", random)
        } else {
            makeQuestion(grade, Topic.LOI_VAN, level, "Hùng có $small viên bi, Lan có nhiều hơn Hùng ${big - small} viên bi. Hỏi Lan có bao nhiêu viên bi?",
                "$big viên bi", listOf("${big + 1} viên bi", "${big - 1} viên bi", "$small viên bi"),
                "Lan có $small + ${big - small} = $big viên bi.", random)
        }
    },
    FormDef("W02", Topic.LOI_VAN, 3, 5) { grade, level, random ->
        val perBox = (3..12).random(random); val total = (20..99).random(random)
        val boxes = (total + perBox - 1) / perBox
        makeQuestion(grade, Topic.LOI_VAN, level, "Có $total chiếc bút, mỗi hộp đựng được $perBox chiếc. Hỏi cần ít nhất bao nhiêu hộp để đựng hết số bút đó?",
            "$boxes hộp", listOf("${boxes - 1} hộp", "${boxes + 1} hộp", "$total hộp"),
            "$total : $perBox = ${total / perBox} dư ${total % perBox}, nên cần thêm 1 hộp, tổng $boxes hộp.", random)
    },
    FormDef("W03", Topic.LOI_VAN, 2, 5) { grade, level, random ->
        val have = (50000..100000 step 10000).random(random); val price = (5000..20000 step 1000).random(random); val qty = (2..5).random(random)
        val cost = price * qty
        if (cost >= have) return@FormDef makeQuestion(grade, Topic.LOI_VAN, level, "Lan có $have đồng, mua 1 quyển vở giá $price đồng. Hỏi Lan còn lại bao nhiêu tiền?",
            "${have - price} đồng", listOf("${have - price + 1000} đồng", "$price đồng", "$have đồng"),
            "Lan còn lại $have - $price = ${have - price} đồng.", random)
        makeQuestion(grade, Topic.LOI_VAN, level, "Lan có $have đồng, mua $qty quyển vở, mỗi quyển giá $price đồng. Hỏi Lan còn lại bao nhiêu tiền?",
            "${have - cost} đồng", listOf("${have - cost + 1000} đồng", "$cost đồng", "$have đồng"),
            "Lan mua hết $price × $qty = $cost đồng, còn lại $have - $cost = ${have - cost} đồng.", random)
    },
    FormDef("W04", Topic.LOI_VAN, 4, 5) { grade, level, random ->
        val a = (10..50).random(random); val b = (10..50).random(random); val c = (10..50).random(random)
        val sum = a + b + c
        if (sum % 3 != 0) return@FormDef makeQuestion(grade, Topic.LOI_VAN, level, "Ba lớp có lần lượt $a, $b, $c học sinh. Hỏi trung bình mỗi lớp có bao nhiêu học sinh? (lấy phần nguyên)",
            "${sum / 3} học sinh", listOf("${sum / 3 + 1} học sinh", "$sum học sinh", "${(a + b) / 2} học sinh"),
            "Trung bình cộng là ($a + $b + $c) : 3 = $sum : 3 = ${sum / 3} học sinh (phần nguyên).", random)
        makeQuestion(grade, Topic.LOI_VAN, level, "Ba lớp có lần lượt $a, $b, $c học sinh. Hỏi trung bình mỗi lớp có bao nhiêu học sinh?",
            "${sum / 3} học sinh", listOf("${sum / 3 + 1} học sinh", "$sum học sinh", "${(a + b) / 2} học sinh"),
            "Trung bình cộng là ($a + $b + $c) : 3 = ${sum / 3} học sinh.", random)
    },
    FormDef("W05", Topic.LOI_VAN, 3, 5) { grade, level, random ->
        val a = (5..30).random(random); val b = (5..30).random(random)
        makeQuestion(grade, Topic.LOI_VAN, level, "Một khu vườn hình chữ nhật dài ${a}m, rộng ${b}m. Hỏi cần bao nhiêu mét rào để rào xung quanh khu vườn?",
            "${2 * (a + b)} m", listOf("${a * b} m", "${a + b} m", "${2 * (a + b) + 2} m"),
            "Chu vi khu vườn là ($a + $b) × 2 = ${2 * (a + b)} m.", random)
    },
    FormDef("W06", Topic.LOI_VAN, 4, 5) { grade, level, random ->
        val a = (5..30).random(random); val b = (5..30).random(random)
        makeQuestion(grade, Topic.LOI_VAN, level, "Một mảnh đất hình chữ nhật dài ${a}m, rộng ${b}m. Diện tích mảnh đất là bao nhiêu?",
            "${a * b} m²", listOf("${2 * (a + b)} m²", "${a * b + a} m²", "${a + b} m²"),
            "Diện tích là $a × $b = ${a * b} m².", random)
    },
    FormDef("W07", Topic.LOI_VAN, 5, 5) { grade, level, random ->
        val v = (10..60).random(random); val t = (2..5).random(random); val s = v * t
        when (random.nextInt(3)) {
            0 -> makeQuestion(grade, Topic.LOI_VAN, level, "Một ô tô đi với vận tốc $v km/giờ trong $t giờ. Hỏi ô tô đi được bao nhiêu km?",
                "$s km", listOf("${s + v} km", "${s - v} km", "$v km"),
                "Quãng đường là $v × $t = $s km.", random)
            1 -> makeQuestion(grade, Topic.LOI_VAN, level, "Một ô tô đi được $s km trong $t giờ. Hỏi vận tốc của ô tô là bao nhiêu?",
                "$v km/giờ", listOf("${v + 5} km/giờ", "${v - 5} km/giờ", "$s km/giờ"),
                "Vận tốc là $s : $t = $v km/giờ.", random)
            else -> makeQuestion(grade, Topic.LOI_VAN, level, "Một ô tô đi với vận tốc $v km/giờ, đi được $s km. Hỏi ô tô đi trong bao nhiêu giờ?",
                "$t giờ", listOf("${t + 1} giờ", "${t - 1} giờ", "$v giờ"),
                "Thời gian là $s : $v = $t giờ.", random)
        }
    },
    FormDef("W08", Topic.LOI_VAN, 5, 5) { grade, level, random ->
        val price = (10000..100000 step 10000).random(random); val p = listOf(10, 20, 25, 50).random(random)
        val discount = price * p / 100; val after = price - discount
        makeQuestion(grade, Topic.LOI_VAN, level, "Một món hàng giá $price đồng, được giảm giá $p%. Hỏi sau khi giảm, món hàng còn bao nhiêu tiền?",
            "$after đồng", listOf("${after + 1000} đồng", "$discount đồng", "$price đồng"),
            "Số tiền giảm là $price × $p : 100 = $discount đồng, còn lại $price - $discount = $after đồng.", random)
    },
    FormDef("W09", Topic.LOI_VAN, 4, 5) { grade, level, random ->
        val den = listOf(2, 3, 4, 5).random(random); val num = (1 until den).random(random); val q = (5..20).random(random)
        val total = den * q; val part = num * q
        makeQuestion(grade, Topic.LOI_VAN, level, "Có $total quả táo, lấy đi $num/$den số táo đó. Hỏi đã lấy đi bao nhiêu quả táo?",
            "$part quả", listOf("${part + num} quả", "$total quả", "$q quả"),
            "$num/$den của $total là $total : $den × $num = $part quả.", random)
    },
    FormDef("W10", Topic.LOI_VAN, 5, 5) { grade, level, random ->
        val a = (5..30).random(random); val b = (5..30).random(random)
        makeQuestion(grade, Topic.LOI_VAN, level, "Lớp có $a học sinh nam và $b học sinh nữ. Tỉ số của số học sinh nam và số học sinh nữ là bao nhiêu?",
            "$a/$b", listOf("$b/$a", "$a/${a + b}", "${a + b}/$a"),
            "Tỉ số của số học sinh nam và số học sinh nữ là $a : $b, viết là $a/$b.", random)
    },
    FormDef("W11", Topic.LOI_VAN, 1, 5) { grade, level, random ->
        val total = nWord(grade, random); val p1 = (1..total - 2).random(random); val p2 = (1..total - p1 - 1).random(random)
        val p3 = total - p1 - p2
        makeQuestion(grade, Topic.LOI_VAN, level, "Có tất cả $total viên bi, trong đó có $p1 viên bi đỏ và $p2 viên bi xanh, còn lại là bi vàng. Hỏi có bao nhiêu viên bi vàng?",
            "$p3 viên bi", listOf("${p3 + 1} viên bi", "${p1 + p2} viên bi", "$total viên bi"),
            "Số bi vàng là $total - $p1 - $p2 = $p3 viên bi.", random)
    },
    FormDef("W12", Topic.LOI_VAN, 2, 5) { grade, level, random ->
        val perDay = (5..30).random(random); val days = (3..10).random(random)
        makeQuestion(grade, Topic.LOI_VAN, level, "Mỗi ngày Lan đọc $perDay trang sách. Hỏi sau $days ngày, Lan đọc được bao nhiêu trang sách?",
            "${perDay * days} trang", listOf("${perDay * days + perDay} trang", "${perDay + days} trang", "$perDay trang"),
            "Số trang là $perDay × $days = ${perDay * days} trang.", random)
    },
    // --- Phân số - Thập phân: 12 dạng, chủ yếu lớp 4-5 ---
    FormDef("F01", Topic.PHAN_SO_THAP_PHAN, 4, 5) { grade, level, random ->
        val pairs = listOf(2 to 4, 3 to 6, 4 to 8, 2 to 6, 3 to 9, 4 to 12, 5 to 10)
        val (d1, d2) = pairs.random(random); val scale = d2 / d1
        var a = (1 until d1).random(random); var b = (1 until d2).random(random)
        var guard = 0
        while (a * scale + b >= d2 && guard < 50) { guard++; a = (1 until d1).random(random); b = (1 until d2).random(random) }
        val sum = a * scale + b
        makeQuestion(grade, Topic.PHAN_SO_THAP_PHAN, level, "$a/$d1 + $b/$d2 = ?",
            "$sum/$d2", listOf("${sum + 1}/$d2", "$sum/${d2 + 1}", "${a + b}/$d2"),
            "$a/$d1 = ${a * scale}/$d2, cộng với $b/$d2 được $sum/$d2.", random)
    },
    FormDef("F02", Topic.PHAN_SO_THAP_PHAN, 4, 5) { grade, level, random ->
        val pairs = listOf(2 to 4, 3 to 6, 4 to 8, 2 to 6, 3 to 9, 5 to 10)
        val (d1, d2) = pairs.random(random); val scale = d2 / d1
        var a = (1 until d1).random(random); var b = (1 until d2).random(random)
        var guard = 0
        while (a * scale <= b && guard < 50) { guard++; a = (1 until d1).random(random); b = (1 until d2).random(random) }
        val diff = a * scale - b
        makeQuestion(grade, Topic.PHAN_SO_THAP_PHAN, level, "$a/$d1 - $b/$d2 = ?",
            "$diff/$d2", listOf("${diff + 1}/$d2", "$diff/${d2 + 1}", "${a - b}/$d2"),
            "$a/$d1 = ${a * scale}/$d2, trừ $b/$d2 được $diff/$d2.", random)
    },
    FormDef("F03", Topic.PHAN_SO_THAP_PHAN, 4, 5) { grade, level, random ->
        val den = listOf(2, 3, 4, 5, 6, 8, 10).random(random); val num = (1 until den).random(random); val c = (2..9).random(random)
        val top = num * c; val g = gcd(top, den)
        val correct = "${top / g}/${den / g}"
        makeQuestion(grade, Topic.PHAN_SO_THAP_PHAN, level, "$num/$den × $c = ?",
            correct, listOf("$top/$den", "${num * c}/${den + 1}", "$num/$den"),
            "$num/$den × $c = $top/$den, rút gọn được $correct.", random)
    },
    FormDef("F04", Topic.PHAN_SO_THAP_PHAN, 4, 5) { grade, level, random ->
        val den = listOf(2, 3, 4, 5, 6, 8).random(random); val num = (1 until den).random(random); val q = (5..20).random(random)
        val total = den * q
        makeQuestion(grade, Topic.PHAN_SO_THAP_PHAN, level, "$num/$den của $total là bao nhiêu?",
            "${num * q}", listOf("${num * q + num}", "$total", "$q"),
            "$num/$den của $total là $total : $den × $num = ${num * q}.", random)
    },
    FormDef("F05", Topic.PHAN_SO_THAP_PHAN, 4, 5) { grade, level, random ->
        val pair = listOf(Triple(1, 2, "0,5"), Triple(1, 4, "0,25"), Triple(3, 4, "0,75"), Triple(1, 5, "0,2"), Triple(1, 10, "0,1"), Triple(3, 10, "0,3"), Triple(7, 10, "0,7"), Triple(1, 20, "0,05")).random(random)
        val allDecimals = listOf("0,5", "0,25", "0,75", "0,2", "0,1", "0,3", "0,7", "0,05")
        makeQuestion(grade, Topic.PHAN_SO_THAP_PHAN, level, "Phân số ${pair.first}/${pair.second} viết dưới dạng số thập phân là bao nhiêu?",
            pair.third, allDecimals.filter { it != pair.third }.shuffled(random).take(3),
            "Phân số ${pair.first}/${pair.second} = ${pair.third}.", random)
    },
    FormDef("F06", Topic.PHAN_SO_THAP_PHAN, 5, 5) { grade, level, random ->
        val tenths = (1..9).random(random); val top = tenths * 10; val g = gcd(top, 100)
        // Dùng số thập phân có 2 chữ số thập phân, ví dụ 0,25 = 25/100 = 1/4
        val hundredths = listOf(25, 50, 75, 20, 40, 60, 80).random(random)
        val gg = gcd(hundredths, 100)
        val dec = "0," + hundredths.toString().padStart(2, '0')
        makeQuestion(grade, Topic.PHAN_SO_THAP_PHAN, level, "Số thập phân $dec viết dưới dạng phân số tối giản là bao nhiêu?",
            "${hundredths / gg}/${100 / gg}", listOf("$hundredths/100", "${hundredths / gg + 1}/${100 / gg}", "1/2"),
            "$dec = $hundredths/100, rút gọn cho $gg được ${hundredths / gg}/${100 / gg}.", random)
    },
    FormDef("F07", Topic.PHAN_SO_THAP_PHAN, 5, 5) { grade, level, random ->
        val a = (11..99).random(random) / 10.0; val b = (11..99).random(random) / 10.0
        if (a == b) return@FormDef makeQuestion(grade, Topic.PHAN_SO_THAP_PHAN, level, "Số nào lớn hơn: 1,5 hay 2,5?", "2,5", listOf("1,5", "Bằng nhau", "Không so sánh được"), "So sánh phần nguyên, 2 > 1 nên 2,5 lớn hơn.", random)
        val bigger = formatNumber(maxOf(a, b)); val smaller = formatNumber(minOf(a, b))
        makeQuestion(grade, Topic.PHAN_SO_THAP_PHAN, level, "Số nào lớn hơn: ${formatNumber(a)} hay ${formatNumber(b)}?",
            bigger, listOf(smaller, "Bằng nhau", formatNumber(maxOf(a, b) + 0.1)),
            "So sánh 2 số thập phân, số lớn hơn là $bigger.", random)
    },
    FormDef("F08", Topic.PHAN_SO_THAP_PHAN, 5, 5) { grade, level, random ->
        val intPart = (1..99).random(random); val tenths = (1..9).random(random); val hundredths = (1..9).random(random)
        val dec = "$intPart,$tenths$hundredths"
        makeQuestion(grade, Topic.PHAN_SO_THAP_PHAN, level, "Chữ số ở hàng phần mười của số thập phân $dec là chữ số nào?",
            tenths.toString(), listOf(hundredths.toString(), intPart.toString(), ((tenths + 1) % 10).toString()),
            "Số $dec có chữ số hàng phần mười là $tenths.", random)
    },
    FormDef("F09", Topic.PHAN_SO_THAP_PHAN, 5, 5) { grade, level, random ->
        val intPart = (1..99).random(random); val frac = (1..9).random(random)
        val rounded = if (frac >= 5) intPart + 1 else intPart
        makeQuestion(grade, Topic.PHAN_SO_THAP_PHAN, level, "Làm tròn số thập phân $intPart,$frac đến số nguyên gần nhất được số nào?",
            rounded.toString(), listOf(intPart.toString(), (rounded + 1).toString(), "$intPart,$frac"),
            "Phần thập phân là 0,$frac, nên làm tròn được $rounded.", random)
    },
    FormDef("F10", Topic.PHAN_SO_THAP_PHAN, 5, 5) { grade, level, random ->
        val a = (11..99).random(random) / 10.0; val c = (2..9).random(random)
        val v = formatNumber(a * c)
        makeQuestion(grade, Topic.PHAN_SO_THAP_PHAN, level, "${formatNumber(a)} × $c = ?", v,
            listOf(formatNumber(a * c + 0.1), formatNumber(a * c - 0.1), formatNumber(a)),
            "Nhân số thập phân với số tự nhiên: ${formatNumber(a)} × $c = $v.", random)
    },
    FormDef("F11", Topic.PHAN_SO_THAP_PHAN, 5, 5) { grade, level, random ->
        val price = (10000..100000 step 10000).random(random); val p = listOf(10, 20, 25, 50).random(random)
        val after = price * (100 + p) / 100
        makeQuestion(grade, Topic.PHAN_SO_THAP_PHAN, level, "Một món hàng giá $price đồng, tăng giá $p%. Hỏi sau khi tăng, món hàng có giá bao nhiêu?",
            "$after đồng", listOf("${after + 1000} đồng", "$price đồng", "${price * p / 100} đồng"),
            "Số tiền tăng là $price × $p : 100 = ${price * p / 100} đồng, giá mới là $after đồng.", random)
    },
    FormDef("F12", Topic.PHAN_SO_THAP_PHAN, 4, 5) { grade, level, random ->
        val w = (1..5).random(random); val den = (2..5).random(random); val num = (1 until den).random(random)
        val top = w * den + num
        makeQuestion(grade, Topic.PHAN_SO_THAP_PHAN, level, "Hỗn số $w $num/$den viết dưới dạng phân số là bao nhiêu?",
            "$top/$den", listOf("${top + 1}/$den", "$w/$den", "$num/$den"),
            "Hỗn số $w $num/$den = ($w × $den + $num)/$den = $top/$den.", random)
    },
    // --- Nâng cao mới: 6 dạng ---
    FormDef("A01", Topic.NANG_CAO, 2, 5) { grade, level, random ->
        val mid = (5..50).random(random); val sum = mid * 3
        makeQuestion(grade, Topic.NANG_CAO, Difficulty.KHO, "Tổng của 3 số liên tiếp là $sum. Hỏi số ở giữa là số nào?",
            mid.toString(), numberDistractors(mid, random), "Tổng 3 số liên tiếp chia cho 3 được số ở giữa: $sum : 3 = $mid.", random)
    },
    FormDef("A02", Topic.NANG_CAO, 3, 5) { grade, level, random ->
        val start = (2..5).random(random); val ratio = (2..3).random(random)
        val next = start * ratio * ratio * ratio
        makeQuestion(grade, Topic.NANG_CAO, Difficulty.KHO, "Dãy số: $start, ${start * ratio}, ${start * ratio * ratio}, ?. Số tiếp theo là số nào?",
            next.toString(), listOf((next + ratio).toString(), (next * ratio).toString(), (next - ratio).toString()),
            "Dãy số nhân với $ratio mỗi lần, số tiếp theo là ${start * ratio * ratio} × $ratio = $next.", random)
    },
    FormDef("A03", Topic.NANG_CAO, 3, 5) { grade, level, random ->
        val k = (1..5).random(random)
        val next = (k + 3) * (k + 3)
        makeQuestion(grade, Topic.NANG_CAO, Difficulty.KHO, "Dãy số: ${k * k}, ${(k + 1) * (k + 1)}, ${(k + 2) * (k + 2)}, ?. Số tiếp theo là số nào?",
            next.toString(), listOf((next + 1).toString(), ((k + 3) * (k + 2)).toString(), (next - 1).toString()),
            "Dãy số là bình phương của ${k}, ${k + 1}, ${k + 2}, tiếp theo là ${(k + 3)}² = $next.", random)
    },
    FormDef("A04", Topic.NANG_CAO, 1, 5) { grade, level, random ->
        val total = (10..50).random(random); val pos = (2..total - 1).random(random)
        val fromEnd = total - pos + 1
        makeQuestion(grade, Topic.NANG_CAO, Difficulty.KHO, "Có $total bạn xếp hàng, An đứng thứ $pos từ đầu hàng. Hỏi An đứng thứ mấy từ cuối hàng?",
            fromEnd.toString(), numberDistractors(fromEnd, random), "Từ cuối hàng, An đứng thứ $total - $pos + 1 = $fromEnd.", random)
    },
    FormDef("A05", Topic.NANG_CAO, 5, 5) { grade, level, random ->
        val p = (2..5).random(random); val q = (3..7).random(random); val k = (10..20).random(random)
        if (p == q) return@FormDef makeQuestion(grade, Topic.NANG_CAO, Difficulty.KHO, "Tổng của 2 số là ${(p + q) * k}, tỉ số là $p:$q. Số lớn là số nào?",
            "${maxOf(p, q) * k}", numberDistractors(maxOf(p, q) * k, random), "Tổng số phần bằng nhau là ${p + q}, số lớn là ${(p + q) * k} : ${p + q} × ${maxOf(p, q)} = ${maxOf(p, q) * k}.", random)
        val total = (p + q) * k; val larger = maxOf(p, q) * k
        makeQuestion(grade, Topic.NANG_CAO, Difficulty.KHO, "Tổng của 2 số là $total, tỉ số của 2 số là $p:$q. Số lớn là số nào?",
            larger.toString(), listOf("${minOf(p, q) * k}", "$total", "${larger + k}"),
            "Tổng số phần bằng nhau là ${p + q}, mỗi phần là $total : ${p + q} = $k, số lớn là $k × ${maxOf(p, q)} = $larger.", random)
    },
    FormDef("A06", Topic.NANG_CAO, 2, 5) { grade, level, random ->
        val tens = (2..9).random(random); val units = (0..tens - 1).random(random)
        val sum = tens + units; val diff = tens - units; val num = tens * 10 + units
        makeQuestion(grade, Topic.NANG_CAO, Difficulty.KHO, "Tìm số có 2 chữ số, biết tổng các chữ số là $sum và hiệu các chữ số (chữ số hàng chục trừ chữ số hàng đơn vị) là $diff.",
            num.toString(), listOf((units * 10 + tens).toString(), (num + 1).toString(), (num - 1).toString()),
            "Chữ số hàng chục là ($sum + $diff) : 2 = $tens, chữ số hàng đơn vị là $sum - $tens = $units, số đó là $num.", random)
    },
    // --- Dạng lớp đầu: 7 dạng, giúp lớp 1-2 không bị thiệt ---
    FormDef("E01", Topic.SO_PHEP_TINH, 1, 3) { grade, level, random ->
        val target = listOf(10, 20).random(random); val a = (1..target - 1).random(random)
        makeQuestion(grade, Topic.SO_PHEP_TINH, level, "$a + ? = $target", (target - a).toString(),
            numberDistractors(target - a, random), "Vì $a + ${target - a} = $target nên số cần tìm là ${target - a}.", random)
    },
    FormDef("E02", Topic.SO_PHEP_TINH, 1, 3) { grade, level, random ->
        val a = (5..50).random(random)
        makeQuestion(grade, Topic.SO_PHEP_TINH, level, "Số ở giữa $a và ${a + 2} là số nào?", (a + 1).toString(),
            listOf(a.toString(), (a + 2).toString(), (a + 3).toString()),
            "Số ở giữa $a và ${a + 2} là ${a + 1}.", random)
    },
    FormDef("E03", Topic.SO_PHEP_TINH, 1, 3) { grade, level, random ->
        val total = if (grade == 1) (5..20).random(random) else (20..99).random(random)
        val b = (1..total - 1).random(random)
        makeQuestion(grade, Topic.SO_PHEP_TINH, level, "Tách số $total thành $b và ?", (total - b).toString(),
            numberDistractors(total - b, random), "Vì $b + ${total - b} = $total nên số còn lại là ${total - b}.", random)
    },
    FormDef("E04", Topic.SO_PHEP_TINH, 1, 3) { grade, level, random ->
        val a = (2..90).random(random); val round = listOf(10, 20, 30).random(random)
        makeQuestion(grade, Topic.SO_PHEP_TINH, level, "$a + $round = ?", (a + round).toString(),
            numberDistractors(a + round, random), "Cộng với số tròn chục: $a + $round = ${a + round}.", random)
    },
    FormDef("E05", Topic.HINH_HOC, 1, 3) { grade, level, random ->
        val shape = listOf("hình tam giác" to 3, "hình vuông" to 4, "hình chữ nhật" to 4, "hình ngũ giác" to 5).random(random)
        val others = listOf("hình tam giác", "hình vuông", "hình chữ nhật", "hình ngũ giác", "hình tròn").filter { it != shape.first }.shuffled(random).take(3)
        makeQuestion(grade, Topic.HINH_HOC, level, "Hình có ${shape.second} cạnh là hình nào?",
            shape.first, others,
            "Hình ${shape.first} có ${shape.second} cạnh.", random)
    },
    FormDef("E06", Topic.HINH_HOC, 1, 3) { grade, level, random ->
        val shape = listOf("hình tam giác" to 3, "hình vuông" to 4, "hình chữ nhật" to 4).random(random)
        val others = listOf("hình tam giác", "hình vuông", "hình chữ nhật", "hình ngũ giác").filter { it != shape.first }.shuffled(random).take(3)
        makeQuestion(grade, Topic.HINH_HOC, level, "Hình có ${shape.second} đỉnh là hình nào?",
            shape.first, others,
            "Hình ${shape.first} có ${shape.second} đỉnh.", random)
    },
    FormDef("E07", Topic.SO_PHEP_TINH, 1, 2) { grade, level, random ->
        val groups = (2..9).random(random)
        makeQuestion(grade, Topic.SO_PHEP_TINH, level, "Có $groups chục que tính. Hỏi có tất cả bao nhiêu que tính?",
            "${groups * 10} que", listOf("${groups * 10 + 10} que", "$groups que", "${groups * 5} que"),
            "1 chục là 10 que, $groups chục là $groups × 10 = ${groups * 10} que.", random)
    }
)
