/*
SQLyog Job Agent Version 8.14 n2 Copyright(c) Webyog Softworks Pvt. Ltd. All Rights Reserved.

MySQL - 5.1.35-community
*********************************************************************
*/
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

/* SYNC DB : company1 */
SET AUTOCOMMIT = 0;
/* SYNC TABLE : `FIELD_DEFINITION` */

	/*Start of batch : 1 */
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('distributionLines.amount', 'distributionLine', 'amount', 'Amt', 'NUMBER');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('distributionLines.percentage', 'distributionLine', 'percentage', 'Pct', 'PERCENTAGE');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('distributionLines.projectCode', 'distributionLine', 'projectCode', 'Designation', 'CODE');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('distributionLines.motivationCode', 'distributionLine', 'motivationCode', 'Motivation', 'CODE_OTHER');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('distributionLines.other_motivationCode', 'distributionLine', 'other_motivationCode', 'Motivation', 'HIDDEN');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('distributionLines.customFieldMap[reference]', 'distributionLine', 'constituent', 'customFieldMap[reference]', 'Reference', 'QUERY_LOOKUP_OTHER');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('distributionLines.customFieldMap[other_reference]', 'distributionLine', 'constituent', 'customFieldMap[other_reference]', 'Reference', 'HIDDEN');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('distributionLines.customFieldMap[tribute]', 'distributionLine', 'customFieldMap[tribute]', 'Tribute Type', 'PICKLIST');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('distributionLines.customFieldMap[tributeReference]', 'distributionLine', 'constituent', 'customFieldMap[tributeReference]', 'Tribute Reference', 'QUERY_LOOKUP_OTHER');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('distributionLines.customFieldMap[other_tributeReference]', 'distributionLine', 'constituent', 'customFieldMap[other_tributeReference]', 'Tribute Reference', 'HIDDEN');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('distributionLines.customFieldMap[tributeOccasion]', 'distributionLine', 'customFieldMap[tributeOccasion]', 'Tribute Occasion', 'MULTI_PICKLIST_ADDITIONAL');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('distributionLines.customFieldMap[additional_tributeOccasion]', 'distributionLine', 'customFieldMap[additional_tributeOccasion]', 'Tribute Occasion', 'HIDDEN');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('distributionLines.customFieldMap[onBehalfOf]', 'distributionLine', 'constituent', 'customFieldMap[onBehalfOf]', 'On Behalf Of', 'QUERY_LOOKUP_OTHER');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('distributionLines.customFieldMap[other_onBehalfOf]', 'distributionLine', 'constituent', 'customFieldMap[other_onBehalfOf]', 'On Behalf Of', 'HIDDEN');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('distributionLines.customFieldMap[anonymous]', 'distributionLine', 'customFieldMap[anonymous]', 'Anonymous', 'CHECKBOX');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('distributionLines.customFieldMap[recognitionName]', 'distributionLine', 'customFieldMap[recognitionName]', 'Recognition Name', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('distributionLines.customFieldMap[notified]', 'distributionLine', 'constituent', 'customFieldMap[notified]', 'To Be Notified', 'QUERY_LOOKUP_OTHER');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('distributionLines.customFieldMap[other_notified]', 'distributionLine', 'constituent', 'customFieldMap[other_notified]', 'To Be Notified', 'HIDDEN');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('distributionLines.customFieldMap[message]', 'distributionLine', 'customFieldMap[message]', 'Message', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('distributionLines.customFieldMap[taxDeductible]', 'distributionLine', 'customFieldMap[taxDeductible]', 'Tax Ded', 'CHECKBOX');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('distributionLines.customFieldMap[associatedPledgeId]', 'distributionLine', 'pledge', 'customFieldMap[associatedPledgeId]', 'Associated Pledge', 'pledge', 'ASSOCIATION');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('distributionLines.customFieldMap[associatedRecurringGiftId]', 'distributionLine', 'recurringGift', 'customFieldMap[associatedRecurringGiftId]', 'Associated Recurring Gift', 'recurringGift', 'ASSOCIATION');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('distributionLines.amountReadOnly', 'distributionLine', 'amount', 'Amt', 'NUMBER_DISPLAY');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('distributionLines.percentageReadOnly', 'distributionLine', 'percentage', 'Pct', 'PERCENTAGE_DISPLAY');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('distributionLines.projectCodeReadOnly', 'distributionLine', 'projectCode', 'Designation', 'CODE_OTHER_DISPLAY');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('distributionLines.motivationCodeReadOnly', 'distributionLine', 'motivationCode', 'Motivation', 'CODE_OTHER_DISPLAY');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('distributionLines.other_motivationCodeReadOnly', 'distributionLine', 'other_motivationCode', 'Motivation', 'HIDDEN');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('distributionLines.customFieldMap[referenceReadOnly]', 'distributionLine', 'constituent', 'customFieldMap[reference]', 'Reference', 'QUERY_LOOKUP_DISPLAY');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('distributionLines.customFieldMap[other_referenceReadOnly]', 'distributionLine', 'constituent', 'customFieldMap[other_reference]', 'Reference', 'HIDDEN');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('distributionLines.customFieldMap[tributeReadOnly]', 'distributionLine', 'customFieldMap[tribute]', 'Tribute Type', 'PICKLIST_DISPLAY');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('distributionLines.customFieldMap[tributeReferenceReadOnly]', 'distributionLine', 'constituent', 'customFieldMap[tributeReference]', 'Tribute Reference', 'QUERY_LOOKUP_DISPLAY');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('distributionLines.customFieldMap[other_tributeReferenceReadOnly]', 'distributionLine', 'constituent', 'customFieldMap[other_tributeReference]', 'Tribute Reference', 'HIDDEN');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('distributionLines.customFieldMap[tributeOccasionReadOnly]', 'distributionLine', 'customFieldMap[tributeOccasion]', 'Tribute Occasion', 'MULTI_PICKLIST_ADDITIONAL_DISPLAY');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('distributionLines.customFieldMap[additional_tributeOccasionReadOnly]', 'distributionLine', 'customFieldMap[additional_tributeOccasion]', 'Tribute Occasion', 'HIDDEN');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('distributionLines.customFieldMap[onBehalfOfReadOnly]', 'distributionLine', 'constituent', 'customFieldMap[onBehalfOf]', 'On Behalf Of', 'QUERY_LOOKUP_DISPLAY');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('distributionLines.customFieldMap[other_onBehalfOfReadOnly]', 'distributionLine', 'constituent', 'customFieldMap[other_onBehalfOf]', 'On Behalf Of', 'HIDDEN');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('distributionLines.customFieldMap[anonymousReadOnly]', 'distributionLine', 'customFieldMap[anonymous]', 'Anonymous', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('distributionLines.customFieldMap[recognitionNameReadOnly]', 'distributionLine', 'customFieldMap[recognitionName]', 'Recognition Name', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('distributionLines.customFieldMap[notifiedReadOnly]', 'distributionLine', 'constituent', 'customFieldMap[notified]', 'To Be Notified', 'QUERY_LOOKUP_DISPLAY');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('distributionLines.customFieldMap[other_notifiedReadOnly]', 'distributionLine', 'constituent', 'customFieldMap[other_notified]', 'To Be Notified', 'HIDDEN');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('distributionLines.customFieldMap[messageReadOnly]', 'distributionLine', 'customFieldMap[message]', 'Message', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('distributionLines.customFieldMap[taxDeductibleReadOnly]', 'distributionLine', 'customFieldMap[taxDeductible]', 'Tax Ded', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('distributionLines.customFieldMap[associatedPledgeIdReadOnly]', 'distributionLine', 'pledge', 'customFieldMap[associatedPledgeId]', 'Associated Pledge', 'pledge', 'ASSOCIATION_DISPLAY');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, ENTITY_ATTRIBUTES, FIELD_TYPE) VALUES ('distributionLines.customFieldMap[associatedRecurringGiftIdReadOnly]', 'distributionLine', 'recurringGift', 'customFieldMap[associatedRecurringGiftId]', 'Associated Recurring Gift', 'recurringGift', 'ASSOCIATION_DISPLAY');



INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('details.description', 'giftInKindDetail', 'description', 'Description', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('details.detailFairMarketValue', 'giftInKindDetail', 'detailFairMarketValue', 'FMV', 'NUMBER');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('details.projectCode', 'giftInKindDetail', 'projectCode', 'Designation', 'CODE');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('details.taxDeductible', 'giftInKindDetail', 'taxDeductible', 'Tax Ded', 'CHECKBOX');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('details.fmvMethod', 'giftInKindDetail', 'fmvMethod', 'Fair Market Value Method', 'PICKLIST');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('details.gikCategory', 'giftInKindDetail', 'gikCategory', 'Category', 'PICKLIST');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('details.quantity', 'giftInKindDetail', 'quantity', 'Quantity', 'NUMBER');



INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('address.id', 'address', 'id', 'Address', 'ADDRESS_PICKLIST');

DELETE FROM FIELD_DEFINITION where FIELD_DEFINITION_ID = 'address.userCreated';

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('address.current', 'address', 'current', 'Current', 'READ_ONLY_TEXT');

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'address.customFieldMap[addressTypeReadOnly]', ENTITY_TYPE = 'address' where FIELD_DEFINITION_ID = 'selectedAddress.customFieldMap[addressTypeReadOnly]';

DELETE FROM FIELD_DEFINITION where FIELD_DEFINITION_ID = 'selectedAddress.addressLine1ReadOnly';

DELETE FROM FIELD_DEFINITION where FIELD_DEFINITION_ID = 'selectedAddress.addressLine2ReadOnly';

DELETE FROM FIELD_DEFINITION where FIELD_DEFINITION_ID = 'selectedAddress.addressLine3ReadOnly';

DELETE FROM FIELD_DEFINITION where FIELD_DEFINITION_ID = 'selectedAddress.cityReadOnly';

DELETE FROM FIELD_DEFINITION where FIELD_DEFINITION_ID = 'selectedAddress.stateProvinceReadOnly';

DELETE FROM FIELD_DEFINITION where FIELD_DEFINITION_ID = 'selectedAddress.postalCodeReadOnly';

DELETE FROM FIELD_DEFINITION where FIELD_DEFINITION_ID = 'selectedAddress.countryReadOnly';

DELETE FROM FIELD_DEFINITION where FIELD_DEFINITION_ID = 'selectedAddress.shortDisplay';

UPDATE FIELD_DEFINITION set FIELD_TYPE = 'PICKLIST_DISPLAY' where FIELD_DEFINITION_ID = 'address.countryReadOnly';




UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'adjustedGift.paymentSource.id', FIELD_NAME = 'paymentSource' where FIELD_DEFINITION_ID = 'adjustedGift.selectedPaymentSource';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'adjustedGift.paymentSource.creditCardSecurityCode', FIELD_NAME = 'paymentSource' where FIELD_DEFINITION_ID = 'adjustedGift.selectedPaymentSource.creditCardSecurityCode';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'adjustedGift.address.id', FIELD_NAME = 'address' where FIELD_DEFINITION_ID = 'adjustedGift.selectedAddress';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'adjustedGift.phone.id', FIELD_NAME = 'phone' where FIELD_DEFINITION_ID = 'adjustedGift.selectedPhone';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'adjustedGift.distributionLines.amount', FIELD_NAME = 'distributionLines' where FIELD_DEFINITION_ID = 'adjustedGift.lineAmount';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'adjustedGift.distributionLines.percentage', FIELD_NAME = 'distributionLines' where FIELD_DEFINITION_ID = 'adjustedGift.percentage';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'adjustedGift.distributionLines.projectCodeReadOnly', FIELD_NAME = 'distributionLines' where FIELD_DEFINITION_ID = 'adjustedGift.projectCodeReadOnly';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'adjustedGift.distributionLines.motivationCodeReadOnly', FIELD_NAME = 'distributionLines' where FIELD_DEFINITION_ID = 'adjustedGift.motivationCodeReadOnly';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'adjustedGift.distributionLines.other_motivationCodeReadOnly', FIELD_NAME = 'distributionLines', DEFAULT_LABEL = 'Motivation' where FIELD_DEFINITION_ID = 'adjustedGift.other_motivationCodeReadOnly';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'adjustedGift.distributionLines.customFieldMap[referenceReadOnly]', FIELD_NAME = 'distributionLines' where FIELD_DEFINITION_ID = 'adjustedGift.customFieldMap[referenceReadOnly]';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'adjustedGift.distributionLines.customFieldMap[other_referenceReadOnly]', FIELD_NAME = 'distributionLines', DEFAULT_LABEL = 'Reference' where FIELD_DEFINITION_ID = 'adjustedGift.customFieldMap[other_referenceReadOnly]';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'adjustedGift.distributionLines.customFieldMap[tributeReadOnly]', FIELD_NAME = 'distributionLines' where FIELD_DEFINITION_ID = 'adjustedGift.customFieldMap[tributeReadOnly]';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'adjustedGift.distributionLines.customFieldMap[tributeReferenceReadOnly]', FIELD_NAME = 'distributionLines' where FIELD_DEFINITION_ID = 'adjustedGift.customFieldMap[tributeReferenceReadOnly]';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'adjustedGift.distributionLines.customFieldMap[other_tributeReferenceReadOnly]', FIELD_NAME = 'distributionLines', DEFAULT_LABEL = 'Tribute Reference' where FIELD_DEFINITION_ID = 'adjustedGift.customFieldMap[other_tributeReferenceReadOnly]';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'adjustedGift.distributionLines.customFieldMap[tributeOccasionReadOnly]', FIELD_NAME = 'distributionLines' where FIELD_DEFINITION_ID = 'adjustedGift.customFieldMap[tributeOccasionReadOnly]';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'adjustedGift.distributionLines.customFieldMap[additional_tributeOccasionReadOnly]', FIELD_NAME = 'distributionLines', DEFAULT_LABEL = 'Tribute Occasion' where FIELD_DEFINITION_ID = 'adjustedGift.customFieldMap[additional_tributeOccasionReadOnly]';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'adjustedGift.distributionLines.customFieldMap[onBehalfOfReadOnly]', FIELD_NAME = 'distributionLines' where FIELD_DEFINITION_ID = 'adjustedGift.customFieldMap[onBehalfOfReadOnly]';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'adjustedGift.distributionLines.customFieldMap[other_onBehalfOfReadOnly]', FIELD_NAME = 'distributionLines', DEFAULT_LABEL = 'On Behalf Of' where FIELD_DEFINITION_ID = 'adjustedGift.customFieldMap[other_onBehalfOfReadOnly]';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'adjustedGift.distributionLines.customFieldMap[anonymousReadOnly]', FIELD_NAME = 'distributionLines' where FIELD_DEFINITION_ID = 'adjustedGift.customFieldMap[anonymousReadOnly]';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'adjustedGift.distributionLines.customFieldMap[recognitionNameReadOnly]', FIELD_NAME = 'distributionLines' where FIELD_DEFINITION_ID = 'adjustedGift.customFieldMap[recognitionNameReadOnly]';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'adjustedGift.distributionLines.customFieldMap[notifiedReadOnly]', FIELD_NAME = 'distributionLines' where FIELD_DEFINITION_ID = 'adjustedGift.customFieldMap[notifiedReadOnly]';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'adjustedGift.distributionLines.customFieldMap[other_notifiedReadOnly]', FIELD_NAME = 'distributionLines', DEFAULT_LABEL = 'To Be Notified' where FIELD_DEFINITION_ID = 'adjustedGift.customFieldMap[other_notifiedReadOnly]';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'adjustedGift.distributionLines.customFieldMap[messageReadOnly]', FIELD_NAME = 'distributionLines' where FIELD_DEFINITION_ID = 'adjustedGift.customFieldMap[messageReadOnly]';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'adjustedGift.distributionLines.customFieldMap[taxDeductibleReadOnly]', FIELD_NAME = 'distributionLines' where FIELD_DEFINITION_ID = 'adjustedGift.customFieldMap[taxDeductibleReadOnly]';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'adjustedGift.distributionLines.customFieldMap[associatedPledgeIdReadOnly]', FIELD_NAME = 'distributionLines' where FIELD_DEFINITION_ID = 'adjustedGift.customFieldMap[associatedPledgeIdReadOnly]';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'adjustedGift.distributionLines.customFieldMap[associatedRecurringGiftIdReadOnly]', FIELD_NAME = 'distributionLines' where FIELD_DEFINITION_ID = 'adjustedGift.customFieldMap[associatedRecurringGiftIdReadOnly]';

UPDATE FIELD_DEFINITION set FIELD_TYPE = 'PICKLIST_DISPLAY' where FIELD_DEFINITION_ID = 'adjustedGift.paymentTypeReadOnly';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'adjustedGift.paymentSource.creditCardHolderNameReadOnly', FIELD_NAME = 'paymentSource' where FIELD_DEFINITION_ID = 'adjustedGift.selectedPaymentSource.creditCardHolderNameReadOnly';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'adjustedGift.paymentSource.creditCardTypeReadOnly', FIELD_NAME = 'paymentSource' where FIELD_DEFINITION_ID = 'adjustedGift.selectedPaymentSource.creditCardTypeReadOnly';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'adjustedGift.paymentSource.creditCardNumberReadOnly', FIELD_NAME = 'paymentSource' where FIELD_DEFINITION_ID = 'adjustedGift.selectedPaymentSource.creditCardNumberReadOnly';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'adjustedGift.paymentSource.creditCardExpirationDisplay', FIELD_NAME = 'paymentSource' where FIELD_DEFINITION_ID = 'adjustedGift.selectedPaymentSource.creditCardExpirationDisplay';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'adjustedGift.paymentSource.achHolderNameReadOnly', FIELD_NAME = 'paymentSource' where FIELD_DEFINITION_ID = 'adjustedGift.selectedPaymentSource.achHolderNameReadOnly';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'adjustedGift.paymentSource.achRoutingNumberReadOnly', FIELD_NAME = 'paymentSource' where FIELD_DEFINITION_ID = 'adjustedGift.selectedPaymentSource.achRoutingNumberReadOnly';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'adjustedGift.paymentSource.achAccountNumberReadOnly', FIELD_NAME = 'paymentSource' where FIELD_DEFINITION_ID = 'adjustedGift.selectedPaymentSource.achAccountNumberReadOnly';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'adjustedGift.address.addressLine1ReadOnly', FIELD_NAME = 'address' where FIELD_DEFINITION_ID = 'adjustedGift.selectedAddress.addressLine1ReadOnly';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'adjustedGift.address.addressLine2ReadOnly', FIELD_NAME = 'address' where FIELD_DEFINITION_ID = 'adjustedGift.selectedAddress.addressLine2ReadOnly';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'adjustedGift.address.addressLine3ReadOnly', FIELD_NAME = 'address' where FIELD_DEFINITION_ID = 'adjustedGift.selectedAddress.addressLine3ReadOnly';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'adjustedGift.address.cityReadOnly', FIELD_NAME = 'address' where FIELD_DEFINITION_ID = 'adjustedGift.selectedAddress.cityReadOnly';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'adjustedGift.address.stateProvinceReadOnly', FIELD_NAME = 'address' where FIELD_DEFINITION_ID = 'adjustedGift.selectedAddress.stateProvinceReadOnly';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'adjustedGift.address.postalCodeReadOnly', FIELD_NAME = 'address' where FIELD_DEFINITION_ID = 'adjustedGift.selectedAddress.postalCodeReadOnly';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'adjustedGift.address.countryReadOnly', FIELD_NAME = 'address', FIELD_TYPE = 'PICKLIST_DISPLAY' where FIELD_DEFINITION_ID = 'adjustedGift.selectedAddress.countryReadOnly';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'adjustedGift.phone.numberReadOnly', FIELD_NAME = 'phone' where FIELD_DEFINITION_ID = 'adjustedGift.selectedPhone.numberReadOnly';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'adjustedGift.distributionLines.amountReadOnly', FIELD_NAME = 'distributionLines', 
DEFAULT_LABEL = 'Amt', FIELD_TYPE = 'NUMBER_DISPLAY' where FIELD_DEFINITION_ID = 'adjustedGift.lineAmountReadOnly';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'adjustedGift.distributionLines.percentageReadOnly', FIELD_NAME = 'distributionLines', 
FIELD_TYPE = 'PERCENTAGE_DISPLAY' where FIELD_DEFINITION_ID = 'adjustedGift.percentageReadOnly';




UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'communicationHistory.address.id', FIELD_NAME = 'address' where FIELD_DEFINITION_ID = 'communicationHistory.selectedAddress';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'communicationHistory.phone.id', FIELD_NAME = 'phone' where FIELD_DEFINITION_ID = 'communicationHistory.selectedPhone';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'communicationHistory.email.id', FIELD_NAME = 'email' where FIELD_DEFINITION_ID = 'communicationHistory.selectedEmail';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'communicationHistory.address.addressLine1ReadOnly', FIELD_NAME = 'address' where FIELD_DEFINITION_ID = 'communicationHistory.selectedAddress.addressLine1ReadOnly';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'communicationHistory.address.addressLine2ReadOnly', FIELD_NAME = 'address' where FIELD_DEFINITION_ID = 'communicationHistory.selectedAddress.addressLine2ReadOnly';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'communicationHistory.address.addressLine3ReadOnly', FIELD_NAME = 'address' where FIELD_DEFINITION_ID = 'communicationHistory.selectedAddress.addressLine3ReadOnly';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'communicationHistory.address.cityReadOnly', FIELD_NAME = 'address' where FIELD_DEFINITION_ID = 'communicationHistory.selectedAddress.cityReadOnly';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'communicationHistory.address.stateProvinceReadOnly', FIELD_NAME = 'address' where FIELD_DEFINITION_ID = 'communicationHistory.selectedAddress.stateProvinceReadOnly';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'communicationHistory.address.postalCodeReadOnly', FIELD_NAME = 'address' where FIELD_DEFINITION_ID = 'communicationHistory.selectedAddress.postalCodeReadOnly';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'communicationHistory.address.countryReadOnly', FIELD_NAME = 'address' where FIELD_DEFINITION_ID = 'communicationHistory.selectedAddress.countryReadOnly';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'communicationHistory.phone.numberReadOnly', FIELD_NAME = 'phone' where FIELD_DEFINITION_ID = 'communicationHistory.selectedPhone.numberReadOnly';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'communicationHistory.email.emailAddressReadOnly', FIELD_NAME = 'email' where FIELD_DEFINITION_ID = 'communicationHistory.selectedEmail.emailAddressReadOnly';


DELETE FROM FIELD_DEFINITION where FIELD_DEFINITION_ID = 'constituent.primaryEmail';

DELETE FROM FIELD_DEFINITION where FIELD_DEFINITION_ID = 'constituent.primaryAddress';

DELETE FROM FIELD_DEFINITION where FIELD_DEFINITION_ID = 'constituent.primaryPhone';

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('constituent.primaryEmail.customFieldMap[emailType]', 'constituent', 'primaryEmail', 'Email', 'MULTI_PICKLIST');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('constituent.primaryEmail.emailAddress', 'constituent', 'primaryEmail', 'Email', 'TEXT');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('constituent.primaryAddress.customFieldMap[addressType]', 'constituent', 'primaryAddress', 'Address', 'MULTI_PICKLIST');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('constituent.primaryAddress.addressLine1', 'constituent', 'primaryAddress', 'Address', 'TEXT');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('constituent.primaryAddress.addressLine2', 'constituent', 'primaryAddress', 'Address', 'TEXT');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('constituent.primaryAddress.postalCode', 'constituent', 'primaryAddress', 'Address', 'TEXT');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('constituent.primaryAddress.city', 'constituent', 'primaryAddress', 'Address', 'TEXT');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('constituent.primaryAddress.stateProvince', 'constituent', 'primaryAddress', 'Address', 'PICKLIST');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('constituent.primaryAddress.country', 'constituent', 'primaryAddress', 'Address', 'PICKLIST');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('constituent.primaryPhone.customFieldMap[phoneType]', 'constituent', 'primaryPhone', 'Phone', 'MULTI_PICKLIST');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('constituent.primaryPhone.number', 'constituent', 'primaryPhone', 'Phone', 'TEXT');

UPDATE FIELD_DEFINITION set ENTITY_ATTRIBUTES = 'individual,spouse' where FIELD_DEFINITION_ID = 'constituent.customFieldMap[individual.spouse]';

UPDATE FIELD_DEFINITION set DEFAULT_LABEL = 'Eligible Funds' where FIELD_DEFINITION_ID = 'constituent.customFieldMap[organization.additional_eligibleFunds]';

UPDATE FIELD_DEFINITION set DEFAULT_LABEL = 'Eligible Orgs' where FIELD_DEFINITION_ID = 'constituent.customFieldMap[organization.additional_eligibleOrganizations]';

UPDATE FIELD_DEFINITION set DEFAULT_LABEL = 'Ineligible Orgs' where FIELD_DEFINITION_ID = 'constituent.customFieldMap[organization.additional_ineligibleOrganizations]';

UPDATE FIELD_DEFINITION set DEFAULT_LABEL = 'Program Contact' where FIELD_DEFINITION_ID = 'constituent.customFieldMap[organization.other_programContact]';

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('constituent.majorDonor', 'constituent', 'majorDonor', 'Major Donor', 'READ_ONLY_TEXT');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('constituent.lapsedDonor', 'constituent', 'lapsedDonor', 'Lapsed Donor', 'READ_ONLY_TEXT');




INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('email.id', 'email', 'id', 'Email', 'EMAIL_PICKLIST');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('email.customFieldMap[emailTypeReadOnly]', 'email', 'customFieldMap[emailType]', 'Email Type', 'MULTI_PICKLIST_DISPLAY');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('email.current', 'email', 'current', 'Current', 'READ_ONLY_TEXT');

DELETE FROM FIELD_DEFINITION where FIELD_DEFINITION_ID = 'email.userCreated';

DELETE FROM FIELD_DEFINITION where FIELD_DEFINITION_ID = 'selectedEmail.customFieldMap[emailTypeReadOnly]';

DELETE FROM FIELD_DEFINITION where FIELD_DEFINITION_ID = 'selectedEmail.emailAddressReadOnly';





UPDATE FIELD_DEFINITION set FIELD_TYPE = 'PICKLIST_DISPLAY' where FIELD_DEFINITION_ID = 'gift.paymentTypeReadOnly';

UPDATE FIELD_DEFINITION set ENTITY_ATTRIBUTES = 'newCredit' where FIELD_DEFINITION_ID IN ( 'gift.paymentSource.creditCardType', 'gift.paymentSource.creditCardHolderName', 'gift.paymentSource.creditCardNumber', 'gift.paymentSource.creditCardExpiration');

UPDATE FIELD_DEFINITION set ENTITY_ATTRIBUTES = 'newAch' where FIELD_DEFINITION_ID IN ( 'gift.paymentSource.achHolderName', 'gift.paymentSource.achRoutingNumber', 'gift.paymentSource.achAccountNumber');

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'gift.paymentSource.creditCardHolderNameReadOnly', FIELD_NAME = 'paymentSource', ENTITY_ATTRIBUTES = 'existingCredit' where FIELD_DEFINITION_ID = 'gift.selectedPaymentSource.creditCardHolderNameReadOnly';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'gift.paymentSource.creditCardTypeReadOnly', FIELD_NAME = 'paymentSource', ENTITY_ATTRIBUTES = 'existingCredit' where FIELD_DEFINITION_ID = 'gift.selectedPaymentSource.creditCardTypeReadOnly';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'gift.paymentSource.creditCardNumberReadOnly', FIELD_NAME = 'paymentSource', ENTITY_ATTRIBUTES = 'existingCredit' where FIELD_DEFINITION_ID = 'gift.selectedPaymentSource.creditCardNumberReadOnly';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'gift.paymentSource.creditCardExpirationDisplay', FIELD_NAME = 'paymentSource', ENTITY_ATTRIBUTES = 'existingCredit' where FIELD_DEFINITION_ID = 'gift.selectedPaymentSource.creditCardExpirationDisplay';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'gift.paymentSource.achHolderNameReadOnly', FIELD_NAME = 'paymentSource', ENTITY_ATTRIBUTES = 'existingAch' where FIELD_DEFINITION_ID = 'gift.selectedPaymentSource.achHolderNameReadOnly';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'gift.paymentSource.achRoutingNumberReadOnly', FIELD_NAME = 'paymentSource', ENTITY_ATTRIBUTES = 'existingAch' where FIELD_DEFINITION_ID = 'gift.selectedPaymentSource.achRoutingNumberReadOnly';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'gift.paymentSource.achAccountNumberReadOnly', FIELD_NAME = 'paymentSource', ENTITY_ATTRIBUTES = 'existingAch' where FIELD_DEFINITION_ID = 'gift.selectedPaymentSource.achAccountNumberReadOnly';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'gift.paymentSource.id', FIELD_NAME = 'paymentSource' where FIELD_DEFINITION_ID = 'gift.selectedPaymentSource';

DELETE FROM FIELD_DEFINITION where FIELD_DEFINITION_ID = 'gift.selectedPaymentSource.creditCardSecurityCode';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'gift.address.id', FIELD_NAME = 'address' where FIELD_DEFINITION_ID = 'gift.selectedAddress';

UPDATE FIELD_DEFINITION set ENTITY_ATTRIBUTES = 'address,newAddress' 
where FIELD_DEFINITION_ID IN ( 'gift.address.customFieldMap[addressType]', 'gift.address.addressLine1', 'gift.address.addressLine2', 'gift.address.addressLine3', 'gift.address.city', 'gift.address.stateProvince', 'gift.address.postalCode', 'gift.address.country');

UPDATE FIELD_DEFINITION set FIELD_TYPE = 'PICKLIST' where FIELD_DEFINITION_ID = 'gift.address.stateProvince';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'gift.address.customFieldMap[addressTypeReadOnly]', FIELD_NAME = 'address' where FIELD_DEFINITION_ID = 'gift.selectedAddress.customFieldMap[addressTypeReadOnly]';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'gift.address.addressLine1ReadOnly', FIELD_NAME = 'address' where FIELD_DEFINITION_ID = 'gift.selectedAddress.addressLine1ReadOnly';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'gift.address.addressLine2ReadOnly', FIELD_NAME = 'address' where FIELD_DEFINITION_ID = 'gift.selectedAddress.addressLine2ReadOnly';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'gift.address.addressLine3ReadOnly', FIELD_NAME = 'address' where FIELD_DEFINITION_ID = 'gift.selectedAddress.addressLine3ReadOnly';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'gift.address.cityReadOnly', FIELD_NAME = 'address' where FIELD_DEFINITION_ID = 'gift.selectedAddress.cityReadOnly';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'gift.address.stateProvinceReadOnly', FIELD_NAME = 'address', FIELD_TYPE = 'PICKLIST_DISPLAY' where FIELD_DEFINITION_ID = 'gift.selectedAddress.stateProvinceReadOnly';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'gift.address.postalCodeReadOnly', FIELD_NAME = 'address' where FIELD_DEFINITION_ID = 'gift.selectedAddress.postalCodeReadOnly';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'gift.address.countryReadOnly', FIELD_NAME = 'address', FIELD_TYPE = 'PICKLIST_DISPLAY' where FIELD_DEFINITION_ID = 'gift.selectedAddress.countryReadOnly';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'gift.phone.id', FIELD_NAME = 'phone' where FIELD_DEFINITION_ID = 'gift.selectedPhone';

UPDATE FIELD_DEFINITION set ENTITY_ATTRIBUTES = 'phone,newPhone' where FIELD_DEFINITION_ID IN ( 'gift.phone.customFieldMap[phoneType]', 'gift.phone.number');

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'gift.phone.customFieldMap[phoneTypeReadOnly]', FIELD_NAME = 'phone' where FIELD_DEFINITION_ID = 'gift.selectedPhone.customFieldMap[phoneTypeReadOnly]';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'gift.phone.numberReadOnly', FIELD_NAME = 'phone' where FIELD_DEFINITION_ID = 'gift.selectedPhone.numberReadOnly';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'gift.distributionLines.amount', FIELD_NAME = 'distributionLines' where FIELD_DEFINITION_ID = 'gift.lineAmount';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'gift.distributionLines.percentage', FIELD_NAME = 'distributionLines' where FIELD_DEFINITION_ID = 'gift.percentage';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'gift.distributionLines.projectCode', FIELD_NAME = 'distributionLines' where FIELD_DEFINITION_ID = 'gift.projectCode';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'gift.distributionLines.motivationCode', FIELD_NAME = 'distributionLines' where FIELD_DEFINITION_ID = 'gift.motivationCode';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'gift.distributionLines.other_motivationCode', FIELD_NAME = 'distributionLines', DEFAULT_LABEL = 'Motivation' where FIELD_DEFINITION_ID = 'gift.other_motivationCode';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'gift.distributionLines.customFieldMap[reference]', FIELD_NAME = 'distributionLines' where FIELD_DEFINITION_ID = 'gift.customFieldMap[reference]';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'gift.distributionLines.customFieldMap[other_reference]', FIELD_NAME = 'distributionLines', DEFAULT_LABEL = 'Reference' where FIELD_DEFINITION_ID = 'gift.customFieldMap[other_reference]';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'gift.distributionLines.customFieldMap[tribute]', FIELD_NAME = 'distributionLines' where FIELD_DEFINITION_ID = 'gift.customFieldMap[tribute]';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'gift.distributionLines.customFieldMap[tributeReference]', FIELD_NAME = 'distributionLines' where FIELD_DEFINITION_ID = 'gift.customFieldMap[tributeReference]';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'gift.distributionLines.customFieldMap[other_tributeReference]', FIELD_NAME = 'distributionLines', DEFAULT_LABEL = 'Tribute Reference' where FIELD_DEFINITION_ID = 'gift.customFieldMap[other_tributeReference]';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'gift.distributionLines.customFieldMap[tributeOccasion]', FIELD_NAME = 'distributionLines' where FIELD_DEFINITION_ID = 'gift.customFieldMap[tributeOccasion]';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'gift.distributionLines.customFieldMap[additional_tributeOccasion]', FIELD_NAME = 'distributionLines', DEFAULT_LABEL = 'Tribute Occasion' where FIELD_DEFINITION_ID = 'gift.customFieldMap[additional_tributeOccasion]';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'gift.distributionLines.customFieldMap[onBehalfOf]', FIELD_NAME = 'distributionLines' where FIELD_DEFINITION_ID = 'gift.customFieldMap[onBehalfOf]';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'gift.distributionLines.customFieldMap[other_onBehalfOf]', FIELD_NAME = 'distributionLines', DEFAULT_LABEL = 'On Behalf Of' where FIELD_DEFINITION_ID = 'gift.customFieldMap[other_onBehalfOf]';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'gift.distributionLines.customFieldMap[anonymous]', FIELD_NAME = 'distributionLines' where FIELD_DEFINITION_ID = 'gift.customFieldMap[anonymous]';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'gift.distributionLines.customFieldMap[recognitionName]', FIELD_NAME = 'distributionLines' where FIELD_DEFINITION_ID = 'gift.customFieldMap[recognitionName]';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'gift.distributionLines.customFieldMap[notified]', FIELD_NAME = 'distributionLines' where FIELD_DEFINITION_ID = 'gift.customFieldMap[notified]';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'gift.distributionLines.customFieldMap[other_notified]', FIELD_NAME = 'distributionLines', DEFAULT_LABEL = 'To Be Notified' where FIELD_DEFINITION_ID = 'gift.customFieldMap[other_notified]';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'gift.distributionLines.customFieldMap[message]', FIELD_NAME = 'distributionLines' where FIELD_DEFINITION_ID = 'gift.customFieldMap[message]';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'gift.distributionLines.customFieldMap[taxDeductible]', FIELD_NAME = 'distributionLines' where FIELD_DEFINITION_ID = 'gift.customFieldMap[taxDeductible]';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'gift.distributionLines.customFieldMap[associatedPledgeId]', FIELD_NAME = 'distributionLines' where FIELD_DEFINITION_ID = 'gift.customFieldMap[associatedPledgeId]';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'gift.distributionLines.customFieldMap[associatedRecurringGiftId]', FIELD_NAME = 'distributionLines' where FIELD_DEFINITION_ID = 'gift.customFieldMap[associatedRecurringGiftId]';



UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'gift.distributionLines.amountReadOnly', FIELD_NAME = 'distributionLines', FIELD_TYPE = 'NUMBER_DISPLAY' where FIELD_DEFINITION_ID = 'gift.lineAmountReadOnly';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'gift.distributionLines.percentageReadOnly', FIELD_NAME = 'distributionLines', FIELD_TYPE = 'PERCENTAGE_DISPLAY' where FIELD_DEFINITION_ID = 'gift.percentageReadOnly';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'gift.distributionLines.projectCodeReadOnly', FIELD_NAME = 'distributionLines' where FIELD_DEFINITION_ID = 'gift.projectCodeReadOnly';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'gift.distributionLines.motivationCodeReadOnly', FIELD_NAME = 'distributionLines' where FIELD_DEFINITION_ID = 'gift.motivationCodeReadOnly';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'gift.distributionLines.other_motivationCodeReadOnly', FIELD_NAME = 'distributionLines', DEFAULT_LABEL = 'Motivation' where FIELD_DEFINITION_ID = 'gift.other_motivationCodeReadOnly';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'gift.distributionLines.customFieldMap[referenceReadOnly]', FIELD_NAME = 'distributionLines' where FIELD_DEFINITION_ID = 'gift.customFieldMap[referenceReadOnly]';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'gift.distributionLines.customFieldMap[other_referenceReadOnly]', FIELD_NAME = 'distributionLines', DEFAULT_LABEL = 'Reference' where FIELD_DEFINITION_ID = 'gift.customFieldMap[other_referenceReadOnly]';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'gift.distributionLines.customFieldMap[tributeReadOnly]', FIELD_NAME = 'distributionLines' where FIELD_DEFINITION_ID = 'gift.customFieldMap[tributeReadOnly]';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'gift.distributionLines.customFieldMap[tributeReferenceReadOnly]', FIELD_NAME = 'distributionLines' where FIELD_DEFINITION_ID = 'gift.customFieldMap[tributeReferenceReadOnly]';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'gift.distributionLines.customFieldMap[other_tributeReferenceReadOnly]', FIELD_NAME = 'distributionLines', DEFAULT_LABEL = 'Tribute Reference' where FIELD_DEFINITION_ID = 'gift.customFieldMap[other_tributeReferenceReadOnly]';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'gift.distributionLines.customFieldMap[tributeOccasionReadOnly]', FIELD_NAME = 'distributionLines' where FIELD_DEFINITION_ID = 'gift.customFieldMap[tributeOccasionReadOnly]';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'gift.distributionLines.customFieldMap[additional_tributeOccasionReadOnly]', FIELD_NAME = 'distributionLines', DEFAULT_LABEL = 'Tribute Occasion' where FIELD_DEFINITION_ID = 'gift.customFieldMap[additional_tributeOccasionReadOnly]';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'gift.distributionLines.customFieldMap[onBehalfOfReadOnly]', FIELD_NAME = 'distributionLines' where FIELD_DEFINITION_ID = 'gift.customFieldMap[onBehalfOfReadOnly]';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'gift.distributionLines.customFieldMap[other_onBehalfOfReadOnly]', FIELD_NAME = 'distributionLines', DEFAULT_LABEL = 'On Behalf Of' where FIELD_DEFINITION_ID = 'gift.customFieldMap[other_onBehalfOfReadOnly]';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'gift.distributionLines.customFieldMap[anonymousReadOnly]', FIELD_NAME = 'distributionLines' where FIELD_DEFINITION_ID = 'gift.customFieldMap[anonymousReadOnly]';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'gift.distributionLines.customFieldMap[recognitionNameReadOnly]', FIELD_NAME = 'distributionLines' where FIELD_DEFINITION_ID = 'gift.customFieldMap[recognitionNameReadOnly]';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'gift.distributionLines.customFieldMap[notifiedReadOnly]', FIELD_NAME = 'distributionLines' where FIELD_DEFINITION_ID = 'gift.customFieldMap[notifiedReadOnly]';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'gift.distributionLines.customFieldMap[other_notifiedReadOnly]', FIELD_NAME = 'distributionLines', DEFAULT_LABEL = 'To Be Notified' where FIELD_DEFINITION_ID = 'gift.customFieldMap[other_notifiedReadOnly]';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'gift.distributionLines.customFieldMap[messageReadOnly]', FIELD_NAME = 'distributionLines' where FIELD_DEFINITION_ID = 'gift.customFieldMap[messageReadOnly]';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'gift.distributionLines.customFieldMap[taxDeductibleReadOnly]', FIELD_NAME = 'distributionLines' where FIELD_DEFINITION_ID = 'gift.customFieldMap[taxDeductibleReadOnly]';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'gift.distributionLines.customFieldMap[associatedPledgeIdReadOnly]', FIELD_NAME = 'distributionLines' where FIELD_DEFINITION_ID = 'gift.customFieldMap[associatedPledgeIdReadOnly]';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'gift.distributionLines.customFieldMap[associatedRecurringGiftIdReadOnly]', FIELD_NAME = 'distributionLines' where FIELD_DEFINITION_ID = 'gift.customFieldMap[associatedRecurringGiftIdReadOnly]';





UPDATE FIELD_DEFINITION set DEFAULT_LABEL = 'Motivation' where FIELD_DEFINITION_ID = 'giftInKind.other_motivationCode';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'giftInKind.details.description', FIELD_NAME = 'details' where FIELD_DEFINITION_ID = 'giftInKind.description';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'giftInKind.details.detailFairMarketValue', FIELD_NAME = 'details' where FIELD_DEFINITION_ID = 'giftInKind.detailFairMarketValue';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'giftInKind.details.projectCode', FIELD_NAME = 'details' where FIELD_DEFINITION_ID = 'giftInKind.projectCode';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'giftInKind.details.taxDeductible', FIELD_NAME = 'details' where FIELD_DEFINITION_ID = 'giftInKind.taxDeductible';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'giftInKind.details.fmvMethod', FIELD_NAME = 'details' where FIELD_DEFINITION_ID = 'giftInKind.fmvMethod';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'giftInKind.details.gikCategory', FIELD_NAME = 'details' where FIELD_DEFINITION_ID = 'giftInKind.gikCategory';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'giftInKind.details.quantity', FIELD_NAME = 'details' where FIELD_DEFINITION_ID = 'giftInKind.quantity';





UPDATE FIELD_DEFINITION set DEFAULT_LABEL = 'Payment Profile', FIELD_TYPE = 'PAYMENT_SOURCE_PICKLIST' where FIELD_DEFINITION_ID = 'paymentSource.id';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'paymentSource.address.id', FIELD_NAME = 'address' where FIELD_DEFINITION_ID = 'paymentSource.selectedAddress';

UPDATE FIELD_DEFINITION set ENTITY_ATTRIBUTES = 'newAddress' 
where FIELD_DEFINITION_ID IN ( 'paymentSource.address.customFieldMap[addressType]', 'paymentSource.address.addressLine1', 'paymentSource.address.addressLine2', 'paymentSource.address.addressLine3', 'paymentSource.address.city', 'paymentSource.address.stateProvince', 'paymentSource.address.postalCode', 'paymentSource.address.country');

UPDATE FIELD_DEFINITION set FIELD_TYPE = 'PICKLIST' where FIELD_DEFINITION_ID = 'paymentSource.address.stateProvince';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'paymentSource.phone.id', FIELD_NAME = 'phone' where FIELD_DEFINITION_ID = 'paymentSource.selectedPhone';

UPDATE FIELD_DEFINITION set ENTITY_ATTRIBUTES = 'newPhone' where FIELD_DEFINITION_ID IN ( 'paymentSource.phone.customFieldMap[phoneType]', 'paymentSource.phone.number');

DELETE FROM FIELD_DEFINITION where FIELD_DEFINITION_ID = 'paymentSource.userCreated';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'paymentSource.address.customFieldMap[addressTypeReadOnly]', FIELD_NAME = 'address', ENTITY_ATTRIBUTES = 'existingAddress' where FIELD_DEFINITION_ID = 'paymentSource.selectedAddress.customFieldMap[addressType]';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'paymentSource.address.addressLine1ReadOnly', FIELD_NAME = 'address', ENTITY_ATTRIBUTES = 'existingAddress' where FIELD_DEFINITION_ID = 'paymentSource.selectedAddress.addressLine1ReadOnly';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'paymentSource.address.addressLine2ReadOnly', FIELD_NAME = 'address', ENTITY_ATTRIBUTES = 'existingAddress' where FIELD_DEFINITION_ID = 'paymentSource.selectedAddress.addressLine2ReadOnly';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'paymentSource.address.addressLine3ReadOnly', FIELD_NAME = 'address', ENTITY_ATTRIBUTES = 'existingAddress' where FIELD_DEFINITION_ID = 'paymentSource.selectedAddress.addressLine3ReadOnly';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'paymentSource.address.cityReadOnly', FIELD_NAME = 'address', ENTITY_ATTRIBUTES = 'existingAddress' where FIELD_DEFINITION_ID = 'paymentSource.selectedAddress.cityReadOnly';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'paymentSource.address.stateProvinceReadOnly', FIELD_NAME = 'address', FIELD_TYPE = 'PICKLIST_DISPLAY', ENTITY_ATTRIBUTES = 'existingAddress' where FIELD_DEFINITION_ID = 'paymentSource.selectedAddress.stateProvinceReadOnly';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'paymentSource.address.postalCodeReadOnly', FIELD_NAME = 'address', ENTITY_ATTRIBUTES = 'existingAddress' where FIELD_DEFINITION_ID = 'paymentSource.selectedAddress.postalCodeReadOnly';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'paymentSource.address.countryReadOnly', FIELD_NAME = 'address', FIELD_TYPE = 'PICKLIST_DISPLAY', ENTITY_ATTRIBUTES = 'existingAddress' where FIELD_DEFINITION_ID = 'paymentSource.selectedAddress.countryReadOnly';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'paymentSource.phone.customFieldMap[phoneTypeReadOnly]', FIELD_NAME = 'phone', ENTITY_ATTRIBUTES = 'existingPhone' where FIELD_DEFINITION_ID = 'paymentSource.selectedPhone.customFieldMap[phoneType]';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'paymentSource.phone.customFieldMap[numberReadOnly]', FIELD_NAME = 'phone', ENTITY_ATTRIBUTES = 'existingPhone' where FIELD_DEFINITION_ID = 'paymentSource.selectedPhone.customFieldMap[numberReadOnly]';

UPDATE FIELD_DEFINITION set ENTITY_ATTRIBUTES = 'existingCredit' where FIELD_DEFINITION_ID IN ( 'paymentSource.creditCardHolderNameReadOnly', 'paymentSource.creditCardTypeReadOnly', 'paymentSource.creditCardNumberReadOnly', 'paymentSource.creditCardExpirationDisplay');

UPDATE FIELD_DEFINITION set ENTITY_ATTRIBUTES = 'existingAch' where FIELD_DEFINITION_ID IN ( 'paymentSource.achHolderNameReadOnly', 'paymentSource.achRoutingNumberReadOnly', 'paymentSource.achAccountNumberReadOnly');

DELETE FROM FIELD_DEFINITION where FIELD_DEFINITION_ID in ('paymentSource.creditCardNumberDisplay', 'paymentSource.achAccountNumberDisplay', 'selectedPaymentSource.creditCardHolderNameReadOnly', 'selectedPaymentSource.creditCardTypeReadOnly', 'selectedPaymentSource.creditCardNumberReadOnly', 'selectedPaymentSource.creditCardExpirationDisplay', 'selectedPaymentSource.achHolderNameReadOnly', 'selectedPaymentSource.achRoutingNumberReadOnly', 'selectedPaymentSource.achAccountNumberReadOnly', 'selectedPaymentSource.creditCardSecurityCode');

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'paymentSource.address.shortAddressReadOnly', FIELD_NAME = 'address' where FIELD_DEFINITION_ID = 'paymentSource.selectedAddress.shortAddressReadOnly';




INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('phone.id', 'phone', 'id', 'Phone', 'PHONE_PICKLIST');

DELETE FROM FIELD_DEFINITION where FIELD_DEFINITION_ID = 'phone.userCreated';

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('phone.current', 'phone', 'current', 'Current', 'READ_ONLY_TEXT');

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'phone.customFieldMap[phoneTypeReadOnly]', FIELD_NAME = 'phone' where FIELD_DEFINITION_ID = 'selectedPhone.customFieldMap[phoneTypeReadOnly]';

DELETE FROM FIELD_DEFINITION where FIELD_DEFINITION_ID = 'selectedPhone.numberReadOnly';





UPDATE FIELD_DEFINITION set FIELD_TYPE = 'PICKLIST_DISPLAY' where FIELD_DEFINITION_ID = 'pledge.frequencyReadOnly';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'pledge.distributionLines.amount', FIELD_NAME = 'distributionLines' where FIELD_DEFINITION_ID = 'pledge.lineAmount';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'pledge.distributionLines.percentage', FIELD_NAME = 'distributionLines' where FIELD_DEFINITION_ID = 'pledge.percentage';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'pledge.distributionLines.projectCode', FIELD_NAME = 'distributionLines' where FIELD_DEFINITION_ID = 'pledge.projectCode';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'pledge.distributionLines.motivationCode', FIELD_NAME = 'distributionLines' where FIELD_DEFINITION_ID = 'pledge.motivationCode';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'pledge.distributionLines.other_motivationCode', FIELD_NAME = 'distributionLines', DEFAULT_LABEL = 'Motivation' where FIELD_DEFINITION_ID = 'pledge.other_motivationCode';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'pledge.distributionLines.customFieldMap[reference]', FIELD_NAME = 'distributionLines' where FIELD_DEFINITION_ID = 'pledge.customFieldMap[reference]';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'pledge.distributionLines.customFieldMap[other_reference]', FIELD_NAME = 'distributionLines', DEFAULT_LABEL = 'Reference' where FIELD_DEFINITION_ID = 'pledge.customFieldMap[other_reference]';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'pledge.distributionLines.customFieldMap[tribute]', FIELD_NAME = 'distributionLines' where FIELD_DEFINITION_ID = 'pledge.customFieldMap[tribute]';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'pledge.distributionLines.customFieldMap[tributeReference]', FIELD_NAME = 'distributionLines' where FIELD_DEFINITION_ID = 'pledge.customFieldMap[tributeReference]';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'pledge.distributionLines.customFieldMap[other_tributeReference]', FIELD_NAME = 'distributionLines', DEFAULT_LABEL = 'Tribute Reference' where FIELD_DEFINITION_ID = 'pledge.customFieldMap[other_tributeReference]';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'pledge.distributionLines.customFieldMap[tributeOccasion]', FIELD_NAME = 'distributionLines' where FIELD_DEFINITION_ID = 'pledge.customFieldMap[tributeOccasion]';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'pledge.distributionLines.customFieldMap[additional_tributeOccasion]', FIELD_NAME = 'distributionLines', DEFAULT_LABEL = 'Tribute Occasion' where FIELD_DEFINITION_ID = 'pledge.customFieldMap[additional_tributeOccasion]';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'pledge.distributionLines.customFieldMap[onBehalfOf]', FIELD_NAME = 'distributionLines' where FIELD_DEFINITION_ID = 'pledge.customFieldMap[onBehalfOf]';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'pledge.distributionLines.customFieldMap[other_onBehalfOf]', FIELD_NAME = 'distributionLines', DEFAULT_LABEL = 'On Behalf Of' where FIELD_DEFINITION_ID = 'pledge.customFieldMap[other_onBehalfOf]';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'pledge.distributionLines.customFieldMap[anonymous]', FIELD_NAME = 'distributionLines' where FIELD_DEFINITION_ID = 'pledge.customFieldMap[anonymous]';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'pledge.distributionLines.customFieldMap[recognitionName]', FIELD_NAME = 'distributionLines' where FIELD_DEFINITION_ID = 'pledge.customFieldMap[recognitionName]';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'pledge.distributionLines.customFieldMap[notified]', FIELD_NAME = 'distributionLines' where FIELD_DEFINITION_ID = 'pledge.customFieldMap[notified]';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'pledge.distributionLines.customFieldMap[other_notified]', FIELD_NAME = 'distributionLines', DEFAULT_LABEL = 'To Be Notified' where FIELD_DEFINITION_ID = 'pledge.customFieldMap[other_notified]';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'pledge.distributionLines.customFieldMap[message]', FIELD_NAME = 'distributionLines' where FIELD_DEFINITION_ID = 'pledge.customFieldMap[message]';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'pledge.distributionLines.customFieldMap[taxDeductible]', FIELD_NAME = 'distributionLines' where FIELD_DEFINITION_ID = 'pledge.customFieldMap[taxDeductible]';



UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'pledge.distributionLines.amountReadOnly', FIELD_NAME = 'distributionLines', FIELD_TYPE = 'NUMBER_DISPLAY' where FIELD_DEFINITION_ID = 'pledge.lineAmountReadOnly';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'pledge.distributionLines.percentageReadOnly', FIELD_NAME = 'distributionLines', FIELD_TYPE = 'PERCENTAGE_DISPLAY' where FIELD_DEFINITION_ID = 'pledge.percentageReadOnly';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'pledge.distributionLines.projectCodeReadOnly', FIELD_NAME = 'distributionLines' where FIELD_DEFINITION_ID = 'pledge.projectCodeReadOnly';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'pledge.distributionLines.motivationCodeReadOnly', FIELD_NAME = 'distributionLines' where FIELD_DEFINITION_ID = 'pledge.motivationCodeReadOnly';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'pledge.distributionLines.other_motivationCodeReadOnly', FIELD_NAME = 'distributionLines', DEFAULT_LABEL = 'Motivation' where FIELD_DEFINITION_ID = 'pledge.other_motivationCodeReadOnly';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'pledge.distributionLines.customFieldMap[referenceReadOnly]', FIELD_NAME = 'distributionLines' where FIELD_DEFINITION_ID = 'pledge.customFieldMap[referenceReadOnly]';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'pledge.distributionLines.customFieldMap[other_referenceReadOnly]', FIELD_NAME = 'distributionLines', DEFAULT_LABEL = 'Reference' where FIELD_DEFINITION_ID = 'pledge.customFieldMap[other_referenceReadOnly]';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'pledge.distributionLines.customFieldMap[tributeReadOnly]', FIELD_NAME = 'distributionLines' where FIELD_DEFINITION_ID = 'pledge.customFieldMap[tributeReadOnly]';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'pledge.distributionLines.customFieldMap[tributeReferenceReadOnly]', FIELD_NAME = 'distributionLines' where FIELD_DEFINITION_ID = 'pledge.customFieldMap[tributeReferenceReadOnly]';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'pledge.distributionLines.customFieldMap[other_tributeReferenceReadOnly]', FIELD_NAME = 'distributionLines', DEFAULT_LABEL = 'Tribute Reference' where FIELD_DEFINITION_ID = 'pledge.customFieldMap[other_tributeReferenceReadOnly]';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'pledge.distributionLines.customFieldMap[tributeOccasionReadOnly]', FIELD_NAME = 'distributionLines' where FIELD_DEFINITION_ID = 'pledge.customFieldMap[tributeOccasionReadOnly]';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'pledge.distributionLines.customFieldMap[additional_tributeOccasionReadOnly]', FIELD_NAME = 'distributionLines', DEFAULT_LABEL = 'Tribute Occasion' where FIELD_DEFINITION_ID = 'pledge.customFieldMap[additional_tributeOccasionReadOnly]';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'pledge.distributionLines.customFieldMap[onBehalfOfReadOnly]', FIELD_NAME = 'distributionLines' where FIELD_DEFINITION_ID = 'pledge.customFieldMap[onBehalfOfReadOnly]';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'pledge.distributionLines.customFieldMap[other_onBehalfOfReadOnly]', FIELD_NAME = 'distributionLines', DEFAULT_LABEL = 'On Behalf Of' where FIELD_DEFINITION_ID = 'pledge.customFieldMap[other_onBehalfOfReadOnly]';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'pledge.distributionLines.customFieldMap[anonymousReadOnly]', FIELD_NAME = 'distributionLines' where FIELD_DEFINITION_ID = 'pledge.customFieldMap[anonymousReadOnly]';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'pledge.distributionLines.customFieldMap[recognitionNameReadOnly]', FIELD_NAME = 'distributionLines' where FIELD_DEFINITION_ID = 'pledge.customFieldMap[recognitionNameReadOnly]';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'pledge.distributionLines.customFieldMap[notifiedReadOnly]', FIELD_NAME = 'distributionLines' where FIELD_DEFINITION_ID = 'pledge.customFieldMap[notifiedReadOnly]';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'pledge.distributionLines.customFieldMap[other_notifiedReadOnly]', FIELD_NAME = 'distributionLines', DEFAULT_LABEL = 'To Be Notified' where FIELD_DEFINITION_ID = 'pledge.customFieldMap[other_notifiedReadOnly]';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'pledge.distributionLines.customFieldMap[messageReadOnly]', FIELD_NAME = 'distributionLines' where FIELD_DEFINITION_ID = 'pledge.customFieldMap[messageReadOnly]';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'pledge.distributionLines.customFieldMap[taxDeductibleReadOnly]', FIELD_NAME = 'distributionLines' where FIELD_DEFINITION_ID = 'pledge.customFieldMap[taxDeductibleReadOnly]';




UPDATE FIELD_DEFINITION set FIELD_TYPE = 'PICKLIST_DISPLAY' where FIELD_DEFINITION_ID = 'recurringGift.paymentTypeReadOnly';

UPDATE FIELD_DEFINITION set ENTITY_ATTRIBUTES = 'newCredit' 
where FIELD_DEFINITION_ID IN ( 'recurringGift.paymentSource.creditCardType', 'recurringGift.paymentSource.creditCardHolderName', 'recurringGift.paymentSource.creditCardNumber', 'recurringGift.paymentSource.creditCardExpiration');

UPDATE FIELD_DEFINITION set ENTITY_ATTRIBUTES = 'newAch' 
where FIELD_DEFINITION_ID IN ( 'recurringGift.paymentSource.achHolderName', 'recurringGift.paymentSource.achRoutingNumber', 'recurringGift.paymentSource.achAccountNumber');

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'recurringGift.paymentSource.creditCardHolderNameReadOnly', FIELD_NAME = 'paymentSource', ENTITY_ATTRIBUTES = 'existingCredit' where FIELD_DEFINITION_ID = 'recurringGift.selectedPaymentSource.creditCardHolderNameReadOnly';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'recurringGift.paymentSource.creditCardTypeReadOnly', FIELD_NAME = 'paymentSource', ENTITY_ATTRIBUTES = 'existingCredit' where FIELD_DEFINITION_ID = 'recurringGift.selectedPaymentSource.creditCardTypeReadOnly';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'recurringGift.paymentSource.creditCardNumberReadOnly', FIELD_NAME = 'paymentSource', ENTITY_ATTRIBUTES = 'existingCredit' where FIELD_DEFINITION_ID = 'recurringGift.selectedPaymentSource.creditCardNumberReadOnly';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'recurringGift.paymentSource.creditCardExpirationDisplay', FIELD_NAME = 'paymentSource', ENTITY_ATTRIBUTES = 'existingCredit' where FIELD_DEFINITION_ID = 'recurringGift.selectedPaymentSource.creditCardExpirationDisplay';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'recurringGift.paymentSource.achHolderNameReadOnly', FIELD_NAME = 'paymentSource', ENTITY_ATTRIBUTES = 'existingAch' where FIELD_DEFINITION_ID = 'recurringGift.selectedPaymentSource.achHolderNameReadOnly';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'recurringGift.paymentSource.achRoutingNumberReadOnly', FIELD_NAME = 'paymentSource', ENTITY_ATTRIBUTES = 'existingAch' where FIELD_DEFINITION_ID = 'recurringGift.selectedPaymentSource.achRoutingNumberReadOnly';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'recurringGift.paymentSource.achAccountNumberReadOnly', FIELD_NAME = 'paymentSource', ENTITY_ATTRIBUTES = 'existingAch' where FIELD_DEFINITION_ID = 'recurringGift.selectedPaymentSource.achAccountNumberReadOnly';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'recurringGift.paymentSource.id', FIELD_NAME = 'paymentSource', ENTITY_ATTRIBUTES = 'recurringGiftPayment' where FIELD_DEFINITION_ID = 'recurringGift.selectedPaymentSource';

DELETE FROM FIELD_DEFINITION where FIELD_DEFINITION_ID IN ( 'recurringGift.checkNumber', 'recurringGift.selectedPaymentSource.creditCardExpiration');

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'recurringGift.address.id', FIELD_NAME = 'address', ENTITY_ATTRIBUTES = 'address'  where FIELD_DEFINITION_ID = 'recurringGift.selectedAddress';

UPDATE FIELD_DEFINITION set ENTITY_ATTRIBUTES = 'address,newAddress' 
where FIELD_DEFINITION_ID IN ( 'recurringGift.address.customFieldMap[addressType]', 'recurringGift.address.addressLine1', 'recurringGift.address.addressLine2', 'recurringGift.address.addressLine3', 'recurringGift.address.city', 'recurringGift.address.stateProvince', 'recurringGift.address.postalCode', 'recurringGift.address.country');

UPDATE FIELD_DEFINITION set FIELD_TYPE = 'PICKLIST' where FIELD_DEFINITION_ID = 'recurringGift.address.stateProvince';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'recurringGift.address.customFieldMap[addressTypeReadOnly]', FIELD_NAME = 'address', ENTITY_ATTRIBUTES = 'address' where FIELD_DEFINITION_ID = 'recurringGift.selectedAddress.customFieldMap[addressTypeReadOnly]';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'recurringGift.address.addressLine1ReadOnly', FIELD_NAME = 'address', ENTITY_ATTRIBUTES = 'address' where FIELD_DEFINITION_ID = 'recurringGift.selectedAddress.addressLine1ReadOnly';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'recurringGift.address.addressLine2ReadOnly', FIELD_NAME = 'address', ENTITY_ATTRIBUTES = 'address' where FIELD_DEFINITION_ID = 'recurringGift.selectedAddress.addressLine2ReadOnly';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'recurringGift.address.addressLine3ReadOnly', FIELD_NAME = 'address', ENTITY_ATTRIBUTES = 'address' where FIELD_DEFINITION_ID = 'recurringGift.selectedAddress.addressLine3ReadOnly';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'recurringGift.address.cityReadOnly', FIELD_NAME = 'address', ENTITY_ATTRIBUTES = 'address' where FIELD_DEFINITION_ID = 'recurringGift.selectedAddress.cityReadOnly';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'recurringGift.address.stateProvinceReadOnly', FIELD_NAME = 'address', FIELD_TYPE = 'PICKLIST_DISPLAY', ENTITY_ATTRIBUTES = 'address' where FIELD_DEFINITION_ID = 'recurringGift.selectedAddress.stateProvinceReadOnly';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'recurringGift.address.postalCodeReadOnly', FIELD_NAME = 'address', ENTITY_ATTRIBUTES = 'address' where FIELD_DEFINITION_ID = 'recurringGift.selectedAddress.postalCodeReadOnly';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'recurringGift.address.countryReadOnly', FIELD_NAME = 'address', FIELD_TYPE = 'PICKLIST_DISPLAY', ENTITY_ATTRIBUTES = 'address' where FIELD_DEFINITION_ID = 'recurringGift.selectedAddress.countryReadOnly';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'recurringGift.phone.id', FIELD_NAME = 'phone', ENTITY_ATTRIBUTES = 'phone' where FIELD_DEFINITION_ID = 'recurringGift.selectedPhone';

UPDATE FIELD_DEFINITION set ENTITY_ATTRIBUTES = 'phone,newPhone' where FIELD_DEFINITION_ID IN ( 'recurringGift.phone.customFieldMap[phoneType]', 'recurringGift.phone.number');

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'recurringGift.phone.customFieldMap[phoneTypeReadOnly]', FIELD_NAME = 'phone', ENTITY_ATTRIBUTES = 'phone' where FIELD_DEFINITION_ID = 'recurringGift.selectedPhone.customFieldMap[phoneTypeReadOnly]';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'recurringGift.phone.numberReadOnly', FIELD_NAME = 'phone', ENTITY_ATTRIBUTES = 'phone' where FIELD_DEFINITION_ID = 'recurringGift.selectedPhone.numberReadOnly';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'recurringGift.distributionLines.amount', FIELD_NAME = 'distributionLines' where FIELD_DEFINITION_ID = 'recurringGift.lineAmount';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'recurringGift.distributionLines.percentage', FIELD_NAME = 'distributionLines' where FIELD_DEFINITION_ID = 'recurringGift.percentage';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'recurringGift.distributionLines.projectCode', FIELD_NAME = 'distributionLines' where FIELD_DEFINITION_ID = 'recurringGift.projectCode';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'recurringGift.distributionLines.motivationCode', FIELD_NAME = 'distributionLines' where FIELD_DEFINITION_ID = 'recurringGift.motivationCode';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'recurringGift.distributionLines.other_motivationCode', FIELD_NAME = 'distributionLines', DEFAULT_LABEL = 'Motivation' where FIELD_DEFINITION_ID = 'recurringGift.other_motivationCode';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'recurringGift.distributionLines.customFieldMap[reference]', FIELD_NAME = 'distributionLines' where FIELD_DEFINITION_ID = 'recurringGift.customFieldMap[reference]';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'recurringGift.distributionLines.customFieldMap[other_reference]', FIELD_NAME = 'distributionLines', DEFAULT_LABEL = 'Reference' where FIELD_DEFINITION_ID = 'recurringGift.customFieldMap[other_reference]';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'recurringGift.distributionLines.customFieldMap[tribute]', FIELD_NAME = 'distributionLines' where FIELD_DEFINITION_ID = 'recurringGift.customFieldMap[tribute]';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'recurringGift.distributionLines.customFieldMap[tributeReference]', FIELD_NAME = 'distributionLines' where FIELD_DEFINITION_ID = 'recurringGift.customFieldMap[tributeReference]';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'recurringGift.distributionLines.customFieldMap[other_tributeReference]', FIELD_NAME = 'distributionLines', DEFAULT_LABEL = 'Tribute Reference' where FIELD_DEFINITION_ID = 'recurringGift.customFieldMap[other_tributeReference]';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'recurringGift.distributionLines.customFieldMap[tributeOccasion]', FIELD_NAME = 'distributionLines' where FIELD_DEFINITION_ID = 'recurringGift.customFieldMap[tributeOccasion]';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'recurringGift.distributionLines.customFieldMap[additional_tributeOccasion]', FIELD_NAME = 'distributionLines', DEFAULT_LABEL = 'Tribute Occasion' where FIELD_DEFINITION_ID = 'recurringGift.customFieldMap[additional_tributeOccasion]';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'recurringGift.distributionLines.customFieldMap[onBehalfOf]', FIELD_NAME = 'distributionLines' where FIELD_DEFINITION_ID = 'recurringGift.customFieldMap[onBehalfOf]';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'recurringGift.distributionLines.customFieldMap[other_onBehalfOf]', FIELD_NAME = 'distributionLines', DEFAULT_LABEL = 'On Behalf Of' where FIELD_DEFINITION_ID = 'recurringGift.customFieldMap[other_onBehalfOf]';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'recurringGift.distributionLines.customFieldMap[anonymous]', FIELD_NAME = 'distributionLines' where FIELD_DEFINITION_ID = 'recurringGift.customFieldMap[anonymous]';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'recurringGift.distributionLines.customFieldMap[recognitionName]', FIELD_NAME = 'distributionLines' where FIELD_DEFINITION_ID = 'recurringGift.customFieldMap[recognitionName]';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'recurringGift.distributionLines.customFieldMap[notified]', FIELD_NAME = 'distributionLines' where FIELD_DEFINITION_ID = 'recurringGift.customFieldMap[notified]';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'recurringGift.distributionLines.customFieldMap[other_notified]', FIELD_NAME = 'distributionLines', DEFAULT_LABEL = 'To Be Notified' where FIELD_DEFINITION_ID = 'recurringGift.customFieldMap[other_notified]';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'recurringGift.distributionLines.customFieldMap[message]', FIELD_NAME = 'distributionLines' where FIELD_DEFINITION_ID = 'recurringGift.customFieldMap[message]';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'recurringGift.distributionLines.customFieldMap[taxDeductible]', FIELD_NAME = 'distributionLines' where FIELD_DEFINITION_ID = 'recurringGift.customFieldMap[taxDeductible]';



UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'recurringGift.distributionLines.amountReadOnly', FIELD_NAME = 'distributionLines', FIELD_TYPE = 'NUMBER_DISPLAY' where FIELD_DEFINITION_ID = 'recurringGift.lineAmountReadOnly';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'recurringGift.distributionLines.percentageReadOnly', FIELD_NAME = 'distributionLines', FIELD_TYPE = 'PERCENTAGE_DISPLAY' where FIELD_DEFINITION_ID = 'recurringGift.percentageReadOnly';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'recurringGift.distributionLines.projectCodeReadOnly', FIELD_NAME = 'distributionLines' where FIELD_DEFINITION_ID = 'recurringGift.projectCodeReadOnly';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'recurringGift.distributionLines.motivationCodeReadOnly', FIELD_NAME = 'distributionLines' where FIELD_DEFINITION_ID = 'recurringGift.motivationCodeReadOnly';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'recurringGift.distributionLines.other_motivationCodeReadOnly', FIELD_NAME = 'distributionLines', DEFAULT_LABEL = 'Motivation' where FIELD_DEFINITION_ID = 'recurringGift.other_motivationCodeReadOnly';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'recurringGift.distributionLines.customFieldMap[referenceReadOnly]', FIELD_NAME = 'distributionLines' where FIELD_DEFINITION_ID = 'recurringGift.customFieldMap[referenceReadOnly]';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'recurringGift.distributionLines.customFieldMap[other_referenceReadOnly]', FIELD_NAME = 'distributionLines', DEFAULT_LABEL = 'Reference' where FIELD_DEFINITION_ID = 'recurringGift.customFieldMap[other_referenceReadOnly]';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'recurringGift.distributionLines.customFieldMap[tributeReadOnly]', FIELD_NAME = 'distributionLines' where FIELD_DEFINITION_ID = 'recurringGift.customFieldMap[tributeReadOnly]';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'recurringGift.distributionLines.customFieldMap[tributeReferenceReadOnly]', FIELD_NAME = 'distributionLines' where FIELD_DEFINITION_ID = 'recurringGift.customFieldMap[tributeReferenceReadOnly]';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'recurringGift.distributionLines.customFieldMap[other_tributeReferenceReadOnly]', FIELD_NAME = 'distributionLines', DEFAULT_LABEL = 'Tribute Reference' where FIELD_DEFINITION_ID = 'recurringGift.customFieldMap[other_tributeReferenceReadOnly]';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'recurringGift.distributionLines.customFieldMap[tributeOccasionReadOnly]', FIELD_NAME = 'distributionLines' where FIELD_DEFINITION_ID = 'recurringGift.customFieldMap[tributeOccasionReadOnly]';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'recurringGift.distributionLines.customFieldMap[additional_tributeOccasionReadOnly]', FIELD_NAME = 'distributionLines', DEFAULT_LABEL = 'Tribute Occasion' where FIELD_DEFINITION_ID = 'recurringGift.customFieldMap[additional_tributeOccasionReadOnly]';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'recurringGift.distributionLines.customFieldMap[onBehalfOfReadOnly]', FIELD_NAME = 'distributionLines' where FIELD_DEFINITION_ID = 'recurringGift.customFieldMap[onBehalfOfReadOnly]';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'recurringGift.distributionLines.customFieldMap[other_onBehalfOfReadOnly]', FIELD_NAME = 'distributionLines', DEFAULT_LABEL = 'On Behalf Of' where FIELD_DEFINITION_ID = 'recurringGift.customFieldMap[other_onBehalfOfReadOnly]';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'recurringGift.distributionLines.customFieldMap[anonymousReadOnly]', FIELD_NAME = 'distributionLines' where FIELD_DEFINITION_ID = 'recurringGift.customFieldMap[anonymousReadOnly]';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'recurringGift.distributionLines.customFieldMap[recognitionNameReadOnly]', FIELD_NAME = 'distributionLines' where FIELD_DEFINITION_ID = 'recurringGift.customFieldMap[recognitionNameReadOnly]';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'recurringGift.distributionLines.customFieldMap[notifiedReadOnly]', FIELD_NAME = 'distributionLines' where FIELD_DEFINITION_ID = 'recurringGift.customFieldMap[notifiedReadOnly]';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'recurringGift.distributionLines.customFieldMap[other_notifiedReadOnly]', FIELD_NAME = 'distributionLines', DEFAULT_LABEL = 'To Be Notified' where FIELD_DEFINITION_ID = 'recurringGift.customFieldMap[other_notifiedReadOnly]';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'recurringGift.distributionLines.customFieldMap[messageReadOnly]', FIELD_NAME = 'distributionLines' where FIELD_DEFINITION_ID = 'recurringGift.customFieldMap[messageReadOnly]';

UPDATE FIELD_DEFINITION set FIELD_DEFINITION_ID = 'recurringGift.distributionLines.customFieldMap[taxDeductibleReadOnly]', FIELD_NAME = 'distributionLines' where FIELD_DEFINITION_ID = 'recurringGift.customFieldMap[taxDeductibleReadOnly]';





/* New by alex */
DELETE FROM `FIELD_DEFINITION`  WHERE (`FIELD_DEFINITION_ID` = 'pledge.constituent.organizationName') ;
DELETE FROM `FIELD_DEFINITION`  WHERE (`FIELD_DEFINITION_ID` = 'pledge.constituent.lastName') ;
DELETE FROM `FIELD_DEFINITION`  WHERE (`FIELD_DEFINITION_ID` = 'recurringGift.constituent.primaryAddress') ;
DELETE FROM `FIELD_DEFINITION`  WHERE (`FIELD_DEFINITION_ID` = 'recurringGift.constituent.organizationName') ;
DELETE FROM `FIELD_DEFINITION`  WHERE (`FIELD_DEFINITION_ID` = 'recurringGift.constituent.lastName') ;
DELETE FROM `FIELD_DEFINITION`  WHERE (`FIELD_DEFINITION_ID` = 'recurringGift.constituent.firstName') ;
DELETE FROM `FIELD_DEFINITION`  WHERE (`FIELD_DEFINITION_ID` = 'recurringGift.constituent.accountNumber') ;
DELETE FROM `FIELD_DEFINITION`  WHERE (`FIELD_DEFINITION_ID` = 'pledge.constituent.firstName') ;
DELETE FROM `FIELD_DEFINITION`  WHERE (`FIELD_DEFINITION_ID` = 'pledge.constituent.primaryAddress') ;
DELETE FROM `FIELD_DEFINITION`  WHERE (`FIELD_DEFINITION_ID` = 'pledge.constituent.accountNumber') ;

INSERT INTO `FIELD_DEFINITION` (FIELD_DEFINITION_ID, DEFAULT_LABEL, ENTITY_TYPE, FIELD_NAME, FIELD_TYPE) VALUES ('recurringGift.id', 'Reference Number', 'recurringGift', 'id', 'READ_ONLY_TEXT');
INSERT INTO `FIELD_DEFINITION` (FIELD_DEFINITION_ID, DEFAULT_LABEL, ENTITY_TYPE, FIELD_NAME, FIELD_TYPE) VALUES ('paymentHistory.paymentStatus', 'Payment Status', 'paymentHistory', 'paymentStatus', 'READ_ONLY_TEXT');
INSERT INTO `FIELD_DEFINITION` (FIELD_DEFINITION_ID, DEFAULT_LABEL, ENTITY_TYPE, FIELD_NAME, FIELD_TYPE) VALUES ('pledge.id', 'Reference Number', 'pledge', 'id', 'READ_ONLY_TEXT');
INSERT INTO `FIELD_DEFINITION` (FIELD_DEFINITION_ID, DEFAULT_LABEL, ENTITY_TYPE, FIELD_NAME, FIELD_TYPE) VALUES ('constituent.primaryAddress.addressLine3', 'Address', 'constituent', 'primaryAddress', 'TEXT');

UPDATE `FIELD_DEFINITION` SET `ENTITY_TYPE`='phone', `FIELD_NAME`='customFieldMap[phoneType]'  WHERE (`FIELD_DEFINITION_ID` = 'phone.customFieldMap[phoneTypeReadOnly]') ;
UPDATE `FIELD_DEFINITION` SET `FIELD_TYPE`='CODE_OTHER_DISPLAY'  WHERE (`FIELD_DEFINITION_ID` = 'pledge.distributionLines.projectCodeReadOnly') ;
UPDATE `FIELD_DEFINITION` SET `FIELD_TYPE`='CODE_OTHER_DISPLAY'  WHERE (`FIELD_DEFINITION_ID` = 'recurringGift.distributionLines.motivationCodeReadOnly') ;
UPDATE `FIELD_DEFINITION` SET `FIELD_TYPE`='CODE_OTHER_DISPLAY'  WHERE (`FIELD_DEFINITION_ID` = 'recurringGift.distributionLines.projectCodeReadOnly') ;
UPDATE `FIELD_DEFINITION` SET `FIELD_TYPE`='CODE_OTHER_DISPLAY'  WHERE (`FIELD_DEFINITION_ID` = 'pledge.distributionLines.motivationCodeReadOnly') ;
UPDATE `FIELD_DEFINITION` SET `FIELD_DEFINITION_ID`='paymentSource.phone.numberReadOnly',`ENTITY_ATTRIBUTES`='existingPhone', `FIELD_NAME`='phone'  WHERE (`FIELD_DEFINITION_ID` = 'paymentSource.selectedPhone.numberReadOnly') ;

update FIELD_DEFINITION set FIELD_TYPE ='PICKLIST_DISPLAY' where FIELD_DEFINITION_ID = 'gift.posted';

/* End of new by alex */

	/*End   of batch : 1 */
/* SYNC TABLE : `SECTION_DEFINITION` */

	/*Start of batch : 1 */
/* New by alex */
DELETE FROM `SECTION_FIELD`  WHERE (`FIELD_DEFINITION_ID` = 'recurringGift.constituent.accountNumber' AND `SECONDARY_FIELD_DEFINITION_ID` = 'constituent.accountNumber') ;
DELETE FROM `SECTION_FIELD`  WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='giftInKindList.resultsInfo' AND PAGE_TYPE='giftInKindList') AND `FIELD_DEFINITION_ID` = 'giftInKind.transactionDate' AND `SECONDARY_FIELD_DEFINITION_ID` IS NULL) ;
/* End new by alex */

UPDATE `SECTION_DEFINITION` SET `LAYOUT_TYPE`='ADJUSTED_DISTRIBUTION_LINE_GRID' WHERE (`SECTION_NAME` = 'adjustedGift.distribution');
UPDATE `SECTION_DEFINITION` SET `SECTION_ORDER`='6'  WHERE (`SECTION_NAME`='recurringGift.extendedDistribution' AND `PAGE_TYPE`='recurringGift') ;
UPDATE `SECTION_DEFINITION` SET `LAYOUT_TYPE`='DISTRIBUTION_LINE_GRID'  WHERE (`SECTION_NAME` = 'gift.distribution' AND `PAGE_TYPE`='gift') ;
UPDATE `SECTION_DEFINITION` SET `LAYOUT_TYPE`='DISTRIBUTION_LINE_GRID_DISPLAY'  WHERE (`PAGE_TYPE`='giftView' AND `SECTION_NAME`='gift.distribution') ;
UPDATE `SECTION_DEFINITION` SET `LAYOUT_TYPE`='GIFT_IN_KIND_GRID'  WHERE (`PAGE_TYPE`='giftInKind' AND `SECTION_NAME`='giftInKind.details') ;
UPDATE `SECTION_DEFINITION` SET `LAYOUT_TYPE`='DISTRIBUTION_LINE_GRID_DISPLAY'  WHERE (`PAGE_TYPE`='recurringGiftView' AND `SECTION_NAME`='recurringGift.distribution') ;
UPDATE `SECTION_DEFINITION` SET `DEFAULT_LABEL`='ACH' WHERE (`PAGE_TYPE`='recurringGiftView' AND `SECTION_NAME`='recurringGift.ach') ;
UPDATE `SECTION_DEFINITION` SET `DEFAULT_LABEL`='Credit Card' WHERE (`PAGE_TYPE`='recurringGiftView' AND `SECTION_NAME`='recurringGift.creditCard') ;
UPDATE `SECTION_DEFINITION` SET `DEFAULT_LABEL`='ACH' WHERE (`PAGE_TYPE`='recurringGift' AND `SECTION_NAME`='recurringGift.ach') ;
UPDATE `SECTION_DEFINITION` SET `DEFAULT_LABEL`='Credit Card' WHERE (`PAGE_TYPE`='recurringGift' AND `SECTION_NAME`='recurringGift.creditCard') ;
UPDATE `SECTION_DEFINITION` SET `LAYOUT_TYPE`='DISTRIBUTION_LINE_GRID', `SECTION_ORDER`='5'  WHERE (`PAGE_TYPE`='recurringGift' AND `SECTION_NAME`='recurringGift.distribution') ;
UPDATE `SECTION_DEFINITION` SET `DEFAULT_LABEL`='Credit Card'  WHERE (`PAGE_TYPE`='gift' AND `SECTION_NAME`='gift.creditCard') ;
UPDATE `SECTION_DEFINITION` SET `LAYOUT_TYPE`='ONE_COLUMN_HIDDEN' WHERE (`PAGE_TYPE`='paymentManagerEdit' AND `SECTION_NAME`='paymentSource.creditCard') ;
UPDATE `SECTION_DEFINITION` SET `LAYOUT_TYPE`='ONE_COLUMN_HIDDEN' WHERE (`PAGE_TYPE`='paymentManagerEdit' AND `SECTION_NAME`='paymentSource.ach') ;
UPDATE `SECTION_DEFINITION` SET `LAYOUT_TYPE`='DISTRIBUTION_LINE_GRID_DISPLAY' WHERE (`PAGE_TYPE`='pledgeView' AND `SECTION_NAME`='pledge.distribution') ;
UPDATE `SECTION_DEFINITION` SET `DEFAULT_LABEL`='ACH' WHERE (`PAGE_TYPE`='gift' AND `SECTION_NAME`='gift.ach') ;
UPDATE `SECTION_DEFINITION` SET `DEFAULT_LABEL`='Check' WHERE (`PAGE_TYPE`='gift' AND `SECTION_NAME`='gift.check') ;
UPDATE `SECTION_DEFINITION` SET `DEFAULT_LABEL`='All Addresses', `PAGE_TYPE`='addressList', `SECTION_NAME`='addressList.resultsInfo'  WHERE (`SECTION_NAME` = 'address.resultsInfo' AND `PAGE_TYPE` = 'address') ;
UPDATE `SECTION_DEFINITION` SET `DEFAULT_LABEL`='All Emails', `PAGE_TYPE`='emailList', `SECTION_NAME`='emailList.resultsInfo'  WHERE (`SECTION_NAME` = 'email.resultsInfo' AND `PAGE_TYPE` = 'email') ;
UPDATE `SECTION_DEFINITION` SET `LAYOUT_TYPE`='DISTRIBUTION_LINE_GRID' WHERE (`PAGE_TYPE`='pledge' AND `SECTION_NAME`='pledge.distribution') ;
UPDATE `SECTION_DEFINITION` SET `DEFAULT_LABEL`='All Phones', `PAGE_TYPE`='phoneList', `SECTION_NAME`='phoneList.resultsInfo'  WHERE (`SECTION_NAME` = 'phone.resultsInfo' AND `PAGE_TYPE` = 'phone') ;


/* New by alex */
UPDATE `SECTION_DEFINITION` SET `DEFAULT_LABEL`='All Gifts-In-Kind' WHERE (`PAGE_TYPE`='giftInKindList' and `SECTION_NAME`='giftInKindList.resultsInfo') ;
UPDATE `SECTION_DEFINITION` SET `DEFAULT_LABEL`='All Payment History', `PAGE_TYPE`='paymentHistoryList' WHERE (`SECTION_NAME`='paymentHistoryList.resultsInfo' AND `PAGE_TYPE`='paymentHistory') ;
UPDATE `SECTION_DEFINITION` SET `DEFAULT_LABEL`='All Recurring Gifts' WHERE (`PAGE_TYPE`='recurringGiftList' AND `SECTION_NAME`='recurringGiftList.resultsInfo') ;
UPDATE `SECTION_DEFINITION` SET `DEFAULT_LABEL`='All Pledges' WHERE (`PAGE_TYPE`='pledgeList' AND `SECTION_NAME`='pledgeList.resultsInfo') ;
UPDATE `SECTION_DEFINITION` SET `DEFAULT_LABEL`='All Communication History' WHERE (`PAGE_TYPE`='communicationHistoryList' AND `SECTION_NAME`='communicationHistoryList.resultsInfo') ;
UPDATE `SECTION_DEFINITION` SET `DEFAULT_LABEL`='All Payment Sources', `PAGE_TYPE`='paymentSourceList', `SECTION_NAME`='paymentSourceList.resultsInfo'  WHERE (`PAGE_TYPE`='paymentSource' AND `SECTION_NAME`='paymentSource.resultsInfo') ;


/* End of new by alex */


	/*End   of batch : 1 */
/* SYNC TABLE : `SECTION_FIELD` */

	/*Start of batch : 1 */
-- DELETES
DELETE FROM SECTION_FIELD WHERE SECTION_DEFINITION_ID IN (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME = 'communicationHistorySearchResults.resultsInfo');
DELETE FROM `SECTION_DEFINITION`  WHERE (`SECTION_NAME` = 'communicationHistorySearchResults.resultsInfo');-- 1000133

DELETE FROM SECTION_FIELD WHERE SECTION_DEFINITION_ID IN (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME = 'communicationHistorySearch.searchInfo');
DELETE FROM `SECTION_DEFINITION`  WHERE (`SECTION_NAME` = 'communicationHistorySearch.searchInfo');-- 1000132

DELETE FROM SECTION_FIELD WHERE SECTION_DEFINITION_ID IN (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME = 'recurringGift.editAch');
DELETE FROM `SECTION_DEFINITION`  WHERE (`SECTION_NAME` = 'recurringGift.editAch');-- 1000104

DELETE FROM SECTION_FIELD WHERE SECTION_DEFINITION_ID IN (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME = 'recurringGift.editCreditCard');
DELETE FROM `SECTION_DEFINITION`  WHERE (`SECTION_NAME` = 'recurringGift.editCreditCard');-- 1000103

DELETE FROM SECTION_FIELD WHERE SECTION_DEFINITION_ID IN (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME = 'gift.editCreditCard');
DELETE FROM `SECTION_DEFINITION`  WHERE (`SECTION_NAME` = 'gift.editCreditCard');-- 1000045

DELETE FROM SECTION_FIELD WHERE SECTION_DEFINITION_ID IN (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME = 'gift.editAch');
DELETE FROM `SECTION_DEFINITION`  WHERE (`SECTION_NAME` = 'gift.editAch');-- 1000083

DELETE FROM SECTION_FIELD WHERE SECTION_DEFINITION_ID IN (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME = 'recurringGiftSearch.searchInfo');
DELETE FROM `SECTION_DEFINITION`  WHERE (`SECTION_NAME` = 'recurringGiftSearch.searchInfo');-- 1000092

DELETE FROM SECTION_FIELD WHERE SECTION_DEFINITION_ID IN (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME = 'recurringGiftSearchResults.resultsInfo');
DELETE FROM `SECTION_DEFINITION`  WHERE (`SECTION_NAME` = 'recurringGiftSearchResults.resultsInfo');-- 1000093

DELETE FROM SECTION_FIELD WHERE SECTION_DEFINITION_ID IN (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME = 'pledgeSearch.searchInfo');
DELETE FROM `SECTION_DEFINITION`  WHERE (`SECTION_NAME` = 'pledgeSearch.searchInfo');-- 1000079

DELETE FROM SECTION_FIELD WHERE SECTION_DEFINITION_ID IN (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME = 'pledgeSearchResults.resultsInfo');
DELETE FROM `SECTION_DEFINITION`  WHERE (`SECTION_NAME` = 'pledgeSearchResults.resultsInfo');-- 1000080

DELETE FROM `SECTION_FIELD`  WHERE (`SECTION_DEFINITION_ID` = 1000052 AND `FIELD_DEFINITION_ID` = 'address.temporaryStartDate') AND `SECONDARY_FIELD_DEFINITION_ID` IS NULL;
DELETE FROM `SECTION_FIELD`  WHERE (`SECTION_DEFINITION_ID` = 1000052 AND `FIELD_DEFINITION_ID` = 'address.activationStatus') AND `SECONDARY_FIELD_DEFINITION_ID` IS NULL;
DELETE FROM `SECTION_FIELD`  WHERE (`SECTION_DEFINITION_ID` = 1000052 AND `FIELD_DEFINITION_ID` = 'address.addressLine2') AND `SECONDARY_FIELD_DEFINITION_ID` IS NULL;
DELETE FROM `SECTION_FIELD`  WHERE (`SECTION_DEFINITION_ID` = 1000052 AND `FIELD_DEFINITION_ID` = 'address.addressLine3') AND `SECONDARY_FIELD_DEFINITION_ID` IS NULL;
DELETE FROM `SECTION_FIELD`  WHERE (`SECTION_DEFINITION_ID` = 1000052 AND `FIELD_DEFINITION_ID` = 'address.cassDate') AND `SECONDARY_FIELD_DEFINITION_ID` IS NULL;
DELETE FROM `SECTION_FIELD`  WHERE (`SECTION_DEFINITION_ID` = 1000052 AND `FIELD_DEFINITION_ID` = 'address.comments') AND `SECONDARY_FIELD_DEFINITION_ID` IS NULL;
DELETE FROM `SECTION_FIELD`  WHERE (`SECTION_DEFINITION_ID` = 1000052 AND `FIELD_DEFINITION_ID` = 'address.effectiveDate') AND `SECONDARY_FIELD_DEFINITION_ID` IS NULL;
DELETE FROM `SECTION_FIELD`  WHERE (`SECTION_DEFINITION_ID` = 1000052 AND `FIELD_DEFINITION_ID` = 'address.temporaryEndDate') AND `SECONDARY_FIELD_DEFINITION_ID` IS NULL;
DELETE FROM `SECTION_FIELD`  WHERE (`SECTION_DEFINITION_ID` = 1000052 AND `FIELD_DEFINITION_ID` = 'address.ncoaDate') AND `SECONDARY_FIELD_DEFINITION_ID` IS NULL;
DELETE FROM `SECTION_FIELD`  WHERE (`SECTION_DEFINITION_ID` = 1000052 AND `FIELD_DEFINITION_ID` = 'address.seasonalStartDate') AND `SECONDARY_FIELD_DEFINITION_ID` IS NULL;
DELETE FROM `SECTION_FIELD`  WHERE (`SECTION_DEFINITION_ID` = 1000052 AND `FIELD_DEFINITION_ID` = 'address.seasonalEndDate') AND `SECONDARY_FIELD_DEFINITION_ID` IS NULL;
INSERT INTO `SECTION_FIELD`(FIELD_ORDER,FIELD_DEFINITION_ID,SECONDARY_FIELD_DEFINITION_ID,SECTION_DEFINITION_ID,SITE_NAME) VALUES ('200', 'address.id', NULL, '1000052', NULL);
INSERT INTO `SECTION_FIELD`(FIELD_ORDER,FIELD_DEFINITION_ID,SECONDARY_FIELD_DEFINITION_ID,SECTION_DEFINITION_ID,SITE_NAME) VALUES ('8000', 'address.current', NULL, '1000052', NULL);

DELETE FROM `SECTION_FIELD`  WHERE (`SECTION_DEFINITION_ID` = 1000056 AND `FIELD_DEFINITION_ID` = 'phone.temporaryStartDate' AND `SECONDARY_FIELD_DEFINITION_ID` IS NULL);
DELETE FROM `SECTION_FIELD`  WHERE (`SECTION_DEFINITION_ID` = 1000056 AND `FIELD_DEFINITION_ID` = 'phone.temporaryEndDate' AND `SECONDARY_FIELD_DEFINITION_ID` IS NULL);
DELETE FROM `SECTION_FIELD`  WHERE (`SECTION_DEFINITION_ID` = 1000056 AND `FIELD_DEFINITION_ID` = 'phone.seasonalStartDate' AND `SECONDARY_FIELD_DEFINITION_ID` IS NULL);
DELETE FROM `SECTION_FIELD`  WHERE (`SECTION_DEFINITION_ID` = 1000056 AND `FIELD_DEFINITION_ID` = 'phone.seasonalEndDate' AND `SECONDARY_FIELD_DEFINITION_ID` IS NULL);
DELETE FROM `SECTION_FIELD`  WHERE (`SECTION_DEFINITION_ID` = 1000056 AND `FIELD_DEFINITION_ID` = 'phone.effectiveDate' AND `SECONDARY_FIELD_DEFINITION_ID` IS NULL);
DELETE FROM `SECTION_FIELD`  WHERE (`SECTION_DEFINITION_ID` = 1000056 AND `FIELD_DEFINITION_ID` = 'phone.comments' AND `SECONDARY_FIELD_DEFINITION_ID` IS NULL);
DELETE FROM `SECTION_FIELD`  WHERE (`SECTION_DEFINITION_ID` = 1000056 AND `FIELD_DEFINITION_ID` = 'phone.activationStatus' AND `SECONDARY_FIELD_DEFINITION_ID` IS NULL);
INSERT INTO `SECTION_FIELD`(FIELD_ORDER,FIELD_DEFINITION_ID,SECONDARY_FIELD_DEFINITION_ID,SECTION_DEFINITION_ID,SITE_NAME) VALUES ('200', 'phone.id', NULL, '1000056', NULL);
INSERT INTO `SECTION_FIELD`(FIELD_ORDER,FIELD_DEFINITION_ID,SECONDARY_FIELD_DEFINITION_ID,SECTION_DEFINITION_ID,SITE_NAME) VALUES ('11000', 'phone.current', NULL, '1000056', NULL);

DELETE FROM `SECTION_FIELD`  WHERE (`SECTION_DEFINITION_ID` = 1000053 AND `FIELD_DEFINITION_ID` = 'email.temporaryStartDate' AND `SECONDARY_FIELD_DEFINITION_ID` IS NULL);
DELETE FROM `SECTION_FIELD`  WHERE (`SECTION_DEFINITION_ID` = 1000053 AND `FIELD_DEFINITION_ID` = 'email.temporaryEndDate' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
DELETE FROM `SECTION_FIELD`  WHERE (`SECTION_DEFINITION_ID` = 1000053 AND `FIELD_DEFINITION_ID` = 'email.seasonalStartDate' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
DELETE FROM `SECTION_FIELD`  WHERE (`SECTION_DEFINITION_ID` = 1000053 AND `FIELD_DEFINITION_ID` = 'email.seasonalEndDate' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
DELETE FROM `SECTION_FIELD`  WHERE (`SECTION_DEFINITION_ID` = 1000053 AND `FIELD_DEFINITION_ID` = 'email.effectiveDate' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
DELETE FROM `SECTION_FIELD`  WHERE (`SECTION_DEFINITION_ID` = 1000053 AND `FIELD_DEFINITION_ID` = 'email.comments' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
DELETE FROM `SECTION_FIELD`  WHERE (`SECTION_DEFINITION_ID` = 1000053 AND `FIELD_DEFINITION_ID` = 'email.activationStatus' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
INSERT INTO `SECTION_FIELD` (FIELD_ORDER,FIELD_DEFINITION_ID,SECONDARY_FIELD_DEFINITION_ID,SECTION_DEFINITION_ID,SITE_NAME)VALUES ('11500', 'email.current', NULL, '1000053', NULL);
INSERT INTO `SECTION_FIELD` (FIELD_ORDER,FIELD_DEFINITION_ID,SECONDARY_FIELD_DEFINITION_ID,SECTION_DEFINITION_ID,SITE_NAME)VALUES ('200', 'email.id', NULL, '1000053', NULL);

-- alex edited
UPDATE `SECTION_DEFINITION` SET `LAYOUT_TYPE`='ADJUSTED_DISTRIBUTION_LINE_GRID' WHERE (`SECTION_NAME` = 'adjustedGift.distribution');
UPDATE `SECTION_DEFINITION` SET `SECTION_ORDER`='6'  WHERE (`SECTION_NAME`='recurringGift.extendedDistribution' AND `PAGE_TYPE`='recurringGift');
UPDATE `SECTION_DEFINITION` SET `LAYOUT_TYPE`='DISTRIBUTION_LINE_GRID'  WHERE (`SECTION_NAME` = 'gift.distribution' AND `PAGE_TYPE`='gift');
UPDATE `SECTION_DEFINITION` SET `LAYOUT_TYPE`='DISTRIBUTION_LINE_GRID_DISPLAY'  WHERE (`PAGE_TYPE`='giftView' AND `SECTION_NAME`='gift.distribution');
UPDATE `SECTION_DEFINITION` SET `LAYOUT_TYPE`='GIFT_IN_KIND_GRID'  WHERE (`PAGE_TYPE`='giftInKind' AND `SECTION_NAME`='giftInKind.details');
UPDATE `SECTION_DEFINITION` SET `LAYOUT_TYPE`='DISTRIBUTION_LINE_GRID_DISPLAY'  WHERE (`PAGE_TYPE`='recurringGiftView' AND `SECTION_NAME`='recurringGift.distribution');
UPDATE `SECTION_DEFINITION` SET `DEFAULT_LABEL`='ACH' WHERE (`PAGE_TYPE`='recurringGiftView' AND `SECTION_NAME`='recurringGift.ach');
UPDATE `SECTION_DEFINITION` SET `DEFAULT_LABEL`='Credit Card' WHERE (`PAGE_TYPE`='recurringGiftView' AND `SECTION_NAME`='recurringGift.creditCard');
UPDATE `SECTION_DEFINITION` SET `DEFAULT_LABEL`='ACH' WHERE (`PAGE_TYPE`='recurringGift' AND `SECTION_NAME`='recurringGift.ach');
UPDATE `SECTION_DEFINITION` SET `DEFAULT_LABEL`='Credit Card' WHERE (`PAGE_TYPE`='recurringGift' AND `SECTION_NAME`='recurringGift.creditCard');
UPDATE `SECTION_DEFINITION` SET `LAYOUT_TYPE`='DISTRIBUTION_LINE_GRID', `SECTION_ORDER`='5'  WHERE (`PAGE_TYPE`='recurringGift' AND `SECTION_NAME`='recurringGift.distribution');
UPDATE `SECTION_DEFINITION` SET `DEFAULT_LABEL`='Credit Card'  WHERE (`PAGE_TYPE`='gift' AND `SECTION_NAME`='gift.creditCard');
UPDATE `SECTION_DEFINITION` SET `LAYOUT_TYPE`='ONE_COLUMN_HIDDEN' WHERE (`PAGE_TYPE`='paymentManagerEdit' AND `SECTION_NAME`='paymentSource.creditCard');
UPDATE `SECTION_DEFINITION` SET `LAYOUT_TYPE`='ONE_COLUMN_HIDDEN' WHERE (`PAGE_TYPE`='paymentManagerEdit' AND `SECTION_NAME`='paymentSource.ach');
UPDATE `SECTION_DEFINITION` SET `LAYOUT_TYPE`='DISTRIBUTION_LINE_GRID_DISPLAY' WHERE (`PAGE_TYPE`='pledgeView' AND `SECTION_NAME`='pledge.distribution');
UPDATE `SECTION_DEFINITION` SET `DEFAULT_LABEL`='ACH' WHERE (`PAGE_TYPE`='gift' AND `SECTION_NAME`='gift.ach');
UPDATE `SECTION_DEFINITION` SET `DEFAULT_LABEL`='Check' WHERE (`PAGE_TYPE`='gift' AND `SECTION_NAME`='gift.check');
UPDATE `SECTION_DEFINITION` SET `DEFAULT_LABEL`='All Addresses', `PAGE_TYPE`='addressList', `SECTION_NAME`='addressList.resultsInfo'  WHERE (`SECTION_NAME` = 'address.resultsInfo' AND `PAGE_TYPE` = 'address');
UPDATE `SECTION_DEFINITION` SET `DEFAULT_LABEL`='All Emails', `PAGE_TYPE`='emailList', `SECTION_NAME`='emailList.resultsInfo'  WHERE (`SECTION_NAME` = 'email.resultsInfo' AND `PAGE_TYPE` = 'email');
UPDATE `SECTION_DEFINITION` SET `LAYOUT_TYPE`='DISTRIBUTION_LINE_GRID' WHERE (`PAGE_TYPE`='pledge' AND `SECTION_NAME`='pledge.distribution');
UPDATE `SECTION_DEFINITION` SET `DEFAULT_LABEL`='All Phones', `PAGE_TYPE`='phoneList', `SECTION_NAME`='phoneList.resultsInfo'  WHERE (`SECTION_NAME` = 'phone.resultsInfo' AND `PAGE_TYPE` = 'phone');

-- comments field order - add 100 to last field order
SET @SEC_DEF=(SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE PAGE_TYPE='addressManager' AND SECTION_NAME='address.info');
SET @NEXT_ID=(SELECT MAX(FIELD_ORDER)+100 FROM SECTION_FIELD WHERE SECTION_DEFINITION_ID=@SEC_DEF);
UPDATE `SECTION_FIELD` SET `FIELD_ORDER`=@NEXT_ID WHERE `SECTION_DEFINITION_ID` =@SEC_DEF AND `FIELD_DEFINITION_ID` = 'address.comments' AND `SECONDARY_FIELD_DEFINITION_ID` IS NULL;

SET @SEC_DEF=(SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE PAGE_TYPE='addressManagerEdit' AND SECTION_NAME='address.edit');
SET @NEXT_ID=(SELECT MAX(FIELD_ORDER)+100 FROM SECTION_FIELD WHERE SECTION_DEFINITION_ID=@SEC_DEF);
UPDATE `SECTION_FIELD` SET `FIELD_ORDER`=@NEXT_ID WHERE `SECTION_DEFINITION_ID` =@SEC_DEF AND `FIELD_DEFINITION_ID` = 'address.comments' AND `SECONDARY_FIELD_DEFINITION_ID` IS NULL;

SET @SEC_DEF=(SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE PAGE_TYPE='emailManager' AND SECTION_NAME='email.info');
SET @NEXT_ID=(SELECT MAX(FIELD_ORDER)+100 FROM SECTION_FIELD WHERE SECTION_DEFINITION_ID=@SEC_DEF);
UPDATE `SECTION_FIELD` SET `FIELD_ORDER`=@NEXT_ID WHERE `SECTION_DEFINITION_ID` =@SEC_DEF AND `FIELD_DEFINITION_ID` = 'email.comments' AND `SECONDARY_FIELD_DEFINITION_ID` IS NULL;

SET @SEC_DEF=(SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE PAGE_TYPE='emailManagerEdit' AND SECTION_NAME='email.edit');
SET @NEXT_ID=(SELECT MAX(FIELD_ORDER)+100 FROM SECTION_FIELD WHERE SECTION_DEFINITION_ID=@SEC_DEF);
UPDATE `SECTION_FIELD` SET `FIELD_ORDER`=@NEXT_ID WHERE `SECTION_DEFINITION_ID` =@SEC_DEF AND `FIELD_DEFINITION_ID` = 'email.comments' AND `SECONDARY_FIELD_DEFINITION_ID` IS NULL;

SET @SEC_DEF=(SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE PAGE_TYPE='phoneManager' AND SECTION_NAME='phone.info');
SET @NEXT_ID=(SELECT MAX(FIELD_ORDER)+100 FROM SECTION_FIELD WHERE SECTION_DEFINITION_ID=@SEC_DEF);
UPDATE `SECTION_FIELD` SET `FIELD_ORDER`=@NEXT_ID WHERE `SECTION_DEFINITION_ID` =@SEC_DEF AND `FIELD_DEFINITION_ID` = 'phone.comments' AND `SECONDARY_FIELD_DEFINITION_ID` IS NULL;

SET @SEC_DEF=(SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE PAGE_TYPE='phoneManagerEdit' AND SECTION_NAME='phone.edit');
SET @NEXT_ID=(SELECT MAX(FIELD_ORDER)+100 FROM SECTION_FIELD WHERE SECTION_DEFINITION_ID=@SEC_DEF);
UPDATE `SECTION_FIELD` SET `FIELD_ORDER`=@NEXT_ID WHERE `SECTION_DEFINITION_ID` =@SEC_DEF AND `FIELD_DEFINITION_ID` = 'phone.comments' AND `SECONDARY_FIELD_DEFINITION_ID` IS NULL;

UPDATE `SECTION_FIELD` SET `FIELD_ORDER`='2000'  WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='addressList.resultsInfo' AND PAGE_TYPE='addressList') AND `FIELD_DEFINITION_ID` = 'address.addressLine1' AND `SECONDARY_FIELD_DEFINITION_ID` IS NULL);
UPDATE `SECTION_FIELD` SET `FIELD_ORDER`='3000'  WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='addressList.resultsInfo' AND PAGE_TYPE='addressList') AND `FIELD_DEFINITION_ID` = 'address.city' AND `SECONDARY_FIELD_DEFINITION_ID` IS NULL);
UPDATE `SECTION_FIELD` SET `FIELD_ORDER`='6000' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='addressList.resultsInfo' AND PAGE_TYPE='addressList') AND `FIELD_DEFINITION_ID` = 'address.country' AND `SECONDARY_FIELD_DEFINITION_ID` IS NULL);
UPDATE `SECTION_FIELD` SET `FIELD_ORDER`='11000' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='addressList.resultsInfo' AND PAGE_TYPE='addressList') AND `FIELD_DEFINITION_ID` = 'address.inactive' AND `SECONDARY_FIELD_DEFINITION_ID` IS NULL);
UPDATE `SECTION_FIELD` SET `FIELD_ORDER`='5000'  WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='addressList.resultsInfo' AND PAGE_TYPE='addressList') AND `FIELD_DEFINITION_ID` = 'address.postalCode' AND `SECONDARY_FIELD_DEFINITION_ID` IS NULL);
UPDATE `SECTION_FIELD` SET `FIELD_ORDER`='9000'  WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='addressList.resultsInfo' AND PAGE_TYPE='addressList') AND `FIELD_DEFINITION_ID` = 'address.primary' AND `SECONDARY_FIELD_DEFINITION_ID` IS NULL);
UPDATE `SECTION_FIELD` SET `FIELD_ORDER`='7000' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='addressList.resultsInfo' AND PAGE_TYPE='addressList') AND `FIELD_DEFINITION_ID` = 'address.receiveCorrespondence' AND `SECONDARY_FIELD_DEFINITION_ID` IS NULL);
UPDATE `SECTION_FIELD` SET `FIELD_ORDER`='4000'  WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='addressList.resultsInfo' AND PAGE_TYPE='addressList') AND `FIELD_DEFINITION_ID` = 'address.stateProvince' AND `SECONDARY_FIELD_DEFINITION_ID` IS NULL);
UPDATE `SECTION_FIELD` SET `FIELD_ORDER`='10000' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='addressList.resultsInfo' AND PAGE_TYPE='addressList') AND `FIELD_DEFINITION_ID` = 'address.undeliverable' AND `SECONDARY_FIELD_DEFINITION_ID` IS NULL);


UPDATE `SECTION_FIELD` SET `FIELD_ORDER`='2000'  WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='phoneList.resultsInfo' AND PAGE_TYPE='phoneList') AND `FIELD_DEFINITION_ID` = 'phone.number' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
UPDATE `SECTION_FIELD` SET `FIELD_ORDER`='3000'  WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='phoneList.resultsInfo' AND PAGE_TYPE='phoneList') AND `FIELD_DEFINITION_ID` = 'phone.provider' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
UPDATE `SECTION_FIELD` SET `FIELD_ORDER`='4000'  WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='phoneList.resultsInfo' AND PAGE_TYPE='phoneList') AND `FIELD_DEFINITION_ID` = 'phone.sms' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);

-- UPDATES I FIXED:

UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='constituent.primaryAddress.addressLine1' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='constituent.contactInfo' AND PAGE_TYPE='constituent') AND `FIELD_DEFINITION_ID` = 'constituent.primaryAddress' AND `SECONDARY_FIELD_DEFINITION_ID` = 'address.addressLine1');
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='constituent.primaryAddress.addressLine2' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='constituent.contactInfo' AND PAGE_TYPE='constituent') AND `FIELD_DEFINITION_ID` = 'constituent.primaryAddress' AND `SECONDARY_FIELD_DEFINITION_ID` = 'address.addressLine2');
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='constituent.primaryAddress.city' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='constituent.contactInfo' AND PAGE_TYPE='constituent') AND `FIELD_DEFINITION_ID` = 'constituent.primaryAddress' AND `SECONDARY_FIELD_DEFINITION_ID` = 'address.city');
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='constituent.primaryAddress.country' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='constituent.contactInfo' AND PAGE_TYPE='constituent') AND `FIELD_DEFINITION_ID` = 'constituent.primaryAddress' AND `SECONDARY_FIELD_DEFINITION_ID` = 'address.country');
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='constituent.primaryAddress.customFieldMap[addressType]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='constituent.contactInfo' AND PAGE_TYPE='constituent') AND `FIELD_DEFINITION_ID` = 'constituent.primaryAddress' AND `SECONDARY_FIELD_DEFINITION_ID` = 'address.customFieldMap[addressType]');
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='constituent.primaryAddress.postalCode' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='constituent.contactInfo' AND PAGE_TYPE='constituent') AND `FIELD_DEFINITION_ID` = 'constituent.primaryAddress' AND `SECONDARY_FIELD_DEFINITION_ID` = 'address.postalCode');
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='constituent.primaryAddress.stateProvince' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='constituent.contactInfo' AND PAGE_TYPE='constituent') AND `FIELD_DEFINITION_ID` = 'constituent.primaryAddress' AND `SECONDARY_FIELD_DEFINITION_ID` = 'address.stateProvince');
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='constituent.primaryEmail.customFieldMap[emailType]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='constituent.contactInfo' AND PAGE_TYPE='constituent') AND `FIELD_DEFINITION_ID` = 'constituent.primaryEmail' AND `SECONDARY_FIELD_DEFINITION_ID` = 'email.customFieldMap[emailType]');
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='constituent.primaryEmail.emailAddress' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='constituent.contactInfo' AND PAGE_TYPE='constituent') AND `FIELD_DEFINITION_ID` = 'constituent.primaryEmail' AND `SECONDARY_FIELD_DEFINITION_ID` = 'email.emailAddress');
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='constituent.primaryPhone.customFieldMap[phoneType]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='constituent.contactInfo' AND PAGE_TYPE='constituent') AND `FIELD_DEFINITION_ID` = 'constituent.primaryPhone' AND `SECONDARY_FIELD_DEFINITION_ID` = 'phone.customFieldMap[phoneType]');
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='constituent.primaryPhone.number' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='constituent.contactInfo' AND PAGE_TYPE='constituent') AND `FIELD_DEFINITION_ID` = 'constituent.primaryPhone' AND `SECONDARY_FIELD_DEFINITION_ID` = 'phone.number');

UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='constituent.primaryPhone.number' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='constituentSearch.searchInfo' AND PAGE_TYPE='constituentSearch') AND `FIELD_DEFINITION_ID` = 'constituent.primaryPhone' AND `SECONDARY_FIELD_DEFINITION_ID` = 'phone.number');
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='constituent.primaryEmail.emailAddress'  WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='constituentSearch.searchInfo' AND PAGE_TYPE='constituentSearch') AND `FIELD_DEFINITION_ID` = 'constituent.primaryEmail' AND `SECONDARY_FIELD_DEFINITION_ID` = 'email.emailAddress');
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='constituent.primaryAddress.stateProvince' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='constituentSearch.searchInfo' AND PAGE_TYPE='constituentSearch') AND `FIELD_DEFINITION_ID` = 'constituent.primaryAddress' AND `SECONDARY_FIELD_DEFINITION_ID` = 'address.stateProvince');
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='constituent.primaryAddress.postalCode' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='constituentSearch.searchInfo' AND PAGE_TYPE='constituentSearch') AND `FIELD_DEFINITION_ID` = 'constituent.primaryAddress' AND `SECONDARY_FIELD_DEFINITION_ID` = 'address.postalCode');
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='constituent.primaryAddress.city' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='constituentSearch.searchInfo' AND PAGE_TYPE='constituentSearch') AND `FIELD_DEFINITION_ID` = 'constituent.primaryAddress' AND `SECONDARY_FIELD_DEFINITION_ID` = 'address.city');
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='constituent.primaryAddress.addressLine1'  WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='constituentSearch.searchInfo' AND PAGE_TYPE='constituentSearch') AND `FIELD_DEFINITION_ID` = 'constituent.primaryAddress' AND `SECONDARY_FIELD_DEFINITION_ID` = 'address.addressLine1');

UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID= 'constituent.primaryEmail.emailAddress'  WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='constituentSearchResults.resultsInfo' AND PAGE_TYPE='constituentSearchResults') AND `FIELD_DEFINITION_ID` = 'constituent.primaryEmail' AND `SECONDARY_FIELD_DEFINITION_ID` = 'email.emailAddress');
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID= 'constituent.primaryAddress.stateProvince' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='constituentSearchResults.resultsInfo' AND PAGE_TYPE='constituentSearchResults') AND `FIELD_DEFINITION_ID` = 'constituent.primaryAddress' AND `SECONDARY_FIELD_DEFINITION_ID` = 'address.stateProvince');
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='constituent.primaryAddress.postalCode' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='constituentSearchResults.resultsInfo' AND PAGE_TYPE='constituentSearchResults') AND `FIELD_DEFINITION_ID` = 'constituent.primaryAddress' AND `SECONDARY_FIELD_DEFINITION_ID` = 'address.postalCode');
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='constituent.primaryAddress.city' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='constituentSearchResults.resultsInfo' AND PAGE_TYPE='constituentSearchResults') AND `FIELD_DEFINITION_ID` = 'constituent.primaryAddress' AND `SECONDARY_FIELD_DEFINITION_ID` = 'address.city');
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='constituent.primaryAddress.addressLine1' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='constituentSearchResults.resultsInfo' AND PAGE_TYPE='constituentSearchResults') AND `FIELD_DEFINITION_ID` = 'constituent.primaryAddress' AND `SECONDARY_FIELD_DEFINITION_ID` = 'address.addressLine1');

UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID= 'gift.paymentSource.creditCardNumberReadOnly',SECONDARY_FIELD_DEFINITION_ID='paymentSource.creditCardNumberReadOnly' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='gift.creditCard' AND PAGE_TYPE='giftView') AND `FIELD_DEFINITION_ID` = 'gift.selectedPaymentSource.creditCardNumberReadOnly' AND `SECONDARY_FIELD_DEFINITION_ID` = 'selectedPaymentSource.creditCardNumberReadOnly');
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID= 'gift.paymentSource.creditCardHolderNameReadOnly',SECONDARY_FIELD_DEFINITION_ID='paymentSource.creditCardHolderNameReadOnly' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='gift.creditCard' AND PAGE_TYPE='giftView') AND `FIELD_DEFINITION_ID` = 'gift.selectedPaymentSource.creditCardHolderNameReadOnly' AND `SECONDARY_FIELD_DEFINITION_ID` = 'selectedPaymentSource.creditCardHolderNameReadOnly');
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID=  'gift.paymentSource.creditCardExpirationDisplay',SECONDARY_FIELD_DEFINITION_ID='paymentSource.creditCardExpirationDisplay' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='gift.creditCard' AND PAGE_TYPE='giftView') AND `FIELD_DEFINITION_ID` = 'gift.selectedPaymentSource.creditCardExpirationDisplay' AND `SECONDARY_FIELD_DEFINITION_ID` = 'selectedPaymentSource.creditCardExpirationDisplay');
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID= 'gift.paymentSource.creditCardTypeReadOnly',SECONDARY_FIELD_DEFINITION_ID='paymentSource.creditCardTypeReadOnly' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='gift.creditCard' AND PAGE_TYPE='giftView') AND `FIELD_DEFINITION_ID` = 'gift.selectedPaymentSource.creditCardTypeReadOnly' AND `SECONDARY_FIELD_DEFINITION_ID` = 'selectedPaymentSource.creditCardTypeReadOnly');

UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='gift.paymentSource.achRoutingNumberReadOnly' ,SECONDARY_FIELD_DEFINITION_ID='paymentSource.achRoutingNumberReadOnly' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='gift.ach' AND PAGE_TYPE='giftView') AND `FIELD_DEFINITION_ID` = 'gift.selectedPaymentSource.achRoutingNumberReadOnly' AND `SECONDARY_FIELD_DEFINITION_ID` = 'selectedPaymentSource.achRoutingNumberReadOnly');
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID= 'gift.paymentSource.achHolderNameReadOnly',SECONDARY_FIELD_DEFINITION_ID='paymentSource.achHolderNameReadOnly' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='gift.ach' AND PAGE_TYPE='giftView') AND `FIELD_DEFINITION_ID` = 'gift.selectedPaymentSource.achHolderNameReadOnly' AND `SECONDARY_FIELD_DEFINITION_ID` = 'selectedPaymentSource.achHolderNameReadOnly');
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='gift.paymentSource.achAccountNumberReadOnly' ,SECONDARY_FIELD_DEFINITION_ID='paymentSource.achAccountNumberReadOnly' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='gift.ach' AND PAGE_TYPE='giftView') AND `FIELD_DEFINITION_ID` = 'gift.selectedPaymentSource.achAccountNumberReadOnly' AND `SECONDARY_FIELD_DEFINITION_ID` = 'selectedPaymentSource.achAccountNumberReadOnly');


UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='gift.distributionLines.projectCode' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.projectCode' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='gift.distribution' AND PAGE_TYPE='gift') AND `FIELD_DEFINITION_ID` = 'gift.projectCode' AND `SECONDARY_FIELD_DEFINITION_ID` IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='gift.distributionLines.percentage' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.percentage' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='gift.distribution' AND PAGE_TYPE='gift') AND `FIELD_DEFINITION_ID` = 'gift.percentage' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='gift.distributionLines.other_motivationCode' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.other_motivationCode' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='gift.distribution' AND PAGE_TYPE='gift') AND `FIELD_DEFINITION_ID` = 'gift.other_motivationCode' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='gift.distributionLines.motivationCode' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.motivationCode'  WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='gift.distribution' AND PAGE_TYPE='gift') AND `FIELD_DEFINITION_ID` = 'gift.motivationCode' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='gift.distributionLines.customFieldMap[reference]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[reference]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='gift.distribution' AND PAGE_TYPE='gift') AND `FIELD_DEFINITION_ID` = 'gift.customFieldMap[reference]' AND `SECONDARY_FIELD_DEFINITION_ID` IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='gift.distributionLines.customFieldMap[other_reference]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[other_reference]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='gift.distribution' AND PAGE_TYPE='gift') AND `FIELD_DEFINITION_ID` = 'gift.customFieldMap[other_reference]' AND `SECONDARY_FIELD_DEFINITION_ID` IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='gift.distributionLines.amount' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.amount' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='gift.distribution' AND PAGE_TYPE='gift') AND `FIELD_DEFINITION_ID` = 'gift.lineAmount' AND `SECONDARY_FIELD_DEFINITION_ID` IS NULL);

UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='gift.distributionLines.projectCodeReadOnly' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.projectCodeReadOnly' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='gift.distribution' AND PAGE_TYPE='giftView') AND `FIELD_DEFINITION_ID` = 'gift.projectCodeReadOnly' AND `SECONDARY_FIELD_DEFINITION_ID` IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='gift.distributionLines.percentageReadOnly' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.percentageReadOnly' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='gift.distribution' AND PAGE_TYPE='giftView') AND `FIELD_DEFINITION_ID` = 'gift.percentageReadOnly' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='gift.distributionLines.other_motivationCodeReadOnly' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.other_motivationCodeReadOnly' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='gift.distribution' AND PAGE_TYPE='giftView') AND `FIELD_DEFINITION_ID` = 'gift.other_motivationCodeReadOnly' AND `SECONDARY_FIELD_DEFINITION_ID` IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='gift.distributionLines.motivationCodeReadOnly' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.motivationCodeReadOnly' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='gift.distribution' AND PAGE_TYPE='giftView') AND `FIELD_DEFINITION_ID` = 'gift.motivationCodeReadOnly' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='gift.distributionLines.customFieldMap[referenceReadOnly]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[referenceReadOnly]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='gift.distribution' AND PAGE_TYPE='giftView') AND `FIELD_DEFINITION_ID` = 'gift.customFieldMap[referenceReadOnly]' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='gift.distributionLines.customFieldMap[other_referenceReadOnly]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[other_referenceReadOnly]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='gift.distribution' AND PAGE_TYPE='giftView') AND `FIELD_DEFINITION_ID` = 'gift.customFieldMap[other_referenceReadOnly]' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='gift.distributionLines.amountReadOnly' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.amountReadOnly' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='gift.distribution' AND PAGE_TYPE='giftView') AND `FIELD_DEFINITION_ID` = 'gift.lineAmountReadOnly' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);

UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='gift.phone.id' ,SECONDARY_FIELD_DEFINITION_ID='phone.id' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='gift.payment' AND PAGE_TYPE='gift') AND `FIELD_DEFINITION_ID` = 'gift.selectedPhone' AND `SECONDARY_FIELD_DEFINITION_ID` IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='gift.paymentSource.id' ,SECONDARY_FIELD_DEFINITION_ID='paymentSource.id' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='gift.payment' AND PAGE_TYPE='gift') AND `FIELD_DEFINITION_ID` = 'gift.selectedPaymentSource' AND `SECONDARY_FIELD_DEFINITION_ID` IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='gift.address.id' ,SECONDARY_FIELD_DEFINITION_ID='address.id' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='gift.payment' AND PAGE_TYPE='gift') AND `FIELD_DEFINITION_ID` = 'gift.selectedAddress' AND `SECONDARY_FIELD_DEFINITION_ID` IS NULL);

UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='gift.phone.numberReadOnly' ,SECONDARY_FIELD_DEFINITION_ID='phone.numberReadOnly' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='gift.payment' AND PAGE_TYPE='giftView') AND `FIELD_DEFINITION_ID` = 'gift.selectedPhone.numberReadOnly' AND `SECONDARY_FIELD_DEFINITION_ID` = 'selectedPhone.numberReadOnly');
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='gift.phone.customFieldMap[phoneTypeReadOnly]' ,SECONDARY_FIELD_DEFINITION_ID='phone.customFieldMap[phoneTypeReadOnly]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='gift.payment' AND PAGE_TYPE='giftView') AND `FIELD_DEFINITION_ID` = 'gift.selectedPhone.customFieldMap[phoneTypeReadOnly]' AND `SECONDARY_FIELD_DEFINITION_ID` = 'selectedPhone.customFieldMap[phoneTypeReadOnly]');
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='gift.address.stateProvinceReadOnly' ,SECONDARY_FIELD_DEFINITION_ID='address.stateProvinceReadOnly' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='gift.payment' AND PAGE_TYPE='giftView') AND `FIELD_DEFINITION_ID` = 'gift.selectedAddress.stateProvinceReadOnly' AND `SECONDARY_FIELD_DEFINITION_ID` = 'selectedAddress.stateProvinceReadOnly');
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='gift.address.postalCodeReadOnly' ,SECONDARY_FIELD_DEFINITION_ID='address.postalCodeReadOnly' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='gift.payment' AND PAGE_TYPE='giftView') AND `FIELD_DEFINITION_ID` = 'gift.selectedAddress.postalCodeReadOnly' AND `SECONDARY_FIELD_DEFINITION_ID` = 'selectedAddress.postalCodeReadOnly');
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='gift.address.customFieldMap[addressTypeReadOnly]' ,SECONDARY_FIELD_DEFINITION_ID='address.customFieldMap[addressTypeReadOnly]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='gift.payment' AND PAGE_TYPE='giftView') AND `FIELD_DEFINITION_ID` = 'gift.selectedAddress.customFieldMap[addressTypeReadOnly]' AND `SECONDARY_FIELD_DEFINITION_ID` = 'selectedAddress.customFieldMap[addressTypeReadOnly]');
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='gift.address.countryReadOnly' ,SECONDARY_FIELD_DEFINITION_ID='address.countryReadOnly' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='gift.payment' AND PAGE_TYPE='giftView') AND `FIELD_DEFINITION_ID` = 'gift.selectedAddress.countryReadOnly' AND `SECONDARY_FIELD_DEFINITION_ID` = 'selectedAddress.countryReadOnly');
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='gift.address.cityReadOnly' ,SECONDARY_FIELD_DEFINITION_ID='address.cityReadOnly' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='gift.payment' AND PAGE_TYPE='giftView') AND `FIELD_DEFINITION_ID` = 'gift.selectedAddress.cityReadOnly' AND `SECONDARY_FIELD_DEFINITION_ID` = 'selectedAddress.cityReadOnly');
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='gift.address.addressLine3ReadOnly' ,SECONDARY_FIELD_DEFINITION_ID='address.addressLine3ReadOnly' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='gift.payment' AND PAGE_TYPE='giftView') AND `FIELD_DEFINITION_ID` = 'gift.selectedAddress.addressLine3ReadOnly' AND `SECONDARY_FIELD_DEFINITION_ID` = 'selectedAddress.addressLine3ReadOnly');
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='gift.address.addressLine2ReadOnly' ,SECONDARY_FIELD_DEFINITION_ID='address.addressLine2ReadOnly' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='gift.payment' AND PAGE_TYPE='giftView') AND `FIELD_DEFINITION_ID` = 'gift.selectedAddress.addressLine2ReadOnly' AND `SECONDARY_FIELD_DEFINITION_ID` = 'selectedAddress.addressLine2ReadOnly');
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='gift.address.addressLine1ReadOnly' ,SECONDARY_FIELD_DEFINITION_ID='address.addressLine1ReadOnly' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='gift.payment' AND PAGE_TYPE='giftView') AND `FIELD_DEFINITION_ID` = 'gift.selectedAddress.addressLine1ReadOnly' AND `SECONDARY_FIELD_DEFINITION_ID` = 'selectedAddress.addressLine1ReadOnly');


UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='paymentSource.address.shortAddressReadOnly' ,SECONDARY_FIELD_DEFINITION_ID='address.shortDisplay' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='paymentSourceList.resultsInfo' AND PAGE_TYPE='paymentSource') AND `FIELD_DEFINITION_ID` = 'paymentSource.selectedAddress.shortAddressReadOnly' AND `SECONDARY_FIELD_DEFINITION_ID` = 'selectedAddress.shortDisplay');
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='paymentSource.phone.numberReadOnly' ,SECONDARY_FIELD_DEFINITION_ID= 'phone.numberReadOnly' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='paymentSourceList.resultsInfo' AND PAGE_TYPE='paymentSource') AND `FIELD_DEFINITION_ID` = 'paymentSource.selectedPhone.numberReadOnly' AND `SECONDARY_FIELD_DEFINITION_ID` = 'selectedPhone.numberReadOnly');

UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID= 'paymentSource.phone.id' ,SECONDARY_FIELD_DEFINITION_ID='phone.id' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='paymentSource.info' AND PAGE_TYPE='paymentManager') AND `FIELD_DEFINITION_ID` = 'paymentSource.selectedPhone' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='paymentSource.address.id' ,SECONDARY_FIELD_DEFINITION_ID='address.id' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='paymentSource.info' AND PAGE_TYPE='paymentManager') AND `FIELD_DEFINITION_ID` = 'paymentSource.selectedAddress' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);

UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='paymentSource.address.id' ,SECONDARY_FIELD_DEFINITION_ID='address.id' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='paymentSource.infoEdit' AND PAGE_TYPE='paymentManagerEdit') AND `FIELD_DEFINITION_ID` = 'paymentSource.selectedAddress' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='paymentSource.phone.id' ,SECONDARY_FIELD_DEFINITION_ID='phone.id' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='paymentSource.infoEdit' AND PAGE_TYPE='paymentManagerEdit') AND `FIELD_DEFINITION_ID` = 'paymentSource.selectedPhone' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);

UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='pledge.distributionLines.amount' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.amount' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='pledge.distribution' AND PAGE_TYPE='pledge') AND `FIELD_DEFINITION_ID` = 'pledge.lineAmount' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='pledge.distributionLines.customFieldMap[other_reference]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[other_reference]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='pledge.distribution' AND PAGE_TYPE='pledge') AND `FIELD_DEFINITION_ID` = 'pledge.customFieldMap[other_reference]' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='pledge.distributionLines.customFieldMap[reference]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[reference]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='pledge.distribution' AND PAGE_TYPE='pledge') AND `FIELD_DEFINITION_ID` = 'pledge.customFieldMap[reference]' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='pledge.distributionLines.motivationCode' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.motivationCode' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='pledge.distribution' AND PAGE_TYPE='pledge') AND `FIELD_DEFINITION_ID` = 'pledge.motivationCode' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='pledge.distributionLines.other_motivationCode' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.other_motivationCode' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='pledge.distribution' AND PAGE_TYPE='pledge') AND `FIELD_DEFINITION_ID` = 'pledge.other_motivationCode' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='pledge.distributionLines.percentage' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.percentage' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='pledge.distribution' AND PAGE_TYPE='pledge') AND `FIELD_DEFINITION_ID` = 'pledge.percentage' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='pledge.distributionLines.projectCode' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.projectCode' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='pledge.distribution' AND PAGE_TYPE='pledge') AND `FIELD_DEFINITION_ID` = 'pledge.projectCode' AND `SECONDARY_FIELD_DEFINITION_ID` IS NULL);

UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='pledge.distributionLines.amountReadOnly' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.amountReadOnly' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='pledge.distribution' AND PAGE_TYPE='pledgeView') AND `FIELD_DEFINITION_ID` = 'pledge.lineAmountReadOnly' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='pledge.distributionLines.customFieldMap[other_referenceReadOnly]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[other_referenceReadOnly]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='pledge.distribution' AND PAGE_TYPE='pledgeView') AND `FIELD_DEFINITION_ID` = 'pledge.customFieldMap[other_referenceReadOnly]' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='pledge.distributionLines.customFieldMap[referenceReadOnly]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[referenceReadOnly]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='pledge.distribution' AND PAGE_TYPE='pledgeView') AND `FIELD_DEFINITION_ID` = 'pledge.customFieldMap[referenceReadOnly]' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='pledge.distributionLines.motivationCodeReadOnly' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.motivationCodeReadOnly' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='pledge.distribution' AND PAGE_TYPE='pledgeView') AND `FIELD_DEFINITION_ID` = 'pledge.motivationCodeReadOnly' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='pledge.distributionLines.other_motivationCodeReadOnly' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.other_motivationCodeReadOnly' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='pledge.distribution' AND PAGE_TYPE='pledgeView') AND `FIELD_DEFINITION_ID` = 'pledge.other_motivationCodeReadOnly' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='pledge.distributionLines.percentageReadOnly' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.percentageReadOnly' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='pledge.distribution' AND PAGE_TYPE='pledgeView') AND `FIELD_DEFINITION_ID` = 'pledge.percentageReadOnly' AND `SECONDARY_FIELD_DEFINITION_ID` IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='pledge.distributionLines.projectCodeReadOnly' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.projectCodeReadOnly' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='pledge.distribution' AND PAGE_TYPE='pledgeView') AND `FIELD_DEFINITION_ID` = 'pledge.projectCodeReadOnly' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);

UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='recurringGift.distributionLines.amount' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.amount' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGift.distribution' AND PAGE_TYPE='recurringGift') AND `FIELD_DEFINITION_ID` = 'recurringGift.lineAmount' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='recurringGift.distributionLines.customFieldMap[other_reference]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[other_reference]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGift.distribution' AND PAGE_TYPE='recurringGift') AND `FIELD_DEFINITION_ID` = 'recurringGift.customFieldMap[other_reference]' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='recurringGift.distributionLines.customFieldMap[reference]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[reference]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGift.distribution' AND PAGE_TYPE='recurringGift') AND `FIELD_DEFINITION_ID` = 'recurringGift.customFieldMap[reference]' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='recurringGift.distributionLines.motivationCode' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.motivationCode' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGift.distribution' AND PAGE_TYPE='recurringGift') AND `FIELD_DEFINITION_ID` = 'recurringGift.motivationCode' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='recurringGift.distributionLines.other_motivationCode' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.other_motivationCode' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGift.distribution' AND PAGE_TYPE='recurringGift') AND `FIELD_DEFINITION_ID` = 'recurringGift.other_motivationCode' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='recurringGift.distributionLines.percentage' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.percentage' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGift.distribution' AND PAGE_TYPE='recurringGift') AND `FIELD_DEFINITION_ID` = 'recurringGift.percentage' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='recurringGift.distributionLines.projectCode' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.projectCode' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGift.distribution' AND PAGE_TYPE='recurringGift') AND `FIELD_DEFINITION_ID` = 'recurringGift.projectCode' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);

UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='recurringGift.paymentSource.creditCardExpirationDisplay' ,SECONDARY_FIELD_DEFINITION_ID='paymentSource.creditCardExpirationDisplay' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGift.creditCard' AND PAGE_TYPE='recurringGiftView') AND `FIELD_DEFINITION_ID` = 'recurringGift.selectedPaymentSource.creditCardExpirationDisplay' AND `SECONDARY_FIELD_DEFINITION_ID` = 'selectedPaymentSource.creditCardExpirationDisplay');
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='recurringGift.paymentSource.creditCardHolderNameReadOnly' ,SECONDARY_FIELD_DEFINITION_ID='paymentSource.creditCardHolderNameReadOnly' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGift.creditCard' AND PAGE_TYPE='recurringGiftView') AND `FIELD_DEFINITION_ID` = 'recurringGift.selectedPaymentSource.creditCardHolderNameReadOnly' AND `SECONDARY_FIELD_DEFINITION_ID` = 'selectedPaymentSource.creditCardHolderNameReadOnly');
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='recurringGift.paymentSource.creditCardNumberReadOnly' ,SECONDARY_FIELD_DEFINITION_ID='paymentSource.creditCardNumberReadOnly' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGift.creditCard' AND PAGE_TYPE='recurringGiftView') AND `FIELD_DEFINITION_ID` = 'recurringGift.selectedPaymentSource.creditCardNumberReadOnly' AND `SECONDARY_FIELD_DEFINITION_ID` = 'selectedPaymentSource.creditCardNumberReadOnly');
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='recurringGift.paymentSource.creditCardTypeReadOnly' ,SECONDARY_FIELD_DEFINITION_ID='paymentSource.creditCardTypeReadOnly' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGift.creditCard' AND PAGE_TYPE='recurringGiftView') AND `FIELD_DEFINITION_ID` = 'recurringGift.selectedPaymentSource.creditCardTypeReadOnly' AND `SECONDARY_FIELD_DEFINITION_ID` = 'selectedPaymentSource.creditCardTypeReadOnly');

UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='recurringGift.paymentSource.achAccountNumberReadOnly' ,SECONDARY_FIELD_DEFINITION_ID='paymentSource.achAccountNumberReadOnly' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGift.ach' AND PAGE_TYPE='recurringGiftView') AND `FIELD_DEFINITION_ID` = 'recurringGift.selectedPaymentSource.achAccountNumberReadOnly' AND `SECONDARY_FIELD_DEFINITION_ID` = 'selectedPaymentSource.achAccountNumberReadOnly');
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='recurringGift.paymentSource.achHolderNameReadOnly' ,SECONDARY_FIELD_DEFINITION_ID='paymentSource.achHolderNameReadOnly' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGift.ach' AND PAGE_TYPE='recurringGiftView') AND `FIELD_DEFINITION_ID` = 'recurringGift.selectedPaymentSource.achHolderNameReadOnly' AND `SECONDARY_FIELD_DEFINITION_ID` = 'selectedPaymentSource.achHolderNameReadOnly');
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='recurringGift.paymentSource.achRoutingNumberReadOnly' ,SECONDARY_FIELD_DEFINITION_ID='paymentSource.achRoutingNumberReadOnly' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGift.ach' AND PAGE_TYPE='recurringGiftView') AND `FIELD_DEFINITION_ID` = 'recurringGift.selectedPaymentSource.achRoutingNumberReadOnly' AND `SECONDARY_FIELD_DEFINITION_ID` = 'selectedPaymentSource.achRoutingNumberReadOnly');


UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='recurringGift.distributionLines.amountReadOnly' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.amountReadOnly' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGift.distribution' AND PAGE_TYPE='recurringGiftView') AND `FIELD_DEFINITION_ID` = 'recurringGift.lineAmountReadOnly' AND `SECONDARY_FIELD_DEFINITION_ID` IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='recurringGift.distributionLines.customFieldMap[other_referenceReadOnly]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[other_referenceReadOnly]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGift.distribution' AND PAGE_TYPE='recurringGiftView') AND `FIELD_DEFINITION_ID` = 'recurringGift.customFieldMap[other_referenceReadOnly]' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='recurringGift.distributionLines.customFieldMap[referenceReadOnly]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[referenceReadOnly]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGift.distribution' AND PAGE_TYPE='recurringGiftView') AND `FIELD_DEFINITION_ID` = 'recurringGift.customFieldMap[referenceReadOnly]' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='recurringGift.distributionLines.motivationCodeReadOnly' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.motivationCodeReadOnly' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGift.distribution' AND PAGE_TYPE='recurringGiftView') AND `FIELD_DEFINITION_ID` = 'recurringGift.motivationCode' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='recurringGift.distributionLines.other_motivationCodeReadOnly' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.other_motivationCodeReadOnly' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGift.distribution' AND PAGE_TYPE='recurringGiftView') AND `FIELD_DEFINITION_ID` = 'recurringGift.other_motivationCode' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='recurringGift.distributionLines.percentageReadOnly' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.percentageReadOnly' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGift.distribution' AND PAGE_TYPE='recurringGiftView') AND `FIELD_DEFINITION_ID` = 'recurringGift.percentageReadOnly' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='recurringGift.distributionLines.projectCodeReadOnly' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.projectCodeReadOnly' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGift.distribution' AND PAGE_TYPE='recurringGiftView') AND `FIELD_DEFINITION_ID` = 'recurringGift.projectCodeReadOnly' AND `SECONDARY_FIELD_DEFINITION_ID` IS NULL);

UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='communicationHistory.phone.id' ,SECONDARY_FIELD_DEFINITION_ID='phone.id' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='communicationHistory' AND PAGE_TYPE='communicationHistory') AND `FIELD_DEFINITION_ID` = 'communicationHistory.selectedPhone' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='communicationHistory.email.id' ,SECONDARY_FIELD_DEFINITION_ID='email.id' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='communicationHistory' AND PAGE_TYPE='communicationHistory') AND `FIELD_DEFINITION_ID` = 'communicationHistory.selectedEmail' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='communicationHistory.address.id' ,SECONDARY_FIELD_DEFINITION_ID='address.id' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='communicationHistory' AND PAGE_TYPE='communicationHistory') AND `FIELD_DEFINITION_ID` = 'communicationHistory.selectedAddress' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);


UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='communicationHistory.phone.numberReadOnly' ,SECONDARY_FIELD_DEFINITION_ID='phone.numberReadOnly' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='communicationHistory' AND PAGE_TYPE='communicationHistoryView') AND `FIELD_DEFINITION_ID` = 'communicationHistory.selectedPhone.numberReadOnly' AND `SECONDARY_FIELD_DEFINITION_ID` = 'selectedPhone.numberReadOnly');
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='communicationHistory.email.emailAddressReadOnly' ,SECONDARY_FIELD_DEFINITION_ID='email.emailAddressReadOnly' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='communicationHistory' AND PAGE_TYPE='communicationHistoryView') AND `FIELD_DEFINITION_ID` = 'communicationHistory.selectedEmail.emailAddressReadOnly' AND `SECONDARY_FIELD_DEFINITION_ID` = 'selectedEmail.emailAddressReadOnly');
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='communicationHistory.address.stateProvinceReadOnly' ,SECONDARY_FIELD_DEFINITION_ID='address.stateProvinceReadOnly' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='communicationHistory' AND PAGE_TYPE='communicationHistoryView') AND `FIELD_DEFINITION_ID` = 'communicationHistory.selectedAddress.stateProvinceReadOnly' AND `SECONDARY_FIELD_DEFINITION_ID` = 'selectedAddress.stateProvinceReadOnly');
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='communicationHistory.address.postalCodeReadOnly' ,SECONDARY_FIELD_DEFINITION_ID='address.postalCodeReadOnly' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='communicationHistory' AND PAGE_TYPE='communicationHistoryView') AND `FIELD_DEFINITION_ID` = 'communicationHistory.selectedAddress.postalCodeReadOnly' AND `SECONDARY_FIELD_DEFINITION_ID` = 'selectedAddress.postalCodeReadOnly');
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='communicationHistory.address.countryReadOnly' ,SECONDARY_FIELD_DEFINITION_ID='address.countryReadOnly' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='communicationHistory' AND PAGE_TYPE='communicationHistoryView') AND `FIELD_DEFINITION_ID` = 'communicationHistory.selectedAddress.countryReadOnly' AND `SECONDARY_FIELD_DEFINITION_ID` = 'selectedAddress.countryReadOnly');
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='communicationHistory.address.cityReadOnly', SECONDARY_FIELD_DEFINITION_ID='address.cityReadOnly' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='communicationHistory' AND PAGE_TYPE='communicationHistoryView') AND `FIELD_DEFINITION_ID` = 'communicationHistory.selectedAddress.cityReadOnly' AND `SECONDARY_FIELD_DEFINITION_ID` = 'selectedAddress.cityReadOnly');
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='communicationHistory.address.addressLine3ReadOnly' ,SECONDARY_FIELD_DEFINITION_ID='address.addressLine3ReadOnly' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='communicationHistory' AND PAGE_TYPE='communicationHistoryView') AND `FIELD_DEFINITION_ID` = 'communicationHistory.selectedAddress.addressLine3ReadOnly' AND `SECONDARY_FIELD_DEFINITION_ID` = 'selectedAddress.addressLine3ReadOnly');
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='communicationHistory.address.addressLine2ReadOnly' ,SECONDARY_FIELD_DEFINITION_ID='address.addressLine2ReadOnly' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='communicationHistory' AND PAGE_TYPE='communicationHistoryView') AND `FIELD_DEFINITION_ID` = 'communicationHistory.selectedAddress.addressLine2ReadOnly' AND `SECONDARY_FIELD_DEFINITION_ID` = 'selectedAddress.addressLine2ReadOnly');
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='communicationHistory.address.addressLine1ReadOnly' ,SECONDARY_FIELD_DEFINITION_ID='address.addressLine1ReadOnly' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='communicationHistory' AND PAGE_TYPE='communicationHistoryView') AND `FIELD_DEFINITION_ID` = 'communicationHistory.selectedAddress.addressLine1ReadOnly' AND `SECONDARY_FIELD_DEFINITION_ID` = 'selectedAddress.addressLine1ReadOnly');

UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='gift.distributionLines.customFieldMap[tributeReference]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[tributeReference]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='gift.extendedDistribution' AND PAGE_TYPE='gift') AND `FIELD_DEFINITION_ID` = 'gift.customFieldMap[tributeReference]' AND `SECONDARY_FIELD_DEFINITION_ID` IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='gift.distributionLines.customFieldMap[tributeOccasion]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[tributeOccasion]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='gift.extendedDistribution' AND PAGE_TYPE='gift') AND `FIELD_DEFINITION_ID` = 'gift.customFieldMap[tributeOccasion]' AND `SECONDARY_FIELD_DEFINITION_ID` IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='gift.distributionLines.customFieldMap[taxDeductible]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[taxDeductible]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='gift.extendedDistribution' AND PAGE_TYPE='gift') AND `FIELD_DEFINITION_ID` = 'gift.customFieldMap[taxDeductible]' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='gift.distributionLines.customFieldMap[recognitionName]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[recognitionName]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='gift.extendedDistribution' AND PAGE_TYPE='gift') AND `FIELD_DEFINITION_ID` = 'gift.customFieldMap[recognitionName]' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='gift.distributionLines.customFieldMap[other_tributeReference]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[other_tributeReference]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='gift.extendedDistribution' AND PAGE_TYPE='gift') AND `FIELD_DEFINITION_ID` = 'gift.customFieldMap[other_tributeReference]' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='gift.distributionLines.customFieldMap[other_onBehalfOf]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[other_onBehalfOf]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='gift.extendedDistribution' AND PAGE_TYPE='gift') AND `FIELD_DEFINITION_ID` = 'gift.customFieldMap[other_onBehalfOf]' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='gift.distributionLines.customFieldMap[other_notified]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[other_notified]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='gift.extendedDistribution' AND PAGE_TYPE='gift') AND `FIELD_DEFINITION_ID` = 'gift.customFieldMap[other_notified]' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='gift.distributionLines.customFieldMap[onBehalfOf]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[onBehalfOf]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='gift.extendedDistribution' AND PAGE_TYPE='gift') AND `FIELD_DEFINITION_ID` = 'gift.customFieldMap[onBehalfOf]' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='gift.distributionLines.customFieldMap[notified]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[notified]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='gift.extendedDistribution' AND PAGE_TYPE='gift') AND `FIELD_DEFINITION_ID` = 'gift.customFieldMap[notified]' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='gift.distributionLines.customFieldMap[message]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[message]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='gift.extendedDistribution' AND PAGE_TYPE='gift') AND `FIELD_DEFINITION_ID` = 'gift.customFieldMap[message]' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='gift.distributionLines.customFieldMap[associatedRecurringGiftId]' ,SECONDARY_FIELD_DEFINITION_ID= 'distributionLines.customFieldMap[associatedRecurringGiftId]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='gift.extendedDistribution' AND PAGE_TYPE='gift') AND `FIELD_DEFINITION_ID` = 'gift.customFieldMap[associatedRecurringGiftId]' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='gift.distributionLines.customFieldMap[associatedPledgeId]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[associatedPledgeId]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='gift.extendedDistribution' AND PAGE_TYPE='gift') AND `FIELD_DEFINITION_ID` = 'gift.customFieldMap[associatedPledgeId]' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='gift.distributionLines.customFieldMap[anonymous]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[anonymous]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='gift.extendedDistribution' AND PAGE_TYPE='gift') AND `FIELD_DEFINITION_ID` = 'gift.customFieldMap[anonymous]' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='gift.distributionLines.customFieldMap[additional_tributeOccasion]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[additional_tributeOccasion]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='gift.extendedDistribution' AND PAGE_TYPE='gift') AND `FIELD_DEFINITION_ID` = 'gift.customFieldMap[additional_tributeOccasion]' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='gift.distributionLines.customFieldMap[tribute]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[tribute]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='gift.extendedDistribution' AND PAGE_TYPE='gift') AND `FIELD_DEFINITION_ID` = 'gift.customFieldMap[tribute]' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);

UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='gift.distributionLines.customFieldMap[notifiedReadOnly]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[notifiedReadOnly]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='gift.extendedDistribution' AND PAGE_TYPE='giftView') AND `FIELD_DEFINITION_ID` = 'gift.customFieldMap[notifiedReadOnly]' AND `SECONDARY_FIELD_DEFINITION_ID` IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='gift.distributionLines.customFieldMap[messageReadOnly]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[messageReadOnly]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='gift.extendedDistribution' AND PAGE_TYPE='giftView') AND `FIELD_DEFINITION_ID` = 'gift.customFieldMap[messageReadOnly]' AND `SECONDARY_FIELD_DEFINITION_ID` IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='gift.distributionLines.customFieldMap[associatedRecurringGiftIdReadOnly]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[associatedRecurringGiftIdReadOnly]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='gift.extendedDistribution' AND PAGE_TYPE='giftView') AND `FIELD_DEFINITION_ID` = 'gift.customFieldMap[associatedRecurringGiftIdReadOnly]' AND `SECONDARY_FIELD_DEFINITION_ID` IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='gift.distributionLines.customFieldMap[associatedPledgeIdReadOnly]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[associatedPledgeIdReadOnly]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='gift.extendedDistribution' AND PAGE_TYPE='giftView') AND `FIELD_DEFINITION_ID` = 'gift.customFieldMap[associatedPledgeIdReadOnly]' AND `SECONDARY_FIELD_DEFINITION_ID` IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='gift.distributionLines.customFieldMap[anonymousReadOnly]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[anonymousReadOnly]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='gift.extendedDistribution' AND PAGE_TYPE='giftView') AND `FIELD_DEFINITION_ID` = 'gift.customFieldMap[anonymousReadOnly]' AND `SECONDARY_FIELD_DEFINITION_ID` IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='gift.distributionLines.customFieldMap[additional_tributeOccasionReadOnly]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[additional_tributeOccasionReadOnly]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='gift.extendedDistribution' AND PAGE_TYPE='giftView') AND `FIELD_DEFINITION_ID` = 'gift.customFieldMap[additional_tributeOccasionReadOnly]' AND `SECONDARY_FIELD_DEFINITION_ID` IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='gift.distributionLines.customFieldMap[tributeReferenceReadOnly]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[tributeReferenceReadOnly]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='gift.extendedDistribution' AND PAGE_TYPE='giftView') AND `FIELD_DEFINITION_ID` = 'gift.customFieldMap[tributeReferenceReadOnly]' AND `SECONDARY_FIELD_DEFINITION_ID` IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='gift.distributionLines.customFieldMap[tributeReadOnly]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[tributeReadOnly]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='gift.extendedDistribution' AND PAGE_TYPE='giftView') AND `FIELD_DEFINITION_ID` = 'gift.customFieldMap[tributeReadOnly]' AND `SECONDARY_FIELD_DEFINITION_ID` IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='gift.distributionLines.customFieldMap[tributeOccasionReadOnly]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[tributeOccasionReadOnly]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='gift.extendedDistribution' AND PAGE_TYPE='giftView') AND `FIELD_DEFINITION_ID` = 'gift.customFieldMap[tributeOccasionReadOnly]' AND `SECONDARY_FIELD_DEFINITION_ID` IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='gift.distributionLines.customFieldMap[taxDeductibleReadOnly]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[taxDeductibleReadOnly]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='gift.extendedDistribution' AND PAGE_TYPE='giftView') AND `FIELD_DEFINITION_ID` = 'gift.customFieldMap[taxDeductibleReadOnly]' AND `SECONDARY_FIELD_DEFINITION_ID` IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='gift.distributionLines.customFieldMap[recognitionNameReadOnly]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[recognitionNameReadOnly]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='gift.extendedDistribution' AND PAGE_TYPE='giftView') AND `FIELD_DEFINITION_ID` = 'gift.customFieldMap[recognitionNameReadOnly]' AND `SECONDARY_FIELD_DEFINITION_ID` IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='gift.distributionLines.customFieldMap[other_tributeReferenceReadOnly]',SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[other_tributeReferenceReadOnly]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='gift.extendedDistribution' AND PAGE_TYPE='giftView') AND `FIELD_DEFINITION_ID` = 'gift.customFieldMap[other_tributeReferenceReadOnly]' AND `SECONDARY_FIELD_DEFINITION_ID` IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='gift.distributionLines.customFieldMap[other_onBehalfOfReadOnly]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[other_onBehalfOfReadOnly]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='gift.extendedDistribution' AND PAGE_TYPE='giftView') AND `FIELD_DEFINITION_ID` = 'gift.customFieldMap[other_onBehalfOfReadOnly]' AND `SECONDARY_FIELD_DEFINITION_ID` IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='gift.distributionLines.customFieldMap[other_notifiedReadOnly]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[other_notifiedReadOnly]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='gift.extendedDistribution' AND PAGE_TYPE='giftView') AND `FIELD_DEFINITION_ID` = 'gift.customFieldMap[other_notifiedReadOnly]' AND `SECONDARY_FIELD_DEFINITION_ID` IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='gift.distributionLines.customFieldMap[onBehalfOfReadOnly]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[onBehalfOfReadOnly]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='gift.extendedDistribution' AND PAGE_TYPE='giftView') AND `FIELD_DEFINITION_ID` = 'gift.customFieldMap[onBehalfOfReadOnly]' AND `SECONDARY_FIELD_DEFINITION_ID` IS NULL);

UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='pledge.distributionLines.customFieldMap[anonymous]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[anonymous]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='pledge.extendedDistribution' AND PAGE_TYPE='pledge') AND `FIELD_DEFINITION_ID` = 'pledge.customFieldMap[anonymous]' AND `SECONDARY_FIELD_DEFINITION_ID` IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='pledge.distributionLines.customFieldMap[additional_tributeOccasion]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[additional_tributeOccasion]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='pledge.extendedDistribution' AND PAGE_TYPE='pledge') AND `FIELD_DEFINITION_ID` = 'pledge.customFieldMap[additional_tributeOccasion]' AND `SECONDARY_FIELD_DEFINITION_ID` IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='pledge.distributionLines.customFieldMap[tribute]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[tribute]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='pledge.extendedDistribution' AND PAGE_TYPE='pledge') AND `FIELD_DEFINITION_ID` = 'pledge.customFieldMap[tribute]' AND `SECONDARY_FIELD_DEFINITION_ID` IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='pledge.distributionLines.customFieldMap[tributeReference]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[tributeReference]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='pledge.extendedDistribution' AND PAGE_TYPE='pledge') AND `FIELD_DEFINITION_ID` = 'pledge.customFieldMap[tributeReference]' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='pledge.distributionLines.customFieldMap[tributeOccasion]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[tributeOccasion]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='pledge.extendedDistribution' AND PAGE_TYPE='pledge') AND `FIELD_DEFINITION_ID` = 'pledge.customFieldMap[tributeOccasion]' AND `SECONDARY_FIELD_DEFINITION_ID` IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='pledge.distributionLines.customFieldMap[taxDeductible]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[taxDeductible]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='pledge.extendedDistribution' AND PAGE_TYPE='pledge') AND `FIELD_DEFINITION_ID` = 'pledge.customFieldMap[taxDeductible]' AND `SECONDARY_FIELD_DEFINITION_ID` IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='pledge.distributionLines.customFieldMap[recognitionName]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[recognitionName]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='pledge.extendedDistribution' AND PAGE_TYPE='pledge') AND `FIELD_DEFINITION_ID` = 'pledge.customFieldMap[recognitionName]' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='pledge.distributionLines.customFieldMap[other_tributeReference]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[other_tributeReference]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='pledge.extendedDistribution' AND PAGE_TYPE='pledge') AND `FIELD_DEFINITION_ID` = 'pledge.customFieldMap[other_tributeReference]' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='pledge.distributionLines.customFieldMap[other_onBehalfOf]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[other_onBehalfOf]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='pledge.extendedDistribution' AND PAGE_TYPE='pledge') AND `FIELD_DEFINITION_ID` = 'pledge.customFieldMap[other_onBehalfOf]' AND `SECONDARY_FIELD_DEFINITION_ID` IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='pledge.distributionLines.customFieldMap[other_notified]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[other_notified]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='pledge.extendedDistribution' AND PAGE_TYPE='pledge') AND `FIELD_DEFINITION_ID` = 'pledge.customFieldMap[other_notified]' AND `SECONDARY_FIELD_DEFINITION_ID` IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='pledge.distributionLines.customFieldMap[onBehalfOf]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[onBehalfOf]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='pledge.extendedDistribution' AND PAGE_TYPE='pledge') AND `FIELD_DEFINITION_ID` = 'pledge.customFieldMap[onBehalfOf]' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='pledge.distributionLines.customFieldMap[notified]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[notified]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='pledge.extendedDistribution' AND PAGE_TYPE='pledge') AND `FIELD_DEFINITION_ID` = 'pledge.customFieldMap[notified]' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='pledge.distributionLines.customFieldMap[message]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[message]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='pledge.extendedDistribution' AND PAGE_TYPE='pledge') AND `FIELD_DEFINITION_ID` = 'pledge.customFieldMap[message]' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);

UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='recurringGift.distributionLines.customFieldMap[tribute]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[tribute]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGift.extendedDistribution' AND PAGE_TYPE='recurringGift') AND `FIELD_DEFINITION_ID` = 'recurringGift.customFieldMap[tribute]' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='recurringGift.distributionLines.customFieldMap[tributeReference]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[tributeReference]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGift.extendedDistribution' AND PAGE_TYPE='recurringGift') AND `FIELD_DEFINITION_ID` = 'recurringGift.customFieldMap[tributeReference]' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='recurringGift.distributionLines.customFieldMap[tributeOccasion]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[tributeOccasion]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGift.extendedDistribution' AND PAGE_TYPE='recurringGift') AND `FIELD_DEFINITION_ID` = 'recurringGift.customFieldMap[tributeOccasion]' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='recurringGift.distributionLines.customFieldMap[taxDeductible]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[taxDeductible]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGift.extendedDistribution' AND PAGE_TYPE='recurringGift') AND `FIELD_DEFINITION_ID` = 'recurringGift.customFieldMap[taxDeductible]' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='recurringGift.distributionLines.customFieldMap[recognitionName]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[recognitionName]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGift.extendedDistribution' AND PAGE_TYPE='recurringGift') AND `FIELD_DEFINITION_ID` = 'recurringGift.customFieldMap[recognitionName]' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='recurringGift.distributionLines.customFieldMap[other_tributeReference]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[other_tributeReference]'  WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGift.extendedDistribution' AND PAGE_TYPE='recurringGift') AND `FIELD_DEFINITION_ID` = 'recurringGift.customFieldMap[other_tributeReference]' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='recurringGift.distributionLines.customFieldMap[other_onBehalfOf]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[other_onBehalfOf]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGift.extendedDistribution' AND PAGE_TYPE='recurringGift') AND `FIELD_DEFINITION_ID` = 'recurringGift.customFieldMap[other_onBehalfOf]' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='recurringGift.distributionLines.customFieldMap[other_notified]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[other_notified]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGift.extendedDistribution' AND PAGE_TYPE='recurringGift') AND `FIELD_DEFINITION_ID` = 'recurringGift.customFieldMap[other_notified]' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='recurringGift.distributionLines.customFieldMap[onBehalfOf]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[onBehalfOf]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGift.extendedDistribution' AND PAGE_TYPE='recurringGift') AND `FIELD_DEFINITION_ID` = 'recurringGift.customFieldMap[onBehalfOf]' AND `SECONDARY_FIELD_DEFINITION_ID` IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='recurringGift.distributionLines.customFieldMap[notified]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[notified]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGift.extendedDistribution' AND PAGE_TYPE='recurringGift') AND `FIELD_DEFINITION_ID` = 'recurringGift.customFieldMap[notified]' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='recurringGift.distributionLines.customFieldMap[message]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[message]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGift.extendedDistribution' AND PAGE_TYPE='recurringGift') AND `FIELD_DEFINITION_ID` = 'recurringGift.customFieldMap[message]' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='recurringGift.distributionLines.customFieldMap[anonymous]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[anonymous]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGift.extendedDistribution' AND PAGE_TYPE='recurringGift') AND `FIELD_DEFINITION_ID` = 'recurringGift.customFieldMap[anonymous]' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='recurringGift.distributionLines.customFieldMap[additional_tributeOccasion]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[additional_tributeOccasion]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGift.extendedDistribution' AND PAGE_TYPE='recurringGift') AND `FIELD_DEFINITION_ID` = 'recurringGift.customFieldMap[additional_tributeOccasion]' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);

UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='pledge.distributionLines.customFieldMap[taxDeductibleReadOnly]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[taxDeductibleReadOnly]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='pledge.extendedDistribution' AND PAGE_TYPE='pledgeView') AND `FIELD_DEFINITION_ID` = 'pledge.customFieldMap[taxDeductibleReadOnly]' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='pledge.distributionLines.customFieldMap[tributeReferenceReadOnly]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[tributeReferenceReadOnly]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='pledge.extendedDistribution' AND PAGE_TYPE='pledgeView') AND `FIELD_DEFINITION_ID` = 'pledge.customFieldMap[tributeReferenceReadOnly]' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='pledge.distributionLines.customFieldMap[tributeReadOnly]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[tributeReadOnly]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='pledge.extendedDistribution' AND PAGE_TYPE='pledgeView') AND `FIELD_DEFINITION_ID` = 'pledge.customFieldMap[tributeReadOnly]' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='pledge.distributionLines.customFieldMap[tributeOccasionReadOnly]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[tributeOccasionReadOnly]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='pledge.extendedDistribution' AND PAGE_TYPE='pledgeView') AND `FIELD_DEFINITION_ID` = 'pledge.customFieldMap[tributeOccasionReadOnly]' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='pledge.distributionLines.customFieldMap[recognitionNameReadOnly]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[recognitionNameReadOnly]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='pledge.extendedDistribution' AND PAGE_TYPE='pledgeView') AND `FIELD_DEFINITION_ID` = 'pledge.customFieldMap[recognitionNameReadOnly]' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='pledge.distributionLines.customFieldMap[other_tributeReferenceReadOnly]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[other_tributeReferenceReadOnly]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='pledge.extendedDistribution' AND PAGE_TYPE='pledgeView') AND `FIELD_DEFINITION_ID` = 'pledge.customFieldMap[other_tributeReferenceReadOnly]' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='pledge.distributionLines.customFieldMap[other_onBehalfOfReadOnly]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[other_onBehalfOfReadOnly]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='pledge.extendedDistribution' AND PAGE_TYPE='pledgeView') AND `FIELD_DEFINITION_ID` = 'pledge.customFieldMap[other_onBehalfOfReadOnly]' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='pledge.distributionLines.customFieldMap[other_notifiedReadOnly]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[other_notifiedReadOnly]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='pledge.extendedDistribution' AND PAGE_TYPE='pledgeView') AND `FIELD_DEFINITION_ID` = 'pledge.customFieldMap[other_notifiedReadOnly]' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='pledge.distributionLines.customFieldMap[onBehalfOfReadOnly]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[onBehalfOfReadOnly]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='pledge.extendedDistribution' AND PAGE_TYPE='pledgeView') AND `FIELD_DEFINITION_ID` = 'pledge.customFieldMap[onBehalfOfReadOnly]' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='pledge.distributionLines.customFieldMap[notifiedReadOnly]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[notifiedReadOnly]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='pledge.extendedDistribution' AND PAGE_TYPE='pledgeView') AND `FIELD_DEFINITION_ID` = 'pledge.customFieldMap[notifiedReadOnly]' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='pledge.distributionLines.customFieldMap[messageReadOnly]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[messageReadOnly]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='pledge.extendedDistribution' AND PAGE_TYPE='pledgeView') AND `FIELD_DEFINITION_ID` = 'pledge.customFieldMap[messageReadOnly]' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='pledge.distributionLines.customFieldMap[anonymousReadOnly]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[anonymousReadOnly]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='pledge.extendedDistribution' AND PAGE_TYPE='pledgeView') AND `FIELD_DEFINITION_ID` = 'pledge.customFieldMap[anonymousReadOnly]' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='pledge.distributionLines.customFieldMap[additional_tributeOccasionReadOnly]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[additional_tributeOccasionReadOnly]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='pledge.extendedDistribution' AND PAGE_TYPE='pledgeView') AND `FIELD_DEFINITION_ID` = 'pledge.customFieldMap[additional_tributeOccasionReadOnly]' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);

UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='recurringGift.distributionLines.customFieldMap[tributeReferenceReadOnly]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[tributeReferenceReadOnly]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGift.extendedDistribution' AND PAGE_TYPE='recurringGiftView') AND `FIELD_DEFINITION_ID` = 'recurringGift.customFieldMap[tributeReferenceReadOnly]' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='recurringGift.distributionLines.customFieldMap[tributeReadOnly]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[tributeReadOnly]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGift.extendedDistribution' AND PAGE_TYPE='recurringGiftView') AND `FIELD_DEFINITION_ID` = 'recurringGift.customFieldMap[tributeReadOnly]' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='recurringGift.distributionLines.customFieldMap[tributeOccasionReadOnly]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[tributeOccasionReadOnly]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGift.extendedDistribution' AND PAGE_TYPE='recurringGiftView') AND `FIELD_DEFINITION_ID` = 'recurringGift.customFieldMap[tributeOccasionReadOnly]' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='recurringGift.distributionLines.customFieldMap[taxDeductibleReadOnly]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[taxDeductibleReadOnly]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGift.extendedDistribution' AND PAGE_TYPE='recurringGiftView') AND `FIELD_DEFINITION_ID` = 'recurringGift.customFieldMap[taxDeductibleReadOnly]' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='recurringGift.distributionLines.customFieldMap[recognitionNameReadOnly]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[recognitionNameReadOnly]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGift.extendedDistribution' AND PAGE_TYPE='recurringGiftView') AND `FIELD_DEFINITION_ID` = 'recurringGift.customFieldMap[recognitionNameReadOnly]' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='recurringGift.distributionLines.customFieldMap[other_tributeReferenceReadOnly]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[other_tributeReferenceReadOnly]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGift.extendedDistribution' AND PAGE_TYPE='recurringGiftView') AND `FIELD_DEFINITION_ID` = 'recurringGift.customFieldMap[other_tributeReferenceReadOnly]' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='recurringGift.distributionLines.customFieldMap[other_onBehalfOfReadOnly]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[other_onBehalfOfReadOnly]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGift.extendedDistribution' AND PAGE_TYPE='recurringGiftView') AND `FIELD_DEFINITION_ID` = 'recurringGift.customFieldMap[other_onBehalfOfReadOnly]' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='recurringGift.distributionLines.customFieldMap[other_notifiedReadOnly]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[other_notifiedReadOnly]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGift.extendedDistribution' AND PAGE_TYPE='recurringGiftView') AND `FIELD_DEFINITION_ID` = 'recurringGift.customFieldMap[other_notifiedReadOnly]' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='recurringGift.distributionLines.customFieldMap[onBehalfOfReadOnly]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[onBehalfOfReadOnly]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGift.extendedDistribution' AND PAGE_TYPE='recurringGiftView') AND `FIELD_DEFINITION_ID` = 'recurringGift.customFieldMap[onBehalfOfReadOnly]' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='recurringGift.distributionLines.customFieldMap[notifiedReadOnly]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[notifiedReadOnly]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGift.extendedDistribution' AND PAGE_TYPE='recurringGiftView') AND `FIELD_DEFINITION_ID` = 'recurringGift.customFieldMap[notifiedReadOnly]' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='recurringGift.distributionLines.customFieldMap[messageReadOnly]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[messageReadOnly]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGift.extendedDistribution' AND PAGE_TYPE='recurringGiftView') AND `FIELD_DEFINITION_ID` = 'recurringGift.customFieldMap[messageReadOnly]' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='recurringGift.distributionLines.customFieldMap[anonymousReadOnly]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[anonymousReadOnly]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGift.extendedDistribution' AND PAGE_TYPE='recurringGiftView') AND `FIELD_DEFINITION_ID` = 'recurringGift.customFieldMap[anonymousReadOnly]' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='recurringGift.distributionLines.customFieldMap[additional_tributeOccasionReadOnly]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[additional_tributeOccasionReadOnly]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGift.extendedDistribution' AND PAGE_TYPE='recurringGiftView') AND `FIELD_DEFINITION_ID` = 'recurringGift.customFieldMap[additional_tributeOccasionReadOnly]' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);

UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='adjustedGift.distributionLines.projectCodeReadOnly' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.projectCodeReadOnly' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='adjustedGift.distribution' AND PAGE_TYPE='adjustedGift') AND `FIELD_DEFINITION_ID` = 'adjustedGift.projectCodeReadOnly' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='adjustedGift.distributionLines.percentage' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.percentage'  WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='adjustedGift.distribution' AND PAGE_TYPE='adjustedGift') AND `FIELD_DEFINITION_ID` = 'adjustedGift.percentage' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='adjustedGift.distributionLines.motivationCodeReadOnly' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.motivationCodeReadOnly' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='adjustedGift.distribution' AND PAGE_TYPE='adjustedGift') AND `FIELD_DEFINITION_ID` = 'adjustedGift.motivationCodeReadOnly' AND `SECONDARY_FIELD_DEFINITION_ID` IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='adjustedGift.distributionLines.other_motivationCodeReadOnly' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.other_motivationCodeReadOnly' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='adjustedGift.distribution' AND PAGE_TYPE='adjustedGift') AND `FIELD_DEFINITION_ID` = 'adjustedGift.other_motivationCodeReadOnly' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='adjustedGift.distributionLines.amount' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.amount' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='adjustedGift.distribution' AND PAGE_TYPE='adjustedGift') AND `FIELD_DEFINITION_ID` = 'adjustedGift.lineAmount' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='adjustedGift.distributionLines.customFieldMap[referenceReadOnly]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[referenceReadOnly]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='adjustedGift.distribution' AND PAGE_TYPE='adjustedGift') AND `FIELD_DEFINITION_ID` = 'adjustedGift.customFieldMap[referenceReadOnly]' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='adjustedGift.distributionLines.customFieldMap[other_referenceReadOnly]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[other_referenceReadOnly]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='adjustedGift.distribution' AND PAGE_TYPE='adjustedGift') AND `FIELD_DEFINITION_ID` = 'adjustedGift.customFieldMap[other_referenceReadOnly]' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);

UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='adjustedGift.distributionLines.customFieldMap[messageReadOnly]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[messageReadOnly]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='adjustedGift.extendedDistribution' AND PAGE_TYPE='adjustedGift') AND `FIELD_DEFINITION_ID` = 'adjustedGift.customFieldMap[messageReadOnly]' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='adjustedGift.distributionLines.customFieldMap[notifiedReadOnly]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[notifiedReadOnly]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='adjustedGift.extendedDistribution' AND PAGE_TYPE='adjustedGift') AND `FIELD_DEFINITION_ID` = 'adjustedGift.customFieldMap[notifiedReadOnly]' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='adjustedGift.distributionLines.customFieldMap[onBehalfOfReadOnly]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[onBehalfOfReadOnly]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='adjustedGift.extendedDistribution' AND PAGE_TYPE='adjustedGift') AND `FIELD_DEFINITION_ID` = 'adjustedGift.customFieldMap[onBehalfOfReadOnly]' AND `SECONDARY_FIELD_DEFINITION_ID` IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='adjustedGift.distributionLines.customFieldMap[other_notifiedReadOnly]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[other_notifiedReadOnly]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='adjustedGift.extendedDistribution' AND PAGE_TYPE='adjustedGift') AND `FIELD_DEFINITION_ID` = 'adjustedGift.customFieldMap[other_notifiedReadOnly]' AND `SECONDARY_FIELD_DEFINITION_ID` IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='adjustedGift.distributionLines.customFieldMap[other_onBehalfOfReadOnly]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[other_onBehalfOfReadOnly]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='adjustedGift.extendedDistribution' AND PAGE_TYPE='adjustedGift') AND `FIELD_DEFINITION_ID` = 'adjustedGift.customFieldMap[other_onBehalfOfReadOnly]' AND `SECONDARY_FIELD_DEFINITION_ID` IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='adjustedGift.distributionLines.customFieldMap[other_tributeReferenceReadOnly]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[other_tributeReferenceReadOnly]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='adjustedGift.extendedDistribution' AND PAGE_TYPE='adjustedGift') AND `FIELD_DEFINITION_ID` = 'adjustedGift.customFieldMap[other_tributeReferenceReadOnly]' AND `SECONDARY_FIELD_DEFINITION_ID` IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='adjustedGift.distributionLines.customFieldMap[anonymousReadOnly]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[anonymousReadOnly]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='adjustedGift.extendedDistribution' AND PAGE_TYPE='adjustedGift') AND `FIELD_DEFINITION_ID` = 'adjustedGift.customFieldMap[anonymousReadOnly]' AND `SECONDARY_FIELD_DEFINITION_ID` IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='adjustedGift.distributionLines.customFieldMap[associatedPledgeIdReadOnly]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[associatedPledgeIdReadOnly]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='adjustedGift.extendedDistribution' AND PAGE_TYPE='adjustedGift') AND `FIELD_DEFINITION_ID` = 'adjustedGift.customFieldMap[associatedPledgeIdReadOnly]' AND `SECONDARY_FIELD_DEFINITION_ID` IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='adjustedGift.distributionLines.customFieldMap[associatedRecurringGiftIdReadOnly]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[associatedRecurringGiftIdReadOnly]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='adjustedGift.extendedDistribution' AND PAGE_TYPE='adjustedGift') AND `FIELD_DEFINITION_ID` = 'adjustedGift.customFieldMap[associatedRecurringGiftIdReadOnly]' AND `SECONDARY_FIELD_DEFINITION_ID` IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='adjustedGift.distributionLines.customFieldMap[additional_tributeOccasionReadOnly]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[additional_tributeOccasionReadOnly]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='adjustedGift.extendedDistribution' AND PAGE_TYPE='adjustedGift') AND `FIELD_DEFINITION_ID` = 'adjustedGift.customFieldMap[additional_tributeOccasionReadOnly]' AND `SECONDARY_FIELD_DEFINITION_ID` IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='adjustedGift.distributionLines.customFieldMap[recognitionNameReadOnly]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[recognitionNameReadOnly]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='adjustedGift.extendedDistribution' AND PAGE_TYPE='adjustedGift') AND `FIELD_DEFINITION_ID` = 'adjustedGift.customFieldMap[recognitionNameReadOnly]' AND `SECONDARY_FIELD_DEFINITION_ID` IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='adjustedGift.distributionLines.customFieldMap[taxDeductibleReadOnly]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[taxDeductibleReadOnly]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='adjustedGift.extendedDistribution' AND PAGE_TYPE='adjustedGift') AND `FIELD_DEFINITION_ID` = 'adjustedGift.customFieldMap[taxDeductibleReadOnly]' AND `SECONDARY_FIELD_DEFINITION_ID` IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='adjustedGift.distributionLines.customFieldMap[tributeOccasionReadOnly]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[tributeOccasionReadOnly]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='adjustedGift.extendedDistribution' AND PAGE_TYPE='adjustedGift') AND `FIELD_DEFINITION_ID` = 'adjustedGift.customFieldMap[tributeOccasionReadOnly]' AND `SECONDARY_FIELD_DEFINITION_ID` IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='adjustedGift.distributionLines.customFieldMap[tributeReferenceReadOnly]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[tributeReferenceReadOnly]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='adjustedGift.extendedDistribution' AND PAGE_TYPE='adjustedGift') AND `FIELD_DEFINITION_ID` = 'adjustedGift.customFieldMap[tributeReferenceReadOnly]' AND `SECONDARY_FIELD_DEFINITION_ID` IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='adjustedGift.distributionLines.customFieldMap[tributeReadOnly]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[tributeReadOnly]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='adjustedGift.extendedDistribution' AND PAGE_TYPE='adjustedGift') AND `FIELD_DEFINITION_ID` = 'adjustedGift.customFieldMap[tributeReadOnly]' AND `SECONDARY_FIELD_DEFINITION_ID` IS NULL);

UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='adjustedGift.address.id' ,SECONDARY_FIELD_DEFINITION_ID='address.id' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='adjustedGift.payment' AND PAGE_TYPE='adjustedGift') AND `FIELD_DEFINITION_ID` = 'adjustedGift.selectedAddress' AND `SECONDARY_FIELD_DEFINITION_ID` IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='adjustedGift.paymentSource.id' ,SECONDARY_FIELD_DEFINITION_ID='paymentSource.id' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='adjustedGift.payment' AND PAGE_TYPE='adjustedGift') AND `FIELD_DEFINITION_ID` = 'adjustedGift.selectedPaymentSource' AND `SECONDARY_FIELD_DEFINITION_ID` IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='adjustedGift.phone.id' ,SECONDARY_FIELD_DEFINITION_ID='phone.id' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='adjustedGift.payment' AND PAGE_TYPE='adjustedGift') AND `FIELD_DEFINITION_ID` = 'adjustedGift.selectedPhone' AND `SECONDARY_FIELD_DEFINITION_ID` IS NULL);

UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='adjustedGift.paymentSource.creditCardExpirationDisplay' ,SECONDARY_FIELD_DEFINITION_ID='paymentSource.creditCardExpirationDisplay' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='adjustedGift.editCreditCard' AND PAGE_TYPE='adjustedGift') AND `FIELD_DEFINITION_ID` = 'adjustedGift.selectedPaymentSource.creditCardExpirationDisplay' AND `SECONDARY_FIELD_DEFINITION_ID` = 'selectedPaymentSource.creditCardExpirationDisplay');
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='adjustedGift.paymentSource.creditCardHolderNameReadOnly' ,SECONDARY_FIELD_DEFINITION_ID='paymentSource.creditCardHolderNameReadOnly' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='adjustedGift.editCreditCard' AND PAGE_TYPE='adjustedGift') AND `FIELD_DEFINITION_ID` = 'adjustedGift.selectedPaymentSource.creditCardHolderNameReadOnly' AND `SECONDARY_FIELD_DEFINITION_ID` = 'selectedPaymentSource.creditCardHolderNameReadOnly');
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='adjustedGift.paymentSource.creditCardNumberReadOnly' ,SECONDARY_FIELD_DEFINITION_ID='paymentSource.creditCardNumberReadOnly' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='adjustedGift.editCreditCard' AND PAGE_TYPE='adjustedGift') AND `FIELD_DEFINITION_ID` = 'adjustedGift.selectedPaymentSource.creditCardNumberReadOnly' AND `SECONDARY_FIELD_DEFINITION_ID` = 'selectedPaymentSource.creditCardNumberReadOnly');
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='adjustedGift.paymentSource.creditCardSecurityCode' ,SECONDARY_FIELD_DEFINITION_ID='paymentSource.creditCardSecurityCode' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='adjustedGift.editCreditCard' AND PAGE_TYPE='adjustedGift') AND `FIELD_DEFINITION_ID` = 'adjustedGift.selectedPaymentSource.creditCardSecurityCode' AND `SECONDARY_FIELD_DEFINITION_ID` = 'selectedPaymentSource.creditCardSecurityCode');
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='adjustedGift.paymentSource.creditCardTypeReadOnly' ,SECONDARY_FIELD_DEFINITION_ID='paymentSource.creditCardTypeReadOnly' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='adjustedGift.editCreditCard' AND PAGE_TYPE='adjustedGift') AND `FIELD_DEFINITION_ID` = 'adjustedGift.selectedPaymentSource.creditCardTypeReadOnly' AND `SECONDARY_FIELD_DEFINITION_ID` = 'selectedPaymentSource.creditCardTypeReadOnly');

UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='adjustedGift.paymentSource.achRoutingNumberReadOnly' ,SECONDARY_FIELD_DEFINITION_ID='paymentSource.achRoutingNumberReadOnly' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='adjustedGift.editAch' AND PAGE_TYPE='adjustedGift') AND `FIELD_DEFINITION_ID` = 'adjustedGift.selectedPaymentSource.achRoutingNumberReadOnly' AND `SECONDARY_FIELD_DEFINITION_ID` = 'selectedPaymentSource.achRoutingNumberReadOnly');
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='adjustedGift.paymentSource.achAccountNumberReadOnly' ,SECONDARY_FIELD_DEFINITION_ID='paymentSource.achAccountNumberReadOnly' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='adjustedGift.editAch' AND PAGE_TYPE='adjustedGift') AND `FIELD_DEFINITION_ID` = 'adjustedGift.selectedPaymentSource.achAccountNumberReadOnly' AND `SECONDARY_FIELD_DEFINITION_ID` = 'selectedPaymentSource.achAccountNumberReadOnly');
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='adjustedGift.paymentSource.achHolderNameReadOnly' ,SECONDARY_FIELD_DEFINITION_ID='paymentSource.achHolderNameReadOnly' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='adjustedGift.editAch' AND PAGE_TYPE='adjustedGift') AND `FIELD_DEFINITION_ID` = 'adjustedGift.selectedPaymentSource.achHolderNameReadOnly' AND `SECONDARY_FIELD_DEFINITION_ID` = 'selectedPaymentSource.achHolderNameReadOnly');

UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='recurringGift.address.id' ,SECONDARY_FIELD_DEFINITION_ID='address.id' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGift.payment' AND PAGE_TYPE='recurringGift') AND `FIELD_DEFINITION_ID` = 'recurringGift.selectedAddress' AND `SECONDARY_FIELD_DEFINITION_ID` IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='recurringGift.paymentSource.id' ,SECONDARY_FIELD_DEFINITION_ID='paymentSource.id' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGift.payment' AND PAGE_TYPE='recurringGift') AND `FIELD_DEFINITION_ID` = 'recurringGift.selectedPaymentSource' AND `SECONDARY_FIELD_DEFINITION_ID` IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='recurringGift.phone.id' ,SECONDARY_FIELD_DEFINITION_ID='phone.id' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGift.payment' AND PAGE_TYPE='recurringGift') AND `FIELD_DEFINITION_ID` = 'recurringGift.selectedPhone' AND `SECONDARY_FIELD_DEFINITION_ID` IS NULL);

UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='recurringGift.address.addressLine1ReadOnly' ,SECONDARY_FIELD_DEFINITION_ID='address.addressLine1ReadOnly' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGift.payment' AND PAGE_TYPE='recurringGiftView') AND `FIELD_DEFINITION_ID` = 'recurringGift.selectedAddress.addressLine1ReadOnly' AND `SECONDARY_FIELD_DEFINITION_ID` = 'selectedAddress.addressLine1ReadOnly');
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='recurringGift.address.addressLine2ReadOnly' ,SECONDARY_FIELD_DEFINITION_ID='address.addressLine2ReadOnly' WHERE (`SECTION_DEFINITION_ID` =(SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGift.payment' AND PAGE_TYPE='recurringGiftView') AND `FIELD_DEFINITION_ID` = 'recurringGift.selectedAddress.addressLine2ReadOnly' AND `SECONDARY_FIELD_DEFINITION_ID` = 'selectedAddress.addressLine2ReadOnly');
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='recurringGift.address.addressLine3ReadOnly' ,SECONDARY_FIELD_DEFINITION_ID='address.addressLine3ReadOnly' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGift.payment' AND PAGE_TYPE='recurringGiftView') AND `FIELD_DEFINITION_ID` = 'recurringGift.selectedAddress.addressLine3ReadOnly' AND `SECONDARY_FIELD_DEFINITION_ID` = 'selectedAddress.addressLine3ReadOnly');
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='recurringGift.address.cityReadOnly' ,SECONDARY_FIELD_DEFINITION_ID='address.cityReadOnly' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGift.payment' AND PAGE_TYPE='recurringGiftView') AND `FIELD_DEFINITION_ID` = 'recurringGift.selectedAddress.cityReadOnly' AND `SECONDARY_FIELD_DEFINITION_ID` = 'selectedAddress.cityReadOnly');
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='recurringGift.address.countryReadOnly' ,SECONDARY_FIELD_DEFINITION_ID='address.countryReadOnly' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGift.payment' AND PAGE_TYPE='recurringGiftView') AND `FIELD_DEFINITION_ID` = 'recurringGift.selectedAddress.countryReadOnly' AND `SECONDARY_FIELD_DEFINITION_ID` = 'selectedAddress.countryReadOnly');
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='recurringGift.address.customFieldMap[addressTypeReadOnly]' ,SECONDARY_FIELD_DEFINITION_ID='address.customFieldMap[addressTypeReadOnly]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGift.payment' AND PAGE_TYPE='recurringGiftView') AND `FIELD_DEFINITION_ID` = 'recurringGift.selectedAddress.customFieldMap[addressTypeReadOnly]' AND `SECONDARY_FIELD_DEFINITION_ID` = 'selectedAddress.customFieldMap[addressTypeReadOnly]');
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='recurringGift.address.postalCodeReadOnly' ,SECONDARY_FIELD_DEFINITION_ID='address.postalCodeReadOnly' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGift.payment' AND PAGE_TYPE='recurringGiftView') AND `FIELD_DEFINITION_ID` = 'recurringGift.selectedAddress.postalCodeReadOnly' AND `SECONDARY_FIELD_DEFINITION_ID` = 'selectedAddress.postalCodeReadOnly');
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='recurringGift.address.stateProvinceReadOnly' ,SECONDARY_FIELD_DEFINITION_ID='address.stateProvinceReadOnly' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGift.payment' AND PAGE_TYPE='recurringGiftView') AND `FIELD_DEFINITION_ID` = 'recurringGift.selectedAddress.stateProvinceReadOnly' AND `SECONDARY_FIELD_DEFINITION_ID` = 'selectedAddress.stateProvinceReadOnly');
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='recurringGift.phone.customFieldMap[phoneTypeReadOnly]' ,SECONDARY_FIELD_DEFINITION_ID='phone.customFieldMap[phoneTypeReadOnly]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGift.payment' AND PAGE_TYPE='recurringGiftView') AND `FIELD_DEFINITION_ID` = 'recurringGift.selectedPhone.customFieldMap[phoneTypeReadOnly]' AND `SECONDARY_FIELD_DEFINITION_ID` = 'selectedPhone.customFieldMap[phoneTypeReadOnly]');
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='recurringGift.phone.numberReadOnly' ,SECONDARY_FIELD_DEFINITION_ID='phone.numberReadOnly' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGift.payment' AND PAGE_TYPE='recurringGiftView') AND `FIELD_DEFINITION_ID` = 'recurringGift.selectedPhone.numberReadOnly' AND `SECONDARY_FIELD_DEFINITION_ID` = 'selectedPhone.numberReadOnly');

UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='giftInKind.details.description' ,SECONDARY_FIELD_DEFINITION_ID='details.description' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='giftInKind.details' AND PAGE_TYPE='giftInKind') AND `FIELD_DEFINITION_ID` = 'giftInKind.description' AND `SECONDARY_FIELD_DEFINITION_ID` IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='giftInKind.details.detailFairMarketValue' ,SECONDARY_FIELD_DEFINITION_ID='details.detailFairMarketValue' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='giftInKind.details' AND PAGE_TYPE='giftInKind') AND `FIELD_DEFINITION_ID` = 'giftInKind.detailFairMarketValue' AND `SECONDARY_FIELD_DEFINITION_ID` IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='giftInKind.details.taxDeductible' ,SECONDARY_FIELD_DEFINITION_ID='details.taxDeductible' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='giftInKind.details' AND PAGE_TYPE='giftInKind') AND `FIELD_DEFINITION_ID` = 'giftInKind.taxDeductible' AND `SECONDARY_FIELD_DEFINITION_ID` IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='giftInKind.details.projectCode' ,SECONDARY_FIELD_DEFINITION_ID='details.projectCode' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='giftInKind.details' AND PAGE_TYPE='giftInKind') AND `FIELD_DEFINITION_ID` = 'giftInKind.projectCode' AND `SECONDARY_FIELD_DEFINITION_ID` IS NULL);


UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='giftInKind.details.fmvMethod' ,SECONDARY_FIELD_DEFINITION_ID='details.fmvMethod' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='gift.extendedDetails' AND PAGE_TYPE='giftInKind') AND `FIELD_DEFINITION_ID` = 'giftInKind.fmvMethod' AND `SECONDARY_FIELD_DEFINITION_ID` IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='giftInKind.details.gikCategory' ,SECONDARY_FIELD_DEFINITION_ID='details.gikCategory' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='gift.extendedDetails' AND PAGE_TYPE='giftInKind') AND `FIELD_DEFINITION_ID` = 'giftInKind.gikCategory' AND `SECONDARY_FIELD_DEFINITION_ID` IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='giftInKind.details.quantity' ,SECONDARY_FIELD_DEFINITION_ID='details.quantity' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='gift.extendedDetails' AND PAGE_TYPE='giftInKind') AND `FIELD_DEFINITION_ID` = 'giftInKind.quantity' AND `SECONDARY_FIELD_DEFINITION_ID` IS NULL);

UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='adjustedGift.address.addressLine1ReadOnly' ,SECONDARY_FIELD_DEFINITION_ID='address.addressLine1ReadOnly' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='adjustedGift.payment' AND PAGE_TYPE='adjustedGiftView') AND `FIELD_DEFINITION_ID` = 'adjustedGift.selectedAddress.addressLine1ReadOnly' AND `SECONDARY_FIELD_DEFINITION_ID` = 'selectedAddress.addressLine1ReadOnly');
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='adjustedGift.address.addressLine2ReadOnly' ,SECONDARY_FIELD_DEFINITION_ID='address.addressLine2ReadOnly' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='adjustedGift.payment' AND PAGE_TYPE='adjustedGiftView') AND `FIELD_DEFINITION_ID` = 'adjustedGift.selectedAddress.addressLine2ReadOnly' AND `SECONDARY_FIELD_DEFINITION_ID` = 'selectedAddress.addressLine2ReadOnly');
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='adjustedGift.address.addressLine3ReadOnly' ,SECONDARY_FIELD_DEFINITION_ID='address.addressLine3ReadOnly' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='adjustedGift.payment' AND PAGE_TYPE='adjustedGiftView') AND `FIELD_DEFINITION_ID` = 'adjustedGift.selectedAddress.addressLine3ReadOnly' AND `SECONDARY_FIELD_DEFINITION_ID` = 'selectedAddress.addressLine3ReadOnly');
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='adjustedGift.address.cityReadOnly' ,SECONDARY_FIELD_DEFINITION_ID='address.cityReadOnly' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='adjustedGift.payment' AND PAGE_TYPE='adjustedGiftView') AND `FIELD_DEFINITION_ID` = 'adjustedGift.selectedAddress.cityReadOnly' AND `SECONDARY_FIELD_DEFINITION_ID` = 'selectedAddress.cityReadOnly');
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='adjustedGift.address.countryReadOnly' ,SECONDARY_FIELD_DEFINITION_ID='address.countryReadOnly' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='adjustedGift.payment' AND PAGE_TYPE='adjustedGiftView') AND `FIELD_DEFINITION_ID` = 'adjustedGift.selectedAddress.countryReadOnly' AND `SECONDARY_FIELD_DEFINITION_ID` = 'selectedAddress.countryReadOnly');
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='adjustedGift.address.postalCodeReadOnly' ,SECONDARY_FIELD_DEFINITION_ID='address.postalCodeReadOnly' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='adjustedGift.payment' AND PAGE_TYPE='adjustedGiftView') AND `FIELD_DEFINITION_ID` = 'adjustedGift.selectedAddress.postalCodeReadOnly' AND `SECONDARY_FIELD_DEFINITION_ID` = 'selectedAddress.postalCodeReadOnly');
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='adjustedGift.address.stateProvinceReadOnly' ,SECONDARY_FIELD_DEFINITION_ID='address.stateProvinceReadOnly' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='adjustedGift.payment' AND PAGE_TYPE='adjustedGiftView') AND `FIELD_DEFINITION_ID` = 'adjustedGift.selectedAddress.stateProvinceReadOnly' AND `SECONDARY_FIELD_DEFINITION_ID` = 'selectedAddress.stateProvinceReadOnly');
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='adjustedGift.phone.numberReadOnly' ,SECONDARY_FIELD_DEFINITION_ID='phone.numberReadOnly' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='adjustedGift.payment' AND PAGE_TYPE='adjustedGiftView') AND `FIELD_DEFINITION_ID` = 'adjustedGift.selectedPhone.numberReadOnly' AND `SECONDARY_FIELD_DEFINITION_ID` = 'selectedPhone.numberReadOnly');

UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='adjustedGift.paymentSource.creditCardHolderNameReadOnly' ,SECONDARY_FIELD_DEFINITION_ID='paymentSource.creditCardHolderNameReadOnly' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='adjustedGift.creditCard' AND PAGE_TYPE='adjustedGiftView') AND `FIELD_DEFINITION_ID` = 'adjustedGift.selectedPaymentSource.creditCardHolderNameReadOnly' AND `SECONDARY_FIELD_DEFINITION_ID` = 'selectedPaymentSource.creditCardHolderNameReadOnly');
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='adjustedGift.paymentSource.creditCardExpirationDisplay' ,SECONDARY_FIELD_DEFINITION_ID='paymentSource.creditCardExpirationDisplay' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='adjustedGift.creditCard' AND PAGE_TYPE='adjustedGiftView') AND `FIELD_DEFINITION_ID` = 'adjustedGift.selectedPaymentSource.creditCardExpirationDisplay' AND `SECONDARY_FIELD_DEFINITION_ID` = 'selectedPaymentSource.creditCardExpirationDisplay');
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='adjustedGift.paymentSource.creditCardNumberReadOnly' ,SECONDARY_FIELD_DEFINITION_ID='paymentSource.creditCardNumberReadOnly' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='adjustedGift.creditCard' AND PAGE_TYPE='adjustedGiftView') AND `FIELD_DEFINITION_ID` = 'adjustedGift.selectedPaymentSource.creditCardNumberReadOnly' AND `SECONDARY_FIELD_DEFINITION_ID` = 'selectedPaymentSource.creditCardNumberReadOnly');
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='adjustedGift.paymentSource.creditCardTypeReadOnly' ,SECONDARY_FIELD_DEFINITION_ID='paymentSource.creditCardTypeReadOnly' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='adjustedGift.creditCard' AND PAGE_TYPE='adjustedGiftView') AND `FIELD_DEFINITION_ID` = 'adjustedGift.selectedPaymentSource.creditCardTypeReadOnly' AND `SECONDARY_FIELD_DEFINITION_ID` = 'selectedPaymentSource.creditCardTypeReadOnly');

UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='adjustedGift.paymentSource.achAccountNumberReadOnly' ,SECONDARY_FIELD_DEFINITION_ID='paymentSource.achAccountNumberReadOnly' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='adjustedGift.ach' AND PAGE_TYPE='adjustedGiftView') AND `FIELD_DEFINITION_ID` = 'adjustedGift.selectedPaymentSource.achAccountNumberReadOnly' AND `SECONDARY_FIELD_DEFINITION_ID` = 'selectedPaymentSource.achAccountNumberReadOnly');
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='adjustedGift.paymentSource.achHolderNameReadOnly' ,SECONDARY_FIELD_DEFINITION_ID='paymentSource.achHolderNameReadOnly' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='adjustedGift.ach' AND PAGE_TYPE='adjustedGiftView') AND `FIELD_DEFINITION_ID` = 'adjustedGift.selectedPaymentSource.achHolderNameReadOnly' AND `SECONDARY_FIELD_DEFINITION_ID` = 'selectedPaymentSource.achHolderNameReadOnly');
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='adjustedGift.paymentSource.achRoutingNumberReadOnly' ,SECONDARY_FIELD_DEFINITION_ID='paymentSource.achRoutingNumberReadOnly' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='adjustedGift.ach' AND PAGE_TYPE='adjustedGiftView') AND `FIELD_DEFINITION_ID` = 'adjustedGift.selectedPaymentSource.achRoutingNumberReadOnly' AND `SECONDARY_FIELD_DEFINITION_ID` = 'selectedPaymentSource.achRoutingNumberReadOnly');

UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='adjustedGift.distributionLines.amountReadOnly' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.amountReadOnly' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='adjustedGift.distribution' AND PAGE_TYPE='adjustedGiftView') AND `FIELD_DEFINITION_ID` = 'adjustedGift.lineAmountReadOnly' AND `SECONDARY_FIELD_DEFINITION_ID` IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='adjustedGift.distributionLines.customFieldMap[other_referenceReadOnly]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[other_referenceReadOnly]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='adjustedGift.distribution' AND PAGE_TYPE='adjustedGiftView') AND `FIELD_DEFINITION_ID` = 'adjustedGift.customFieldMap[other_referenceReadOnly]' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='adjustedGift.distributionLines.customFieldMap[referenceReadOnly]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[referenceReadOnly]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='adjustedGift.distribution' AND PAGE_TYPE='adjustedGiftView') AND `FIELD_DEFINITION_ID` = 'adjustedGift.customFieldMap[referenceReadOnly]' AND `SECONDARY_FIELD_DEFINITION_ID` IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='adjustedGift.distributionLines.projectCodeReadOnly' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.projectCodeReadOnly' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='adjustedGift.distribution' AND PAGE_TYPE='adjustedGiftView') AND `FIELD_DEFINITION_ID` = 'adjustedGift.projectCodeReadOnly' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='adjustedGift.distributionLines.motivationCodeReadOnly' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.motivationCodeReadOnly' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='adjustedGift.distribution' AND PAGE_TYPE='adjustedGiftView') AND `FIELD_DEFINITION_ID` = 'adjustedGift.motivationCodeReadOnly' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='adjustedGift.distributionLines.other_motivationCodeReadOnly' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.other_motivationCodeReadOnly' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='adjustedGift.distribution' AND PAGE_TYPE='adjustedGiftView') AND `FIELD_DEFINITION_ID` = 'adjustedGift.other_motivationCodeReadOnly' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='adjustedGift.distributionLines.percentageReadOnly' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.percentageReadOnly' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='adjustedGift.distribution' AND PAGE_TYPE='adjustedGiftView') AND `FIELD_DEFINITION_ID` = 'adjustedGift.percentageReadOnly' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);

UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='adjustedGift.distributionLines.customFieldMap[anonymousReadOnly]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[anonymousReadOnly]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='adjustedGift.extendedDistribution' AND PAGE_TYPE='adjustedGiftView') AND `FIELD_DEFINITION_ID` = 'adjustedGift.customFieldMap[anonymousReadOnly]' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='adjustedGift.distributionLines.customFieldMap[associatedPledgeIdReadOnly]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[associatedPledgeIdReadOnly]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='adjustedGift.extendedDistribution' AND PAGE_TYPE='adjustedGiftView') AND `FIELD_DEFINITION_ID` = 'adjustedGift.customFieldMap[associatedPledgeIdReadOnly]' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='adjustedGift.distributionLines.customFieldMap[associatedRecurringGiftIdReadOnly]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[associatedRecurringGiftIdReadOnly]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='adjustedGift.extendedDistribution' AND PAGE_TYPE='adjustedGiftView') AND `FIELD_DEFINITION_ID` = 'adjustedGift.customFieldMap[associatedRecurringGiftIdReadOnly]' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='adjustedGift.distributionLines.customFieldMap[messageReadOnly]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[messageReadOnly]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='adjustedGift.extendedDistribution' AND PAGE_TYPE='adjustedGiftView') AND `FIELD_DEFINITION_ID` = 'adjustedGift.customFieldMap[messageReadOnly]' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='adjustedGift.distributionLines.customFieldMap[notifiedReadOnly]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[notifiedReadOnly]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='adjustedGift.extendedDistribution' AND PAGE_TYPE='adjustedGiftView') AND `FIELD_DEFINITION_ID` = 'adjustedGift.customFieldMap[notifiedReadOnly]' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='adjustedGift.distributionLines.customFieldMap[onBehalfOfReadOnly]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[onBehalfOfReadOnly]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='adjustedGift.extendedDistribution' AND PAGE_TYPE='adjustedGiftView') AND `FIELD_DEFINITION_ID` = 'adjustedGift.customFieldMap[onBehalfOfReadOnly]' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='adjustedGift.distributionLines.customFieldMap[other_notifiedReadOnly]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[other_notifiedReadOnly]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='adjustedGift.extendedDistribution' AND PAGE_TYPE='adjustedGiftView') AND `FIELD_DEFINITION_ID` = 'adjustedGift.customFieldMap[other_notifiedReadOnly]' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='adjustedGift.distributionLines.customFieldMap[other_onBehalfOfReadOnly]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[other_onBehalfOfReadOnly]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='adjustedGift.extendedDistribution' AND PAGE_TYPE='adjustedGiftView') AND `FIELD_DEFINITION_ID` = 'adjustedGift.customFieldMap[other_onBehalfOfReadOnly]' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='adjustedGift.distributionLines.customFieldMap[other_tributeReferenceReadOnly]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[other_tributeReferenceReadOnly]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='adjustedGift.extendedDistribution' AND PAGE_TYPE='adjustedGiftView') AND `FIELD_DEFINITION_ID` = 'adjustedGift.customFieldMap[other_tributeReferenceReadOnly]' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='adjustedGift.distributionLines.customFieldMap[recognitionNameReadOnly]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[recognitionNameReadOnly]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='adjustedGift.extendedDistribution' AND PAGE_TYPE='adjustedGiftView') AND `FIELD_DEFINITION_ID` = 'adjustedGift.customFieldMap[recognitionNameReadOnly]' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='adjustedGift.distributionLines.customFieldMap[taxDeductibleReadOnly]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[taxDeductibleReadOnly]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='adjustedGift.extendedDistribution' AND PAGE_TYPE='adjustedGiftView') AND `FIELD_DEFINITION_ID` = 'adjustedGift.customFieldMap[taxDeductibleReadOnly]' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='adjustedGift.distributionLines.customFieldMap[tributeOccasionReadOnly]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[tributeOccasionReadOnly]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='adjustedGift.extendedDistribution' AND PAGE_TYPE='adjustedGiftView') AND `FIELD_DEFINITION_ID` = 'adjustedGift.customFieldMap[tributeOccasionReadOnly]' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='adjustedGift.distributionLines.customFieldMap[tributeReadOnly]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[tributeReadOnly]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='adjustedGift.extendedDistribution' AND PAGE_TYPE='adjustedGiftView') AND `FIELD_DEFINITION_ID` = 'adjustedGift.customFieldMap[tributeReadOnly]' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='adjustedGift.distributionLines.customFieldMap[tributeReferenceReadOnly]',SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[tributeReferenceReadOnly]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='adjustedGift.extendedDistribution' AND PAGE_TYPE='adjustedGiftView') AND `FIELD_DEFINITION_ID` = 'adjustedGift.customFieldMap[tributeReferenceReadOnly]' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='adjustedGift.distributionLines.customFieldMap[additional_tributeOccasionReadOnly]' ,SECONDARY_FIELD_DEFINITION_ID='distributionLines.customFieldMap[additional_tributeOccasionReadOnly]' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='adjustedGift.extendedDistribution' AND PAGE_TYPE='adjustedGiftView') AND `FIELD_DEFINITION_ID` = 'adjustedGift.customFieldMap[additional_tributeOccasionReadOnly]' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);

SET @Next_ID=(SELECT MAX(SECTION_DEFINITION_ID)+1000 FROM SECTION_DEFINITION);
-- 1003520

INSERT INTO `SECTION_DEFINITION` VALUES (@Next_ID, 'All Constituents', 'GRID', 'constituentList', 'ROLE_USER', 'constituentList.resultsInfo', '1', NULL);
INSERT INTO `SECTION_FIELD` (FIELD_ORDER,FIELD_DEFINITION_ID,SECTION_DEFINITION_ID,SITE_NAME)VALUES ('2000', 'constituent.accountNumber', @Next_ID, NULL);
INSERT INTO `SECTION_FIELD` (FIELD_ORDER,FIELD_DEFINITION_ID,SECTION_DEFINITION_ID,SITE_NAME)VALUES ('1500', 'constituent.constituentType', @Next_ID, NULL);
INSERT INTO `SECTION_FIELD` (FIELD_ORDER,FIELD_DEFINITION_ID,SECTION_DEFINITION_ID,SITE_NAME)VALUES ('4000', 'constituent.firstName', @Next_ID, NULL);
INSERT INTO `SECTION_FIELD` (FIELD_ORDER,FIELD_DEFINITION_ID,SECTION_DEFINITION_ID,SITE_NAME)VALUES ('1000', 'constituent.id', @Next_ID, NULL);
INSERT INTO `SECTION_FIELD` (FIELD_ORDER,FIELD_DEFINITION_ID,SECTION_DEFINITION_ID,SITE_NAME)VALUES ('7000', 'constituent.lapsedDonor', @Next_ID, NULL);
INSERT INTO `SECTION_FIELD` (FIELD_ORDER,FIELD_DEFINITION_ID,SECTION_DEFINITION_ID,SITE_NAME)VALUES ('3000', 'constituent.lastName', @Next_ID, NULL);
INSERT INTO `SECTION_FIELD` (FIELD_ORDER,FIELD_DEFINITION_ID,SECTION_DEFINITION_ID,SITE_NAME)VALUES ('6000', 'constituent.majorDonor', @Next_ID, NULL);
INSERT INTO `SECTION_FIELD` (FIELD_ORDER,FIELD_DEFINITION_ID,SECTION_DEFINITION_ID,SITE_NAME)VALUES ('5000', 'constituent.organizationName', @Next_ID, NULL);


SET @Next_ID=@Next_ID+1000;
INSERT INTO `SECTION_DEFINITION` VALUES (@Next_ID, 'Gift Distribution', 'DISTRIBUTION_LINE_GRID', 'giftCombinedDistributionLines', 'ROLE_USER', 'gift.distribution', '1', NULL);
INSERT INTO `SECTION_FIELD` (FIELD_ORDER,FIELD_DEFINITION_ID,SECONDARY_FIELD_DEFINITION_ID,SECTION_DEFINITION_ID,SITE_NAME)VALUES ('7000', 'gift.distributionLines.customFieldMap[other_reference]', 'distributionLines.customFieldMap[other_reference]', @Next_ID, NULL);
INSERT INTO `SECTION_FIELD`(FIELD_ORDER,FIELD_DEFINITION_ID,SECONDARY_FIELD_DEFINITION_ID,SECTION_DEFINITION_ID,SITE_NAME) VALUES ('6000', 'gift.distributionLines.customFieldMap[reference]', 'distributionLines.customFieldMap[reference]', @Next_ID, NULL);
INSERT INTO `SECTION_FIELD` (FIELD_ORDER,FIELD_DEFINITION_ID,SECONDARY_FIELD_DEFINITION_ID,SECTION_DEFINITION_ID,SITE_NAME)VALUES ('1000', 'gift.distributionLines.amount', 'distributionLines.amount', @Next_ID, NULL);
INSERT INTO `SECTION_FIELD` (FIELD_ORDER,FIELD_DEFINITION_ID,SECONDARY_FIELD_DEFINITION_ID,SECTION_DEFINITION_ID,SITE_NAME)VALUES ('4000', 'gift.distributionLines.motivationCode', 'distributionLines.motivationCode', @Next_ID, NULL);
INSERT INTO `SECTION_FIELD`(FIELD_ORDER,FIELD_DEFINITION_ID,SECONDARY_FIELD_DEFINITION_ID,SECTION_DEFINITION_ID,SITE_NAME) VALUES ('5000', 'gift.distributionLines.other_motivationCode', 'distributionLines.other_motivationCode', @Next_ID, NULL);
INSERT INTO `SECTION_FIELD`(FIELD_ORDER,FIELD_DEFINITION_ID,SECONDARY_FIELD_DEFINITION_ID,SECTION_DEFINITION_ID,SITE_NAME) VALUES ('2000', 'gift.distributionLines.percentage', 'distributionLines.percentage', @Next_ID, NULL);
INSERT INTO `SECTION_FIELD` (FIELD_ORDER,FIELD_DEFINITION_ID,SECONDARY_FIELD_DEFINITION_ID,SECTION_DEFINITION_ID,SITE_NAME)VALUES ('3000', 'gift.distributionLines.projectCode', 'distributionLines.projectCode', @Next_ID, NULL);

SET @Next_ID=@Next_ID+1000;

INSERT INTO `SECTION_DEFINITION` VALUES (@Next_ID, 'Extended Distribution Lines', 'GRID_HIDDEN_ROW', 'giftCombinedDistributionLines', 'ROLE_USER', 'gift.extendedDistribution', '2', NULL);
INSERT INTO `SECTION_FIELD`(FIELD_ORDER,FIELD_DEFINITION_ID,SECONDARY_FIELD_DEFINITION_ID,SECTION_DEFINITION_ID,SITE_NAME) VALUES ('5000', 'gift.distributionLines.customFieldMap[additional_tributeOccasion]', 'distributionLines.customFieldMap[additional_tributeOccasion]', @Next_ID, NULL);
INSERT INTO `SECTION_FIELD` (FIELD_ORDER,FIELD_DEFINITION_ID,SECONDARY_FIELD_DEFINITION_ID,SECTION_DEFINITION_ID,SITE_NAME) VALUES ('8000', 'gift.distributionLines.customFieldMap[anonymous]', 'distributionLines.customFieldMap[anonymous]', @Next_ID, NULL);
INSERT INTO `SECTION_FIELD` (FIELD_ORDER,FIELD_DEFINITION_ID,SECONDARY_FIELD_DEFINITION_ID,SECTION_DEFINITION_ID,SITE_NAME) VALUES ('0', 'gift.distributionLines.customFieldMap[associatedPledgeId]', 'distributionLines.customFieldMap[associatedPledgeId]', @Next_ID, NULL);
INSERT INTO `SECTION_FIELD` (FIELD_ORDER,FIELD_DEFINITION_ID,SECONDARY_FIELD_DEFINITION_ID,SECTION_DEFINITION_ID,SITE_NAME) VALUES ('500', 'gift.distributionLines.customFieldMap[associatedRecurringGiftId]', 'distributionLines.customFieldMap[associatedRecurringGiftId]', @Next_ID, NULL);
INSERT INTO `SECTION_FIELD` (FIELD_ORDER,FIELD_DEFINITION_ID,SECONDARY_FIELD_DEFINITION_ID,SECTION_DEFINITION_ID,SITE_NAME) VALUES ('12000', 'gift.distributionLines.customFieldMap[message]', 'distributionLines.customFieldMap[message]', @Next_ID, NULL);
INSERT INTO `SECTION_FIELD` (FIELD_ORDER,FIELD_DEFINITION_ID,SECONDARY_FIELD_DEFINITION_ID,SECTION_DEFINITION_ID,SITE_NAME) VALUES ('10000', 'gift.distributionLines.customFieldMap[notified]', 'distributionLines.customFieldMap[notified]', @Next_ID, NULL);
INSERT INTO `SECTION_FIELD` (FIELD_ORDER,FIELD_DEFINITION_ID,SECONDARY_FIELD_DEFINITION_ID,SECTION_DEFINITION_ID,SITE_NAME) VALUES ('6000', 'gift.distributionLines.customFieldMap[onBehalfOf]', 'distributionLines.customFieldMap[onBehalfOf]', @Next_ID, NULL);
INSERT INTO `SECTION_FIELD` (FIELD_ORDER,FIELD_DEFINITION_ID,SECONDARY_FIELD_DEFINITION_ID,SECTION_DEFINITION_ID,SITE_NAME) VALUES ('11000', 'gift.distributionLines.customFieldMap[other_notified]', 'distributionLines.customFieldMap[other_notified]', @Next_ID, NULL);
INSERT INTO `SECTION_FIELD` (FIELD_ORDER,FIELD_DEFINITION_ID,SECONDARY_FIELD_DEFINITION_ID,SECTION_DEFINITION_ID,SITE_NAME) VALUES ('4000', 'gift.distributionLines.customFieldMap[tributeOccasion]', 'distributionLines.customFieldMap[tributeOccasion]', @Next_ID, NULL);
INSERT INTO `SECTION_FIELD` (FIELD_ORDER,FIELD_DEFINITION_ID,SECONDARY_FIELD_DEFINITION_ID,SECTION_DEFINITION_ID,SITE_NAME) VALUES ('2000', 'gift.distributionLines.customFieldMap[tributeReference]', 'distributionLines.customFieldMap[tributeReference]', @Next_ID, NULL);
INSERT INTO `SECTION_FIELD` (FIELD_ORDER,FIELD_DEFINITION_ID,SECONDARY_FIELD_DEFINITION_ID,SECTION_DEFINITION_ID,SITE_NAME) VALUES ('1000', 'gift.distributionLines.customFieldMap[tribute]', 'distributionLines.customFieldMap[tribute]', @Next_ID, NULL);
INSERT INTO `SECTION_FIELD` (FIELD_ORDER,FIELD_DEFINITION_ID,SECONDARY_FIELD_DEFINITION_ID,SECTION_DEFINITION_ID,SITE_NAME) VALUES ('7000', 'gift.distributionLines.customFieldMap[other_onBehalfOf]', 'distributionLines.customFieldMap[other_onBehalfOf]', @Next_ID, NULL);
INSERT INTO `SECTION_FIELD` (FIELD_ORDER,FIELD_DEFINITION_ID,SECONDARY_FIELD_DEFINITION_ID,SECTION_DEFINITION_ID,SITE_NAME) VALUES ('3000', 'gift.distributionLines.customFieldMap[other_tributeReference]', 'distributionLines.customFieldMap[other_tributeReference]', @Next_ID, NULL);
INSERT INTO `SECTION_FIELD` (FIELD_ORDER,FIELD_DEFINITION_ID,SECONDARY_FIELD_DEFINITION_ID,SECTION_DEFINITION_ID,SITE_NAME) VALUES ('9000', 'gift.distributionLines.customFieldMap[recognitionName]', 'distributionLines.customFieldMap[recognitionName]', @Next_ID, NULL);
INSERT INTO `SECTION_FIELD` (FIELD_ORDER,FIELD_DEFINITION_ID,SECONDARY_FIELD_DEFINITION_ID,SECTION_DEFINITION_ID,SITE_NAME) VALUES ('13000', 'gift.distributionLines.customFieldMap[taxDeductible]', 'distributionLines.customFieldMap[taxDeductible]', @Next_ID, NULL);

INSERT INTO `SECTION_FIELD` (FIELD_ORDER,FIELD_DEFINITION_ID,SECONDARY_FIELD_DEFINITION_ID,SECTION_DEFINITION_ID,SITE_NAME) SELECT '4000', 'gift.paymentSource.creditCardExpirationDisplay', 'paymentSource.creditCardExpirationDisplay',SECTION_DEFINITION_ID,NULL FROM SECTION_DEFINITION WHERE SECTION_NAME='gift.creditCard' AND PAGE_TYPE='gift';
INSERT INTO `SECTION_FIELD` (FIELD_ORDER,FIELD_DEFINITION_ID,SECONDARY_FIELD_DEFINITION_ID,SECTION_DEFINITION_ID,SITE_NAME) SELECT '2000', 'gift.paymentSource.creditCardTypeReadOnly', 'paymentSource.creditCardTypeReadOnly',SECTION_DEFINITION_ID,NULL FROM SECTION_DEFINITION WHERE SECTION_NAME='gift.creditCard' AND PAGE_TYPE='gift';
INSERT INTO `SECTION_FIELD` (FIELD_ORDER,FIELD_DEFINITION_ID,SECONDARY_FIELD_DEFINITION_ID,SECTION_DEFINITION_ID,SITE_NAME) SELECT '3000', 'gift.paymentSource.creditCardNumberReadOnly', 'paymentSource.creditCardNumberReadOnly',SECTION_DEFINITION_ID,NULL FROM SECTION_DEFINITION WHERE SECTION_NAME='gift.creditCard' AND PAGE_TYPE='gift';
INSERT INTO `SECTION_FIELD` (FIELD_ORDER,FIELD_DEFINITION_ID,SECONDARY_FIELD_DEFINITION_ID,SECTION_DEFINITION_ID,SITE_NAME) SELECT '1000', 'gift.paymentSource.creditCardHolderNameReadOnly', 'paymentSource.creditCardHolderNameReadOnly',SECTION_DEFINITION_ID,NULL FROM SECTION_DEFINITION WHERE SECTION_NAME='gift.creditCard' AND PAGE_TYPE='gift';

INSERT INTO `SECTION_FIELD` (FIELD_ORDER,FIELD_DEFINITION_ID,SECONDARY_FIELD_DEFINITION_ID,SECTION_DEFINITION_ID,SITE_NAME) SELECT '4000', 'gift.paymentSource.achRoutingNumberReadOnly', 'paymentSource.achRoutingNumberReadOnly',SECTION_DEFINITION_ID,NULL FROM SECTION_DEFINITION WHERE SECTION_NAME='gift.ach' AND PAGE_TYPE='gift';
INSERT INTO `SECTION_FIELD` (FIELD_ORDER,FIELD_DEFINITION_ID,SECONDARY_FIELD_DEFINITION_ID,SECTION_DEFINITION_ID,SITE_NAME) SELECT '1000', 'gift.paymentSource.achHolderNameReadOnly', 'paymentSource.achHolderNameReadOnly',SECTION_DEFINITION_ID,NULL FROM SECTION_DEFINITION WHERE SECTION_NAME='gift.ach' AND PAGE_TYPE='gift';
INSERT INTO `SECTION_FIELD` (FIELD_ORDER,FIELD_DEFINITION_ID,SECONDARY_FIELD_DEFINITION_ID,SECTION_DEFINITION_ID,SITE_NAME) SELECT '3000', 'gift.paymentSource.achAccountNumberReadOnly', 'paymentSource.achAccountNumberReadOnly',SECTION_DEFINITION_ID,NULL FROM SECTION_DEFINITION WHERE SECTION_NAME='gift.ach' AND PAGE_TYPE='gift';

INSERT INTO `SECTION_FIELD` (FIELD_ORDER,FIELD_DEFINITION_ID,SECONDARY_FIELD_DEFINITION_ID,SECTION_DEFINITION_ID,SITE_NAME) SELECT '8000', 'recurringGift.paymentSource.creditCardExpirationDisplay', 'paymentSource.creditCardExpirationDisplay',SECTION_DEFINITION_ID,NULL FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGift.creditCard' AND PAGE_TYPE='recurringGift';
INSERT INTO `SECTION_FIELD` (FIELD_ORDER,FIELD_DEFINITION_ID,SECONDARY_FIELD_DEFINITION_ID,SECTION_DEFINITION_ID,SITE_NAME) SELECT '7000', 'recurringGift.paymentSource.creditCardNumberReadOnly', 'paymentSource.creditCardNumberReadOnly',SECTION_DEFINITION_ID,NULL FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGift.creditCard' AND PAGE_TYPE='recurringGift';
INSERT INTO `SECTION_FIELD` (FIELD_ORDER,FIELD_DEFINITION_ID,SECONDARY_FIELD_DEFINITION_ID,SECTION_DEFINITION_ID,SITE_NAME) SELECT '6000', 'recurringGift.paymentSource.creditCardTypeReadOnly', 'paymentSource.creditCardTypeReadOnly',SECTION_DEFINITION_ID,NULL FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGift.creditCard' AND PAGE_TYPE='recurringGift';
INSERT INTO `SECTION_FIELD` (FIELD_ORDER,FIELD_DEFINITION_ID,SECONDARY_FIELD_DEFINITION_ID,SECTION_DEFINITION_ID,SITE_NAME) SELECT '5000', 'recurringGift.paymentSource.creditCardHolderNameReadOnly', 'paymentSource.creditCardHolderNameReadOnly',SECTION_DEFINITION_ID,NULL FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGift.creditCard' AND PAGE_TYPE='recurringGift';

INSERT INTO `SECTION_FIELD` (FIELD_ORDER,FIELD_DEFINITION_ID,SECONDARY_FIELD_DEFINITION_ID,SECTION_DEFINITION_ID,SITE_NAME) SELECT '6000', 'recurringGift.paymentSource.achAccountNumberReadOnly', 'paymentSource.achAccountNumberReadOnly',SECTION_DEFINITION_ID,NULL FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGift.ach' AND PAGE_TYPE='recurringGift';
INSERT INTO `SECTION_FIELD` (FIELD_ORDER,FIELD_DEFINITION_ID,SECONDARY_FIELD_DEFINITION_ID,SECTION_DEFINITION_ID,SITE_NAME) SELECT '4000', 'recurringGift.paymentSource.achHolderNameReadOnly', 'paymentSource.achHolderNameReadOnly',SECTION_DEFINITION_ID,NULL FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGift.ach' AND PAGE_TYPE='recurringGift';
INSERT INTO `SECTION_FIELD` (FIELD_ORDER,FIELD_DEFINITION_ID,SECONDARY_FIELD_DEFINITION_ID,SECTION_DEFINITION_ID,SITE_NAME) SELECT '5000', 'recurringGift.paymentSource.achRoutingNumberReadOnly', 'paymentSource.achRoutingNumberReadOnly',SECTION_DEFINITION_ID,NULL FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGift.ach' AND PAGE_TYPE='recurringGift';


/* New by alex */
UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='paymentSource.profileReadOnly' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='paymentSourceList.resultsInfo' AND PAGE_TYPE='paymentSourceList') AND `FIELD_DEFINITION_ID` = 'paymentSource.profile' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);

DELETE FROM `SECTION_FIELD`  WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='paymentSourceList.resultsInfo' AND PAGE_TYPE='paymentSourceList') AND `FIELD_DEFINITION_ID` = 'paymentSource.phone.numberReadOnly' AND `SECONDARY_FIELD_DEFINITION_ID` = 'phone.numberReadOnly') ;

UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='paymentSource.paymentTypeReadOnly' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='paymentSourceList.resultsInfo' AND PAGE_TYPE='paymentSourceList') AND `FIELD_DEFINITION_ID` = 'paymentSource.paymentType' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);

UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='paymentSource.inactiveReadOnly' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='paymentSourceList.resultsInfo' AND PAGE_TYPE='paymentSourceList') AND `FIELD_DEFINITION_ID` = 'paymentSource.inactive' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);

UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='paymentSource.creditCardTypeReadOnly' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='paymentSourceList.resultsInfo' AND PAGE_TYPE='paymentSourceList') AND `FIELD_DEFINITION_ID` = 'paymentSource.creditCardType' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);

UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='paymentSource.creditCardHolderNameReadOnly' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='paymentSourceList.resultsInfo' AND PAGE_TYPE='paymentSourceList') AND `FIELD_DEFINITION_ID` = 'paymentSource.creditCardHolderName' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);

UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='paymentSource.creditCardExpirationDisplay' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='paymentSourceList.resultsInfo' AND PAGE_TYPE='paymentSourceList') AND `FIELD_DEFINITION_ID` = 'paymentSource.creditCardExpiration' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);

DELETE FROM `SECTION_FIELD`  WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='paymentSourceList.resultsInfo' AND PAGE_TYPE='paymentSourceList')  AND `FIELD_DEFINITION_ID` = 'paymentSource.address.shortAddressReadOnly' AND `SECONDARY_FIELD_DEFINITION_ID` = 'address.shortDisplay') ;

UPDATE `SECTION_FIELD` SET FIELD_DEFINITION_ID='paymentSource.achHolderNameReadOnly' WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='paymentSourceList.resultsInfo' AND PAGE_TYPE='paymentSourceList') AND `FIELD_DEFINITION_ID` = 'paymentSource.achHolderName' AND `SECONDARY_FIELD_DEFINITION_ID`  IS NULL);

INSERT INTO `SECTION_FIELD` (FIELD_ORDER,FIELD_DEFINITION_ID,SECONDARY_FIELD_DEFINITION_ID,SECTION_DEFINITION_ID,SITE_NAME) SELECT '200', 'paymentSource.id', NULL, SECTION_DEFINITION_ID,NULL FROM SECTION_DEFINITION WHERE SECTION_NAME='paymentSourceList.resultsInfo' AND PAGE_TYPE='paymentSourceList';

DELETE FROM SECTION_FIELD  WHERE SECTION_DEFINITION_ID = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='constituent.contactInfo' AND PAGE_TYPE='constituent') AND FIELD_DEFINITION_ID='constituent.primaryAddress' AND SECONDARY_FIELD_DEFINITION_ID='address.addressLine3';

INSERT INTO `SECTION_FIELD` (FIELD_ORDER,FIELD_DEFINITION_ID,SECONDARY_FIELD_DEFINITION_ID,SECTION_DEFINITION_ID,SITE_NAME) SELECT '13000', 'constituent.primaryAddress.addressLine3', 'address.addressLine3',SECTION_DEFINITION_ID,NULL FROM SECTION_DEFINITION WHERE SECTION_NAME='constituent.contactInfo' AND PAGE_TYPE='constituent';


INSERT INTO `SECTION_FIELD` (FIELD_ORDER,FIELD_DEFINITION_ID,SECONDARY_FIELD_DEFINITION_ID,SECTION_DEFINITION_ID,SITE_NAME) SELECT '1000', 'giftInKind.id', NULL, SECTION_DEFINITION_ID,NULL FROM SECTION_DEFINITION WHERE SECTION_NAME='giftInKindList.resultsInfo' AND PAGE_TYPE='giftInKindList';

INSERT INTO `SECTION_FIELD` (FIELD_ORDER,FIELD_DEFINITION_ID,SECONDARY_FIELD_DEFINITION_ID,SECTION_DEFINITION_ID,SITE_NAME) SELECT '1000', 'communicationHistory.id', NULL, SECTION_DEFINITION_ID,NULL FROM SECTION_DEFINITION WHERE SECTION_NAME='communicationHistoryList.resultsInfo' AND PAGE_TYPE='communicationHistoryList';

INSERT INTO `SECTION_FIELD` (FIELD_ORDER,FIELD_DEFINITION_ID,SECONDARY_FIELD_DEFINITION_ID,SECTION_DEFINITION_ID,SITE_NAME) SELECT '4000', 'communicationHistory.customFieldMap[assignedTo]', NULL, SECTION_DEFINITION_ID,NULL FROM SECTION_DEFINITION WHERE SECTION_NAME='communicationHistoryList.resultsInfo' AND PAGE_TYPE='communicationHistoryList';

INSERT INTO `SECTION_FIELD` (FIELD_ORDER,FIELD_DEFINITION_ID,SECONDARY_FIELD_DEFINITION_ID,SECTION_DEFINITION_ID,SITE_NAME) SELECT '3500', 'paymentHistory.paymentStatus', NULL, SECTION_DEFINITION_ID,NULL FROM SECTION_DEFINITION WHERE SECTION_NAME='paymentHistoryList.resultsInfo' AND PAGE_TYPE='paymentHistory';

INSERT INTO `SECTION_FIELD` (FIELD_ORDER,FIELD_DEFINITION_ID,SECONDARY_FIELD_DEFINITION_ID,SECTION_DEFINITION_ID,SITE_NAME) SELECT '6000', 'recurringGift.startDate', NULL, SECTION_DEFINITION_ID,NULL FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGiftList.resultsInfo' AND PAGE_TYPE='recurringGiftList';

INSERT INTO `SECTION_FIELD` (FIELD_ORDER,FIELD_DEFINITION_ID,SECONDARY_FIELD_DEFINITION_ID,SECTION_DEFINITION_ID,SITE_NAME) SELECT '1000', 'recurringGift.id', NULL, SECTION_DEFINITION_ID,NULL FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGiftList.resultsInfo' AND PAGE_TYPE='recurringGiftList';

INSERT INTO `SECTION_FIELD` (FIELD_ORDER,FIELD_DEFINITION_ID,SECONDARY_FIELD_DEFINITION_ID,SECTION_DEFINITION_ID,SITE_NAME) SELECT '5700', 'recurringGift.frequency', NULL, SECTION_DEFINITION_ID,NULL FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGiftList.resultsInfo' AND PAGE_TYPE='recurringGiftList';

INSERT INTO `SECTION_FIELD` (FIELD_ORDER,FIELD_DEFINITION_ID,SECONDARY_FIELD_DEFINITION_ID,SECTION_DEFINITION_ID,SITE_NAME) SELECT '7000', 'recurringGift.endDate', NULL, SECTION_DEFINITION_ID,NULL FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGiftList.resultsInfo' AND PAGE_TYPE='recurringGiftList';

INSERT INTO `SECTION_FIELD` (FIELD_ORDER,FIELD_DEFINITION_ID,SECONDARY_FIELD_DEFINITION_ID,SECTION_DEFINITION_ID,SITE_NAME) SELECT '8000', 'recurringGift.activate', NULL, SECTION_DEFINITION_ID,NULL FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGiftList.resultsInfo' AND PAGE_TYPE='recurringGiftList';

INSERT INTO `SECTION_FIELD` (FIELD_ORDER,FIELD_DEFINITION_ID,SECONDARY_FIELD_DEFINITION_ID,SECTION_DEFINITION_ID,SITE_NAME) SELECT '3500', 'pledge.recurring', NULL, SECTION_DEFINITION_ID,NULL FROM SECTION_DEFINITION WHERE SECTION_NAME='pledgeList.resultsInfo' AND PAGE_TYPE='pledgeList';

INSERT INTO `SECTION_FIELD` (FIELD_ORDER,FIELD_DEFINITION_ID,SECONDARY_FIELD_DEFINITION_ID,SECTION_DEFINITION_ID,SITE_NAME) SELECT '8000', 'pledge.pledgeDate', NULL, SECTION_DEFINITION_ID,NULL FROM SECTION_DEFINITION WHERE SECTION_NAME='pledgeList.resultsInfo' AND PAGE_TYPE='pledgeList';

INSERT INTO `SECTION_FIELD` (FIELD_ORDER,FIELD_DEFINITION_ID,SECONDARY_FIELD_DEFINITION_ID,SECTION_DEFINITION_ID,SITE_NAME) SELECT '1000', 'pledge.id', NULL, SECTION_DEFINITION_ID,NULL FROM SECTION_DEFINITION WHERE SECTION_NAME='pledgeList.resultsInfo' AND PAGE_TYPE='pledgeList';

UPDATE `SECTION_FIELD` SET `FIELD_ORDER`='2000'  WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='gift.ach' AND PAGE_TYPE='gift') AND `FIELD_DEFINITION_ID` = 'gift.paymentSource.achRoutingNumberReadOnly' AND `SECONDARY_FIELD_DEFINITION_ID` = 'paymentSource.achRoutingNumberReadOnly') ;

UPDATE `SECTION_FIELD` SET `FIELD_ORDER`='2000'  WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGiftList.resultsInfo' AND PAGE_TYPE='recurringGiftList') AND `FIELD_DEFINITION_ID` = 'recurringGift.recurringGiftStatus' AND `SECONDARY_FIELD_DEFINITION_ID` IS NULL) ;

UPDATE `SECTION_FIELD` SET `FIELD_ORDER`='4000'  WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGiftList.resultsInfo' AND PAGE_TYPE='recurringGiftList') AND `FIELD_DEFINITION_ID` = 'recurringGift.amountTotal' AND `SECONDARY_FIELD_DEFINITION_ID` IS NULL) ;

UPDATE `SECTION_FIELD` SET `FIELD_ORDER`='5500'  WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGiftList.resultsInfo' AND PAGE_TYPE='recurringGiftList') AND `FIELD_DEFINITION_ID` = 'recurringGift.amountRemaining' AND `SECONDARY_FIELD_DEFINITION_ID` IS NULL) ;

UPDATE `SECTION_FIELD` SET `FIELD_ORDER`='3000'  WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGiftList.resultsInfo' AND PAGE_TYPE='recurringGiftList') AND `FIELD_DEFINITION_ID` = 'recurringGift.amountPerGift' AND `SECONDARY_FIELD_DEFINITION_ID` IS NULL) ;

UPDATE `SECTION_FIELD` SET `FIELD_ORDER`='5000'  WHERE (`SECTION_DEFINITION_ID` = (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE SECTION_NAME='recurringGiftList.resultsInfo' AND PAGE_TYPE='recurringGiftList') AND `FIELD_DEFINITION_ID` = 'recurringGift.amountPaid' AND `SECONDARY_FIELD_DEFINITION_ID` IS NULL) ;


/* End new by alex */


	/*End   of batch : 1 */
/* SYNC TABLE : `FIELD_REQUIRED` */

	/*Start of batch : 1 */
DELETE FROM `FIELD_REQUIRED`  WHERE (`FIELD_DEFINITION_ID` = 'gift.selectedPaymentSource' and SECTION_NAME = 'gift.payment') ;
DELETE FROM `FIELD_REQUIRED`  WHERE (`FIELD_DEFINITION_ID` = 'recurringGift.selectedPaymentSource' and SECTION_NAME = 'recurringGift.payment') ;
DELETE FROM `FIELD_REQUIRED`  WHERE (`FIELD_DEFINITION_ID` = 'recurringGift.projectCode' and SECTION_NAME = 'recurringGift.distribution') ;
DELETE FROM `FIELD_REQUIRED`  WHERE (`FIELD_DEFINITION_ID` = 'pledge.projectCode' and SECTION_NAME = 'pledge.distribution') ;
INSERT INTO `FIELD_REQUIRED` VALUES ('1066001', '1', 'recurringGift.distribution', NULL, 'recurringGift.distributionLines.projectCode', 'distributionLines.projectCode');
INSERT INTO `FIELD_REQUIRED` VALUES ('1022000', '1', 'address.info', NULL, 'address.stateProvince', NULL);
INSERT INTO `FIELD_REQUIRED` VALUES ('1010000', '1', 'address.edit', NULL, 'address.stateProvince', NULL);
INSERT INTO `FIELD_REQUIRED` VALUES ('1068001', '1', 'pledge.distribution', NULL, 'pledge.distributionLines.projectCode', 'distributionLines.projectCode');
INSERT INTO `FIELD_REQUIRED` VALUES ('1000136', '1', 'recurringGift.payment', NULL, 'recurringGift.address.stateProvince', 'address.stateProvince');
INSERT INTO `FIELD_REQUIRED` VALUES ('10000013', '1', 'gift.payment', NULL, 'gift.address.stateProvince', 'address.stateProvince');
INSERT INTO `FIELD_REQUIRED` VALUES ('1000086', '1', 'paymentSource.infoEdit', NULL, 'paymentSource.address.stateProvince', 'address.stateProvince');
INSERT INTO `FIELD_REQUIRED` VALUES ('1000080', '1', 'paymentSource.info', NULL, 'paymentSource.address.stateProvince', 'address.stateProvince');
UPDATE `FIELD_REQUIRED` SET `FIELD_REQUIRED_ID`='1068000', `REQUIRED`='1', `SECTION_NAME`='pledge.distribution', `SITE_NAME`=NULL, `FIELD_DEFINITION_ID`='pledge.distributionLines.percentage', `SECONDARY_FIELD_DEFINITION_ID`='distributionLines.percentage'  WHERE (`FIELD_REQUIRED_ID` = 1068000) ;
UPDATE `FIELD_REQUIRED` SET `FIELD_REQUIRED_ID`='1066000', `REQUIRED`='1', `SECTION_NAME`='recurringGift.distribution', `SITE_NAME`=NULL, `FIELD_DEFINITION_ID`='recurringGift.distributionLines.percentage', `SECONDARY_FIELD_DEFINITION_ID`='distributionLines.percentage'  WHERE (`FIELD_REQUIRED_ID` = 1066000) ;
UPDATE `FIELD_REQUIRED` SET `FIELD_REQUIRED_ID`='1065000', `REQUIRED`='1', `SECTION_NAME`='recurringGift.distribution', `SITE_NAME`=NULL, `FIELD_DEFINITION_ID`='recurringGift.distributionLines.amount', `SECONDARY_FIELD_DEFINITION_ID`='distributionLines.amount'  WHERE (`FIELD_REQUIRED_ID` = 1065000) ;
UPDATE `FIELD_REQUIRED` SET `FIELD_REQUIRED_ID`='1064000', `REQUIRED`='1', `SECTION_NAME`='gift.distribution', `SITE_NAME`=NULL, `FIELD_DEFINITION_ID`='gift.distributionLines.percentage', `SECONDARY_FIELD_DEFINITION_ID`='distributionLines.percentage'  WHERE (`FIELD_REQUIRED_ID` = 1064000) ;
UPDATE `FIELD_REQUIRED` SET `FIELD_REQUIRED_ID`='1063000', `REQUIRED`='1', `SECTION_NAME`='gift.distribution', `SITE_NAME`=NULL, `FIELD_DEFINITION_ID`='gift.distributionLines.amount', `SECONDARY_FIELD_DEFINITION_ID`='distributionLines.amount'  WHERE (`FIELD_REQUIRED_ID` = 1063000) ;
UPDATE `FIELD_REQUIRED` SET `FIELD_REQUIRED_ID`='1062001', `REQUIRED`='1', `SECTION_NAME`='gift.distribution', `SITE_NAME`=NULL, `FIELD_DEFINITION_ID`='gift.distributionLines.projectCode', `SECONDARY_FIELD_DEFINITION_ID`='distributionLines.projectCode'  WHERE (`FIELD_REQUIRED_ID` = 1062001) ;
UPDATE `FIELD_REQUIRED` SET `FIELD_REQUIRED_ID`='1062000', `REQUIRED`='1', `SECTION_NAME`='giftInKind.details', `SITE_NAME`=NULL, `FIELD_DEFINITION_ID`='giftInKind.details.description', `SECONDARY_FIELD_DEFINITION_ID`='details.description'  WHERE (`FIELD_REQUIRED_ID` = 1062000) ;
UPDATE `FIELD_REQUIRED` SET `FIELD_REQUIRED_ID`='1061000', `REQUIRED`='1', `SECTION_NAME`='giftInKind.details', `SITE_NAME`=NULL, `FIELD_DEFINITION_ID`='giftInKind.details.detailFairMarketValue', `SECONDARY_FIELD_DEFINITION_ID`='details.detailFairMarketValue'  WHERE (`FIELD_REQUIRED_ID` = 1061000) ;
UPDATE `FIELD_REQUIRED` SET `FIELD_REQUIRED_ID`='1001120', `REQUIRED`='1', `SECTION_NAME`='adjustedGift.distribution', `SITE_NAME`=NULL, `FIELD_DEFINITION_ID`='adjustedGift.distributionLines.percentage', `SECONDARY_FIELD_DEFINITION_ID`='distributionLines.percentage'  WHERE (`FIELD_REQUIRED_ID` = 1001120) ;
UPDATE `FIELD_REQUIRED` SET `FIELD_REQUIRED_ID`='1001110', `REQUIRED`='1', `SECTION_NAME`='adjustedGift.distribution', `SITE_NAME`=NULL, `FIELD_DEFINITION_ID`='adjustedGift.distributionLines.amount', `SECONDARY_FIELD_DEFINITION_ID`='distributionLines.amount'  WHERE (`FIELD_REQUIRED_ID` = 1001110) ;
UPDATE `FIELD_REQUIRED` SET `FIELD_REQUIRED_ID`='1000930', `REQUIRED`='1', `SECTION_NAME`='adjustedGift.payment', `SITE_NAME`=NULL, `FIELD_DEFINITION_ID`='adjustedGift.paymentSource.id', `SECONDARY_FIELD_DEFINITION_ID`=NULL  WHERE (`FIELD_REQUIRED_ID` = 1000930) ;
UPDATE `FIELD_REQUIRED` SET `FIELD_REQUIRED_ID`='1067000', `REQUIRED`='1', `SECTION_NAME`='pledge.distribution', `SITE_NAME`=NULL, `FIELD_DEFINITION_ID`='pledge.distributionLines.amount', `SECONDARY_FIELD_DEFINITION_ID`='distributionLines.amount'  WHERE (`FIELD_REQUIRED_ID` = 1067000) ;
	/*End   of batch : 1 */
/* SYNC TABLE : `FIELD_VALIDATION` */

	/*Start of batch : 1 */
UPDATE `FIELD_VALIDATION` SET `VALIDATION_ID`='1001000', `VALIDATION_REGEX`='extensions:isEmail', `SECTION_NAME`='constituent.contactInfo', `SITE_NAME`=NULL, `FIELD_DEFINITION_ID`='constituent.primaryEmail.emailAddress', `SECONDARY_FIELD_DEFINITION_ID`='email.emailAddress'  WHERE (`VALIDATION_ID` = 1001000) ;
	/*End   of batch : 1 */
/* SYNC TABLE : `FIELD_CONDITION` */

	/*Start of batch : 1 */
INSERT INTO `FIELD_CONDITION` VALUES ('1900001', 'US|CA', 'address.country', NULL, '1022000', NULL);
INSERT INTO `FIELD_CONDITION` VALUES ('1000052', 'US|CA', 'paymentSource.address.country', 'address.country', '1000086', NULL);
INSERT INTO `FIELD_CONDITION` VALUES ('1000039', 'US|CA', 'paymentSource.address.country', 'address.country', '1000080', NULL);
INSERT INTO `FIELD_CONDITION` VALUES ('1900000', 'US|CA', 'address.country', NULL, '1010000', NULL);
INSERT INTO `FIELD_CONDITION` VALUES ('1000072', 'US|CA', 'recurringGift.address.country', 'address.country', '1000136', NULL);
INSERT INTO `FIELD_CONDITION` VALUES ('1000013', 'US|CA', 'gift.address.country', 'address.country', '10000013', NULL);
UPDATE `FIELD_CONDITION` SET `CONDITION_ID`='1000008', `DEPENDENT_VALUE`='0', `DEPENDENT_FIELD_DEFINITION_ID`='gift.paymentSource.achRoutingNumber', `DEPENDENT_SECONDARY_FIELD_DEFINITION_ID`='paymentSource.id', `FIELD_REQUIRED_ID`='1000009', `VALIDATION_ID`=NULL  WHERE (`CONDITION_ID` = 1000008) ;
UPDATE `FIELD_CONDITION` SET `CONDITION_ID`='10000026', `DEPENDENT_VALUE`='0', `DEPENDENT_FIELD_DEFINITION_ID`='paymentSource.address.customFieldMap[addressType]', `DEPENDENT_SECONDARY_FIELD_DEFINITION_ID`='address.id', `FIELD_REQUIRED_ID`='10000026', `VALIDATION_ID`=NULL  WHERE (`CONDITION_ID` = 10000026) ;
UPDATE `FIELD_CONDITION` SET `CONDITION_ID`='1000010', `DEPENDENT_VALUE`='0', `DEPENDENT_FIELD_DEFINITION_ID`='gift.paymentSource.achAccountNumber', `DEPENDENT_SECONDARY_FIELD_DEFINITION_ID`='paymentSource.id', `FIELD_REQUIRED_ID`='1000010', `VALIDATION_ID`=NULL  WHERE (`CONDITION_ID` = 1000010) ;
UPDATE `FIELD_CONDITION` SET `CONDITION_ID`='1000011', `DEPENDENT_VALUE`='0', `DEPENDENT_FIELD_DEFINITION_ID`='gift.address.addressLine1', `DEPENDENT_SECONDARY_FIELD_DEFINITION_ID`='address.id', `FIELD_REQUIRED_ID`='10000011', `VALIDATION_ID`=NULL  WHERE (`CONDITION_ID` = 1000011) ;
UPDATE `FIELD_CONDITION` SET `CONDITION_ID`='1000012', `DEPENDENT_VALUE`='0', `DEPENDENT_FIELD_DEFINITION_ID`='gift.address.city', `DEPENDENT_SECONDARY_FIELD_DEFINITION_ID`='address.id', `FIELD_REQUIRED_ID`='10000012', `VALIDATION_ID`=NULL  WHERE (`CONDITION_ID` = 1000012) ;
UPDATE `FIELD_CONDITION` SET `CONDITION_ID`='10000027', `DEPENDENT_VALUE`='0', `DEPENDENT_FIELD_DEFINITION_ID`='paymentSource.phone.customFieldMap[phoneType]', `DEPENDENT_SECONDARY_FIELD_DEFINITION_ID`='phone.id', `FIELD_REQUIRED_ID`='10000027', `VALIDATION_ID`=NULL  WHERE (`CONDITION_ID` = 10000027) ;
UPDATE `FIELD_CONDITION` SET `CONDITION_ID`='1000014', `DEPENDENT_VALUE`='0', `DEPENDENT_FIELD_DEFINITION_ID`='gift.address.postalCode', `DEPENDENT_SECONDARY_FIELD_DEFINITION_ID`='address.id', `FIELD_REQUIRED_ID`='10000014', `VALIDATION_ID`=NULL  WHERE (`CONDITION_ID` = 1000014) ;
UPDATE `FIELD_CONDITION` SET `CONDITION_ID`='1000015', `DEPENDENT_VALUE`='0', `DEPENDENT_FIELD_DEFINITION_ID`='gift.address.country', `DEPENDENT_SECONDARY_FIELD_DEFINITION_ID`='address.id', `FIELD_REQUIRED_ID`='10000015', `VALIDATION_ID`=NULL  WHERE (`CONDITION_ID` = 1000015) ;
UPDATE `FIELD_CONDITION` SET `CONDITION_ID`='1000016', `DEPENDENT_VALUE`='0', `DEPENDENT_FIELD_DEFINITION_ID`='gift.phone.number', `DEPENDENT_SECONDARY_FIELD_DEFINITION_ID`='phone.id', `FIELD_REQUIRED_ID`='10000016', `VALIDATION_ID`=NULL  WHERE (`CONDITION_ID` = 1000016) ;
UPDATE `FIELD_CONDITION` SET `CONDITION_ID`='10000025', `DEPENDENT_VALUE`='0', `DEPENDENT_FIELD_DEFINITION_ID`='paymentSource.phone.customFieldMap[phoneType]', `DEPENDENT_SECONDARY_FIELD_DEFINITION_ID`='phone.id', `FIELD_REQUIRED_ID`='10000025', `VALIDATION_ID`=NULL  WHERE (`CONDITION_ID` = 10000025) ;
UPDATE `FIELD_CONDITION` SET `CONDITION_ID`='10000024', `DEPENDENT_VALUE`='0', `DEPENDENT_FIELD_DEFINITION_ID`='paymentSource.address.customFieldMap[addressType]', `DEPENDENT_SECONDARY_FIELD_DEFINITION_ID`='address.id', `FIELD_REQUIRED_ID`='10000024', `VALIDATION_ID`=NULL  WHERE (`CONDITION_ID` = 10000024) ;
UPDATE `FIELD_CONDITION` SET `CONDITION_ID`='10000021', `DEPENDENT_VALUE`='0', `DEPENDENT_FIELD_DEFINITION_ID`='gift.phone.customFieldMap[phoneType]', `DEPENDENT_SECONDARY_FIELD_DEFINITION_ID`='phone.id', `FIELD_REQUIRED_ID`='10000021', `VALIDATION_ID`=NULL  WHERE (`CONDITION_ID` = 10000021) ;
UPDATE `FIELD_CONDITION` SET `CONDITION_ID`='1000022', `DEPENDENT_VALUE`='0', `DEPENDENT_FIELD_DEFINITION_ID`='recurringGift.address.addressLine1', `DEPENDENT_SECONDARY_FIELD_DEFINITION_ID`='address.id', `FIELD_REQUIRED_ID`='1000022', `VALIDATION_ID`=NULL  WHERE (`CONDITION_ID` = 1000022) ;
UPDATE `FIELD_CONDITION` SET `CONDITION_ID`='1000023', `DEPENDENT_VALUE`='0', `DEPENDENT_FIELD_DEFINITION_ID`='recurringGift.phone.number', `DEPENDENT_SECONDARY_FIELD_DEFINITION_ID`='phone.id', `FIELD_REQUIRED_ID`='1000023', `VALIDATION_ID`=NULL  WHERE (`CONDITION_ID` = 1000023) ;
UPDATE `FIELD_CONDITION` SET `CONDITION_ID`='10000020', `DEPENDENT_VALUE`='0', `DEPENDENT_FIELD_DEFINITION_ID`='gift.address.customFieldMap[addressType]', `DEPENDENT_SECONDARY_FIELD_DEFINITION_ID`='address.id', `FIELD_REQUIRED_ID`='10000020', `VALIDATION_ID`=NULL  WHERE (`CONDITION_ID` = 10000020) ;
UPDATE `FIELD_CONDITION` SET `CONDITION_ID`='1000006', `DEPENDENT_VALUE`='0', `DEPENDENT_FIELD_DEFINITION_ID`='gift.paymentSource.achHolderName', `DEPENDENT_SECONDARY_FIELD_DEFINITION_ID`='paymentSource.id', `FIELD_REQUIRED_ID`='1000008', `VALIDATION_ID`=NULL  WHERE (`CONDITION_ID` = 1000006) ;
UPDATE `FIELD_CONDITION` SET `CONDITION_ID`='10000204', `DEPENDENT_VALUE`='0', `DEPENDENT_FIELD_DEFINITION_ID`='gift.paymentSource.creditCardNumber', `DEPENDENT_SECONDARY_FIELD_DEFINITION_ID`='paymentSource.id', `FIELD_REQUIRED_ID`=NULL, `VALIDATION_ID`='1002000'  WHERE (`CONDITION_ID` = 10000204) ;
UPDATE `FIELD_CONDITION` SET `CONDITION_ID`='1000220', `DEPENDENT_VALUE`='0', `DEPENDENT_FIELD_DEFINITION_ID`='recurringGift.paymentSource.creditCardNumber', `DEPENDENT_SECONDARY_FIELD_DEFINITION_ID`='paymentSource.id', `FIELD_REQUIRED_ID`=NULL, `VALIDATION_ID`='1003000'  WHERE (`CONDITION_ID` = 1000220) ;
UPDATE `FIELD_CONDITION` SET `CONDITION_ID`='10000215', `DEPENDENT_VALUE`='0', `DEPENDENT_FIELD_DEFINITION_ID`='gift.paymentSource.creditCardNumber', `DEPENDENT_SECONDARY_FIELD_DEFINITION_ID`='paymentSource.id', `FIELD_REQUIRED_ID`='1000006', `VALIDATION_ID`=NULL  WHERE (`CONDITION_ID` = 10000215) ;
UPDATE `FIELD_CONDITION` SET `CONDITION_ID`='1000075', `DEPENDENT_VALUE`='0', `DEPENDENT_FIELD_DEFINITION_ID`='recurringGift.phone.number', `DEPENDENT_SECONDARY_FIELD_DEFINITION_ID`='phone.id', `FIELD_REQUIRED_ID`='1000139', `VALIDATION_ID`=NULL  WHERE (`CONDITION_ID` = 1000075) ;
UPDATE `FIELD_CONDITION` SET `CONDITION_ID`='1000074', `DEPENDENT_VALUE`='0', `DEPENDENT_FIELD_DEFINITION_ID`='recurringGift.address.country', `DEPENDENT_SECONDARY_FIELD_DEFINITION_ID`='address.id', `FIELD_REQUIRED_ID`='1000138', `VALIDATION_ID`=NULL  WHERE (`CONDITION_ID` = 1000074) ;
UPDATE `FIELD_CONDITION` SET `CONDITION_ID`='1000037', `DEPENDENT_VALUE`='0', `DEPENDENT_FIELD_DEFINITION_ID`='paymentSource.address.addressLine1', `DEPENDENT_SECONDARY_FIELD_DEFINITION_ID`='address.id', `FIELD_REQUIRED_ID`='1000078', `VALIDATION_ID`=NULL  WHERE (`CONDITION_ID` = 1000037) ;
UPDATE `FIELD_CONDITION` SET `CONDITION_ID`='1000038', `DEPENDENT_VALUE`='0', `DEPENDENT_FIELD_DEFINITION_ID`='paymentSource.address.city', `DEPENDENT_SECONDARY_FIELD_DEFINITION_ID`='address.id', `FIELD_REQUIRED_ID`='1000079', `VALIDATION_ID`=NULL  WHERE (`CONDITION_ID` = 1000038) ;
UPDATE `FIELD_CONDITION` SET `CONDITION_ID`='10000211', `DEPENDENT_VALUE`='0', `DEPENDENT_FIELD_DEFINITION_ID`='gift.paymentSource.creditCardHolderName', `DEPENDENT_SECONDARY_FIELD_DEFINITION_ID`='paymentSource.id', `FIELD_REQUIRED_ID`='1000004', `VALIDATION_ID`=NULL  WHERE (`CONDITION_ID` = 10000211) ;
UPDATE `FIELD_CONDITION` SET `CONDITION_ID`='1000040', `DEPENDENT_VALUE`='0', `DEPENDENT_FIELD_DEFINITION_ID`='paymentSource.address.postalCode', `DEPENDENT_SECONDARY_FIELD_DEFINITION_ID`='address.id', `FIELD_REQUIRED_ID`='1000081', `VALIDATION_ID`=NULL  WHERE (`CONDITION_ID` = 1000040) ;
UPDATE `FIELD_CONDITION` SET `CONDITION_ID`='1000041', `DEPENDENT_VALUE`='0', `DEPENDENT_FIELD_DEFINITION_ID`='paymentSource.address.country', `DEPENDENT_SECONDARY_FIELD_DEFINITION_ID`='address.id', `FIELD_REQUIRED_ID`='1000082', `VALIDATION_ID`=NULL  WHERE (`CONDITION_ID` = 1000041) ;
UPDATE `FIELD_CONDITION` SET `CONDITION_ID`='1000042', `DEPENDENT_VALUE`='0', `DEPENDENT_FIELD_DEFINITION_ID`='paymentSource.phone.number', `DEPENDENT_SECONDARY_FIELD_DEFINITION_ID`='phone.id', `FIELD_REQUIRED_ID`='1000083', `VALIDATION_ID`=NULL  WHERE (`CONDITION_ID` = 1000042) ;
UPDATE `FIELD_CONDITION` SET `CONDITION_ID`='1000050', `DEPENDENT_VALUE`='0', `DEPENDENT_FIELD_DEFINITION_ID`='paymentSource.address.addressLine1', `DEPENDENT_SECONDARY_FIELD_DEFINITION_ID`='address.id', `FIELD_REQUIRED_ID`='1000084', `VALIDATION_ID`=NULL  WHERE (`CONDITION_ID` = 1000050) ;
UPDATE `FIELD_CONDITION` SET `CONDITION_ID`='1000051', `DEPENDENT_VALUE`='0', `DEPENDENT_FIELD_DEFINITION_ID`='paymentSource.address.city', `DEPENDENT_SECONDARY_FIELD_DEFINITION_ID`='address.id', `FIELD_REQUIRED_ID`='1000085', `VALIDATION_ID`=NULL  WHERE (`CONDITION_ID` = 1000051) ;
UPDATE `FIELD_CONDITION` SET `CONDITION_ID`='10000213', `DEPENDENT_VALUE`='0', `DEPENDENT_FIELD_DEFINITION_ID`='gift.paymentSource.creditCardType', `DEPENDENT_SECONDARY_FIELD_DEFINITION_ID`='paymentSource.id', `FIELD_REQUIRED_ID`='1000005', `VALIDATION_ID`=NULL  WHERE (`CONDITION_ID` = 10000213) ;
UPDATE `FIELD_CONDITION` SET `CONDITION_ID`='1000053', `DEPENDENT_VALUE`='0', `DEPENDENT_FIELD_DEFINITION_ID`='paymentSource.address.postalCode', `DEPENDENT_SECONDARY_FIELD_DEFINITION_ID`='address.id', `FIELD_REQUIRED_ID`='1000087', `VALIDATION_ID`=NULL  WHERE (`CONDITION_ID` = 1000053) ;
UPDATE `FIELD_CONDITION` SET `CONDITION_ID`='1000054', `DEPENDENT_VALUE`='0', `DEPENDENT_FIELD_DEFINITION_ID`='paymentSource.address.country', `DEPENDENT_SECONDARY_FIELD_DEFINITION_ID`='address.id', `FIELD_REQUIRED_ID`='1000088', `VALIDATION_ID`=NULL  WHERE (`CONDITION_ID` = 1000054) ;
UPDATE `FIELD_CONDITION` SET `CONDITION_ID`='1000055', `DEPENDENT_VALUE`='0', `DEPENDENT_FIELD_DEFINITION_ID`='paymentSource.phone.number', `DEPENDENT_SECONDARY_FIELD_DEFINITION_ID`='phone.id', `FIELD_REQUIRED_ID`='1000089', `VALIDATION_ID`=NULL  WHERE (`CONDITION_ID` = 1000055) ;
UPDATE `FIELD_CONDITION` SET `CONDITION_ID`='1000073', `DEPENDENT_VALUE`='0', `DEPENDENT_FIELD_DEFINITION_ID`='recurringGift.address.postalCode', `DEPENDENT_SECONDARY_FIELD_DEFINITION_ID`='address.id', `FIELD_REQUIRED_ID`='1000137', `VALIDATION_ID`=NULL  WHERE (`CONDITION_ID` = 1000073) ;
UPDATE `FIELD_CONDITION` SET `CONDITION_ID`='1000057', `DEPENDENT_VALUE`='0', `DEPENDENT_FIELD_DEFINITION_ID`='recurringGift.paymentSource.creditCardHolderName', `DEPENDENT_SECONDARY_FIELD_DEFINITION_ID`='paymentSource.id', `FIELD_REQUIRED_ID`='1000127', `VALIDATION_ID`=NULL  WHERE (`CONDITION_ID` = 1000057) ;
UPDATE `FIELD_CONDITION` SET `CONDITION_ID`='10000217', `DEPENDENT_VALUE`='0', `DEPENDENT_FIELD_DEFINITION_ID`='gift.paymentSource.creditCardExpiration', `DEPENDENT_SECONDARY_FIELD_DEFINITION_ID`='paymentSource.id', `FIELD_REQUIRED_ID`='1000007', `VALIDATION_ID`=NULL  WHERE (`CONDITION_ID` = 10000217) ;
UPDATE `FIELD_CONDITION` SET `CONDITION_ID`='1000059', `DEPENDENT_VALUE`='0', `DEPENDENT_FIELD_DEFINITION_ID`='recurringGift.paymentSource.creditCardType', `DEPENDENT_SECONDARY_FIELD_DEFINITION_ID`='paymentSource.id', `FIELD_REQUIRED_ID`='1000128', `VALIDATION_ID`=NULL  WHERE (`CONDITION_ID` = 1000059) ;
UPDATE `FIELD_CONDITION` SET `CONDITION_ID`='1000071', `DEPENDENT_VALUE`='0', `DEPENDENT_FIELD_DEFINITION_ID`='recurringGift.address.city', `DEPENDENT_SECONDARY_FIELD_DEFINITION_ID`='address.id', `FIELD_REQUIRED_ID`='1000135', `VALIDATION_ID`=NULL  WHERE (`CONDITION_ID` = 1000071) ;
UPDATE `FIELD_CONDITION` SET `CONDITION_ID`='1000061', `DEPENDENT_VALUE`='0', `DEPENDENT_FIELD_DEFINITION_ID`='recurringGift.paymentSource.creditCardNumber', `DEPENDENT_SECONDARY_FIELD_DEFINITION_ID`='paymentSource.id', `FIELD_REQUIRED_ID`='1000129', `VALIDATION_ID`=NULL  WHERE (`CONDITION_ID` = 1000061) ;
UPDATE `FIELD_CONDITION` SET `CONDITION_ID`='1000070', `DEPENDENT_VALUE`='0', `DEPENDENT_FIELD_DEFINITION_ID`='recurringGift.address.addressLine1', `DEPENDENT_SECONDARY_FIELD_DEFINITION_ID`='address.id', `FIELD_REQUIRED_ID`='1000134', `VALIDATION_ID`=NULL  WHERE (`CONDITION_ID` = 1000070) ;
UPDATE `FIELD_CONDITION` SET `CONDITION_ID`='1000063', `DEPENDENT_VALUE`='0', `DEPENDENT_FIELD_DEFINITION_ID`='recurringGift.paymentSource.creditCardExpiration', `DEPENDENT_SECONDARY_FIELD_DEFINITION_ID`='paymentSource.id', `FIELD_REQUIRED_ID`='1000130', `VALIDATION_ID`=NULL  WHERE (`CONDITION_ID` = 1000063) ;
UPDATE `FIELD_CONDITION` SET `CONDITION_ID`='1000069', `DEPENDENT_VALUE`='0', `DEPENDENT_FIELD_DEFINITION_ID`='recurringGift.paymentSource.achAccountNumber', `DEPENDENT_SECONDARY_FIELD_DEFINITION_ID`='paymentSource.id', `FIELD_REQUIRED_ID`='1000133', `VALIDATION_ID`=NULL  WHERE (`CONDITION_ID` = 1000069) ;
UPDATE `FIELD_CONDITION` SET `CONDITION_ID`='1000065', `DEPENDENT_VALUE`='0', `DEPENDENT_FIELD_DEFINITION_ID`='recurringGift.paymentSource.achHolderName', `DEPENDENT_SECONDARY_FIELD_DEFINITION_ID`='paymentSource.id', `FIELD_REQUIRED_ID`='1000131', `VALIDATION_ID`=NULL  WHERE (`CONDITION_ID` = 1000065) ;
UPDATE `FIELD_CONDITION` SET `CONDITION_ID`='1000067', `DEPENDENT_VALUE`='0', `DEPENDENT_FIELD_DEFINITION_ID`='recurringGift.paymentSource.achRoutingNumber', `DEPENDENT_SECONDARY_FIELD_DEFINITION_ID`='paymentSource.id', `FIELD_REQUIRED_ID`='1000132', `VALIDATION_ID`=NULL  WHERE (`CONDITION_ID` = 1000067) ;
	/*End   of batch : 1 */
/* SYNC TABLE : `MESSAGE_RESOURCE` */

	/*Start of batch : 1 */
INSERT INTO `MESSAGE_RESOURCE` VALUES ('1000025', 'en_US', 'distributionLineDesignationCode', 'FIELD_VALIDATION', 'A designation code must be entered for each individual distribution line', NULL);
	/*End   of batch : 1 */
/* SYNC TABLE : `PICKLIST` */

	/*Start of batch : 1 */
UPDATE `PICKLIST` SET `PICKLIST_NAME_ID`='customFieldMap[organization.eligibleFunds]', `PICKLIST_NAME`='customFieldMap[organization.eligibleFunds]' WHERE (`PICKLIST_NAME_ID`='customFieldMap[organization.eligibleFunds].value') ;
	/*End   of batch : 1 */
/* SYNC TABLE : `PICKLIST_ITEM` */

	/*Start of batch : 1 */
INSERT INTO `PICKLIST_ITEM`
(`ITEM_NAME`,  `DEFAULT_DISPLAY_VALUE`, `LONG_DESCRIPTION`, `INACTIVE`, `READ_ONLY`, `ITEM_ORDER`, `REFERENCE_VALUE`, `SUPPRESS_REFERENCE_VALUE`, `PICKLIST_ID`)
SELECT 'Other', 'Other', NULL, '0', '0', '5', '.gift_other', NULL, PICKLIST_ID from PICKLIST where PICKLIST_NAME_ID = 'gift.paymentType';
INSERT INTO `PICKLIST_ITEM` 
(`ITEM_NAME`,  `DEFAULT_DISPLAY_VALUE`, `LONG_DESCRIPTION`, `INACTIVE`, `READ_ONLY`, `ITEM_ORDER`, `REFERENCE_VALUE`, `SUPPRESS_REFERENCE_VALUE`, `PICKLIST_ID`)
SELECT 'Other', 'Other', NULL, '0', '0', '5', '.adjustedGift_other', NULL, PICKLIST_ID from PICKLIST where PICKLIST_NAME_ID = 'adjustedGift.paymentType';
UPDATE PICKLIST_ITEM I, PICKLIST L
    SET `REFERENCE_VALUE`='li:has(#paymentSource-td-id),.adjustedGift_editAch'
    WHERE I.PICKLIST_ID=L.PICKLIST_ID
    AND PICKLIST_NAME_ID='adjustedGift.paymentType'
    AND `ITEM_NAME` = 'ACH';

UPDATE PICKLIST_ITEM I, PICKLIST L
    SET `REFERENCE_VALUE`='li:has(#paymentSource-td-id),.adjustedGift_editCreditCard'
    WHERE I.PICKLIST_ID=L.PICKLIST_ID
    AND PICKLIST_NAME_ID='adjustedGift.paymentType'
    AND `ITEM_NAME` = 'Credit Card';

UPDATE PICKLIST_ITEM I, PICKLIST L
    SET `REFERENCE_VALUE`='li:has(#paymentSource-td-id)'
    WHERE I.PICKLIST_ID=L.PICKLIST_ID
    AND PICKLIST_NAME_ID='gift.paymentType'
    AND `ITEM_NAME` = 'Credit Card';

UPDATE PICKLIST_ITEM I, PICKLIST L
    SET `REFERENCE_VALUE`='li:has(#paymentSource-td-id)'
    WHERE I.PICKLIST_ID=L.PICKLIST_ID
    AND PICKLIST_NAME_ID='gift.paymentType'
    AND `ITEM_NAME` = 'ACH';

UPDATE PICKLIST_ITEM I, PICKLIST L
    SET `REFERENCE_VALUE`='li:has(.ea-spouse)'
    WHERE I.PICKLIST_ID=L.PICKLIST_ID
    AND PICKLIST_NAME_ID='maritalStatus'
    AND `ITEM_NAME` = 'Unknown';

UPDATE PICKLIST_ITEM I, PICKLIST L
    SET `REFERENCE_VALUE`='li:has(.ea-spouse)'
    WHERE I.PICKLIST_ID=L.PICKLIST_ID
    AND PICKLIST_NAME_ID='maritalStatus'
    AND `ITEM_NAME` = 'Married';

UPDATE PICKLIST_ITEM I, PICKLIST L
    SET `REFERENCE_VALUE`='li:has(.ea-spouse)'
    WHERE I.PICKLIST_ID=L.PICKLIST_ID
    AND PICKLIST_NAME_ID='maritalStatus'
    AND `ITEM_NAME` = 'Engaged';

UPDATE PICKLIST_ITEM I, PICKLIST L
    SET `REFERENCE_VALUE`='li:has(.ea-spouse)'
    WHERE I.PICKLIST_ID=L.PICKLIST_ID
    AND PICKLIST_NAME_ID='maritalStatus'
    AND `ITEM_NAME` = 'Divorced';

UPDATE PICKLIST_ITEM I, PICKLIST L
    SET `REFERENCE_VALUE`='li:has(.ea-spouse)'
    WHERE I.PICKLIST_ID=L.PICKLIST_ID
    AND PICKLIST_NAME_ID='maritalStatus'
    AND `ITEM_NAME` = 'Widowed';
/*End   of batch : 1 */
/* SYNC TABLE : `QUERY_LOOKUP` */

	/*Start of batch : 1 */
UPDATE `QUERY_LOOKUP` SET `QUERY_LOOKUP_ID`='1000039', `ENTITY_TYPE`='constituent', `SQL_WHERE`='constituent_type = \'individual\' ', `SECTION_NAME`='recurringGift.notified', `FIELD_DEFINITION_ID`='recurringGift.distributionLines.customFieldMap[notified]', `SITE_NAME`=NULL  WHERE (`QUERY_LOOKUP_ID` = 1000039) ;
UPDATE `QUERY_LOOKUP` SET `QUERY_LOOKUP_ID`='1000037', `ENTITY_TYPE`='constituent', `SQL_WHERE`='constituent_type = \'individual\' ', `SECTION_NAME`='recurringGift.tributeReference', `FIELD_DEFINITION_ID`='recurringGift.distributionLines.customFieldMap[tributeReference]', `SITE_NAME`=NULL  WHERE (`QUERY_LOOKUP_ID` = 1000037) ;
UPDATE `QUERY_LOOKUP` SET `QUERY_LOOKUP_ID`='1000036', `ENTITY_TYPE`='constituent', `SQL_WHERE`='constituent_type = \'individual\' ', `SECTION_NAME`='pledge.info', `FIELD_DEFINITION_ID`='recurringGift.distributionLines.customFieldMap[reference]', `SITE_NAME`=NULL  WHERE (`QUERY_LOOKUP_ID` = 1000036) ;
UPDATE `QUERY_LOOKUP` SET `QUERY_LOOKUP_ID`='1000035', `ENTITY_TYPE`='constituent', `SQL_WHERE`='constituent_type = \'individual\' ', `SECTION_NAME`='pledge.notified', `FIELD_DEFINITION_ID`='pledge.distributionLines.customFieldMap[notified]', `SITE_NAME`=NULL  WHERE (`QUERY_LOOKUP_ID` = 1000035) ;
UPDATE `QUERY_LOOKUP` SET `QUERY_LOOKUP_ID`='1000034', `ENTITY_TYPE`='constituent', `SQL_WHERE`='constituent_type = \'individual\' ', `SECTION_NAME`='pledge.onBehalfOf', `FIELD_DEFINITION_ID`='pledge.distributionLines.customFieldMap[onBehalfOf]', `SITE_NAME`=NULL  WHERE (`QUERY_LOOKUP_ID` = 1000034) ;
UPDATE `QUERY_LOOKUP` SET `QUERY_LOOKUP_ID`='1000033', `ENTITY_TYPE`='constituent', `SQL_WHERE`='constituent_type = \'individual\' ', `SECTION_NAME`='pledge.tributeReference', `FIELD_DEFINITION_ID`='pledge.distributionLines.customFieldMap[tributeReference]', `SITE_NAME`=NULL  WHERE (`QUERY_LOOKUP_ID` = 1000033) ;
UPDATE `QUERY_LOOKUP` SET `QUERY_LOOKUP_ID`='1000032', `ENTITY_TYPE`='constituent', `SQL_WHERE`='constituent_type = \'individual\' ', `SECTION_NAME`='gift.notified', `FIELD_DEFINITION_ID`='gift.distributionLines.customFieldMap[notified]', `SITE_NAME`=NULL  WHERE (`QUERY_LOOKUP_ID` = 1000032) ;
UPDATE `QUERY_LOOKUP` SET `QUERY_LOOKUP_ID`='1000031', `ENTITY_TYPE`='constituent', `SQL_WHERE`='constituent_type = \'individual\' ', `SECTION_NAME`='gift.onBehalfOf', `FIELD_DEFINITION_ID`='gift.distributionLines.customFieldMap[onBehalfOf]', `SITE_NAME`=NULL  WHERE (`QUERY_LOOKUP_ID` = 1000031) ;
UPDATE `QUERY_LOOKUP` SET `QUERY_LOOKUP_ID`='1000030', `ENTITY_TYPE`='constituent', `SQL_WHERE`='constituent_type = \'individual\' ', `SECTION_NAME`='gift.tributeReference', `FIELD_DEFINITION_ID`='gift.distributionLines.customFieldMap[tributeReference]', `SITE_NAME`=NULL  WHERE (`QUERY_LOOKUP_ID` = 1000030) ;
UPDATE `QUERY_LOOKUP` SET `QUERY_LOOKUP_ID`='1000026', `ENTITY_TYPE`='constituent', `SQL_WHERE`='constituent_type = \'individual\' ', `SECTION_NAME`='pledge.info', `FIELD_DEFINITION_ID`='pledge.distributionLines.customFieldMap[reference]', `SITE_NAME`=NULL  WHERE (`QUERY_LOOKUP_ID` = 1000026) ;
UPDATE `QUERY_LOOKUP` SET `QUERY_LOOKUP_ID`='1000025', `ENTITY_TYPE`='constituent', `SQL_WHERE`='constituent_type = \'individual\' ', `SECTION_NAME`='gift.donation', `FIELD_DEFINITION_ID`='gift.distributionLines.customFieldMap[reference]', `SITE_NAME`=NULL  WHERE (`QUERY_LOOKUP_ID` = 1000025) ;
UPDATE `QUERY_LOOKUP` SET `QUERY_LOOKUP_ID`='1000038', `ENTITY_TYPE`='constituent', `SQL_WHERE`='constituent_type = \'individual\' ', `SECTION_NAME`='recurringGift.onBehalfOf', `FIELD_DEFINITION_ID`='recurringGift.distributionLines.customFieldMap[onBehalfOf]', `SITE_NAME`=NULL  WHERE (`QUERY_LOOKUP_ID` = 1000038) ;
	/*End   of batch : 1 */
/* SYNC TABLE : `QUERY_LOOKUP_PARAM` */


/* SYNC TABLE : `ENTITY_DEFAULT ` */

	/*Start of batch : 1 */
insert into ENTITY_DEFAULT (DEFAULT_VALUE, ENTITY_FIELD_NAME, ENTITY_TYPE)
values ('permanent', 'activationStatus', 'address');

insert into ENTITY_DEFAULT (DEFAULT_VALUE, ENTITY_FIELD_NAME, ENTITY_TYPE)
values ('permanent', 'activationStatus', 'email');

insert into ENTITY_DEFAULT (DEFAULT_VALUE, ENTITY_FIELD_NAME, ENTITY_TYPE)
values ('permanent', 'activationStatus', 'phone');

insert into ENTITY_DEFAULT (DEFAULT_VALUE, CONDITION_EXP, ENTITY_FIELD_NAME, ENTITY_TYPE)
values ('now:', 'activationStatus == ''permanent''', 'effectiveDate', 'address');

insert into ENTITY_DEFAULT (DEFAULT_VALUE, CONDITION_EXP, ENTITY_FIELD_NAME, ENTITY_TYPE)
values ('now:', 'activationStatus == ''permanent''', 'effectiveDate', 'phone');

insert into ENTITY_DEFAULT (DEFAULT_VALUE, CONDITION_EXP, ENTITY_FIELD_NAME, ENTITY_TYPE)
values ('now:', 'activationStatus == ''permanent''', 'effectiveDate', 'email');

insert into ENTITY_DEFAULT (DEFAULT_VALUE, ENTITY_FIELD_NAME, ENTITY_TYPE)
values ('bean:constituent.firstLast', 'adjustedPaymentTo', 'adjustedGift');

insert into ENTITY_DEFAULT (DEFAULT_VALUE, ENTITY_FIELD_NAME, ENTITY_TYPE)
values ('0', 'customFieldMap[initialReminder]', 'recurringGift');

insert into ENTITY_DEFAULT (DEFAULT_VALUE, ENTITY_FIELD_NAME, ENTITY_TYPE)
values ('1', 'customFieldMap[maximumReminders]', 'recurringGift');

insert into ENTITY_DEFAULT (DEFAULT_VALUE, ENTITY_FIELD_NAME, ENTITY_TYPE)
values ('0', 'customFieldMap[reminderInterval]', 'recurringGift');

insert into ENTITY_DEFAULT (DEFAULT_VALUE, ENTITY_FIELD_NAME, ENTITY_TYPE)
values ('0', 'customFieldMap[initialReminder]', 'pledge');

insert into ENTITY_DEFAULT (DEFAULT_VALUE, ENTITY_FIELD_NAME, ENTITY_TYPE)
values ('1', 'customFieldMap[maximumReminders]', 'pledge');

insert into ENTITY_DEFAULT (DEFAULT_VALUE, ENTITY_FIELD_NAME, ENTITY_TYPE)
values ('0', 'customFieldMap[reminderInterval]', 'pledge');

insert into ENTITY_DEFAULT (DEFAULT_VALUE, CONDITION_EXP, ENTITY_FIELD_NAME, ENTITY_TYPE)
values ('unknown', 'constituentType == ''organization''', 'customFieldMap[organization.eligibility]', 'constituent');

insert into ENTITY_DEFAULT (DEFAULT_VALUE, ENTITY_FIELD_NAME, ENTITY_TYPE)
values ('true', 'customFieldMap[taxDeductible]', 'distributionLine');

insert into ENTITY_DEFAULT (DEFAULT_VALUE, ENTITY_FIELD_NAME, ENTITY_TYPE)
values ('bean:constituent.recognitionName', 'customFieldMap[recognitionName]', 'distributionLine');

insert into ENTITY_DEFAULT (DEFAULT_VALUE, ENTITY_FIELD_NAME, ENTITY_TYPE)
values ('bean:constituent.recognitionName', 'recognitionName', 'giftInKind');

insert into ENTITY_DEFAULT (DEFAULT_VALUE, ENTITY_FIELD_NAME, ENTITY_TYPE)
values ('bean:constituent.firstLast', 'creditCardHolderName', 'paymentSource');

insert into ENTITY_DEFAULT (DEFAULT_VALUE, ENTITY_FIELD_NAME, ENTITY_TYPE)
values ('bean:constituent.firstLast', 'achHolderName', 'paymentSource');

insert into ENTITY_DEFAULT (DEFAULT_VALUE, ENTITY_FIELD_NAME, ENTITY_TYPE)
values ('picklist:currencyCode', 'currencyCode', 'gift');

insert into ENTITY_DEFAULT (DEFAULT_VALUE, ENTITY_FIELD_NAME, ENTITY_TYPE)
values ('picklist:currencyCode', 'currencyCode', 'recurringGift');

insert into ENTITY_DEFAULT (DEFAULT_VALUE, ENTITY_FIELD_NAME, ENTITY_TYPE)
values ('picklist:currencyCode', 'currencyCode', 'pledge');

insert into ENTITY_DEFAULT (DEFAULT_VALUE, ENTITY_FIELD_NAME, ENTITY_TYPE)
values ('picklist:currencyCode', 'currencyCode', 'giftInKind');

insert into ENTITY_DEFAULT (DEFAULT_VALUE, ENTITY_FIELD_NAME, ENTITY_TYPE)
values ('picklist:projectCode', 'projectCode', 'distributionLine');

insert into ENTITY_DEFAULT (DEFAULT_VALUE, ENTITY_FIELD_NAME, ENTITY_TYPE)
values ('unknown', 'customFieldMap[addressType]', 'address');

insert into ENTITY_DEFAULT (DEFAULT_VALUE, ENTITY_FIELD_NAME, ENTITY_TYPE)
values ('unknown', 'customFieldMap[phoneType]', 'phone');

insert into ENTITY_DEFAULT (DEFAULT_VALUE, ENTITY_FIELD_NAME, ENTITY_TYPE)
values ('unknown', 'customFieldMap[emailType]', 'email');
	/*End   of batch : 1 */



INSERT INTO `PAGE_ACCESS` (`PAGE_ACCESS_ID`, `ACCESS_TYPE`, `PAGE_TYPE`, `ROLE`, `SITE_NAME`) VALUES ('1000009', 'ALLOWED', 'screenDefinition', 'ROLE_SUPER_ADMIN', NULL);
UPDATE `PAGE_ACCESS` SET `ROLE`='ROLE_SUPER_ADMIN,ROLE_ADMIN,ROLE_SUPER_USER'  WHERE (`PAGE_ACCESS_ID` = 1000001) ;
UPDATE `PAGE_ACCESS` SET `ROLE`='ROLE_SUPER_ADMIN,ROLE_ADMIN'  WHERE (`PAGE_ACCESS_ID` = 1000008) ;
UPDATE `PAGE_ACCESS` SET `ROLE`='ROLE_SUPER_ADMIN,ROLE_ADMIN'  WHERE (`PAGE_ACCESS_ID` = 1000003) ;
UPDATE `PAGE_ACCESS` SET `ROLE`='ROLE_SUPER_ADMIN,ROLE_ADMIN'  WHERE (`PAGE_ACCESS_ID` = 1000004) ;
UPDATE `PAGE_ACCESS` SET `ROLE`='ROLE_SUPER_ADMIN,ROLE_ADMIN'  WHERE (`PAGE_ACCESS_ID` = 1000000) ;


UPDATE VERSION SET SCHEMA_MAJOR_VERSION = 2 WHERE COMPONENT_ID = 'ORANGE' AND COMPONENT_DESC = 'Orange Leap';

COMMIT;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

