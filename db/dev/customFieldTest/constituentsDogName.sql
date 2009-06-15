--
-- ADD CUSTOM FIELD

-- Dog Name on Constituent
--   Field, Field Label, SectionField
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, SITE_NAME) VALUES ('constituent.customFieldMap[dogName]1', 'constituent', 'customFieldMap[dogName]', 'Dog Name', 'TEXT', 'company2');
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER, SITE_NAME) VALUES (1, 'constituent.customFieldMap[dogName]1', 10500, 'company2');
INSERT INTO FIELD_REQUIRED (SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, REQUIRED) VALUES ('company2', 'constituent.contactInfo', 'constituent.customFieldMap[dogName]1',  true);

-- Cat Name on Constituent
--   Field, Field Label, SectionField
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, SITE_NAME) VALUES ('constituent.customFieldMap[catName]1', 'constituent', 'customFieldMap[catName]', 'Cat Name', 'TEXT', 'company2');
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER, SITE_NAME) VALUES (1, 'constituent.customFieldMap[catName]1', 13500, 'company2');
INSERT INTO FIELD_REQUIRED (SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, REQUIRED) VALUES ('company2', 'constituent.contactInfo', 'constituent.customFieldMap[catName]1',  true);
--
-- REMOVE OUT OF BOX FIELD
--INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, FIELD_ORDER, SITE_NAME) VALUES (1, 'constituent.addressMap[home]', 'address.country', 0, 'company2');
--INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER, SITE_NAME) VALUES (1, 'constituent.phoneMap[home]', 0, 'company2');

-- REORDER OUT OF BOX FIELD
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER, SITE_NAME) VALUES (1, 'constituent.lastName', 2500, 'company2');