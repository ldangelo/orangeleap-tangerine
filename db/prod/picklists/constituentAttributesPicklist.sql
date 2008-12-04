-- ConstituentAttributes Picklist

INSERT INTO PICKLIST (PICKLIST_ID, PICKLIST_NAME) VALUES ('constituentAttributes', 'constituentAttributes');
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, REFERENCE_VALUE, ITEM_ORDER) VALUES ('constituentAttributes', 'person', 'Person', 'li:has(.ea-person)', 1);
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, REFERENCE_VALUE, ITEM_ORDER) VALUES ('constituentAttributes', 'donor', 'Donor', 'li:has(.ea-donor)', 2);
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, REFERENCE_VALUE, ITEM_ORDER) VALUES ('constituentAttributes', 'family', 'Family', 'li:has(.ea-family)', 3);
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, REFERENCE_VALUE, ITEM_ORDER) VALUES ('constituentAttributes', 'employee', 'Employee', 'li:has(.ea-employee)', 4);
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, REFERENCE_VALUE, ITEM_ORDER) VALUES ('constituentAttributes', 'prospect', 'Prospect', 'li:has(.ea-prospect)', 5);
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, REFERENCE_VALUE, ITEM_ORDER) VALUES ('constituentAttributes', 'volunteer', 'Volunteer', 'li:has(.ea-volunteer)', 6);
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, REFERENCE_VALUE, ITEM_ORDER) VALUES ('constituentAttributes', 'organization', 'Organization', 'li:has(.ea-organization)', 7);
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, REFERENCE_VALUE, ITEM_ORDER) VALUES ('constituentAttributes', 'foundation', 'Foundation', 'li:has(.ea-foundation)', 7);
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, REFERENCE_VALUE, ITEM_ORDER) VALUES ('constituentAttributes', 'trust', 'Trust', 'li:has(.ea-trust)', 7);
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, REFERENCE_VALUE, ITEM_ORDER) VALUES ('constituentAttributes', 'vendor', 'Vendor', 'li:has(.ea-vendor)', 7);
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, REFERENCE_VALUE, ITEM_ORDER) VALUES ('constituentAttributes', 'affiliate', 'Affiliate', 'li:has(.ea-affiliate)', 7);
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, REFERENCE_VALUE, ITEM_ORDER) VALUES ('constituentAttributes', 'sponsor', 'Sponsor', 'li:has(.ea-sponsor)', 7);
