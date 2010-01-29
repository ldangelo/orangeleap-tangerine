INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('adjustedGift.aliasId', 'adjustedGift', 'aliasId', 'ID', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('adjustedGift.firstDistributionLine.amount', 'adjustedGift', 'firstDistributionLine.amount', 'Amount', 'NUMBER');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('adjustedGift.id', 'adjustedGift', 'id', 'Reference Number', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('adjustedGift.currentTotalAdjustedAmount', 'adjustedGift', 'currentTotalAdjustedAmount', 'Current Total Adjustment Amount', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('adjustedGift.adjustedAmount', 'adjustedGift', 'adjustedAmount', 'This Adjustment Amount', 'NUMBER');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('adjustedGift.reason', 'adjustedGift', 'adjustedReason', 'Adjustment Reason', 'PICKLIST');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('adjustedGift.status', 'adjustedGift', 'adjustedStatus', 'Adjustment Status', 'PICKLIST');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('adjustedGift.customFieldMap[bank]', 'adjustedGift', 'customFieldMap[bank]', 'Bank', 'PICKLIST');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('adjustedGift.transactionDate', 'adjustedGift', 'adjustedTransactionDate', 'Adjustment Date', 'DATE_DISPLAY');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('adjustedGift.type', 'adjustedGift', 'adjustedType', 'Adjustment Type', 'PICKLIST');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('adjustedGift.paymentRequired', 'adjustedGift', 'adjustedPaymentRequired', 'Payment Required', 'PICKLIST');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('adjustedGift.paymentTo', 'adjustedGift', 'adjustedPaymentTo', 'Payment To', 'giftPayment', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('adjustedGift.comments', 'adjustedGift', 'comments', 'Comments', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('adjustedGift.originalGiftId', 'adjustedGift', 'gift', 'originalGiftId', 'Original Gift', 'ASSOCIATION_DISPLAY');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('adjustedGift.paymentType', 'adjustedGift', 'paymentType', 'Payment Method', 'giftPayment', 'ADJUSTED_GIFT_PAYMENT_TYPE_PICKLIST');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('adjustedGift.paymentSource.id', 'adjustedGift', 'paymentSource', 'Payment Profile', 'paymentSource', 'ADJUSTED_GIFT_PAYMENT_SOURCE_PICKLIST');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('adjustedGift.customFieldMap[additionalInfo]', 'adjustedGift', 'customFieldMap[additionalInfo]', 'Additional Info', 'giftPayment', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('adjustedGift.customFieldMap[additionalInfoReadOnly]', 'adjustedGift', 'customFieldMap[additionalInfo]', 'Additional Info', 'giftPayment', 'READ_ONLY_TEXT');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('adjustedGift.posted', 'adjustedGift', 'posted', 'Posted', 'PICKLIST');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('adjustedGift.postedDate', 'adjustedGift', 'postedDate', 'Posted Date', 'postedDate', 'DATE');

-- Credit Card
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('adjustedGift.paymentSource.creditCardSecurityCode', 'adjustedGift', 'paymentSource', 'Security Code', '', 'TEXT');

-- Check
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('adjustedGift.checkNumber', 'adjustedGift', 'checkNumber', 'Check Number', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('adjustedGift.checkDate', 'adjustedGift', 'checkDate', 'Check Date', 'DATE');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('adjustedGift.paymentSource.checkHolderNameReadOnly', 'adjustedGift', 'paymentSource', 'Check Holder Name', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('adjustedGift.paymentSource.checkAccountNumberReadOnly', 'adjustedGift', 'paymentSource', 'Account Number', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('adjustedGift.paymentSource.checkRoutingNumberReadOnly', 'adjustedGift', 'paymentSource', 'Routing Number', 'READ_ONLY_TEXT');

-- Address
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('adjustedGift.address.id', 'adjustedGift', 'address', 'Billing Address', 'address', 'EXISTING_ADDRESS_PICKLIST');

-- Phone
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('adjustedGift.phone.id', 'adjustedGift', 'phone', 'Billing Phone', 'phone', 'EXISTING_PHONE_PICKLIST');

-- Distribution Lines
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('adjustedGift.distributionLines.amount', 'adjustedGift', 'distributionLines', 'Amt', 'NUMBER');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('adjustedGift.distributionLines.percentage', 'adjustedGift', 'distributionLines', 'Pct', 'PERCENTAGE');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('adjustedGift.distributionLines.projectCodeReadOnly', 'adjustedGift', 'distributionLines', 'Designation', 'CODE_OTHER_DISPLAY');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('adjustedGift.distributionLines.motivationCodeReadOnly', 'adjustedGift', 'distributionLines', 'Motivation', 'CODE_OTHER_DISPLAY');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('adjustedGift.distributionLines.other_motivationCodeReadOnly', 'adjustedGift', 'distributionLines', 'Motivation', 'HIDDEN');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('adjustedGift.distributionLines.customFieldMap[referenceReadOnly]', 'adjustedGift', 'constituent', 'distributionLines', 'Reference', 'QUERY_LOOKUP_DISPLAY');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('adjustedGift.distributionLines.customFieldMap[other_referenceReadOnly]', 'adjustedGift', 'constituent', 'distributionLines', 'Reference', 'HIDDEN');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('adjustedGift.distributionLines.customFieldMap[tributeReadOnly]', 'adjustedGift', 'distributionLines', 'Tribute Type', 'PICKLIST_DISPLAY');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('adjustedGift.distributionLines.customFieldMap[tributeReferenceReadOnly]', 'adjustedGift', 'constituent', 'distributionLines', 'Tribute Reference', 'QUERY_LOOKUP_DISPLAY');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('adjustedGift.distributionLines.customFieldMap[other_tributeReferenceReadOnly]', 'adjustedGift', 'constituent', 'distributionLines', 'Tribute Reference', 'HIDDEN');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('adjustedGift.distributionLines.customFieldMap[tributeOccasionReadOnly]', 'adjustedGift', 'distributionLines', 'Tribute Occasion', 'MULTI_PICKLIST_ADDITIONAL_DISPLAY');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('adjustedGift.distributionLines.customFieldMap[additional_tributeOccasionReadOnly]', 'adjustedGift', 'distributionLines', 'Tribute Occasion', 'HIDDEN');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('adjustedGift.distributionLines.customFieldMap[onBehalfOfReadOnly]', 'adjustedGift', 'constituent', 'distributionLines', 'On Behalf Of', 'QUERY_LOOKUP_DISPLAY');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('adjustedGift.distributionLines.customFieldMap[other_onBehalfOfReadOnly]', 'adjustedGift', 'constituent', 'distributionLines', 'On Behalf Of', 'HIDDEN');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('adjustedGift.distributionLines.customFieldMap[anonymousReadOnly]', 'adjustedGift', 'distributionLines', 'Anonymous', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('adjustedGift.distributionLines.customFieldMap[recognitionNameReadOnly]', 'adjustedGift', 'distributionLines', 'Recognition Name', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('adjustedGift.distributionLines.customFieldMap[notifiedReadOnly]', 'adjustedGift', 'constituent', 'distributionLines', 'To Be Notified', 'QUERY_LOOKUP_DISPLAY');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('adjustedGift.distributionLines.customFieldMap[other_notifiedReadOnly]', 'adjustedGift', 'constituent', 'distributionLines', 'To Be Notified', 'HIDDEN');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('adjustedGift.distributionLines.customFieldMap[messageReadOnly]', 'adjustedGift', 'distributionLines', 'Message', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('adjustedGift.distributionLines.customFieldMap[taxDeductibleReadOnly]', 'adjustedGift', 'distributionLines', 'Tax Ded', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('adjustedGift.distributionLines.customFieldMap[associatedPledgeIdReadOnly]', 'adjustedGift', 'pledge', 'distributionLines', 'Associated Pledge', 'pledge', 'ASSOCIATION_DISPLAY');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('adjustedGift.distributionLines.customFieldMap[associatedRecurringGiftIdReadOnly]', 'adjustedGift', 'recurringGift', 'distributionLines', 'Associated Recurring Gift', 'recurringGift', 'ASSOCIATION_DISPLAY');

-- Read Only Below
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('adjustedGift.adjustedAmountReadOnly', 'adjustedGift', 'adjustedAmount', 'This Adjustment Amount', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('adjustedGift.reasonReadOnly', 'adjustedGift', 'adjustedReason', 'Adjustment Reason', 'PICKLIST_DISPLAY');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('adjustedGift.statusReadOnly', 'adjustedGift', 'adjustedStatus', 'Adjustment Status', 'PICKLIST_DISPLAY');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('adjustedGift.typeReadOnly', 'adjustedGift', 'adjustedType', 'Adjustment Type', 'PICKLIST_DISPLAY');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('adjustedGift.paymentRequiredReadOnly', 'adjustedGift', 'adjustedPaymentRequired', 'Payment Required', 'PICKLIST_DISPLAY');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('adjustedGift.paymentToReadOnly', 'adjustedGift', 'adjustedPaymentTo', 'Payment To', 'giftPayment', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('adjustedGift.authCodeReadOnly', 'adjustedGift', 'authCode', 'Auth Code', 'wrappable', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('adjustedGift.customFieldMap[bankReadOnly]', 'adjustedGift', 'customFieldMap[bank]', 'Bank', 'PICKLIST_DISPLAY');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('adjustedGift.currencyCodeReadOnly', 'adjustedGift', 'currencyCode', 'Currency Code', 'CODE_OTHER_DISPLAY');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('adjustedGift.postedReadOnly', 'adjustedGift', 'posted', 'Posted', 'PICKLIST_DISPLAY');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('adjustedGift.postedDateReadOnly', 'adjustedGift', 'postedDate', 'Posted Date', 'postedDate', 'DATE_DISPLAY');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('adjustedGift.commentsReadOnly', 'adjustedGift', 'comments', 'Comments', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('adjustedGift.paymentMessageReadOnly', 'adjustedGift', 'paymentMessage', 'Payment Message', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('adjustedGift.paymentStatusReadOnly', 'adjustedGift', 'paymentStatus', 'Payment Status', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('adjustedGift.txRefNumReadOnly', 'adjustedGift', 'txRefNum', 'Reference Number', 'wrappable', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('adjustedGift.paymentTypeReadOnly', 'adjustedGift', 'paymentType', 'Payment Method', 'giftPayment', 'PICKLIST_DISPLAY');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('adjustedGift.customFieldMap[transactionDateReadOnly]', 'adjustedGift', 'customFieldMap[transactionDate]', 'Transaction Date', 'READ_ONLY_TEXT');


INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('adjustedGift.paymentSource.creditCardHolderNameReadOnly', 'adjustedGift', 'paymentSource', 'Cardholder Name', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('adjustedGift.paymentSource.creditCardTypeReadOnly', 'adjustedGift', 'paymentSource', 'Credit Card', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('adjustedGift.paymentSource.creditCardNumberReadOnly', 'adjustedGift', 'paymentSource', 'Credit Card Number', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('adjustedGift.paymentSource.creditCardExpirationDisplay', 'adjustedGift', 'paymentSource', 'Expiration', 'CC_EXPIRATION_DISPLAY');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('adjustedGift.paymentSource.achHolderNameReadOnly', 'adjustedGift', 'paymentSource', 'ACH Holder Name', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('adjustedGift.paymentSource.achRoutingNumberReadOnly', 'adjustedGift', 'paymentSource', 'Routing Number', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('adjustedGift.paymentSource.achAccountNumberReadOnly', 'adjustedGift', 'paymentSource', 'Account Number', 'READ_ONLY_TEXT');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('adjustedGift.checkNumberReadOnly', 'adjustedGift', 'checkNumber', 'Check Number', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('adjustedGift.checkDateReadOnly', 'adjustedGift', 'checkDate', 'Check Date', 'DATE_DISPLAY');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('adjustedGift.address.addressLine1ReadOnly', 'adjustedGift', 'address', 'Address Line 1', 'address', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('adjustedGift.address.addressLine2ReadOnly', 'adjustedGift', 'address', 'Address Line 2', 'address', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('adjustedGift.address.addressLine3ReadOnly', 'adjustedGift', 'address', 'Address Line 3', 'address', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('adjustedGift.address.cityReadOnly', 'adjustedGift', 'address', 'City', 'address', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('adjustedGift.address.stateProvinceReadOnly', 'adjustedGift', 'address', 'State/Province', 'address', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('adjustedGift.address.postalCodeReadOnly', 'adjustedGift', 'address', 'Zip/Postal Code', 'address', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('adjustedGift.address.countryReadOnly', 'adjustedGift', 'address', 'Country', 'address', 'READ_ONLY_TEXT');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('adjustedGift.phone.numberReadOnly', 'adjustedGift', 'phone', 'Phone Number', 'phone', 'READ_ONLY_TEXT');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('adjustedGift.distributionLines.amountReadOnly', 'adjustedGift', 'distributionLines', 'Amt', 'NUMBER_DISPLAY');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('adjustedGift.distributionLines.percentageReadOnly', 'adjustedGift', 'distributionLines', 'Pct', 'PERCENTAGE_DISPLAY');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('adjustedGift.constituent.accountNumber', 'adjustedGift', 'constituent', 'Account Number', 'NUMBER');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('adjustedGift.constituent.firstName', 'adjustedGift', 'constituent', 'First Name', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('adjustedGift.constituent.lastName', 'adjustedGift', 'constituent', 'Last Name', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('adjustedGift.constituent.organizationName', 'adjustedGift', 'constituent', 'Organization Name', 'TEXT');
