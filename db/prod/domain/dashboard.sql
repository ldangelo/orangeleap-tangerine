INSERT INTO DASHBOARD_ITEM (DASHBOARD_ITEM_ID, DASHBOARD_ITEM_TYPE, DASHBOARD_ITEM_TITLE, URL, ITEM_ORDER, ROLES, SITE_NAME) VALUES (1, 'Pie', 'Gifts By Motivation' , NULL, 1, NULL, NULL);
INSERT INTO DASHBOARD_ITEM (DASHBOARD_ITEM_ID, DASHBOARD_ITEM_TYPE, DASHBOARD_ITEM_TITLE, URL, ITEM_ORDER, ROLES, SITE_NAME) VALUES (2, 'Pie', 'Gifts By Designation' , NULL, 2, NULL, NULL);
INSERT INTO DASHBOARD_ITEM (DASHBOARD_ITEM_ID, DASHBOARD_ITEM_TYPE, DASHBOARD_ITEM_TITLE, URL, ITEM_ORDER, ROLES, SITE_NAME) VALUES (3, 'Bar', 'Gifts Over Past Week' , NULL, 3, NULL, NULL);
INSERT INTO DASHBOARD_ITEM (DASHBOARD_ITEM_ID, DASHBOARD_ITEM_TYPE, DASHBOARD_ITEM_TITLE, URL, ITEM_ORDER, ROLES, SITE_NAME) VALUES (4, 'Area', 'Donor Trends' , NULL, 4, NULL, NULL);
INSERT INTO DASHBOARD_ITEM (DASHBOARD_ITEM_ID, DASHBOARD_ITEM_TYPE, DASHBOARD_ITEM_TITLE, URL, ITEM_ORDER, ROLES, SITE_NAME) VALUES (5, 'Rss', 'From The Orange Leap Blog' , 'http://blogs.mpowersystems.com/feed/', 5, NULL, NULL);

INSERT INTO DASHBOARD_ITEM_DATASET (DASHBOARD_ITEM_DATASET_ID, DASHBOARD_ITEM_ID, DATASET_NUM, DATASET_LABEL, SQL_TEXT) VALUES (1, 1, 1, '', 'select dl.MOTIVATION_CODE as "LABEL", @rownum:=@rownum+1 as "LABEL_VALUE", SUM(g.AMOUNT) as "DATA_VALUE" from (SELECT @rownum:=0) r, GIFT g inner join CONSTITUENT c on c.CONSTITUENT_ID = g. CONSTITUENT_ID inner join DISTRO_LINE dl on g.GIFT_ID = dl.GIFT_ID where g.DONATION_DATE between adddate(CURDATE(), -7) and adddate(CURDATE(), 1) and c.SITE_NAME = #siteName# group by LABEL ');
INSERT INTO DASHBOARD_ITEM_DATASET (DASHBOARD_ITEM_DATASET_ID, DASHBOARD_ITEM_ID, DATASET_NUM, DATASET_LABEL, SQL_TEXT) VALUES (2, 2, 1, '', 'select dl.PROJECT_CODE as "LABEL", @rownum:=@rownum+1 as "LABEL_VALUE", SUM(g.AMOUNT) as "DATA_VALUE" from (SELECT @rownum:=0) r, GIFT g inner join CONSTITUENT c on c.CONSTITUENT_ID = g. CONSTITUENT_ID inner join DISTRO_LINE dl on g.GIFT_ID = dl.GIFT_ID where g.DONATION_DATE between adddate(CURDATE(), -7) and adddate(CURDATE(), 1) and c.SITE_NAME = #siteName# group by LABEL ');
INSERT INTO DASHBOARD_ITEM_DATASET (DASHBOARD_ITEM_DATASET_ID, DASHBOARD_ITEM_ID, DATASET_NUM, DATASET_LABEL, SQL_TEXT) VALUES (3, 3, 1, 'Total Gifts ($)', 'select DATE_FORMAT(g.DONATION_DATE, "%b %e" ) as "LABEL", TO_DAYS(g.DONATION_DATE) as "LABEL_VALUE", SUM(g.AMOUNT) as "DATA_VALUE" from GIFT g inner join CONSTITUENT c on c.CONSTITUENT_ID = g.CONSTITUENT_ID where g.DONATION_DATE between adddate(CURDATE(), -7) and adddate(CURDATE(), 1) and c.SITE_NAME = #siteName# group by LABEL_VALUE ');
INSERT INTO DASHBOARD_ITEM_DATASET (DASHBOARD_ITEM_DATASET_ID, DASHBOARD_ITEM_ID, DATASET_NUM, DATASET_LABEL, SQL_TEXT) VALUES (4, 3, 2, 'Largest Gift ($)', 'select DATE_FORMAT(g.DONATION_DATE, "%b %e" ) as "LABEL", TO_DAYS(g.DONATION_DATE) as "LABEL_VALUE", MAX(g.AMOUNT) as "DATA_VALUE" from GIFT g inner join CONSTITUENT c on c.CONSTITUENT_ID = g.CONSTITUENT_ID where g.DONATION_DATE between adddate(CURDATE(), -7) and adddate(CURDATE(), 1) and c.SITE_NAME = #siteName# group by LABEL_VALUE ');
INSERT INTO DASHBOARD_ITEM_DATASET (DASHBOARD_ITEM_DATASET_ID, DASHBOARD_ITEM_ID, DATASET_NUM, DATASET_LABEL, SQL_TEXT) VALUES (5, 4, 1, 'Active Donors', 'select DATE_FORMAT(g.DONATION_DATE, "%b" ) as "LABEL", EXTRACT(YEAR_MONTH FROM g.DONATION_DATE) as "LABEL_VALUE", SUM(g.AMOUNT) as "DATA_VALUE" from GIFT g inner join CONSTITUENT c on c.CONSTITUENT_ID = g.CONSTITUENT_ID where g.DONATION_DATE between adddate(CURDATE(), -180) and adddate(CURDATE(), 1) and c.SITE_NAME = #siteName# group by LABEL_VALUE ');
INSERT INTO DASHBOARD_ITEM_DATASET (DASHBOARD_ITEM_DATASET_ID, DASHBOARD_ITEM_ID, DATASET_NUM, DATASET_LABEL, SQL_TEXT) VALUES (6, 4, 2, 'Pledge Donors', 'select DATE_FORMAT(ct.PLEDGE_DATE, "%b" ) as "LABEL", EXTRACT(YEAR_MONTH FROM ct.PLEDGE_DATE) as "LABEL_VALUE", SUM(ct.AMOUNT_TOTAL) as "DATA_VALUE" from PLEDGE ct inner join CONSTITUENT c on c.CONSTITUENT_ID = ct.CONSTITUENT_ID  where ct.PLEDGE_DATE between adddate(CURDATE(), -180) and adddate(CURDATE(), 1) and c.SITE_NAME = #siteName# group by LABEL_VALUE ');


