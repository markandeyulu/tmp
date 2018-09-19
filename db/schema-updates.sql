ALTER TABLE REQUIREMENT MODIFY COLUMN ID varchar(20);

ALTER TABLE requirement_profile_mapping MODIFY COLUMN REQUIREMENT_ID varchar(20);

ALTER TABLE profile MODIFY COLUMN REQUIREMENT_ID varchar(20);


CREATE TABLE `account_master` (
  `ACCOUNT_ID` int(11) NOT NULL AUTO_INCREMENT,
  `ACCOUNT_NAME` varchar(255) NOT NULL,
   PRIMARY KEY (`ACCOUNT_ID`));

drop Table projects;
 
CREATE TABLE `projects` (
  `PROJECT_ID` int(11) NOT NULL,
  `PROJECT_NAME` varchar(255) NOT NULL,
  `ACC_ID` int(4),
   PRIMARY KEY (`PROJECT_ID`));
       
INSERT INTO `account_master` VALUES (1,'Ford'),(2,'Ford Direct'),(3,'Mazda'),(4,'TRW'),(5,'Rivian');
   
INSERT INTO `projects` VALUES (1,'OWS',1),(2,'GPIRS',1),(3,'HROGC',1),(4,'NVO',2),(5,'NGP',2),(6,'AEM',2),
   (7,'Mazda NA',3),(8,'Mazda US',3),(9,'Mazda UK',3),(10,'EPort',3),(11,'ETS',3),(12,'TRW UK',4),(13,'TRW US',4),
    (14,'SAP',5);
   
UPDATE tmp.admin_info_value
SET VALUE = 'HROGC'
WHERE ID = 48;

UPDATE tmp.admin_info_value
SET VALUE = 'AEM'
WHERE ID = 51;

UPDATE tmp.admin_info_value
SET VALUE = 'Mazda NA'
WHERE ID = 49;

UPDATE tmp.admin_info_value
SET VALUE = 'Mazda US'
WHERE ID = 53;

UPDATE tmp.admin_info_value
SET VALUE = 'Mazda UK'
WHERE ID = 55;

UPDATE tmp.admin_info_value
SET VALUE = 'ETS'
WHERE ID = 24;

UPDATE tmp.admin_info_value
SET VALUE = 'TRW UK'
WHERE ID = 54;

UPDATE tmp.admin_info_value
SET VALUE = 'TRW US'
WHERE ID = 57;

INSERT INTO `user` VALUES 
(4,'Siva Subramanian','Siva.Subramanian@TechMahindra.com','8978453939','Wel@123','2018-08-22 13:53:48',1,'0000-00-00 00:00:00',NULL),
(5,'Ramkrishna Rao','Ramkrishna.Rao@TechMahindra.com','9940125401','Wel@123','2018-08-23 07:01:03',2,'0000-00-00 00:00:00',NULL),
(6,'Kannan Kasthurirajan','KK00539399@TechMahindra.com','9898989898','Wel@123','2018-08-22 13:53:48',2,'0000-00-00 00:00:00',NULL),
(7,'Sukumar Natesan','Sukumar.Natesan@TechMahindra.com','9998887777','Wel@123','2018-08-22 13:53:48',2,'0000-00-00 00:00:00',NULL);


INSERT INTO `user_role_mapping` VALUES 
(4,4,2,'2018-04-18 18:30:00',4,'0000-00-00 00:00:00',NULL),
(5,5,3,'2018-06-28 18:30:00',5,'0000-00-00 00:00:00',NULL),
(6,6,4,'2018-06-28 18:30:00',6,'0000-00-00 00:00:00',NULL),
(7,7,2,'2018-06-28 18:30:00',7,'0000-00-00 00:00:00',NULL);
   
   
INSERT INTO `user_role_account_mapping` VALUES
		(11,7,1,'2018-05-11 18:30:00',1,'0000-00-00 00:00:00',NULL),
		(12,7,2,'2018-05-11 18:30:00',1,'0000-00-00 00:00:00',NULL),
		(13,7,3,'2018-05-11 18:30:00',1,'0000-00-00 00:00:00',NULL),
		(14,7,4,'2018-05-11 18:30:00',1,'0000-00-00 00:00:00',NULL),
		(15,4,1,'2018-07-06 10:35:04',2,'0000-00-00 00:00:00',NULL),
		(16,4,2,'2018-06-28 18:30:00',2,'0000-00-00 00:00:00',NULL),
		(17,4,3,'2018-06-28 18:30:00',3,'0000-00-00 00:00:00',NULL),
		(18,4,4,'2018-08-19 18:30:00',1,'0000-00-00 00:00:00',NULL),
		(19,6,2,'2018-08-19 18:30:00',1,'0000-00-00 00:00:00',NULL),
		(20,6,3,'2018-08-19 18:30:00',1,'0000-00-00 00:00:00',NULL),
		(21,5,1,'2018-05-11 18:30:00',1,'0000-00-00 00:00:00',NULL),
		(22,5,2,'2018-05-11 18:30:00',1,'0000-00-00 00:00:00',NULL),
		(23,5,3,'2018-05-11 18:30:00',1,'0000-00-00 00:00:00',NULL),
		(24,5,4,'2018-05-11 18:30:00',1,'0000-00-00 00:00:00',NULL);