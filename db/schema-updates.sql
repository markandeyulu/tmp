use tmp; 

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

ALTER TABLE user
ADD DISPLAY_NAME varchar(30);


UPDATE user
SET DISPLAY_NAME = 'Sangeetha'
WHERE NAME = 'Sangeetha Selvakumar' AND EMAIL='ss00474123@techmahindra.com';

UPDATE user
SET DISPLAY_NAME = 'Agalya'
WHERE NAME = 'Agalya Sivasubramanian' AND EMAIL='as00490953@TechMahindra.com';

UPDATE user
SET DISPLAY_NAME = 'Saranya'
WHERE NAME = 'Saranya Palanisamy' AND EMAIL='ss00425154@Techmahindra.com';

UPDATE user
SET DISPLAY_NAME = 'Siva'
WHERE NAME = 'Siva Subramanian' AND EMAIL='Siva.Subramanian@TechMahindra.com';

UPDATE user
SET DISPLAY_NAME = 'Ram'
WHERE NAME = 'Ramkrishna Rao' AND EMAIL='Ramkrishna.Rao@TechMahindra.com';

UPDATE user
SET DISPLAY_NAME = 'Kannan'
WHERE NAME = 'Kannan Kasthurirajan' AND EMAIL='KK00539399@TechMahindra.com';

UPDATE user
SET DISPLAY_NAME = 'Sukumar'
WHERE NAME = 'Sukumar Natesan' AND EMAIL='Sukumar.Natesan@TechMahindra.com';

UPDATE user
SET NAME = 'Sangeetha', DISPLAY_NAME='Sangeetha Selvakumar'
WHERE EMAIL='ss00474123@techmahindra.com';

UPDATE user
SET NAME = 'Agalya', DISPLAY_NAME='Agalya Sivasubramanian'
WHERE EMAIL='as00490953@techmahindra.com';

UPDATE user
SET NAME = 'Saranya', DISPLAY_NAME='Saranya Palanisamy'
WHERE EMAIL='ss00425154@Techmahindra.com';

UPDATE user
SET NAME = 'Siva', DISPLAY_NAME='Siva Subramanian'
WHERE EMAIL='Siva.Subramanian@TechMahindra.com';

UPDATE user
SET NAME = 'Ram', DISPLAY_NAME='Ramkrishna Rao'
WHERE EMAIL='Ramkrishna.Rao@TechMahindra.com';

UPDATE user
SET NAME = 'Kannan', DISPLAY_NAME='Kannan Kasthurirajan'
WHERE EMAIL='KK00539399@TechMahindra.com';

UPDATE user
SET NAME = 'Sukumar', DISPLAY_NAME='Sukumar Natesan'
WHERE EMAIL='Sukumar.Natesan@TechMahindra.com';


update projects 
set PROJECT_NAME='Eport' Where PROJECT_ID='5';

CREATE TABLE `user_account_mapping` (
	`ID` int(11) NOT NULL AUTO_INCREMENT,
	`USER_ID` int(11) NOT NULL,
  `ACCOUNT_ID` int(11) NOT NULL,
    PRIMARY KEY (`ID`));
	
ALTER TABLE user
ADD ROLE int(2);

truncate TABLE account_master; 

truncate TABLE projects; 

ALTER TABLE `tmp`.`projects` 
CHANGE COLUMN `PROJECT_ID` `PROJECT_ID` INT(11) NOT NULL AUTO_INCREMENT ;

INSERT INTO `account_master` (ACCOUNT_NAME) VALUES ('Bosch'),('Daimler AG'),('Fiat Chrysler Automobiles'),('Ford Motor Company'),('FordDirect.com'),
('INTERNAL'),('Inteva Products, LLC'),('Johnson Controls'),('JTEKT Corporation'),('Mahindra & Mahindra'),('Mazda Motor Corporation'),('Navistar'),('Rehau Polymers Private Limited'),
('Rivian Automotive'),('TRW Automotive');

INSERT INTO `projects` (PROJECT_NAME,ACC_ID) VALUES ('Bosch Limited,IND',1),
   ('BOSCH Ltd',1),
   ('Mercedes Benz US LLC Project',2),
   ('FCA Assessment- POC',3),
   ('AM Support IAUT',4),
   ('Ford Extranet - Offshore',4),
   ('Ford Extranet NA',4),
   ('Ford GESB',4),
   ('Ford GPIRS Phase II - CR',4),
   ('FORD GPIRS Tech Refresh',4),
   ('Ford GVDS EU',4),
   ('Ford IMS T&M_Onsite',4),
   ('Ford Kronos',4),
   ('Ford Motors Pvt Ltd Chennai',4),
   ('Ford Motors Pvt Ltd Gurgaon',4),
   ('Ford OWS EU 3.1 Phase II - PR',4),
    ('Ford OWS EU 3.1 Phase II- CR',4),
    ('Ford OWS_GESB AM Support',4),
    ('Ford Professional Services 200',4),
    ('Ford_Extranet_FMCL_ITI_F_C',4),
    ('Ford_HR OGC AD_AM',4),
    ('Ford_Onsite_T&M',4),
    ('Ford_Onsite_T&M_Emerging',4),
    ('Ford_Onsite_T&M_Testing',4),
    ('GSEC PIVPN Tunnel Mirgation',4),
    ('HROGC Enhancements-FP',4),
    ('HROGC FMMS TechRefresh',4),
    ('Maximo fleet Management System',4),
    ('OWS GC-CR',4),
    ('OWS Pega 7,X Upgrade - WF 1288',4),
    ('OWS-SA-CR',4),
    ('Panda Tech Refresh',4),
    ('Ford Direct',5),
    ('Building Warranty Framework',6),
    ('IBU Automotive BUFF - MGMT',6),
    ('IBU AUTOMOTIVE BUFFER',6),
    ('IBU AUTOMOTIVE PIPELINE',6),
    ('Inteva - IS Support',7),
    ('Adient US LLC',8),
    ('JTEKT - North America',9),
    ('JTEKT - North America - 2',9),
    ('Mahindra North America Technia',10),
    ('Mazda - IS Support Projects',11),
    ('Mazda_Development Projects 201',11),
    ('Mazda_Support Projects_B_C',11),
    ('Mazda-BusinessSupport Projects',11),
    ('Mazda-BusinessSupport Projects',11),
    ('Navistar:BSA',12),
    ('Navistar-Mobile App Dev & Supp',12),
    ('Navistar-PSFSCM_Upgrade',12),
    ('Navistar-Telematics Device Tes',12),
    ('REHAU AMS Support Srv',13),
    ('REHAU SAP Hybris-AMS Support',13),
    ('RIVIAN S4 HANA Implementation',14),
    ('QAD Monitoring Project',15),
    ('TRW IMS Support Services',15),
    ('TRW MES Application Support',15),
    ('TRW SAP ABAP Support Serv',15),
    ('TRW-IMS-GDC Staffing Agreement',15),
    ('TRW-IS EDI Offshore Support',15),
    ('TRW-IS EDI Onsite Support - AU',15),
    ('TRW-IS- MAD Offshore Support',15),
    ('TRW-IS- Windchill & PDM Link S',15),
    ('TRW-IS-CAS-Support2',15),
    ('TRW-IS-Gifco Onsite & Offshore',15),
    ('TRW-IS-HRIT Security Admin Sup',15),
    ('TRW-IS-INDIRECT SPEND',15),
    ('TRW-IS-Indirect Spend Managed',15),
    ('TRW-IS-MSBI-ManagedServices-Su',15),
    ('TRW-IS-Onsite Offshore Support',15),
    ('TRW-IS-QADGifco Onsite support',15),
    ('TRW-IS-SAP Managed Services',15),
    ('TRW-IS-SAP ONS & OFF support',15),
    ('TRW-IS-SAP-Support',15),
    ('TRW-IS-WEB Aftermarket Support',15),
    ('TRW-IS-Web-PREPS-Maintenance',15),
    ('ZF IT Domain License Services',15),
    ('ZF MICS Development Project',15);
   

UPDATE user
SET ROLE = '1'
WHERE NAME = 'Sangeetha' AND EMAIL='ss00474123@techmahindra.com';

UPDATE user
SET ROLE = '1'
WHERE NAME = 'Agalya' AND EMAIL='as00490953@TechMahindra.com';

UPDATE user
SET ROLE = '1'
WHERE NAME = 'Saranya' AND EMAIL='ss00425154@Techmahindra.com';

UPDATE user
SET ROLE = '1'
WHERE NAME = 'Siva' AND EMAIL='Siva.Subramanian@TechMahindra.com';

UPDATE user
SET ROLE = '2'
WHERE NAME = 'Ram' AND EMAIL='Ramkrishna.Rao@TechMahindra.com';

UPDATE user
SET ROLE = '3'
WHERE NAME = 'Kannan' AND EMAIL='KK00539399@TechMahindra.com';

UPDATE user
SET ROLE = '4'
WHERE NAME = 'Sukumar' AND EMAIL='Sukumar.Natesan@TechMahindra.com';

INSERT INTO `user_account_mapping` (USER_ID,ACCOUNT_ID) VALUES (4,1),(4,2),(4,3),(4,4),(4,5),(4,6),(4,7),(4,8),(4,9),(4,10),(4,11),(4,12),(4,13),(4,14),(4,15);
INSERT INTO `user_account_mapping` (USER_ID,ACCOUNT_ID) VALUES (5,1),(5,2),(5,3),(5,4),(5,5),(5,6),(5,7),(5,8),(5,9),(5,10),(5,11),(5,12),(5,13),(5,14),(5,15);
INSERT INTO `user_account_mapping` (USER_ID,ACCOUNT_ID) VALUES (6,5),(6,6),(6,11);
INSERT INTO `user_account_mapping` (USER_ID,ACCOUNT_ID) VALUES (7,5);

ALTER TABLE requirement
  DROP FOREIGN KEY REQUIREMENT_PROJECT_MAPPING;
  
ALTER TABLE requirement
  DROP FOREIGN KEY REQUIREMENT_ACCOUNT_MAPPING;
  
ALTER TABLE profile
  DROP FOREIGN KEY PROFILE_ACCOUNT_MAPPING;
  
ALTER TABLE profile
  DROP FOREIGN KEY PROFILE_PROJECT_MAPPING;
 
ALTER TABLE profile_resume
  DROP FOREIGN KEY PROFILE_RESUME_PROFILE;
  
  
  ALTER TABLE profile
  DROP FOREIGN KEY PROFILE_REQUIREMENT_ID;
  
/* USED only for truncating requirements and profiles tables

truncate table profile_resume;
drop table requirement_profile_mapping;
truncate table profile;
truncate table requirement;

CREATE TABLE `requirement_profile_mapping` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `REQUIREMENT_ID` varchar(14) NOT NULL,
  `PROFILE_ID` int(11) NOT NULL,
  `INTERNAL_EVALUATION_RESULT` int(11) DEFAULT NULL,
  `CUSTOMER_INTERVIEW_STATUS` int(11) DEFAULT NULL,
  `REMARKS` varchar(500) DEFAULT NULL,
  `CREATED_ON` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATED_BY` int(11) NOT NULL,
  `UPDATED_ON` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `UPDATED_BY` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `REQUIREMENT_CUSTOMER` (`CUSTOMER_INTERVIEW_STATUS`),
  KEY `REQUIREMENT_INTERNAL` (`INTERNAL_EVALUATION_RESULT`),
  KEY `REQUIREMENT_PROFILE_MAPPING_PROFILE` (`PROFILE_ID`),
  KEY `REQUIREMENT_PROFILE_MAPPING_REQUIREMENT` (`REQUIREMENT_ID`),
  KEY `REQUIREMENT_PROFILE_MAPPING_USER_CREATE` (`CREATED_BY`),
  KEY `REQUIREMENT_PROFILE_MAPPING_USER_UPDATE` (`UPDATED_BY`),
  CONSTRAINT `REQUIREMENT_CUSTOMER` FOREIGN KEY (`CUSTOMER_INTERVIEW_STATUS`) REFERENCES `config_key_value_mapping` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `REQUIREMENT_INTERNAL` FOREIGN KEY (`INTERNAL_EVALUATION_RESULT`) REFERENCES `config_key_value_mapping` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `REQUIREMENT_PROFILE_MAPPING_PROFILE` FOREIGN KEY (`PROFILE_ID`) REFERENCES `profile` (`ID`),
  CONSTRAINT `REQUIREMENT_PROFILE_MAPPING_REQUIREMENT` FOREIGN KEY (`REQUIREMENT_ID`) REFERENCES `requirement` (`ID`),
  CONSTRAINT `REQUIREMENT_PROFILE_MAPPING_USER_CREATE` FOREIGN KEY (`CREATED_BY`) REFERENCES `user` (`ID`),
  CONSTRAINT `REQUIREMENT_PROFILE_MAPPING_USER_UPDATE` FOREIGN KEY (`UPDATED_BY`) REFERENCES `user` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=279 DEFAULT CHARSET=utf8;*/
  
 ALTER TABLE user_role_account_mapping
  DROP FOREIGN KEY user_role_account_mapping_ACCOUNT;
  
ALTER TABLE user_role_account_mapping
  DROP FOREIGN KEY user_role_account_mapping_CREATE;
 
ALTER TABLE user_role_account_mapping
  DROP FOREIGN KEY user_role_account_mapping_UPDATE;
 
ALTER TABLE user_role_account_mapping
  DROP FOREIGN KEY user_role_account_mapping_USER_ROLE;

DROP TABLE user_role_account_mapping;

ALTER TABLE user_page_permission
  DROP FOREIGN KEY USER_PAGE_PERMISSION_PAGE;
  
ALTER TABLE user_page_permission
  DROP FOREIGN KEY USER_PAGE_PERMISSION_USER_CREATE;
 
ALTER TABLE user_page_permission
  DROP FOREIGN KEY USER_PAGE_PERMISSION_USER_ROLE_MAPPING;
 
ALTER TABLE user_page_permission
  DROP FOREIGN KEY USER_PAGE_PERMISSION_USER_UPDATE;

DROP TABLE user_page_permission;

ALTER TABLE account_mapping
  DROP FOREIGN KEY ACCOUNT_MAPPING_ACCOUNT;
  
ALTER TABLE account_mapping
  DROP FOREIGN KEY ACCOUNT_MAPPING_IBU;
 
ALTER TABLE account_mapping
  DROP FOREIGN KEY ACCOUNT_MAPPING_ORG;
 
ALTER TABLE account_mapping
  DROP FOREIGN KEY ACCOUNT_MAPPING_USER_CREATE;
 
ALTER TABLE account_mapping
  DROP FOREIGN KEY ACCOUNT_MAPPING_USER_UPDATE;

DROP TABLE account_mapping;


ALTER TABLE project_mapping
  DROP FOREIGN KEY PROJECT_MAPPING_ACCOUNT_MAPPING;
  
ALTER TABLE project_mapping
  DROP FOREIGN KEY PROJECT_MAPPING_ADMIN_INFO_KEY_VALUE_MAPPING;
 
ALTER TABLE project_mapping
  DROP FOREIGN KEY PROJECT_MAPPING_USER_CREATE;
 
ALTER TABLE project_mapping
  DROP FOREIGN KEY PROJECT_MAPPING_USER_UPDATE;

DROP TABLE project_mapping;


ALTER TABLE profile_resume
  DROP FOREIGN KEY PROFILE_RESUME_USER_CREATE;
 
ALTER TABLE profile_resume
  DROP FOREIGN KEY PROFILE_RESUME_USER_UPDATE;

DROP TABLE profile_resume;

ALTER TABLE user_role_mapping
  DROP FOREIGN KEY USER_ROLE_MAPPING_ROLE;
  
ALTER TABLE user_role_mapping
  DROP FOREIGN KEY USER_ROLE_MAPPING_USER;
 
ALTER TABLE user_role_mapping
  DROP FOREIGN KEY USER_ROLE_MAPPING_USER_CREATE;
 
ALTER TABLE user_role_mapping
  DROP FOREIGN KEY USER_ROLE_MAPPING_USER_UPDATE;
  
DROP TABLE user_role_mapping;


ALTER TABLE profile
DROP COLUMN INTERNAL_EVALUATION_RESULT_DATE;

ALTER TABLE profile
DROP COLUMN INITIAL_EVALUATION_RESULT;

ALTER TABLE profile
DROP COLUMN PROFILE_SHARED_CUSTOMER;

ALTER TABLE profile
DROP COLUMN PROFILE_SHARED_CUSTOMER_DATE;

ALTER TABLE profile
DROP COLUMN CUSTOMER_INTERVIEW_STATUS;

ALTER TABLE profile
DROP COLUMN PROJECT;

ALTER TABLE profile
DROP COLUMN ACCOUNT;

ALTER TABLE requirement_profile_mapping
ADD COLUMN INTERNAL_EVALUATION_RESULT_DATE date DEFAULT NULL;

ALTER TABLE requirement_profile_mapping
ADD COLUMN PROFILE_SHARED_CUSTOMER varchar(50) DEFAULT NULL;

ALTER TABLE requirement_profile_mapping
ADD COLUMN PROFILE_SHARED_CUSTOMER_DATE date DEFAULT NULL;

ALTER TABLE `tmp`.`requirement` 
CHANGE COLUMN `ID` `ID` VARCHAR(25) NOT NULL ;

ALTER TABLE `tmp`.`requirement` 
CHANGE COLUMN `JOB_DESCRIPTION` `JOB_DESCRIPTION` VARCHAR(255) NULL DEFAULT NULL ;

INSERT INTO `user_account_mapping` (USER_ID,ACCOUNT_ID) VALUES (4,16),(4,17),(4,18),(4,19),(4,20),(4,21),(4,22),(4,23),(4,24),(4,25),(4,26),(4,27),(4,28),(4,29),(4,30);

ALTER TABLE tmp.profile AUTO_INCREMENT = 1

ALTER TABLE tmp.requirement_profile_mapping AUTO_INCREMENT = 1;

--02/jan/2019 added query

ALTER TABLE tmp.requirement MODIFY COLUMN YEAR_EXPERIENCE DOUBLE;

ALTER TABLE tmp.profile MODIFY COLUMN YEARS_OF_EXPERIENCE DOUBLE;

ALTER TABLE tmp.profile MODIFY COLUMN RELEVANT_EXPERIENCE DOUBLE;

--04/jan/2019

INSERT INTO `tmp`.`config_key` (`KEY`, `CREATED_BY`) VALUES ('Offer Processing Status', '1');

INSERT INTO `tmp`.`config_value` (`VALUE`, `CREATED_BY`) VALUES ('A', '1');
INSERT INTO `tmp`.`config_value` (`VALUE`, `CREATED_BY`) VALUES ('B', '1');
INSERT INTO `tmp`.`config_value` (`VALUE`, `CREATED_BY`) VALUES ('C', '1');
INSERT INTO `tmp`.`config_value` (`VALUE`, `CREATED_BY`) VALUES ('D', '1');
INSERT INTO `tmp`.`config_value` (`VALUE`, `CREATED_BY`) VALUES ('E', '1');
INSERT INTO `tmp`.`config_value` (`VALUE`, `CREATED_BY`) VALUES ('F', '1');
INSERT INTO `tmp`.`config_value` (`VALUE`, `CREATED_BY`) VALUES ('G', '1');

INSERT INTO `tmp`.`config_key_value_mapping` (`CONFIG_KEY_ID`, `CONFIG_VALUE_ID`, `CREATED_BY`) VALUES ('14', '89', '1');
INSERT INTO `tmp`.`config_key_value_mapping` (`CONFIG_KEY_ID`, `CONFIG_VALUE_ID`, `CREATED_BY`) VALUES ('14', '90', '1');
INSERT INTO `tmp`.`config_key_value_mapping` (`CONFIG_KEY_ID`, `CONFIG_VALUE_ID`, `CREATED_BY`) VALUES ('14', '91', '1');
INSERT INTO `tmp`.`config_key_value_mapping` (`CONFIG_KEY_ID`, `CONFIG_VALUE_ID`, `CREATED_BY`) VALUES ('14', '92', '1');
INSERT INTO `tmp`.`config_key_value_mapping` (`CONFIG_KEY_ID`, `CONFIG_VALUE_ID`, `CREATED_BY`) VALUES ('14', '93', '1');
INSERT INTO `tmp`.`config_key_value_mapping` (`CONFIG_KEY_ID`, `CONFIG_VALUE_ID`, `CREATED_BY`) VALUES ('14', '94', '1');
INSERT INTO `tmp`.`config_key_value_mapping` (`CONFIG_KEY_ID`, `CONFIG_VALUE_ID`, `CREATED_BY`) VALUES ('14', '95', '1');

INSERT INTO `tmp`.`config_value` (`ID`, `VALUE`, `CREATED_BY`) VALUES ('96', 'Abandoned', '1');
INSERT INTO `tmp`.`config_key_value_mapping` (`CONFIG_KEY_ID`, `CONFIG_VALUE_ID`, `CREATED_BY`) VALUES ('6', '96', '1');

ALTER TABLE requirement
ADD COLUMN PLANNED_CLOSURE_DATE date DEFAULT NULL;


INSERT INTO `tmp`.`config_value` (`VALUE`, `CREATED_BY`) VALUES ('Hold Opportunity', '1');
INSERT INTO `tmp`.`config_value` (`VALUE`, `CREATED_BY`) VALUES ('Lost Opportunity', '1');
INSERT INTO `tmp`.`config_value` (`VALUE`, `CREATED_BY`) VALUES ('Abandoned Opportunity', '1');


INSERT INTO `tmp`.`config_key_value_mapping` (`CONFIG_KEY_ID`, `CONFIG_VALUE_ID`, `CREATED_BY`) VALUES ('5', '97', '1');
INSERT INTO `tmp`.`config_key_value_mapping` (`CONFIG_KEY_ID`, `CONFIG_VALUE_ID`, `CREATED_BY`) VALUES ('5', '98', '1');
INSERT INTO `tmp`.`config_key_value_mapping` (`CONFIG_KEY_ID`, `CONFIG_VALUE_ID`, `CREATED_BY`) VALUES ('5', '99', '1');

ALTER TABLE requirement_profile_mapping
ADD COLUMN OFFER_PROCESSING_STATUS int DEFAULT NULL;

--18thJan2018 - Creating new tables (PRIMARY_SKILL, SECONDARY_SKILL, UPGRADE_SKILL, CUSTOMER, IBU, VISA_DETAILS, ASSOCIATE, ASSOCIATE_UPGRADE_SKILL_MAPPING) & 
--Adding new columns to existing table (PROJECTS).

CREATE TABLE `tmp`.`primary_skill` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `SKILL_NAME` VARCHAR(45) NULL,
  `SKILL_DESCRIPTION` VARCHAR(45) NULL,
  PRIMARY KEY (`ID`));
  
  CREATE TABLE `tmp`.`secondary_skill` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `SKILL_NAME` VARCHAR(45) NULL,
  `SKILL_DESCRIPTION` VARCHAR(45) NULL,
  PRIMARY KEY (`ID`));
  
  CREATE TABLE `tmp`.`customer` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `CUSTOMER_ID` VARCHAR(45) NULL,
  `CUSTOMER_NAME` VARCHAR(45) NULL,
  `CUSTOMER_GROUP_NAME` VARCHAR(45) NULL,
  PRIMARY KEY (`ID`));
  
  CREATE TABLE `tmp`.`ibu` (
  `IBU_ID` INT NOT NULL AUTO_INCREMENT,
  `IBU_NAME` VARCHAR(45) NULL,
  `IBU_DESCRIPTION` VARCHAR(45) NULL,
  `IBU_HEAD_NAME` VARCHAR(45) NULL,
  `IBU_HEAD_EMAIL` VARCHAR(45) NULL,
  PRIMARY KEY (`IBU_ID`));
  
  CREATE TABLE `tmp`.`upgrade_skill` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `SKILL_NAME` VARCHAR(45) NULL,
  `SKILL_DESCRIPTION` VARCHAR(45) NULL,
  PRIMARY KEY (`ID`));

  ALTER TABLE tmp.projects CHANGE `PROJECT_ID` `ID` int(5);
  ALTER TABLE `tmp`.`projects` 
CHANGE COLUMN `ID` `ID` INT(5) NOT NULL AUTO_INCREMENT ;
ALTER TABLE `tmp`.`projects` 
ADD COLUMN `PROJECT_ID` INT(10) NULL AFTER `ID`,
ADD COLUMN `PROJECT_DESCRIPTION` VARCHAR(45) NULL AFTER `PROJECT_NAME`,
ADD COLUMN `PROJECT_END_DATE` DATE NULL AFTER `PROJECT_DESCRIPTION`,
ADD COLUMN `PROJECT_CONTRACT_TYPE` VARCHAR(45) NULL AFTER `PROJECT_END_DATE`,
ADD COLUMN `IBU_ID` INT NULL AFTER `ACC_ID`,
ADD INDEX `IBU_ID_idx` (`IBU_ID` ASC) VISIBLE;
;
ALTER TABLE `tmp`.`projects` 
ADD CONSTRAINT `IBU_ID`
  FOREIGN KEY (`IBU_ID`)
  REFERENCES `tmp`.`ibu` (`IBU_ID`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
  
  CREATE TABLE `tmp`.`associate` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `EMP_ID` INT NULL,
  `EMP_NAME` VARCHAR(45) NULL,
  `BAND` VARCHAR(45) NULL,
  `GENDER` CHAR NULL,
  `HTR_FLAG` CHAR NULL,
  `EMP_IBU_ID` INT NULL,
  `TOTAL_EXPERIENCE` DECIMAL NULL,
  `TECHM_EXPERIENCE` DECIMAL NULL,
  `CURRENT_COUNTRY` VARCHAR(45) NULL,
  `CURRENT_LOCATION_CITY` VARCHAR(45) NULL,
  `ONSITE_OFFSHORE` VARCHAR(45) NULL,
  `BILLABILITY_STATUS` CHAR NULL,
  `BILLABILITY_START_DATE` DATE NULL,
  `BILLABILITY_END_DATE` DATE NULL,
  `SUPERVISOR_ID` INT NULL,
  `PROJECT_MANAGER_ID` INT NULL,
  `PROGRAM_MANAGER_ID` INT NULL,
  `PRIMARY_SKILL_ID` INT NULL,
  `SECONDARY_SKILL_ID` INT NULL,
  `CERTIFICATIONS_DONE` VARCHAR(45) NULL,
  `PROJECT_ID` INT NULL,
  `VISA_ID` INT NULL,
  `IS_ACTIVE` INT NULL,
  PRIMARY KEY (`ID`),
  INDEX `EMP_IBU_ID_idx` (`EMP_IBU_ID` ASC) VISIBLE,
  INDEX `PRIMARY_SKILL_ID_idx` (`PRIMARY_SKILL_ID` ASC) VISIBLE,
  INDEX `SECONDARY_SKILL_ID_idx` (`SECONDARY_SKILL_ID` ASC) VISIBLE,
  INDEX `PROJECT_ID_idx` (`PROJECT_ID` ASC) VISIBLE,
  CONSTRAINT `EMP_IBU_ID`
    FOREIGN KEY (`EMP_IBU_ID`)
    REFERENCES `tmp`.`ibu` (`IBU_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `PRIMARY_SKILL_ID`
    FOREIGN KEY (`PRIMARY_SKILL_ID`)
    REFERENCES `tmp`.`primary_skill` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `SECONDARY_SKILL_ID`
    FOREIGN KEY (`SECONDARY_SKILL_ID`)
    REFERENCES `tmp`.`secondary_skill` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `PROJECT_ID`
    FOREIGN KEY (`PROJECT_ID`)
    REFERENCES `tmp`.`projects` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

	CREATE TABLE `tmp`.`visa_details` (
  `VISA_ID` INT NOT NULL AUTO_INCREMENT,
  `VISA_EMP_ID` INT(11) NULL,
  `VISA_TYPE` VARCHAR(45) NULL,
  `VISA_STATUS` VARCHAR(45) NULL,
  `VISA_REMARKS` VARCHAR(45) NULL,
  PRIMARY KEY (`VISA_ID`),
  INDEX `VISA_EMP_ID_idx` (`VISA_EMP_ID` ASC) VISIBLE,
  CONSTRAINT `VISA_EMP_ID`
    FOREIGN KEY (`VISA_EMP_ID`)
    REFERENCES `tmp`.`associate` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
	
	ALTER TABLE `tmp`.`associate` 
ADD INDEX `VISA_ID_idx` (`VISA_ID` ASC) VISIBLE;
;
ALTER TABLE `tmp`.`associate` 
ADD CONSTRAINT `VISA_ID`
  FOREIGN KEY (`VISA_ID`)
  REFERENCES `tmp`.`visa_details` (`VISA_ID`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
  
  CREATE TABLE `tmp`.`associate_upgrade_skill_mapping` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `ASSOCIATE_ID` INT NULL,
  `UPGRADE_SKILL_ID` INT NULL,
  `COURSE_PRIORITY` INT NULL,
  `IS_CERTIFICATE_MANDATORY` CHAR NULL,
  `CERTIFICATE_DUE_DATE` DATE NULL,
  `CERTIFICATE_COMPLETION_DATE` DATE NULL,
  `CERTIFICATE_EXPIRY_DATE` DATE NULL,
  PRIMARY KEY (`ID`),
  INDEX `ASSOCIATE_ID_idx` (`ASSOCIATE_ID` ASC) VISIBLE,
  INDEX `UPGRADE_SKILL_ID_idx` (`UPGRADE_SKILL_ID` ASC) VISIBLE,
  CONSTRAINT `ASSOCIATE_ID`
    FOREIGN KEY (`ASSOCIATE_ID`)
    REFERENCES `tmp`.`associate` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `UPGRADE_SKILL_ID`
    FOREIGN KEY (`UPGRADE_SKILL_ID`)
    REFERENCES `tmp`.`upgrade_skill` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
	
--Changes in the new tables for the data available
	
	ALTER TABLE `tmp`.`associate` 
DROP FOREIGN KEY `VISA_ID`;
ALTER TABLE `tmp`.`associate` 
DROP COLUMN `IS_ACTIVE`,
DROP COLUMN `VISA_ID`,
CHANGE COLUMN `SUPERVISOR_ID` `SUPERVISOR_NAME` INT(11) NULL DEFAULT NULL ,
CHANGE COLUMN `PROJECT_MANAGER_ID` `PROJECT_MANAGER_NAME` INT(11) NULL DEFAULT NULL ,
DROP INDEX `VISA_ID_idx` ;
;

ALTER TABLE `tmp`.`ibu` 
DROP COLUMN `IBU_HEAD_EMAIL`,
DROP COLUMN `IBU_HEAD_NAME`;

CREATE TABLE `tmp`.`skill` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `SKILL_NAME` VARCHAR(45) NULL,
  `SKILL_DESCRIPTION` VARCHAR(45) NULL,
  PRIMARY KEY (`ID`));
  
  ALTER TABLE `tmp`.`associate` 
DROP FOREIGN KEY `PRIMARY_SKILL_ID`,
DROP FOREIGN KEY `SECONDARY_SKILL_ID`;
ALTER TABLE `tmp`.`associate` 
ADD INDEX `PRIMARY_SKILL_ID_idx` (`PRIMARY_SKILL_ID` ASC) VISIBLE,
ADD INDEX `SECONDARY_SKILL_ID_idx` (`SECONDARY_SKILL_ID` ASC) VISIBLE,
DROP INDEX `SECONDARY_SKILL_ID_idx` ,
DROP INDEX `PRIMARY_SKILL_ID_idx` ;
;
ALTER TABLE `tmp`.`associate` 
ADD CONSTRAINT `PRIMARY_SKILL_ID`
  FOREIGN KEY (`PRIMARY_SKILL_ID`)
  REFERENCES `tmp`.`skill` (`ID`),
ADD CONSTRAINT `SECONDARY_SKILL_ID`
  FOREIGN KEY (`SECONDARY_SKILL_ID`)
  REFERENCES `tmp`.`skill` (`ID`);
  
  DROP TABLE `tmp`.`primary_skill`;
  
  DROP TABLE `tmp`.`secondary_skill`;
  
--21stJan2019 changes in account_master table
  
  ALTER TABLE `tmp`.`account_master` 
ADD COLUMN `CUSTOMER_ID` INT(11) NOT NULL AFTER `ACCOUNT_ID`,
ADD COLUMN `CUSTOMER_NAME` VARCHAR(45) NULL AFTER `ACCOUNT_NAME;
	
	CREATE TABLE `tmp`.`project_account_master` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `PROJECT_ID` VARCHAR(45) NULL,
  `PROJECT_NAME` VARCHAR(45) NULL,
  `PROJECT_DESCRIPTION` VARCHAR(45) NULL,
  `CUSTOMER_ID` VARCHAR(45) NULL,
  `CUSTOMER_NAME` VARCHAR(45) NULL,
  `IBU_ID` INT NULL,
  PRIMARY KEY (`ID`));
  
  ALTER TABLE project_account_master ADD CONSTRAINT fk_IBU_ID FOREIGN KEY (IBU_ID) REFERENCES IBU(IBU_ID);
  
  CREATE TABLE `tmp`.`associate_project_mapping` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `ASSOCIATE_ID` INT(11) NULL,
  `PROJECT_ID` INT(11) NULL,
  `PROJECT_CONTRACT_TYPE` VARCHAR(45) NULL,
  `PROJECT_END_DATE` DATE NULL,
  PRIMARY KEY (`ID`));
  
  ALTER TABLE associate_project_mapping ADD CONSTRAINT fk_ASSOCIATE_ID FOREIGN KEY (ID) REFERENCES ASSOCIATE(ID);
  ALTER TABLE associate_project_mapping ADD CONSTRAINT fk_PROJECT_ID FOREIGN KEY (ID) REFERENCES PROJECTS(ID);
  
  DROP TABLE `tmp`.`customer`;
  
  --22ndJan2019
  ALTER TABLE `tmp`.`projects` 
CHANGE COLUMN `PROJECT_ID` `PROJECT_ID` VARCHAR(45) NULL DEFAULT NULL ;

ALTER TABLE `tmp`.`account_master` 
CHANGE COLUMN `CUSTOMER_ID` `CUSTOMER_ID` VARCHAR(45) NOT NULL ;

ALTER TABLE `tmp`.`account_master` 
DROP COLUMN `CUSTOMER_NAME`,
DROP COLUMN `CUSTOMER_ID`;

ALTER TABLE `tmp`.`project_account_master` 
ADD COLUMN `CUSTOMER_GROUP_NAME` VARCHAR(45) NULL AFTER `CUSTOMER_NAME`;

ALTER TABLE `tmp`.`associate_project_mapping` 
ADD COLUMN `PROJECT_IBU_ID` VARCHAR(45) NULL AFTER `PROJECT_END_DATE`;

--23rdJan2019 

ALTER TABLE `tmp`.`associate` 
CHANGE COLUMN `GENDER` `GENDER` CHAR(10) NULL DEFAULT NULL ,
CHANGE COLUMN `HTR_FLAG` `HTR_FLAG` CHAR(10) NULL DEFAULT NULL ,
CHANGE COLUMN `TOTAL_EXPERIENCE` `TOTAL_EXPERIENCE` DOUBLE NULL DEFAULT NULL ,
CHANGE COLUMN `TECHM_EXPERIENCE` `TECHM_EXPERIENCE` DOUBLE NULL DEFAULT NULL ,
CHANGE COLUMN `BILLABILITY_STATUS` `BILLABILITY_STATUS` CHAR(10) NULL DEFAULT NULL ,
CHANGE COLUMN `SUPERVISOR_NAME` `SUPERVISOR_NAME` VARCHAR(45) NULL DEFAULT NULL ,
CHANGE COLUMN `PROJECT_MANAGER_NAME` `PROJECT_MANAGER_NAME` VARCHAR(45) NULL DEFAULT NULL ,
CHANGE COLUMN `PROGRAM_MANAGER_ID` `PROGRAM_MANAGER_NAME` VARCHAR(45) NULL DEFAULT NULL ;
  
  ALTER TABLE associate_project_mapping ADD CONSTRAINT fk_PROJECT_ID FOREIGN KEY (ID) REFERENCES project_account_master(ID);
  
  DROP tmp.associate_project_mapping;
  
  CREATE TABLE `associate_project_mapping` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ASSOCIATE_ID` int(11) DEFAULT NULL,
  `PROJECT_ID` int(11) DEFAULT NULL,
  `PROJECT_CONTRACT_TYPE` varchar(45) DEFAULT NULL,
  `PROJECT_END_DATE` date DEFAULT NULL,
  `PROJECT_IBU_ID` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `ASSOCIATE_ID_idx` (`ASSOCIATE_ID`),
  KEY `PROJECT_ID_idx` (`PROJECT_ID`),
  CONSTRAINT `fk_ASSOCIATE_ID` FOREIGN KEY (`ASSOCIATE_ID`) REFERENCES `associate` (`id`),
  CONSTRAINT `fk_PROJECT_ID` FOREIGN KEY (`PROJECT_ID`) REFERENCES `project_account_master` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
  
--24-01-2019 saranya query
  INSERT INTO `tmp`.`user_account_mapping` (`USER_ID`, `ACCOUNT_ID`) VALUES ('5', '16');

--25thJan2019

ALTER TABLE `tmp`.`associate` 
DROP FOREIGN KEY `PROJECT_ID`;
ALTER TABLE `tmp`.`associate` 
ADD INDEX `PROJECT_ID_idx` (`PROJECT_ID` ASC) VISIBLE,
DROP INDEX `PROJECT_ID_idx` ;
;
ALTER TABLE `tmp`.`associate` 
ADD CONSTRAINT `PROJECT_ID`
  FOREIGN KEY (`PROJECT_ID`)
  REFERENCES `tmp`.`project_account_master` (`ID`);

  

INSERT INTO project_account_master (PROJECT_ID, PROJECT_NAME, CUSTOMER_ID, CUSTOMER_NAME) VALUES('31766','AM Support IAUT','100000000000416','Ford Motor Company');
INSERT INTO project_account_master (PROJECT_ID, PROJECT_NAME, CUSTOMER_ID, CUSTOMER_NAME) VALUES('30634','Adient US LLC','3840','Adient US LLC');
INSERT INTO project_account_master (PROJECT_ID, PROJECT_NAME, CUSTOMER_ID, CUSTOMER_NAME) VALUES('29762','BOSCH Ltd','100000000009294','Bosch Limited');
INSERT INTO project_account_master (PROJECT_ID, PROJECT_NAME, CUSTOMER_ID, CUSTOMER_NAME) VALUES('22436','Bosch Limited,IND','100000000009294','Bosch Limited');
INSERT INTO project_account_master (PROJECT_ID, PROJECT_NAME, CUSTOMER_ID, CUSTOMER_NAME) VALUES('32517','Building Warranty Framework','1491','INTERNAL');
INSERT INTO project_account_master (PROJECT_ID, PROJECT_NAME, CUSTOMER_ID, CUSTOMER_NAME) VALUES('28537','FORD GPIRS Tech Refresh','100000000000416','Ford Motor Company');
INSERT INTO project_account_master (PROJECT_ID, PROJECT_NAME, CUSTOMER_ID, CUSTOMER_NAME) VALUES('34532','Ford -  GIBIS','100000000000416','Ford Motor Company');
INSERT INTO project_account_master (PROJECT_ID, PROJECT_NAME, CUSTOMER_ID, CUSTOMER_NAME) VALUES('22090','Ford Direct','2650','FordDirect');
INSERT INTO project_account_master (PROJECT_ID, PROJECT_NAME, CUSTOMER_ID, CUSTOMER_NAME) VALUES('30680','Ford Extranet - Offshore','2952','Ford Motor Private Limited');
INSERT INTO project_account_master (PROJECT_ID, PROJECT_NAME, CUSTOMER_ID, CUSTOMER_NAME) VALUES('23532','Ford Extranet NA','2952','Ford Motor Private Limited');
INSERT INTO project_account_master (PROJECT_ID, PROJECT_NAME, CUSTOMER_ID, CUSTOMER_NAME) VALUES('34751','Ford GDIA ICI Pega','100000000000416','Ford Motor Company');
INSERT INTO project_account_master (PROJECT_ID, PROJECT_NAME, CUSTOMER_ID, CUSTOMER_NAME) VALUES('34752','Ford GDIA – ICI AEM','100000000000416','Ford Motor Company');
INSERT INTO project_account_master (PROJECT_ID, PROJECT_NAME, CUSTOMER_ID, CUSTOMER_NAME) VALUES('28238','Ford GESB','100000000000416','Ford Motor Company');
INSERT INTO project_account_master (PROJECT_ID, PROJECT_NAME, CUSTOMER_ID, CUSTOMER_NAME) VALUES('35511','Ford GESBRe SMW and AM Support','100000000000416','Ford Motor Company');
INSERT INTO project_account_master (PROJECT_ID, PROJECT_NAME, CUSTOMER_ID, CUSTOMER_NAME) VALUES('34153','Ford GPIRS Phase II - CR','100000000000416','Ford Motor Company');
INSERT INTO project_account_master (PROJECT_ID, PROJECT_NAME, CUSTOMER_ID, CUSTOMER_NAME) VALUES('29267','Ford GVDS EU','100000000005468','Ford-Werke GmbH');
INSERT INTO project_account_master (PROJECT_ID, PROJECT_NAME, CUSTOMER_ID, CUSTOMER_NAME) VALUES('34002','Ford HROGC-FMMS-Tech Refresh','100000000000416','Ford Motor Company');
INSERT INTO project_account_master (PROJECT_ID, PROJECT_NAME, CUSTOMER_ID, CUSTOMER_NAME) VALUES('25767','Ford IMS T&M_Onsite','100000000009719','MSX Americas, Inc.');
INSERT INTO project_account_master (PROJECT_ID, PROJECT_NAME, CUSTOMER_ID, CUSTOMER_NAME) VALUES('C01000000011520','Ford Kronos','100000000000416','Ford Motor Company');
INSERT INTO project_account_master (PROJECT_ID, PROJECT_NAME, CUSTOMER_ID, CUSTOMER_NAME) VALUES('30670','Ford Motors Pvt Ltd Chennai','2952','Ford Motor Private Limited');
INSERT INTO project_account_master (PROJECT_ID, PROJECT_NAME, CUSTOMER_ID, CUSTOMER_NAME) VALUES('30671','Ford Motors Pvt Ltd Gurgaon','2952','Ford Motor Private Limited');
INSERT INTO project_account_master (PROJECT_ID, PROJECT_NAME, CUSTOMER_ID, CUSTOMER_NAME) VALUES('29905','Ford OWS EU 3.1 Phase II - PR','100000000000416','Ford Motor Company');
INSERT INTO project_account_master (PROJECT_ID, PROJECT_NAME, CUSTOMER_ID, CUSTOMER_NAME) VALUES('29904','Ford OWS EU 3.1 Phase II- CR','100000000000416','Ford Motor Company');
INSERT INTO project_account_master (PROJECT_ID, PROJECT_NAME, CUSTOMER_ID, CUSTOMER_NAME) VALUES('34070','Ford OWS GC-CR','100000000000416','Ford Motor Company');
INSERT INTO project_account_master (PROJECT_ID, PROJECT_NAME, CUSTOMER_ID, CUSTOMER_NAME) VALUES('33811','Ford OWS-APAC','100000000000416','Ford Motor Company');
INSERT INTO project_account_master (PROJECT_ID, PROJECT_NAME, CUSTOMER_ID, CUSTOMER_NAME) VALUES('34988','Ford OWS-APAC-CR','100000000000416','Ford Motor Company');
INSERT INTO project_account_master (PROJECT_ID, PROJECT_NAME, CUSTOMER_ID, CUSTOMER_NAME) VALUES('33738','Ford OWS-SA-CR','100000000000416','Ford Motor Company');
INSERT INTO project_account_master (PROJECT_ID, PROJECT_NAME, CUSTOMER_ID, CUSTOMER_NAME) VALUES('31765','Ford OWS_GESB AM Support','100000000000416','Ford Motor Company');
INSERT INTO project_account_master (PROJECT_ID, PROJECT_NAME, CUSTOMER_ID, CUSTOMER_NAME) VALUES('31368','Ford Panda Tech Refresh','100000000000416','Ford Motor Company');
INSERT INTO project_account_master (PROJECT_ID, PROJECT_NAME, CUSTOMER_ID, CUSTOMER_NAME) VALUES('9000600','Ford Professional Services 200','100000000001942','Ford Motor Company Limited');
INSERT INTO project_account_master (PROJECT_ID, PROJECT_NAME, CUSTOMER_ID, CUSTOMER_NAME) VALUES('12000380','Ford_Extranet_FMCL_ITI_F_C','100000000000416','Ford Motor Company');
INSERT INTO project_account_master (PROJECT_ID, PROJECT_NAME, CUSTOMER_ID, CUSTOMER_NAME) VALUES('27121','Ford_HR OGC AD_AM','100000000000416','Ford Motor Company');
INSERT INTO project_account_master (PROJECT_ID, PROJECT_NAME, CUSTOMER_ID, CUSTOMER_NAME) VALUES('13006440','Ford_Onsite_T&M','100000000009719','MSX Americas, Inc.');
INSERT INTO project_account_master (PROJECT_ID, PROJECT_NAME, CUSTOMER_ID, CUSTOMER_NAME) VALUES('31941','Ford_Onsite_T&M_Emerging','100000000009719','MSX Americas, Inc.');
INSERT INTO project_account_master (PROJECT_ID, PROJECT_NAME, CUSTOMER_ID, CUSTOMER_NAME) VALUES('31936','Ford_Onsite_T&M_Testing','100000000009719','MSX Americas, Inc.');
INSERT INTO project_account_master (PROJECT_ID, PROJECT_NAME, CUSTOMER_ID, CUSTOMER_NAME) VALUES('32983','GSEC PIVPN Tunnel Mirgation','100000000000416','Ford Motor Company');
INSERT INTO project_account_master (PROJECT_ID, PROJECT_NAME, CUSTOMER_ID, CUSTOMER_NAME) VALUES('21250','IBU Automotive BUFF - MGMT','1491','INTERNAL');
INSERT INTO project_account_master (PROJECT_ID, PROJECT_NAME, CUSTOMER_ID, CUSTOMER_NAME) VALUES('C02000000022336','Inteva - IS Support','2712','Inteva Products, LLC');
INSERT INTO project_account_master (PROJECT_ID, PROJECT_NAME, CUSTOMER_ID, CUSTOMER_NAME) VALUES('35813','JTEKT NA Offshore Support','4123','JTEKT North America Corporation');
INSERT INTO project_account_master (PROJECT_ID, PROJECT_NAME, CUSTOMER_ID, CUSTOMER_NAME) VALUES('35773','JTEKT North America Corp','4123','JTEKT North America Corporation');
INSERT INTO project_account_master (PROJECT_ID, PROJECT_NAME, CUSTOMER_ID, CUSTOMER_NAME) VALUES('27158','Mahindra North America Technia','3495','Mahindra North America Technial Center');
INSERT INTO project_account_master (PROJECT_ID, PROJECT_NAME, CUSTOMER_ID, CUSTOMER_NAME) VALUES('26804','Mazda - IS Support Projects','100000000003004','Mazda Motor of America, Inc');
INSERT INTO project_account_master (PROJECT_ID, PROJECT_NAME, CUSTOMER_ID, CUSTOMER_NAME) VALUES('32555','Mazda-BusinessSupport Projects','100000000003004','Mazda Motor of America, Inc');
INSERT INTO project_account_master (PROJECT_ID, PROJECT_NAME, CUSTOMER_ID, CUSTOMER_NAME) VALUES('35243','Mazda-Core Flex AD and AMS Sup','100000000003004','Mazda Motor of America, Inc');
INSERT INTO project_account_master (PROJECT_ID, PROJECT_NAME, CUSTOMER_ID, CUSTOMER_NAME) VALUES('10003580','Mazda_Support Projects_B_C','100000000003004','Mazda Motor of America, Inc');
INSERT INTO project_account_master (PROJECT_ID, PROJECT_NAME, CUSTOMER_ID, CUSTOMER_NAME) VALUES('34238','Mercedes Benz US LLC Project','4363','Mercedes-Benz Financial Services USA LLC');
INSERT INTO project_account_master (PROJECT_ID, PROJECT_NAME, CUSTOMER_ID, CUSTOMER_NAME) VALUES('33694','Navistar-Mobile App Dev & Supp','500000000001939','Navistar Inc.');
INSERT INTO project_account_master (PROJECT_ID, PROJECT_NAME, CUSTOMER_ID, CUSTOMER_NAME) VALUES('35422','Navistar-PSFIN-ProjectOlive','500000000001939','Navistar Inc.');
INSERT INTO project_account_master (PROJECT_ID, PROJECT_NAME, CUSTOMER_ID, CUSTOMER_NAME) VALUES('34113','Navistar:BSA','500000000001939','Navistar Inc.');
INSERT INTO project_account_master (PROJECT_ID, PROJECT_NAME, CUSTOMER_ID, CUSTOMER_NAME) VALUES('30441','REHAU AMS Support Srv','3525','REHAU Incorporated');
INSERT INTO project_account_master (PROJECT_ID, PROJECT_NAME, CUSTOMER_ID, CUSTOMER_NAME) VALUES('33823','RIVIAN S4 HANA Implementation','3153','Rivian Automotive,LLC');
INSERT INTO project_account_master (PROJECT_ID, PROJECT_NAME, CUSTOMER_ID, CUSTOMER_NAME) VALUES('33516','TRW IMS Support Services','100000000007889','KELSEY- HAYES COMPANY');
INSERT INTO project_account_master (PROJECT_ID, PROJECT_NAME, CUSTOMER_ID, CUSTOMER_NAME) VALUES('34477','TRW IS QAD Europe Project','100000000009518','TRW KFZ Ausrustung GmbH');
INSERT INTO project_account_master (PROJECT_ID, PROJECT_NAME, CUSTOMER_ID, CUSTOMER_NAME) VALUES('35655','TRW IS Windchill ZF Catia VPM','100000000007889','KELSEY- HAYES COMPANY');
INSERT INTO project_account_master (PROJECT_ID, PROJECT_NAME, CUSTOMER_ID, CUSTOMER_NAME) VALUES('32790','TRW MES Application Support','3249','ZF Friedrichshafen AG');
INSERT INTO project_account_master (PROJECT_ID, PROJECT_NAME, CUSTOMER_ID, CUSTOMER_NAME) VALUES('35751','TRW-IMS-GDC Staffing Agreement','100000000009518','TRW KFZ Ausrustung GmbH');
INSERT INTO project_account_master (PROJECT_ID, PROJECT_NAME, CUSTOMER_ID, CUSTOMER_NAME) VALUES('27955','TRW-IS EDI Offshore Support','100000000007889','KELSEY- HAYES COMPANY');
INSERT INTO project_account_master (PROJECT_ID, PROJECT_NAME, CUSTOMER_ID, CUSTOMER_NAME) VALUES('32911','TRW-IS EDI Onsite Support - AU','100000000009518','TRW KFZ Ausrustung GmbH');
INSERT INTO project_account_master (PROJECT_ID, PROJECT_NAME, CUSTOMER_ID, CUSTOMER_NAME) VALUES('26933','TRW-IS- MAD Offshore Support','100000000007889','KELSEY- HAYES COMPANY');
INSERT INTO project_account_master (PROJECT_ID, PROJECT_NAME, CUSTOMER_ID, CUSTOMER_NAME) VALUES('26930','TRW-IS- Windchill & PDM Link S','100000000007889','KELSEY- HAYES COMPANY');
INSERT INTO project_account_master (PROJECT_ID, PROJECT_NAME, CUSTOMER_ID, CUSTOMER_NAME) VALUES('13007560','TRW-IS-CAS-Support2','100000000007889','KELSEY- HAYES COMPANY');
INSERT INTO project_account_master (PROJECT_ID, PROJECT_NAME, CUSTOMER_ID, CUSTOMER_NAME) VALUES('26931','TRW-IS-Gifco Onsite & Offshore','100000000007889','KELSEY- HAYES COMPANY');
INSERT INTO project_account_master (PROJECT_ID, PROJECT_NAME, CUSTOMER_ID, CUSTOMER_NAME) VALUES('26928','TRW-IS-HRIT Security Admin Sup','100000000007889','KELSEY- HAYES COMPANY');
INSERT INTO project_account_master (PROJECT_ID, PROJECT_NAME, CUSTOMER_ID, CUSTOMER_NAME) VALUES('C01000000014833','TRW-IS-INDIRECT SPEND','100000000007889','KELSEY- HAYES COMPANY');
INSERT INTO project_account_master (PROJECT_ID, PROJECT_NAME, CUSTOMER_ID, CUSTOMER_NAME) VALUES('26929','TRW-IS-Indirect Spend Managed','100000000007889','KELSEY- HAYES COMPANY');
INSERT INTO project_account_master (PROJECT_ID, PROJECT_NAME, CUSTOMER_ID, CUSTOMER_NAME) VALUES('13006810','TRW-IS-MSBI-ManagedServices-Su','100000000007889','KELSEY- HAYES COMPANY');
INSERT INTO project_account_master (PROJECT_ID, PROJECT_NAME, CUSTOMER_ID, CUSTOMER_NAME) VALUES('11001850','TRW-IS-Onsite Offshore Support','100000000007889','KELSEY- HAYES COMPANY');
INSERT INTO project_account_master (PROJECT_ID, PROJECT_NAME, CUSTOMER_ID, CUSTOMER_NAME) VALUES('34146','TRW-IS-QADGifco Onsite support','GMB000000000105','TRW Deutschland Holding GmbH');
INSERT INTO project_account_master (PROJECT_ID, PROJECT_NAME, CUSTOMER_ID, CUSTOMER_NAME) VALUES('35750','TRW-IS-SAP Managed Services','100000000009518','TRW KFZ Ausrustung GmbH');
INSERT INTO project_account_master (PROJECT_ID, PROJECT_NAME, CUSTOMER_ID, CUSTOMER_NAME) VALUES('23489','TRW-IS-SAP ONS & OFF support','100000000007889','KELSEY- HAYES COMPANY');
INSERT INTO project_account_master (PROJECT_ID, PROJECT_NAME, CUSTOMER_ID, CUSTOMER_NAME) VALUES('13001280','TRW-IS-SAP-Support','100000000009518','TRW KFZ Ausrustung GmbH');
INSERT INTO project_account_master (PROJECT_ID, PROJECT_NAME, CUSTOMER_ID, CUSTOMER_NAME) VALUES('23538','TRW-IS-WEB Aftermarket Support','100000000007889','KELSEY- HAYES COMPANY');
INSERT INTO project_account_master (PROJECT_ID, PROJECT_NAME, CUSTOMER_ID, CUSTOMER_NAME) VALUES('34020','ZF IT Domain License Services','3249','ZF Friedrichshafen AG');
INSERT INTO project_account_master (PROJECT_ID, PROJECT_NAME, CUSTOMER_ID, CUSTOMER_NAME) VALUES('35663','ZF IT SE Development Project','3249','ZF Friedrichshafen AG');
INSERT INTO project_account_master (PROJECT_ID, PROJECT_NAME, CUSTOMER_ID, CUSTOMER_NAME) VALUES('35465','ZF License Management Mgd Srv','3249','ZF Friedrichshafen AG');
INSERT INTO project_account_master (PROJECT_ID, PROJECT_NAME, CUSTOMER_ID, CUSTOMER_NAME) VALUES('33203','ZF MICS Development Project','3249','ZF Friedrichshafen AG');



--15-02-19 SQL server management query for table creation of Resume_Upload--

CREATE TABLE dbo.Resume_Upload(
	ID int IDENTITY(1,1) NOT NULL PRIMARY KEY,
	EMP_ID int NOT NULL,
	BLOB varbinary(max) NOT NULL,
	CREATED_ON [date] NOT NULL,
	CREATED_BY INT NOT NULL,
	UPDATED_ON [date] ,
	UPDATED_BY INT
	);
	
	
	ALTER TABLE `tmp`.`requirement_profile_mapping` 
ADD COLUMN `INTERNAL_EVALUATION_RESULT_DATE` VARCHAR(45) NOT NULL AFTER `UPDATED_BY`;
