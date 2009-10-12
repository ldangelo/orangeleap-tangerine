export PTH=/opt/orangeleap/instance_TEST/webapps

cd $PTH/jasperserver/WEB-INF
cp applicationContext-security-CAS.xml.txt applicationContext-security.xml

cd $PTH/jasperserver/WEB-INF/jsp
cp login_welcome-CAS.jsp login_welcome.jsp

cd $PTH/orangeleap
cp login-CAS.jsp login.jsp

cd $PTH/orangeleap/WEB-INF
cp applicationContext-security-CAS.xml.txt applicationContext-security.xml

cd $PTH/orangeleap/WEB-INF/jsp/includes
cp navigation-CAS.jsp navigation.jsp

cd $PTH/orangeleap/WEB-INF/contexts
cp webservice-context-CAS.xml.txt webservice-context.xml

cd $PTH/clementine/WEB-INF
cp webservice-context-CAS.xml.txt webservice-context.xml

cd $PTH/clementine/WEB-INF
cp web-CAS.xml web.xml
