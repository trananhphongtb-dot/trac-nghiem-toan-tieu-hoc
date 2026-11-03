# Trắc Nghiệm Toán Tiểu Học

App Android trắc nghiệm toán lớp 1 đến lớp 5, chạy offline, không đăng nhập, không quảng cáo.

- Gói: `vn.phong.toantieuhoc`
- Nền tảng: Kotlin + Jetpack Compose, AGP 8.11.1, Kotlin 2.1.21, compileSdk/targetSdk 36, minSdk 24, Java 17
- Build APK: GitHub Actions, chỉ lấy APK debug trước, chưa làm AAB.

## Cách ngân hàng câu hỏi hoạt động

Ngân hàng có 2 lớp, để vừa có chất lượng vừa phong phú:

1. **Câu cố định viết tay** trong `app/src/main/java/vn/phong/toantieuhoc/data/FixedQuestions.kt`:
   hiện có 33 câu, lớp 1: 6, lớp 2: 6, lớp 3: 6, lớp 4: 7, lớp 5: 8,
   đủ các chủ đề Số và phép tính, Hình học, Đo lường,
   Toán có lời văn, Phân số - Thập phân và Toán nâng cao.
   Sửa câu nào thì sửa đúng 1 dòng ở file này.

2. **Câu sinh tự động** trong `Generators.kt`:
   máy tự đổi số trong đúng phạm vi lớp đó, tự tạo đáp án nhiễu hợp lý
   (lệch 1, lệch 10, đổi chỗ chữ số...), trộn đáp án, chống lặp câu trong 1 lượt.
   Nhờ vậy mỗi lần bấm "Làm đề mới" là 1 đề khác, không học vẹt đáp án.
   Phần Số là trọng tâm, nhiều dạng nhất: cộng, trừ, nhân, chia, so sánh,
   số liền trước/sau, tìm số chưa biết, cộng 3 số, làm tròn, nhân chia
   với 10/100/1000, tính 2 bước.

## Số dạng thật mỗi lớp (không tính đổi số)

Dạng thật = 1 ý tưởng toán khác nhau, đếm bằng mã nguồn, không phải
số biến thể số. Tổng cộng có 108 dạng mới trong `RealForms.kt`,
`RealForms2.kt`, `RealForms3.kt`, cộng với khoảng 24-28 dạng cũ mỗi lớp
trong `Generators.kt`, trừ 2-3 dạng trùng nhau:

- Lớp 1: khoảng 83-84 dạng (dạng mới: 59)
- Lớp 2: khoảng 99-100 dạng (dạng mới: 75)
- Lớp 3: khoảng 87-88 dạng (dạng mới: 66)
- Lớp 4: khoảng 90-91 dạng (dạng mới: 66)
- Lớp 5: khoảng 103-104 dạng (dạng mới: 78)

Ví dụ dạng mới gồm: tìm số lớn nhất/bé nhất/ở giữa trong 3 số,
chữ số hàng chục/trăm, số chẵn/lẻ, gấp đôi/một nửa, tìm thừa số
chưa biết, so sánh tổng, tính nhanh nhóm thành 100, đọc/viết số
bằng chữ, chu vi tam giác/ngũ giác, diện tích tam giác/hình bình hành,
thể tích hình lập phương, đổi đơn vị phức hợp, tiền thừa, thứ trong tuần,
bài toán tổng - hiệu, dãy số nhân, dãy số bình phương, số có 2 chữ số
từ tổng/hiệu các chữ số, và các dạng lớp đầu như tách số, số gồm
mấy chục mấy đơn vị, sắp xếp tăng/giảm dần, hình không có cạnh.

## Số biến thể số mỗi lớp

Ngoài số dạng trên, mỗi dạng lại sinh ra hàng nghìn biến thể số.
Chỉ tính riêng phần Số, cận dưới an toàn là:

- Lớp 1: hơn 6.500 biến thể
- Lớp 2: hơn 8.000 biến thể
- Lớp 3: hơn 10.000 biến thể
- Lớp 4: hơn 12.000 biến thể
- Lớp 5: hơn 12.000 biến thể

Ví dụ lớp 1: riêng phép cộng a + b với a, b từ 2 đến 100 đã có
99 × 99 = 9.801 đề khác nhau. Màn hình chọn chủ đề trong app cũng hiện
con số này, hàm tính ở `minBankSize()` trong `Generators.kt`.
Lưu ý trung thực: đây là số đề sinh được từ các mẫu số, không phải
33 câu viết tay. Câu viết tay giữ vai trò câu mẫu chuẩn chương trình,
còn độ phong phú đến từ cả dạng mới và bộ sinh tự động.

Mỗi câu có: lớp, chủ đề, mức độ, đề bài, 4 đáp án, đáp án đúng, lời giải ngắn.

## Giao diện

- Trang chính: 5 ô lớp to, bấm là vào.
- Chọn chủ đề, mức độ Dễ / Vừa / Khó, chế độ Luyện tập 10 câu hoặc Kiểm tra 20 câu.
- Làm bài: 1 câu / 1 màn hình, 4 đáp án to, trả lời xong báo đúng/sai ngay kèm lời giải.
- Kết quả: điểm, số sao, xem lại toàn bộ câu sai.
- Lịch sử: lưu trên máy bằng SharedPreferences, tối đa 50 lượt gần nhất.

## Thêm câu hỏi mới

Thêm 1 dòng `fixed(...)` vào `FixedQuestions.kt` là đủ, không cần sửa màn hình.
Muốn thêm dạng toán mới thì thêm 1 hàm sinh vào `Generators.kt` và gọi trong `generateOne()`.

## Build

Đẩy code lên GitHub, workflow `.github/workflows/build-apk.yml` sẽ chạy
`gradle assembleDebug` và tải artifact `TracNghiemToan-debug` về cho anh test.
Build local trong máy Muse hiện bị chặn ở kết nối TCP nội bộ của Gradle daemon
(giống app Bé Tập Đếm và app quản lý điện thoại con), nên đường build chính là GitHub Actions.
