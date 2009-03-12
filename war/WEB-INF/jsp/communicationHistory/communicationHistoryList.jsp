<%@ include file="/WEB-INF/jsp/include.jsp" %>
<tiles:insertDefinition name="base">
	<tiles:putAttribute name="browserTitle" value="Journal Entries" />
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

		<spring:message code='journalEntries' var="titleText" />
		<jsp:include page="../snippets/personHeader.jsp">
			<jsp:param name="currentFunctionTitleText" value="${titleText}" />
		</jsp:include>
			
		<c:choose>
			<c:when test="${!empty communicationHistoryList}">
				<div class="searchResultsHeader">
					<h4 class="searchResults"><spring:message code='journalEntries'/> <strong>1 - ${communicationHistoryListSize}</strong> <spring:message code='of'/> <strong>${communicationHistoryListSize}</strong></h4>
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
									<td><a href="communicationHistoryView.htm?communicationHistoryId=${row.id}&personId=${row.person.id}"><spring:message code='view'/></a></td>
									<%@ include file="/WEB-INF/jsp/snippets/gridResults.jsp" %>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</c:forEach>
				<p class="enterNew"><a class="newLink" href="communicationHistory.htm?personId=${person.id}"><spring:message code='enterNewJournal'/></a></p>
			</c:when>
			<c:when test="${communicationHistoryList ne null}">
				<p style="margin:8px 0 6px 0;"><spring:message code='noJournalEntriesEntered'/></p>
				<p><spring:message code='wouldYouLikeTo'/> <a href="communicationHistory.htm?personId=${person.id}"><spring:message code='createNewJournalEntry'/></a>?</p>
			</c:when>
			<c:otherwise>
			</c:otherwise>
		</c:choose>

		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>