INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, JPA_QUERY, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (1, 'SELECT person FROM com.mpower.domain.Person person WHERE person.site.id = :siteName AND person.constituentAttributes like ''%person%'' ', 'person.spouse', 'person', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('lastName', 1);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('firstName', 1);

INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, JPA_QUERY, FIELD_DEFINITION_ID, SITE_NAME) VALUES (2, 'SELECT commitment FROM com.mpower.domain.Commitment commitment WHERE commitment.person.site.id = :siteName', 'commitment.person.lastName', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('lastName', 2);


INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, JPA_QUERY, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (3, 'SELECT person FROM com.mpower.domain.Person person WHERE person.site.id = :siteName AND person.constituentAttributes like ''%organization%'' ', 'person.customFieldMap[employee.organization]', 'person.organization', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('organizationName', 3);

INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, JPA_QUERY, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (4, 'SELECT person FROM com.mpower.domain.Person person WHERE person.site.id = :siteName AND person.constituentAttributes like ''%employee%'' ', 'person.customFieldMap[organization.employees]', 'person.employees', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('customFieldMap[organization,donor,employee.taxid]', 4);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('lastName', 4);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('firstName', 4);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('middleName', 4);

// Head of Household relationship
INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, JPA_QUERY, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (5, 'SELECT person FROM com.mpower.domain.Person person WHERE person.site.id = :siteName AND person.constituentAttributes like ''%headOfHousehold%'' ', 'person.customFieldMap[familyMember.headOfHousehold]', 'person.headOfHousehold', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('lastName', 5);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('firstName', 5);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('middleName', 5);

INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, JPA_QUERY, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (6, 'SELECT person FROM com.mpower.domain.Person person WHERE person.site.id = :siteName AND person.constituentAttributes like ''%familyMember%'' ', 'person.customFieldMap[headOfHousehold.familyMembers]', 'person.familyMembers', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('lastName', 6);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('firstName', 6);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('middleName', 6);

// Subsidiaries
INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, JPA_QUERY, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (7, 'SELECT person FROM com.mpower.domain.Person person WHERE person.site.id = :siteName AND person.constituentAttributes like ''%organization%'' ', 'person.customFieldMap[organization.parent]', 'person.organization.parent', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('organizationName', 7);
INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, JPA_QUERY, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (8, 'SELECT person FROM com.mpower.domain.Person person WHERE person.site.id = :siteName AND person.constituentAttributes like ''%organization%'' ', 'person.customFieldMap[organization.subsidiaryList]', 'person.organization.subsidiaryList', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('organizationName', 8);


// Club ClubMember relationship
INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, JPA_QUERY, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (9, 'SELECT person FROM com.mpower.domain.Person person WHERE person.site.id = :siteName AND person.constituentAttributes like ''%clubGroup%'' ', 'person.customFieldMap[clubMember.clubGroups]', 'person.clubGroups', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('organizationName', 9);

INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, JPA_QUERY, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (10, 'SELECT person FROM com.mpower.domain.Person person WHERE person.site.id = :siteName AND person.constituentAttributes like ''%clubMember%'' ', 'person.customFieldMap[clubGroup.clubMembers]', 'person.clubMembers', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('lastName', 10);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('firstName', 10);

// Friend relationship
INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, JPA_QUERY, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (11, 'SELECT person FROM com.mpower.domain.Person person WHERE person.site.id = :siteName AND person.constituentAttributes like ''%person%'' ', 'person.customFieldMap[person.friends]', 'person.friends', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('lastName', 11);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('firstName', 11);


// Employee alternate relationship
INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, JPA_QUERY, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (12, 'SELECT person FROM com.mpower.domain.Person person WHERE person.site.id = :siteName AND person.constituentAttributes like ''%employee%'' ', 'person.customFieldMap[employee.alternate]', 'person.alternate', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('lastName', 12);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID) VALUES ('firstName', 12);

