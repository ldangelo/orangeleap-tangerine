<%@ include file="/WEB-INF/jsp/include.jsp"%>

<mp:page pageName='person' />
<c:set var="person" value="${person}" scope="request" />
<c:if test="${person.id!=null}">
	<c:set var="viewingPerson" value="true" scope="request" />
</c:if>

<form:form method="post" commandName="person">
	<c:if test="${id != null}">
		<input type="hidden" name="id" value="${id}" />
	</c:if>

	<jsp:include page="snippets/personHeader.jsp">
		<jsp:param name="currentFunctionTitleText" value="Profile" />
		<jsp:param name="submitButtonText" value="Save Changes" />
	</jsp:include>
	
	<jsp:include page="snippets/standardFormErrors.jsp"/>

	<c:forEach var="sectionDefinition" items="${sectionDefinitions}">
		<h4 class="formSectionHeader"><mp:sectionHeader sectionDefinition="${sectionDefinition}" /></h4>
		<%@ include file="/WEB-INF/jsp/snippets/fieldLayout.jsp" %>
	</c:forEach>
	<div class="formButtonFooter personFormButtons">
		<input type="submit" value="Save Changes" class="saveButton" />
		<a class="newAccountButton" href="person.htm">Create Another Person � </a>
	</div>
</form:form>