# Trắc Nghiệm Toán Tiểu Học — Bài tập tuần

App Android trắc nghiệm toán lớp 1 đến lớp 5, chạy offline, không đăng nhập, không quảng cáo, không Firebase.

- Gói: `vn.phong.toantieuhoc`
- Nền tảng: Kotlin + Jetpack Compose, AGP 8.11.1, Kotlin 2.1.21, compileSdk/targetSdk 36, minSdk 24, Java 17
- Build APK: GitHub Actions, chỉ lấy APK debug trước, chưa làm AAB.

## Bản tuần mới (thay thế hoàn toàn bản cũ chia theo chủ đề/mức độ)

Bản cũ chia theo chủ đề (Số và phép tính, Hình học, Đo lường, …) và mức độ
Dễ/Vừa/Khó đã bị bỏ. Bản mới chia theo **bài tập tuần**:

- Chọn lớp 1–5, rồi chọn tuần 1–35: học kỳ 1 tuần 1–18, học kỳ 2 tuần 19–35.
- Mỗi tuần ghi chủ đề của tuần đó, theo khung bộ **Kết nối tri thức** trong báo
  cáo nghiên cứu `research_notes/bai-tap-tuan-toan-tieu-hoc-20261103-1535/report.md`.
- Ngoài 35 tuần, mỗi lớp có 4 đề: Giữa học kỳ 1 (tuần 1–10), Cuối học kỳ 1
  (tuần 1–18), Giữa học kỳ 2 (tuần 19–26), Cuối năm (tuần 1–35).
- Lớp 1–3: đề giữa kỳ được ghi rõ là **đề luyện**, vì theo Thông tư 27 lớp 1–3
  không có kiểm tra viết giữa kỳ bắt buộc; chỉ lớp 4–5 mới có kiểm tra viết
  giữa kỳ. Đề cuối kỳ và cuối năm thì áp dụng cho mọi lớp.

## Đề không cố định — quan trọng nhất

Không có ngân hàng câu cố định làm đề. Mỗi lần làm một tuần hoặc một đề kỳ,
app sinh ngẫu nhiên một bộ mới bằng `Random` gieo bằng thời gian hệ thống
(`nanoTime`, không seed cố định):

- 40 câu trắc nghiệm mỗi lượt, đủ các dạng của tuần đó (hoặc của phạm vi đề kỳ).
- Số, tên, đồ vật trong bài toán có lời văn, thứ tự câu, thứ tự đáp án và
  đáp án nhiễu đều ngẫu nhiên theo lượt.
- Nút “Làm đề mới (đề khác)” sinh lại từ đầu, không lặp lại bộ cũ.
- Trong một lượt: không lặp câu (so sánh theo nội dung câu), 4 đáp án không
  trùng nhau, đáp án đúng luôn được kiểm tra hợp lệ (không NaN, không trống).
- Một tờ bài tập tuần trên giấy thường chỉ khoảng 15–30 câu, nên bộ 40 câu ở
  đây là ngân hàng luyện tập, không phải tờ đề tuần nguyên bản — điều này
  cũng được ghi trong báo cáo nghiên cứu.

## Màn làm bài

- Câu hỏi trong một ô (card), 4 đáp án ở dưới.
- Nút Câu trước và Câu tiếp, sơ đồ 40 câu (bấm vào số để nhảy tới câu đó).
- Thanh tiến trình trên đầu theo số câu đã làm, kèm đồng hồ đếm ngược
  giới hạn 60 phút; hết giờ tự nộp bài.
- Câu đã trả lời thì khóa, không trả lời lại được, hiện giải thích ngay.
- Nộp bài ra điểm thang 10, sao, và xem lại câu sai kèm đáp án đúng + giải thích.
- Lịch sử làm bài lưu trên máy bằng SharedPreferences, tối đa 50 lượt.

## Giao diện và icon

- Icon app là vector tự vẽ (`app/src/main/res/drawable/ic_app.xml`: nền xanh,
  tam giác vàng, dấu cộng trắng, chấm cam), không dùng icon mặc định
  `ic_dialog_info` nữa.
- Ảnh nền dọc là gradient + hình vẽ vector bằng Canvas trong app (3 chủ đề:
  Xanh dương, Hồng cam, Xanh lá, chọn được ở trang chính), không tải ảnh
  ngoài nên APK vẫn nhẹ.

## Khung tuần đã dùng — và chỗ chưa chắc chắn

Chi tiết đầy đủ ở `app/src/main/java/vn/phong/toantieuhoc/data/WeekTopics.kt`:

- Lớp 2 và lớp 5: bảng tuần đầy đủ từng tuần, đã xác minh trong báo cáo.
- Lớp 1, 3, 4: một số tuần trong báo cáo chỉ là giai đoạn (ghi dấu ~), app
  ghi rõ các tuần đó bằng chữ “(ước tính)” trên nút tuần và trong tiêu đề đề.
  Trường có thể lệch 1 tuần ở các tuần ranh giới.
- Tuần 16 lớp 1 không có trong báo cáo nên app ghi là “Luyện tập chung
  (ước tính)”, dùng dạng của giai đoạn xung quanh, không bịa chủ đề mới.
- Các khoảng tuần chồng nhau (ví dụ tuần 8 lớp 2 vừa kết thúc cộng/trừ 20
  vừa bắt đầu kg/lít) được xử lý theo nguyên tắc khoảng khai sau thắng;
  nội dung cũ vẫn quay lại ở đề kỳ và đề ôn tập.
- Các dạng câu chưa kiểm chứng bằng biên dịch thật: bộ sinh được rà soát
  bằng tay (ngoặc, import, đáp án trùng, NaN, và lỗi `(range step n).random`
  đã sửa thành `.toList().random`), nhưng chưa chạy được trình biên dịch
  Kotlin trong môi trường này (không có kotlinc/Java) và chưa build APK.
  Bước đẩy lên GitHub và build GitHub Actions sẽ là bước kiểm chứng biên
  dịch thật đầu tiên cho bản tuần này.

## Build

Đẩy code lên GitHub, workflow `.github/workflows/build-apk.yml` sẽ chạy
`gradle assembleDebug` và tải artifact `TracNghiemToan-debug` về để kiểm tra.
Build local trong máy Muse hiện bị chặn ở kết nối TCP nội bộ của Gradle daemon
(giống các app trước), nên đường build chính là GitHub Actions.
