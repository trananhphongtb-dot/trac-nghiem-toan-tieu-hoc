package vn.phong.toantieuhoc.data

import vn.phong.toantieuhoc.model.Difficulty
import vn.phong.toantieuhoc.model.Question
import vn.phong.toantieuhoc.model.Topic
import kotlin.random.Random

private fun IntRange.randomIn(random: Random): Int = random.nextInt(first, last + 1)

/**
 * Ước tính tối thiểu số câu khác nhau mỗi lớp, chỉ tính riêng phần Số.
 * Con số này là cận dưới an toàn, chưa tính Hình học, Đo lường,
 * Lời văn, Phân số - Thập phân và Toán nâng cao.
 * Ví dụ lớp 1: riêng phép cộng a + b với a, b từ 2 đến 100 đã có
 * 99 × 99 = 9.801 đề khác nhau, nên lấy 6.500 là còn trừ hao rất nhiều.
 */
fun minBankSize(grade: Int): Int = when (grade) {
    1 -> 6500
    2 -> 8000
    3 -> 10000
    4 -> 12000
    else -> 12000
}

fun generateQuestions(
    grade: Int,
    topic: Topic,
    difficulty: Difficulty,
    count: Int,
    random: Random = Random(System.currentTimeMillis())
): List<Question> {
    val result = mutableListOf<Question>()
    val fixed = fixedQuestions.filter { it.grade == grade && (topic == Topic.ALL || it.topic == topic) }
        .shuffled(random)
    result += fixed
    var attempts = 0
    while (result.size < count && attempts < 20000) {
        attempts++
        val useTopic = if (topic == Topic.ALL) allowedTopics(grade).random(random) else topic
        val q = generateOne(grade, useTopic, difficulty, random) ?: continue
        if (result.none { it.text == q.text }) result += q
    }
    return result.shuffled(random).take(count)
}

fun allowedTopics(grade: Int): List<Topic> {
    val base = mutableListOf(
        Topic.SO_PHEP_TINH, Topic.HINH_HOC, Topic.DO_LUONG, Topic.LOI_VAN, Topic.NANG_CAO
    )
    if (grade >= 4) base += Topic.PHAN_SO_THAP_PHAN
    return base
}

private fun generateOne(grade: Int, topic: Topic, level: Difficulty, random: Random): Question? {
    // Trộn cả 2 nguồn: dạng cũ (đã có) và dạng thật mới (RealForms).
    // Tỉ lệ 50/50 để bé gặp cả 2 loại, và tổng số dạng mỗi lớp là cộng cả 2 nguồn.
    if (random.nextBoolean()) {
        val form = generateFormQuestion(grade, topic, level, random)
        if (form != null) return form
    }
    return when (topic) {
        Topic.SO_PHEP_TINH -> generateArithmetic(grade, level, random)
        Topic.HINH_HOC -> generateGeometry(grade, level, random)
        Topic.DO_LUONG -> generateMeasurement(grade, level, random)
        Topic.LOI_VAN -> generateWordProblem(grade, level, random)
        Topic.PHAN_SO_THAP_PHAN -> generateFractionDecimal(grade, level, random)
        Topic.NANG_CAO -> generateAdvanced(grade, level, random)
        Topic.ALL -> generateArithmetic(grade, level, random)
    }
}

// ---------------------------------------------------------------------------
// PHẦN SỐ - trọng tâm, nhiều dạng nhất
// ---------------------------------------------------------------------------

private fun generateArithmetic(grade: Int, level: Difficulty, random: Random): Question {
    val t = Topic.SO_PHEP_TINH
    return when (grade) {
        1 -> {
            val max = when (level) { Difficulty.DE -> 10; Difficulty.VUA -> 20; Difficulty.KHO -> 100 }
            when (random.nextInt(6)) {
                0 -> {
                    val a = (2..max).randomIn(random); val b = (2..max).randomIn(random)
                    if (a + b > max && level != Difficulty.KHO) return generateArithmetic(grade, level, random)
                    makeQuestion(grade, t, level, "$a + $b = ?", (a + b).toString(),
                        numberDistractors(a + b, random), "Tính $a + $b = ${a + b}.", random)
                }
                1 -> {
                    val a = (2..max).randomIn(random); val b = (2..max).randomIn(random)
                    val big = maxOf(a, b); val small = minOf(a, b)
                    makeQuestion(grade, t, level, "$big - $small = ?", (big - small).toString(),
                        numberDistractors(big - small, random), "Tính $big - $small = ${big - small}.", random)
                }
                2 -> {
                    val a = (2..max).randomIn(random); val b = (2..max).randomIn(random)
                    if (a == b) return generateArithmetic(grade, level, random)
                    val big = maxOf(a, b); val small = minOf(a, b)
                    makeQuestion(grade, t, level, "Số nào lớn hơn: $a hay $b?", big.toString(),
                        listOf(small.toString(), (big + 1).toString(), (big + 10).toString()),
                        "So sánh $a và $b, số lớn hơn là $big.", random)
                }
                3 -> {
                    val a = (2..max).randomIn(random); val b = (2..max).randomIn(random)
                    if (a + b > max && level != Difficulty.KHO) return generateArithmetic(grade, level, random)
                    makeQuestion(grade, t, level, "$a + ? = ${a + b}", b.toString(),
                        numberDistractors(b, random), "Vì $a + $b = ${a + b} nên số cần tìm là $b.", random)
                }
                4 -> {
                    val a = (2..max).randomIn(random)
                    if (random.nextBoolean()) {
                        makeQuestion(grade, t, level, "Số liền sau của $a là số nào?", (a + 1).toString(),
                            numberDistractors(a + 1, random), "Số liền sau của $a là ${a + 1}.", random)
                    } else {
                        if (a < 2) return generateArithmetic(grade, level, random)
                        makeQuestion(grade, t, level, "Số liền trước của $a là số nào?", (a - 1).toString(),
                            numberDistractors(a - 1, random), "Số liền trước của $a là ${a - 1}.", random)
                    }
                }
                else -> {
                    val a = (1..max / 2).randomIn(random); val b = (1..max / 2).randomIn(random); val c = (1..max / 2).randomIn(random)
                    if (a + b + c > max && level != Difficulty.KHO) return generateArithmetic(grade, level, random)
                    makeQuestion(grade, t, level, "$a + $b + $c = ?", (a + b + c).toString(),
                        numberDistractors(a + b + c, random), "Tính $a + $b + $c = ${a + b + c}.", random)
                }
            }
        }
        2 -> {
            when (random.nextInt(6)) {
                0 -> {
                    val a = (11..99).randomIn(random); val b = (11..99).randomIn(random)
                    val big = maxOf(a, b); val small = minOf(a, b)
                    if (random.nextBoolean()) {
                        makeQuestion(grade, t, level, "$big + $small = ?", (big + small).toString(),
                            numberDistractors(big + small, random), "Tính $big + $small = ${big + small}.", random)
                    } else {
                        makeQuestion(grade, t, level, "$big - $small = ?", (big - small).toString(),
                            numberDistractors(big - small, random), "Tính $big - $small = ${big - small}.", random)
                    }
                }
                1 -> {
                    val a = (2..9).randomIn(random); val b = (2..9).randomIn(random)
                    makeQuestion(grade, t, level, "$a × $b = ?", (a * b).toString(),
                        numberDistractors(a * b, random), "Bảng nhân: $a × $b = ${a * b}.", random)
                }
                2 -> {
                    val a = (2..9).randomIn(random); val b = (2..9).randomIn(random)
                    makeQuestion(grade, t, level, "${a * b} : $b = ?", a.toString(),
                        numberDistractors(a, random), "Vì $b × $a = ${a * b} nên ${a * b} : $b = $a.", random)
                }
                3 -> {
                    val a = (100..999).randomIn(random); val b = (100..999).randomIn(random)
                    if (a == b) return generateArithmetic(grade, level, random)
                    val big = maxOf(a, b); val small = minOf(a, b)
                    makeQuestion(grade, t, level, "Số nào lớn hơn: $a hay $b?", big.toString(),
                        listOf(small.toString(), (big + 10).toString(), (big + 100).toString()),
                        "So sánh $a và $b, số lớn hơn là $big.", random)
                }
                4 -> {
                    val a = (11..99).randomIn(random)
                    val rounded = ((a + 5) / 10) * 10
                    makeQuestion(grade, t, level, "Làm tròn số $a đến hàng chục được số nào?", rounded.toString(),
                        listOf((rounded + 10).toString(), (rounded - 10).toString(), a.toString()),
                        "Làm tròn $a đến hàng chục được $rounded.", random)
                }
                else -> {
                    val a = (100..999).randomIn(random); val b = (100..999).randomIn(random)
                    val big = maxOf(a, b); val small = minOf(a, b)
                    if (random.nextBoolean()) {
                        makeQuestion(grade, t, level, "$big + $small = ?", (big + small).toString(),
                            numberDistractors(big + small, random), "Tính $big + $small = ${big + small}.", random)
                    } else {
                        makeQuestion(grade, t, level, "$big - $small = ?", (big - small).toString(),
                            numberDistractors(big - small, random), "Tính $big - $small = ${big - small}.", random)
                    }
                }
            }
        }
        3 -> {
            when (random.nextInt(6)) {
                0 -> {
                    val a = (100..9999).randomIn(random); val b = (100..9999).randomIn(random)
                    val big = maxOf(a, b); val small = minOf(a, b)
                    if (random.nextBoolean()) {
                        makeQuestion(grade, t, level, "$big + $small = ?", (big + small).toString(),
                            numberDistractors(big + small, random), "Tính $big + $small = ${big + small}.", random)
                    } else {
                        makeQuestion(grade, t, level, "$big - $small = ?", (big - small).toString(),
                            numberDistractors(big - small, random), "Tính $big - $small = ${big - small}.", random)
                    }
                }
                1 -> {
                    val a = (12..999).randomIn(random); val b = (2..9).randomIn(random)
                    makeQuestion(grade, t, level, "$a × $b = ?", (a * b).toString(),
                        numberDistractors(a * b, random), "Tính $a × $b = ${a * b}.", random)
                }
                2 -> {
                    val a = (12..999).randomIn(random); val b = (2..9).randomIn(random)
                    makeQuestion(grade, t, level, "${a * b} : $b = ?", a.toString(),
                        numberDistractors(a, random), "Vì $b × $a = ${a * b} nên ${a * b} : $b = $a.", random)
                }
                3 -> {
                    val a = (12..999).randomIn(random); val factor = listOf(10, 100, 1000).random(random)
                    makeQuestion(grade, t, level, "$a × $factor = ?", (a * factor).toString(),
                        numberDistractors(a * factor, random), "Nhân với $factor chỉ cần thêm số 0 vào sau $a.", random)
                }
                4 -> {
                    val a = (1000..9999).randomIn(random); val b = (1000..9999).randomIn(random)
                    if (a == b) return generateArithmetic(grade, level, random)
                    val big = maxOf(a, b)
                    makeQuestion(grade, t, level, "Số nào bé hơn: $a hay $b?", minOf(a, b).toString(),
                        listOf(big.toString(), (big + 100).toString(), (minOf(a, b) + 10).toString()),
                        "So sánh $a và $b, số bé hơn là ${minOf(a, b)}.", random)
                }
                else -> {
                    val a = (12..99).randomIn(random); val b = (2..9).randomIn(random); val c = (10..99).randomIn(random)
                    val v = a * b + c
                    makeQuestion(grade, t, level, "$a × $b + $c = ?", v.toString(),
                        numberDistractors(v, random), "Tính $a × $b = ${a * b}, rồi cộng $c được $v.", random)
                }
            }
        }
        4 -> {
            when (random.nextInt(5)) {
                0 -> {
                    val a = (1000..999999).randomIn(random); val b = (1000..999999).randomIn(random)
                    val big = maxOf(a, b); val small = minOf(a, b)
                    if (random.nextBoolean()) {
                        makeQuestion(grade, t, level, "$big + $small = ?", (big + small).toString(),
                            numberDistractors(big + small, random), "Tính $big + $small = ${big + small}.", random)
                    } else {
                        makeQuestion(grade, t, level, "$big - $small = ?", (big - small).toString(),
                            numberDistractors(big - small, random), "Tính $big - $small = ${big - small}.", random)
                    }
                }
                1 -> {
                    val a = (123..9999).randomIn(random); val b = (12..99).randomIn(random)
                    makeQuestion(grade, t, level, "$a × $b = ?", (a * b).toString(),
                        numberDistractors(a * b, random), "Tính $a × $b = ${a * b}.", random)
                }
                2 -> {
                    val a = (123..9999).randomIn(random); val b = (12..99).randomIn(random)
                    makeQuestion(grade, t, level, "${a * b} : $b = ?", a.toString(),
                        numberDistractors(a, random), "Vì $b × $a = ${a * b} nên ${a * b} : $b = $a.", random)
                }
                3 -> {
                    val a = (123..9999).randomIn(random); val factor = listOf(10, 100, 1000).random(random)
                    makeQuestion(grade, t, level, "$a × $factor = ?", (a * factor).toString(),
                        numberDistractors(a * factor, random), "Nhân với $factor chỉ cần thêm số 0 vào sau $a.", random)
                }
                else -> {
                    val a = (12..99).randomIn(random); val b = (2..9).randomIn(random); val c = (12..99).randomIn(random); val d = (2..9).randomIn(random)
                    val v = a * b + c * d
                    makeQuestion(grade, t, level, "$a × $b + $c × $d = ?", v.toString(),
                        numberDistractors(v, random), "Tính $a × $b = ${a * b}, $c × $d = ${c * d}, tổng là $v.", random)
                }
            }
        }
        else -> {
            when (random.nextInt(5)) {
                0 -> {
                    val a = (1000..999999).randomIn(random); val b = (1000..999999).randomIn(random)
                    val big = maxOf(a, b); val small = minOf(a, b)
                    if (random.nextBoolean()) {
                        makeQuestion(grade, t, level, "$big + $small = ?", (big + small).toString(),
                            numberDistractors(big + small, random), "Tính $big + $small = ${big + small}.", random)
                    } else {
                        makeQuestion(grade, t, level, "$big - $small = ?", (big - small).toString(),
                            numberDistractors(big - small, random), "Tính $big - $small = ${big - small}.", random)
                    }
                }
                1 -> {
                    val b = (12..99).randomIn(random); val q = (12..99).randomIn(random)
                    if (random.nextBoolean()) {
                        makeQuestion(grade, t, level, "$b × $q = ?", (b * q).toString(),
                            numberDistractors(b * q, random), "Tính $b × $q = ${b * q}.", random)
                    } else {
                        makeQuestion(grade, t, level, "${b * q} : $b = ?", q.toString(),
                            numberDistractors(q, random), "Vì $b × $q = ${b * q} nên ${b * q} : $b = $q.", random)
                    }
                }
                2 -> {
                    val a = (123..9999).randomIn(random); val factor = listOf(10, 100, 1000).random(random)
                    makeQuestion(grade, t, level, "$a × $factor = ?", (a * factor).toString(),
                        numberDistractors(a * factor, random), "Nhân với $factor chỉ cần thêm số 0 vào sau $a.", random)
                }
                3 -> {
                    val a = (100..999).randomIn(random)
                    val rounded = ((a + 50) / 100) * 100
                    makeQuestion(grade, t, level, "Làm tròn số $a đến hàng trăm được số nào?", rounded.toString(),
                        listOf((rounded + 100).toString(), (rounded - 100).toString(), a.toString()),
                        "Làm tròn $a đến hàng trăm được $rounded.", random)
                }
                else -> {
                    val a = (12..99).randomIn(random); val b = (2..9).randomIn(random); val c = (100..999).randomIn(random)
                    val v = a * b - c
                    if (v <= 0) return generateArithmetic(grade, level, random)
                    makeQuestion(grade, t, level, "$a × $b - $c = ?", v.toString(),
                        numberDistractors(v, random), "Tính $a × $b = ${a * b}, rồi trừ $c được $v.", random)
                }
            }
        }
    }
}

// ---------------------------------------------------------------------------
// HÌNH HỌC, ĐO LƯỜNG, LỜI VĂN, PHÂN SỐ - THẬP PHÂN
// ---------------------------------------------------------------------------

private fun generateGeometry(grade: Int, level: Difficulty, random: Random): Question {
    val t = Topic.HINH_HOC
    val a = (3..50).randomIn(random); val b = (3..50).randomIn(random)
    return when {
        grade <= 2 -> when (random.nextInt(4)) {
            0 -> {
                val shape = listOf("hình vuông" to 4, "hình tam giác" to 3, "hình chữ nhật" to 4, "hình ngũ giác" to 5, "hình lục giác" to 6).random(random)
                makeQuestion(grade, t, level, "Hình ${shape.first} có bao nhiêu cạnh?", shape.second.toString(),
                    numberDistractors(shape.second, random), "Hình ${shape.first} có ${shape.second} cạnh.", random)
            }
            1 -> {
                val x = (2..30).randomIn(random); val y = (2..30).randomIn(random); val z = (2..30).randomIn(random)
                makeQuestion(grade, t, level, "Đường gấp khúc gồm 3 đoạn dài ${x}cm, ${y}cm, ${z}cm. Độ dài đường gấp khúc là bao nhiêu?",
                    "${x + y + z} cm", listOf("${x + y} cm", "${x + y + z + 1} cm", "${x + y + z - 1} cm"),
                    "Độ dài đường gấp khúc là $x + $y + $z = ${x + y + z} cm.", random)
            }
            2 -> {
                val side = (2..50).randomIn(random)
                makeQuestion(grade, t, level, "Hình vuông có cạnh ${side}cm. Chu vi hình vuông là bao nhiêu?",
                    "${side * 4} cm", listOf("${side * 2} cm", "${side * 3} cm", "${side * 4 + 4} cm"),
                    "Chu vi hình vuông = cạnh × 4 = $side × 4 = ${side * 4} cm.", random)
            }
            else -> {
                val tri = (1..30).randomIn(random); val sq = (1..30).randomIn(random)
                makeQuestion(grade, t, level, "Có $tri hình tam giác và $sq hình vuông. Hỏi có tất cả bao nhiêu hình?",
                    "${tri + sq} hình", listOf("${tri + sq + 1} hình", "${tri * sq} hình", "$tri hình"),
                    "Tổng số hình là $tri + $sq = ${tri + sq} hình.", random)
            }
        }
        grade == 3 -> {
            val p = 2 * (a + b)
            if (random.nextBoolean()) {
                makeQuestion(grade, t, level, "Hình chữ nhật có chiều dài ${a}cm, chiều rộng ${b}cm. Chu vi hình đó là bao nhiêu?",
                    "$p cm", listOf("${p + 2} cm", "${p - 2} cm", "${a + b} cm"),
                    "Chu vi = (chiều dài + chiều rộng) × 2 = ($a + $b) × 2 = $p cm.", random)
            } else {
                val s = a * b
                makeQuestion(grade, t, level, "Hình chữ nhật có chiều dài ${a}cm, chiều rộng ${b}cm. Diện tích hình đó là bao nhiêu?",
                    "$s cm²", listOf("${s + a} cm²", "${s - a} cm²", "${2 * (a + b)} cm²"),
                    "Diện tích = chiều dài × chiều rộng = $a × $b = $s cm².", random)
            }
        }
        else -> {
            if (random.nextBoolean()) {
                val s = a * b
                makeQuestion(grade, t, level, "Hình chữ nhật có chiều dài ${a}cm, chiều rộng ${b}cm. Diện tích hình đó là bao nhiêu?",
                    "$s cm²", listOf("${s + a} cm²", "${s - a} cm²", "${2 * (a + b)} cm²"),
                    "Diện tích = chiều dài × chiều rộng = $a × $b = $s cm².", random)
            } else if (grade == 5 && random.nextBoolean()) {
                val c = (3..20).randomIn(random)
                val v = a * b * c
                makeQuestion(grade, t, level, "Hình hộp chữ nhật có chiều dài ${a}cm, chiều rộng ${b}cm, chiều cao ${c}cm. Thể tích là bao nhiêu?",
                    "$v cm³", listOf("${v + a} cm³", "${a * b} cm³", "${a + b + c} cm³"),
                    "Thể tích = $a × $b × $c = $v cm³.", random)
            } else {
                val side = (3..50).randomIn(random)
                makeQuestion(grade, t, level, "Hình vuông có cạnh ${side}cm. Diện tích hình vuông là bao nhiêu?",
                    "${side * side} cm²", listOf("${side * 4} cm²", "${side * side + side} cm²", "${side * 2} cm²"),
                    "Diện tích hình vuông = cạnh × cạnh = $side × $side = ${side * side} cm².", random)
            }
        }
    }
}

private fun generateMeasurement(grade: Int, level: Difficulty, random: Random): Question {
    val t = Topic.DO_LUONG
    val conversions = listOf(
        Triple("m", "cm", 100), Triple("dm", "cm", 10), Triple("cm", "mm", 10),
        Triple("km", "m", 1000), Triple("kg", "g", 1000), Triple("l", "ml", 1000),
        Triple("giờ", "phút", 60), Triple("phút", "giây", 60)
    )
    return when (random.nextInt(3)) {
        0 -> {
            val conv = conversions.random(random)
            val v = (2..99).randomIn(random)
            val correct = v * conv.third
            makeQuestion(grade, t, level, "$v ${conv.first} = ? ${conv.second}", "$correct ${conv.second}",
                listOf("${correct + conv.third} ${conv.second}", "${v * 10} ${conv.second}", "$v ${conv.second}"),
                "1 ${conv.first} = ${conv.third} ${conv.second} nên $v ${conv.first} = $correct ${conv.second}.", random)
        }
        1 -> {
            val a = (2..99).randomIn(random); val b = (2..99).randomIn(random)
            val unit = listOf("m", "cm", "kg", "l").random(random)
            if (random.nextBoolean()) {
                makeQuestion(grade, t, level, "$a $unit + $b $unit = ?", "${a + b} $unit",
                    listOf("${a + b + 1} $unit", "${a + b - 1} $unit", "${a * b} $unit"),
                    "Tính $a + $b = ${a + b} $unit.", random)
            } else {
                val big = maxOf(a, b); val small = minOf(a, b)
                makeQuestion(grade, t, level, "$big $unit - $small $unit = ?", "${big - small} $unit",
                    listOf("${big - small + 1} $unit", "${big - small - 1} $unit", "${big + small} $unit"),
                    "Tính $big - $small = ${big - small} $unit.", random)
            }
        }
        else -> {
            val h = (1..9).randomIn(random); val m = listOf(10, 20, 30, 40, 50).random(random)
            val total = h * 60 + m
            makeQuestion(grade, t, level, "$h giờ $m phút = ? phút", "$total phút",
                listOf("${total + 60} phút", "${total - 10} phút", "${h * 60} phút"),
                "$h giờ = ${h * 60} phút, cộng thêm $m phút được $total phút.", random)
        }
    }
}

private fun generateWordProblem(grade: Int, level: Difficulty, random: Random): Question {
    val t = Topic.LOI_VAN
    val names = listOf("Lan", "Hùng", "An", "Minh", "Mai", "Nam", "Hoa", "Tuấn")
    val objects = listOf("viên bi", "quả táo", "chiếc kẹo", "quyển vở", "chiếc bút", "quyển sách", "cái bánh", "quả cam")
    val name = names.random(random); val obj = objects.random(random)
    val a = (5..99).randomIn(random); val b = (3..99).randomIn(random)
    return when (random.nextInt(5)) {
        0 -> {
            val total = a + b
            makeQuestion(grade, t, level, "$name có $a $obj, được cho thêm $b $obj. Hỏi $name có tất cả bao nhiêu $obj?",
                "$total $obj", listOf("${total + 1} $obj", "${total - 1} $obj", "${total + 10} $obj"),
                "Số $obj của $name là $a + $b = $total $obj.", random)
        }
        1 -> {
            val big = maxOf(a, b); val small = minOf(a, b)
            makeQuestion(grade, t, level, "$name có $big $obj, $name cho bạn $small $obj. Hỏi $name còn lại bao nhiêu $obj?",
                "${big - small} $obj", listOf("${big - small + 1} $obj", "${big + small} $obj", "$small $obj"),
                "Số $obj còn lại là $big - $small = ${big - small} $obj.", random)
        }
        2 -> {
            val each = (3..12).randomIn(random); val boxes = (3..30).randomIn(random)
            makeQuestion(grade, t, level, "Mỗi hộp có $each $obj. Hỏi $boxes hộp như thế có bao nhiêu $obj?",
                "${each * boxes} $obj", listOf("${each * boxes + each} $obj", "${each * boxes - each} $obj", "${each + boxes} $obj"),
                "Số $obj là $boxes × $each = ${each * boxes} $obj.", random)
        }
        3 -> {
            val each = (3..12).randomIn(random); val boxes = (3..30).randomIn(random)
            makeQuestion(grade, t, level, "Có ${each * boxes} $obj, chia đều vào $boxes hộp. Mỗi hộp có bao nhiêu $obj?",
                "$each $obj", listOf("${each + 1} $obj", "${each - 1} $obj", "$boxes $obj"),
                "Mỗi hộp có ${each * boxes} : $boxes = $each $obj.", random)
        }
        else -> {
            if (grade >= 4) {
                val a2 = a * 2; val b2 = b * 2
                makeQuestion(grade, t, level, "Lớp 4A có $a2 học sinh, lớp 4B có $b2 học sinh. Trung bình mỗi lớp có bao nhiêu học sinh?",
                    "${(a2 + b2) / 2} học sinh", listOf("${(a2 + b2) / 2 + 2} học sinh", "${(a2 + b2) / 2 - 2} học sinh", "${a2 + b2} học sinh"),
                    "Trung bình cộng = ($a2 + $b2) : 2 = ${(a2 + b2) / 2} học sinh.", random)
            } else {
                val price = (5..30).randomIn(random) * 1000; val qty = (3..9).randomIn(random)
                makeQuestion(grade, t, level, "Một quyển vở giá $price đồng. Mua $qty quyển vở như thế hết bao nhiêu tiền?",
                    "${price * qty} đồng", listOf("${price * qty + price} đồng", "${price * qty - price} đồng", "${price + qty} đồng"),
                    "Số tiền là $price × $qty = ${price * qty} đồng.", random)
            }
        }
    }
}

private fun generateFractionDecimal(grade: Int, level: Difficulty, random: Random): Question {
    val t = Topic.PHAN_SO_THAP_PHAN
    if (grade < 4) {
        val even = (2..100 step 2).toList().random(random)
        return if (random.nextBoolean()) {
            makeQuestion(grade, t, level, "Một nửa của $even là bao nhiêu?", (even / 2).toString(),
                numberDistractors(even / 2, random), "Một nửa của $even là $even : 2 = ${even / 2}.", random)
        } else {
            val mult4 = (4..100 step 4).toList().random(random)
            makeQuestion(grade, t, level, "Một phần tư của $mult4 là bao nhiêu?", (mult4 / 4).toString(),
                numberDistractors(mult4 / 4, random), "Một phần tư của $mult4 là $mult4 : 4 = ${mult4 / 4}.", random)
        }
    }
    if (grade == 4) {
        return when (random.nextInt(4)) {
            0 -> {
                val den = listOf(3, 4, 5, 6, 8, 10, 12, 20).random(random)
                val a = (1 until den).randomIn(random); val b = (1 until den).randomIn(random)
                val sum = a + b
                if (sum < den) {
                    makeQuestion(grade, t, level, "$a/$den + $b/$den = ?", "$sum/$den",
                        listOf("${sum + 1}/$den", "$sum/${den + 1}", "${a + b}/${den * 2}"),
                        "Cùng mẫu số, cộng tử số: $a/$den + $b/$den = $sum/$den.", random)
                } else {
                    makeQuestion(grade, t, level, "Phân số nào lớn hơn: 1/2 hay 1/$den?", "1/2",
                        listOf("1/$den", "Bằng nhau", "Không so sánh được"),
                        "Cùng tử số, mẫu số nhỏ hơn thì phân số lớn hơn.", random)
                }
            }
            1 -> {
                val den = listOf(3, 4, 5, 6, 8, 10).random(random)
                val a = (1 until den).randomIn(random); val b = (1 until den).randomIn(random)
                if (a == b) return generateFractionDecimal(grade, level, random)
                val big = maxOf(a, b)
                makeQuestion(grade, t, level, "Phân số nào lớn hơn: $a/$den hay $b/$den?", "$big/$den",
                    listOf("${minOf(a, b)}/$den", "${big + 1}/$den", "Bằng nhau"),
                    "Cùng mẫu số, tử số lớn hơn thì phân số lớn hơn.", random)
            }
            2 -> {
                val a = (2..9).randomIn(random); val b = (2..9).randomIn(random); val k = (2..5).randomIn(random)
                makeQuestion(grade, t, level, "Rút gọn phân số ${a * k}/${b * k} được phân số nào?", "$a/$b",
                    listOf("${a * k}/$b", "$a/${b * k}", "${a + 1}/$b"),
                    "Chia cả tử số và mẫu số cho $k được $a/$b.", random)
            }
            else -> {
                val den = listOf(2, 4, 5, 10, 20).random(random); val q = (2..20).randomIn(random)
                val total = den * q
                makeQuestion(grade, t, level, "1/$den của $total là bao nhiêu?", q.toString(),
                    numberDistractors(q, random), "1/$den của $total là $total : $den = $q.", random)
            }
        }
    }
    // Lớp 5: thập phân và tỉ số phần trăm.
    return when (random.nextInt(4)) {
        0 -> {
            val a = (11..999).randomIn(random) / 10.0
            val b = (11..999).randomIn(random) / 10.0
            val sum = formatNumber(a + b)
            makeQuestion(grade, t, level, "${formatNumber(a)} + ${formatNumber(b)} = ?", sum,
                listOf(formatNumber(a + b + 0.1), formatNumber(a + b - 0.1), formatNumber(a + b + 1.0)),
                "Cộng số thập phân: ${formatNumber(a)} + ${formatNumber(b)} = $sum.", random)
        }
        1 -> {
            val a = (11..999).randomIn(random) / 100.0
            val b = (11..999).randomIn(random) / 100.0
            val big = maxOf(a, b); val small = minOf(a, b)
            val diff = formatNumber(big - small)
            makeQuestion(grade, t, level, "${formatNumber(big)} - ${formatNumber(small)} = ?", diff,
                listOf(formatNumber(big - small + 0.01), formatNumber(big - small - 0.01), formatNumber(big + small)),
                "Trừ số thập phân: ${formatNumber(big)} - ${formatNumber(small)} = $diff.", random)
        }
        2 -> {
            val p = (5..95 step 5).toList().random(random); val base = listOf(100, 200, 500, 1000).random(random)
            val v = base * p / 100
            makeQuestion(grade, t, level, "$p% của $base là bao nhiêu?", v.toString(),
                listOf((v + p).toString(), (v - 5).toString(), (100 - p).toString()),
                "$p% của $base là $base × $p : 100 = $v.", random)
        }
        else -> {
            val a = (11..99).randomIn(random) / 10.0
            val factor = listOf(10, 100).random(random)
            val v = formatNumber(a * factor)
            makeQuestion(grade, t, level, "${formatNumber(a)} × $factor = ?", v,
                listOf(formatNumber(a * factor + 1), formatNumber(a * factor - 1), formatNumber(a)),
                "Nhân số thập phân với $factor, dịch dấu phẩy sang phải.", random)
        }
    }
}

// ---------------------------------------------------------------------------
// TOÁN NÂNG CAO - dành cho học sinh khá, giỏi
// ---------------------------------------------------------------------------

fun generateAdvanced(grade: Int, level: Difficulty, random: Random): Question {
    val t = Topic.NANG_CAO
    return when (random.nextInt(6)) {
        0 -> {
            val start = (2..50).randomIn(random); val step = (2..12).randomIn(random)
            val next = start + step * 3
            makeQuestion(grade, t, Difficulty.KHO, "Dãy số: $start, ${start + step}, ${start + step * 2}, ?. Số tiếp theo là số nào?",
                next.toString(), listOf((next + step).toString(), (next - step).toString(), (next + 1).toString()),
                "Dãy số tăng đều $step một lần, số tiếp theo là ${start + step * 2} + $step = $next.", random)
        }
        1 -> {
            val a = (5..99).randomIn(random); val b = (5..99).randomIn(random)
            val sum = a + b
            makeQuestion(grade, t, Difficulty.KHO, "Tìm x: x + $a = $sum", b.toString(),
                numberDistractors(b, random), "x = $sum - $a = $b.", random)
        }
        2 -> {
            val a = (3..30).randomIn(random); val b = (3..12).randomIn(random); val c = (3..30).randomIn(random)
            val v = (a + b) * c
            makeQuestion(grade, t, Difficulty.KHO, "($a + $b) × $c = ?", v.toString(),
                numberDistractors(v, random), "Tính trong ngoặc trước: ($a + $b) = ${a + b}, rồi nhân $c được $v.", random)
        }
        3 -> {
            val large = (10..99).randomIn(random); val small = (1..large - 1).randomIn(random)
            val sum = large + small; val diff = large - small
            makeQuestion(grade, t, Difficulty.KHO, "Tổng của hai số là $sum, hiệu của hai số là $diff. Số lớn là số nào?",
                large.toString(), listOf(small.toString(), (sum).toString(), (large + 1).toString()),
                "Số lớn = (tổng + hiệu) : 2 = ($sum + $diff) : 2 = $large.", random)
        }
        4 -> {
            val a = (3..30).randomIn(random); val b = (3..12).randomIn(random); val c = (3..30).randomIn(random); val d = (3..12).randomIn(random)
            val v = a * b + c * d
            makeQuestion(grade, t, Difficulty.KHO, "$a × $b + $c × $d = ?", v.toString(),
                numberDistractors(v, random), "Tính $a × $b = ${a * b}, $c × $d = ${c * d}, tổng là $v.", random)
        }
        else -> {
            val age = (6..12).randomIn(random); val years = (3..10).randomIn(random); val gap = (20..35).randomIn(random)
            makeQuestion(grade, t, Difficulty.KHO, "Năm nay An $age tuổi. Sau $years năm nữa, mẹ hơn An $gap tuổi. Hỏi sau $years năm nữa mẹ bao nhiêu tuổi?",
                "${age + years + gap} tuổi", listOf("${age + gap} tuổi", "${age + years} tuổi", "${gap} tuổi"),
                "Sau $years năm, An ${age + years} tuổi, mẹ hơn An $gap tuổi nên mẹ ${age + years + gap} tuổi.", random)
        }
    }
}
