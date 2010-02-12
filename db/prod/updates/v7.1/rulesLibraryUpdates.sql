-- This script should be usded to add/delete/modify the rules DSL.


-- ********************************************************************************************************************************
-- ********************************** Conditions **********************************************************************************
-- ********************************************************************************************************************************


-- --------------------------------------------------------------------------------------------------------------------------------
SET @PHRASE_CD = 'Constituent has not donated at least ? gifts';
SET @CODE_CD = 'map.ruleHelperService.numberOfDonationsMadePerTimeFrame(map.constituent, -1, null) < ?';

-- Insert code
INSERT INTO RULE_SEGMENT_TYPE (RULE_SEGMENT_TYPE_TYPE, RULE_SEGMENT_TYPE_PHRASE, RULE_SEGMENT_TYPE_TEXT) VALUES ('condition',@PHRASE_CD,@CODE_CD);
SET @RULE_SEGMENT_TYPE_ID = LAST_INSERT_ID();

-- Insert what segment types can be used for what event types (this is for the UI piece)
INSERT INTO RULE_EVENT_TYPE_X_RULE_SEGMENT_TYPE (RULE_EVENT_TYPE_ID,RULE_SEGMENT_TYPE_ID) VALUES ((SELECT RULE_EVENT_TYPE_ID FROM RULE_EVENT_TYPE WHERE RULE_EVENT_TYPE_NAME_ID = 'constituent-save'),@RULE_SEGMENT_TYPE_ID);
INSERT INTO RULE_EVENT_TYPE_X_RULE_SEGMENT_TYPE (RULE_EVENT_TYPE_ID,RULE_SEGMENT_TYPE_ID) VALUES ((SELECT RULE_EVENT_TYPE_ID FROM RULE_EVENT_TYPE WHERE RULE_EVENT_TYPE_NAME_ID = 'gift-save'),@RULE_SEGMENT_TYPE_ID);
INSERT INTO RULE_EVENT_TYPE_X_RULE_SEGMENT_TYPE (RULE_EVENT_TYPE_ID,RULE_SEGMENT_TYPE_ID) VALUES ((SELECT RULE_EVENT_TYPE_ID FROM RULE_EVENT_TYPE WHERE RULE_EVENT_TYPE_NAME_ID = 'email'),@RULE_SEGMENT_TYPE_ID);

-- Insert the parameters for the condition
SET @RULE_SEGMENT_TYPE_PARM_SEQ = (SELECT IFNULL( (SELECT MAX(RULE_SEGMENT_TYPE_PARM_SEQ)+1 FROM RULE_SEGMENT_TYPE_PARM WHERE RULE_SEGMENT_TYPE_ID = @RULE_SEGMENT_TYPE_ID), 0));
INSERT INTO RULE_SEGMENT_TYPE_PARM (RULE_SEGMENT_TYPE_ID, RULE_SEGMENT_TYPE_PARM_SEQ, RULE_SEGMENT_TYPE_PARM_TYPE) VALUES (@RULE_SEGMENT_TYPE_ID,@RULE_SEGMENT_TYPE_PARM_SEQ,'NUMBER');





-- ********************************************************************************************************************************
-- ********************************** Consequences **********************************************************************************
-- ********************************************************************************************************************************
