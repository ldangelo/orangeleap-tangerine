<%@ include file="/WEB-INF/jsp/include.jsp" %>
<tiles:insertDefinition name="base">
	<tiles:putAttribute name="browserTitle" value="Commitment History" />
	<tiles:putAttribute name="primaryNav" value="People" />
	<tiles:putAttribute name="secondaryNav" value="Edit" />
	<tiles:putAttribute name="sidebarNav" value="Commitments" />
	<tiles:putAttribute name="mainContent" type="string">
		<mp:page pageName='commitmentList' />
		<c:set var="person" value="${person}" scope="request" />
		<c:if test="${person.id!=null}">
			<c:set var="viewingPerson" value="true" scope="request" />
		</c:if>
		<div class="content760 mainForm">

		<jsp:include page="snippets/personHeader.jsp">
			<jsp:param name="currentFunctionTitleText" value="Commitments" />
		</jsp:include>

		<c:choose>
			<c:when test="${!empty commitmentList}">
				<div class="searchResultsHeader">
					<h4 class="searchResults">Commitments <strong>1 - ${commitmentListSize}</strong> of <strong>${commitmentListSize}</strong></h4>
				</div>

				<mp:page pageName='commitmentList' />
				<c:forEach var="sectionDefinition" items="${sectionDefinitions}">
					<table id="commitmentListTable" class="tablesorter" cellspacing="0" cellpadding="0">
						<thead>
							<c:forEach items="${commitmentList}" var="row" begin="0" end="0">
								<tr>
									<th>&nbsp;</th>
									<%@ include file="/WEB-INF/jsp/snippets/gridResultsHeader.jsp" %>
								</tr>
							</c:forEach>
						</thead>
						<tbody>
							<c:forEach items="${commitmentList}" var="row">
								<tr>
									<td><a href="commitmentView.htm?commitmentId=${row.id}">View</a></td>
									<%@ include file="/WEB-INF/jsp/snippets/gridResults.jsp" %>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</c:forEach>
				<p style="padding-top:12px;text-align:right;"><a class="newLink" href="commitment.htm?personId=${person.id}">Enter a New Commitment » </a></p>
			</c:when>
			<c:when test="${commitmentList ne null}">
				<p style="margin:8px 0 6px 0;">No commitments have been entered for this person.</p>
				<p>Would you like to <a href="commitment.htm?personId=${person.id}">create a new commitment</a>?</p>
			</c:when>
			<c:otherwise>
			</c:otherwise>
		</c:choose>

		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>