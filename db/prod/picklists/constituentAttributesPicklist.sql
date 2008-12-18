-- ConstituentAttributes Picklist 

INSERT INTO PICKLIST (PICKLIST_ID, PICKLIST_NAME) VALUES ('constituentAttributes', 'constituentAttributes');
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, REFERENCE_VALUE, ITEM_ORDER) VALUES ('constituentAttributes', 'individual', 'Individual', 'li:has(.ea-individual)', 1);
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, REFERENCE_VALUE, ITEM_ORDER) VALUES ('constituentAttributes', 'organization', 'Organization', 'li:has(.ea-organization)', 2);
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, REFERENCE_VALUE, ITEM_ORDER) VALUES ('constituentAttributes', 'donor', 'Donor', 'li:has(.ea-donor)', 3);
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, REFERENCE_VALUE, ITEM_ORDER) VALUES ('constituentAttributes', 'sponsor', 'Sponsor', 'li:has(.ea-sponsor)', 4);
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, REFERENCE_VALUE, ITEM_ORDER) VALUES ('constituentAttributes', 'affiliate', 'Affiliate', 'li:has(.ea-affiliate)', 5);
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, REFERENCE_VALUE, ITEM_ORDER) VALUES ('constituentAttributes', 'volunteer', 'Volunteer', 'li:has(.ea-volunteer)', 6);
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, REFERENCE_VALUE, ITEM_ORDER) VALUES ('constituentAttributes', 'member', 'Member', 'li:has(.ea-member)', 7);
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, REFERENCE_VALUE, ITEM_ORDER) VALUES ('constituentAttributes', 'prospect', 'Prospect', 'li:has(.ea-prospect)', 8);
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, REFERENCE_VALUE, ITEM_ORDER) VALUES ('constituentAttributes', 'alumni', 'Alumni', 'li:has(.ea-alumni)', 9);
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, REFERENCE_VALUE, ITEM_ORDER) VALUES ('constituentAttributes', 'headOfHousehold', 'Head of Household', 'li:has(.ea-headOfHousehold)', 10);
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, REFERENCE_VALUE, ITEM_ORDER) VALUES ('constituentAttributes', 'trust', 'Trust', 'li:has(.ea-trust)', 11);
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, REFERENCE_VALUE, ITEM_ORDER) VALUES ('constituentAttributes', 'foundation', 'Foundation', 'li:has(.ea-foundation)', 12);
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, REFERENCE_VALUE, ITEM_ORDER) VALUES ('constituentAttributes', 'vendor', 'Vendor', 'li:has(.ea-vendor)', 13);
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, REFERENCE_VALUE, ITEM_ORDER) VALUES ('constituentAttributes', 'contact', 'Contact', 'li:has(.ea-contact)', 14);
