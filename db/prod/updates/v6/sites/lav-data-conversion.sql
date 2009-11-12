SET AUTOCOMMIT = 0;

-- No actual inserts unless someone added custom fields recently; just a check below
SELECT count(sf.FIELD_DEFINITION_ID) AS REC_COUNT, 0 AS EXPECTED_COUNT
FROM SECTION_FIELD sf
WHERE sf.SECTION_DEFINITION_ID IN (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE PAGE_TYPE IN ('gift', 'giftView', 'adjustedGift', 'adjustedGiftView'))
AND sf.SITE_NAME IS NOT NULL;



COMMIT;
