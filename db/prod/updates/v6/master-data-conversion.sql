/*
SQLyog Job Agent Version 8.14 n2 Copyright(c) Webyog Softworks Pvt. Ltd. All Rights Reserved.

MySQL - 5.1.35-community
*********************************************************************
*/
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

/* SYNC DB : company1 */
SET AUTOCOMMIT = 0;


UPDATE VERSION SET SCHEMA_MAJOR_VERSION = 4 WHERE COMPONENT_ID = 'ORANGE' AND COMPONENT_DESC = 'Orange Leap';



/* TANGERINE-867 */
UPDATE SECTION_DEFINITION SET PAGE_TYPE = 'giftPosted' WHERE PAGE_TYPE = 'giftView';

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES
('gift.customFieldMap[bankReadOnly]', 'gift', 'customFieldMap[bank]', 'Bank', 'PICKLIST_DISPLAY');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES
('gift.postedDateReadOnly', 'gift', 'postedDate', 'Posted Date', 'postedDate', 'DATE_DISPLAY');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES
('gift.donationDateReadOnly', 'gift', 'donationDate', 'Date of Donation', 'DATE_DISPLAY');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES
('gift.postmarkDateReadOnly', 'gift', 'postmarkDate', 'Postmark Date', 'DATE_DISPLAY');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES
('gift.customFieldMap[sourceReadOnly]', 'gift', 'customFieldMap[source]', 'Source', 'PICKLIST_DISPLAY');



UPDATE SECTION_FIELD set FIELD_DEFINITION_ID = 'gift.giftStatusReadOnly' WHERE FIELD_DEFINITION_ID = 'gift.giftStatus'
and SECTION_DEFINITION_ID = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='gift.donation' AND PAGE_TYPE='giftPosted');

UPDATE SECTION_FIELD set FIELD_DEFINITION_ID = 'gift.customFieldMap[bankReadOnly]' WHERE FIELD_DEFINITION_ID = 'gift.customFieldMap[bank]'
and SECTION_DEFINITION_ID = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='gift.donation' AND PAGE_TYPE='giftPosted');

UPDATE SECTION_FIELD set FIELD_DEFINITION_ID = 'gift.postedDateReadOnly' WHERE FIELD_DEFINITION_ID = 'gift.postedDate'
and SECTION_DEFINITION_ID = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='gift.donation' AND PAGE_TYPE='giftPosted');

UPDATE SECTION_FIELD set FIELD_DEFINITION_ID = 'gift.donationDateReadOnly' WHERE FIELD_DEFINITION_ID = 'gift.donationDate'
and SECTION_DEFINITION_ID = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='gift.donation' AND PAGE_TYPE='giftPosted');

UPDATE SECTION_FIELD set FIELD_DEFINITION_ID = 'gift.postmarkDateReadOnly' WHERE FIELD_DEFINITION_ID = 'gift.postmarkDate'
and SECTION_DEFINITION_ID = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='gift.donation' AND PAGE_TYPE='giftPosted');

UPDATE SECTION_FIELD set FIELD_DEFINITION_ID = 'gift.customFieldMap[sourceReadOnly]' WHERE FIELD_DEFINITION_ID = 'gift.customFieldMap[source]'
and SECTION_DEFINITION_ID = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='gift.donation' AND PAGE_TYPE='giftPosted');


COMMIT;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

