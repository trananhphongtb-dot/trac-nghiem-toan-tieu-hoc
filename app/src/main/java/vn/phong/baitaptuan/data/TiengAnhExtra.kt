package vn.phong.baitaptuan.data

/** Tiếng Anh bổ sung: thêm từ vựng theo chủ đề, từ trái nghĩa, số nhiều và mẫu câu. */

private fun vocabX(block: String) =
    PairSet("Từ “{k}” có nghĩa là gì?", "Từ tiếng Anh nào có nghĩa là “{v}”?", pairList(block), "“{k}” nghĩa là “{v}”.")

private val X_CLOTHES = """
shirt|áo sơ mi
T-shirt|áo phông
skirt|chân váy
dress|váy liền
shorts|quần soóc
trousers|quần dài
hat|cái mũ
cap|mũ lưỡi trai
shoes|đôi giày
socks|đôi tất
jacket|áo khoác
scarf|khăn quàng
"""
private val X_FRUIT = """
mango|quả xoài
grapes|quả nho
pineapple|quả dứa
watermelon|quả dưa hấu
strawberry|quả dâu tây
lemon|quả chanh vàng
coconut|quả dừa
peach|quả đào
pear|quả lê
papaya|quả đu đủ
"""
private val X_VEG = """
carrot|cà rốt
potato|khoai tây
tomato|cà chua
cabbage|bắp cải
onion|hành tây
cucumber|dưa chuột
corn|ngô
pumpkin|bí ngô
beans|đậu
"""
private val X_DRINK = """
water|nước lọc
juice|nước ép
tea|trà
coffee|cà phê
lemonade|nước chanh
milkshake|sữa lắc
"""
private val X_HOUSE = """
house|ngôi nhà
room|căn phòng
bedroom|phòng ngủ
kitchen|nhà bếp
bathroom|phòng tắm
living room|phòng khách
garden|khu vườn
door|cửa ra vào
window|cửa sổ
table|cái bàn
bed|cái giường
sofa|ghế sô-pha
lamp|đèn bàn
fan|cái quạt
television|tivi
"""
private val X_TOWN = """
hospital|bệnh viện
market|chợ
park|công viên
zoo|sở thú
library|thư viện
supermarket|siêu thị
cinema|rạp chiếu phim
post office|bưu điện
bus stop|trạm xe buýt
farm|nông trại
beach|bãi biển
museum|bảo tàng
bank|ngân hàng
restaurant|nhà hàng
bakery|tiệm bánh
"""
private val X_JOBS = """
teacher|giáo viên
doctor|bác sĩ
nurse|y tá
farmer|nông dân
driver|tài xế
police officer|cảnh sát
firefighter|lính cứu hỏa
pilot|phi công
singer|ca sĩ
cook|đầu bếp
worker|công nhân
engineer|kĩ sư
dentist|nha sĩ
artist|họa sĩ
"""
private val X_ACTIONS = """
run|chạy
jump|nhảy
swim|bơi
fly|bay
walk|đi bộ
sing|hát
dance|nhảy múa
read|đọc
write|viết
draw|vẽ
eat|ăn
drink|uống
sleep|ngủ
cook|nấu ăn
climb|leo trèo
open|mở
close|đóng
"""
private val X_ADJ = """
big|to
small|nhỏ
tall|cao
short|thấp
long|dài
hot|nóng
cold|lạnh
happy|vui
sad|buồn
fast|nhanh
slow|chậm
beautiful|xinh đẹp
clean|sạch
dirty|bẩn
"""
private val X_OPP = """
big|small
hot|cold
happy|sad
tall|short
new|old
fast|slow
clean|dirty
open|close
up|down
day|night
in|out
yes|no
black|white
"""
private val X_VEHICLES = """
bus|xe buýt
taxi|xe taxi
motorbike|xe máy
bicycle|xe đạp
boat|con thuyền
ship|tàu thủy
helicopter|máy bay trực thăng
truck|xe tải
airplane|máy bay
train|tàu hỏa
"""
private val X_SEASON = """
spring|mùa xuân
summer|mùa hè
autumn|mùa thu
winter|mùa đông
sunny|có nắng
rainy|có mưa
windy|có gió
cloudy|có mây
snowy|có tuyết
stormy|có bão
"""
private val X_PLURAL = """
book|books
box|boxes
child|children
man|men
woman|women
foot|feet
tooth|teeth
mouse|mice
bus|buses
watch|watches
city|cities
baby|babies
knife|knives
leaf|leaves
"""
private val X_ADV = """
always|luôn luôn
usually|thường xuyên
often|hay
sometimes|thỉnh thoảng
never|không bao giờ
"""

private fun opp(block: String) = PairSet(
    "Từ trái nghĩa với “{k}” là:", "Từ trái nghĩa với “{v}” là:", pairList(block), "“{k}” và “{v}” là hai từ trái nghĩa."
)

private fun plural(block: String) = PairSet(
    "Số nhiều của “{k}” là:", "Số ít của “{v}” là:", pairList(block), "Số ít: {k}, số nhiều: {v}."
)

internal fun tiengAnhExtraTopics(): List<Topic> = listOf(
    // ---------------- Lớp 1 ----------------
    Topic(1, 1..12, "Trái cây và đồ uống", pairSets = listOf(vocabX(X_FRUIT), vocabX(X_DRINK))),
    Topic(1, 13..24, "Quần áo", pairSets = listOf(vocabX(X_CLOTHES))),
    Topic(1, 25..35, "Hành động đơn giản", pairSets = listOf(vocabX(X_ACTIONS)),
        items = items("""
Câu nào có nghĩa “Mình thích màu đỏ.”?|I like red.|I like blue.|I am red.|It is red.||like = thích, red = màu đỏ.
Khi muốn xin lỗi, em nói:|Sorry!|Thank you!|Hello!|Goodbye!||Sorry nghĩa là xin lỗi.
Khi ai đó giúp em, em nói:|Thank you!|Sorry!|Good night!|Hello!||Thank you nghĩa là cảm ơn.
“Stand up!” nghĩa là:|Đứng lên!|Ngồi xuống!|Mở sách ra!|Im lặng!||stand up = đứng lên.
“Sit down!” nghĩa là:|Ngồi xuống!|Đứng lên!|Chạy đi!|Nhảy lên!||sit down = ngồi xuống.
“Open your book!” nghĩa là:|Mở sách ra!|Đóng sách lại!|Viết đi!|Vẽ đi!||open = mở.
“Close your book!” nghĩa là:|Đóng sách lại!|Mở sách ra!|Đọc sách!|Cất bút đi!||close = đóng.
""")),

    // ---------------- Lớp 2 ----------------
    Topic(2, 1..12, "Ngôi nhà", pairSets = listOf(vocabX(X_HOUSE)),
        items = items("""
Where is the cat? It's ___ the box. (ở trong hộp)|in|on|under|behind||in = ở trong.
The book is ___ the table. (ở trên bàn)|on|in|under|at||on = ở trên.
The ball is ___ the chair. (ở dưới ghế)|under|on|in|next||under = ở dưới.
""")),
    Topic(2, 13..24, "Trái cây, rau và quần áo", pairSets = listOf(vocabX(X_FRUIT), vocabX(X_VEG), vocabX(X_CLOTHES))),
    Topic(2, 25..35, "Hành động và tính từ", pairSets = listOf(vocabX(X_ACTIONS), opp(X_OPP)),
        items = items("""
Do you like ice cream? Trả lời “có”:|Yes, I do.|Yes, I am.|Yes, it is.|Yes, I can.||Do you like...? trả lời Yes, I do.
Can you swim? Trả lời “có”:|Yes, I can.|Yes, I do.|Yes, I am.|Yes, it is.||Can you...? trả lời Yes, I can.
What color is it? Trả lời đúng là:|It's red.|It's a cat.|I'm seven.|I'm fine.||Hỏi màu sắc thì trả lời màu.
How many books? Câu này hỏi về:|số lượng|màu sắc|tên|tuổi||How many = bao nhiêu.
""")),

    // ---------------- Lớp 3 ----------------
    Topic(3, 1..10, "Nhà cửa và đồ vật", pairSets = listOf(vocabX(X_HOUSE), vocabX(X_CLOTHES)),
        items = items("""
This is ___ apple.|an|a|the|is||apple bắt đầu bằng nguyên âm nên dùng “an”.
I ___ a student.|am|is|are|be||I đi với am.
She ___ my sister.|is|am|are|be||She đi với is.
They ___ my friends.|are|is|am|be||They đi với are.
I have two ___. (con mèo)|cats|cat|cates|catses||Hai con mèo: thêm s.
""")),
    Topic(3, 11..22, "Trái cây, rau và đồ uống", pairSets = listOf(vocabX(X_FRUIT), vocabX(X_VEG), vocabX(X_DRINK))),
    Topic(3, 23..35, "Hành động, tính từ và nơi chốn", pairSets = listOf(vocabX(X_ACTIONS), opp(X_OPP), vocabX(X_TOWN)),
        items = items("""
___ is your name?|What|Where|Who|When||Hỏi tên dùng What.
___ are you from?|Where|What|Who|How old||Where ... from = từ đâu đến.
He ___ a book every night.|reads|read|reading|readed||He đi với động từ thêm s: reads.
Where is the school? Trả lời “It's ___ the park.” (cạnh công viên)|next to|in|on|at||next to = bên cạnh.
""")),

    // ---------------- Lớp 4 ----------------
    Topic(4, 1..10, "Nơi chốn trong thành phố", pairSets = listOf(vocabX(X_TOWN)),
        items = items("""
Hỏi giờ bằng tiếng Anh:|What time is it?|What day is it?|How old are you?|What's this?||What time = mấy giờ.
“It's seven o'clock.” nghĩa là:|Bây giờ là 7 giờ.|Hôm nay là thứ Bảy.|Mình 7 tuổi.|Có 7 người.||o'clock = giờ đúng.
Hỏi “Hôm nay là thứ mấy?”|What day is it today?|What time is it?|Where are you?|Who are you?||What day = thứ mấy.
Trả lời “When is your birthday?”|It's in May.|It's Monday.|I'm eight.|Yes, I do.||Hỏi ngày sinh, trả lời bằng tháng.
""")),
    Topic(4, 11..20, "Nghề nghiệp và phương tiện", pairSets = listOf(vocabX(X_JOBS), vocabX(X_VEHICLES)),
        items = items("""
What does your father do? Trả lời:|He's a doctor.|He's tall.|He's fine.|He's seven.||What does ... do? hỏi nghề nghiệp.
She ___ apples.|likes|like|liking|liked||She đi với động từ thêm s.
I'm good ___ drawing.|at|in|on|to||good at = giỏi về.
How much is it? Câu này hỏi về:|giá tiền|số lượng|tuổi|giờ||How much = bao nhiêu tiền.
""")),
    Topic(4, 21..30, "Mùa, thời tiết và số nhiều", pairSets = listOf(vocabX(X_SEASON), plural(X_PLURAL)),
        items = items("""
What's the weather like today? Trả lời:|It's sunny.|It's seven.|It's a book.|It's Monday.||Hỏi thời tiết, trả lời tính từ thời tiết.
He is ___ a book now.|reading|read|reads|to read||Hiện tại tiếp diễn: be + V-ing.
""")),
    Topic(4, 31..35, "Từ trái nghĩa và hoạt động", pairSets = listOf(opp(X_OPP), vocabX(X_ACTIONS), vocabX(X_ADJ))),

    // ---------------- Lớp 5 ----------------
    Topic(5, 1..9, "Nghề nghiệp, nơi chốn và phương tiện", pairSets = listOf(vocabX(X_JOBS), vocabX(X_TOWN), vocabX(X_VEHICLES)),
        items = items("""
Which is correct?|She can swim.|She can swims.|She cans swim.|She can to swim.||can + động từ nguyên mẫu.
There ___ two books on the desk.|are|is|am|be||Two books là số nhiều nên dùng are.
There ___ a cat in the garden.|is|are|am|be||A cat là số ít nên dùng is.
You ___ brush your teeth twice a day.|should|shouldn't|can't|don't||should = nên.
""")),
    Topic(5, 10..18, "Trạng từ tần suất và số nhiều", pairSets = listOf(vocabX(X_ADV), plural(X_PLURAL)),
        items = items("""
She ___ to school every day.|goes|go|going|went||She đi với goes ở thì hiện tại đơn.
How often do you go to the library? Trả lời:|Twice a week.|Yesterday.|At home.|Yes, I do.||How often = bao lâu một lần.
My brother is ___ than me.|taller|tall|tallest|more tall||So sánh hơn: tall → taller.
She is the ___ student in my class.|tallest|taller|tall|most tall||So sánh nhất: tall → tallest.
""")),
    Topic(5, 19..27, "Quá khứ và dự định", pairSets = listOf(opp(X_OPP), vocabX(X_SEASON)),
        items = items("""
We ___ football yesterday.|played|play|plays|playing||yesterday: dùng quá khứ.
He ___ his homework last night.|did|do|does|doing||Quá khứ của do là did.
What are you going to do tomorrow? Trả lời:|I'm going to visit my grandmother.|I visited my grandmother.|I visit my grandmother yesterday.|I visiting my grandmother.||tomorrow: dùng be going to.
How far is it from your house to school? Trả lời:|It's about two kilometers.|By bus.|It's seven o'clock.|Yes, it is.||How far = bao xa.
""")),
    Topic(5, 28..35, "Ôn tập từ vựng", pairSets = listOf(vocabX(X_HOUSE), vocabX(X_ACTIONS), vocabX(X_ADJ)))
)
