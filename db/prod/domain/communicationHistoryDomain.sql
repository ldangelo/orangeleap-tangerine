INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('communicationHistory.id', 'communicationHistory', 'id', 'Reference Number', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('communicationHistory.entryType', 'communicationHistory', 'entryType', 'Entry Type', 'PICKLIST');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('communicationHistory.recordDate', 'communicationHistory', 'recordDate', 'Date', 'DATE');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('communicationHistory.customFieldMap[assignedTo]', 'communicationHistory', 'constituent', 'customFieldMap[assignedTo]', 'Assigned To', 'QUERY_LOOKUP');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('communicationHistory.customFieldMap[recordedByReadOnly]', 'communicationHistory', 'constituent', 'customFieldMap[recordedBy]', 'Recorded By', 'QUERY_LOOKUP_DISPLAY');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('communicationHistory.comments', 'communicationHistory', 'comments', 'Comments', 'LONG_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('communicationHistory.address.id', 'communicationHistory', 'address', 'Address', 'address', 'EXISTING_ADDRESS_PICKLIST');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('communicationHistory.phone.id', 'communicationHistory', 'phone', 'Phone', 'phone', 'EXISTING_PHONE_PICKLIST');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('communicationHistory.email.id', 'communicationHistory', 'email', 'Email', 'email,2dialogemail', 'EXISTING_EMAIL_PICKLIST');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('communicationHistory.customFieldMap[template]', 'communicationHistory', 'customFieldMap[template]', 'Template', 'template', 'TEXT');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('communicationHistory.entryTypeReadOnly', 'communicationHistory', 'entryType', 'Entry Type', 'PICKLIST_DISPLAY');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('communicationHistory.address.addressLine1ReadOnly', 'communicationHistory', 'address', 'Address Line 1', 'address', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('communicationHistory.address.addressLine2ReadOnly', 'communicationHistory', 'address', 'Address Line 2', 'address', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('communicationHistory.address.addressLine3ReadOnly', 'communicationHistory', 'address', 'Address Line 3', 'address', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('communicationHistory.address.cityReadOnly', 'communicationHistory', 'address', 'City', 'address', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('communicationHistory.address.stateProvinceReadOnly', 'communicationHistory', 'address', 'State/Province', 'address', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('communicationHistory.address.postalCodeReadOnly', 'communicationHistory', 'address', 'Zip/Postal Code', 'address', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('communicationHistory.address.countryReadOnly', 'communicationHistory', 'address', 'Country', 'address', 'READ_ONLY_TEXT');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('communicationHistory.phone.numberReadOnly', 'communicationHistory', 'phone', 'Phone Number', 'phone', 'READ_ONLY_TEXT');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('communicationHistory.email.emailAddressReadOnly', 'communicationHistory', 'email', 'Email', 'email,2dialogemail', 'READ_ONLY_TEXT');


INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('communicationHistory.customFieldMap[correspondenceFor]', 'communicationHistory', 'customFieldMap[correspondenceFor]', 'Correspondence For', 'PICKLIST');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('communicationHistory.customFieldMap[noteType]', 'communicationHistory', 'customFieldMap[noteType]', 'Note Type', 'noteType', 'PICKLIST');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('communicationHistory.customFieldMap[event]', 'communicationHistory', 'customFieldMap[event]', 'Event', 'event', 'PICKLIST');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('communicationHistory.customFieldMap[eventParticipation]', 'communicationHistory', 'customFieldMap[eventParticipation]', 'Event Participation', 'event', 'PICKLIST');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('communicationHistory.customFieldMap[eventDate]', 'communicationHistory', 'customFieldMap[eventDate]', 'Event Date', 'event', 'DATE');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('communicationHistory.customFieldMap[eventLocation]', 'communicationHistory', 'customFieldMap[eventLocation]', 'Event Location', 'event', 'TEXT');


INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES
('communicationHistory.customFieldMap[packageMotiveCode]', 'communicationHistory', 'customFieldMap[packageMotiveCode]', 'Package Motive Code', '2dialogemail,2dialogMessageResponseGroup,2dialogLinkResponseGroup,2dialogActionResponseGroup', 'TEXT');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES
('communicationHistory.customFieldMap[packageName]', 'communicationHistory', 'customFieldMap[packageName]', 'Package Name', '2dialogemail,2dialogMessageResponseGroup,2dialogLinkResponseGroup,2dialogActionResponseGroup', 'TEXT');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES
('communicationHistory.customFieldMap[subjectLine]', 'communicationHistory', 'customFieldMap[subjectLine]', 'Subject Line', '2dialogemail', 'TEXT');


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
