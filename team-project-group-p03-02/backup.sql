-- MySQL dump 10.13  Distrib 8.0.39, for Linux (x86_64)
--
-- Host: localhost    Database: vetcare
-- ------------------------------------------------------
-- Server version	8.0.39

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
-- Table structure for table `appointment`
--

DROP TABLE IF EXISTS `appointment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `appointment` (
  `appointment_id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint DEFAULT NULL,
  `pet_id` bigint DEFAULT NULL,
  `clinic_id` bigint DEFAULT NULL,
  `appointment_date` date DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `general_notes` varchar(255) DEFAULT NULL,
  `fees` float DEFAULT NULL,
  `vet_id` bigint DEFAULT NULL,
  `appointment_time` time DEFAULT NULL,
  PRIMARY KEY (`appointment_id`),
  KEY `user_id` (`user_id`),
  KEY `pet_id` (`pet_id`),
  KEY `clinic_id` (`clinic_id`),
  KEY `vet_id` (`vet_id`),
  CONSTRAINT `appointment_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `appointment_ibfk_2` FOREIGN KEY (`pet_id`) REFERENCES `pet` (`pet_id`),
  CONSTRAINT `appointment_ibfk_3` FOREIGN KEY (`clinic_id`) REFERENCES `clinic` (`clinic_id`),
  CONSTRAINT `appointment_ibfk_4` FOREIGN KEY (`vet_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `appointment`
--

LOCK TABLES `appointment` WRITE;
/*!40000 ALTER TABLE `appointment` DISABLE KEYS */;
INSERT INTO `appointment` VALUES (1,1,1,1,'2024-09-10','Upcoming','Routine check-up',50,2,'10:00:00'),(2,1,2,1,'2024-09-12','Upcoming','Annual vaccination',30,2,'11:00:00'),(3,2,3,2,'2024-09-15','Completed','Teeth cleaning',100,2,'09:30:00'),(4,3,4,3,'2024-09-20','Cancelled','Behavioral consultation',75,2,'14:00:00'),(5,4,5,1,'2024-09-25','Upcoming','Health check',60,2,'16:00:00'),(6,1,2,1,'2024-09-30','Upcoming','Follow-up visit',25,2,'10:30:00'),(7,2,1,2,'2024-10-01','Upcoming','Ear infection treatment',45,2,'13:00:00'),(8,5,6,1,'2024-12-01','Cancelled','testGeneralNotes',45,1,'12:00:00'),(9,5,6,NULL,'2024-10-12','Scheduled','Follow-up visit',NULL,NULL,'14:26:00');
/*!40000 ALTER TABLE `appointment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `clinic`
--

DROP TABLE IF EXISTS `clinic`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clinic` (
  `clinic_id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`clinic_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clinic`
--

LOCK TABLES `clinic` WRITE;
/*!40000 ALTER TABLE `clinic` DISABLE KEYS */;
INSERT INTO `clinic` VALUES (1,'Healthy Pets Clinic','123 Main Street Springfield','555-1111','info@healthypets.com'),(2,'City Vet Clinic','456 Elm Street Springfield','555-2222','contact@cityvet.com'),(3,'Greenwood Animal Hospital','789 Oak Avenue Greenwood','555-3333','info@greenwoodanimal.com');
/*!40000 ALTER TABLE `clinic` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dosage`
--

DROP TABLE IF EXISTS `dosage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dosage` (
  `dosage_id` bigint NOT NULL AUTO_INCREMENT,
  `date_administered` datetime(6) DEFAULT NULL,
  `dosage_quantity` varchar(255) DEFAULT NULL,
  `instructions` varchar(255) DEFAULT NULL,
  `next_dosage_date` datetime(6) DEFAULT NULL,
  `medicine_id` bigint DEFAULT NULL,
  `pet_id` bigint DEFAULT NULL,
  `notes` text,
  PRIMARY KEY (`dosage_id`),
  KEY `medicine_id` (`medicine_id`),
  KEY `pet_id` (`pet_id`),
  CONSTRAINT `dosage_ibfk_1` FOREIGN KEY (`medicine_id`) REFERENCES `medicine` (`medicine_id`),
  CONSTRAINT `dosage_ibfk_2` FOREIGN KEY (`pet_id`) REFERENCES `pet` (`pet_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dosage`
--

LOCK TABLES `dosage` WRITE;
/*!40000 ALTER TABLE `dosage` DISABLE KEYS */;
INSERT INTO `dosage` VALUES (1,'2024-10-02 00:00:00.000000','testdosage','testInstuctions','2025-02-06 00:00:00.000000',1,6,'none'),(2,'2024-10-02 00:00:00.000000','testdosage','testInstuctionsExpired','2025-03-07 00:00:00.000000',2,6,'none');
/*!40000 ALTER TABLE `dosage` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `educational_resource`
--

DROP TABLE IF EXISTS `educational_resource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `educational_resource` (
  `resource_id` bigint NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  `resource_type` varchar(255) DEFAULT NULL,
  `author` varchar(255) DEFAULT NULL,
  `publish_date` date DEFAULT NULL,
  `category` varchar(255) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`resource_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `educational_resource`
--

LOCK TABLES `educational_resource` WRITE;
/*!40000 ALTER TABLE `educational_resource` DISABLE KEYS */;
INSERT INTO `educational_resource` VALUES (1,'7 Tips To Care For Your Dog\'s Health','Video','AnimalWised','2021-09-06','Dogs','https://www.youtube.com/embed/JhKfEOLmDAU','Tips to care for your dog\'s health'),(2,'How To Care For A Kitten','Video','AnimalWised','2019-05-30','Dogs','https://www.youtube.com/embed/uV1RMT_ld3k','How to care for a kitten'),(3,'How To Take Care Of A Rabbit','Video','AnimalWised','2019-10-31','Rabbits','https://www.youtube.com/embed/1s-ydKa7nPA','How to take care of a rabbit'),(4,'Grooming Tips For Dogs','Article','Kanawal','2019-08-20','Dogs','http://www.kanwalvet.com.au/article/grooming-tips-for-dogs/','Grooming sessions can become a special bonding time between you and your dog and are also a good opportunity to check on your dog’s health.'),(5,'How to Keep Your Dogs Joints Healthy','Article','Purina','2024-05-21','Dogs','https://www.purina.com/articles/dog/health/symptoms/joint-health-for-dogs','As your canine companion ages, they can put a lot of wear and tear on their joints. And if joint problems occur, they can be painful, reduce mobility, and affect their quality of life. That’s why joint health for dogs is so important. '),(6,'How to care for older cats ','Guide','RSPCA','2021-11-26','Cats','https://www.rspcapetinsurance.org.au/pet-care/guides/caring-for-older-cats','A guide on caring for an older cat'),(7,'General Dog Care','Guide','ASPCA','2023-10-10','Dogs','https://www.aspca.org/pet-care/dog-care/general-dog-care','A dog can be a wonderful addition to any home, but whether you\'re an experienced pet parent or a first-time adopter, it\'s important to keep your canine companion\'s health and happiness a top priority. Below are some useful tips for all dog parents.');
/*!40000 ALTER TABLE `educational_resource` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `flyway_schema_history`
--

DROP TABLE IF EXISTS `flyway_schema_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `flyway_schema_history` (
  `installed_rank` int NOT NULL,
  `version` varchar(50) DEFAULT NULL,
  `description` varchar(200) NOT NULL,
  `type` varchar(20) NOT NULL,
  `script` varchar(1000) NOT NULL,
  `checksum` int DEFAULT NULL,
  `installed_by` varchar(100) NOT NULL,
  `installed_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `execution_time` int NOT NULL,
  `success` tinyint(1) NOT NULL,
  PRIMARY KEY (`installed_rank`),
  KEY `flyway_schema_history_s_idx` (`success`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `flyway_schema_history`
--

LOCK TABLES `flyway_schema_history` WRITE;
/*!40000 ALTER TABLE `flyway_schema_history` DISABLE KEYS */;
INSERT INTO `flyway_schema_history` VALUES (1,'1','drop tables','SQL','V1__drop_tables.sql',-881499042,'root','2024-10-12 03:25:39',42,1),(2,'2','create tables','SQL','V2__create_tables.sql',1977268771,'root','2024-10-12 03:25:39',112,1),(3,'3','insert into tables','SQL','V3__insert_into_tables.sql',1108562811,'root','2024-10-12 03:25:39',10,1),(4,'4','update appointment status','SQL','V4__update_appointment_status.sql',-1325901221,'root','2024-10-12 03:25:40',65,1),(5,'5','insert into table eduresources','SQL','V5__insert_into_table_eduresources.sql',-865783152,'root','2024-10-12 03:25:40',6,1),(6,'6','create tables','SQL','V6__create_tables.sql',399819900,'root','2024-10-12 03:25:40',13,1),(7,'7','insert into tables','SQL','V7__insert_into_tables.sql',-2035743147,'root','2024-10-12 03:25:40',3,1),(8,'8','insert into tables','SQL','V8__insert_into_tables.sql',-801078561,'root','2024-10-13 00:59:24',7,1),(9,'9','insert into tables','SQL','V9__insert_into_tables.sql',-801078561,'root','2024-10-13 01:00:18',9,1),(10,'10','insert into tables','SQL','V10__insert_into_tables.sql',0,'root','2024-10-13 01:00:18',2,1),(11,'11','insert into tables','SQL','V11__insert_into_tables.sql',-801078561,'root','2024-10-13 01:01:26',8,1),(12,'12','insert into tables','SQL','V12__insert_into_tables.sql',1490784439,'root','2024-10-13 01:01:48',4,1),(13,'13','insert into tables','SQL','V13__insert_into_tables.sql',1490784439,'root','2024-10-13 01:02:07',4,1),(14,'14','insert into tables','SQL','V14__insert_into_tables.sql',-1361243413,'root','2024-10-13 01:02:45',4,1),(15,'15','insert into tables','SQL','V15__insert_into_tables.sql',-80455087,'root','2024-10-13 01:26:02',24,1);
/*!40000 ALTER TABLE `flyway_schema_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `latest_trends`
--

DROP TABLE IF EXISTS `latest_trends`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `latest_trends` (
  `trend_id` bigint NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  `description` text,
  `author` varchar(255) DEFAULT NULL,
  `publish_date` date DEFAULT NULL,
  `trend_category` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`trend_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `latest_trends`
--

LOCK TABLES `latest_trends` WRITE;
/*!40000 ALTER TABLE `latest_trends` DISABLE KEYS */;
/*!40000 ALTER TABLE `latest_trends` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `medical_history`
--

DROP TABLE IF EXISTS `medical_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `medical_history` (
  `medical_history_id` bigint NOT NULL AUTO_INCREMENT,
  `pet_id` bigint DEFAULT NULL,
  `chronic_conditions` varchar(255) DEFAULT NULL,
  `allergies` varchar(255) DEFAULT NULL,
  `notes` varchar(255) DEFAULT NULL,
  `last_vaccination_date` date DEFAULT NULL,
  `last_treatment_date` date DEFAULT NULL,
  `last_prescription_date` date DEFAULT NULL,
  PRIMARY KEY (`medical_history_id`),
  KEY `pet_id` (`pet_id`),
  CONSTRAINT `medical_history_ibfk_1` FOREIGN KEY (`pet_id`) REFERENCES `pet` (`pet_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `medical_history`
--

LOCK TABLES `medical_history` WRITE;
/*!40000 ALTER TABLE `medical_history` DISABLE KEYS */;
/*!40000 ALTER TABLE `medical_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `medicine`
--

DROP TABLE IF EXISTS `medicine`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `medicine` (
  `medicine_id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `strength` varchar(255) DEFAULT NULL,
  `side_effects` varchar(255) DEFAULT NULL,
  `cost` double DEFAULT NULL,
  PRIMARY KEY (`medicine_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `medicine`
--

LOCK TABLES `medicine` WRITE;
/*!40000 ALTER TABLE `medicine` DISABLE KEYS */;
INSERT INTO `medicine` VALUES (1,'testMedicine','testDescription','teststrength','testSideEffects',1),(2,'testMedicine2','testDescription2','teststrength2','testSideEffects2',1),(5,'Carprofen','Non-steroidal anti-inflammatory drug (NSAID) used for arthritis.','50mg','May cause stomach upset.',25),(6,'Carprofen','Non-steroidal anti-inflammatory drug (NSAID) used for arthritis.','50mg','May cause stomach upset.',25),(8,'Carprofen','Non-steroidal anti-inflammatory drug (NSAID) used for arthritis.','50mg','May cause stomach upset.',25);
/*!40000 ALTER TABLE `medicine` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pet`
--

DROP TABLE IF EXISTS `pet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pet` (
  `pet_id` bigint NOT NULL AUTO_INCREMENT,
  `owner_id` bigint DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `species` varchar(255) DEFAULT NULL,
  `breed` varchar(255) DEFAULT NULL,
  `age` int DEFAULT NULL,
  PRIMARY KEY (`pet_id`),
  KEY `owner_id` (`owner_id`),
  CONSTRAINT `pet_ibfk_1` FOREIGN KEY (`owner_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pet`
--

LOCK TABLES `pet` WRITE;
/*!40000 ALTER TABLE `pet` DISABLE KEYS */;
INSERT INTO `pet` VALUES (1,1,'Rex','Dog','Labrador',5),(2,1,'Whiskers','Cat','Siamese',3),(3,2,'Bella','Dog','Poodle',2),(4,3,'Charlie','Cat','Maine Coon',4),(5,4,'Max','Dog','Beagle',6),(6,5,'testPet','testSpecies','testBreed',1);
/*!40000 ALTER TABLE `pet` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prescription`
--

DROP TABLE IF EXISTS `prescription`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `prescription` (
  `prescription_id` bigint NOT NULL AUTO_INCREMENT,
  `pet_id` bigint DEFAULT NULL,
  `vet_id` bigint DEFAULT NULL,
  `medicine_id` bigint DEFAULT NULL,
  `instructions` varchar(255) DEFAULT NULL,
  `dosage_quantity` varchar(255) DEFAULT NULL,
  `date_administered` date DEFAULT NULL,
  `expiry_date` date DEFAULT NULL,
  `repeats_left` int DEFAULT NULL,
  `renewal_date` date DEFAULT NULL,
  PRIMARY KEY (`prescription_id`),
  KEY `pet_id` (`pet_id`),
  KEY `vet_id` (`vet_id`),
  KEY `medicine_id` (`medicine_id`),
  CONSTRAINT `prescription_ibfk_1` FOREIGN KEY (`pet_id`) REFERENCES `pet` (`pet_id`),
  CONSTRAINT `prescription_ibfk_2` FOREIGN KEY (`vet_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `prescription_ibfk_3` FOREIGN KEY (`medicine_id`) REFERENCES `medicine` (`medicine_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prescription`
--

LOCK TABLES `prescription` WRITE;
/*!40000 ALTER TABLE `prescription` DISABLE KEYS */;
INSERT INTO `prescription` VALUES (1,6,1,1,'testInstuctions','testdosage','2024-10-02','2024-12-08',5,'2024-10-10'),(2,6,1,2,'testInstuctionsExpired','testdosage','2024-10-02','2024-01-01',5,'2024-10-10');
/*!40000 ALTER TABLE `prescription` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `saved_resources`
--

DROP TABLE IF EXISTS `saved_resources`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `saved_resources` (
  `user_id` bigint NOT NULL,
  `resource_id` bigint NOT NULL,
  `saved_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`,`resource_id`),
  KEY `resource_id` (`resource_id`),
  CONSTRAINT `saved_resources_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `saved_resources_ibfk_2` FOREIGN KEY (`resource_id`) REFERENCES `educational_resource` (`resource_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `saved_resources`
--

LOCK TABLES `saved_resources` WRITE;
/*!40000 ALTER TABLE `saved_resources` DISABLE KEYS */;
/*!40000 ALTER TABLE `saved_resources` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `treatment_plan`
--

DROP TABLE IF EXISTS `treatment_plan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `treatment_plan` (
  `treatment_plan_id` bigint NOT NULL AUTO_INCREMENT,
  `pet_id` bigint DEFAULT NULL,
  `diagnosis` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `date_administered` date DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `notes` varchar(255) DEFAULT NULL,
  `vet_id` bigint DEFAULT NULL,
  PRIMARY KEY (`treatment_plan_id`),
  KEY `pet_id` (`pet_id`),
  KEY `vet_id` (`vet_id`),
  CONSTRAINT `treatment_plan_ibfk_1` FOREIGN KEY (`pet_id`) REFERENCES `pet` (`pet_id`),
  CONSTRAINT `treatment_plan_ibfk_2` FOREIGN KEY (`vet_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `treatment_plan`
--

LOCK TABLES `treatment_plan` WRITE;
/*!40000 ALTER TABLE `treatment_plan` DISABLE KEYS */;
INSERT INTO `treatment_plan` VALUES (7,6,'Arthritis','Chronic arthritis, requires regular monitoring and anti-inflammatory medication.','2024-10-01','2025-01-01','Follow-up in 3 months.',1);
/*!40000 ALTER TABLE `treatment_plan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `user_id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `clinic_id` bigint DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `user_type` enum('PetOwner','Vet','Admin') DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  KEY `clinic_id` (`clinic_id`),
  CONSTRAINT `user_ibfk_1` FOREIGN KEY (`clinic_id`) REFERENCES `clinic` (`clinic_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'John Doe','$2a$10$2iY5tmkdvi2OzSIGfDalH.v7b4Iq3EfwXbrDmNepC9a78jt.Bz5zu','john.doe@example.com',1,'555-1111','Apt 1 Main Street Springfield','PetOwner'),(2,'Jane Smith','$2a$10$2iY5tmkdvi2OzSIGfDalH.v7b4Iq3EfwXbrDmNepC9a78jt.Bz5zu','jane.smith@example.com',2,'555-2222','Apt 2 Elm Street Springfield','Vet'),(3,'Robert Johnson','$2a$10$2iY5tmkdvi2OzSIGfDalH.v7b4Iq3EfwXbrDmNepC9a78jt.Bz5zu','robert.johnson@example.com',3,'555-3333','Apt 3 Oak Avenue Greenwood','Admin'),(4,'Emily Davis','$2a$10$2iY5tmkdvi2OzSIGfDalH.v7b4Iq3EfwXbrDmNepC9a78jt.Bz5zu','emily.davis@example.com',1,'555-4444','Apt 4 123 Main Street Springfield','PetOwner'),(5,'testUser','$2a$10$lb8PN70R8kagmOhkvpBfhuUzwdp9gPrcuieCnZD4FcY0Tl3nLnFG6','test@test',1,'000-000','testAddress','PetOwner');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vaccination_record`
--

DROP TABLE IF EXISTS `vaccination_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vaccination_record` (
  `vaccination_id` bigint NOT NULL AUTO_INCREMENT,
  `pet_id` bigint DEFAULT NULL,
  `vaccine_name` varchar(255) DEFAULT NULL,
  `date_administered` date DEFAULT NULL,
  `next_due_date` date DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `notes` varchar(255) DEFAULT NULL,
  `vet_id` bigint DEFAULT NULL,
  `cost` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`vaccination_id`),
  KEY `pet_id` (`pet_id`),
  KEY `vet_id` (`vet_id`),
  CONSTRAINT `vaccination_record_ibfk_1` FOREIGN KEY (`pet_id`) REFERENCES `pet` (`pet_id`),
  CONSTRAINT `vaccination_record_ibfk_2` FOREIGN KEY (`vet_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vaccination_record`
--

LOCK TABLES `vaccination_record` WRITE;
/*!40000 ALTER TABLE `vaccination_record` DISABLE KEYS */;
INSERT INTO `vaccination_record` VALUES (2,6,'Rabies','2024-09-01','2025-09-01','Completed','Next vaccination in 12 months.',1,45.00);
/*!40000 ALTER TABLE `vaccination_record` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-10-13  1:42:28
