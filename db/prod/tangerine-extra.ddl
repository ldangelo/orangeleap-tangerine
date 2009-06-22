
SET foreign_key_checks = 0;


# Dump of table POST_BATCH
# ------------------------------------------------------------

DROP TABLE IF EXISTS `POST_BATCH`;

CREATE TABLE `POST_BATCH` (
  `POST_BATCH_ID` bigint(20) NOT NULL auto_increment,
  `POST_BATCH_DESC` varchar(255) NOT NULL,
  `ENTITY` varchar(255) NOT NULL,
  `REVIEW_SET_GENERATED` char(1) NOT NULL default '0',
  `REVIEW_SET_GENERATED_BY_ID` bigint(20) default NULL,
  `REVIEW_SET_GENERATED_DATE` datetime default NULL,
  `REVIEW_SET_SIZE` bigint(20) default NULL,
  `POSTED` char(1) NOT NULL default '0',
  `POSTED_BY_ID` bigint(20) default NULL,
  `POSTED_DATE` datetime default NULL,
  `SITE_NAME` varchar(255) NOT NULL,
  `CREATE_DATE` datetime default NULL,
  `UPDATE_DATE` datetime default NULL,
  PRIMARY KEY  (`POST_BATCH_ID`),
  KEY `FK_POST_BATCH_SITE_NAME` (`SITE_NAME`),
  CONSTRAINT `FK_POST_BATCH_SITE_NAME` FOREIGN KEY (`SITE_NAME`) REFERENCES `SITE` (`SITE_NAME`),
  CONSTRAINT `FK_POST_BATCH_REVIEW_SET_GENERATED_BY_ID` FOREIGN KEY (`REVIEW_SET_GENERATED_BY_ID`) REFERENCES `CONSTITUENT` (`CONSTITUENT_ID`),
  CONSTRAINT `FK_POST_BATCH_POSTED_BY_ID` FOREIGN KEY (`POSTED_BY_ID`) REFERENCES `CONSTITUENT` (`CONSTITUENT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

# Dump of table POST_BATCH_REVIEW_SET_ITEM
# ------------------------------------------------------------

DROP TABLE IF EXISTS `POST_BATCH_REVIEW_SET_ITEM`;

CREATE TABLE `POST_BATCH_REVIEW_SET_ITEM` (
  `POST_BATCH_REVIEW_SET_ITEM_ID` bigint(20) NOT NULL auto_increment,
  `POST_BATCH_ID` bigint(20) NOT NULL,
  `ENTITY_ID` bigint(20) NOT NULL,
  `CREATE_DATE` datetime default NULL,
  `UPDATE_DATE` datetime default NULL,
  PRIMARY KEY  (`POST_BATCH_REVIEW_SET_ITEM_ID`),
  CONSTRAINT `FK_POST_BATCH_REVIEW_SET_ITEM_BATCH_ID` FOREIGN KEY (`POST_BATCH_ID`) REFERENCES `POST_BATCH` (`POST_BATCH_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


# Dump of table JOURNAL
# ------------------------------------------------------------

DROP TABLE IF EXISTS `JOURNAL`;

CREATE TABLE `JOURNAL` (
  `JOURNAL_ID` bigint(20) NOT NULL auto_increment,
  `POST_BATCH_ID` bigint(20) NOT NULL,
  `ENTITY` varchar(255) NOT NULL,
  `ENTITY_ID` bigint(20) NOT NULL,
  `MASTER_ENTITY` varchar(255) NOT NULL,
  `MASTER_ENTITY_ID` bigint(20) NOT NULL,
  `ORIG_ENTITY` varchar(255) NOT NULL,
  `ORIG_ENTITY_ID` bigint(20) NOT NULL,
  `CODE` varchar(255) default NULL,
  `GL_CODE1` varchar(255) NOT NULL,
  `GL_CODE2` varchar(255) NOT NULL,
  `AMOUNT` decimal(19,2) NOT NULL,
  `JE_TYPE` varchar(255) default NULL,  -- debit or credit
  `PAYMENT_METHOD` varchar(255) default NULL,
  `CC_TYPE` varchar(255) default NULL,
  `DESC` varchar(255) default NULL,
  `DONATION_DATE` datetime default NULL,
  `ADJUSTMENT_DATE` datetime default NULL,
  `POSTED_DATE` datetime default NULL,
  `EXPORT_DATE` datetime default NULL,  -- when record is read into an external system (the only updateable field)
  `SITE_NAME` varchar(255) NOT NULL,
  `CREATE_DATE` datetime default NULL,
  `UPDATE_DATE` datetime default NULL,
  PRIMARY KEY  (`JOURNAL_ID`),
  KEY `FK_JOURNAL_SITE_NAME` (`SITE_NAME`),
  CONSTRAINT `FK_JOURNAL_SITE_NAME` FOREIGN KEY (`SITE_NAME`) REFERENCES `SITE` (`SITE_NAME`),
  CONSTRAINT `FK_JOURNAL_POST_BATCH_ID` FOREIGN KEY (`POST_BATCH_ID`) REFERENCES `POST_BATCH` (`POST_BATCH_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


SET foreign_key_checks = 1;

