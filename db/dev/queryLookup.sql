INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, JPA_QUERY, FIELD_DEFINITION_ID, SECTION_NAME, SITE_NAME) VALUES (1, 'SELECT person FROM com.mpower.domain.Person person WHERE person.site.id = :siteName', 'person.spouse', 'person', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID, PARAM_ORDER) VALUES ('person.lastName', 1, 1000);

INSERT INTO QUERY_LOOKUP (QUERY_LOOKUP_ID, JPA_QUERY, FIELD_DEFINITION_ID, SITE_NAME) VALUES (2, 'SELECT commitment FROM com.mpower.domain.Commitment commitment WHERE commitment.person.site.id = :siteName', 'commitment.person.lastName', null);
INSERT INTO QUERY_LOOKUP_PARAM (PARAM_NAME, QUERY_LOOKUP_ID, PARAM_ORDER) VALUES ('commitment.person.lastName', 2, 1000);
