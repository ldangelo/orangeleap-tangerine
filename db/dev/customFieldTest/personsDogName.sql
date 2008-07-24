--
-- ADD CUSTOM FIELD

-- Dog Name on Person
--   Field, Field Label, SectionField
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, SITE_NAME) VALUES ('person.customFieldMap[dogName]1', 'person', 'customFieldMap[dogName]', 'Dog Name', 'TEXT', 'CrederaDev');
INSERT INTO SECTION_FIELD (SECTION_NAME, FIELD_DEFINITION_ID, FIELD_ORDER) VALUES ('person.contactInfo', 'person.customFieldMap[dogName]1', 10500);
INSERT INTO FIELD_REQUIRED (SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, REQUIRED) VALUES ('CrederaDev', 'person.contactInfo', 'person.customFieldMap[dogName]1',  true);
-- Cat Name on Person
--   Field, Field Label, SectionField
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, SITE_NAME) VALUES ('person.customFieldMap[catName]1', 'person', 'customFieldMap[catName]', 'Cat Name', 'TEXT', 'CrederaDev');
INSERT INTO SECTION_FIELD (SECTION_NAME, FIELD_DEFINITION_ID, FIELD_ORDER) VALUES ('person.contactInfo', 'person.customFieldMap[catName]1', 13500);
INSERT INTO FIELD_REQUIRED (SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, REQUIRED) VALUES ('CrederaDev', 'person.contactInfo', 'person.customFieldMap[catName]1',  true);
--
-- REMOVE OUT OF BOX FIELD
INSERT INTO SECTION_FIELD (SECTION_NAME, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, FIELD_ORDER, SITE_NAME) VALUES ('person.contactInfo', 'person.addressMap[primaryAddress]', 'address.country', 0, 'CrederaDev');
INSERT INTO SECTION_FIELD (SECTION_NAME, FIELD_DEFINITION_ID, FIELD_ORDER, SITE_NAME) VALUES ('person.contactInfo', 'person.phoneMap[home]', 0, 'CrederaDev');

-- REORDER OUT OF BOX FIELD
INSERT INTO SECTION_FIELD (SECTION_NAME, FIELD_DEFINITION_ID, FIELD_ORDER, SITE_NAME) VALUES ('person.contactInfo', 'person.lastName', 2500, 'CrederaDev');