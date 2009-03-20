INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('person.accountNumber', 'person', 'accountNumber', 'Account Number', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('person.id', 'person', 'id', 'Account Number', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('person.constituentType', 'person', 'constituentType', 'Constituent Type', 'PICKLIST');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('person.constituentIndividualRoles', 'person', 'constituentIndividualRoles', 'Profile Types', 'MULTI_PICKLIST', 'individual');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('person.constituentOrganizationRoles', 'person', 'constituentOrganizationRoles', 'Profile Types', 'MULTI_PICKLIST', 'organization');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('person.title', 'person', 'title', 'Title', 'PICKLIST', 'individual');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('person.firstName', 'person', 'firstName', 'First Name', 'TEXT', 'individual');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('person.middleName', 'person', 'middleName', 'Middle Name', 'TEXT', 'individual');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('person.lastName', 'person', 'lastName', 'Last Name', 'TEXT', 'individual');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('person.suffix', 'person', 'suffix', 'Suffix', 'PICKLIST', 'individual');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('person.recognitionName', 'person', 'recognitionName', 'Recognition Name', 'TEXT', 'individual');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('person.organizationName', 'person', 'organizationName', 'Organization Name', 'TEXT', 'organization');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('person.legalName', 'person', 'legalName', 'Legal Name', 'TEXT', 'organization');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('person.primaryEmail', 'person', 'primaryEmail', 'Email', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('person.primaryAddress', 'person', 'primaryAddress', ' ', 'ADDRESS');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('person.primaryPhone', 'person', 'primaryPhone.number', 'Phone', 'PHONE');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('person.preferredPhoneType', 'person', 'preferredPhoneType', 'Primary Phone', 'PREFERRED_PHONE_TYPES');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('person.customFieldMap[organization.website]', 'person', 'customFieldMap[organization.website]', 'Web Site', 'TEXT', 'organization');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('person.customFieldMap[organization.taxid]', 'person', 'customFieldMap[organization.taxid]', 'Tax Id', 'TEXT', 'organization');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('person.ncaisCode', 'person', 'ncaisCode', 'NCAIS Code', 'PICKLIST', 'organization');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('person.customFieldMap[communicationPreferences]', 'person', 'customFieldMap[communicationPreferences]', 'Communication Preferences', 'PICKLIST', 'individual,organization');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('person.customFieldMap[emailFormat]', 'person', 'customFieldMap[emailFormat]', 'Email Format', 'PICKLIST', 'individual,organization');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('person.customFieldMap[individual.employmentTitle]', 'person', 'customFieldMap[individual.employmentTitle]', 'Employment Title', 'TEXT', 'individual');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('person.customFieldMap[formalSalutation]', 'person', 'customFieldMap[formalSalutation]', 'Formal Salutation', 'TEXT', 'individual,organization');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('person.customFieldMap[informalSalutation]', 'person', 'customFieldMap[informalSalutation]', 'Informal Salutation', 'TEXT', 'individual,organization');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('person.customFieldMap[headOfHouseholdSalutation]', 'person', 'customFieldMap[headOfHouseholdSalutation]', 'Head Of Household Salutation', 'TEXT', 'individual');

// Demographics
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('person.maritalStatus', 'person', 'maritalStatus', 'Marital Status', 'PICKLIST', 'individual');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('person.customFieldMap[individual.spouse]', 'person', 'person', 'customFieldMap[individual.spouse]', 'Spouse', 'QUERY_LOOKUP', 'individual');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('person.customFieldMap[individual.birthDate]', 'person', 'customFieldMap[individual.birthDate]', 'Birth Date', 'DATE', 'individual');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('person.customFieldMap[individual.deceased]', 'person', 'customFieldMap[individual.deceased]', 'Deceased', 'CHECKBOX', 'individual');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('person.customFieldMap[individual.gender]', 'person', 'customFieldMap[individual.gender]', 'Gender', 'PICKLIST', 'individual');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('person.customFieldMap[individual.race]', 'person', 'customFieldMap[individual.race]', 'Race', 'PICKLIST', 'individual');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('person.customFieldMap[individual.military]', 'person', 'customFieldMap[individual.military]', 'Military', 'PICKLIST', 'individual');

// Relationships
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('person.customFieldMap[individual.organizations]', 'person', 'person', 'customFieldMap[individual.organizations]', 'Employers', 'MULTI_QUERY_LOOKUP', 'individual');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('person.customFieldMap[organization.employees]', 'person', 'person', 'customFieldMap[organization.employees]', 'Employee List', 'MULTI_QUERY_LOOKUP', 'organization');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('person.customFieldMap[individual.headofhousehold]', 'person', 'person', 'customFieldMap[individual.headofhousehold]', 'Head of Household', 'QUERY_LOOKUP', 'individual,not.headofhousehold');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('person.customFieldMap[headofhousehold.householdMembers]', 'person', 'person', 'customFieldMap[headofhousehold.householdMembers]', 'Household Members', 'MULTI_QUERY_LOOKUP', 'headofhousehold');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('person.customFieldMap[organization.parent]', 'person', 'person', 'customFieldMap[organization.parent]', 'Parent Organization', 'QUERY_LOOKUP', 'organization');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('person.customFieldMap[organization.subsidiaryList]', 'person', 'person', 'customFieldMap[organization.subsidiaryList]', 'Subsidiary List', 'MULTI_QUERY_LOOKUP', 'organization');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('person.customFieldMap[individual.friends]', 'person', 'person', 'customFieldMap[individual.friends]', 'Friends', 'MULTI_QUERY_LOOKUP', 'individual');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('person.customFieldMap[individual.siblings]', 'person', 'person', 'customFieldMap[individual.siblings]', 'Siblings', 'MULTI_QUERY_LOOKUP', 'individual');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('person.customFieldMap[individual.parents]', 'person', 'person', 'customFieldMap[individual.parents]', 'Parents', 'MULTI_QUERY_LOOKUP', 'individual');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('person.customFieldMap[individual.children]', 'person', 'person', 'customFieldMap[individual.children]', 'Children', 'MULTI_QUERY_LOOKUP', 'individual');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('person.customFieldMap[accountManager]', 'person', 'person', 'customFieldMap[accountManager]', 'Account Manager', 'QUERY_LOOKUP', 'individual,organization');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('person.customFieldMap[individual.accountManagerFor]', 'person', 'person', 'customFieldMap[individual.accountManagerFor]', 'Account Manager For', 'MULTI_QUERY_LOOKUP', 'contact,user');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('person.customFieldMap[organization.primaryContacts]', 'person', 'person', 'customFieldMap[organization.primaryContacts]', 'Primary Contacts', 'MULTI_QUERY_LOOKUP', 'organization');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('person.customFieldMap[individual.primaryContactFor]', 'person', 'person', 'customFieldMap[individual.primaryContactFor]', 'Primary Contact For', 'MULTI_QUERY_LOOKUP', 'contact');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('person.customFieldMap[organization.billingContacts]', 'person', 'person', 'customFieldMap[organization.billingContacts]', 'Billing Contacts', 'MULTI_QUERY_LOOKUP', 'organization');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('person.customFieldMap[individual.billingContactFor]', 'person', 'person', 'customFieldMap[individual.billingContactFor]', 'Billing Contact For', 'MULTI_QUERY_LOOKUP', 'contact');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('person.customFieldMap[organization.salesContacts]', 'person', 'person', 'customFieldMap[organization.salesContacts]', 'Sales Contacts', 'MULTI_QUERY_LOOKUP', 'organization');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('person.customFieldMap[individual.salesContactFor]', 'person', 'person', 'customFieldMap[individual.salesContactFor]', 'Sales Contact For', 'MULTI_QUERY_LOOKUP', 'contact');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('person.customFieldMap[organization.publicRelationsContacts]', 'person', 'person', 'customFieldMap[organization.publicRelationsContacts]', 'Public Relations Contacts', 'MULTI_QUERY_LOOKUP', 'organization');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('person.customFieldMap[individual.publicRelationsContactFor]', 'person', 'person', 'customFieldMap[individual.publicRelationsContactFor]', 'Public Relations Contact For', 'MULTI_QUERY_LOOKUP', 'contact');

-- Matching
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES 
('person.customFieldMap[organization.matching]', 'person', 'customFieldMap[organization.matching]', 'Matching', 'PICKLIST', 'organization');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES 
('person.customFieldMap[organization.percentMatch]', 'person', 'customFieldMap[organization.percentMatch]', 'Percent Match', 'PERCENTAGE', 'organization,matching');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES 
('person.customFieldMap[organization.maximumAnnualLimit]', 'person', 'customFieldMap[organization.maximumAnnualLimit]', 'Maximum Annual Limit', 'PERCENTAGE', 'organization,matching');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES 
('person.customFieldMap[organization.minimumGiftMatch]', 'person', 'customFieldMap[organization.minimumGiftMatch]', 'Minimum Gift Match', 'PERCENTAGE', 'organization,matching');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES 
('person.customFieldMap[organization.maximumGiftMatch]', 'person', 'customFieldMap[organization.maximumGiftMatch]', 'Maximum Gift Match', 'PERCENTAGE', 'organization,matching');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES 
('person.customFieldMap[organization.totalPerEmployeePerYear]', 'person', 'customFieldMap[organization.totalPerEmployeePerYear]', 'Total Per Employee Per Year', 'NUMBER', 'organization,matching');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES 
('person.customFieldMap[organization.eligibility]', 'person', 'customFieldMap[organization.eligibility]', 'Eligibility', 'MULTI_PICKLIST', 'organization,matching');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES 
('person.customFieldMap[organization.eligibleFunds]', 'person', 'customFieldMap[organization.eligibleFunds]', 'Eligible Funds', 'MULTI_CODE_ADDITIONAL', 'organization,matching');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES 
('person.customFieldMap[organization.additional_eligibleFunds]', 'person', 'customFieldMap[organization.additional_eligibleFunds]', ' ', 'HIDDEN', 'organization,matching');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES 
('person.customFieldMap[organization.eligibleOrganizations]', 'person', 'customFieldMap[organization.eligibleOrganizations]', 'Eligible Orgs', 'MULTI_PICKLIST_ADDITIONAL', 'organization,matching');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES 
('person.customFieldMap[organization.additional_eligibleOrganizations]', 'person', 'customFieldMap[organization.additional_eligibleOrganizations]', ' ', 'HIDDEN', 'organization,matching');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES 
('person.customFieldMap[organization.ineligibleOrganizations]', 'person', 'customFieldMap[organization.ineligibleOrganizations]', 'Ineligible Orgs', 'MULTI_PICKLIST_ADDITIONAL', 'organization,matching');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES 
('person.customFieldMap[organization.additional_ineligibleOrganizations]', 'person', 'customFieldMap[organization.additional_ineligibleOrganizations]', ' ', 'HIDDEN', 'organization,matching');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES 
('person.customFieldMap[organization.onlineMatchingGiftForm]', 'person', 'customFieldMap[organization.onlineMatchingGiftForm]', 'Matching Gift Form', 'TEXT', 'organization,matching');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES 
('person.customFieldMap[organization.programStartMonth]', 'person', 'customFieldMap[organization.programStartMonth]', 'Program Start Month', 'PICKLIST', 'organization,matching');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES 
('person.customFieldMap[organization.programContact]', 'person', 'person', 'customFieldMap[organization.programContact]', 'Program Contact', 'QUERY_LOOKUP_OTHER', 'organization,matching');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES 
('person.customFieldMap[organization.other_programContact]', 'person', 'person', 'customFieldMap[organization.other_programContact]', ' ', 'HIDDEN', 'organization,matching');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES 
('person.customFieldMap[organization.procedureForRequestingMatch]', 'person', 'customFieldMap[organization.procedureForRequestingMatch]', 'Procedure for Requesting Match', 'LONG_TEXT', 'organization,matching');

