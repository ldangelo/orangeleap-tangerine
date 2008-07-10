<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>



<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>MPower Login</title>

</head>
<body>

    <a href="<c:url value="/"/>">Home</a>

<div id="content">
    <h1>Login Required</h1>
    Temporary credentials: test / test

    
    <div class="section">
    	<c:if test="${not empty param.login_error}">
    		<div class="errors">
    			Your login attempt was not successful, try again.
    			<br />
    			Reason: ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
    			<br />
    			username submitted: ${sessionScope["SPRING_SECURITY_LAST_USERNAME"]}
    		
    			

    		</div>
    	</c:if>
    </div>
    
    <div class="section">
    	<form name="f" action="<c:url value="/loginProcess" />" method="post">
    	    <form:errors path="*">
	<div class="globalFormErrors">
		<h5>Please correct the following errors on this page:</h5>
		<ul>
		<c:forEach items="${messages}" var="message">
			<li>${message}</li>
		</c:forEach>
		</ul>
	</div>
</form:errors>
    		<fieldset>
    			<div class="field">
    				<div class="label"><label for="j_username">User:</label></div>
    				<div class="output">
    					<input type="text" name="j_username" id="j_username" <c:if test="${not empty param.login_error}">value="${sessionScope["SPRING_SECURITY_LAST_USERNAME"]}"</c:if> />
    				</div>
    			</div>
    			<div class="field">
    				<div class="label"><label for="j_password">Password:</label></div>
    				<div class="output">
    					<input type="password" name="j_password" id="j_password" />
    				</div>
    			</div>
    			<div class="field">
    				<div class="label"><label for="remember_me">Don't ask for my password for two weeks:</label></div>
    				<div class="output">
    					<input type="checkbox" name="_spring_security_remember_me" id="remember_me" />
    				</div>
    			</div>
                <div class="form-buttons">
                    <div class="button">
                        <input name="submit" id="submit" type="submit" value="Login" />
                    </div>
                </div>
    		</fieldset>
    	</form>
    </div>
</div>


</div>
</body>
</html>
