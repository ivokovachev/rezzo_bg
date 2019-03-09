-- MySQL dump 10.13  Distrib 8.0.15, for Win64 (x86_64)
--
-- Host: localhost    Database: rezzo_db
-- ------------------------------------------------------
-- Server version	8.0.15

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `address`
--

DROP TABLE IF EXISTS `address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `address` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `street` varchar(45) NOT NULL,
  `country` varchar(45) NOT NULL,
  `city_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_address_cities1_idx` (`city_id`),
  CONSTRAINT `fk_address_cities1` FOREIGN KEY (`city_id`) REFERENCES `cities` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=66 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `address`
--

LOCK TABLES `address` WRITE;
/*!40000 ALTER TABLE `address` DISABLE KEYS */;
INSERT INTO `address` VALUES (1,'бул. Пенчо Славейков 12-14','България',1),(2,'Езеро Ариана, Орлов Мост','България',1),(3,'ул. Искър 1','България',1),(4,'Морска гарa, Port 2','България',2),(5,'бул. Цар Освободител 25','България',2),(6,'бул. Сливница 22','България',2),(7,'ул.\'Княз Александър I-ви\' 38','България',3),(8,'ул. 6-ти Септември','България',3),(9,'ул. Ясна Поляна №1','България',3),(10,'ул. Отец Паисий №26','България',3),(11,'ул. \"Адам Мицкевич\" № 3,','България',4),(12,'ж.к. \"Меден Рудник\" бл.188','България',4),(13,'ул. Цар Симеон I №3','България',4),(14,'пл. Възраждане №1','България',5),(15,'кв. Новия град, ул.\"Найден Геров\"','България',5),(16,'Пирин Голф Ризорт','България',5),(17,'ул. Найден Геров №13','България',5),(18,'Вилни селища \"Ягода и Малина\"','България',6),(19,'хотел Рила, Ниво -1','България',6),(20,'Лифт \"Ястребец\"','България',6),(21,'ул. Неофит Бозвели 13','България',7),(22,'ул. Княжеска №41','България',7),(23,'пл. Хан Кубрат №1','България',7),(24,'пл. Батенберг №1','България',7),(25,'ул. Цар Калоян №50','България',8),(26,'бул. \"Цар Симеон Велики\" №162','България',8),(27,'ул. Цар Калоян №50','България',8),(28,'ул. Димитър Папазов 2','България',9),(29,'Лого','България',9),(30,'Хотел Фламинго Гранд','България',10),(31,'Централна Алея','България',11),(32,'Пристанище Диневи','България',12),(33,'Комплекс Марина Диневи','България',12),(34,'Cacao Beach','България',12),(35,'ул. Крайбрежна №15','Българяи',13),(36,'Пл. \"Черно Море\"','България',13),(37,'ул. Републиканска №16','България',13),(38,'ул. Царевски път №1','България',14),(39,'Комплекс Oasis','България',14),(40,'бул. Джеймс Баучер 100','България',1),(41,'ул. Богатица 36','България',1),(42,'ул. Атанас Манчев № 6','България',1),(43,'ул. Атанас Манчев №1','България',1),(44,'бул. България №1','България',1),(45,'ул. Княз Борис 1 №12','България',1),(46,'ул. Георги Сава Раковски №127','България',1),(47,'ул. Леге № 8','България',1),(48,'ул. Ген. Паренсов №11','България',1),(49,'ул. Отец Паисий №26','България',3),(50,'бул. Дунав №29','България',3),(51,'ул. Генерал Гурко №19','България',3),(52,'ул. Ясна Поляна №1','България',3),(53,'ул. Отец Паисий №27','България',3),(54,'ул. Перущица 8','България',3),(55,'ул. Патриарх Евтимий 13','България',3),(56,'ул. Патриарх Евтимии №5','България',3),(57,'Приморски парк пл.3','България',2),(58,'бул. „Приморски“ №5','България',2),(59,'ул \"Цар Борис III\"','България',2),(60,'ул. Мусала 3','България',2),(61,'ул. Димитър Икономов 36','България',2),(62,'бул. Ян Палах 10','България',2),(64,'','Bulgaria',16),(65,'','Bulgaria',17);
/*!40000 ALTER TABLE `address` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-03-09 12:05:24
