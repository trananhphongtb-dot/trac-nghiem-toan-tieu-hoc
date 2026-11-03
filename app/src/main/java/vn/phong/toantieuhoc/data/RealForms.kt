package vn.phong.toantieuhoc.data

import vn.phong.toantieuhoc.model.Difficulty
import vn.phong.toantieuhoc.model.Question
import vn.phong.toantieuhoc.model.Topic
import kotlin.random.Random

/**
 * DẠNG THẬT, không phải chỉ đổi số.
 * Mỗi FormDef là 1 ý tưởng toán khác nhau: câu hỏi, cách tính, lời giải khác nhau.
 * Số lượng dạng mỗi lớp được đếm thật bằng countRealFormsPerGrade() ở cuối file,
 * không ước tính bằng số biến thể số.
 */
data class FormDef(
    val id: String,
    val topic: Topic,
    val minGrade: Int,
    val maxGrade: Int,
    val make: (Int, Difficulty, Random) -> Question
)

fun generateFormQuestion(grade: Int, topic: Topic, level: Difficulty, random: Random): Question? {
    val pool = (realFormsPart1 + realFormsPart2 + realFormsPart3).filter {
        grade in it.minGrade..it.maxGrade && (topic == Topic.ALL || it.topic == topic)
    }
    if (pool.isEmpty()) return null
    return pool.random(random).make(grade, level, random)
}

fun countRealFormsPerGrade(): Map<Int, Int> {
    return (1..5).associateWith { grade ->
        (realFormsPart1 + realFormsPart2 + realFormsPart3).count { grade in it.minGrade..it.maxGrade }
    }
}

// --- Trợ giúp theo lớp: số nhỏ dần cho lớp nhỏ, lớn dần cho lớp lớn ---
fun nSmall(grade: Int, random: Random): Int = when (grade) {
    1 -> (2..20).random(random)
    2 -> (10..99).random(random)
    3 -> (100..999).random(random)
    4 -> (1000..9999).random(random)
    else -> (1000..99999).random(random)
}

fun nTiny(grade: Int, random: Random): Int = when (grade) {
    1 -> (2..9).random(random)
    2 -> (2..20).random(random)
    else -> (10..99).random(random)
}

fun nWord(grade: Int, random: Random): Int = when (grade) {
    1 -> (2..20).random(random)
    2 -> (10..99).random(random)
    else -> (10..999).random(random)
}

fun distinctNums(count: Int, range: IntRange, random: Random): List<Int> {
    val set = mutableSetOf<Int>()
    var guard = 0
    while (set.size < count && guard < 200) {
        guard++
        set += range.random(random)
    }
    return set.toList()
}

fun gcd(a: Int, b: Int): Int {
    var x = kotlin.math.abs(a); var y = kotlin.math.abs(b)
    while (y != 0) { val t = x % y; x = y; y = t }
    return if (x == 0) 1 else x
}

private val vietUnits = listOf("không", "một", "hai", "ba", "bốn", "năm", "sáu", "bảy", "tám", "chín")

fun vietnamese(n: Int): String {
    if (n < 10) return vietUnits[n]
    if (n < 100) {
        val ten = n / 10; val unit = n % 10
        if (ten == 1) {
            return if (unit == 0) "mười" else if (unit == 5) "mười lăm" else "mười " + vietUnits[unit]
        }
        val base = vietUnits[ten] + " mươi"
        return when (unit) {
            0 -> base
            1 -> "$base mốt"
            5 -> "$base lăm"
            else -> "$base " + vietUnits[unit]
        }
    }
    val hundred = n / 100; val rest = n % 100
    if (rest == 0) return vietUnits[hundred] + " trăm"
    if (rest < 10) return vietUnits[hundred] + " trăm lẻ " + vietUnits[rest]
    return vietUnits[hundred] + " trăm " + vietnamese(rest)
}

// ---------------------------------------------------------------------------
// PHẦN 1: SỐ, HÌNH HỌC, ĐO LƯỜNG
// ---------------------------------------------------------------------------

val realFormsPart1: List<FormDef> = listOf(
    // --- Số: 25 dạng ---
    FormDef("N01", Topic.SO_PHEP_TINH, 1, 5) { grade, level, random ->
        val nums = distinctNums(3, if (grade == 1) 2..20 else 10..999, random)
        makeQuestion(grade, Topic.SO_PHEP_TINH, level, "Số nào lớn nhất trong các số ${nums.joinToString(", ")}?",
            nums.max().toString(), nums.filter { it != nums.max() }.map { it.toString() } + listOf((nums.max() + 1).toString()),
            "So sánh 3 số, số lớn nhất là ${nums.max()}.", random)
    },
    FormDef("N02", Topic.SO_PHEP_TINH, 1, 5) { grade, level, random ->
        val nums = distinctNums(3, if (grade == 1) 2..20 else 10..999, random)
        makeQuestion(grade, Topic.SO_PHEP_TINH, level, "Số nào bé nhất trong các số ${nums.joinToString(", ")}?",
            nums.min().toString(), nums.filter { it != nums.min() }.map { it.toString() } + listOf((nums.min() + 1).toString()),
            "So sánh 3 số, số bé nhất là ${nums.min()}.", random)
    },
    FormDef("N03", Topic.SO_PHEP_TINH, 1, 5) { grade, level, random ->
        val nums = distinctNums(3, if (grade == 1) 2..20 else 10..999, random).sorted()
        makeQuestion(grade, Topic.SO_PHEP_TINH, level, "Số ở giữa trong các số ${nums.joinToString(", ")} là số nào?",
            nums[1].toString(), listOf(nums[0].toString(), nums[2].toString(), (nums[1] + 1).toString()),
            "Sắp xếp tăng dần: ${nums.joinToString(", ")}, số ở giữa là ${nums[1]}.", random)
    },
    FormDef("N04", Topic.SO_PHEP_TINH, 1, 5) { grade, level, random ->
        val digits = when (grade) { 1 -> listOf(1, 2); 2 -> listOf(2, 3); 3 -> listOf(3, 4); else -> listOf(4, 5, 6) }.random(random)
        val largest = "9".repeat(digits).toInt()
        makeQuestion(grade, Topic.SO_PHEP_TINH, level, "Số lớn nhất có $digits chữ số là số nào?",
            largest.toString(), listOf((largest - 1).toString(), (largest + 1).toString(), ("1" + "0".repeat(digits)).toString()),
            "Số lớn nhất có $digits chữ số là $largest.", random)
    },
    FormDef("N05", Topic.SO_PHEP_TINH, 1, 5) { grade, level, random ->
        val digits = when (grade) { 1 -> listOf(1, 2); 2 -> listOf(2, 3); 3 -> listOf(3, 4); else -> listOf(4, 5) }.random(random)
        val smallest = ("1" + "0".repeat(digits - 1)).toInt()
        makeQuestion(grade, Topic.SO_PHEP_TINH, level, "Số bé nhất có $digits chữ số là số nào?",
            smallest.toString(), listOf((smallest - 1).toString(), (smallest + 1).toString(), (smallest * 10).toString()),
            "Số bé nhất có $digits chữ số là $smallest.", random)
    },
    FormDef("N06", Topic.SO_PHEP_TINH, 1, 5) { grade, level, random ->
        val num = nSmall(grade, random)
        val place = if (grade == 1) "hàng chục" else listOf("hàng chục", "hàng trăm").random(random)
        val digit = if (place == "hàng chục") (num / 10) % 10 else (num / 100) % 10
        makeQuestion(grade, Topic.SO_PHEP_TINH, level, "Chữ số ở $place của số $num là chữ số nào?",
            digit.toString(), listOf(((digit + 1) % 10).toString(), ((digit + 2) % 10).toString(), (num % 10).toString()),
            "Số $num có chữ số ở $place là $digit.", random)
    },
    FormDef("N07", Topic.SO_PHEP_TINH, 1, 5) { grade, level, random ->
        val num = nSmall(grade, random)
        val count = num.toString().length
        makeQuestion(grade, Topic.SO_PHEP_TINH, level, "Số $num có bao nhiêu chữ số?",
            count.toString(), listOf((count + 1).toString(), (count - 1).coerceAtLeast(1).toString(), "10"),
            "Đếm các chữ số của $num được $count chữ số.", random)
    },
    FormDef("N08", Topic.SO_PHEP_TINH, 1, 5) { grade, level, random ->
        val wantEven = random.nextBoolean()
        val correct = if (wantEven) (2..98 step 2).toList().random(random) else (3..99 step 2).toList().random(random)
        val distractors = if (wantEven) (3..99 step 2).shuffled(random).take(3) else (2..98 step 2).shuffled(random).take(3)
        makeQuestion(grade, Topic.SO_PHEP_TINH, level, if (wantEven) "Số nào là số chẵn?" else "Số nào là số lẻ?",
            correct.toString(), distractors.map { it.toString() },
            if (wantEven) "$correct chia hết cho 2 nên là số chẵn." else "$correct không chia hết cho 2 nên là số lẻ.", random)
    },
    FormDef("N09", Topic.SO_PHEP_TINH, 1, 5) { grade, level, random ->
        val a = nTiny(grade, random)
        makeQuestion(grade, Topic.SO_PHEP_TINH, level, "Gấp đôi số $a là số nào?", (a * 2).toString(),
            numberDistractors(a * 2, random), "Gấp đôi số $a là $a × 2 = ${a * 2}.", random)
    },
    FormDef("N10", Topic.SO_PHEP_TINH, 1, 5) { grade, level, random ->
        val a = (2..50 step 2).toList().random(random)
        makeQuestion(grade, Topic.SO_PHEP_TINH, level, "Một nửa của số $a là số nào?", (a / 2).toString(),
            numberDistractors(a / 2, random), "Một nửa của $a là $a : 2 = ${a / 2}.", random)
    },
    FormDef("N11", Topic.SO_PHEP_TINH, 1, 5) { grade, level, random ->
        val a = nTiny(grade, random)
        if (random.nextBoolean()) {
            makeQuestion(grade, Topic.SO_PHEP_TINH, level, "Gấp 10 lần số $a là số nào?", (a * 10).toString(),
                numberDistractors(a * 10, random), "Gấp 10 lần số $a là $a × 10 = ${a * 10}.", random)
        } else {
            makeQuestion(grade, Topic.SO_PHEP_TINH, level, "Gấp 3 lần số $a là số nào?", (a * 3).toString(),
                numberDistractors(a * 3, random), "Gấp 3 lần số $a là $a × 3 = ${a * 3}.", random)
        }
    },
    FormDef("N12", Topic.SO_PHEP_TINH, 1, 5) { grade, level, random ->
        val a = nTiny(grade, random)
        if (random.nextBoolean()) {
            makeQuestion(grade, Topic.SO_PHEP_TINH, level, "$a × 1 = ?", a.toString(),
                listOf("0", (a + 1).toString(), "1"), "Số nào nhân với 1 cũng bằng chính nó.", random)
        } else {
            makeQuestion(grade, Topic.SO_PHEP_TINH, level, "$a × 0 = ?", "0",
                listOf(a.toString(), "1", (a + 1).toString()), "Số nào nhân với 0 cũng bằng 0.", random)
        }
    },
    FormDef("N13", Topic.SO_PHEP_TINH, 2, 5) { grade, level, random ->
        val a = nTiny(grade, random)
        if (random.nextBoolean()) {
            makeQuestion(grade, Topic.SO_PHEP_TINH, level, "$a : 1 = ?", a.toString(),
                listOf("0", (a + 1).toString(), "1"), "Số nào chia cho 1 cũng bằng chính nó.", random)
        } else {
            makeQuestion(grade, Topic.SO_PHEP_TINH, level, "$a : $a = ?", "1",
                listOf(a.toString(), "0", (a + 1).toString()), "Số nào chia cho chính nó cũng bằng 1.", random)
        }
    },
    FormDef("N14", Topic.SO_PHEP_TINH, 2, 5) { grade, level, random ->
        val a = (2..9).random(random); val b = (2..9).random(random)
        makeQuestion(grade, Topic.SO_PHEP_TINH, level, "Tìm x: x × $a = ${a * b}", b.toString(),
            numberDistractors(b, random), "x = ${a * b} : $a = $b.", random)
    },
    FormDef("N15", Topic.SO_PHEP_TINH, 2, 5) { grade, level, random ->
        val a = (2..9).random(random); val b = (2..9).random(random)
        makeQuestion(grade, Topic.SO_PHEP_TINH, level, "Tìm x: ${a * b} : x = $a", b.toString(),
            numberDistractors(b, random), "x = ${a * b} : $a = $b.", random)
    },
    FormDef("N16", Topic.SO_PHEP_TINH, 1, 5) { grade, level, random ->
        val a = nTiny(grade, random); val b = nTiny(grade, random); val c = nTiny(grade, random); val d = nTiny(grade, random)
        val s1 = a + b; val s2 = c + d
        if (s1 == s2) return@FormDef makeQuestion(grade, Topic.SO_PHEP_TINH, level, "$a + $b = ?", s1.toString(), numberDistractors(s1, random), "Tính $a + $b = $s1.", random)
        val bigger = if (s1 > s2) "$a + $b" else "$c + $d"
        val biggerVal = maxOf(s1, s2)
        makeQuestion(grade, Topic.SO_PHEP_TINH, level, "Tổng nào lớn hơn: $a + $b hay $c + $d?",
            bigger, listOf(if (s1 > s2) "$c + $d" else "$a + $b", "Bằng nhau", "$biggerVal"),
            "$a + $b = $s1, $c + $d = $s2, tổng lớn hơn là $bigger.", random)
    },
    FormDef("N17", Topic.SO_PHEP_TINH, 3, 5) { grade, level, random ->
        val a = nTiny(grade, random); val b = (2..9).random(random); val c = (2..9).random(random)
        val v = a + b * c
        makeQuestion(grade, Topic.SO_PHEP_TINH, level, "$a + $b × $c = ?", v.toString(),
            numberDistractors(v, random), "Nhân trước, cộng sau: $b × $c = ${b * c}, rồi $a + ${b * c} = $v.", random)
    },
    FormDef("N18", Topic.SO_PHEP_TINH, 1, 5) { grade, level, random ->
        val a = nTiny(grade, random); val b = (1..99).random(random); val c = 100 - b
        if (c <= 0) return@FormDef makeQuestion(grade, Topic.SO_PHEP_TINH, level, "$a + 10 = ?", (a + 10).toString(), numberDistractors(a + 10, random), "Tính $a + 10 = ${a + 10}.", random)
        val v = a + b + c
        makeQuestion(grade, Topic.SO_PHEP_TINH, level, "Tính nhanh: $a + $b + $c = ?", v.toString(),
            numberDistractors(v, random), "Nhóm $b + $c = 100 trước, rồi $a + 100 = $v.", random)
    },
    FormDef("N19", Topic.SO_PHEP_TINH, 1, 3) { grade, level, random ->
        val step = listOf(2, 5, 10).random(random); val start = 0
        val next = step * 4
        makeQuestion(grade, Topic.SO_PHEP_TINH, level, "Đếm cách $step: $start, $step, ${step * 2}, ${step * 3}, ?. Số tiếp theo là số nào?",
            next.toString(), listOf((next + step).toString(), (next - step).toString(), (next + 1).toString()),
            "Đếm cách $step, số tiếp theo là ${step * 3} + $step = $next.", random)
    },
    FormDef("N20", Topic.SO_PHEP_TINH, 1, 5) { grade, level, random ->
        val n = if (grade <= 2) (11..99).random(random) else (101..999).random(random)
        makeQuestion(grade, Topic.SO_PHEP_TINH, level, "Số $n đọc là gì?", vietnamese(n),
            listOf(vietnamese(n + 1), vietnamese(n - 1), vietnamese(n + 10)),
            "Số $n đọc là ${vietnamese(n)}.", random)
    },
    FormDef("N21", Topic.SO_PHEP_TINH, 1, 5) { grade, level, random ->
        val n = if (grade <= 2) (11..99).random(random) else (101..999).random(random)
        makeQuestion(grade, Topic.SO_PHEP_TINH, level, "Số \"${vietnamese(n)}\" viết bằng chữ số là số nào?",
            n.toString(), listOf((n + 1).toString(), (n - 1).toString(), (n + 10).toString()),
            "\"${vietnamese(n)}\" viết bằng chữ số là $n.", random)
    },
    FormDef("N22", Topic.SO_PHEP_TINH, 1, 5) { grade, level, random ->
        val n = if (grade == 1) (11..99).random(random) else (100..999).random(random)
        val sum = n.toString().map { it.digitToInt() }.sum()
        makeQuestion(grade, Topic.SO_PHEP_TINH, level, "Tổng các chữ số của số $n là bao nhiêu?",
            sum.toString(), numberDistractors(sum, random), "Cộng các chữ số của $n được $sum.", random)
    },
    FormDef("N23", Topic.SO_PHEP_TINH, 1, 5) { grade, level, random ->
        val nums = distinctNums(3, if (grade == 1) 2..20 else 10..999, random)
        val diff = nums.max() - nums.min()
        makeQuestion(grade, Topic.SO_PHEP_TINH, level, "Hiệu của số lớn nhất và số bé nhất trong các số ${nums.joinToString(", ")} là bao nhiêu?",
            diff.toString(), numberDistractors(diff, random), "Số lớn nhất là ${nums.max()}, số bé nhất là ${nums.min()}, hiệu là $diff.", random)
    },
    FormDef("N24", Topic.SO_PHEP_TINH, 2, 5) { grade, level, random ->
        val b = (2..9).random(random); val k = (2..9).random(random); val a = b * k
        makeQuestion(grade, Topic.SO_PHEP_TINH, level, "Số $a gấp số $b bao nhiêu lần?", k.toString(),
            numberDistractors(k, random), "Vì $b × $k = $a nên $a gấp $b $k lần.", random)
    },
    FormDef("N25", Topic.SO_PHEP_TINH, 1, 5) { grade, level, random ->
        val a = if (grade == 1) (11..99).random(random) else (101..999).random(random)
        val roundDown = (a / 10) * 10
        makeQuestion(grade, Topic.SO_PHEP_TINH, level, "Số tròn chục lớn nhất bé hơn $a là số nào?",
            roundDown.toString(), listOf((roundDown + 10).toString(), (roundDown - 10).toString(), a.toString()),
            "Số tròn chục lớn nhất bé hơn $a là $roundDown.", random)
    },
    // --- Hình học: 12 dạng ---
    FormDef("G01", Topic.HINH_HOC, 3, 5) { grade, level, random ->
        val a = (3..30).random(random); val b = (3..30).random(random); val c = (3..30).random(random)
        makeQuestion(grade, Topic.HINH_HOC, level, "Hình tam giác có 3 cạnh dài ${a}cm, ${b}cm, ${c}cm. Chu vi hình tam giác là bao nhiêu?",
            "${a + b + c} cm", listOf("${a + b} cm", "${a + b + c + 1} cm", "${a * b} cm"),
            "Chu vi hình tam giác là $a + $b + $c = ${a + b + c} cm.", random)
    },
    FormDef("G02", Topic.HINH_HOC, 3, 5) { grade, level, random ->
        val sides = listOf(5, 6).random(random); val side = (3..20).random(random)
        val name = if (sides == 5) "ngũ giác đều" else "lục giác đều"
        makeQuestion(grade, Topic.HINH_HOC, level, "Hình $name có mỗi cạnh dài ${side}cm. Chu vi hình đó là bao nhiêu?",
            "${side * sides} cm", listOf("${side * 4} cm", "${side + sides} cm", "${side * sides + side} cm"),
            "Chu vi hình $name là $side × $sides = ${side * sides} cm.", random)
    },
    FormDef("G03", Topic.HINH_HOC, 5, 5) { grade, level, random ->
        val base = (4..30 step 2).toList().random(random); val height = (2..20).random(random)
        val area = base * height / 2
        makeQuestion(grade, Topic.HINH_HOC, level, "Hình tam giác có đáy ${base}cm, chiều cao ${height}cm. Diện tích là bao nhiêu?",
            "$area cm²", listOf("${base * height} cm²", "${area + base} cm²", "${base + height} cm²"),
            "Diện tích hình tam giác = đáy × chiều cao : 2 = $base × $height : 2 = $area cm².", random)
    },
    FormDef("G04", Topic.HINH_HOC, 4, 5) { grade, level, random ->
        val base = (3..30).random(random); val height = (3..20).random(random)
        makeQuestion(grade, Topic.HINH_HOC, level, "Hình bình hành có đáy ${base}cm, chiều cao ${height}cm. Diện tích là bao nhiêu?",
            "${base * height} cm²", listOf("${2 * (base + height)} cm²", "${base * height + base} cm²", "${base + height} cm²"),
            "Diện tích hình bình hành = đáy × chiều cao = $base × $height = ${base * height} cm².", random)
    },
    FormDef("G05", Topic.HINH_HOC, 2, 5) { grade, level, random ->
        val kind = listOf("mặt" to 6, "cạnh" to 12, "đỉnh" to 8).random(random)
        makeQuestion(grade, Topic.HINH_HOC, level, "Hình lập phương có bao nhiêu ${kind.first}?",
            kind.second.toString(), numberDistractors(kind.second, random),
            "Hình lập phương có ${kind.second} ${kind.first}.", random)
    },
    FormDef("G06", Topic.HINH_HOC, 3, 5) { grade, level, random ->
        val side = (3..30).random(random)
        makeQuestion(grade, Topic.HINH_HOC, level, "Hình vuông có chu vi ${side * 4}cm. Cạnh hình vuông là bao nhiêu?",
            "$side cm", listOf("${side + 1} cm", "${side * 2} cm", "${side * 4} cm"),
            "Cạnh hình vuông = chu vi : 4 = ${side * 4} : 4 = $side cm.", random)
    },
    FormDef("G07", Topic.HINH_HOC, 4, 5) { grade, level, random ->
        val length = (3..30).random(random); val width = (3..20).random(random)
        makeQuestion(grade, Topic.HINH_HOC, level, "Hình chữ nhật có diện tích ${length * width}cm², chiều dài ${length}cm. Chiều rộng là bao nhiêu?",
            "$width cm", listOf("${width + 1} cm", "$length cm", "${length * width} cm"),
            "Chiều rộng = diện tích : chiều dài = ${length * width} : $length = $width cm.", random)
    },
    FormDef("G08", Topic.HINH_HOC, 2, 5) { grade, level, random ->
        val rows = (2..6).random(random); val cols = (2..6).random(random)
        makeQuestion(grade, Topic.HINH_HOC, level, "Lưới ô vuông có $rows hàng, mỗi hàng có $cols ô vuông nhỏ. Hỏi có tất cả bao nhiêu ô vuông nhỏ?",
            "${rows * cols} ô", listOf("${rows + cols} ô", "${rows * cols + 1} ô", "$rows ô"),
            "Số ô vuông nhỏ là $rows × $cols = ${rows * cols} ô.", random)
    },
    FormDef("G09", Topic.HINH_HOC, 4, 5) { grade, level, random ->
        val a = (3..20).random(random); val b = (3..20).random(random); val c = (3..20).random(random); val d = (3..20).random(random)
        val total = a * b + c * d
        makeQuestion(grade, Topic.HINH_HOC, level, "Hình gồm 2 hình chữ nhật: hình thứ nhất dài ${a}cm, rộng ${b}cm; hình thứ hai dài ${c}cm, rộng ${d}cm. Diện tích cả hình là bao nhiêu?",
            "$total cm²", listOf("${a * b} cm²", "${total + a} cm²", "${a + b + c + d} cm²"),
            "Diện tích là $a × $b + $c × $d = ${a * b} + ${c * d} = $total cm².", random)
    },
    FormDef("G10", Topic.HINH_HOC, 4, 5) { grade, level, random ->
        val a = (3..20).random(random); val b = (3..20).random(random); val c = (3..20).random(random); val d = (3..20).random(random)
        val s1 = a * b; val s2 = c * d
        if (s1 == s2) return@FormDef makeQuestion(grade, Topic.HINH_HOC, level, "Hình chữ nhật dài ${a}cm, rộng ${b}cm có diện tích là bao nhiêu?",
            "$s1 cm²", listOf("${s1 + a} cm²", "${2 * (a + b)} cm²", "${a + b} cm²"),
            "Diện tích là $a × $b = $s1 cm².", random)
        val bigger = if (s1 > s2) "hình thứ nhất" else "hình thứ hai"
        makeQuestion(grade, Topic.HINH_HOC, level, "Hình thứ nhất dài ${a}cm, rộng ${b}cm; hình thứ hai dài ${c}cm, rộng ${d}cm. Hình nào có diện tích lớn hơn?",
            bigger, listOf(if (s1 > s2) "hình thứ hai" else "hình thứ nhất", "Bằng nhau", "Không so sánh được"),
            "Diện tích hình thứ nhất là $s1 cm², hình thứ hai là $s2 cm², hình lớn hơn là $bigger.", random)
    },
    FormDef("G11", Topic.HINH_HOC, 2, 5) { grade, level, random ->
        val shape = listOf("hình vuông" to 4, "hình chữ nhật" to 4, "hình tam giác vuông" to 1).random(random)
        makeQuestion(grade, Topic.HINH_HOC, level, "Hình ${shape.first} có bao nhiêu góc vuông?",
            shape.second.toString(), numberDistractors(shape.second, random),
            "Hình ${shape.first} có ${shape.second} góc vuông.", random)
    },
    FormDef("G12", Topic.HINH_HOC, 5, 5) { grade, level, random ->
        val side = (2..10).random(random)
        makeQuestion(grade, Topic.HINH_HOC, level, "Hình lập phương có cạnh ${side}cm. Thể tích là bao nhiêu?",
            "${side * side * side} cm³", listOf("${side * 6} cm³", "${side * side} cm³", "${side * side * side + side} cm³"),
            "Thể tích hình lập phương = cạnh × cạnh × cạnh = $side × $side × $side = ${side * side * side} cm³.", random)
    },
    // --- Đo lường: 12 dạng ---
    FormDef("M01", Topic.DO_LUONG, 1, 5) { grade, level, random ->
        val m = (1..9).random(random); val cm = listOf(10, 20, 30, 40, 50, 60, 70, 80, 90).random(random)
        val total = m * 100 + cm
        makeQuestion(grade, Topic.DO_LUONG, level, "$m m $cm cm = ? cm", "$total cm",
            listOf("${total + 10} cm", "${m * 100} cm", "$cm cm"),
            "$m m = ${m * 100} cm, cộng thêm $cm cm được $total cm.", random)
    },
    FormDef("M02", Topic.DO_LUONG, 1, 5) { grade, level, random ->
        val kg = (1..9).random(random); val g = listOf(100, 200, 300, 400, 500, 600, 700, 800, 900).random(random)
        val total = kg * 1000 + g
        makeQuestion(grade, Topic.DO_LUONG, level, "$kg kg $g g = ? g", "$total g",
            listOf("${total + 100} g", "${kg * 1000} g", "$g g"),
            "$kg kg = ${kg * 1000} g, cộng thêm $g g được $total g.", random)
    },
    FormDef("M03", Topic.DO_LUONG, 2, 5) { grade, level, random ->
        val h = (1..9).random(random); val m = listOf(10, 15, 20, 30, 40, 45, 50).random(random)
        val total = h * 60 + m
        makeQuestion(grade, Topic.DO_LUONG, level, "$h giờ $m phút = ? phút", "$total phút",
            listOf("${total + 60} phút", "${h * 60} phút", "$m phút"),
            "$h giờ = ${h * 60} phút, cộng thêm $m phút được $total phút.", random)
    },
    FormDef("M04", Topic.DO_LUONG, 2, 5) { grade, level, random ->
        val a = (1..5).random(random); val b = (1..5).random(random); val c = (1..9).random(random)
        val total = a * 10000 + b * 5000 + c * 1000
        makeQuestion(grade, Topic.DO_LUONG, level, "Có $a tờ 10.000 đồng, $b tờ 5.000 đồng và $c tờ 1.000 đồng. Hỏi có tất cả bao nhiêu tiền?",
            "$total đồng", listOf("${total + 1000} đồng", "${total - 1000} đồng", "${a + b + c} đồng"),
            "Tổng tiền là $a × 10.000 + $b × 5.000 + $c × 1.000 = $total đồng.", random)
    },
    FormDef("M05", Topic.DO_LUONG, 2, 5) { grade, level, random ->
        val price = (1000..40000 step 1000).toList().random(random); val pay = ((price / 10000) + 1) * 10000
        val change = pay - price
        makeQuestion(grade, Topic.DO_LUONG, level, "Mua một món hàng giá $price đồng, trả $pay đồng. Hỏi được trả lại bao nhiêu tiền?",
            "$change đồng", listOf("${change + 1000} đồng", "${change - 1000} đồng", "$price đồng"),
            "Tiền trả lại là $pay - $price = $change đồng.", random)
    },
    FormDef("M06", Topic.DO_LUONG, 2, 5) { grade, level, random ->
        val start = (6..18).random(random); val dur = (1..5).random(random)
        val end = (start + dur) % 24
        makeQuestion(grade, Topic.DO_LUONG, level, "Bắt đầu lúc $start giờ, sau $dur giờ nữa là mấy giờ?",
            "$end giờ", listOf("${(end + 1) % 24} giờ", "$start giờ", "${if (end == 0) 23 else end - 1} giờ"),
            "Bắt đầu lúc $start giờ, cộng thêm $dur giờ được $end giờ.", random)
    },
    FormDef("M07", Topic.DO_LUONG, 2, 5) { grade, level, random ->
        val h1 = (6..15).random(random); val h2 = h1 + (1..6).random(random)
        makeQuestion(grade, Topic.DO_LUONG, level, "Từ $h1 giờ đến $h2 giờ là bao nhiêu giờ?",
            "${h2 - h1} giờ", listOf("${h2 - h1 + 1} giờ", "$h2 giờ", "${h2 - h1 - 1} giờ"),
            "Thời gian là $h2 - $h1 = ${h2 - h1} giờ.", random)
    },
    FormDef("M08", Topic.DO_LUONG, 1, 5) { grade, level, random ->
        val weekdays = listOf("Thứ hai", "Thứ ba", "Thứ tư", "Thứ năm", "Thứ sáu", "Thứ bảy", "Chủ nhật")
        val start = (0..6).random(random); val k = (2..10).random(random)
        val end = (start + k) % 7
        makeQuestion(grade, Topic.DO_LUONG, level, "Hôm nay là ${weekdays[start]}, $k ngày nữa là thứ mấy?",
            weekdays[end], weekdays.filter { it != weekdays[end] }.shuffled(random).take(3),
            "Cộng $k ngày vào ${weekdays[start]}, đếm vòng 7 ngày được ${weekdays[end]}.", random)
    },
    FormDef("M09", Topic.DO_LUONG, 1, 5) { grade, level, random ->
        val aKg = (1..9).random(random); val bG = (100..9000 step 100).toList().random(random)
        val heavier = if (aKg * 1000 > bG) "$aKg kg" else "$bG g"
        makeQuestion(grade, Topic.DO_LUONG, level, "Cái nào nặng hơn: $aKg kg hay $bG g?",
            heavier, listOf(if (heavier == "$aKg kg") "$bG g" else "$aKg kg", "Bằng nhau", "Không so sánh được"),
            "$aKg kg = ${aKg * 1000} g, so với $bG g thì $heavier nặng hơn.", random)
    },
    FormDef("M10", Topic.DO_LUONG, 1, 5) { grade, level, random ->
        val a = (1..5).random(random); val b = (10..90 step 10).toList().random(random); val c = (1..5).random(random); val d = (10..90 step 10).toList().random(random)
        val total = (a + c) * 100 + b + d
        makeQuestion(grade, Topic.DO_LUONG, level, "$a m $b cm + $c m $d cm = ? cm", "$total cm",
            listOf("${total + 10} cm", "${total - 10} cm", "${(a + c) * 100} cm"),
            "Đổi ra cm rồi cộng: ${a * 100 + b} + ${c * 100 + d} = $total cm.", random)
    },
    FormDef("M11", Topic.DO_LUONG, 1, 5) { grade, level, random ->
        val a = (1..9).random(random); val b = (100..900 step 100).toList().random(random)
        val total = a * 1000 + b
        makeQuestion(grade, Topic.DO_LUONG, level, "$a l + $b ml = ? ml", "$total ml",
            listOf("${total + 100} ml", "${a * 1000} ml", "$b ml"),
            "$a l = ${a * 1000} ml, cộng thêm $b ml được $total ml.", random)
    },
    FormDef("M12", Topic.DO_LUONG, 1, 5) { grade, level, random ->
        val day = (1..18).random(random); val k = (2..10).random(random); val month = (1..12).random(random)
        makeQuestion(grade, Topic.DO_LUONG, level, "Ngày $day tháng $month, $k ngày sau là ngày nào?",
            "Ngày ${day + k} tháng $month", listOf("Ngày ${day + k + 1} tháng $month", "Ngày $day tháng $month", "Ngày ${day + k} tháng ${month + 1}"),
            "Ngày $day cộng thêm $k ngày được ngày ${day + k} tháng $month.", random)
    }
)
