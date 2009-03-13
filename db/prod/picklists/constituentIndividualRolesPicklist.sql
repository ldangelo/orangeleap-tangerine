-- ConstituentIndividualRoles Picklist 

INSERT INTO PICKLIST (PICKLIST_ID, PICKLIST_NAME, PICKLIST_DESC) VALUES ('constituentIndividualRoles', 'constituentIndividualRoles', 'Individual Roles');
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, REFERENCE_VALUE, ITEM_ORDER) VALUES ('constituentIndividualRoles', 'donor', 'Donor', 'li:has(.ea-donor)', 3);
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, REFERENCE_VALUE, ITEM_ORDER) VALUES ('constituentIndividualRoles', 'sponsor', 'Sponsor', 'li:has(.ea-sponsor)', 4);
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, REFERENCE_VALUE, ITEM_ORDER) VALUES ('constituentIndividualRoles', 'affiliate', 'Affiliate', 'li:has(.ea-affiliate)', 5);
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, REFERENCE_VALUE, ITEM_ORDER) VALUES ('constituentIndividualRoles', 'volunteer', 'Volunteer', 'li:has(.ea-volunteer)', 6);
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, REFERENCE_VALUE, ITEM_ORDER) VALUES ('constituentIndividualRoles', 'member', 'Member', 'li:has(.ea-member)', 7);
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, REFERENCE_VALUE, ITEM_ORDER) VALUES ('constituentIndividualRoles', 'prospect', 'Prospect', 'li:has(.ea-prospect)', 8);
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, REFERENCE_VALUE, ITEM_ORDER) VALUES ('constituentIndividualRoles', 'alumni', 'Alumni', 'li:has(.ea-alumni)', 9);
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, REFERENCE_VALUE, SUPPRESS_REFERENCE_VALUE, ITEM_ORDER) VALUES ('constituentIndividualRoles', 'headofhousehold', 'Head of Household', 'li:has(.ea-headofhousehold)', 'li:has(.ea-not.headofhousehold)', 10);
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, REFERENCE_VALUE, ITEM_ORDER) VALUES ('constituentIndividualRoles', 'contact', 'Contact', 'li:has(.ea-contact)', 11);
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, REFERENCE_VALUE, ITEM_ORDER) VALUES ('constituentIndividualRoles', 'user', 'User', 'li:has(.ea-user)', 12);
