-- drop database gomap_public_transport_db;
create database gomap_public_transport_db;
USE gomap_public_transport_db;

CREATE TABLE User (
    id INT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    username VARCHAR(100) NOT NULL,
    password VARCHAR(255) NOT NULL,
    birthday DATE DEFAULT NULL,
    avatar VARCHAR(255) DEFAULT NULL,
	user_role ENUM('admin', 'user') NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
	gender ENUM('male', 'female', 'other') DEFAULT NULL,
    is_active BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE Notification (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    message VARCHAR(510) NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    message_type VARCHAR(50) NOT NULL
);

CREATE TABLE User_Notification (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    notification_id INT NOT NULL,
    send_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    is_read BOOLEAN NOT NULL DEFAULT FALSE,
    FOREIGN KEY (user_id) REFERENCES User(id),
    FOREIGN KEY (notification_id) REFERENCES Notification(id)
);

CREATE TABLE Traffic_Report (
    id INT AUTO_INCREMENT PRIMARY KEY,
    address VARCHAR(255) NOT NULL,
    latitude FLOAT NOT NULL,
    longitude FLOAT NOT NULL,
    image VARCHAR(255) DEFAULT NULL,
    description VARCHAR(255) DEFAULT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    user_id INT NOT NULL,
    is_verified BOOLEAN NOT NULL DEFAULT FALSE,
    FOREIGN KEY (user_id) REFERENCES User(id)
);

CREATE TABLE Route (
    id INT AUTO_INCREMENT PRIMARY KEY,
    start_point VARCHAR(255) NOT NULL,
    end_point VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    status VARCHAR(50) NOT NULL,
    frequency VARCHAR(50) NOT NULL,
    start_time TIME DEFAULT NULL,
    end_time TIME DEFAULT NULL,
    distance FLOAT NOT NULL,
    duration INT NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE Favorite_Route (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    route_id INT NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES User(id),
    FOREIGN KEY (route_id) REFERENCES Route(id)
);

CREATE TABLE Station (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    latitude FLOAT NOT NULL,
    longitude FLOAT NOT NULL,
    address VARCHAR(255) DEFAULT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE Route_Station (
    id INT AUTO_INCREMENT PRIMARY KEY,
    route_id INT NOT NULL,
    station_id INT NOT NULL,
    order_station INT NOT NULL,
    distance FLOAT NOT NULL,
    duration INT NOT NULL,
    FOREIGN KEY (route_id) REFERENCES Route(id),
    FOREIGN KEY (station_id) REFERENCES Station(id)
);

CREATE TABLE Vehicle (
    id INT AUTO_INCREMENT PRIMARY KEY,
    license_plate VARCHAR(50) NOT NULL,
    vehicle_type VARCHAR(50) NOT NULL,
    driver VARCHAR(255) NOT NULL,
    capacity INT NOT NULL,
    latitude FLOAT NOT NULL,
    longitude FLOAT NOT NULL,
	updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    route_id INT NOT NULL,
    status VARCHAR(50) NOT NULL,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    FOREIGN KEY (route_id) REFERENCES Route(id)
);

CREATE TABLE Schedule (
    id INT AUTO_INCREMENT PRIMARY KEY,
    departure_time TIME NOT NULL,
    arrival_time TIME NOT NULL,
    vehicle_id INT NOT NULL,
    FOREIGN KEY (vehicle_id) REFERENCES Vehicle(id)
);

-- Thêm dữ liệu vào bảng User

INSERT INTO User (full_name, email, phone, username, password, birthday, avatar, user_role, gender, is_active) VALUES
('admin', 'admin@gmail.com', 092331, 'admin', '$2a$12$INvgMJFt4OIdSTbhmRRe.uiWbYbnObP3OvASmQikztvnC5GxKLb1O', '1990-05-20', NULL, 'admin', 'male', TRUE),
('customer', 'customer@gmail.com', 092331, 'customer', '$2a$12$INvgMJFt4OIdSTbhmRRe.uiWbYbnObP3OvASmQikztvnC5GxKLb1O', '1990-05-20', NULL, 'user', 'male', TRUE),
('Nguyen Van A', 'nguyenvana@gmail.com', '0901234567', 'nguyenvana', '$2a$10$X9vG7jNpqJCeBa9skQ9OXuR6ftC6JkAkG7gFQgOvUjReMEKsI.q5a', '1990-05-20', NULL, 'admin', 'male', TRUE),
('Tran Thi B', 'tranthib@gmail.com', '0912345678', 'tranthib', '$2a$10$KcUdrQOf0aEKVZ6O3qFD1uB5kj0YQl4iQQE/mCf22V51Ye.lxW8A2', '1995-08-15', NULL, 'user', 'female', TRUE),
('Le Van C', 'levanc@gmail.com', '0923456789', 'levanc', '$2a$10$eJ/TKxy9zQ/FrzDfV1GLnOz6UOr6tA.C8kkX.v1JZmXc7qUq0SyCm', '1988-11-30', NULL, 'user', 'male', TRUE),
('Pham Thi D', 'phamthid@gmail.com', '0934567890', 'phamthid', '$2a$10$AQGpXZ9uFYVQFTX8C1lgXOaYjmDQaF.EE3t9XZDJNHcQn5E3hT8uG', '2000-03-25', NULL, 'user', 'female', TRUE),
('Hoang Van E', 'hoangvane@gmail.com', '0945678901', 'hoangvane', '$2a$10$Ov3p.Qw9Qe4ZW1m5TLYV/eiBBN6.xcxlsMHZcJQMY7PZt5FNK7pGe', '1993-07-10', NULL, 'admin', 'male', TRUE);

-- Thêm dữ liệu vào bảng Notification
INSERT INTO Notification (title, message, message_type) VALUES
('Cảnh báo giao thông', 'Có tai nạn trên đường Nguyễn Văn Cừ, quận 1.', 'alert'),
('Khuyến mãi vé tháng', 'Giảm giá 20% khi mua vé tháng xe buýt.', 'promotion'),
('Thay đổi lịch trình', 'Tuyến xe buýt số 10 sẽ thay đổi lộ trình từ ngày 10/09.', 'update'),
('Sự kiện cộng đồng', 'Mời tham gia sự kiện làm sạch môi trường tại quận 5.', 'event'),
('Thông báo hệ thống', 'Hệ thống bảo trì vào lúc 2 giờ sáng ngày 15/09.', 'system');

-- Thêm dữ liệu vào bảng User_Notification
INSERT INTO User_Notification (user_id, notification_id, is_read) VALUES
(1, 1, FALSE),
(2, 2, TRUE),
(3, 3, FALSE),
(4, 4, TRUE),
(5, 5, FALSE);

-- Thêm dữ liệu vào bảng Traffic_Report
INSERT INTO Traffic_Report (address, latitude, longitude, image, description, user_id, is_verified) VALUES
('Nguyễn Văn Cừ, Quận 1', 10.762622, 106.660172, NULL, 'Tai nạn xe máy, giao thông ùn tắc.', 1, TRUE),
('Điện Biên Phủ, Quận Bình Thạnh', 10.804567, 106.714478, NULL, 'Đường đang sửa chữa, xe lưu thông chậm.', 2, FALSE),
('Lê Lợi, Quận 1', 10.774934, 106.699257, NULL, 'Kẹt xe giờ cao điểm.', 3, TRUE),
('Hoàng Văn Thụ, Quận Tân Bình', 10.799263, 106.668922, NULL, 'Cây đổ chắn đường.', 4, FALSE),
('Phan Xích Long, Quận Phú Nhuận', 10.799349, 106.689750, NULL, 'Va chạm nhẹ giữa hai ô tô.', 5, TRUE);

-- Thêm dữ liệu vào bảng Route
INSERT INTO Route (start_point, end_point, name, status, frequency, start_time, end_time, distance, duration) VALUES
('Bến xe Miền Đông', 'Bến Thành', 'Tuyến số 01', 'active', '15 phút', '05:30', '22:30', 12.5, 40),
('Bến xe An Sương', 'Chợ Lớn', 'Tuyến số 10', 'active', '20 phút', '05:00', '21:30', 18.2, 50),
('Ngã tư Ga', 'Bến Thành', 'Tuyến số 26', 'active', '12 phút', '06:00', '22:00', 14.0, 45),
('Bến xe Củ Chi', 'Bến xe Miền Tây', 'Tuyến số 94', 'active', '30 phút', '04:30', '20:00', 45.0, 90),
('Khu Công Nghệ Cao', 'ĐH Quốc Gia', 'Tuyến số 99', 'active', '10 phút', '06:00', '23:00', 8.5, 30);

-- Thêm dữ liệu vào bảng Station
INSERT INTO Station (name, latitude, longitude, address) VALUES
('Bến xe Miền Đông', 10.801556, 106.711498, '292 Đinh Bộ Lĩnh, Phường 26, Bình Thạnh, TP.HCM'),
('Bến Thành', 10.772002, 106.698340, 'Chợ Bến Thành, Quận 1, TP.HCM'),
('Bến xe An Sương', 10.857124, 106.612576, 'QL22, Bà Điểm, Hóc Môn, TP.HCM'),
('Chợ Lớn', 10.754296, 106.636703, 'Bến xe Chợ Lớn, Quận 5, TP.HCM'),
('Ngã tư Ga', 10.868610, 106.703015, 'QL1A, Thạnh Lộc, Quận 12, TP.HCM'),
('Bến xe Củ Chi', 11.007148, 106.498729, 'QL22, Thị trấn Củ Chi, TP.HCM'),
('Bến xe Miền Tây', 10.745421, 106.628037, '395 Kinh Dương Vương, Bình Tân, TP.HCM'),
('Khu Công Nghệ Cao', 10.841802, 106.809444, 'Xa lộ Hà Nội, Quận 9, TP.HCM'),
('ĐH Quốc Gia', 10.878065, 106.803897, 'Linh Trung, TP.Thủ Đức, TP.HCM'),
('Phan Xích Long', 10.798836, 106.692642, 'Phan Xích Long, Phường 7, Phú Nhuận, TP.HCM');

-- Thêm dữ liệu vào bảng Vehicle
INSERT INTO Vehicle (license_plate, vehicle_type, driver, capacity, latitude, longitude, route_id, status, is_active) VALUES
('51B-12345', 'Bus', 'Nguyễn Văn A', 50, 10.762622, 106.660172, 1, 'running', TRUE),
('51B-23456', 'Bus', 'Trần Văn B', 45, 10.804567, 106.714478, 2, 'stopped', TRUE),
('51B-34567', 'Bus', 'Lê Thị C', 40, 10.774934, 106.699257, 3, 'running', TRUE),
('51B-45678', 'Bus', 'Phạm Văn D', 55, 10.799263, 106.668922, 4, 'repairing', FALSE),
('51B-56789', 'Bus', 'Hoàng Thị E', 50, 10.799349, 106.689750, 5, 'running', TRUE);

-- Thêm dữ liệu vào bảng Schedule
INSERT INTO Schedule (departure_time, arrival_time, vehicle_id) VALUES
('06:00', '06:40', 1),
('06:15', '07:05', 2),
('06:30', '07:15', 3),
('06:45', '08:15', 4),
('07:00', '07:30', 5);

-- Thêm cột 'title' vào bảng 'Traffic_Report'
ALTER TABLE Traffic_Report
ADD COLUMN title VARCHAR(255) NOT NULL;

