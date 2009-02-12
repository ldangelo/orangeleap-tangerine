-- add messages for required field (required field not entered) and bad field (entered value doesn't pass validation)
INSERT INTO MESSAGE_RESOURCE (LANGUAGE_ABBREVIATION, MESSAGE_KEY, MESSAGE_RESOURCE_TYPE, MESSAGE_VALUE) values ('en_US', 'fieldRequiredFailure', 'FIELD_VALIDATION', '{0} is required');
INSERT INTO MESSAGE_RESOURCE (LANGUAGE_ABBREVIATION, MESSAGE_KEY, MESSAGE_RESOURCE_TYPE, MESSAGE_VALUE) values ('en_US', 'fieldValidationFailure', 'FIELD_VALIDATION', '{0} value is incorrect');

INSERT INTO MESSAGE_RESOURCE (LANGUAGE_ABBREVIATION, MESSAGE_KEY, MESSAGE_RESOURCE_TYPE, MESSAGE_VALUE) values ('en_US', 'fieldSelfReference', 'FIELD_VALIDATION', 'Value for {0} cannot reference itself.');
INSERT INTO MESSAGE_RESOURCE (LANGUAGE_ABBREVIATION, MESSAGE_KEY, MESSAGE_RESOURCE_TYPE, MESSAGE_VALUE) values ('en_US', 'childReferenceError', 'FIELD_VALIDATION', 'A value in list field {0} cannot reference a higher level item.');
INSERT INTO MESSAGE_RESOURCE (LANGUAGE_ABBREVIATION, MESSAGE_KEY, MESSAGE_RESOURCE_TYPE, MESSAGE_VALUE) values ('en_US', 'parentReferenceError', 'FIELD_VALIDATION', 'Value for field {0} cannot reference a lower level item.');
INSERT INTO MESSAGE_RESOURCE (LANGUAGE_ABBREVIATION, MESSAGE_KEY, MESSAGE_RESOURCE_TYPE, MESSAGE_VALUE) values ('en_US', 'relationshipTooManyLevels', 'FIELD_VALIDATION', 'Relationship tree for {0} exceeds maximum number of levels.');


INSERT INTO FIELD_REQUIRED (FIELD_REQUIRED_ID, SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, REQUIRED) VALUES (1, null, 'person.contactInfo', 'person.constituentType', null, TRUE);
INSERT INTO FIELD_REQUIRED (FIELD_REQUIRED_ID, SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, REQUIRED) VALUES (148, null, 'person.contactInfo', 'person.firstName', null, TRUE);
INSERT INTO FIELD_CONDITION (DEPENDENT_FIELD_DEFINITION_ID, DEPENDENT_VALUE, FIELD_REQUIRED_ID, VALIDATION_ID) VALUES ('person.constituentType', 'individual', 148, null);
INSERT INTO FIELD_REQUIRED (FIELD_REQUIRED_ID, SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, REQUIRED) VALUES (149, null, 'person.contactInfo', 'person.lastName', null, TRUE);
INSERT INTO FIELD_CONDITION (DEPENDENT_FIELD_DEFINITION_ID, DEPENDENT_VALUE, FIELD_REQUIRED_ID, VALIDATION_ID) VALUES ('person.constituentType', 'individual', 149, null);

INSERT INTO FIELD_REQUIRED (FIELD_REQUIRED_ID, SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, REQUIRED) VALUES (300, null, 'person.contactInfo', 'person.organizationName', null, TRUE);
INSERT INTO FIELD_CONDITION (DEPENDENT_FIELD_DEFINITION_ID, DEPENDENT_VALUE, FIELD_REQUIRED_ID, VALIDATION_ID) VALUES ('person.constituentType', 'organization', 300, null);

-- Add email syntax validation 
INSERT INTO FIELD_VALIDATION (SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, VALIDATION_REGEX) VALUES (null, 'person.contactInfo', 'person.emailMap[home]', 'email.emailAddress', 'extensions:isEmail');

-- Add credit card number validation 
-- A test credit card number is Visa 4111111111111111
INSERT INTO FIELD_VALIDATION (SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, VALIDATION_REGEX) VALUES (null, 'gift.creditCard', 'gift.paymentSource.creditCardNumber', 'paymentSource.creditCardNumber', 'extensions:isCreditCard');
INSERT INTO FIELD_VALIDATION (SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, VALIDATION_REGEX) VALUES (null, 'commitment.creditCard', 'commitment.paymentSource.creditCardNumber', 'paymentSource.creditCardNumber', 'extensions:isCreditCard');
INSERT INTO FIELD_VALIDATION (SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, VALIDATION_REGEX) VALUES (null, 'paymentSource.creditCard', 'paymentSource.creditCardNumber', null, 'extensions:isCreditCard');

-- Gift Value messages
INSERT INTO MESSAGE_RESOURCE (LANGUAGE_ABBREVIATION, MESSAGE_KEY, MESSAGE_RESOURCE_TYPE, MESSAGE_VALUE, SITE_NAME) values ('en_US', 'fieldRequiredFailure.gift.amount', 'FIELD_VALIDATION', 'Amount is required', null);
INSERT INTO MESSAGE_RESOURCE (LANGUAGE_ABBREVIATION, MESSAGE_KEY, MESSAGE_RESOURCE_TYPE, MESSAGE_VALUE, SITE_NAME) values ('en_US', 'fieldValidationFailure.gift.amount', 'FIELD_VALIDATION', 'Amount is incorrect', null);

-- Gift value required
INSERT INTO FIELD_REQUIRED (FIELD_REQUIRED_ID, SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, REQUIRED) VALUES (2, null, 'gift.donation', 'gift.amount', null, TRUE);

-- Gift payment source required 
INSERT INTO FIELD_REQUIRED (FIELD_REQUIRED_ID, SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, REQUIRED) VALUES (3, null, 'gift.payment', 'gift.selectedPaymentSource', null, TRUE);

-- Gift credit card info required
INSERT INTO FIELD_REQUIRED (FIELD_REQUIRED_ID, SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, REQUIRED) VALUES (4, null, 'gift.creditCard', 'gift.paymentSource.creditCardHolderName', 'paymentSource.creditCardHolderName', TRUE);
INSERT INTO FIELD_REQUIRED (FIELD_REQUIRED_ID, SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, REQUIRED) VALUES (5, null, 'gift.creditCard', 'gift.paymentSource.creditCardType', 'paymentSource.creditCardType', TRUE);
INSERT INTO FIELD_REQUIRED (FIELD_REQUIRED_ID, SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, REQUIRED) VALUES (6, null, 'gift.creditCard', 'gift.paymentSource.creditCardNumber', 'paymentSource.creditCardNumber', TRUE);
INSERT INTO FIELD_REQUIRED (FIELD_REQUIRED_ID, SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, REQUIRED) VALUES (7, null, 'gift.creditCard', 'gift.paymentSource.creditCardExpiration', 'paymentSource.creditCardExpiration', TRUE);
INSERT INTO FIELD_CONDITION (DEPENDENT_FIELD_DEFINITION_ID, DEPENDENT_VALUE, FIELD_REQUIRED_ID, VALIDATION_ID) VALUES ('gift.paymentType', 'Credit Card', 4, null);
INSERT INTO FIELD_CONDITION (DEPENDENT_FIELD_DEFINITION_ID, DEPENDENT_VALUE, FIELD_REQUIRED_ID, VALIDATION_ID) VALUES ('gift.paymentType', 'Credit Card', 5, null);
INSERT INTO FIELD_CONDITION (DEPENDENT_FIELD_DEFINITION_ID, DEPENDENT_VALUE, FIELD_REQUIRED_ID, VALIDATION_ID) VALUES ('gift.paymentType', 'Credit Card', 6, null);
INSERT INTO FIELD_CONDITION (DEPENDENT_FIELD_DEFINITION_ID, DEPENDENT_VALUE, FIELD_REQUIRED_ID, VALIDATION_ID) VALUES ('gift.paymentType', 'Credit Card', 7, null);

-- Gift ach info required
INSERT INTO FIELD_REQUIRED (FIELD_REQUIRED_ID, SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, REQUIRED) VALUES (8, null, 'gift.ach', 'gift.paymentSource.achHolderName', 'paymentSource.achHolderName', TRUE);
INSERT INTO FIELD_REQUIRED (FIELD_REQUIRED_ID, SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, REQUIRED) VALUES (9, null, 'gift.ach', 'gift.paymentSource.achRoutingNumber', 'paymentSource.achRoutingNumber', TRUE);
INSERT INTO FIELD_REQUIRED (FIELD_REQUIRED_ID, SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, REQUIRED) VALUES (10, null, 'gift.ach', 'gift.paymentSource.achAccountNumber', 'paymentSource.achAccountNumber', TRUE);
INSERT INTO FIELD_CONDITION (DEPENDENT_FIELD_DEFINITION_ID, DEPENDENT_VALUE, FIELD_REQUIRED_ID, VALIDATION_ID) VALUES ('gift.paymentType', 'ACH', 8, null);
INSERT INTO FIELD_CONDITION (DEPENDENT_FIELD_DEFINITION_ID, DEPENDENT_VALUE, FIELD_REQUIRED_ID, VALIDATION_ID) VALUES ('gift.paymentType', 'ACH', 9, null);
INSERT INTO FIELD_CONDITION (DEPENDENT_FIELD_DEFINITION_ID, DEPENDENT_VALUE, FIELD_REQUIRED_ID, VALIDATION_ID) VALUES ('gift.paymentType', 'ACH', 10, null);

INSERT INTO FIELD_REQUIRED (FIELD_REQUIRED_ID, SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, REQUIRED) VALUES (11, null, 'gift.payment', 'gift.address.addressLine1', 'address.addressLine1', TRUE);
INSERT INTO FIELD_REQUIRED (FIELD_REQUIRED_ID, SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, REQUIRED) VALUES (12, null, 'gift.payment', 'gift.address.city', 'address.city', TRUE);
INSERT INTO FIELD_REQUIRED (FIELD_REQUIRED_ID, SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, REQUIRED) VALUES (13, null, 'gift.payment', 'gift.address.stateProvince', 'address.stateProvince', TRUE);
INSERT INTO FIELD_REQUIRED (FIELD_REQUIRED_ID, SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, REQUIRED) VALUES (14, null, 'gift.payment', 'gift.address.postalCode', 'address.postalCode', TRUE);
INSERT INTO FIELD_REQUIRED (FIELD_REQUIRED_ID, SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, REQUIRED) VALUES (15, null, 'gift.payment', 'gift.address.country', 'address.country', TRUE);
INSERT INTO FIELD_CONDITION (DEPENDENT_FIELD_DEFINITION_ID, DEPENDENT_SECONDARY_FIELD_DEFINITION_ID, DEPENDENT_VALUE, FIELD_REQUIRED_ID, VALIDATION_ID) VALUES ('gift.address.addressLine1', 'address.userCreated', 'true', 11, null);
INSERT INTO FIELD_CONDITION (DEPENDENT_FIELD_DEFINITION_ID, DEPENDENT_SECONDARY_FIELD_DEFINITION_ID, DEPENDENT_VALUE, FIELD_REQUIRED_ID, VALIDATION_ID) VALUES ('gift.address.city', 'address.userCreated', 'true', 12, null);
INSERT INTO FIELD_CONDITION (DEPENDENT_FIELD_DEFINITION_ID, DEPENDENT_SECONDARY_FIELD_DEFINITION_ID, DEPENDENT_VALUE, FIELD_REQUIRED_ID, VALIDATION_ID) VALUES ('gift.address.stateProvince', 'address.userCreated', 'true', 13, null);
INSERT INTO FIELD_CONDITION (DEPENDENT_FIELD_DEFINITION_ID, DEPENDENT_SECONDARY_FIELD_DEFINITION_ID, DEPENDENT_VALUE, FIELD_REQUIRED_ID, VALIDATION_ID) VALUES ('gift.address.postalCode', 'address.userCreated', 'true', 14, null);
INSERT INTO FIELD_CONDITION (DEPENDENT_FIELD_DEFINITION_ID, DEPENDENT_SECONDARY_FIELD_DEFINITION_ID, DEPENDENT_VALUE, FIELD_REQUIRED_ID, VALIDATION_ID) VALUES ('gift.address.country', 'address.userCreated', 'true', 15, null);

INSERT INTO FIELD_REQUIRED (FIELD_REQUIRED_ID, SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, REQUIRED) VALUES (16, null, 'gift.payment', 'gift.phone.number', 'phone.number', TRUE);
INSERT INTO FIELD_CONDITION (DEPENDENT_FIELD_DEFINITION_ID, DEPENDENT_SECONDARY_FIELD_DEFINITION_ID, DEPENDENT_VALUE, FIELD_REQUIRED_ID, VALIDATION_ID) VALUES ('gift.phone.number', 'phone.userCreated', 'true', 16, null);

INSERT INTO FIELD_REQUIRED (FIELD_REQUIRED_ID, SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, REQUIRED) VALUES (17, null, 'gift.acknowledgment', 'gift.email.emailAddress', 'email.emailAddress', TRUE);
INSERT INTO FIELD_CONDITION (DEPENDENT_FIELD_DEFINITION_ID, DEPENDENT_SECONDARY_FIELD_DEFINITION_ID, DEPENDENT_VALUE, FIELD_REQUIRED_ID, VALIDATION_ID) VALUES ('gift.email.emailAddress', 'email.userCreated', 'true', 17, null);

INSERT INTO FIELD_REQUIRED (FIELD_REQUIRED_ID, SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, REQUIRED) VALUES (18, null, 'gift.payment', 'gift.paymentType', TRUE);

-- Gift check info required
INSERT INTO FIELD_REQUIRED (FIELD_REQUIRED_ID, SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, REQUIRED) VALUES (19, null, 'gift.check', 'gift.checkNumber', null, TRUE);
INSERT INTO FIELD_CONDITION (DEPENDENT_FIELD_DEFINITION_ID, DEPENDENT_VALUE, FIELD_REQUIRED_ID, VALIDATION_ID) VALUES ('gift.paymentType', 'Check', 19, null);

INSERT INTO FIELD_REQUIRED (FIELD_REQUIRED_ID, SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, REQUIRED) VALUES (20, null, 'gift.donation', 'gift.donationDate', null, TRUE);
INSERT INTO FIELD_REQUIRED (FIELD_REQUIRED_ID, SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, REQUIRED) VALUES (21, null, 'gift.donation', 'gift.currencyCode', null, TRUE);


INSERT INTO FIELD_REQUIRED (FIELD_REQUIRED_ID, SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, REQUIRED) VALUES (151, null, 'person.contactInfo', 'person.customFieldMap[communicationPreferences]', null, TRUE);
-- make phone required based on primary phone selection
INSERT INTO FIELD_REQUIRED (FIELD_REQUIRED_ID, SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, REQUIRED) VALUES (40, null, 'person.contactInfo', 'person.phoneMap[home]', null, TRUE);
INSERT INTO FIELD_REQUIRED (FIELD_REQUIRED_ID, SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, REQUIRED) VALUES (41, null, 'person.contactInfo', 'person.phoneMap[work]', null, TRUE);
INSERT INTO FIELD_REQUIRED (FIELD_REQUIRED_ID, SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, REQUIRED) VALUES (42, null, 'person.contactInfo', 'person.phoneMap[mobile]', null, TRUE);
INSERT INTO FIELD_CONDITION (DEPENDENT_FIELD_DEFINITION_ID, DEPENDENT_VALUE, FIELD_REQUIRED_ID, VALIDATION_ID) VALUES ('person.preferredPhoneType', 'phoneMap[home].number', 40, null);
INSERT INTO FIELD_CONDITION (DEPENDENT_FIELD_DEFINITION_ID, DEPENDENT_VALUE, FIELD_REQUIRED_ID, VALIDATION_ID) VALUES ('person.preferredPhoneType', 'phoneMap[work].number', 41, null);
INSERT INTO FIELD_CONDITION (DEPENDENT_FIELD_DEFINITION_ID, DEPENDENT_VALUE, FIELD_REQUIRED_ID, VALIDATION_ID) VALUES ('person.preferredPhoneType', 'phoneMap[mobile].number', 42, null);
-- Make types required
INSERT INTO FIELD_REQUIRED (FIELD_REQUIRED_ID, SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, REQUIRED) VALUES (43, null, 'phone.edit', 'phone.phoneType', null, TRUE);
INSERT INTO FIELD_REQUIRED (FIELD_REQUIRED_ID, SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, REQUIRED) VALUES (44, null, 'phone.info', 'phone.phoneType', null, TRUE);
INSERT INTO FIELD_REQUIRED (FIELD_REQUIRED_ID, SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, REQUIRED) VALUES (45, null, 'address.edit', 'address.addressType', null, TRUE);
INSERT INTO FIELD_REQUIRED (FIELD_REQUIRED_ID, SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, REQUIRED) VALUES (46, null, 'address.info', 'address.addressType', null, TRUE);
INSERT INTO FIELD_REQUIRED (FIELD_REQUIRED_ID, SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, REQUIRED) VALUES (47, null, 'email.edit', 'email.emailType', null, TRUE);
INSERT INTO FIELD_REQUIRED (FIELD_REQUIRED_ID, SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, REQUIRED) VALUES (48, null, 'email.info', 'email.emailType', null, TRUE);

-- make address line 1 required if address line 2 has a value
INSERT INTO FIELD_REQUIRED (FIELD_REQUIRED_ID, SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, REQUIRED) VALUES (50, null, 'person.contactInfo', 'person.addressMap[home]', 'address.addressLine1', TRUE);
INSERT INTO FIELD_CONDITION (DEPENDENT_FIELD_DEFINITION_ID, DEPENDENT_SECONDARY_FIELD_DEFINITION_ID, DEPENDENT_VALUE, FIELD_REQUIRED_ID, VALIDATION_ID) VALUES ('person.addressMap[home]', 'address.addressLine2', '!null', 50, null);

-- add messages for ach
INSERT INTO MESSAGE_RESOURCE (LANGUAGE_ABBREVIATION, MESSAGE_KEY, MESSAGE_RESOURCE_TYPE, MESSAGE_VALUE) values ('en_US', 'invalidAchAccountNumber', 'FIELD_VALIDATION', 'Invalid ACH account number');
INSERT INTO MESSAGE_RESOURCE (LANGUAGE_ABBREVIATION, MESSAGE_KEY, MESSAGE_RESOURCE_TYPE, MESSAGE_VALUE) values ('en_US', 'invalidAchRoutingNumber', 'FIELD_VALIDATION', 'Invalid ACH routing number');

-- add messages for credit card
INSERT INTO MESSAGE_RESOURCE (LANGUAGE_ABBREVIATION, MESSAGE_KEY, MESSAGE_RESOURCE_TYPE, MESSAGE_VALUE) values ('en_US', 'invalidCreditCardType', 'FIELD_VALIDATION', 'Invalid credit card type');
INSERT INTO MESSAGE_RESOURCE (LANGUAGE_ABBREVIATION, MESSAGE_KEY, MESSAGE_RESOURCE_TYPE, MESSAGE_VALUE) values ('en_US', 'invalidCreditCardNumber', 'FIELD_VALIDATION', 'Invalid credit card number');
INSERT INTO MESSAGE_RESOURCE (LANGUAGE_ABBREVIATION, MESSAGE_KEY, MESSAGE_RESOURCE_TYPE, MESSAGE_VALUE) values ('en_US', 'invalidCreditCardExpiration', 'FIELD_VALIDATION', 'Credit card expiration must be after today');


-- add validation to require spouse name if married
INSERT INTO FIELD_REQUIRED (FIELD_REQUIRED_ID, SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, REQUIRED) VALUES (51, null, 'person.contactInfo', 'person.customFieldMap[individual.spouse]', TRUE);
INSERT INTO FIELD_CONDITION (DEPENDENT_FIELD_DEFINITION_ID, DEPENDENT_VALUE, FIELD_REQUIRED_ID, VALIDATION_ID) VALUES ('person.maritalStatus', 'Married', 51, null);



-- Payment Methods
-- Validate paymentType for payment methods
INSERT INTO FIELD_REQUIRED (FIELD_REQUIRED_ID, SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, REQUIRED) VALUES (70, null, 'paymentSource.info', 'paymentSource.type', TRUE);

INSERT INTO FIELD_REQUIRED (FIELD_REQUIRED_ID, SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, REQUIRED) VALUES (71, null, 'paymentSource.creditCard', 'paymentSource.creditCardHolderName', TRUE);
INSERT INTO FIELD_REQUIRED (FIELD_REQUIRED_ID, SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, REQUIRED) VALUES (72, null, 'paymentSource.creditCard', 'paymentSource.creditCardType', TRUE);
INSERT INTO FIELD_REQUIRED (FIELD_REQUIRED_ID, SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, REQUIRED) VALUES (73, null, 'paymentSource.creditCard', 'paymentSource.creditCardExpiration', TRUE);
INSERT INTO FIELD_REQUIRED (FIELD_REQUIRED_ID, SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, REQUIRED) VALUES (74, null, 'paymentSource.creditCard', 'paymentSource.creditCardNumber', TRUE);
INSERT INTO FIELD_CONDITION (DEPENDENT_FIELD_DEFINITION_ID, DEPENDENT_VALUE, FIELD_REQUIRED_ID, VALIDATION_ID) VALUES ('paymentSource.type', 'Credit Card', 71, null);
INSERT INTO FIELD_CONDITION (DEPENDENT_FIELD_DEFINITION_ID, DEPENDENT_VALUE, FIELD_REQUIRED_ID, VALIDATION_ID) VALUES ('paymentSource.type', 'Credit Card', 72, null);
INSERT INTO FIELD_CONDITION (DEPENDENT_FIELD_DEFINITION_ID, DEPENDENT_VALUE, FIELD_REQUIRED_ID, VALIDATION_ID) VALUES ('paymentSource.type', 'Credit Card', 73, null);
INSERT INTO FIELD_CONDITION (DEPENDENT_FIELD_DEFINITION_ID, DEPENDENT_VALUE, FIELD_REQUIRED_ID, VALIDATION_ID) VALUES ('paymentSource.type', 'Credit Card', 74, null);

INSERT INTO FIELD_REQUIRED (FIELD_REQUIRED_ID, SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, REQUIRED) VALUES (75, null, 'paymentSource.ach', 'paymentSource.achHolderName', TRUE);
INSERT INTO FIELD_REQUIRED (FIELD_REQUIRED_ID, SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, REQUIRED) VALUES (76, null, 'paymentSource.ach', 'paymentSource.achRoutingNumber', TRUE);
INSERT INTO FIELD_REQUIRED (FIELD_REQUIRED_ID, SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, REQUIRED) VALUES (77, null, 'paymentSource.ach', 'paymentSource.achAccountNumber', TRUE);
INSERT INTO FIELD_CONDITION (DEPENDENT_FIELD_DEFINITION_ID, DEPENDENT_VALUE, FIELD_REQUIRED_ID, VALIDATION_ID) VALUES ('paymentSource.type', 'ACH', 75, null);
INSERT INTO FIELD_CONDITION (DEPENDENT_FIELD_DEFINITION_ID, DEPENDENT_VALUE, FIELD_REQUIRED_ID, VALIDATION_ID) VALUES ('paymentSource.type', 'ACH', 76, null);
INSERT INTO FIELD_CONDITION (DEPENDENT_FIELD_DEFINITION_ID, DEPENDENT_VALUE, FIELD_REQUIRED_ID, VALIDATION_ID) VALUES ('paymentSource.type', 'ACH', 77, null);

INSERT INTO FIELD_REQUIRED (FIELD_REQUIRED_ID, SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, REQUIRED) VALUES (78, null, 'paymentSource.info', 'paymentSource.address.addressLine1', 'address.addressLine1', TRUE);
INSERT INTO FIELD_REQUIRED (FIELD_REQUIRED_ID, SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, REQUIRED) VALUES (79, null, 'paymentSource.info', 'paymentSource.address.city', 'address.city', TRUE);
INSERT INTO FIELD_REQUIRED (FIELD_REQUIRED_ID, SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, REQUIRED) VALUES (80, null, 'paymentSource.info', 'paymentSource.address.stateProvince', 'address.stateProvince', TRUE);
INSERT INTO FIELD_REQUIRED (FIELD_REQUIRED_ID, SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, REQUIRED) VALUES (81, null, 'paymentSource.info', 'paymentSource.address.postalCode', 'address.postalCode', TRUE);
INSERT INTO FIELD_REQUIRED (FIELD_REQUIRED_ID, SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, REQUIRED) VALUES (82, null, 'paymentSource.info', 'paymentSource.address.country', 'address.country', TRUE);
INSERT INTO FIELD_CONDITION (DEPENDENT_FIELD_DEFINITION_ID, DEPENDENT_SECONDARY_FIELD_DEFINITION_ID, DEPENDENT_VALUE, FIELD_REQUIRED_ID, VALIDATION_ID) VALUES ('paymentSource.address.addressLine1', 'address.userCreated', 'true', 78, null);
INSERT INTO FIELD_CONDITION (DEPENDENT_FIELD_DEFINITION_ID, DEPENDENT_SECONDARY_FIELD_DEFINITION_ID, DEPENDENT_VALUE, FIELD_REQUIRED_ID, VALIDATION_ID) VALUES ('paymentSource.address.city', 'address.userCreated', 'true', 79, null);
INSERT INTO FIELD_CONDITION (DEPENDENT_FIELD_DEFINITION_ID, DEPENDENT_SECONDARY_FIELD_DEFINITION_ID, DEPENDENT_VALUE, FIELD_REQUIRED_ID, VALIDATION_ID) VALUES ('paymentSource.address.stateProvince', 'address.userCreated', 'true', 80, null);
INSERT INTO FIELD_CONDITION (DEPENDENT_FIELD_DEFINITION_ID, DEPENDENT_SECONDARY_FIELD_DEFINITION_ID, DEPENDENT_VALUE, FIELD_REQUIRED_ID, VALIDATION_ID) VALUES ('paymentSource.address.postalCode', 'address.userCreated', 'true', 81, null);
INSERT INTO FIELD_CONDITION (DEPENDENT_FIELD_DEFINITION_ID, DEPENDENT_SECONDARY_FIELD_DEFINITION_ID, DEPENDENT_VALUE, FIELD_REQUIRED_ID, VALIDATION_ID) VALUES ('paymentSource.address.country', 'address.userCreated', 'true', 82, null);

INSERT INTO FIELD_REQUIRED (FIELD_REQUIRED_ID, SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, REQUIRED) VALUES (83, null, 'paymentSource.info', 'paymentSource.phone.number', 'phone.number', TRUE);
INSERT INTO FIELD_CONDITION (DEPENDENT_FIELD_DEFINITION_ID, DEPENDENT_SECONDARY_FIELD_DEFINITION_ID, DEPENDENT_VALUE, FIELD_REQUIRED_ID, VALIDATION_ID) VALUES ('paymentSource.phone.number', 'phone.userCreated', 'true', 83, null);


-- Edit Payment Method
INSERT INTO FIELD_REQUIRED (FIELD_REQUIRED_ID, SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, REQUIRED) VALUES (84, null, 'paymentSource.infoEdit', 'paymentSource.address.addressLine1', 'address.addressLine1', TRUE);
INSERT INTO FIELD_REQUIRED (FIELD_REQUIRED_ID, SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, REQUIRED) VALUES (85, null, 'paymentSource.infoEdit', 'paymentSource.address.city', 'address.city', TRUE);
INSERT INTO FIELD_REQUIRED (FIELD_REQUIRED_ID, SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, REQUIRED) VALUES (86, null, 'paymentSource.infoEdit', 'paymentSource.address.stateProvince', 'address.stateProvince', TRUE);
INSERT INTO FIELD_REQUIRED (FIELD_REQUIRED_ID, SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, REQUIRED) VALUES (87, null, 'paymentSource.infoEdit', 'paymentSource.address.postalCode', 'address.postalCode', TRUE);
INSERT INTO FIELD_REQUIRED (FIELD_REQUIRED_ID, SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, REQUIRED) VALUES (88, null, 'paymentSource.infoEdit', 'paymentSource.address.country', 'address.country', TRUE);
INSERT INTO FIELD_CONDITION (DEPENDENT_FIELD_DEFINITION_ID, DEPENDENT_SECONDARY_FIELD_DEFINITION_ID, DEPENDENT_VALUE, FIELD_REQUIRED_ID, VALIDATION_ID) VALUES ('paymentSource.address.addressLine1', 'address.userCreated', 'true', 84, null);
INSERT INTO FIELD_CONDITION (DEPENDENT_FIELD_DEFINITION_ID, DEPENDENT_SECONDARY_FIELD_DEFINITION_ID, DEPENDENT_VALUE, FIELD_REQUIRED_ID, VALIDATION_ID) VALUES ('paymentSource.address.city', 'address.userCreated', 'true', 85, null);
INSERT INTO FIELD_CONDITION (DEPENDENT_FIELD_DEFINITION_ID, DEPENDENT_SECONDARY_FIELD_DEFINITION_ID, DEPENDENT_VALUE, FIELD_REQUIRED_ID, VALIDATION_ID) VALUES ('paymentSource.address.stateProvince', 'address.userCreated', 'true', 86, null);
INSERT INTO FIELD_CONDITION (DEPENDENT_FIELD_DEFINITION_ID, DEPENDENT_SECONDARY_FIELD_DEFINITION_ID, DEPENDENT_VALUE, FIELD_REQUIRED_ID, VALIDATION_ID) VALUES ('paymentSource.address.postalCode', 'address.userCreated', 'true', 87, null);
INSERT INTO FIELD_CONDITION (DEPENDENT_FIELD_DEFINITION_ID, DEPENDENT_SECONDARY_FIELD_DEFINITION_ID, DEPENDENT_VALUE, FIELD_REQUIRED_ID, VALIDATION_ID) VALUES ('paymentSource.address.country', 'address.userCreated', 'true', 88, null);

INSERT INTO FIELD_REQUIRED (FIELD_REQUIRED_ID, SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, REQUIRED) VALUES (89, null, 'paymentSource.infoEdit', 'paymentSource.phone.number', 'phone.number', TRUE);
INSERT INTO FIELD_CONDITION (DEPENDENT_FIELD_DEFINITION_ID, DEPENDENT_SECONDARY_FIELD_DEFINITION_ID, DEPENDENT_VALUE, FIELD_REQUIRED_ID, VALIDATION_ID) VALUES ('paymentSource.phone.number', 'phone.userCreated', 'true', 89, null);



-- Commitment amountPerGift required
INSERT INTO FIELD_REQUIRED (FIELD_REQUIRED_ID, SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, REQUIRED) VALUES (125, null, 'recurringGift.info', 'commitment.amountPerGift', null, TRUE);

-- Commitment payment source, type, frequency, start date required 
INSERT INTO FIELD_REQUIRED (FIELD_REQUIRED_ID, SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, REQUIRED) VALUES (126, null, 'recurringGift.payment', 'commitment.selectedPaymentSource', null, TRUE);
INSERT INTO FIELD_REQUIRED (FIELD_REQUIRED_ID, SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, REQUIRED) VALUES (140, null, 'recurringGift.payment', 'commitment.paymentType', TRUE);
INSERT INTO FIELD_REQUIRED (FIELD_REQUIRED_ID, SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, REQUIRED) VALUES (141, null, 'recurringGift.info', 'commitment.frequency', TRUE);
INSERT INTO FIELD_REQUIRED (FIELD_REQUIRED_ID, SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, REQUIRED) VALUES (142, null, 'recurringGift.info', 'commitment.startDate', TRUE);

-- Commitment credit card info required
INSERT INTO FIELD_REQUIRED (FIELD_REQUIRED_ID, SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, REQUIRED) VALUES (127, null, 'commitment.creditCard', 'commitment.paymentSource.creditCardHolderName', 'paymentSource.creditCardHolderName', TRUE);
INSERT INTO FIELD_REQUIRED (FIELD_REQUIRED_ID, SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, REQUIRED) VALUES (128, null, 'commitment.creditCard', 'commitment.paymentSource.creditCardType', 'paymentSource.creditCardType', TRUE);
INSERT INTO FIELD_REQUIRED (FIELD_REQUIRED_ID, SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, REQUIRED) VALUES (129, null, 'commitment.creditCard', 'commitment.paymentSource.creditCardNumber', 'paymentSource.creditCardNumber', TRUE);
INSERT INTO FIELD_REQUIRED (FIELD_REQUIRED_ID, SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, REQUIRED) VALUES (130, null, 'commitment.creditCard', 'commitment.paymentSource.creditCardExpiration', 'paymentSource.creditCardExpiration', TRUE);
INSERT INTO FIELD_CONDITION (DEPENDENT_FIELD_DEFINITION_ID, DEPENDENT_VALUE, FIELD_REQUIRED_ID, VALIDATION_ID) VALUES ('commitment.paymentType', 'Credit Card', 127, null);
INSERT INTO FIELD_CONDITION (DEPENDENT_FIELD_DEFINITION_ID, DEPENDENT_VALUE, FIELD_REQUIRED_ID, VALIDATION_ID) VALUES ('commitment.paymentType', 'Credit Card', 128, null);
INSERT INTO FIELD_CONDITION (DEPENDENT_FIELD_DEFINITION_ID, DEPENDENT_VALUE, FIELD_REQUIRED_ID, VALIDATION_ID) VALUES ('commitment.paymentType', 'Credit Card', 129, null);
INSERT INTO FIELD_CONDITION (DEPENDENT_FIELD_DEFINITION_ID, DEPENDENT_VALUE, FIELD_REQUIRED_ID, VALIDATION_ID) VALUES ('commitment.paymentType', 'Credit Card', 130, null);

-- Commitment ach info required
INSERT INTO FIELD_REQUIRED (FIELD_REQUIRED_ID, SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, REQUIRED) VALUES (131, null, 'commitment.ach', 'commitment.paymentSource.achHolderName', 'paymentSource.achHolderName', TRUE);
INSERT INTO FIELD_REQUIRED (FIELD_REQUIRED_ID, SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, REQUIRED) VALUES (132, null, 'commitment.ach', 'commitment.paymentSource.achRoutingNumber', 'paymentSource.achRoutingNumber', TRUE);
INSERT INTO FIELD_REQUIRED (FIELD_REQUIRED_ID, SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, REQUIRED) VALUES (133, null, 'commitment.ach', 'commitment.paymentSource.achAccountNumber', 'paymentSource.achAccountNumber', TRUE);
INSERT INTO FIELD_CONDITION (DEPENDENT_FIELD_DEFINITION_ID, DEPENDENT_VALUE, FIELD_REQUIRED_ID, VALIDATION_ID) VALUES ('commitment.paymentType', 'ACH', 131, null);
INSERT INTO FIELD_CONDITION (DEPENDENT_FIELD_DEFINITION_ID, DEPENDENT_VALUE, FIELD_REQUIRED_ID, VALIDATION_ID) VALUES ('commitment.paymentType', 'ACH', 132, null);
INSERT INTO FIELD_CONDITION (DEPENDENT_FIELD_DEFINITION_ID, DEPENDENT_VALUE, FIELD_REQUIRED_ID, VALIDATION_ID) VALUES ('commitment.paymentType', 'ACH', 133, null);

INSERT INTO FIELD_REQUIRED (FIELD_REQUIRED_ID, SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, REQUIRED) VALUES (134, null, 'recurringGift.payment', 'commitment.address.addressLine1', 'address.addressLine1', TRUE);
INSERT INTO FIELD_REQUIRED (FIELD_REQUIRED_ID, SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, REQUIRED) VALUES (135, null, 'recurringGift.payment', 'commitment.address.city', 'address.city', TRUE);
INSERT INTO FIELD_REQUIRED (FIELD_REQUIRED_ID, SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, REQUIRED) VALUES (136, null, 'recurringGift.payment', 'commitment.address.stateProvince', 'address.stateProvince', TRUE);
INSERT INTO FIELD_REQUIRED (FIELD_REQUIRED_ID, SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, REQUIRED) VALUES (137, null, 'recurringGift.payment', 'commitment.address.postalCode', 'address.postalCode', TRUE);
INSERT INTO FIELD_REQUIRED (FIELD_REQUIRED_ID, SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, REQUIRED) VALUES (138, null, 'recurringGift.payment', 'commitment.address.country', 'address.country', TRUE);
INSERT INTO FIELD_CONDITION (DEPENDENT_FIELD_DEFINITION_ID, DEPENDENT_SECONDARY_FIELD_DEFINITION_ID, DEPENDENT_VALUE, FIELD_REQUIRED_ID, VALIDATION_ID) VALUES ('commitment.address.addressLine1', 'address.userCreated', 'true', 134, null);
INSERT INTO FIELD_CONDITION (DEPENDENT_FIELD_DEFINITION_ID, DEPENDENT_SECONDARY_FIELD_DEFINITION_ID, DEPENDENT_VALUE, FIELD_REQUIRED_ID, VALIDATION_ID) VALUES ('commitment.address.city', 'address.userCreated', 'true', 135, null);
INSERT INTO FIELD_CONDITION (DEPENDENT_FIELD_DEFINITION_ID, DEPENDENT_SECONDARY_FIELD_DEFINITION_ID, DEPENDENT_VALUE, FIELD_REQUIRED_ID, VALIDATION_ID) VALUES ('commitment.address.stateProvince', 'address.userCreated', 'true', 136, null);
INSERT INTO FIELD_CONDITION (DEPENDENT_FIELD_DEFINITION_ID, DEPENDENT_SECONDARY_FIELD_DEFINITION_ID, DEPENDENT_VALUE, FIELD_REQUIRED_ID, VALIDATION_ID) VALUES ('commitment.address.postalCode', 'address.userCreated', 'true', 137, null);
INSERT INTO FIELD_CONDITION (DEPENDENT_FIELD_DEFINITION_ID, DEPENDENT_SECONDARY_FIELD_DEFINITION_ID, DEPENDENT_VALUE, FIELD_REQUIRED_ID, VALIDATION_ID) VALUES ('commitment.address.country', 'address.userCreated', 'true', 138, null);

INSERT INTO FIELD_REQUIRED (FIELD_REQUIRED_ID, SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, REQUIRED) VALUES (139, null, 'recurringGift.payment', 'commitment.phone.number', 'phone.number', TRUE);
INSERT INTO FIELD_CONDITION (DEPENDENT_FIELD_DEFINITION_ID, DEPENDENT_SECONDARY_FIELD_DEFINITION_ID, DEPENDENT_VALUE, FIELD_REQUIRED_ID, VALIDATION_ID) VALUES ('commitment.phone.number', 'phone.userCreated', 'true', 139, null);

-- Commitment recurring required
INSERT INTO FIELD_REQUIRED (FIELD_REQUIRED_ID, SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, REQUIRED) VALUES (143, null, 'pledge.info', 'commitment.recurring', TRUE);

INSERT INTO FIELD_REQUIRED (FIELD_REQUIRED_ID, SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, REQUIRED) VALUES (144, null, 'pledge.info', 'commitment.amountPerGift', TRUE);
INSERT INTO FIELD_CONDITION (DEPENDENT_FIELD_DEFINITION_ID, DEPENDENT_VALUE, FIELD_REQUIRED_ID, VALIDATION_ID) VALUES ('commitment.recurring', 'true', 144, null);

INSERT INTO FIELD_REQUIRED (FIELD_REQUIRED_ID, SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, REQUIRED) VALUES (145, null, 'pledge.info', 'commitment.frequency', TRUE);
INSERT INTO FIELD_CONDITION (DEPENDENT_FIELD_DEFINITION_ID, DEPENDENT_VALUE, FIELD_REQUIRED_ID, VALIDATION_ID) VALUES ('commitment.recurring', 'true', 145, null);

INSERT INTO FIELD_REQUIRED (FIELD_REQUIRED_ID, SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, REQUIRED) VALUES (146, null, 'pledge.info', 'commitment.startDate', TRUE);
INSERT INTO FIELD_CONDITION (DEPENDENT_FIELD_DEFINITION_ID, DEPENDENT_VALUE, FIELD_REQUIRED_ID, VALIDATION_ID) VALUES ('commitment.recurring', 'true', 146, null);

INSERT INTO FIELD_REQUIRED (FIELD_REQUIRED_ID, SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, REQUIRED) VALUES (147, null, 'pledge.info', 'commitment.amountTotal', TRUE);
INSERT INTO FIELD_CONDITION (DEPENDENT_FIELD_DEFINITION_ID, DEPENDENT_VALUE, FIELD_REQUIRED_ID, VALIDATION_ID) VALUES ('commitment.recurring', 'false', 147, null);
INSERT INTO FIELD_REQUIRED (FIELD_REQUIRED_ID, SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, REQUIRED) VALUES (152, null, 'pledge.info', 'commitment.pledgeStatus', TRUE);


INSERT INTO FIELD_REQUIRED (FIELD_REQUIRED_ID, SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, REQUIRED) VALUES (150, null, 'communicationHistory', 'communicationHistory.customFieldMap[journalType]', TRUE);

INSERT INTO FIELD_REQUIRED (FIELD_REQUIRED_ID, SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, REQUIRED) VALUES (153, null, 'commitment.acknowledgment', 'commitment.email.emailAddress', 'email.emailAddress', TRUE);
INSERT INTO FIELD_CONDITION (DEPENDENT_FIELD_DEFINITION_ID, DEPENDENT_SECONDARY_FIELD_DEFINITION_ID, DEPENDENT_VALUE, FIELD_REQUIRED_ID, VALIDATION_ID) VALUES ('commitment.email.emailAddress', 'email.userCreated', 'true', 153, null);

INSERT INTO FIELD_REQUIRED (FIELD_REQUIRED_ID, SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, REQUIRED) VALUES (154, null, 'pledge.info', 'commitment.currencyCode', null, TRUE);
INSERT INTO FIELD_REQUIRED (FIELD_REQUIRED_ID, SITE_NAME, SECTION_NAME, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, REQUIRED) VALUES (155, null, 'recurringGift.info', 'commitment.currencyCode', null, TRUE);


-- Distribution Lines
INSERT INTO MESSAGE_RESOURCE (LANGUAGE_ABBREVIATION, MESSAGE_KEY, MESSAGE_RESOURCE_TYPE, MESSAGE_VALUE) values ('en_US', 'distributionLineAmounts', 'FIELD_VALIDATION', 'Individual distribution line amounts must add up to the total gift amount');

-- add messages for Payment Source Profile
INSERT INTO MESSAGE_RESOURCE (LANGUAGE_ABBREVIATION, MESSAGE_KEY, MESSAGE_RESOURCE_TYPE, MESSAGE_VALUE) values ('en_US', 'blankPaymentProfile', 'FIELD_VALIDATION', 'The Payment Profile must be composed of alphanumeric characters');
INSERT INTO MESSAGE_RESOURCE (LANGUAGE_ABBREVIATION, MESSAGE_KEY, MESSAGE_RESOURCE_TYPE, MESSAGE_VALUE) values ('en_US', 'paymentProfileAlreadyExists', 'FIELD_VALIDATION', 'The Payment Profile entered already exists and is not unique');
