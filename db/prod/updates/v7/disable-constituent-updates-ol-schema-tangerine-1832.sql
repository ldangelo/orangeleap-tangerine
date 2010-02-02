-- Script to disable the orangeleap schema for constituent updates

-- do not run on client databases!!!!

USE orangeleap;

-- drop CONSTITUENT_TYPE column

ALTER TABLE CONSTITUENT DROP COLUMN CONSTITUENT_TYPE; 

COMMIT;
