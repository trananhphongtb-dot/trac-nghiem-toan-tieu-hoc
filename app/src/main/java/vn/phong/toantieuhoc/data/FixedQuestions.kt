package vn.phong.toantieuhoc.data

import vn.phong.toantieuhoc.model.Difficulty
import vn.phong.toantieuhoc.model.Question
import vn.phong.toantieuhoc.model.Topic
import kotlin.random.Random

private fun fixed(
    grade: Int, topic: Topic, text: String, correct: String,
    distractors: List<String>, explanation: String
): Question {
    return makeQuestion(
        grade, topic, Difficulty.VUA, text, correct, distractors, explanation,
        Random(text.hashCode().toLong())
    )
}

/**
 * Ngân hàng câu hỏi cố định, bám chương trình lớp 1-5.
 * Đây là phần "chất lượng cao" do người biên soạn viết tay.
 * Phần sinh tự động trong Generators.kt tạo thêm đề mới mỗi lượt,
 * nên tổng số đề thực tế là không giới hạn với Số và phép tính,
 * Hình học, Đo lường và Toán có lời văn.
 */
val fixedQuestions: List<Question> = listOf(
    // Lớp 1
    fixed(1, Topic.SO_PHEP_TINH, "Số liền sau của 19 là số nào?", "20",
        listOf("18", "21", "29"), "Số liền sau của 19 là 19 + 1 = 20."),
    fixed(1, Topic.SO_PHEP_TINH, "8 + 7 = ?", "15",
        listOf("14", "16", "13"), "8 + 7 = 15."),
    fixed(1, Topic.HINH_HOC, "Hình nào có 3 cạnh?", "Hình tam giác",
        listOf("Hình vuông", "Hình tròn", "Hình chữ nhật"), "Hình tam giác có 3 cạnh."),
    fixed(1, Topic.DO_LUONG, "1 tuần có bao nhiêu ngày?", "7 ngày",
        listOf("5 ngày", "6 ngày", "10 ngày"), "1 tuần có 7 ngày."),
    fixed(1, Topic.LOI_VAN, "An có 9 quả cam, An ăn 4 quả. Hỏi An còn lại bao nhiêu quả cam?", "5 quả",
        listOf("4 quả", "6 quả", "13 quả"), "Số cam còn lại là 9 - 4 = 5 quả."),
    // Lớp 2
    fixed(2, Topic.SO_PHEP_TINH, "5 × 4 = ?", "20",
        listOf("15", "25", "30"), "5 × 4 = 20."),
    fixed(2, Topic.SO_PHEP_TINH, "20 : 5 = ?", "4",
        listOf("5", "15", "25"), "Vì 5 × 4 = 20 nên 20 : 5 = 4."),
    fixed(2, Topic.DO_LUONG, "1 giờ có bao nhiêu phút?", "60 phút",
        listOf("30 phút", "100 phút", "24 phút"), "1 giờ có 60 phút."),
    fixed(2, Topic.HINH_HOC, "Hình vuông có bao nhiêu góc vuông?", "4 góc vuông",
        listOf("2 góc vuông", "3 góc vuông", "0 góc vuông"), "Hình vuông có 4 góc vuông."),
    fixed(2, Topic.LOI_VAN, "Mỗi túi có 5 quả táo. 4 túi như thế có bao nhiêu quả táo?", "20 quả",
        listOf("9 quả", "15 quả", "25 quả"), "Số táo là 4 × 5 = 20 quả."),
    // Lớp 3
    fixed(3, Topic.SO_PHEP_TINH, "7 × 8 = ?", "56",
        listOf("54", "48", "64"), "7 × 8 = 56."),
    fixed(3, Topic.SO_PHEP_TINH, "72 : 9 = ?", "8",
        listOf("7", "9", "6"), "Vì 9 × 8 = 72 nên 72 : 9 = 8."),
    fixed(3, Topic.HINH_HOC, "Chu vi hình vuông cạnh 6cm là bao nhiêu?", "24 cm",
        listOf("12 cm", "36 cm", "18 cm"), "Chu vi hình vuông = cạnh × 4 = 6 × 4 = 24 cm."),
    fixed(3, Topic.DO_LUONG, "2 kg = ? g", "2000 g",
        listOf("200 g", "20 g", "20000 g"), "1 kg = 1000 g nên 2 kg = 2000 g."),
    fixed(3, Topic.LOI_VAN, "Một thùng có 24 chai nước, chia đều vào 4 hộp. Mỗi hộp có bao nhiêu chai?", "6 chai",
        listOf("4 chai", "8 chai", "20 chai"), "Mỗi hộp có 24 : 4 = 6 chai."),
    // Lớp 4
    fixed(4, Topic.SO_PHEP_TINH, "Số lớn nhất có 4 chữ số là số nào?", "9999",
        listOf("1000", "999", "10000"), "Số lớn nhất có 4 chữ số là 9999."),
    fixed(4, Topic.PHAN_SO_THAP_PHAN, "1/2 + 1/4 = ?", "3/4",
        listOf("2/6", "1/6", "2/4"), "1/2 = 2/4 nên 2/4 + 1/4 = 3/4."),
    fixed(4, Topic.PHAN_SO_THAP_PHAN, "Phân số nào bé hơn 1?", "2/5",
        listOf("5/2", "7/3", "4/3"), "Phân số có tử số nhỏ hơn mẫu số thì bé hơn 1."),
    fixed(4, Topic.HINH_HOC, "Diện tích hình chữ nhật dài 8cm, rộng 5cm là bao nhiêu?", "40 cm²",
        listOf("26 cm²", "13 cm²", "45 cm²"), "Diện tích = 8 × 5 = 40 cm²."),
    fixed(4, Topic.DO_LUONG, "3 m 5 dm = ? dm", "35 dm",
        listOf("305 dm", "53 dm", "350 dm"), "3 m = 30 dm nên 3 m 5 dm = 35 dm."),
    fixed(4, Topic.LOI_VAN, "Trung bình cộng của 12 và 20 là bao nhiêu?", "16",
        listOf("32", "8", "15"), "Trung bình cộng = (12 + 20) : 2 = 16."),
    // Lớp 5
    fixed(5, Topic.SO_PHEP_TINH, "12,5 + 7,5 = ?", "20",
        listOf("19", "20,5", "19,10"), "12,5 + 7,5 = 20."),
    fixed(5, Topic.PHAN_SO_THAP_PHAN, "0,5 viết dưới dạng phân số là bao nhiêu?", "1/2",
        listOf("1/5", "1/4", "2/1"), "0,5 = 5/10 = 1/2."),
    fixed(5, Topic.PHAN_SO_THAP_PHAN, "25% của 200 là bao nhiêu?", "50",
        listOf("25", "75", "100"), "25% của 200 là 200 × 25 : 100 = 50."),
    fixed(5, Topic.HINH_HOC, "Thể tích hình hộp chữ nhật dài 5cm, rộng 4cm, cao 3cm là bao nhiêu?", "60 cm³",
        listOf("12 cm³", "20 cm³", "47 cm³"), "Thể tích = 5 × 4 × 3 = 60 cm³."),
    fixed(5, Topic.DO_LUONG, "2,5 km = ? m", "2500 m",
        listOf("250 m", "25 m", "25000 m"), "1 km = 1000 m nên 2,5 km = 2500 m."),
    fixed(5, Topic.LOI_VAN, "Một ô tô đi 60 km trong 2 giờ. Vận tốc của ô tô là bao nhiêu?", "30 km/giờ",
        listOf("120 km/giờ", "62 km/giờ", "58 km/giờ"), "Vận tốc = quãng đường : thời gian = 60 : 2 = 30 km/giờ."),
    fixed(5, Topic.LOI_VAN, "Lớp 5A có 40 học sinh, trong đó 50% là học sinh nữ. Hỏi lớp có bao nhiêu học sinh nữ?", "20 học sinh",
        listOf("10 học sinh", "30 học sinh", "40 học sinh"), "50% của 40 là 40 × 50 : 100 = 20 học sinh."),
    // Toán nâng cao viết tay, mỗi lớp 1 câu mẫu
    fixed(1, Topic.NANG_CAO, "Dãy số: 2, 4, 6, ?. Số tiếp theo là số nào?", "8",
        listOf("7", "9", "10"), "Dãy số tăng đều 2 một lần, số tiếp theo là 6 + 2 = 8."),
    fixed(2, Topic.NANG_CAO, "Tìm x: x + 27 = 50", "23",
        listOf("27", "77", "22"), "x = 50 - 27 = 23."),
    fixed(3, Topic.NANG_CAO, "(12 + 8) × 5 = ?", "100",
        listOf("60", "90", "110"), "Tính trong ngoặc trước: 12 + 8 = 20, rồi 20 × 5 = 100."),
    fixed(4, Topic.NANG_CAO, "Tổng của hai số là 100, hiệu của hai số là 20. Số lớn là số nào?", "60",
        listOf("40", "80", "50"), "Số lớn = (100 + 20) : 2 = 60."),
    fixed(5, Topic.NANG_CAO, "Một cửa hàng giảm giá 20% cho một món hàng giá 500.000 đồng. Hỏi sau khi giảm, món hàng còn bao nhiêu tiền?", "400.000 đồng",
        listOf("480.000 đồng", "100.000 đồng", "300.000 đồng"), "Số tiền giảm là 500.000 × 20 : 100 = 100.000 đồng, còn lại 400.000 đồng.")
)
