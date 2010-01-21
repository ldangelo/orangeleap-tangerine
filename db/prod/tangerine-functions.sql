-- ******************* Un-comment out the below line when running this script in mySql Query Browser ***************************************
-- DELIMITER $$


-- CREATE DEFINER=`root`@`localhost` FUNCTION to set 4-digit year on date.
DROP FUNCTION IF EXISTS SET_YEAR$$

CREATE DEFINER=`root`@`localhost` FUNCTION SET_YEAR(ADATE datetime, AYEAR int)
    RETURNS datetime  DETERMINISTIC
BEGIN

	return STR_TO_DATE(  CONCAT( AYEAR ,  '-' , DATE_FORMAT( ADATE, '%m-%d' ) ) , '%Y-%m-%d' );

END
$$

-- CREATE DEFINER=`root`@`localhost` FUNCTION to determine if as-of date is in the seasonal range determined by start and end date.
-- All years are ignored so the start date can be after the end date.
DROP FUNCTION IF EXISTS IS_IN_SEASON$$

CREATE DEFINER=`root`@`localhost` FUNCTION IS_IN_SEASON(AS_OF_DATE datetime, START_DATE datetime, END_DATE datetime)
    RETURNS BIT  DETERMINISTIC
BEGIN

    DECLARE Y INT;
    DECLARE AS_OF_DOY INT;
    DECLARE START_DOY INT;
    DECLARE END_DOY INT;

    -- Strip times for all dates
    SET AS_OF_DATE = DATE(AS_OF_DATE);
    SET START_DATE = DATE(START_DATE);
    SET END_DATE = DATE(END_DATE);

	-- Set year to current year on all dates
    SET Y = YEAR(NOW());
	SET AS_OF_DATE = SET_YEAR(AS_OF_DATE, Y);
	SET START_DATE = SET_YEAR(START_DATE, Y);
	SET END_DATE = SET_YEAR(END_DATE, Y);

	-- Check if start date is before or after end date
	SET AS_OF_DOY = DAYOFYEAR(AS_OF_DATE);
	SET START_DOY = DAYOFYEAR(START_DATE);
	SET END_DOY = DAYOFYEAR(END_DATE);

    -- If start and end dates are the same, it is only in season on that date
	if START_DOY = END_DOY then
	    return ( AS_OF_DOY = START_DOY );
	end if;

	if START_DOY < END_DOY then
	    -- Season doesn't wrap year
        return ( AS_OF_DOY >= START_DOY and AS_OF_DOY <= END_DOY );
	else
        -- Season does wrap year
        return ( AS_OF_DOY <= END_DOY or AS_OF_DOY >= START_DOY );
	end if;


END
$$


-- CREATE DEFINER=`root`@`localhost` FUNCTION to retrieve custom fields
DROP FUNCTION IF EXISTS ISDONORTYPE$$
CREATE DEFINER=`root`@`localhost` FUNCTION `ISDONORTYPE`(ENTITYID INT, DONOR_TYPE VARCHAR(255))
        RETURNS BIT
BEGIN

        DECLARE CNT TINYINT;
        
        SELECT COUNT(CUSTOM_FIELD_ID) INTO CNT
                FROM CUSTOM_FIELD
                WHERE ENTITY_ID = ENTITYID
                AND ENTITY_TYPE = 'constituent'
                AND FIELD_NAME = 'donorProfiles'
                AND FIELD_VALUE = DONOR_TYPE;
                
        RETURN (CNT > 0);
        
END$$

-- CREATE DEFINER=`root`@`localhost` FUNCTION to get next key for a site
DROP FUNCTION IF EXISTS GENERATEID$$

CREATE DEFINER=`root`@`localhost` FUNCTION GENERATEID(SITENAME VARCHAR(255))
    RETURNS VARCHAR(255)
BEGIN
    DECLARE CUSTOMID VARCHAR(255);

    IF (SELECT COUNT(SITE_NAME) FROM CUSTOM_ID WHERE SITE_NAME = SITENAME) = 0 THEN
    	INSERT CUSTOM_ID(SITE_NAME) VALUES(SITENAME);
    END IF;

    -- Changed per TANGERINE-596; no more prefix or padding with zeros
    -- SELECT CONCAT(SITE_PREFIX,LPAD(CAST(NEXT_KEY as CHAR),6, '0')) INTO CUSTOMID
    SELECT CAST(NEXT_KEY as CHAR) INTO CUSTOMID
    FROM CUSTOM_ID
    WHERE SITE_NAME=SITENAME;

    UPDATE CUSTOM_ID SET NEXT_KEY = NEXT_KEY+1 WHERE SITE_NAME=SITENAME;

    IF CUSTOMID = null THEN SET CUSTOMID = '9999999';
    END IF;

    RETURN CUSTOMID;

END$$

-- Call GENERATEID if inserting a null ACCOUNT_NUMBER
DROP TRIGGER IF EXISTS INSERT_CONSTITUENT_GENERATEID$$

CREATE DEFINER=`root`@`localhost` TRIGGER INSERT_CONSTITUENT_GENERATEID 
BEFORE INSERT ON `CONSTITUENT` 
FOR EACH ROW
BEGIN
	
  IF NEW.ACCOUNT_NUMBER is null THEN SET NEW.ACCOUNT_NUMBER = GENERATEID(NEW.SITE_NAME);
  END IF;

END$$


DROP TRIGGER IF EXISTS INSERT_CUSTOM_FIELD_NUMERIC_AND_DATE$$

CREATE DEFINER=`root`@`localhost` TRIGGER INSERT_CUSTOM_FIELD_NUMERIC_AND_DATE 
BEFORE INSERT ON `CUSTOM_FIELD` 
FOR EACH ROW
BEGIN

  IF NEW.FIELD_VALUE RLIKE '^[0-9]*\.?[0-9]{0,2}$' THEN SET NEW.FIELD_NUMERIC_VALUE = CONVERT(NEW.FIELD_VALUE, decimal(19,2));
  ELSE SET NEW.FIELD_NUMERIC_VALUE = null;
  END IF;
  
  -- CUSTOM_FIELD stores dates as mm/dd/yyyy
  -- STR_TO_DATE will set to NULL if invalid date
  IF NEW.FIELD_VALUE RLIKE '^[0-9]{2}\/[0-9]{2}\/[0-9]{4}$' THEN SET NEW.FIELD_DATE_VALUE = STR_TO_DATE(NEW.FIELD_VALUE, '%m/%d/%Y'); 
  ELSE SET NEW.FIELD_DATE_VALUE = null;
  END IF;
  
END$$

DROP TRIGGER IF EXISTS UPDATE_CUSTOM_FIELD_NUMERIC_AND_DATE$$

CREATE DEFINER=`root`@`localhost` TRIGGER UPDATE_CUSTOM_FIELD_NUMERIC_AND_DATE 
BEFORE UPDATE ON `CUSTOM_FIELD` 
FOR EACH ROW
BEGIN

  IF NEW.FIELD_VALUE RLIKE '^[0-9]*\.?[0-9]{0,2}$' THEN SET NEW.FIELD_NUMERIC_VALUE = CONVERT(NEW.FIELD_VALUE, decimal(19,2));
  ELSE SET NEW.FIELD_NUMERIC_VALUE = null;
  END IF;
  
  -- CUSTOM_FIELD stores dates as mm/dd/yyyy
  -- STR_TO_DATE will set to NULL if invalid date
  IF NEW.FIELD_VALUE RLIKE '^[0-9]{2}\/[0-9]{2}\/[0-9]{4}$' THEN SET NEW.FIELD_DATE_VALUE = STR_TO_DATE(NEW.FIELD_VALUE, '%m/%d/%Y'); 
  ELSE SET NEW.FIELD_DATE_VALUE = null;
  END IF;
  
END$$

-- Create function to get an eligible mailing address.
-- Returns address id.
DROP FUNCTION IF EXISTS MAILING_ADDRESS$$

CREATE DEFINER=`root`@`localhost` FUNCTION MAILING_ADDRESS(CONSTITUENT_ID bigint(20))
    RETURNS bigint(20)
	DETERMINISTIC
BEGIN

	DECLARE result bigint(20);
	SET result = null;
	
	
	SELECT ADDRESS_ID into result 
	FROM ADDRESS a 
	where 
	a.CONSTITUENT_ID = CONSTITUENT_ID 
	and a.UNDELIVERABLE = '0' 
	and a.INACTIVE= '0' 
	and a.RECEIVE_CORRESPONDENCE = '1' 
	and a.ACTIVATION_STATUS = 'temporary' 
	and a.TEMPORARY_START_DATE <= now() 
	and a.TEMPORARY_END_DATE >= now() 
	order by a.IS_PRIMARY desc, a.UPDATE_DATE desc LIMIT 1;

	if result != NULL then return result;
	end if;

	SELECT ADDRESS_ID into result
	FROM ADDRESS a 
	where
	a.CONSTITUENT_ID = CONSTITUENT_ID
	and a.UNDELIVERABLE = '0' 
	and a.INACTIVE= '0' 
	and a.RECEIVE_CORRESPONDENCE = '1' 
	and a.ACTIVATION_STATUS = 'seasonal'
	and IS_IN_SEASON(now(), SEASONAL_START_DATE, SEASONAL_END_DATE) 
	order by a.IS_PRIMARY desc, a.UPDATE_DATE desc LIMIT 1;

	if result is not NULL then return result;
	end if;

	SELECT ADDRESS_ID into result
	FROM ADDRESS a 
	where
	a.CONSTITUENT_ID = CONSTITUENT_ID
	and a.UNDELIVERABLE = '0' 
	and a.INACTIVE= '0' 
	and a.RECEIVE_CORRESPONDENCE = '1' 
	and a.ACTIVATION_STATUS = 'permanent'
	and a.EFFECTIVE_DATE <= now() 
	order by a.IS_PRIMARY desc, a.UPDATE_DATE desc LIMIT 1;

	if result is not NULL then return result;
	end if;
  
	
	return result;

END$$


-- Mass update constituent time-dependent flags
DROP FUNCTION IF EXISTS SET_CONSTITUENT_FLAGS$$
CREATE DEFINER=`root`@`localhost` FUNCTION `SET_CONSTITUENT_FLAGS`()
        RETURNS int
BEGIN

        -- Set or unset flags for items that can change due to passage of time without any transactions occurring, based on site options.
        -- Update CONSTITUENT.UPDATE_DATE for any changed records to allow nightly rules to fire on those records.

	
	  DECLARE cutOffDate datetime default NULL;
	  DECLARE done INT DEFAULT 0;
	  DECLARE cutoffAmt VARCHAR(255) default NULL;
	  DECLARE constituentId, cnt INT;
	  DECLARE recentGift char(1);
	  DECLARE cur1 CURSOR FOR SELECT CONSTITUENT_ID, RECENT_GIFT FROM CONSTITUENT;
	  DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;
	  
	  select OPTION_VALUE from SITE_OPTION WHERE OPTION_NAME = 'lapsed.donor.days' INTO cutoffAmt;
	  IF cutoffAmt is not null THEN
		  SET cutOffDate = DATE_SUB(now(), INTERVAL cutoffAmt DAY); 
	  END IF;
	  select OPTION_VALUE from SITE_OPTION WHERE OPTION_NAME = 'lapsed.donor.months' INTO cutoffAmt;
	  IF cutoffAmt is not null THEN
		  SET cutOffDate = DATE_SUB(now(), INTERVAL cutoffAmt MONTH); 
	  END IF;
	  
	  IF cutOffDate is NULL THEN
	      RETURN 1;
	  END IF;
	  
	
	  OPEN cur1;
	
	  REPEAT
	    FETCH cur1 INTO constituentId, recentGift;
	    IF NOT done THEN
	       select count(*) INTO cnt from GIFT g where g.CONSTITUENT_ID = id and g.DONATION_DATE >= cutOffDate;
	       IF cnt > 0 THEN
	          IF recentGift = '0' THEN
		          UPDATE CONSTITUENT set RECENT_GIFT = '1', UPDATE_DATE = now() where CONSTITUENT_ID = constituentId;
		      END IF;
	       ELSE
	          IF recentGift = '1' THEN
		          UPDATE CONSTITUENT set RECENT_GIFT = '0', UPDATE_DATE = now() where CONSTITUENT_ID = constituentId;
		      END IF;
	       END IF;
	    END IF;
	  UNTIL done END REPEAT;
	
	  CLOSE cur1;
	
	
      RETURN 0;
        
END$$

