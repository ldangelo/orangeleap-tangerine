/*
V4 Schema changes
*********************************************************************
*/

-- TANGERINE-1185
ALTER TABLE SITE DROP COLUMN MAJOR_DONOR_ACCOUNT_MANAGER_ID, DROP COLUMN FISCAL_YEAR_STARTING_MONTH;

ALTER TABLE `SITE` 
	CHANGE `JASPER_USER_ID` `JASPER_USER_ID` varchar(64)  COLLATE utf8_general_ci NULL after `UPDATE_DATE`, 
	CHANGE `JASPER_PASSWORD` `JASPER_PASSWORD` varchar(64)  COLLATE utf8_general_ci NULL after `JASPER_USER_ID`;

ALTER TABLE `SITE` DROP FOREIGN KEY `FK_SITE_PARENT_SITE` ;


ALTER TABLE `DISTRO_LINE` 
	ADD KEY `IDX_DISTRO_LINE_ADJUSTED_GIFT_ID`(`ADJUSTED_GIFT_ID`), 
	ADD KEY `IDX_DISTRO_LINE_PLEDGE_ID`(`PLEDGE_ID`), 
	ADD KEY `IDX_DISTRO_LINE_RECURRING_GIFT_ID`(`RECURRING_GIFT_ID`);



DROP TABLE IF EXISTS `ENTITY_DEFAULT`;

CREATE TABLE `ENTITY_DEFAULT` (
`ENTITY_DEFAULT_ID` bigint(20) NOT NULL auto_increment,
`DEFAULT_VALUE` text,
`ENTITY_FIELD_NAME` varchar(255) default NULL,
`ENTITY_TYPE` varchar(255) default NULL,
`CONDITION_EXP` text,
`SITE_NAME` varchar(255) default NULL,
PRIMARY KEY  (`ENTITY_DEFAULT_ID`),
KEY `FK_ENT_DEF_SITE` (`SITE_NAME`),
CONSTRAINT `FK_ENT_DEF_SITE` FOREIGN KEY (`SITE_NAME`) REFERENCES `SITE` (`SITE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `JOURNAL`;

CREATE TABLE `JOURNAL`(
	`JOURNAL_ID` bigint(20) NOT NULL  auto_increment , 
	`POST_BATCH_ID` bigint(20) NOT NULL  , 
	`ENTITY` varchar(255) COLLATE utf8_general_ci NOT NULL  , 
	`ENTITY_ID` bigint(20) NOT NULL  , 
	`MASTER_ENTITY` varchar(255) COLLATE utf8_general_ci NULL  , 
	`MASTER_ENTITY_ID` bigint(20) NULL  , 
	`ORIG_ENTITY` varchar(255) COLLATE utf8_general_ci NULL  , 
	`ORIG_ENTITY_ID` bigint(20) NULL  , 
	`CODE` varchar(255) COLLATE utf8_general_ci NULL  , 
	`GL_CODE` varchar(255) COLLATE utf8_general_ci NOT NULL  , 
	`GL_ACCOUNT_1` varchar(255) COLLATE utf8_general_ci NOT NULL  , 
	`GL_ACCOUNT_2` varchar(255) COLLATE utf8_general_ci NOT NULL  , 
	`AMOUNT` decimal(19,2) NOT NULL  , 
	`JE_TYPE` varchar(255) COLLATE utf8_general_ci NULL  , 
	`PAYMENT_METHOD` varchar(255) COLLATE utf8_general_ci NULL  , 
	`CC_TYPE` varchar(255) COLLATE utf8_general_ci NULL  , 
	`DESCRIPTION` varchar(255) COLLATE utf8_general_ci NULL  , 
	`DONATION_DATE` datetime NULL  , 
	`ADJUSTMENT_DATE` datetime NULL  , 
	`POSTED_DATE` datetime NULL  , 
	`EXPORT_DATE` datetime NULL  , 
	`SITE_NAME` varchar(255) COLLATE utf8_general_ci NOT NULL  , 
	`CREATE_DATE` datetime NULL  , 
	`UPDATE_DATE` datetime NULL  , 
	PRIMARY KEY (`JOURNAL_ID`) , 
	KEY `FK_JOURNAL_SITE_NAME`(`SITE_NAME`) , 
	KEY `FK_JOURNAL_POST_BATCH_ID`(`POST_BATCH_ID`) 
) ENGINE=InnoDB DEFAULT CHARSET='utf8';

DROP TABLE IF EXISTS `POST_BATCH_REVIEW_SET_ITEM`;


CREATE TABLE `POST_BATCH_REVIEW_SET_ITEM`(
	`POST_BATCH_REVIEW_SET_ITEM_ID` bigint(20) NOT NULL  auto_increment , 
	`POST_BATCH_ID` bigint(20) NOT NULL  , 
	`ENTITY_ID` bigint(20) NOT NULL  , 
	`CREATE_DATE` datetime NULL  , 
	`UPDATE_DATE` datetime NULL  , 
	PRIMARY KEY (`POST_BATCH_REVIEW_SET_ITEM_ID`) , 
	KEY `FK_POST_BATCH_REVIEW_SET_ITEM_BATCH_ID`(`POST_BATCH_ID`) 
) ENGINE=InnoDB DEFAULT CHARSET='utf8';

DROP TABLE IF EXISTS `POST_BATCH`;

CREATE TABLE `POST_BATCH`(
	`POST_BATCH_ID` bigint(20) NOT NULL  auto_increment , 
	`POST_BATCH_DESC` varchar(255) COLLATE utf8_general_ci NOT NULL  , 
	`ENTITY` varchar(255) COLLATE utf8_general_ci NOT NULL  , 
	`REVIEW_SET_GENERATED` char(1) COLLATE utf8_general_ci NOT NULL  DEFAULT '0' , 
	`REVIEW_SET_GENERATED_BY_ID` bigint(20) NULL  , 
	`REVIEW_SET_GENERATED_DATE` datetime NULL  , 
	`REVIEW_SET_SIZE` bigint(20) NULL  , 
	`BATCH_UPDATED` char(1) COLLATE utf8_general_ci NOT NULL  DEFAULT '0' , 
	`BATCH_UPDATED_BY_ID` bigint(20) NULL  , 
	`BATCH_UPDATED_DATE` datetime NULL  , 
	`POSTED` char(1) COLLATE utf8_general_ci NOT NULL  DEFAULT '0' , 
	`POSTED_BY_ID` bigint(20) NULL  , 
	`POSTED_DATE` datetime NULL  , 
	`SITE_NAME` varchar(255) COLLATE utf8_general_ci NOT NULL  , 
	`CREATE_DATE` datetime NULL  , 
	`UPDATE_DATE` datetime NULL  , 
	PRIMARY KEY (`POST_BATCH_ID`) , 
	KEY `FK_POST_BATCH_SITE_NAME`(`SITE_NAME`) , 
	KEY `FK_POST_BATCH_REVIEW_SET_GENERATED_BY_ID`(`REVIEW_SET_GENERATED_BY_ID`) , 
	KEY `FK_POST_BATCH_POSTED_BY_ID`(`POSTED_BY_ID`) 
) ENGINE=InnoDB DEFAULT CHARSET='utf8';

DROP TABLE IF EXISTS `SCHEDULED_ITEM`;

CREATE TABLE `SCHEDULED_ITEM`(
	`SCHEDULED_ITEM_ID` bigint(20) NOT NULL  auto_increment , 
	`SOURCE_ENTITY` varchar(255) COLLATE utf8_general_ci NULL  , 
	`SOURCE_ENTITY_ID` bigint(20) NULL  , 
	`SCHEDULED_ITEM_TYPE` varchar(255) COLLATE utf8_general_ci NULL  , 
	`SCHEDULED_ITEM_AMOUNT` decimal(19,2) NULL  DEFAULT '0.00' , 
	`RESULT_ENTITY` varchar(255) COLLATE utf8_general_ci NULL  , 
	`RESULT_ENTITY_ID` bigint(20) NULL  , 
	`ORIGINAL_SCHEDULED_DATE` datetime NULL  , 
	`ACTUAL_SCHEDULED_DATE` datetime NULL  , 
	`COMPLETION_DATE` datetime NULL  , 
	`COMPLETION_STATUS` varchar(255) COLLATE utf8_general_ci NULL  , 
	`MODIFIED_BY` bigint(20) NULL  , 
	`CREATE_DATE` datetime NULL  , 
	`UPDATE_DATE` datetime NULL  , 
	PRIMARY KEY (`SCHEDULED_ITEM_ID`) , 
	KEY `KEY_SOURCE_ENTITY_ID`(`SOURCE_ENTITY_ID`) , 
	KEY `FK_ACTUAL_SCHEDULED_DATE`(`ACTUAL_SCHEDULED_DATE`) 
) ENGINE=InnoDB DEFAULT CHARSET='utf8';

DROP TABLE IF EXISTS `SITE_OPTION`;

CREATE TABLE `SITE_OPTION`(
	`SITE_OPTION_ID` bigint(20) NOT NULL  auto_increment , 
	`SITE_NAME` varchar(255) COLLATE utf8_general_ci NOT NULL  , 
	`OPTION_NAME` varchar(255) COLLATE utf8_general_ci NOT NULL  , 
	`OPTION_NAME_READ_ONLY` char(1) COLLATE utf8_general_ci NOT NULL  DEFAULT '0' , 
	`OPTION_DESC` varchar(255) COLLATE utf8_general_ci NOT NULL  DEFAULT '' , 
	`OPTION_VALUE` varchar(255) COLLATE utf8_general_ci NOT NULL  , 
	`OPTION_VALUE_READ_ONLY` char(1) COLLATE utf8_general_ci NOT NULL  DEFAULT '0' , 
	`MODIFIED_BY` bigint(20) NULL  , 
	`CREATE_DATE` datetime NULL  , 
	`UPDATE_DATE` datetime NULL  , 
	PRIMARY KEY (`SITE_OPTION_ID`) , 
	KEY `KEY_SITE_OPTION_SITE_NAME`(`SITE_NAME`) 
) ENGINE=InnoDB DEFAULT CHARSET='utf8';


DROP TABLE IF EXISTS `THEGURU_SEGMENTATION_RESULT`;

CREATE TABLE `THEGURU_SEGMENTATION_RESULT`(
	`THEGURU_SEGMENTATION_RESULT_ID` bigint(20) NOT NULL  auto_increment , 
	`REPORT_ID` bigint(20) NOT NULL  , 
	`ENTITY_ID` bigint(20) NOT NULL  , 
	PRIMARY KEY (`THEGURU_SEGMENTATION_RESULT_ID`) , 
	KEY `THEGURU_SEGMENTATION_RESULT_REPORT_ID_IDX`(`REPORT_ID`) 
) ENGINE=InnoDB DEFAULT CHARSET='utf8';

ALTER TABLE `JOURNAL`
ADD CONSTRAINT `FK_JOURNAL_SITE_NAME` 
FOREIGN KEY (`SITE_NAME`) REFERENCES `SITE` (`SITE_NAME`);

ALTER TABLE `JOURNAL`
ADD CONSTRAINT `FK_JOURNAL_POST_BATCH_ID` 
FOREIGN KEY (`POST_BATCH_ID`) REFERENCES `POST_BATCH` (`POST_BATCH_ID`);

ALTER TABLE `POST_BATCH`
ADD CONSTRAINT `FK_POST_BATCH_SITE_NAME` 
FOREIGN KEY (`SITE_NAME`) REFERENCES `SITE` (`SITE_NAME`);

ALTER TABLE `POST_BATCH`
ADD CONSTRAINT `FK_POST_BATCH_REVIEW_SET_GENERATED_BY_ID` 
FOREIGN KEY (`REVIEW_SET_GENERATED_BY_ID`) REFERENCES `CONSTITUENT` (`CONSTITUENT_ID`);

ALTER TABLE `POST_BATCH`
ADD CONSTRAINT `FK_POST_BATCH_POSTED_BY_ID` 
FOREIGN KEY (`POSTED_BY_ID`) REFERENCES `CONSTITUENT` (`CONSTITUENT_ID`);

ALTER TABLE `POST_BATCH_REVIEW_SET_ITEM`
ADD CONSTRAINT `FK_POST_BATCH_REVIEW_SET_ITEM_BATCH_ID` 
FOREIGN KEY (`POST_BATCH_ID`) REFERENCES `POST_BATCH` (`POST_BATCH_ID`);

ALTER TABLE `SITE_OPTION`
ADD CONSTRAINT `FK_SITE_OPTION_SITE_NAME` 
FOREIGN KEY (`SITE_NAME`) REFERENCES `SITE` (`SITE_NAME`);
 

ALTER TABLE `SITE`
ADD CONSTRAINT `FK_SITE_PARENT_SITE` 
FOREIGN KEY (`PARENT_SITE_NAME`) REFERENCES `SITE` (`SITE_NAME`);

