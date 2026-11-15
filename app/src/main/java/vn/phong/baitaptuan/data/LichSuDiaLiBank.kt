package vn.phong.baitaptuan.data

/** Lịch sử và Địa lí lớp 4-5. Chỉ dùng kiến thức bền vững (không nêu tên tỉnh, số tỉnh
 *  vì ranh giới hành chính thay đổi). */

private val SU_KIEN_NAM = """
Cuộc khởi nghĩa Hai Bà Trưng nổ ra|năm 40
Ngô Quyền đại thắng quân Nam Hán trên sông Bạch Đằng|năm 938
Vua Lý Thái Tổ dời đô về Thăng Long|năm 1010
Quân dân nhà Trần đại thắng quân Mông - Nguyên trên sông Bạch Đằng lần cuối|năm 1288
Khởi nghĩa Lam Sơn giành thắng lợi|năm 1427
Nguyễn Huệ đại phá quân Thanh|năm 1789
Thực dân Pháp nổ súng xâm lược Việt Nam|năm 1858
Nguyễn Tất Thành ra đi tìm đường cứu nước|năm 1911
Đảng Cộng sản Việt Nam được thành lập|năm 1930
Chủ tịch Hồ Chí Minh đọc Tuyên ngôn Độc lập|năm 1945
Chiến thắng Điện Biên Phủ|năm 1954
Thống nhất đất nước, miền Nam hoàn toàn giải phóng|năm 1975
"""

private val NHAN_VAT = """
Hai Bà Trưng|lãnh đạo cuộc khởi nghĩa chống quân Hán
Ngô Quyền|chỉ huy chiến thắng Bạch Đằng năm 938
Đinh Bộ Lĩnh|dẹp loạn 12 sứ quân, lập nhà Đinh
Lý Thái Tổ|dời đô từ Hoa Lư về Thăng Long
Trần Hưng Đạo|chỉ huy quân dân nhà Trần chống quân Mông - Nguyên
Lê Lợi|lãnh đạo khởi nghĩa Lam Sơn
Nguyễn Trãi|soạn Bình Ngô đại cáo
Quang Trung (Nguyễn Huệ)|đại phá quân Thanh
Võ Nguyên Giáp|chỉ huy chiến dịch Điện Biên Phủ
"""

internal fun lichSuDiaLiTopics(): List<Topic> = listOf(
    // ---------------- Lớp 4 ----------------
    Topic(4, 1..4, "Làm quen bản đồ và phương hướng",
        items = items("""
Có mấy hướng chính trên bản đồ?|4 hướng|2 hướng|3 hướng|8 hướng||Đông, Tây, Nam, Bắc.
Hướng nào đối diện với hướng Bắc?|Nam|Đông|Tây|Đông Bắc||Bắc đối diện Nam.
Hướng nào đối diện với hướng Đông?|Tây|Bắc|Nam|Tây Nam||Đông đối diện Tây.
Mặt Trời mọc ở hướng:|Đông|Tây|Nam|Bắc||Mặt Trời mọc ở phía Đông.
Mặt Trời lặn ở hướng:|Tây|Đông|Nam|Bắc||Mặt Trời lặn ở phía Tây.
Trên bản đồ, phần phía trên thường chỉ hướng:|Bắc|Nam|Đông|Tây||Quy ước: trên Bắc, dưới Nam, phải Đông, trái Tây.
Bản đồ là:|hình vẽ thu nhỏ một vùng đất trên mặt phẳng|bức ảnh chụp người|bức tranh phong cảnh|quyển sách||Bản đồ thu nhỏ một khu vực.
Kí hiệu bản đồ dùng để:|thể hiện các đối tượng địa lí|trang trí|viết thơ|đo nhiệt độ||Kí hiệu thay cho núi, sông, thành phố...
""")),
    Topic(4, 5..10, "Trung du và miền núi Bắc Bộ",
        items = items("""
Đỉnh núi cao nhất Việt Nam là:|Phan-xi-păng|Ba Vì|Tam Đảo|Yên Tử||Phan-xi-păng thuộc dãy Hoàng Liên Sơn.
Dãy núi cao nhất Việt Nam là:|Hoàng Liên Sơn|Trường Sơn|Tam Đảo|Bạch Mã||Hoàng Liên Sơn ở Bắc Bộ.
Ruộng bậc thang thường thấy ở vùng:|miền núi|đồng bằng ven biển|đô thị lớn|hải đảo||Làm ruộng bậc thang để trồng lúa trên sườn núi.
Vua Hùng dựng nước Văn Lang, đóng đô ở:|Phong Châu|Cổ Loa|Thăng Long|Hoa Lư||Phong Châu là kinh đô của nhà nước Văn Lang.
Lễ hội Đền Hùng tưởng nhớ:|các Vua Hùng|Hai Bà Trưng|Ngô Quyền|Lê Lợi||Giỗ Tổ Hùng Vương.
Ngày Giỗ Tổ Hùng Vương theo âm lịch là:|10 tháng 3|15 tháng 1|1 tháng 5|10 tháng 10||Mùng 10 tháng 3 âm lịch.
Vịnh Hạ Long là:|di sản thiên nhiên thế giới|sân bay|đồng bằng|đỉnh núi||Vịnh Hạ Long được UNESCO công nhận.
Dân tộc nào đông nhất ở nước ta?|Kinh|Tày|Thái|Mường||Dân tộc Kinh đông nhất.
""")),
    Topic(4, 11..18, "Đồng bằng Bắc Bộ",
        items = items("""
Sông lớn nhất bồi đắp nên đồng bằng Bắc Bộ là:|sông Hồng|sông Cửu Long|sông Mã|sông Đà||Sông Hồng bồi đắp phù sa.
Thủ đô của nước Việt Nam là:|Hà Nội|Huế|Đà Nẵng|Thành phố Hồ Chí Minh||Hà Nội là thủ đô.
Văn Miếu - Quốc Tử Giám ở:|Hà Nội|Huế|Hội An|Cần Thơ||Văn Miếu là trường đại học đầu tiên của nước ta.
Hồ Hoàn Kiếm (hồ Gươm) nằm ở:|Hà Nội|Huế|Đà Nẵng|Cần Thơ||Hồ Gươm giữa Hà Nội.
Đồng bằng Bắc Bộ trồng nhiều:|lúa nước|cà phê|cao su|hồ tiêu||Đất phù sa phù hợp trồng lúa.
Nghề truyền thống nổi tiếng ở Hà Nội là:|làng nghề gốm Bát Tràng|khai thác than|đánh cá biển|làm muối||Bát Tràng làm gốm nổi tiếng.
Thăng Long là tên cũ của:|Hà Nội|Huế|Hải Phòng|Nam Định||Thăng Long nay là Hà Nội.
Con sông nào chảy qua Hà Nội?|sông Hồng|sông Cửu Long|sông Hương|sông Đồng Nai||Sông Hồng chảy qua Hà Nội.
""")),
    Topic(4, 19..26, "Duyên hải miền Trung",
        items = items("""
Cố đô Huế từng là kinh đô của triều đại:|nhà Nguyễn|nhà Lý|nhà Trần|nhà Đinh||Huế là kinh đô triều Nguyễn.
Phố cổ nổi tiếng được UNESCO công nhận ở miền Trung là:|Hội An|Hà Nội|Hà Giang|Sa Pa||Phố cổ Hội An là di sản thế giới.
Dãy núi chạy dọc miền Trung là:|Trường Sơn|Hoàng Liên Sơn|Tam Đảo|Ba Vì||Dãy Trường Sơn.
Biển nước ta tên là:|Biển Đông|Biển Tây|Biển Bắc|Biển Nam||Việt Nam nhìn ra Biển Đông.
Nghề nổi tiếng của người dân ven biển miền Trung là:|đánh bắt hải sản, làm muối|trồng cà phê|nuôi bò sữa|khai thác than||Vùng ven biển nên làm nghề biển.
Thành phố nào ở miền Trung có cầu Rồng nổi tiếng?|Đà Nẵng|Hà Nội|Cần Thơ|Hải Phòng||Cầu Rồng ở Đà Nẵng.
Quần đảo Hoàng Sa và Trường Sa thuộc chủ quyền của:|Việt Nam|Trung Quốc|Philippines|Malaysia||Hoàng Sa và Trường Sa là của Việt Nam.
Thánh địa Mỹ Sơn là di tích của người:|Chăm|Kinh|Mường|Thái||Mỹ Sơn là tháp Chăm cổ.
""")),
    Topic(4, 27..30, "Tây Nguyên",
        items = items("""
Cây công nghiệp nổi tiếng nhất ở Tây Nguyên là:|cà phê|chè|mía|bông||Tây Nguyên trồng nhiều cà phê.
Nhạc cụ đặc trưng của đồng bào Tây Nguyên là:|cồng chiêng|đàn bầu|đàn tranh|sáo trúc||Cồng chiêng Tây Nguyên là di sản.
Lễ hội đặc sắc của Tây Nguyên:|lễ hội cồng chiêng|hội Lim|hội chùa Hương|lễ hội Katê||Cồng chiêng gắn với đời sống tâm linh.
Nhà dài, nhà rông là kiểu nhà của:|đồng bào Tây Nguyên|người miền biển|người thành phố|người ven biển||Nhà rông của dân tộc Tây Nguyên.
Tây Nguyên có địa hình chủ yếu là:|cao nguyên|đồng bằng|đảo|bãi biển||Các cao nguyên xếp tầng.
Thành phố Đà Lạt nổi tiếng với:|khí hậu mát mẻ, nhiều hoa|bãi biển|sa mạc|băng tuyết||Đà Lạt ở độ cao lớn nên mát mẻ.
""")),
    Topic(4, 31..35, "Nam Bộ",
        items = items("""
Hai con sông lớn ở Nam Bộ là:|sông Tiền, sông Hậu|sông Hồng, sông Mã|sông Đà, sông Lô|sông Hương, sông Thu Bồn||Sông Tiền và sông Hậu thuộc sông Cửu Long.
Đồng bằng nào là vựa lúa lớn nhất cả nước?|đồng bằng sông Cửu Long|đồng bằng Bắc Bộ|đồng bằng Duyên hải miền Trung|đồng bằng sông Hồng||Đồng bằng sông Cửu Long.
Thành phố đông dân nhất nước ta là:|Thành phố Hồ Chí Minh|Hà Nội|Hải Phòng|Cần Thơ||Thành phố Hồ Chí Minh.
Chợ nổi là nét đặc trưng của vùng:|đồng bằng sông Cửu Long|Tây Nguyên|miền núi phía Bắc|duyên hải miền Trung||Chợ nổi trên sông.
Cây ăn quả nổi tiếng ở Nam Bộ là:|sầu riêng, chôm chôm, xoài|đào, mận|chè|cà phê||Nam Bộ có nhiều cây ăn quả nhiệt đới.
Nghề đặc trưng ở vùng sông nước Nam Bộ là:|nuôi trồng, đánh bắt thủy sản|khai thác than|trồng chè|làm đồ gốm núi||Sông ngòi chằng chịt.
Hải sản nuôi nhiều ở Nam Bộ là:|tôm, cá tra|cừu|bò sữa|ngựa||Nuôi tôm và cá tra.
"""))
,
    // ---------------- Lớp 5 ----------------
    Topic(5, 1..6, "Đất nước, dân cư và khí hậu",
        items = items("""
Hình dạng lãnh thổ Việt Nam giống chữ cái:|S|C|L|T||Hình chữ S.
Biển ở phía đông và phía nam nước ta là:|Biển Đông|Biển Đen|Biển Bắc|Biển Hồng||Biển Đông.
Nước ta có bao nhiêu dân tộc?|54|63|34|20||Việt Nam có 54 dân tộc.
Dân tộc nào có số dân đông nhất?|dân tộc Kinh|dân tộc Thái|dân tộc Tày|dân tộc Mường||Dân tộc Kinh.
Khí hậu của nước ta là:|nhiệt đới gió mùa|ôn đới|hàn đới|sa mạc||Nhiệt đới gió mùa.
Quốc kì nước ta có:|nền đỏ, sao vàng năm cánh|nền xanh, sao đỏ|nền vàng, sao đỏ|nền trắng, sao vàng||Cờ đỏ sao vàng.
Quốc ca nước ta là bài:|Tiến quân ca|Quốc tế ca|Giải phóng miền Nam|Bài ca Hồ Chí Minh||Tiến quân ca của Văn Cao.
Khoáng sản nào được khai thác nhiều ở nước ta?|than|vàng ròng toàn bộ|kim cương|bạch kim||Việt Nam có nhiều than.
Ngày Quốc khánh nước ta là:|2 tháng 9|30 tháng 4|1 tháng 5|19 tháng 5||Ngày 2/9/1945.
""")),
    Topic(5, 7..12, "Dựng nước và giữ nước thuở đầu",
        pairSets = listOf(PairSet("{k} nổi tiếng vì:", "Ai {v}?", pairList(NHAN_VAT), "{k} {v}.")),
        items = items("""
Nhà nước đầu tiên của người Việt là:|Văn Lang|Âu Lạc|Đại Việt|Đại Ngu||Văn Lang do Vua Hùng dựng.
Kinh đô của nhà nước Âu Lạc là:|Cổ Loa|Phong Châu|Hoa Lư|Thăng Long||An Dương Vương đóng đô ở Cổ Loa.
Chiến thắng Bạch Đằng năm 938 do ai lãnh đạo?|Ngô Quyền|Lê Hoàn|Lý Thường Kiệt|Trần Hưng Đạo||Ngô Quyền đánh quân Nam Hán.
Sau chiến thắng Bạch Đằng, Ngô Quyền lên ngôi vua, đóng đô ở:|Cổ Loa|Hoa Lư|Thăng Long|Huế||Đóng đô ở Cổ Loa.
Kinh đô của nhà Đinh và nhà Tiền Lê là:|Hoa Lư|Thăng Long|Huế|Cổ Loa||Hoa Lư.
Năm 1010 Lý Thái Tổ dời đô ra:|Thăng Long|Huế|Hoa Lư|Cổ Loa||Thăng Long nay là Hà Nội.
""")),
    Topic(5, 13..18, "Nhà Lý, nhà Trần và khởi nghĩa Lam Sơn",
        pairSets = listOf(
            PairSet("Sự kiện: “{k}” diễn ra vào:", "Sự kiện nào diễn ra vào {v}?", pairList(SU_KIEN_NAM), "{k} vào {v}.")
        ),
        items = items("""
Chiến thắng chống quân Tống ở sông Như Nguyệt do ai chỉ huy?|Lý Thường Kiệt|Trần Hưng Đạo|Lê Lợi|Quang Trung||Lý Thường Kiệt.
Triều đại nào ba lần đánh thắng quân Mông - Nguyên?|nhà Trần|nhà Lý|nhà Lê|nhà Nguyễn||Nhà Trần ba lần đánh thắng quân Mông - Nguyên.
Trần Hưng Đạo là vị tướng của triều đại:|nhà Trần|nhà Lý|nhà Lê|nhà Nguyễn||Trần Quốc Tuấn.
Khởi nghĩa Lam Sơn do ai lãnh đạo?|Lê Lợi|Nguyễn Huệ|Lý Bí|Ngô Quyền||Lê Lợi cùng Nguyễn Trãi.
Người soạn Bình Ngô đại cáo là:|Nguyễn Trãi|Lê Lợi|Trần Hưng Đạo|Quang Trung||Nguyễn Trãi.
Sau chiến thắng quân Minh, Lê Lợi lên ngôi, lập ra nhà:|nhà Hậu Lê|nhà Lý|nhà Trần|nhà Nguyễn||Lê Lợi lập nhà Lê.
""")),
    Topic(5, 19..26, "Thời Tây Sơn và thực dân Pháp xâm lược",
        items = items("""
Nguyễn Huệ lên ngôi hoàng đế lấy hiệu là:|Quang Trung|Gia Long|Minh Mạng|Tự Đức||Hoàng đế Quang Trung.
Chiến thắng Ngọc Hồi - Đống Đa năm 1789 đánh thắng quân:|Thanh|Nguyên|Minh|Tống||Quang Trung đại phá quân Thanh.
Triều đại phong kiến cuối cùng của nước ta là:|nhà Nguyễn|nhà Lê|nhà Trần|nhà Lý||Nhà Nguyễn (1802 - 1945).
Thực dân Pháp nổ súng xâm lược nước ta năm 1858 tại:|Đà Nẵng|Hà Nội|Sài Gòn|Hải Phòng||Pháp đánh Đà Nẵng đầu tiên.
Nguyễn Tất Thành ra đi tìm đường cứu nước năm:|1911|1920|1930|1945||Từ bến Nhà Rồng.
Bến cảng nơi Nguyễn Tất Thành xuống tàu năm 1911 là:|Bến Nhà Rồng|cảng Hải Phòng|cảng Đà Nẵng|cảng Cam Ranh||Bến Nhà Rồng (Sài Gòn).
Bác Hồ sinh ngày:|19 tháng 5 năm 1890|2 tháng 9 năm 1945|7 tháng 5 năm 1954|30 tháng 4 năm 1975||Sinh ngày 19/5/1890.
""")),
    Topic(5, 27..35, "Cách mạng, kháng chiến và đổi mới",
        items = items("""
Đảng Cộng sản Việt Nam ra đời vào ngày:|3 tháng 2 năm 1930|2 tháng 9 năm 1945|7 tháng 5 năm 1954|30 tháng 4 năm 1975||Ngày 3/2/1930.
Cách mạng tháng Tám thành công vào năm:|1945|1930|1954|1975||Năm 1945.
Ngày 2 tháng 9 năm 1945, Chủ tịch Hồ Chí Minh đọc:|Tuyên ngôn Độc lập|Bình Ngô đại cáo|Hịch tướng sĩ|Chiếu dời đô||Khai sinh nước Việt Nam Dân chủ Cộng hòa.
Chiến thắng Điện Biên Phủ diễn ra vào năm:|1954|1945|1975|1930||Ngày 7/5/1954.
Người chỉ huy chiến dịch Điện Biên Phủ là:|Đại tướng Võ Nguyên Giáp|Trần Hưng Đạo|Lê Lợi|Quang Trung||Võ Nguyên Giáp.
Đất nước thống nhất, miền Nam hoàn toàn giải phóng vào ngày:|30 tháng 4 năm 1975|7 tháng 5 năm 1954|2 tháng 9 năm 1945|19 tháng 8 năm 1945||Ngày 30/4/1975.
Chiến dịch cuối cùng giải phóng miền Nam năm 1975 mang tên:|Chiến dịch Hồ Chí Minh|Chiến dịch Điện Biên Phủ|Chiến dịch Việt Bắc|Chiến dịch Biên giới||Chiến dịch Hồ Chí Minh.
Năm 1986, Đảng ta khởi xướng công cuộc:|Đổi mới|Cải cách ruộng đất|Hợp tác hóa|Chống Pháp||Công cuộc Đổi mới.
Tên nước ta từ năm 1976 là:|Cộng hòa xã hội chủ nghĩa Việt Nam|Việt Nam Dân chủ Cộng hòa|Đại Việt|Đại Nam||Từ 1976.
""")),

    // ---- Bổ sung ----
    Topic(4, 5..10, "Trung du và miền núi Bắc Bộ (bổ sung)",
        items = items("""
Dãy núi Hoàng Liên Sơn nằm ở vùng:|Trung du và miền núi Bắc Bộ|Đồng bằng Bắc Bộ|Tây Nguyên|Nam Bộ||Hoàng Liên Sơn ở Tây Bắc Bộ.
Cây công nghiệp nổi tiếng ở trung du miền núi phía Bắc là:|chè|cà phê|cao su|hồ tiêu||Chè trồng nhiều ở miền núi phía Bắc.
Sa Pa là điểm du lịch nổi tiếng thuộc vùng:|Trung du và miền núi Bắc Bộ|Nam Bộ|Tây Nguyên|duyên hải miền Trung||Sa Pa có khí hậu mát mẻ, ruộng bậc thang.
""")),
    Topic(4, 11..18, "Đồng bằng Bắc Bộ (bổ sung)",
        items = items("""
Hải Phòng là thành phố cảng lớn thuộc vùng:|Đồng bằng Bắc Bộ|Nam Bộ|Tây Nguyên|miền núi Bắc Bộ||Hải Phòng là cảng biển lớn ở phía Bắc.
Làng Bát Tràng nổi tiếng với nghề:|làm gốm|làm giấy|dệt lụa|đúc đồng||Gốm Bát Tràng.
Đồng bằng sông Hồng có hình dạng giống:|tam giác châu|hình tròn|hình vuông|hình thoi||Đồng bằng châu thổ hình tam giác.
""")),
    Topic(4, 19..26, "Duyên hải miền Trung (bổ sung)",
        items = items("""
Đèo Hải Vân nằm giữa hai thành phố:|Huế và Đà Nẵng|Hà Nội và Hải Phòng|Nha Trang và Đà Lạt|Cần Thơ và Cà Mau||Đèo Hải Vân nối Huế và Đà Nẵng.
Đà Nẵng nằm ở vùng:|duyên hải miền Trung|Tây Nguyên|Nam Bộ|miền núi Bắc Bộ||Đà Nẵng ở ven biển miền Trung.
""")),
    Topic(4, 27..30, "Tây Nguyên (bổ sung)",
        items = items("""
Buôn Ma Thuột nổi tiếng với cây:|cà phê|chè|cao su|bông||Buôn Ma Thuột là thủ phủ cà phê.
Cồng chiêng Tây Nguyên được UNESCO công nhận là:|di sản văn hóa|di sản thiên nhiên|công trình hiện đại|kì quan thế giới||Không gian văn hóa cồng chiêng.
""")),
    Topic(4, 31..35, "Nam Bộ (bổ sung)",
        items = items("""
Sài Gòn là tên cũ của:|Thành phố Hồ Chí Minh|Hà Nội|Cần Thơ|Biên Hòa||Sài Gòn nay là Thành phố Hồ Chí Minh.
Cần Thơ nổi tiếng với:|chợ nổi Cái Răng|phố cổ|ruộng bậc thang|cồng chiêng||Chợ nổi Cái Răng ở Cần Thơ.
Mũi Cà Mau là điểm cực Nam trên:|đất liền của nước ta|biển Đông|đảo Phú Quốc|cao nguyên||Cực Nam trên đất liền.
""")),
    Topic(4, 1..4, "Bản đồ và phương hướng (bổ sung)",
        items = items("""
Bản đồ là:|hình vẽ thu nhỏ một khu vực của bề mặt Trái Đất|bức ảnh chụp cận cảnh|bài thơ về quê hương|bức tranh phong cảnh||Bản đồ thu nhỏ bề mặt Trái Đất.
Bốn hướng chính trên bản đồ là:|bắc, nam, đông, tây|trên, dưới, trái, phải|sáng, trưa, chiều, tối|xuân, hạ, thu, đông||Bắc, nam, đông, tây.
Hướng đối diện với hướng bắc là:|nam|đông|tây|đông bắc||Bắc đối diện nam.
Mặt Trời mọc ở hướng:|đông|tây|nam|bắc||Hướng đông.
Tỉ lệ bản đồ cho biết:|khoảng cách trên bản đồ thu nhỏ bao nhiêu lần so với thực tế|màu của bản đồ|tuổi của bản đồ|số dân của khu vực||Tỉ lệ cho biết mức thu nhỏ.
Kí hiệu bản đồ dùng để:|thể hiện các đối tượng địa lí trên bản đồ|trang trí bản đồ|đo nhiệt độ|ghi tên tác giả||Bảng chú giải giải thích kí hiệu.
""")),
    Topic(4, 5..10, "Trung du và miền núi Bắc Bộ (bổ sung 2)",
        items = items("""
Đỉnh núi nào được gọi là “nóc nhà Đông Dương”?|Phan-xi-păng|Ngọc Linh|Lang Biang|Bà Nà||Phan-xi-păng thuộc dãy Hoàng Liên Sơn.
Thái Nguyên nổi tiếng về cây:|chè|cà phê|cao su|hồ tiêu||Chè Thái Nguyên nổi tiếng.
Lễ hội Lồng Tồng là của dân tộc:|Tày|Ê-đê|Khmer|Ba Na||Lồng Tồng là lễ hội xuống đồng của người Tày.
Người dân miền núi làm nhà sàn để:|tránh ẩm thấp và thú dữ|đi biển dễ hơn|tránh tuyết|trồng cây trên mái||Nhà sàn thích hợp với vùng núi.
Nhà máy thủy điện Hòa Bình nằm trên sông:|sông Đà|sông Hồng|sông Mã|sông Cả||Thủy điện Hòa Bình trên sông Đà.
Ruộng bậc thang thường thấy ở:|vùng núi|vùng biển|sa mạc|đồng bằng ven biển||Người dân khai phá sườn núi trồng lúa.
""")),
    Topic(4, 11..18, "Đồng bằng Bắc Bộ (bổ sung 2)",
        items = items("""
Sông nào bồi đắp nên đồng bằng Bắc Bộ?|sông Hồng|sông Cửu Long|sông Mã|sông Đà Rằng||Phù sa sông Hồng bồi đắp.
Thủ đô Hà Nội nằm ở vùng:|đồng bằng Bắc Bộ|Tây Nguyên|Nam Bộ|duyên hải miền Trung||Hà Nội thuộc đồng bằng Bắc Bộ.
Văn Miếu – Quốc Tử Giám nằm ở:|Hà Nội|Huế|Đà Nẵng|Cần Thơ||Được xem là trường đại học đầu tiên của nước ta.
Dân ca quan họ là của tỉnh:|Bắc Ninh|Quảng Nam|Khánh Hòa|Cà Mau||Quan họ Bắc Ninh là di sản văn hóa.
Tranh dân gian Đông Hồ là sản phẩm của:|đồng bằng Bắc Bộ|Tây Nguyên|Nam Bộ|vùng núi Tây Bắc||Làng Đông Hồ thuộc Bắc Ninh.
Cây trồng chính của đồng bằng Bắc Bộ là:|lúa nước|cà phê|cao su|hồ tiêu||Đồng bằng Bắc Bộ là vựa lúa phía Bắc.
Đê ven sông được đắp để:|ngăn nước lũ|chứa nước biển|trồng rừng|dẫn gió||Đê giúp chống lũ.
""")),
    Topic(4, 19..26, "Duyên hải miền Trung (bổ sung 2)",
        items = items("""
Cố đô Huế nằm ở vùng:|duyên hải miền Trung|Nam Bộ|Tây Nguyên|đồng bằng Bắc Bộ||Huế là cố đô thời Nguyễn.
Đèo Hải Vân nằm giữa Huế và:|Đà Nẵng|Nha Trang|Quy Nhơn|Vinh||Đèo Hải Vân nối Huế và Đà Nẵng.
Nha Trang nổi tiếng về:|du lịch biển|trồng chè|khai thác than|ruộng bậc thang||Nha Trang có biển đẹp.
Duyên hải miền Trung thường chịu:|bão, lũ lụt, hạn hán|tuyết rơi|động đất lớn|băng giá||Miền Trung hay có thiên tai.
Hội An là di sản thế giới nổi tiếng với:|phố cổ|hang động|ruộng bậc thang|đảo san hô||Phố cổ Hội An.
Phong Nha – Kẻ Bàng nổi tiếng với:|hang động|phố cổ|ruộng bậc thang|rừng ngập mặn||Là di sản thế giới với nhiều hang động.
""")),
    Topic(4, 27..30, "Tây Nguyên (bổ sung 2)",
        items = items("""
Cây công nghiệp nổi tiếng nhất Tây Nguyên là:|cà phê|bông vải|mía|lúa nước||Tây Nguyên trồng nhiều cà phê.
Cồng chiêng Tây Nguyên là:|di sản văn hóa phi vật thể|loại cây công nghiệp|món ăn|loại nhà ở||Không gian văn hóa cồng chiêng.
Đất ba dan ở Tây Nguyên thích hợp trồng:|cà phê, cao su, hồ tiêu|lúa nước ngập sâu|chè mạn|cói||Đất đỏ ba dan màu mỡ.
Các dân tộc sinh sống lâu đời ở Tây Nguyên là:|Ê-đê, Gia Rai, Ba Na|Tày, Nùng, Thái|Khmer, Chăm, Hoa|Dao, Mông, Mường||Dân tộc bản địa Tây Nguyên.
Nhà rông là nơi sinh hoạt chung của:|buôn làng Tây Nguyên|làng chài ven biển|làng nghề đồng bằng|bản vùng cao Tây Bắc||Nhà rông của người Ba Na, Xơ Đăng.
""")),
    Topic(4, 31..35, "Nam Bộ (bổ sung 2)",
        items = items("""
Vựa lúa lớn nhất cả nước là:|đồng bằng sông Cửu Long|đồng bằng Bắc Bộ|Tây Nguyên|duyên hải miền Trung||Đồng bằng sông Cửu Long sản xuất nhiều lúa gạo.
Thành phố đông dân nhất cả nước là:|Thành phố Hồ Chí Minh|Hà Nội|Cần Thơ|Đà Nẵng||Thành phố Hồ Chí Minh đông dân nhất.
Sông Mê Công chảy vào Nam Bộ chia thành các nhánh:|sông Tiền và sông Hậu|sông Đà và sông Lô|sông Cả|sông Mã||Tiền Giang và Hậu Giang.
Chợ nổi là nét văn hóa đặc trưng của:|đồng bằng sông Cửu Long|Tây Bắc|Tây Nguyên|đồng bằng Bắc Bộ||Buôn bán trên sông.
Cần Thơ nằm ở:|đồng bằng sông Cửu Long|Tây Nguyên|đồng bằng Bắc Bộ|duyên hải miền Trung||Cần Thơ là trung tâm của đồng bằng sông Cửu Long.
Rừng ngập mặn ở Cà Mau có nhiều cây:|đước, mắm|thông|lim|pơ mu||Cây chịu được nước mặn.
Điểm cực Nam trên đất liền nước ta là:|Mũi Cà Mau|Mũi Né|Mũi Nai|Mũi Đại Lãnh||Mũi Cà Mau.
""")),
    Topic(5, 1..6, "Đất nước, dân cư và khí hậu (bổ sung)",
        items = items("""
Việt Nam nằm ở khu vực:|Đông Nam Á|Nam Á|Tây Âu|Bắc Mĩ||Nước ta thuộc Đông Nam Á.
Phía bắc nước ta giáp:|Trung Quốc|Lào|Cam-pu-chia|Thái Lan||Biên giới phía bắc với Trung Quốc.
Phía tây nước ta giáp:|Lào và Cam-pu-chia|Trung Quốc|Biển Đông|Thái Lan||Biên giới phía tây.
Khí hậu nước ta là khí hậu:|nhiệt đới gió mùa|ôn đới|hàn đới|sa mạc||Nóng ẩm, có gió mùa.
Dân tộc có số dân đông nhất ở nước ta là:|Kinh|Tày|Mường|Thái||Người Kinh chiếm đa số.
Nước ta có bao nhiêu dân tộc?|54|63|34|45||Có 54 dân tộc anh em.
Cây lương thực chính của nước ta là:|lúa gạo|cà phê|cao su|chè||Lúa gạo là cây lương thực chính.
Đảo lớn nhất nước ta là:|Phú Quốc|Cát Bà|Cồn Cỏ|Lý Sơn||Phú Quốc lớn nhất.
Thủ đô của nước ta là:|Hà Nội|Huế|Đà Nẵng|Thành phố Hồ Chí Minh||Hà Nội là thủ đô.
Đại dương lớn nhất thế giới là:|Thái Bình Dương|Đại Tây Dương|Ấn Độ Dương|Bắc Băng Dương||Thái Bình Dương lớn nhất.
Châu lục lớn nhất thế giới là:|châu Á|châu Phi|châu Âu|châu Mĩ||Châu Á lớn nhất.
""")),
    Topic(5, 7..12, "Dựng nước và giữ nước thuở đầu (bổ sung)",
        items = items("""
Hai Bà Trưng khởi nghĩa năm 40 để chống lại:|nhà Hán|nhà Tống|nhà Nguyên|quân Pháp||Khởi nghĩa Hai Bà Trưng chống ách đô hộ nhà Hán.
Bà Triệu khởi nghĩa vào năm:|248|40|542|938||Khởi nghĩa Bà Triệu năm 248.
Lý Bí lên ngôi vua, đặt tên nước là:|Vạn Xuân|Âu Lạc|Đại Việt|Đại Cồ Việt||Năm 544, nước Vạn Xuân ra đời.
Ngô Quyền đánh thắng quân Nam Hán trên sông:|Bạch Đằng|Như Nguyệt|sông Mã|sông Hồng||Chiến thắng Bạch Đằng năm 938.
Đinh Bộ Lĩnh lên ngôi, đặt tên nước là:|Đại Cồ Việt|Đại Việt|Vạn Xuân|Âu Lạc||Năm 968.
Kinh đô của nhà Đinh đặt ở:|Hoa Lư|Thăng Long|Huế|Phú Xuân||Kinh đô Hoa Lư.
""")),
    Topic(5, 13..18, "Nhà Lý, nhà Trần và khởi nghĩa Lam Sơn (bổ sung)",
        items = items("""
Lý Công Uẩn dời đô từ Hoa Lư ra Thăng Long vào năm:|1010|938|1075|1288||Chiếu dời đô năm 1010.
Người chỉ huy quân dân nhà Trần thắng quân Nguyên trên sông Bạch Đằng năm 1288 là:|Trần Hưng Đạo|Lê Lợi|Quang Trung|Lý Thường Kiệt||Trần Hưng Đạo là vị tướng tài.
Khởi nghĩa Lam Sơn do ai lãnh đạo?|Lê Lợi|Nguyễn Huệ|Trần Hưng Đạo|Phan Bội Châu||Lê Lợi lãnh đạo, Nguyễn Trãi giúp sức.
""")),
    Topic(5, 19..26, "Tây Sơn và thực dân Pháp xâm lược (bổ sung)",
        items = items("""
Nguyễn Huệ lên ngôi hoàng đế, lấy hiệu là:|Quang Trung|Gia Long|Minh Mạng|Thái Tổ||Vua Quang Trung.
Chiến thắng Ngọc Hồi – Đống Đa diễn ra năm:|1789|1288|1428|1945||Quang Trung đại phá quân Thanh.
Nhà Nguyễn được thành lập năm:|1802|1010|1428|1945||Nguyễn Ánh lên ngôi, lấy hiệu Gia Long.
Thực dân Pháp nổ súng xâm lược nước ta ở Đà Nẵng năm:|1858|1802|1945|1954||Mở đầu cuộc xâm lược của Pháp.
""")),
    Topic(5, 27..35, "Cách mạng, kháng chiến và đổi mới (bổ sung)",
        items = items("""
Nguyễn Tất Thành ra đi tìm đường cứu nước vào năm:|1911|1930|1945|1954||Từ bến Nhà Rồng.
Đảng Cộng sản Việt Nam thành lập năm:|1930|1945|1954|1975||Ngày 3 tháng 2 năm 1930.
Cách mạng tháng Tám thành công vào năm:|1945|1930|1954|1975||Giành độc lập năm 1945.
Chủ tịch Hồ Chí Minh đọc Tuyên ngôn Độc lập tại:|quảng trường Ba Đình|Huế|Sài Gòn|Điện Biên||Ngày 2 tháng 9 năm 1945.
Chiến dịch Điện Biên Phủ thắng lợi vào năm:|1954|1945|1975|1930||Chiến thắng Điện Biên Phủ năm 1954.
Chiến dịch Hồ Chí Minh toàn thắng vào ngày:|30 tháng 4 năm 1975|2 tháng 9 năm 1945|7 tháng 5 năm 1954|19 tháng 8 năm 1945||Đất nước thống nhất.
Công cuộc Đổi mới của nước ta bắt đầu từ năm:|1986|1975|1954|2000||Đổi mới năm 1986.
"""))
)
