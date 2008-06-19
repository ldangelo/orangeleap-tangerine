-- make Last Name and First Name required for a person

INSERT INTO FIELD_REQUIRED (FIELD_REQUIRED_ID, SITE_ID, SECTION_NAME, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, REQUIRED) VALUES (1, 1, 'person.contactInfo', 'person.lastName',null, TRUE);