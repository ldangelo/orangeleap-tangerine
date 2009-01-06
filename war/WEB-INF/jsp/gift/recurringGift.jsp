<%@ include file="/WEB-INF/jsp/include.jsp" %>
<tiles:insertDefinition name="base">
	<tiles:putAttribute name="browserTitle" value="Enter Recurring Gift" />
	<tiles:putAttribute name="primaryNav" value="People" />
	<tiles:putAttribute name="secondaryNav" value="Edit" />
	<tiles:putAttribute name="sidebarNav" value="Enter Recurring Gift" />
	<tiles:putAttribute name="mainContent" type="string">
		<div class="content760 mainForm">
			<mp:page pageName='recurringGift' />
				<c:set var="person" value="${commitment.person}" scope="request" />
				<c:if test="${person.id!=null}">
					<c:set var="viewingPerson" value="true" scope="request" />
				</c:if>
				<c:set var="commandObject" value="commitment" />
				<form:form method="post" commandName="${commandObject}">
					<c:if test="${id != null}">
						<input type="hidden" name="id" value="<c:out value='${id}'/>" />
					</c:if>

					<jsp:include page="../snippets/personHeader.jsp">
						<jsp:param name="currentFunctionTitleText" value="Enter Recurring Gift" />
						<jsp:param name="submitButtonText" value="Save Recurring Gift" />
					</jsp:include>

					<jsp:include page="../snippets/standardFormErrors.jsp"/>

					<c:set var="gridCollectionName" value="distributionLines" />
					<c:set var="gridCollection" value="${commitment.distributionLines}" />
					<c:set var="paymentSource" value="${commitment.paymentSource}" />

					<div class="columns">
						<c:forEach var="sectionDefinition" items="${sectionDefinitions}">
							<c:if test="${sectionDefinition.layoutType ne 'GRID'}">
								<%@ include file="/WEB-INF/jsp/snippets/fieldLayout.jsp"%>
							</c:if>
						</c:forEach>
						<div class="clearColumns"></div>
					</div>
					<c:forEach var="sectionDefinition" items="${sectionDefinitions}">
						<c:if test="${sectionDefinition.layoutType eq 'GRID'}">
							<%@ include file="/WEB-INF/jsp/snippets/fieldLayout.jsp"%>
							<p class="gridActions">&nbsp;<span id="subTotal">Total <span class="warningText">(must match recurring gift value)</span> <span class="value">0</span></span></p>
						</c:if>
					</c:forEach>
					<div class="formButtonFooter personFormButtons"><input type="submit" value="Save Recurring Gift" class="saveButton" /></div>
				</form:form>
		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>