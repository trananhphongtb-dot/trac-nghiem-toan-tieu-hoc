package vn.phong.baitaptuan.data

import vn.phong.baitaptuan.model.Question
import kotlin.random.Random

/**
 * Câu Toán tư duy (quy luật dãy số, tìm số, bài toán đố, hình học vui...) theo kiểu
 * các đề luyện thi trên mạng. Toàn bộ câu do app tự viết, không chép từ đề nào.
 * Mỗi đề Toán sẽ trộn thêm vài câu này vào các câu sinh ngẫu nhiên.
 */
private val TU_DUY: Map<Int, String> = mapOf(
    1 to """
Số tiếp theo của dãy 2, 4, 6, 8, … là:|10|9|12|11||Mỗi số hơn số đứng trước 2 đơn vị.
Dãy 1, 3, 5, 7, … có số tiếp theo là:|9|8|10|11||Mỗi số hơn số trước 2.
Số bé nhất có hai chữ số là:|10|11|9|0||Số bé nhất có hai chữ số là 10.
Số lớn nhất có một chữ số là:|9|10|8|1||Số lớn nhất có một chữ số là 9.
Có 5 con gà và 3 con vịt. Có tất cả bao nhiêu con?|8|7|9|2||5 + 3 = 8.
Lan có 9 cái kẹo, cho bạn 4 cái. Lan còn lại:|5|4|6|13||9 − 4 = 5.
3 + … = 10. Số cần điền vào chỗ chấm là:|7|6|8|13||10 − 3 = 7.
Hình tam giác có mấy cạnh?|3|4|2|5||Hình tam giác có 3 cạnh.
Số liền sau của 19 là:|20|18|21|10||19 + 1 = 20.
Số liền trước của 30 là:|29|31|20|28||30 − 1 = 29.
10 − 6 + 2 = ?|6|2|8|4||10 − 6 = 4, rồi 4 + 2 = 6.
An cao hơn Bình, Bình cao hơn Chi. Ai thấp nhất?|Chi|An|Bình|Cả ba bằng nhau||Chi thấp hơn Bình, Bình thấp hơn An.
Có bao nhiêu số có một chữ số lớn hơn 6?|3|2|4|9||Đó là 7, 8, 9.
""",
    2 to """
Số tiếp theo của dãy 5, 10, 15, 20, … là:|25|30|35|24||Mỗi số hơn số trước 5.
Số lớn nhất có hai chữ số là:|99|100|90|98||Số lớn nhất có hai chữ số là 99.
Mỗi hộp có 5 cái bút. 6 hộp như vậy có bao nhiêu cái bút?|30|11|25|35||5 × 6 = 30.
Tìm x: x + 17 = 40|23|57|13|33||x = 40 − 17 = 23.
Tìm x: x − 12 = 30|42|18|32|52||x = 30 + 12 = 42.
Hiệu của 50 và 18 là:|32|68|42|38||50 − 18 = 32.
36 : 4 = ?|9|8|7|10||36 : 4 = 9.
Số tiếp theo của dãy 1, 2, 4, 8, … là:|16|12|10|14||Mỗi số gấp đôi số trước.
Tổng của ba số 12, 8 và 10 là:|30|20|28|32||12 + 8 + 10 = 30.
Hình vuông có cạnh 4 cm thì chu vi là:|16 cm|8 cm|12 cm|4 cm||4 × 4 = 16 (cm).
Có 3 hàng ghế, mỗi hàng 7 ghế, thêm 4 ghế nữa. Tất cả có bao nhiêu ghế?|25|21|24|28||7 × 3 = 21, rồi 21 + 4 = 25.
Số liền sau của số lớn nhất có hai chữ số là:|100|99|101|90||99 + 1 = 100.
Bố hơn con 28 tuổi. Con 7 tuổi thì bố bao nhiêu tuổi?|35|21|28|42||7 + 28 = 35.
""",
    3 to """
Số tiếp theo của dãy 3, 6, 12, 24, … là:|48|36|30|42||Mỗi số gấp đôi số trước.
Số tiếp theo của dãy 1, 4, 9, 16, … là:|25|20|24|36||Các số chính phương: 5 × 5 = 25.
Số lớn nhất có ba chữ số khác nhau là:|987|999|989|997||Chọn các chữ số lớn nhất, khác nhau: 9, 8, 7.
Số bé nhất có ba chữ số khác nhau là:|102|100|101|123||Hàng trăm là 1, rồi 0, rồi 2.
Hình chữ nhật dài 12 m, rộng 5 m có chu vi là:|34 m|17 m|60 m|30 m||(12 + 5) × 2 = 34 (m).
Hình vuông cạnh 6 cm có diện tích là:|36 cm²|24 cm²|12 cm²|6 cm²||6 × 6 = 36 (cm²).
Tìm x: x × 6 = 54|9|8|48|60||x = 54 : 6 = 9.
45 gấp mấy lần 5?|9|40|8|50||45 : 5 = 9.
Giảm 36 đi 4 lần thì được:|9|32|40|144||36 : 4 = 9.
Mẹ 32 tuổi, con 8 tuổi. Tuổi mẹ gấp mấy lần tuổi con?|4|24|3|5||32 : 8 = 4.
Số chẵn lớn nhất có ba chữ số là:|998|999|996|988||999 là số lẻ nên số chẵn lớn nhất là 998.
1 + 2 + 3 + 4 + 5 + 6 + 7 + 8 + 9 + 10 = ?|55|45|50|110||Ghép cặp: 11 × 5 = 55.
Hình chữ nhật có chu vi 24 cm, chiều rộng 4 cm. Chiều dài là:|8 cm|20 cm|6 cm|10 cm||Nửa chu vi là 12 cm, chiều dài 12 − 4 = 8 (cm).
""",
    4 to """
Số tiếp theo của dãy 2, 5, 10, 17, 26, … là:|37|35|36|38||Các số bằng bình phương cộng 1: 6 × 6 + 1 = 37.
Trung bình cộng của 12, 18 và 24 là:|18|54|24|16||(12 + 18 + 24) : 3 = 18.
Tổng hai số là 60, hiệu hai số là 12. Số bé là:|24|36|48|12||(60 − 12) : 2 = 24.
Số chia hết cho cả 2 và 5 thì có chữ số tận cùng là:|0|5|2|1||Chia hết cho 2 và 5 thì tận cùng là 0.
Số nào sau đây chia hết cho 9?|234|235|236|237||2 + 3 + 4 = 9 chia hết cho 9.
Số lớn nhất có bốn chữ số là:|9999|10000|9990|9899||Số lớn nhất có bốn chữ số là 9999.
Phân số nào bằng 1/2?|3/6|2/6|3/5|1/4||3/6 = 1/2.
3/4 của 20 là:|15|12|5|16||20 : 4 × 3 = 15.
Hình bình hành có:|hai cặp cạnh đối song song|chỉ một cặp cạnh song song|bốn góc vuông|ba cạnh bằng nhau||Hai cặp cạnh đối song song và bằng nhau.
25 × 4 × 8 = ?|800|200|400|1000||25 × 4 = 100, rồi 100 × 8 = 800.
125 × 8 = ?|1000|800|1200|900||125 × 8 = 1000.
5 người làm xong một việc trong 6 ngày. Cũng việc đó, 10 người làm (cùng năng suất) trong:|3 ngày|12 ngày|6 ngày|2 ngày||Nhiều người gấp đôi thì thời gian giảm một nửa.
1 kg + 500 g = ?|1500 g|600 g|1050 g|150 g||1 kg = 1000 g, cộng 500 g được 1500 g.
""",
    5 to """
0,25 bằng phân số nào?|1/4|1/5|1/2|2/5||0,25 = 25/100 = 1/4.
25% của 80 là:|20|25|32|40||80 : 100 × 25 = 20.
Một xe đi 60 km trong 1,5 giờ. Vận tốc của xe là:|40 km/giờ|90 km/giờ|60 km/giờ|45 km/giờ||60 : 1,5 = 40.
Tìm x: x : 2,5 = 4|10|1,6|6,5|8||x = 4 × 2,5 = 10.
Hình tròn bán kính 5 cm có chu vi (lấy π = 3,14) là:|31,4 cm|15,7 cm|78,5 cm|25 cm||5 × 2 × 3,14 = 31,4 (cm).
Hình tròn bán kính 10 cm có diện tích (lấy π = 3,14) là:|314 cm²|62,8 cm²|31,4 cm²|100 cm²||10 × 10 × 3,14 = 314 (cm²).
3/5 + 1/5 = ?|4/5|3/10|4/10|2/5||Cùng mẫu số, cộng tử số: 4/5.
Số 2025 chia hết cho số nào sau đây?|5|2|4|10||2025 tận cùng là 5 nên chia hết cho 5, không chia hết cho 2, 4, 10.
Hình lập phương cạnh 3 cm có thể tích là:|27 cm³|9 cm³|18 cm³|81 cm³||3 × 3 × 3 = 27 (cm³).
Số tiếp theo của dãy 1, 1, 2, 3, 5, 8, … là:|13|11|12|10||Mỗi số bằng tổng hai số đứng trước: 5 + 8 = 13.
Khi chia một số thập phân cho 10, dấu phẩy:|dịch sang trái 1 chữ số|dịch sang phải 1 chữ số|không đổi chỗ|dịch sang trái 2 chữ số||Chia cho 10 thì dấu phẩy dịch sang trái 1 chữ số.
1,2 giờ bằng bao nhiêu phút?|72|120|12|62||1,2 × 60 = 72 (phút).
Một số nhân với 3 được 4,5. Số đó là:|1,5|13,5|15|0,15||4,5 : 3 = 1,5.
"""
)

/**
 * Thay [take] câu ngẫu nhiên trong đề [base] bằng câu Toán tư duy của lớp [grade].
 * Số câu của đề không đổi.
 */
fun mixToanTuDuy(base: List<Question>, grade: Int, take: Int = 5): List<Question> {
    val block = TU_DUY[grade] ?: return base
    val random = Random(System.nanoTime())
    val extra = items(block).shuffled(random).take(take).mapNotNull { item ->
        val wrongs = item.wrong.filter { it != item.correct && it.isNotBlank() }.distinct()
        if (wrongs.size < 3) null else {
            val options = (listOf(item.correct) + wrongs.shuffled(random).take(3)).shuffled(random)
            Question(item.q, options, options.indexOf(item.correct), item.expl.ifBlank { "Đáp án đúng là “${item.correct}”." }, "TUDUY")
        }
    }
    if (extra.isEmpty() || base.size < extra.size) return base
    val result = base.toMutableList()
    val slots = result.indices.shuffled(random).take(extra.size)
    slots.forEachIndexed { i, pos -> result[pos] = extra[i] }
    return result
}
