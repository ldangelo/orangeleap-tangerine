
INSERT INTO ROLLUP_ATTRIBUTE (ROLLUP_ATTRIBUTE_ID, ATTRIBUTE_DESC, ROLLUP_ENTITY_TYPE, ROLLUP_STAT_TYPE, FIELD_NAME, CUSTOM_FIELD_NAME, SITE_NAME) VALUES (1000001, 'Gift Amount', 'constituent' , 'ROLLUP_GIFT_BY_CONSTITUENT', 'ADJUSTED_AMOUNT', NULL, NULL);
INSERT INTO ROLLUP_ATTRIBUTE (ROLLUP_ATTRIBUTE_ID, ATTRIBUTE_DESC, ROLLUP_ENTITY_TYPE, ROLLUP_STAT_TYPE, FIELD_NAME, CUSTOM_FIELD_NAME, SITE_NAME) VALUES (1000002, 'Gift In Kind Amount', 'constituent', 'ROLLUP_GIFT_IN_KIND_BY_CONSTITUENT', 'FAIR_MARKET_VALUE', NULL, NULL);
INSERT INTO ROLLUP_ATTRIBUTE (ROLLUP_ATTRIBUTE_ID, ATTRIBUTE_DESC, ROLLUP_ENTITY_TYPE, ROLLUP_STAT_TYPE, FIELD_NAME, CUSTOM_FIELD_NAME, SITE_NAME) VALUES (1000003, 'Soft Gift Amount', 'constituent', 'ROLLUP_GIFT_DISTRO_LINE_AMOUNT_BY_CF_CONSTITUENT', NULL, 'onBehalfOf', NULL);

INSERT INTO ROLLUP_ATTRIBUTE (ROLLUP_ATTRIBUTE_ID, ATTRIBUTE_DESC, ROLLUP_ENTITY_TYPE, ROLLUP_STAT_TYPE, FIELD_NAME, CUSTOM_FIELD_NAME, SITE_NAME) VALUES (1000005, 'Projected Pledge Amount', 'constituent', 'ROLLUP_PLEDGE_SCHEDULED_PAYMENTS_BY_CONSTITUENT', NULL, NULL, NULL);
INSERT INTO ROLLUP_ATTRIBUTE (ROLLUP_ATTRIBUTE_ID, ATTRIBUTE_DESC, ROLLUP_ENTITY_TYPE, ROLLUP_STAT_TYPE, FIELD_NAME, CUSTOM_FIELD_NAME, SITE_NAME) VALUES (1000006, 'Projected Recurring Gift Amount', 'constituent', 'ROLLUP_RECURRING_GIFT_SCHEDULED_PAYMENTS_BY_CONSTITUENT', NULL, NULL, NULL);

INSERT INTO ROLLUP_ATTRIBUTE (ROLLUP_ATTRIBUTE_ID, ATTRIBUTE_DESC, ROLLUP_ENTITY_TYPE, ROLLUP_STAT_TYPE, FIELD_NAME, CUSTOM_FIELD_NAME, SITE_NAME) VALUES (1000101, 'Projected Pledge Amount', 'summary', 'ROLLUP_PLEDGE_SCHEDULED_PAYMENTS_BY_ALL', NULL, NULL, NULL);
INSERT INTO ROLLUP_ATTRIBUTE (ROLLUP_ATTRIBUTE_ID, ATTRIBUTE_DESC, ROLLUP_ENTITY_TYPE, ROLLUP_STAT_TYPE, FIELD_NAME, CUSTOM_FIELD_NAME, SITE_NAME) VALUES (1000102, 'Projected Recurring Gift Amount', 'summary', 'ROLLUP_RECURRING_GIFT_SCHEDULED_PAYMENTS_BY_ALL', NULL, NULL, NULL);


-- Gifts, Adjusted Gifts, Gifts In Kind, Soft Gifts
INSERT INTO ROLLUP_SERIES (ROLLUP_SERIES_ID, SERIES_DESC, SERIES_TYPE, MAINTAIN_PERIODS, FUTURE_PERIODS, KEEP_UNMAINTAINED, SITE_NAME) VALUES (2000001, 'Calendar Year', 'CALENDAR_YEAR', 5, 0, FALSE, NULL);
INSERT INTO ROLLUP_SERIES_X_ATTRIBUTE (ROLLUP_SERIES_ID, ROLLUP_ATTRIBUTE_ID) VALUES (2000001, 1000001);
INSERT INTO ROLLUP_SERIES_X_ATTRIBUTE (ROLLUP_SERIES_ID, ROLLUP_ATTRIBUTE_ID) VALUES (2000001, 1000002);
INSERT INTO ROLLUP_SERIES_X_ATTRIBUTE (ROLLUP_SERIES_ID, ROLLUP_ATTRIBUTE_ID) VALUES (2000001, 1000003);
INSERT INTO ROLLUP_SERIES (ROLLUP_SERIES_ID, SERIES_DESC, SERIES_TYPE, MAINTAIN_PERIODS, FUTURE_PERIODS, KEEP_UNMAINTAINED, SITE_NAME) VALUES (2000002, 'Fiscal Year', 'FISCAL_YEAR', 5, 0, FALSE, NULL);
INSERT INTO ROLLUP_SERIES_X_ATTRIBUTE (ROLLUP_SERIES_ID, ROLLUP_ATTRIBUTE_ID) VALUES (2000002, 1000001);
INSERT INTO ROLLUP_SERIES_X_ATTRIBUTE (ROLLUP_SERIES_ID, ROLLUP_ATTRIBUTE_ID) VALUES (2000002, 1000002);
INSERT INTO ROLLUP_SERIES_X_ATTRIBUTE (ROLLUP_SERIES_ID, ROLLUP_ATTRIBUTE_ID) VALUES (2000002, 1000003);
INSERT INTO ROLLUP_SERIES (ROLLUP_SERIES_ID, SERIES_DESC, SERIES_TYPE, MAINTAIN_PERIODS, FUTURE_PERIODS, KEEP_UNMAINTAINED, SITE_NAME) VALUES (2000003, 'Total Lifetime', 'ALLTIME', 1, 0, FALSE, NULL);
INSERT INTO ROLLUP_SERIES_X_ATTRIBUTE (ROLLUP_SERIES_ID, ROLLUP_ATTRIBUTE_ID) VALUES (2000003, 1000001);
INSERT INTO ROLLUP_SERIES_X_ATTRIBUTE (ROLLUP_SERIES_ID, ROLLUP_ATTRIBUTE_ID) VALUES (2000003, 1000002);
INSERT INTO ROLLUP_SERIES_X_ATTRIBUTE (ROLLUP_SERIES_ID, ROLLUP_ATTRIBUTE_ID) VALUES (2000003, 1000003);

-- Scheduled Payments by constituent
INSERT INTO ROLLUP_SERIES (ROLLUP_SERIES_ID, SERIES_DESC, SERIES_TYPE, MAINTAIN_PERIODS, FUTURE_PERIODS, KEEP_UNMAINTAINED, SITE_NAME) VALUES (2000004, 'Calendar Year', 'CALENDAR_YEAR', 2, 1, FALSE, NULL);
INSERT INTO ROLLUP_SERIES_X_ATTRIBUTE (ROLLUP_SERIES_ID, ROLLUP_ATTRIBUTE_ID) VALUES (2000004, 1000005);
INSERT INTO ROLLUP_SERIES_X_ATTRIBUTE (ROLLUP_SERIES_ID, ROLLUP_ATTRIBUTE_ID) VALUES (2000004, 1000006);
INSERT INTO ROLLUP_SERIES (ROLLUP_SERIES_ID, SERIES_DESC, SERIES_TYPE, MAINTAIN_PERIODS, FUTURE_PERIODS, KEEP_UNMAINTAINED, SITE_NAME) VALUES (2000005, 'Fiscal Year', 'FISCAL_YEAR', 2, 1, FALSE, NULL);
INSERT INTO ROLLUP_SERIES_X_ATTRIBUTE (ROLLUP_SERIES_ID, ROLLUP_ATTRIBUTE_ID) VALUES (2000005, 1000005);
INSERT INTO ROLLUP_SERIES_X_ATTRIBUTE (ROLLUP_SERIES_ID, ROLLUP_ATTRIBUTE_ID) VALUES (2000005, 1000006);

-- All Scheduled Payments
INSERT INTO ROLLUP_SERIES (ROLLUP_SERIES_ID, SERIES_DESC, SERIES_TYPE, MAINTAIN_PERIODS, FUTURE_PERIODS, KEEP_UNMAINTAINED, SITE_NAME) VALUES (2000006, 'Monthly Total', 'MONTH', 12, 11, FALSE, NULL);
INSERT INTO ROLLUP_SERIES_X_ATTRIBUTE (ROLLUP_SERIES_ID, ROLLUP_ATTRIBUTE_ID) VALUES (2000006, 1000101);
INSERT INTO ROLLUP_SERIES_X_ATTRIBUTE (ROLLUP_SERIES_ID, ROLLUP_ATTRIBUTE_ID) VALUES (2000006, 1000102);

-- Monthly history by constituent
INSERT INTO ROLLUP_SERIES (ROLLUP_SERIES_ID, SERIES_DESC, SERIES_TYPE, MAINTAIN_PERIODS, FUTURE_PERIODS, KEEP_UNMAINTAINED, SITE_NAME) VALUES (2000007, 'Monthly Total', 'MONTH', 13, 0, FALSE, NULL);
INSERT INTO ROLLUP_SERIES_X_ATTRIBUTE (ROLLUP_SERIES_ID, ROLLUP_ATTRIBUTE_ID) VALUES (2000007, 1000001);
INSERT INTO ROLLUP_SERIES (ROLLUP_SERIES_ID, SERIES_DESC, SERIES_TYPE, MAINTAIN_PERIODS, FUTURE_PERIODS, KEEP_UNMAINTAINED, SITE_NAME) VALUES (2000008, 'Past Year Total', 'ROLLING_YEAR', 1, 0, FALSE, NULL);
INSERT INTO ROLLUP_SERIES_X_ATTRIBUTE (ROLLUP_SERIES_ID, ROLLUP_ATTRIBUTE_ID) VALUES (2000008, 1000001);


