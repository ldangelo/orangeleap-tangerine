-- make Last Name and First Name required for a constituent

INSERT INTO FIELD_REQUIRED (FIELD_REQUIRED_ID, SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, REQUIRED) VALUES (1, 'company2', 'constituent.contactInfo', 'constituent.lastName',null, TRUE);
INSERT INTO FIELD_REQUIRED (FIELD_REQUIRED_ID, SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, REQUIRED) VALUES (2, 'company2', 'constituent.contactInfo', 'constituent.firstName',null, TRUE);