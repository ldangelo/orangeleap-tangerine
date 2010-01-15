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
UPDATE VERSION SET SCHEMA_MAJOR_VERSION = 7 WHERE COMPONENT_ID = 'ORANGE' AND COMPONENT_DESC = 'Orange Leap';

/* TODO  add rollupAttributesDomain.sql */

/* TANGERINE-1042 */
SET @Next_ID=(SELECT MAX(PICKLIST_ID)+1 FROM PICKLIST);

INSERT INTO PICKLIST (PICKLIST_ID, PICKLIST_NAME_ID, PICKLIST_NAME, PICKLIST_DESC) VALUES (@Next_ID, 'giftBatchFields', 'giftBatchFields', 'Updatable Gift Batch Fields');
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, LONG_DESCRIPTION, ITEM_ORDER) VALUES (@Next_ID, 'postedDate', 'postedDate', 'Posted Date (Creates Journal Entry)', 1);
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, LONG_DESCRIPTION, ITEM_ORDER) VALUES (@Next_ID, 'giftStatus', 'giftStatus', 'Status', 2);
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, LONG_DESCRIPTION, ITEM_ORDER) VALUES (@Next_ID, 'customFieldMap[source]', 'customFieldMap[source]', 'Source', 3);
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, LONG_DESCRIPTION, ITEM_ORDER) VALUES (@Next_ID, 'customFieldMap[bank]', 'customFieldMap[bank]', 'Bank', 4);
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, LONG_DESCRIPTION, ITEM_ORDER) VALUES (@Next_ID, 'postmarkDate', 'postmarkDate', 'Postmark Date', 5);
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, LONG_DESCRIPTION, ITEM_ORDER) VALUES (@Next_ID, 'donationDate', 'donationDate', 'Donation Date', 6);


SET @Next_ID=(SELECT MAX(PICKLIST_ID)+1 FROM PICKLIST);

INSERT INTO PICKLIST (PICKLIST_ID, PICKLIST_NAME_ID, PICKLIST_NAME, PICKLIST_DESC) VALUES (@Next_ID, 'adjustedGiftBatchFields', 'adjustedGiftBatchFields', 'Updatable Adjusted Gift Batch Fields');
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, LONG_DESCRIPTION, ITEM_ORDER) VALUES (@Next_ID, 'postedDate', 'postedDate', 'Posted Date (Creates Journal Entry)', 1);
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, LONG_DESCRIPTION, ITEM_ORDER) VALUES (@Next_ID, 'status', 'status', 'Adjustment Status', 2);
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, LONG_DESCRIPTION, ITEM_ORDER) VALUES (@Next_ID, 'type', 'type', 'Adjustment Type', 3);
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, LONG_DESCRIPTION, ITEM_ORDER) VALUES (@Next_ID, 'reason', 'reason', 'Adjustment Reason', 4);
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, LONG_DESCRIPTION, ITEM_ORDER) VALUES (@Next_ID, 'customFieldMap[bank]', 'customFieldMap[bank]', 'Bank', 5);
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, LONG_DESCRIPTION, ITEM_ORDER) VALUES (@Next_ID, 'paymentRequired', 'paymentRequired', 'Payment Required', 6);



UPDATE PAGE_ACCESS set PAGE_TYPE = 'batch', ROLE = 'ROLE_SUPER_ADMIN,ROLE_ADMIN' where PAGE_TYPE = 'postbatch';

SET @Next_ID=(SELECT MAX(PAGE_ACCESS_ID)+1 FROM PAGE_ACCESS);
INSERT INTO PAGE_ACCESS (PAGE_ACCESS_ID, ACCESS_TYPE, PAGE_TYPE, ROLE, SITE_NAME) VALUES (@Next_ID, 'ALLOWED', 'executeBatch', 'ROLE_SUPER_ADMIN', null);

SET @Next_ID=(SELECT MAX(PAGE_ACCESS_ID)+1 FROM PAGE_ACCESS);
INSERT INTO PAGE_ACCESS (PAGE_ACCESS_ID, ACCESS_TYPE, PAGE_TYPE, ROLE, SITE_NAME) VALUES (@Next_ID, 'ALLOWED', 'deleteBatch', 'ROLE_SUPER_ADMIN', null);

SET @Next_ID=(SELECT MAX(PAGE_ACCESS_ID)+1 FROM PAGE_ACCESS);
INSERT INTO PAGE_ACCESS (PAGE_ACCESS_ID, ACCESS_TYPE, PAGE_TYPE, ROLE, SITE_NAME) VALUES (@Next_ID, 'ALLOWED', 'createBatch', 'ROLE_SUPER_ADMIN,ROLE_ADMIN', null);

/* TANGERINE-1599 */
UPDATE FIELD_DEFINITION set DEFAULT_LABEL = 'Original Amount' WHERE FIELD_DEFINITION_ID IN ('gift.amount', 'gift.amountReadOnly');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('gift.adjustedAmountReadOnly', 'gift', 'adjustedAmount', 'Adjusted Amount', 'NUMBER_DISPLAY');

INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (1000008, 'gift.adjustedAmountReadOnly', 1500);

INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER)
SELECT SECTION_DEFINITION_ID, 'gift.adjustedAmountReadOnly', 1500 FROM SECTION_DEFINITION WHERE SECTION_NAME='gift.donation' AND PAGE_TYPE='gift';

INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER)
SELECT SECTION_DEFINITION_ID, 'gift.adjustedAmountReadOnly', 1500 FROM SECTION_DEFINITION WHERE SECTION_NAME='gift.donation' AND PAGE_TYPE='giftPaid';

INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER)
SELECT SECTION_DEFINITION_ID, 'gift.adjustedAmountReadOnly', 1500 FROM SECTION_DEFINITION WHERE SECTION_NAME='gift.donation' AND PAGE_TYPE='giftPosted';

/* TANGERINE-1592 */
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES
('paymentSource.checkHolderName', 'paymentSource', 'checkHolderName', 'Check Holder Name', 'TEXT');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES
('paymentSource.checkRoutingNumber', 'paymentSource', 'checkRoutingNumber', 'Routing Number', 'TEXT');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES
('paymentSource.checkAccountNumber', 'paymentSource', 'checkAccountNumber', 'Account Number', 'TEXT');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES
('paymentSource.checkHolderNameReadOnly', 'paymentSource', 'checkHolderName', 'Check Holder Name', 'existingCheck', 'READ_ONLY_TEXT');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES
('paymentSource.checkRoutingNumberReadOnly', 'paymentSource', 'checkRoutingNumberDisplay', 'Routing Number', 'existingCheck', 'READ_ONLY_TEXT');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES
('paymentSource.checkAccountNumberReadOnly', 'paymentSource', 'checkAccountNumberDisplay', 'Account Number', 'existingCheck', 'READ_ONLY_TEXT');


INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES 
('gift.paymentSource.checkHolderName', 'gift', 'paymentSource', 'Check Holder Name', 'newCheck', 'TEXT');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES 
('gift.paymentSource.checkAccountNumber', 'gift', 'paymentSource', 'Account Number', 'newCheck', 'TEXT');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES
('gift.paymentSource.checkRoutingNumber', 'gift', 'paymentSource', 'Routing Number', 'newCheck', 'TEXT');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES
('gift.paymentSource.checkHolderNameReadOnly', 'gift', 'paymentSource', 'Check Holder Name', 'existingCheck', 'READ_ONLY_TEXT');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES
('gift.paymentSource.checkAccountNumberReadOnly', 'gift', 'paymentSource', 'Account Number', 'existingCheck', 'READ_ONLY_TEXT');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES
('gift.paymentSource.checkRoutingNumberReadOnly', 'gift', 'paymentSource', 'Routing Number', 'existingCheck', 'READ_ONLY_TEXT');


INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES
('adjustedGift.paymentSource.checkHolderNameReadOnly', 'adjustedGift', 'paymentSource', 'Check Holder Name', 'READ_ONLY_TEXT');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES
('adjustedGift.paymentSource.checkAccountNumberReadOnly', 'adjustedGift', 'paymentSource', 'Account Number', 'READ_ONLY_TEXT');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES
('adjustedGift.paymentSource.checkRoutingNumberReadOnly', 'adjustedGift', 'paymentSource', 'Routing Number', 'READ_ONLY_TEXT');


SET @Next_ID=(SELECT MAX(SECTION_DEFINITION_ID)+1 FROM SECTION_DEFINITION);
INSERT INTO SECTION_DEFINITION (SECTION_DEFINITION_ID, PAGE_TYPE, SECTION_NAME, DEFAULT_LABEL, SECTION_ORDER, LAYOUT_TYPE, ROLE)
VALUES (@Next_ID, 'paymentManager', 'paymentSource.check', 'Check', 4, 'ONE_COLUMN_HIDDEN', 'ROLE_USER');

INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER)
VALUES (@Next_ID, 'paymentSource.checkHolderName', 1000);

INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER)
VALUES (@Next_ID, 'paymentSource.checkRoutingNumber', 2000);

INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER)
VALUES (@Next_ID, 'paymentSource.checkAccountNumber', 3000);

SET @Next_ID=(SELECT MAX(SECTION_DEFINITION_ID)+1 FROM SECTION_DEFINITION);
INSERT INTO SECTION_DEFINITION (SECTION_DEFINITION_ID, PAGE_TYPE, SECTION_NAME, DEFAULT_LABEL, SECTION_ORDER, LAYOUT_TYPE, ROLE) VALUES
(@Next_ID, 'paymentManagerEdit', 'paymentSource.check', 'Check', 4, 'ONE_COLUMN_HIDDEN', 'ROLE_USER');

INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER) VALUES
(@Next_ID, 'paymentSource.checkHolderNameReadOnly', 1000);

INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER) VALUES
(@Next_ID, 'paymentSource.checkRoutingNumberReadOnly', 2000);

INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER) VALUES
(@Next_ID, 'paymentSource.checkAccountNumberReadOnly', 3000);



INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, FIELD_ORDER)
SELECT SECTION_DEFINITION_ID, 'gift.paymentSource.checkHolderName', 'paymentSource.checkHolderName', 3000 FROM SECTION_DEFINITION WHERE SECTION_NAME='gift.check' AND PAGE_TYPE='gift';

INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, FIELD_ORDER)
SELECT SECTION_DEFINITION_ID, 'gift.paymentSource.checkRoutingNumber', 'paymentSource.checkRoutingNumber', 4000 FROM SECTION_DEFINITION WHERE SECTION_NAME='gift.check' AND PAGE_TYPE='gift';

INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID,FIELD_ORDER)
SELECT SECTION_DEFINITION_ID, 'gift.paymentSource.checkAccountNumber', 'paymentSource.checkAccountNumber', 5000 FROM SECTION_DEFINITION WHERE SECTION_NAME='gift.check' AND PAGE_TYPE='gift';

INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, FIELD_ORDER)
SELECT SECTION_DEFINITION_ID, 'gift.paymentSource.checkHolderNameReadOnly', 'paymentSource.checkHolderNameReadOnly', 6000 FROM SECTION_DEFINITION WHERE SECTION_NAME='gift.check' AND PAGE_TYPE='gift';

INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID,FIELD_ORDER)
SELECT SECTION_DEFINITION_ID, 'gift.paymentSource.checkRoutingNumberReadOnly', 'paymentSource.checkRoutingNumberReadOnly', 7000 FROM SECTION_DEFINITION WHERE SECTION_NAME='gift.check' AND PAGE_TYPE='gift';

INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID,FIELD_ORDER)
SELECT SECTION_DEFINITION_ID, 'gift.paymentSource.checkAccountNumberReadOnly', 'paymentSource.checkAccountNumberReadOnly', 8000 FROM SECTION_DEFINITION WHERE SECTION_NAME='gift.check' AND PAGE_TYPE='gift';



INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, FIELD_ORDER)
SELECT SECTION_DEFINITION_ID, 'gift.paymentSource.checkHolderNameReadOnly', 'paymentSource.checkHolderNameReadOnly', 3000 FROM SECTION_DEFINITION WHERE SECTION_NAME='gift.check' AND PAGE_TYPE='giftPaid';

INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, FIELD_ORDER)
SELECT SECTION_DEFINITION_ID, 'gift.paymentSource.checkRoutingNumberReadOnly', 'paymentSource.checkRoutingNumberReadOnly', 4000 FROM SECTION_DEFINITION WHERE SECTION_NAME='gift.check' AND PAGE_TYPE='giftPaid';

INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, FIELD_ORDER)
SELECT SECTION_DEFINITION_ID, 'gift.paymentSource.checkAccountNumberReadOnly', 'paymentSource.checkAccountNumberReadOnly', 5000 FROM SECTION_DEFINITION WHERE SECTION_NAME='gift.check' AND PAGE_TYPE='giftPaid';



INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, FIELD_ORDER)
SELECT SECTION_DEFINITION_ID, 'gift.paymentSource.checkHolderNameReadOnly', 'paymentSource.checkHolderNameReadOnly', 3000 FROM SECTION_DEFINITION WHERE SECTION_NAME='gift.check' AND PAGE_TYPE='giftPosted';

INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, FIELD_ORDER)
SELECT SECTION_DEFINITION_ID, 'gift.paymentSource.checkRoutingNumberReadOnly', 'paymentSource.checkRoutingNumberReadOnly', 4000 FROM SECTION_DEFINITION WHERE SECTION_NAME='gift.check' AND PAGE_TYPE='giftPosted';

INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, FIELD_ORDER)
SELECT SECTION_DEFINITION_ID, 'gift.paymentSource.checkAccountNumberReadOnly', 'paymentSource.checkAccountNumberReadOnly', 5000 FROM SECTION_DEFINITION WHERE SECTION_NAME='gift.check' AND PAGE_TYPE='giftPosted';


INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, FIELD_ORDER)
SELECT SECTION_DEFINITION_ID, 'adjustedGift.paymentSource.checkHolderNameReadOnly', 'paymentSource.checkHolderNameReadOnly', 3000 FROM SECTION_DEFINITION WHERE SECTION_NAME='adjustedGift.check' AND PAGE_TYPE='adjustedGift';

INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, FIELD_ORDER)
SELECT SECTION_DEFINITION_ID, 'adjustedGift.paymentSource.checkRoutingNumberReadOnly', 'paymentSource.checkRoutingNumberReadOnly', 4000 FROM SECTION_DEFINITION WHERE SECTION_NAME='adjustedGift.check' AND PAGE_TYPE='adjustedGift';

INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, FIELD_ORDER)
SELECT SECTION_DEFINITION_ID, 'adjustedGift.paymentSource.checkAccountNumberReadOnly', 'paymentSource.checkAccountNumberReadOnly', 5000 FROM SECTION_DEFINITION WHERE SECTION_NAME='adjustedGift.check' AND PAGE_TYPE='adjustedGift';


INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, FIELD_ORDER)
SELECT SECTION_DEFINITION_ID, 'adjustedGift.paymentSource.checkHolderNameReadOnly', 'paymentSource.checkHolderNameReadOnly', 3000 FROM SECTION_DEFINITION WHERE SECTION_NAME='adjustedGift.check' AND PAGE_TYPE='adjustedGiftPaid';

INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, FIELD_ORDER)
SELECT SECTION_DEFINITION_ID, 'adjustedGift.paymentSource.checkRoutingNumberReadOnly', 'paymentSource.checkRoutingNumberReadOnly', 4000 FROM SECTION_DEFINITION WHERE SECTION_NAME='adjustedGift.check' AND PAGE_TYPE='adjustedGiftPaid';

INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, FIELD_ORDER)
SELECT SECTION_DEFINITION_ID, 'adjustedGift.paymentSource.checkAccountNumberReadOnly', 'paymentSource.checkAccountNumberReadOnly', 5000 FROM SECTION_DEFINITION WHERE SECTION_NAME='adjustedGift.check' AND PAGE_TYPE='adjustedGiftPaid';


INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, FIELD_ORDER)
SELECT SECTION_DEFINITION_ID, 'adjustedGift.paymentSource.checkHolderNameReadOnly', 'paymentSource.checkHolderNameReadOnly', 3000 FROM SECTION_DEFINITION WHERE SECTION_NAME='adjustedGift.check' AND PAGE_TYPE='adjustedGiftPosted';

INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, FIELD_ORDER)
SELECT SECTION_DEFINITION_ID, 'adjustedGift.paymentSource.checkRoutingNumberReadOnly', 'paymentSource.checkRoutingNumberReadOnly', 4000 FROM SECTION_DEFINITION WHERE SECTION_NAME='adjustedGift.check' AND PAGE_TYPE='adjustedGiftPosted';

INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, FIELD_ORDER) 
SELECT SECTION_DEFINITION_ID, 'adjustedGift.paymentSource.checkAccountNumberReadOnly', 'paymentSource.checkAccountNumberReadOnly', 5000 FROM SECTION_DEFINITION WHERE SECTION_NAME='adjustedGift.check' AND PAGE_TYPE='adjustedGiftPosted';



UPDATE PICKLIST_ITEM set REFERENCE_VALUE = 'li:has(#paymentSource-td-id)' WHERE ITEM_NAME = 'Check' AND PICKLIST_ID IN
(SELECT PICKLIST_ID FROM PICKLIST WHERE PICKLIST_NAME_ID = 'gift.paymentType' AND PICKLIST_NAME = 'paymentType');

INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, REFERENCE_VALUE, ITEM_ORDER)
SELECT PICKLIST_ID, 'Check', 'Check', '.paymentSource_check', 3 FROM PICKLIST WHERE PICKLIST_NAME_ID = 'paymentSource.paymentType' AND PICKLIST_NAME = 'paymentType'; 

UPDATE PICKLIST_ITEM set REFERENCE_VALUE = 'li:has(#paymentSource-td-id),.adjustedGift_check' WHERE ITEM_NAME = 'Check' AND PICKLIST_ID IN
(SELECT PICKLIST_ID FROM PICKLIST WHERE PICKLIST_NAME_ID = 'adjustedGift.paymentType' AND PICKLIST_NAME = 'paymentType');


INSERT INTO `ENTITY_DEFAULT` (`DEFAULT_VALUE`,`ENTITY_FIELD_NAME`,`ENTITY_TYPE`,`CONDITION_EXP`,`SITE_NAME`)
VALUES ('bean:constituent.firstLast', 'checkHolderName', 'paymentSource', NULL, NULL);


SET @Next_ID=(SELECT MAX(FIELD_REQUIRED_ID)+1 FROM FIELD_REQUIRED);

INSERT INTO FIELD_REQUIRED (FIELD_REQUIRED_ID, SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, REQUIRED) VALUES
(@Next_ID, null, 'paymentSource.check', 'paymentSource.checkHolderName', TRUE);

INSERT INTO FIELD_CONDITION (DEPENDENT_FIELD_DEFINITION_ID, DEPENDENT_VALUE, FIELD_REQUIRED_ID) VALUES ('paymentSource.paymentType', 'Check', @Next_ID);


SET @Next_ID=(SELECT MAX(FIELD_REQUIRED_ID)+1 FROM FIELD_REQUIRED);

INSERT INTO FIELD_REQUIRED (FIELD_REQUIRED_ID, SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, REQUIRED) VALUES
(@Next_ID, null, 'paymentSource.check', 'paymentSource.checkRoutingNumber', TRUE);

INSERT INTO FIELD_CONDITION (DEPENDENT_FIELD_DEFINITION_ID, DEPENDENT_VALUE, FIELD_REQUIRED_ID) VALUES ('paymentSource.paymentType', 'Check', @Next_ID);


SET @Next_ID=(SELECT MAX(FIELD_REQUIRED_ID)+1 FROM FIELD_REQUIRED);

INSERT INTO FIELD_REQUIRED (FIELD_REQUIRED_ID, SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, REQUIRED) VALUES
(@Next_ID, null, 'paymentSource.check', 'paymentSource.checkAccountNumber', TRUE);

INSERT INTO FIELD_CONDITION (DEPENDENT_FIELD_DEFINITION_ID, DEPENDENT_VALUE, FIELD_REQUIRED_ID) VALUES ('paymentSource.paymentType', 'Check', @Next_ID);


INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER)
SELECT SECTION_DEFINITION_ID, 'paymentSource.checkHolderName', 10000 FROM SECTION_DEFINITION WHERE SECTION_NAME='paymentSourceList.resultsInfo' AND PAGE_TYPE='paymentSourceList';

INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER)
SELECT SECTION_DEFINITION_ID, 'paymentSource.checkAccountNumberReadOnly', 11000 FROM SECTION_DEFINITION WHERE SECTION_NAME='paymentSourceList.resultsInfo' AND PAGE_TYPE='paymentSourceList';

INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER)
SELECT SECTION_DEFINITION_ID, 'paymentSource.checkRoutingNumberReadOnly', 12000 FROM SECTION_DEFINITION WHERE SECTION_NAME='paymentSourceList.resultsInfo' AND PAGE_TYPE='paymentSourceList';

UPDATE SECTION_FIELD SET FIELD_ORDER = 13000 WHERE FIELD_DEFINITION_ID = 'paymentSource.inactive'
AND SECTION_DEFINITION_ID = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='paymentSourceList.resultsInfo' AND PAGE_TYPE='paymentSourceList');



/* TANGERINE-1534 */
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES
('gift.checkDate', 'gift', 'checkDate', 'Check Date', '', 'DATE');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES
('gift.checkDateReadOnly', 'gift', 'checkDate', 'Check Date', '', 'DATE_DISPLAY');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES
('adjustedGift.checkDate', 'adjustedGift', 'checkDate', 'Check Date', '', 'DATE');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES
('adjustedGift.checkDateReadOnly', 'adjustedGift', 'checkDate', 'Check Date', '', 'DATE_DISPLAY');


INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER)
SELECT SECTION_DEFINITION_ID, 'gift.checkDate', 2000 FROM SECTION_DEFINITION WHERE SECTION_NAME='gift.check' AND PAGE_TYPE='gift';

INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER)
SELECT SECTION_DEFINITION_ID, 'gift.checkDate', 2000 FROM SECTION_DEFINITION WHERE SECTION_NAME='gift.check' AND PAGE_TYPE='giftPaid';

INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER)
SELECT SECTION_DEFINITION_ID, 'gift.checkDateReadOnly', 2000 FROM SECTION_DEFINITION WHERE SECTION_NAME='gift.check' AND PAGE_TYPE='giftPosted';


INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER)
SELECT SECTION_DEFINITION_ID, 'adjustedGift.checkDate', 2000 FROM SECTION_DEFINITION WHERE SECTION_NAME='adjustedGift.check' AND PAGE_TYPE='adjustedGift';

INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER)
SELECT SECTION_DEFINITION_ID, 'adjustedGift.checkDate', 2000 FROM SECTION_DEFINITION WHERE SECTION_NAME='adjustedGift.check' AND PAGE_TYPE='adjustedGiftPaid';

INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER)
SELECT SECTION_DEFINITION_ID, 'adjustedGift.checkDateReadOnly', 2000 FROM SECTION_DEFINITION WHERE SECTION_NAME='adjustedGift.check' AND PAGE_TYPE='adjustedGiftPosted';

insert into ENTITY_DEFAULT (DEFAULT_VALUE, ENTITY_FIELD_NAME, ENTITY_TYPE)
values ('bean:donationDate', 'checkDate', 'gift');



-- BEGIN ROLLUPS --

-- NAME_IDs for ATTRIBUTES and SERIES can be used by code to reference the specific attributes/series. ATTRIBUTE/SERIES_DESC are the labels seen by the user in the UI.

INSERT INTO ROLLUP_ATTRIBUTE (ROLLUP_ATTRIBUTE_ID, ATTRIBUTE_NAME_ID, ATTRIBUTE_DESC, ROLLUP_ENTITY_TYPE, ROLLUP_STAT_TYPE, FIELD_NAME, CUSTOM_FIELD_NAME, SITE_NAME) VALUES (1000001, 'Gift Amount', 'Gift Amount', 'constituent' , 'ROLLUP_GIFT_BY_CONSTITUENT', 'ADJUSTED_AMOUNT', NULL, NULL);
INSERT INTO ROLLUP_ATTRIBUTE (ROLLUP_ATTRIBUTE_ID, ATTRIBUTE_NAME_ID, ATTRIBUTE_DESC, ROLLUP_ENTITY_TYPE, ROLLUP_STAT_TYPE, FIELD_NAME, CUSTOM_FIELD_NAME, SITE_NAME) VALUES (1000002, 'Gift In Kind Amount', 'Gift In Kind Amount', 'constituent', 'ROLLUP_GIFT_IN_KIND_BY_CONSTITUENT', 'FAIR_MARKET_VALUE', NULL, NULL);
INSERT INTO ROLLUP_ATTRIBUTE (ROLLUP_ATTRIBUTE_ID, ATTRIBUTE_NAME_ID, ATTRIBUTE_DESC, ROLLUP_ENTITY_TYPE, ROLLUP_STAT_TYPE, FIELD_NAME, CUSTOM_FIELD_NAME, SITE_NAME) VALUES (1000003, 'Soft Gift Amount', 'Soft Gift Amount', 'constituent', 'ROLLUP_GIFT_DISTRO_LINE_AMOUNT_BY_CF_CONSTITUENT', NULL, 'onBehalfOf', NULL);

INSERT INTO ROLLUP_ATTRIBUTE (ROLLUP_ATTRIBUTE_ID, ATTRIBUTE_NAME_ID, ATTRIBUTE_DESC, ROLLUP_ENTITY_TYPE, ROLLUP_STAT_TYPE, FIELD_NAME, CUSTOM_FIELD_NAME, SITE_NAME) VALUES (1000005, 'Projected Pledge Amount', 'Projected Pledge Amount', 'constituent', 'ROLLUP_PLEDGE_SCHEDULED_PAYMENTS_BY_CONSTITUENT', NULL, NULL, NULL);
INSERT INTO ROLLUP_ATTRIBUTE (ROLLUP_ATTRIBUTE_ID, ATTRIBUTE_NAME_ID, ATTRIBUTE_DESC, ROLLUP_ENTITY_TYPE, ROLLUP_STAT_TYPE, FIELD_NAME, CUSTOM_FIELD_NAME, SITE_NAME) VALUES (1000006, 'Projected Recurring Gift Amount', 'Projected Recurring Gift Amount', 'constituent', 'ROLLUP_RECURRING_GIFT_SCHEDULED_PAYMENTS_BY_CONSTITUENT', NULL, NULL, NULL);

-- Second-level rollups by constituent
INSERT INTO ROLLUP_ATTRIBUTE (ROLLUP_ATTRIBUTE_ID, ATTRIBUTE_NAME_ID, ATTRIBUTE_DESC, ROLLUP_ENTITY_TYPE, ROLLUP_STAT_TYPE, FIELD_NAME, CUSTOM_FIELD_NAME, SITE_NAME) VALUES (1000101, 'All Actuals', 'All Actuals', 'constituent', 'ROLLUP_ACTUALS_BY_CONSTITUENT', NULL, NULL, NULL);
INSERT INTO ROLLUP_ATTRIBUTE (ROLLUP_ATTRIBUTE_ID, ATTRIBUTE_NAME_ID, ATTRIBUTE_DESC, ROLLUP_ENTITY_TYPE, ROLLUP_STAT_TYPE, FIELD_NAME, CUSTOM_FIELD_NAME, SITE_NAME) VALUES (1000102, 'All Projected', 'All Projected', 'constituent', 'ROLLUP_PROJECTEDS_BY_CONSTITUENT', NULL, NULL, NULL);


INSERT INTO ROLLUP_ATTRIBUTE (ROLLUP_ATTRIBUTE_ID, ATTRIBUTE_NAME_ID, ATTRIBUTE_DESC, ROLLUP_ENTITY_TYPE, ROLLUP_STAT_TYPE, FIELD_NAME, CUSTOM_FIELD_NAME, SITE_NAME) VALUES (1000201, 'Projected Pledge Amount Summary', 'Projected Pledge Amount', 'site', 'ROLLUP_PLEDGE_SCHEDULED_PAYMENTS_BY_ALL', NULL, NULL, NULL);
INSERT INTO ROLLUP_ATTRIBUTE (ROLLUP_ATTRIBUTE_ID, ATTRIBUTE_NAME_ID, ATTRIBUTE_DESC, ROLLUP_ENTITY_TYPE, ROLLUP_STAT_TYPE, FIELD_NAME, CUSTOM_FIELD_NAME, SITE_NAME) VALUES (1000202, 'Projected Recurring Gift Amount Summary', 'Projected Recurring Gift Amount', 'site', 'ROLLUP_RECURRING_GIFT_SCHEDULED_PAYMENTS_BY_ALL', NULL, NULL, NULL);


-- Gifts + Adjusted Gifts, Gifts In Kind, Soft Gifts
INSERT INTO ROLLUP_SERIES (ROLLUP_SERIES_ID, SERIES_NAME_ID, SERIES_DESC, SERIES_TYPE, MAINTAIN_PERIODS, FUTURE_PERIODS, KEEP_UNMAINTAINED, SITE_NAME) VALUES (2000001, 'Calendar Year', 'Calendar Year', 'CALENDAR_YEAR', 5, 0, FALSE, NULL);
INSERT INTO ROLLUP_SERIES_X_ATTRIBUTE (ROLLUP_SERIES_ID, ROLLUP_ATTRIBUTE_ID) VALUES (2000001, 1000001);
INSERT INTO ROLLUP_SERIES_X_ATTRIBUTE (ROLLUP_SERIES_ID, ROLLUP_ATTRIBUTE_ID) VALUES (2000001, 1000002);
INSERT INTO ROLLUP_SERIES_X_ATTRIBUTE (ROLLUP_SERIES_ID, ROLLUP_ATTRIBUTE_ID) VALUES (2000001, 1000003);
INSERT INTO ROLLUP_SERIES_X_ATTRIBUTE (ROLLUP_SERIES_ID, ROLLUP_ATTRIBUTE_ID) VALUES (2000001, 1000101);
INSERT INTO ROLLUP_SERIES (ROLLUP_SERIES_ID, SERIES_NAME_ID, SERIES_DESC, SERIES_TYPE, MAINTAIN_PERIODS, FUTURE_PERIODS, KEEP_UNMAINTAINED, SITE_NAME) VALUES (2000002, 'Fiscal Year', 'Fiscal Year', 'FISCAL_YEAR', 5, 0, FALSE, NULL);
INSERT INTO ROLLUP_SERIES_X_ATTRIBUTE (ROLLUP_SERIES_ID, ROLLUP_ATTRIBUTE_ID) VALUES (2000002, 1000001);
INSERT INTO ROLLUP_SERIES_X_ATTRIBUTE (ROLLUP_SERIES_ID, ROLLUP_ATTRIBUTE_ID) VALUES (2000002, 1000002);
INSERT INTO ROLLUP_SERIES_X_ATTRIBUTE (ROLLUP_SERIES_ID, ROLLUP_ATTRIBUTE_ID) VALUES (2000002, 1000003);
INSERT INTO ROLLUP_SERIES_X_ATTRIBUTE (ROLLUP_SERIES_ID, ROLLUP_ATTRIBUTE_ID) VALUES (2000002, 1000101);
INSERT INTO ROLLUP_SERIES (ROLLUP_SERIES_ID, SERIES_NAME_ID, SERIES_DESC, SERIES_TYPE, MAINTAIN_PERIODS, FUTURE_PERIODS, KEEP_UNMAINTAINED, SITE_NAME) VALUES (2000003, 'Total Lifetime', 'Total Lifetime', 'ALLTIME', 1, 0, FALSE, NULL);
INSERT INTO ROLLUP_SERIES_X_ATTRIBUTE (ROLLUP_SERIES_ID, ROLLUP_ATTRIBUTE_ID) VALUES (2000003, 1000001);
INSERT INTO ROLLUP_SERIES_X_ATTRIBUTE (ROLLUP_SERIES_ID, ROLLUP_ATTRIBUTE_ID) VALUES (2000003, 1000002);
INSERT INTO ROLLUP_SERIES_X_ATTRIBUTE (ROLLUP_SERIES_ID, ROLLUP_ATTRIBUTE_ID) VALUES (2000003, 1000003);
INSERT INTO ROLLUP_SERIES_X_ATTRIBUTE (ROLLUP_SERIES_ID, ROLLUP_ATTRIBUTE_ID) VALUES (2000003, 1000101);

-- Scheduled Payments by constituent
INSERT INTO ROLLUP_SERIES (ROLLUP_SERIES_ID, SERIES_NAME_ID, SERIES_DESC, SERIES_TYPE, MAINTAIN_PERIODS, FUTURE_PERIODS, KEEP_UNMAINTAINED, SITE_NAME) VALUES (2000004, 'Future Calendar Year', 'Calendar Year', 'CALENDAR_YEAR', 2, 1, FALSE, NULL);
INSERT INTO ROLLUP_SERIES_X_ATTRIBUTE (ROLLUP_SERIES_ID, ROLLUP_ATTRIBUTE_ID) VALUES (2000004, 1000005);
INSERT INTO ROLLUP_SERIES_X_ATTRIBUTE (ROLLUP_SERIES_ID, ROLLUP_ATTRIBUTE_ID) VALUES (2000004, 1000006);
INSERT INTO ROLLUP_SERIES_X_ATTRIBUTE (ROLLUP_SERIES_ID, ROLLUP_ATTRIBUTE_ID) VALUES (2000004, 1000102);
INSERT INTO ROLLUP_SERIES (ROLLUP_SERIES_ID, SERIES_NAME_ID, SERIES_DESC, SERIES_TYPE, MAINTAIN_PERIODS, FUTURE_PERIODS, KEEP_UNMAINTAINED, SITE_NAME) VALUES (2000005, 'Future Fiscal Year', 'Fiscal Year', 'FISCAL_YEAR', 2, 1, FALSE, NULL);
INSERT INTO ROLLUP_SERIES_X_ATTRIBUTE (ROLLUP_SERIES_ID, ROLLUP_ATTRIBUTE_ID) VALUES (2000005, 1000005);
INSERT INTO ROLLUP_SERIES_X_ATTRIBUTE (ROLLUP_SERIES_ID, ROLLUP_ATTRIBUTE_ID) VALUES (2000005, 1000006);
INSERT INTO ROLLUP_SERIES_X_ATTRIBUTE (ROLLUP_SERIES_ID, ROLLUP_ATTRIBUTE_ID) VALUES (2000005, 1000102);

-- Monthly gift history by constituent
INSERT INTO ROLLUP_SERIES (ROLLUP_SERIES_ID, SERIES_NAME_ID, SERIES_DESC, SERIES_TYPE, MAINTAIN_PERIODS, FUTURE_PERIODS, KEEP_UNMAINTAINED, SITE_NAME) VALUES (2000007, 'Monthly Total', 'Monthly Total', 'MONTH', 13, 0, FALSE, NULL);
INSERT INTO ROLLUP_SERIES_X_ATTRIBUTE (ROLLUP_SERIES_ID, ROLLUP_ATTRIBUTE_ID) VALUES (2000007, 1000001);
INSERT INTO ROLLUP_SERIES_X_ATTRIBUTE (ROLLUP_SERIES_ID, ROLLUP_ATTRIBUTE_ID) VALUES (2000007, 1000002);
INSERT INTO ROLLUP_SERIES_X_ATTRIBUTE (ROLLUP_SERIES_ID, ROLLUP_ATTRIBUTE_ID) VALUES (2000007, 1000003);
INSERT INTO ROLLUP_SERIES_X_ATTRIBUTE (ROLLUP_SERIES_ID, ROLLUP_ATTRIBUTE_ID) VALUES (2000007, 1000101);
INSERT INTO ROLLUP_SERIES (ROLLUP_SERIES_ID, SERIES_NAME_ID, SERIES_DESC, SERIES_TYPE, MAINTAIN_PERIODS, FUTURE_PERIODS, KEEP_UNMAINTAINED, SITE_NAME) VALUES (2000008, 'Past Year Total', 'Past Year Total', 'ROLLING_YEAR', 1, 0, FALSE, NULL);
INSERT INTO ROLLUP_SERIES_X_ATTRIBUTE (ROLLUP_SERIES_ID, ROLLUP_ATTRIBUTE_ID) VALUES (2000008, 1000001);
INSERT INTO ROLLUP_SERIES_X_ATTRIBUTE (ROLLUP_SERIES_ID, ROLLUP_ATTRIBUTE_ID) VALUES (2000008, 1000002);
INSERT INTO ROLLUP_SERIES_X_ATTRIBUTE (ROLLUP_SERIES_ID, ROLLUP_ATTRIBUTE_ID) VALUES (2000008, 1000003);
INSERT INTO ROLLUP_SERIES_X_ATTRIBUTE (ROLLUP_SERIES_ID, ROLLUP_ATTRIBUTE_ID) VALUES (2000008, 1000101);


-- All Scheduled Payments Summary
INSERT INTO ROLLUP_SERIES (ROLLUP_SERIES_ID, SERIES_NAME_ID, SERIES_DESC, SERIES_TYPE, MAINTAIN_PERIODS, FUTURE_PERIODS, KEEP_UNMAINTAINED, SITE_NAME) VALUES (2000200, 'Future Monthly Total', 'Monthly Total', 'MONTH', 12, 11, FALSE, NULL);
INSERT INTO ROLLUP_SERIES_X_ATTRIBUTE (ROLLUP_SERIES_ID, ROLLUP_ATTRIBUTE_ID) VALUES (2000200, 1000201);
INSERT INTO ROLLUP_SERIES_X_ATTRIBUTE (ROLLUP_SERIES_ID, ROLLUP_ATTRIBUTE_ID) VALUES (2000200, 1000202);



-- END ROLLUPS --

-- Update GIFT.ADJUSTED_GIFT column
UPDATE GIFT g SET g.ADJUSTED_AMOUNT = (
	select SUM(ag.ADJUSTED_AMOUNT) from ADJUSTED_GIFT ag where g.GIFT_ID = ag.GIFT_ID and ag.ADJUSTED_STATUS = 'Paid'
);
UPDATE GIFT SET ADJUSTED_AMOUNT = ifnull(ADJUSTED_AMOUNT,0) + AMOUNT;

UPDATE GIFT g SET g.DEDUCTIBLE_AMOUNT = (
	select SUM(dl.AMOUNT) from DISTRO_LINE dl, CUSTOM_FIELD cf where g.GIFT_ID = dl.GIFT_ID and cf.ENTITY_ID = dl.DISTRO_LINE_ID and cf.ENTITY_TYPE = 'distributionline' and cf.FIELD_NAME = 'taxDeductible' and cf.FIELD_VALUE='true'
);


-- Soft Gift List
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('gift.aliasId', 'gift', 'aliasId', 'ID', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('adjustedGift.aliasId', 'adjustedGift', 'aliasId', 'ID', 'READ_ONLY_TEXT');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('gift.firstDistributionLine.amount', 'gift', 'firstDistributionLine.amount', 'Amount', 'NUMBER');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('adjustedGift.firstDistributionLine.amount', 'adjustedGift', 'firstDistributionLine.amount', 'Amount', 'NUMBER');

INSERT INTO SECTION_DEFINITION (SECTION_DEFINITION_ID, PAGE_TYPE, SECTION_NAME, DEFAULT_LABEL, SECTION_ORDER, LAYOUT_TYPE, ROLE) VALUES (1000018, 'softGiftList', 'softGiftList.resultsInfo', 'Soft Gifts', 1, 'TREE_GRID', 'ROLE_USER');
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (1000018, 'gift.id', 100);
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (1000018, 'gift.aliasId', 200);
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (1000018, 'gift.donationDate', 1000);
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (1000018, 'gift.firstDistributionLine.amount', 2000);
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (1000018, 'gift.currencyCode', 3000);
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (1000018, 'gift.paymentType', 4000);
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (1000018, 'gift.paymentStatusReadOnly', 5000);
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (1000018, 'gift.authCodeReadOnly', 6000);
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (1000018, 'gift.txRefNumReadOnly', 7000);
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (1000018, 'gift.comments', 8000);

INSERT INTO SECTION_DEFINITION (SECTION_DEFINITION_ID, PAGE_TYPE, SECTION_NAME, DEFAULT_LABEL, SECTION_ORDER, LAYOUT_TYPE, ROLE) VALUES (1000019, 'softAdjustedGiftList', 'softAdjustedGiftList.resultsInfo', ' ', 2, 'TREE_GRID_HIDDEN_ROW', 'ROLE_USER');
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (1000019, 'adjustedGift.id', 100);
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (1000019, 'adjustedGift.aliasId', 200);
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (1000019, 'adjustedGift.transactionDate', 1000);
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (1000019, 'adjustedGift.firstDistributionLine.amount', 2000);
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (1000019, 'adjustedGift.currencyCodeReadOnly', 3000);
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (1000019, 'adjustedGift.paymentType', 4000);
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (1000019, 'adjustedGift.paymentStatusReadOnly', 5000);
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (1000019, 'adjustedGift.authCodeReadOnly', 6000);
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (1000019, 'adjustedGift.txRefNumReadOnly', 7000);
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (1000019, 'adjustedGift.comments', 8000);


-- Age

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('constituent.customFieldMap[age:individual.birthDate]', 'constituent', 'customFieldMap[age:individual.birthDate]', 'Age', 'READ_ONLY_TEXT', 'individual');
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (1000110, 'constituent.customFieldMap[age:individual.birthDate]', 8000);


-- RULES

INSERT INTO PAGE_ACCESS (PAGE_ACCESS_ID, ACCESS_TYPE, PAGE_TYPE, ROLE, SITE_NAME) VALUES (1000015, 'ALLOWED', 'manageRules', 'ROLE_SUPER_ADMIN', null);

INSERT INTO CACHE_GROUP (CACHE_GROUP_ID, UPDATE_DATE) VALUES ('RULE_GENERATED_CODE', now());

INSERT INTO RULE_EVENT_TYPE (RULE_EVENT_TYPE_ID, RULE_EVENT_TYPE_NAME_ID, RULE_EVENT_TYPE_DESC) VALUES (1,'constituent-save', 'Save Constituent');
INSERT INTO RULE_EVENT_TYPE (RULE_EVENT_TYPE_ID, RULE_EVENT_TYPE_NAME_ID, RULE_EVENT_TYPE_DESC) VALUES (2,'gift-save', 'Save Gift');
INSERT INTO RULE_EVENT_TYPE (RULE_EVENT_TYPE_ID, RULE_EVENT_TYPE_NAME_ID, RULE_EVENT_TYPE_DESC) VALUES (3,'touchpoint-save', 'Save Touchpoint');
INSERT INTO RULE_EVENT_TYPE (RULE_EVENT_TYPE_ID, RULE_EVENT_TYPE_NAME_ID, RULE_EVENT_TYPE_DESC) VALUES (4,'email', 'Email');
INSERT INTO RULE_EVENT_TYPE (RULE_EVENT_TYPE_ID, RULE_EVENT_TYPE_NAME_ID, RULE_EVENT_TYPE_DESC) VALUES (5,'payment-processing', 'Payment Processing');
INSERT INTO RULE_EVENT_TYPE (RULE_EVENT_TYPE_ID, RULE_EVENT_TYPE_NAME_ID, RULE_EVENT_TYPE_DESC) VALUES (6,'scheduled-one-time', 'Scheduled One Time');
INSERT INTO RULE_EVENT_TYPE (RULE_EVENT_TYPE_ID, RULE_EVENT_TYPE_NAME_ID, RULE_EVENT_TYPE_DESC) VALUES (7,'scheduled-daily', 'Scheduled Daily');
INSERT INTO RULE_EVENT_TYPE (RULE_EVENT_TYPE_ID, RULE_EVENT_TYPE_NAME_ID, RULE_EVENT_TYPE_DESC) VALUES (8,'scheduled-weekly', 'Scheduled Weekly');
INSERT INTO RULE_EVENT_TYPE (RULE_EVENT_TYPE_ID, RULE_EVENT_TYPE_NAME_ID, RULE_EVENT_TYPE_DESC) VALUES (9,'scheduled-monthly', 'Scheduled Monthly');
INSERT INTO RULE_EVENT_TYPE (RULE_EVENT_TYPE_ID, RULE_EVENT_TYPE_NAME_ID, RULE_EVENT_TYPE_DESC) VALUES (10,'email-scheduled-daily', 'Email Scheduled Daily');
INSERT INTO RULE_EVENT_TYPE (RULE_EVENT_TYPE_ID, RULE_EVENT_TYPE_NAME_ID, RULE_EVENT_TYPE_DESC) VALUES (11,'email-scheduled-weekly', 'Email Scheduled Weekly');
INSERT INTO RULE_EVENT_TYPE (RULE_EVENT_TYPE_ID, RULE_EVENT_TYPE_NAME_ID, RULE_EVENT_TYPE_DESC) VALUES (12,'email-scheduled-monthly', 'Email Scheduled Monthly');


-- END RULES



COMMIT;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

