-- ******************* Un-comment out the below line when running this script in mySql Query Browser ***************************************
-- DELIMITER $$

-- Create function to retrieve custom fields
DROP FUNCTION IF EXISTS ISDONORTYPE$$
CREATE FUNCTION `ISDONORTYPE`(ENTITYID INT, DONOR_TYPE VARCHAR(255))
        RETURNS BIT
BEGIN

        DECLARE CNT TINYINT;
        
        SELECT COUNT(CUSTOM_FIELD_ID) INTO CNT
                FROM CUSTOM_FIELD
                WHERE ENTITY_ID = ENTITYID
                AND ENTITY_TYPE = 'person'
                AND FIELD_NAME = 'donorProfiles'
                AND FIELD_VALUE = DONOR_TYPE;
                
        RETURN (CNT > 0);
        
END$$

-- Create function to get next key for a site
DROP FUNCTION IF EXISTS GENERATEID$$

CREATE FUNCTION GENERATEID(SITENAME VARCHAR(255))
    RETURNS VARCHAR(255)
BEGIN
    DECLARE CUSTOMID VARCHAR(255);

    IF (SELECT COUNT(SITE_NAME) FROM CUSTOM_ID WHERE SITE_NAME = SITENAME) = 0 THEN
    	INSERT CUSTOM_ID(SITE_NAME) VALUES(SITENAME);
    END IF;


    SELECT CONCAT(SITE_PREFIX,LPAD(CAST(NEXT_KEY as CHAR),6, '0')) INTO CUSTOMID
    FROM CUSTOM_ID
    WHERE SITE_NAME=SITENAME;

    UPDATE CUSTOM_ID SET NEXT_KEY = NEXT_KEY+1 WHERE SITE_NAME=SITENAME;

    RETURN CUSTOMID;

END$$
