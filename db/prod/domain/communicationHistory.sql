INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('communicationHistory.id', 'communicationHistory', 'id', 'Reference Number', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('communicationHistory.entryType', 'communicationHistory', 'entryType', 'Entry Type', 'PICKLIST');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('communicationHistory.recordDate', 'communicationHistory', 'recordDate', 'Date', 'DATE');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('communicationHistory.customFieldMap[assignedTo]', 'communicationHistory', 'person', 'customFieldMap[assignedTo]', 'Assigned To', 'QUERY_LOOKUP');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('communicationHistory.customFieldMap[recordedByReadOnly]', 'communicationHistory', 'person', 'customFieldMap[recordedBy]', 'Recorded By', 'QUERY_LOOKUP_DISPLAY');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('communicationHistory.comments', 'communicationHistory', 'comments', 'Comments', 'LONG_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('communicationHistory.selectedAddress', 'communicationHistory', 'selectedAddress', 'Address', 'address', 'EXISTING_ADDRESS_PICKLIST');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('communicationHistory.selectedPhone', 'communicationHistory', 'selectedPhone', 'Phone', 'phone', 'EXISTING_PHONE_PICKLIST');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('communicationHistory.selectedEmail', 'communicationHistory', 'selectedEmail', 'Email', 'email', 'EXISTING_EMAIL_PICKLIST');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('communicationHistory.customFieldMap[template]', 'communicationHistory', 'customFieldMap[template]', 'Template', 'template', 'TEXT');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('communicationHistory.entryTypeReadOnly', 'communicationHistory', 'entryType', 'Entry Type', 'PICKLIST_DISPLAY');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('communicationHistory.selectedAddress.addressLine1ReadOnly', 'communicationHistory', 'selectedAddress', 'Address Line 1', 'address', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('communicationHistory.selectedAddress.addressLine2ReadOnly', 'communicationHistory', 'selectedAddress', 'Address Line 2', 'address', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('communicationHistory.selectedAddress.addressLine3ReadOnly', 'communicationHistory', 'selectedAddress', 'Address Line 3', 'address', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('communicationHistory.selectedAddress.cityReadOnly', 'communicationHistory', 'selectedAddress', 'City', 'address', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('communicationHistory.selectedAddress.stateProvinceReadOnly', 'communicationHistory', 'selectedAddress', 'State/Province', 'address', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('communicationHistory.selectedAddress.postalCodeReadOnly', 'communicationHistory', 'selectedAddress', 'Zip/Postal Code', 'address', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('communicationHistory.selectedAddress.countryReadOnly', 'communicationHistory', 'selectedAddress', 'Country', 'address', 'READ_ONLY_TEXT');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('communicationHistory.selectedPhone.numberReadOnly', 'communicationHistory', 'selectedPhone', 'Phone Number', 'phone', 'READ_ONLY_TEXT');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('communicationHistory.selectedEmail.emailAddressReadOnly', 'communicationHistory', 'selectedEmail', 'Email', 'email', 'READ_ONLY_TEXT');


