<%@ include file="/WEB-INF/jsp/include.jsp" %>
<tiles:insertDefinition name="base">
	<tiles:putAttribute name="browserTitle" value="Recurring Gifts" />
	<tiles:putAttribute name="primaryNav" value="People" />
	<tiles:putAttribute name="secondaryNav" value="Edit" />
	<tiles:putAttribute name="sidebarNav" value="Recurring Gifts" />
	<tiles:putAttribute name="mainContent" type="string">
		<mp:page pageName='recurringGiftList' />
		<c:set var="person" value="${person}" scope="request" />
		<c:if test="${person.id!=null}">
			<c:set var="viewingPerson" value="true" scope="request" />
		</c:if>
		<div class="content760 mainForm">

		<jsp:include page="../snippets/personHeader.jsp">
			<jsp:param name="currentFunctionTitleText" value="Recurring Gifts" />
		</jsp:include>

		<c:choose>
			<c:when test="${!empty commitmentList}">
				<div class="searchResultsHeader">
					<h4 class="searchResults">Recurring Gifts <strong>1 - ${commitmentListSize}</strong> of <strong>${commitmentListSize}</strong></h4>
				</div>

				<mp:page pageName='recurringGiftList' />
				<c:forEach var="sectionDefinition" items="${sectionDefinitions}">
					<table id="recurringGiftListTable" class="tablesorter" cellspacing="0" cellpadding="0">
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
								<c:choose>
									<c:when test="${empty row.gifts}">
										<tr>
											<td><a href="recurringGift.htm?commitmentId=${row.id}&personId=${row.person.id}">View</a></td>
											<%@ include file="/WEB-INF/jsp/snippets/gridResults.jsp" %>
										</tr>
									</c:when>
									<c:otherwise>
										<tr>
											<td><a href="recurringGiftView.htm?commitmentId=${row.id}&personId=${row.person.id}">View</a></td>
											<%@ include file="/WEB-INF/jsp/snippets/gridResults.jsp" %>
										</tr>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</tbody>
					</table>
				</c:forEach>
				<p style="padding-top:12px;text-align:right;"><a class="newLink" href="recurringGift.htm?personId=${person.id}">Enter a New Recurring Gift » </a></p>
			</c:when>
			<c:when test="${commitmentList ne null}">
				<p style="margin:8px 0 6px 0;">No recurring gift have been entered for this person.</p>
				<p>Would you like to <a href="recurringGift.htm?personId=${person.id}">create a new recurring gift</a>?</p>
			</c:when>
			<c:otherwise>
			</c:otherwise>
		</c:choose>

		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>