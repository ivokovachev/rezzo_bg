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
-- Table structure for table `events`
--

DROP TABLE IF EXISTS `events`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `events` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `event_url` longtext NOT NULL,
  `event_description` longtext,
  `event_title` longtext NOT NULL,
  `place_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_events_places1_idx` (`place_id`),
  CONSTRAINT `fk_events_places1` FOREIGN KEY (`place_id`) REFERENCES `places` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `events`
--

LOCK TABLES `events` WRITE;
/*!40000 ALTER TABLE `events` DISABLE KEYS */;
INSERT INTO `events` VALUES (1,'2018-03-12','https://rezzo.bg/files/images/11259/fit_431_304.jpg','Присъствие, движение, ритъм, модерен позитивен дух и джаз вдъхновение... Тук всяка вечер е неповторима с многоликите си емоции. Едно спонтанно и незабравимо изживяване, прилична доза висококачествени напитки и завладяващата страст на музика, изпълнена на живо','Елате ни на гости с РИТОН и Венци Мартинов',42),(2,'2019-03-13','https://rezzo.bg/files/images/12120/fit_664_456.png','Всяка Неделя отваряме в 14ч, пускаме стената и проектора, Premier League (или каквото мнозинството реши) , наливаме бирите (които са на неделна промоция) и easy rider , цяла неделя!','Easy Sundaу: Мачове и Бирa',32),(3,'2019-03-19','https://rezzo.bg/files/images/11895/fit_664_456.png','','MC Stojan Live at Megami Club',6),(4,'2019-03-17','https://rezzo.bg/files/images/12020/fit_664_456.png','Заповядайте на 8ми Март в Show Bar \"No limit\", където ще има много забавление, еротични танцьори и най-добрите шоу-програми.','Lady\'s show program',5),(5,'2019-03-15','https://rezzo.bg/files/images/12010/fit_664_456.png','Dim4ou live on stage','Blackroom R\'n\'B & Hip-Hop - Dim4ou',47),(6,'2019-03-20','https://rezzo.bg/files/images/12176/fit_664_456.png','Очакваме ви и тази вечер в Оазисът на Нощна Варна.','Студентско парти',59),(7,'2019-03-21','https://rezzo.bg/files/images/12084/fit_664_456.png','Все пак е празник момичета , а ние го планираме да ви захраним яко с мускулеста плът ;) !','50 Shades Of Grey ',8),(8,'2019-03-23','https://rezzo.bg/files/images/12104/fit_664_456.png','23-ти март във Flash Club с Crazibiza Live! Заповядайте!','Crazibiza Live at Flash Club',15),(9,'2019-03-14','https://rezzo.bg/files/images/11963/fit_664_456.png','GET TO KNOW OUR CODEX','World DJ Day with Timo Maas',2),(10,'2019-03-16','https://rezzo.bg/files/images/11750/fit_664_456.png','Вечерта ще имаме Happy Hour! Поръчваш 1 бургер -> получаваш голяма бира;','Състезание по надяждане с ЛЮТИ Бургери!',19);
/*!40000 ALTER TABLE `events` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-03-09 12:05:25
