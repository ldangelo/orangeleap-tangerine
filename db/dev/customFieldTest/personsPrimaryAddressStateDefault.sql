-- make person's primary address state default to FL

INSERT INTO FIELD_REQUIRED (SITE_NAME, ENTITY_TYPE, ENTITY_FIELD_NAME, DEFAULT_VALUE) VALUES ('company2', 'person', 'addressMap[home].stateProvince', 'FL');