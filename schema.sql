-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               8.0.30 - MySQL Community Server - GPL
-- Server OS:                    Win64
-- HeidiSQL Version:             12.11.0.7065
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

-- Dumping database structure for car_service_db
DROP DATABASE IF EXISTS `car_service_db`;
CREATE DATABASE IF NOT EXISTS `car_service_db` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `car_service_db`;

-- Dumping structure for table car_service_db.customers
CREATE TABLE IF NOT EXISTS `customers` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `first_name` varchar(100) NOT NULL,
  `last_name` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table car_service_db.customers: ~1 rows (approximately)
INSERT INTO `customers` (`id`, `first_name`, `last_name`, `email`, `phone`, `address`, `created_at`) VALUES
	(1, 'Herathasd', 'Yasiru', 'harshithayasiru34@gmail.com', '+94770371157', '', '2026-05-14 14:01:56');

-- Dumping structure for table car_service_db.users
CREATE TABLE IF NOT EXISTS `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password_hash` varchar(255) NOT NULL,
  `role` enum('ADMIN','CUSTOMER') NOT NULL DEFAULT 'CUSTOMER',
  `customer_id` bigint DEFAULT NULL,
  `is_active` tinyint(1) DEFAULT '1',
  `last_login` datetime DEFAULT NULL,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table car_service_db.users: ~2 rows (approximately)
INSERT INTO `users` (`id`, `username`, `email`, `password_hash`, `role`, `customer_id`, `is_active`, `last_login`, `created_at`, `updated_at`) VALUES
	(1, 'admin', 'admin@carservice.com', 'Admin@1234', 'ADMIN', NULL, 1, NULL, '2026-05-14 19:27:28', '2026-05-14 19:27:28'),
	(2, 'harshithayasiru34@gmail.com', 'harshithayasiru34@gmail.com', '12345678', 'CUSTOMER', 1, 1, NULL, '2026-05-14 14:01:56', '2026-05-14 14:01:56');

-- Dumping structure for table car_service_db.vehicles
CREATE TABLE IF NOT EXISTS `vehicles` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `customer_id` bigint NOT NULL,
  `license_plate` varchar(20) NOT NULL,
  `brand` varchar(50) NOT NULL,
  `model` varchar(50) NOT NULL,
  `year` int NOT NULL,
  `mileage` int DEFAULT '0',
  `next_service_mileage` int DEFAULT '0',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `license_plate` (`license_plate`),
  KEY `customer_id` (`customer_id`),
  CONSTRAINT `vehicles_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table car_service_db.vehicles: ~2 rows (approximately)
INSERT INTO `vehicles` (`id`, `customer_id`, `license_plate`, `brand`, `model`, `year`, `mileage`, `next_service_mileage`, `created_at`) VALUES
	(1, 1, 'abc-1234', 'kp', 'sh', 2000, 50000, 55000, '2026-05-14 14:02:20'),
	(2, 1, 'anc-5678', 'askj', 'asdbh', 2035, 12000, 17000, '2026-05-14 14:02:34');

-- Dumping structure for table car_service_db.staff
CREATE TABLE IF NOT EXISTS `staff` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `first_name` varchar(100) NOT NULL,
  `last_name` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `role` enum('MECHANIC','ADVISOR','MANAGER','ADMIN','TECHNICIAN','RECEPTIONIST') NOT NULL DEFAULT 'MECHANIC',
  `salary` double DEFAULT NULL,
  `hire_date` date DEFAULT NULL,
  `active` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table car_service_db.staff: ~1 rows (approximately)
INSERT INTO `staff` (`id`, `first_name`, `last_name`, `email`, `phone`, `role`, `salary`, `hire_date`, `active`) VALUES
	(1, 'Herath', 'Yasiru', 'harshithayasiru34@gmail.com', '+94770371157', 'MANAGER', 567890, '2026-05-13', 1);

-- Dumping structure for table car_service_db.service_catalog
CREATE TABLE IF NOT EXISTS `service_catalog` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `service_name` varchar(100) NOT NULL,
  `description` text,
  `price` double NOT NULL,
  `duration_minutes` int DEFAULT NULL,
  `category` varchar(50) DEFAULT NULL,
  `active` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table car_service_db.service_catalog: ~5 rows (approximately)
INSERT INTO `service_catalog` (`id`, `service_name`, `description`, `price`, `duration_minutes`, `category`, `active`) VALUES
	(1, 'Oil Changesda', 'Full synthetic oil change with filter replacement', 49.99, 30, 'Maintenance', 1),
	(2, 'Brake Service', 'Brake pad inspection and replacement', 149.99, 90, 'Safety', 1),
	(3, 'Tire Rotation', 'Rotate all four tires for even wear', 29.99, 45, 'Maintenance', 1),
	(4, 'Engine Tune-Up', 'Full engine inspection and tune-up service', 199.99, 120, 'Performance', 1),
	(5, 'AC Service', 'Air conditioning inspection and recharge', 89.99, 60, 'Comfort', 1);

-- Dumping structure for table car_service_db.bookings
CREATE TABLE IF NOT EXISTS `bookings` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `vehicle_id` bigint NOT NULL,
  `service_id` bigint NOT NULL,
  `staff_id` bigint DEFAULT NULL,
  `booking_date` datetime NOT NULL,
  `status` enum('PENDING','CONFIRMED','IN_PROGRESS','COMPLETED','CANCELLED') DEFAULT 'PENDING',
  `total_amount` double DEFAULT NULL,
  `payment_status` enum('UNPAID','PAID') DEFAULT 'UNPAID',
  `payment_method` varchar(50) DEFAULT NULL,
  `notes` text,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `vehicle_id` (`vehicle_id`),
  KEY `service_id` (`service_id`),
  KEY `staff_id` (`staff_id`),
  CONSTRAINT `bookings_ibfk_1` FOREIGN KEY (`vehicle_id`) REFERENCES `vehicles` (`id`) ON DELETE CASCADE,
  CONSTRAINT `bookings_ibfk_2` FOREIGN KEY (`service_id`) REFERENCES `service_catalog` (`id`) ON DELETE CASCADE,
  CONSTRAINT `bookings_ibfk_3` FOREIGN KEY (`staff_id`) REFERENCES `staff` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table car_service_db.bookings: ~3 rows (approximately)
INSERT INTO `bookings` (`id`, `vehicle_id`, `service_id`, `staff_id`, `booking_date`, `status`, `total_amount`, `payment_status`, `payment_method`, `notes`, `created_at`) VALUES
	(1, 1, 3, NULL, '2026-05-21 05:30:00', 'CONFIRMED', 29.99, 'UNPAID', NULL, '', '2026-05-14 14:02:53'),
	(2, 1, 4, NULL, '2026-05-21 05:30:00', 'COMPLETED', 199.99, 'UNPAID', NULL, '', '2026-05-14 14:02:53'),
	(3, 1, 1, NULL, '2026-05-21 05:30:00', 'CONFIRMED', 49.99, 'UNPAID', NULL, '', '2026-05-14 14:02:53');

-- Dumping structure for table car_service_db.feedback
CREATE TABLE IF NOT EXISTS `feedback` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `booking_id` bigint NOT NULL,
  `customer_id` bigint NOT NULL,
  `rating` int NOT NULL,
  `comment` text,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `booking_id` (`booking_id`),
  KEY `customer_id` (`customer_id`),
  CONSTRAINT `feedback_ibfk_1` FOREIGN KEY (`booking_id`) REFERENCES `bookings` (`id`) ON DELETE CASCADE,
  CONSTRAINT `feedback_ibfk_2` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`id`) ON DELETE CASCADE,
  CONSTRAINT `feedback_chk_1` CHECK ((`rating` between 1 and 5))
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table car_service_db.feedback: ~1 rows (approximately)
INSERT INTO `feedback` (`id`, `booking_id`, `customer_id`, `rating`, `comment`, `created_at`) VALUES
	(1, 2, 1, 4, 'aid', '2026-05-14 14:04:28');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
