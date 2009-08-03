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
values ('5', 'customFieldMap[initialReminder]', 'recurringGift');

insert into ENTITY_DEFAULT (DEFAULT_VALUE, ENTITY_FIELD_NAME, ENTITY_TYPE)
values ('1', 'customFieldMap[maximumReminders]', 'recurringGift');

insert into ENTITY_DEFAULT (DEFAULT_VALUE, ENTITY_FIELD_NAME, ENTITY_TYPE)
values ('0', 'customFieldMap[reminderInterval]', 'recurringGift');

insert into ENTITY_DEFAULT (DEFAULT_VALUE, ENTITY_FIELD_NAME, ENTITY_TYPE)
values ('5', 'customFieldMap[initialReminder]', 'pledge');

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
