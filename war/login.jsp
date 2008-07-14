<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>



<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>MPower Login</title>
<link href="<c:url value='/css/login.css' />" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="loginPane">
<div class="loginContent">
    <img src="images/mpowerLogo.gif" />

    <h1 class="loginHeader">Please sign in.</h1>
    
    	<c:if test="${not empty param.login_error}">
    		<p style="color:red;padding:0;margin:0;">The information you provided could not be validated.</p>
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
</body>
</html>
