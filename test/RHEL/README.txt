LDAP notes:
yum install openldap-servers openldap-clients

chown -R ldap.ldap /etc/openldap

edit slapd.conf
copy over /var/lib/ldap/DB_CONFIG
service start ldap
run ./setup-base.sh to create base dn
import users ldiff


http://www.linuxmail.info/openldap-setup-howto/

blog entry regarding service start problem -

> $ sudo /etc/init.d/ldap start
> Starting slapd: /bin/bash: /tmp/start-slapd.l14891: Permission denied

Your /tmp/ is mounted with noexec.

To work around this, create a directory where the script can be
written to and executed (can be owned by root:root and permissions
755), for example, /etc/openldap/initscript/, and then add the
following line to /etc/sysconfig/ldap (create the file if it doesn't
exist):

TMP=/etc/openldap/initscript

