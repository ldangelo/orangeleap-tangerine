<%@ include file="/WEB-INF/jsp/include.jsp" %>
<tiles:insertDefinition name="base">
	<tiles:putAttribute name="browserTitle" value="Constituent Profile" />
	<tiles:putAttribute name="primaryNav" value="People" />
	<tiles:putAttribute name="secondaryNav" value="Edit" />
	<tiles:putAttribute name="sidebarNav" value="Profile" />
	<tiles:putAttribute name="mainContent" type="string">
		<div class="content760 mainForm">
			<mp:page pageName='person' />
			<c:set var="person" value="${person}" scope="request" />
			<c:if test="${person.id!=null}">
				<c:set var="viewingPerson" value="true" scope="request" />
			</c:if>
			<c:set var="commandObject" value="person" />
			
			<form:form method="post" commandName="person">
				<c:if test="${id != null}">
					<input type="hidden" name="id" value="<c:out value='${id}'/>" />
				</c:if>

				<spring:message code='profileSummary' var="titleText" />
				<spring:message code='saveChanges' var="submitText" />
				<jsp:include page="../snippets/personHeader.jsp">
					<jsp:param name="currentFunctionTitleText" value="${titleText}" />
					<jsp:param name="submitButtonText" value="${submitText}" />
				</jsp:include>
				
				<jsp:include page="../snippets/standardFormErrors.jsp"/>
			
				<c:forEach var="sectionDefinition" items="${sectionDefinitions}">
					<h4 class="formSectionHeader"><mp:sectionHeader sectionDefinition="${sectionDefinition}" /></h4>
					<%@ include file="/WEB-INF/jsp/snippets/fieldLayout.jsp" %>
				</c:forEach>
				<div class="formButtonFooter personFormButtons">
					<input type="submit" value="Save Changes" class="saveButton" />
					<a class="newAccountButton" href="person.htm">Add New Constituent � </a>
				</div>
			</form:form>
		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>