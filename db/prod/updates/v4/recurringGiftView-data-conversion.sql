UPDATE SECTION_DEFINITION SET LAYOUT_TYPE = 'DISTRIBUTION_LINE_GRID' WHERE PAGE_TYPE = 'recurringGiftView' and SECTION_NAME = 'recurringGift.distribution';

UPDATE SECTION_FIELD set FIELD_DEFINITION_ID = 'recurringGift.endDate' WHERE FIELD_DEFINITION_ID = 'recurringGift.endDateReadOnly'
and SECTION_DEFINITION_ID = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGiftView.info' AND PAGE_TYPE='recurringGiftView');



UPDATE SECTION_FIELD set FIELD_DEFINITION_ID = 'recurringGift.paymentType' WHERE FIELD_DEFINITION_ID = 'recurringGift.paymentTypeReadOnly'
and SECTION_DEFINITION_ID = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGift.payment' AND PAGE_TYPE='recurringGiftView');

INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, FIELD_ORDER)
SELECT SECTION_DEFINITION_ID, 'recurringGift.paymentSource.id', 'paymentSource.id', 12500 FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGift.payment' AND PAGE_TYPE='recurringGiftView';

INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, FIELD_ORDER)
SELECT SECTION_DEFINITION_ID, 'recurringGift.address.id', 'address.id', 13000 FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGift.payment' AND PAGE_TYPE='recurringGiftView';

UPDATE SECTION_FIELD set FIELD_DEFINITION_ID = 'recurringGift.address.customFieldMap[addressType]', SECONDARY_FIELD_DEFINITION_ID='address.customFieldMap[addressType]' 
WHERE FIELD_DEFINITION_ID = 'recurringGift.address.customFieldMap[addressTypeReadOnly]' and SECONDARY_FIELD_DEFINITION_ID='address.customFieldMap[addressTypeReadOnly]'
and SECTION_DEFINITION_ID = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGift.payment' AND PAGE_TYPE='recurringGiftView');

UPDATE SECTION_FIELD set FIELD_DEFINITION_ID = 'recurringGift.address.addressLine1', SECONDARY_FIELD_DEFINITION_ID='address.addressLine1'
WHERE FIELD_DEFINITION_ID = 'recurringGift.address.addressLine1ReadOnly' and SECONDARY_FIELD_DEFINITION_ID='address.addressLine1ReadOnly'
and SECTION_DEFINITION_ID = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGift.payment' AND PAGE_TYPE='recurringGiftView');

UPDATE SECTION_FIELD set FIELD_DEFINITION_ID = 'recurringGift.address.addressLine2', SECONDARY_FIELD_DEFINITION_ID='address.addressLine2'
WHERE FIELD_DEFINITION_ID = 'recurringGift.address.addressLine2ReadOnly' and SECONDARY_FIELD_DEFINITION_ID='address.addressLine2ReadOnly'
and SECTION_DEFINITION_ID = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGift.payment' AND PAGE_TYPE='recurringGiftView');

UPDATE SECTION_FIELD set FIELD_DEFINITION_ID = 'recurringGift.address.addressLine3', SECONDARY_FIELD_DEFINITION_ID='address.addressLine3'
WHERE FIELD_DEFINITION_ID = 'recurringGift.address.addressLine3ReadOnly' and SECONDARY_FIELD_DEFINITION_ID='address.addressLine3ReadOnly'
and SECTION_DEFINITION_ID = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGift.payment' AND PAGE_TYPE='recurringGiftView');

UPDATE SECTION_FIELD set FIELD_DEFINITION_ID = 'recurringGift.address.city', SECONDARY_FIELD_DEFINITION_ID='address.city'
WHERE FIELD_DEFINITION_ID = 'recurringGift.address.cityReadOnly' and SECONDARY_FIELD_DEFINITION_ID='address.cityReadOnly'
and SECTION_DEFINITION_ID = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGift.payment' AND PAGE_TYPE='recurringGiftView');

UPDATE SECTION_FIELD set FIELD_DEFINITION_ID = 'recurringGift.address.stateProvince', SECONDARY_FIELD_DEFINITION_ID='address.stateProvince'
WHERE FIELD_DEFINITION_ID = 'recurringGift.address.stateProvinceReadOnly' and SECONDARY_FIELD_DEFINITION_ID='address.stateProvinceReadOnly'
and SECTION_DEFINITION_ID = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGift.payment' AND PAGE_TYPE='recurringGiftView');

UPDATE SECTION_FIELD set FIELD_DEFINITION_ID = 'recurringGift.address.postalCode', SECONDARY_FIELD_DEFINITION_ID='address.postalCode'
WHERE FIELD_DEFINITION_ID = 'recurringGift.address.postalCodeReadOnly' and SECONDARY_FIELD_DEFINITION_ID='address.postalCodeReadOnly'
and SECTION_DEFINITION_ID = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGift.payment' AND PAGE_TYPE='recurringGiftView');

UPDATE SECTION_FIELD set FIELD_DEFINITION_ID = 'recurringGift.address.country', SECONDARY_FIELD_DEFINITION_ID='address.country'
WHERE FIELD_DEFINITION_ID = 'recurringGift.address.countryReadOnly' and SECONDARY_FIELD_DEFINITION_ID='address.countryReadOnly'
and SECTION_DEFINITION_ID = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGift.payment' AND PAGE_TYPE='recurringGiftView');

INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, FIELD_ORDER)
SELECT SECTION_DEFINITION_ID, 'recurringGift.phone.id', 'phone.id', 20500 FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGift.payment' AND PAGE_TYPE='recurringGiftView';

UPDATE SECTION_FIELD set FIELD_DEFINITION_ID = 'recurringGift.phone.customFieldMap[phoneType]', SECONDARY_FIELD_DEFINITION_ID='phone.customFieldMap[phoneType]'
WHERE FIELD_DEFINITION_ID = 'recurringGift.phone.customFieldMap[phoneTypeReadOnly]' and SECONDARY_FIELD_DEFINITION_ID='phone.customFieldMap[phoneTypeReadOnly]'
and SECTION_DEFINITION_ID = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGift.payment' AND PAGE_TYPE='recurringGiftView');

UPDATE SECTION_FIELD set FIELD_DEFINITION_ID = 'recurringGift.phone.number', SECONDARY_FIELD_DEFINITION_ID='phone.number'
WHERE FIELD_DEFINITION_ID = 'recurringGift.phone.numberReadOnly' and SECONDARY_FIELD_DEFINITION_ID='phone.numberReadOnly'
and SECTION_DEFINITION_ID = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGift.payment' AND PAGE_TYPE='recurringGiftView');

UPDATE SECTION_FIELD set FIELD_ORDER = 5000
WHERE FIELD_DEFINITION_ID = 'recurringGift.paymentSource.creditCardHolderNameReadOnly' and SECONDARY_FIELD_DEFINITION_ID='paymentSource.creditCardHolderNameReadOnly'
and SECTION_DEFINITION_ID = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGift.creditCard' AND PAGE_TYPE='recurringGiftView');

UPDATE SECTION_FIELD set FIELD_ORDER = 6000
WHERE FIELD_DEFINITION_ID = 'recurringGift.paymentSource.creditCardTypeReadOnly' and SECONDARY_FIELD_DEFINITION_ID='paymentSource.creditCardTypeReadOnly'
and SECTION_DEFINITION_ID = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGift.creditCard' AND PAGE_TYPE='recurringGiftView');

UPDATE SECTION_FIELD set FIELD_ORDER = 7000
WHERE FIELD_DEFINITION_ID = 'recurringGift.paymentSource.creditCardNumberReadOnly' and SECONDARY_FIELD_DEFINITION_ID='paymentSource.creditCardNumberReadOnly'
and SECTION_DEFINITION_ID = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGift.creditCard' AND PAGE_TYPE='recurringGiftView');

UPDATE SECTION_FIELD set FIELD_ORDER = 8000
WHERE FIELD_DEFINITION_ID = 'recurringGift.paymentSource.creditCardExpirationDisplay' and SECONDARY_FIELD_DEFINITION_ID='paymentSource.creditCardExpirationDisplay'
and SECTION_DEFINITION_ID = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGift.creditCard' AND PAGE_TYPE='recurringGiftView');

INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, FIELD_ORDER)
SELECT SECTION_DEFINITION_ID, 'recurringGift.paymentSource.creditCardHolderName', 'paymentSource.creditCardHolderName', 1000 FROM SECTION_DEFINITION
WHERE SECTION_NAME='recurringGift.creditCard' AND PAGE_TYPE='recurringGiftView';

INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, FIELD_ORDER)
SELECT SECTION_DEFINITION_ID, 'recurringGift.paymentSource.creditCardType', 'paymentSource.creditCardType', 2000 FROM SECTION_DEFINITION
WHERE SECTION_NAME='recurringGift.creditCard' AND PAGE_TYPE='recurringGiftView';

INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, FIELD_ORDER)
SELECT SECTION_DEFINITION_ID, 'recurringGift.paymentSource.creditCardNumber', 'paymentSource.creditCardNumber', 3000 FROM SECTION_DEFINITION
WHERE SECTION_NAME='recurringGift.creditCard' AND PAGE_TYPE='recurringGiftView';

INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, FIELD_ORDER)
SELECT SECTION_DEFINITION_ID, 'recurringGift.paymentSource.creditCardExpiration', 'paymentSource.creditCardExpiration', 4000 FROM SECTION_DEFINITION
WHERE SECTION_NAME='recurringGift.creditCard' AND PAGE_TYPE='recurringGiftView';

UPDATE SECTION_FIELD set FIELD_ORDER = 4000
WHERE FIELD_DEFINITION_ID = 'recurringGift.paymentSource.achHolderNameReadOnly' and SECONDARY_FIELD_DEFINITION_ID='paymentSource.achHolderNameReadOnly'
and SECTION_DEFINITION_ID = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGift.ach' AND PAGE_TYPE='recurringGiftView');

UPDATE SECTION_FIELD set FIELD_ORDER = 5000
WHERE FIELD_DEFINITION_ID = 'recurringGift.paymentSource.achRoutingNumberReadOnly' and SECONDARY_FIELD_DEFINITION_ID='paymentSource.achRoutingNumberReadOnly'
and SECTION_DEFINITION_ID = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGift.ach' AND PAGE_TYPE='recurringGiftView');

UPDATE SECTION_FIELD set FIELD_ORDER = 6000
WHERE FIELD_DEFINITION_ID = 'recurringGift.paymentSource.achAccountNumberReadOnly' and SECONDARY_FIELD_DEFINITION_ID='paymentSource.achAccountNumberReadOnly'
and SECTION_DEFINITION_ID = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGift.ach' AND PAGE_TYPE='recurringGiftView');

INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, FIELD_ORDER)
SELECT SECTION_DEFINITION_ID, 'recurringGift.paymentSource.achHolderName', 'paymentSource.achHolderName', 1000 FROM SECTION_DEFINITION
WHERE SECTION_NAME='recurringGift.ach' AND PAGE_TYPE='recurringGiftView';

INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, FIELD_ORDER)
SELECT SECTION_DEFINITION_ID, 'recurringGift.paymentSource.achRoutingNumber', 'paymentSource.achRoutingNumber', 2000 FROM SECTION_DEFINITION
WHERE SECTION_NAME='recurringGift.ach' AND PAGE_TYPE='recurringGiftView';

INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, FIELD_ORDER)
SELECT SECTION_DEFINITION_ID, 'recurringGift.paymentSource.achAccountNumber', 'paymentSource.achAccountNumber', 3000 FROM SECTION_DEFINITION
WHERE SECTION_NAME='recurringGift.ach' AND PAGE_TYPE='recurringGiftView';




UPDATE SECTION_FIELD set FIELD_DEFINITION_ID = 'recurringGift.distributionLines.amount', SECONDARY_FIELD_DEFINITION_ID='distributionLines.amount'
WHERE FIELD_DEFINITION_ID = 'recurringGift.distributionLines.amountReadOnly' and SECONDARY_FIELD_DEFINITION_ID='distributionLines.amountReadOnly'
and SECTION_DEFINITION_ID = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGift.distribution' AND PAGE_TYPE='recurringGiftView');

UPDATE SECTION_FIELD set FIELD_DEFINITION_ID = 'recurringGift.distributionLines.percentage', SECONDARY_FIELD_DEFINITION_ID='distributionLines.percentage'
WHERE FIELD_DEFINITION_ID = 'recurringGift.distributionLines.percentageReadOnly' and SECONDARY_FIELD_DEFINITION_ID='distributionLines.percentageReadOnly'
and SECTION_DEFINITION_ID = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGift.distribution' AND PAGE_TYPE='recurringGiftView');

UPDATE SECTION_FIELD set FIELD_DEFINITION_ID = 'recurringGift.distributionLines.projectCode', SECONDARY_FIELD_DEFINITION_ID='distributionLines.projectCode'
WHERE FIELD_DEFINITION_ID = 'recurringGift.distributionLines.projectCodeReadOnly' and SECONDARY_FIELD_DEFINITION_ID='distributionLines.projectCodeReadOnly'
and SECTION_DEFINITION_ID = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGift.distribution' AND PAGE_TYPE='recurringGiftView');

UPDATE SECTION_FIELD set FIELD_DEFINITION_ID = 'recurringGift.distributionLines.motivationCode', SECONDARY_FIELD_DEFINITION_ID='distributionLines.motivationCode'
WHERE FIELD_DEFINITION_ID = 'recurringGift.distributionLines.motivationCodeReadOnly' and SECONDARY_FIELD_DEFINITION_ID='distributionLines.motivationCodeReadOnly'
and SECTION_DEFINITION_ID = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGift.distribution' AND PAGE_TYPE='recurringGiftView');

UPDATE SECTION_FIELD set FIELD_DEFINITION_ID = 'recurringGift.distributionLines.other_motivationCode', SECONDARY_FIELD_DEFINITION_ID='distributionLines.other_motivationCode'
WHERE FIELD_DEFINITION_ID = 'recurringGift.distributionLines.other_motivationCodeReadOnly' and SECONDARY_FIELD_DEFINITION_ID='distributionLines.other_motivationCodeReadOnly'
and SECTION_DEFINITION_ID = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGift.distribution' AND PAGE_TYPE='recurringGiftView');

UPDATE SECTION_FIELD set FIELD_DEFINITION_ID = 'recurringGift.distributionLines.customFieldMap[reference]', SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[reference]'
WHERE FIELD_DEFINITION_ID = 'recurringGift.distributionLines.customFieldMap[referenceReadOnly]' and SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[referenceReadOnly]'
and SECTION_DEFINITION_ID = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGift.distribution' AND PAGE_TYPE='recurringGiftView');

UPDATE SECTION_FIELD set FIELD_DEFINITION_ID = 'recurringGift.distributionLines.customFieldMap[other_reference]', SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[other_reference]'
WHERE FIELD_DEFINITION_ID = 'recurringGift.distributionLines.customFieldMap[other_referenceReadOnly]' and SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[other_referenceReadOnly]'
and SECTION_DEFINITION_ID = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGift.distribution' AND PAGE_TYPE='recurringGiftView');



UPDATE SECTION_FIELD set FIELD_DEFINITION_ID = 'recurringGift.distributionLines.customFieldMap[tribute]', SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[tribute]'
WHERE FIELD_DEFINITION_ID = 'recurringGift.distributionLines.customFieldMap[tributeReadOnly]' and SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[tributeReadOnly]'
and SECTION_DEFINITION_ID = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGift.extendedDistribution' AND PAGE_TYPE='recurringGiftView');

UPDATE SECTION_FIELD set FIELD_DEFINITION_ID = 'recurringGift.distributionLines.customFieldMap[tributeReference]', SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[tributeReference]'
WHERE FIELD_DEFINITION_ID = 'recurringGift.distributionLines.customFieldMap[tributeReferenceReadOnly]' and SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[tributeReferenceReadOnly]'
and SECTION_DEFINITION_ID = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGift.extendedDistribution' AND PAGE_TYPE='recurringGiftView');

UPDATE SECTION_FIELD set FIELD_DEFINITION_ID = 'recurringGift.distributionLines.customFieldMap[other_tributeReference]', SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[other_tributeReference]'
WHERE FIELD_DEFINITION_ID = 'recurringGift.distributionLines.customFieldMap[other_tributeReferenceReadOnly]' and SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[other_tributeReferenceReadOnly]'
and SECTION_DEFINITION_ID = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGift.extendedDistribution' AND PAGE_TYPE='recurringGiftView');

UPDATE SECTION_FIELD set FIELD_DEFINITION_ID = 'recurringGift.distributionLines.customFieldMap[tributeOccasion]', SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[tributeOccasion]'
WHERE FIELD_DEFINITION_ID = 'recurringGift.distributionLines.customFieldMap[tributeOccasionReadOnly]' and SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[tributeOccasionReadOnly]'
and SECTION_DEFINITION_ID = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGift.extendedDistribution' AND PAGE_TYPE='recurringGiftView');

UPDATE SECTION_FIELD set FIELD_DEFINITION_ID = 'recurringGift.distributionLines.customFieldMap[additional_tributeOccasion]', SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[additional_tributeOccasion]'
WHERE FIELD_DEFINITION_ID = 'recurringGift.distributionLines.customFieldMap[additional_tributeOccasionReadOnly]' and SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[additional_tributeOccasionReadOnly]'
and SECTION_DEFINITION_ID = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGift.extendedDistribution' AND PAGE_TYPE='recurringGiftView');

UPDATE SECTION_FIELD set FIELD_DEFINITION_ID = 'recurringGift.distributionLines.customFieldMap[onBehalfOf]', SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[onBehalfOf]'
WHERE FIELD_DEFINITION_ID = 'recurringGift.distributionLines.customFieldMap[onBehalfOfReadOnly]' and SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[onBehalfOfReadOnly]'
and SECTION_DEFINITION_ID = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGift.extendedDistribution' AND PAGE_TYPE='recurringGiftView');

UPDATE SECTION_FIELD set FIELD_DEFINITION_ID = 'recurringGift.distributionLines.customFieldMap[other_onBehalfOf]', SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[other_onBehalfOf]'
WHERE FIELD_DEFINITION_ID = 'recurringGift.distributionLines.customFieldMap[other_onBehalfOfReadOnly]' and SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[other_onBehalfOfReadOnly]'
and SECTION_DEFINITION_ID = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGift.extendedDistribution' AND PAGE_TYPE='recurringGiftView');

UPDATE SECTION_FIELD set FIELD_DEFINITION_ID = 'recurringGift.distributionLines.customFieldMap[anonymous]', SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[anonymous]'
WHERE FIELD_DEFINITION_ID = 'recurringGift.distributionLines.customFieldMap[anonymousReadOnly]' and SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[anonymousReadOnly]'
and SECTION_DEFINITION_ID = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGift.extendedDistribution' AND PAGE_TYPE='recurringGiftView');

UPDATE SECTION_FIELD set FIELD_DEFINITION_ID = 'recurringGift.distributionLines.customFieldMap[recognitionName]', SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[recognitionName]'
WHERE FIELD_DEFINITION_ID = 'recurringGift.distributionLines.customFieldMap[recognitionNameReadOnly]' and SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[recognitionNameReadOnly]'
and SECTION_DEFINITION_ID = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGift.extendedDistribution' AND PAGE_TYPE='recurringGiftView');

UPDATE SECTION_FIELD set FIELD_DEFINITION_ID = 'recurringGift.distributionLines.customFieldMap[notified]', SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[notified]'
WHERE FIELD_DEFINITION_ID = 'recurringGift.distributionLines.customFieldMap[notifiedReadOnly]' and SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[notifiedReadOnly]'
and SECTION_DEFINITION_ID = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGift.extendedDistribution' AND PAGE_TYPE='recurringGiftView');

UPDATE SECTION_FIELD set FIELD_DEFINITION_ID = 'recurringGift.distributionLines.customFieldMap[other_notified]', SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[other_notified]'
WHERE FIELD_DEFINITION_ID = 'recurringGift.distributionLines.customFieldMap[other_notifiedReadOnly]' and SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[other_notifiedReadOnly]'
and SECTION_DEFINITION_ID = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGift.extendedDistribution' AND PAGE_TYPE='recurringGiftView');

UPDATE SECTION_FIELD set FIELD_DEFINITION_ID = 'recurringGift.distributionLines.customFieldMap[message]', SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[message]'
WHERE FIELD_DEFINITION_ID = 'recurringGift.distributionLines.customFieldMap[messageReadOnly]' and SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[messageReadOnly]'
and SECTION_DEFINITION_ID = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGift.extendedDistribution' AND PAGE_TYPE='recurringGiftView');

UPDATE SECTION_FIELD set FIELD_DEFINITION_ID = 'recurringGift.distributionLines.customFieldMap[taxDeductible]', SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[taxDeductible]'
WHERE FIELD_DEFINITION_ID = 'recurringGift.distributionLines.customFieldMap[taxDeductibleReadOnly]' and SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[taxDeductibleReadOnly]'
and SECTION_DEFINITION_ID = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGift.extendedDistribution' AND PAGE_TYPE='recurringGiftView');
