-- ConstituentType Picklist 

INSERT INTO PICKLIST (PICKLIST_ID, PICKLIST_NAME) VALUES ('constituentType', 'constituentType');
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, REFERENCE_VALUE, ITEM_ORDER) VALUES ('constituentType', 'individual', 'Individual', 'li:has(.ea-individual)', 1);
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, REFERENCE_VALUE, ITEM_ORDER) VALUES ('constituentType', 'organization', 'Organization', 'li:has(.ea-organization)', 2);
