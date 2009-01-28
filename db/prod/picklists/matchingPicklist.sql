-- NOTE: reference values characters are escaped for HTML IDs -> '.' is replaced with '_' and ']' and '[' are replaced with '-'; see FieldVO#escapeChars()


INSERT INTO PICKLIST (PICKLIST_ID, PICKLIST_NAME) VALUES ('customFieldMap[organization.matching]', 'customFieldMap[organization.matching]');
--INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, REFERENCE_VALUE, ITEM_ORDER) VALUES ('customFieldMap[organization.matching]', 'No', 'No', '', 1);
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, REFERENCE_VALUE, ITEM_ORDER) VALUES ('customFieldMap[organization.matching]', 'Yes', 'Yes', 'li:has(#customFieldMap-organization_percentMatch-_value), li:has(#customFieldMap-organization_maximumMatch-_value)', 2);
