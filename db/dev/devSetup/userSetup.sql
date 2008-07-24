-- Create a new user
-- Creating a new user must come BEFORE creating user roles

INSERT INTO MPOWER_USER (USER_NAME, SITE_NAME) VALUES ('rod', 'company1');
INSERT INTO MPOWER_USER (USER_NAME, SITE_NAME) VALUES ('dianne', 'company1');
INSERT INTO MPOWER_USER (USER_NAME, SITE_NAME) VALUES ('scott', 'company1');

INSERT INTO MPOWER_USER (USER_NAME, SITE_NAME) VALUES ('jack', 'company2');
INSERT INTO MPOWER_USER (USER_NAME, SITE_NAME) VALUES ('locke', 'company2');
INSERT INTO MPOWER_USER (USER_NAME, SITE_NAME) VALUES ('hurley', 'company2');
