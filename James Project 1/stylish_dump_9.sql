-- MySQL dump 10.13  Distrib 8.0.26, for Win64 (x86_64)
--
-- Host: localhost    Database: stylish
-- ------------------------------------------------------
-- Server version	8.0.26

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
-- Table structure for table `campaign`
--

DROP TABLE IF EXISTS `campaign`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `campaign` (
  `id` int NOT NULL AUTO_INCREMENT,
  `product_id` int DEFAULT NULL,
  `picture` varchar(255) DEFAULT NULL,
  `story` text,
  PRIMARY KEY (`id`),
  KEY `product_id` (`product_id`),
  CONSTRAINT `campaign_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `campaign`
--

LOCK TABLES `campaign` WRITE;
/*!40000 ALTER TABLE `campaign` DISABLE KEYS */;
INSERT INTO `campaign` VALUES (1,2,'uploads/c0124928-8126-4e9c-9a9f-937213d04209.jpg','於是<br>我也想要給你<br>一個那麼美好的自己。<br>不朽《與自己和好如初》'),(2,1,'uploads/a45e8764-7bac-4297-9c46-49b7a87afd61.jpg','永遠<br>展現自信與專業<br>無法抵擋的男人魅力。<br>復古《再一次經典》'),(3,4,'uploads/40ff8120-d894-4784-ba43-c4c6f8eaaa20.jpg','瞬間<br>在城市的角落<br>找到失落多時的記憶。<br>印象《都會故事集》');
/*!40000 ALTER TABLE `campaign` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `color`
--

DROP TABLE IF EXISTS `color`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `color` (
  `code` varchar(6) NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `color`
--

LOCK TABLES `color` WRITE;
/*!40000 ALTER TABLE `color` DISABLE KEYS */;
INSERT INTO `color` VALUES ('00008B','海軍藍'),('6C6F70','石板灰'),('8B0000','暗紅'),('8B4513','馬鞍棕'),('ADD8E6','海水藍'),('FFC0CB','少女粉');
/*!40000 ALTER TABLE `color` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hots`
--

DROP TABLE IF EXISTS `hots`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hots` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hots`
--

LOCK TABLES `hots` WRITE;
/*!40000 ALTER TABLE `hots` DISABLE KEYS */;
/*!40000 ALTER TABLE `hots` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hots_products`
--

DROP TABLE IF EXISTS `hots_products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hots_products` (
  `id` int NOT NULL AUTO_INCREMENT,
  `hots_id` int DEFAULT NULL,
  `product_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `hots_id` (`hots_id`),
  KEY `product_id` (`product_id`),
  CONSTRAINT `hots_products_ibfk_1` FOREIGN KEY (`hots_id`) REFERENCES `hots` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `hots_products_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hots_products`
--

LOCK TABLES `hots_products` WRITE;
/*!40000 ALTER TABLE `hots_products` DISABLE KEYS */;
/*!40000 ALTER TABLE `hots_products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `image`
--

DROP TABLE IF EXISTS `image`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `image` (
  `id` int NOT NULL AUTO_INCREMENT,
  `url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `url` (`url`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `image`
--

LOCK TABLES `image` WRITE;
/*!40000 ALTER TABLE `image` DISABLE KEYS */;
INSERT INTO `image` VALUES (10,'uploads/00b19391-b3d7-4442-b47b-ec63afa3ada6.jpg'),(3,'uploads/0b7e1e5e-30b8-4e98-b297-bf726570b79d.jpg'),(20,'uploads/19ca1849-a983-4d60-9263-3d45f7edb214.jpg'),(18,'uploads/1f6b54fe-c4af-40fe-8946-406b55adb8bd.jpg'),(33,'uploads/1fe977dd-7843-4dea-881d-61b54db34402.jpg'),(1,'uploads/216c045d-db80-4d1f-b117-7909965cbd33.jpg'),(7,'uploads/25128ca3-f61d-4815-b77c-fc285643726d.jpg'),(15,'uploads/32567c5d-8dd7-4873-b23c-44078b4ff1c3.jpg'),(17,'uploads/3c492424-4b86-458a-b0be-b9c3f4987e37.jpg'),(4,'uploads/3fa4c753-0b38-40f4-80c8-b20e7357fc5c.jpg'),(11,'uploads/4bbc0ce6-000c-4a88-abbb-bc1556caf9d8.jpg'),(13,'uploads/5ca2f7be-e8bb-4aea-8594-9183688fdada.jpg'),(25,'uploads/5d18f486-02d4-4a2e-8033-e8e41b40f6a1.jpg'),(30,'uploads/5ffef738-d28f-4506-af04-13ce560d47b3.jpg'),(31,'uploads/67317e85-3260-4ec7-8274-f56db027b16f.jpg'),(2,'uploads/790c1f6c-4059-40e8-9e4b-25cb3c6429fd.jpg'),(19,'uploads/83425789-8edd-4d19-bfcd-2fa62205e9f5.jpg'),(23,'uploads/8b46a51a-de4c-419c-940d-88e133887e29.jpg'),(16,'uploads/9bf60423-3d92-439d-a763-f61fe75fe76b.jpg'),(6,'uploads/9c8218eb-442e-405e-b8f7-e96ef68dff97.jpg'),(9,'uploads/bab49103-3a2b-46ab-abe2-5943b8048364.jpg'),(14,'uploads/bc115909-bac6-4ebb-a821-26da4b9c94cf.jpg'),(36,'uploads/c58019f8-e1c4-4e64-a06b-bcc8073adfd8.jpg'),(8,'uploads/c82a6e90-d725-4849-8114-fa046d541107.jpg'),(27,'uploads/d14527d9-6433-428f-90f5-27dc917038f0.jpg'),(21,'uploads/d2da8187-04f7-4c1e-a406-e5fb92eae35f.jpg'),(5,'uploads/d8ad912a-743a-496f-bb6e-a043b6784782.jpg'),(28,'uploads/dccd93f4-7b65-420b-96af-8b1704bf1b7d.jpg'),(29,'uploads/e0b669e7-8d5c-4df8-9938-55ec50a120df.jpg'),(35,'uploads/e9cddc3a-6403-433f-b34b-f74c2e46194a.jpg'),(34,'uploads/ed1405fe-fa65-48de-8c79-99a66b948961.jpg'),(26,'uploads/ee29418f-4953-43ac-a13f-018f49ecafe3.jpg'),(22,'uploads/f5c6f32e-caf7-4fba-a4ec-5a04fa9ec890.jpg'),(32,'uploads/f9fe0f02-b92d-4d5c-b26a-5bd7f6311b8d.jpg'),(12,'uploads/fbe85ea3-5de5-4a52-ae14-edc1fdf1bb8a.jpg'),(24,'uploads/fda25984-d593-4bce-8b0c-569b892a8438.jpg');
/*!40000 ALTER TABLE `image` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_item`
--

DROP TABLE IF EXISTS `order_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_item` (
  `id` int NOT NULL AUTO_INCREMENT,
  `order_id` int DEFAULT NULL,
  `product_id` int DEFAULT NULL,
  `product_name` varchar(255) DEFAULT NULL,
  `product_price` decimal(10,2) DEFAULT NULL,
  `color_code` varchar(6) DEFAULT NULL,
  `size` varchar(10) DEFAULT NULL,
  `qty` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `order_id` (`order_id`),
  KEY `product_id` (`product_id`),
  KEY `color_code` (`color_code`),
  KEY `size` (`size`),
  CONSTRAINT `order_item_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `order_item_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `order_item_ibfk_3` FOREIGN KEY (`color_code`) REFERENCES `color` (`code`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `order_item_ibfk_4` FOREIGN KEY (`size`) REFERENCES `size` (`size`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `order_item_chk_1` CHECK ((`product_price` >= 0)),
  CONSTRAINT `order_item_chk_2` CHECK ((`qty` > 0))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_item`
--

LOCK TABLES `order_item` WRITE;
/*!40000 ALTER TABLE `order_item` DISABLE KEYS */;
/*!40000 ALTER TABLE `order_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int DEFAULT NULL,
  `prime` varchar(255) DEFAULT NULL,
  `shipping` varchar(50) DEFAULT NULL,
  `payment` varchar(50) DEFAULT NULL,
  `subtotal` decimal(10,2) DEFAULT NULL,
  `freight` decimal(10,2) DEFAULT NULL,
  `total` decimal(10,2) DEFAULT NULL,
  `recipient_id` int DEFAULT NULL,
  `payment_status` tinyint DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `recipient_id` (`recipient_id`),
  CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `orders_ibfk_2` FOREIGN KEY (`recipient_id`) REFERENCES `recipient` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `orders_chk_1` CHECK ((`subtotal` >= 0)),
  CONSTRAINT `orders_chk_2` CHECK ((`freight` >= 0)),
  CONSTRAINT `orders_chk_3` CHECK ((`total` >= 0)),
  CONSTRAINT `orders_chk_4` CHECK ((`payment_status` in (-(1),0,1)))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `id` int NOT NULL,
  `category` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `description` text,
  `price` decimal(10,2) DEFAULT NULL,
  `texture` varchar(255) DEFAULT NULL,
  `wash` varchar(255) DEFAULT NULL,
  `place` varchar(255) DEFAULT NULL,
  `note` text,
  `story` text,
  `main_image` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `product_chk_1` CHECK ((`price` >= 0))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,'men','優雅冬季經典外套','精緻的灰色粗花呢西裝外套',4200.00,'粗花呢面料','乾洗','義大利','下單後由國外進口，約5-7個工作天到貨','這款西裝外套的靈感來自冬季時尚的經典優雅，是任何衣櫥的永恆點睛之筆。它非常適合假日聚會或時尚的一天，將傳統工藝與現代設計相結合。','uploads/aa2ecf05-5620-4966-8068-56bc96ab05ae.jpg'),(2,'accessories','手工毛氈帽','時尚的棕色軟呢帽，搭配黑色飾帶，採用經典設計',2800.00,'光滑的毛氈面料','局部清潔','美國','手工染劑可能因摩擦而褪色','這款軟呢帽的靈感來自 20 世紀初時尚的永恆優雅，將復古魅力與現代風格相結合。無論您是參加正式活動還是享受休閒的一天，它都非常適合為您的衣櫥增添獨特的風格。','uploads/01936f85-d50b-4b92-b8a9-174358f2f736.jpg'),(3,'accessories','優質皮革手提包','別致優雅的淺粉色手提包，設計簡約。',3500.00,'真皮','避免使用刺激性化學品','義大利','這款手提包配有可調節肩帶','這款淺粉色手提包的靈感來自現代時尚潮流，為任何裝扮增添了一絲女性氣質和優雅氣質。它非常適合在城市中度過一天或休閒早午餐，將風格與功能相結合。','uploads/17605953-656c-489b-b54a-6051f4663b6c.jpg'),(4,'women','涼夏純棉襯衫','這款襯衫採用寬鬆版型，舒適且活動方便。',1200.00,'純棉','冷水機洗','日本','可拆式領口，兩種風格自由變換','這款淺藍色襯衫的靈感來源於晴朗夏日天空的寧靜和美麗。專為注重時尚和舒適感的現代女性設計，非常適合在陽光下度過一天或與朋友度過一個休閒的夜晚。','uploads/1b918450-65ee-47c0-ac2a-5ab5e8398641.jpg'),(5,'women','貝殼薄紗襯衫','胸前飾有獨特的貝殼裝飾',2000.00,'聚酯纖維','溫和冷水手洗','法國','反面清洗','受海邊優雅的啟發，這套服裝將舒適與風格相結合。襯衫上精緻的軟殼面料裝飾增添了一絲奇思妙想，使其成為任何衣櫥中的佼佼者。非常適合在海灘上度過一天或休閒的晚間聚會，這套套裝既百搭又別致。','uploads/09616037-6556-4d3f-9098-187019da79d7.jpg'),(6,'women','前開衩扭結洋裝','迷人的淺粉色連衣裙，飾有精緻的小鳥圖案',3200.00,'純棉','低溫烘乾或懸掛晾乾','西班牙','可能因長時間摩擦起毛球','這款連衣裙的靈感來自大自然的美麗和鳥兒飛翔的自由，捕捉了無憂無慮和快樂精神的精髓。非常適合在海邊度過陽光明媚的日子或在公園裡漫步，它結合了舒適與異想天開的設計，讓您毫不費力地感到美麗。','uploads/db188b90-8d30-4ce3-8a3c-88be583c25a2.jpg'),(7,'women','涼夏純棉襯衫','這款襯衫採用寬鬆版型，舒適且活動方便。',1200.00,'純棉','冷水機洗','日本','可拆式領口，兩種風格自由變換','這款淺藍色襯衫的靈感來源於晴朗夏日天空的寧靜和美麗。專為注重時尚和舒適感的現代女性設計，非常適合在陽光下度過一天或與朋友度過一個休閒的夜晚。','uploads/ac633edb-6c48-4c46-857a-0b9add8039e5.jpg'),(8,'women','涼夏純棉襯衫','這款襯衫採用寬鬆版型，舒適且活動方便。',1200.00,'純棉','冷水機洗','日本','可拆式領口，兩種風格自由變換','這款淺藍色襯衫的靈感來源於晴朗夏日天空的寧靜和美麗。專為注重時尚和舒適感的現代女性設計，非常適合在陽光下度過一天或與朋友度過一個休閒的夜晚。','uploads/4de2e9cf-7226-41fa-99b3-bb302de4def3.jpg'),(9,'women','涼夏純棉襯衫','這款襯衫採用寬鬆版型，舒適且活動方便。',1200.00,'純棉','冷水機洗','日本','可拆式領口，兩種風格自由變換','這款淺藍色襯衫的靈感來源於晴朗夏日天空的寧靜和美麗。專為注重時尚和舒適感的現代女性設計，非常適合在陽光下度過一天或與朋友度過一個休閒的夜晚。','uploads/415641d0-5fdb-4f01-a502-fb21a0a09421.jpg'),(10,'men','優雅冬季經典外套','精緻的灰色粗花呢西裝外套',4200.00,'粗花呢面料','乾洗','義大利','下單後由國外進口，約5-7個工作天到貨','這款西裝外套的靈感來自冬季時尚的經典優雅，是任何衣櫥的永恆點睛之筆。它非常適合假日聚會或時尚的一天，將傳統工藝與現代設計相結合。','uploads/7c937b4a-520c-4b34-bb62-f74cc5c6ff21.jpg'),(11,'men','優雅冬季經典外套','精緻的灰色粗花呢西裝外套',4200.00,'粗花呢面料','乾洗','義大利','下單後由國外進口，約5-7個工作天到貨','這款西裝外套的靈感來自冬季時尚的經典優雅，是任何衣櫥的永恆點睛之筆。它非常適合假日聚會或時尚的一天，將傳統工藝與現代設計相結合。','uploads/4839ca8d-37a8-4653-8df4-b2731a3d0e8f.jpg'),(12,'men','優雅冬季經典外套','精緻的灰色粗花呢西裝外套',4200.00,'粗花呢面料','乾洗','義大利','下單後由國外進口，約5-7個工作天到貨','這款西裝外套的靈感來自冬季時尚的經典優雅，是任何衣櫥的永恆點睛之筆。它非常適合假日聚會或時尚的一天，將傳統工藝與現代設計相結合。','uploads/6a0018cd-7a19-4c87-a74d-23da6ca412e4.jpg'),(13,'men','優雅冬季經典外套','精緻的灰色粗花呢西裝外套',4200.00,'粗花呢面料','乾洗','義大利','下單後由國外進口，約5-7個工作天到貨','這款西裝外套的靈感來自冬季時尚的經典優雅，是任何衣櫥的永恆點睛之筆。它非常適合假日聚會或時尚的一天，將傳統工藝與現代設計相結合。','uploads/c079dae3-1833-4ce2-a736-dcc392fd4c0f.jpg'),(14,'men','優雅冬季經典外套','精緻的灰色粗花呢西裝外套',4200.00,'粗花呢面料','乾洗','義大利','下單後由國外進口，約5-7個工作天到貨','這款西裝外套的靈感來自冬季時尚的經典優雅，是任何衣櫥的永恆點睛之筆。它非常適合假日聚會或時尚的一天，將傳統工藝與現代設計相結合。','uploads/5964bdd4-6a07-4ae2-9991-8496eccbe6b4.jpg'),(15,'accessories','優質皮革手提包','別致優雅的淺粉色手提包，設計簡約。',3500.00,'真皮','避免使用刺激性化學品','義大利','這款手提包配有可調節肩帶','這款淺粉色手提包的靈感來自現代時尚潮流，為任何裝扮增添了一絲女性氣質和優雅氣質。它非常適合在城市中度過一天或休閒早午餐，將風格與功能相結合。','uploads/c6508a84-86d8-4fa3-b7f3-2f5034dbb22b.jpg'),(16,'accessories','優質皮革手提包','別致優雅的淺粉色手提包，設計簡約。',3500.00,'真皮','避免使用刺激性化學品','義大利','這款手提包配有可調節肩帶','這款淺粉色手提包的靈感來自現代時尚潮流，為任何裝扮增添了一絲女性氣質和優雅氣質。它非常適合在城市中度過一天或休閒早午餐，將風格與功能相結合。','uploads/b88a9c96-7d42-4c53-9c1a-154fad6166e2.jpg'),(17,'accessories','優質皮革手提包','別致優雅的淺粉色手提包，設計簡約。',3500.00,'真皮','避免使用刺激性化學品','義大利','這款手提包配有可調節肩帶','這款淺粉色手提包的靈感來自現代時尚潮流，為任何裝扮增添了一絲女性氣質和優雅氣質。它非常適合在城市中度過一天或休閒早午餐，將風格與功能相結合。','uploads/f7e13043-104f-47c7-809c-59792c200dc7.jpg'),(18,'accessories','優質皮革手提包','別致優雅的淺粉色手提包，設計簡約。',3500.00,'真皮','避免使用刺激性化學品','義大利','這款手提包配有可調節肩帶','這款淺粉色手提包的靈感來自現代時尚潮流，為任何裝扮增添了一絲女性氣質和優雅氣質。它非常適合在城市中度過一天或休閒早午餐，將風格與功能相結合。','uploads/4cd53950-d44b-48e4-9f31-be97c858e1ef.jpg');
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_color`
--

DROP TABLE IF EXISTS `product_color`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_color` (
  `id` int NOT NULL AUTO_INCREMENT,
  `product_id` int DEFAULT NULL,
  `color_code` varchar(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `product_id` (`product_id`),
  KEY `color_code` (`color_code`),
  CONSTRAINT `product_color_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `product_color_ibfk_2` FOREIGN KEY (`color_code`) REFERENCES `color` (`code`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=86 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_color`
--

LOCK TABLES `product_color` WRITE;
/*!40000 ALTER TABLE `product_color` DISABLE KEYS */;
INSERT INTO `product_color` VALUES (1,1,'6C6F70'),(2,1,'6C6F70'),(3,1,'8B0000'),(4,1,'8B0000'),(5,1,'8B0000'),(6,2,'8B4513'),(7,2,'8B4513'),(8,2,'8B0000'),(9,2,'8B0000'),(10,2,'00008B'),(11,3,'8B4513'),(12,3,'8B4513'),(13,3,'FFC0CB'),(14,3,'FFC0CB'),(15,4,'8B4513'),(16,4,'8B4513'),(17,4,'FFC0CB'),(18,4,'FFC0CB'),(19,4,'ADD8E6'),(20,5,'6C6F70'),(21,5,'6C6F70'),(22,5,'FFC0CB'),(23,5,'FFC0CB'),(24,5,'ADD8E6'),(25,6,'8B0000'),(26,6,'8B0000'),(27,6,'FFC0CB'),(28,6,'FFC0CB'),(29,6,'ADD8E6'),(30,7,'8B4513'),(31,7,'8B4513'),(32,7,'FFC0CB'),(33,7,'FFC0CB'),(34,7,'ADD8E6'),(35,8,'8B4513'),(36,8,'8B4513'),(37,8,'FFC0CB'),(38,8,'FFC0CB'),(39,8,'ADD8E6'),(40,9,'8B4513'),(41,9,'8B4513'),(42,9,'FFC0CB'),(43,9,'FFC0CB'),(44,9,'ADD8E6'),(45,10,'6C6F70'),(46,10,'6C6F70'),(47,10,'8B0000'),(48,10,'8B0000'),(49,10,'8B0000'),(50,11,'6C6F70'),(51,11,'6C6F70'),(52,11,'8B0000'),(53,11,'8B0000'),(54,11,'8B0000'),(55,12,'6C6F70'),(56,12,'6C6F70'),(57,12,'8B0000'),(58,12,'8B0000'),(59,12,'8B0000'),(60,13,'6C6F70'),(61,13,'6C6F70'),(62,13,'8B0000'),(63,13,'8B0000'),(64,13,'8B0000'),(65,14,'6C6F70'),(66,14,'6C6F70'),(67,14,'8B0000'),(68,14,'8B0000'),(69,14,'8B0000'),(70,15,'8B4513'),(71,15,'8B4513'),(72,15,'FFC0CB'),(73,15,'FFC0CB'),(74,16,'8B4513'),(75,16,'8B4513'),(76,16,'FFC0CB'),(77,16,'FFC0CB'),(78,17,'8B4513'),(79,17,'8B4513'),(80,17,'FFC0CB'),(81,17,'FFC0CB'),(82,18,'8B4513'),(83,18,'8B4513'),(84,18,'FFC0CB'),(85,18,'FFC0CB');
/*!40000 ALTER TABLE `product_color` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_image`
--

DROP TABLE IF EXISTS `product_image`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_image` (
  `id` int NOT NULL AUTO_INCREMENT,
  `product_id` int DEFAULT NULL,
  `image_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `product_id` (`product_id`),
  KEY `image_id` (`image_id`),
  CONSTRAINT `product_image_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `product_image_ibfk_2` FOREIGN KEY (`image_id`) REFERENCES `image` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_image`
--

LOCK TABLES `product_image` WRITE;
/*!40000 ALTER TABLE `product_image` DISABLE KEYS */;
INSERT INTO `product_image` VALUES (1,1,1),(2,1,2),(3,2,3),(4,2,4),(5,3,5),(6,3,6),(7,4,7),(8,4,8),(9,5,9),(10,5,10),(11,6,11),(12,6,12),(13,7,13),(14,7,14),(15,8,15),(16,8,16),(17,9,17),(18,9,18),(19,10,19),(20,10,20),(21,11,21),(22,11,22),(23,12,23),(24,12,24),(25,13,25),(26,13,26),(27,14,27),(28,14,28),(29,15,29),(30,15,30),(31,16,31),(32,16,32),(33,17,33),(34,17,34),(35,18,35),(36,18,36);
/*!40000 ALTER TABLE `product_image` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_size`
--

DROP TABLE IF EXISTS `product_size`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_size` (
  `id` int NOT NULL AUTO_INCREMENT,
  `product_id` int DEFAULT NULL,
  `size` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `product_id` (`product_id`),
  KEY `size` (`size`),
  CONSTRAINT `product_size_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `product_size_ibfk_2` FOREIGN KEY (`size`) REFERENCES `size` (`size`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_size`
--

LOCK TABLES `product_size` WRITE;
/*!40000 ALTER TABLE `product_size` DISABLE KEYS */;
INSERT INTO `product_size` VALUES (1,1,'S'),(2,1,'M'),(3,1,'L'),(4,2,'S'),(5,2,'M'),(6,3,'S'),(7,3,'M'),(8,4,'S'),(9,4,'M'),(10,5,'S'),(11,5,'M'),(12,6,'S'),(13,6,'M'),(14,7,'S'),(15,7,'M'),(16,8,'S'),(17,8,'M'),(18,9,'S'),(19,9,'M'),(20,10,'S'),(21,10,'M'),(22,10,'L'),(23,11,'S'),(24,11,'M'),(25,11,'L'),(26,12,'S'),(27,12,'M'),(28,12,'L'),(29,13,'S'),(30,13,'M'),(31,13,'L'),(32,14,'S'),(33,14,'M'),(34,14,'L'),(35,15,'S'),(36,15,'M'),(37,16,'S'),(38,16,'M'),(39,17,'S'),(40,17,'M'),(41,18,'S'),(42,18,'M');
/*!40000 ALTER TABLE `product_size` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `recipient`
--

DROP TABLE IF EXISTS `recipient`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `recipient` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `address` text,
  `time` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `recipient`
--

LOCK TABLES `recipient` WRITE;
/*!40000 ALTER TABLE `recipient` DISABLE KEYS */;
/*!40000 ALTER TABLE `recipient` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `size`
--

DROP TABLE IF EXISTS `size`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `size` (
  `size` varchar(10) NOT NULL,
  PRIMARY KEY (`size`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `size`
--

LOCK TABLES `size` WRITE;
/*!40000 ALTER TABLE `size` DISABLE KEYS */;
INSERT INTO `size` VALUES ('L'),('M'),('S');
/*!40000 ALTER TABLE `size` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `provider` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `password_hash` varchar(255) DEFAULT NULL,
  `picture` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `variant`
--

DROP TABLE IF EXISTS `variant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `variant` (
  `id` int NOT NULL AUTO_INCREMENT,
  `product_id` int DEFAULT NULL,
  `color_code` varchar(6) DEFAULT NULL,
  `size` varchar(10) DEFAULT NULL,
  `stock` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `product_id` (`product_id`),
  KEY `color_code` (`color_code`),
  KEY `size` (`size`),
  CONSTRAINT `variant_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `variant_ibfk_2` FOREIGN KEY (`color_code`) REFERENCES `color` (`code`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `variant_ibfk_3` FOREIGN KEY (`size`) REFERENCES `size` (`size`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `variant_chk_1` CHECK ((`stock` >= 0))
) ENGINE=InnoDB AUTO_INCREMENT=86 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `variant`
--

LOCK TABLES `variant` WRITE;
/*!40000 ALTER TABLE `variant` DISABLE KEYS */;
INSERT INTO `variant` VALUES (1,1,'6C6F70','S',5),(2,1,'6C6F70','M',10),(3,1,'8B0000','S',10),(4,1,'8B0000','M',8),(5,1,'8B0000','L',8),(6,2,'8B4513','S',5),(7,2,'8B4513','M',10),(8,2,'8B0000','S',10),(9,2,'8B0000','M',8),(10,2,'00008B','M',8),(11,3,'8B4513','S',5),(12,3,'8B4513','M',10),(13,3,'FFC0CB','S',10),(14,3,'FFC0CB','M',8),(15,4,'8B4513','S',5),(16,4,'8B4513','M',10),(17,4,'FFC0CB','S',10),(18,4,'FFC0CB','M',8),(19,4,'ADD8E6','M',7),(20,5,'6C6F70','S',5),(21,5,'6C6F70','M',10),(22,5,'FFC0CB','S',10),(23,5,'FFC0CB','M',8),(24,5,'ADD8E6','M',7),(25,6,'8B0000','S',5),(26,6,'8B0000','M',10),(27,6,'FFC0CB','S',10),(28,6,'FFC0CB','M',8),(29,6,'ADD8E6','M',7),(30,7,'8B4513','S',5),(31,7,'8B4513','M',10),(32,7,'FFC0CB','S',10),(33,7,'FFC0CB','M',8),(34,7,'ADD8E6','M',7),(35,8,'8B4513','S',5),(36,8,'8B4513','M',10),(37,8,'FFC0CB','S',10),(38,8,'FFC0CB','M',8),(39,8,'ADD8E6','M',7),(40,9,'8B4513','S',5),(41,9,'8B4513','M',10),(42,9,'FFC0CB','S',10),(43,9,'FFC0CB','M',8),(44,9,'ADD8E6','M',7),(45,10,'6C6F70','S',5),(46,10,'6C6F70','M',10),(47,10,'8B0000','S',10),(48,10,'8B0000','M',7),(49,10,'8B0000','L',8),(50,11,'6C6F70','S',5),(51,11,'6C6F70','M',10),(52,11,'8B0000','S',10),(53,11,'8B0000','M',7),(54,11,'8B0000','L',8),(55,12,'6C6F70','S',5),(56,12,'6C6F70','M',10),(57,12,'8B0000','S',10),(58,12,'8B0000','M',7),(59,12,'8B0000','L',8),(60,13,'6C6F70','S',5),(61,13,'6C6F70','M',10),(62,13,'8B0000','S',10),(63,13,'8B0000','M',7),(64,13,'8B0000','L',8),(65,14,'6C6F70','S',5),(66,14,'6C6F70','M',10),(67,14,'8B0000','S',10),(68,14,'8B0000','M',7),(69,14,'8B0000','L',8),(70,15,'8B4513','S',5),(71,15,'8B4513','M',10),(72,15,'FFC0CB','S',10),(73,15,'FFC0CB','M',8),(74,16,'8B4513','S',5),(75,16,'8B4513','M',10),(76,16,'FFC0CB','S',10),(77,16,'FFC0CB','M',8),(78,17,'8B4513','S',5),(79,17,'8B4513','M',10),(80,17,'FFC0CB','S',10),(81,17,'FFC0CB','M',8),(82,18,'8B4513','S',5),(83,18,'8B4513','M',10),(84,18,'FFC0CB','S',10),(85,18,'FFC0CB','M',8);
/*!40000 ALTER TABLE `variant` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-08-07 16:15:59
