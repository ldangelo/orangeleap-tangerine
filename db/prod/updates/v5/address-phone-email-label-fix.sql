SET AUTOCOMMIT = 0;


UPDATE FIELD_DEFINITION set DEFAULT_LABEL = 'Address Line 3'
WHERE FIELD_DEFINITION_ID = 'constituent.primaryAddress.addressLine3' and DEFAULT_LABEL = 'Address';

UPDATE FIELD_DEFINITION set DEFAULT_LABEL = 'Address Type'
WHERE FIELD_DEFINITION_ID = 'constituent.primaryAddress.customFieldMap[addressType]' and DEFAULT_LABEL = 'Address';

UPDATE FIELD_DEFINITION set DEFAULT_LABEL = 'Address Line 1'
WHERE FIELD_DEFINITION_ID = 'constituent.primaryAddress.addressLine1' and DEFAULT_LABEL = 'Address';

UPDATE FIELD_DEFINITION set DEFAULT_LABEL = 'Address Line 2'
WHERE FIELD_DEFINITION_ID = 'constituent.primaryAddress.addressLine2' and DEFAULT_LABEL = 'Address';

UPDATE FIELD_DEFINITION set DEFAULT_LABEL = 'Zip/Postal Code'
WHERE FIELD_DEFINITION_ID = 'constituent.primaryAddress.postalCode' and DEFAULT_LABEL = 'Address';

UPDATE FIELD_DEFINITION set DEFAULT_LABEL = 'City'
WHERE FIELD_DEFINITION_ID = 'constituent.primaryAddress.city' and DEFAULT_LABEL = 'Address';

UPDATE FIELD_DEFINITION set DEFAULT_LABEL = 'State/Province'
WHERE FIELD_DEFINITION_ID = 'constituent.primaryAddress.stateProvince' and DEFAULT_LABEL = 'Address';

UPDATE FIELD_DEFINITION set DEFAULT_LABEL = 'Country'
WHERE FIELD_DEFINITION_ID = 'constituent.primaryAddress.country' and DEFAULT_LABEL = 'Address';

UPDATE FIELD_DEFINITION set DEFAULT_LABEL = 'Email Type'
WHERE FIELD_DEFINITION_ID = 'constituent.primaryEmail.customFieldMap[emailType]' and DEFAULT_LABEL = 'Email';

UPDATE FIELD_DEFINITION set DEFAULT_LABEL = 'Email Address'
WHERE FIELD_DEFINITION_ID = 'constituent.primaryEmail.emailAddress' and DEFAULT_LABEL = 'Email';

UPDATE FIELD_DEFINITION set DEFAULT_LABEL = 'Phone Type'
WHERE FIELD_DEFINITION_ID = 'constituent.primaryPhone.customFieldMap[phoneType]' and DEFAULT_LABEL = 'Phone';

UPDATE FIELD_DEFINITION set DEFAULT_LABEL = 'Phone Number'
WHERE FIELD_DEFINITION_ID = 'constituent.primaryPhone.number' and DEFAULT_LABEL = 'Phone';

COMMIT;
