-- Flag the major donors in the who have donate at at least $5000 in the past year
-- Must be executed AFTER:
	-- PERSON insertions (personSetup.sql)
	-- GIFT insertions (giftSetup.sql)

UPDATE PERSON SET MAJOR_DONOR = TRUE WHERE PERSON_ID IN (SELECT PERSON_ID FROM GIFT WHERE VALUE > 0 AND (TRANSACTION_DATE BETWEEN (DATE_ADD(CURRENT_TIMESTAMP, INTERVAL -1 YEAR)) AND CURRENT_TIMESTAMP) GROUP BY PERSON_ID HAVING (SUM(VALUE)>=5000));

-- Flag the lapsed donors in the DB who have not donated in the past two months
-- Must be executed AFTER:
	-- PERSON insertions (personSetup.sql)
	-- GIFT insertions (giftSetup.sql)

UPDATE PERSON SET LAPSED_DONOR = TRUE WHERE PERSON_ID NOT IN (SELECT PERSON_ID FROM GIFT WHERE (VALUE > 0) AND (TRANSACTION_DATE BETWEEN (DATE_ADD(CURRENT_TIMESTAMP, INTERVAL -2 MONTH)) AND CURRENT_TIMESTAMP) GROUP BY PERSON_ID);