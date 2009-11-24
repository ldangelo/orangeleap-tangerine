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


-- BEGIN ROLLUPS --

-- NAME_IDs for ATTRIBUTES and SERIES can be used by code to reference the specific attributes/series. ATTRIBUTE/SERIES_DESC are the labels seen by the user in the UI.

INSERT INTO ROLLUP_ATTRIBUTE (ROLLUP_ATTRIBUTE_ID, ATTRIBUTE_NAME_ID, ATTRIBUTE_DESC, ROLLUP_ENTITY_TYPE, ROLLUP_STAT_TYPE, FIELD_NAME, CUSTOM_FIELD_NAME, SITE_NAME) VALUES (1000001, 'Gift Amount', 'Gift Amount', 'constituent' , 'ROLLUP_GIFT_BY_CONSTITUENT', 'ADJUSTED_AMOUNT', NULL, NULL);
INSERT INTO ROLLUP_ATTRIBUTE (ROLLUP_ATTRIBUTE_ID, ATTRIBUTE_NAME_ID, ATTRIBUTE_DESC, ROLLUP_ENTITY_TYPE, ROLLUP_STAT_TYPE, FIELD_NAME, CUSTOM_FIELD_NAME, SITE_NAME) VALUES (1000002, 'Gift In Kind Amount', 'Gift In Kind Amount', 'constituent', 'ROLLUP_GIFT_IN_KIND_BY_CONSTITUENT', 'FAIR_MARKET_VALUE', NULL, NULL);
INSERT INTO ROLLUP_ATTRIBUTE (ROLLUP_ATTRIBUTE_ID, ATTRIBUTE_NAME_ID, ATTRIBUTE_DESC, ROLLUP_ENTITY_TYPE, ROLLUP_STAT_TYPE, FIELD_NAME, CUSTOM_FIELD_NAME, SITE_NAME) VALUES (1000003, 'Soft Gift Amount', 'Soft Gift Amount', 'constituent', 'ROLLUP_GIFT_DISTRO_LINE_AMOUNT_BY_CF_CONSTITUENT', NULL, 'onBehalfOf', NULL);

INSERT INTO ROLLUP_ATTRIBUTE (ROLLUP_ATTRIBUTE_ID, ATTRIBUTE_NAME_ID, ATTRIBUTE_DESC, ROLLUP_ENTITY_TYPE, ROLLUP_STAT_TYPE, FIELD_NAME, CUSTOM_FIELD_NAME, SITE_NAME) VALUES (1000005, 'Projected Pledge Amount', 'Projected Pledge Amount', 'constituent', 'ROLLUP_PLEDGE_SCHEDULED_PAYMENTS_BY_CONSTITUENT', NULL, NULL, NULL);
INSERT INTO ROLLUP_ATTRIBUTE (ROLLUP_ATTRIBUTE_ID, ATTRIBUTE_NAME_ID, ATTRIBUTE_DESC, ROLLUP_ENTITY_TYPE, ROLLUP_STAT_TYPE, FIELD_NAME, CUSTOM_FIELD_NAME, SITE_NAME) VALUES (1000006, 'Projected Recurring Gift Amount', 'Projected Recurring Gift Amount', 'constituent', 'ROLLUP_RECURRING_GIFT_SCHEDULED_PAYMENTS_BY_CONSTITUENT', NULL, NULL, NULL);

INSERT INTO ROLLUP_ATTRIBUTE (ROLLUP_ATTRIBUTE_ID, ATTRIBUTE_NAME_ID, ATTRIBUTE_DESC, ROLLUP_ENTITY_TYPE, ROLLUP_STAT_TYPE, FIELD_NAME, CUSTOM_FIELD_NAME, SITE_NAME) VALUES (1000101, 'Projected Pledge Amount All', 'Projected Pledge Amount', 'summary', 'ROLLUP_PLEDGE_SCHEDULED_PAYMENTS_BY_ALL', NULL, NULL, NULL);
INSERT INTO ROLLUP_ATTRIBUTE (ROLLUP_ATTRIBUTE_ID, ATTRIBUTE_NAME_ID, ATTRIBUTE_DESC, ROLLUP_ENTITY_TYPE, ROLLUP_STAT_TYPE, FIELD_NAME, CUSTOM_FIELD_NAME, SITE_NAME) VALUES (1000102, 'Projected Recurring Gift Amount All', 'Projected Recurring Gift Amount', 'summary', 'ROLLUP_RECURRING_GIFT_SCHEDULED_PAYMENTS_BY_ALL', NULL, NULL, NULL);


-- Gifts, Adjusted Gifts, Gifts In Kind, Soft Gifts
INSERT INTO ROLLUP_SERIES (ROLLUP_SERIES_ID, SERIES_NAME_ID, SERIES_DESC, SERIES_TYPE, MAINTAIN_PERIODS, FUTURE_PERIODS, KEEP_UNMAINTAINED, SITE_NAME) VALUES (2000001, 'Calendar Year', 'Calendar Year', 'CALENDAR_YEAR', 5, 0, FALSE, NULL);
INSERT INTO ROLLUP_SERIES_X_ATTRIBUTE (ROLLUP_SERIES_ID, ROLLUP_ATTRIBUTE_ID) VALUES (2000001, 1000001);
INSERT INTO ROLLUP_SERIES_X_ATTRIBUTE (ROLLUP_SERIES_ID, ROLLUP_ATTRIBUTE_ID) VALUES (2000001, 1000002);
INSERT INTO ROLLUP_SERIES_X_ATTRIBUTE (ROLLUP_SERIES_ID, ROLLUP_ATTRIBUTE_ID) VALUES (2000001, 1000003);
INSERT INTO ROLLUP_SERIES (ROLLUP_SERIES_ID, SERIES_NAME_ID, SERIES_DESC, SERIES_TYPE, MAINTAIN_PERIODS, FUTURE_PERIODS, KEEP_UNMAINTAINED, SITE_NAME) VALUES (2000002, 'Fiscal Year', 'Fiscal Year', 'FISCAL_YEAR', 5, 0, FALSE, NULL);
INSERT INTO ROLLUP_SERIES_X_ATTRIBUTE (ROLLUP_SERIES_ID, ROLLUP_ATTRIBUTE_ID) VALUES (2000002, 1000001);
INSERT INTO ROLLUP_SERIES_X_ATTRIBUTE (ROLLUP_SERIES_ID, ROLLUP_ATTRIBUTE_ID) VALUES (2000002, 1000002);
INSERT INTO ROLLUP_SERIES_X_ATTRIBUTE (ROLLUP_SERIES_ID, ROLLUP_ATTRIBUTE_ID) VALUES (2000002, 1000003);
INSERT INTO ROLLUP_SERIES (ROLLUP_SERIES_ID, SERIES_NAME_ID, SERIES_DESC, SERIES_TYPE, MAINTAIN_PERIODS, FUTURE_PERIODS, KEEP_UNMAINTAINED, SITE_NAME) VALUES (2000003, 'Total Lifetime', 'Total Lifetime', 'ALLTIME', 1, 0, FALSE, NULL);
INSERT INTO ROLLUP_SERIES_X_ATTRIBUTE (ROLLUP_SERIES_ID, ROLLUP_ATTRIBUTE_ID) VALUES (2000003, 1000001);
INSERT INTO ROLLUP_SERIES_X_ATTRIBUTE (ROLLUP_SERIES_ID, ROLLUP_ATTRIBUTE_ID) VALUES (2000003, 1000002);
INSERT INTO ROLLUP_SERIES_X_ATTRIBUTE (ROLLUP_SERIES_ID, ROLLUP_ATTRIBUTE_ID) VALUES (2000003, 1000003);

-- Scheduled Payments by constituent
INSERT INTO ROLLUP_SERIES (ROLLUP_SERIES_ID, SERIES_NAME_ID, SERIES_DESC, SERIES_TYPE, MAINTAIN_PERIODS, FUTURE_PERIODS, KEEP_UNMAINTAINED, SITE_NAME) VALUES (2000004, 'Future Calendar Year', 'Calendar Year', 'CALENDAR_YEAR', 2, 1, FALSE, NULL);
INSERT INTO ROLLUP_SERIES_X_ATTRIBUTE (ROLLUP_SERIES_ID, ROLLUP_ATTRIBUTE_ID) VALUES (2000004, 1000005);
INSERT INTO ROLLUP_SERIES_X_ATTRIBUTE (ROLLUP_SERIES_ID, ROLLUP_ATTRIBUTE_ID) VALUES (2000004, 1000006);
INSERT INTO ROLLUP_SERIES (ROLLUP_SERIES_ID, SERIES_NAME_ID, SERIES_DESC, SERIES_TYPE, MAINTAIN_PERIODS, FUTURE_PERIODS, KEEP_UNMAINTAINED, SITE_NAME) VALUES (2000005, 'Future Fiscal Year', 'Fiscal Year', 'FISCAL_YEAR', 2, 1, FALSE, NULL);
INSERT INTO ROLLUP_SERIES_X_ATTRIBUTE (ROLLUP_SERIES_ID, ROLLUP_ATTRIBUTE_ID) VALUES (2000005, 1000005);
INSERT INTO ROLLUP_SERIES_X_ATTRIBUTE (ROLLUP_SERIES_ID, ROLLUP_ATTRIBUTE_ID) VALUES (2000005, 1000006);

-- All Scheduled Payments
INSERT INTO ROLLUP_SERIES (ROLLUP_SERIES_ID, SERIES_NAME_ID, SERIES_DESC, SERIES_TYPE, MAINTAIN_PERIODS, FUTURE_PERIODS, KEEP_UNMAINTAINED, SITE_NAME) VALUES (2000006, 'Future Monthly Total', 'Monthly Total', 'MONTH', 12, 11, FALSE, NULL);
INSERT INTO ROLLUP_SERIES_X_ATTRIBUTE (ROLLUP_SERIES_ID, ROLLUP_ATTRIBUTE_ID) VALUES (2000006, 1000101);
INSERT INTO ROLLUP_SERIES_X_ATTRIBUTE (ROLLUP_SERIES_ID, ROLLUP_ATTRIBUTE_ID) VALUES (2000006, 1000102);

-- Monthly history by constituent
INSERT INTO ROLLUP_SERIES (ROLLUP_SERIES_ID, SERIES_NAME_ID, SERIES_DESC, SERIES_TYPE, MAINTAIN_PERIODS, FUTURE_PERIODS, KEEP_UNMAINTAINED, SITE_NAME) VALUES (2000007, 'Monthly Total', 'Monthly Total', 'MONTH', 13, 0, FALSE, NULL);
INSERT INTO ROLLUP_SERIES_X_ATTRIBUTE (ROLLUP_SERIES_ID, ROLLUP_ATTRIBUTE_ID) VALUES (2000007, 1000001);
INSERT INTO ROLLUP_SERIES (ROLLUP_SERIES_ID, SERIES_NAME_ID, SERIES_DESC, SERIES_TYPE, MAINTAIN_PERIODS, FUTURE_PERIODS, KEEP_UNMAINTAINED, SITE_NAME) VALUES (2000008, 'Past Year Total', 'Past Year Total', 'ROLLING_YEAR', 1, 0, FALSE, NULL);
INSERT INTO ROLLUP_SERIES_X_ATTRIBUTE (ROLLUP_SERIES_ID, ROLLUP_ATTRIBUTE_ID) VALUES (2000008, 1000001);



-- END ROLLUPS --

-- Update GIFT.ADJUSTED_GIFT column
UPDATE GIFT g SET g.ADJUSTED_AMOUNT = (
	select SUM(ag.ADJUSTED_AMOUNT) from ADJUSTED_GIFT ag where g.GIFT_ID = ag.GIFT_ID
);
UPDATE GIFT SET ADJUSTED_AMOUNT = ifnull(ADJUSTED_AMOUNT,0) + AMOUNT;


COMMIT;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

