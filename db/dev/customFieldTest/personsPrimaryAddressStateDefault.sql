-- make person's primary address state default to FL

INSERT INTO FIELD_REQUIRED (SITE_ID, ENTITY_TYPE, ENTITY_FIELD_NAME, DEFAULT_VALUE) VALUES (1, 'person', 'addressMap[primaryAddress].stateProvince', 'FL');