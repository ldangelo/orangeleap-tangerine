<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><spring:message code="appName"/> <spring:message code="login"/></title>
	<link href="<c:url value='css/login.css' />" rel="stylesheet" type="text/css" />
	<link rel="shortcut icon" type="image/ico" href="images/favicon.ico" />
	<script type="text/javascript" src="js/jquery/jquery-1.3.2.min.js"></script>
	<script type="text/javascript" src="js/jquery.cookie.js"></script>
</head>

	    <body>
	       
	        <iframe id="casLogout" src="/<%= System.getProperty("contextPrefix") %>cas/logout" height="0" width="100%" style="display: none"></iframe>
	        <%@ include file="/WEB-INF/jsp/includes/clearcookies.jsp"%>
	        <script>
	        window.location.href="index.htm";
	        </script>
 	    </body>
	    
</html>