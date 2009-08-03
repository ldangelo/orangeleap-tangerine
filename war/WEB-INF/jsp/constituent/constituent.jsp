<%@ include file="/WEB-INF/jsp/include.jsp" %>
<page:applyDecorator name="form">
	<spring:message code='constituentProfile' var="titleText" scope="request" />
	<spring:message code='profileSummary' var="headerText" scope="request" />
	<spring:message code='submit' var="submitText" />

	<c:set var="headerText" value="${headerText}" scope="request"/>
	<html>
		<head>
			<title><c:out value="${titleText}"/><c:if test="${form.domainObject.id > 0}"><c:out value=" - ${requestScope.constituent.firstLast}"/></c:if></title>  
        </head>
        <body>
	        <form:form method="post" commandName="${requestScope.commandObject}">
		        <c:set var="topButtons" scope="request">
			        <input type="submit" value="<c:out value='${submitText}'/>" class="saveButton" id="submitButton"/>
		        </c:set>

		        <%@ include file="/WEB-INF/jsp/includes/formHeader.jsp"%>

		        <tangerine:fields pageName="constituent"/>

				<div class="formButtonFooter constituentFormButtons">
					<input type="submit" value="<spring:message code='submit'/>" class="saveButton" />
					<c:if test="${form.domainObject.id > 0}">
						<a class="newAccountButton" href="constituent.htm"><spring:message code='enterNew'/></a>
					</c:if>
				</div>
			</form:form>
	        <page:param name="scripts">
				<script type="text/javascript" src="js/contactinfo.js"></script>
				<script type="text/javascript">
					<c:if test="${not empty requestScope.duplicateConstituentName}">
						$(function() {
							var duplicateConstituentName = '<c:out value="${requestScope.duplicateConstituentName}"/>';
							Ext.Msg.show({
								title: "Save Duplicate Constituent '" + duplicateConstituentName + "'?",
								msg: duplicateConstituentName + ' is a duplicate of another constituent.  Would you like to continue?',
								buttons: Ext.Msg.OKCANCEL,
								icon: Ext.MessageBox.WARNING,
								fn: function(btn, text) {
									if (btn == "ok") {
										$("div.mainForm form").eq(0).append("<input type='hidden' name='byPassDuplicateDetection' id='byPassDuplicateDetection' value='true'/>").submit();
									}
								}
							});
						});
					</c:if>
				</script>
	        </page:param>
		</body>
	</html>
</page:applyDecorator>