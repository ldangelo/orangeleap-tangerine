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
<div class="loginPane">
	<div class="loginContent">
	    <img src="images/orangeleap-logo-tag.png" />
	    <h1 class="loginHeader"><spring:message code="pleaseSignIn"/></h1>

    	<c:if test="${not empty param.login_error}">
    		<p style="color:red;padding:0;margin:0;"><c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}"/></p>
    	</c:if>

    	<form id="f" name="f" action="<c:url value="loginProcess" />" method="post">
			<table class="loginInfo">
				<tr>
					<td style="text-align:right"><label for="j_fullname"><spring:message code="userName"/></label></td>
					<td><input size="30" class="loginField" type="text" name="j_fullname" id="j_fullname"  <c:if test="${not empty param.login_error}">value="${sessionScope['SPRING_SECURITY_LAST_USERNAME']}"</c:if> /></td>
	    		</tr>
				<tr>
					<td style="text-align:right"><label for="j_password"><spring:message code="password"/></label></td>
					<td><input size="30" class="loginField" type="password" name="j_password" id="j_password" /></td>
	    		</tr>
	    		<tr>
		    		<td>&nbsp;</td>
		    		<td class="loginButton">
		    		<input class="loginField" type="hidden" name="sitename" id="sitename" />
		    		<input class="loginField" type="hidden" name="j_username" id="j_username" />
		    		<input class="loginButton" name="submit" id="submit" type="submit" onclick="return splitLoginName();" value="<spring:message code='signIn'/>" />
		    		</td>
	            </tr>
	            <%--
	            <tr>
		    		<td>&nbsp;</td>
		    		<td><a href="#">Forgot your password?</a></td>
	            </tr>
	            --%>
    		</table>
    	</form>
    	<spring:message code="release"/>: ${build.version} <!-- ${build.time}  -->
	</div>
</div>
<script type="text/javascript">

$('#j_fullname').focus();    

function splitLoginName() {

	var fullname = $('#j_fullname').val();

	var i = fullname.indexOf('@');
	if (i == -1 || i == fullname.length - 1) {
		alert('<spring:message code="loginError"/>');
		return false;
	}

	var sitename = fullname.substring(i+1);
	var username = fullname.substring(0,i);

	$('#j_username').val(username);
	$('#sitename').val(sitename);
	
	return true;
}
</script>
<%-->
<br />
<br />
<h5>Cheat Sheet</h5>
Username/Password/Organization/Roles:<br/>
nolan/ryan/company1/<b>ROLE_SUPER_ADMIN</b>, ROLE_ADMIN, ROLE_SUPER_MANAGER, ROLE_MANAGER, ROLE_SUPER_USER, ROLE_USER<br/>
rod/koala/company1/<b>ROLE_ADMIN</b>, ROLE_SUPER_MANAGER, ROLE_MANAGER, ROLE_SUPER_USER, ROLE_USER<br/>
randy/johnson/company1/<b>ROLE_SUPER_MANAGER</b>, ROLE_MANAGER, ROLE_SUPER_USER, ROLE_USER<br/>
dianne/emu/company1/<b>ROLE_MANAGER</b>, ROLE_SUPER_USER, ROLE_USER<br/>
greg/maddux/company1/<b>ROLE_SUPER_USER</b>, ROLE_USER<br/>
scott/wombat/company1/<b>ROLE_USER</b><br/></br>
jack/jack/company2/<b>ROLE_ADMIN</b>, ROLE_USER, ROLE_MANAGER<br/>
locke/locke/company2/<b>ROLE_USER</b>, ROLE_MANAGER<br/>
hurley/hurley/company2/<b>ROLE_USER</b>
--%>
</body>
</html>