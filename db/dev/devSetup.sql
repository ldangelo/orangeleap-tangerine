-- Create a new site crederaDev
INSERT INTO SITE (SITE_ID, SITE_NAME, PARENT_SITE_ID) VALUES ('1', 'CrederaDev', NULL);

-- Create test people entries into system

INSERT INTO PERSON (TITLE, FIRST_NAME, MIDDLE_NAME, LAST_NAME, EMAIL, SUFFIX, MARITAL_STATUS, SPOUSE_FIRST_NAME, ORGANIZATION_NAME, SITE_ID) VALUES ('Mr.', 'Adam', 'Jay', 'Smith', 'thepatriarch@gmail.com', '', 'Single', 'Eve', 'Alpha Omega Inc.', 1);
INSERT INTO PERSON (TITLE, FIRST_NAME, MIDDLE_NAME, LAST_NAME, EMAIL, SUFFIX, MARITAL_STATUS, SPOUSE_FIRST_NAME, ORGANIZATION_NAME, SITE_ID) VALUES ('Ms.', 'Eve', 'Kay', 'Smith', 'thematriarch@gmail.com', '', 'Single', '', 'Alpha Omega, Inc.', 1);
INSERT INTO PERSON (TITLE, FIRST_NAME, MIDDLE_NAME, LAST_NAME, EMAIL, SUFFIX, MARITAL_STATUS, SPOUSE_FIRST_NAME, ORGANIZATION_NAME, SITE_ID) VALUES ('Mr.', 'Erik', '', 'Weibust', 'original_gangsta@tamu.edu', '', 'Married', '', 'Credera', 1);
INSERT INTO PERSON (TITLE, FIRST_NAME, MIDDLE_NAME, LAST_NAME, EMAIL, SUFFIX, MARITAL_STATUS, SPOUSE_FIRST_NAME, ORGANIZATION_NAME, SITE_ID) VALUES ('Mr.', 'Jonathan', '', 'Ball', 'hotchocolate01@yahoo.com', '', 'Married', '', 'Credera', 1);
INSERT INTO PERSON (TITLE, FIRST_NAME, MIDDLE_NAME, LAST_NAME, EMAIL, SUFFIX, MARITAL_STATUS, SPOUSE_FIRST_NAME, ORGANIZATION_NAME, SITE_ID) VALUES ('Mr.', 'Ryan', '', 'Smith',  'grapples2apples@gmail.com', '', 'Single', '', 'Credera', 1);
INSERT INTO PERSON (TITLE, FIRST_NAME, MIDDLE_NAME, LAST_NAME, EMAIL, SUFFIX, MARITAL_STATUS, SPOUSE_FIRST_NAME, ORGANIZATION_NAME, SITE_ID) VALUES ('Ms.', 'Jennifer', '', 'Stephenson', 'jstephenson@mpowersystems.com', '', 'Single', '', 'MPower Open', 1);
INSERT INTO PERSON (TITLE, FIRST_NAME, MIDDLE_NAME, LAST_NAME, EMAIL, SUFFIX, MARITAL_STATUS, SPOUSE_FIRST_NAME, ORGANIZATION_NAME, SITE_ID) VALUES ('Mr.', 'Leo', '', 'D''Angelo', 'lDAngelo@mpowersystems.com', '', 'Married', '', 'MPower Open', 1);
INSERT INTO PERSON (TITLE, FIRST_NAME, MIDDLE_NAME, LAST_NAME, EMAIL, SUFFIX, MARITAL_STATUS, SPOUSE_FIRST_NAME, ORGANIZATION_NAME, SITE_ID) VALUES ('Mr.', 'Vincent', '', 'Young', 'longhorn4life@utexas.edu', '', 'Single', '', 'The University of Texas at Austin', 1);
INSERT INTO PERSON (TITLE, FIRST_NAME, MIDDLE_NAME, LAST_NAME, EMAIL, SUFFIX, MARITAL_STATUS, SPOUSE_FIRST_NAME, ORGANIZATION_NAME, SITE_ID) VALUES ('Mr.', 'Jack', '', 'Jones', 'mothergooserhymez@msn.com', '', 'Married', 'Jill', 'Mother Goose', 1);
INSERT INTO PERSON (TITLE, FIRST_NAME, MIDDLE_NAME, LAST_NAME, EMAIL, SUFFIX, MARITAL_STATUS, SPOUSE_FIRST_NAME, ORGANIZATION_NAME, SITE_ID) VALUES ('Mrs.', 'Jill', '', 'Jones', 'tumbler@aol.com', '', 'Married', '', 'Mother Goose', 1);
INSERT INTO PERSON (TITLE, FIRST_NAME, MIDDLE_NAME, LAST_NAME, EMAIL, SUFFIX, MARITAL_STATUS, SPOUSE_FIRST_NAME, ORGANIZATION_NAME, SITE_ID) VALUES ('Mr.', 'James', '', 'Hook', 'codfish@gmail.com', '', 'Unknown', '', '', 1);
INSERT INTO PERSON (TITLE, FIRST_NAME, MIDDLE_NAME, LAST_NAME, EMAIL, SUFFIX, MARITAL_STATUS, SPOUSE_FIRST_NAME, ORGANIZATION_NAME, SITE_ID) VALUES ('Ms.', 'Jane', '', 'Porter', 'gorilla@gmail.com', '', 'Single', '', '', 1);
INSERT INTO PERSON (TITLE, FIRST_NAME, MIDDLE_NAME, LAST_NAME, EMAIL, SUFFIX, MARITAL_STATUS, SPOUSE_FIRST_NAME, ORGANIZATION_NAME, SITE_ID) VALUES ('Mr.', 'Tarzan', '', 'Ape', 'ahauhaua@yahoo.com', '', 'Single', '', '', 1);

