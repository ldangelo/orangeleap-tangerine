-- NOTE: reference values characters are escaped for HTML IDs -> '.' is replaced with '_' and ']' and '[' are replaced with '-'; see FieldVO#escapeChars()


INSERT INTO PICKLIST (PICKLIST_ID, PICKLIST_NAME_ID, PICKLIST_NAME, PICKLIST_DESC) VALUES (15,'customFieldMap[organization.matching]', 'customFieldMap[organization.matching]', 'Organization Matching');
--INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, REFERENCE_VALUE, ITEM_ORDER) VALUES (15, 'No', 'No', '', 1);
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, REFERENCE_VALUE, ITEM_ORDER) VALUES (15, 'Yes', 'Yes', 'li:has(#customFieldMap-organization_percentMatch-_value),li:has(#customFieldMap-organization_maximumAnnualLimit-_value)', 2);
