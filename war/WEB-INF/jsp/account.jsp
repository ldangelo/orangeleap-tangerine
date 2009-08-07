<%@ include file="/WEB-INF/jsp/include.jsp" %>
<page:applyDecorator name="form">
    <spring:message code='myAccount' var="titleText" />
    <html>
        <head>
            <title><c:out value="${titleText}"/></title>
        </head>
        <body>
			<form:form method="post" commandName="user">
				<h1>My Account</h1>
				<br />
				<h4 class="formSectionHeader">Change Password</h4>
                <%@ include file="/WEB-INF/jsp/includes/standardFormErrors.jsp"%>
                <table class="formTable">
                    <tr>
                        <td style="text-align:right"><label for="j_username">Enter your <b>current password:</b></label></td>
                        <td><input size="30" class="loginField" type="password" name="currentPassword" id="currentPassword" value="<c:out value='${user.currentPassword}'/>" /></td>
                    </tr>
                    <tr>
                        <td style="text-align:right"><label for="j_password">Choose a <b>new password:</b></label></td>
                        <td><input size="30" class="loginField" type="password" name="newPassword" id="newPassword" value="<c:out value='${user.newPassword}'/>" /></td>
                    </tr>
                    <tr>
                        <td style="text-align:right"><label for="j_username">Confirm your <b>new password:</b></label></td>
                        <td><input size="30" class="loginField" type="password" name="newPasswordConfirm" id="newPasswordConfirm" value="<c:out value='${user.newPasswordConfirm}'/>" /></td>
                    </tr>
                </table>
                <div class="formButtonFooter constituentFormButtons">
                    <input type="submit" class="saveButton" value="Save Changes"/>
                </div>
			</form:form>
        </body>
    </html>
</page:applyDecorator>
