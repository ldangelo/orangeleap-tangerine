-- ConstituentAttributes Picklist 

INSERT INTO PICKLIST (PICKLIST_ID, PICKLIST_NAME) VALUES ('constituentAttributes', 'constituentAttributes');
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, REFERENCE_VALUE, ITEM_ORDER) VALUES ('constituentAttributes', 'person', 'Person', 'li:has(.ea-person)', 1);
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, REFERENCE_VALUE, ITEM_ORDER) VALUES ('constituentAttributes', 'organization', 'Organization', 'li:has(.ea-organization)', 2);
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, REFERENCE_VALUE, ITEM_ORDER) VALUES ('constituentAttributes', 'employee', 'Employee', 'li:has(.ea-employee)', 3);
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, REFERENCE_VALUE, ITEM_ORDER) VALUES ('constituentAttributes', 'volunteer', 'Volunteer', 'li:has(.ea-volunteer)', 4);
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, REFERENCE_VALUE, ITEM_ORDER) VALUES ('constituentAttributes', 'donor', 'Donor', 'li:has(.ea-donor)', 5);
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, REFERENCE_VALUE, ITEM_ORDER) VALUES ('constituentAttributes', 'prospect', 'Prospect', 'li:has(.ea-prospect)', 6);
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, REFERENCE_VALUE, ITEM_ORDER) VALUES ('constituentAttributes', 'foundation', 'Foundation', 'li:has(.ea-foundation)', 7);
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, REFERENCE_VALUE, ITEM_ORDER) VALUES ('constituentAttributes', 'trust', 'Trust', 'li:has(.ea-trust)', 8);
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, REFERENCE_VALUE, ITEM_ORDER) VALUES ('constituentAttributes', 'vendor', 'Vendor', 'li:has(.ea-vendor)', 9);
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, REFERENCE_VALUE, ITEM_ORDER) VALUES ('constituentAttributes', 'affiliate', 'Affiliate', 'li:has(.ea-affiliate)', 10);
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, REFERENCE_VALUE, ITEM_ORDER) VALUES ('constituentAttributes', 'subsidiary', 'Subsidiary', 'li:has(.ea-subsidiary)', 11);
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, REFERENCE_VALUE, ITEM_ORDER) VALUES ('constituentAttributes', 'sponsor', 'Sponsor', 'li:has(.ea-sponsor)', 12);
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, REFERENCE_VALUE, ITEM_ORDER) VALUES ('constituentAttributes', 'family', 'Family', 'li:has(.ea-family)', 13);
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, REFERENCE_VALUE, ITEM_ORDER) VALUES ('constituentAttributes', 'school', 'School', 'li:has(.ea-school)', 14);
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, REFERENCE_VALUE, ITEM_ORDER) VALUES ('constituentAttributes', 'student', 'Student', 'li:has(.ea-student)', 15);
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, REFERENCE_VALUE, ITEM_ORDER) VALUES ('constituentAttributes', 'teacher', 'Teacher', 'li:has(.ea-teacher)', 16);
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, REFERENCE_VALUE, ITEM_ORDER) VALUES ('constituentAttributes', 'child', 'Child', 'li:has(.ea-student)', 17);
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, REFERENCE_VALUE, ITEM_ORDER) VALUES ('constituentAttributes', 'parent', 'Parent', 'li:has(.ea-parent)', 18);
