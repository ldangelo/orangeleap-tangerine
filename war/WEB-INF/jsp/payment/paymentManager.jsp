<%@ include file="/WEB-INF/jsp/include.jsp" %>
<tiles:insertDefinition name="base">
	<tiles:putAttribute name="browserTitle" value="Payment Manager"/>
	<tiles:putAttribute name="primaryNav" value="People"/>
	<tiles:putAttribute name="secondaryNav" value="Edit"/>
	<tiles:putAttribute name="sidebarNav" value="Payment Methods"/>
	<tiles:putAttribute name="mainContent" type="string">

	<div class="content760 mainForm">
		<mp:page pageName='paymentManager'/>	
		<c:set var="person" value="${paymentSource.person}" scope="request"/>
		<c:if test="${person.id != null}">
			<c:set var="viewingPerson" value="true" scope="request"/>
		</c:if>
		<form:form method="post" commandName="paymentSource">
			<c:if test="${id != null}">
				<input type="hidden" name="id" value="<c:out value='${id}'/>"/>
			</c:if>
	
			<spring:message code='paymentMethods' var="titleText" />
			<spring:message code='submitPaymentMethod' var="submitText" />
			<jsp:include page="../snippets/personHeader.jsp">
				<jsp:param name="currentFunctionTitleText" value="${titleText}" />
				<jsp:param name="submitButtonText" value="${submitText}" />
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
				<input type="submit" value="<spring:message code='submitPaymentMethod'/>" class="saveButton"/>
				<c:if test="${paymentSource.id != null}">
					<input type="button" value="<spring:message code='cancel'/>" class="saveButton" onclick="MPower.gotoUrl('paymentManager.htm?personId=${person.id}')"/>
				</c:if>
			</div>
		</form:form>
	
		<jsp:include page="paymentSource.jsp"/>

	</div>

	</tiles:putAttribute>
</tiles:insertDefinition>