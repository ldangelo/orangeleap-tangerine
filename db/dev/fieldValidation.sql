-- add messages for required field (required field not entered) and bad field (entered value doesn't pass validation)
INSERT INTO MESSAGE_RESOURCE (LANGUAGE_ABBREVIATION, MESSAGE_KEY, MESSAGE_RESOURCE_TYPE, MESSAGE_VALUE) values ('en_US', 'fieldRequiredFailure', 'FIELD_VALIDATION', '{0} is required');
INSERT INTO MESSAGE_RESOURCE (LANGUAGE_ABBREVIATION, MESSAGE_KEY, MESSAGE_RESOURCE_TYPE, MESSAGE_VALUE) values ('en_US', 'fieldValidationFailure', 'FIELD_VALIDATION', '{0} value is incorrect');

-- make zip code required, and contain 5 digits, for company1
INSERT INTO FIELD_VALIDATION (SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, VALIDATION_REGEX) VALUES ('company1', 'person.contactInfo', 'person.addressMap[primaryAddress]', 'address.postalCode', '^\\d{5}$');
INSERT INTO FIELD_REQUIRED (SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, REQUIRED) VALUES ('company1', 'person.contactInfo', 'person.addressMap[primaryAddress]', 'address.postalCode', TRUE);

-- Gift Value messages for company1 
INSERT INTO MESSAGE_RESOURCE (LANGUAGE_ABBREVIATION, MESSAGE_KEY, MESSAGE_RESOURCE_TYPE, MESSAGE_VALUE, SITE_NAME) values ('en_US', 'fieldRequiredFailure.gift.value', 'FIELD_VALIDATION', 'Amount is required', 'company1');
INSERT INTO MESSAGE_RESOURCE (LANGUAGE_ABBREVIATION, MESSAGE_KEY, MESSAGE_RESOURCE_TYPE, MESSAGE_VALUE, SITE_NAME) values ('en_US', 'fieldValidationFailure.gift.value', 'FIELD_VALIDATION', 'Amount is incorrect', 'company1');

-- make gift value required
INSERT INTO FIELD_REQUIRED (SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, REQUIRED) VALUES (null, 'gift.info', 'gift.value', null, TRUE);
