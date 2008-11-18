-- Add Dog Name to company1

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, SITE_NAME) VALUES ('person.customFieldMap[dogName]1', 'person', 'customFieldMap[dogName]', 'Dog Name', 'TEXT', 'company1');
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER, SITE_NAME) VALUES (1, 'person.customFieldMap[dogName]1', 10500, 'company1');

-- Reorder Dog Name field

UPDATE SECTION_FIELD SET FIELD_ORDER=6500 WHERE FIELD_DEFINITION_ID='person.customFieldMap[dogName]1' AND SITE_NAME='company1';

-- Make Zip Code required on company2

INSERT INTO FIELD_REQUIRED (FIELD_REQUIRED_ID, SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, REQUIRED) VALUES (20, 'company2', 'person.contactInfo', 'person.addressMap[home]', 'address.postalCode', TRUE);

-- Add 5-digit validation to Zip Code on company2

INSERT INTO FIELD_VALIDATION (SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, VALIDATION_REGEX) VALUES ('company2', 'person.contactInfo', 'person.addressMap[home]', 'address.postalCode', '^\\d{5}$');

-- Change Zip Code validation on company2 to 5+4

UPDATE FIELD_VALIDATION SET VALIDATION_REGEX='^\\d{5}-\\d{4}$' WHERE SITE_NAME='company2' AND SECONDARY_FIELD_DEFINITION_ID='address.postalCode';

-- Change Zip Code error message on Company2

INSERT INTO MESSAGE_RESOURCE (LANGUAGE_ABBREVIATION, MESSAGE_KEY, MESSAGE_RESOURCE_TYPE, MESSAGE_VALUE, SITE_NAME) values ('en_US', 'fieldValidationFailure.person.addressMap[home].postalCode', 'FIELD_VALIDATION', 'Zip code format should be in the format 00000-0000', 'company2');

-- Make Spouse Name a required field only when Marital Status = Married

INSERT INTO FIELD_REQUIRED (FIELD_REQUIRED_ID, SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, REQUIRED) VALUES (21, null, 'person.contactInfo', 'person.spouse', TRUE);
INSERT INTO FIELD_CONDITION (DEPENDENT_FIELD_DEFINITION_ID, DEPENDENT_VALUE, FIELD_REQUIRED_ID, VALIDATION_ID) VALUES ('person.maritalStatus', 'Married', 21, null);