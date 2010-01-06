-- NAME_IDs for ATTRIBUTES and SERIES can be used by code to reference the specific attributes/series. ATTRIBUTE/SERIES_DESC are the labels seen by the user in the UI.

INSERT INTO ROLLUP_ATTRIBUTE (ROLLUP_ATTRIBUTE_ID, ATTRIBUTE_NAME_ID, ATTRIBUTE_DESC, ROLLUP_ENTITY_TYPE, ROLLUP_STAT_TYPE, FIELD_NAME, CUSTOM_FIELD_NAME, SITE_NAME) VALUES (1000001, 'Gift Amount', 'Gift Amount', 'constituent' , 'ROLLUP_GIFT_BY_CONSTITUENT', 'ADJUSTED_AMOUNT', NULL, NULL);
INSERT INTO ROLLUP_ATTRIBUTE (ROLLUP_ATTRIBUTE_ID, ATTRIBUTE_NAME_ID, ATTRIBUTE_DESC, ROLLUP_ENTITY_TYPE, ROLLUP_STAT_TYPE, FIELD_NAME, CUSTOM_FIELD_NAME, SITE_NAME) VALUES (1000002, 'Gift In Kind Amount', 'Gift In Kind Amount', 'constituent', 'ROLLUP_GIFT_IN_KIND_BY_CONSTITUENT', 'FAIR_MARKET_VALUE', NULL, NULL);
INSERT INTO ROLLUP_ATTRIBUTE (ROLLUP_ATTRIBUTE_ID, ATTRIBUTE_NAME_ID, ATTRIBUTE_DESC, ROLLUP_ENTITY_TYPE, ROLLUP_STAT_TYPE, FIELD_NAME, CUSTOM_FIELD_NAME, SITE_NAME) VALUES (1000003, 'Soft Gift Amount', 'Soft Gift Amount', 'constituent', 'ROLLUP_GIFT_DISTRO_LINE_AMOUNT_BY_CF_CONSTITUENT', NULL, 'onBehalfOf', NULL);

INSERT INTO ROLLUP_ATTRIBUTE (ROLLUP_ATTRIBUTE_ID, ATTRIBUTE_NAME_ID, ATTRIBUTE_DESC, ROLLUP_ENTITY_TYPE, ROLLUP_STAT_TYPE, FIELD_NAME, CUSTOM_FIELD_NAME, SITE_NAME) VALUES (1000005, 'Projected Pledge Amount', 'Projected Pledge Amount', 'constituent', 'ROLLUP_PLEDGE_SCHEDULED_PAYMENTS_BY_CONSTITUENT', NULL, NULL, NULL);
INSERT INTO ROLLUP_ATTRIBUTE (ROLLUP_ATTRIBUTE_ID, ATTRIBUTE_NAME_ID, ATTRIBUTE_DESC, ROLLUP_ENTITY_TYPE, ROLLUP_STAT_TYPE, FIELD_NAME, CUSTOM_FIELD_NAME, SITE_NAME) VALUES (1000006, 'Projected Recurring Gift Amount', 'Projected Recurring Gift Amount', 'constituent', 'ROLLUP_RECURRING_GIFT_SCHEDULED_PAYMENTS_BY_CONSTITUENT', NULL, NULL, NULL);

-- Second-level rollups by constituent
INSERT INTO ROLLUP_ATTRIBUTE (ROLLUP_ATTRIBUTE_ID, ATTRIBUTE_NAME_ID, ATTRIBUTE_DESC, ROLLUP_ENTITY_TYPE, ROLLUP_STAT_TYPE, FIELD_NAME, CUSTOM_FIELD_NAME, SITE_NAME) VALUES (1000101, 'All Actuals', 'All Actuals', 'constituent', 'ROLLUP_ACTUALS_BY_CONSTITUENT', NULL, NULL, NULL);
INSERT INTO ROLLUP_ATTRIBUTE (ROLLUP_ATTRIBUTE_ID, ATTRIBUTE_NAME_ID, ATTRIBUTE_DESC, ROLLUP_ENTITY_TYPE, ROLLUP_STAT_TYPE, FIELD_NAME, CUSTOM_FIELD_NAME, SITE_NAME) VALUES (1000102, 'All Projected', 'All Projected', 'constituent', 'ROLLUP_PROJECTEDS_BY_CONSTITUENT', NULL, NULL, NULL);


INSERT INTO ROLLUP_ATTRIBUTE (ROLLUP_ATTRIBUTE_ID, ATTRIBUTE_NAME_ID, ATTRIBUTE_DESC, ROLLUP_ENTITY_TYPE, ROLLUP_STAT_TYPE, FIELD_NAME, CUSTOM_FIELD_NAME, SITE_NAME) VALUES (1000201, 'Projected Pledge Amount Summary', 'Projected Pledge Amount', 'site', 'ROLLUP_PLEDGE_SCHEDULED_PAYMENTS_BY_ALL', NULL, NULL, NULL);
INSERT INTO ROLLUP_ATTRIBUTE (ROLLUP_ATTRIBUTE_ID, ATTRIBUTE_NAME_ID, ATTRIBUTE_DESC, ROLLUP_ENTITY_TYPE, ROLLUP_STAT_TYPE, FIELD_NAME, CUSTOM_FIELD_NAME, SITE_NAME) VALUES (1000202, 'Projected Recurring Gift Amount Summary', 'Projected Recurring Gift Amount', 'site', 'ROLLUP_RECURRING_GIFT_SCHEDULED_PAYMENTS_BY_ALL', NULL, NULL, NULL);


-- Gifts + Adjusted Gifts, Gifts In Kind, Soft Gifts
INSERT INTO ROLLUP_SERIES (ROLLUP_SERIES_ID, SERIES_NAME_ID, SERIES_DESC, SERIES_TYPE, MAINTAIN_PERIODS, FUTURE_PERIODS, KEEP_UNMAINTAINED, SITE_NAME) VALUES (2000001, 'Calendar Year', 'Calendar Year', 'CALENDAR_YEAR', 5, 0, FALSE, NULL);
INSERT INTO ROLLUP_SERIES_X_ATTRIBUTE (ROLLUP_SERIES_ID, ROLLUP_ATTRIBUTE_ID) VALUES (2000001, 1000001);
INSERT INTO ROLLUP_SERIES_X_ATTRIBUTE (ROLLUP_SERIES_ID, ROLLUP_ATTRIBUTE_ID) VALUES (2000001, 1000002);
INSERT INTO ROLLUP_SERIES_X_ATTRIBUTE (ROLLUP_SERIES_ID, ROLLUP_ATTRIBUTE_ID) VALUES (2000001, 1000003);
INSERT INTO ROLLUP_SERIES_X_ATTRIBUTE (ROLLUP_SERIES_ID, ROLLUP_ATTRIBUTE_ID) VALUES (2000001, 1000101);
INSERT INTO ROLLUP_SERIES (ROLLUP_SERIES_ID, SERIES_NAME_ID, SERIES_DESC, SERIES_TYPE, MAINTAIN_PERIODS, FUTURE_PERIODS, KEEP_UNMAINTAINED, SITE_NAME) VALUES (2000002, 'Fiscal Year', 'Fiscal Year', 'FISCAL_YEAR', 5, 0, FALSE, NULL);
INSERT INTO ROLLUP_SERIES_X_ATTRIBUTE (ROLLUP_SERIES_ID, ROLLUP_ATTRIBUTE_ID) VALUES (2000002, 1000001);
INSERT INTO ROLLUP_SERIES_X_ATTRIBUTE (ROLLUP_SERIES_ID, ROLLUP_ATTRIBUTE_ID) VALUES (2000002, 1000002);
INSERT INTO ROLLUP_SERIES_X_ATTRIBUTE (ROLLUP_SERIES_ID, ROLLUP_ATTRIBUTE_ID) VALUES (2000002, 1000003);
INSERT INTO ROLLUP_SERIES_X_ATTRIBUTE (ROLLUP_SERIES_ID, ROLLUP_ATTRIBUTE_ID) VALUES (2000002, 1000101);
INSERT INTO ROLLUP_SERIES (ROLLUP_SERIES_ID, SERIES_NAME_ID, SERIES_DESC, SERIES_TYPE, MAINTAIN_PERIODS, FUTURE_PERIODS, KEEP_UNMAINTAINED, SITE_NAME) VALUES (2000003, 'Total Lifetime', 'Total Lifetime', 'ALLTIME', 1, 0, FALSE, NULL);
INSERT INTO ROLLUP_SERIES_X_ATTRIBUTE (ROLLUP_SERIES_ID, ROLLUP_ATTRIBUTE_ID) VALUES (2000003, 1000001);
INSERT INTO ROLLUP_SERIES_X_ATTRIBUTE (ROLLUP_SERIES_ID, ROLLUP_ATTRIBUTE_ID) VALUES (2000003, 1000002);
INSERT INTO ROLLUP_SERIES_X_ATTRIBUTE (ROLLUP_SERIES_ID, ROLLUP_ATTRIBUTE_ID) VALUES (2000003, 1000003);
INSERT INTO ROLLUP_SERIES_X_ATTRIBUTE (ROLLUP_SERIES_ID, ROLLUP_ATTRIBUTE_ID) VALUES (2000003, 1000101);

-- Scheduled Payments by constituent
INSERT INTO ROLLUP_SERIES (ROLLUP_SERIES_ID, SERIES_NAME_ID, SERIES_DESC, SERIES_TYPE, MAINTAIN_PERIODS, FUTURE_PERIODS, KEEP_UNMAINTAINED, SITE_NAME) VALUES (2000004, 'Future Calendar Year', 'Calendar Year', 'CALENDAR_YEAR', 2, 5, FALSE, NULL);
INSERT INTO ROLLUP_SERIES_X_ATTRIBUTE (ROLLUP_SERIES_ID, ROLLUP_ATTRIBUTE_ID) VALUES (2000004, 1000005);
INSERT INTO ROLLUP_SERIES_X_ATTRIBUTE (ROLLUP_SERIES_ID, ROLLUP_ATTRIBUTE_ID) VALUES (2000004, 1000006);
INSERT INTO ROLLUP_SERIES_X_ATTRIBUTE (ROLLUP_SERIES_ID, ROLLUP_ATTRIBUTE_ID) VALUES (2000004, 1000102);
INSERT INTO ROLLUP_SERIES (ROLLUP_SERIES_ID, SERIES_NAME_ID, SERIES_DESC, SERIES_TYPE, MAINTAIN_PERIODS, FUTURE_PERIODS, KEEP_UNMAINTAINED, SITE_NAME) VALUES (2000005, 'Future Fiscal Year', 'Fiscal Year', 'FISCAL_YEAR', 2, 5, FALSE, NULL);
INSERT INTO ROLLUP_SERIES_X_ATTRIBUTE (ROLLUP_SERIES_ID, ROLLUP_ATTRIBUTE_ID) VALUES (2000005, 1000005);
INSERT INTO ROLLUP_SERIES_X_ATTRIBUTE (ROLLUP_SERIES_ID, ROLLUP_ATTRIBUTE_ID) VALUES (2000005, 1000006);
INSERT INTO ROLLUP_SERIES_X_ATTRIBUTE (ROLLUP_SERIES_ID, ROLLUP_ATTRIBUTE_ID) VALUES (2000005, 1000102);

-- Monthly gift history by constituent
INSERT INTO ROLLUP_SERIES (ROLLUP_SERIES_ID, SERIES_NAME_ID, SERIES_DESC, SERIES_TYPE, MAINTAIN_PERIODS, FUTURE_PERIODS, KEEP_UNMAINTAINED, SITE_NAME) VALUES (2000007, 'Monthly Total', 'Monthly Total', 'MONTH', 13, 0, FALSE, NULL);
INSERT INTO ROLLUP_SERIES_X_ATTRIBUTE (ROLLUP_SERIES_ID, ROLLUP_ATTRIBUTE_ID) VALUES (2000007, 1000001);
INSERT INTO ROLLUP_SERIES_X_ATTRIBUTE (ROLLUP_SERIES_ID, ROLLUP_ATTRIBUTE_ID) VALUES (2000007, 1000002);
INSERT INTO ROLLUP_SERIES_X_ATTRIBUTE (ROLLUP_SERIES_ID, ROLLUP_ATTRIBUTE_ID) VALUES (2000007, 1000003);
INSERT INTO ROLLUP_SERIES_X_ATTRIBUTE (ROLLUP_SERIES_ID, ROLLUP_ATTRIBUTE_ID) VALUES (2000007, 1000101);
INSERT INTO ROLLUP_SERIES (ROLLUP_SERIES_ID, SERIES_NAME_ID, SERIES_DESC, SERIES_TYPE, MAINTAIN_PERIODS, FUTURE_PERIODS, KEEP_UNMAINTAINED, SITE_NAME) VALUES (2000008, 'Last 12 Months Total', 'Last 12 Months Total', 'ROLLING_YEAR', 1, 0, FALSE, NULL);
INSERT INTO ROLLUP_SERIES_X_ATTRIBUTE (ROLLUP_SERIES_ID, ROLLUP_ATTRIBUTE_ID) VALUES (2000008, 1000001);
INSERT INTO ROLLUP_SERIES_X_ATTRIBUTE (ROLLUP_SERIES_ID, ROLLUP_ATTRIBUTE_ID) VALUES (2000008, 1000002);
INSERT INTO ROLLUP_SERIES_X_ATTRIBUTE (ROLLUP_SERIES_ID, ROLLUP_ATTRIBUTE_ID) VALUES (2000008, 1000003);
INSERT INTO ROLLUP_SERIES_X_ATTRIBUTE (ROLLUP_SERIES_ID, ROLLUP_ATTRIBUTE_ID) VALUES (2000008, 1000101);


-- All Scheduled Payments Summary
INSERT INTO ROLLUP_SERIES (ROLLUP_SERIES_ID, SERIES_NAME_ID, SERIES_DESC, SERIES_TYPE, MAINTAIN_PERIODS, FUTURE_PERIODS, KEEP_UNMAINTAINED, SITE_NAME) VALUES (2000200, 'Future Monthly Total', 'Monthly Total', 'MONTH', 12, 11, FALSE, NULL);
INSERT INTO ROLLUP_SERIES_X_ATTRIBUTE (ROLLUP_SERIES_ID, ROLLUP_ATTRIBUTE_ID) VALUES (2000200, 1000201);
INSERT INTO ROLLUP_SERIES_X_ATTRIBUTE (ROLLUP_SERIES_ID, ROLLUP_ATTRIBUTE_ID) VALUES (2000200, 1000202);

