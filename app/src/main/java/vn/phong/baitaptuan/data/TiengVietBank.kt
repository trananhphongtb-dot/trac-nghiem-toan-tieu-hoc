package vn.phong.baitaptuan.data

/** Tiếng Việt lớp 1-5: chủ đề theo kỹ năng thường gặp ở phiếu bài tập cuối tuần
 *  (chính tả, luyện từ và câu, đọc hiểu), không theo bài đọc cụ thể của một bộ sách. */

private val DOI_NGHIA = """
cao|thấp
to|nhỏ
nhanh|chậm
sáng|tối
nóng|lạnh
vui|buồn
yêu|ghét
siêng năng|lười biếng
trung thực|gian dối
dũng cảm|hèn nhát
rộng|hẹp
xa|gần
giàu|nghèo
sạch|bẩn
mới|cũ
ngày|đêm
khóc|cười
lên|xuống
đóng|mở
khỏe|yếu
"""

private val DONG_NGHIA = """
chăm chỉ|siêng năng
đẹp|xinh
bố|cha
mẹ|má
trung thực|thật thà
yên lặng|im lặng
mênh mông|bát ngát
dũng cảm|can đảm
Tổ quốc|đất nước
học trò|học sinh
hiền|lành
bé|nhỏ
"""

private val TU_LOAI_2 = """
từ chỉ sự vật|bàn, ghế, sách, bút, cây, hoa, mặt trời, con mèo
từ chỉ hoạt động|chạy, nhảy, đọc, viết, hát, bơi, ăn, ngủ
từ chỉ đặc điểm|xanh, cao, đẹp, ngoan, nhanh, chậm, ngọt, thơm
"""

private val TU_LOAI_3 = """
từ chỉ người|bác sĩ, giáo viên, công nhân, nông dân, ca sĩ, học sinh
từ chỉ con vật|trâu, chó, gà, voi, chim sẻ, cá chép
từ chỉ cây cối|cây xoài, hoa hồng, cây tre, hoa sen, cây bàng, rau muống
từ chỉ hoạt động|chạy, nhảy, đọc, viết, hát, bơi
"""

private val TU_LOAI_4 = """
danh từ|học sinh, con đường, niềm vui, bàn học, dòng sông, cánh đồng
động từ|chạy, suy nghĩ, tưới, xây dựng, ngắm, nghiên cứu
tính từ|xanh biếc, hiền lành, rộng lớn, thông minh, nhanh nhẹn, ngọt ngào
"""

private val TU_LOAI_5 = """
đại từ xưng hô|tôi, chúng tôi, cậu, tớ, nó, họ
quan hệ từ|và, nhưng, vì, nên, hoặc, của
danh từ|bàn ghế, quê hương, tình bạn, dòng sông
tính từ|đẹp đẽ, hiền hậu, cần cù, mênh mông
"""

internal fun tiengVietTopics(): List<Topic> = listOf(
    // ---------------- Lớp 1 ----------------
    Topic(1, 1..8, "Chữ cái và âm đầu",
        pairSets = listOf(
            PairSet("Từ “{k}” bắt đầu bằng chữ cái nào?", "Từ nào bắt đầu bằng chữ cái “{v}”?", pairList("""
mẹ|m
bố|b
cá|c
dê|d
gà|g
hoa|h
lá|l
nho|n
sách|s
tôm|t
voi|v
xe|x
rùa|r
quạ|q
kẹo|k
đèn|đ
"""), "Từ “{k}” bắt đầu bằng chữ cái “{v}”.")
        ),
        items = items("""
Trong bảng chữ cái tiếng Việt, chữ cái nào đứng ngay sau chữ “b”?|c|a|d|e||Thứ tự là a, ă, â, b, c, d, đ, e...
Trong bảng chữ cái tiếng Việt, chữ cái nào đứng ngay sau chữ “c”?|d|b|đ|e||Thứ tự là a, ă, â, b, c, d, đ, e...
Tiếng nào có chữ cái “h” đứng đầu?|hoa|bóng|mèo|lá
Tiếng “ba” gồm những chữ cái nào?|b - a|b - o|p - a|d - a
""")),
    Topic(1, 9..18, "Vần và tiếng",
        pairSets = listOf(
            PairSet("Tiếng “{k}” có vần gì?", "Tiếng nào có vần “{v}”?", pairList("""
bàn|an
mèo|eo
cây|ây
lúa|ua
mưa|ưa
sông|ông
trăng|ăng
núi|ui
biển|iên
chim|im
nước|ươc
đường|ương
trời|ơi
hoa|oa
xanh|anh
"""), "Tiếng “{k}” có vần “{v}”.")
        ),
        items = items("""
Câu “Mẹ đi chợ.” có mấy tiếng?|3|2|4|5||Đếm từng tiếng: Mẹ - đi - chợ.
Câu “Bé học bài.” có mấy tiếng?|3|2|4|5||Đếm từng tiếng: Bé - học - bài.
Câu “Hôm nay em đi học.” có mấy tiếng?|5|4|6|3||Hôm - nay - em - đi - học.
Câu “Con mèo nằm trên ghế.” có mấy tiếng?|5|4|6|3||Con - mèo - nằm - trên - ghế.
Câu “Chú chó vẫy đuôi.” có mấy tiếng?|4|3|5|6||Chú - chó - vẫy - đuôi.
Ghép tiếng “học” với tiếng nào để thành từ?|sinh|bàn|mèo|nhà||học sinh
Ghép tiếng “nhà” với tiếng nào để thành từ?|trường|bàn|mưa|trăng||nhà trường
""")),
    Topic(1, 19..30, "Chính tả: c/k/q, g/gh, ng/ngh",
        items = items("""
Điền vào chỗ trống: ...ế (cái ...) – chọn g hay gh?|gh|g|k|ngh||gh đứng trước e, ê, i: ghế.
Điền vào chỗ trống: ...à (con ...) – chọn g hay gh?|g|gh|k|ngh||g đứng trước a: gà.
Điền vào chỗ trống: ...e (...e nhạc) – chọn ng hay ngh?|ngh|ng|g|gh||ngh đứng trước e, ê, i: nghe.
Điền vào chỗ trống: ...ô (bắp ...) – chọn ng hay ngh?|ng|ngh|g|gh||ng đứng trước ô: ngô.
Điền vào chỗ trống: ...ẹo (cái ...) – chọn c hay k?|k|c|q|g||k đứng trước e, ê, i: kẹo.
Điền vào chỗ trống: ...á (con ...) – chọn c hay k?|c|k|q|gh||c đứng trước a: cá.
Điền vào chỗ trống: ...ính (...ính chào) – chọn c hay k?|k|c|q|g||k đứng trước i: kính.
Điền vào chỗ trống: ...ả (...ả cam) – chọn qu hay c?|qu|c|k|kh||quả
Điền vào chỗ trống: ...i (ghi ...) – chọn g hay gh?|gh|g|k|ng||gh đứng trước i: ghi.
Điền vào chỗ trống: ...ỉ (...ỉ ngơi) – chọn ng hay ngh?|ngh|ng|g|gh||ngh đứng trước i: nghỉ.
""")),
    Topic(1, 31..35, "Đọc hiểu và dấu câu",
        items = items("""
Bé có con mèo. Con mèo tên là Mun. Mun rất ngoan.\nCon mèo tên là gì?|Mun|Miu|Tom|Bông||Văn bản nói: Con mèo tên là Mun.
Bé có con mèo. Con mèo tên là Mun. Mun rất ngoan.\nMun là con vật nào?|con mèo|con chó|con gà|con vịt||Bé có con mèo tên là Mun.
Nam có quả bóng đỏ. Nam đá bóng ở sân trường.\nNam đá bóng ở đâu?|ở sân trường|ở nhà|ở công viên|ở ngoài đường||Nam đá bóng ở sân trường.
Nam có quả bóng đỏ. Nam đá bóng ở sân trường.\nQuả bóng của Nam màu gì?|đỏ|xanh|vàng|trắng||Nam có quả bóng đỏ.
Mẹ nấu cơm. Bố đọc báo. Em làm bài.\nAi làm bài?|em|mẹ|bố|bà||Em làm bài.
Cuối câu kể “Mẹ đi chợ” ta đặt dấu gì?|dấu chấm|dấu phẩy|dấu chấm hỏi|dấu chấm than
Cuối câu hỏi “Bạn tên là gì” ta đặt dấu gì?|dấu chấm hỏi|dấu chấm|dấu phẩy|dấu chấm than
Chữ đầu câu phải viết thế nào?|viết hoa|viết thường|viết in nghiêng|không cần chú ý||Chữ cái đầu câu phải viết hoa.
Tên riêng của người phải viết thế nào?|viết hoa chữ cái đầu mỗi tiếng|viết thường tất cả|viết hoa toàn bộ chữ|viết hoa chữ cuối||Ví dụ: Nguyễn Văn An.
""")),

    // ---------------- Lớp 2 ----------------
    Topic(2, 1..8, "Từ chỉ sự vật, hoạt động, đặc điểm",
        catSets = listOf(CatSet("Từ nào là {c}?", catList(TU_LOAI_2), "“{w}” thuộc nhóm nào?")),
        items = items("""
Từ nào chỉ đồ vật trong câu “Bạn Lan đọc sách.”?|sách|đọc|Lan đọc|bạn Lan đọc||“sách” là tên đồ vật, chỉ sự vật.
Từ nào chỉ hoạt động trong câu “Chim sâu hót líu lo.”?|hót|chim|líu lo|sâu||“hót” là hoạt động của chim.
Từ nào chỉ đặc điểm trong câu “Bông hoa rất đẹp.”?|đẹp|bông hoa|rất|hoa||“đẹp” nói về đặc điểm của bông hoa.
""")),
    Topic(2, 9..16, "Từ trái nghĩa và câu Ai là gì?",
        pairSets = listOf(PairSet("Từ trái nghĩa với “{k}” là:", "Từ trái nghĩa với “{v}” là:", pairList(DOI_NGHIA), "“{k}” và “{v}” là hai từ trái nghĩa.")),
        items = items("""
Câu nào là câu giới thiệu (Ai là gì?)?|Bạn Mai là học sinh lớp 2.|Bạn Mai đang đọc sách.|Bạn Mai chạy rất nhanh.|Bạn Mai hát hay quá!||Câu “Ai là gì?” dùng từ “là” để giới thiệu.
Câu nào là câu nêu hoạt động (Ai làm gì?)?|Bố em đang sửa xe.|Bố em là thợ sửa xe.|Bố em rất hiền.|Bố em cao lớn.||Câu có từ chỉ hoạt động “sửa”.
Câu nào là câu nêu đặc điểm (Ai thế nào?)?|Con mèo rất lười.|Con mèo bắt chuột.|Con mèo là vật nuôi.|Con mèo nằm trên ghế.||“rất lười” nêu đặc điểm.
""")),
    Topic(2, 17..24, "Chính tả và dấu câu",
        items = spell("""
chăm chỉ|trăm chỉ|chăm trỉ|trăm trỉ
trong trẻo|chong trẻo|trong chẻo|chong chẻo
sáng suốt|xáng suốt|sáng xuốt|xáng xuốt
xinh xắn|sinh xắn|xinh sắn|sinh sắn
sạch sẽ|xạch sẽ|sạch xẽ|xạch xẽ
giản dị|dản dị|giản gị|dản gị
dịu dàng|giịu dàng|dịu giàng|giịu giàng
rực rỡ|dực rỡ|rực dỡ|rực rở
nghỉ ngơi|nghĩ ngơi|nghỉ ngơ|nghĩ ngơ
ngoan ngoãn|ngoan ngoản|ngoan ngoảng|ngoan ngõan
xôn xao|sôn xao|xôn sao|sôn sao
chạy nhảy|trạy nhảy|chạy nhãy|trạy nhãy
""") + items("""
Cuối câu “Bạn có khỏe không” ta đặt dấu gì?|dấu chấm hỏi|dấu chấm|dấu phẩy|dấu chấm than
Cuối câu “Ôi, hoa đẹp quá” ta đặt dấu gì?|dấu chấm than|dấu chấm|dấu phẩy|dấu chấm hỏi
Dấu phẩy trong câu “Em có sách, vở, bút.” dùng để làm gì?|ngăn cách các từ cùng loại|kết thúc câu|hỏi|nêu lời nói||Dấu phẩy ngăn cách các từ cùng loại.
""")),
    Topic(2, 25..35, "Từ đồng nghĩa và ôn tập",
        pairSets = listOf(PairSet("Từ nào có nghĩa gần giống với “{k}”?", "Từ nào có nghĩa gần giống với “{v}”?", pairList(DONG_NGHIA), "“{k}” và “{v}” có nghĩa gần giống nhau.")),
        items = items("""
Từ nào viết hoa đúng tên riêng của người?|Trần Văn Nam|trần văn nam|Trần văn nam|trần Văn Nam||Viết hoa chữ cái đầu mỗi tiếng.
Từ nào viết hoa đúng tên địa danh?|Hà Nội|hà nội|Hà nội|hà Nội||Tên riêng của địa danh phải viết hoa.
Từ nào chỉ thời gian?|buổi sáng|con mèo|quyển vở|cái bàn
Từ nào chỉ người trong gia đình?|ông nội|bàn ghế|con sông|bầu trời
""")),

    // ---------------- Lớp 3 ----------------
    Topic(3, 1..8, "Từ chỉ người, con vật, cây cối, hoạt động",
        catSets = listOf(CatSet("Từ nào là {c}?", catList(TU_LOAI_3), "“{w}” thuộc nhóm nào?")),
        items = items("""
Từ “thợ xây” chỉ gì?|người|con vật|cây cối|đồ vật||Thợ xây là nghề của con người.
Từ “chim sẻ” chỉ gì?|con vật|người|cây cối|đồ vật||Chim sẻ là một loài chim.
""")),
    Topic(3, 9..16, "Từ trái nghĩa, đồng nghĩa",
        pairSets = listOf(
            PairSet("Từ trái nghĩa với “{k}” là:", "Từ trái nghĩa với “{v}” là:", pairList(DOI_NGHIA), "“{k}” và “{v}” là hai từ trái nghĩa."),
            PairSet("Từ nào có nghĩa gần giống với “{k}”?", null, pairList(DONG_NGHIA), "“{k}” và “{v}” có nghĩa gần giống nhau.")
        )),
    Topic(3, 17..24, "So sánh và nhân hóa",
        items = items("""
Câu nào có hình ảnh so sánh?|Đôi mắt bé tròn như hai hạt nhãn.|Đôi mắt bé rất đẹp.|Bé nhìn mẹ cười.|Mắt bé sáng lắm.||Có từ so sánh “như”.
Câu nào có hình ảnh so sánh?|Trăng tròn như quả bóng.|Trăng lên rất cao.|Trăng sáng lắm.|Trăng đã lên.||Có từ so sánh “như”.
Trong câu “Mặt trời đỏ như quả cầu lửa.”, từ nào là từ so sánh?|như|đỏ|mặt trời|quả cầu||“như” nối hai sự vật được so sánh.
Trong câu “Cây tre xanh như dải lụa.”, hai sự vật được so sánh là:|cây tre và dải lụa|cây tre và màu xanh|xanh và dải lụa|dải lụa và mặt trời||So sánh cây tre với dải lụa.
Câu nào dùng biện pháp nhân hóa?|Chú gà trống gáy vang chào ngày mới.|Gà trống gáy vang.|Gà trống rất đẹp.|Con gà trống đứng ở sân.||Gà “chào” như người.
Câu nào dùng biện pháp nhân hóa?|Cây cau đứng canh gác trước sân nhà.|Cây cau cao vút.|Cây cau xanh tốt.|Trước sân có cây cau.||Cây “đứng canh gác” như người.
Câu nào KHÔNG dùng biện pháp nhân hóa?|Bé đang chơi trong vườn.|Cây bàng khoác chiếc áo mới.|Chú mèo cười tít mắt.|Gió thì thầm với lá.||Câu “Bé chơi trong vườn” chỉ nói về bé.
""")),
    Topic(3, 25..35, "Chính tả và dấu câu",
        items = spell("""
sáng suốt|xáng suốt|sáng xuốt|xáng xuốt
xinh xắn|sinh xắn|xinh sắn|sinh sắn
giản dị|dản dị|giản gị|dản gị
trung thực|chung thực|trung thức|chung thức
dành dụm|giành dụm|dành giụm|giành giụm
lấp lánh|lấp nánh|nấp lánh|nấp nánh
lung linh|lung nhinh|nung linh|lung ninh
nô nức|lô nức|nô lức|lô lức
bỡ ngỡ|bở ngỡ|bỡ ngở|bở ngở
vất vả|vất vã|vấc vả|vật vả
mãi mãi|mải mãi|mãi mải|mải mải
""") + items("""
Dấu hai chấm trong câu “Mẹ dặn: “Con nhớ đóng cửa.”” dùng để làm gì?|báo hiệu lời nói của người khác|kết thúc câu|ngăn cách các từ cùng loại|dùng khi hỏi||Dấu hai chấm đặt trước lời nói.
Dấu ngoặc kép dùng để:|đánh dấu lời nói trực tiếp|kết thúc câu hỏi|ngăn cách các từ|gạch tên sách||Dấu ngoặc kép đánh dấu lời nói.
Thành ngữ nào nói về sự biết ơn?|Uống nước nhớ nguồn.|Chậm như rùa.|Nhanh như cắt.|Lá lành đùm lá rách.||Uống nước nhớ nguồn: nhớ ơn người đi trước.
Câu “Lá lành đùm lá rách.” khuyên ta điều gì?|biết giúp đỡ người gặp khó khăn|lười biếng|nói dối|học chăm chỉ||Giúp người khó khăn.
""")),

    // ---------------- Lớp 4 ----------------
    Topic(4, 1..8, "Danh từ, động từ, tính từ",
        catSets = listOf(CatSet("Từ nào là {c}?", catList(TU_LOAI_4), "“{w}” thuộc nhóm nào?")),
        items = items("""
Trong câu “Em bé ngủ ngon lành.”, từ nào là động từ?|ngủ|em bé|ngon lành|ngủ ngon||“ngủ” chỉ hoạt động.
Trong câu “Mùa thu, bầu trời trong xanh.”, từ nào là tính từ?|trong xanh|mùa thu|bầu trời|bầu||“trong xanh” chỉ đặc điểm.
Danh từ là những từ chỉ:|người, vật, hiện tượng, khái niệm|hoạt động, trạng thái|đặc điểm, tính chất|mối quan hệ giữa các từ||Danh từ chỉ sự vật.
Động từ là những từ chỉ:|hoạt động, trạng thái|đặc điểm, tính chất|người, vật|mối quan hệ giữa các từ||Động từ chỉ hoạt động, trạng thái.
Tính từ là những từ chỉ:|đặc điểm, tính chất|hoạt động, trạng thái|người, vật|cảm thán||Tính từ chỉ đặc điểm, tính chất.
""")),
    Topic(4, 9..16, "Câu kể: chủ ngữ và vị ngữ",
        items = items("""
Trong câu “Mẹ em đang nấu cơm.”, chủ ngữ là:|Mẹ em|đang nấu cơm|nấu cơm|cơm||Chủ ngữ trả lời “Ai?”.
Trong câu “Mẹ em đang nấu cơm.”, vị ngữ là:|đang nấu cơm|Mẹ em|em|Mẹ||Vị ngữ trả lời “làm gì?”.
Trong câu “Chú mèo con rất tinh nghịch.”, chủ ngữ là:|Chú mèo con|rất tinh nghịch|tinh nghịch|mèo con rất||Chủ ngữ là “Chú mèo con”.
Trong câu “Bạn Lan là học sinh giỏi.”, vị ngữ là:|là học sinh giỏi|Bạn Lan|học sinh giỏi|Lan là||Vị ngữ bắt đầu bằng từ “là”.
Câu kể Ai làm gì? là câu nào?|Cô giáo giảng bài.|Cô giáo rất hiền.|Cô giáo là người mẹ thứ hai.|Cô giáo ơi!||Có từ chỉ hoạt động “giảng bài”.
Câu kể Ai thế nào? là câu nào?|Lớp em rất đoàn kết.|Lớp em đang học bài.|Lớp em là lớp 4A.|Lớp em ơi!||“rất đoàn kết” nêu đặc điểm.
Câu kể Ai là gì? là câu nào?|Hà Nội là thủ đô của nước ta.|Hà Nội rất đẹp.|Hà Nội đang đổi mới.|Hà Nội ơi!||Có từ “là” giới thiệu.
""")),
    Topic(4, 17..24, "Dấu câu và thành ngữ, tục ngữ",
        pairSets = listOf(PairSet("Câu “{k}” khuyên chúng ta điều gì?", null, pairList("""
Ăn quả nhớ kẻ trồng cây.|biết ơn người đã giúp mình
Uống nước nhớ nguồn.|nhớ ơn người đi trước
Có công mài sắt có ngày nên kim.|kiên trì thì sẽ thành công
Lá lành đùm lá rách.|giúp đỡ người gặp khó khăn
Đoàn kết là sức mạnh.|đoàn kết thì làm nên việc lớn
Học ăn học nói học gói học mở.|phải học hỏi từ những việc nhỏ
Chớ thấy sóng cả mà ngã tay chèo.|đừng nản chí khi gặp khó khăn
Thương người như thể thương thân.|yêu thương người khác như yêu chính mình
"""), "Câu “{k}” khuyên: {v}.")),
        items = items("""
Dấu gạch ngang (-) đầu dòng dùng để:|đánh dấu lời nói của nhân vật|kết thúc câu|ngăn cách các từ|hỏi||Gạch đầu dòng đánh dấu lời nói.
Dấu ngoặc kép dùng để:|đánh dấu lời nói trực tiếp hoặc từ ngữ đặc biệt|kết thúc câu|báo hiệu câu hỏi|ngăn cách các vế câu||Dấu ngoặc kép đánh dấu lời nói.
Dấu hai chấm dùng để:|báo hiệu lời nói hoặc phần giải thích đứng sau|kết thúc câu|ngăn cách từ cùng loại|bày tỏ cảm xúc||Dấu hai chấm báo hiệu lời nói.
""")),
    Topic(4, 25..35, "Chính tả và biện pháp nghệ thuật",
        items = spell("""
chói chang|trói chang|chói trang|trói trang
sáng sủa|xáng sủa|sáng xủa|xáng xủa
tròn trịa|chòn trịa|tròn chịa|chòn chịa
chuyên cần|truyên cần|chuyên cằn|truyên cằn
trân trọng|chân trọng|trân chọng|chân chọng
giữ gìn|dữ gìn|giữ dìn|dữ dìn
dung dăng|giung dăng|dung giăng|giung giăng
nhẫn nại|nhẫn lại|nhẫn nạy|nhẫn nậi
""") + items("""
Câu nào dùng biện pháp so sánh?|Dòng sông uốn lượn như một dải lụa.|Dòng sông chảy rất êm.|Dòng sông là quê hương em.|Bên dòng sông có cây đa.||Có từ so sánh “như”.
Câu nào dùng biện pháp nhân hóa?|Chị gió ngồi hát ru cây lá.|Gió thổi mạnh.|Gió rất mát.|Cơn gió thổi qua sân.||Gió “hát ru” như người.
Câu “Trời mưa như trút nước.” dùng biện pháp nghệ thuật nào?|so sánh|nhân hóa|liệt kê|điệp từ||Có từ so sánh “như”.
""")),

    // ---------------- Lớp 5 ----------------
    Topic(5, 1..8, "Đại từ và quan hệ từ",
        catSets = listOf(CatSet("Từ nào là {c}?", catList(TU_LOAI_5), "“{w}” thuộc nhóm nào?")),
        items = items("""
Đại từ là từ dùng để:|xưng hô hoặc thay thế danh từ, động từ, tính từ|chỉ hoạt động|chỉ đặc điểm|nối các câu||Đại từ dùng để xưng hô hoặc thay thế.
Quan hệ từ có tác dụng gì?|nối các từ hoặc các câu|chỉ người và vật|chỉ hoạt động|chỉ đặc điểm||Quan hệ từ nối các từ, các vế câu.
Từ “nhưng” trong câu “Trời mưa nhưng em vẫn đi học.” là:|quan hệ từ|danh từ|động từ|tính từ||“nhưng” chỉ quan hệ tương phản.
Cặp quan hệ từ nào chỉ quan hệ nguyên nhân - kết quả?|Vì ... nên ...|Tuy ... nhưng ...|Nếu ... thì ...|Không những ... mà còn ...||Vì ... nên ... chỉ nguyên nhân - kết quả.
Cặp quan hệ từ nào chỉ quan hệ điều kiện - kết quả?|Nếu ... thì ...|Vì ... nên ...|Tuy ... nhưng ...|Không những ... mà còn ...||Nếu ... thì ... chỉ điều kiện - kết quả.
Cặp quan hệ từ nào chỉ quan hệ tương phản?|Tuy ... nhưng ...|Vì ... nên ...|Nếu ... thì ...|Nhờ ... mà ...||Tuy ... nhưng ... chỉ quan hệ tương phản.
""")),
    Topic(5, 9..16, "Từ đồng nghĩa, trái nghĩa, nhiều nghĩa",
        pairSets = listOf(
            PairSet("Từ trái nghĩa với “{k}” là:", "Từ trái nghĩa với “{v}” là:", pairList(DOI_NGHIA), "“{k}” và “{v}” là hai từ trái nghĩa."),
            PairSet("Từ nào có nghĩa gần giống với “{k}”?", "Từ nào có nghĩa gần giống với “{v}”?", pairList(DONG_NGHIA), "“{k}” và “{v}” có nghĩa gần giống nhau.")
        ),
        items = items("""
Từ “đá” trong “đá bóng” và “hòn đá” là:|từ đồng âm|từ đồng nghĩa|từ trái nghĩa|từ láy||Cùng cách viết, cùng cách đọc nhưng nghĩa khác nhau.
Từ nào là từ láy?|long lanh|học sinh|bàn ghế|xe đạp||“long lanh” lặp lại âm đầu.
Từ nào là từ ghép?|bàn ghế|long lanh|xôn xao|lấp lánh||“bàn ghế” ghép hai từ có nghĩa.
""")),
    Topic(5, 17..24, "Liên kết câu và dấu câu",
        items = items("""
Việc lặp lại từ ngữ của câu trước ở câu sau để nối hai câu gọi là:|liên kết bằng cách lặp từ ngữ|liên kết bằng cách thay thế từ ngữ|liên kết bằng từ nối|không phải liên kết câu||Lặp từ ngữ là một cách liên kết câu.
Từ nối “tuy nhiên” dùng để:|nối hai ý có sự tương phản|nối hai ý giống nhau|kết thúc bài|mở đầu bài||“Tuy nhiên” chỉ sự tương phản.
Dấu phẩy có tác dụng gì trong câu?|ngăn cách các thành phần hoặc các vế câu|kết thúc câu|bày tỏ cảm xúc|hỏi||Dấu phẩy ngăn cách các thành phần.
Dấu chấm lửng (...) dùng để:|biểu thị ý bỏ lửng hoặc lời nói ngập ngừng|kết thúc câu hỏi|báo hiệu lời nói|đánh dấu tên riêng||Dấu chấm lửng bỏ lửng ý.
""")),
    Topic(5, 25..35, "Chính tả và làm văn",
        items = spell("""
chân thành|trân thành|chân thàng|trân thàng
tập trung|tập chung|tạp trung|tập chun
chia sẻ|chia xẻ|trai sẻ|chia sẽ
sâu sắc|xâu xắc|sâu xắc|xâu sắc
bổ sung|bổ xung|bỏ sung|bổ xúng
xuất sắc|xuất xắc|suất sắc|suất xắc
chắc chắn|trắc chắn|chắc trắn|trắc trắn
dí dỏm|rí dỏm|dí giỏm|dỉ dỏm
""") + items("""
Bài văn tả cảnh thường gồm mấy phần?|3 phần: mở bài, thân bài, kết bài|2 phần|4 phần|5 phần||Mở bài, thân bài, kết bài.
Phần thân bài của bài văn tả cảnh dùng để:|tả chi tiết cảnh vật|giới thiệu cảnh sẽ tả|nêu cảm nghĩ cuối bài|kể chuyện||Thân bài tả chi tiết.
Phần kết bài của bài văn tả cảnh dùng để:|nêu cảm nghĩ về cảnh|tả chi tiết cảnh|giới thiệu cảnh|kể chuyện||Kết bài nêu cảm nghĩ.
Phần mở bài của bài văn tả cảnh dùng để:|giới thiệu cảnh sẽ tả|tả chi tiết cảnh|nêu cảm nghĩ|kết thúc bài||Mở bài giới thiệu cảnh.
""")),

    // ---- Bổ sung ----
    Topic(4, 1..8, "Danh từ, động từ, tính từ (bổ sung)",
        catSets = listOf(CatSet("Từ nào là {c}?", catList("""
từ láy|long lanh, xinh xắn, lấp lánh, thì thầm, rì rào, nhỏ nhắn
từ ghép|bàn ghế, sách vở, học sinh, xe đạp, nhà cửa, quần áo
"""), null)),
        items = items("""
Từ nào là danh từ riêng?|Hà Nội|dòng sông|học sinh|thành phố||Tên riêng của địa danh là danh từ riêng.
Từ nào là danh từ chung?|dòng sông|Hà Nội|Nguyễn Du|Việt Nam||“dòng sông” chỉ chung một loại sự vật.
Danh từ riêng phải viết như thế nào?|viết hoa chữ cái đầu mỗi tiếng|viết thường tất cả|viết hoa chữ cái cuối|viết hoa toàn bộ chữ||Ví dụ: Nguyễn Văn An, Thành phố Hồ Chí Minh.
""")),
    Topic(4, 17..24, "Thành ngữ (bổ sung)",
        pairSets = listOf(PairSet("Thành ngữ “{k}” nói về điều gì?", null, pairList("""
Chậm như rùa|rất chậm chạp
Nhanh như chớp|rất nhanh
Khỏe như voi|rất khỏe
Hiền như đất|rất hiền lành
Đẹp như tiên|rất đẹp
"""), "“{k}” nghĩa là {v}."))
    ),
    Topic(4, 25..35, "Ôn tập (bổ sung)",
        items = items("""
Từ nào là từ ghép?|bạn bè|long lanh|lấp lánh|xinh xắn||“bạn bè” gồm hai tiếng có nghĩa ghép lại.
Từ nào là từ láy?|rì rào|bàn ghế|nhà cửa|sách vở||“rì rào” có hai tiếng lặp âm đầu.
Từ nào KHÔNG phải từ láy?|sách vở|nhỏ nhắn|thì thầm|long lanh||“sách vở” là từ ghép.
"""))
)
