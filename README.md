# GomapPublicTransport 🚍

Ứng dụng web Java hiển thị và quản lý thông tin giao thông công cộng trên bản đồ, xây dựng bằng **Spring MVC** và **Maven** trên NetBeans.

---

## 📚 Mục lục

- [🚀 Giới thiệu](#-giới-thiệu)
- [🧩 Tính năng](#-tính-năng)
- [⚙️ Công nghệ](#-công-nghệ)
- [📁 Cấu trúc dự án](#-cấu-trúc-dự-án)
- [🛠️ Cài đặt & chạy](#️-cài-đặt--chạy)
- [📖 Hướng dẫn sử dụng](#-hướng-dẫn-sử-dụng)
- [🔐 Cấu hình thêm](#-cấu-hình-thêm)
- [📝 TODO / Định hướng mở rộng](#-todo--định-hướng-mở-rộng)
- [🤝 Đóng góp](#-đóng-góp)
- [📜 License](#-license)
- [👤 Tác giả & Liên hệ](#-tác-giả--liên-hệ)

---

## 🚀 Giới thiệu

**GomapPublicTransport** là một ứng dụng web mô phỏng hệ thống bản đồ giao thông công cộng. Ứng dụng cho phép hiển thị tuyến đường, điểm dừng xe buýt trên bản đồ Google Maps và hỗ trợ các chức năng quản lý dữ liệu (CRUD) cho tuyến và điểm dừng.

---

## 🧩 Tính năng

- Hiển thị bản đồ với các tuyến xe buýt và điểm dừng.
- Quản lý thông tin tuyến:
- Thêm / sửa / xóa tuyến.
- Quản lý điểm dừng:
- Thêm / sửa / xóa điểm dừng.
- Tự động load dữ liệu tuyến & điểm dừng từ database.
- Hiển thị trực quan qua Google Maps.

---

## ⚙️ Công nghệ

| Thành phần     |         Công nghệ sử dụng               |
|----------------|-----------------------------------------|
| Backend        | Java, Spring MVC                        |
| Frontend       | Thymeleaf, HTML, JavaScript, React      |
| CSDL           | MySQL                                   |
| Build Tool     | Apache Maven                            |
| API bản đồ     | Mapbox Maps JavaScript API              |    
| IDE            | NetBeans(Backend), Visual Code(Frontend)|

---

## 📁 Cấu trúc dự án

```
GomapPublicTransport/
├── src/
│   └── java/
│       └── controller/     # Xử lý logic tuyến & điểm dừng
│       └── dao/            # Tương tác CSDL
│       └── model/          # Lớp dữ liệu Route, Stop
│       └── helper/         # Kết nối DB (DBConnect)
├── src/main/webapp/
│   └── WEB-INF/
│       └── jsp/            # Các file giao diện JSP
│   └── resources/          # CSS, JS, hình ảnh tĩnh
├── databasehelper.sql      # Script tạo dữ liệu mẫu
├── public_transport_db.sql# CSDL thay thế
├── pom.xml                 # Cấu hình Maven
└── README.md               # Tài liệu hướng dẫn
```

---

## 🛠️ Cài đặt & chạy

### 1. Clone dự án

```bash
git clone https://github.com/quytranlx123/GomapPublicTransport.git
cd GomapPublicTransport
```

### 2. Tạo CSDL MySQL

```sql
CREATE DATABASE gomap_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE gomap_db;
SOURCE databasehelper.sql;
-- Hoặc dùng file public_transport_db.sql
```

### 3. Cấu hình kết nối DB

Trong `src/java/helper/DBConnect.java`, chỉnh sửa:

```java
String jdbcURL = "jdbc:mysql://localhost:3306/gomap_db";
String jdbcUsername = "root";
String jdbcPassword = "your_password";
```

### 4. Build và chạy

- Mở bằng **NetBeans**
- Nhấn Run (F6)

**Hoặc dùng Maven:**

```bash
mvn clean package
# Deploy file WAR sinh ra trong thư mục `target` lên Tomcat
```

---

## 📖 Hướng dẫn sử dụng

### Truy cập ứng dụng:

```
http://localhost:8080/GomapPublicTransport/
```

### Quản lý tuyến:

- Danh sách tuyến: `/routes/list`
- Tạo mới: `/routes/create`
- Chỉnh sửa: `/routes/edit?id={id}`
- Xóa: `/routes/delete?id={id}`

### Quản lý điểm dừng:

- Danh sách điểm dừng: `/stops/list`
- Tạo mới: `/stops/create`
- Chỉnh sửa: `/stops/edit?id={id}`
- Xóa: `/stops/delete?id={id}`

---

## 🔐 Cấu hình thêm

### Google Maps API Key

- Đăng ký API Key tại: https://console.cloud.google.com/
- Chèn key vào file JSP có chứa bản đồ:

```html
<script src="https://maps.googleapis.com/maps/api/js?key=YOUR_API_KEY"></script>
```

---

## 📝 TODO / Định hướng mở rộng

- [ ] Xác thực và phân quyền user / admin
- [ ] Tìm tuyến đường tối ưu (route planning)
- [ ] Giao diện responsive / mobile-friendly
- [ ] Cập nhật lịch trình và thông báo

---

## 🤝 Đóng góp

1. Fork repo
2. Tạo nhánh mới:
```bash
git checkout -b feature/ten-tinh-nang
```
3. Commit thay đổi:
```bash
git commit -m "Thêm tính năng xyz"
```
4. Push lên GitHub:
```bash
git push origin feature/ten-tinh-nang
```
5. Mở Pull Request

---

## 📜 License

Hiện tại chưa có file LICENSE. Có thể sử dụng giấy phép mã nguồn mở như:

- [MIT License](https://opensource.org/licenses/MIT)
- [Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0)

---

## 👤 Tác giả & Liên hệ

- **Tên:** Trần Ngọc Quí
- **GitHub:** [@quytranlx123](https://github.com/quytranlx123)

- **Tên:** Đỗ Đình Nhật
- **GitHub:** [@DinhNhat20](https://github.com/DinhNhat20)

---
