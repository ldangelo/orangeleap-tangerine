INSERT INTO PICKLIST (PICKLIST_ID, PICKLIST_NAME_ID, PICKLIST_NAME, PICKLIST_DESC) VALUES (1000047, 'adjustedPaymentRequired', 'adjustedPaymentRequired', 'Adjustment Payment Required');
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, REFERENCE_VALUE, ITEM_ORDER) VALUES (1000047, 'false', 'No', '', 1);
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, REFERENCE_VALUE, ITEM_ORDER) VALUES (1000047, 'true', 'Yes', 'li:has(.ea-giftPayment),li:has(.ea-address),li:has(.ea-phone)', 2);