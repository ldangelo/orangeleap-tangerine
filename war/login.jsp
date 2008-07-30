<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>MPower Login</title>
	<link href="<c:url value='/css/login.css' />" rel="stylesheet" type="text/css" />
	<link rel="shortcut icon" type="image/ico" href="images/favicon.ico" />
	<script type="text/javascript" src="js/jquery.js"></script>
	<script type="text/javascript" src="js/jquery.cookie.js"></script>
</head>
<body>
<div class="loginPane">
	<div class="loginContent">
	    <img src="images/mpowerLogo.gif" />
	    <h1 class="loginHeader">Please sign in.</h1>

    	<c:if test="${not empty param.login_error}">
    		<p style="color:red;padding:0;margin:0;">The information you provided could not be validated.</p>
    		<p style="color:red;padding:0;margin:0;"><c:out value="${SPRING_SECURITY_LAST_EXCEPTION}"/></p>
    	</c:if>

    	<form name="f" action="<c:url value="/loginProcess" />" method="post">
			<table class="loginInfo">
				<tr>
					<td style="text-align:right"><label for="j_username">Username:</label></td>
					<td><input size="30" class="loginField" type="text" name="j_username" id="j_username" <c:if test="${not empty param.login_error}">value="${sessionScope["SPRING_SECURITY_LAST_USERNAME"]}"</c:if> /></td>
	    		</tr>
				<tr>
					<td style="text-align:right"><label for="j_password">Password:</label></td>
					<td><input size="30" class="loginField" type="password" name="j_password" id="j_password" /></td>
	    		</tr>
				<tr>
					<td style="text-align:right"><label for="j_username">Organization:</label></td>
					<td><input size="30" class="loginField" type="text" name="sitename" id="sitename" /></td>
	    		</tr>
	    		<tr>
		    		<td>&nbsp;</td>
		    		<td class="loginButton"><input class="loginButton" name="submit" id="submit" type="submit" value="Sign In" /></td>
	            </tr>
	            <tr>
		    		<td>&nbsp;</td>
		    		<td><a href="#">Forgot your password?</a></td>
	            </tr>
    		</table>
    	</form>
	</div>
</div>
<br />
<br />
<h5>Cheat Sheet</h5>
Username/Password/Organization/Roles:<br/>
rod/koala/company1/ROLE_ADMIN, ROLE_USER, ROLE_MANAGER<br/>
dianne/emu/company1/ROLE_USER, ROLE_MANAGER<br/>
scott/wombat/company1/ROLE_USER<br/>
jack/jack/company2/ROLE_ADMIN, ROLE_USER, ROLE_MANAGER<br/>
locke/locke/company2/ROLE_USER, ROLE_MANAGER<br/>
hurley/hurley/company2/ROLE_USER
<script type="text/javascript">
	$(document).ready(function() {
		var savedOrgName = $.cookie('siteCookie');
		if (savedOrgName != null && savedOrgName != "") {
			$("#sitename").attr("value",savedOrgName);
		}
	});
</script>
</body>
</html>