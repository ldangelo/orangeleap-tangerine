<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title><spring:message code="appName"/> <spring:message code="login"/></title>
        <link href="<c:url value='css/login.css' />" rel="stylesheet" type="text/css" />
        <link rel="shortcut icon" type="image/ico" href="images/favicon.ico" />
    </head>
    <body>
    
        <div class="loginPane">
            <div class="loginContent">
                <img src="images/orangeleap-logo-tag.png" />
                <br /><br />
                <h1 class="loginHeader"><spring:message code='loggedOut'/></h1>
                <p style="padding:8px;">
                    <a style="font-size:14px;" href="welcome.htm"><spring:message code='returnLoginPage'/></a>
                </p>
            </div>
        </div>

    <%--
    
    CAS logout procedure
	When an application logs out it needs to log out of CAS as well and also erase the browser session tokens of any other apps using CAS or they will still be logged in.  
	This could cause a problem if they log out and log back in the other app with the shared cas cookie on the root path.

     --%>
    
        <iframe id="casLogout" src="/<%= System.getProperty("contextPrefix") %>cas/logout" height="0" width="100%" style="display: none"></iframe>
	    <%@ include file="/WEB-INF/jsp/includes/clearcookies.jsp"%>
        
    </body>
</html>