<%@ include file="/WEB-INF/jsp/include.jsp"%>
<tiles:insertDefinition name="base">
	<tiles:putAttribute name="browserTitle" value="Site Audit" />
	<tiles:putAttribute name="primaryNav" value="Administration" />
	<tiles:putAttribute name="secondaryNav" value="Auditing" />
	<tiles:putAttribute name="mainContent" type="string">
		<div class="content760 mainForm">
		<h1>Sitewide Audit History</h1>
		<c:choose>
			<c:when test="${!empty audits}">
				<table class="tablesorter defaultSort">
					<thead>
						<tr>
							<th>Date</th>
							<th>User</th>
							<th>Type</th>
							<th>Description</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${audits}" var="audit">
							<tr>
								<td><fmt:formatDate value="${audit.date}" pattern="MM-dd-yy h:mm a" /></td>
								<td>${audit.user}</td>
								<td>${audit.auditType}</td>
								<td>${audit.description}</td>
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