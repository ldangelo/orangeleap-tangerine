INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('gift.amount', 'gift', 'amount', 'Amount', 'NUMBER');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('gift.currencyCode', 'gift', 'currencyCode', 'Currency Code', 'CODE');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('gift.comments', 'gift', 'comments', 'Comments', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('gift.transactionDate', 'gift', 'transactionDate', 'Transaction Date', 'DATE');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('gift.donationDate', 'gift', 'donationDate', 'Date of Donation', 'DATE');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('gift.posted', 'gift', 'posted', 'Posted', 'PICKLIST_DISPLAY');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('gift.postedDate', 'gift', 'postedDate', 'Posted Date', 'postedDate', 'DATE');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('gift.postmarkDate', 'gift', 'postmarkDate', 'Postmark Date', 'DATE');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('gift.customFieldMap[source]', 'gift', 'customFieldMap[source]', 'Source', 'PICKLIST');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('gift.giftStatus', 'gift', 'giftStatus', 'Status', 'PICKLIST');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('gift.customFieldMap[bank]', 'gift', 'customFieldMap[bank]', 'Bank', 'PICKLIST');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('gift.associatedPledgeIds', 'gift', 'pledge', 'associatedPledgeIds', 'Pledges', 'pledge', 'SELECTION');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('gift.associatedRecurringGiftIds', 'gift', 'recurringGift', 'associatedRecurringGiftIds', 'Recurring Gifts', 'recurringGift', 'SELECTION');


INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('gift.id', 'gift', 'id', 'Reference Number', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('gift.amountReadOnly', 'gift', 'amount', 'Amount', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('gift.currencyCodeReadOnly', 'gift', 'currencyCode', 'Currency Code', 'CODE_OTHER_DISPLAY');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('gift.avsMessageReadOnly', 'gift', 'avsMessage', 'AVS Message', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('gift.paymentMessageReadOnly', 'gift', 'paymentMessage', 'Payment Message', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('gift.paymentStatusReadOnly', 'gift', 'paymentStatus', 'Payment Status', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('gift.txRefNumReadOnly', 'gift', 'txRefNum', 'Reference Number', 'wrappable', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('gift.authCodeReadOnly', 'gift', 'authCode', 'Auth Code', 'wrappable', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('gift.idHidden', 'gift', 'id', ' ', 'HIDDEN');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('gift.giftStatusReadOnly', 'gift', 'giftStatus', 'Status', 'PICKLIST_DISPLAY');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('gift.customFieldMap[bankReadOnly]', 'gift', 'customFieldMap[bank]', 'Bank', 'PICKLIST_DISPLAY');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('gift.postedDateReadOnly', 'gift', 'postedDate', 'Posted Date', 'postedDate', 'DATE_DISPLAY');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('gift.donationDateReadOnly', 'gift', 'donationDate', 'Date of Donation', 'DATE_DISPLAY');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('gift.postmarkDateReadOnly', 'gift', 'postmarkDate', 'Postmark Date', 'DATE_DISPLAY');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('gift.customFieldMap[sourceReadOnly]', 'gift', 'customFieldMap[source]', 'Source', 'PICKLIST_DISPLAY');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('gift.associatedPledgeIdsReadOnly', 'gift', 'pledge', 'associatedPledgeIds', 'Pledges', 'pledge', 'SELECTION_DISPLAY');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('gift.associatedRecurringGiftIdsReadOnly', 'gift', 'recurringGift', 'associatedRecurringGiftIds', 'Recurring Gifts', 'recurringGift', 'SELECTION_DISPLAY');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('gift.adjustedGiftsReadOnly', 'gift', 'adjustedGift', 'adjustedGifts', 'Adjustments', 'adjustedGift', 'SELECTION_DISPLAY');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('gift.constituentLastName', 'gift', 'constituent.lastName', 'Last Name', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('gift.constituentFirstName', 'gift', 'constituent.firstName', 'First Name', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('gift.commitmentCode', 'gift', 'commitmentCode', 'Commitment', 'CODE');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('gift.paymentType', 'gift', 'paymentType', 'Payment Method', 'giftPayment', 'PICKLIST');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('gift.paymentTypeReadOnly', 'gift', 'paymentType', 'Payment Method', 'giftPayment', 'PICKLIST_DISPLAY');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('gift.checkNumber', 'gift', 'checkNumber', 'Check Number', '', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('gift.checkNumberReadOnly', 'gift', 'checkNumber', 'Check Number', '', 'READ_ONLY_TEXT');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('gift.paymentSource.creditCardType', 'gift', 'paymentSource', 'Credit Card', 'newCredit', 'PICKLIST');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('gift.paymentSource.creditCardHolderName', 'gift', 'paymentSource', 'Cardholder Name', 'newCredit', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('gift.paymentSource.creditCardNumber', 'gift', 'paymentSource', 'Credit Card Number', 'newCredit', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('gift.paymentSource.creditCardExpiration', 'gift', 'paymentSource', 'Expiration', 'newCredit', 'CC_EXPIRATION');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('gift.paymentSource.creditCardSecurityCode', 'gift', 'paymentSource', 'Security Code', '', 'TEXT');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('gift.paymentSource.achHolderName', 'gift', 'paymentSource', 'ACH Holder Name', 'newAch', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('gift.paymentSource.achRoutingNumber', 'gift', 'paymentSource', 'Routing Number', 'newAch', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('gift.paymentSource.achAccountNumber', 'gift', 'paymentSource', 'Account Number', 'newAch', 'TEXT');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('gift.paymentSource.creditCardHolderNameReadOnly', 'gift', 'paymentSource', 'Cardholder Name', 'existingCredit', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('gift.paymentSource.creditCardTypeReadOnly', 'gift', 'paymentSource', 'Credit Card', 'existingCredit', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('gift.paymentSource.creditCardNumberReadOnly', 'gift', 'paymentSource', 'Credit Card Number', 'existingCredit', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('gift.paymentSource.creditCardExpirationDisplay', 'gift', 'paymentSource', 'Expiration', 'existingCredit', 'CC_EXPIRATION_DISPLAY');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('gift.paymentSource.achHolderNameReadOnly', 'gift', 'paymentSource', 'ACH Holder Name', 'existingAch', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('gift.paymentSource.achRoutingNumberReadOnly', 'gift', 'paymentSource', 'Routing Number', 'existingAch', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('gift.paymentSource.achAccountNumberReadOnly', 'gift', 'paymentSource', 'Account Number', 'existingAch', 'READ_ONLY_TEXT');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('gift.paymentSource.id', 'gift', 'paymentSource', 'Payment Profile', 'giftPayment', 'PAYMENT_SOURCE_PICKLIST');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('gift.customFieldMap[additionalInfo]', 'gift', 'customFieldMap[additionalInfo]', 'Additional Info', 'giftPayment', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('gift.customFieldMap[additionalInfoReadOnly]', 'gift', 'customFieldMap[additionalInfo]', 'Additional Info', 'giftPayment', 'READ_ONLY_TEXT');


INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('gift.address.id', 'gift', 'address', 'Billing Address', 'address', 'ADDRESS_PICKLIST');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('gift.address.customFieldMap[addressType]', 'gift', 'address', 'Address Type', 'address,newAddress', 'MULTI_PICKLIST');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('gift.address.addressLine1', 'gift', 'address', 'Address Line 1', 'address,newAddress', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('gift.address.addressLine2', 'gift', 'address', 'Address Line 2', 'address,newAddress', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('gift.address.addressLine3', 'gift', 'address', 'Address Line 3', 'address,newAddress', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('gift.address.city', 'gift', 'address', 'City', 'address,newAddress', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('gift.address.stateProvince', 'gift', 'address', 'State/Province', 'address,newAddress', 'PICKLIST');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('gift.address.postalCode', 'gift', 'address', 'Zip/Postal Code', 'address,newAddress', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('gift.address.country', 'gift', 'address', 'Country', 'address,newAddress', 'PICKLIST');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('gift.address.customFieldMap[addressTypeReadOnly]', 'gift', 'address', 'Address Type', 'address', 'MULTI_PICKLIST_DISPLAY');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('gift.address.addressLine1ReadOnly', 'gift', 'address', 'Address Line 1', 'address', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('gift.address.addressLine2ReadOnly', 'gift', 'address', 'Address Line 2', 'address', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('gift.address.addressLine3ReadOnly', 'gift', 'address', 'Address Line 3', 'address', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('gift.address.cityReadOnly', 'gift', 'address', 'City', 'address', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('gift.address.stateProvinceReadOnly', 'gift', 'address', 'State/Province', 'address', 'PICKLIST_DISPLAY');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('gift.address.postalCodeReadOnly', 'gift', 'address', 'Zip/Postal Code', 'address', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('gift.address.countryReadOnly', 'gift', 'address', 'Country', 'address', 'READ_ONLY_TEXT');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('gift.phone.id', 'gift', 'phone', 'Billing Phone', 'phone', 'PHONE_PICKLIST');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('gift.phone.customFieldMap[phoneType]', 'gift', 'phone', 'Phone Type', 'phone,newPhone', 'MULTI_PICKLIST');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('gift.phone.number', 'gift', 'phone', 'Phone Number', 'phone,newPhone', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('gift.phone.customFieldMap[phoneTypeReadOnly]', 'gift', 'phone', 'Phone Type', 'phone', 'MULTI_PICKLIST_DISPLAY');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('gift.phone.numberReadOnly', 'gift', 'phone', 'Phone Number', 'phone', 'READ_ONLY_TEXT');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('gift.aliasId', 'gift', 'aliasId', 'ID', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('gift.firstDistributionLine.amount', 'gift', 'firstDistributionLine.amount', 'Amount', 'NUMBER');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('gift.distributionLines.amount', 'gift', 'distributionLines', 'Amt', 'NUMBER');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('gift.distributionLines.percentage', 'gift', 'distributionLines', 'Pct', 'PERCENTAGE');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('gift.distributionLines.projectCode', 'gift', 'distributionLines', 'Designation', 'CODE');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('gift.distributionLines.motivationCode', 'gift', 'distributionLines', 'Motivation', 'CODE_OTHER');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('gift.distributionLines.other_motivationCode', 'gift', 'distributionLines', 'Motivation', 'HIDDEN');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('gift.distributionLines.customFieldMap[reference]', 'gift', 'constituent', 'distributionLines', 'Reference', 'QUERY_LOOKUP_OTHER');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('gift.distributionLines.customFieldMap[other_reference]', 'gift', 'constituent', 'distributionLines', 'Reference', 'HIDDEN');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('gift.distributionLines.customFieldMap[tribute]', 'gift', 'distributionLines', 'Tribute Type', 'PICKLIST');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('gift.distributionLines.customFieldMap[tributeReference]', 'gift', 'constituent', 'distributionLines', 'Tribute Reference', 'QUERY_LOOKUP_OTHER');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('gift.distributionLines.customFieldMap[other_tributeReference]', 'gift', 'constituent', 'distributionLines', 'Tribute Reference', 'HIDDEN');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('gift.distributionLines.customFieldMap[tributeOccasion]', 'gift', 'distributionLines', 'Tribute Occasion', 'MULTI_PICKLIST_ADDITIONAL');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('gift.distributionLines.customFieldMap[additional_tributeOccasion]', 'gift', 'distributionLines', 'Tribute Occasion', 'HIDDEN');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('gift.distributionLines.customFieldMap[onBehalfOf]', 'gift', 'constituent', 'distributionLines', 'On Behalf Of', 'QUERY_LOOKUP_OTHER');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('gift.distributionLines.customFieldMap[other_onBehalfOf]', 'gift', 'constituent', 'distributionLines', 'On Behalf Of', 'HIDDEN');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('gift.distributionLines.customFieldMap[anonymous]', 'gift', 'distributionLines', 'Anonymous', 'CHECKBOX');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('gift.distributionLines.customFieldMap[recognitionName]', 'gift', 'distributionLines', 'Recognition Name', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('gift.distributionLines.customFieldMap[notified]', 'gift', 'constituent', 'distributionLines', 'To Be Notified', 'QUERY_LOOKUP_OTHER');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('gift.distributionLines.customFieldMap[other_notified]', 'gift', 'constituent', 'distributionLines', 'To Be Notified', 'HIDDEN');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('gift.distributionLines.customFieldMap[message]', 'gift', 'distributionLines', 'Message', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('gift.distributionLines.customFieldMap[taxDeductible]', 'gift', 'distributionLines', 'Tax Ded', 'CHECKBOX');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('gift.distributionLines.customFieldMap[associatedPledgeId]', 'gift', 'pledge', 'distributionLines', 'Associated Pledge', 'pledge', 'ASSOCIATION');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('gift.distributionLines.customFieldMap[associatedRecurringGiftId]', 'gift', 'recurringGift', 'distributionLines', 'Associated Recurring Gift', 'recurringGift', 'ASSOCIATION');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('gift.distributionLines.amountReadOnly', 'gift', 'distributionLines', 'Amt', 'NUMBER_DISPLAY');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('gift.distributionLines.percentageReadOnly', 'gift', 'distributionLines', 'Pct', 'PERCENTAGE_DISPLAY');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('gift.distributionLines.projectCodeReadOnly', 'gift', 'distributionLines', 'Designation', 'CODE_OTHER_DISPLAY');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('gift.distributionLines.motivationCodeReadOnly', 'gift', 'distributionLines', 'Motivation', 'CODE_OTHER_DISPLAY');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('gift.distributionLines.other_motivationCodeReadOnly', 'gift', 'distributionLines', 'Motivation', 'HIDDEN');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('gift.distributionLines.customFieldMap[referenceReadOnly]', 'gift', 'constituent', 'distributionLines', 'Reference', 'QUERY_LOOKUP_DISPLAY');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('gift.distributionLines.customFieldMap[other_referenceReadOnly]', 'gift', 'constituent', 'distributionLines', 'Reference', 'HIDDEN');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('gift.distributionLines.customFieldMap[tributeReadOnly]', 'gift', 'distributionLines', 'Tribute Type', 'PICKLIST_DISPLAY');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('gift.distributionLines.customFieldMap[tributeReferenceReadOnly]', 'gift', 'constituent', 'distributionLines', 'Tribute Reference', 'QUERY_LOOKUP_DISPLAY');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('gift.distributionLines.customFieldMap[other_tributeReferenceReadOnly]', 'gift', 'constituent', 'distributionLines', 'Tribute Reference', 'HIDDEN');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('gift.distributionLines.customFieldMap[tributeOccasionReadOnly]', 'gift', 'distributionLines', 'Tribute Occasion', 'MULTI_PICKLIST_ADDITIONAL_DISPLAY');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('gift.distributionLines.customFieldMap[additional_tributeOccasionReadOnly]', 'gift', 'distributionLines', 'Tribute Occasion', 'HIDDEN');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('gift.distributionLines.customFieldMap[onBehalfOfReadOnly]', 'gift', 'constituent', 'distributionLines', 'On Behalf Of', 'QUERY_LOOKUP_DISPLAY');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('gift.distributionLines.customFieldMap[other_onBehalfOfReadOnly]', 'gift', 'constituent', 'distributionLines', 'On Behalf Of', 'HIDDEN');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('gift.distributionLines.customFieldMap[anonymousReadOnly]', 'gift', 'distributionLines', 'Anonymous', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('gift.distributionLines.customFieldMap[recognitionNameReadOnly]', 'gift', 'distributionLines', 'Recognition Name', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('gift.distributionLines.customFieldMap[notifiedReadOnly]', 'gift', 'constituent', 'distributionLines', 'To Be Notified', 'QUERY_LOOKUP_DISPLAY');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('gift.distributionLines.customFieldMap[other_notifiedReadOnly]', 'gift', 'constituent', 'distributionLines', 'To Be Notified', 'HIDDEN');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('gift.distributionLines.customFieldMap[messageReadOnly]', 'gift', 'distributionLines', 'Message', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('gift.distributionLines.customFieldMap[taxDeductibleReadOnly]', 'gift', 'distributionLines', 'Tax Ded', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('gift.distributionLines.customFieldMap[associatedPledgeIdReadOnly]', 'gift', 'pledge', 'distributionLines', 'Associated Pledge', 'pledge', 'ASSOCIATION_DISPLAY');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('gift.distributionLines.customFieldMap[associatedRecurringGiftIdReadOnly]', 'gift', 'recurringGift', 'distributionLines', 'Associated Recurring Gift', 'recurringGift', 'ASSOCIATION_DISPLAY');
