--
-- ADD CUSTOM FIELD

-- Dog Name on Person
--   Field, Field Label, SectionField
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, SITE_NAME) VALUES ('person.customFieldMap[dogName]1', 'person', 'customFieldMap[dogName]', 'Dog Name', 'TEXT', 'company1');
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER, SITE_NAME) VALUES (1, 'person.customFieldMap[dogName]1', 10500, 'company1');
INSERT INTO FIELD_REQUIRED (SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, REQUIRED) VALUES ('company1', 'person.contactInfo', 'person.customFieldMap[dogName]1',  true);
