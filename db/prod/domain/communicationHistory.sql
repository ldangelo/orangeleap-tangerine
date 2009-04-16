INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('communicationHistory.id', 'communicationHistory', 'id', 'Reference Number', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('communicationHistory.entryType', 'communicationHistory', 'entryType', 'Entry Type', 'PICKLIST');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('communicationHistory.recordDate', 'communicationHistory', 'recordDate', 'Date', 'DATE');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('communicationHistory.customFieldMap[assignedTo]', 'communicationHistory', 'person', 'customFieldMap[assignedTo]', 'Assigned To', 'QUERY_LOOKUP');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('communicationHistory.customFieldMap[recordedByReadOnly]', 'communicationHistory', 'person', 'customFieldMap[recordedBy]', 'Recorded By', 'QUERY_LOOKUP_DISPLAY');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('communicationHistory.comments', 'communicationHistory', 'comments', 'Comments', 'LONG_TEXT');

