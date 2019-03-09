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
-- Table structure for table `offers`
--

DROP TABLE IF EXISTS `offers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `offers` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `offer_description` longtext,
  `offer_title` longtext NOT NULL,
  `offer_url` longtext NOT NULL,
  `place_id` int(11) NOT NULL,
  `offer_price` int(11) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_offers_places1_idx` (`place_id`),
  CONSTRAINT `fk_offers_places1` FOREIGN KEY (`place_id`) REFERENCES `places` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `offers`
--

LOCK TABLES `offers` WRITE;
/*!40000 ALTER TABLE `offers` DISABLE KEYS */;
INSERT INTO `offers` VALUES (1,'Аспержи | Поширано Пъдпъдъче Яйце | Карамелизирани Орехи, Пушена пъстърва | Ряпа Дайкон | Целина | Маракуя | Гел от Грейпфрут, Патешко Бутче Конфи | Печено Червено Цвекло | Грейви от Греяно Вино | Бадеми | Гел от Цвекло | Грилован Праз, Сахер | Портокалово Сорбе','Theme Night: Women\'s Day','https://rezzo.bg/files/images/12150/fit_664_456.jpg',11,42),(2,'1. Домати с моцарела и песто-350гр. ','8-ми март с  BG WINE','https://rezzo.bg/files/images/12219/fit_664_456.jpg',58,39),(3,'Салата ‚Цезар\', Салата ‚колсо‘, Тортила чипс с 3 вида дип, Картофи Уеджийс, Пържени картофи, Хамбургер, Чийзбургер, Тортила със зеленчуци','Вечер на „вредната храна“ с неограничена консумация на блок маса','https://rezzo.bg/files/images/12139/fit_664_456.jpg',37,26),(4,'Цезар с пушена сьомга, Патешко магре с трюфел, Малиново тирамису, Аператив-50мл. /марков алкохол по избор/, Безалкохолна напитка-1бр.','Live party with Fency','https://rezzo.bg/files/images/12005/fit_664_456.jpg',24,68),(5,'В Неделя, закриваме седмицата с любмите хитове от миналотo.','50% На Алкохола','https://rezzo.bg/files/images/11875/fit_664_456.jpg',13,75),(6,'В рамките на учаситето си в международната инициатива Вкусът на Франция #GOODFRANCE #GOUTDEFRANCE Лили Фам ви кани на една незабравима вечер посветена на френската кухня и френската култура на забавления.','French Cabaret - dinner and show','https://rezzo.bg/files/images/12237/fit_664_456.jpg',26,65),(7,'Крем супа с трюфелово какао, Пълнени пилешки бутчета със зеленчукова нуга и сос от трюфел, кус-кус от карфиол и трюфел, Аджестив от пъпешов ликьор, Кокосово суфле с трюфел','Вечер на трюфела в Ресторант 59','https://rezzo.bg/files/images/11561/fit_664_456.jpg',14,40),(8,'Уикендът е любимото ни време от седмицата. Късното ставане и бавните удоволствия толкова му отиват. За да го направим още по-лежерен и приятен за вас, от тази неделя сливаме закуската и обяда в едно разточително кулинарно изживяване – Брънчът на Петрус. Очаква ви много богат и много труден избор от закуски, основни ястия и салати, с авторския почерк на шеф Янков.','Неделен брънч в Петрус','https://rezzo.bg/files/images/10854/fit_664_456.jpg',26,28),(9,'За всички любители на месото - Лили Фам ви кани на специално кулинарно събитие, на което ще предложим голямо разнообразие от меса, приготвени на барбекю шишчета, котлети, филенца и ребърца, кюфтенца и кърначета, предложени в микс грил.','Lili Pham loves meat - All you can eat offer','https://rezzo.bg/files/images/12234/fit_664_456.jpg',40,40),(10,'Минерална вода - 350мл. Пъстра салата с авокадо и козе сирене - 350гр. Бутилка чилийско вино \'\' Гато Негро\'\' - 187мл. Спаначена палачинка - 200гр. Салами сет - 100гр. Печен чийзкейк с роте грютце - 150гр. Прясно изпечена питка чабата - 1бр. Свинско бон филе ','Празничен куверт в Pastel Grill & House','https://rezzo.bg/files/images/12065/fit_664_456.jpg',49,35);
/*!40000 ALTER TABLE `offers` ENABLE KEYS */;
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
