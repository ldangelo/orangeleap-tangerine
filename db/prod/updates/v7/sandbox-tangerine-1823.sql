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

INSERT INTO sandbox.PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, REFERENCE_VALUE, ITEM_ORDER)
SELECT PICKLIST_ID, '2dialogemail', '2Dialog Email', 'li:has(.ea-2dialogemail)', 10 
FROM sandbox.PICKLIST WHERE PICKLIST_NAME_ID = 'entryType' AND PICKLIST_NAME = 'entryType';

COMMIT;