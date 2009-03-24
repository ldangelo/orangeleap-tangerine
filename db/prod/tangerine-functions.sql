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
