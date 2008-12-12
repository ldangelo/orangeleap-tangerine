-- add messages for required field (required field not entered) and bad field (entered value doesn't pass validation)
INSERT INTO MESSAGE_RESOURCE (LANGUAGE_ABBREVIATION, MESSAGE_KEY, MESSAGE_RESOURCE_TYPE, MESSAGE_VALUE) values ('en_US', 'fieldRequiredFailure', 'FIELD_VALIDATION', '{0} is required');
INSERT INTO MESSAGE_RESOURCE (LANGUAGE_ABBREVIATION, MESSAGE_KEY, MESSAGE_RESOURCE_TYPE, MESSAGE_VALUE) values ('en_US', 'fieldValidationFailure', 'FIELD_VALIDATION', '{0} value is incorrect');

INSERT INTO MESSAGE_RESOURCE (LANGUAGE_ABBREVIATION, MESSAGE_KEY, MESSAGE_RESOURCE_TYPE, MESSAGE_VALUE) values ('en_US', 'fieldSelfReference', 'FIELD_VALIDATION', 'Value for {0} cannot reference itself.');
INSERT INTO MESSAGE_RESOURCE (LANGUAGE_ABBREVIATION, MESSAGE_KEY, MESSAGE_RESOURCE_TYPE, MESSAGE_VALUE) values ('en_US', 'childReferenceError', 'FIELD_VALIDATION', 'A value in dependent list field {0} cannot itself reference this item as one of its dependent items.');
INSERT INTO MESSAGE_RESOURCE (LANGUAGE_ABBREVIATION, MESSAGE_KEY, MESSAGE_RESOURCE_TYPE, MESSAGE_VALUE) values ('en_US', 'parentReferenceError', 'FIELD_VALIDATION', 'Value for field {0} cannot reference a lower level item.');
INSERT INTO MESSAGE_RESOURCE (LANGUAGE_ABBREVIATION, MESSAGE_KEY, MESSAGE_RESOURCE_TYPE, MESSAGE_VALUE) values ('en_US', 'relationshipTooManyLevels', 'FIELD_VALIDATION', 'Relationship tree for {0} exceeds maximum number of levels.');





-- Add email syntax validation for company1
INSERT INTO FIELD_VALIDATION (SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, VALIDATION_REGEX) VALUES ('company1', 'person.contactInfo', 'person.emailMap[home]', 'email.emailAddress', 'extensions:isEmail');

-- Add credit card number validation for company1
-- A test credit card number is Visa 4111111111111111
INSERT INTO FIELD_VALIDATION (SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, VALIDATION_REGEX) VALUES ('company1', 'gift.creditCard', 'gift.paymentSource.creditCardNumber', 'paymentSource.creditCardNumber', 'extensions:isCreditCard');
INSERT INTO FIELD_VALIDATION (SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, VALIDATION_REGEX) VALUES ('company1', 'commitment.creditCard', 'commitment.paymentSource.creditCardNumber', 'paymentSource.creditCardNumber', 'extensions:isCreditCard');
INSERT INTO FIELD_VALIDATION (SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, VALIDATION_REGEX) VALUES ('company1', 'paymentSource.creditCard', 'paymentSource.creditCardNumber', null, 'extensions:isCreditCard');

-- make zip code required, and contain 5 digits, for company1
INSERT INTO FIELD_VALIDATION (SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, VALIDATION_REGEX) VALUES ('company1', 'person.contactInfo', 'person.addressMap[home]', 'address.postalCode', '^\\d{5}$');
INSERT INTO FIELD_REQUIRED (FIELD_REQUIRED_ID, SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, REQUIRED) VALUES (1, 'company1', 'person.contactInfo', 'person.addressMap[home]', 'address.postalCode', TRUE);

-- Gift Value messages for company1
INSERT INTO MESSAGE_RESOURCE (LANGUAGE_ABBREVIATION, MESSAGE_KEY, MESSAGE_RESOURCE_TYPE, MESSAGE_VALUE, SITE_NAME) values ('en_US', 'fieldRequiredFailure.gift.amount', 'FIELD_VALIDATION', 'Amount is required', 'company1');
INSERT INTO MESSAGE_RESOURCE (LANGUAGE_ABBREVIATION, MESSAGE_KEY, MESSAGE_RESOURCE_TYPE, MESSAGE_VALUE, SITE_NAME) values ('en_US', 'fieldValidationFailure.gift.amount', 'FIELD_VALIDATION', 'Amount is incorrect', 'company1');

-- make gift value required
INSERT INTO FIELD_REQUIRED (FIELD_REQUIRED_ID, SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, REQUIRED) VALUES (2, null, 'gift.info', 'gift.amount', null, TRUE);

-- make gift credit card info required
INSERT INTO FIELD_REQUIRED (FIELD_REQUIRED_ID, SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, REQUIRED) VALUES (3, null, 'gift.creditCard', 'gift.paymentSource.creditCardType', 'paymentSource.creditCardType', TRUE);
INSERT INTO FIELD_REQUIRED (FIELD_REQUIRED_ID, SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, REQUIRED) VALUES (4, null, 'gift.creditCard', 'gift.paymentSource.creditCardNumber', 'paymentSource.creditCardNumber', TRUE);
INSERT INTO FIELD_REQUIRED (FIELD_REQUIRED_ID, SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, REQUIRED) VALUES (5, null, 'gift.creditCard', 'gift.paymentSource.creditCardExpiration', 'paymentSource.creditCardExpiration', TRUE);
INSERT INTO FIELD_CONDITION (DEPENDENT_FIELD_DEFINITION_ID, DEPENDENT_VALUE, FIELD_REQUIRED_ID, VALIDATION_ID) VALUES ('gift.paymentType', 'Credit Card', 3, null);
INSERT INTO FIELD_CONDITION (DEPENDENT_FIELD_DEFINITION_ID, DEPENDENT_VALUE, FIELD_REQUIRED_ID, VALIDATION_ID) VALUES ('gift.paymentType', 'Credit Card', 4, null);
INSERT INTO FIELD_CONDITION (DEPENDENT_FIELD_DEFINITION_ID, DEPENDENT_VALUE, FIELD_REQUIRED_ID, VALIDATION_ID) VALUES ('gift.paymentType', 'Credit Card', 5, null);

-- make gift ach info required
INSERT INTO FIELD_REQUIRED (FIELD_REQUIRED_ID, SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, REQUIRED) VALUES (7, null, 'gift.ach', 'gift.paymentSource.achRoutingNumber', 'paymentSource.achRoutingNumber', TRUE);
INSERT INTO FIELD_REQUIRED (FIELD_REQUIRED_ID, SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, REQUIRED) VALUES (8, null, 'gift.ach', 'gift.paymentSource.achAccountNumber', 'paymentSource.achAccountNumber', TRUE);
INSERT INTO FIELD_CONDITION (DEPENDENT_FIELD_DEFINITION_ID, DEPENDENT_VALUE, FIELD_REQUIRED_ID, VALIDATION_ID) VALUES ('gift.paymentType', 'ACH', 7, null);
INSERT INTO FIELD_CONDITION (DEPENDENT_FIELD_DEFINITION_ID, DEPENDENT_VALUE, FIELD_REQUIRED_ID, VALIDATION_ID) VALUES ('gift.paymentType', 'ACH', 8, null);

-- make gift check info required
INSERT INTO FIELD_REQUIRED (FIELD_REQUIRED_ID, SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, REQUIRED) VALUES (9, null, 'gift.check', 'gift.checkNumber', null, TRUE);
INSERT INTO FIELD_CONDITION (DEPENDENT_FIELD_DEFINITION_ID, DEPENDENT_VALUE, FIELD_REQUIRED_ID, VALIDATION_ID) VALUES ('gift.paymentType', 'Check', 9, null);

-- make phone required based on primary phone selection
INSERT INTO FIELD_REQUIRED (FIELD_REQUIRED_ID, SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, REQUIRED) VALUES (10, null, 'person.contactInfo', 'person.phoneMap[home]', null, TRUE);
INSERT INTO FIELD_REQUIRED (FIELD_REQUIRED_ID, SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, REQUIRED) VALUES (11, null, 'person.contactInfo', 'person.phoneMap[work]', null, TRUE);
INSERT INTO FIELD_REQUIRED (FIELD_REQUIRED_ID, SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, REQUIRED) VALUES (12, null, 'person.contactInfo', 'person.phoneMap[mobile]', null, TRUE);
INSERT INTO FIELD_CONDITION (DEPENDENT_FIELD_DEFINITION_ID, DEPENDENT_VALUE, FIELD_REQUIRED_ID, VALIDATION_ID) VALUES ('person.preferredPhoneType', 'phoneMap[home].number', 10, null);
INSERT INTO FIELD_CONDITION (DEPENDENT_FIELD_DEFINITION_ID, DEPENDENT_VALUE, FIELD_REQUIRED_ID, VALIDATION_ID) VALUES ('person.preferredPhoneType', 'phoneMap[work].number', 11, null);
INSERT INTO FIELD_CONDITION (DEPENDENT_FIELD_DEFINITION_ID, DEPENDENT_VALUE, FIELD_REQUIRED_ID, VALIDATION_ID) VALUES ('person.preferredPhoneType', 'phoneMap[mobile].number', 12, null);

-- make address line 1 required if address line 2 has a value
INSERT INTO FIELD_REQUIRED (FIELD_REQUIRED_ID, SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, REQUIRED) VALUES (13, null, 'person.contactInfo', 'person.addressMap[home]', 'address.addressLine1', TRUE);
INSERT INTO FIELD_CONDITION (DEPENDENT_FIELD_DEFINITION_ID, DEPENDENT_SECONDARY_FIELD_DEFINITION_ID, DEPENDENT_VALUE, FIELD_REQUIRED_ID, VALIDATION_ID) VALUES ('person.addressMap[home]', 'address.addressLine2', '!null', 13, null);

-- add messages for ach
INSERT INTO MESSAGE_RESOURCE (LANGUAGE_ABBREVIATION, MESSAGE_KEY, MESSAGE_RESOURCE_TYPE, MESSAGE_VALUE) values ('en_US', 'invalidAchAccountNumber', 'FIELD_VALIDATION', 'Invalid ACH account number');
INSERT INTO MESSAGE_RESOURCE (LANGUAGE_ABBREVIATION, MESSAGE_KEY, MESSAGE_RESOURCE_TYPE, MESSAGE_VALUE) values ('en_US', 'invalidAchRoutingNumber', 'FIELD_VALIDATION', 'Invalid ACH routing number');

-- add messages for credit card
INSERT INTO MESSAGE_RESOURCE (LANGUAGE_ABBREVIATION, MESSAGE_KEY, MESSAGE_RESOURCE_TYPE, MESSAGE_VALUE) values ('en_US', 'invalidCreditCardNumber', 'FIELD_VALIDATION', 'Invalid credit card number');
INSERT INTO MESSAGE_RESOURCE (LANGUAGE_ABBREVIATION, MESSAGE_KEY, MESSAGE_RESOURCE_TYPE, MESSAGE_VALUE) values ('en_US', 'invalidCreditCardExpiration', 'FIELD_VALIDATION', 'Credit card expiration must be after today');

-- add validation to require spouse name if married
-- INSERT INTO FIELD_REQUIRED (FIELD_REQUIRED_ID, SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, REQUIRED) VALUES (15, null, 'person.contactInfo', 'person.spouseFirstName', TRUE);
-- INSERT INTO FIELD_CONDITION (DEPENDENT_FIELD_DEFINITION_ID, DEPENDENT_VALUE, FIELD_REQUIRED_ID, VALIDATION_ID) VALUES ('person.maritalStatus', 'Married', 15, null);
