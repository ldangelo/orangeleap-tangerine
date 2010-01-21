-- ******************* Un-comment out the below line when running this script in mySql Query Browser ***************************************
-- DELIMITER $$

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
	  ELSE
	  	  select OPTION_VALUE from SITE_OPTION WHERE OPTION_NAME = 'lapsed.donor.months' INTO cutoffAmt;
		  IF cutoffAmt is not null THEN
			  SET cutOffDate = DATE_SUB(now(), INTERVAL cutoffAmt MONTH); 
	  	  END IF;
	  END IF;
	  
	  IF cutOffDate is NULL THEN
	      RETURN 1;
	  END IF;
	  
	
	  OPEN cur1;
	
	  REPEAT
	    FETCH cur1 INTO constituentId, recentGift;
	    IF NOT done THEN
	       select count(*) INTO cnt from GIFT g where g.CONSTITUENT_ID = constituentId and g.DONATION_DATE >= cutOffDate;
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


