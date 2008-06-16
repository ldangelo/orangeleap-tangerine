INSERT INTO SECTION_DEFINITION (PAGE_TYPE, SECTION_NAME, DEFAULT_LABEL, SECTION_ORDER, LAYOUT_TYPE) VALUES ('personSearchResults', 'personSearchResults.resultsInfo', ' ', 1, 'GRID');

INSERT INTO SECTION_FIELD (SECTION_NAME, FIELD_DEFINITION_ID, FIELD_ORDER) VALUES ('personSearchResults.resultsInfo', 'person.accountNumber', 1000);
INSERT INTO SECTION_FIELD (SECTION_NAME, FIELD_DEFINITION_ID, FIELD_ORDER) VALUES ('personSearchResults.resultsInfo', 'person.lastName', 2000);
INSERT INTO SECTION_FIELD (SECTION_NAME, FIELD_DEFINITION_ID, FIELD_ORDER) VALUES ('personSearchResults.resultsInfo', 'person.firstName', 3000);
INSERT INTO SECTION_FIELD (SECTION_NAME, FIELD_DEFINITION_ID, FIELD_ORDER) VALUES ('personSearchResults.resultsInfo', 'person.organizationName', 4000);
INSERT INTO SECTION_FIELD (SECTION_NAME, FIELD_DEFINITION_ID, FIELD_ORDER) VALUES ('personSearchResults.resultsInfo', 'person.email', 5000);
INSERT INTO SECTION_FIELD (SECTION_NAME, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, FIELD_ORDER) VALUES ('personSearchResults.resultsInfo', 'person.addressMap[primaryAddress]', 'address.postalCode', 6000);
INSERT INTO SECTION_FIELD (SECTION_NAME, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, FIELD_ORDER) VALUES ('personSearchResults.resultsInfo', 'person.addressMap[primaryAddress]', 'address.addressLine1', 7000);
INSERT INTO SECTION_FIELD (SECTION_NAME, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, FIELD_ORDER) VALUES ('personSearchResults.resultsInfo', 'person.addressMap[primaryAddress]', 'address.city', 8000);
INSERT INTO SECTION_FIELD (SECTION_NAME, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, FIELD_ORDER) VALUES ('personSearchResults.resultsInfo', 'person.addressMap[primaryAddress]', 'address.stateProvince', 9000);
INSERT INTO SECTION_FIELD (SECTION_NAME, FIELD_DEFINITION_ID, FIELD_ORDER) VALUES ('personSearchResults.resultsInfo', 'person.phoneMap[generic]', 10000);