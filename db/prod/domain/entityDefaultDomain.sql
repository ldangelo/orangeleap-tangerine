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
values ('unknown', 'customFieldMap[addressType]', 'address');

insert into ENTITY_DEFAULT (DEFAULT_VALUE, ENTITY_FIELD_NAME, ENTITY_TYPE)
values ('unknown', 'customFieldMap[phoneType]', 'phone');

insert into ENTITY_DEFAULT (DEFAULT_VALUE, ENTITY_FIELD_NAME, ENTITY_TYPE)
values ('unknown', 'customFieldMap[emailType]', 'email');

insert into ENTITY_DEFAULT (DEFAULT_VALUE, ENTITY_FIELD_NAME, ENTITY_TYPE)
values ('', 'customFieldMap[recurringGiftName]', 'recurringGift');

insert into ENTITY_DEFAULT (DEFAULT_VALUE, ENTITY_FIELD_NAME, ENTITY_TYPE)
values ('', 'customFieldMap[pledgeName]', 'pledge');

insert into ENTITY_DEFAULT (DEFAULT_VALUE, ENTITY_FIELD_NAME, ENTITY_TYPE)
values ('bean:donationDate', 'checkDate', 'gift');

INSERT INTO `ENTITY_DEFAULT` (`DEFAULT_VALUE`,`ENTITY_FIELD_NAME`,`ENTITY_TYPE`,`CONDITION_EXP`,`SITE_NAME`)
VALUES ('bean:constituent.firstLast', 'checkHolderName', 'paymentSource', NULL, NULL);

insert into ENTITY_DEFAULT (DEFAULT_VALUE, ENTITY_FIELD_NAME, ENTITY_TYPE)
values ('bean:donationDate', 'checkDate', 'gift');

INSERT INTO `ENTITY_DEFAULT` (`DEFAULT_VALUE`,`ENTITY_FIELD_NAME`,`ENTITY_TYPE`)
VALUES ('now:', 'customFieldMap[eventDate]', 'communicationHistory');

INSERT INTO `ENTITY_DEFAULT` (`DEFAULT_VALUE`,`ENTITY_FIELD_NAME`,`ENTITY_TYPE`)
VALUES ('now:', 'cancelDate', 'recurringGift');

INSERT INTO `ENTITY_DEFAULT` (`DEFAULT_VALUE`,`ENTITY_FIELD_NAME`,`ENTITY_TYPE`)
VALUES ('now:', 'cancelDate', 'pledge');
