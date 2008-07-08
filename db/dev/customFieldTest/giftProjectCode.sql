--
-- ADD CUSTOM FIELD

-- Project Code on Gift
--   Field, Field Label, SectionField
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, SITE_ID) VALUES ('gift.customFieldMap[projectCode]1', 'gift', 'customFieldMap[projectCode]', 'Project Code', 'TEXT', 1);

-- Add to edit page
INSERT INTO SECTION_FIELD (SECTION_NAME, FIELD_DEFINITION_ID, FIELD_ORDER) VALUES ('gift.info', 'gift.customFieldMap[projectCode]1', 2500);

-- Add to view page
INSERT INTO SECTION_FIELD (SECTION_NAME, FIELD_DEFINITION_ID, FIELD_ORDER) VALUES ('giftView.info', 'gift.customFieldMap[projectCode]1', 2500);

-- Add to search page
INSERT INTO SECTION_FIELD (SECTION_NAME, FIELD_DEFINITION_ID, FIELD_ORDER) VALUES ('giftSearch.searchInfo', 'gift.customFieldMap[projectCode]1', 2500);
