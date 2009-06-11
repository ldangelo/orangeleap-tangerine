-- ******************* Un-comment out the below line when running this script in mySql Query Browser ***************************************
-- DELIMITER $$

-- Create function to set 4-digit year on date.
DROP FUNCTION IF EXISTS SET_YEAR$$

CREATE FUNCTION SET_YEAR(ADATE datetime, AYEAR int)
    RETURNS datetime
BEGIN

	return STR_TO_DATE(  CONCAT( AYEAR ,  '-' , DATE_FORMAT( ADATE, '%m-%d' ) ) , '%Y-%m-%d' );

END
$$

-- Create function to determine if as-of date is in the seasonal range determined by start and end date.
-- All years are ignored so the start date can be after the end date.
DROP FUNCTION IF EXISTS IS_IN_SEASON$$

CREATE FUNCTION IS_IN_SEASON(AS_OF_DATE datetime, START_DATE datetime, END_DATE datetime)
    RETURNS BIT
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
