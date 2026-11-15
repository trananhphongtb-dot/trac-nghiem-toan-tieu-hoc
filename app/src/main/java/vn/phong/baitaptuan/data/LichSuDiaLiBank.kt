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
"""))
)
