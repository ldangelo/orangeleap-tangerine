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

