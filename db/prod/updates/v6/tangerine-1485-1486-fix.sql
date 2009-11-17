SET AUTOCOMMIT = 0;

DELETE FROM PICKLIST_ITEM WHERE ITEM_NAME IN ('ROLE_SUPER_ADMIN', 'ROLE_ADMIN', 'ROLE_USER') and PICKLIST_ID NOT IN (SELECT PICKLIST_ID FROM PICKLIST WHERE PICKLIST_NAME_ID = 'screenDefinitionRole');


INSERT INTO PICKLIST (PICKLIST_ID, PICKLIST_NAME_ID, PICKLIST_NAME, PICKLIST_DESC)
SELECT @rownum:=@rownum+1,S.PICKLIST_NAME_ID,S.PICKLIST_NAME,S.PICKLIST_DESC
    FROM (SELECT 'screenDefinitionRole' AS 'PICKLIST_NAME_ID', 'screenDefinitionRole' AS 'PICKLIST_NAME' , 'Screen Definition Role' AS 'PICKLIST_DESC') S LEFT JOIN PICKLIST P
        ON S.PICKLIST_NAME_ID=P.PICKLIST_NAME
        AND S.PICKLIST_NAME=P.PICKLIST_NAME
        AND S.PICKLIST_DESC=P.PICKLIST_DESC
    JOIN (SELECT @RowNum:=(SELECT MAX(PICKLIST_ID)
                  FROM  PICKLIST))R
    WHERE P.PICKLIST_ID IS NULL;

INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, ITEM_ORDER)
SELECT (SELECT PICKLIST_ID FROM PICKLIST WHERE PICKLIST_NAME_ID='screenDefinitionRole'),I.ITEM_NAME,I.DEFAULT_DISPLAY_VALUE,I.ITEM_ORDER
    FROM (SELECT 'ROLE_SUPER_ADMIN' AS 'ITEM_NAME','ROLE_SUPER_ADMIN' AS 'DEFAULT_DISPLAY_VALUE',1 AS 'ITEM_ORDER')I LEFT JOIN (SELECT *
                                                        FROM PICKLIST_ITEM
                                                        WHERE PICKLIST_ID=(SELECT PICKLIST_ID
                                                                    FROM PICKLIST
                                                                    WHERE PICKLIST_NAME_ID='screenDefinitionRole')
                                                        AND ITEM_NAME='ROLE_SUPER_ADMIN') P
        ON I.ITEM_NAME=P.ITEM_NAME
    WHERE P.PICKLIST_ITEM_ID IS NULL;

INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, ITEM_ORDER)
SELECT (SELECT PICKLIST_ID FROM PICKLIST WHERE PICKLIST_NAME_ID='screenDefinitionRole'),I.ITEM_NAME,I.DEFAULT_DISPLAY_VALUE,I.ITEM_ORDER
    FROM (SELECT 'ROLE_ADMIN' AS 'ITEM_NAME','ROLE_ADMIN' AS 'DEFAULT_DISPLAY_VALUE',2 AS 'ITEM_ORDER')I LEFT JOIN (SELECT *
                                                        FROM PICKLIST_ITEM
                                                        WHERE PICKLIST_ID=(SELECT PICKLIST_ID
                                                                    FROM PICKLIST
                                                                    WHERE PICKLIST_NAME_ID='screenDefinitionRole')
                                                        AND ITEM_NAME='ROLE_ADMIN') P
        ON I.ITEM_NAME=P.ITEM_NAME
    WHERE P.PICKLIST_ITEM_ID IS NULL;

INSERT INTO PICKLIST_ITEM (PICKLIST_ID, ITEM_NAME, DEFAULT_DISPLAY_VALUE, ITEM_ORDER)
SELECT (SELECT PICKLIST_ID FROM PICKLIST WHERE PICKLIST_NAME_ID='screenDefinitionRole'),I.ITEM_NAME,I.DEFAULT_DISPLAY_VALUE,I.ITEM_ORDER
    FROM (SELECT 'ROLE_USER' AS 'ITEM_NAME','ROLE_USER' AS 'DEFAULT_DISPLAY_VALUE',3 AS 'ITEM_ORDER')I LEFT JOIN (SELECT *
                                                        FROM PICKLIST_ITEM
                                                        WHERE PICKLIST_ID=(SELECT PICKLIST_ID
                                                                    FROM PICKLIST
                                                                    WHERE PICKLIST_NAME_ID='screenDefinitionRole')
                                                        AND ITEM_NAME='ROLE_USER') P
        ON I.ITEM_NAME=P.ITEM_NAME
    WHERE P.PICKLIST_ITEM_ID IS NULL;

COMMIT;