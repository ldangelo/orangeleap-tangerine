<%@ include file="/WEB-INF/jsp/include.jsp" %>
<tiles:insertDefinition name="base">
	<tiles:putAttribute name="browserTitle" value="Email Manager" />
	<tiles:putAttribute name="primaryNav" value="People" />
	<tiles:putAttribute name="secondaryNav" value="Edit" />
	<tiles:putAttribute name="sidebarNav" value="Email Manager" />
	<tiles:putAttribute name="mainContent" type="string">

	<div class="content760 mainForm">
	<mp:page pageName='emailManager' />

	<c:set var="constituent" value="${constituent}" scope="request" />

	<c:if test="${constituent.id!=null}">
		<c:set var="viewingConstituent" value="true" scope="request" />
	</c:if>

	<c:set var="commandObject" value="email" />

	<form:form method="post" commandName="email">
		<c:if test="${id != null}">
			<input type="hidden" name="id" value="<c:out value='${id}'/>" />
		</c:if>

		<jsp:include page="../snippets/constituentHeader.jsp">
			<jsp:param name="currentFunctionTitleText" value="Emails" />
			<jsp:param name="submitButtonText" value="Submit" />
		</jsp:include>

		<jsp:include page="../snippets/standardFormErrors.jsp"/>

		<div class="columns">
			<c:forEach var="sectionDefinition" items="${sectionDefinitions}">
				<c:if test="${sectionDefinition.layoutType ne 'GRID'}">
					<%@ include file="/WEB-INF/jsp/snippets/fieldLayout.jsp"%>
				</c:if>
			</c:forEach>
			<div class="clearColumns"></div>
		</div>
		<div class="formButtonFooter constituentFormButtons">
			<input type="submit" value="<spring:message code='submit'/>" class="saveButton" />
			<c:if test="${email.id != null}">
				<input type="button" value="<spring:message code='cancel'/>" class="saveButton" onclick="OrangeLeap.gotoUrl('emailManager.htm?constituentId=${constituent.id}')"/>
			</c:if>
		</div>
	</form:form>

	<jsp:include page="email.jsp" />

	</div>

	</tiles:putAttribute>
</tiles:insertDefinition>