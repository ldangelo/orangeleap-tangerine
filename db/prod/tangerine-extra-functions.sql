-- ******************* Un-comment out the below line when running this script in mySql Query Browser ***************************************
-- DELIMITER $$

-- Create function to get an eligible mailing address.
-- Returns address id.
DROP FUNCTION IF EXISTS MAILING_ADDRESS$$

CREATE DEFINER=`root`@`localhost` FUNCTION MAILING_ADDRESS(CONSTITUENT_ID bigint(20))
    RETURNS bigint(20)
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
