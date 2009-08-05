INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('constituent.accountNumber', 'constituent', 'accountNumber', 'Account Number', 'NUMBER');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('constituent.loginId', 'constituent', 'loginId', 'Login Id', 'TEXT', 'user');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('constituent.accountNumberReadOnly', 'constituent', 'accountNumber', 'Account Number', 'READ_ONLY_TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('constituent.id', 'constituent', 'id', 'Account Id', 'TEXT');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('constituent.constituentType', 'constituent', 'constituentType', 'Constituent Type', 'PICKLIST');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('constituent.customFieldMap[constituentIndividualRoles]', 'constituent', 'customFieldMap[constituentIndividualRoles]', 'Profile Types', 'MULTI_PICKLIST', 'individual');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('constituent.customFieldMap[constituentOrganizationRoles]', 'constituent', 'customFieldMap[constituentOrganizationRoles]', 'Profile Types', 'MULTI_PICKLIST', 'organization');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('constituent.title', 'constituent', 'title', 'Title', 'PICKLIST', 'individual');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('constituent.firstName', 'constituent', 'firstName', 'First Name', 'TEXT', 'individual');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('constituent.middleName', 'constituent', 'middleName', 'Middle Name', 'TEXT', 'individual');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('constituent.lastName', 'constituent', 'lastName', 'Last Name', 'TEXT', 'individual');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('constituent.suffix', 'constituent', 'suffix', 'Suffix', 'PICKLIST', 'individual');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('constituent.recognitionName', 'constituent', 'recognitionName', 'Recognition Name', 'TEXT', 'individual,organization');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('constituent.organizationName', 'constituent', 'organizationName', 'Organization Name', 'TEXT', 'organization');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('constituent.legalName', 'constituent', 'legalName', 'Legal Name', 'TEXT', 'organization');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL) VALUES ('constituent.primaryEmail', 'constituent', 'primaryEmail', 'Email');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL) VALUES ('constituent.primaryAddress', 'constituent', 'primaryAddress', 'Address');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL) VALUES ('constituent.primaryPhone', 'constituent', 'primaryPhone', 'Phone');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE) VALUES ('constituent.preferredPhoneType', 'constituent', 'preferredPhoneType', 'Primary Phone', 'PREFERRED_PHONE_TYPES');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('constituent.customFieldMap[organization.website]', 'constituent', 'customFieldMap[organization.website]', 'Web Site', 'TEXT', 'organization');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('constituent.customFieldMap[organization.taxid]', 'constituent', 'customFieldMap[organization.taxid]', 'Tax Id', 'TEXT', 'organization');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('constituent.naicsCode', 'constituent', 'naicsCode', 'NAICS Code', 'PICKLIST', 'organization');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('constituent.customFieldMap[communicationPreferences]', 'constituent', 'customFieldMap[communicationPreferences]', 'Communication Preferences', 'PICKLIST', '');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('constituent.customFieldMap[communicationOptInPreferences]', 'constituent', 'customFieldMap[communicationOptInPreferences]', 'Opt In Preferences', 'MULTI_PICKLIST', 'communicationOptIn');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('constituent.customFieldMap[emailFormat]', 'constituent', 'customFieldMap[emailFormat]', 'Email Format', 'PICKLIST', 'individual,organization');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('constituent.customFieldMap[formalSalutation]', 'constituent', 'customFieldMap[formalSalutation]', 'Formal Salutation', 'TEXT', 'individual,organization');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('constituent.customFieldMap[informalSalutation]', 'constituent', 'customFieldMap[informalSalutation]', 'Informal Salutation', 'TEXT', 'individual,organization');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('constituent.customFieldMap[headOfHouseholdSalutation]', 'constituent', 'customFieldMap[headOfHouseholdSalutation]', 'Head Of Household Salutation', 'TEXT', 'individual');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('constituent.customFieldMap[donorProfiles]', 'constituent', 'customFieldMap[donorProfiles]', 'Donor Profiles', 'MULTI_PICKLIST', 'donor');

// Demographics
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('constituent.maritalStatus', 'constituent', 'maritalStatus', 'Marital Status', 'PICKLIST', 'individual');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('constituent.customFieldMap[individual.spouse]', 'constituent', 'constituent', 'customFieldMap[individual.spouse]', 'Spouse', 'QUERY_LOOKUP', 'individual,spouse');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('constituent.customFieldMap[individual.birthDate]', 'constituent', 'customFieldMap[individual.birthDate]', 'Birth Date', 'DATE', 'individual');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('constituent.customFieldMap[individual.deceased]', 'constituent', 'customFieldMap[individual.deceased]', 'Deceased', 'CHECKBOX', 'individual');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('constituent.customFieldMap[individual.gender]', 'constituent', 'customFieldMap[individual.gender]', 'Gender', 'PICKLIST', 'individual');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('constituent.customFieldMap[individual.race]', 'constituent', 'customFieldMap[individual.race]', 'Race', 'PICKLIST', 'individual');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('constituent.customFieldMap[individual.military]', 'constituent', 'customFieldMap[individual.military]', 'Military', 'PICKLIST', 'individual');

// Relationships
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('constituent.customFieldMap[individual.organizations]', 'constituent', 'constituent', 'customFieldMap[individual.organizations]', 'Employers', 'MULTI_QUERY_LOOKUP', 'individual');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('constituent.customFieldMap[organization.employees]', 'constituent', 'constituent', 'customFieldMap[organization.employees]', 'Employee List', 'MULTI_QUERY_LOOKUP', 'organization');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('constituent.customFieldMap[individual.headofhousehold]', 'constituent', 'constituent', 'customFieldMap[individual.headofhousehold]', 'Head of Household', 'QUERY_LOOKUP', 'individual,not.headofhousehold');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('constituent.customFieldMap[headofhousehold.householdMembers]', 'constituent', 'constituent', 'customFieldMap[headofhousehold.householdMembers]', 'Household Members', 'MULTI_QUERY_LOOKUP', 'headofhousehold');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('constituent.customFieldMap[organization.parent]', 'constituent', 'constituent', 'customFieldMap[organization.parent]', 'Parent Organization', 'QUERY_LOOKUP', 'organization');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('constituent.customFieldMap[organization.subsidiaryList]', 'constituent', 'constituent', 'customFieldMap[organization.subsidiaryList]', 'Subsidiary List', 'MULTI_QUERY_LOOKUP', 'organization');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('constituent.customFieldMap[individual.friends]', 'constituent', 'constituent', 'customFieldMap[individual.friends]', 'Friends', 'MULTI_QUERY_LOOKUP', 'individual');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('constituent.customFieldMap[individual.siblings]', 'constituent', 'constituent', 'customFieldMap[individual.siblings]', 'Siblings', 'MULTI_QUERY_LOOKUP', 'individual');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('constituent.customFieldMap[individual.parents]', 'constituent', 'constituent', 'customFieldMap[individual.parents]', 'Parents', 'MULTI_QUERY_LOOKUP', 'individual');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('constituent.customFieldMap[individual.children]', 'constituent', 'constituent', 'customFieldMap[individual.children]', 'Children', 'MULTI_QUERY_LOOKUP', 'individual');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('constituent.customFieldMap[accountManager]', 'constituent', 'constituent', 'customFieldMap[accountManager]', 'Account Manager', 'QUERY_LOOKUP', 'individual,organization');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('constituent.customFieldMap[individual.accountManagerFor]', 'constituent', 'constituent', 'customFieldMap[individual.accountManagerFor]', 'Account Manager For', 'MULTI_QUERY_LOOKUP', 'contact,user');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('constituent.customFieldMap[organization.primaryContacts]', 'constituent', 'constituent', 'customFieldMap[organization.primaryContacts]', 'Primary Contacts', 'MULTI_QUERY_LOOKUP', 'organization');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('constituent.customFieldMap[individual.primaryContactFor]', 'constituent', 'constituent', 'customFieldMap[individual.primaryContactFor]', 'Primary Contact For', 'MULTI_QUERY_LOOKUP', 'contact');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('constituent.customFieldMap[organization.billingContacts]', 'constituent', 'constituent', 'customFieldMap[organization.billingContacts]', 'Billing Contacts', 'MULTI_QUERY_LOOKUP', 'organization');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('constituent.customFieldMap[individual.billingContactFor]', 'constituent', 'constituent', 'customFieldMap[individual.billingContactFor]', 'Billing Contact For', 'MULTI_QUERY_LOOKUP', 'contact');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('constituent.customFieldMap[organization.salesContacts]', 'constituent', 'constituent', 'customFieldMap[organization.salesContacts]', 'Sales Contacts', 'MULTI_QUERY_LOOKUP', 'organization');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('constituent.customFieldMap[individual.salesContactFor]', 'constituent', 'constituent', 'customFieldMap[individual.salesContactFor]', 'Sales Contact For', 'MULTI_QUERY_LOOKUP', 'contact');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('constituent.customFieldMap[organization.publicRelationsContacts]', 'constituent', 'constituent', 'customFieldMap[organization.publicRelationsContacts]', 'Public Relations Contacts', 'MULTI_QUERY_LOOKUP', 'organization');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('constituent.customFieldMap[individual.publicRelationsContactFor]', 'constituent', 'constituent', 'customFieldMap[individual.publicRelationsContactFor]', 'Public Relations Contact For', 'MULTI_QUERY_LOOKUP', 'contact');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES ('constituent.customFieldMap[individual.programContactFor]', 'constituent', 'constituent', 'customFieldMap[individual.programContactFor]', 'Matching Program Contact For', 'MULTI_QUERY_LOOKUP', 'contact');

-- Matching
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES 
('constituent.customFieldMap[organization.matching]', 'constituent', 'customFieldMap[organization.matching]', 'Matching', 'PICKLIST', 'organization');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES 
('constituent.customFieldMap[organization.percentMatch]', 'constituent', 'customFieldMap[organization.percentMatch]', 'Percent Match', 'PERCENTAGE', 'organization,matching');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES 
('constituent.customFieldMap[organization.maximumAnnualLimit]', 'constituent', 'customFieldMap[organization.maximumAnnualLimit]', 'Maximum Annual Limit', 'PERCENTAGE', 'organization,matching');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES 
('constituent.customFieldMap[organization.minimumGiftMatch]', 'constituent', 'customFieldMap[organization.minimumGiftMatch]', 'Minimum Gift Match', 'PERCENTAGE', 'organization,matching');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES 
('constituent.customFieldMap[organization.maximumGiftMatch]', 'constituent', 'customFieldMap[organization.maximumGiftMatch]', 'Maximum Gift Match', 'PERCENTAGE', 'organization,matching');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES 
('constituent.customFieldMap[organization.totalPerEmployeePerYear]', 'constituent', 'customFieldMap[organization.totalPerEmployeePerYear]', 'Total Per Employee Per Year', 'NUMBER', 'organization,matching');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES 
('constituent.customFieldMap[organization.eligibility]', 'constituent', 'customFieldMap[organization.eligibility]', 'Eligibility', 'MULTI_PICKLIST', 'organization,matching');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES 
('constituent.customFieldMap[organization.eligibleFunds]', 'constituent', 'customFieldMap[organization.eligibleFunds]', 'Eligible Funds', 'MULTI_CODE_ADDITIONAL', 'organization,matching');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES 
('constituent.customFieldMap[organization.additional_eligibleFunds]', 'constituent', 'customFieldMap[organization.additional_eligibleFunds]', 'Eligible Funds', 'HIDDEN', 'organization,matching');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES 
('constituent.customFieldMap[organization.eligibleOrganizations]', 'constituent', 'customFieldMap[organization.eligibleOrganizations]', 'Eligible Orgs', 'MULTI_PICKLIST_ADDITIONAL', 'organization,matching');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES 
('constituent.customFieldMap[organization.additional_eligibleOrganizations]', 'constituent', 'customFieldMap[organization.additional_eligibleOrganizations]', 'Eligible Orgs', 'HIDDEN', 'organization,matching');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES 
('constituent.customFieldMap[organization.ineligibleOrganizations]', 'constituent', 'customFieldMap[organization.ineligibleOrganizations]', 'Ineligible Orgs', 'MULTI_PICKLIST_ADDITIONAL', 'organization,matching');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES 
('constituent.customFieldMap[organization.additional_ineligibleOrganizations]', 'constituent', 'customFieldMap[organization.additional_ineligibleOrganizations]', 'Ineligible Orgs', 'HIDDEN', 'organization,matching');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES 
('constituent.customFieldMap[organization.onlineMatchingGiftForm]', 'constituent', 'customFieldMap[organization.onlineMatchingGiftForm]', 'Matching Gift Form', 'TEXT', 'organization,matching');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES 
('constituent.customFieldMap[organization.programStartMonth]', 'constituent', 'customFieldMap[organization.programStartMonth]', 'Program Start Month', 'PICKLIST', 'organization,matching');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES 
('constituent.customFieldMap[organization.programContact]', 'constituent', 'constituent', 'customFieldMap[organization.programContact]', 'Program Contact', 'QUERY_LOOKUP_OTHER', 'organization,matching');
INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, REFERENCE_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES 
('constituent.customFieldMap[organization.other_programContact]', 'constituent', 'constituent', 'customFieldMap[organization.other_programContact]', 'Program Contact', 'HIDDEN', 'organization,matching');

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, ENTITY_TYPE, FIELD_NAME, DEFAULT_LABEL, FIELD_TYPE, ENTITY_ATTRIBUTES) VALUES 
('constituent.customFieldMap[organization.procedureForRequestingMatch]', 'constituent', 'customFieldMap[organization.procedureForRequestingMatch]', 'Procedure for Requesting Match', 'LONG_TEXT', 'organization,matching');

