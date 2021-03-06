INSERT INTO SECTION_DEFINITION (SECTION_DEFINITION_ID, PAGE_TYPE, SECTION_NAME, DEFAULT_LABEL, SECTION_ORDER, LAYOUT_TYPE, ROLE) VALUES (1000004, 'giftPosted', 'gift.donation', 'Donation Information', 1, 'TWO_COLUMN', 'ROLE_USER');
INSERT INTO SECTION_DEFINITION (SECTION_DEFINITION_ID, PAGE_TYPE, SECTION_NAME, DEFAULT_LABEL, SECTION_ORDER, LAYOUT_TYPE, ROLE) VALUES (1000032, 'giftPosted', 'gift.payment', 'Payment Information', 2, 'ONE_COLUMN', 'ROLE_USER');

INSERT INTO SECTION_DEFINITION (SECTION_DEFINITION_ID, PAGE_TYPE, SECTION_NAME, DEFAULT_LABEL, SECTION_ORDER, LAYOUT_TYPE, ROLE) VALUES (1000005, 'giftPosted', 'gift.creditCard', 'Credit Card', 3, 'ONE_COLUMN_HIDDEN', 'ROLE_USER');
INSERT INTO SECTION_DEFINITION (SECTION_DEFINITION_ID, PAGE_TYPE, SECTION_NAME, DEFAULT_LABEL, SECTION_ORDER, LAYOUT_TYPE, ROLE) VALUES (1000006, 'giftPosted', 'gift.ach', 'ACH', 4, 'ONE_COLUMN_HIDDEN', 'ROLE_USER');
INSERT INTO SECTION_DEFINITION (SECTION_DEFINITION_ID, PAGE_TYPE, SECTION_NAME, DEFAULT_LABEL, SECTION_ORDER, LAYOUT_TYPE, ROLE) VALUES (1000007, 'giftPosted', 'gift.check', 'Check', 5, 'ONE_COLUMN_HIDDEN', 'ROLE_USER');
INSERT INTO SECTION_DEFINITION (SECTION_DEFINITION_ID, PAGE_TYPE, SECTION_NAME, DEFAULT_LABEL, SECTION_ORDER, LAYOUT_TYPE, ROLE) VALUES (1000021, 'giftPosted', 'gift.other', 'Other', 6, 'ONE_COLUMN_HIDDEN', 'ROLE_USER');

INSERT INTO SECTION_DEFINITION (SECTION_DEFINITION_ID, PAGE_TYPE, SECTION_NAME, DEFAULT_LABEL, SECTION_ORDER, LAYOUT_TYPE, ROLE) VALUES (1000099, 'giftPosted', 'gift.paymentStatus', 'Payment Status', 7, 'TWO_COLUMN', 'ROLE_USER');
INSERT INTO SECTION_DEFINITION (SECTION_DEFINITION_ID, PAGE_TYPE, SECTION_NAME, DEFAULT_LABEL, SECTION_ORDER, LAYOUT_TYPE, ROLE) VALUES (1000026, 'giftPosted', 'gift.distribution', 'Gift Distribution', 8, 'DISTRIBUTION_LINE_GRID_DISPLAY', 'ROLE_USER');
INSERT INTO SECTION_DEFINITION (SECTION_DEFINITION_ID, PAGE_TYPE, SECTION_NAME, DEFAULT_LABEL, SECTION_ORDER, LAYOUT_TYPE, ROLE) VALUES (1000141, 'giftPosted', 'gift.extendedDistribution', 'Extended Distribution Lines', 9, 'GRID_HIDDEN_ROW', 'ROLE_USER');

-- Donation
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (1000004, 'gift.idHidden', 1);
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (1000004, 'gift.amountReadOnly', 1000);
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (1000004, 'gift.adjustedAmountReadOnly', 1500);
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (1000004, 'gift.deductibleAmountReadOnly', 1700);
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (1000004, 'gift.currencyCodeReadOnly', 2000);
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (1000004, 'gift.giftStatusReadOnly', 2500);
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (1000004, 'gift.customFieldMap[bank]', 2600);
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (1000004, 'gift.posted', 2700);
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (1000004, 'gift.postedDateReadOnly', 2900);
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (1000004, 'gift.donationDateReadOnly', 3000);
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (1000004, 'gift.postmarkDateReadOnly', 4000);
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (1000004, 'gift.customFieldMap[sourceReadOnly]', 4300);
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (1000004, 'gift.associatedPledgeIdsReadOnly', 4500);
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (1000004, 'gift.associatedRecurringGiftIdsReadOnly', 5500);
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (1000004, 'gift.adjustedGiftsReadOnly', 5750);
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (1000004, 'gift.comments', 6000);

-- Payment
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (1000032, 'gift.paymentTypeReadOnly', 1000);
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (1000032, 'gift.address.customFieldMap[addressTypeReadOnly]', 'address.customFieldMap[addressTypeReadOnly]', 1500);
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (1000032, 'gift.address.addressLine1ReadOnly', 'address.addressLine1ReadOnly', 2000);
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (1000032, 'gift.address.addressLine2ReadOnly', 'address.addressLine2ReadOnly', 3000);
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (1000032, 'gift.address.addressLine3ReadOnly', 'address.addressLine3ReadOnly', 4000);
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (1000032, 'gift.address.cityReadOnly', 'address.cityReadOnly', 5000);
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (1000032, 'gift.address.stateProvinceReadOnly', 'address.stateProvinceReadOnly', 6000);
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (1000032, 'gift.address.postalCodeReadOnly', 'address.postalCodeReadOnly', 7000);
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (1000032, 'gift.address.countryReadOnly', 'address.countryReadOnly', 8000);
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (1000032, 'gift.phone.customFieldMap[phoneTypeReadOnly]', 'phone.customFieldMap[phoneTypeReadOnly]', 8500);
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (1000032, 'gift.phone.numberReadOnly', 'phone.numberReadOnly', 9000);
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (1000032, 'gift.customFieldMap[additionalInfoReadOnly]', 10000);

-- Credit Card
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (1000005, 'gift.paymentSource.creditCardHolderNameReadOnly', 'paymentSource.creditCardHolderNameReadOnly', 1000);
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (1000005, 'gift.paymentSource.creditCardTypeReadOnly', 'paymentSource.creditCardTypeReadOnly', 2000);
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (1000005, 'gift.paymentSource.creditCardNumberReadOnly', 'paymentSource.creditCardNumberReadOnly', 3000);
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (1000005, 'gift.paymentSource.creditCardExpirationDisplay', 'paymentSource.creditCardExpirationDisplay', 4000);

-- ACH
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (1000006, 'gift.paymentSource.achHolderNameReadOnly', 'paymentSource.achHolderNameReadOnly', 1000);
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (1000006, 'gift.paymentSource.achRoutingNumberReadOnly', 'paymentSource.achRoutingNumberReadOnly', 2000);
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (1000006, 'gift.paymentSource.achAccountNumberReadOnly', 'paymentSource.achAccountNumberReadOnly', 3000);

-- Check
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (1000007, 'gift.checkNumberReadOnly', 1000);
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (1000007, 'gift.checkDateReadOnly', 2000);
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (1000007, 'gift.paymentSource.checkHolderNameReadOnly', 'paymentSource.checkHolderNameReadOnly', 3000);
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (1000007, 'gift.paymentSource.checkRoutingNumberReadOnly', 'paymentSource.checkRoutingNumberReadOnly', 4000);
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (1000007, 'gift.paymentSource.checkAccountNumberReadOnly', 'paymentSource.checkAccountNumberReadOnly', 5000);

-- Other
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (1000021, 'gift.customFieldMap[OtherPayMethodReadOnly]', 1);

-- Payment Status
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (1000099, 'gift.paymentStatusReadOnly', 1000);
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (1000099, 'gift.paymentMessageReadOnly', 2000);
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (1000099, 'gift.txRefNumReadOnly', 3000);
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (1000099, 'gift.authCodeReadOnly', 4000);
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (1000099, 'gift.avsMessageReadOnly', 5000);
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (1000099, 'gift.customFieldMap[transactionDateReadOnly]', 6000);

-- Distribution
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (1000026, 'gift.distributionLines.amountReadOnly', 'distributionLines.amountReadOnly', 1000);
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (1000026, 'gift.distributionLines.percentageReadOnly', 'distributionLines.percentageReadOnly', 2000);
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (1000026, 'gift.distributionLines.projectCodeReadOnly', 'distributionLines.projectCodeReadOnly', 3000);
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (1000026, 'gift.distributionLines.motivationCodeReadOnly', 'distributionLines.motivationCodeReadOnly', 4000);
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (1000026, 'gift.distributionLines.other_motivationCodeReadOnly', 'distributionLines.other_motivationCodeReadOnly', 5000);
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (1000026, 'gift.distributionLines.customFieldMap[referenceReadOnly]', 'distributionLines.customFieldMap[referenceReadOnly]', 6000);
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (1000026, 'gift.distributionLines.customFieldMap[other_referenceReadOnly]', 'distributionLines.customFieldMap[other_referenceReadOnly]', 7000);

INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (1000141, 'gift.distributionLines.customFieldMap[associatedPledgeIdReadOnly]', 'distributionLines.customFieldMap[associatedPledgeIdReadOnly]', 1);
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (1000141, 'gift.distributionLines.customFieldMap[associatedRecurringGiftIdReadOnly]', 'distributionLines.customFieldMap[associatedRecurringGiftIdReadOnly]', 500);
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (1000141, 'gift.distributionLines.customFieldMap[tributeReadOnly]', 'distributionLines.customFieldMap[tributeReadOnly]', 1000);
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (1000141, 'gift.distributionLines.customFieldMap[tributeReferenceReadOnly]', 'distributionLines.customFieldMap[tributeReferenceReadOnly]', 2000);
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (1000141, 'gift.distributionLines.customFieldMap[other_tributeReferenceReadOnly]', 'distributionLines.customFieldMap[other_tributeReferenceReadOnly]', 3000);
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (1000141, 'gift.distributionLines.customFieldMap[tributeOccasionReadOnly]', 'distributionLines.customFieldMap[tributeOccasionReadOnly]', 4000);
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (1000141, 'gift.distributionLines.customFieldMap[additional_tributeOccasionReadOnly]', 'distributionLines.customFieldMap[additional_tributeOccasionReadOnly]', 5000);
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (1000141, 'gift.distributionLines.customFieldMap[onBehalfOfReadOnly]', 'distributionLines.customFieldMap[onBehalfOfReadOnly]', 6000);
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (1000141, 'gift.distributionLines.customFieldMap[other_onBehalfOfReadOnly]', 'distributionLines.customFieldMap[other_onBehalfOfReadOnly]', 7000);
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (1000141, 'gift.distributionLines.customFieldMap[anonymousReadOnly]', 'distributionLines.customFieldMap[anonymousReadOnly]', 8000);
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (1000141, 'gift.distributionLines.customFieldMap[recognitionNameReadOnly]', 'distributionLines.customFieldMap[recognitionNameReadOnly]', 9000);
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (1000141, 'gift.distributionLines.customFieldMap[notifiedReadOnly]', 'distributionLines.customFieldMap[notifiedReadOnly]', 10000);
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (1000141, 'gift.distributionLines.customFieldMap[other_notifiedReadOnly]', 'distributionLines.customFieldMap[other_notifiedReadOnly]', 11000);
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (1000141, 'gift.distributionLines.customFieldMap[messageReadOnly]', 'distributionLines.customFieldMap[messageReadOnly]', 12000);
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (1000141, 'gift.distributionLines.customFieldMap[taxDeductibleReadOnly]', 'distributionLines.customFieldMap[taxDeductibleReadOnly]', 13000);


