INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('address.addressType', 'address', 'addressType', 'Address Type', 'PICKLIST');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('address.activationStatus', 'address', 'activationStatus', 'Address Status', 'PICKLIST');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('address.effectiveDate', 'address', 'effectiveDate', 'Effective Date', 'DATE');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('address.seasonalStartDate', 'address', 'seasonalStartDate', 'Seasonal Start Date', 'DATE');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('address.seasonalEndDate', 'address', 'seasonalEndDate', 'Seasonal End Date', 'DATE');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('address.temporaryStartDate', 'address', 'temporaryStartDate', 'Temporary Start Date', 'DATE');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('address.temporaryEndDate', 'address', 'temporaryEndDate', 'Temporary End Date', 'DATE');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('address.addressLine1', 'address', 'addressLine1', 'Address Line 1', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('address.addressLine2', 'address', 'addressLine2', 'Address Line 2', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('address.addressLine3', 'address', 'addressLine3', 'Address Line 3', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('address.country', 'address', 'country', 'Country', 'PICKLIST');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('address.postalCode', 'address', 'postalCode', 'Zip Code', 'NUMBER');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('address.city', 'address', 'city', 'City', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('address.stateProvince', 'address', 'stateProvince', 'State', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('address.receiveMail', 'address', 'receiveMail', 'Receive Correspondence', 'CHECKBOX');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('address.comments', 'address', 'comments', 'Comments', 'LONG_TEXT');

INSERT INTO PICKLIST (PICKLIST_ID, PICKLIST_NAME) VALUES ('addressType', 'addressType');
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, ITEM_ORDER) VALUES ('addressType', 'home', 'Home', 1);
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, ITEM_ORDER) VALUES ('addressType', 'work', 'Work', 2);

INSERT INTO PICKLIST (PICKLIST_ID, PICKLIST_NAME) VALUES ('activationStatus', 'activationStatus');
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, REFERENCE_VALUE, ITEM_ORDER) VALUES ('activationStatus', 'permanent', 'Permanent', 'li:has(#effectiveDate)', 1);
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, REFERENCE_VALUE, ITEM_ORDER) VALUES ('activationStatus', 'seasonal', 'Seasonal', 'li:has(#seasonalStartDate), li:has(#seasonalEndDate)', 2); 
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, REFERENCE_VALUE, ITEM_ORDER) VALUES ('activationStatus', 'temporary', 'Temporary', 'li:has(#temporaryStartDate), li:has(#temporaryEndDate)', 3); 
