SET AUTOCOMMIT = 0;


SELECT count(sf.FIELD_DEFINITION_ID) AS REC_COUNT, 2 AS EXPECTED_COUNT
FROM SECTION_FIELD sf
WHERE sf.SECTION_DEFINITION_ID IN (SELECT SECTION_DEFINITION_ID FROM SECTION_DEFINITION WHERE PAGE_TYPE IN ('gift', 'giftView', 'adjustedGift', 'adjustedGiftView'))
AND sf.SITE_NAME IS NOT NULL;



INSERT INTO SECTION_FIELD (SECTION_DEFINITION_ID, FIELD_DEFINITION_ID, FIELD_ORDER, SITE_NAME)
SELECT SECTION_DEFINITION_ID, 'afa-gift.customFieldMap[OtherPayMethodReadOnly]', 1200, 'afa' FROM SECTION_DEFINITION WHERE SECTION_NAME='gift.payment' AND PAGE_TYPE='giftPaid';

COMMIT;
