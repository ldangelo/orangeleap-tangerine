-- ConstituentOrganizationRoles Picklist 

INSERT INTO PICKLIST (PICKLIST_ID, PICKLIST_NAME) VALUES ('constituentOrganizationRoles', 'constituentOrganizationRoles');
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, REFERENCE_VALUE, ITEM_ORDER) VALUES ('constituentOrganizationRoles', 'donor', 'Donor', 'li:has(.ea-donor)', 3);
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, REFERENCE_VALUE, ITEM_ORDER) VALUES ('constituentOrganizationRoles', 'sponsor', 'Sponsor', 'li:has(.ea-sponsor)', 4);
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, REFERENCE_VALUE, ITEM_ORDER) VALUES ('constituentOrganizationRoles', 'affiliate', 'Affiliate', 'li:has(.ea-affiliate)', 5);
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, REFERENCE_VALUE, ITEM_ORDER) VALUES ('constituentOrganizationRoles', 'volunteer', 'Volunteer', 'li:has(.ea-volunteer)', 6);
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, REFERENCE_VALUE, ITEM_ORDER) VALUES ('constituentOrganizationRoles', 'member', 'Member', 'li:has(.ea-member)', 7);
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, REFERENCE_VALUE, ITEM_ORDER) VALUES ('constituentOrganizationRoles', 'prospect', 'Prospect', 'li:has(.ea-prospect)', 8);
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, REFERENCE_VALUE, ITEM_ORDER) VALUES ('constituentOrganizationRoles', 'trust', 'Trust', 'li:has(.ea-trust)', 11);
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, REFERENCE_VALUE, ITEM_ORDER) VALUES ('constituentOrganizationRoles', 'foundation', 'Foundation', 'li:has(.ea-foundation)', 12);
INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, REFERENCE_VALUE, ITEM_ORDER) VALUES ('constituentOrganizationRoles', 'vendor', 'Vendor', 'li:has(.ea-vendor)', 13);
