INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('person.accountNumber', 'person', 'accountNumber', 'Account Number', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('person.id', 'person', 'id', 'Account Number', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('person.title', 'person', 'title', 'Title', 'PICKLIST', 'person');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('person.firstName', 'person', 'firstName', 'First Name', 'TEXT', 'person');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('person.middleName', 'person', 'middleName', 'Middle Name', 'TEXT', 'person');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('person.lastName', 'person', 'lastName', 'Last Name', 'TEXT', 'person');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('person.suffix', 'person', 'suffix', 'Suffix', 'PICKLIST', 'person');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('person.organizationName', 'person', 'organizationName', 'Organization Name', 'TEXT', 'organization');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('person.emailMap[home]', 'person', 'emailMap[home]', 'Email', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('person.maritalStatus', 'person', 'maritalStatus', 'Marital Status', 'PICKLIST', 'person');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('person.spouse', 'person', 'spouse', 'Spouse', 'QUERY_LOOKUP', 'person');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('person.addressMap[home]', 'person', 'addressMap[home]', ' ', 'ADDRESS');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('person.phoneMap[home]', 'person', 'phoneMap[home].number', 'Home Phone', 'PHONE');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('person.phoneMap[work]', 'person', 'phoneMap[work].number', 'Work Phone', 'PHONE');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('person.phoneMap[mobile]', 'person', 'phoneMap[mobile].number', 'Mobile Phone', 'PHONE');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('person.preferredPhoneType', 'person', 'preferredPhoneType', 'Primary Phone', 'PREFERRED_PHONE_TYPES');

// Constituent type picklist
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('person.constituentAttributes', 'person', 'constituentAttributes', 'Constituent Attributes', 'MULTI_PICKLIST');

// Tax Id field is used by organization, donor and employee (only ENTITY_ATTRIBUTES is used to determine this; FIELD_DEFINITION_ID and FIELD_NAME just have different uniqueness constraints)
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('person.customFieldMap[organization,donor,employee.taxid]', 'person', 'customFieldMap[organization,donor,employee.taxid]', 'Tax Id', 'TEXT', 'organization,donor,employee');

// The organization this employee belongs to
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('person.customFieldMap[employee.organization]', 'person', 'customFieldMap[employee.organization]', 'Employee''s Organization', 'QUERY_LOOKUP', 'employee');

// The employee list for an organization
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('person.customFieldMap[organization.employees]', 'person', 'customFieldMap[organization.employees]', 'Employee List', 'MULTI_QUERY_LOOKUP', 'organization');

// Sponsor-specific fields
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('person.customFieldMap[sponsor.effectiveDate]', 'person', 'customFieldMap[sponsor.effectiveDate]', 'Sponsorship Effective Date', 'DATE', 'sponsor');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('person.customFieldMap[sponsor.years]', 'person', 'customFieldMap[sponsor.years]', 'Years as a Sponsor', 'NUMBER', 'sponsor');

