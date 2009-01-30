<%@ include file="/WEB-INF/jsp/include.jsp" %>
<tiles:insertDefinition name="base">
	<tiles:putAttribute name="browserTitle" value="View Pledge" />
	<tiles:putAttribute name="primaryNav" value="People" />
	<tiles:putAttribute name="secondaryNav" value="Search" />
	<tiles:putAttribute name="sidebarNav" value="Pledges" />
	<tiles:putAttribute name="mainContent" type="string">
		<div class="content760 mainForm">
			<mp:page pageName='pledgeView'/>
			<c:set var="person" value="${commitment.person}" scope="request" />
			<c:if test="${person.id != null}">
				<c:set var="viewingPerson" value="true" scope="request" />
			</c:if>
			
			<form:form method="post" commandName="commitment">
				<spring:message code='viewPledge' var="titleText" />
				<spring:message code='submitPledge' var="submitText" />
				<jsp:include page="../snippets/personHeader.jsp">
					<jsp:param name="currentFunctionTitleText" value="${titleText}" />
					<jsp:param name="submitButtonText" value="${submitText}" />
				</jsp:include>
				
			    <c:set var="gridCollectionName" value="distributionLines" />
				<c:set var="gridCollection" value="${commitment.distributionLines}" />
				<c:set var="paymentSource" value="${commitment.paymentSource}" />

				<h3 class="info"><spring:message code="thisPledgeEntered"/> <fmt:formatDate value="${commitment.createDate}"/>&nbsp;<spring:message code='at'/>&nbsp;<fmt:formatDate value="${commitment.createDate}" type="time" />.</h3>
				<div class="columns">
					<c:forEach var="sectionDefinition" items="${columnSections}">
							<%@ include file="/WEB-INF/jsp/snippets/fieldLayout.jsp" %>
					</c:forEach>
					<div class="clearColumns"></div>
				</div>
				
				<c:forEach var="sectionDefinition" items="${gridSections}">
					<c:if test="${!empty sectionDefinition.defaultLabel}">
						<h4 class="gridSectionHeader"><mp:sectionHeader sectionDefinition="${sectionDefinition}" /></h4>
					</c:if>
					<table class="tablesorter" id="<c:out value='${sectionDefinition.sectionHtmlName}'/>" cellspacing="0"> 
						<thead> 
							<c:forEach items="${gridCollection}" var="row" begin="0" end="0">
								<tr>
									<%@ include file="/WEB-INF/jsp/snippets/gridResultsHeader.jsp" %>
								</tr>
							</c:forEach>
						</thead>
						<tbody>
							<c:forEach items="${gridCollection}" var="row" varStatus="status">
								<c:if test="${row != null}">
									<tr>
										<%@ include file="/WEB-INF/jsp/snippets/gridResults.jsp" %>
									</tr>
								</c:if>
							</c:forEach>
						</tbody>
					</table>
				</c:forEach>
				<div class="formButtonFooter personFormButtons"><input type="submit" value="<spring:message code='submitPledge'/>" class="saveButton" /></div>
				
				
<%-- TODO: put back below
				<div class="formButtonFooter">
					<input type="button" value="<spring:message code='receiveGift'/>" class="saveButton" onclick="MPower.gotoUrl('gift.htm?personId=${person.id}&commitmentId=${commitment.id}')"/>
					<c:if test="${pageAccess['/pledgeList.htm']!='DENIED'}">
						<input type="button" value="<spring:message code='viewPledgeHistory'/>" class="saveButton" onclick="MPower.gotoUrl('pledgeList.htm?personId=${person.id}')"/>
					</c:if>
					<input type="button" value="<spring:message code='enterNewPledge'/>" class="saveButton" onclick="MPower.gotoUrl('pledge.htm?personId=${person.id}')"/>
				</div>
--%>				
			</form:form>

<%-- TODO: put back below
			<c:forEach var="gift" items="${gifts}">
				<c:out value='${gift.transactionDate}'/> ... <c:out value='${gift.amount}'/><br />
			</c:forEach>
--%>				
		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>