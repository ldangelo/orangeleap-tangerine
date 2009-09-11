/*
V5 Schema changes
*********************************************************************
*/

DROP TABLE IF EXISTS `ENTITY_SEARCH`;

CREATE TABLE `ENTITY_SEARCH` (
`ENTITY_TYPE` varchar(255) NOT NULL,
`ENTITY_ID` bigint(20) NOT NULL,
`SEARCH_TEXT` TEXT NOT NULL,
INDEX `ENTITY_SEARCH` (`ENTITY_ID`,`ENTITY_TYPE`),
FULLTEXT INDEX (`SEARCH_TEXT`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;



