-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: fanfics_db
-- ------------------------------------------------------
-- Server version	5.7.20-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `fanfics`
--

DROP TABLE IF EXISTS `fanfics`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fanfics` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fanfic_name` varchar(45) NOT NULL,
  `image` varchar(100) DEFAULT NULL,
  `description` text,
  `genre_id` int(11) DEFAULT NULL,
  `creator_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=63 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fanfics`
--

LOCK TABLES `fanfics` WRITE;
/*!40000 ALTER TABLE `fanfics` DISABLE KEYS */;
INSERT INTO `fanfics` VALUES (48,'Свободу оборотням!','http://res.cloudinary.com/bareysho/image/upload/v1518988557/mini-kak-stat-oborotnem-900x450.jpg','Лаванда не погибла в битве за Хогвартс. Капля везения - и вместо призрачного Кингс-Кросса ей предстоит новая, куда более жестокая война. Есть ли шанс на победу?',4,12),(53,'Ненаступившее завтра','http://res.cloudinary.com/bareysho/image/upload/v1518996585/n4gz4zhnzuttkiokq3pw.jpg','Многие мечтают найти свою любовь.\r\nТакую, что бы в будущем завести семью, что бы было искренне и надолго. \"Раз и навсегда\" - так её любят называть, верно? Но реальность, черт возьми, не так добра, как хотелось бы.',1,17),(54,'Наше счастливое завтра','http://res.cloudinary.com/bareysho/image/upload/v1518998223/lzheu1c9krncsfthidcn.jpg','Твик и Крейг, самая известная пара школы, встречаются уже 7 лет. В их любви никто не сомневаются, все им завидуют, все их обожают. Так можно кратко описать их, ведь сами они не могут с этим справиться. Они утопают в нежных чувствах, но за 7 лет научились не стесняться.\r\nВ их жизни абсолютно нормально долго держаться за руки, давать милые прозвища, ревновать ко всему живому, ходить в кино на последний ряд, мечтательно смотреть друг на друга и ещё, примерно, миллион вещей. Они просто счастливы.',1,12);
/*!40000 ALTER TABLE `fanfics` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-02-20 14:05:55
