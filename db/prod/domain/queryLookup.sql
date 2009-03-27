--INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, ENTITY_TYPE, SQL_WHERE, FIELD_DEFINITION_ID, SITE_NAME) VALUES (2, 'SELECT commitment FROM com.orangeleap.tangerine.domain.Commitment commitment WHERE commitment.site_name = :site_name', 'commitment.person.lastName', null);
--INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('lastName', 2);

// Organization employee relationship
INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, ENTITY_TYPE, SQL_WHERE, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (3, 'person','constituent_type = ''organization'' ', 'person.customFieldMap[individual.organizations]', 'person.organizations', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('organizationName', 3);
INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, ENTITY_TYPE, SQL_WHERE, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (4, 'person','constituent_type = ''individual'' ', 'person.customFieldMap[organization.employees]', 'person.employees', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('lastName', 4);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('firstName', 4);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('middle_name', 4);

// Head of Household relationship
INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, ENTITY_TYPE, SQL_WHERE, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (5, 'person','constituent_type = ''individual'' ', 'person.customFieldMap[individual.headofhousehold]', 'person.headofhousehold', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('lastName', 5);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('firstName', 5);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('middle_name', 5);
INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, ENTITY_TYPE, SQL_WHERE, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (6, 'person','constituent_type = ''individual'' ', 'person.customFieldMap[headofhousehold.householdMembers]', 'person.householdMembers', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('lastName', 6);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('firstName', 6);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('middle_name', 6);

// Organization hierarchy
INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, ENTITY_TYPE, SQL_WHERE, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (7, 'person','constituent_type = ''organization'' ', 'person.customFieldMap[organization.parent]', 'person.organization.parent', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('organizationName', 7);
INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, ENTITY_TYPE, SQL_WHERE, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (8, 'person','constituent_type = ''organization'' ', 'person.customFieldMap[organization.subsidiaryList]', 'person.organization.subsidiaryList', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('organizationName', 8);

// Sibling relationship
INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, ENTITY_TYPE, SQL_WHERE, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (10, 'person','constituent_type = ''individual'' ', 'person.customFieldMap[individual.siblings]', 'person.siblings', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('lastName', 10);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('firstName', 10);

// Friend relationship
INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, ENTITY_TYPE, SQL_WHERE, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (11, 'person','constituent_type = ''individual'' ', 'person.customFieldMap[individual.friends]', 'person.friends', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('lastName', 11);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('firstName', 11);

// Parent child relationship
INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, ENTITY_TYPE, SQL_WHERE, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (12, 'person','constituent_type = ''individual'' ', 'person.customFieldMap[individual.parents]', 'person.parents', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('lastName', 12);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('firstName', 12);
INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, ENTITY_TYPE, SQL_WHERE, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (13, 'person','constituent_type = ''individual'' ', 'person.customFieldMap[individual.children]', 'person.children', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('lastName', 13);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('firstName', 13);

// Spouse relationship
INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, ENTITY_TYPE, SQL_WHERE, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (14, 'person','constituent_type = ''individual'' ', 'person.customFieldMap[individual.spouse]', 'person.spouse', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('lastName', 14);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('firstName', 14);

// Organization contacts
INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, ENTITY_TYPE, SQL_WHERE, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (15, 'person','constituent_type = ''individual'' ', 'person.customFieldMap[accountManager]', 'person.accountManager', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('lastName', 15);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('firstName', 15);
INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, ENTITY_TYPE, SQL_WHERE, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (16, 'person', '', 'person.customFieldMap[individual.accountManagerFor]', 'person.accountManagerFor', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('lastName', 16);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('firstName', 16);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('organizationName', 16);

INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, ENTITY_TYPE, SQL_WHERE, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (17, 'person','constituent_type = ''individual'' ', 'person.customFieldMap[organization.primaryContacts]', 'person.primaryContacts', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('lastName', 17);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('firstName', 17);
INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, ENTITY_TYPE, SQL_WHERE, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (18, 'person','constituent_type = ''organization'' ', 'person.customFieldMap[individual.primaryContactFor]', 'person.primaryContactFor', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('organizationName', 18);

INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, ENTITY_TYPE, SQL_WHERE, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (19, 'person','constituent_type = ''individual'' ', 'person.customFieldMap[organization.billingContacts]', 'person.billingContacts', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('lastName', 19);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('firstName', 19);
INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, ENTITY_TYPE, SQL_WHERE, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (20, 'person','constituent_type = ''organization'' ', 'person.customFieldMap[individual.billingContactFor]', 'person.billingContactFor', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('organizationName', 20);

INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, ENTITY_TYPE, SQL_WHERE, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (21, 'person','constituent_type = ''individual'' ', 'person.customFieldMap[organization.salesContacts]', 'person.salesContacts', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('lastName', 21);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('firstName', 21);
INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, ENTITY_TYPE, SQL_WHERE, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (22, 'person','constituent_type = ''organization'' ', 'person.customFieldMap[individual.salesContactFor]', 'person.salesContactFor', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('organizationName', 22);

INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, ENTITY_TYPE, SQL_WHERE, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (23, 'person','constituent_type = ''individual'' ', 'person.customFieldMap[organization.publicRelationsContacts]', 'person.publicRelationsContacts', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('lastName', 23);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('firstName', 23);
INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, ENTITY_TYPE, SQL_WHERE, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (24, 'person','constituent_type = ''organization'' ', 'person.customFieldMap[individual.publicRelationsContactFor]', 'person.publicRelationsContactFor', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('organizationName', 24);

// Gift reference
INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, ENTITY_TYPE, SQL_WHERE, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (25, 'person','constituent_type = ''individual'' ', 'gift.customFieldMap[reference]', 'gift.donation', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('lastName', 25);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('firstName', 25);

// Commitment reference
INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, ENTITY_TYPE, SQL_WHERE, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (26, 'person','constituent_type = ''individual'' ', 'commitment.customFieldMap[reference]', 'pledge.info', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('lastName', 26);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('firstName', 26);

// Communication History reference
INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, ENTITY_TYPE, SQL_WHERE, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (27, 'person','constituent_type = ''individual'' ', 'communicationHistory.customFieldMap[assignedTo]', 'communicationHistory', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('lastName', 27);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('firstName', 27);

// Matching
INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, ENTITY_TYPE, SQL_WHERE, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (28, 'person','constituent_type = ''individual'' ', 'person.customFieldMap[organization.programContact]', 'person.programContact', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('lastName', 28);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('firstName', 28);
INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, ENTITY_TYPE, SQL_WHERE, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (29, 'person','constituent_type = ''organization'' ', 'person.customFieldMap[individual.programContactFor]', 'person.programContactFor', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('organizationName', 29);

INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, ENTITY_TYPE, SQL_WHERE, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (30, 'person','constituent_type = ''individual'' ', 'gift.customFieldMap[tributeReference]', 'gift.tributeReference', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('lastName', 30);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('firstName', 30);

INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, ENTITY_TYPE, SQL_WHERE, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (31, 'person','constituent_type = ''individual'' ', 'gift.customFieldMap[onBehalfOf]', 'gift.onBehalfOf', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('lastName', 31);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('firstName', 31);

INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, ENTITY_TYPE, SQL_WHERE, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (32, 'person','constituent_type = ''individual'' ', 'gift.customFieldMap[notified]', 'gift.notified', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('lastName', 32);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('firstName', 32);

INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, ENTITY_TYPE, SQL_WHERE, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (33, 'person','constituent_type = ''individual'' ', 'commitment.customFieldMap[tributeReference]', 'commitment.tributeReference', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('lastName', 33);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('firstName', 33);

INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, ENTITY_TYPE, SQL_WHERE, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (34, 'person','constituent_type = ''individual'' ', 'commitment.customFieldMap[onBehalfOf]', 'commitment.onBehalfOf', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('lastName', 34);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('firstName', 34);

INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, ENTITY_TYPE, SQL_WHERE, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (35, 'person','constituent_type = ''individual'' ', 'commitment.customFieldMap[notified]', 'commitment.notified', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('lastName', 35);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('firstName', 35);
