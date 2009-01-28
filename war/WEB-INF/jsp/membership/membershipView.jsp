<%@ include file="/WEB-INF/jsp/include.jsp" %>
<tiles:insertDefinition name="base">
	<tiles:putAttribute name="browserTitle" value="View Membership" />
	<tiles:putAttribute name="primaryNav" value="People" />
	<tiles:putAttribute name="secondaryNav" value="Search" />
	<tiles:putAttribute name="sidebarNav" value="" />
	<tiles:putAttribute name="mainContent" type="string">
		<div class="content760 mainForm">
			<mp:page pageName='membershipView'/>
			<c:set var="person" value="${commitment.person}" scope="request" />
			<c:if test="${person.id!=null}">
				<c:set var="viewingPerson" value="true" scope="request" />
			</c:if>
			<form:form method="post" commandName="commitment">

				<jsp:include page="../snippets/personHeader.jsp">
					<jsp:param name="currentFunctionTitleText" value="View Membership" />
				</jsp:include>

				<h3 class="info">This membership was entered on <fmt:formatDate value="${commitment.createDate}"/> at <fmt:formatDate value="${commitment.createDate}" type="time" />.</h3>
					<div class="columns">
						<c:forEach var="sectionDefinition" items="${sectionDefinitions}" begin="0" end="0">
								<%@ include file="/WEB-INF/jsp/snippets/fieldLayout.jsp" %>
						</c:forEach>
						<c:forEach var="sectionDefinition" items="${sectionDefinitions}" begin="1" end="3">
							<c:if test="${sectionDefinition.defaultLabel==commitment.paymentType}">
								<%@ include file="/WEB-INF/jsp/snippets/fieldLayout.jsp" %>
							</c:if>
						</c:forEach>
						<div class="clearColumns"></div>
					</div>
				<div class="formButtonFooter personFormButtons">
					<input type="submit" value="Save Changes" class="saveButton" />
				</div>
			</form:form>

			<div class="formButtonFooter">
				<c:if test="${pageAccess['/membershipList.htm']!='DENIED'}">
					<input type="button" value="<spring:message code='viewMembershipHistory'/>" class="saveButton" onclick="MPower.gotoUrl('membershipList.htm?personId=${person.id}')"/>
				</c:if>
				<input type="button" value="<spring:message code='enterNewMembership'/>" class="saveButton" onclick="MPower.gotoUrl('membership.htm?personId=${person.id}')"/>
			</div>
			<c:forEach var="gift" items="${gifts}">
			<c:out value='${gift.transactionDate}'/> ... <c:out value='${gift.amount}'/><br />
			</c:forEach>
		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>