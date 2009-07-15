// Organization employee relationship
INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, ENTITY_TYPE, SQL_WHERE, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (1000003, 'constituent','constituent_type = ''organization'' ', 'constituent.customFieldMap[individual.organizations]', 'constituent.organizations', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('organizationName', 1000003);
INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, ENTITY_TYPE, SQL_WHERE, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (1000004, 'constituent','constituent_type = ''individual'' ', 'constituent.customFieldMap[organization.employees]', 'constituent.employees', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('lastName', 1000004);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('firstName', 1000004);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('middle_name', 1000004);

// Head of Household relationship
INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, ENTITY_TYPE, SQL_WHERE, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (1000005, 'constituent','constituent_type = ''individual'' ', 'constituent.customFieldMap[individual.headofhousehold]', 'constituent.headofhousehold', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('lastName', 1000005);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('firstName', 1000005);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('middle_name', 1000005);
INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, ENTITY_TYPE, SQL_WHERE, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (1000006, 'constituent','constituent_type = ''individual'' ', 'constituent.customFieldMap[headofhousehold.householdMembers]', 'constituent.householdMembers', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('lastName', 1000006);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('firstName', 1000006);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('middle_name', 1000006);

// Organization hierarchy
INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, ENTITY_TYPE, SQL_WHERE, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (1000007, 'constituent','constituent_type = ''organization'' ', 'constituent.customFieldMap[organization.parent]', 'constituent.organization.parent', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('organizationName', 1000007);
INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, ENTITY_TYPE, SQL_WHERE, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (1000008, 'constituent','constituent_type = ''organization'' ', 'constituent.customFieldMap[organization.subsidiaryList]', 'constituent.organization.subsidiaryList', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('organizationName', 1000008);

// Sibling relationship
INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, ENTITY_TYPE, SQL_WHERE, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (1000010, 'constituent','constituent_type = ''individual'' ', 'constituent.customFieldMap[individual.siblings]', 'constituent.siblings', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('lastName', 1000010);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('firstName', 1000010);

// Friend relationship
INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, ENTITY_TYPE, SQL_WHERE, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (1000011, 'constituent','constituent_type = ''individual'' ', 'constituent.customFieldMap[individual.friends]', 'constituent.friends', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('lastName', 1000011);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('firstName', 1000011);

// Parent child relationship
INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, ENTITY_TYPE, SQL_WHERE, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (1000012, 'constituent','constituent_type = ''individual'' ', 'constituent.customFieldMap[individual.parents]', 'constituent.parents', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('lastName', 1000012);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('firstName', 1000012);
INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, ENTITY_TYPE, SQL_WHERE, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (1000013, 'constituent','constituent_type = ''individual'' ', 'constituent.customFieldMap[individual.children]', 'constituent.children', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('lastName', 1000013);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('firstName', 1000013);

// Spouse relationship
INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, ENTITY_TYPE, SQL_WHERE, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (1000014, 'constituent','constituent_type = ''individual'' ', 'constituent.customFieldMap[individual.spouse]', 'constituent.spouse', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('lastName', 1000014);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('firstName', 1000014);

// Organization contacts
INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, ENTITY_TYPE, SQL_WHERE, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (1000015, 'constituent','constituent_type = ''individual'' ', 'constituent.customFieldMap[accountManager]', 'constituent.accountManager', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('lastName', 1000015);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('firstName', 1000015);
INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, ENTITY_TYPE, SQL_WHERE, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (1000016, 'constituent', '', 'constituent.customFieldMap[individual.accountManagerFor]', 'constituent.accountManagerFor', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('lastName', 1000016);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('firstName', 1000016);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('organizationName', 1000016);

INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, ENTITY_TYPE, SQL_WHERE, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (1000017, 'constituent','constituent_type = ''individual'' ', 'constituent.customFieldMap[organization.primaryContacts]', 'constituent.primaryContacts', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('lastName', 1000017);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('firstName', 1000017);
INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, ENTITY_TYPE, SQL_WHERE, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (1000018, 'constituent','constituent_type = ''organization'' ', 'constituent.customFieldMap[individual.primaryContactFor]', 'constituent.primaryContactFor', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('organizationName', 1000018);

INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, ENTITY_TYPE, SQL_WHERE, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (1000019, 'constituent','constituent_type = ''individual'' ', 'constituent.customFieldMap[organization.billingContacts]', 'constituent.billingContacts', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('lastName', 1000019);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('firstName', 1000019);
INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, ENTITY_TYPE, SQL_WHERE, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (1000020, 'constituent','constituent_type = ''organization'' ', 'constituent.customFieldMap[individual.billingContactFor]', 'constituent.billingContactFor', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('organizationName', 1000020);

INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, ENTITY_TYPE, SQL_WHERE, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (1000021, 'constituent','constituent_type = ''individual'' ', 'constituent.customFieldMap[organization.salesContacts]', 'constituent.salesContacts', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('lastName', 1000021);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('firstName', 1000021);
INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, ENTITY_TYPE, SQL_WHERE, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (1000022, 'constituent','constituent_type = ''organization'' ', 'constituent.customFieldMap[individual.salesContactFor]', 'constituent.salesContactFor', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('organizationName', 1000022);

INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, ENTITY_TYPE, SQL_WHERE, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (1000023, 'constituent','constituent_type = ''individual'' ', 'constituent.customFieldMap[organization.publicRelationsContacts]', 'constituent.publicRelationsContacts', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('lastName', 1000023);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('firstName', 1000023);
INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, ENTITY_TYPE, SQL_WHERE, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (1000024, 'constituent','constituent_type = ''organization'' ', 'constituent.customFieldMap[individual.publicRelationsContactFor]', 'constituent.publicRelationsContactFor', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('organizationName', 1000024);

// Gift reference
INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, ENTITY_TYPE, SQL_WHERE, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (1000025, 'constituent','constituent_type = ''individual'' ', 'gift.distributionLines.customFieldMap[reference]', 'gift.donation', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('lastName', 1000025);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('firstName', 1000025);

// Pledge reference
INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, ENTITY_TYPE, SQL_WHERE, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (1000026, 'constituent','constituent_type = ''individual'' ', 'pledge.customFieldMap[reference]', 'pledge.info', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('lastName', 1000026);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('firstName', 1000026);

// Recurring gift reference
INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, ENTITY_TYPE, SQL_WHERE, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (1000036, 'constituent','constituent_type = ''individual'' ', 'recurringGift.customFieldMap[reference]', 'pledge.info', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('lastName', 1000036);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('firstName', 1000036);

// Communication History reference
INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, ENTITY_TYPE, SQL_WHERE, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (1000027, 'constituent','constituent_type = ''individual'' ', 'communicationHistory.customFieldMap[assignedTo]', 'communicationHistory', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('lastName', 1000027);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('firstName', 1000027);

// Matching
INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, ENTITY_TYPE, SQL_WHERE, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (1000028, 'constituent','constituent_type = ''individual'' ', 'constituent.customFieldMap[organization.programContact]', 'constituent.programContact', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('lastName', 1000028);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('firstName', 1000028);
INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, ENTITY_TYPE, SQL_WHERE, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (1000029, 'constituent','constituent_type = ''organization'' ', 'constituent.customFieldMap[individual.programContactFor]', 'constituent.programContactFor', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('organizationName', 1000029);

INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, ENTITY_TYPE, SQL_WHERE, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (1000030, 'constituent','constituent_type = ''individual'' ', 'gift.distributionLines.customFieldMap[tributeReference]', 'gift.tributeReference', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('lastName', 1000030);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('firstName', 1000030);

INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, ENTITY_TYPE, SQL_WHERE, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (1000031, 'constituent','constituent_type = ''individual'' ', 'gift.distributionLines.customFieldMap[onBehalfOf]', 'gift.onBehalfOf', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('lastName', 1000031);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('firstName', 1000031);

INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, ENTITY_TYPE, SQL_WHERE, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (1000032, 'constituent','constituent_type = ''individual'' ', 'gift.distributionLines.customFieldMap[notified]', 'gift.notified', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('lastName', 1000032);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('firstName', 1000032);

INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, ENTITY_TYPE, SQL_WHERE, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (1000033, 'constituent','constituent_type = ''individual'' ', 'pledge.customFieldMap[tributeReference]', 'pledge.tributeReference', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('lastName', 1000033);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('firstName', 1000033);

INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, ENTITY_TYPE, SQL_WHERE, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (1000034, 'constituent','constituent_type = ''individual'' ', 'pledge.customFieldMap[onBehalfOf]', 'pledge.onBehalfOf', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('lastName', 1000034);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('firstName', 1000034);

INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, ENTITY_TYPE, SQL_WHERE, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (1000035, 'constituent','constituent_type = ''individual'' ', 'pledge.customFieldMap[notified]', 'pledge.notified', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('lastName', 1000035);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('firstName', 1000035);

INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, ENTITY_TYPE, SQL_WHERE, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (1000037, 'constituent','constituent_type = ''individual'' ', 'recurringGift.customFieldMap[tributeReference]', 'recurringGift.tributeReference', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('lastName', 1000037);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('firstName', 1000037);

INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, ENTITY_TYPE, SQL_WHERE, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (1000038, 'constituent','constituent_type = ''individual'' ', 'recurringGift.customFieldMap[onBehalfOf]', 'recurringGift.onBehalfOf', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('lastName', 1000038);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('firstName', 1000038);

INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, ENTITY_TYPE, SQL_WHERE, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (1000039, 'constituent','constituent_type = ''individual'' ', 'recurringGift.customFieldMap[notified]', 'recurringGift.notified', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('lastName', 1000039);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('firstName', 1000039);
