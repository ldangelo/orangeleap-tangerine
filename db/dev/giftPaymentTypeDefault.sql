-- make gift payment source's payment type default to Cash

INSERT INTO ENTITY_DEFAULT (SITE_ID, ENTITY_TYPE, ENTITY_FIELD_NAME, DEFAULT_VALUE) VALUES (1, 'gift', 'paymentSource.paymentType', 'Cash');