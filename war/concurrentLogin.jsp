<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>MPower Login</title>
	<link href="<c:url value='/css/login.css' />" rel="stylesheet" type="text/css" />
	<link rel="shortcut icon" type="image/ico" href="images/favicon.ico" />
</head>
<body>
<div class="loginPane">
	<div class="loginContent">
	    <img src="images/mpowerLogo.gif" />
	    <br /><br />
	    <h1 class="loginHeader">Session Logged Out</h1>
        <p>Your account has been logged in from another computer or browser window</p>
	    <p style="padding:8px;">
	    <a style="font-size:14px;" href="login.jsp">Click here to login again</a>
	    </p>
	</div>
</div>
</body>
</html>