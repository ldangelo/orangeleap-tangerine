-- ADD CUSTOM FIELD

-- Project Code 1 on Gift
--   Field, Field Label, SectionField
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, SITE_NAME) VALUES ('gift.customFieldMap[projectCode]1', 'gift', 'customFieldMap[projectCode1]', 'Project Code 1', 'TEXT', 'CrederaDev');

-- Add to edit page
INSERT INTO SECTION_FIELD (SECTION_NAME, FIELD_DEFINITION_ID, FIELD_ORDER) VALUES ('gift.info', 'gift.customFieldMap[projectCode]1', 2500);

-- Add to view page
INSERT INTO SECTION_FIELD (SECTION_NAME, FIELD_DEFINITION_ID, FIELD_ORDER) VALUES ('giftView.info', 'gift.customFieldMap[projectCode]1', 2500);

-- Add to search page
INSERT INTO SECTION_FIELD (SECTION_NAME, FIELD_DEFINITION_ID, FIELD_ORDER) VALUES ('giftSearch.searchInfo', 'gift.customFieldMap[projectCode]1', 2500);


-- Project Code 2 on Gift
--   Field, Field Label, SectionField
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, SITE_NAME) VALUES ('gift.customFieldMap[projectCode]2', 'gift', 'customFieldMap[projectCode2]', 'Project Code 2', 'TEXT', 'CrederaDev');

-- Add to edit page
INSERT INTO SECTION_FIELD (SECTION_NAME, FIELD_DEFINITION_ID, FIELD_ORDER) VALUES ('gift.info', 'gift.customFieldMap[projectCode]2', 2600);

-- Add to view page
INSERT INTO SECTION_FIELD (SECTION_NAME, FIELD_DEFINITION_ID, FIELD_ORDER) VALUES ('giftView.info', 'gift.customFieldMap[projectCode]2', 2600);

-- Add to search page
INSERT INTO SECTION_FIELD (SECTION_NAME, FIELD_DEFINITION_ID, FIELD_ORDER) VALUES ('giftSearch.searchInfo', 'gift.customFieldMap[projectCode]2', 2600);