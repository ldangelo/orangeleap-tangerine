-- Create two new sites company1 and company2

-- update  SITE set   MERCHANT_NUMBER='700000007668', MERCHANT_BIN='000002', ACH_SITE_NUMBER=853, ACH_MERCHANT_ID=11229, ACH_RULE_NUMBER= 5000, ACH_COMPANY_NAME='Orange Leap', ACH_TEST_MODE='1';


INSERT INTO SITE (SITE_NAME, PARENT_SITE_NAME, CREATE_DATE, UPDATE_DATE, MERCHANT_NUMBER,MERCHANT_BIN,MERCHANT_TERMINAL_ID,ACH_SITE_NUMBER,ACH_MERCHANT_ID,ACH_RULE_NUMBER,ACH_COMPANY_NAME,ACH_TEST_MODE,JASPER_USER_ID,JASPER_PASSWORD) VALUES ('company1', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '700000007668','000002','001',853,11229,5000,'Orange Leap','1','jasperadmin@company1','orang3');
INSERT INTO SITE (SITE_NAME, PARENT_SITE_NAME, CREATE_DATE, UPDATE_DATE, MERCHANT_NUMBER,MERCHANT_BIN,MERCHANT_TERMINAL_ID,ACH_SITE_NUMBER,ACH_MERCHANT_ID,ACH_RULE_NUMBER,ACH_COMPANY_NAME,ACH_TEST_MODE,JASPER_USER_ID,JASPER_PASSWORD) VALUES ('company2', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '700000007668','000002','001',853,11229,5000,'Orange Leap','1','jasperadmin@company2','orang3');
INSERT INTO SITE (SITE_NAME, PARENT_SITE_NAME, CREATE_DATE, UPDATE_DATE, MERCHANT_NUMBER,MERCHANT_BIN,MERCHANT_TERMINAL_ID,ACH_SITE_NUMBER,ACH_MERCHANT_ID,ACH_RULE_NUMBER,ACH_COMPANY_NAME,ACH_TEST_MODE,JASPER_USER_ID,JASPER_PASSWORD) VALUES ('demo', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '700000007668','000002','001',853,11229,5000,'Orange Leap','1','jasperadmin@demo','orang3');
INSERT INTO SITE (SITE_NAME, PARENT_SITE_NAME, CREATE_DATE, UPDATE_DATE, MERCHANT_NUMBER,MERCHANT_BIN,MERCHANT_TERMINAL_ID,ACH_SITE_NUMBER,ACH_MERCHANT_ID,ACH_RULE_NUMBER,ACH_COMPANY_NAME,ACH_TEST_MODE,JASPER_USER_ID,JASPER_PASSWORD) VALUES ('sandbox', NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '700000007668','000002','001',853,11229,5000,'Orange Leap','1','jasperadmin@sandbox','orang3');