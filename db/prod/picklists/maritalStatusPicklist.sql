-- Marital Status Picklist
-- NOTE: reference values characters are escaped for HTML IDs -> '.' is replaced with '_' and ']' and '[' are replaced with '-'; see FieldVO#escapeChars()

INSERT INTO PICKLIST (PICKLIST_ID, PICKLIST_NAME_ID, PICKLIST_NAME, PICKLIST_DESC) VALUES (14,'maritalStatus', 'maritalStatus', 'Marital Status');
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, REFERENCE_VALUE, ITEM_ORDER) VALUES (14, 'Unknown', 'Unknown', 'li:has(#customFieldMap-individual_spouse-_value)', 1);
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, REFERENCE_VALUE, ITEM_ORDER) VALUES (14, 'Single', 'Single', '', 2);
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, REFERENCE_VALUE, ITEM_ORDER) VALUES (14, 'Married', 'Married', 'li:has(#customFieldMap-individual_spouse-_value)', 3);
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, REFERENCE_VALUE, ITEM_ORDER) VALUES (14, 'Divorced', 'Divorced', 'li:has(#customFieldMap-individual_spouse-_value)', 4);
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, REFERENCE_VALUE, ITEM_ORDER) VALUES (14, 'Widowed', 'Widowed', 'li:has(#customFieldMap-individual_spouse-_value)', 5);
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, REFERENCE_VALUE, ITEM_ORDER) VALUES (14, 'Engaged', 'Engaged', 'li:has(#customFieldMap-individual_spouse-_value)', 6);