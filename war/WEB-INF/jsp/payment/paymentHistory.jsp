<%@ include file="/WEB-INF/jsp/include.jsp" %>
<tiles:insertDefinition name="base">
	<tiles:putAttribute name="browserTitle" value="Payment History" />
	<tiles:putAttribute name="primaryNav" value="People" />
	<tiles:putAttribute name="secondaryNav" value="Edit" />
	<tiles:putAttribute name="sidebarNav" value="Payment Manager" />
	<tiles:putAttribute name="mainContent" type="string">
		<mp:page pageName='paymentHistory' />
		<c:set var="person" value="${person}" scope="request" />
		
		<c:set var="sortLink" value="paymentHistory.htm?personId=${person.id}" scope="request" />
		
		<c:if test="${person.id!=null}">
			<c:set var="viewingPerson" value="true" scope="request" />
		</c:if>
		<div class="content760 mainForm">

		<jsp:include page="../snippets/personHeader.jsp">
			<jsp:param name="currentFunctionTitleText" value="Payment History" />
		</jsp:include>
			
		<c:choose>
			<c:when test="${!empty paymentHistoryList}">
				<div class="searchResultsHeader">
					<h4 class="searchResults">Payments <strong>1 - ${paymentHistoryListSize}</strong> of <strong>${paymentHistoryListSize}</strong></h4>
				</div>

				<mp:page pageName='paymentHistory' />
				<c:forEach var="sectionDefinition" items="${sectionDefinitions}">
					<table id="paymentHistoryListTable" class="tablesorter" cellspacing="0" cellpadding="0">
						<thead>
							<c:forEach items="${paymentHistoryList}" var="row" begin="0" end="0">
								<tr>
									<th>&nbsp;</th>
									<%@ include file="/WEB-INF/jsp/snippets/gridResultsHeader.jsp" %>
								</tr>
							</c:forEach>
						</thead>
						<tbody>
							<c:forEach items="${paymentHistoryList}" var="row">
								<tr>
								    <td></td>
									<%@ include file="/WEB-INF/jsp/snippets/gridResults.jsp" %>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</c:forEach>
			</c:when>
			<c:when test="${paymentHistoryList ne null}">
				<p style="margin:8px 0 6px 0;">No payments have been entered for this person.</p>
			</c:when>
			<c:otherwise>
			</c:otherwise>
		</c:choose>

		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>