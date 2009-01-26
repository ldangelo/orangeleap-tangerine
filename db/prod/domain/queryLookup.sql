INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, JPA_QUERY, FIELD_DEFINITION_ID, SITE_NAME) VALUES (2, 'SELECT commitment FROM com.mpower.domain.Commitment commitment WHERE commitment.person.site.id = :siteName', 'commitment.person.lastName', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('lastName', 2);

// Organization employee relationship
INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, JPA_QUERY, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (3, 'SELECT person FROM com.mpower.domain.Person person WHERE person.site.id = :siteName AND person.constituentType = ''organization'' ', 'person.customFieldMap[individual.organizations]', 'person.organizations', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('organizationName', 3);
INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, JPA_QUERY, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (4, 'SELECT person FROM com.mpower.domain.Person person WHERE person.site.id = :siteName AND person.constituentType = ''individual'' ', 'person.customFieldMap[organization.employees]', 'person.employees', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('lastName', 4);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('firstName', 4);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('middleName', 4);

// Head of Household relationship
INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, JPA_QUERY, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (5, 'SELECT person FROM com.mpower.domain.Person person WHERE person.site.id = :siteName AND person.constituentType = ''individual'' ', 'person.customFieldMap[individual.headOfHousehold]', 'person.headOfHousehold', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('lastName', 5);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('firstName', 5);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('middleName', 5);
INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, JPA_QUERY, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (6, 'SELECT person FROM com.mpower.domain.Person person WHERE person.site.id = :siteName AND person.constituentType = ''individual'' ', 'person.customFieldMap[headOfHousehold.householdMembers]', 'person.householdMembers', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('lastName', 6);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('firstName', 6);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('middleName', 6);

// Organization hierarchy
INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, JPA_QUERY, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (7, 'SELECT person FROM com.mpower.domain.Person person WHERE person.site.id = :siteName AND person.constituentType = ''organization'' ', 'person.customFieldMap[organization.parent]', 'person.organization.parent', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('organizationName', 7);
INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, JPA_QUERY, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (8, 'SELECT person FROM com.mpower.domain.Person person WHERE person.site.id = :siteName AND person.constituentType = ''organization'' ', 'person.customFieldMap[organization.subsidiaryList]', 'person.organization.subsidiaryList', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('organizationName', 8);

// Friend relationship
INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, JPA_QUERY, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (11, 'SELECT person FROM com.mpower.domain.Person person WHERE person.site.id = :siteName AND person.constituentType = ''individual'' ', 'person.customFieldMap[individual.friends]', 'person.friends', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('lastName', 11);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('firstName', 11);

// Parent child relationship
INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, JPA_QUERY, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (12, 'SELECT person FROM com.mpower.domain.Person person WHERE person.site.id = :siteName AND person.constituentType = ''individual'' ', 'person.customFieldMap[individual.parents]', 'person.parents', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('lastName', 12);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('firstName', 12);
INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, JPA_QUERY, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (13, 'SELECT person FROM com.mpower.domain.Person person WHERE person.site.id = :siteName AND person.constituentType = ''individual'' ', 'person.customFieldMap[individual.children]', 'person.children', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('lastName', 13);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('firstName', 13);

// Spouse relationship
INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, JPA_QUERY, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (14, 'SELECT person FROM com.mpower.domain.Person person WHERE person.site.id = :siteName AND person.constituentType = ''individual'' ', 'person.customFieldMap[individual.spouse]', 'person.spouse', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('lastName', 14);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('firstName', 14);

// Organization contacts
INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, JPA_QUERY, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (15, 'SELECT person FROM com.mpower.domain.Person person WHERE person.site.id = :siteName AND person.constituentType = ''individual'' ', 'person.customFieldMap[accountManager]', 'person.accountManager', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('lastName', 15);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('firstName', 15);
INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, JPA_QUERY, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (16, 'SELECT person FROM com.mpower.domain.Person person WHERE person.site.id = :siteName ', 'person.customFieldMap[individual.accountManagerFor]', 'person.accountManagerFor', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('lastName', 16);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('firstName', 16);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('organizationName', 16);

INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, JPA_QUERY, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (17, 'SELECT person FROM com.mpower.domain.Person person WHERE person.site.id = :siteName AND person.constituentType = ''individual'' ', 'person.customFieldMap[organization.primaryContacts]', 'person.primaryContacts', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('lastName', 17);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('firstName', 17);
INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, JPA_QUERY, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (18, 'SELECT person FROM com.mpower.domain.Person person WHERE person.site.id = :siteName AND person.constituentType = ''organization'' ', 'person.customFieldMap[individual.primaryContactFor]', 'person.primaryContactFor', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('organizationName', 18);

INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, JPA_QUERY, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (19, 'SELECT person FROM com.mpower.domain.Person person WHERE person.site.id = :siteName AND person.constituentType = ''individual'' ', 'person.customFieldMap[organization.billingContacts]', 'person.billingContacts', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('lastName', 19);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('firstName', 19);
INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, JPA_QUERY, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (20, 'SELECT person FROM com.mpower.domain.Person person WHERE person.site.id = :siteName AND person.constituentType = ''organization'' ', 'person.customFieldMap[individual.billingContactFor]', 'person.billingContactFor', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('organizationName', 20);

INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, JPA_QUERY, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (21, 'SELECT person FROM com.mpower.domain.Person person WHERE person.site.id = :siteName AND person.constituentType = ''individual'' ', 'person.customFieldMap[organization.salesContacts]', 'person.salesContacts', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('lastName', 21);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('firstName', 21);
INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, JPA_QUERY, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (22, 'SELECT person FROM com.mpower.domain.Person person WHERE person.site.id = :siteName AND person.constituentType = ''organization'' ', 'person.customFieldMap[individual.salesContactFor]', 'person.salesContactFor', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('organizationName', 22);

INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, JPA_QUERY, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (23, 'SELECT person FROM com.mpower.domain.Person person WHERE person.site.id = :siteName AND person.constituentType = ''individual'' ', 'person.customFieldMap[organization.publicRelationsContacts]', 'person.publicRelationsContacts', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('lastName', 23);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('firstName', 23);
INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, JPA_QUERY, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (24, 'SELECT person FROM com.mpower.domain.Person person WHERE person.site.id = :siteName AND person.constituentType = ''organization'' ', 'person.customFieldMap[individual.publicRelationsContactFor]', 'person.publicRelationsContactFor', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('organizationName', 24);

// Gift reference
INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, JPA_QUERY, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (25, 'SELECT person FROM com.mpower.domain.Person person WHERE person.site.id = :siteName AND person.constituentType = ''individual'' ', 'gift.customFieldMap[reference]', 'gift.donation', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('lastName', 25);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('firstName', 25);


