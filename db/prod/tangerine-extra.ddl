
SET foreign_key_checks = 0;

# Gift Rollup tables (FKs omitted)
# SERIES_TYPE = ALLTIME, YEAR, MONTH, WEEK, or DAY

DROP TABLE IF EXISTS `ROLLUP_SERIES`;
DROP TABLE IF EXISTS `rollup_series`;
CREATE TABLE `ROLLUP_SERIES` (
`ROLLUP_SERIES_ID` bigint(20) NOT NULL auto_increment,
`SERIES_DESC` varchar(255) NOT NULL,
`SERIES_TYPE` varchar(255) NOT NULL, 
`BEGIN_DATE` datetime NOT NULL,
`MAINTAIN_PERIODS` int(5) default 1 NOT NULL,
`KEEP_UNMAINTAINED` char(1) default 'N' NOT NULL,
`SITE_NAME` varchar(255) NOT NULL, 
PRIMARY KEY  (`ROLLUP_SERIES_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- GROUP_BY_TYPE = AVG, SUM, COUNT (these are not directly mapped to SQL, logic depends on entity type)
DROP TABLE IF EXISTS `ROLLUP_ATTRIBUTE`;
CREATE TABLE `ROLLUP_ATTRIBUTE` (
`ROLLUP_ATTRIBUTE_ID` bigint(20) NOT NULL auto_increment,
`ATTRIBUTE_DESC` varchar(255) NOT NULL,
`GROUP_BY_TYPE` varchar(255) default 'SUM' NOT NULL,
`ROLLUP_ENTITY_TYPE` varchar(255) NOT NULL,
`ROLLUP_STAT_TYPE` varchar(255) NOT NULL,
`CUSTOM_FIELD_NAME` varchar(255) NOT NULL,
`SITE_NAME` varchar(255) NOT NULL, 
PRIMARY KEY  (`ROLLUP_ATTRIBUTE_ID`),
KEY `KEY_ROLLUP_ATTRIBUTE` (`ROLLUP_ENTITY_TYPE`,`ROLLUP_STAT_TYPE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `ROLLUP_SERIES_X_ATTRIBUTE`;
CREATE TABLE `ROLLUP_SERIES_X_ATTRIBUTE` (
`ROLLUP_SERIES_ID` bigint(20) NOT NULL,
`ROLLUP_ATTRIBUTE_ID` bigint(20) NOT NULL,
KEY `KEY_ROLLUP_SERIES_X_ATTRIBUTE` (`ROLLUP_SERIES_ID`),
KEY `KEY_ROLLUP_ATTRIBUTE_X_SERIES` (`ROLLUP_ATTRIBUTE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `ROLLUP_VALUE`;
CREATE TABLE `ROLLUP_VALUE` (
`ROLLUP_VALUE_ID` bigint(20) NOT NULL auto_increment,
`ROLLUP_SERIES_ID` bigint(20) NOT NULL,
`ROLLUP_ATTRIBUTE_ID` bigint(20) NOT NULL,
`GROUP_BY_VALUE` varchar(255) NOT NULL,
`START_DATE` datetime NOT NULL,
`END_DATE` datetime NOT NULL,
`CURRENCY_CODE` varchar(255) NOT NULL,
`TOTAL_VALUE` decimal(19,2) default '0' NOT NULL,
PRIMARY KEY  (`ROLLUP_VALUE_ID`),
KEY `KEY_ROLLUP_VALUE` (`GROUP_BY_VALUE`,`ROLLUP_ATTRIBUTE_ID`,`START_DATE`,`END_DATE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



SET foreign_key_checks = 1;

