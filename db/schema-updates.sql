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
       
INSERT INTO `account_master` VALUES (1,'Ford'),(2,'Ford Direct'),(3,'Mazda'),(4,'TRW');
   
INSERT INTO `projects` VALUES (1,'OWS',1),(2,'GPIRS',1),(3,'HROGC',1),(4,'NVO',2),(5,'NGP',2),(6,'AEM',2),
   (7,'Mazda NA',3),(8,'Mazda US',3),(9,'Mazda UK',3),(10,'EPort',3),(11,'ETS',3),(12,'TRW UK',4),(13,'TRW US',4);
   
   
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


   
   
   