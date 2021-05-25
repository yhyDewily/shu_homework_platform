-- MySQL dump 10.13  Distrib 8.0.23, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: ems
-- ------------------------------------------------------
-- Server version	8.0.23

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comment` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `parentId` bigint DEFAULT '0',
  `questionId` bigint NOT NULL,
  `commentator` varchar(45) NOT NULL,
  `content` text NOT NULL,
  `time` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='论坛评论';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
INSERT INTO `comment` VALUES (1,0,1,'李林','一点也不难','2021-05-20'),(2,0,1,'张涛','就这？','2021-05-21'),(5,0,1,'刘谕鸿','asfasdfasfsd','2021-05-22'),(6,0,1,'刘谕鸿','楼上的什么意思','2021-05-22'),(7,0,1,'刘谕鸿','asfasfsdaf','2021-05-23'),(8,0,2,'刘谕鸿','asdfasdfasfsdf','2021-05-23'),(9,0,2,'曹旻','asdfasdfasf','2021-05-24'),(10,0,2,'曹旻','asfasfasfsdafasf','2021-05-24'),(11,0,2,'曹旻','asfasfasfsdafasf','2021-05-24');
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `course`
--

DROP TABLE IF EXISTS `course`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `course` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `courseId` varchar(45) NOT NULL,
  `courseName` text NOT NULL,
  `major` varchar(45) NOT NULL,
  `t_id` bigint NOT NULL,
  `stopTime` date NOT NULL,
  `intro` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `course`
--

LOCK TABLES `course` WRITE;
/*!40000 ALTER TABLE `course` DISABLE KEYS */;
INSERT INTO `course` VALUES (1,'08305008','数据结构','计算机工程与科学学院',3,'2021-06-30','数据结构是计算机存储、组织数据的方式。数据结构是指相互之间存在一种或多种特定关系的数据元素的集合。通常情况下，精心选择的数据结构可以带来更高的运行或者存储效率。数据结构往往同高效的检索算法和索引技术有关。'),(2,'08305011','计算机组成原理','计算机工程与科学学院',3,'2021-06-30',NULL),(3,'08305072','离散数学','计算机工程与科学学院',5,'2021-06-30',NULL),(4,'08305138','编译原理','计算机工程与科学学院',8,'2021-06-30',NULL),(5,'0P000005','概率论','理学院',6,'2021-11-30',NULL),(6,'3ZS08105','嵌入式系统开发技术','计算机工程与科学学院',5,'2021-06-30',NULL),(7,'3ZS08101','程序设计','计算机工程与科学学院',5,'2021-05-30',NULL),(9,'02976899','高等数学','理学院',3,'2020-12-30','高等数学5666666');
/*!40000 ALTER TABLE `course` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `coursegrade`
--

DROP TABLE IF EXISTS `coursegrade`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `coursegrade` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `courseId` varchar(45) NOT NULL,
  `studentId` varchar(45) NOT NULL,
  `usual` tinyint DEFAULT '0',
  `exam` tinyint DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `coursegrade`
--

LOCK TABLES `coursegrade` WRITE;
/*!40000 ALTER TABLE `coursegrade` DISABLE KEYS */;
INSERT INTO `coursegrade` VALUES (1,'08305008','1',80,85),(4,'08305011','1',86,75),(5,'08305008','2',64,75),(6,'08305008','10',0,0),(7,'08305008','12',0,0);
/*!40000 ALTER TABLE `coursegrade` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `courseratio`
--

DROP TABLE IF EXISTS `courseratio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `courseratio` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `courseId` varchar(45) NOT NULL,
  `usualRatio` tinyint DEFAULT '50',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `courseratio`
--

LOCK TABLES `courseratio` WRITE;
/*!40000 ALTER TABLE `courseratio` DISABLE KEYS */;
INSERT INTO `courseratio` VALUES (1,'08305008',40),(2,'08305011',50),(7,'08305072',30),(8,'08305138',40),(9,'0P000005',50),(10,'3ZS081005',30);
/*!40000 ALTER TABLE `courseratio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `file_table`
--

DROP TABLE IF EXISTS `file_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `file_table` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `file_name` text NOT NULL,
  `file_path` text NOT NULL,
  `courseId` varchar(45) NOT NULL,
  `is_homework` tinyint NOT NULL,
  `is_resource` tinyint NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `file_table`
--

LOCK TABLES `file_table` WRITE;
/*!40000 ALTER TABLE `file_table` DISABLE KEYS */;
/*!40000 ALTER TABLE `file_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `homework`
--

DROP TABLE IF EXISTS `homework`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `homework` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `fileName` text NOT NULL,
  `studentId` varchar(45) NOT NULL,
  `teacherId` varchar(45) NOT NULL,
  `time` date NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `homework`
--

LOCK TABLES `homework` WRITE;
/*!40000 ALTER TABLE `homework` DISABLE KEYS */;
INSERT INTO `homework` VALUES (2,'cmd.txt','1016886553@qq.com','123456@qq.com','2021-05-26');
/*!40000 ALTER TABLE `homework` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `material`
--

DROP TABLE IF EXISTS `material`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `material` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `fileName` text NOT NULL,
  `uploader` varchar(100) NOT NULL,
  `time` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `material`
--

LOCK TABLES `material` WRITE;
/*!40000 ALTER TABLE `material` DISABLE KEYS */;
INSERT INTO `material` VALUES (2,'微信图片_20210427093134.gif','123456@qq.com','2021-05-25'),(3,'debug.txt','123456@qq.com','2021-05-25'),(4,'cmd.txt','123456@qq.com','2021-05-25');
/*!40000 ALTER TABLE `material` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `question`
--

DROP TABLE IF EXISTS `question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `question` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `title` varchar(100) NOT NULL,
  `content` text NOT NULL,
  `creator` varchar(45) NOT NULL,
  `commentCount` int DEFAULT '0',
  `time` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `question`
--

LOCK TABLES `question` WRITE;
/*!40000 ALTER TABLE `question` DISABLE KEYS */;
INSERT INTO `question` VALUES (1,'这个问题该怎么解决','怎么会这么难','刘谕鸿',5,'2021-05-21'),(2,'软件项目管理问题','软件项目管理问题','刘谕鸿',4,'2021-05-22'),(3,'66666666666666666','软件项目管理问题','刘谕鸿',0,'2021-05-22');
/*!40000 ALTER TABLE `question` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `scourse`
--

DROP TABLE IF EXISTS `scourse`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `scourse` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `courseId` varchar(45) NOT NULL,
  `studentId` bigint NOT NULL,
  `status` char(3) NOT NULL DEFAULT 'no',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `scourse`
--

LOCK TABLES `scourse` WRITE;
/*!40000 ALTER TABLE `scourse` DISABLE KEYS */;
INSERT INTO `scourse` VALUES (1,'08305008',1,'no'),(2,'08305008',2,'no'),(3,'08305011',1,'no'),(4,'08305072',1,'no'),(6,'08305138',1,'no'),(10,'08305008',10,'no'),(11,'08305072',2,'no'),(12,'08305072',9,'no'),(13,'08305008',12,'no'),(14,'08305011',12,'no');
/*!40000 ALTER TABLE `scourse` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `spring_session`
--

DROP TABLE IF EXISTS `spring_session`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `spring_session` (
  `PRIMARY_ID` char(36) NOT NULL,
  `SESSION_ID` char(36) NOT NULL,
  `CREATION_TIME` bigint NOT NULL,
  `LAST_ACCESS_TIME` bigint NOT NULL,
  `MAX_INACTIVE_INTERVAL` int NOT NULL,
  `EXPIRY_TIME` bigint NOT NULL,
  `PRINCIPAL_NAME` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`PRIMARY_ID`),
  UNIQUE KEY `SPRING_SESSION_IX1` (`SESSION_ID`),
  KEY `SPRING_SESSION_IX2` (`EXPIRY_TIME`),
  KEY `SPRING_SESSION_IX3` (`PRINCIPAL_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `spring_session`
--

LOCK TABLES `spring_session` WRITE;
/*!40000 ALTER TABLE `spring_session` DISABLE KEYS */;
INSERT INTO `spring_session` VALUES ('ca6f29f9-607a-427f-a73f-e1ee60b256b6','28091fa4-f378-4ada-a19f-5c0b86038cff',1621962553286,1621962678905,1800,1621964478905,NULL),('f44710a7-7ccd-465b-9995-a869ee5de7d6','a988c766-858e-4df4-b645-040abe13ab95',1621962553286,1621962553287,1800,1621964353287,NULL);
/*!40000 ALTER TABLE `spring_session` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `spring_session_attributes`
--

DROP TABLE IF EXISTS `spring_session_attributes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `spring_session_attributes` (
  `SESSION_PRIMARY_ID` char(36) NOT NULL,
  `ATTRIBUTE_NAME` varchar(200) NOT NULL,
  `ATTRIBUTE_BYTES` blob NOT NULL,
  PRIMARY KEY (`SESSION_PRIMARY_ID`,`ATTRIBUTE_NAME`),
  CONSTRAINT `SPRING_SESSION_ATTRIBUTES_FK` FOREIGN KEY (`SESSION_PRIMARY_ID`) REFERENCES `spring_session` (`PRIMARY_ID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `spring_session_attributes`
--

LOCK TABLES `spring_session_attributes` WRITE;
/*!40000 ALTER TABLE `spring_session_attributes` DISABLE KEYS */;
/*!40000 ALTER TABLE `spring_session_attributes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student_group`
--

DROP TABLE IF EXISTS `student_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `student_group` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `size` int NOT NULL,
  `name` varchar(45) NOT NULL,
  `captain` varchar(45) NOT NULL,
  `member` text NOT NULL,
  `courseId` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student_group`
--

LOCK TABLES `student_group` WRITE;
/*!40000 ALTER TABLE `student_group` DISABLE KEYS */;
INSERT INTO `student_group` VALUES (1,3,'第1组','刘谕鸿','刘谕鸿;李林;崔毅','08305008'),(2,1,'第1组','刘谕鸿','刘谕鸿','08305011'),(5,2,'第1组','张涛','张涛;李林','08305072'),(8,1,'第2组','刘谕鸿','刘谕鸿','08305072'),(10,1,'第2组','王迎港','王迎港','08305008');
/*!40000 ALTER TABLE `student_group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(60) NOT NULL,
  `mail` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `role` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'刘谕鸿','1016886553@qq.com','F1759176858D0D46D7B6C3276FE828D6',0),(2,'李林','1111@qq.com','E10ADC3949BA59ABBE56E057F20F883E',0),(3,'曹旻','123456@qq.com','E10ADC3949BA59ABBE56E057F20F883E',1),(4,'钟保琪','456789@qq.com','E80B5017098950FC58AAD83C8C14978E',0),(5,'陈圣波','123abc@qq.com','E80B5017098950FC58AAD83C8C14978E',1),(6,'雷州','345abc@qq.com','E10ADC3949BA59ABBE56E057F20F883E',1),(7,'dewily','yhy16121755@gmail.com','7066FE51342F77B4C37F96E74C6D22BC',2),(8,'骆祥峰','luo123@shu.edu.cn','E80B5017098950FC58AAD83C8C14978E',1),(9,'张涛','zhang@shu.edu.cn','E10ADC3949BA59ABBE56E057F20F883E',0),(10,'崔毅','cuiyi@qq.com','E10ADC3949BA59ABBE56E057F20F883E',0),(12,'王迎港','1231413@qq.com','E10ADC3949BA59ABBE56E057F20F883E',0);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-05-26  1:16:36
