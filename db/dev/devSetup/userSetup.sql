-- Create a new user
-- Creating a new user must come BEFORE creating user roles
	
INSERT INTO MPOWER_USER (USER_NAME, PASSWORD, SITE_ID) VALUES ('test', 'test', 1);

-- Create a new user roles
-- Creating a new user role must come AFTER creating new users
	
INSERT INTO MPOWER_ROLE (ROLE_NAME, USER_ID) VALUES ('ROLE_SUPERVISOR', 1);
INSERT INTO MPOWER_ROLE (ROLE_NAME, USER_ID) VALUES ('ROLE_USER', 1);
INSERT INTO MPOWER_ROLE (ROLE_NAME, USER_ID) VALUES ('ROLE_TELLER', 1);