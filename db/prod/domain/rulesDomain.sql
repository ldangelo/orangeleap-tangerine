-- RULE_EVENT_TYPE (code entry points for rules)

INSERT INTO RULE_EVENT_TYPE (RULE_EVENT_TYPE_ID, RULE_EVENT_TYPE_NAME_ID) VALUES (1,'constituent-save');
INSERT INTO RULE_EVENT_TYPE (RULE_EVENT_TYPE_ID, RULE_EVENT_TYPE_NAME_ID) VALUES (2,'gift-save');
INSERT INTO RULE_EVENT_TYPE (RULE_EVENT_TYPE_ID, RULE_EVENT_TYPE_NAME_ID) VALUES (3,'touchpoint-save');
INSERT INTO RULE_EVENT_TYPE (RULE_EVENT_TYPE_ID, RULE_EVENT_TYPE_NAME_ID) VALUES (4,'email');
INSERT INTO RULE_EVENT_TYPE (RULE_EVENT_TYPE_ID, RULE_EVENT_TYPE_NAME_ID) VALUES (5,'payment-processing');
INSERT INTO RULE_EVENT_TYPE (RULE_EVENT_TYPE_ID, RULE_EVENT_TYPE_NAME_ID) VALUES (6,'scheduled-one-time');
INSERT INTO RULE_EVENT_TYPE (RULE_EVENT_TYPE_ID, RULE_EVENT_TYPE_NAME_ID) VALUES (7,'scheduled-daily');
INSERT INTO RULE_EVENT_TYPE (RULE_EVENT_TYPE_ID, RULE_EVENT_TYPE_NAME_ID) VALUES (8,'scheduled-weekly');
INSERT INTO RULE_EVENT_TYPE (RULE_EVENT_TYPE_ID, RULE_EVENT_TYPE_NAME_ID) VALUES (9,'scheduled-monthly');
INSERT INTO RULE_EVENT_TYPE (RULE_EVENT_TYPE_ID, RULE_EVENT_TYPE_NAME_ID) VALUES (10,'email-scheduled-daily');
INSERT INTO RULE_EVENT_TYPE (RULE_EVENT_TYPE_ID, RULE_EVENT_TYPE_NAME_ID) VALUES (11,'email-scheduled-weekly');
INSERT INTO RULE_EVENT_TYPE (RULE_EVENT_TYPE_ID, RULE_EVENT_TYPE_NAME_ID) VALUES (12,'email-scheduled-monthly');


-- RULE_SEGMENT_TYPE and RULE_SEGMENT_TYPE_PARM (code function definitions for rule text phrases)

-- TODO replace with actual list
INSERT INTO RULE_SEGMENT_TYPE (RULE_SEGMENT_TYPE_ID, RULE_SEGMENT_TYPE_TYPE, RULE_SEGMENT_TYPE_PHRASE, RULE_SEGMENT_TYPE_TEXT) VALUES (1,'condition','is a login user','map.constituent.getCustomFieldValue("constituentIndividualRoles").contains("user")');




-- RULE_EVENT_TYPE_X_SEGMENT_TYPE (what segment types can be used for what event types)

-- TODO replace with actual list
INSERT INTO RULE_EVENT_TYPE_X_RULE_SEGMENT_TYPE (RULE_EVENT_TYPE_ID,RULE_SEGMENT_TYPE_ID) VALUES (1,1);
