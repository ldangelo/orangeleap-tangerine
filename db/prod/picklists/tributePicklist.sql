-- We need to leave space under 999 for adding default fields later.  New site-specific fields will now start being automatically added by the system starting at 1000.
INSERT INTO PICKLIST (PICKLIST_ID, PICKLIST_NAME_ID, PICKLIST_NAME, PICKLIST_DESC) VALUES (999, 'customFieldMap[tribute]', 'customFieldMap[tribute]', 'Tribute');
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, ITEM_ORDER) VALUES (999, 'inHonorOf', 'In Honor Of', 1);
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, ITEM_ORDER) VALUES (999, 'inMemoryOf', 'In Memory Of', 2);
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, ITEM_ORDER) VALUES (999, 'inPerpetualMemoryOf', 'In Perpetual Memory Of', 3);
