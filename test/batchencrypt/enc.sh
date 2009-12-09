export LIB=/opt/orangeleap/instance_DEV/webapps/orangeleap/WEB-INF/lib
java -cp "$LIB/commons-logging-1.1.1.jar:$LIB/commons-codec-1.3.jar:$LIB/commons-io-1.2.jar:$LIB/tangerine.jar" com.orangeleap.tangerine.util.AESEncrypt $1 $2 $3

