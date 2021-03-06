INSERT INTO SECTION_DEFINITION (SECTION_DEFINITION_ID, PAGE_TYPE, SECTION_NAME, DEFAULT_LABEL, SECTION_ORDER, LAYOUT_TYPE, ROLE) VALUES (1000098, 'paymentHistoryList', 'paymentHistoryList.resultsInfo', 'All Payment History', 1, 'GRID', 'ROLE_USER');
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (1000098, 'paymentHistory.id', 1000);
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (1000098, 'paymentHistory.transactionDate', 2000);
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (1000098, 'paymentHistory.paymentType', 3000);
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (1000098, 'paymentHistory.paymentStatus', 3500);
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (1000098, 'paymentHistory.amount', 4000);
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (1000098, 'paymentHistory.currencyCode', 5000);
INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER) VALUES (1000098, 'paymentHistory.description', 6000);
