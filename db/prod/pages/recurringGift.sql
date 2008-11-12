INSERT INTO SECTION_DEFINITION (SECTION_DEFINITION_ID, PAGE_TYPE, SECTION_NAME, DEFAULT_LABEL, SECTION_ORDER, LAYOUT_TYPE, ROLE) VALUES (86, 'recurringGift', 'recurringGift.info', 'Recurring Gift Information', 1, 'ONE_COLUMN', 'ROLE_USER');
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (86, 'commitment.commitmentCode', 1000);
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (86, 'commitment.projectCode', 3000);
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (86, 'commitment.motivationCode', 4000);
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (86, 'commitment.amountPerGift', 5000);
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (86, 'commitment.frequency', 8000);
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (86, 'commitment.startDate', 9000);
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (86, 'commitment.endDate', 10000);
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (86, 'commitment.notes', 11000);
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (86, 'commitment.selectedPaymentSource', 14000);
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (86, 'commitment.paymentType', 15000);

INSERT INTO SECTION_DEFINITION (SECTION_DEFINITION_ID, PAGE_TYPE, SECTION_NAME, DEFAULT_LABEL, SECTION_ORDER, LAYOUT_TYPE, ROLE) VALUES (87, 'recurringGift', 'commitment.creditCard', 'New Credit Card', 2, 'ONE_COLUMN_HIDDEN', 'ROLE_USER');
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (87, 'commitment.paymentSource.creditCardType', 'paymentSource.creditCardType', 1000);
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (87, 'commitment.paymentSource.creditCardNumber', 'paymentSource.creditCardNumber', 2000);
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (87, 'commitment.paymentSource.creditCardExpiration', 'paymentSource.creditCardExpiration', 3000);

INSERT INTO SECTION_DEFINITION (SECTION_DEFINITION_ID, PAGE_TYPE, SECTION_NAME, DEFAULT_LABEL, SECTION_ORDER, LAYOUT_TYPE, ROLE) VALUES (88, 'recurringGift', 'commitment.ach', 'New ACH', 3, 'ONE_COLUMN_HIDDEN', 'ROLE_USER');
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (88, 'commitment.paymentSource.achRoutingNumber', 'paymentSource.achRoutingNumber', 2000);
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, SECONDARY_FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (88, 'commitment.paymentSource.achAccountNumber', 'paymentSource.achAccountNumber', 3000);

INSERT INTO SECTION_DEFINITION (SECTION_DEFINITION_ID, PAGE_TYPE, SECTION_NAME, DEFAULT_LABEL, SECTION_ORDER, LAYOUT_TYPE, ROLE) VALUES (89, 'recurringGift', 'commitment.check', 'New Check', 4, 'ONE_COLUMN_HIDDEN', 'ROLE_USER');
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (89, 'commitment.checkNumber', 1000);
