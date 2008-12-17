INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('person.accountNumber', 'person', 'accountNumber', 'Account Number', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('person.id', 'person', 'id', 'Account Number', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('person.constituentAttributes', 'person', 'constituentAttributes', 'Constituent Attributes', 'MULTI_PICKLIST');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('person.title', 'person', 'title', 'Title', 'PICKLIST', 'person');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('person.firstName', 'person', 'firstName', 'First Name', 'TEXT', 'person');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('person.middleName', 'person', 'middleName', 'Middle Name', 'TEXT', 'person');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('person.lastName', 'person', 'lastName', 'Last Name', 'TEXT', 'person');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('person.suffix', 'person', 'suffix', 'Suffix', 'PICKLIST', 'person');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('person.organizationName', 'person', 'organizationName', 'Organization Name', 'TEXT', 'organization');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('person.emailMap[home]', 'person', 'emailMap[home]', 'Email', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('person.maritalStatus', 'person', 'maritalStatus', 'Marital Status', 'PICKLIST', 'person');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('person.customFieldMap[person.spouse]', 'person', 'customFieldMap[person.spouse]', 'Spouse', 'QUERY_LOOKUP', 'person');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('person.addressMap[home]', 'person', 'addressMap[home]', ' ', 'ADDRESS');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('person.phoneMap[home]', 'person', 'phoneMap[home].number', 'Home Phone', 'PHONE');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('person.phoneMap[work]', 'person', 'phoneMap[work].number', 'Work Phone', 'PHONE');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('person.phoneMap[mobile]', 'person', 'phoneMap[mobile].number', 'Mobile Phone', 'PHONE');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('person.preferredPhoneType', 'person', 'preferredPhoneType', 'Primary Phone', 'PREFERRED_PHONE_TYPES');



INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('person.customFieldMap[organization.matching]', 'person', 'customFieldMap[organization.matching]', 'Matching', 'CHECKBOX', 'organization');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('person.customFieldMap[organization.website]', 'person', 'customFieldMap[organization.website]', 'Web Site', 'TEXT', 'organization');


// Tax Id field is used by organization, donor and employee (only ENTITY_ATTRIBUTES is used to determine this; FIELD_DEFINITION_ID and FIELD_NAME just have different uniqueness constraints)
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('person.customFieldMap[organization,donor,employee.taxid]', 'person', 'customFieldMap[organization,donor,employee.taxid]', 'Tax Id', 'TEXT', 'organization,donor,employee');

// Sponsor-specific typed fields
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('person.customFieldMap[sponsor.effectiveDate]', 'person', 'customFieldMap[sponsor.effectiveDate]', 'Sponsorship Effective Date', 'DATE', 'sponsor');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('person.customFieldMap[sponsor.years]', 'person', 'customFieldMap[sponsor.years]', 'Years as a Sponsor', 'NUMBER', 'sponsor');

// Organization Employee relationship ONE_TO_MANY
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('person.customFieldMap[employee.organization]', 'person', 'customFieldMap[employee.organization]', 'Employee''s Organization', 'QUERY_LOOKUP', 'employee');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('person.customFieldMap[organization.employees]', 'person', 'customFieldMap[organization.employees]', 'Employee List', 'MULTI_QUERY_LOOKUP', 'organization');

// Head of Household and family member fields ONE_TO_MANY
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('person.customFieldMap[familyMember.headOfHousehold]', 'person', 'customFieldMap[familyMember.headOfHousehold]', 'Head of Household', 'QUERY_LOOKUP', 'familyMember');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('person.customFieldMap[headOfHousehold.familyMembers]', 'person', 'customFieldMap[headOfHousehold.familyMembers]', 'Family members', 'MULTI_QUERY_LOOKUP', 'headOfHousehold');

// Organization Subsidiary relationship ONE_TO_MANY recursive
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('person.customFieldMap[organization.parent]', 'person', 'customFieldMap[organization.parent]', 'Parent Organization', 'QUERY_LOOKUP', 'organization');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('person.customFieldMap[organization.subsidiaryList]', 'person', 'customFieldMap[organization.subsidiaryList]', 'Subsidiary List', 'MULTI_QUERY_LOOKUP', 'organization');


// Club relationship MANY_TO_MANY 
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('person.customFieldMap[clubMember.clubGroups]', 'person', 'customFieldMap[clubMember.clubGroups]', 'Member of clubs', 'MULTI_QUERY_LOOKUP', 'clubMember');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('person.customFieldMap[clubGroup.clubMembers]', 'person', 'customFieldMap[clubGroup.clubMembers]', 'Club members', 'MULTI_QUERY_LOOKUP', 'clubGroup');

// Friend relationship MANY_TO_MANY
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('person.customFieldMap[person.friends]', 'person', 'customFieldMap[person.friends]', 'Friends', 'MULTI_QUERY_LOOKUP', 'person');

// Employee alternate ONE_TO_ONE 
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('person.customFieldMap[employee.alternate]', 'person', 'customFieldMap[employee.alternate]', 'Alternate', 'QUERY_LOOKUP', 'employee');

