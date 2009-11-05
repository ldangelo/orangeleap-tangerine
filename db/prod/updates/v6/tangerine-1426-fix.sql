SET AUTOCOMMIT = 0;


UPDATE PICKLIST SET PICKLIST_DESC = 'Adjusted Gift Payment Type' WHERE PICKLIST_NAME_ID = 'adjustedGift.paymentType' AND PICKLIST_DESC = 'Gift Payment Type';

COMMIT;
