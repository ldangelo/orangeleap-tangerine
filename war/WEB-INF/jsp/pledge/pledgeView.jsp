<%@ include file="/WEB-INF/jsp/include.jsp" %>
<tiles:insertDefinition name="base">
	<tiles:putAttribute name="browserTitle" value="View Pledge" />
	<tiles:putAttribute name="primaryNav" value="People" />
	<tiles:putAttribute name="secondaryNav" value="Search" />
	<tiles:putAttribute name="sidebarNav" value="" />
	<tiles:putAttribute name="mainContent" type="string">
		<div class="content760 mainForm">
			<mp:page pageName='pledgeView'/>
			<c:set var="person" value="${commitment.person}" scope="request" />
			<c:if test="${person.id!=null}">
				<c:set var="viewingPerson" value="true" scope="request" />
			</c:if>
			<form:form method="post" commandName="commitment">

				<jsp:include page="../snippets/personHeader.jsp">
					<jsp:param name="currentFunctionTitleText" value="View Pledge" />
				</jsp:include>
				
			    <c:set var="gridCollectionName" value="distributionLines" />
				<c:set var="gridCollection" value="${commitment.distributionLines}" />
				<c:set var="paymentSource" value="${commitment.paymentSource}" />

				

				<h3 class="info">This pledge was entered on <fmt:formatDate value="${commitment.createDate}"/> at <fmt:formatDate value="${commitment.createDate}" type="time" />.</h3>
					<div class="columns">
						<c:forEach var="sectionDefinition" items="${sectionDefinitions}" begin="0" end="0">
								<%@ include file="/WEB-INF/jsp/snippets/fieldLayout.jsp" %>
						</c:forEach>
						<div class="clearColumns"></div>
					</div>
				<div class="formButtonFooter personFormButtons">
					<input type="submit" value="Save Changes" class="saveButton" />
				</div>
				<div class="formButtonFooter">
					<input type="button" value="<spring:message code='receiveGift'/>" class="saveButton" onclick="MPower.gotoUrl('gift.htm?personId=${person.id}&commitmentId=${commitment.id}')"/>
					<c:if test="${pageAccess['/pledgeList.htm']!='DENIED'}">
						<input type="button" value="<spring:message code='viewPledgeHistory'/>" class="saveButton" onclick="MPower.gotoUrl('pledgeList.htm?personId=${person.id}')"/>
					</c:if>
					<input type="button" value="<spring:message code='enterNewPledge'/>" class="saveButton" onclick="MPower.gotoUrl('pledge.htm?personId=${person.id}')"/>
				</div>
			</form:form>

			<c:forEach var="gift" items="${gifts}">
				<c:out value='${gift.transactionDate}'/> ... <c:out value='${gift.amount}'/><br />
			</c:forEach>
		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>