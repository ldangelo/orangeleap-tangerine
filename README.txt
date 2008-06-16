In order to set up and run the MPower Application do the following:

Database:
1.  Ensure that MySQL 5.0.x is installed locally.
2.  Create a new MySQL database schema called 'mpoweropen'
3.  Create a new MySQL database user called 'mpower' with password 'mpower'
4.  Assign all permissions for mpoweropen to mpower

Tomcat:
5.  Ensure that JDK 1.6 is installed locally
6.  Ensure that Tomcat 6.0.16 is installed at /dev/apps/apache-tomcat-6.0.16
7.  Follow instructions in README under build/tomcat in this project
8.  Copy the following jars to /dev/apps/apache-tomcat-6.0.16/lib
	a. mysql-connector-java-5.0.8-bin.jar (mysql driver)
	b. jta.jar (Java Transaction APIs)
	c. sqljdbc.jar (SQL Server Driver - for SQL Server only)

	Note: For classloader reasons it is necessary to copy these jars if you want
	a container managed data source.

9.  Open the build/build.xml file from this project in the Eclipse ant view
10. Execute the 'Deploy War' task
11. Execute the 'Start Tomcat...' task.

You can now query the database and see that tables have been created for the application.
