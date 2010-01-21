-- ******************* Un-comment out the below line when running this script in mySql Query Browser ***************************************
-- DELIMITER $$

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



