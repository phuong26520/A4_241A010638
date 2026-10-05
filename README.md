# Bài nâng cao Lab A4

## NC2. Lưu lịch sử năm phép tính gần nhất

Ứng dụng dùng ArrayList<String> để lưu năm phép tính thành công gần nhất và hiển thị bên dưới máy tính. Phép tính mới nhất nằm trên cùng; khi vượt quá năm phép tính, phép tính cũ nhất được xóa.

Lịch sử được lưu vào Bundle trong onSaveInstanceState và khôi phục trong onCreate, nên không bị mất khi xoay màn hình.

## NC3. Đổi màu dòng phân loại BMI

Dòng phân loại BMI hiển thị màu xanh khi bình thường, màu vàng khi thừa cân và màu đỏ khi béo phì.

Các màu được khai báo trong colors.xml, lấy bằng ContextCompat.getColor và áp dụng bằng setTextColor. Ngưỡng BMI theo tài liệu: dưới 18,5 là thiếu cân; từ 18,5 đến dưới 23 là bình thường; từ 23 đến dưới 25 là thừa cân; từ 25 trở lên là béo phì.