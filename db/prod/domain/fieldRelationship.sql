// The FIELD_RELATIONSHIP table is responsible for definitions that create and maintain a relationship between master and detail entities based on a particular field pair.
// For example if a custom field called 'employee.organizations' on employee X is set to reference an organization Y, then the employee X will also be automatically added to the list of employees in custom field 'organization.employees' on organization Y.
// Also when the organization employee list is changed, the reverse operation of setting the 'employee.organizations' value on the employee(s) affected is automatically perfomed.
// Unless the relationship is MANY_TO_MANY, a child can only be in the children list of one parent at a time.  
// The participating field types of QUERY_LOOKUP and MULTI_QUERY_LOOKUP can currently fully determine the RELATIONSHIP_TYPE, however we may wish to add other, more refined, relationship types in the future. 
// Spouse is a case of a ONE_TO_ONE relationship, and club membership would be a MANY_TO_MANY relationship.  
// Tree heirarchies are ONE_TO_MANY with the CHECK_RECURSION flag set to true.  CHECK_RECURSION is only used with ONE_TO_MANY between the same type.
INSERT INTO FIELD_RELATIONSHIP (RELATIONSHIP_TYPE, CHECK_RECURSION, MASTER_FIELD_DEFINITION_ID, DETAIL_FIELD_DEFINITION_ID, SITE_NAME) VALUES ('ONE_TO_ONE', FALSE, 'person.customFieldMap[individual.spouse]', 'person.customFieldMap[individual.spouse]', null);
INSERT INTO FIELD_RELATIONSHIP (RELATIONSHIP_TYPE, CHECK_RECURSION, MASTER_FIELD_DEFINITION_ID, DETAIL_FIELD_DEFINITION_ID, SITE_NAME) VALUES ('MANY_TO_MANY', FALSE, 'person.customFieldMap[organization.employees]', 'person.customFieldMap[individual.organizations]', null);
INSERT INTO FIELD_RELATIONSHIP (RELATIONSHIP_TYPE, CHECK_RECURSION, MASTER_FIELD_DEFINITION_ID, DETAIL_FIELD_DEFINITION_ID, SITE_NAME) VALUES ('ONE_TO_MANY', FALSE, 'person.customFieldMap[headofhousehold.householdMembers]', 'person.customFieldMap[individual.headofhousehold]', null);
INSERT INTO FIELD_RELATIONSHIP (RELATIONSHIP_TYPE, CHECK_RECURSION, MASTER_FIELD_DEFINITION_ID, DETAIL_FIELD_DEFINITION_ID, SITE_NAME) VALUES ('ONE_TO_MANY', TRUE, 'person.customFieldMap[organization.subsidiaryList]', 'person.customFieldMap[organization.parent]', null);
INSERT INTO FIELD_RELATIONSHIP (RELATIONSHIP_TYPE, CHECK_RECURSION, MASTER_FIELD_DEFINITION_ID, DETAIL_FIELD_DEFINITION_ID, SITE_NAME) VALUES ('MANY_TO_MANY', FALSE, 'person.customFieldMap[individual.friends]', 'person.customFieldMap[individual.friends]', null);
INSERT INTO FIELD_RELATIONSHIP (RELATIONSHIP_TYPE, CHECK_RECURSION, MASTER_FIELD_DEFINITION_ID, DETAIL_FIELD_DEFINITION_ID, SITE_NAME) VALUES ('MANY_TO_MANY', FALSE, 'person.customFieldMap[individual.children]', 'person.customFieldMap[individual.parents]', null);
INSERT INTO FIELD_RELATIONSHIP (RELATIONSHIP_TYPE, CHECK_RECURSION, MASTER_FIELD_DEFINITION_ID, DETAIL_FIELD_DEFINITION_ID, SITE_NAME) VALUES ('MANY_TO_MANY', FALSE, 'person.customFieldMap[individual.siblings]', 'person.customFieldMap[individual.siblings]', null);

INSERT INTO FIELD_RELATIONSHIP (RELATIONSHIP_TYPE, CHECK_RECURSION, MASTER_FIELD_DEFINITION_ID, DETAIL_FIELD_DEFINITION_ID, SITE_NAME) VALUES ('ONE_TO_MANY', FALSE, 'person.customFieldMap[individual.accountManagerFor]', 'person.customFieldMap[accountManager]', null);
INSERT INTO FIELD_RELATIONSHIP (RELATIONSHIP_TYPE, CHECK_RECURSION, MASTER_FIELD_DEFINITION_ID, DETAIL_FIELD_DEFINITION_ID, SITE_NAME) VALUES ('MANY_TO_MANY', FALSE, 'person.customFieldMap[organization.primaryContacts]', 'person.customFieldMap[individual.primaryContactFor]', null);
INSERT INTO FIELD_RELATIONSHIP (RELATIONSHIP_TYPE, CHECK_RECURSION, MASTER_FIELD_DEFINITION_ID, DETAIL_FIELD_DEFINITION_ID, SITE_NAME) VALUES ('MANY_TO_MANY', FALSE, 'person.customFieldMap[organization.billingContacts]', 'person.customFieldMap[individual.billingContactFor]', null);
INSERT INTO FIELD_RELATIONSHIP (RELATIONSHIP_TYPE, CHECK_RECURSION, MASTER_FIELD_DEFINITION_ID, DETAIL_FIELD_DEFINITION_ID, SITE_NAME) VALUES ('MANY_TO_MANY', FALSE, 'person.customFieldMap[organization.salesContacts]', 'person.customFieldMap[individual.salesContactFor]', null);
INSERT INTO FIELD_RELATIONSHIP (RELATIONSHIP_TYPE, CHECK_RECURSION, MASTER_FIELD_DEFINITION_ID, DETAIL_FIELD_DEFINITION_ID, SITE_NAME) VALUES ('MANY_TO_MANY', FALSE, 'person.customFieldMap[organization.publicRelationsContacts]', 'person.customFieldMap[individual.publicRelationsContactFor]', null);
