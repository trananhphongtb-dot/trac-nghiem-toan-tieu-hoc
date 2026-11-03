package vn.phong.toantieuhoc.data

import vn.phong.toantieuhoc.model.Difficulty
import vn.phong.toantieuhoc.model.Question
import vn.phong.toantieuhoc.model.Topic
import kotlin.random.Random

// Dạng lớp đầu (1-2, một số tới lớp 3): giúp lớp 1-2 cũng đủ 80 dạng,
// không phải chỉ lấy dạng lớp lớn rồi thu nhỏ số.

val realFormsPart3: List<FormDef> = listOf(
    FormDef("Y01", Topic.SO_PHEP_TINH, 1, 2) { grade, level, random ->
        val tens = (1..9).random(random); val units = (0..9).random(random)
        val num = tens * 10 + units
        makeQuestion(grade, Topic.SO_PHEP_TINH, level, "Số gồm $tens chục và $units đơn vị là số nào?",
            num.toString(), listOf((num + 1).toString(), (num - 1).toString(), (units * 10 + tens).toString()),
            "Số gồm $tens chục và $units đơn vị là $num.", random)
    },
    FormDef("Y02", Topic.SO_PHEP_TINH, 1, 2) { grade, level, random ->
        val tens = (1..9).random(random); val units = (1..9).random(random)
        val num = tens * 10 + units
        makeQuestion(grade, Topic.SO_PHEP_TINH, level, "Số $num gồm mấy chục và mấy đơn vị?",
            "$tens chục $units đơn vị", listOf("$units chục $tens đơn vị", "$tens chục ${units + 1} đơn vị", "$tens đơn vị $units chục"),
            "Số $num gồm $tens chục và $units đơn vị.", random)
    },
    FormDef("Y03", Topic.SO_PHEP_TINH, 1, 3) { grade, level, random ->
        val a = (2..99).random(random); val b = (2..99).random(random)
        if (a == b) return@FormDef makeQuestion(grade, Topic.SO_PHEP_TINH, level, "$a + 1 = ?", (a + 1).toString(), numberDistractors(a + 1, random), "Tính $a + 1 = ${a + 1}.", random)
        val correct = if (a > b) ">" else "<"
        val other = if (a > b) "<" else ">"
        makeQuestion(grade, Topic.SO_PHEP_TINH, level, "Điền dấu thích hợp vào chỗ chấm: $a ... $b",
            correct, listOf(other, "=", "Không so sánh được"),
            "So sánh $a và $b, dấu thích hợp là $correct.", random)
    },
    FormDef("Y04", Topic.SO_PHEP_TINH, 1, 3) { grade, level, random ->
        val nums = distinctNums(3, if (grade == 1) 2..20 else 10..99, random).sorted()
        val correct = nums.joinToString(", ")
        val perms = listOf(listOf(nums[0], nums[2], nums[1]), listOf(nums[2], nums[1], nums[0]), listOf(nums[1], nums[0], nums[2]))
            .map { it.joinToString(", ") }.filter { it != correct }
        makeQuestion(grade, Topic.SO_PHEP_TINH, level, "Sắp xếp các số ${nums.shuffled(random).joinToString(", ")} theo thứ tự từ bé đến lớn.",
            correct, perms, "Sắp xếp từ bé đến lớn được $correct.", random)
    },
    FormDef("Y05", Topic.SO_PHEP_TINH, 1, 3) { grade, level, random ->
        val nums = distinctNums(3, if (grade == 1) 2..20 else 10..99, random).sortedDescending()
        val correct = nums.joinToString(", ")
        val perms = listOf(listOf(nums[0], nums[2], nums[1]), listOf(nums[2], nums[1], nums[0]), listOf(nums[1], nums[0], nums[2]))
            .map { it.joinToString(", ") }.filter { it != correct }
        makeQuestion(grade, Topic.SO_PHEP_TINH, level, "Sắp xếp các số ${nums.shuffled(random).joinToString(", ")} theo thứ tự từ lớn đến bé.",
            correct, perms, "Sắp xếp từ lớn đến bé được $correct.", random)
    },
    FormDef("Y06", Topic.SO_PHEP_TINH, 1, 3) { grade, level, random ->
        val a = (5..50).random(random); val b = (2..20).random(random); val c = (2..20).random(random)
        if (a + b <= c) return@FormDef makeQuestion(grade, Topic.SO_PHEP_TINH, level, "$a + $b = ?", (a + b).toString(), numberDistractors(a + b, random), "Tính $a + $b = ${a + b}.", random)
        val v = a + b - c
        makeQuestion(grade, Topic.SO_PHEP_TINH, level, "$a + $b - $c = ?", v.toString(),
            numberDistractors(v, random), "Tính $a + $b = ${a + b}, rồi ${a + b} - $c = $v.", random)
    },
    FormDef("Y07", Topic.SO_PHEP_TINH, 1, 3) { grade, level, random ->
        val a = (10..50).random(random); val b = (2..a - 1).random(random); val c = (2..20).random(random)
        val v = a - b + c
        makeQuestion(grade, Topic.SO_PHEP_TINH, level, "$a - $b + $c = ?", v.toString(),
            numberDistractors(v, random), "Tính $a - $b = ${a - b}, rồi ${a - b} + $c = $v.", random)
    },
    FormDef("Y08", Topic.SO_PHEP_TINH, 1, 3) { grade, level, random ->
        val step = listOf(1, 2, 5, 10).random(random); val start = step * 5
        val next = start - step * 3
        if (next < 0) return@FormDef makeQuestion(grade, Topic.SO_PHEP_TINH, level, "$start - $step = ?", (start - step).toString(), numberDistractors(start - step, random), "Tính $start - $step = ${start - step}.", random)
        makeQuestion(grade, Topic.SO_PHEP_TINH, level, "Đếm lùi cách $step: $start, ${start - step}, ${start - step * 2}, ?. Số tiếp theo là số nào?",
            next.toString(), listOf((next + step).toString(), (next - step).toString(), (next + 1).toString()),
            "Đếm lùi cách $step, số tiếp theo là ${start - step * 2} - $step = $next.", random)
    },
    FormDef("Y09", Topic.SO_PHEP_TINH, 1, 2) { grade, level, random ->
        val a = (11..99).random(random)
        val evenLess = if (a % 2 == 0) a - 2 else a - 1
        makeQuestion(grade, Topic.SO_PHEP_TINH, level, "Số chẵn lớn nhất bé hơn $a là số nào?",
            evenLess.toString(), listOf((evenLess + 2).toString(), a.toString(), (evenLess - 2).toString()),
            "Số chẵn lớn nhất bé hơn $a là $evenLess.", random)
    },
    FormDef("Y10", Topic.SO_PHEP_TINH, 1, 2) { grade, level, random ->
        val a = (11..99).random(random)
        val oddGreater = if (a % 2 == 1) a + 2 else a + 1
        makeQuestion(grade, Topic.SO_PHEP_TINH, level, "Số lẻ bé nhất lớn hơn $a là số nào?",
            oddGreater.toString(), listOf((oddGreater - 2).toString(), a.toString(), (oddGreater + 2).toString()),
            "Số lẻ bé nhất lớn hơn $a là $oddGreater.", random)
    },
    FormDef("Y11", Topic.DO_LUONG, 1, 2) { grade, level, random ->
        val nums = distinctNums(3, 5..50, random)
        val max = nums.max()
        makeQuestion(grade, Topic.DO_LUONG, level, "Đoạn thẳng nào dài nhất: ${nums[0]} cm, ${nums[1]} cm, ${nums[2]} cm?",
            "$max cm", nums.filter { it != max }.map { "$it cm" } + listOf("${max + 1} cm"),
            "So sánh 3 đoạn thẳng, đoạn dài nhất là $max cm.", random)
    },
    FormDef("Y12", Topic.DO_LUONG, 1, 2) { grade, level, random ->
        val nums = distinctNums(3, 5..50, random)
        val min = nums.min()
        makeQuestion(grade, Topic.DO_LUONG, level, "Đoạn thẳng nào ngắn nhất: ${nums[0]} cm, ${nums[1]} cm, ${nums[2]} cm?",
            "$min cm", nums.filter { it != min }.map { "$it cm" } + listOf("${min - 1} cm"),
            "So sánh 3 đoạn thẳng, đoạn ngắn nhất là $min cm.", random)
    },
    FormDef("Y13", Topic.DO_LUONG, 1, 2) { grade, level, random ->
        val h = (1..12).random(random)
        val otherHours = (1..12).filter { it != h }.shuffled(random).take(3)
        makeQuestion(grade, Topic.DO_LUONG, level, "Kim giờ chỉ số $h, kim phút chỉ số 12. Hỏi đồng hồ chỉ mấy giờ?",
            "$h giờ", otherHours.map { "$it giờ" },
            "Kim giờ chỉ $h, kim phút chỉ 12 nên đồng hồ chỉ $h giờ.", random)
    },
    FormDef("Y14", Topic.DO_LUONG, 1, 2) { grade, level, random ->
        val a = (1..5).random(random); val b = (1..9).random(random)
        val total = a * 2000 + b * 1000
        makeQuestion(grade, Topic.DO_LUONG, level, "Có $a tờ 2.000 đồng và $b tờ 1.000 đồng. Hỏi có tất cả bao nhiêu tiền?",
            "$total đồng", listOf("${total + 1000} đồng", "${total - 1000} đồng", "${a + b} đồng"),
            "Tổng tiền là $a × 2.000 + $b × 1.000 = $total đồng.", random)
    },
    FormDef("Y15", Topic.DO_LUONG, 1, 2) { grade, level, random ->
        val weekdays = listOf("Thứ hai", "Thứ ba", "Thứ tư", "Thứ năm", "Thứ sáu", "Thứ bảy", "Chủ nhật")
        val today = (0..6).random(random)
        val yesterday = (today + 6) % 7
        makeQuestion(grade, Topic.DO_LUONG, level, "Hôm nay là ${weekdays[today]}, hỏi hôm qua là thứ mấy?",
            weekdays[yesterday], weekdays.filter { it != weekdays[yesterday] }.shuffled(random).take(3),
            "Hôm nay là ${weekdays[today]}, hôm qua là ${weekdays[yesterday]}.", random)
    },
    FormDef("Y16", Topic.LOI_VAN, 1, 2) { grade, level, random ->
        val a = (10..50).random(random); val b = (2..a - 1).random(random)
        makeQuestion(grade, Topic.LOI_VAN, level, "An có $a viên bi, Bình ít hơn An $b viên bi. Hỏi Bình có bao nhiêu viên bi?",
            "${a - b} viên bi", listOf("${a - b + 1} viên bi", "${a + b} viên bi", "$a viên bi"),
            "Bình có $a - $b = ${a - b} viên bi.", random)
    },
    FormDef("Y17", Topic.LOI_VAN, 1, 2) { grade, level, random ->
        val a = (10..50).random(random); val b = (2..a - 1).random(random); val c = (2..20).random(random)
        makeQuestion(grade, Topic.LOI_VAN, level, "Lan có $a viên bi, Lan cho bạn $b viên bi, rồi mẹ cho Lan thêm $c viên bi. Hỏi Lan còn lại bao nhiêu viên bi?",
            "${a - b + c} viên bi", listOf("${a - b + c + 1} viên bi", "${a - b} viên bi", "${a + c} viên bi"),
            "Lan còn lại $a - $b + $c = ${a - b + c} viên bi.", random)
    },
    FormDef("Y18", Topic.HINH_HOC, 1, 2) { grade, level, random ->
        makeQuestion(grade, Topic.HINH_HOC, level, "Hình nào không có cạnh, không có đỉnh?",
            "Hình tròn", listOf("Hình vuông", "Hình tam giác", "Hình chữ nhật"),
            "Hình tròn không có cạnh, không có đỉnh.", random)
    },
    FormDef("Y19", Topic.HINH_HOC, 1, 2) { grade, level, random ->
        makeQuestion(grade, Topic.HINH_HOC, level, "Hình nào có 4 cạnh bằng nhau?",
            "Hình vuông", listOf("Hình chữ nhật", "Hình tam giác", "Hình ngũ giác"),
            "Hình vuông có 4 cạnh bằng nhau.", random)
    },
    FormDef("Y20", Topic.SO_PHEP_TINH, 1, 2) { grade, level, random ->
        val a = (10..99).random(random); val b = (2..a - 1).random(random)
        makeQuestion(grade, Topic.SO_PHEP_TINH, level, "Số $a hơn số $b bao nhiêu?",
            "${a - b}", listOf("${a - b + 1}", "${a + b}", "${a - b - 1}"),
            "Số $a hơn số $b là $a - $b = ${a - b}.", random)
    },
    FormDef("Y21", Topic.SO_PHEP_TINH, 1, 2) { grade, level, random ->
        val round = listOf(10, 20, 30, 40, 50, 60, 70, 80, 90).random(random)
        makeQuestion(grade, Topic.SO_PHEP_TINH, level, "Số liền trước của số tròn chục $round là số nào?",
            "${round - 1}", listOf("${round + 1}", "$round", "${round - 10}"),
            "Số liền trước của $round là ${round - 1}.", random)
    },
    FormDef("Y22", Topic.SO_PHEP_TINH, 1, 2) { grade, level, random ->
        val round = listOf(10, 20, 30, 40, 50, 60, 70, 80, 90).random(random)
        makeQuestion(grade, Topic.SO_PHEP_TINH, level, "Số liền sau của số tròn chục $round là số nào?",
            "${round + 1}", listOf("${round - 1}", "$round", "${round + 10}"),
            "Số liền sau của $round là ${round + 1}.", random)
    }
)
