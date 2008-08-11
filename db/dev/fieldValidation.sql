INSERT INTO MESSAGE_RESOURCE (LANGUAGE_ABBREVIATION, MESSAGE_KEY, MESSAGE_RESOURCE_TYPE, MESSAGE_VALUE) values ('en_US', 'fieldRequiredFailure.person.addressMap[primaryAddress].postalCode', 'FIELD_VALIDATION', 'Zip Code is required');
INSERT INTO MESSAGE_RESOURCE (LANGUAGE_ABBREVIATION, MESSAGE_KEY, MESSAGE_RESOURCE_TYPE, MESSAGE_VALUE) values ('en_US', 'fieldValidationFailure.person.addressMap[primaryAddress].postalCode', 'FIELD_VALIDATION', 'Zip Code value is incorrect');

INSERT INTO REQUIRED_FIELD (ENTITY_TYPE, REQUIRED, SITE_NAME, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID) values ('person', true, 'company1', 'person.addressMap[primaryAddress]', 'address.postalCode');


INSERT INTO MESSAGE_RESOURCE (LANGUAGE_ABBREVIATION, MESSAGE_KEY, MESSAGE_RESOURCE_TYPE, MESSAGE_VALUE) values ('en_US', 'fieldRequiredFailure.gift.value', 'FIELD_VALIDATION', 'Amount is required');
INSERT INTO MESSAGE_RESOURCE (LANGUAGE_ABBREVIATION, MESSAGE_KEY, MESSAGE_RESOURCE_TYPE, MESSAGE_VALUE) values ('en_US', 'fieldValidationFailure.gift.value', 'FIELD_VALIDATION', 'Amount value is incorrect');

INSERT INTO REQUIRED_FIELD (ENTITY_TYPE, REQUIRED, SITE_NAME, FIELD_DEFINITION_ID) values ('gift', true, 'company1', 'gift.value');
