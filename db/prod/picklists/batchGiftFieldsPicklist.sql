SET @Next_ID=(SELECT MAX(PICKLIST_ID)+1 FROM PICKLIST);

INSERT INTO PICKLIST (PICKLIST_ID, PICKLIST_NAME_ID, PICKLIST_NAME, PICKLIST_DESC) VALUES (@Next_ID, 'batchGiftFields', 'batchGiftFields', 'Updatable Batch Gift Fields');
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, REFERENCE_VALUE, ITEM_ORDER) VALUES (@Next_ID, 'postedDate', 'Posted Date (Creates Journal Entry)', 'date', 1);
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, REFERENCE_VALUE, ITEM_ORDER) VALUES (@Next_ID, 'status', 'Status', 'picklist', 2);
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, REFERENCE_VALUE, ITEM_ORDER) VALUES (@Next_ID, 'source', 'Source', 'picklist', 3);