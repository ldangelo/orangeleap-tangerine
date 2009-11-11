SET AUTOCOMMIT = 0;


UPDATE PAGE_ACCESS SET ROLE = 'ROLE_SUPER_ADMIN,ROLE_ADMIN' WHERE PAGE_TYPE IN ('siteSettings', 'logView', 'manageDashboard');

UPDATE PAGE_ACCESS SET ROLE = 'ROLE_SUPER_ADMIN' WHERE PAGE_TYPE IN ('postbatch');

INSERT INTO `PAGE_ACCESS` (`ACCESS_TYPE`,`PAGE_TYPE`,`ROLE`,`SITE_NAME`) VALUES ('ALLOWED', 'siteDefaults', 'ROLE_SUPER_ADMIN,ROLE_ADMIN', NULL);

COMMIT;