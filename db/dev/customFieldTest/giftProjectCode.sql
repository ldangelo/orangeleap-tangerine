-- ADD CUSTOM FIELD

-- Project Code 1 on Gift
--   Field, Field Label, SectionField
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, SITE_NAME) VALUES ('gift.customFieldMap[projectCode]1', 'gift', 'customFieldMap[projectCode1]', 'Project Code 1', 'TEXT', 'company2');

-- Add to edit page
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER, SITE_NAME) VALUES (8, 'gift.customFieldMap[projectCode]1', 2500, 'company2');

-- Add to view page
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER, SITE_NAME) VALUES (4, 'gift.customFieldMap[projectCode]1', 2500, 'company2');

-- Add to search page
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER, SITE_NAME) VALUES (13, 'gift.customFieldMap[projectCode]1', 2500, 'company2');


-- Project Code 2 on Gift
--   Field, Field Label, SectionField
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, SITE_NAME) VALUES ('gift.customFieldMap[projectCode]2', 'gift', 'customFieldMap[projectCode2]', 'Project Code 2', 'TEXT', 'company2');

-- Add to edit page
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER, SITE_NAME) VALUES (8, 'gift.customFieldMap[projectCode]2', 2600, 'company2');

-- Add to view page
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER, SITE_NAME) VALUES (4, 'gift.customFieldMap[projectCode]2', 2600, 'company2');

-- Add to search page
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER, SITE_NAME) VALUES (13, 'gift.customFieldMap[projectCode]2', 2600, 'company2');