INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.commitmentCode', 'commitment', 'commitmentCode', 'Code', 'CODE');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.commitmentCodeReadOnly', 'commitment', 'commitmentCode', 'Code', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.projectCode', 'commitment', 'projectCode', 'Designation', 'CODE');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.projectCodeReadOnly', 'commitment', 'projectCode', 'Designation', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.lineAmount', 'commitment', 'amount', 'Amount', 'TEXT');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.motivationCode', 'commitment', 'motivationCode', 'Motivation', 'CODE');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.motivationCodeReadOnly', 'commitment', 'motivationCode', 'Motivation', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.amountPerGift', 'commitment', 'amountPerGift', 'Amount Per Gift', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.amountTotal', 'commitment', 'amountTotal', 'Amount Total', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.amountPaid', 'commitment', 'amountPaid', 'Amount Paid', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.amountRemaining', 'commitment', 'amountRemaining', 'Amount Remaining', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.frequency', 'commitment', 'frequency', 'Frequency', 'PICKLIST');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.frequencyReadOnly', 'commitment', 'frequency', 'Frequency', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.startDate', 'commitment', 'startDate', 'Start Date', 'DATE');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.startDateReadOnly', 'commitment', 'startDate', 'Start Date', 'DATE_DISPLAY');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.endDate', 'commitment', 'endDate', 'End Date', 'DATE');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.endDateReadOnly', 'commitment', 'endDate', 'End Date', 'DATE_DISPLAY');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.status', 'commitment', 'status', 'Status', 'PICKLIST');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.notes', 'commitment', 'notes', 'Notes', 'LONG_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.projectedDate', 'commitment', 'projectedDate', 'Projected Date', 'DATE');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.dues', 'commitment', 'amountTotal', 'Dues', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.recurring', 'commitment', 'recurring', 'Recurring', 'PICKLIST');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.checkNumber', 'commitment', 'checkNumber', 'Number', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.paymentType', 'commitment', 'paymentType', 'Payment Method', 'PICKLIST');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.paymentSource.creditCardHolderName', 'commitment', 'paymentSource', 'Cardholder Name', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.paymentSource.creditCardType', 'commitment', 'paymentSource', 'Credit Card', 'PICKLIST');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.paymentSource.creditCardNumber', 'commitment', 'paymentSource', 'Number', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.paymentSource.creditCardExpiration', 'commitment', 'paymentSource', 'Expiration', 'CC_EXPIRATION');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.paymentSource.achHolderName', 'commitment', 'paymentSource', 'ACH Holder Name', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.paymentSource.achRoutingNumber', 'commitment', 'paymentSource', 'Routing Number', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.paymentSource.achAccountNumber', 'commitment', 'paymentSource', 'Account Number', 'TEXT');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.selectedPaymentSource', 'commitment', 'selectedPaymentSource', 'Payment Source', 'PAYMENT_SOURCE_PICKLIST');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.selectedPaymentSource.creditCardExpiration', 'commitment', 'selectedPaymentSource', 'Expiration', 'CC_EXPIRATION_DISPLAY');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.selectedAddress', 'commitment', 'selectedAddress', 'Billing Address', 'ADDRESS_PICKLIST');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.address.addressLine1', 'commitment', 'address', 'Address Line 1', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.address.addressLine2', 'commitment', 'address', 'Address Line 2', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.address.addressLine3', 'commitment', 'address', 'Address Line 3', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.address.city', 'commitment', 'address', 'City', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.address.stateProvince', 'commitment', 'address', 'State', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.address.postalCode', 'commitment', 'address', 'Zip Code', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.address.country', 'commitment', 'address', 'Country', 'PICKLIST');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.selectedPhone', 'commitment', 'selectedPhone', 'Billing Phone', 'PHONE_PICKLIST');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.phone.number', 'commitment', 'phone', 'Phone Number', 'TEXT');
