package vn.phong.baitaptuan.data

/** Tự nhiên và Xã hội (lớp 1-3) và Khoa học (lớp 4-5). Nội dung là kiến thức phổ thông,
 *  chia theo mạch chủ đề của chương trình, không bám một bài đọc cụ thể của bộ sách nào. */

private val NGHE_NOI = """
bác sĩ|bệnh viện
giáo viên|trường học
nông dân|cánh đồng
công an|đồn công an
người bán hàng|cửa hàng
thợ xây|công trường
công nhân|nhà máy
thợ may|tiệm may
"""

private val GIAC_QUAN = """
mắt|nhìn
tai|nghe
mũi|ngửi
lưỡi|nếm
da|cảm nhận nóng, lạnh
"""

private val DONG_VAT_1 = """
con vật nuôi trong nhà|chó, mèo, gà, lợn, trâu, bò
con vật sống dưới nước|cá, tôm, cua, mực
con vật biết bay|chim sẻ, ong, bướm, chuồn chuồn
con vật sống trong rừng|hổ, voi, khỉ, hươu
"""

private val BO_PHAN_CAY = """
rễ|hút nước và muối khoáng từ đất
thân|nâng đỡ cây
lá|tạo thức ăn cho cây nhờ ánh sáng
hoa|sinh ra quả và hạt
quả|chứa hạt để nhân giống
"""

private val CHAT_DINH_DUONG = """
chất đạm|thịt, cá, trứng, đậu phụ
chất bột đường|gạo, ngô, khoai, bánh mì
chất béo|dầu ăn, mỡ, bơ, dầu mè
vitamin và chất khoáng|rau xanh, trái cây, cà rốt, cam
"""

private val NANG_LUONG = """
nguồn năng lượng tái tạo|Mặt Trời, gió, nước chảy, địa nhiệt
nguồn năng lượng không tái tạo|than đá, dầu mỏ, khí tự nhiên
"""

internal fun tuNhienTopics(): List<Topic> = listOf(
    // ---------------- Lớp 1: Tự nhiên và Xã hội ----------------
    Topic(1, 1..8, "Gia đình",
        items = items("""
Bố của bố em gọi là:|ông nội|ông ngoại|bác|chú||Bố của bố là ông nội.
Mẹ của mẹ em gọi là:|bà ngoại|bà nội|cô|dì||Mẹ của mẹ là bà ngoại.
Người sinh ra em là:|bố và mẹ|ông và bà|cô và chú|thầy và cô||Bố mẹ sinh ra em.
Nhà là nơi:|cả gia đình cùng sống|chỉ để chơi|bán hàng|chữa bệnh||Gia đình cùng sống trong nhà.
Khi ở nhà một mình có người lạ gõ cửa, em nên:|không mở cửa và báo cho người lớn|mở cửa ngay|mời người lạ vào nhà|chạy ra ngoài||Không mở cửa cho người lạ.
Đồ vật nào em không nên tự nghịch ở nhà?|ổ điện|quả bóng|quyển sách|bút chì||Ổ điện có thể gây điện giật.
Em nên giúp đỡ cha mẹ việc nhà như thế nào?|làm việc vừa sức|làm việc nguy hiểm|không làm gì|làm việc mà người lớn cấm||Làm việc vừa sức.
""")),
    Topic(1, 9..14, "Trường học",
        items = items("""
Người dạy em học ở trường là:|cô giáo|bác sĩ|công an|nông dân||Cô giáo dạy em học.
Phòng có nhiều sách ở trường là:|thư viện|phòng y tế|nhà bếp|sân chơi||Thư viện có nhiều sách.
Nơi em chơi và tập thể dục ở trường là:|sân trường|thư viện|phòng y tế|nhà bếp||Sân trường để vui chơi.
Khi bị ốm ở trường, em đến:|phòng y tế|thư viện|sân trường|cổng trường||Phòng y tế chăm sóc sức khỏe.
Giờ ra chơi em nên:|chơi vui, không xô đẩy bạn|leo trèo nguy hiểm|đánh bạn|chạy ra đường||Chơi an toàn.
Khi đến lớp, em cần:|chào thầy cô và các bạn|không chào ai|nói chuyện to|đi muộn||Chào hỏi lễ phép.
""")),
    Topic(1, 15..20, "Cộng đồng địa phương",
        pairSets = listOf(PairSet("{k} thường làm việc ở:", "Người nào thường làm việc ở {v}?", pairList(NGHE_NOI), "{k} thường làm việc ở {v}.")),
        items = items("""
Nơi chữa bệnh cho mọi người là:|bệnh viện|trường học|chợ|cửa hàng||Bệnh viện khám chữa bệnh.
Nơi mọi người đến mua đồ ăn, rau, thịt là:|chợ|bệnh viện|thư viện|công viên||Chợ bán thực phẩm.
Khi sang đường, em phải:|đi theo người lớn, nhìn đèn tín hiệu|chạy nhanh qua đường|đứng giữa đường|chơi trên đường||Sang đường phải an toàn.
Đèn tín hiệu giao thông màu đỏ nghĩa là:|dừng lại|đi|đi nhanh|chậm lại||Đèn đỏ là dừng lại.
Đèn tín hiệu giao thông màu xanh nghĩa là:|được đi|dừng lại|chờ|quay đầu||Đèn xanh được đi.
""")),
    Topic(1, 21..26, "Thực vật và động vật",
        catSets = listOf(CatSet("Con vật nào là {c}?", catList(DONG_VAT_1), "“{w}” thuộc nhóm nào?")),
        pairSets = listOf(PairSet("Bộ phận “{k}” của cây có tác dụng:", null, pairList(BO_PHAN_CAY), "{k}: {v}.")),
        items = items("""
Cây có các bộ phận chính nào?|rễ, thân, lá|đầu, tay, chân|vỏ, ruột, hạt cứng|bánh, xe, đèn||Cây có rễ, thân, lá.
Cây cần gì để sống?|nước, ánh sáng, không khí|chỉ có kẹo|chỉ có đồ chơi|nước ngọt||Cây cần nước và ánh sáng.
""")),
    Topic(1, 27..31, "Con người và sức khỏe",
        pairSets = listOf(PairSet("Cơ quan “{k}” giúp chúng ta:", null, pairList(GIAC_QUAN), "{k} giúp chúng ta {v}.")),
        items = items("""
Để răng chắc khỏe, em nên:|đánh răng hằng ngày|ăn thật nhiều kẹo|không đánh răng|uống nhiều nước ngọt||Đánh răng sạch.
Trước khi ăn, em phải:|rửa tay|chạy nhảy|xem ti vi|đi ngủ||Rửa tay trước khi ăn.
Thức ăn nào tốt cho sức khỏe?|rau và hoa quả|kẹo ngọt|nước ngọt có ga|bánh quy nhiều đường||Rau, quả có nhiều vitamin.
Để có sức khỏe tốt, ban đêm em nên:|ngủ sớm, ngủ đủ giấc|thức khuya xem ti vi|không ngủ|chơi game cả đêm||Ngủ đủ giấc tốt cho sức khỏe.
""")),
    Topic(1, 32..35, "Trái Đất và bầu trời",
        items = items("""
Ban ngày, vật sáng nhất trên bầu trời là:|Mặt Trời|Mặt Trăng|ngôi sao|đèn đường||Ban ngày có Mặt Trời.
Ban đêm, em có thể nhìn thấy:|Mặt Trăng và các vì sao|Mặt Trời|cầu vồng|mây đen||Ban đêm có trăng và sao.
Trời mưa, em nên mang theo:|áo mưa|mũ nắng|áo khoác dày|kính râm||Áo mưa che mưa.
Trời nắng nóng, em nên:|đội mũ và uống đủ nước|đứng ngoài nắng|không uống nước|chạy nhảy ngoài nắng||Giữ gìn sức khỏe khi nắng nóng.
""")),

    // ---------------- Lớp 2: Tự nhiên và Xã hội ----------------
    Topic(2, 1..8, "Gia đình và họ hàng",
        items = items("""
Anh, chị, em của bố được gọi là:|bác, chú, cô|ông, bà|dì, cậu|anh chị em họ||Anh, chị, em trai và em gái của bố là bác, chú, cô.
Anh, chị, em của mẹ được gọi là:|dì, cậu|bác, chú|ông, bà|anh chị em họ||Anh chị em của mẹ gọi là dì, cậu.
Con của bác, chú, cô, dì, cậu so với em là:|anh chị em họ|anh chị em ruột|ông bà|bố mẹ||Anh chị em họ.
Khi có khách đến nhà, em nên:|chào hỏi lễ phép|trốn đi|không chào|nói chuyện to||Lễ phép với khách.
Nhà ở nào thường xây trên cột để tránh lũ, thú dữ?|nhà sàn|nhà chung cư|nhà mái bằng|nhà tầng||Nhà sàn của đồng bào miền núi.
""")),
    Topic(2, 9..16, "Trường học và mua bán",
        items = items("""
Nơi người ta mua bán hàng hóa là:|chợ, siêu thị, cửa hàng|trường học|bệnh viện|nhà ga||Chợ, siêu thị, cửa hàng.
Khi mua hàng, em cần:|lựa chọn kỹ và trả tiền|lấy không|làm hỏng hàng|nói to||Mua hàng phải trả tiền.
Biển báo hình tròn viền đỏ thường là biển:|cấm|chỉ dẫn|nguy hiểm|không có ý nghĩa||Biển cấm thường tròn viền đỏ.
Khi đi xe máy hoặc xe đạp điện, em phải:|đội mũ bảo hiểm|không cần đội mũ|đi nhanh|đi hàng ba||Mũ bảo hiểm bảo vệ đầu.
Ngồi trên ô tô, em nên:|thắt dây an toàn|đứng lên ngồi xuống|thò đầu ra ngoài|mở cửa||An toàn khi đi xe.
""")),
    Topic(2, 17..26, "Thực vật và động vật",
        catSets = listOf(CatSet("Con vật nào là {c}?", catList(DONG_VAT_1), "“{w}” thuộc nhóm nào?")),
        pairSets = listOf(PairSet("Bộ phận “{k}” của cây có tác dụng:", null, pairList(BO_PHAN_CAY), "{k}: {v}.")),
        items = items("""
Cây nào là cây ăn quả?|cây xoài|cây lúa|cây bàng|cây rau muống||Cây xoài cho quả.
Cây nào là cây rau?|cây rau muống|cây xoài|cây bàng|cây phượng||Rau muống là cây rau.
Cây nào là cây lương thực?|cây lúa|cây rau muống|cây bàng|cây xoài||Lúa cho gạo, là lương thực.
Hoa nào là loài hoa nở vào mùa xuân ở miền Bắc?|hoa đào|hoa phượng|hoa sen|hoa cúc||Hoa đào nở vào Tết ở miền Bắc.
""")),
    Topic(2, 27..35, "Cơ thể, bầu trời và các mùa",
        items = items("""
Cơ quan nào giúp cơ thể di chuyển?|xương và cơ|tim|phổi|da||Xương và cơ giúp cơ thể vận động.
Để xương chắc khỏe, em nên uống:|sữa|nước ngọt có ga|cà phê|rượu||Sữa có nhiều canxi.
Mặt Trời mọc ở hướng:|Đông|Tây|Nam|Bắc||Mặt Trời mọc ở hướng Đông.
Mặt Trời lặn ở hướng:|Tây|Đông|Nam|Bắc||Mặt Trời lặn ở hướng Tây.
Một năm có mấy mùa?|4 mùa|2 mùa|3 mùa|5 mùa||Xuân, hạ, thu, đông.
Mùa nào trời nóng nhất trong năm?|mùa hạ|mùa xuân|mùa thu|mùa đông||Mùa hạ nóng nhất.
Ban ngày, nguồn sáng chủ yếu chiếu xuống Trái Đất là:|Mặt Trời|Mặt Trăng|đèn điện|ngôi sao||Ban ngày ánh sáng chủ yếu đến từ Mặt Trời.
""")),

    // ---------------- Lớp 3: Tự nhiên và Xã hội ----------------
    Topic(3, 1..8, "Gia đình và trường học",
        items = items("""
Các thế hệ cùng sống trong gia đình thường là:|ông bà, bố mẹ và con cái|bạn bè|hàng xóm|đồng nghiệp||Gia đình nhiều thế hệ.
Khi xảy ra hỏa hoạn, em cần làm gì?|gọi to người lớn, thoát ra ngoài|trốn trong phòng|mở cửa sổ để lửa lan|dùng nước dập lửa điện||Báo động và thoát hiểm.
Số điện thoại gọi cứu hỏa là:|114|113|115|111||114 là số cứu hỏa.
Số điện thoại gọi cấp cứu y tế là:|115|113|114|116||115 là cấp cứu.
Số điện thoại gọi công an là:|113|114|115|117||113 là công an.
Ở trường, em cần giữ gìn:|bàn ghế, sách vở, vệ sinh lớp học|không giữ gìn gì|chỉ giữ gìn đồ chơi|chỉ giữ gìn quần áo||Giữ gìn tài sản chung.
""")),
    Topic(3, 9..16, "Cộng đồng và nghề nghiệp",
        pairSets = listOf(PairSet("{k} thường làm việc ở:", "Người nào thường làm việc ở {v}?", pairList(NGHE_NOI), "{k} thường làm việc ở {v}.")),
        items = items("""
Ở cộng đồng, em cần tuân thủ:|nội quy, quy định chung|không quy định nào|chỉ ý thích riêng|chỉ lời bạn||Tuân thủ nội quy nơi công cộng.
Khi đến nơi công cộng, em cần:|giữ trật tự, xếp hàng|chen lấn|vứt rác bừa bãi|la hét||Có ý thức nơi công cộng.
""")),
    Topic(3, 17..26, "Thực vật và động vật",
        catSets = listOf(CatSet("Con vật nào là {c}?", catList(DONG_VAT_1), "“{w}” thuộc nhóm nào?")),
        pairSets = listOf(PairSet("Bộ phận “{k}” của cây có tác dụng:", null, pairList(BO_PHAN_CAY), "{k}: {v}.")),
        items = items("""
Cây hút nước và muối khoáng bằng bộ phận nào?|rễ|lá|hoa|quả||Rễ hút nước và muối khoáng.
Lá cây có màu xanh là nhờ chất:|diệp lục|đường|muối|dầu||Chất diệp lục làm lá có màu xanh.
Động vật có xương sống là:|cá, chim, thú|giun, sâu|sứa|ốc||Cá, chim, thú có xương sống.
Động vật nào đẻ trứng?|gà|trâu|bò|lợn||Gà đẻ trứng.
Động vật nào đẻ con và nuôi con bằng sữa?|bò|gà|cá|chim sẻ||Bò đẻ con, nuôi con bằng sữa.
""")),
    Topic(3, 27..35, "Cơ thể người và Trái Đất",
        items = items("""
Cơ quan hô hấp có nhiệm vụ:|trao đổi khí|tiêu hóa thức ăn|bơm máu|suy nghĩ||Hô hấp là hít khí oxi, thở ra khí cacbonic.
Cơ quan nào bơm máu đi khắp cơ thể?|tim|phổi|dạ dày|gan||Tim bơm máu.
Cơ quan nào dùng để thở?|phổi|tim|dạ dày|thận||Phổi trao đổi khí.
Cơ quan nào điều khiển mọi hoạt động của cơ thể?|não|tim|phổi|dạ dày||Não là trung tâm thần kinh.
Để bảo vệ cơ quan hô hấp, em nên:|giữ không khí trong lành, tránh khói bụi|hút thuốc|ở nơi nhiều khói bụi|nín thở||Không khí sạch bảo vệ phổi.
Trái Đất quay quanh:|Mặt Trời|Mặt Trăng|ngôi sao Bắc Đẩu|Sao Hỏa||Trái Đất quay quanh Mặt Trời.
Mặt Trăng quay quanh:|Trái Đất|Mặt Trời|Sao Kim|Sao Hỏa||Mặt Trăng là vệ tinh của Trái Đất.
Trái Đất tự quay quanh trục tạo ra:|ngày và đêm|bốn mùa|mưa và nắng|bão||Trái Đất tự quay một vòng là một ngày đêm.
""")),

    // ---------------- Lớp 4: Khoa học ----------------
    Topic(4, 1..8, "Chất: nước và không khí",
        items = items("""
Nước tồn tại ở những thể nào?|rắn, lỏng, khí|chỉ rắn|chỉ lỏng|chỉ khí||Nước có 3 thể: rắn, lỏng, khí.
Nước đá ở thể nào?|rắn|lỏng|khí|không có||Nước đá là thể rắn.
Hơi nước là nước ở thể nào?|khí|rắn|lỏng|bột||Hơi nước là thể khí.
Nước đóng băng ở nhiệt độ:|0°C|100°C|50°C|-100°C||Nước đóng băng ở 0°C.
Nước sôi ở nhiệt độ khoảng:|100°C|0°C|50°C|200°C||Nước sôi ở 100°C (ở mức nước biển).
Quá trình nước lỏng bay hơi thành hơi nước gọi là:|sự bay hơi|sự ngưng tụ|sự đông đặc|sự nóng chảy||Bay hơi: lỏng thành khí.
Quá trình hơi nước gặp lạnh thành nước lỏng gọi là:|sự ngưng tụ|sự bay hơi|sự nóng chảy|sự đông đặc||Ngưng tụ: khí thành lỏng.
Quá trình nước đá tan thành nước lỏng gọi là:|sự nóng chảy|sự bay hơi|sự ngưng tụ|sự đông đặc||Nóng chảy: rắn thành lỏng.
Khí nào chiếm nhiều nhất trong không khí?|nitơ|oxi|cacbonic|hơi nước||Nitơ chiếm khoảng 78%.
Khí nào cần cho sự hô hấp của người và động vật?|oxi|nitơ|cacbonic|heli||Oxi giúp hô hấp.
Gió là:|không khí chuyển động|nước chuyển động|ánh sáng|âm thanh||Gió là không khí chuyển động.
""")),
    Topic(4, 9..16, "Ánh sáng, âm thanh và nhiệt",
        items = items("""
Ánh sáng truyền theo:|đường thẳng|đường cong|đường gấp khúc|vòng tròn||Ánh sáng truyền thẳng.
Vật nào là nguồn sáng?|Mặt Trời|Mặt Trăng|quyển sách|cái gương||Mặt Trời tự phát ra ánh sáng.
Vật nào cho ánh sáng truyền qua gần như hoàn toàn?|kính trong|tấm gỗ|tấm bìa|tường gạch||Kính trong là vật trong suốt.
Âm thanh được tạo ra do:|vật rung động|vật đứng yên|ánh sáng|nhiệt||Vật rung động tạo ra âm thanh.
Âm thanh KHÔNG truyền được trong môi trường nào?|chân không|không khí|nước|chất rắn||Âm thanh cần môi trường để truyền.
Vật nào dẫn nhiệt tốt?|thìa kim loại|que gỗ|cái cốc nhựa|miếng xốp||Kim loại dẫn nhiệt tốt.
Vật nào cách nhiệt tốt?|miếng xốp|nồi nhôm|thìa sắt|dây đồng||Xốp cách nhiệt.
Khi nhiệt độ tăng, chất lỏng trong nhiệt kế:|nở ra, dâng lên|co lại|không đổi|bay đi||Chất lỏng nở khi nóng.
""")),
    Topic(4, 17..26, "Thực vật, động vật và nấm",
        items = items("""
Quá trình lá cây hấp thụ ánh sáng để tạo chất dinh dưỡng gọi là:|quang hợp|hô hấp|ngưng tụ|bay hơi||Quang hợp cần ánh sáng.
Khi quang hợp, cây thải ra khí:|oxi|cacbonic|nitơ|hơi nước||Quang hợp thải oxi.
Chuỗi thức ăn đúng là:|cỏ → thỏ → cáo|cáo → thỏ → cỏ|thỏ → cỏ → cáo|cáo → cỏ → thỏ||Mũi tên chỉ hướng được ăn.
Động vật ăn thực vật là:|thỏ|hổ|cáo|rắn||Thỏ ăn cỏ.
Động vật ăn thịt là:|hổ|thỏ|trâu|dê||Hổ ăn thịt.
Nấm nào ăn được?|nấm rơm|nấm độc|nấm mốc|nấm gây bệnh||Nấm rơm ăn được.
Nấm mốc thường mọc ở đâu?|thức ăn để lâu ngày|trên mặt trăng|trong nước đá|trên kim loại sáng||Nấm mốc mọc trên thức ăn ẩm.
""")),
    Topic(4, 27..35, "Con người và dinh dưỡng",
        catSets = listOf(CatSet("Thực phẩm nào thuộc nhóm {c}?", catList(CHAT_DINH_DUONG), "“{w}” thuộc nhóm nào?")),
        items = items("""
Nhóm chất nào giúp cơ thể phát triển và xây dựng cơ bắp?|chất đạm|chất xơ|nước lọc|không khí||Chất đạm xây dựng cơ thể.
Nhóm chất nào cung cấp năng lượng chủ yếu cho cơ thể?|chất bột đường|vitamin|chất khoáng|nước||Chất bột đường cung cấp năng lượng.
Để cơ thể khỏe mạnh, em nên ăn:|đa dạng các loại thức ăn|chỉ ăn thịt|chỉ ăn rau|chỉ ăn bánh kẹo||Ăn đa dạng.
Thiếu vitamin A có thể gây:|khô mắt, quáng gà|còi xương|bướu cổ|thiếu máu||Thiếu vitamin A gây quáng gà.
Thiếu canxi và vitamin D dễ bị:|còi xương|bướu cổ|quáng gà|béo phì||Còi xương.
Ăn quá nhiều đồ ngọt và chất béo dễ bị:|béo phì|còi xương|quáng gà|bướu cổ||Thừa năng lượng gây béo phì.
Để thực phẩm an toàn, em nên:|rửa sạch, nấu chín|ăn đồ ôi thiu|để ngoài nắng|không rửa||Ăn chín, uống sôi.
Để tránh đuối nước, em nên:|chỉ bơi khi có người lớn trông chừng|bơi một mình ở sông|đi nghịch ở ao hồ|nhảy xuống nước sâu||An toàn khi bơi.
""")),

    // ---------------- Lớp 5: Khoa học ----------------
    Topic(5, 1..8, "Chất: hỗn hợp, dung dịch và biến đổi",
        items = items("""
Nước muối là:|dung dịch|chất rắn|chất khí|kim loại||Muối tan hoàn toàn trong nước.
Hỗn hợp cát và nước:|cát không tan trong nước|cát tan hoàn toàn|nước biến mất|cát thành khí||Cát không tan.
Muốn tách cát ra khỏi nước, em có thể:|lọc|đun sôi nước|thêm muối|thêm đường||Lọc giữ lại cát.
Biến đổi nào là biến đổi hóa học?|đốt giấy|cắt giấy|xé giấy|gấp giấy||Đốt giấy tạo chất mới.
Biến đổi nào là biến đổi lí học?|nước đá tan|sắt bị gỉ|đốt than|nấu cơm||Nước đá tan chỉ thay đổi thể.
Biến đổi nào là biến đổi hóa học?|sắt bị gỉ|nước đá tan|hòa tan đường vào nước|cắt sợi dây||Sắt gỉ tạo chất mới.
""")),
    Topic(5, 9..16, "Năng lượng",
        catSets = listOf(CatSet("Nguồn năng lượng nào là {c}?", catList(NANG_LUONG), "“{w}” thuộc nhóm nào?")),
        items = items("""
Năng lượng Mặt Trời được dùng để:|phơi khô, làm điện mặt trời|nấu bằng than|chạy bằng xăng|thắp bằng dầu||Năng lượng mặt trời dùng phơi và phát điện.
Pin mặt trời biến đổi:|ánh sáng thành điện|điện thành ánh sáng|nhiệt thành gió|nước thành gió||Pin mặt trời tạo ra điện.
Tiết kiệm điện bằng cách:|tắt đèn khi không dùng|để quạt chạy cả ngày|mở tủ lạnh liên tục|bật đèn cả đêm||Tắt thiết bị khi không dùng.
Dùng điện an toàn là:|không chạm tay ướt vào ổ điện|cắm nhiều thiết bị vào một ổ|nghịch dây điện|sờ vào dây điện hở||Tay ướt dễ bị điện giật.
""")),
    Topic(5, 17..26, "Sinh vật và môi trường",
        items = items("""
Rừng có tác dụng gì?|giữ đất, chống xói mòn, điều hòa khí hậu|làm đất bạc màu|gây lũ|làm không khí bẩn||Rừng bảo vệ môi trường.
Việc làm nào góp phần bảo vệ môi trường?|trồng cây, phân loại rác|vứt rác ra sông|đốt rừng|xả nước thải bẩn||Bảo vệ môi trường.
Tài nguyên nào là tài nguyên không tái tạo?|than đá|rừng|nước mưa|ánh nắng||Than đá dùng hết thì không tự có lại.
Sinh vật nào là sinh vật sản xuất trong chuỗi thức ăn?|cây xanh|con thỏ|con cáo|con hổ||Cây xanh tự tạo chất dinh dưỡng.
""")),
    Topic(5, 27..35, "Con người: sinh sản và sức khỏe",
        items = items("""
Sự kết hợp giữa trứng và tinh trùng gọi là:|sự thụ tinh|sự nảy mầm|sự nở|sự phát triển||Thụ tinh tạo hợp tử.
Bệnh sốt xuất huyết do loài nào truyền?|muỗi vằn|ruồi|gián|chuột||Muỗi vằn truyền sốt xuất huyết.
Cách phòng bệnh sốt xuất huyết là:|diệt muỗi, diệt lăng quăng|nuôi muỗi|để nước đọng|không ngủ màn||Diệt muỗi và lăng quăng.
Tuổi dậy thì là giai đoạn:|cơ thể có nhiều thay đổi về thể chất và tâm lí|chỉ có thay đổi về răng|không có thay đổi|em bé mới sinh||Dậy thì là giai đoạn chuyển từ trẻ em sang người lớn.
Chất gây nghiện là:|ma túy, thuốc lá, rượu|nước lọc|rau xanh|sữa||Chất gây nghiện hại sức khỏe.
Khi gặp người lạ có cử chỉ không đúng mực với em, em nên:|tránh xa và báo người lớn|đi theo|nhận quà|giữ bí mật||Báo ngay cho người tin cậy.
""")),

    // ---- Bổ sung ----
    Topic(2, 17..26, "Thực vật và động vật (bổ sung)",
        items = items("""
Cây xanh có lợi ích gì?|cho bóng mát, làm sạch không khí|làm bẩn không khí|gây tiếng ồn|làm đất cứng||Cây xanh có nhiều lợi ích.
Động vật nào sống ở dưới nước?|cá|gà|chó|mèo||Cá sống dưới nước.
""")),
    Topic(2, 27..35, "Cơ thể (bổ sung)",
        items = items("""
Xương sườn bảo vệ cơ quan nào?|tim và phổi|dạ dày|não|mắt||Lồng ngực bảo vệ tim và phổi.
Hộp sọ bảo vệ cơ quan nào?|não|tim|phổi|dạ dày||Hộp sọ bảo vệ não.
Cơ quan hô hấp gồm mũi, khí quản, phế quản và:|phổi|tim|dạ dày|gan||Phổi là cơ quan chính của hô hấp.
Khi đi bộ trên đường phố, em nên đi:|trên vỉa hè|giữa lòng đường|sát xe đang chạy|trên dải phân cách||Đi bộ trên vỉa hè cho an toàn.
""")),
    Topic(5, 1..8, "Vật liệu (bổ sung)",
        items = items("""
Vật liệu nào dẫn điện tốt?|đồng|nhựa|gỗ|cao su||Kim loại dẫn điện tốt.
Chất nào là kim loại?|sắt|nhựa|thủy tinh|gỗ||Sắt là kim loại.
Thủy tinh được làm chủ yếu từ:|cát trắng|đất sét|gỗ|cao su||Cát trắng nấu chảy làm thủy tinh.
Gạch, ngói được làm từ:|đất sét nung|cát thuần|gỗ|giấy||Đất sét nung thành gạch, ngói.
Cao su tự nhiên được lấy từ:|cây cao su|đá|đất|cát||Mủ cây cao su.
Nhôm là kim loại có đặc điểm:|nhẹ, dẻo|nặng, giòn|không dẫn điện|dễ vỡ||Nhôm nhẹ và dẻo.
Tơ tằm là:|sợi tự nhiên|sợi nhân tạo|kim loại|nhựa||Tơ tằm lấy từ kén tằm.
Đá vôi tác dụng với giấm ăn sinh ra khí:|cacbonic|oxi|hiđro|nitơ||Khí CO2 tạo bọt.
""")),
    Topic(5, 9..16, "Năng lượng (bổ sung)",
        items = items("""
Nhà máy thủy điện dùng năng lượng của:|nước chảy|gió|than đá|dầu mỏ||Nước chảy làm quay tuabin.
Tuabin gió dùng năng lượng của:|gió|nước|than đá|dầu mỏ||Gió làm quay cánh quạt.
Than đá, dầu mỏ hình thành từ:|xác sinh vật cổ bị vùi lấp hàng triệu năm|nước biển|không khí|ánh sáng||Là nguồn năng lượng không tái tạo.
Hành động nào tiết kiệm năng lượng?|tắt điện khi ra khỏi phòng|bật đèn cả ngày|mở tủ lạnh liên tục|để quạt chạy khi vắng người||Tắt điện khi không dùng.
""")),
    Topic(5, 17..26, "Môi trường (bổ sung)",
        items = items("""
Hiệu ứng nhà kính làm cho:|Trái Đất nóng lên|Trái Đất lạnh đi|mưa nhiều hơn|mất trọng lực||Khí nhà kính giữ nhiệt.
Khí chủ yếu gây hiệu ứng nhà kính là:|cacbonic|oxi|nitơ|heli||CO2 giữ nhiệt trong khí quyển.
Rác thải nhựa phân hủy:|rất chậm|rất nhanh|trong một ngày|không tồn tại||Nhựa tồn tại hàng trăm năm.
Tái chế là:|biến rác thải thành vật liệu dùng được|đốt rác|chôn rác|vứt rác||Tái chế giảm rác thải.
Việc làm nào giúp giảm rác thải nhựa?|dùng túi vải, bình nước dùng lại|dùng nhiều túi nilon|vứt chai nhựa bừa bãi|đốt rác nhựa||Giảm dùng nhựa một lần.
""")),
    Topic(5, 27..35, "Con người và sức khỏe (bổ sung)",
        items = items("""
Bệnh sốt rét do muỗi nào truyền?|muỗi Anophen|muỗi vằn|ruồi|gián||Muỗi Anophen truyền sốt rét.
Bệnh viêm gan A lây qua:|đường tiêu hóa|đường hô hấp|đường máu|muỗi đốt||Ăn chín, uống sôi, rửa tay để phòng bệnh.
HIV KHÔNG lây qua con đường nào sau đây?|bắt tay, ôm nhau|truyền máu không an toàn|dùng chung kim tiêm|mẹ truyền sang con||HIV không lây qua tiếp xúc thông thường.
Thuốc lá gây hại chủ yếu cho cơ quan nào?|phổi|tai|mắt|chân||Khói thuốc hại phổi.
Chất nào sau đây gây nghiện?|ma túy|nước lọc|sữa|rau xanh||Ma túy gây nghiện, hại sức khỏe.
"""))
)
