INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('recurringGift.amountPerGift', 'recurringGift', 'amountPerGift', 'Amount Per Gift', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('recurringGift.frequency', 'recurringGift', 'frequency', 'Frequency', 'PICKLIST');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('recurringGift.startDate', 'recurringGift', 'startDate', 'Start Date', 'DATE');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('recurringGift.endDate', 'recurringGift', 'endDate', 'End Date', 'DATE');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('recurringGift.comments', 'recurringGift', 'comments', 'Comments', 'LONG_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('recurringGift.currencyCode', 'recurringGift', 'currencyCode', 'Currency Code', 'CODE');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('recurringGift.associatedGiftIdsReadOnly', 'recurringGift', 'gift', 'associatedGiftIds', 'Associated Gifts', 'SELECTION_DISPLAY');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('recurringGift.recurringGiftStatus', 'recurringGift', 'recurringGiftStatus', 'Status', 'PICKLIST');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('recurringGift.customFieldMap[initialReminder]', 'recurringGift', 'customFieldMap[initialReminder]', 'Initial Reminder (days)', 'NUMBER');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('recurringGift.customFieldMap[maximumReminders]', 'recurringGift', 'customFieldMap[maximumReminders]', 'Maximum Reminders', 'NUMBER');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('recurringGift.customFieldMap[reminderInterval]', 'recurringGift', 'customFieldMap[reminderInterval]', 'Reminder interval (days)', 'NUMBER');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('recurringGift.amountPerGiftReadOnly', 'recurringGift', 'amountPerGift', 'Amount Per Gift', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('recurringGift.frequencyReadOnly', 'recurringGift', 'frequency', 'Frequency', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('recurringGift.startDateReadOnly', 'recurringGift', 'startDate', 'Start Date', 'DATE_DISPLAY');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('recurringGift.endDateReadOnly', 'recurringGift', 'endDate', 'End Date', 'DATE_DISPLAY');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('recurringGift.currencyCodeReadOnly', 'recurringGift', 'currencyCode', 'Currency Code', 'READ_ONLY_TEXT');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('recurringGift.lineAmount', 'recurringGift', 'amount', 'Amt', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('recurringGift.percentage', 'recurringGift', 'percentage', 'Pct', 'PERCENTAGE');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('recurringGift.projectCode', 'recurringGift', 'projectCode', 'Designation', 'CODE');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('recurringGift.motivationCode', 'recurringGift', 'motivationCode', 'Motivation', 'CODE_OTHER');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('recurringGift.other_motivationCode', 'recurringGift', 'other_motivationCode', ' ', 'HIDDEN');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('recurringGift.customFieldMap[reference]', 'recurringGift', 'person', 'customFieldMap[reference]', 'Reference', 'QUERY_LOOKUP_OTHER');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('recurringGift.customFieldMap[other_reference]', 'recurringGift', 'person', 'customFieldMap[other_reference]', ' ', 'HIDDEN');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('recurringGift.customFieldMap[tribute]', 'recurringGift', 'customFieldMap[tribute]', 'Tribute Type', 'PICKLIST');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('recurringGift.customFieldMap[tributeReference]', 'recurringGift', 'person', 'customFieldMap[tributeReference]', 'Tribute Reference', 'QUERY_LOOKUP_OTHER');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('recurringGift.customFieldMap[other_tributeReference]', 'recurringGift', 'person', 'customFieldMap[other_tributeReference]', ' ', 'HIDDEN');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('recurringGift.customFieldMap[tributeOccasion]', 'recurringGift', 'customFieldMap[tributeOccasion]', 'Tribute Occasion', 'MULTI_PICKLIST_ADDITIONAL');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('recurringGift.customFieldMap[additional_tributeOccasion]', 'recurringGift', 'customFieldMap[additional_tributeOccasion]', ' ', 'HIDDEN');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('recurringGift.customFieldMap[onBehalfOf]', 'recurringGift', 'person', 'customFieldMap[onBehalfOf]', 'On Behalf Of', 'QUERY_LOOKUP_OTHER');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('recurringGift.customFieldMap[other_onBehalfOf]', 'recurringGift', 'person', 'customFieldMap[other_onBehalfOf]', ' ', 'HIDDEN');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('recurringGift.customFieldMap[anonymous]', 'recurringGift', 'customFieldMap[anonymous]', 'Anonymous', 'CHECKBOX');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('recurringGift.customFieldMap[recognitionName]', 'recurringGift', 'customFieldMap[recognitionName]', 'Recognition Name', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('recurringGift.customFieldMap[notified]', 'recurringGift', 'person', 'customFieldMap[notified]', 'To Be Notified', 'QUERY_LOOKUP_OTHER');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('recurringGift.customFieldMap[other_notified]', 'recurringGift', 'person', 'customFieldMap[other_notified]', ' ', 'HIDDEN');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('recurringGift.customFieldMap[message]', 'recurringGift', 'customFieldMap[message]', 'Message', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('recurringGift.customFieldMap[taxDeductible]', 'recurringGift', 'customFieldMap[taxDeductible]', 'Tax Ded', 'CHECKBOX');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('recurringGift.lineAmountReadOnly', 'recurringGift', 'amount', 'Amt', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('recurringGift.percentageReadOnly', 'recurringGift', 'percentage', 'Pct', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('recurringGift.projectCodeReadOnly', 'recurringGift', 'projectCode', 'Designation', 'CODE');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('recurringGift.motivationCodeReadOnly', 'recurringGift', 'motivationCode', 'Motivation', 'CODE_OTHER');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('recurringGift.other_motivationCodeReadOnly', 'recurringGift', 'other_motivationCode', ' ', 'HIDDEN');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('recurringGift.customFieldMap[referenceReadOnly]', 'recurringGift', 'person', 'customFieldMap[reference]', 'Reference', 'QUERY_LOOKUP_DISPLAY');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('recurringGift.customFieldMap[other_referenceReadOnly]', 'recurringGift', 'person', 'customFieldMap[other_reference]', ' ', 'HIDDEN');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('recurringGift.customFieldMap[tributeReadOnly]', 'recurringGift', 'customFieldMap[tribute]', 'Tribute Type', 'PICKLIST_DISPLAY');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('recurringGift.customFieldMap[tributeReferenceReadOnly]', 'recurringGift', 'person', 'customFieldMap[tributeReference]', 'Tribute Reference', 'QUERY_LOOKUP_DISPLAY');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('recurringGift.customFieldMap[other_tributeReferenceReadOnly]', 'recurringGift', 'person', 'customFieldMap[other_tributeReference]', ' ', 'HIDDEN');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('recurringGift.customFieldMap[tributeOccasionReadOnly]', 'recurringGift', 'customFieldMap[tributeOccasion]', 'Tribute Occasion', 'MULTI_PICKLIST_ADDITIONAL_DISPLAY');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('recurringGift.customFieldMap[additional_tributeOccasionReadOnly]', 'recurringGift', 'customFieldMap[additional_tributeOccasion]', ' ', 'HIDDEN');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('recurringGift.customFieldMap[onBehalfOfReadOnly]', 'recurringGift', 'person', 'customFieldMap[onBehalfOf]', 'On Behalf Of', 'QUERY_LOOKUP_DISPLAY');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('recurringGift.customFieldMap[other_onBehalfOfReadOnly]', 'recurringGift', 'person', 'customFieldMap[other_onBehalfOf]', ' ', 'HIDDEN');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('recurringGift.customFieldMap[anonymousReadOnly]', 'recurringGift', 'customFieldMap[anonymous]', 'Anonymous', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('recurringGift.customFieldMap[recognitionNameReadOnly]', 'recurringGift', 'customFieldMap[recognitionName]', 'Recognition Name', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('recurringGift.customFieldMap[notifiedReadOnly]', 'recurringGift', 'person', 'customFieldMap[notified]', 'To Be Notified', 'QUERY_LOOKUP_DISPLAY');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('recurringGift.customFieldMap[other_notifiedReadOnly]', 'recurringGift', 'person', 'customFieldMap[other_notified]', ' ', 'HIDDEN');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('recurringGift.customFieldMap[messageReadOnly]', 'recurringGift', 'customFieldMap[message]', 'Message', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('recurringGift.customFieldMap[taxDeductibleReadOnly]', 'recurringGift', 'customFieldMap[taxDeductible]', 'Tax Ded', 'READ_ONLY_TEXT');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('recurringGift.paymentType', 'recurringGift', 'paymentType', 'Payment Method', 'PICKLIST');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('recurringGift.paymentTypeReadOnly', 'recurringGift', 'paymentType', 'Payment Method', 'PAYMENT_TYPE_READ_ONLY_TEXT');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('recurringGift.paymentSource.creditCardHolderName', 'recurringGift', 'paymentSource', 'Cardholder Name', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('recurringGift.paymentSource.creditCardType', 'recurringGift', 'paymentSource', 'Credit Card', 'PICKLIST');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('recurringGift.paymentSource.creditCardNumber', 'recurringGift', 'paymentSource', 'Credit Card Number', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('recurringGift.paymentSource.creditCardExpiration', 'recurringGift', 'paymentSource', 'Expiration', 'CC_EXPIRATION');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('recurringGift.selectedPaymentSource.creditCardHolderNameReadOnly', 'recurringGift', 'selectedPaymentSource', 'Cardholder Name', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('recurringGift.selectedPaymentSource.creditCardTypeReadOnly', 'recurringGift', 'selectedPaymentSource', 'Credit Card', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('recurringGift.selectedPaymentSource.creditCardNumberReadOnly', 'recurringGift', 'selectedPaymentSource', 'Credit Card Number', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('recurringGift.selectedPaymentSource.creditCardExpirationDisplay', 'recurringGift', 'selectedPaymentSource', 'Expiration', 'CC_EXPIRATION_DISPLAY');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('recurringGift.paymentSource.achHolderName', 'recurringGift', 'paymentSource', 'ACH Holder Name', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('recurringGift.paymentSource.achRoutingNumber', 'recurringGift', 'paymentSource', 'Routing Number', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('recurringGift.paymentSource.achAccountNumber', 'recurringGift', 'paymentSource', 'Account Number', 'TEXT');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('recurringGift.selectedPaymentSource.achHolderNameReadOnly', 'recurringGift', 'selectedPaymentSource', 'ACH Holder Name', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('recurringGift.selectedPaymentSource.achRoutingNumberReadOnly', 'recurringGift', 'selectedPaymentSource', 'Routing Number', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('recurringGift.selectedPaymentSource.achAccountNumberReadOnly', 'recurringGift', 'selectedPaymentSource', 'Account Number', 'READ_ONLY_TEXT');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('recurringGift.checkNumber', 'recurringGift', 'checkNumber', 'Number', 'TEXT');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('recurringGift.selectedPaymentSource', 'recurringGift', 'selectedPaymentSource', 'Payment Profile', 'PAYMENT_SOURCE_PICKLIST');
--INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('recurringGift.selectedPaymentSource.creditCardExpiration', 'recurringGift', 'selectedPaymentSource', 'Expiration', 'CC_EXPIRATION_DISPLAY'); --TODO: put back?

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('recurringGift.selectedAddress', 'recurringGift', 'selectedAddress', 'Billing Address', 'ADDRESS_PICKLIST');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('recurringGift.address.customFieldMap[addressType]', 'recurringGift', 'address', 'Address Type', 'MULTI_PICKLIST');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('recurringGift.address.addressLine1', 'recurringGift', 'address', 'Address Line 1', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('recurringGift.address.addressLine2', 'recurringGift', 'address', 'Address Line 2', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('recurringGift.address.addressLine3', 'recurringGift', 'address', 'Address Line 3', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('recurringGift.address.city', 'recurringGift', 'address', 'City', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('recurringGift.address.stateProvince', 'recurringGift', 'address', 'State/Province', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('recurringGift.address.postalCode', 'recurringGift', 'address', 'Zip/Postal Code', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('recurringGift.address.country', 'recurringGift', 'address', 'Country', 'PICKLIST');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('recurringGift.selectedAddress.customFieldMap[addressTypeReadOnly]', 'recurringGift', 'selectedAddress', 'Address Type', 'MULTI_PICKLIST_DISPLAY');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('recurringGift.selectedAddress.addressLine1ReadOnly', 'recurringGift', 'selectedAddress', 'Address Line 1', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('recurringGift.selectedAddress.addressLine2ReadOnly', 'recurringGift', 'selectedAddress', 'Address Line 2', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('recurringGift.selectedAddress.addressLine3ReadOnly', 'recurringGift', 'selectedAddress', 'Address Line 3', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('recurringGift.selectedAddress.cityReadOnly', 'recurringGift', 'selectedAddress', 'City', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('recurringGift.selectedAddress.stateProvinceReadOnly', 'recurringGift', 'selectedAddress', 'State/Province', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('recurringGift.selectedAddress.postalCodeReadOnly', 'recurringGift', 'selectedAddress', 'Zip/Postal Code', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('recurringGift.selectedAddress.countryReadOnly', 'recurringGift', 'selectedAddress', 'Country', 'READ_ONLY_TEXT');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('recurringGift.selectedPhone', 'recurringGift', 'selectedPhone', 'Billing Phone', 'PHONE_PICKLIST');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('recurringGift.phone.customFieldMap[phoneType]', 'recurringGift', 'phone', 'Phone Type', 'MULTI_PICKLIST');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('recurringGift.phone.number', 'recurringGift', 'phone', 'Phone Number', 'TEXT');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('recurringGift.selectedPhone.customFieldMap[phoneTypeReadOnly]', 'recurringGift', 'selectedPhone', 'Phone Type', 'MULTI_PICKLIST_DISPLAY');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('recurringGift.selectedPhone.numberReadOnly', 'recurringGift', 'selectedPhone', 'Phone Number', 'READ_ONLY_TEXT');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('recurringGift.selectedEmail', 'recurringGift', 'selectedEmail', 'Receipt EMail', 'EMAIL_PICKLIST');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('recurringGift.email.emailAddress', 'recurringGift', 'email', 'Email', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('recurringGift.sendAcknowledgment', 'recurringGift', 'sendAcknowledgment', 'Send Acknowledgment', 'CHECKBOX');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('recurringGift.acknowledgmentDate', 'recurringGift', 'acknowledgmentDate', 'Acknowledgment Date', 'DATE');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('recurringGift.amountTotal', 'recurringGift', 'amountTotal', 'Amount Total', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('recurringGift.amountPaid', 'recurringGift', 'amountPaid', 'Amount Paid', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('recurringGift.amountRemaining', 'recurringGift', 'amountRemaining', 'Amount Remaining', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('recurringGift.projectedDate', 'recurringGift', 'projectedDate', 'Projected Date', 'DATE');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('recurringGift.dues', 'recurringGift', 'amountTotal', 'Dues', 'TEXT');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('recurringGift.amountTotalReadOnly', 'recurringGift', 'amountTotal', 'Amount Total', 'READ_ONLY_TEXT');

