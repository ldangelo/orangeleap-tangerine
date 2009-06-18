SET foreign_key_checks = 0;


TRUNCATE TABLE ADDRESS;
TRUNCATE TABLE AUDIT;
TRUNCATE TABLE RECURRING_GIFT;
TRUNCATE TABLE PLEDGE;
TRUNCATE TABLE COMMUNICATION_HISTORY;
TRUNCATE TABLE CUSTOM_FIELD_RELATIONSHIP;
TRUNCATE TABLE CONSTITUENT_CUSTOM_FIELD_RELATIONSHIP;
TRUNCATE TABLE CACHE_GROUP;
TRUNCATE TABLE CUSTOM_FIELD;
TRUNCATE TABLE DASHBOARD_ITEM;
TRUNCATE TABLE DASHBOARD_ITEM_DATASET;
TRUNCATE TABLE DISTRO_LINE;
TRUNCATE TABLE EMAIL;
TRUNCATE TABLE ENTITY_DEFAULT;
TRUNCATE TABLE FIELD_CONDITION;
TRUNCATE TABLE FIELD_DEFINITION;
TRUNCATE TABLE FIELD_RELATIONSHIP;
TRUNCATE TABLE FIELD_REQUIRED;
TRUNCATE TABLE FIELD_VALIDATION;
TRUNCATE TABLE GIFT;
TRUNCATE TABLE MESSAGE_RESOURCE;
TRUNCATE TABLE PAGE_ACCESS;
TRUNCATE TABLE PAYMENT_SOURCE;
TRUNCATE TABLE PAYMENT_HISTORY;
TRUNCATE TABLE CONSTITUENT;
TRUNCATE TABLE PHONE;
TRUNCATE TABLE PICKLIST;
TRUNCATE TABLE PICKLIST_ITEM;
TRUNCATE TABLE QUERY_LOOKUP;
TRUNCATE TABLE QUERY_LOOKUP_PARAM;
TRUNCATE TABLE RECURRING_GIFT;
TRUNCATE TABLE SECTION_DEFINITION;
TRUNCATE TABLE SECTION_FIELD;
TRUNCATE TABLE SITE;
TRUNCATE TABLE CUSTOM_ID;
-- TRUNCATE TABLE ERROR_LOG;    cant do this with ARCHIVE option



SET foreign_key_checks = 1;
