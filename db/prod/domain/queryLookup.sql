--INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, SQL_QUERY, FIELD_DEFINITION_ID, SITE_NAME) VALUES (2, 'SELECT commitment FROM com.mpower.domain.Commitment commitment WHERE commitment.site_name = :site_name', 'commitment.person.last_name', null);
--INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('last_name', 2);

// Organization employee relationship
INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, SQL_QUERY, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (3, 'SELECT constituent_id, organization_name, last_name, first_name, middle_name, suffix FROM CONSTITUENT WHERE site_name = :siteName AND constituent_type = ''organization'' ', 'person.customFieldMap[individual.organizations]', 'person.organizations', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('organization_name', 3);
INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, SQL_QUERY, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (4, 'SELECT constituent_id, organization_name, last_name, first_name, middle_name, suffix FROM CONSTITUENT WHERE site_name = :siteName AND constituent_type = ''individual'' ', 'person.customFieldMap[organization.employees]', 'person.employees', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('last_name', 4);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('first_name', 4);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('middle_name', 4);

// Head of Household relationship
INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, SQL_QUERY, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (5, 'SELECT constituent_id, organization_name, last_name, first_name, middle_name, suffix FROM CONSTITUENT WHERE site_name = :siteName AND constituent_type = ''individual'' ', 'person.customFieldMap[individual.headOfHousehold]', 'person.headOfHousehold', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('last_name', 5);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('first_name', 5);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('middle_name', 5);
INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, SQL_QUERY, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (6, 'SELECT constituent_id, organization_name, last_name, first_name, middle_name, suffix FROM CONSTITUENT WHERE site_name = :siteName AND constituent_type = ''individual'' ', 'person.customFieldMap[headOfHousehold.householdMembers]', 'person.householdMembers', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('last_name', 6);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('first_name', 6);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('middle_name', 6);

// Organization hierarchy
INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, SQL_QUERY, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (7, 'SELECT constituent_id, organization_name, last_name, first_name, middle_name, suffix FROM CONSTITUENT WHERE site_name = :siteName AND constituent_type = ''organization'' ', 'person.customFieldMap[organization.parent]', 'person.organization.parent', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('organization_name', 7);
INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, SQL_QUERY, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (8, 'SELECT constituent_id, organization_name, last_name, first_name, middle_name, suffix FROM CONSTITUENT WHERE site_name = :siteName AND constituent_type = ''organization'' ', 'person.customFieldMap[organization.subsidiaryList]', 'person.organization.subsidiaryList', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('organization_name', 8);

// Sibling relationship
INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, SQL_QUERY, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (10, 'SELECT constituent_id, organization_name, last_name, first_name, middle_name, suffix FROM CONSTITUENT WHERE site_name = :siteName AND constituent_type = ''individual'' ', 'person.customFieldMap[individual.siblings]', 'person.siblings', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('last_name', 10);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('first_name', 10);

// Friend relationship
INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, SQL_QUERY, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (11, 'SELECT constituent_id, organization_name, last_name, first_name, middle_name, suffix FROM CONSTITUENT WHERE site_name = :siteName AND constituent_type = ''individual'' ', 'person.customFieldMap[individual.friends]', 'person.friends', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('last_name', 11);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('first_name', 11);

// Parent child relationship
INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, SQL_QUERY, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (12, 'SELECT constituent_id, organization_name, last_name, first_name, middle_name, suffix FROM CONSTITUENT WHERE site_name = :siteName AND constituent_type = ''individual'' ', 'person.customFieldMap[individual.parents]', 'person.parents', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('last_name', 12);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('first_name', 12);
INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, SQL_QUERY, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (13, 'SELECT constituent_id, organization_name, last_name, first_name, middle_name, suffix FROM CONSTITUENT WHERE site_name = :siteName AND constituent_type = ''individual'' ', 'person.customFieldMap[individual.children]', 'person.children', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('last_name', 13);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('first_name', 13);

// Spouse relationship
INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, SQL_QUERY, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (14, 'SELECT constituent_id, organization_name, last_name, first_name, middle_name, suffix FROM CONSTITUENT WHERE site_name = :siteName AND constituent_type = ''individual'' ', 'person.customFieldMap[individual.spouse]', 'person.spouse', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('last_name', 14);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('first_name', 14);

// Organization contacts
INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, SQL_QUERY, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (15, 'SELECT constituent_id, organization_name, last_name, first_name, middle_name, suffix FROM CONSTITUENT WHERE site_name = :siteName AND constituent_type = ''individual'' ', 'person.customFieldMap[accountManager]', 'person.accountManager', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('last_name', 15);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('first_name', 15);
INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, SQL_QUERY, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (16, 'SELECT constituent_id, organization_name, last_name, first_name, middle_name, suffix FROM CONSTITUENT WHERE site_name = :siteName ', 'person.customFieldMap[individual.accountManagerFor]', 'person.accountManagerFor', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('last_name', 16);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('first_name', 16);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('organization_name', 16);

INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, SQL_QUERY, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (17, 'SELECT constituent_id, organization_name, last_name, first_name, middle_name, suffix FROM CONSTITUENT WHERE site_name = :siteName AND constituent_type = ''individual'' ', 'person.customFieldMap[organization.primaryContacts]', 'person.primaryContacts', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('last_name', 17);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('first_name', 17);
INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, SQL_QUERY, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (18, 'SELECT constituent_id, organization_name, last_name, first_name, middle_name, suffix FROM CONSTITUENT WHERE site_name = :siteName AND constituent_type = ''organization'' ', 'person.customFieldMap[individual.primaryContactFor]', 'person.primaryContactFor', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('organization_name', 18);

INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, SQL_QUERY, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (19, 'SELECT constituent_id, organization_name, last_name, first_name, middle_name, suffix FROM CONSTITUENT WHERE site_name = :siteName AND constituent_type = ''individual'' ', 'person.customFieldMap[organization.billingContacts]', 'person.billingContacts', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('last_name', 19);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('first_name', 19);
INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, SQL_QUERY, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (20, 'SELECT constituent_id, organization_name, last_name, first_name, middle_name, suffix FROM CONSTITUENT WHERE site_name = :siteName AND constituent_type = ''organization'' ', 'person.customFieldMap[individual.billingContactFor]', 'person.billingContactFor', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('organization_name', 20);

INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, SQL_QUERY, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (21, 'SELECT constituent_id, organization_name, last_name, first_name, middle_name, suffix FROM CONSTITUENT WHERE site_name = :siteName AND constituent_type = ''individual'' ', 'person.customFieldMap[organization.salesContacts]', 'person.salesContacts', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('last_name', 21);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('first_name', 21);
INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, SQL_QUERY, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (22, 'SELECT constituent_id, organization_name, last_name, first_name, middle_name, suffix FROM CONSTITUENT WHERE site_name = :siteName AND constituent_type = ''organization'' ', 'person.customFieldMap[individual.salesContactFor]', 'person.salesContactFor', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('organization_name', 22);

INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, SQL_QUERY, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (23, 'SELECT constituent_id, organization_name, last_name, first_name, middle_name, suffix FROM CONSTITUENT WHERE site_name = :siteName AND constituent_type = ''individual'' ', 'person.customFieldMap[organization.publicRelationsContacts]', 'person.publicRelationsContacts', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('last_name', 23);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('first_name', 23);
INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, SQL_QUERY, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (24, 'SELECT constituent_id, organization_name, last_name, first_name, middle_name, suffix FROM CONSTITUENT WHERE site_name = :siteName AND constituent_type = ''organization'' ', 'person.customFieldMap[individual.publicRelationsContactFor]', 'person.publicRelationsContactFor', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('organization_name', 24);

// Gift reference
INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, SQL_QUERY, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (25, 'SELECT constituent_id, organization_name, last_name, first_name, middle_name, suffix FROM CONSTITUENT WHERE site_name = :siteName AND constituent_type = ''individual'' ', 'gift.customFieldMap[reference]', 'gift.donation', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('last_name', 25);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('first_name', 25);

// Commitment reference
INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, SQL_QUERY, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (26, 'SELECT constituent_id, organization_name, last_name, first_name, middle_name, suffix FROM CONSTITUENT WHERE site_name = :siteName AND constituent_type = ''individual'' ', 'commitment.customFieldMap[reference]', 'pledge.info', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('last_name', 26);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('first_name', 26);


