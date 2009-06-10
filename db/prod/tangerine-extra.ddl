
SET foreign_key_checks = 0;


# Dump of table BATCH
# ------------------------------------------------------------

DROP TABLE IF EXISTS `BATCH`;

CREATE TABLE `BATCH` (
  `BATCH_ID` bigint(20) NOT NULL auto_increment,
  `BATCH_DESC` varchar(255) NOT NULL,
  `CREATE_JOURNAL` char(1) NOT NULL default '0',
  `ENTITY` varchar(255) NOT NULL,
  `PROCESSED` char(1) NOT NULL default '0',
  `PROCESSED_BY_ID` bigint(20) default NULL,
  `PROCESSED_DATE` datetime default NULL,
  `NUM_RECORDS_PROCESSED` bigint(20) default NULL,
  `SITE_NAME` varchar(255) NOT NULL,
  `CREATE_DATE` datetime default NULL,
  `UPDATE_DATE` datetime default NULL,
  PRIMARY KEY  (`BATCH_ID`),
  KEY `FK_BATCH_SITE_NAME` (`SITE_NAME`),
  CONSTRAINT `FK_BATCH_SITE_NAME` FOREIGN KEY (`SITE_NAME`) REFERENCES `SITE` (`SITE_NAME`),
  CONSTRAINT `FK_BATCH_CONSTITUENT_ID` FOREIGN KEY (`PROCESSED_BY_ID`) REFERENCES `CONSTITUENT` (`CONSTITUENT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



# Dump of table JOURNAL
# ------------------------------------------------------------

DROP TABLE IF EXISTS `JOURNAL`;

CREATE TABLE `JOURNAL` (
  `JOURNAL_ID` bigint(20) NOT NULL auto_increment,
  `BATCH_ID` bigint(20) NOT NULL,
  `ENTITY` varchar(255) NOT NULL,
  `ENTITY_ID` bigint(20) NOT NULL,
  `GL_CODE` varchar(255) NOT NULL,
  `IS_DEBIT` char(1) NOT NULL default '0',
  `AMOUNT` decimal(19,2) NOT NULL,
  `TYPE` varchar(255) default NULL,
  `SOURCE_CODE` varchar(255) default NULL,
  `DISTRIBUTION_CODE` varchar(255) default NULL,
  `SITE_NAME` varchar(255) NOT NULL,
  `CREATE_DATE` datetime default NULL,
  `UPDATE_DATE` datetime default NULL,
  PRIMARY KEY  (`JOURNAL_ID`),
  KEY `FK_JOURNAL_SITE_NAME` (`SITE_NAME`),
  CONSTRAINT `FK_JOURNAL_SITE_NAME` FOREIGN KEY (`SITE_NAME`) REFERENCES `SITE` (`SITE_NAME`),
  CONSTRAINT `FK_JOURNAL_BATCH_ID` FOREIGN KEY (`BATCH_ID`) REFERENCES `BATCH` (`BATCH_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


SET foreign_key_checks = 1;

