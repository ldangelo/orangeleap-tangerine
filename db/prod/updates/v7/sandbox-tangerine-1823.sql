SET AUTOCOMMIT = 0;

UPDATE sandbox.FIELD_DEFINITION set ENTITY_ATTRIBUTES = 'email,2dialogemail'
WHERE FIELD_DEFINITION_ID IN ('communicationHistory.email.id', 'communicationHistory.email.emailAddressReadOnly') AND ENTITY_TYPE = 'communicationHistory';

INSERT INTO sandbox.FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES
('communicationHistory.customFieldMap[packageMotiveCode]', 'communicationHistory', 'customFieldMap[packageMotiveCode]', 'Package Motive Code', '2dialogemail', 'TEXT');

INSERT INTO sandbox.FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES
('communicationHistory.customFieldMap[packageName]', 'communicationHistory', 'customFieldMap[packageName]', 'Package Name', '2dialogemail', 'TEXT');

INSERT INTO sandbox.FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES
('communicationHistory.customFieldMap[subjectLine]', 'communicationHistory', 'customFieldMap[subjectLine]', 'Subject Line', '2dialogemail', 'TEXT');

INSERT INTO sandbox.SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER)
SELECT SECTION_DEFINITION_ID, 'communicationHistory.customFieldMap[packageMotiveCode]', 1610
FROM sandbox.SECTION_DEFINITION WHERE PAGE_TYPE = 'communicationHistory' AND SECTION_NAME = 'communicationHistory';

INSERT INTO sandbox.SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER)
SELECT SECTION_DEFINITION_ID, 'communicationHistory.customFieldMap[packageName]', 1611
FROM sandbox.SECTION_DEFINITION WHERE PAGE_TYPE = 'communicationHistory' AND SECTION_NAME = 'communicationHistory';

INSERT INTO sandbox.SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER)
SELECT SECTION_DEFINITION_ID, 'communicationHistory.customFieldMap[subjectLine]', 1612
FROM sandbox.SECTION_DEFINITION WHERE PAGE_TYPE = 'communicationHistory' AND SECTION_NAME = 'communicationHistory';

INSERT INTO sandbox.SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER)
SELECT SECTION_DEFINITION_ID, 'communicationHistory.customFieldMap[packageMotiveCode]', 10110
FROM sandbox.SECTION_DEFINITION WHERE PAGE_TYPE = 'communicationHistoryView' AND SECTION_NAME = 'communicationHistory';

INSERT INTO sandbox.SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER)
SELECT SECTION_DEFINITION_ID, 'communicationHistory.customFieldMap[packageName]', 10111
FROM sandbox.SECTION_DEFINITION WHERE PAGE_TYPE = 'communicationHistoryView' AND SECTION_NAME = 'communicationHistory';

INSERT INTO sandbox.SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER)
SELECT SECTION_DEFINITION_ID, 'communicationHistory.customFieldMap[subjectLine]', 10112 
FROM sandbox.SECTION_DEFINITION WHERE PAGE_TYPE = 'communicationHistoryView' AND SECTION_NAME = 'communicationHistory';

UPDATE sandbox.PICKLIST_ITEM SET REFERENCE_VALUE = 'li:has(.ea-2dialogemail)' WHERE ITEM_NAME = '2dialogemail' AND DEFAULT_DISPLAY_VALUE = '2Dialog Email';

COMMIT;