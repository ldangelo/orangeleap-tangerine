-- Create a new site crederaDev
INSERT INTO SITE (SITE_ID, SITE_NAME, PARENT_SITE_ID) VALUES ('1', 'CrederaDev', NULL);

-- Create test people entries into system

INSERT INTO PERSON (TITLE, FIRST_NAME, MIDDLE_NAME, LAST_NAME, EMAIL, SUFFIX, MARITAL_STATUS, SPOUSE_FIRST_NAME, ORGANIZATION_NAME, SITE_ID, MAJOR_DONOR) VALUES ('Mr.', 'Adam', 'Jay', 'Smith', 'thepatriarch@gmail.com', null, 'Single', 'Eve', 'Alpha Omega Inc.', 1, false);
INSERT INTO PERSON (TITLE, FIRST_NAME, MIDDLE_NAME, LAST_NAME, EMAIL, SUFFIX, MARITAL_STATUS, SPOUSE_FIRST_NAME, ORGANIZATION_NAME, SITE_ID, MAJOR_DONOR) VALUES ('Ms.', 'Eve', 'Kay', 'Smith', 'thematriarch@gmail.com', null, 'Single', null, 'Alpha Omega, Inc.', 1, false);
INSERT INTO PERSON (TITLE, FIRST_NAME, MIDDLE_NAME, LAST_NAME, EMAIL, SUFFIX, MARITAL_STATUS, SPOUSE_FIRST_NAME, ORGANIZATION_NAME, SITE_ID, MAJOR_DONOR) VALUES ('Mr.', 'Erik', null, 'Weibust', 'original_gangsta@tamu.edu', null, 'Married', null, 'Credera', 1, false);
INSERT INTO PERSON (TITLE, FIRST_NAME, MIDDLE_NAME, LAST_NAME, EMAIL, SUFFIX, MARITAL_STATUS, SPOUSE_FIRST_NAME, ORGANIZATION_NAME, SITE_ID, MAJOR_DONOR) VALUES ('Mr.', 'Jonathan', null, 'Ball', 'hotchocolate01@yahoo.com', null, 'Married', null, 'Credera', 1, false);
INSERT INTO PERSON (TITLE, FIRST_NAME, MIDDLE_NAME, LAST_NAME, EMAIL, SUFFIX, MARITAL_STATUS, SPOUSE_FIRST_NAME, ORGANIZATION_NAME, SITE_ID, MAJOR_DONOR) VALUES ('Mr.', 'Ryan', null, 'Smith',  'grapples2apples@gmail.com', null, 'Single', null, 'Credera', 1, false);
INSERT INTO PERSON (TITLE, FIRST_NAME, MIDDLE_NAME, LAST_NAME, EMAIL, SUFFIX, MARITAL_STATUS, SPOUSE_FIRST_NAME, ORGANIZATION_NAME, SITE_ID, MAJOR_DONOR) VALUES ('Ms.', 'Jennifer', null, 'Stephenson', 'jstephenson@mpowersystems.com', null, 'Single', null, 'MPower Open', 1, false);
INSERT INTO PERSON (TITLE, FIRST_NAME, MIDDLE_NAME, LAST_NAME, EMAIL, SUFFIX, MARITAL_STATUS, SPOUSE_FIRST_NAME, ORGANIZATION_NAME, SITE_ID, MAJOR_DONOR) VALUES ('Mr.', 'Leo', null, 'D''Angelo', 'lDAngelo@mpowersystems.com', null, 'Married', null, 'MPower Open', 1, false);
INSERT INTO PERSON (TITLE, FIRST_NAME, MIDDLE_NAME, LAST_NAME, EMAIL, SUFFIX, MARITAL_STATUS, SPOUSE_FIRST_NAME, ORGANIZATION_NAME, SITE_ID, MAJOR_DONOR) VALUES ('Mr.', 'Vincent', null, 'Young', 'longhorn4life@utexas.edu', null, 'Single', null, 'The University of Texas at Austin', 1, false);
INSERT INTO PERSON (TITLE, FIRST_NAME, MIDDLE_NAME, LAST_NAME, EMAIL, SUFFIX, MARITAL_STATUS, SPOUSE_FIRST_NAME, ORGANIZATION_NAME, SITE_ID, MAJOR_DONOR) VALUES ('Mr.', 'Jack', null, 'Jones', 'mothergooserhymez@msn.com', null, 'Married', 'Jill', 'Mother Goose', 1, false);
INSERT INTO PERSON (TITLE, FIRST_NAME, MIDDLE_NAME, LAST_NAME, EMAIL, SUFFIX, MARITAL_STATUS, SPOUSE_FIRST_NAME, ORGANIZATION_NAME, SITE_ID, MAJOR_DONOR) VALUES ('Mrs.', 'Jill', null, 'Jones', 'tumbler@aol.com', null, 'Married', null, 'Mother Goose', 1, false);
INSERT INTO PERSON (TITLE, FIRST_NAME, MIDDLE_NAME, LAST_NAME, EMAIL, SUFFIX, MARITAL_STATUS, SPOUSE_FIRST_NAME, ORGANIZATION_NAME, SITE_ID, MAJOR_DONOR) VALUES ('Mr.', 'James', null, 'Hook', 'codfish@gmail.com', null, 'Unknown', null, null, 1, false);
INSERT INTO PERSON (TITLE, FIRST_NAME, MIDDLE_NAME, LAST_NAME, EMAIL, SUFFIX, MARITAL_STATUS, SPOUSE_FIRST_NAME, ORGANIZATION_NAME, SITE_ID, MAJOR_DONOR) VALUES ('Ms.', 'Jane', null, 'Porter', 'gorilla@gmail.com', null, 'Single', null, null, 1, false);
INSERT INTO PERSON (TITLE, FIRST_NAME, MIDDLE_NAME, LAST_NAME, EMAIL, SUFFIX, MARITAL_STATUS, SPOUSE_FIRST_NAME, ORGANIZATION_NAME, SITE_ID, MAJOR_DONOR) VALUES ('Mr.', 'Tarzan', null, 'Ape', 'ahauhaua@yahoo.com', null, 'Single', null, null, 1, false);
INSERT INTO PERSON (TITLE, FIRST_NAME, MIDDLE_NAME, LAST_NAME, EMAIL, SUFFIX, MARITAL_STATUS, SPOUSE_FIRST_NAME, ORGANIZATION_NAME, SITE_ID, MAJOR_DONOR) VALUES ('Mr.', 'Frodo', null, 'Baggins', 'ringbearer@gmail.com', null, 'Single', null, 'Fellowship', 1, false);
INSERT INTO PERSON (TITLE, FIRST_NAME, MIDDLE_NAME, LAST_NAME, EMAIL, SUFFIX, MARITAL_STATUS, SPOUSE_FIRST_NAME, ORGANIZATION_NAME, SITE_ID, MAJOR_DONOR) VALUES ('Ms.', 'Pam', null, 'Beesly', 'ilovejim@gmail.com', null, 'Single', null, 'Dunder Mifflin, Inc.', 1, false);
INSERT INTO PERSON (TITLE, FIRST_NAME, MIDDLE_NAME, LAST_NAME, EMAIL, SUFFIX, MARITAL_STATUS, SPOUSE_FIRST_NAME, ORGANIZATION_NAME, SITE_ID, MAJOR_DONOR) VALUES ('Mr.', 'Jim', null, 'Halpert', 'ilovepam@gmail.com', null, 'Single', null, 'Dunder Mifflin, Inc.', 1, false);
INSERT INTO PERSON (TITLE, FIRST_NAME, MIDDLE_NAME, LAST_NAME, EMAIL, SUFFIX, MARITAL_STATUS, SPOUSE_FIRST_NAME, ORGANIZATION_NAME, SITE_ID, MAJOR_DONOR) VALUES ('Ms.', 'Angela', null, 'Martin', 'kittycats@yahoo.com', null, 'Single', null, 'Dunder Mifflin, Inc.', 1, false);
INSERT INTO PERSON (TITLE, FIRST_NAME, MIDDLE_NAME, LAST_NAME, EMAIL, SUFFIX, MARITAL_STATUS, SPOUSE_FIRST_NAME, ORGANIZATION_NAME, SITE_ID, MAJOR_DONOR) VALUES ('Mrs.', 'Phyllis', null, 'Vance',  'pvance@gmail.com', null, 'Married', null, 'Dunder Mifflin, Inc.', 1, false);
INSERT INTO PERSON (TITLE, FIRST_NAME, MIDDLE_NAME, LAST_NAME, EMAIL, SUFFIX, MARITAL_STATUS, SPOUSE_FIRST_NAME, ORGANIZATION_NAME, SITE_ID, MAJOR_DONOR) VALUES ('Mr.', 'Bob', null, 'Vance', 'bvance@vancerefrigeration.com', null, 'Unknown', null, 'Vance Refrigeration', 1, false);
INSERT INTO PERSON (TITLE, FIRST_NAME, MIDDLE_NAME, LAST_NAME, EMAIL, SUFFIX, MARITAL_STATUS, SPOUSE_FIRST_NAME, ORGANIZATION_NAME, SITE_ID, MAJOR_DONOR) VALUES ('Mr.', 'Dwight', null, 'Schrute', 'bearsnbeets@yahoo.com', null, 'Single', null, 'Dunder Mifflin, Inc.', 1, false);
INSERT INTO PERSON (TITLE, FIRST_NAME, MIDDLE_NAME, LAST_NAME, EMAIL, SUFFIX, MARITAL_STATUS, SPOUSE_FIRST_NAME, ORGANIZATION_NAME, SITE_ID, MAJOR_DONOR) VALUES ('Ms.', 'Karen', null, 'Filippelli', 'kfilippelli@dmifflin.com', null, 'Single', null, 'Dunder Mifflin, Inc.', 1, false);
INSERT INTO PERSON (TITLE, FIRST_NAME, MIDDLE_NAME, LAST_NAME, EMAIL, SUFFIX, MARITAL_STATUS, SPOUSE_FIRST_NAME, ORGANIZATION_NAME, SITE_ID, MAJOR_DONOR) VALUES ('Mr.', 'Jack', null, 'Shephard', 'drj@msn.com', null, 'Single', null, 'ABC', 1, false);
INSERT INTO PERSON (TITLE, FIRST_NAME, MIDDLE_NAME, LAST_NAME, EMAIL, SUFFIX, MARITAL_STATUS, SPOUSE_FIRST_NAME, ORGANIZATION_NAME, SITE_ID, MAJOR_DONOR) VALUES ('Mr.', 'John', null, 'Locke', 'adventurer@aol.com', null, 'Single', null, 'ABC', 1, false);
INSERT INTO PERSON (TITLE, FIRST_NAME, MIDDLE_NAME, LAST_NAME, EMAIL, SUFFIX, MARITAL_STATUS, SPOUSE_FIRST_NAME, ORGANIZATION_NAME, SITE_ID, MAJOR_DONOR) VALUES ('Ms.', 'Kate', null, 'Austen', 'thefugitive@gmail.com', null, 'Unknown', null, 'ABC', 1, false);
INSERT INTO PERSON (TITLE, FIRST_NAME, MIDDLE_NAME, LAST_NAME, EMAIL, SUFFIX, MARITAL_STATUS, SPOUSE_FIRST_NAME, ORGANIZATION_NAME, SITE_ID, MAJOR_DONOR) VALUES ('Mr.', 'James', 'Sawyer', 'Ford', 'lonewolf@gmail.com', null, 'Single', null, 'ABC', 1, false);
INSERT INTO PERSON (TITLE, FIRST_NAME, MIDDLE_NAME, LAST_NAME, EMAIL, SUFFIX, MARITAL_STATUS, SPOUSE_FIRST_NAME, ORGANIZATION_NAME, SITE_ID, MAJOR_DONOR) VALUES ('Mrs.', 'Sun', null, 'Kwon', 'sunny@yahoo.com', null, 'Married', 'Jin', 'ABC', 1, false);
INSERT INTO PERSON (TITLE, FIRST_NAME, MIDDLE_NAME, LAST_NAME, EMAIL, SUFFIX, MARITAL_STATUS, SPOUSE_FIRST_NAME, ORGANIZATION_NAME, SITE_ID, MAJOR_DONOR) VALUES ('Mr.', 'Jin', null, 'Kwon', 'fisherman@yahoo.com', null, 'Married', 'Jin', 'ABC', 1, false);
INSERT INTO PERSON (TITLE, FIRST_NAME, MIDDLE_NAME, LAST_NAME, EMAIL, SUFFIX, MARITAL_STATUS, SPOUSE_FIRST_NAME, ORGANIZATION_NAME, SITE_ID, MAJOR_DONOR) VALUES ('Mr.', 'Homer', 'Jay', 'Simpson', 'doh@yahoo.com', null, 'Married', 'Marge', 'Fox', 1, false);
INSERT INTO PERSON (TITLE, FIRST_NAME, MIDDLE_NAME, LAST_NAME, EMAIL, SUFFIX, MARITAL_STATUS, SPOUSE_FIRST_NAME, ORGANIZATION_NAME, SITE_ID, MAJOR_DONOR) VALUES ('Mrs.', 'Marge', null, 'Simpson', 'bluetree@yahoo.com', null, 'Married', null, 'Fox', 1, false);
INSERT INTO PERSON (TITLE, FIRST_NAME, MIDDLE_NAME, LAST_NAME, EMAIL, SUFFIX, MARITAL_STATUS, SPOUSE_FIRST_NAME, ORGANIZATION_NAME, SITE_ID, MAJOR_DONOR) VALUES ('Mr.', 'Bob', null, 'Incredible', 'bigguy@yahoo.com', null, 'Married', null, 'Pixar', 1, false);
INSERT INTO PERSON (TITLE, FIRST_NAME, MIDDLE_NAME, LAST_NAME, EMAIL, SUFFIX, MARITAL_STATUS, SPOUSE_FIRST_NAME, ORGANIZATION_NAME, SITE_ID, MAJOR_DONOR) VALUES ('Mrs.', 'Helen', null, 'Incredible', 'mom@yahoo.com', null, 'Married', null, 'Pixar', 1, false);
INSERT INTO PERSON (TITLE, FIRST_NAME, MIDDLE_NAME, LAST_NAME, EMAIL, SUFFIX, MARITAL_STATUS, SPOUSE_FIRST_NAME, ORGANIZATION_NAME, SITE_ID, MAJOR_DONOR) VALUES ('Mr.', 'William', null, 'Fitzpatrick', 'gogreen@yahoo.com', null, 'Unknown', null, 'O''Reilly''s', 1, false);
INSERT INTO PERSON (TITLE, FIRST_NAME, MIDDLE_NAME, LAST_NAME, EMAIL, SUFFIX, MARITAL_STATUS, SPOUSE_FIRST_NAME, ORGANIZATION_NAME, SITE_ID, MAJOR_DONOR) VALUES ('Ms.', 'Mary', null, 'O''Doul', 'gogreener@yahoo.com', null, 'Widowed', null, 'O''Doul''s', 1, false);
INSERT INTO PERSON (TITLE, FIRST_NAME, MIDDLE_NAME, LAST_NAME, EMAIL, SUFFIX, MARITAL_STATUS, SPOUSE_FIRST_NAME, ORGANIZATION_NAME, SITE_ID, MAJOR_DONOR) VALUES ('Mr.', 'Tom', null, 'Sawyer', 'painter@yahoo.com', null, 'Single', null, null, 1, false);
INSERT INTO PERSON (TITLE, FIRST_NAME, MIDDLE_NAME, LAST_NAME, EMAIL, SUFFIX, MARITAL_STATUS, SPOUSE_FIRST_NAME, ORGANIZATION_NAME, SITE_ID, MAJOR_DONOR) VALUES ('Mr.', 'Huckleberry', null, 'Finn', 'livinfree@yahoo.com', null, 'Married', null, null, 1, false);
INSERT INTO PERSON (TITLE, FIRST_NAME, MIDDLE_NAME, LAST_NAME, EMAIL, SUFFIX, MARITAL_STATUS, SPOUSE_FIRST_NAME, ORGANIZATION_NAME, SITE_ID, MAJOR_DONOR) VALUES ('Ms.', 'Amy', null, 'Abbott', 'lady@yahoo.com', null, 'Married', 'Aaron', null, 1, false);
INSERT INTO PERSON (TITLE, FIRST_NAME, MIDDLE_NAME, LAST_NAME, EMAIL, SUFFIX, MARITAL_STATUS, SPOUSE_FIRST_NAME, ORGANIZATION_NAME, SITE_ID, MAJOR_DONOR) VALUES ('Mr.', 'Oliver', null, 'Twist', 'fictional@yahoo.com', null, 'Married', 'Nancy', null, 1, false);
INSERT INTO PERSON (TITLE, FIRST_NAME, MIDDLE_NAME, LAST_NAME, EMAIL, SUFFIX, MARITAL_STATUS, SPOUSE_FIRST_NAME, ORGANIZATION_NAME, SITE_ID, MAJOR_DONOR) VALUES ('Mr.', 'John', 'Doe', 'Kwon', 'plainojohn@yahoo.com', null, 'Married', null, null, 1, false);
INSERT INTO PERSON (TITLE, FIRST_NAME, MIDDLE_NAME, LAST_NAME, EMAIL, SUFFIX, MARITAL_STATUS, SPOUSE_FIRST_NAME, ORGANIZATION_NAME, SITE_ID, MAJOR_DONOR) VALUES ('Ms.', 'Jane', null, 'Doe', 'fisherman@yahoo.com', null, 'Single', null, null, 1, false);
INSERT INTO PERSON (TITLE, FIRST_NAME, MIDDLE_NAME, LAST_NAME, EMAIL, SUFFIX, MARITAL_STATUS, SPOUSE_FIRST_NAME, ORGANIZATION_NAME, SITE_ID, MAJOR_DONOR) VALUES ('Mr.', 'Colt', null, 'McCoy', 'hookem@utexas.edu', null, 'Single', null, 'The University of Texas at Austin', 1, false);
INSERT INTO PERSON (TITLE, FIRST_NAME, MIDDLE_NAME, LAST_NAME, EMAIL, SUFFIX, MARITAL_STATUS, SPOUSE_FIRST_NAME, ORGANIZATION_NAME, SITE_ID, MAJOR_DONOR) VALUES ('Mr.', 'George', null, 'Washington', 'fisherman@yahoo.com', null, 'Married', null, 'USA', 1, false);


-- Insert random phone numbers
INSERT INTO PHONE (NUMBER, PHONE_TYPE) VALUES (2144436829, 'home');
INSERT INTO PHONE (NUMBER, PHONE_TYPE) VALUES (2149116681, 'work');
INSERT INTO PHONE (NUMBER, PHONE_TYPE) VALUES (2145480929, 'mobile');
INSERT INTO PHONE (NUMBER, PHONE_TYPE) VALUES (2141056590, 'home');
INSERT INTO PHONE (NUMBER, PHONE_TYPE) VALUES (2141299781, 'work');
INSERT INTO PHONE (NUMBER, PHONE_TYPE) VALUES (2148781663, 'mobile');
INSERT INTO PHONE (NUMBER, PHONE_TYPE) VALUES (2141132542, 'home');
INSERT INTO PHONE (NUMBER, PHONE_TYPE) VALUES (2146423253, 'work');
INSERT INTO PHONE (NUMBER, PHONE_TYPE) VALUES (2149189851, 'mobile');
INSERT INTO PHONE (NUMBER, PHONE_TYPE) VALUES (2147561687, 'home');
INSERT INTO PHONE (NUMBER, PHONE_TYPE) VALUES (2144871581, 'work');
INSERT INTO PHONE (NUMBER, PHONE_TYPE) VALUES (2148202725, 'home');
INSERT INTO PHONE (NUMBER, PHONE_TYPE) VALUES (2141222394, 'work');
INSERT INTO PHONE (NUMBER, PHONE_TYPE) VALUES (2142368824, 'mobile');
INSERT INTO PHONE (NUMBER, PHONE_TYPE) VALUES (2141649149, 'home');
INSERT INTO PHONE (NUMBER, PHONE_TYPE) VALUES (2147981229, 'work');
INSERT INTO PHONE (NUMBER, PHONE_TYPE) VALUES (2141859155, 'mobile');
INSERT INTO PHONE (NUMBER, PHONE_TYPE) VALUES (2142878431, 'home');
INSERT INTO PHONE (NUMBER, PHONE_TYPE) VALUES (2147319815, 'work');
INSERT INTO PHONE (NUMBER, PHONE_TYPE) VALUES (2147876507, 'mobile');
INSERT INTO PHONE (NUMBER, PHONE_TYPE) VALUES (2143083942, 'home');
INSERT INTO PHONE (NUMBER, PHONE_TYPE) VALUES (2142449403, 'work');
INSERT INTO PHONE (NUMBER, PHONE_TYPE) VALUES (2144428248, 'home');
INSERT INTO PHONE (NUMBER, PHONE_TYPE) VALUES (2147056173, 'work');
INSERT INTO PHONE (NUMBER, PHONE_TYPE) VALUES (2143089287, 'mobile');
INSERT INTO PHONE (NUMBER, PHONE_TYPE) VALUES (2146305617, 'home');
INSERT INTO PHONE (NUMBER, PHONE_TYPE) VALUES (2146142230, 'work');
INSERT INTO PHONE (NUMBER, PHONE_TYPE) VALUES (2142623664, 'mobile');
INSERT INTO PHONE (NUMBER, PHONE_TYPE) VALUES (2146042984, 'home');
INSERT INTO PHONE (NUMBER, PHONE_TYPE) VALUES (2149779562, 'work');
INSERT INTO PHONE (NUMBER, PHONE_TYPE) VALUES (2145560383, 'mobile');
INSERT INTO PHONE (NUMBER, PHONE_TYPE) VALUES (2142571382, 'home');
INSERT INTO PHONE (NUMBER, PHONE_TYPE) VALUES (2141133055, 'work');
INSERT INTO PHONE (NUMBER, PHONE_TYPE) VALUES (2148702222, 'home');
INSERT INTO PHONE (NUMBER, PHONE_TYPE) VALUES (2146203524, 'work');
INSERT INTO PHONE (NUMBER, PHONE_TYPE) VALUES (2145926636, 'mobile');
INSERT INTO PHONE (NUMBER, PHONE_TYPE) VALUES (2148291284, 'home');
INSERT INTO PHONE (NUMBER, PHONE_TYPE) VALUES (2149453722, 'work');
INSERT INTO PHONE (NUMBER, PHONE_TYPE) VALUES (2142279317, 'mobile');
INSERT INTO PHONE (NUMBER, PHONE_TYPE) VALUES (2141116576, 'home');
INSERT INTO PHONE (NUMBER, PHONE_TYPE) VALUES (2148544211, 'work');


INSERT INTO ADDRESS (ADDRESS_TYPE, ADDRESS_LINE_1, ADDRESS_LINE_2, CITY, STATE_PROVINCE, COUNTRY, POSTAL_CODE) VALUES ('primaryAddress', '8070 FIRST ST', ' ', 'Dallas', 'TX', 'US', 75478);
INSERT INTO ADDRESS (ADDRESS_TYPE, ADDRESS_LINE_1, ADDRESS_LINE_2, CITY, STATE_PROVINCE, COUNTRY, POSTAL_CODE) VALUES ('primaryAddress', '8070 FIRST ST', ' ', 'Dallas', 'TX', 'US', 75478);
INSERT INTO ADDRESS (ADDRESS_TYPE, ADDRESS_LINE_1, ADDRESS_LINE_2, CITY, STATE_PROVINCE, COUNTRY, POSTAL_CODE) VALUES ('primaryAddress', '3726 THIRD ST', ' ', 'Dallas', 'TX', 'US', 75554);
INSERT INTO ADDRESS (ADDRESS_TYPE, ADDRESS_LINE_1, ADDRESS_LINE_2, CITY, STATE_PROVINCE, COUNTRY, POSTAL_CODE) VALUES ('primaryAddress', '406 FAIR OAK DR', ' ', 'Dallas', 'TX', 'US', 75479);
INSERT INTO ADDRESS (ADDRESS_TYPE, ADDRESS_LINE_1, ADDRESS_LINE_2, CITY, STATE_PROVINCE, COUNTRY, POSTAL_CODE) VALUES ('primaryAddress', '3709 ASPEN BLVD', ' ', 'Dallas', 'TX', 'US', 75238);
INSERT INTO ADDRESS (ADDRESS_TYPE, ADDRESS_LINE_1, ADDRESS_LINE_2, CITY, STATE_PROVINCE, COUNTRY, POSTAL_CODE) VALUES ('primaryAddress', '5908 ELM ', ' ', 'Dallas', 'TX', 'US', 75347);
INSERT INTO ADDRESS (ADDRESS_TYPE, ADDRESS_LINE_1, ADDRESS_LINE_2, CITY, STATE_PROVINCE, COUNTRY, POSTAL_CODE) VALUES ('primaryAddress', '8457 ACORN', ' ', 'Dallas', 'TX', 'US', 75149);
INSERT INTO ADDRESS (ADDRESS_TYPE, ADDRESS_LINE_1, ADDRESS_LINE_2, CITY, STATE_PROVINCE, COUNTRY, POSTAL_CODE) VALUES ('primaryAddress', '3980 LOVERS LN', ' ', 'Dallas', 'TX', 'US', 75424);
INSERT INTO ADDRESS (ADDRESS_TYPE, ADDRESS_LINE_1, ADDRESS_LINE_2, CITY, STATE_PROVINCE, COUNTRY, POSTAL_CODE) VALUES ('primaryAddress', '784 MOCKINGBIRD', ' ', 'Dallas', 'TX', 'US', 75738);
INSERT INTO ADDRESS (ADDRESS_TYPE, ADDRESS_LINE_1, ADDRESS_LINE_2, CITY, STATE_PROVINCE, COUNTRY, POSTAL_CODE) VALUES ('primaryAddress', '784 MOCKINGBIRD', ' ', 'Dallas', 'TX', 'US', 75738);
INSERT INTO ADDRESS (ADDRESS_TYPE, ADDRESS_LINE_1, ADDRESS_LINE_2, CITY, STATE_PROVINCE, COUNTRY, POSTAL_CODE) VALUES ('primaryAddress', '9431 ROYAL LN', ' ', 'Dallas', 'TX', 'US', 75207);
INSERT INTO ADDRESS (ADDRESS_TYPE, ADDRESS_LINE_1, ADDRESS_LINE_2, CITY, STATE_PROVINCE, COUNTRY, POSTAL_CODE) VALUES ('primaryAddress', '8842 FORSET LN', ' ', 'Dallas', 'TX', 'US', 75456);
INSERT INTO ADDRESS (ADDRESS_TYPE, ADDRESS_LINE_1, ADDRESS_LINE_2, CITY, STATE_PROVINCE, COUNTRY, POSTAL_CODE) VALUES ('primaryAddress', '8842 FORSET LN', ' ', 'Dallas', 'TX', 'US', 75456);
INSERT INTO ADDRESS (ADDRESS_TYPE, ADDRESS_LINE_1, ADDRESS_LINE_2, CITY, STATE_PROVINCE, COUNTRY, POSTAL_CODE) VALUES ('primaryAddress', '5612 SRING VALLEY RD', ' ', 'Dallas', 'TX', 'US', 75463);
INSERT INTO ADDRESS (ADDRESS_TYPE, ADDRESS_LINE_1, ADDRESS_LINE_2, CITY, STATE_PROVINCE, COUNTRY, POSTAL_CODE) VALUES ('primaryAddress', '598 KELLER SPRINGS RD', ' ', 'Dallas', 'TX', 'US', 75931);
INSERT INTO ADDRESS (ADDRESS_TYPE, ADDRESS_LINE_1, ADDRESS_LINE_2, CITY, STATE_PROVINCE, COUNTRY, POSTAL_CODE) VALUES ('primaryAddress', '3946 HILLCREST', ' ', 'Dallas', 'TX', 'US', 75187);
INSERT INTO ADDRESS (ADDRESS_TYPE, ADDRESS_LINE_1, ADDRESS_LINE_2, CITY, STATE_PROVINCE, COUNTRY, POSTAL_CODE) VALUES ('primaryAddress', '1121 PRESTON RD', ' ', 'Dallas', 'TX', 'US', 75801);
INSERT INTO ADDRESS (ADDRESS_TYPE, ADDRESS_LINE_1, ADDRESS_LINE_2, CITY, STATE_PROVINCE, COUNTRY, POSTAL_CODE) VALUES ('primaryAddress', '2201 CHURCHILL RD', ' ', 'Dallas', 'TX', 'US', 75696);
INSERT INTO ADDRESS (ADDRESS_TYPE, ADDRESS_LINE_1, ADDRESS_LINE_2, CITY, STATE_PROVINCE, COUNTRY, POSTAL_CODE) VALUES ('primaryAddress', '2934 COIT', ' ', 'Dallas', 'TX', 'US', 75263);
INSERT INTO ADDRESS (ADDRESS_TYPE, ADDRESS_LINE_1, ADDRESS_LINE_2, CITY, STATE_PROVINCE, COUNTRY, POSTAL_CODE) VALUES ('primaryAddress', '712 SQUIRES BEND', ' ', 'Dallas', 'TX', 'US', 75916);
INSERT INTO ADDRESS (ADDRESS_TYPE, ADDRESS_LINE_1, ADDRESS_LINE_2, CITY, STATE_PROVINCE, COUNTRY, POSTAL_CODE) VALUES ('primaryAddress', '2718 KNIGHTS CT', ' ', 'Dallas', 'TX', 'US', 75336);
INSERT INTO ADDRESS (ADDRESS_TYPE, ADDRESS_LINE_1, ADDRESS_LINE_2, CITY, STATE_PROVINCE, COUNTRY, POSTAL_CODE) VALUES ('primaryAddress', '3317 ALABAMA', ' ', 'Dallas', 'TX', 'US', 75397);
INSERT INTO ADDRESS (ADDRESS_TYPE, ADDRESS_LINE_1, ADDRESS_LINE_2, CITY, STATE_PROVINCE, COUNTRY, POSTAL_CODE) VALUES ('primaryAddress', '407 LOUSIANA', ' ', 'Dallas', 'TX', 'US', 75698);
INSERT INTO ADDRESS (ADDRESS_TYPE, ADDRESS_LINE_1, ADDRESS_LINE_2, CITY, STATE_PROVINCE, COUNTRY, POSTAL_CODE) VALUES ('primaryAddress', '5193 MAIN ST', ' ', 'Dallas', 'TX', 'US', 75768);
INSERT INTO ADDRESS (ADDRESS_TYPE, ADDRESS_LINE_1, ADDRESS_LINE_2, CITY, STATE_PROVINCE, COUNTRY, POSTAL_CODE) VALUES ('primaryAddress', '7945 ALPHA', ' ', 'Dallas', 'TX', 'US', 75534);
INSERT INTO ADDRESS (ADDRESS_TYPE, ADDRESS_LINE_1, ADDRESS_LINE_2, CITY, STATE_PROVINCE, COUNTRY, POSTAL_CODE) VALUES ('primaryAddress', '2775 ARAPAHO', ' ', 'Dallas', 'TX', 'US', 75447);
INSERT INTO ADDRESS (ADDRESS_TYPE, ADDRESS_LINE_1, ADDRESS_LINE_2, CITY, STATE_PROVINCE, COUNTRY, POSTAL_CODE) VALUES ('primaryAddress', '8429 16TH', ' ', 'Dallas', 'TX', 'US', 75335);
INSERT INTO ADDRESS (ADDRESS_TYPE, ADDRESS_LINE_1, ADDRESS_LINE_2, CITY, STATE_PROVINCE, COUNTRY, POSTAL_CODE) VALUES ('primaryAddress', '6032 UNIVERSITY BLVD', ' ', 'Dallas', 'TX', 'US', 75953);
INSERT INTO ADDRESS (ADDRESS_TYPE, ADDRESS_LINE_1, ADDRESS_LINE_2, CITY, STATE_PROVINCE, COUNTRY, POSTAL_CODE) VALUES ('primaryAddress', '6032 UNIVERSITY BLVD', ' ', 'Dallas', 'TX', 'US', 75953);
INSERT INTO ADDRESS (ADDRESS_TYPE, ADDRESS_LINE_1, ADDRESS_LINE_2, CITY, STATE_PROVINCE, COUNTRY, POSTAL_CODE) VALUES ('primaryAddress', '3347 GREENVILLE AVE', ' ', 'Dallas', 'TX', 'US', 75832);
INSERT INTO ADDRESS (ADDRESS_TYPE, ADDRESS_LINE_1, ADDRESS_LINE_2, CITY, STATE_PROVINCE, COUNTRY, POSTAL_CODE) VALUES ('primaryAddress', '3347 GREENVILLE AVE', ' ', 'Dallas', 'TX', 'US', 75832);
INSERT INTO ADDRESS (ADDRESS_TYPE, ADDRESS_LINE_1, ADDRESS_LINE_2, CITY, STATE_PROVINCE, COUNTRY, POSTAL_CODE) VALUES ('primaryAddress', '2247 SUNSET BLVD', ' ', 'Dallas', 'TX', 'US', 75119);
INSERT INTO ADDRESS (ADDRESS_TYPE, ADDRESS_LINE_1, ADDRESS_LINE_2, CITY, STATE_PROVINCE, COUNTRY, POSTAL_CODE) VALUES ('primaryAddress', '122 WESTHEIMER', ' ', 'Dallas', 'TX', 'US', 75615);
INSERT INTO ADDRESS (ADDRESS_TYPE, ADDRESS_LINE_1, ADDRESS_LINE_2, CITY, STATE_PROVINCE, COUNTRY, POSTAL_CODE) VALUES ('primaryAddress', '2271 STAFFORDSHIRE', ' ', 'Dallas', 'TX', 'US', 75727);
INSERT INTO ADDRESS (ADDRESS_TYPE, ADDRESS_LINE_1, ADDRESS_LINE_2, CITY, STATE_PROVINCE, COUNTRY, POSTAL_CODE) VALUES ('primaryAddress', '9412 YOUNG', ' ', 'Dallas', 'TX', 'US', 75469);
INSERT INTO ADDRESS (ADDRESS_TYPE, ADDRESS_LINE_1, ADDRESS_LINE_2, CITY, STATE_PROVINCE, COUNTRY, POSTAL_CODE) VALUES ('primaryAddress', '8571 MCCOY', ' ', 'Dallas', 'TX', 'US', 75275);
INSERT INTO ADDRESS (ADDRESS_TYPE, ADDRESS_LINE_1, ADDRESS_LINE_2, CITY, STATE_PROVINCE, COUNTRY, POSTAL_CODE) VALUES ('primaryAddress', '3672 ROSS', ' ', 'Dallas', 'TX', 'US', 75287);
INSERT INTO ADDRESS (ADDRESS_TYPE, ADDRESS_LINE_1, ADDRESS_LINE_2, CITY, STATE_PROVINCE, COUNTRY, POSTAL_CODE) VALUES ('primaryAddress', '7956 HUFF', ' ', 'Dallas', 'TX', 'US', 75941);
INSERT INTO ADDRESS (ADDRESS_TYPE, ADDRESS_LINE_1, ADDRESS_LINE_2, CITY, STATE_PROVINCE, COUNTRY, POSTAL_CODE) VALUES ('primaryAddress', '4527 CHARLES', ' ', 'Dallas', 'TX', 'US', 75543);
INSERT INTO ADDRESS (ADDRESS_TYPE, ADDRESS_LINE_1, ADDRESS_LINE_2, CITY, STATE_PROVINCE, COUNTRY, POSTAL_CODE) VALUES ('primaryAddress', '784 BENSON', ' ', 'Dallas', 'TX', 'US', 75551);
INSERT INTO ADDRESS (ADDRESS_TYPE, ADDRESS_LINE_1, ADDRESS_LINE_2, CITY, STATE_PROVINCE, COUNTRY, POSTAL_CODE) VALUES ('primaryAddress', '1514 CAMPBELL', ' ', 'Dallas', 'TX', 'US', 75306);

-- Insert into person/address cross references

INSERT INTO PERSON_ADDRESS (PERSON_ID, ADDRESS_ID) VALUES (1, 1);
INSERT INTO PERSON_ADDRESS (PERSON_ID, ADDRESS_ID) VALUES (2, 2);
INSERT INTO PERSON_ADDRESS (PERSON_ID, ADDRESS_ID) VALUES (3, 3);
INSERT INTO PERSON_ADDRESS (PERSON_ID, ADDRESS_ID) VALUES (4, 4);
INSERT INTO PERSON_ADDRESS (PERSON_ID, ADDRESS_ID) VALUES (5, 5);
INSERT INTO PERSON_ADDRESS (PERSON_ID, ADDRESS_ID) VALUES (6, 6);
INSERT INTO PERSON_ADDRESS (PERSON_ID, ADDRESS_ID) VALUES (7, 7);
INSERT INTO PERSON_ADDRESS (PERSON_ID, ADDRESS_ID) VALUES (8, 8);
INSERT INTO PERSON_ADDRESS (PERSON_ID, ADDRESS_ID) VALUES (9, 9);
INSERT INTO PERSON_ADDRESS (PERSON_ID, ADDRESS_ID) VALUES (10, 10);
INSERT INTO PERSON_ADDRESS (PERSON_ID, ADDRESS_ID) VALUES (11, 11);
INSERT INTO PERSON_ADDRESS (PERSON_ID, ADDRESS_ID) VALUES (12, 12);
INSERT INTO PERSON_ADDRESS (PERSON_ID, ADDRESS_ID) VALUES (13, 13);
INSERT INTO PERSON_ADDRESS (PERSON_ID, ADDRESS_ID) VALUES (14, 14);
INSERT INTO PERSON_ADDRESS (PERSON_ID, ADDRESS_ID) VALUES (15, 15);
INSERT INTO PERSON_ADDRESS (PERSON_ID, ADDRESS_ID) VALUES (16, 16);
INSERT INTO PERSON_ADDRESS (PERSON_ID, ADDRESS_ID) VALUES (17, 17);
INSERT INTO PERSON_ADDRESS (PERSON_ID, ADDRESS_ID) VALUES (18, 18);
INSERT INTO PERSON_ADDRESS (PERSON_ID, ADDRESS_ID) VALUES (19, 19);
INSERT INTO PERSON_ADDRESS (PERSON_ID, ADDRESS_ID) VALUES (20, 20);
INSERT INTO PERSON_ADDRESS (PERSON_ID, ADDRESS_ID) VALUES (21, 21);
INSERT INTO PERSON_ADDRESS (PERSON_ID, ADDRESS_ID) VALUES (22, 22);
INSERT INTO PERSON_ADDRESS (PERSON_ID, ADDRESS_ID) VALUES (23, 23);
INSERT INTO PERSON_ADDRESS (PERSON_ID, ADDRESS_ID) VALUES (24, 24);
INSERT INTO PERSON_ADDRESS (PERSON_ID, ADDRESS_ID) VALUES (25, 25);
INSERT INTO PERSON_ADDRESS (PERSON_ID, ADDRESS_ID) VALUES (26, 26);
INSERT INTO PERSON_ADDRESS (PERSON_ID, ADDRESS_ID) VALUES (27, 27);
INSERT INTO PERSON_ADDRESS (PERSON_ID, ADDRESS_ID) VALUES (28, 28);
INSERT INTO PERSON_ADDRESS (PERSON_ID, ADDRESS_ID) VALUES (29, 29);
INSERT INTO PERSON_ADDRESS (PERSON_ID, ADDRESS_ID) VALUES (30, 30);
INSERT INTO PERSON_ADDRESS (PERSON_ID, ADDRESS_ID) VALUES (31, 31);
INSERT INTO PERSON_ADDRESS (PERSON_ID, ADDRESS_ID) VALUES (32, 32);
INSERT INTO PERSON_ADDRESS (PERSON_ID, ADDRESS_ID) VALUES (33, 33);
INSERT INTO PERSON_ADDRESS (PERSON_ID, ADDRESS_ID) VALUES (34, 34);
INSERT INTO PERSON_ADDRESS (PERSON_ID, ADDRESS_ID) VALUES (35, 35);
INSERT INTO PERSON_ADDRESS (PERSON_ID, ADDRESS_ID) VALUES (36, 36);
INSERT INTO PERSON_ADDRESS (PERSON_ID, ADDRESS_ID) VALUES (37, 37);
INSERT INTO PERSON_ADDRESS (PERSON_ID, ADDRESS_ID) VALUES (38, 38);
INSERT INTO PERSON_ADDRESS (PERSON_ID, ADDRESS_ID) VALUES (39, 39);
INSERT INTO PERSON_ADDRESS (PERSON_ID, ADDRESS_ID) VALUES (40, 40);
INSERT INTO PERSON_ADDRESS (PERSON_ID, ADDRESS_ID) VALUES (41, 41);

-- Insert into person/phone cross references

INSERT INTO PERSON_PHONE (PERSON_ID, PHONE_ID) VALUES (1, 1);
INSERT INTO PERSON_PHONE (PERSON_ID, PHONE_ID) VALUES (2, 2);
INSERT INTO PERSON_PHONE (PERSON_ID, PHONE_ID) VALUES (3, 3);
INSERT INTO PERSON_PHONE (PERSON_ID, PHONE_ID) VALUES (4, 4);
INSERT INTO PERSON_PHONE (PERSON_ID, PHONE_ID) VALUES (5, 5);
INSERT INTO PERSON_PHONE (PERSON_ID, PHONE_ID) VALUES (6, 6);
INSERT INTO PERSON_PHONE (PERSON_ID, PHONE_ID) VALUES (7, 7);
INSERT INTO PERSON_PHONE (PERSON_ID, PHONE_ID) VALUES (8, 8);
INSERT INTO PERSON_PHONE (PERSON_ID, PHONE_ID) VALUES (9, 9);
INSERT INTO PERSON_PHONE (PERSON_ID, PHONE_ID) VALUES (10, 10);
INSERT INTO PERSON_PHONE (PERSON_ID, PHONE_ID) VALUES (11, 11);
INSERT INTO PERSON_PHONE (PERSON_ID, PHONE_ID) VALUES (12, 12);
INSERT INTO PERSON_PHONE (PERSON_ID, PHONE_ID) VALUES (13, 13);
INSERT INTO PERSON_PHONE (PERSON_ID, PHONE_ID) VALUES (14, 14);
INSERT INTO PERSON_PHONE (PERSON_ID, PHONE_ID) VALUES (15, 15);
INSERT INTO PERSON_PHONE (PERSON_ID, PHONE_ID) VALUES (16, 16);
INSERT INTO PERSON_PHONE (PERSON_ID, PHONE_ID) VALUES (17, 17);
INSERT INTO PERSON_PHONE (PERSON_ID, PHONE_ID) VALUES (18, 18);
INSERT INTO PERSON_PHONE (PERSON_ID, PHONE_ID) VALUES (19, 19);
INSERT INTO PERSON_PHONE (PERSON_ID, PHONE_ID) VALUES (20, 20);
INSERT INTO PERSON_PHONE (PERSON_ID, PHONE_ID) VALUES (21, 21);
INSERT INTO PERSON_PHONE (PERSON_ID, PHONE_ID) VALUES (22, 22);
INSERT INTO PERSON_PHONE (PERSON_ID, PHONE_ID) VALUES (23, 23);
INSERT INTO PERSON_PHONE (PERSON_ID, PHONE_ID) VALUES (24, 24);
INSERT INTO PERSON_PHONE (PERSON_ID, PHONE_ID) VALUES (25, 25);
INSERT INTO PERSON_PHONE (PERSON_ID, PHONE_ID) VALUES (26, 26);
INSERT INTO PERSON_PHONE (PERSON_ID, PHONE_ID) VALUES (27, 27);
INSERT INTO PERSON_PHONE (PERSON_ID, PHONE_ID) VALUES (28, 28);
INSERT INTO PERSON_PHONE (PERSON_ID, PHONE_ID) VALUES (29, 29);
INSERT INTO PERSON_PHONE (PERSON_ID, PHONE_ID) VALUES (30, 30);
INSERT INTO PERSON_PHONE (PERSON_ID, PHONE_ID) VALUES (31, 31);
INSERT INTO PERSON_PHONE (PERSON_ID, PHONE_ID) VALUES (32, 32);
INSERT INTO PERSON_PHONE (PERSON_ID, PHONE_ID) VALUES (33, 33);
INSERT INTO PERSON_PHONE (PERSON_ID, PHONE_ID) VALUES (34, 34);
INSERT INTO PERSON_PHONE (PERSON_ID, PHONE_ID) VALUES (35, 35);
INSERT INTO PERSON_PHONE (PERSON_ID, PHONE_ID) VALUES (36, 36);
INSERT INTO PERSON_PHONE (PERSON_ID, PHONE_ID) VALUES (37, 37);
INSERT INTO PERSON_PHONE (PERSON_ID, PHONE_ID) VALUES (38, 38);
INSERT INTO PERSON_PHONE (PERSON_ID, PHONE_ID) VALUES (39, 39);
INSERT INTO PERSON_PHONE (PERSON_ID, PHONE_ID) VALUES (40, 40);
INSERT INTO PERSON_PHONE (PERSON_ID, PHONE_ID) VALUES (41, 41);

-- Create gifts
INSERT INTO GIFT (COMMENTS, VALUE, PAYMENT_TYPE, TRANSACTION_DATE, PERSON_ID) VALUES ('Thank you for your ministry', 1000, 'Cash', current_timestamp(), 1);
