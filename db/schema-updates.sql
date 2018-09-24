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
WHERE NAME = 'Sangeetha Selvakumar' AND EMAIL='ss00474123@techmahindra.com'

UPDATE user
SET DISPLAY_NAME = 'Agalya'
WHERE NAME = 'Agalya Sivasubramanian' AND EMAIL='as00490953@TechMahindra.com'

UPDATE user
SET DISPLAY_NAME = 'Saranya'
WHERE NAME = 'Saranya Palanisamy' AND EMAIL='ss00425154@Techmahindra.com'

UPDATE user
SET DISPLAY_NAME = 'Siva'
WHERE NAME = 'Siva Subramanian' AND EMAIL='Siva.Subramanian@TechMahindra.com'

UPDATE user
SET DISPLAY_NAME = 'Ram'
WHERE NAME = 'Ramkrishna Rao' AND EMAIL='Ramkrishna.Rao@TechMahindra.com'

UPDATE user
SET DISPLAY_NAME = 'Kannan'
WHERE NAME = 'Kannan Kasthurirajan' AND EMAIL='KK00539399@TechMahindra.com'

UPDATE user
SET DISPLAY_NAME = 'Sukumar'
WHERE NAME = 'Sukumar Natesan' AND EMAIL='Sukumar.Natesan@TechMahindra.com'

UPDATE user
SET NAME = 'Sangeetha', DISPLAY_NAME='Sangeetha Selvakumar'
WHERE EMAIL='ss00474123@techmahindra.com'

UPDATE user
SET NAME = 'Agalya', DISPLAY_NAME='Agalya Sivasubramanian'
WHERE EMAIL='as00490953@techmahindra.com'

UPDATE user
SET NAME = 'Saranya', DISPLAY_NAME='Saranya Palanisamy'
WHERE EMAIL='ss00425154@Techmahindra.com'

UPDATE user
SET NAME = 'Siva', DISPLAY_NAME='Siva Subramanian'
WHERE EMAIL='Siva.Subramanian@TechMahindra.com'

UPDATE user
SET NAME = 'Ram', DISPLAY_NAME='Ramkrishna Rao'
WHERE EMAIL='Ramkrishna.Rao@TechMahindra.com'

UPDATE user
SET NAME = 'Kannan', DISPLAY_NAME='Kannan Kasthurirajan'
WHERE EMAIL='KK00539399@TechMahindra.com'

UPDATE user
SET NAME = 'Sukumar', DISPLAY_NAME='Sukumar Natesan'
WHERE EMAIL='Sukumar.Natesan@TechMahindra.com'


update projects 
set PROJECT_NAME='Eport' Where PROJECT_ID='5'

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
WHERE NAME = 'Sangeetha' AND EMAIL='ss00474123@techmahindra.com'

UPDATE user
SET ROLE = '1'
WHERE NAME = 'Agalya' AND EMAIL='as00490953@TechMahindra.com';

UPDATE user
SET ROLE = '1'
WHERE NAME = 'Saranya Palanisamy' AND EMAIL='ss00425154@Techmahindra.com';

UPDATE user
SET ROLE = '1'
WHERE NAME = 'Siva Subramanian' AND EMAIL='Siva.Subramanian@TechMahindra.com';

UPDATE user
SET ROLE = '2'
WHERE NAME = 'Ramkrishna Rao' AND EMAIL='Ramkrishna.Rao@TechMahindra.com';

UPDATE user
SET ROLE = '3'
WHERE NAME = 'Kannan Kasthurirajan' AND EMAIL='KK00539399@TechMahindra.com';

UPDATE user
SET ROLE = '4'
WHERE NAME = 'Sukumar Natesan' AND EMAIL='Sukumar.Natesan@TechMahindra.com';

INSERT INTO `user_account_mapping` (USER_ID,ACCOUNT_ID) VALUES (4,1),(4,2),(4,3),(4,4),(4,5),(4,6),(4,7),(4,8),(4,9),(4,10),(4,11),(4,12),(4,13),(4,14),(4,15);