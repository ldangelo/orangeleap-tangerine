<%@ include file="/WEB-INF/jsp/include.jsp" %>
<tiles:insertDefinition name="base">
	<tiles:putAttribute name="browserTitle" value="Address Manager" />
	<tiles:putAttribute name="primaryNav" value="People" />
	<tiles:putAttribute name="secondaryNav" value="Edit" />
	<tiles:putAttribute name="sidebarNav" value="Address Manager" />
	<tiles:putAttribute name="mainContent" type="string">

	<div class="content760 mainForm">
	<mp:page pageName='addressManagerEdit' />

	<c:set var="person" value="${person}" scope="request" />

	<c:if test="${person.id!=null}">
		<c:set var="viewingPerson" value="true" scope="request" />
	</c:if>

	<form:form method="post" commandName="address">
		<c:if test="${id != null}">
			<input type="hidden" name="id" value="<c:out value='${id}'/>" />
		</c:if>

		<jsp:include page="../snippets/personHeader.jsp">
			<jsp:param name="currentFunctionTitleText" value="Addresses" />
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
		<div class="formButtonFooter personFormButtons">
			<input type="submit" value="<spring:message code='submit'/>" class="saveButton" />
			<c:if test="${address.id != null}">
				<input type="button" value="<spring:message code='cancel'/>" class="saveButton" onclick="MPower.gotoUrl('addressManager.htm?personId=${person.id}')"/>
			</c:if>
		</div>

	</form:form>

	<!--<jsp:include page="address.jsp" />-->

	</div>

	</tiles:putAttribute>
</tiles:insertDefinition>