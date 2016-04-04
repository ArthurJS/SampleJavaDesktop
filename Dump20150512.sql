-- MySQL dump 10.13  Distrib 5.6.17, for Win32 (x86)
--
-- Host: 127.0.0.1    Database: sokoban
-- ------------------------------------------------------
-- Server version	5.6.23-log

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
-- Table structure for table `kist`
--

DROP TABLE IF EXISTS `kist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `kist` (
  `Veld_XCoord` int(11) NOT NULL,
  `Veld_YCoord` int(11) NOT NULL,
  `Veld_spelbord_spel_spelnaam` varchar(30) NOT NULL,
  `Veld_spelbord_spel_volgordeID` int(11) NOT NULL,
  KEY `FK` (`Veld_XCoord`,`Veld_YCoord`,`Veld_spelbord_spel_spelnaam`,`Veld_spelbord_spel_volgordeID`),
  CONSTRAINT `fk_Kist_Veld1` FOREIGN KEY (`Veld_XCoord`, `Veld_YCoord`, `Veld_spelbord_spel_spelnaam`, `Veld_spelbord_spel_volgordeID`) REFERENCES `veld` (`XCoord`, `YCoord`, `Spelbord_spel_Spelnaam`, `Spelbord_VolgordeID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kist`
--

LOCK TABLES `kist` WRITE;
/*!40000 ALTER TABLE `kist` DISABLE KEYS */;
INSERT INTO `kist` VALUES (1,3,'Makoto',3),(1,4,'Sung Yong',2),(1,7,'Makoto',1),(2,2,'Kohaku',1),(2,2,'Soraka',2),(2,4,'Soraka',2),(2,5,'Sung Yong',2),(2,6,'Makoto',3),(2,6,'Sung Yong',2),(3,2,'Sung Yong',2),(3,3,'Makoto',1),(3,3,'Solaris',2),(3,4,'Solaris',1),(3,4,'Solaris',2),(3,4,'Soraka',2),(3,4,'Sung Yong',2),(3,5,'Kohaku',1),(3,5,'Makoto',1),(4,2,'Solaris',1),(4,3,'Kohaku',1),(4,3,'Makoto',1),(4,4,'Soraka',1),(4,5,'Makoto',1),(4,5,'Solaris',2),(5,3,'Solaris',2),(5,3,'Soraka',1),(5,5,'Makoto',1),(5,5,'Makoto',3),(5,5,'Soraka',1),(6,3,'Sung Yong',1),(6,4,'Soraka',1),(6,4,'Sung Yong',1),(6,6,'Makoto',2),(7,5,'Makoto',2),(7,6,'Makoto',2);
/*!40000 ALTER TABLE `kist` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mannetje`
--

DROP TABLE IF EXISTS `mannetje`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mannetje` (
  `Veld_XCoord` int(11) NOT NULL,
  `Veld_YCoord` int(11) NOT NULL,
  `Veld_spelbord_spel_spelnaam` varchar(30) NOT NULL,
  `Veld_spelbord_spel_volgordeID` int(11) NOT NULL,
  KEY `FK` (`Veld_XCoord`,`Veld_YCoord`,`Veld_spelbord_spel_spelnaam`,`Veld_spelbord_spel_volgordeID`),
  CONSTRAINT `fk_Mannetje_Veld1` FOREIGN KEY (`Veld_XCoord`, `Veld_YCoord`, `Veld_spelbord_spel_spelnaam`, `Veld_spelbord_spel_volgordeID`) REFERENCES `veld` (`XCoord`, `YCoord`, `Spelbord_spel_Spelnaam`, `Spelbord_VolgordeID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mannetje`
--

LOCK TABLES `mannetje` WRITE;
/*!40000 ALTER TABLE `mannetje` DISABLE KEYS */;
INSERT INTO `mannetje` VALUES (1,6,'Sung Yong',2),(2,3,'Soraka',2),(3,4,'Makoto',1),(4,3,'Solaris',1),(4,4,'Solaris',2),(5,4,'Soraka',1),(6,2,'Kohaku',1),(6,5,'Sung Yong',1),(6,6,'Makoto',3),(6,7,'Makoto',2);
/*!40000 ALTER TABLE `mannetje` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `muur`
--

DROP TABLE IF EXISTS `muur`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `muur` (
  `Veld_XCoord` int(11) NOT NULL,
  `Veld_YCoord` int(11) NOT NULL,
  `Veld_spelbord_spel_spelnaam` varchar(30) NOT NULL,
  `Veld_spelbord_spel_volgordeID` int(11) NOT NULL,
  KEY `FK` (`Veld_XCoord`,`Veld_YCoord`,`Veld_spelbord_spel_spelnaam`,`Veld_spelbord_spel_volgordeID`),
  CONSTRAINT `fk_Muur_Veld1` FOREIGN KEY (`Veld_XCoord`, `Veld_YCoord`, `Veld_spelbord_spel_spelnaam`, `Veld_spelbord_spel_volgordeID`) REFERENCES `veld` (`XCoord`, `YCoord`, `Spelbord_spel_Spelnaam`, `Spelbord_VolgordeID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `muur`
--

LOCK TABLES `muur` WRITE;
/*!40000 ALTER TABLE `muur` DISABLE KEYS */;
INSERT INTO `muur` VALUES (0,0,'Kohaku',1),(0,0,'Soraka',2),(0,1,'Kohaku',1),(0,1,'Makoto',3),(0,1,'Soraka',2),(0,2,'Kohaku',1),(0,2,'Makoto',3),(0,2,'Soraka',2),(0,2,'Sung Yong',1),(0,2,'Sung Yong',2),(0,3,'Kohaku',1),(0,3,'Makoto',2),(0,3,'Makoto',3),(0,3,'Solaris',2),(0,3,'Soraka',2),(0,3,'Sung Yong',1),(0,3,'Sung Yong',2),(0,4,'Kohaku',1),(0,4,'Makoto',1),(0,4,'Makoto',2),(0,4,'Makoto',3),(0,4,'Solaris',2),(0,4,'Soraka',2),(0,4,'Sung Yong',1),(0,4,'Sung Yong',2),(0,5,'Makoto',1),(0,5,'Makoto',2),(0,5,'Makoto',3),(0,5,'Solaris',2),(0,5,'Soraka',2),(0,5,'Sung Yong',1),(0,5,'Sung Yong',2),(0,6,'Makoto',2),(0,6,'Makoto',3),(0,6,'Soraka',2),(0,6,'Sung Yong',1),(0,6,'Sung Yong',2),(0,7,'Makoto',2),(0,7,'Makoto',3),(0,7,'Soraka',2),(0,7,'Sung Yong',2),(0,8,'Makoto',3),(0,8,'Sung Yong',2),(0,9,'Makoto',1),(1,0,'Kohaku',1),(1,0,'Makoto',2),(1,0,'Solaris',1),(1,0,'Soraka',2),(1,1,'Makoto',2),(1,1,'Makoto',3),(1,1,'Solaris',1),(1,1,'Sung Yong',2),(1,2,'Makoto',2),(1,2,'Solaris',1),(1,2,'Soraka',2),(1,2,'Sung Yong',1),(1,2,'Sung Yong',2),(1,3,'Makoto',2),(1,3,'Solaris',1),(1,3,'Solaris',2),(1,3,'Soraka',2),(1,4,'Kohaku',1),(1,4,'Makoto',1),(1,4,'Solaris',1),(1,4,'Soraka',1),(1,5,'Kohaku',1),(1,5,'Solaris',2),(1,5,'Soraka',1),(1,6,'Kohaku',1),(1,6,'Soraka',1),(1,6,'Sung Yong',1),(1,7,'Kohaku',1),(1,7,'Makoto',2),(1,7,'Soraka',1),(1,7,'Soraka',2),(1,8,'Kohaku',1),(1,8,'Makoto',3),(1,8,'Sung Yong',2),(1,9,'Makoto',1),(2,0,'Kohaku',1),(2,0,'Makoto',1),(2,0,'Makoto',2),(2,0,'Solaris',1),(2,0,'Solaris',2),(2,0,'Soraka',2),(2,0,'Sung Yong',2),(2,1,'Makoto',1),(2,1,'Makoto',3),(2,1,'Solaris',2),(2,1,'Soraka',1),(2,1,'Sung Yong',1),(2,1,'Sung Yong',2),(2,2,'Makoto',1),(2,2,'Solaris',2),(2,2,'Soraka',1),(2,2,'Sung Yong',1),(2,3,'Makoto',1),(2,3,'Makoto',2),(2,3,'Makoto',3),(2,3,'Solaris',2),(2,3,'Soraka',1),(2,4,'Kohaku',1),(2,4,'Makoto',1),(2,4,'Solaris',1),(2,4,'Soraka',1),(2,4,'Sung Yong',1),(2,5,'Solaris',1),(2,5,'Solaris',2),(2,6,'Solaris',1),(2,6,'Sung Yong',1),(2,7,'Makoto',2),(2,7,'Soraka',1),(2,7,'Soraka',2),(2,8,'Kohaku',1),(2,8,'Makoto',3),(2,8,'Sung Yong',2),(2,9,'Makoto',1),(3,0,'Kohaku',1),(3,0,'Makoto',1),(3,0,'Makoto',2),(3,0,'Solaris',1),(3,0,'Solaris',2),(3,0,'Soraka',2),(3,0,'Sung Yong',2),(3,1,'Makoto',3),(3,1,'Soraka',1),(3,1,'Sung Yong',1),(3,2,'Makoto',1),(3,4,'Kohaku',1),(3,4,'Makoto',3),(3,5,'Solaris',2),(3,6,'Kohaku',1),(3,6,'Makoto',2),(3,6,'Makoto',3),(3,6,'Solaris',1),(3,6,'Solaris',2),(3,6,'Soraka',2),(3,6,'Sung Yong',1),(3,6,'Sung Yong',2),(3,7,'Makoto',1),(3,7,'Makoto',2),(3,7,'Solaris',2),(3,7,'Soraka',1),(3,7,'Soraka',2),(3,7,'Sung Yong',2),(3,8,'Kohaku',1),(3,8,'Makoto',1),(3,8,'Makoto',3),(3,8,'Sung Yong',2),(3,9,'Makoto',1),(4,0,'Kohaku',1),(4,0,'Makoto',1),(4,0,'Makoto',2),(4,0,'Solaris',1),(4,0,'Solaris',2),(4,0,'Soraka',2),(4,0,'Sung Yong',2),(4,1,'Kohaku',1),(4,1,'Makoto',3),(4,1,'Solaris',2),(4,1,'Soraka',1),(4,1,'Soraka',2),(4,1,'Sung Yong',1),(4,1,'Sung Yong',2),(4,2,'Kohaku',1),(4,2,'Makoto',2),(4,2,'Makoto',3),(4,2,'Solaris',2),(4,2,'Soraka',2),(4,3,'Makoto',2),(4,3,'Soraka',2),(4,4,'Makoto',2),(4,4,'Makoto',3),(4,5,'Soraka',2),(4,6,'Makoto',2),(4,6,'Makoto',3),(4,6,'Solaris',1),(4,6,'Soraka',2),(4,6,'Sung Yong',1),(4,6,'Sung Yong',2),(4,7,'Makoto',1),(4,7,'Makoto',3),(4,7,'Solaris',2),(4,7,'Soraka',1),(4,8,'Kohaku',1),(4,8,'Makoto',3),(5,0,'Makoto',1),(5,0,'Makoto',2),(5,0,'Solaris',1),(5,1,'Kohaku',1),(5,1,'Solaris',1),(5,1,'Soraka',1),(5,1,'Sung Yong',1),(5,1,'Sung Yong',2),(5,2,'Makoto',2),(5,2,'Makoto',3),(5,2,'Solaris',1),(5,2,'Solaris',2),(5,2,'Sung Yong',1),(5,2,'Sung Yong',2),(5,3,'Makoto',1),(5,3,'Soraka',2),(5,3,'Sung Yong',2),(5,4,'Makoto',1),(5,4,'Makoto',2),(5,4,'Solaris',1),(5,4,'Solaris',2),(5,4,'Soraka',2),(5,4,'Sung Yong',1),(5,5,'Kohaku',1),(5,5,'Solaris',2),(5,5,'Soraka',2),(5,5,'Sung Yong',1),(5,5,'Sung Yong',2),(5,6,'Kohaku',1),(5,6,'Makoto',2),(5,6,'Solaris',1),(5,6,'Solaris',2),(5,6,'Sung Yong',1),(5,6,'Sung Yong',2),(5,7,'Kohaku',1),(5,7,'Makoto',1),(5,7,'Makoto',2),(5,7,'Makoto',3),(5,7,'Solaris',2),(5,7,'Soraka',1),(5,8,'Kohaku',1),(5,8,'Makoto',2),(6,0,'Makoto',1),(6,0,'Makoto',2),(6,1,'Kohaku',1),(6,1,'Makoto',2),(6,1,'Soraka',1),(6,2,'Makoto',1),(6,2,'Makoto',2),(6,2,'Makoto',3),(6,2,'Solaris',1),(6,2,'Solaris',2),(6,2,'Sung Yong',1),(6,3,'Makoto',3),(6,3,'Sung Yong',2),(6,4,'Makoto',1),(6,4,'Makoto',2),(6,4,'Makoto',3),(6,4,'Solaris',2),(6,4,'Sung Yong',2),(6,5,'Sung Yong',2),(6,6,'Solaris',1),(6,6,'Sung Yong',1),(6,7,'Kohaku',1),(6,7,'Makoto',1),(6,7,'Makoto',3),(6,7,'Soraka',1),(6,8,'Makoto',2),(7,0,'Makoto',1),(7,1,'Kohaku',1),(7,1,'Makoto',1),(7,1,'Soraka',1),(7,2,'Kohaku',1),(7,2,'Makoto',1),(7,2,'Solaris',1),(7,2,'Solaris',2),(7,2,'Sung Yong',1),(7,3,'Kohaku',1),(7,3,'Solaris',1),(7,3,'Solaris',2),(7,4,'Kohaku',1),(7,4,'Makoto',1),(7,4,'Makoto',2),(7,4,'Makoto',3),(7,4,'Solaris',1),(7,4,'Solaris',2),(7,5,'Kohaku',1),(7,5,'Makoto',3),(7,5,'Solaris',1),(7,6,'Kohaku',1),(7,6,'Makoto',3),(7,6,'Solaris',1),(7,6,'Sung Yong',1),(7,7,'Kohaku',1),(7,7,'Makoto',1),(7,7,'Makoto',3),(7,7,'Soraka',1),(7,8,'Makoto',2),(8,1,'Soraka',1),(8,2,'Soraka',1),(8,2,'Sung Yong',1),(8,3,'Soraka',1),(8,3,'Sung Yong',1),(8,4,'Makoto',1),(8,4,'Makoto',2),(8,4,'Soraka',1),(8,4,'Sung Yong',1),(8,5,'Makoto',1),(8,5,'Soraka',1),(8,5,'Sung Yong',1),(8,6,'Makoto',1),(8,6,'Soraka',1),(8,6,'Sung Yong',1),(8,7,'Makoto',1),(8,7,'Soraka',1),(8,8,'Makoto',2),(9,4,'Makoto',1),(9,4,'Makoto',2),(9,5,'Makoto',1),(9,5,'Makoto',2),(9,6,'Makoto',1),(9,6,'Makoto',2),(9,7,'Makoto',1),(9,7,'Makoto',2),(9,8,'Makoto',1),(9,8,'Makoto',2);
/*!40000 ALTER TABLE `muur` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `spel`
--

DROP TABLE IF EXISTS `spel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `spel` (
  `Spelnaam` varchar(30) NOT NULL,
  PRIMARY KEY (`Spelnaam`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `spel`
--

LOCK TABLES `spel` WRITE;
/*!40000 ALTER TABLE `spel` DISABLE KEYS */;
INSERT INTO `spel` VALUES ('Kohaku'),('Makoto'),('Solaris'),('Soraka'),('Sung Yong');
/*!40000 ALTER TABLE `spel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `spelbord`
--

DROP TABLE IF EXISTS `spelbord`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `spelbord` (
  `Mapcode` varchar(100) NOT NULL,
  `spel_Spelnaam` varchar(30) NOT NULL,
  `VolgordeID` int(11) NOT NULL,
  PRIMARY KEY (`spel_Spelnaam`,`VolgordeID`),
  KEY `fk_Spelbord_spel1_idx` (`spel_Spelnaam`),
  CONSTRAINT `fk_Spelbord_spel1` FOREIGN KEY (`spel_Spelnaam`) REFERENCES `spel` (`Spelnaam`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `spelbord`
--

LOCK TABLES `spelbord` WRITE;
/*!40000 ALTER TABLE `spelbord` DISABLE KEYS */;
INSERT INTO `spelbord` VALUES ('#####+++++#...####++#.K.#.@#++#...K..#++####..o#+++#.K.#o#+++#.#.#o#+++#...###+++#####++++++++++++++','Kohaku',1),('++######++++#oooo#++++##oo##++++#KK#++++###@.######..KKK..##........##.K.#######...#+++++#####+++++','Makoto',1),('+######++++#.ooo#++++#..###+++###.#+++++#...#######......K.##..###KK.#####+#@..#+++++#####++++++++++','Makoto',2),('++++++++++#####+++++#...###+++#K#...#+++#..##.##++#....K.#++#.K##.@#++#ooo####++#####+++++++++++++++','Makoto',3),('+#####+++++#...#+++++#o.K###+++#o.@..#+++##K.#.#++++#....#++++######++++++++++++++++++++++++++++++++','Solaris',1),('++###+++++++#o#+++++++#.####++###K.Ko#++#o.K@###++####K#+++++++#o#+++++++###++++++++++++++++++++++++','Solaris',2),('++++++++++++#######+++#.....#+++#.oKo.#++##.K@k.#++#..oKo.#++#......#++########+++++++++++++++++++++','Soraka',1),('#####+++++#ooo#+++++##K.#+++++##@.##++++#.KK.#++++#...##++++#..##+++++####++++++++++++++++++++++++++','Soraka',2),('++++++++++++####++++###..####+#.....K.#+#.#..#K.#+#.o.o#@.#+#########+++++++++++++++++++++++++++++++','Sung Yong',1),('++###++++++##.##++++##ok.#++++#ooo.##+++#KoK..#+++#.K..##+++#@K###++++#..#++++++####++++++++++++++++','Sung Yong',2);
/*!40000 ALTER TABLE `spelbord` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `speler`
--

DROP TABLE IF EXISTS `speler`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `speler` (
  `voornaam` varchar(30) DEFAULT NULL,
  `achternaam` varchar(45) DEFAULT NULL,
  `gebruikersnaam` varchar(30) NOT NULL,
  `wachtwoord` varchar(30) NOT NULL,
  `admin` tinyint(1) NOT NULL,
  PRIMARY KEY (`gebruikersnaam`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `speler`
--

LOCK TABLES `speler` WRITE;
/*!40000 ALTER TABLE `speler` DISABLE KEYS */;
INSERT INTO `speler` VALUES ('Mario','Verstraeten','dbsowner','588808992',1),('Tester','Tester','tester123','829275379',1),('Tester1234','Tester1234','tester1234','-62266975',0);
/*!40000 ALTER TABLE `speler` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `veld`
--

DROP TABLE IF EXISTS `veld`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `veld` (
  `heeftDoel` tinyint(1) DEFAULT NULL,
  `XCoord` int(11) NOT NULL,
  `YCoord` int(11) NOT NULL,
  `Spelbord_spel_Spelnaam` varchar(30) NOT NULL,
  `Spelbord_VolgordeID` int(11) NOT NULL,
  KEY `fk_veld_Spelbord1_idx` (`XCoord`,`YCoord`,`Spelbord_spel_Spelnaam`,`Spelbord_VolgordeID`),
  KEY `fk_veld_Spelbord1` (`Spelbord_spel_Spelnaam`,`Spelbord_VolgordeID`),
  CONSTRAINT `fk_veld_Spelbord1` FOREIGN KEY (`Spelbord_spel_Spelnaam`, `Spelbord_VolgordeID`) REFERENCES `spelbord` (`spel_Spelnaam`, `VolgordeID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `veld`
--

LOCK TABLES `veld` WRITE;
/*!40000 ALTER TABLE `veld` DISABLE KEYS */;
INSERT INTO `veld` VALUES (0,2,1,'Sung Yong',1),(0,3,1,'Sung Yong',1),(0,4,1,'Sung Yong',1),(0,5,1,'Sung Yong',1),(0,0,2,'Sung Yong',1),(0,1,2,'Sung Yong',1),(0,2,2,'Sung Yong',1),(0,3,2,'Sung Yong',1),(0,4,2,'Sung Yong',1),(0,5,2,'Sung Yong',1),(0,6,2,'Sung Yong',1),(0,7,2,'Sung Yong',1),(0,8,2,'Sung Yong',1),(0,0,3,'Sung Yong',1),(0,1,3,'Sung Yong',1),(0,2,3,'Sung Yong',1),(0,3,3,'Sung Yong',1),(0,4,3,'Sung Yong',1),(0,5,3,'Sung Yong',1),(0,6,3,'Sung Yong',1),(0,7,3,'Sung Yong',1),(0,8,3,'Sung Yong',1),(0,0,4,'Sung Yong',1),(0,1,4,'Sung Yong',1),(0,2,4,'Sung Yong',1),(0,3,4,'Sung Yong',1),(0,4,4,'Sung Yong',1),(0,5,4,'Sung Yong',1),(0,6,4,'Sung Yong',1),(0,7,4,'Sung Yong',1),(0,8,4,'Sung Yong',1),(0,0,5,'Sung Yong',1),(0,1,5,'Sung Yong',1),(1,2,5,'Sung Yong',1),(0,3,5,'Sung Yong',1),(1,4,5,'Sung Yong',1),(0,5,5,'Sung Yong',1),(0,6,5,'Sung Yong',1),(0,7,5,'Sung Yong',1),(0,8,5,'Sung Yong',1),(0,0,6,'Sung Yong',1),(0,1,6,'Sung Yong',1),(0,2,6,'Sung Yong',1),(0,3,6,'Sung Yong',1),(0,4,6,'Sung Yong',1),(0,5,6,'Sung Yong',1),(0,6,6,'Sung Yong',1),(0,7,6,'Sung Yong',1),(0,8,6,'Sung Yong',1),(0,2,0,'Sung Yong',2),(0,3,0,'Sung Yong',2),(0,4,0,'Sung Yong',2),(0,1,1,'Sung Yong',2),(0,2,1,'Sung Yong',2),(0,3,1,'Sung Yong',2),(0,4,1,'Sung Yong',2),(0,5,1,'Sung Yong',2),(0,0,2,'Sung Yong',2),(0,1,2,'Sung Yong',2),(1,2,2,'Sung Yong',2),(0,3,2,'Sung Yong',2),(0,4,2,'Sung Yong',2),(0,5,2,'Sung Yong',2),(0,0,3,'Sung Yong',2),(1,1,3,'Sung Yong',2),(1,2,3,'Sung Yong',2),(1,3,3,'Sung Yong',2),(0,4,3,'Sung Yong',2),(0,5,3,'Sung Yong',2),(0,6,3,'Sung Yong',2),(0,0,4,'Sung Yong',2),(0,1,4,'Sung Yong',2),(1,2,4,'Sung Yong',2),(0,3,4,'Sung Yong',2),(0,4,4,'Sung Yong',2),(0,5,4,'Sung Yong',2),(0,6,4,'Sung Yong',2),(0,0,5,'Sung Yong',2),(0,1,5,'Sung Yong',2),(0,2,5,'Sung Yong',2),(0,3,5,'Sung Yong',2),(0,4,5,'Sung Yong',2),(0,5,5,'Sung Yong',2),(0,6,5,'Sung Yong',2),(0,0,6,'Sung Yong',2),(0,1,6,'Sung Yong',2),(0,2,6,'Sung Yong',2),(0,3,6,'Sung Yong',2),(0,4,6,'Sung Yong',2),(0,5,6,'Sung Yong',2),(0,0,7,'Sung Yong',2),(0,1,7,'Sung Yong',2),(0,2,7,'Sung Yong',2),(0,3,7,'Sung Yong',2),(0,0,8,'Sung Yong',2),(0,1,8,'Sung Yong',2),(0,2,8,'Sung Yong',2),(0,3,8,'Sung Yong',2),(0,2,0,'Solaris',2),(0,3,0,'Solaris',2),(0,4,0,'Solaris',2),(0,2,1,'Solaris',2),(1,3,1,'Solaris',2),(0,4,1,'Solaris',2),(0,2,2,'Solaris',2),(0,3,2,'Solaris',2),(0,4,2,'Solaris',2),(0,5,2,'Solaris',2),(0,6,2,'Solaris',2),(0,7,2,'Solaris',2),(0,0,3,'Solaris',2),(0,1,3,'Solaris',2),(0,2,3,'Solaris',2),(0,3,3,'Solaris',2),(0,4,3,'Solaris',2),(0,5,3,'Solaris',2),(1,6,3,'Solaris',2),(0,7,3,'Solaris',2),(0,0,4,'Solaris',2),(1,1,4,'Solaris',2),(0,2,4,'Solaris',2),(0,3,4,'Solaris',2),(0,4,4,'Solaris',2),(0,5,4,'Solaris',2),(0,6,4,'Solaris',2),(0,7,4,'Solaris',2),(0,0,5,'Solaris',2),(0,1,5,'Solaris',2),(0,2,5,'Solaris',2),(0,3,5,'Solaris',2),(0,4,5,'Solaris',2),(0,5,5,'Solaris',2),(0,3,6,'Solaris',2),(1,4,6,'Solaris',2),(0,5,6,'Solaris',2),(0,3,7,'Solaris',2),(0,4,7,'Solaris',2),(0,5,7,'Solaris',2),(0,1,0,'Solaris',1),(0,2,0,'Solaris',1),(0,3,0,'Solaris',1),(0,4,0,'Solaris',1),(0,5,0,'Solaris',1),(0,1,1,'Solaris',1),(0,2,1,'Solaris',1),(0,3,1,'Solaris',1),(0,4,1,'Solaris',1),(0,5,1,'Solaris',1),(0,1,2,'Solaris',1),(1,2,2,'Solaris',1),(0,3,2,'Solaris',1),(0,4,2,'Solaris',1),(0,5,2,'Solaris',1),(0,6,2,'Solaris',1),(0,7,2,'Solaris',1),(0,1,3,'Solaris',1),(1,2,3,'Solaris',1),(0,3,3,'Solaris',1),(0,4,3,'Solaris',1),(0,5,3,'Solaris',1),(0,6,3,'Solaris',1),(0,7,3,'Solaris',1),(0,1,4,'Solaris',1),(0,2,4,'Solaris',1),(0,3,4,'Solaris',1),(0,4,4,'Solaris',1),(0,5,4,'Solaris',1),(0,6,4,'Solaris',1),(0,7,4,'Solaris',1),(0,2,5,'Solaris',1),(0,3,5,'Solaris',1),(0,4,5,'Solaris',1),(0,5,5,'Solaris',1),(0,6,5,'Solaris',1),(0,7,5,'Solaris',1),(0,2,6,'Solaris',1),(0,3,6,'Solaris',1),(0,4,6,'Solaris',1),(0,5,6,'Solaris',1),(0,6,6,'Solaris',1),(0,7,6,'Solaris',1),(0,2,1,'Soraka',1),(0,3,1,'Soraka',1),(0,4,1,'Soraka',1),(0,5,1,'Soraka',1),(0,6,1,'Soraka',1),(0,7,1,'Soraka',1),(0,8,1,'Soraka',1),(0,2,2,'Soraka',1),(0,3,2,'Soraka',1),(0,4,2,'Soraka',1),(0,5,2,'Soraka',1),(0,6,2,'Soraka',1),(0,7,2,'Soraka',1),(0,8,2,'Soraka',1),(0,2,3,'Soraka',1),(0,3,3,'Soraka',1),(1,4,3,'Soraka',1),(0,5,3,'Soraka',1),(1,6,3,'Soraka',1),(0,7,3,'Soraka',1),(0,8,3,'Soraka',1),(0,1,4,'Soraka',1),(0,2,4,'Soraka',1),(0,3,4,'Soraka',1),(0,4,4,'Soraka',1),(0,5,4,'Soraka',1),(0,6,4,'Soraka',1),(0,7,4,'Soraka',1),(0,8,4,'Soraka',1),(0,1,5,'Soraka',1),(0,2,5,'Soraka',1),(0,3,5,'Soraka',1),(1,4,5,'Soraka',1),(0,5,5,'Soraka',1),(1,6,5,'Soraka',1),(0,7,5,'Soraka',1),(0,8,5,'Soraka',1),(0,1,6,'Soraka',1),(0,2,6,'Soraka',1),(0,3,6,'Soraka',1),(0,4,6,'Soraka',1),(0,5,6,'Soraka',1),(0,6,6,'Soraka',1),(0,7,6,'Soraka',1),(0,8,6,'Soraka',1),(0,1,7,'Soraka',1),(0,2,7,'Soraka',1),(0,3,7,'Soraka',1),(0,4,7,'Soraka',1),(0,5,7,'Soraka',1),(0,6,7,'Soraka',1),(0,7,7,'Soraka',1),(0,8,7,'Soraka',1),(0,0,0,'Soraka',2),(0,1,0,'Soraka',2),(0,2,0,'Soraka',2),(0,3,0,'Soraka',2),(0,4,0,'Soraka',2),(0,0,1,'Soraka',2),(1,1,1,'Soraka',2),(1,2,1,'Soraka',2),(1,3,1,'Soraka',2),(0,4,1,'Soraka',2),(0,0,2,'Soraka',2),(0,1,2,'Soraka',2),(0,2,2,'Soraka',2),(0,3,2,'Soraka',2),(0,4,2,'Soraka',2),(0,0,3,'Soraka',2),(0,1,3,'Soraka',2),(0,2,3,'Soraka',2),(0,3,3,'Soraka',2),(0,4,3,'Soraka',2),(0,5,3,'Soraka',2),(0,0,4,'Soraka',2),(0,1,4,'Soraka',2),(0,2,4,'Soraka',2),(0,3,4,'Soraka',2),(0,4,4,'Soraka',2),(0,5,4,'Soraka',2),(0,0,5,'Soraka',2),(0,1,5,'Soraka',2),(0,2,5,'Soraka',2),(0,3,5,'Soraka',2),(0,4,5,'Soraka',2),(0,5,5,'Soraka',2),(0,0,6,'Soraka',2),(0,1,6,'Soraka',2),(0,2,6,'Soraka',2),(0,3,6,'Soraka',2),(0,4,6,'Soraka',2),(0,0,7,'Soraka',2),(0,1,7,'Soraka',2),(0,2,7,'Soraka',2),(0,3,7,'Soraka',2),(0,2,0,'Makoto',1),(0,3,0,'Makoto',1),(0,4,0,'Makoto',1),(0,5,0,'Makoto',1),(0,6,0,'Makoto',1),(0,7,0,'Makoto',1),(0,2,1,'Makoto',1),(1,3,1,'Makoto',1),(1,4,1,'Makoto',1),(1,5,1,'Makoto',1),(1,6,1,'Makoto',1),(0,7,1,'Makoto',1),(0,2,2,'Makoto',1),(0,3,2,'Makoto',1),(1,4,2,'Makoto',1),(1,5,2,'Makoto',1),(0,6,2,'Makoto',1),(0,7,2,'Makoto',1),(0,2,3,'Makoto',1),(0,3,3,'Makoto',1),(0,4,3,'Makoto',1),(0,5,3,'Makoto',1),(0,0,4,'Makoto',1),(0,1,4,'Makoto',1),(0,2,4,'Makoto',1),(0,3,4,'Makoto',1),(0,4,4,'Makoto',1),(0,5,4,'Makoto',1),(0,6,4,'Makoto',1),(0,7,4,'Makoto',1),(0,8,4,'Makoto',1),(0,9,4,'Makoto',1),(0,0,5,'Makoto',1),(0,1,5,'Makoto',1),(0,2,5,'Makoto',1),(0,3,5,'Makoto',1),(0,4,5,'Makoto',1),(0,5,5,'Makoto',1),(0,6,5,'Makoto',1),(0,7,5,'Makoto',1),(0,8,5,'Makoto',1),(0,9,5,'Makoto',1),(0,0,6,'Makoto',1),(0,1,6,'Makoto',1),(0,2,6,'Makoto',1),(0,3,6,'Makoto',1),(0,4,6,'Makoto',1),(0,5,6,'Makoto',1),(0,6,6,'Makoto',1),(0,7,6,'Makoto',1),(0,8,6,'Makoto',1),(0,9,6,'Makoto',1),(0,0,7,'Makoto',1),(0,1,7,'Makoto',1),(0,2,7,'Makoto',1),(0,3,7,'Makoto',1),(0,4,7,'Makoto',1),(0,5,7,'Makoto',1),(0,6,7,'Makoto',1),(0,7,7,'Makoto',1),(0,8,7,'Makoto',1),(0,9,7,'Makoto',1),(0,0,8,'Makoto',1),(0,1,8,'Makoto',1),(0,2,8,'Makoto',1),(0,3,8,'Makoto',1),(0,9,8,'Makoto',1),(0,0,9,'Makoto',1),(0,1,9,'Makoto',1),(0,2,9,'Makoto',1),(0,3,9,'Makoto',1),(0,1,0,'Makoto',2),(0,2,0,'Makoto',2),(0,3,0,'Makoto',2),(0,4,0,'Makoto',2),(0,5,0,'Makoto',2),(0,6,0,'Makoto',2),(0,1,1,'Makoto',2),(0,2,1,'Makoto',2),(1,3,1,'Makoto',2),(1,4,1,'Makoto',2),(1,5,1,'Makoto',2),(0,6,1,'Makoto',2),(0,1,2,'Makoto',2),(0,2,2,'Makoto',2),(0,3,2,'Makoto',2),(0,4,2,'Makoto',2),(0,5,2,'Makoto',2),(0,6,2,'Makoto',2),(0,0,3,'Makoto',2),(0,1,3,'Makoto',2),(0,2,3,'Makoto',2),(0,3,3,'Makoto',2),(0,4,3,'Makoto',2),(0,0,4,'Makoto',2),(0,1,4,'Makoto',2),(0,2,4,'Makoto',2),(0,3,4,'Makoto',2),(0,4,4,'Makoto',2),(0,5,4,'Makoto',2),(0,6,4,'Makoto',2),(0,7,4,'Makoto',2),(0,8,4,'Makoto',2),(0,9,4,'Makoto',2),(0,0,5,'Makoto',2),(0,1,5,'Makoto',2),(0,2,5,'Makoto',2),(0,3,5,'Makoto',2),(0,4,5,'Makoto',2),(0,5,5,'Makoto',2),(0,6,5,'Makoto',2),(0,7,5,'Makoto',2),(0,8,5,'Makoto',2),(0,9,5,'Makoto',2),(0,0,6,'Makoto',2),(0,1,6,'Makoto',2),(0,2,6,'Makoto',2),(0,3,6,'Makoto',2),(0,4,6,'Makoto',2),(0,5,6,'Makoto',2),(0,6,6,'Makoto',2),(0,7,6,'Makoto',2),(0,8,6,'Makoto',2),(0,9,6,'Makoto',2),(0,0,7,'Makoto',2),(0,1,7,'Makoto',2),(0,2,7,'Makoto',2),(0,3,7,'Makoto',2),(0,5,7,'Makoto',2),(0,6,7,'Makoto',2),(0,7,7,'Makoto',2),(0,8,7,'Makoto',2),(0,9,7,'Makoto',2),(0,5,8,'Makoto',2),(0,6,8,'Makoto',2),(0,7,8,'Makoto',2),(0,8,8,'Makoto',2),(0,9,8,'Makoto',2),(0,0,1,'Makoto',3),(0,1,1,'Makoto',3),(0,2,1,'Makoto',3),(0,3,1,'Makoto',3),(0,4,1,'Makoto',3),(0,0,2,'Makoto',3),(0,1,2,'Makoto',3),(0,2,2,'Makoto',3),(0,3,2,'Makoto',3),(0,4,2,'Makoto',3),(0,5,2,'Makoto',3),(0,6,2,'Makoto',3),(0,0,3,'Makoto',3),(0,1,3,'Makoto',3),(0,2,3,'Makoto',3),(0,3,3,'Makoto',3),(0,4,3,'Makoto',3),(0,5,3,'Makoto',3),(0,6,3,'Makoto',3),(0,0,4,'Makoto',3),(0,1,4,'Makoto',3),(0,2,4,'Makoto',3),(0,3,4,'Makoto',3),(0,4,4,'Makoto',3),(0,5,4,'Makoto',3),(0,6,4,'Makoto',3),(0,7,4,'Makoto',3),(0,0,5,'Makoto',3),(0,1,5,'Makoto',3),(0,2,5,'Makoto',3),(0,3,5,'Makoto',3),(0,4,5,'Makoto',3),(0,5,5,'Makoto',3),(0,6,5,'Makoto',3),(0,7,5,'Makoto',3),(0,0,6,'Makoto',3),(0,1,6,'Makoto',3),(0,2,6,'Makoto',3),(0,3,6,'Makoto',3),(0,4,6,'Makoto',3),(0,5,6,'Makoto',3),(0,6,6,'Makoto',3),(0,7,6,'Makoto',3),(0,0,7,'Makoto',3),(1,1,7,'Makoto',3),(1,2,7,'Makoto',3),(1,3,7,'Makoto',3),(0,4,7,'Makoto',3),(0,5,7,'Makoto',3),(0,6,7,'Makoto',3),(0,7,7,'Makoto',3),(0,0,8,'Makoto',3),(0,1,8,'Makoto',3),(0,2,8,'Makoto',3),(0,3,8,'Makoto',3),(0,4,8,'Makoto',3),(0,0,0,'Kohaku',1),(0,1,0,'Kohaku',1),(0,2,0,'Kohaku',1),(0,3,0,'Kohaku',1),(0,4,0,'Kohaku',1),(0,0,1,'Kohaku',1),(0,1,1,'Kohaku',1),(0,2,1,'Kohaku',1),(0,3,1,'Kohaku',1),(0,4,1,'Kohaku',1),(0,5,1,'Kohaku',1),(0,6,1,'Kohaku',1),(0,7,1,'Kohaku',1),(0,0,2,'Kohaku',1),(0,1,2,'Kohaku',1),(0,2,2,'Kohaku',1),(0,3,2,'Kohaku',1),(0,4,2,'Kohaku',1),(0,5,2,'Kohaku',1),(0,6,2,'Kohaku',1),(0,7,2,'Kohaku',1),(0,0,3,'Kohaku',1),(0,1,3,'Kohaku',1),(0,2,3,'Kohaku',1),(0,3,3,'Kohaku',1),(0,4,3,'Kohaku',1),(0,5,3,'Kohaku',1),(0,6,3,'Kohaku',1),(0,7,3,'Kohaku',1),(0,0,4,'Kohaku',1),(0,1,4,'Kohaku',1),(0,2,4,'Kohaku',1),(0,3,4,'Kohaku',1),(0,4,4,'Kohaku',1),(0,5,4,'Kohaku',1),(1,6,4,'Kohaku',1),(0,7,4,'Kohaku',1),(0,1,5,'Kohaku',1),(0,2,5,'Kohaku',1),(0,3,5,'Kohaku',1),(0,4,5,'Kohaku',1),(0,5,5,'Kohaku',1),(1,6,5,'Kohaku',1),(0,7,5,'Kohaku',1),(0,1,6,'Kohaku',1),(0,2,6,'Kohaku',1),(0,3,6,'Kohaku',1),(0,4,6,'Kohaku',1),(0,5,6,'Kohaku',1),(1,6,6,'Kohaku',1),(0,7,6,'Kohaku',1),(0,1,7,'Kohaku',1),(0,2,7,'Kohaku',1),(0,3,7,'Kohaku',1),(0,4,7,'Kohaku',1),(0,5,7,'Kohaku',1),(0,6,7,'Kohaku',1),(0,7,7,'Kohaku',1),(0,1,8,'Kohaku',1),(0,2,8,'Kohaku',1),(0,3,8,'Kohaku',1),(0,4,8,'Kohaku',1),(0,5,8,'Kohaku',1);
/*!40000 ALTER TABLE `veld` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-05-12  9:34:41
