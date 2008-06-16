-- make person's custom field, dogName, default to Spot

INSERT INTO FIELD_REQUIRED (SITE_ID, ENTITY_TYPE, ENTITY_FIELD_NAME, DEFAULT_VALUE) VALUES (1, 'person', 'customFieldMap[dogName].value', 'Spot');