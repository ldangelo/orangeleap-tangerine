<%@ include file="/WEB-INF/jsp/include.jsp" %>
<tiles:insertDefinition name="base">
	<tiles:putAttribute name="browserTitle" value="My Account" />
	<tiles:putAttribute name="primaryNav" value="People" />
	<tiles:putAttribute name="secondaryNav" value="" />
	<tiles:putAttribute name="mainContent" type="string">
		<div class="content760 accountForm">
			<form:form method="post" commandName="user">
				<h1>My Account</h1>
				<br />
				<h4 class="formSectionHeader">Change Password</h4>
				<jsp:include page="snippets/standardFormErrors.jsp"/>
			<table class="formTable">
				<tr>
					<td style="text-align:right"><label for="j_username">Enter your <b>current password:</b></label></td>
					<td><input size="30" class="loginField" type="password" name="currentPassword" id="currentPassword" value="${user.currentPassword}" /></td>
	    		</tr>
				<tr>
					<td style="text-align:right"><label for="j_password">Choose a <b>new password:</b></label></td>
					<td><input size="30" class="loginField" type="password" name="newPassword" id="newPassword" value="${user.newPassword}" /></td>
	    		</tr>
				<tr>
					<td style="text-align:right"><label for="j_username">Confirm your <b>new password:</b></label></td>
					<td><input size="30" class="loginField" type="password" name="newPasswordConfirm" id="newPasswordConfirm" value="${user.newPasswordConfirm}" /></td>
	    		</tr>
    		</table>
    		<div class="formButtonFooter personFormButtons">
				<input type="submit" class="saveButton" value="Save Changes"/>
			</div>
			</form:form>
		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>