-- MySQL dump 10.13  Distrib 8.0.21, for Linux (x86_64)
--
-- Host: localhost    Database: backend
-- ------------------------------------------------------
-- Server version	8.0.21-0ubuntu0.20.04.4

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
-- Table structure for table `food`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE IF NOT EXISTS `food` (
    `id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `location_id` bigint NOT NULL,
    `applicant` varchar(255) DEFAULT NULL,
    `facility_type` int DEFAULT 0 COMMENT '0:None;1:Push Cart;2:Truck',
    `cnn` bigint DEFAULT 0,
    `location_description` varchar(255)  NULL,
    `address` varchar(255) NOT NULL,
    `block` varchar(16)  NULL,
    `lot` varchar(16)  NULL,
    `permit` varchar(32) NOT NULL,
    `status` int DEFAULT 0 COMMENT '0:REQUESTED;1:APPROVED;2:EXPIRED;3:SUSPEND;4:ISSUED',
    `food_items` varchar(10240)  NULL,
    `latitude` double NOT NULL DEFAULT 0,
    `longitude` double NOT NULL DEFAULT 0,
    `day_hours` varchar(255)  NULL,
    `approved` timestamp NULL DEFAULT NULL,
    `received` timestamp NULL DEFAULT NULL,
    `expiration` timestamp NULL DEFAULT NULL,
    `prior_permit` int DEFAULT 0,
    `fire_prevention_districts` int NULL DEFAULT NULL,
    `police_districts` int NULL DEFAULT NULL,
    `supervisor_districts` int NULL DEFAULT NULL,
    `zip_codes` int NULL DEFAULT NULL,
    UNIQUE KEY `location_id` (`location_id`) USING BTREE,
    KEY `cnn` (`cnn`) USING BTREE,
    KEY `permit` (`permit`) USING BTREE

) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `day_hours` (
    `id` bigint AUTO_INCREMENT PRIMARY KEY,
    `location_id` bigint NOT NULL,
    `day` int NOT NULL DEFAULT 1,
    `start_hour` int NOT NULL DEFAULT 0,
    `end_hour` int NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

create index dayHours_location_id_day_index
    on day_hours (location_id, day);

/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

