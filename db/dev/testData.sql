-- Override a section name

INSERT INTO MESSAGE_RESOURCE (LANGUAGE_ABBREVIATION, MESSAGE_RESOURCE_TYPE, MESSAGE_KEY, MESSAGE_VALUE, SITE_ID) VALUES ('en', 'SECTION_HEADER', 'person.contactInfo', 'Hakeem Olajuwaon', 1);

-- Override a field label

INSERT INTO MESSAGE_RESOURCE (LANGUAGE_ABBREVIATION, MESSAGE_RESOURCE_TYPE, MESSAGE_KEY, MESSAGE_VALUE, SITE_ID) VALUES ('en', 'FIELD_LABEL', 'person.idReadOnly', 'Clyde Drexler', 1);

-- Override a picklist display value

INSERT INTO MESSAGE_RESOURCE (LANGUAGE_ABBREVIATION, MESSAGE_RESOURCE_TYPE, MESSAGE_KEY, MESSAGE_VALUE, SITE_ID) VALUES ('en', 'PICKLIST_VALUE', 'US', 'Otis Thorpe', 1);

INSERT INTO FIELD_REQUIRED (SITE_ID, SECTION_NAME, FIELD_DEFINITION_ID, REQUIRED) VALUES (1, 'person.contactInfo', 'person.lastName',  true);