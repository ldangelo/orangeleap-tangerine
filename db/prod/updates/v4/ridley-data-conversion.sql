/* TANGERINE-1227 */

UPDATE PICKLIST set PICKLIST_NAME = 'customFieldMap[OtherPayMethod]' where PICKLIST_NAME_ID = 'customFieldMap[OtherPayMethod]' and ENTITY_TYPE = 'gift' and PICKLIST_NAME = 'otherPayMethod';

INSERT INTO FIELD_DEFINITION (FIELD_DEFINITION_ID, DEFAULT_LABEL, ENTITY_ATTRIBUTES, ENTITY_TYPE, FIELD_INFO, FIELD_NAME, FIELD_TYPE, REFERENCE_TYPE, SITE_NAME) values ('ridleycollege-gift.customFieldMap[OtherPayMethodReadOnly]', 'Other Payment Method', 'otherPayMethod', 'gift', NULL, 'customFieldMap[OtherPayMethod]', 'PICKLIST_DISPLAY', NULL, 'ridleycollege');

INSERT INTO `SECTION_FIELD` (FIELD_ORDER,FIELD_DEFINITION_ID,SECTION_DEFINITION_ID,SITE_NAME)SELECT '1200', 'ridleycollege-gift.customFieldMap[OtherPayMethodReadOnly]', SECTION_DEFINITION_ID, 'ridleycollege' FROM SECTION_DEFINITION WHERE SECTION_NAME='gift.payment' AND PAGE_TYPE='giftView';





/* TANGERINE-1222 */

UPDATE FIELD_DEFINITION set ENTITY_ATTRIBUTES = 'paymentAmt' where FIELD_DEFINITION_ID LIKE 'ridleycollege-pledge.customFieldMap[paymentAmt%]';

UPDATE FIELD_DEFINITION set ENTITY_ATTRIBUTES = 'paymentDate' where FIELD_DEFINITION_ID LIKE 'ridleycollege-pledge.customFieldMap[paymentDate%]';

UPDATE PICKLIST_ITEM set REFERENCE_VALUE = 'li:has(.ea-paymentAmt),li:has(.ea-paymentDate)' where ITEM_NAME = 'Yes' and PICKLIST_ID = (SELECT PICKLIST_ID from PICKLIST where PICKLIST_NAME_ID = 'customFieldMap[schedulePayments]');