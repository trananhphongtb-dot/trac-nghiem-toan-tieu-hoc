package vn.phong.toantieuhoc.data

import vn.phong.toantieuhoc.model.ExamKind
import vn.phong.toantieuhoc.model.WeekDef

/**
 * Khung tuần theo báo cáo nghiên cứu bài tập tuần (bộ Kết nối tri thức làm chính).
 * - Lớp 2 và lớp 5: bảng tuần đầy đủ từng tuần, đã xác minh.
 * - Lớp 1, 3, 4: một số tuần ghi estimated = true là tuần ước tính theo giai đoạn
 *   (báo cáo ghi dấu ~), không phải tuần chính xác trong PPCT; trường có thể lệch 1 tuần.
 * - Các khoảng tuần chồng nhau (ví dụ tuần 8 vừa kết thúc chủ đề cũ vừa bắt đầu
 *   chủ đề mới) được xử lý theo nguyên tắc: khoảng khai sau thắng, vì đó là
 *   chủ đề đang học ở tuần chuyển tiếp đó. Nội dung ôn tập vẫn quay lại ở đề kỳ.
 * - Không bịa tuần ngoài báo cáo: mọi tiêu đề dưới đây đều nằm trong một khoảng
 *   của báo cáo; tuần 16 lớp 1 (không có trong báo cáo) được ghi là luyện tập chung.
 */
private data class RangeEntry(
    val grade: Int,
    val from: Int,
    val to: Int,
    val title: String,
    val estimated: Boolean,
    val forms: List<String>
)

private val RANGES = listOf(
    // ---------------- Lớp 1 ----------------
    RangeEntry(1, 1, 7, "Các số từ 0 đến 10", false, listOf("READ_NUM", "COMP_MAX", "COMP_MIN", "PRED_SUCC", "ADD", "SUB", "DECOMP", "COMP_LIST_MAX")),
    RangeEntry(1, 7, 8, "Hình phẳng: vuông, tròn, tam giác, chữ nhật", true, listOf("SHAPE", "READ_NUM", "COMP_MAX", "PRED_SUCC", "GROUP_COUNT", "COMP_LIST_MAX", "COMP_LIST_MIN", "EVENODD_LIST")),
    RangeEntry(1, 9, 10, "Phép cộng trong phạm vi 10", true, listOf("ADD", "MISS_ADD", "COMP_SUM", "WORD_ADD", "COMP_LIST_MAX", "COMP_LIST_MIN", "EVENODD_LIST", "PLACE_TENS_UNITS")),
    RangeEntry(1, 11, 12, "Phép trừ trong phạm vi 10", true, listOf("SUB", "MISS_SUB", "WORD_SUB", "COMP_SUM", "COMP_LIST_MAX", "COMP_LIST_MIN", "EVENODD_LIST", "PLACE_TENS_UNITS")),
    RangeEntry(1, 13, 13, "Bảng cộng, bảng trừ trong phạm vi 10", true, listOf("ADD", "SUB", "MISS_ADD", "MISS_SUB", "COMP_LIST_MAX", "COMP_LIST_MIN", "EVENODD_LIST", "PLACE_TENS_UNITS")),
    RangeEntry(1, 14, 15, "Hình khối và vị trí trong không gian", true, listOf("SHAPE", "COMP_MAX", "READ_NUM", "ADD", "GROUP_COUNT", "COMP_LIST_MAX", "COMP_LIST_MIN", "EVENODD_LIST")),
    RangeEntry(1, 16, 16, "Luyện tập chung", true, listOf("ADD", "SUB", "SHAPE", "READ_NUM", "COMP_LIST_MAX", "COMP_LIST_MIN", "EVENODD_LIST", "PLACE_TENS_UNITS")),
    RangeEntry(1, 17, 18, "Ôn tập học kỳ 1", true, listOf("ADD", "SUB", "SHAPE", "COMP_MAX", "READ_NUM", "PRED_SUCC", "COMP_LIST_MAX", "COMP_LIST_MIN")),
    RangeEntry(1, 19, 20, "Số có hai chữ số", false, listOf("PLACE", "READ_NUM", "DECOMP", "COMP_MAX", "COMP_LIST_MAX", "COMP_LIST_MIN", "PLACE_TENS_UNITS", "PLACE_DIGIT_VALUE")),
    RangeEntry(1, 21, 22, "So sánh số có hai chữ số, bảng 1 đến 100", true, listOf("COMP_MAX", "COMP_MIN", "PRED_SUCC", "SEQ_NEXT", "PLACE", "COMP_LIST_MAX", "COMP_LIST_MIN", "PLACE_TENS_UNITS")),
    RangeEntry(1, 23, 24, "Độ dài và đo độ dài (cm)", true, listOf("CONV_LEN", "COMP_MAX", "ADD", "SUB", "UNIT_CONV_SIMPLE", "COMP_LIST_MAX", "PLACE_DIGIT_VALUE", "COMP_LIST_MIN")),
    RangeEntry(1, 25, 30, "Cộng, trừ không nhớ trong phạm vi 100", true, listOf("ADD", "SUB", "MISS_ADD", "MISS_SUB", "WORD_ADD", "WORD_SUB", "COMP_LIST_MAX", "COMP_LIST_MIN", "WORD_GROUP", "ADD_SUB_CHAIN")),
    RangeEntry(1, 31, 33, "Thời gian: giờ và lịch", true, listOf("CLOCK", "WEEKDAY", "ADD", "SUB", "COMP_LIST_MAX", "GROUP_COUNT", "MONEY_CHANGE", "COMP_LIST_MIN", "WORD_GROUP", "ADD_SUB_CHAIN")),
    RangeEntry(1, 34, 35, "Ôn tập cuối năm", true, listOf("ADD", "SUB", "PLACE", "SHAPE", "CLOCK", "COMP_MAX", "READ_NUM", "COMP_LIST_MAX", "WORD_GROUP", "ADD_SUB_CHAIN")),
    // ---------------- Lớp 2 ----------------
    RangeEntry(2, 1, 3, "Ôn tập số đến 100, tia số, thành phần phép tính", false, listOf("READ_NUM", "COMP_MAX", "PRED_SUCC", "MISS_ADD", "MISS_SUB", "SEQ_NEXT", "COMP_LIST_MAX", "COMP_LIST_MIN", "ADD_SUB_CHAIN")),
    RangeEntry(2, 4, 8, "Cộng, trừ trong phạm vi 20 (qua 10, bảng)", false, listOf("ADD", "SUB", "MISS_ADD", "MISS_SUB", "WORD_ADD", "WORD_SUB", "COMP_LIST_MAX", "COMP_LIST_MIN", "ADD_SUB_CHAIN")),
    RangeEntry(2, 8, 10, "Khối lượng (kg) và dung tích (lít)", false, listOf("CONV_MASS", "COMP_MAX", "ADD", "SUB", "COMP_LIST_MAX", "COMP_LIST_MIN", "EVENODD_LIST", "PLACE_TENS_UNITS", "ADD_SUB_CHAIN")),
    RangeEntry(2, 10, 14, "Cộng, trừ có nhớ trong phạm vi 100", false, listOf("ADD", "SUB", "MISS_ADD", "MISS_SUB", "WORD_2STEP", "COMP_LIST_MAX", "COMP_LIST_MIN", "EVENODD_LIST", "ADD_SUB_CHAIN")),
    RangeEntry(2, 14, 15, "Hình phẳng: điểm, đoạn thẳng, đường gấp khúc", false, listOf("SHAPE", "ADD", "COMP_MAX", "UNIT_CONV_SIMPLE", "COMP_LIST_MAX", "PLACE_DIGIT_VALUE", "COMP_LIST_MIN", "EVENODD_LIST", "ADD_SUB_CHAIN")),
    RangeEntry(2, 15, 17, "Ngày, giờ, giờ, phút, ngày, tháng", false, listOf("CLOCK", "WEEKDAY", "TIME_ADD", "ADD", "COMP_LIST_MAX", "GROUP_COUNT", "MONEY_CHANGE", "COMP_LIST_MIN", "ADD_SUB_CHAIN")),
    RangeEntry(2, 17, 19, "Ôn tập học kỳ 1", false, listOf("ADD", "SUB", "SHAPE", "CLOCK", "COMP_MAX", "READ_NUM", "COMP_LIST_MAX", "COMP_LIST_MIN", "ADD_SUB_CHAIN")),
    RangeEntry(2, 19, 23, "Phép nhân, phép chia: bảng 2 và bảng 5", false, listOf("MUL", "DIV", "MISS_MUL", "MISS_DIV", "WORD_MUL", "WORD_DIV", "COMP_LIST_MAX", "COMP_LIST_MIN", "ADD_SUB_CHAIN", "EXPR_BIGGEST", "WORD_GROUP", "TRIPLE_PART")),
    RangeEntry(2, 23, 24, "Hình khối: khối trụ, khối cầu", false, listOf("SHAPE", "ADD", "MUL", "GROUP_COUNT", "COMP_LIST_MAX", "COMP_LIST_MIN", "EVENODD_LIST", "PLACE_TENS_UNITS", "ADD_SUB_CHAIN", "EXPR_BIGGEST", "WORD_GROUP", "TRIPLE_PART")),
    RangeEntry(2, 24, 27, "Các số trong phạm vi 1000", false, listOf("PLACE", "READ_NUM", "DECOMP", "COMP_MAX", "COMP_MIN", "SEQ_NEXT", "COMP_LIST_MAX", "COMP_LIST_MIN", "ADD_SUB_CHAIN", "EXPR_BIGGEST", "WORD_GROUP", "TRIPLE_PART")),
    RangeEntry(2, 27, 28, "Độ dài (dm, m, km) và tiền Việt Nam", false, listOf("CONV_LEN", "MONEY", "ADD", "SUB", "MONEY_CHANGE", "COMP_LIST_MAX", "GROUP_COUNT", "COMP_LIST_MIN", "ADD_SUB_CHAIN", "EXPR_BIGGEST", "WORD_GROUP", "TRIPLE_PART")),
    RangeEntry(2, 28, 31, "Cộng, trừ trong phạm vi 1000", false, listOf("ADD", "SUB", "MISS_ADD", "MISS_SUB", "WORD_2STEP", "COMP_LIST_MAX", "COMP_LIST_MIN", "EVENODD_LIST", "ADD_SUB_CHAIN", "EXPR_BIGGEST", "WORD_GROUP", "TRIPLE_PART")),
    RangeEntry(2, 31, 32, "Thống kê, xác suất: biểu đồ tranh, chắc chắn, có thể", false, listOf("CHART", "PROB", "ADD", "COMP_MAX", "COMP_LIST_MAX", "COMP_LIST_MIN", "EVENODD_LIST", "PLACE_TENS_UNITS", "ADD_SUB_CHAIN", "EXPR_BIGGEST", "WORD_GROUP", "TRIPLE_PART")),
    RangeEntry(2, 32, 35, "Ôn tập cuối năm", false, listOf("ADD", "SUB", "MUL", "DIV", "PLACE", "SHAPE", "CLOCK", "MONEY", "ADD_SUB_CHAIN", "EXPR_BIGGEST", "WORD_GROUP", "TRIPLE_PART")),
    // ---------------- Lớp 3 ----------------
    RangeEntry(3, 1, 3, "Ôn tập số đến 1000, bảng nhân, chia 2, 3, 4, 5", false, listOf("READ_NUM", "PLACE", "MUL", "DIV", "MISS_ADD", "MISS_MUL", "COMP_LIST_MAX", "COMP_LIST_MIN", "EXPR_BIGGEST", "WORD_GROUP", "ADD_SUB_CHAIN", "TRIPLE_PART")),
    RangeEntry(3, 4, 6, "Bảng nhân, bảng chia 6, 7, 8, 9, một phần mấy", true, listOf("MUL", "DIV", "MISS_MUL", "MISS_DIV", "FRAC_PART", "WORD_MUL", "COMP_LIST_MAX", "COMP_LIST_MIN", "EXPR_BIGGEST", "WORD_GROUP", "ADD_SUB_CHAIN", "TRIPLE_PART")),
    RangeEntry(3, 7, 9, "Hình phẳng, hình khối: trung điểm, góc, hình tròn", true, listOf("SHAPE", "ANGLE", "PERIM_RECT", "PERIM_SQ", "ADD", "GROUP_COUNT", "COMP_LIST_MAX", "COMP_LIST_MIN", "EXPR_BIGGEST", "WORD_GROUP", "ADD_SUB_CHAIN", "TRIPLE_PART")),
    RangeEntry(3, 9, 12, "Nhân, chia trong phạm vi 100, bài toán hai bước", true, listOf("MUL", "DIV", "DOUBLE_HALF", "WORD_2STEP", "EXPR", "COMP_LIST_MAX", "COMP_LIST_MIN", "EVENODD_LIST", "EXPR_BIGGEST", "WORD_GROUP", "ADD_SUB_CHAIN", "TRIPLE_PART")),
    RangeEntry(3, 13, 13, "Đơn vị đo: mm, gam, ml, nhiệt độ", true, listOf("CONV_LEN", "CONV_MASS", "COMP_MAX", "ADD", "UNIT_CONV_SIMPLE", "COMP_LIST_MAX", "PLACE_DIGIT_VALUE", "COMP_LIST_MIN", "EXPR_BIGGEST", "WORD_GROUP", "ADD_SUB_CHAIN", "TRIPLE_PART")),
    RangeEntry(3, 14, 16, "Nhân, chia trong phạm vi 1000, biểu thức số", true, listOf("MUL", "DIV", "EXPR", "COMP_SUM", "WORD_2STEP", "COMP_LIST_MAX", "COMP_LIST_MIN", "PLACE_TENS_UNITS", "EXPR_BIGGEST", "WORD_GROUP", "ADD_SUB_CHAIN", "TRIPLE_PART")),
    RangeEntry(3, 17, 18, "Ôn tập học kỳ 1", false, listOf("MUL", "DIV", "SHAPE", "EXPR", "ADD", "SUB", "COMP_LIST_MAX", "COMP_LIST_MIN", "EXPR_BIGGEST", "WORD_GROUP", "ADD_SUB_CHAIN", "TRIPLE_PART")),
    RangeEntry(3, 19, 21, "Các số đến 10000, số La Mã, làm tròn", false, listOf("PLACE", "READ_NUM", "ROMAN", "ROUND", "COMP_MAX", "SEQ_NEXT", "COMP_LIST_MAX", "COMP_LIST_MIN", "EXPR_BIGGEST", "WORD_GROUP", "ADD_SUB_CHAIN", "TRIPLE_PART")),
    RangeEntry(3, 21, 22, "Chu vi, diện tích hình phẳng", false, listOf("PERIM_RECT", "PERIM_SQ", "PERIM_TRI", "AREA_RECT", "AREA_SQ", "ADD", "GROUP_COUNT", "COMP_LIST_MAX", "EXPR_BIGGEST", "WORD_GROUP", "ADD_SUB_CHAIN", "TRIPLE_PART")),
    RangeEntry(3, 23, 25, "Cộng, trừ, nhân, chia trong phạm vi 10000", false, listOf("ADD", "SUB", "MUL", "DIV", "WORD_2STEP", "EXPR", "COMP_LIST_MAX", "COMP_LIST_MIN", "EXPR_BIGGEST", "WORD_GROUP", "ADD_SUB_CHAIN", "TRIPLE_PART")),
    RangeEntry(3, 26, 27, "Các số đến 100000, làm tròn", false, listOf("PLACE", "ROUND", "COMP_MAX", "READ_NUM", "SEQ_NEXT", "COMP_LIST_MAX", "COMP_LIST_MIN", "PLACE_TENS_UNITS", "EXPR_BIGGEST", "WORD_GROUP", "ADD_SUB_CHAIN", "TRIPLE_PART")),
    RangeEntry(3, 28, 28, "Cộng, trừ trong phạm vi 100000", false, listOf("ADD", "SUB", "MISS_ADD", "WORD_ADD", "COMP_LIST_MAX", "COMP_LIST_MIN", "EVENODD_LIST", "PLACE_TENS_UNITS", "EXPR_BIGGEST", "WORD_GROUP", "ADD_SUB_CHAIN", "TRIPLE_PART")),
    RangeEntry(3, 29, 30, "Xem đồng hồ, tháng, năm, tiền Việt Nam", false, listOf("CLOCK", "WEEKDAY", "MONEY", "TIME_ADD", "MONEY_CHANGE", "COMP_LIST_MAX", "GROUP_COUNT", "COMP_LIST_MIN", "EXPR_BIGGEST", "WORD_GROUP", "ADD_SUB_CHAIN", "TRIPLE_PART")),
    RangeEntry(3, 30, 32, "Nhân, chia trong phạm vi 100000, bảng số liệu", false, listOf("MUL", "DIV", "CHART", "WORD_MUL", "ADD", "COMP_LIST_MAX", "COMP_LIST_MIN", "PLACE_TENS_UNITS", "EXPR_BIGGEST", "WORD_GROUP", "ADD_SUB_CHAIN", "TRIPLE_PART")),
    RangeEntry(3, 33, 35, "Khả năng xảy ra và ôn tập cuối năm", false, listOf("PROB", "CHART", "MUL", "DIV", "AREA_RECT", "MONEY", "ADD", "SUB", "EXPR_BIGGEST", "WORD_GROUP", "ADD_SUB_CHAIN", "TRIPLE_PART")),
    // ---------------- Lớp 4 ----------------
    RangeEntry(4, 1, 3, "Ôn tập số đến 100000, số chẵn, lẻ, biểu thức chứa chữ", false, listOf("READ_NUM", "EVEN_ODD", "EXPR", "WORD_2STEP", "COMP_MAX", "COMP_LIST_MAX", "COMP_LIST_MIN", "PLACE_TENS_UNITS", "EXPR_BIGGEST", "WORD_GROUP", "ADD_SUB_CHAIN", "TRIPLE_PART")),
    RangeEntry(4, 3, 4, "Góc và đơn vị đo góc", true, listOf("ANGLE", "SHAPE", "COMP_MAX", "UNIT_CONV_SIMPLE", "COMP_LIST_MAX", "PLACE_DIGIT_VALUE", "COMP_LIST_MIN", "EVENODD_LIST", "EXPR_BIGGEST", "WORD_GROUP", "ADD_SUB_CHAIN", "TRIPLE_PART")),
    RangeEntry(4, 4, 7, "Số có nhiều chữ số, lớp triệu, làm tròn", true, listOf("PLACE", "ROUND", "COMP_MAX", "READ_NUM", "SEQ_NEXT", "PRED_SUCC", "COMP_LIST_MAX", "COMP_LIST_MIN", "EXPR_BIGGEST", "WORD_GROUP", "ADD_SUB_CHAIN", "TRIPLE_PART")),
    RangeEntry(4, 7, 10, "Đơn vị đo: yến, tạ, tấn, dm², m², giây, thế kỷ", true, listOf("CONV_MASS", "CONV_AREA", "CONV_TIME", "ADD", "COMP_MAX", "UNIT_CONV_SIMPLE", "COMP_LIST_MAX", "PLACE_DIGIT_VALUE", "EXPR_BIGGEST", "WORD_GROUP", "ADD_SUB_CHAIN", "TRIPLE_PART")),
    RangeEntry(4, 10, 13, "Cộng, trừ số có nhiều chữ số, bài toán tổng, hiệu", true, listOf("ADD", "SUB", "SUM_DIFF", "WORD_2STEP", "EXPR", "COMP_LIST_MAX", "COMP_LIST_MIN", "PLACE_TENS_UNITS", "EXPR_BIGGEST", "WORD_GROUP", "ADD_SUB_CHAIN", "TRIPLE_PART")),
    RangeEntry(4, 13, 16, "Đường thẳng vuông góc, song song, hình bình hành, thoi", true, listOf("SHAPE", "ANGLE", "PERIM_RECT", "ADD", "GROUP_COUNT", "COMP_LIST_MAX", "COMP_LIST_MIN", "EVENODD_LIST", "EXPR_BIGGEST", "WORD_GROUP", "ADD_SUB_CHAIN", "TRIPLE_PART")),
    RangeEntry(4, 17, 18, "Ôn tập học kỳ 1", false, listOf("ADD", "SUB", "PLACE", "SHAPE", "CONV_MASS", "EXPR", "COMP_LIST_MAX", "COMP_LIST_MIN", "EXPR_BIGGEST", "WORD_GROUP", "ADD_SUB_CHAIN", "TRIPLE_PART")),
    RangeEntry(4, 19, 19, "Nhân, chia với số có một chữ số", false, listOf("MUL", "DIV", "MISS_MUL", "WORD_MUL", "COMP_LIST_MAX", "COMP_LIST_MIN", "PLACE_TENS_UNITS", "PLACE_DIGIT_VALUE", "EXPR_BIGGEST", "WORD_GROUP", "ADD_SUB_CHAIN", "TRIPLE_PART")),
    RangeEntry(4, 20, 20, "Nhân, chia với 10, 100, 1000, tính chất phép nhân", false, listOf("MUL10", "EXPR", "MUL", "DIV", "COMP_LIST_MAX", "COMP_LIST_MIN", "EVENODD_LIST", "PLACE_TENS_UNITS", "EXPR_BIGGEST", "WORD_GROUP", "ADD_SUB_CHAIN", "TRIPLE_PART")),
    RangeEntry(4, 21, 21, "Nhân với số có hai chữ số, tính chất phân phối", false, listOf("MUL", "EXPR", "WORD_MUL", "ADD", "COMP_LIST_MAX", "COMP_LIST_MIN", "PLACE_TENS_UNITS", "PLACE_DIGIT_VALUE", "EXPR_BIGGEST", "WORD_GROUP", "ADD_SUB_CHAIN", "TRIPLE_PART")),
    RangeEntry(4, 22, 22, "Chia cho số có hai chữ số, ước lượng thương", false, listOf("DIV", "MISS_DIV", "WORD_DIV", "SUB", "COMP_LIST_MAX", "COMP_LIST_MIN", "PLACE_TENS_UNITS", "PLACE_DIGIT_VALUE", "EXPR_BIGGEST", "WORD_GROUP", "ADD_SUB_CHAIN", "TRIPLE_PART")),
    RangeEntry(4, 23, 23, "Trung bình cộng, rút về đơn vị", false, listOf("AVG", "WORD_2STEP", "ADD", "DIV", "UNIT_CONV_SIMPLE", "COMP_LIST_MAX", "PLACE_DIGIT_VALUE", "COMP_LIST_MIN", "EXPR_BIGGEST", "WORD_GROUP", "ADD_SUB_CHAIN", "TRIPLE_PART")),
    RangeEntry(4, 24, 25, "Thống kê: dãy số liệu, biểu đồ cột, số lần xuất hiện", false, listOf("CHART", "PROB", "AVG", "ADD", "COMP_LIST_MAX", "COMP_LIST_MIN", "PLACE_TENS_UNITS", "PLACE_DIGIT_VALUE", "EXPR_BIGGEST", "WORD_GROUP", "ADD_SUB_CHAIN", "TRIPLE_PART")),
    RangeEntry(4, 26, 26, "Khái niệm phân số, phân số và phép chia", false, listOf("FRAC_PART", "FRAC_CMP", "READ_NUM", "ADD", "COMP_LIST_MAX", "COMP_LIST_MIN", "PLACE_TENS_UNITS", "PLACE_DIGIT_VALUE", "EXPR_BIGGEST", "WORD_GROUP", "ADD_SUB_CHAIN", "TRIPLE_PART")),
    RangeEntry(4, 27, 27, "Rút gọn phân số, phân số bằng nhau", false, listOf("FRAC_SIMP", "FRAC_CMP", "FRAC_PART", "MUL", "COMP_LIST_MAX", "COMP_LIST_MIN", "PLACE_TENS_UNITS", "PLACE_DIGIT_VALUE", "EXPR_BIGGEST", "WORD_GROUP", "ADD_SUB_CHAIN", "TRIPLE_PART")),
    RangeEntry(4, 28, 28, "Quy đồng mẫu số, so sánh phân số", false, listOf("FRAC_CMP", "FRAC_PART", "MUL", "DIV", "COMP_LIST_MAX", "COMP_LIST_MIN", "PLACE_TENS_UNITS", "PLACE_DIGIT_VALUE", "EXPR_BIGGEST", "WORD_GROUP", "ADD_SUB_CHAIN", "TRIPLE_PART")),
    RangeEntry(4, 29, 29, "Cộng, trừ phân số", false, listOf("FRAC_ADD", "FRAC_SUB", "WORD_ADD", "WORD_SUB", "COMP_LIST_MAX", "COMP_LIST_MIN", "PLACE_TENS_UNITS", "PLACE_DIGIT_VALUE", "EXPR_BIGGEST", "WORD_GROUP", "ADD_SUB_CHAIN", "TRIPLE_PART")),
    RangeEntry(4, 30, 30, "Nhân, chia phân số, tìm phân số của một số", false, listOf("FRAC_MUL", "FRAC_OF", "FRAC_ADD", "WORD_MUL", "COMP_LIST_MAX", "COMP_LIST_MIN", "PLACE_TENS_UNITS", "PLACE_DIGIT_VALUE", "EXPR_BIGGEST", "WORD_GROUP", "ADD_SUB_CHAIN", "TRIPLE_PART")),
    RangeEntry(4, 31, 35, "Ôn tập cuối năm", false, listOf("ADD", "SUB", "MUL", "DIV", "FRAC_ADD", "FRAC_CMP", "SHAPE", "CHART", "AVG", "EXPR_BIGGEST", "WORD_GROUP", "ADD_SUB_CHAIN", "TRIPLE_PART")),
    // ---------------- Lớp 5 ----------------
    RangeEntry(5, 1, 4, "Ôn tập số tự nhiên, phân số, hỗn số", false, listOf("READ_NUM", "FRAC_PART", "FRAC_ADD", "FRAC_SUB", "FRAC_CMP", "ADD")),
    RangeEntry(5, 4, 7, "Số thập phân: khái niệm, so sánh, làm tròn", false, listOf("DEC_PLACE", "DEC_CMP", "ROUND", "READ_NUM", "PLACE")),
    RangeEntry(5, 7, 8, "Đơn vị đo diện tích: km², ha", false, listOf("CONV_AREA", "COMP_MAX", "MUL")),
    RangeEntry(5, 8, 12, "Phép tính với số thập phân", false, listOf("DEC_ADD", "DEC_SUB", "DEC_MUL", "MUL10", "WORD_2STEP")),
    RangeEntry(5, 12, 15, "Hình phẳng: tam giác, thang, tròn", false, listOf("AREA_TRI", "AREA_TRAP", "AREA_CIRC", "PERIM_TRI", "PERIM_RECT")),
    RangeEntry(5, 15, 18, "Ôn tập học kỳ 1", false, listOf("DEC_ADD", "DEC_SUB", "AREA_TRI", "FRAC_ADD", "CONV_AREA", "ADD")),
    RangeEntry(5, 19, 22, "Tỉ số, tỉ số phần trăm, tỉ lệ bản đồ, tổng, hiệu, tỉ", false, listOf("PERCENT", "SUM_DIFF", "FRAC_OF", "WORD_2STEP", "COMP_MAX")),
    RangeEntry(5, 22, 24, "Thể tích: cm³, dm³, m³, hình khai triển", false, listOf("CONV_AREA", "VOL_BOX", "VOL_CUBE", "SHAPE")),
    RangeEntry(5, 24, 26, "Diện tích xung quanh, toàn phần, thể tích hộp, lập phương", false, listOf("VOL_BOX", "VOL_CUBE", "AREA_RECT", "AREA_SQ", "MUL")),
    RangeEntry(5, 26, 30, "Số đo thời gian, vận tốc, quãng đường, thời gian", false, listOf("CONV_TIME", "TIME_ADD", "SPEED", "WORD_2STEP", "ADD")),
    RangeEntry(5, 30, 31, "Thống kê: biểu đồ quạt tròn, tỉ số lần lặp lại", false, listOf("CHART", "PROB", "PERCENT", "ADD")),
    RangeEntry(5, 31, 35, "Ôn tập cuối năm", false, listOf("DEC_ADD", "DEC_SUB", "PERCENT", "VOL_BOX", "SPEED", "AREA_TRI", "FRAC_ADD", "CHART")),
)

/** Trả về định nghĩa tuần. Tuần nào cũng có, kể cả tuần chuyển tiếp. */
fun weekDef(grade: Int, week: Int): WeekDef {
    var found: RangeEntry? = null
    for (e in RANGES) {
        if (e.grade == grade && week in e.from..e.to) found = e
    }
    val e = found ?: RangeEntry(grade, week, week, "Luyện tập chung", true, listOf("ADD", "SUB", "COMP_MAX"))
    return WeekDef(grade, week, e.title, e.estimated, e.forms)
}

fun allWeeks(grade: Int): List<WeekDef> = (1..35).map { weekDef(grade, it) }

/** Các dạng câu của một đề kỳ = hợp tất cả dạng của các tuần trong phạm vi đề,
 *  giữ nguyên thứ tự xuất hiện, bỏ trùng. Đề kỳ vì vậy bao quát toàn bộ phạm vi,
 *  không chỉ dạng của một tuần. */
fun examForms(grade: Int, exam: ExamKind): List<String> {
    val out = LinkedHashSet<String>()
    for (w in exam.weeks) out.addAll(weekDef(grade, w).forms)
    return out.toList()
}
