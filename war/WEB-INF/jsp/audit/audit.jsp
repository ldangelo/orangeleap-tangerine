<%@ include file="/WEB-INF/jsp/include.jsp"%>
<tiles:insertDefinition name="base">
	<tiles:putAttribute name="browserTitle" value="Site Audit" />
	<tiles:putAttribute name="primaryNav" value="People" />
	<tiles:putAttribute name="secondaryNav" value="Edit" />
	<tiles:putAttribute name="sidebarNav" value="Audit" />
	<tiles:putAttribute name="mainContent" type="string">
		<div class="content760 mainForm">
		<c:set var="person" value="${person}" scope="request" />
		<c:if test="${person.id!=null}">
			<c:set var="viewingPerson" value="true" scope="request" />
		</c:if>
		<jsp:include page="../snippets/personHeader.jsp">
			<jsp:param name="currentFunctionTitleText" value="Audit" />
		</jsp:include>
		<div class="searchResultsHeader">
			<h4 class="searchResults">Audit History</h4>
		</div>
		<c:choose>
			<c:when test="${!empty audits}">
				<table class="tablesorter defaultSort">
					<thead>
						<tr>
							<th>Date</th>
							<th>User</th>
							<th>Type</th>
							<th>Description</th>
							<th>Object Type</th>
							<th>Object ID</th>
							<th>Link</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${audits}" var="audit">
							<tr>
								<td><fmt:formatDate value="${audit.date}" pattern="MM-dd-yy h:mm a" /></td>
								<td><c:out value='${audit.user}'/></td>
								<td><c:out value='${audit.auditType}'/></td>
								<td><c:out value='${audit.description}'/></td>
								<td class="capitalized"><c:out value='${audit.entityType}'/></td>
								<td><c:out value='${audit.objectId}'/></td>
								<td><c:choose>
									<c:when test="${audit.entityType=='person'}">
										<a href="person.htm?personId=${audit.objectId}">View</a>
									</c:when>
									<c:when test="${audit.entityType=='gift'}">
										<a href="giftView.htm?giftId=${audit.objectId}&personId=${audit.person.id}">View</a>
									</c:when>
								</c:choose></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:when>
			<c:otherwise>
				<p>No audit history.</p>
			</c:otherwise>
		</c:choose></div>
	</tiles:putAttribute>
</tiles:insertDefinition>