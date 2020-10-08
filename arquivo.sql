-- MariaDB dump 10.17  Distrib 10.4.8-MariaDB, for Win64 (AMD64)
--
-- Host: localhost    Database: api_contracheque
-- ------------------------------------------------------
-- Server version	10.4.8-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `funcionario`
--

DROP TABLE IF EXISTS `funcionario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `funcionario` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `data_de_admissao` datetime DEFAULT NULL,
  `desconta_plano_de_saude` bit(1) NOT NULL,
  `desconta_plano_dental` bit(1) NOT NULL,
  `desconta_vale_transporte` bit(1) NOT NULL,
  `documento` varchar(11) DEFAULT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `salario_bruto` double NOT NULL,
  `setor` varchar(255) DEFAULT NULL,
  `sobrenome` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKcssw1sscshj0a4b63yey606l9` (`documento`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `funcionario`
--

LOCK TABLES `funcionario` WRITE;
/*!40000 ALTER TABLE `funcionario` DISABLE KEYS */;
INSERT INTO `funcionario` VALUES (1,'2019-01-02 15:33:00','','','','00099458810','Daiane Sarah',1000,'RH','Porto'),(2,'2018-01-02 15:33:00','','','\0','08899345171','Kevin Oliver',2000,'Marketing','Silva'),(3,'2002-01-02 15:33:00','','\0','','10272679712','Luzia Marina',3000,'Vendas','Farias'),(4,'2001-01-02 15:33:00','\0','','','35407397854','Ester Mirella',4000,'Compras','de Paula'),(5,'2015-01-02 15:33:00','','\0','\0','69670036747','Tatiane Betina Laís',5000,'RH','Galvão'),(6,'2020-01-02 15:33:00','\0','','\0','04456466243','Thomas Sebastião',6000,'Marketing','Bernardes'),(7,'2017-01-02 15:33:00','\0','\0','','70926876082','Melissa Gabriela',7000,'Vendas','Nascimento'),(8,'2008-01-02 15:33:00','\0','\0','\0','37835550199','Gabriel Augusto',8000,'Compras','Mendes'),(9,'2019-01-02 15:33:00','','','','25910888021','Funcionario Delete',1000,'RH','Porto'),(11,'2019-01-02 00:00:00','','','','32457040892','Daiane Sarah',1000,'RH','Porto');
/*!40000 ALTER TABLE `funcionario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `perfis`
--

DROP TABLE IF EXISTS `perfis`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `perfis` (
  `usuario_codigo` int(11) NOT NULL,
  `perfis` int(11) DEFAULT NULL,
  KEY `FK5i472wtil2m70150ugv0jlejx` (`usuario_codigo`),
  CONSTRAINT `FK5i472wtil2m70150ugv0jlejx` FOREIGN KEY (`usuario_codigo`) REFERENCES `usuario` (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `perfis`
--

LOCK TABLES `perfis` WRITE;
/*!40000 ALTER TABLE `perfis` DISABLE KEYS */;
INSERT INTO `perfis` VALUES (1,1);
/*!40000 ALTER TABLE `perfis` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuario` (
  `codigo` int(11) NOT NULL AUTO_INCREMENT,
  `data_hora_ultima_alteracao` datetime DEFAULT NULL,
  `data_hora_ultimo_bloqueio` datetime DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `senha` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `usuario_inclusao_codigo` int(11) DEFAULT NULL,
  `usuario_ultima_alteracao_codigo` int(11) DEFAULT NULL,
  PRIMARY KEY (`codigo`),
  KEY `FKt5u2wy9uqq22t5ifw36cj5gs4` (`usuario_inclusao_codigo`),
  KEY `FKbgb9hx3mo0kum4cjvjt8ovi9e` (`usuario_ultima_alteracao_codigo`),
  CONSTRAINT `FKbgb9hx3mo0kum4cjvjt8ovi9e` FOREIGN KEY (`usuario_ultima_alteracao_codigo`) REFERENCES `usuario` (`codigo`),
  CONSTRAINT `FKt5u2wy9uqq22t5ifw36cj5gs4` FOREIGN KEY (`usuario_inclusao_codigo`) REFERENCES `usuario` (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,'2020-10-07 21:12:28',NULL,'everton.mfernandes@gmail.com','Administrador','$2a$10$q5IIPjgeQzT5GDKDsdgLn.DyatoVc3xlsK7MCmYPVt/IcimQsewB.',1,1,1);
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-10-07 21:38:24
