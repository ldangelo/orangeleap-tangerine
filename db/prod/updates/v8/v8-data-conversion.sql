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


/* Schema versions now match release versions  */
UPDATE VERSION SET SCHEMA_MAJOR_VERSION = 8 WHERE COMPONENT_ID = 'ORANGE' AND COMPONENT_DESC = 'Orange Leap';

/* TANGERINE-1807 */
SET @Next_ID=(SELECT MAX(PICKLIST_ID)+1 FROM PICKLIST);

INSERT INTO PICKLIST (PICKLIST_ID, PICKLIST_NAME_ID, PICKLIST_NAME, PICKLIST_DESC) VALUES (@Next_ID, 'constituentBatchFields', 'constituentBatchFields', 'Updatable Constituent Batch Fields');
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, LONG_DESCRIPTION, ITEM_ORDER) VALUES (@Next_ID, 'constituentType', 'constituentType', 'Constituent Type', 1);
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, LONG_DESCRIPTION, ITEM_ORDER) VALUES (@Next_ID, 'title', 'title', 'Title', 2);
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, LONG_DESCRIPTION, ITEM_ORDER) VALUES (@Next_ID, 'suffix', 'suffix', 'Suffix', 3);
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, LONG_DESCRIPTION, ITEM_ORDER) VALUES (@Next_ID, 'customFieldMap[organization.website]', 'customFieldMap[organization.website]', 'Website', 4);
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, LONG_DESCRIPTION, ITEM_ORDER) VALUES (@Next_ID, 'customFieldMap[organization.taxid]', 'customFieldMap[organization.taxid]', 'Tax Id', 5);
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, LONG_DESCRIPTION, ITEM_ORDER) VALUES (@Next_ID, 'naicsCode', 'naicsCode', 'NAICS Code', 6);
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, LONG_DESCRIPTION, ITEM_ORDER) VALUES (@Next_ID, 'customFieldMap[communicationPreferences]', 'customFieldMap[communicationPreferences]', 'Communication Preferences', 7);
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, LONG_DESCRIPTION, ITEM_ORDER) VALUES (@Next_ID, 'customFieldMap[emailFormat]', 'customFieldMap[emailFormat]', 'Email Format', 8);
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, LONG_DESCRIPTION, ITEM_ORDER) VALUES (@Next_ID, 'customFieldMap[formalSalutation]', 'customFieldMap[formalSalutation]', 'Formal Salutation', 9);
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, LONG_DESCRIPTION, ITEM_ORDER) VALUES (@Next_ID, 'customFieldMap[informalSalutation]', 'customFieldMap[informalSalutation]', 'Informal Salutation', 10);
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, LONG_DESCRIPTION, ITEM_ORDER) VALUES (@Next_ID, 'customFieldMap[headOfHouseholdSalutation]', 'customFieldMap[headOfHouseholdSalutation]', 'Head Of Household Salutation', 11);
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, LONG_DESCRIPTION, ITEM_ORDER) VALUES (@Next_ID, 'maritalStatus', 'maritalStatus', 'Marital Status', 12);
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, LONG_DESCRIPTION, ITEM_ORDER) VALUES (@Next_ID, 'customFieldMap[individual.gender]', 'customFieldMap[individual.gender]', 'Gender', 13);
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, LONG_DESCRIPTION, ITEM_ORDER) VALUES (@Next_ID, 'customFieldMap[individual.race]', 'customFieldMap[individual.race]', 'Race', 14);
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, LONG_DESCRIPTION, ITEM_ORDER) VALUES (@Next_ID, 'customFieldMap[individual.military]', 'customFieldMap[individual.military]', 'Military', 15);
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, LONG_DESCRIPTION, ITEM_ORDER) VALUES (@Next_ID, 'customFieldMap[organization.matching]', 'customFieldMap[organization.matching]', 'Matching', 16);
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, LONG_DESCRIPTION, ITEM_ORDER) VALUES (@Next_ID, 'customFieldMap[organization.percentMatch]', 'customFieldMap[organization.percentMatch]', 'Percent Match', 17);
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, LONG_DESCRIPTION, ITEM_ORDER) VALUES (@Next_ID, 'customFieldMap[organization.maximumAnnualLimit]', 'customFieldMap[organization.maximumAnnualLimit]', 'Maximum Annual Limit', 18);
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, LONG_DESCRIPTION, ITEM_ORDER) VALUES (@Next_ID, 'customFieldMap[organization.minimumGiftMatch]', 'customFieldMap[organization.minimumGiftMatch]', 'Minimum Gift Match', 19);
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, LONG_DESCRIPTION, ITEM_ORDER) VALUES (@Next_ID, 'customFieldMap[organization.maximumGiftMatch]', 'customFieldMap[organization.maximumGiftMatch]', 'Maximum Gift Match', 20);
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, LONG_DESCRIPTION, ITEM_ORDER) VALUES (@Next_ID, 'customFieldMap[organization.totalPerEmployeePerYear]', 'customFieldMap[organization.totalPerEmployeePerYear]', 'Total Per Employee Per Year', 21);
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, LONG_DESCRIPTION, ITEM_ORDER) VALUES (@Next_ID, 'customFieldMap[organization.onlineMatchingGiftForm]', 'customFieldMap[organization.onlineMatchingGiftForm]', 'Matching Gift Form', 22);
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, LONG_DESCRIPTION, ITEM_ORDER) VALUES (@Next_ID, 'customFieldMap[organization.programStartMonth]', 'customFieldMap[organization.programStartMonth]', 'Program Start Month', 23);
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, LONG_DESCRIPTION, ITEM_ORDER) VALUES (@Next_ID, 'customFieldMap[organization.procedureForRequestingMatch]', 'customFieldMap[organization.procedureForRequestingMatch]', 'Procedure for Requesting Match', 24);



COMMIT;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
