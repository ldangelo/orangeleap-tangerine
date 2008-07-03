--
-- ADD CUSTOM FIELD

-- Dog Name on Person
--   Field, Field Label, SectionField
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, SITE_ID) VALUES ('gift.customFieldMap[projectCode]1', 'gift', 'customFieldMap[projectCode]', 'Project Code', 'TEXT', 1);
INSERT INTO SECTION_FIELD (SECTION_NAME, FIELD_DEFINITION_ID, FIELD_ORDER) VALUES ('gift.info', 'gift.customFieldMap[projectCode]1', 2500);
INSERT INTO SECTION_FIELD (SECTION_NAME, FIELD_DEFINITION_ID, FIELD_ORDER) VALUES ('giftView.info', 'gift.customFieldMap[projectCode]1', 2500);
