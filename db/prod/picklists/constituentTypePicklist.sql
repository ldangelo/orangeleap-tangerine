-- ConstituentType Picklist 

INSERT INTO PICKLIST (PICKLIST_ID, PICKLIST_NAME_ID, PICKLIST_NAME, PICKLIST_DESC) VALUES (4,'constituentType', 'constituentType', 'Constituent Type');
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, REFERENCE_VALUE, ITEM_ORDER) VALUES (4, 'individual', 'Individual', 'li:has(.ea-individual)', 1);
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, REFERENCE_VALUE, ITEM_ORDER) VALUES (4, 'organization', 'Organization', 'li:has(.ea-organization)', 2);
