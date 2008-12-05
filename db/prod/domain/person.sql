INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('person.accountNumber', 'person', 'accountNumber', 'Account Number', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('person.id', 'person', 'id', 'Account Number', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('person.title', 'person', 'title', 'Title', 'PICKLIST');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('person.firstName', 'person', 'firstName', 'First Name', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('person.middleName', 'person', 'middleName', 'Middle Name', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('person.lastName', 'person', 'lastName', 'Last Name', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('person.suffix', 'person', 'suffix', 'Suffix', 'PICKLIST');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('person.organizationName', 'person', 'organizationName', 'Organization Name', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('person.emailMap[home]', 'person', 'emailMap[home]', 'Email', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('person.maritalStatus', 'person', 'maritalStatus', 'Marital Status', 'PICKLIST');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('person.spouse', 'person', 'spouse', 'Spouse', 'QUERY_LOOKUP');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('person.addressMap[home]', 'person', 'addressMap[home]', ' ', 'ADDRESS');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('person.phoneMap[home]', 'person', 'phoneMap[home].number', 'Home Phone', 'PHONE');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('person.phoneMap[work]', 'person', 'phoneMap[work].number', 'Work Phone', 'PHONE');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('person.phoneMap[mobile]', 'person', 'phoneMap[mobile].number', 'Mobile Phone', 'PHONE');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('person.preferredPhoneType', 'person', 'preferredPhoneType', 'Primary Phone', 'PREFERRED_PHONE_TYPES');

// Constituent type picklist
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('person.constituentAttributes', 'person', 'constituentAttributes', 'Constituent Attributes', 'MULTI_PICKLIST');

// Tax Id field is used by organization, donor and employee (only FIELD_NAME is used to determine this; FIELD_DEFINITION_ID just has to be unique)
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('person.customFieldMap[organization,donor,employee.taxid]', 'person', 'customFieldMap[organization,donor,employee.taxid]', 'Tax Id', 'TEXT');

// The organization this employee belongs to
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('person.customFieldMap[employee.organization]', 'person', 'customFieldMap[employee.organization]', 'Employee''s Organization', 'QUERY_LOOKUP');

// The employee list for an organization
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('person.customFieldMap[organization.employees]', 'person', 'customFieldMap[organization.employee]', 'Employee List', 'MULTI_QUERY_LOOKUP');
