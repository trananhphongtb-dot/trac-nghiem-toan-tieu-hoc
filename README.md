# Bài Tập Tuần Tiểu Học


App Android bài tập tuần trắc nghiệm cho học sinh tiểu học (lớp 1 đến lớp 5), chạy offline,
không đăng nhập, không quảng cáo, không Firebase.


- Gói: `vn.phong.baitaptuan`
- Nền tảng: Kotlin + Jetpack Compose, AGP 8.11.1, Kotlin 2.1.21, compileSdk/targetSdk 36, minSdk 24, Java 17
- Build APK: GitHub Actions (`.github/workflows/build-apk.yml`), lấy APK debug trước, chưa làm AAB.
- Tên hiển thị của app: **Bài Tập Tuần Tiểu Học** (đổi ở `app/src/main/AndroidManifest.xml`, thuộc tính `android:label`).


## Môn học


Màn chính chọn môn, rồi chọn lớp, rồi chọn tuần 1–35 hoặc đề kỳ:


| Môn | Lớp | Cách sinh đề |
|---|---|---|
| Toán | 1–5 | Sinh ngẫu nhiên từ các dạng bài (số, phép tính, lời văn, hình, đo lường...) |
| Tiếng Việt | 1–5 | Ngân hàng nội dung: chữ cái/vần, chính tả, từ loại, câu, so sánh/nhân hóa, quan hệ từ, tả cảnh |
| Tiếng Anh | 1–5 | Từ vựng (hỏi hai chiều Anh–Việt) và mẫu câu hỏi đáp, ngữ pháp cơ bản |
| Tự nhiên và Xã hội (lớp 1–3) / Khoa học (lớp 4–5) | 1–5 | Ngân hàng nội dung theo mạch chủ đề |
| Lịch sử và Địa lí | 4–5 | Ngân hàng nội dung: địa lí các vùng, các mốc và nhân vật lịch sử |


- Mỗi tuần: tối đa 40 câu. Ngoài 35 tuần có 4 đề: Giữa học kỳ 1 (tuần 1–10), Cuối học kỳ 1
  (tuần 1–18), Giữa học kỳ 2 (tuần 19–26), Cuối năm (tuần 1–35).
- Lớp 1–3: đề giữa kỳ ghi là **đề luyện** (theo Thông tư 27 lớp 1–3 không có kiểm tra viết giữa kỳ bắt buộc).


## Đề không cố định


- **Toán** sinh bằng `Random` gieo theo thời gian: số, tên, đồ vật, thứ tự câu, thứ tự đáp án và đáp án
  nhiễu đều khác nhau mỗi lượt. 8 câu cuối mỗi đề là dạng khó phù hợp lớp.
- **Các môn khác** lấy từ ngân hàng nội dung (`SubjectBank.kt` và các file `*Bank.kt`). Mỗi lượt xáo thứ tự
  câu và đáp án, đáp án nhiễu chọn ngẫu nhiên từ các mục cùng nhóm. Đề tuần ưu tiên nội dung của tuần đó,
  thiếu thì bổ sung từ các chủ đề khác cùng lớp (ôn tập). Số câu độc lập trong ngân hàng còn hữu hạn
  nên làm lại nhiều lần sẽ thấy câu quen.
- Trong một lượt: không lặp câu, 4 đáp án không trùng nhau, đáp án đúng luôn hợp lệ.


## Nội dung Toán đã đối chiếu với phiếu bài tập tuần trên mạng


Đã xem phiếu bài tập cuối tuần Toán 3 Kết nối tri thức (tuần 2, 25, 26, 30) và đề kiểm tra các lớp, rồi bổ sung:


- `FIND_X`: tìm x kiểu `243 + x = 254 + 142`, `x - 207 = 312`, `x × 8 = 64`, `a : x = b`.
- `TIMES`: gấp lên / giảm đi một số lần, số này gấp mấy lần số kia, bằng một phần mấy.
- `DIGIT_EXT`: số lớn nhất, nhỏ nhất có n chữ số (cả trường hợp các chữ số khác nhau).
- `CALC_RIGHT`: chọn phép tính đúng trong 4 phép tính.
- Chỉnh lại khung Toán lớp 3 tuần 1–3: tuần 1 ôn số trong phạm vi 1000, tuần 2 ôn cộng trừ và tìm
  thành phần, tuần 3 ôn bảng nhân chia 2 và 5.


Các dạng cũ (so sánh, phân số, thập phân, hình học, đo lường, lời văn, biểu đồ...) giữ nguyên.


## Khung tuần Toán — chỗ chưa chắc chắn


Chi tiết ở `app/src/main/java/vn/phong/baitaptuan/data/WeekTopics.kt`:


- Lớp 2 và lớp 5: bảng tuần đầy đủ.
- Lớp 1, 3, 4: một số tuần ghi “(ước tính)”; trường có thể lệch 1 tuần ở các tuần ranh giới.
- Các môn ngoài Toán chia theo chủ đề theo mạch kiến thức, không bám số tuần chính xác của một bộ sách.


## Cấu trúc code


- `model/Models.kt`: `Subject`, `Question`, `WeekDef`, `HistoryEntry`...
