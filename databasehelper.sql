-- Xoá dữ liệu trong database và đặt giá trị cho trường id về 1
SET SQL_SAFE_UPDATES = 0;
DELETE FROM Favorite_Route WHERE user_id IS NOT NULL;
DELETE FROM Schedule WHERE vehicle_id IS NOT NULL;
DELETE FROM Vehicle WHERE license_plate IS NOT NULL;
DELETE FROM Route_Station WHERE route_id IS NOT NULL;
DELETE FROM Route WHERE name IS NOT NULL;
DELETE FROM Station WHERE id IS NOT NULL;
DELETE FROM Traffic_Report WHERE user_id IS NOT NULL;
DELETE FROM User_Notification WHERE user_id IS NOT NULL;
DELETE FROM Notification WHERE id IS NOT NULL;
DELETE FROM User WHERE id IS NOT NULL;
ALTER TABLE Favorite_Route AUTO_INCREMENT = 1;
ALTER TABLE Schedule AUTO_INCREMENT = 1;
ALTER TABLE Vehicle AUTO_INCREMENT = 1;
ALTER TABLE Route_Station AUTO_INCREMENT = 1;
ALTER TABLE Route AUTO_INCREMENT = 1;
ALTER TABLE Station AUTO_INCREMENT = 1;
ALTER TABLE Traffic_Report AUTO_INCREMENT = 1;
ALTER TABLE User_Notification AUTO_INCREMENT = 1;
ALTER TABLE Notification AUTO_INCREMENT = 1;
ALTER TABLE User AUTO_INCREMENT = 1;
SET SQL_SAFE_UPDATES = 1;


-- xuất file database, vị trí của file ở tại thư mục cmd đang thực hiện dòng lệnh
-- terminal hoặc cmd đã install mysql
-- terminal:
-- /bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
-- brew install mysql
mysqldump -u root -p gomap_public_transport_db > gomap_public_transport_db.sql
