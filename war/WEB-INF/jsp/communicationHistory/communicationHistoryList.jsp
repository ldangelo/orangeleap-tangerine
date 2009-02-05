<%@ include file="/WEB-INF/jsp/include.jsp" %>
<tiles:insertDefinition name="base">
	<tiles:putAttribute name="browserTitle" value="Communication History Entries" />
	<tiles:putAttribute name="primaryNav" value="People" />
	<tiles:putAttribute name="secondaryNav" value="Edit" />
	<tiles:putAttribute name="sidebarNav" value="communicationHistoryList" />
	<tiles:putAttribute name="mainContent" type="string">
		<mp:page pageName='communicationHistoryList' />
		<c:set var="person" value="${person}" scope="request" />
		<c:if test="${person.id!=null}">
			<c:set var="viewingPerson" value="true" scope="request" />
		</c:if>
		<div class="content760 mainForm">

		<jsp:include page="../snippets/personHeader.jsp">
			<jsp:param name="currentFunctionTitleText" value="Communication History" />
		</jsp:include>
			
		<c:choose>
			<c:when test="${!empty communicationHistoryList}">
				<div class="searchResultsHeader">
					<h4 class="searchResults">CommunicationHistory Entries <strong>1 - ${communicationHistoryListSize}</strong> of <strong>${communicationHistoryListSize}</strong></h4>
				</div>

				<mp:page pageName='communicationHistoryList' />
				<c:forEach var="sectionDefinition" items="${sectionDefinitions}">
					<table id="communicationHistoryListTable" class="tablesorter" cellspacing="0" cellpadding="0">
						<thead>
							<c:forEach items="${communicationHistoryList}" var="row" begin="0" end="0">
								<tr>
									<th>&nbsp;</th>
									<%@ include file="/WEB-INF/jsp/snippets/gridResultsHeader.jsp" %>
								</tr>
							</c:forEach>
						</thead>
						<tbody>
							<c:forEach items="${communicationHistoryList}" var="row">
								<tr>
									<td><a href="communicationHistoryView.htm?communicationHistoryId=${row.id}&personId=${row.person.id}">View</a></td>
									<%@ include file="/WEB-INF/jsp/snippets/gridResults.jsp" %>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</c:forEach>
				<p style="padding-top:12px;text-align:right;"><a class="newLink" href="communicationHistory.htm?personId=${person.id}">Enter a New Communication Entry » </a></p>
			</c:when>
			<c:when test="${communicationHistoryList ne null}">
				<p style="margin:8px 0 6px 0;">No communication history entries have been created for this person.</p>
				<p>Would you like to <a href="communicationHistory.htm?personId=${person.id}">create a new communication history entry?</a>?</p>
			</c:when>
			<c:otherwise>
			</c:otherwise>
		</c:choose>

		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>