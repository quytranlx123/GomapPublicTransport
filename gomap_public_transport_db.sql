-- MySQL dump 10.13  Distrib 9.3.0, for macos14.7 (x86_64)
--
-- Host: localhost    Database: gomap_public_transport_db
-- ------------------------------------------------------
-- Server version	9.3.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Favorite_Route`
--

DROP TABLE IF EXISTS `Favorite_Route`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Favorite_Route` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `route_id` int NOT NULL,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `route_id` (`route_id`),
  CONSTRAINT `favorite_route_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `User` (`id`),
  CONSTRAINT `favorite_route_ibfk_2` FOREIGN KEY (`route_id`) REFERENCES `Route` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Favorite_Route`
--

LOCK TABLES `Favorite_Route` WRITE;
/*!40000 ALTER TABLE `Favorite_Route` DISABLE KEYS */;
INSERT INTO `Favorite_Route` VALUES (1,4,6,'2025-04-21 20:50:00'),(2,5,7,'2025-04-21 20:50:00'),(3,6,8,'2025-04-21 20:50:00'),(4,7,1,'2025-04-21 20:50:00'),(5,8,10,'2025-04-21 20:50:00');
/*!40000 ALTER TABLE `Favorite_Route` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Notification`
--

DROP TABLE IF EXISTS `Notification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Notification` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `message` varchar(510) NOT NULL,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `message_type` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Notification`
--

LOCK TABLES `Notification` WRITE;
/*!40000 ALTER TABLE `Notification` DISABLE KEYS */;
INSERT INTO `Notification` VALUES (1,'Cảnh báo giao thông','Có tai nạn trên đường Nguyễn Văn Cừ, quận 1.','2025-04-21 20:49:39','alert'),(2,'Khuyến mãi vé tháng','Giảm giá 20% khi mua vé tháng xe buýt.','2025-04-21 20:49:39','promotion'),(3,'Thay đổi lịch trình','Tuyến xe buýt số 10 sẽ thay đổi lộ trình từ ngày 10/09.','2025-04-21 20:49:39','update'),(4,'Sự kiện cộng đồng','Mời tham gia sự kiện làm sạch môi trường tại quận 5.','2025-04-21 20:49:39','event'),(5,'Thông báo hệ thống','Hệ thống bảo trì vào lúc 2 giờ sáng ngày 15/09.','2025-04-21 20:49:39','system'),(6,'Cảnh báo kẹt xe','Kẹt xe nghiêm trọng tại ngã tư Hàng Xanh.','2025-04-21 20:50:00','alert'),(7,'Khuyến mãi vé tuần','Mua vé tuần giảm giá 15% từ ngày 20/09.','2025-04-21 20:50:00','promotion'),(8,'Cập nhật tuyến xe','Tuyến số 55 thay đổi giờ hoạt động từ 25/09.','2025-04-21 20:50:00','update'),(9,'Sự kiện giao lưu','Tham gia sự kiện giao lưu tại công viên Lê Thị Riêng ngày 22/09.','2025-04-21 20:50:00','event'),(10,'Thông báo bảo trì','Bảo trì hệ thống vé điện tử vào 3h sáng 16/09.','2025-04-21 20:50:00','system');
/*!40000 ALTER TABLE `Notification` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Route`
--

DROP TABLE IF EXISTS `Route`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Route` (
  `id` int NOT NULL AUTO_INCREMENT,
  `start_point` varchar(255) NOT NULL,
  `end_point` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `status` varchar(50) NOT NULL,
  `frequency` varchar(50) NOT NULL,
  `start_time` time DEFAULT NULL,
  `end_time` time DEFAULT NULL,
  `distance` float NOT NULL,
  `duration` int NOT NULL,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Route`
--

LOCK TABLES `Route` WRITE;
/*!40000 ALTER TABLE `Route` DISABLE KEYS */;
INSERT INTO `Route` VALUES (1,'Bến xe Miền Đông','Bến Thành','Tuyến số 01','active','15 phút','05:30:00','22:30:00',12.5,40,'2025-04-21 20:49:39'),(2,'Bến xe An Sương','Chợ Lớn','Tuyến số 10','active','20 phút','05:00:00','21:30:00',18.2,50,'2025-04-21 20:49:39'),(3,'Ngã tư Ga','Bến Thành','Tuyến số 26','active','12 phút','06:00:00','22:00:00',14,45,'2025-04-21 20:49:39'),(4,'Bến xe Củ Chi','Bến xe Miền Tây','Tuyến số 94','active','30 phút','04:30:00','20:00:00',45,90,'2025-04-21 20:49:39'),(5,'Khu Công Nghệ Cao','ĐH Quốc Gia','Tuyến số 99','active','10 phút','06:00:00','23:00:00',8.5,30,'2025-04-21 20:49:39'),(6,'Bến xe Chợ Lớn','ĐH Nông Lâm','Tuyến số 55','active','15 phút','05:45:00','22:15:00',22.3,60,'2025-04-21 20:50:00'),(7,'Tân Sơn Nhất','Bến xe Miền Đông','Tuyến số 152','active','20 phút','05:00:00','23:00:00',10.8,35,'2025-04-21 20:50:00'),(8,'Bến Thành','Suối Tiên','Tuyến số 56','active','12 phút','06:00:00','22:30:00',20.5,55,'2025-04-21 20:50:00'),(9,'Bến xe Miền Tây','Bình Chánh','Tuyến số 103','active','25 phút','05:30:00','21:00:00',15.7,45,'2025-04-21 20:50:00'),(10,'Ngã tư Phú Nhuận','Thảo Điền','Tuyến số 104','active','10 phút','06:15:00','22:45:00',7.2,25,'2025-04-21 20:50:00');
/*!40000 ALTER TABLE `Route` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Route_Station`
--

DROP TABLE IF EXISTS `Route_Station`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Route_Station` (
  `id` int NOT NULL AUTO_INCREMENT,
  `route_id` int NOT NULL,
  `station_id` int NOT NULL,
  `order_station` int NOT NULL,
  `distance` float NOT NULL,
  `duration` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `route_id` (`route_id`),
  KEY `station_id` (`station_id`),
  CONSTRAINT `route_station_ibfk_1` FOREIGN KEY (`route_id`) REFERENCES `Route` (`id`),
  CONSTRAINT `route_station_ibfk_2` FOREIGN KEY (`station_id`) REFERENCES `Station` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Route_Station`
--

LOCK TABLES `Route_Station` WRITE;
/*!40000 ALTER TABLE `Route_Station` DISABLE KEYS */;
INSERT INTO `Route_Station` VALUES (1,6,4,1,0,0),(2,6,17,2,5.5,15),(3,6,11,3,22.3,60),(4,7,12,1,0,0),(5,7,19,2,7.5,20),(6,7,1,3,10.8,35),(7,8,2,1,0,0),(8,8,20,2,15,40),(9,8,13,3,20.5,55),(10,10,15,1,0,0),(11,10,16,2,7.2,25);
/*!40000 ALTER TABLE `Route_Station` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Schedule`
--

DROP TABLE IF EXISTS `Schedule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Schedule` (
  `id` int NOT NULL AUTO_INCREMENT,
  `departure_time` time NOT NULL,
  `arrival_time` time NOT NULL,
  `vehicle_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `vehicle_id` (`vehicle_id`),
  CONSTRAINT `schedule_ibfk_1` FOREIGN KEY (`vehicle_id`) REFERENCES `Vehicle` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Schedule`
--

LOCK TABLES `Schedule` WRITE;
/*!40000 ALTER TABLE `Schedule` DISABLE KEYS */;
INSERT INTO `Schedule` VALUES (1,'06:00:00','06:40:00',1),(2,'06:15:00','07:05:00',2),(3,'06:30:00','07:15:00',3),(4,'06:45:00','08:15:00',4),(5,'07:00:00','07:30:00',5),(6,'06:00:00','07:00:00',6),(7,'06:30:00','07:05:00',7),(8,'06:15:00','07:10:00',8),(9,'05:45:00','06:30:00',9),(10,'06:45:00','07:10:00',10);
/*!40000 ALTER TABLE `Schedule` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Station`
--

DROP TABLE IF EXISTS `Station`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Station` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `latitude` float NOT NULL,
  `longitude` float NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Station`
--

LOCK TABLES `Station` WRITE;
/*!40000 ALTER TABLE `Station` DISABLE KEYS */;
INSERT INTO `Station` VALUES (1,'Bến xe Miền Đông',10.8016,106.711,'292 Đinh Bộ Lĩnh, Phường 26, Bình Thạnh, TP.HCM','2025-04-21 20:49:39'),(2,'Bến Thành',10.772,106.698,'Chợ Bến Thành, Quận 1, TP.HCM','2025-04-21 20:49:39'),(3,'Bến xe An Sương',10.8571,106.613,'QL22, Bà Điểm, Hóc Môn, TP.HCM','2025-04-21 20:49:39'),(4,'Chợ Lớn',10.7543,106.637,'Bến xe Chợ Lớn, Quận 5, TP.HCM','2025-04-21 20:49:39'),(5,'Ngã tư Ga',10.8686,106.703,'QL1A, Thạnh Lộc, Quận 12, TP.HCM','2025-04-21 20:49:39'),(6,'Bến xe Củ Chi',11.0071,106.499,'QL22, Thị trấn Củ Chi, TP.HCM','2025-04-21 20:49:39'),(7,'Bến xe Miền Tây',10.7454,106.628,'395 Kinh Dương Vương, Bình Tân, TP.HCM','2025-04-21 20:49:39'),(8,'Khu Công Nghệ Cao',10.8418,106.809,'Xa lộ Hà Nội, Quận 9, TP.HCM','2025-04-21 20:49:39'),(9,'ĐH Quốc Gia',10.8781,106.804,'Linh Trung, TP.Thủ Đức, TP.HCM','2025-04-21 20:49:39'),(10,'Phan Xích Long',10.7988,106.693,'Phan Xích Long, Phường 7, Phú Nhuận, TP.HCM','2025-04-21 20:49:39'),(11,'ĐH Nông Lâm',10.8712,106.794,'Linh Trung, TP.Thủ Đức, TP.HCM','2025-04-21 20:50:00'),(12,'Tân Sơn Nhất',10.8167,106.665,'Trường Sơn, Tân Bình, TP.HCM','2025-04-21 20:50:00'),(13,'Suối Tiên',10.8932,106.804,'Xa lộ Hà Nội, TP.Thủ Đức, TP.HCM','2025-04-21 20:50:00'),(14,'Bình Chánh',10.7197,106.581,'QL1A, Bình Chánh, TP.HCM','2025-04-21 20:50:00'),(15,'Ngã tư Phú Nhuận',10.7998,106.678,'Ngã tư Phú Nhuận, Phú Nhuận, TP.HCM','2025-04-21 20:50:00'),(16,'Thảo Điền',10.8039,106.737,'Thảo Điền, TP.Thủ Đức, TP.HCM','2025-04-21 20:50:00'),(17,'Công viên Lê Thị Riêng',10.7847,106.665,'Cách Mạng Tháng Tám, Quận 3, TP.HCM','2025-04-21 20:50:00'),(18,'Chợ Tân Định',10.7886,106.688,'Hai Bà Trưng, Quận 3, TP.HCM','2025-04-21 20:50:00'),(19,'Ngã tư Hàng Xanh',10.8036,106.706,'Điện Biên Phủ, Bình Thạnh, TP.HCM','2025-04-21 20:50:00'),(20,'Cầu Sài Gòn',10.8032,106.723,'Xa lộ Hà Nội, TP.Thủ Đức, TP.HCM','2025-04-21 20:50:00');
/*!40000 ALTER TABLE `Station` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `traffic_report`
--

DROP TABLE IF EXISTS `traffic_report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `traffic_report` (
  `id` int NOT NULL AUTO_INCREMENT,
  `address` varchar(255) NOT NULL,
  `latitude` float NOT NULL,
  `longitude` float NOT NULL,
  `image` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `user_id` int DEFAULT NULL,
  `is_verified` tinyint(1) NOT NULL DEFAULT '0',
  `title` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `traffic_report_ibfk_1` (`user_id`),
  CONSTRAINT `traffic_report_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `User` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `traffic_report`
--

LOCK TABLES `traffic_report` WRITE;
/*!40000 ALTER TABLE `traffic_report` DISABLE KEYS */;
INSERT INTO `traffic_report` VALUES (1,'Nguyễn Văn Cừ, Quận 1',10.7626,106.66,NULL,'Tai nạn xe máy, giao thông ùn tắc.','2025-04-21 20:49:39',1,1,''),(2,'Điện Biên Phủ, Quận Bình Thạnh',10.8046,106.714,NULL,'Đường đang sửa chữa, xe lưu thông chậm.','2025-04-21 20:49:39',NULL,0,''),(3,'Lê Lợi, Quận 1',10.7749,106.699,NULL,'Kẹt xe giờ cao điểm.','2025-04-21 20:49:39',3,1,''),(4,'Hoàng Văn Thụ, Quận Tân Bình',10.7993,106.669,NULL,'Cây đổ chắn đường.','2025-04-21 20:49:39',NULL,0,''),(5,'Phan Xích Long, Quận Phú Nhuận',10.7993,106.69,NULL,'Va chạm nhẹ giữa hai ô tô.','2025-04-21 20:49:39',NULL,1,''),(6,'Xa lộ Hà Nội, TP.Thủ Đức',10.8032,106.723,NULL,'Ùn tắc do xe container hỏng.','2025-04-21 20:50:00',4,1,'Kẹt xe nặng'),(7,'Cách Mạng Tháng Tám, Quận 3',10.7847,106.665,NULL,'Đường ngập nước sau mưa lớn.','2025-04-21 20:50:00',NULL,0,'Ngập lụt'),(8,'Hai Bà Trưng, Quận 3',10.7886,106.688,NULL,'Tai nạn xe máy, giao thông chậm.','2025-04-21 20:50:00',5,1,'Tai nạn'),(9,'Ngã tư Phú Nhuận, Phú Nhuận',10.7998,106.678,NULL,'Đèn giao thông hỏng, kẹt xe.','2025-04-21 20:50:00',NULL,0,'Hỏng đèn'),(10,'Thảo Điền, TP.Thủ Đức',10.8039,106.737,NULL,'Công trình xây dựng chắn đường.','2025-04-21 20:50:00',6,1,'Công trình');
/*!40000 ALTER TABLE `traffic_report` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `User`
--

DROP TABLE IF EXISTS `User`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `User` (
  `id` int NOT NULL AUTO_INCREMENT,
  `full_name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `phone` varchar(20) NOT NULL,
  `username` varchar(100) NOT NULL,
  `password` varchar(255) NOT NULL,
  `birthday` date DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `user_role` enum('admin','user') NOT NULL,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `gender` enum('male','female','other') DEFAULT NULL,
  `is_active` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `User`
--

LOCK TABLES `User` WRITE;
/*!40000 ALTER TABLE `User` DISABLE KEYS */;
INSERT INTO `User` VALUES (1,'admin','admin@gmail.com','92331','admin','$2a$12$INvgMJFt4OIdSTbhmRRe.uiWbYbnObP3OvASmQikztvnC5GxKLb1O','1990-05-20',NULL,'admin','2025-04-21 20:49:39','male',1),(3,'Nguyen Van A','nguyenvana@gmail.com','0901234567','nguyenvana','$2a$10$X9vG7jNpqJCeBa9skQ9OXuR6ftC6JkAkG7gFQgOvUjReMEKsI.q5a','1990-05-20',NULL,'admin','2025-04-21 20:49:39','male',1),(4,'Tran Thi B','tranthib@gmail.com','0912345678','tranthib','$2a$10$Y8vH9jMpqKDeCa0tkR9PYvT7gtD7KlBlH8gGRhPwztwnD6HxJ.r2b','1995-03-15',NULL,'user','2025-04-21 20:50:00','female',1),(5,'Le Van C','levanc@gmail.com','0923456789','levanc','$2a$10$Z0wI0kNprLFeDb1ukS0QZwU8iuE8JmCmI0hHSiQyzuynE7IyK.s4c','1988-07-22',NULL,'user','2025-04-21 20:50:00','male',1),(6,'Pham Thi D','phamthid@gmail.com','0934567890','phamthid','$2a$10$A1xJ1lOprMGeEc2vlT1RaxV9jvF9KnDnJ1iITjRzzyynF8IzL.t6e','1992-11-10',NULL,'user','2025-04-21 20:50:00','female',1),(7,'Hoang Van E','hoangvane@gmail.com','0945678901','hoangvane','$2a$10$B2yK2mPqsNHfFd3wmU2SbyW0kwG0LoEoK2jJUlSazyznG9JzM.u8f','1990-01-05',NULL,'admin','2025-04-21 20:50:00','male',1),(8,'Nguyen Thi F','nguyenthif@gmail.com','0956789012','nguyenthif','$2a$10$C3zL3nQrtOIgGe4xnV3TczX1lxH1MpFpL3kKVsTbzzyoH0KzN.w0g','1997-09-18',NULL,'user','2025-04-21 20:50:00','female',1);
/*!40000 ALTER TABLE `User` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_notification`
--

DROP TABLE IF EXISTS `user_notification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_notification` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int DEFAULT NULL,
  `notification_id` int NOT NULL,
  `send_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `is_read` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `user_notification_ibfk_1` (`user_id`),
  KEY `user_notification_ibfk_2` (`notification_id`),
  CONSTRAINT `user_notification_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `User` (`id`) ON DELETE SET NULL,
  CONSTRAINT `user_notification_ibfk_2` FOREIGN KEY (`notification_id`) REFERENCES `Notification` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_notification`
--

LOCK TABLES `user_notification` WRITE;
/*!40000 ALTER TABLE `user_notification` DISABLE KEYS */;
INSERT INTO `user_notification` VALUES (1,1,1,'2025-04-21 20:49:39',0),(2,NULL,2,'2025-04-21 20:49:39',1),(3,3,3,'2025-04-21 20:49:39',0),(4,NULL,4,'2025-04-21 20:49:39',1),(5,NULL,5,'2025-04-21 20:49:39',0),(6,4,6,'2025-04-21 20:50:00',0),(7,5,7,'2025-04-21 20:50:00',1),(8,6,8,'2025-04-21 20:50:00',0),(9,7,9,'2025-04-21 20:50:00',1),(10,8,10,'2025-04-21 20:50:00',0);
/*!40000 ALTER TABLE `user_notification` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Vehicle`
--

DROP TABLE IF EXISTS `Vehicle`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Vehicle` (
  `id` int NOT NULL AUTO_INCREMENT,
  `license_plate` varchar(50) NOT NULL,
  `vehicle_type` varchar(50) NOT NULL,
  `driver` varchar(255) NOT NULL,
  `capacity` int NOT NULL,
  `latitude` float NOT NULL,
  `longitude` float NOT NULL,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `route_id` int NOT NULL,
  `status` varchar(50) NOT NULL,
  `is_active` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `route_id` (`route_id`),
  CONSTRAINT `vehicle_ibfk_1` FOREIGN KEY (`route_id`) REFERENCES `Route` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Vehicle`
--

LOCK TABLES `Vehicle` WRITE;
/*!40000 ALTER TABLE `Vehicle` DISABLE KEYS */;
INSERT INTO `Vehicle` VALUES (1,'51B-12345','Bus','Nguyễn Văn A',50,10.7626,106.66,'2025-04-21 20:49:39','2025-04-21 20:49:39',1,'running',1),(2,'51B-23456','Bus','Trần Văn B',45,10.8046,106.714,'2025-04-21 20:49:39','2025-04-21 20:49:39',2,'stopped',1),(3,'51B-34567','Bus','Lê Thị C',40,10.7749,106.699,'2025-04-21 20:49:39','2025-04-21 20:49:39',3,'running',1),(4,'51B-45678','Bus','Phạm Văn D',55,10.7993,106.669,'2025-04-21 20:49:39','2025-04-21 20:49:39',4,'repairing',0),(5,'51B-56789','Bus','Hoàng Thị E',50,10.7993,106.69,'2025-04-21 20:49:39','2025-04-21 20:49:39',5,'running',1),(6,'51B-67890','Bus','Nguyen Thi G',50,10.7543,106.637,'2025-04-21 20:50:00','2025-04-21 20:50:00',6,'running',1),(7,'51B-78901','Bus','Tran Van H',45,10.8167,106.665,'2025-04-21 20:50:00','2025-04-21 20:50:00',7,'stopped',1),(8,'51B-89012','Bus','Le Thi I',40,10.772,106.698,'2025-04-21 20:50:00','2025-04-21 20:50:00',8,'running',1),(9,'51B-90123','Bus','Pham Van K',55,10.7454,106.628,'2025-04-21 20:50:00','2025-04-21 20:50:00',9,'running',1),(10,'51B-01234','Bus','Hoang Thi L',50,10.7998,106.678,'2025-04-21 20:50:00','2025-04-21 20:50:00',10,'stopped',1);
/*!40000 ALTER TABLE `Vehicle` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-04-29  6:52:31
