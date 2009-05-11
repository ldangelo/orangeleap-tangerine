INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('paymentSource.id', 'paymentSource', 'id', 'Id', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('paymentSource.profile', 'paymentSource', 'profile', 'Payment Profile', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('paymentSource.paymentType', 'paymentSource', 'paymentType', 'Payment Method', 'PICKLIST');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('paymentSource.selectedAddress', 'paymentSource', 'selectedAddress', 'Default Billing Address', 'ADDRESS_PICKLIST');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('paymentSource.address.customFieldMap[addressType]', 'paymentSource', 'address', 'Address Type', 'MULTI_PICKLIST');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('paymentSource.address.addressLine1', 'paymentSource', 'address', 'Address Line 1', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('paymentSource.address.addressLine2', 'paymentSource', 'address', 'Address Line 2', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('paymentSource.address.addressLine3', 'paymentSource', 'address', 'Address Line 3', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('paymentSource.address.city', 'paymentSource', 'address', 'City', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('paymentSource.address.stateProvince', 'paymentSource', 'address', 'State/Province', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('paymentSource.address.postalCode', 'paymentSource', 'address', 'Zip/Postal Code', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('paymentSource.address.country', 'paymentSource', 'address', 'Country', 'PICKLIST');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('paymentSource.selectedPhone', 'paymentSource', 'selectedPhone', 'Default Billing Phone', 'PHONE_PICKLIST');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('paymentSource.phone.customFieldMap[phoneType]', 'paymentSource', 'phone', 'Phone Type', 'MULTI_PICKLIST');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('paymentSource.phone.number', 'paymentSource', 'phone', 'Phone Number', 'TEXT');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('paymentSource.creditCardHolderName', 'paymentSource', 'creditCardHolderName', 'Cardholder Name', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('paymentSource.creditCardType', 'paymentSource', 'creditCardType', 'Credit Card', 'PICKLIST');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('paymentSource.creditCardNumber', 'paymentSource', 'creditCardNumber', 'Credit Card Number', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('paymentSource.creditCardExpiration', 'paymentSource', 'creditCardExpiration', 'Expiration', 'CC_EXPIRATION');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('paymentSource.creditCardSecurityCode', 'paymentSource', 'creditCardSecurityCode', 'Security Code', 'TEXT');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('paymentSource.achHolderName', 'paymentSource', 'achHolderName', 'ACH Holder Name', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('paymentSource.achRoutingNumber', 'paymentSource', 'achRoutingNumber', 'Routing Number', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('paymentSource.achAccountNumber', 'paymentSource', 'achAccountNumber', 'Account Number', 'TEXT');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('paymentSource.inactive', 'paymentSource', 'inactive', 'Inactive', 'CHECKBOX');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('paymentSource.userCreated', 'paymentSource', 'userCreated', ' ', 'HIDDEN');



INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('paymentSource.profileReadOnly', 'paymentSource', 'profile', 'Payment Profile', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('paymentSource.paymentTypeReadOnly', 'paymentSource', 'paymentType', 'Payment Method', 'PICKLIST_DISPLAY');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('paymentSource.selectedAddress.customFieldMap[addressType]', 'paymentSource', 'selectedAddress', 'Address Type', 'MULTI_PICKLIST_DISPLAY');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('paymentSource.selectedAddress.addressLine1ReadOnly', 'paymentSource', 'selectedAddress', 'Address Line 1', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('paymentSource.selectedAddress.addressLine2ReadOnly', 'paymentSource', 'selectedAddress', 'Address Line 2', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('paymentSource.selectedAddress.addressLine3ReadOnly', 'paymentSource', 'selectedAddress', 'Address Line 3', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('paymentSource.selectedAddress.cityReadOnly', 'paymentSource', 'selectedAddress', 'City', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('paymentSource.selectedAddress.stateProvinceReadOnly', 'paymentSource', 'selectedAddress', 'State/Province', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('paymentSource.selectedAddress.postalCodeReadOnly', 'paymentSource', 'selectedAddress', 'Zip/Postal Code', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('paymentSource.selectedAddress.countryReadOnly', 'paymentSource', 'selectedAddress', 'Country', 'READ_ONLY_TEXT');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('paymentSource.selectedPhone.customFieldMap[phoneType]', 'paymentSource', 'selectedPhone', 'Phone Type', 'MULTI_PICKLIST_DISPLAY');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('paymentSource.selectedPhone.numberReadOnly', 'paymentSource', 'selectedPhone', 'Default Billing Phone', 'READ_ONLY_TEXT');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('paymentSource.creditCardHolderNameReadOnly', 'paymentSource', 'creditCardHolderName', 'Cardholder Name', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('paymentSource.creditCardTypeReadOnly', 'paymentSource', 'creditCardType', 'Credit Card', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('paymentSource.creditCardNumberReadOnly', 'paymentSource', 'creditCardNumberDisplay', 'Credit Card Number', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('paymentSource.creditCardExpirationDisplay', 'paymentSource', 'creditCardExpiration', 'Expiration', 'CC_EXPIRATION_DISPLAY');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('paymentSource.achHolderNameReadOnly', 'paymentSource', 'achHolderName', 'ACH Holder Name', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('paymentSource.achRoutingNumberReadOnly', 'paymentSource', 'achRoutingNumberDisplay', 'Routing Number', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('paymentSource.achAccountNumberReadOnly', 'paymentSource', 'achAccountNumberDisplay', 'Account Number', 'READ_ONLY_TEXT');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('paymentSource.creditCardNumberDisplay', 'paymentSource', 'creditCardNumberDisplay', 'Credit Card Number', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('paymentSource.achAccountNumberDisplay', 'paymentSource', 'achAccountNumberDisplay', 'Account Number', 'TEXT');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('paymentSource.selectedAddress.shortAddressReadOnly', 'paymentSource', 'selectedAddress', 'Default Billing Address', 'READ_ONLY_TEXT');


INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('selectedPaymentSource.creditCardHolderNameReadOnly', 'selectedPaymentSource', 'creditCardHolderName', 'Cardholder Name', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('selectedPaymentSource.creditCardTypeReadOnly', 'selectedPaymentSource', 'creditCardType', 'Credit Card', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('selectedPaymentSource.creditCardNumberReadOnly', 'selectedPaymentSource', 'creditCardNumberDisplay', 'Credit Card Number', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('selectedPaymentSource.creditCardExpirationDisplay', 'selectedPaymentSource', 'creditCardExpiration', 'Expiration', 'CC_EXPIRATION_DISPLAY');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('selectedPaymentSource.achHolderNameReadOnly', 'selectedPaymentSource', 'achHolderName', 'ACH Holder Name', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('selectedPaymentSource.achRoutingNumberReadOnly', 'selectedPaymentSource', 'achRoutingNumberDisplay', 'Routing Number', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('selectedPaymentSource.achAccountNumberReadOnly', 'selectedPaymentSource', 'achAccountNumberDisplay', 'Account Number', 'READ_ONLY_TEXT');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('selectedPaymentSource.creditCardSecurityCode', 'selectedPaymentSource', 'creditCardSecurityCode', 'Security Code', 'TEXT');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('paymentSource.inactiveReadOnly', 'paymentSource', 'inactive', 'Inactive', 'READ_ONLY_TEXT');
