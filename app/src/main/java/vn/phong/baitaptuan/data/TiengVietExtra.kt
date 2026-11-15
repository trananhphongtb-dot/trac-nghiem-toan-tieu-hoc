package vn.phong.baitaptuan.data

/** Tiếng Việt bổ sung: chính tả, từ trái nghĩa/đồng nghĩa, từ loại, kiểu câu và vần. */

private val TRAI_NGHIA_X = """
khen|chê
thắng|thua
sống|chết
mở|khép
trên|dưới
trong|ngoài
nặng|nhẹ
đầy|vơi
thật thà|dối trá
hiền|dữ
trẻ|già
đẹp|xấu
cứng|mềm
khó|dễ
buổi sáng|buổi tối
nông|sâu
dày|mỏng
thẳng|cong
"""

private val DONG_NGHIA_X = """
lợn|heo
ngô|bắp
quả|trái
máy bay|phi cơ
tàu hỏa|xe lửa
to lớn|khổng lồ
nhỏ bé|bé tí
bao la|mênh mông
hiền lành|nhân hậu
siêng năng|cần cù
"""

private val NGUOI_VAT_CAY = """
từ chỉ người|bác sĩ, giáo viên, nông dân, công nhân, kĩ sư, bộ đội, thợ may
từ chỉ con vật|con trâu, con ong, con cá, con voi, con gấu, con chim
từ chỉ cây cối|cây cam, cây tre, hoa sen, cây lúa, cây phượng, cây bàng
từ chỉ đồ vật|cái bàn, cái ghế, cái tủ, cặp sách, quyển vở, cái bút
"""

private val HOAT_DONG_DAC_DIEM = """
từ chỉ hoạt động|chạy, nhảy, đọc, viết, ăn, uống, quét, tưới
từ chỉ đặc điểm|cao, thấp, đẹp, ngoan, xanh, nhanh, hiền, chăm chỉ
từ chỉ sự vật|bàn, sách, mặt trời, dòng sông, cánh đồng, ngôi nhà
"""

private val MAU_CAU_2_3 = """
Ai là gì?|Mẹ em là giáo viên., Hà Nội là thủ đô của nước ta., Chú Nam là bộ đội., Bạn Lan là học sinh giỏi.
Ai làm gì?|Bố đang sửa xe., Em quét nhà giúp mẹ., Bà tưới cây trong vườn., Chị Hoa đọc sách.
Ai thế nào?|Bé Hoa rất ngoan., Con mèo nhà em rất lười., Cô giáo dịu dàng., Cánh đồng rộng mênh mông.
"""

private val KIEU_CAU = """
câu kể|Mẹ đang nấu cơm., Em học bài chăm chỉ., Mùa xuân đã đến., Bé Lan có một con mèo., Bác nông dân gặt lúa.
câu hỏi|Bạn tên là gì?, Hôm nay bạn đi đâu?, Ai đã làm đổ nước?, Vì sao trời mưa?, Mấy giờ em đi học?
câu cảm|Ôi cảnh đẹp quá!, Chao ơi trời nóng thật!, Con bé đáng yêu làm sao!, Hay quá!, Đẹp biết bao!
câu khiến|Con hãy đóng cửa lại., Em đừng nói chuyện trong lớp., Mời bác ngồi., Các bạn trật tự nào!, Hãy giữ gìn sách vở.
"""

private val CHINH_TA_23 = """
chăm chỉ|trăm chỉ|chăm trỉ|trăm trỉ
con trâu|con châu|con trau|con chau
sáng sủa|xáng xủa|sáng xủa|xáng sủa
xin lỗi|sin lỗi|xin nỗi|sin nỗi
giúp đỡ|dúp đỡ|giúp đở|dúp đở
dịu dàng|rịu dàng|giịu dàng|dịu giàng
rực rỡ|dực rỡ|rực rở|giực rỡ
nghỉ ngơi|nghĩ ngơi|nghỉ nghơi|nghĩ nghơi
lao động|nao động|lao đông|nao đông
nấu cơm|lấu cơm|nấu cờm|lấu cờm
truyện tranh|chuyện tranh|truyện chanh|chuyện chanh
cuộc sống|cuốc sống|cuộc xống|cuốc xống
sương mù|xương mù|sương mừ|xương mừ
trong trẻo|chong trẻo|trong chẻo|chong chẻo
"""

private val CHINH_TA_45 = """
xuất sắc|suất sắc|xuất xắc|suất xắc
chân thành|trân thành|chân thạnh|trân thạnh
trung thực|chung thực|trung thức|chung thức
chăm sóc|trăm sóc|chăm xóc|trăm xóc
sáng tạo|xáng tạo|sáng tạu|xáng tạu
gian nan|dan nan|giang nan|dian nan
lịch sự|lịt sự|lịch xự|lịt xự
nghiêm túc|nghiêm tút|nghiêm tụt|ngiêm túc
tự hào|tự hao|từ hào|tự hầo
bình tĩnh|bình tỉnh|bình tịnh|binh tĩnh
cần cù|cần cu|cầng cù|cần cữ
rộng rãi|rộng rải|rộng rãy|rộng rảy
"""

private val VAN_1 = """
Tiếng nào có vần “an”?|bàn|bài|bông|bút||Bàn có vần an.
Tiếng nào có vần “ăn”?|ăn|an|ân|anh||Ăn có vần ăn.
Tiếng nào có vần “ong”?|trong|trăng|tranh|trên||Trong có vần ong.
Tiếng nào có vần “ương”?|trường|trưa|trứng|trăng||Trường có vần ương.
Tiếng nào có vần “iên”?|biển|bia|bín|bên||Biển có vần iên.
Tiếng nào có vần “uôn”?|muốn|mua|mùn|mưa||Muốn có vần uôn.
Tiếng nào có âm đầu “ch”?|chim|trăng|thỏ|khỉ||Chim bắt đầu bằng ch.
Tiếng nào có âm đầu “nh”?|nhà|nai|ngà|nghe||Nhà bắt đầu bằng nh.
Chữ “q” luôn đi với chữ:|u|a|o|e||Ta viết qu.
Chữ “k” chỉ đi với các chữ:|e, ê, i|a, o, u|ư, ô, ơ|ă, â||k đi với e, ê, i.
Chữ “gh” chỉ đi với các chữ:|e, ê, i|a, o, u|ă, â|ư, ơ||gh đi với e, ê, i.
Chữ “ngh” chỉ đi với các chữ:|e, ê, i|a, o, u|ă, â|ư, ơ||ngh đi với e, ê, i.
Tiếng “bà” có dấu thanh gì?|dấu huyền|dấu sắc|dấu hỏi|dấu nặng||Bà có dấu huyền.
Tiếng “bá” có dấu thanh gì?|dấu sắc|dấu huyền|dấu ngã|dấu nặng||Bá có dấu sắc.
Tiếng “bả” có dấu thanh gì?|dấu hỏi|dấu ngã|dấu nặng|dấu sắc||Bả có dấu hỏi.
Tiếng “bã” có dấu thanh gì?|dấu ngã|dấu hỏi|dấu nặng|dấu huyền||Bã có dấu ngã.
Tiếng “bạ” có dấu thanh gì?|dấu nặng|dấu ngã|dấu hỏi|dấu sắc||Bạ có dấu nặng.
Câu “Em yêu mẹ.” có mấy tiếng?|3|2|4|5||Em, yêu, mẹ là 3 tiếng.
Câu “Trời mưa to.” có mấy tiếng?|3|2|4|5||Trời, mưa, to là 3 tiếng.
Từ “học sinh” có mấy tiếng?|2|1|3|4||Học và sinh là 2 tiếng.
Từ “cái bàn” có mấy tiếng?|2|1|3|4||Cái và bàn là 2 tiếng.
"""

internal fun tiengVietExtraTopics(): List<Topic> = listOf(
    // ---------------- Lớp 1 ----------------
    Topic(1, 1..9, "Vần và âm đầu (bổ sung)", items = items(VAN_1.lines().filter { it.isNotBlank() }.take(8).joinToString("\n"))),
    Topic(1, 10..18, "Quy tắc c/k, gh/ngh, q/qu (bổ sung)", items = items(VAN_1.lines().filter { it.isNotBlank() }.drop(8).take(5).joinToString("\n"))),
    Topic(1, 19..27, "Dấu thanh (bổ sung)", items = items(VAN_1.lines().filter { it.isNotBlank() }.drop(13).take(5).joinToString("\n"))),
    Topic(1, 28..35, "Đếm tiếng (bổ sung)", items = items(VAN_1.lines().filter { it.isNotBlank() }.drop(18).joinToString("\n"))),

    // ---------------- Lớp 2 ----------------
    Topic(2, 1..12, "Chính tả (bổ sung)", items = spell(CHINH_TA_23)),
    Topic(2, 9..20, "Từ trái nghĩa (bổ sung)",
        pairSets = listOf(PairSet("Từ trái nghĩa với “{k}” là:", "Từ trái nghĩa với “{v}” là:", pairList(TRAI_NGHIA_X), "“{k}” và “{v}” là hai từ trái nghĩa."))),
    Topic(2, 13..28, "Từ chỉ sự vật, hoạt động, đặc điểm (bổ sung)",
        catSets = listOf(CatSet("Từ nào là {c}?", catList(HOAT_DONG_DAC_DIEM), "“{w}” thuộc nhóm nào?"))),
    Topic(2, 21..35, "Mẫu câu Ai là gì, Ai làm gì, Ai thế nào",
        catSets = listOf(CatSet("Câu nào thuộc mẫu câu “{c}”?", catList(MAU_CAU_2_3), "Câu “{w}” thuộc mẫu câu nào?"))),

    // ---------------- Lớp 3 ----------------
    Topic(3, 1..12, "Chính tả (bổ sung)", items = spell(CHINH_TA_23)),
    Topic(3, 9..20, "Từ đồng nghĩa và trái nghĩa (bổ sung)",
        pairSets = listOf(
            PairSet("Từ đồng nghĩa với “{k}” là:", "Từ đồng nghĩa với “{v}” là:", pairList(DONG_NGHIA_X), "“{k}” và “{v}” là hai từ đồng nghĩa."),
            PairSet("Từ trái nghĩa với “{k}” là:", "Từ trái nghĩa với “{v}” là:", pairList(TRAI_NGHIA_X), "“{k}” và “{v}” là hai từ trái nghĩa."))),
    Topic(3, 13..28, "Từ chỉ người, con vật, cây cối, đồ vật (bổ sung)",
        catSets = listOf(CatSet("Từ nào là {c}?", catList(NGUOI_VAT_CAY), "“{w}” thuộc nhóm nào?"))),
    Topic(3, 21..35, "Mẫu câu Ai là gì, Ai làm gì, Ai thế nào",
        catSets = listOf(CatSet("Câu nào thuộc mẫu câu “{c}”?", catList(MAU_CAU_2_3), "Câu “{w}” thuộc mẫu câu nào?"))),

    // ---------------- Lớp 4 ----------------
    Topic(4, 1..12, "Chính tả (bổ sung)", items = spell(CHINH_TA_45)),
    Topic(4, 9..24, "Từ đồng nghĩa và trái nghĩa (bổ sung)",
        pairSets = listOf(
            PairSet("Từ đồng nghĩa với “{k}” là:", "Từ đồng nghĩa với “{v}” là:", pairList(DONG_NGHIA_X), "“{k}” và “{v}” là hai từ đồng nghĩa."),
            PairSet("Từ trái nghĩa với “{k}” là:", "Từ trái nghĩa với “{v}” là:", pairList(TRAI_NGHIA_X), "“{k}” và “{v}” là hai từ trái nghĩa."))),
    Topic(4, 13..35, "Câu kể, câu hỏi, câu cảm, câu khiến",
        catSets = listOf(CatSet("Câu nào là {c}?", catList(KIEU_CAU), "Câu “{w}” thuộc loại câu nào?"))),

    // ---------------- Lớp 5 ----------------
    Topic(5, 1..12, "Chính tả (bổ sung)", items = spell(CHINH_TA_45)),
    Topic(5, 9..24, "Từ đồng nghĩa và trái nghĩa (bổ sung)",
        pairSets = listOf(
            PairSet("Từ đồng nghĩa với “{k}” là:", "Từ đồng nghĩa với “{v}” là:", pairList(DONG_NGHIA_X), "“{k}” và “{v}” là hai từ đồng nghĩa."),
            PairSet("Từ trái nghĩa với “{k}” là:", "Từ trái nghĩa với “{v}” là:", pairList(TRAI_NGHIA_X), "“{k}” và “{v}” là hai từ trái nghĩa."))),
    Topic(5, 13..35, "Câu kể, câu hỏi, câu cảm, câu khiến",
        catSets = listOf(CatSet("Câu nào là {c}?", catList(KIEU_CAU), "Câu “{w}” thuộc loại câu nào?")))
)
