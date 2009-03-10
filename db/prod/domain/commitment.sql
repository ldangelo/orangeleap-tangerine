INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.amountPerGift', 'commitment', 'amountPerGift', 'Amount Per Gift', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.frequency', 'commitment', 'frequency', 'Frequency', 'PICKLIST');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.startDate', 'commitment', 'startDate', 'Start Date', 'DATE');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.endDate', 'commitment', 'endDate', 'End Date', 'DATE');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.notes', 'commitment', 'notes', 'Comments', 'LONG_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.currencyCode', 'commitment', 'currencyCode', 'Currency Code', 'CODE');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.customFieldMap[reference]', 'commitment', 'person', 'customFieldMap[reference]', 'Reference', 'QUERY_LOOKUP_OTHER');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.customFieldMap[other_reference]', 'commitment', 'person', 'customFieldMap[other_reference]', ' ', 'HIDDEN');


// Pledge-specific
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.pledgeStatus', 'commitment', 'pledgeStatus', 'Status', 'PICKLIST');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.pledgeDate', 'commitment', 'pledgeDate', 'Pledge Date', 'DATE');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.pledgeCancelDate', 'commitment', 'pledgeCancelDate', 'Pledge Cancel Date', 'DATE');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.pledgeCancelReason', 'commitment', 'pledgeCancelReason', 'Pledge Cancel Reason', 'LONG_TEXT');


INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.amountPerGiftReadOnly', 'commitment', 'amountPerGift', 'Amount Per Gift', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.frequencyReadOnly', 'commitment', 'frequency', 'Frequency', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.startDateReadOnly', 'commitment', 'startDate', 'Start Date', 'DATE_DISPLAY');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.endDateReadOnly', 'commitment', 'endDate', 'End Date', 'DATE_DISPLAY');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.currencyCodeReadOnly', 'commitment', 'currencyCode', 'Currency Code', 'READ_ONLY_TEXT');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.lineAmount', 'commitment', 'amount', 'Amount', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.percentage', 'commitment', 'percentage', 'Percentage', 'PERCENTAGE');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.projectCode', 'commitment', 'projectCode', 'Designation', 'CODE');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.motivationCode', 'commitment', 'motivationCode', 'Motivation', 'CODE_OTHER');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.other_motivationCode', 'commitment', 'other_motivationCode', ' ', 'HIDDEN');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.lineAmountReadOnly', 'commitment', 'amount', 'Amount', 'NUMBER');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.percentageReadOnly', 'commitment', 'percentage', 'Percentage', 'PERCENTAGE');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.projectCodeReadOnly', 'commitment', 'projectCode', 'Designation', 'CODE');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.paymentType', 'commitment', 'paymentType', 'Payment Method', 'PICKLIST');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.paymentTypeReadOnly', 'commitment', 'paymentType', 'Payment Method', 'PICKLIST_DISPLAY');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.paymentSource.creditCardHolderName', 'commitment', 'paymentSource', 'Cardholder Name', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.paymentSource.creditCardType', 'commitment', 'paymentSource', 'Credit Card', 'PICKLIST');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.paymentSource.creditCardNumber', 'commitment', 'paymentSource', 'Number', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.paymentSource.creditCardExpiration', 'commitment', 'paymentSource', 'Expiration', 'CC_EXPIRATION');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.selectedPaymentSource.creditCardHolderNameReadOnly', 'commitment', 'selectedPaymentSource', 'Cardholder Name', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.selectedPaymentSource.creditCardTypeReadOnly', 'commitment', 'selectedPaymentSource', 'Credit Card', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.selectedPaymentSource.creditCardNumberReadOnly', 'commitment', 'selectedPaymentSource', 'Number', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.selectedPaymentSource.creditCardExpirationDisplay', 'commitment', 'selectedPaymentSource', 'Expiration', 'CC_EXPIRATION_DISPLAY');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.paymentSource.achHolderName', 'commitment', 'paymentSource', 'ACH Holder Name', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.paymentSource.achRoutingNumber', 'commitment', 'paymentSource', 'Routing Number', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.paymentSource.achAccountNumber', 'commitment', 'paymentSource', 'Account Number', 'TEXT');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.selectedPaymentSource.achHolderNameReadOnly', 'commitment', 'selectedPaymentSource', 'ACH Holder Name', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.selectedPaymentSource.achRoutingNumberReadOnly', 'commitment', 'selectedPaymentSource', 'Routing Number', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.selectedPaymentSource.achAccountNumberReadOnly', 'commitment', 'selectedPaymentSource', 'Account Number', 'READ_ONLY_TEXT');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.checkNumber', 'commitment', 'checkNumber', 'Number', 'TEXT');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.selectedPaymentSource', 'commitment', 'selectedPaymentSource', 'Payment Profile', 'PAYMENT_SOURCE_PICKLIST');
--INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.selectedPaymentSource.creditCardExpiration', 'commitment', 'selectedPaymentSource', 'Expiration', 'CC_EXPIRATION_DISPLAY'); --TODO: put back?

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.selectedAddress', 'commitment', 'selectedAddress', 'Billing Address', 'ADDRESS_PICKLIST');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.address.addressLine1', 'commitment', 'address', 'Address Line 1', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.address.addressLine2', 'commitment', 'address', 'Address Line 2', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.address.addressLine3', 'commitment', 'address', 'Address Line 3', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.address.city', 'commitment', 'address', 'City', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.address.stateProvince', 'commitment', 'address', 'State/Province', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.address.postalCode', 'commitment', 'address', 'Zip/Postal Code', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.address.country', 'commitment', 'address', 'Country', 'PICKLIST');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.selectedAddress.addressLine1ReadOnly', 'commitment', 'selectedAddress', 'Address Line 1', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.selectedAddress.addressLine2ReadOnly', 'commitment', 'selectedAddress', 'Address Line 2', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.selectedAddress.addressLine3ReadOnly', 'commitment', 'selectedAddress', 'Address Line 3', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.selectedAddress.cityReadOnly', 'commitment', 'selectedAddress', 'City', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.selectedAddress.stateProvinceReadOnly', 'commitment', 'selectedAddress', 'State/Province', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.selectedAddress.postalCodeReadOnly', 'commitment', 'selectedAddress', 'Zip/Postal Code', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.selectedAddress.countryReadOnly', 'commitment', 'selectedAddress', 'Country', 'READ_ONLY_TEXT');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.selectedPhone', 'commitment', 'selectedPhone', 'Billing Phone', 'PHONE_PICKLIST');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.phone.number', 'commitment', 'phone', 'Phone Number', 'TEXT');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.selectedPhone.numberReadOnly', 'commitment', 'selectedPhone', 'Phone Number', 'READ_ONLY_TEXT');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.selectedEmail', 'commitment', 'selectedEmail', 'Receipt EMail', 'EMAIL_PICKLIST');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.email.emailAddress', 'commitment', 'email', 'Email', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.sendAcknowledgment', 'commitment', 'sendAcknowledgment', 'Send Acknowledgment', 'CHECKBOX');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.acknowledgmentDate', 'commitment', 'acknowledgmentDate', 'Acknowledgment Date', 'DATE');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.amountTotal', 'commitment', 'amountTotal', 'Amount Total', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.amountPaid', 'commitment', 'amountPaid', 'Amount Paid', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.amountRemaining', 'commitment', 'amountRemaining', 'Amount Remaining', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.status', 'commitment', 'status', 'Status', 'PICKLIST');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.projectedDate', 'commitment', 'projectedDate', 'Projected Date', 'DATE');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.dues', 'commitment', 'amountTotal', 'Dues', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.recurring', 'commitment', 'recurring', 'Recurring', 'PICKLIST');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.amountTotalReadOnly', 'commitment', 'amountTotal', 'Amount Total', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('commitment.recurringReadOnly', 'commitment', 'recurring', 'Recurring', 'PICKLIST_DISPLAY');

