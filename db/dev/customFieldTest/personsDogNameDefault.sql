-- make person's custom field, dogName, default to Spot

INSERT INTO FIELD_REQUIRED (SITE_NAME, ENTITY_TYPE, ENTITY_FIELD_NAME, DEFAULT_VALUE) VALUES ('company2', 'person', 'customFieldMap[dogName].value', 'Spot');