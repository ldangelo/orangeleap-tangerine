<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>Orange Leap Login</title>
	<link href="<c:url value='css/login.css' />" rel="stylesheet" type="text/css" />
	<link rel="shortcut icon" type="image/ico" href="images/favicon.ico" />
</head>
<body>
<div class="loginPane">
	<div class="loginContent">
	    <img src="images/orangeleap-logo-tag.png" />
	    <br /><br />
	    <h1 class="loginHeader">You have successfully logged out.</h1>
	    <p style="padding:8px;">
	    <a style="font-size:14px;" href="welcome.htm">Return to the login page</a>
	    </p>
	</div>
</div>
<iframe id="casLogout" src="/cas/logout" height="0" width="100%" style="display: none"></iframe>

</body>
</html>