
SET foreign_key_checks = 0;

CREATE TABLE `SCHEDULED_ITEM` (
  `SCHEDULED_ITEM_ID` bigint(20) NOT NULL auto_increment,
  `SOURCE_ENTITY` varchar(255) default NULL,
  `SOURCE_ENTITY_ID` bigint(20) default NULL,
  `RESULT_ENTITY` varchar(255) default NULL,
  `RESULT_ENTITY_ID` bigint(20) default NULL,
  `ORIGINAL_SCHEDULED_DATE` datetime default NULL,
  `ACTUAL_SCHEDULED_DATE` datetime default NULL,
  `COMPLETION_DATE` datetime default NULL,
  `COMPLETION_STATUS` varchar(255) default NULL,
  `MODIFIED_BY` bigint(20) default NULL,
  `CREATE_DATE` datetime default NULL,
  `UPDATE_DATE` datetime default NULL,
  PRIMARY KEY  (`SCHEDULED_ITEM_ID`),
  CONSTRAINT `FK_SCHEDULED_ITEM_USER` FOREIGN KEY (`MODIFIED_BY`) REFERENCES `CONSTITUENT` (`CONSTITUENT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



SET foreign_key_checks = 1;

