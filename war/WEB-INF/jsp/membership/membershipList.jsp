<%@ include file="/WEB-INF/jsp/include.jsp" %>
<tiles:insertDefinition name="base">
	<tiles:putAttribute name="browserTitle" value="Memberships" />
	<tiles:putAttribute name="primaryNav" value="People" />
	<tiles:putAttribute name="secondaryNav" value="Edit" />
	<tiles:putAttribute name="sidebarNav" value="Memberships" />
	<tiles:putAttribute name="mainContent" type="string">
		<mp:page pageName='membershipList' />
		<c:set var="constituent" value="${constituent}" scope="request" />
		<c:if test="${constituent.id!=null}">
			<c:set var="viewingConstituent" value="true" scope="request" />
		</c:if>
		<div class="content760 mainForm">

		<jsp:include page="../snippets/constituentHeader.jsp">
			<jsp:param name="currentFunctionTitleText" value="Memberships" />
		</jsp:include>

		<c:choose>
			<c:when test="${!empty commitmentList}">
				<div class="searchResultsHeader">
					<h4 class="searchResults">Memberships <strong>1 - ${commitmentListSize}</strong> of <strong>${commitmentListSize}</strong></h4>
				</div>

				<mp:page pageName='membershipList' />
				<c:forEach var="sectionDefinition" items="${sectionDefinitions}">
					<table id="membershipListTable" class="tablesorter" cellspacing="0" cellpadding="0">
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
									<td><a href="membershipView.htm?commitmentId=${row.id}">View</a></td>
									<%@ include file="/WEB-INF/jsp/snippets/gridResults.jsp" %>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</c:forEach>
				<p style="padding-top:12px;text-align:right;"><a class="newLink" href="membership.htm?constituentId=${constituent.id}">Enter a New Membership » </a></p>
			</c:when>
			<c:when test="${commitmentList ne null}">
				<p style="margin:8px 0 6px 0;">No memberships have been entered for this constituent.</p>
				<p>Would you like to <a href="membership.htm?constituentId=${constituent.id}">create a new membership</a>?</p>
			</c:when>
			<c:otherwise>
			</c:otherwise>
		</c:choose>

		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>