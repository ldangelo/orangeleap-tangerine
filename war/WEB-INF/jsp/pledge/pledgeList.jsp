<%@ include file="/WEB-INF/jsp/include.jsp" %>
<spring:message code='pledges' var="titleText" />

<tiles:insertDefinition name="base">
	<tiles:putAttribute name="browserTitle" value="${titleText}" />
	<tiles:putAttribute name="primaryNav" value="People" />
	<tiles:putAttribute name="secondaryNav" value="Edit" />
	<tiles:putAttribute name="sidebarNav" value="Pledges" />
	<tiles:putAttribute name="mainContent" type="string">
		<mp:page pageName='pledgeList' />
		<c:set var="person" value="${person}" scope="request" />
		<c:if test="${person.id!=null}">
			<c:set var="viewingPerson" value="true" scope="request" />
		</c:if>
		<div class="content760 mainForm">

		<jsp:include page="../snippets/personHeader.jsp">
			<jsp:param name="currentFunctionTitleText" value="${titleText}" />
		</jsp:include>

		<c:choose>
			<c:when test="${!empty list}">
				<div class="searchResultsHeader">
					<h4 class="searchResults"><c:out value='${titleText}'/>&nbsp;<strong>1 - ${listSize}</strong>&nbsp;<spring:message code='of'/>&nbsp;<strong>${listSize}</strong></h4>
				</div>

				<mp:page pageName='pledgeList' />
				<c:forEach var="sectionDefinition" items="${sectionDefinitions}">
					<table id="pledgeListTable" class="tablesorter" cellspacing="0" cellpadding="0">
						<thead>
							<c:forEach items="${list}" var="row" begin="0" end="0">
								<tr>
									<th>&nbsp;</th>
									<%@ include file="/WEB-INF/jsp/snippets/gridResultsHeader.jsp" %>
								</tr>
							</c:forEach>
						</thead>
						<tbody>
							<c:forEach items="${list}" var="row">
								<c:choose>
									<c:when test="${empty row.gifts}">
										<tr>
											<td><a href="pledge.htm?commitmentId=${row.id}&personId=${row.person.id}"">View</a></td>
											<%@ include file="/WEB-INF/jsp/snippets/gridResults.jsp" %>
										</tr>
									</c:when>
									<c:otherwise>
										<tr>
											<td><a href="pledgeView.htm?commitmentId=${row.id}&personId=${row.person.id}"">View</a></td>
											<%@ include file="/WEB-INF/jsp/snippets/gridResults.jsp" %>
										</tr>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</tbody>
					</table>
				</c:forEach>
				<p style="padding-top:12px;text-align:right;"><a class="newLink" href="pledge.htm?personId=${person.id}"><spring:message code='enterNewPledge'/></a></p>
			</c:when>
			<c:when test="${list ne null}">
				<p style="margin:8px 0 6px 0;"><spring:message code='noPledgesEntered'/></p>
				<p><spring:message code='wouldYouLikeTo'/> <a href="pledge.htm?personId=${person.id}"> <spring:message code='createNewPledge'/></a>?</p>
			</c:when>
			<c:otherwise>
			</c:otherwise>
		</c:choose>

		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>