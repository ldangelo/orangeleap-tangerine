SET AUTOCOMMIT = 0;

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES
('communicationHistory.customFieldMap[messageResponseCategory]', 'communicationHistory', 'customFieldMap[messageResponseCategory]', 'Message Response Category', '2dialogMessageResponseGroup', 'TEXT');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES
('communicationHistory.customFieldMap[linkResponseCategory]', 'communicationHistory', 'customFieldMap[linkResponseCategory]', 'Link Response Category', '2dialogLinkResponseGroup', 'TEXT');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES
('communicationHistory.customFieldMap[actionResponseCategory]', 'communicationHistory', 'customFieldMap[actionResponseCategory]', 'Action Response Category', '2dialogActionResponseGroup', 'TEXT');


INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES
('communicationHistory.customFieldMap[membershipName]', 'communicationHistory', 'customFieldMap[membershipName]', 'Membership Name', '2dialogMembershipDetails', 'TEXT');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES
('communicationHistory.customFieldMap[membershipType]', 'communicationHistory', 'customFieldMap[membershipType]', 'Membership Type', '2dialogMembershipDetails', 'TEXT');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES
('communicationHistory.customFieldMap[memberStatusChange]', 'communicationHistory', 'customFieldMap[memberStatusChange]', 'Member Status Change', '2dialogMembershipDetails', 'TEXT');


INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER)
SELECT SECTION_DEFINITION_ID, 'communicationHistory.customFieldMap[messageResponseCategory]', 1613 FROM SECTION_DEFINITION WHERE SECTION_NAME='communicationHistory' AND PAGE_TYPE='communicationHistory';

INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER)
SELECT SECTION_DEFINITION_ID, 'communicationHistory.customFieldMap[linkResponseCategory]', 1614 FROM SECTION_DEFINITION WHERE SECTION_NAME='communicationHistory' AND PAGE_TYPE='communicationHistory';

INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER)
SELECT SECTION_DEFINITION_ID, 'communicationHistory.customFieldMap[actionResponseCategory]', 1615 FROM SECTION_DEFINITION WHERE SECTION_NAME='communicationHistory' AND PAGE_TYPE='communicationHistory';

INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER)
SELECT SECTION_DEFINITION_ID, 'communicationHistory.customFieldMap[membershipName]', 1616 FROM SECTION_DEFINITION WHERE SECTION_NAME='communicationHistory' AND PAGE_TYPE='communicationHistory';

INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER)
SELECT SECTION_DEFINITION_ID, 'communicationHistory.customFieldMap[membershipType]', 1617 FROM SECTION_DEFINITION WHERE SECTION_NAME='communicationHistory' AND PAGE_TYPE='communicationHistory';

INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER)
SELECT SECTION_DEFINITION_ID, 'communicationHistory.customFieldMap[memberStatusChange]', 1618 FROM SECTION_DEFINITION WHERE SECTION_NAME='communicationHistory' AND PAGE_TYPE='communicationHistory';


INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER)
SELECT SECTION_DEFINITION_ID, 'communicationHistory.customFieldMap[messageResponseCategory]', 10113 FROM SECTION_DEFINITION WHERE SECTION_NAME='communicationHistory' AND PAGE_TYPE='communicationHistoryView';

INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER)
SELECT SECTION_DEFINITION_ID, 'communicationHistory.customFieldMap[linkResponseCategory]', 10114 FROM SECTION_DEFINITION WHERE SECTION_NAME='communicationHistory' AND PAGE_TYPE='communicationHistoryView';

INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER)
SELECT SECTION_DEFINITION_ID, 'communicationHistory.customFieldMap[actionResponseCategory]', 10115 FROM SECTION_DEFINITION WHERE SECTION_NAME='communicationHistory' AND PAGE_TYPE='communicationHistoryView';

INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER)
SELECT SECTION_DEFINITION_ID, 'communicationHistory.customFieldMap[membershipName]', 101101 FROM SECTION_DEFINITION WHERE SECTION_NAME='communicationHistory' AND PAGE_TYPE='communicationHistoryView';

INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER)
SELECT SECTION_DEFINITION_ID, 'communicationHistory.customFieldMap[membershipType]', 10117 FROM SECTION_DEFINITION WHERE SECTION_NAME='communicationHistory' AND PAGE_TYPE='communicationHistoryView';

INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER)
SELECT SECTION_DEFINITION_ID, 'communicationHistory.customFieldMap[memberStatusChange]', 10118 FROM SECTION_DEFINITION WHERE SECTION_NAME='communicationHistory' AND PAGE_TYPE='communicationHistoryView';


UPDATE FIELD_DEFINITION SET ENTITY_ATTRIBUTES = '2dialogemail,2dialogMessageResponseGroup,2dialogLinkResponseGroup,2dialogActionResponseGroup'
WHERE FIELD_DEFINITION_ID IN ('communicationHistory.customFieldMap[packageMotiveCode]', 'communicationHistory.customFieldMap[packageName]');


SET @Next_ItemOrder=(SELECT MAX(ITEM_ORDER)+1 FROM PICKLIST_ITEM WHERE PICKLIST_ID = (SELECT PICKLIST_ID FROM PICKLIST WHERE
PICKLIST_NAME_ID = 'entryType' AND PICKLIST_NAME = 'entryType' AND SITE_NAME IS NULL));

INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, REFERENCE_VALUE, CREATE_DATE, UPDATE_DATE, ITEM_ORDER)
SELECT PICKLIST_ID, '2dialogMessageResponseGroup', '2Dialog Message Response Group', 'li:has(.ea-2dialogMessageResponseGroup)', now(), now(), @Next_ItemOrder FROM PICKLIST
WHERE PICKLIST_NAME_ID = 'entryType' AND PICKLIST_NAME = 'entryType' AND SITE_NAME IS NULL;

SET @Next_ItemOrder=(SELECT MAX(ITEM_ORDER)+1 FROM PICKLIST_ITEM WHERE PICKLIST_ID = (SELECT PICKLIST_ID FROM PICKLIST WHERE
PICKLIST_NAME_ID = 'entryType' AND PICKLIST_NAME = 'entryType' AND SITE_NAME IS NULL));

INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, REFERENCE_VALUE, CREATE_DATE, UPDATE_DATE, ITEM_ORDER)
SELECT PICKLIST_ID, '2dialogLinkResponseGroup', '2Dialog Link Response Group', 'li:has(.ea-2dialogLinkResponseGroup)', now(), now(), @Next_ItemOrder FROM PICKLIST
WHERE PICKLIST_NAME_ID = 'entryType' AND PICKLIST_NAME = 'entryType' AND SITE_NAME IS NULL;

SET @Next_ItemOrder=(SELECT MAX(ITEM_ORDER)+1 FROM PICKLIST_ITEM WHERE PICKLIST_ID = (SELECT PICKLIST_ID FROM PICKLIST WHERE
PICKLIST_NAME_ID = 'entryType' AND PICKLIST_NAME = 'entryType' AND SITE_NAME IS NULL));

INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, REFERENCE_VALUE, CREATE_DATE, UPDATE_DATE, ITEM_ORDER)
SELECT PICKLIST_ID, '2dialogActionResponseGroup', '2Dialog Action Response Group', 'li:has(.ea-2dialogActionResponseGroup)', now(), now(), @Next_ItemOrder FROM PICKLIST
WHERE PICKLIST_NAME_ID = 'entryType' AND PICKLIST_NAME = 'entryType' AND SITE_NAME IS NULL;

SET @Next_ItemOrder=(SELECT MAX(ITEM_ORDER)+1 FROM PICKLIST_ITEM WHERE PICKLIST_ID = (SELECT PICKLIST_ID FROM PICKLIST WHERE
PICKLIST_NAME_ID = 'entryType' AND PICKLIST_NAME = 'entryType' AND SITE_NAME IS NULL));

INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, REFERENCE_VALUE, CREATE_DATE, UPDATE_DATE, ITEM_ORDER)
SELECT PICKLIST_ID, '2dialogMembershipDetails', '2Dialog Membership Details', 'li:has(.ea-2dialogMembershipDetails)', now(), now(), @Next_ItemOrder FROM PICKLIST
WHERE PICKLIST_NAME_ID = 'entryType' AND PICKLIST_NAME = 'entryType' AND SITE_NAME IS NULL;

SET @Next_ItemOrder=(SELECT MAX(ITEM_ORDER)+1 FROM PICKLIST_ITEM WHERE PICKLIST_ID = (SELECT PICKLIST_ID FROM PICKLIST WHERE
PICKLIST_NAME_ID = 'entryType' AND PICKLIST_NAME = 'entryType' AND SITE_NAME IS NULL));


INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, REFERENCE_VALUE, CREATE_DATE, UPDATE_DATE, ITEM_ORDER)
SELECT PICKLIST_ID, '2dialogMessageResponseGroup', '2Dialog Message Response Group', 'li:has(.ea-2dialogMessageResponseGroup)', now(), now(), @Next_ItemOrder FROM PICKLIST
WHERE PICKLIST_NAME_ID = 'entryType' AND PICKLIST_NAME = 'entryType' AND SITE_NAME IS NOT NULL;

SET @Next_ItemOrder=(SELECT MAX(ITEM_ORDER)+1 FROM PICKLIST_ITEM WHERE PICKLIST_ID = (SELECT PICKLIST_ID FROM PICKLIST WHERE
PICKLIST_NAME_ID = 'entryType' AND PICKLIST_NAME = 'entryType' AND SITE_NAME IS NOT NULL));

INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, REFERENCE_VALUE, CREATE_DATE, UPDATE_DATE, ITEM_ORDER)
SELECT PICKLIST_ID, '2dialogLinkResponseGroup', '2Dialog Link Response Group', 'li:has(.ea-2dialogLinkResponseGroup)', now(), now(), @Next_ItemOrder FROM PICKLIST
WHERE PICKLIST_NAME_ID = 'entryType' AND PICKLIST_NAME = 'entryType' AND SITE_NAME IS NOT NULL;

SET @Next_ItemOrder=(SELECT MAX(ITEM_ORDER)+1 FROM PICKLIST_ITEM WHERE PICKLIST_ID = (SELECT PICKLIST_ID FROM PICKLIST WHERE
PICKLIST_NAME_ID = 'entryType' AND PICKLIST_NAME = 'entryType' AND SITE_NAME IS NOT NULL));

INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, REFERENCE_VALUE, CREATE_DATE, UPDATE_DATE, ITEM_ORDER)
SELECT PICKLIST_ID, '2dialogActionResponseGroup', '2Dialog Action Response Group', 'li:has(.ea-2dialogActionResponseGroup)', now(), now(), @Next_ItemOrder FROM PICKLIST
WHERE PICKLIST_NAME_ID = 'entryType' AND PICKLIST_NAME = 'entryType' AND SITE_NAME IS NOT NULL;

SET @Next_ItemOrder=(SELECT MAX(ITEM_ORDER)+1 FROM PICKLIST_ITEM WHERE PICKLIST_ID = (SELECT PICKLIST_ID FROM PICKLIST WHERE
PICKLIST_NAME_ID = 'entryType' AND PICKLIST_NAME = 'entryType' AND SITE_NAME IS NOT NULL));

INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, REFERENCE_VALUE, CREATE_DATE, UPDATE_DATE, ITEM_ORDER)
SELECT PICKLIST_ID, '2dialogMembershipDetails', '2Dialog Membership Details', 'li:has(.ea-2dialogMembershipDetails)', now(), now(), @Next_ItemOrder FROM PICKLIST
WHERE PICKLIST_NAME_ID = 'entryType' AND PICKLIST_NAME = 'entryType' AND SITE_NAME IS NOT NULL;

COMMIT;