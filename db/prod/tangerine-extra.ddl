
SET foreign_key_checks = 0;

use company1;

delete from RULE_SEGMENT_TYPE;
delete from RULE;
delete from RULE_VERSION;
delete from RULE_SEGMENT;

-- DSL (globally available orangeleap-defined rule phrases)
INSERT INTO RULE_SEGMENT_TYPE (RULE_SEGMENT_TYPE_ID, RULE_SEGMENT_TYPE_TYPE, RULE_SEGMENT_TYPE_PHRASE, RULE_SEGMENT_TYPE_TEXT) VALUES (1,'condition','is a login user','map.constituent.getCustomFieldValue("constituentIndividualRoles").contains("user")');
INSERT INTO RULE_SEGMENT_TYPE (RULE_SEGMENT_TYPE_ID, RULE_SEGMENT_TYPE_TYPE, RULE_SEGMENT_TYPE_PHRASE, RULE_SEGMENT_TYPE_TEXT) VALUES (2,'consequence','set testfield value','map.constituent.setCustomFieldValue("testfield","testvalue"); map.constituentService.maintainConstituent(map.constituent);');
-- this one would normally be a parameterized rule instead so they could set any login id:
INSERT INTO RULE_SEGMENT_TYPE (RULE_SEGMENT_TYPE_ID, RULE_SEGMENT_TYPE_TYPE, RULE_SEGMENT_TYPE_PHRASE, RULE_SEGMENT_TYPE_TEXT) VALUES (3,'condition','is nolan','map.constituent.getLoginId().equals("nolan")'); 
INSERT INTO RULE_SEGMENT_TYPE (RULE_SEGMENT_TYPE_ID, RULE_SEGMENT_TYPE_TYPE, RULE_SEGMENT_TYPE_PHRASE, RULE_SEGMENT_TYPE_TEXT) VALUES (4,'consequence','set testfield2 value','map.constituent.setCustomFieldValue("testfield2","testvalue2"); map.constituentService.maintainConstituent(map.constituent);');

-- DSLR (site-specific rules)
INSERT INTO RULE (RULE_ID,RULE_EVENT_TYPE_NAME_ID,RULE_SEQ,RULE_DESC,RULE_IS_ACTIVE,SITE_NAME) VALUES (1,'constituent-save',0,'A Test Rule','Y','company1');
INSERT INTO RULE_VERSION (RULE_VERSION_ID,RULE_ID,RULE_VERSION_SEQ,RULE_VERSION_IS_TEST_ONLY,UPDATED_BY) VALUES (1,1,0,'N','');
INSERT INTO RULE_SEGMENT (RULE_SEGMENT_ID,RULE_VERSION_ID,RULE_SEGMENT_SEQ,RULE_SEGMENT_TYPE_ID) VALUES (1,1,0,1);
INSERT INTO RULE_SEGMENT (RULE_SEGMENT_ID,RULE_VERSION_ID,RULE_SEGMENT_SEQ,RULE_SEGMENT_TYPE_ID) VALUES (2,1,1,2);
INSERT INTO RULE_SEGMENT (RULE_SEGMENT_ID,RULE_VERSION_ID,RULE_SEGMENT_SEQ,RULE_SEGMENT_TYPE_ID) VALUES (3,1,2,3);
INSERT INTO RULE_SEGMENT (RULE_SEGMENT_ID,RULE_VERSION_ID,RULE_SEGMENT_SEQ,RULE_SEGMENT_TYPE_ID) VALUES (4,1,3,4);


SET foreign_key_checks = 1;

