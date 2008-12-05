// The FIELD_RELATIONSHIP table is responsible for definitions that create and maintain a relationship between master and detail entities based on a particular field pair.
// For example if a custom field called 'employee.organization' on employee X is set to reference an organization Y, then the employee X will also be automatically added to the list of employees in custom field 'organization.employees' on organization Y.
// Also when the organization employee list is changed, the reverse operation of setting the 'employee.organization' value on the employee(s) affected is automatically perfomed.
// Unless the relationship is MANY_TO_MANY, an employee can only be in the employee list of one organization at a time.  
// In this case, if the employee is already a member of another organization, and an attempt is made to add it to the employee list of a different organization, a validation error will occur.
// If the employee is edited to point to another organization, the reference on the old organization will be removed and a new reference added to the new organization employee list.
// The participating field types of QUERY_LOOKUP and MULTI_QUERY_LOOKUP can currently fully determine the RELATIONSHIP_TYPE, however we may wish to add other, more refined, relationship types in the future. 
// Spouse is a case of a ONE_TO_ONE relationship, and club membership would be a MANY_TO_MANY relationship.
INSERT INTO FIELD_RELATIONSHIP (RELATIONSHIP_TYPE, MASTER_FIELD_DEFINITION_ID, DETAIL_FIELD_DEFINITION_ID, SITE_NAME) VALUES ('ONE_TO_MANY', 'person.customFieldMap[organization.employees]', 'person.customFieldMap[employee.organization]', null);
