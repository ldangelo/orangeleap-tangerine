INSERT INTO PICKLIST (PICKLIST_ID, PICKLIST_NAME, ENTITY_TYPE) VALUES ('gift.paymentType', 'paymentType', 'gift');
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, REFERENCE_VALUE, ITEM_ORDER) VALUES ('gift.paymentType', 'Credit Card', 'Credit Card', '.gift_creditCard, li:has(#selectedAddress), li:has(#selectedPhone)', 1);
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, REFERENCE_VALUE, ITEM_ORDER) VALUES ('gift.paymentType', 'Check', 'Check', '.gift_check', 2);
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, REFERENCE_VALUE, ITEM_ORDER) VALUES ('gift.paymentType', 'Cash', 'Cash', '.gift_cash', 3);
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, REFERENCE_VALUE, ITEM_ORDER) VALUES ('gift.paymentType', 'ACH', 'ACH', '.gift_ach, li:has(#selectedAddress), li:has(#selectedPhone)', 4);

INSERT INTO PICKLIST (PICKLIST_ID, PICKLIST_NAME, ENTITY_TYPE) VALUES ('commitment.paymentType', 'paymentType', 'commitment');
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, REFERENCE_VALUE, ITEM_ORDER) VALUES ('commitment.paymentType', 'Credit Card', 'Credit Card', '.commitment_creditCard', 1);
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, REFERENCE_VALUE, ITEM_ORDER) VALUES ('commitment.paymentType', 'ACH', 'ACH', '.commitment_ach', 4);

INSERT INTO PICKLIST (PICKLIST_ID, PICKLIST_NAME, ENTITY_TYPE) VALUES ('paymentSource.type', 'type', 'paymentSource');
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, REFERENCE_VALUE, ITEM_ORDER) VALUES ('paymentSource.type', 'Credit Card', 'Credit Card', '.paymentSource_creditCard', 1);
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, REFERENCE_VALUE, ITEM_ORDER) VALUES ('paymentSource.type', 'ACH', 'ACH', '.paymentSource_ach', 2);



