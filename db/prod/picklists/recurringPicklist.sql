INSERT INTO PICKLIST (PICKLIST_ID, PICKLIST_NAME, PICKLIST_DESC) VALUES ('recurring', 'recurring', 'Recurring');
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, REFERENCE_VALUE, ITEM_ORDER) VALUES ('recurring', 'true', 'Yes', 'li:has(#amountPerGift),li:has(#frequency),li:has(#startDate),li:has(#endDate)', 1);
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, REFERENCE_VALUE, ITEM_ORDER) VALUES ('recurring', 'false', 'No', 'li:has(#amountTotal),li:has(#projectedDate)', 2);
