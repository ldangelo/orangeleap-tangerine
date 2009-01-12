INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('paymentSource.id', 'paymentSource', 'id', 'Id', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('paymentSource.profile', 'paymentSource', 'profile', 'Payment Profile', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('paymentSource.type', 'paymentSource', 'type', 'Payment Method', 'PICKLIST');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('paymentSource.selectedAddress', 'paymentSource', 'selectedAddress', 'Default Address', 'ADDRESS_PICKLIST');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('paymentSource.address.addressLine1', 'paymentSource', 'address', 'Address Line 1', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('paymentSource.address.addressLine2', 'paymentSource', 'address', 'Address Line 2', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('paymentSource.address.addressLine3', 'paymentSource', 'address', 'Address Line 3', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('paymentSource.address.city', 'paymentSource', 'address', 'City', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('paymentSource.address.stateProvince', 'paymentSource', 'address', 'State', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('paymentSource.address.postalCode', 'paymentSource', 'address', 'Zip Code', 'NUMBER');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('paymentSource.address.country', 'paymentSource', 'address', 'Country', 'PICKLIST');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('paymentSource.selectedPhone', 'paymentSource', 'selectedPhone', 'Default Phone', 'PHONE_PICKLIST');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('paymentSource.phone.number', 'paymentSource', 'phone', 'Phone Number', 'TEXT');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('paymentSource.creditCardHolderName', 'paymentSource', 'creditCardHolderName', 'Cardholder Name', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('paymentSource.creditCardType', 'paymentSource', 'creditCardType', 'Credit Card', 'PICKLIST');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('paymentSource.creditCardNumber', 'paymentSource', 'creditCardNumber', 'Number', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('paymentSource.creditCardExpiration', 'paymentSource', 'creditCardExpiration', 'Expiration', 'CC_EXPIRATION');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('paymentSource.creditCardSecurityCode', 'paymentSource', 'creditCardSecurityCode', 'Security Code', 'TEXT');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('paymentSource.achHolderName', 'paymentSource', 'achHolderName', 'ACH Holder Name', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('paymentSource.achRoutingNumber', 'paymentSource', 'achRoutingNumber', 'Routing Number', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('paymentSource.achAccountNumber', 'paymentSource', 'achAccountNumber', 'Account Number', 'TEXT');




INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('paymentSource.profileReadOnly', 'paymentSource', 'profile', 'Payment Profile', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('paymentSource.typeReadOnly', 'paymentSource', 'type', 'Payment Method', 'PICKLIST_DISPLAY');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('paymentSource.address.addressLine1ReadOnly', 'paymentSource', 'address', 'Address Line 1', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('paymentSource.address.addressLine2ReadOnly', 'paymentSource', 'address', 'Address Line 2', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('paymentSource.address.addressLine3ReadOnly', 'paymentSource', 'address', 'Address Line 3', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('paymentSource.address.cityReadOnly', 'paymentSource', 'address', 'City', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('paymentSource.address.stateProvinceReadOnly', 'paymentSource', 'address', 'State', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('paymentSource.address.postalCodeReadOnly', 'paymentSource', 'address', 'Zip Code', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('paymentSource.address.countryReadOnly', 'paymentSource', 'address', 'Country', 'READ_ONLY_TEXT');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('paymentSource.phone.numberReadOnly', 'paymentSource', 'phone', 'Default Phone', 'READ_ONLY_TEXT');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('paymentSource.creditCardHolderNameReadOnly', 'paymentSource', 'creditCardHolderName', 'Cardholder Name', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('paymentSource.creditCardTypeReadOnly', 'paymentSource', 'creditCardType', 'Credit Card', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('paymentSource.creditCardNumberReadOnly', 'paymentSource', 'creditCardNumber', 'Number', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('paymentSource.creditCardExpirationDisplay', 'paymentSource', 'creditCardExpiration', 'Expiration', 'CC_EXPIRATION_DISPLAY');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('paymentSource.achHolderNameReadOnly', 'paymentSource', 'achHolderName', 'Account Holder Name', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('paymentSource.achRoutingNumberReadOnly', 'paymentSource', 'achRoutingNumber', 'Routing Number', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('paymentSource.achAccountNumberReadOnly', 'paymentSource', 'achAccountNumber', 'Account Number', 'READ_ONLY_TEXT');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('paymentSource.creditCardNumberDisplay', 'paymentSource', 'creditCardNumberDisplay', 'Number', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('paymentSource.achAccountNumberDisplay', 'paymentSource', 'achAccountNumberDisplay', 'Account Number', 'TEXT');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('paymentSource.address.shortAddressReadOnly', 'paymentSource', 'address', 'Default Address', 'READ_ONLY_TEXT');



INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('selectedPaymentSource.creditCardExpiration', 'paymentSource', 'creditCardExpiration', 'Expiration', 'CC_EXPIRATION');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('selectedPaymentSource.creditCardSecurityCode', 'paymentSource', 'creditCardSecurityCode', 'Security Code', 'TEXT');
