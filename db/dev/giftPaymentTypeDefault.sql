-- make gift payment source's payment type default to Cash

INSERT INTO ENTITY_DEFAULT (SITE_NAME, ENTITY_TYPE, ENTITY_FIELD_NAME, DEFAULT_VALUE) VALUES ('company2', 'gift', 'paymentType', 'Cash');