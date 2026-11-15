package vn.phong.baitaptuan.data

/** Tiếng Anh lớp 1-5: từ vựng và mẫu câu theo chương trình tiểu học (bộ Global Success,
 *  nhóm chủ đề phổ biến). Mỗi chủ đề là một nhóm từ vựng + vài mẫu câu. */

private const val Q_VI = "Từ “{k}” có nghĩa là gì?"
private const val Q_EN = "Từ tiếng Anh nào có nghĩa là “{v}”?"
private const val E_PAIR = "“{k}” nghĩa là “{v}”."

private fun vocab(block: String) = PairSet(Q_VI, Q_EN, pairList(block), E_PAIR)

private val COLORS = """
red|màu đỏ
blue|màu xanh dương
green|màu xanh lá
yellow|màu vàng
pink|màu hồng
black|màu đen
white|màu trắng
brown|màu nâu
purple|màu tím
"""
private val NUMBERS = """
one|số một
two|số hai
three|số ba
four|số bốn
five|số năm
six|số sáu
seven|số bảy
eight|số tám
nine|số chín
ten|số mười
"""
private val SCHOOL = """
book|quyển sách
pen|bút mực
pencil|bút chì
ruler|thước kẻ
eraser|cục tẩy
schoolbag|cặp sách
desk|bàn học
chair|cái ghế
board|cái bảng
notebook|quyển vở
"""
private val FAMILY = """
father|bố
mother|mẹ
brother|anh hoặc em trai
sister|chị hoặc em gái
grandmother|bà
grandfather|ông
baby|em bé
"""
private val ANIMALS = """
cat|con mèo
dog|con chó
bird|con chim
fish|con cá
duck|con vịt
rabbit|con thỏ
cow|con bò
pig|con lợn
horse|con ngựa
elephant|con voi
monkey|con khỉ
tiger|con hổ
"""
private val FOOD = """
rice|cơm
bread|bánh mì
milk|sữa
egg|quả trứng
apple|quả táo
banana|quả chuối
orange|quả cam
cake|cái bánh ngọt
fish and chips|cá và khoai tây chiên
"""
private val TOYS = """
ball|quả bóng
doll|búp bê
car|xe ô tô
kite|con diều
robot|người máy
teddy bear|gấu bông
train|tàu hỏa
plane|máy bay
bike|xe đạp
"""
private val BODY = """
head|cái đầu
eye|mắt
ear|tai
nose|mũi
mouth|miệng
hand|bàn tay
arm|cánh tay
leg|cẳng chân
face|khuôn mặt
"""
private val SCHOOL_PLACES = """
library|thư viện
gym|phòng thể dục
playground|sân chơi
classroom|lớp học
canteen|căng tin
computer room|phòng máy tính
music room|phòng âm nhạc
art room|phòng mĩ thuật
"""
private val DAYS = """
Monday|thứ Hai
Tuesday|thứ Ba
Wednesday|thứ Tư
Thursday|thứ Năm
Friday|thứ Sáu
Saturday|thứ Bảy
Sunday|chủ nhật
"""
private val MONTHS = """
January|tháng Một
February|tháng Hai
March|tháng Ba
April|tháng Tư
May|tháng Năm
June|tháng Sáu
July|tháng Bảy
August|tháng Tám
September|tháng Chín
October|tháng Mười
November|tháng Mười một
December|tháng Mười hai
"""
private val JOBS = """
teacher|giáo viên
doctor|bác sĩ
nurse|y tá
farmer|nông dân
driver|tài xế
singer|ca sĩ
worker|công nhân
pilot|phi công
police officer|cảnh sát
engineer|kỹ sư
"""
private val FREE_TIME = """
reading|đọc sách
swimming|bơi
cycling|đạp xe
cooking|nấu ăn
painting|vẽ tranh
singing|hát
dancing|nhảy múa
playing football|chơi bóng đá
watching TV|xem ti vi
"""
private val WEATHER = """
sunny|có nắng
rainy|có mưa
cloudy|nhiều mây
windy|có gió
snowy|có tuyết
hot|nóng
cold|lạnh
"""
private val COUNTRIES = """
Viet Nam|nước Việt Nam
England|nước Anh
America|nước Mỹ
Australia|nước Úc
Japan|nước Nhật Bản
China|nước Trung Quốc
Thailand|nước Thái Lan
France|nước Pháp
"""
private val NATIONALITIES = """
Vietnamese|người Việt Nam
English|người Anh
American|người Mỹ
Australian|người Úc
Japanese|người Nhật Bản
Chinese|người Trung Quốc
Thai|người Thái Lan
French|người Pháp
"""
private val SUBJECTS = """
Maths|môn Toán
Science|môn Khoa học
Music|môn Âm nhạc
Art|môn Mĩ thuật
PE|môn Thể dục
History|môn Lịch sử
Geography|môn Địa lí
IT|môn Tin học
"""
private val ROUTINE = """
get up|thức dậy
have breakfast|ăn sáng
go to school|đi học
have lunch|ăn trưa
do homework|làm bài tập
go to bed|đi ngủ
brush teeth|đánh răng
"""
private val PAST_SIMPLE = """
go|went
see|saw
eat|ate
buy|bought
drink|drank
take|took
come|came
make|made
write|wrote
"""

internal fun tiengAnhTopics(): List<Topic> = listOf(
    // ---------------- Lớp 1 ----------------
    Topic(1, 1..8, "Chào hỏi và màu sắc",
        pairSets = listOf(vocab(COLORS)),
        items = items("""
Khi gặp bạn lần đầu trong ngày, em nói:|Hello!|Goodbye!|Thank you!|Sorry!||Hello nghĩa là xin chào.
Khi chia tay bạn, em nói:|Goodbye!|Hello!|Good morning!|Hi!||Goodbye nghĩa là tạm biệt.
Buổi sáng, em chào cô giáo:|Good morning!|Good night!|Good afternoon!|Goodbye!||Good morning nghĩa là chào buổi sáng.
Khi đi ngủ, em nói:|Good night!|Good morning!|Hello!|Hi!||Good night nghĩa là chúc ngủ ngon.
Khi muốn cảm ơn, em nói:|Thank you!|Sorry!|Goodbye!|Hello!||Thank you nghĩa là cảm ơn.
""")),
    Topic(1, 9..18, "Số đếm và đồ dùng học tập",
        pairSets = listOf(vocab(NUMBERS), vocab(SCHOOL))),
    Topic(1, 19..28, "Gia đình và con vật",
        pairSets = listOf(vocab(FAMILY), vocab(ANIMALS))),
    Topic(1, 29..35, "Đồ chơi và thức ăn",
        pairSets = listOf(vocab(TOYS), vocab(FOOD))),

    // ---------------- Lớp 2 ----------------
    Topic(2, 1..8, "Chào hỏi và giới thiệu bản thân",
        pairSets = listOf(vocab(COLORS), vocab(NUMBERS)),
        items = items("""
Chọn câu hỏi tên bạn:|What's your name?|How are you?|How old are you?|What's this?||What's your name? nghĩa là Bạn tên là gì?
Câu trả lời cho “What's your name?” là:|My name is Mai.|I'm fine.|I'm seven.|It's a pen.||Giới thiệu tên: My name is...
Câu trả lời cho “How are you?” là:|I'm fine, thank you.|My name is Nam.|I'm seven.|It's a cat.||I'm fine = Mình khỏe.
Câu trả lời cho “How old are you?” là:|I'm seven.|I'm fine.|My name is Mai.|It's a book.||I'm seven = Mình bảy tuổi.
""")),
    Topic(2, 9..18, "Trường lớp và gia đình",
        pairSets = listOf(vocab(SCHOOL), vocab(FAMILY)),
        items = items("""
Câu nào hỏi “Đây là gì?”|What's this?|Who's this?|How are you?|Where is it?||What's this? = Đây là gì?
Câu trả lời cho “What's this?” (đây là quyển sách) là:|It's a book.|He's my father.|I'm fine.|My name is Mai.||It's a book. = Đó là quyển sách.
Câu hỏi “Who's this?” dùng để hỏi:|đây là ai|đây là cái gì|bạn bao nhiêu tuổi|bạn tên gì||Who = ai.
""")),
    Topic(2, 19..28, "Con vật và đồ chơi",
        pairSets = listOf(vocab(ANIMALS), vocab(TOYS)),
        items = items("""
Câu nào có nghĩa “Mình thích con mèo.”?|I like cats.|I have a cat.|I see a bird.|I am a cat.||like = thích.
Câu “I have a doll.” nghĩa là:|Mình có một con búp bê.|Mình thích búp bê.|Mình là búp bê.|Mình nhìn búp bê.||have = có.
""")),
    Topic(2, 29..35, "Thức ăn và cơ thể",
        pairSets = listOf(vocab(FOOD), vocab(BODY)),
        items = items("""
Khi muốn hỏi “Bạn muốn uống gì?” em nói:|What do you want to drink?|What's this?|How are you?|Where is it?||drink = uống.
Câu “I'm hungry.” nghĩa là:|Mình đói.|Mình khát.|Mình mệt.|Mình vui.||hungry = đói.
Câu “I'm thirsty.” nghĩa là:|Mình khát.|Mình đói.|Mình mệt.|Mình vui.||thirsty = khát.
""")),

    // ---------------- Lớp 3 ----------------
    Topic(3, 1..8, "Chào hỏi và tên bạn bè",
        pairSets = listOf(vocab(COLORS), vocab(NUMBERS)),
        items = items("""
Hỏi “Bạn khỏe không?” bằng tiếng Anh:|How are you?|What's your name?|How old are you?|Where are you from?
Trả lời “How old are you?” (mình chín tuổi):|I'm nine.|I'm fine.|I'm Mai.|I'm in Class 3A.
Câu “Nice to meet you.” nghĩa là:|Rất vui được gặp bạn.|Tạm biệt.|Cảm ơn bạn.|Chúc ngủ ngon.
Câu trả lời cho “Nice to meet you.” là:|Nice to meet you, too.|I'm fine.|It's a pen.|Goodbye.||too = cũng vậy.
""")),
    Topic(3, 9..18, "Trường học và đồ dùng",
        pairSets = listOf(vocab(SCHOOL_PLACES), vocab(SCHOOL)),
        items = items("""
Trả lời “Is this your book?” (đúng):|Yes, it is.|No, I don't.|I'm fine.|Yes, I do.
Trả lời “What's this?” (đây là cái thước):|It's a ruler.|He's a ruler.|I'm a ruler.|They are rulers.
Câu hỏi nào hỏi “Đây có phải cái bàn không?”|Is this a desk?|What is this?|Who is this?|Where is the desk?
""")),
    Topic(3, 19..26, "Đồ chơi và thú cưng",
        pairSets = listOf(vocab(TOYS), vocab(ANIMALS)),
        items = items("""
Hỏi “Bạn có con mèo không?” bằng tiếng Anh:|Do you have a cat?|Is this a cat?|How are you?|Who is this?
Trả lời “Do you have a dog?” (có):|Yes, I do.|Yes, I am.|Yes, it is.|No, I am.
Số nhiều của “toy” là:|toys|toies|toyes|toy's
""")),
    Topic(3, 27..35, "Thời tiết, cơ thể và hoạt động",
        pairSets = listOf(vocab(BODY), vocab(WEATHER)),
        items = items("""
Hỏi thời tiết hôm nay bằng tiếng Anh:|What's the weather like today?|What time is it?|How old are you?|Where are you from?
Trả lời “What's the weather like?” (trời nắng):|It's sunny.|It's a pen.|I'm fine.|My name is Nam.
Câu hỏi nào hỏi “Mấy giờ rồi?”|What time is it?|What day is it?|How are you?|Who is this?||time = giờ.
""")),

    // ---------------- Lớp 4 ----------------
    Topic(4, 1..8, "Các ngày trong tuần và hoạt động",
        pairSets = listOf(vocab(DAYS), vocab(SUBJECTS)),
        items = items("""
Hỏi “Hôm nay là thứ mấy?” bằng tiếng Anh:|What day is it today?|What time is it?|What's the weather like?|How old are you?
Trả lời “What day is it today?” (thứ Hai):|It's Monday.|It's sunny.|It's a pen.|I'm fine.
Ngày nào đứng ngay sau thứ Sáu (Friday)?|Saturday|Thursday|Sunday|Monday||Thứ tự: Friday, Saturday, Sunday.
""")),
    Topic(4, 9..16, "Tháng và ngày sinh nhật",
        pairSets = listOf(vocab(MONTHS)),
        items = items("""
Hỏi “Sinh nhật bạn là khi nào?” bằng tiếng Anh:|When is your birthday?|What day is it today?|How old are you?|Where are you from?
Trả lời “When is your birthday?”:|It's on the fifth of May.|It's sunny.|I'm fine.|It's a ruler.
Tháng đứng ngay sau tháng Ba (March) là:|April|February|May|June||Thứ tự: March, April, May.
""")),
    Topic(4, 17..26, "Nghề nghiệp và sở thích",
        pairSets = listOf(vocab(JOBS), vocab(FREE_TIME)),
        items = items("""
Hỏi “Bố bạn làm nghề gì?” bằng tiếng Anh:|What does your father do?|What is your father's name?|How old is your father?|Where is your father?
Trả lời “What does your father do?” (bố là bác sĩ):|He's a doctor.|She's a doctor.|It's a doctor.|I'm a doctor.||father là nam nên dùng he.
Hỏi “Bạn thích làm gì khi rảnh?” bằng tiếng Anh:|What do you do in your free time?|What time is it?|What day is it?|How old are you?
""")),
    Topic(4, 27..35, "Thời tiết, giờ và nơi chốn",
        pairSets = listOf(vocab(WEATHER), vocab(SCHOOL_PLACES)),
        items = items("""
Trả lời “What time is it?” (7 giờ đúng):|It's seven o'clock.|It's seven.|It's on seven.|I'm seven.
“Where is the book?” – “It's ... the desk.” (ở trên bàn)|on|in|at|under||on = ở trên bề mặt.
“Where is the cat?” – “It's ... the box.” (ở trong hộp)|in|on|at|from||in = ở trong.
""")),

    // ---------------- Lớp 5 ----------------
    Topic(5, 1..8, "Quốc gia, quốc tịch và địa điểm",
        pairSets = listOf(vocab(COUNTRIES), vocab(NATIONALITIES)),
        items = items("""
Hỏi “Bạn đến từ đâu?” bằng tiếng Anh:|Where are you from?|What's your name?|How old are you?|What do you do?
Trả lời “Where are you from?”:|I'm from Viet Nam.|I'm Vietnam.|I from Viet Nam.|I'm in Viet Nam from.
Hỏi “Quốc tịch của bạn là gì?” bằng tiếng Anh:|What nationality are you?|Where are you from?|What's your name?|What time is it?
""")),
    Topic(5, 9..18, "Thói quen hằng ngày",
        pairSets = listOf(vocab(ROUTINE), vocab(SUBJECTS)),
        items = items("""
Chọn câu đúng: He ___ up at six every day.|gets|get|getting|got||Chủ ngữ số ít (he) ở thì hiện tại đơn: get thêm s.
Chọn câu đúng: She ___ to school by bike.|goes|go|going|gone||Chủ ngữ số ít (she): go thêm es.
Chọn câu đúng: They ___ breakfast at 6:30.|have|has|having|had||Chủ ngữ số nhiều (they) dùng have.
Hỏi “Bạn thức dậy lúc mấy giờ?” bằng tiếng Anh:|What time do you get up?|What do you do?|Where are you from?|How old are you?
""")),
    Topic(5, 19..27, "Quá khứ đơn",
        pairSets = listOf(PairSet("Quá khứ của “{k}” là:", "Dạng nguyên thể của “{v}” là:", pairList(PAST_SIMPLE), "Quá khứ của “{k}” là “{v}”.")),
        items = items("""
Hỏi “Hôm qua bạn đã làm gì?” bằng tiếng Anh:|What did you do yesterday?|What do you do every day?|What are you doing now?|What will you do?
Chọn câu đúng: I ___ to the zoo yesterday.|went|go|goes|going||yesterday là dấu hiệu quá khứ.
Chọn câu đúng: She ___ TV last night.|watched|watch|watches|watching||Quá khứ của watch là watched.
Chọn câu đúng: They ___ football on Sunday. (đã chơi)|played|play|plays|playing||Quá khứ của play là played.
""")),
    Topic(5, 28..35, "Sức khỏe, thời tiết và ôn tập",
        pairSets = listOf(vocab(WEATHER), vocab(FREE_TIME)),
        items = items("""
Hỏi “Bạn bị làm sao?” bằng tiếng Anh:|What's the matter?|What's your name?|How old are you?|Where are you from?
Trả lời “What's the matter?” (đau đầu):|I have a headache.|I'm headache.|It's a headache.|I'm in headache.
Lời khuyên cho người đau đầu: You ___ see a doctor.|should|shouldn't|are|do||should = nên.
"""))
)
