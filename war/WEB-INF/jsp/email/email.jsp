<%@ include file="/WEB-INF/jsp/include.jsp"%>

<mp:page pageName='email' />

<c:choose>
	<c:when test="${!empty emails}">
		<div class="searchResultsHeader">
			<h4 class="searchResults">Emails</h4>
		</div>
		<mp:page pageName='email' />
		<c:forEach var="sectionDefinition" items="${sectionDefinitions}">
			<c:forEach items="${emails}" var="row">
				<mp:section sectionDefinition="${sectionDefinition}" />
				<c:set var="totalFields" value="${sectionFieldCount}" />
				<c:forEach var="sectionField" items="${sectionFieldList}" varStatus="status">
					<mp:field sectionField='${sectionField}' sectionFieldList='${sectionFieldList}' model="${row}" />
					<c:if test="${fieldVO.fieldValue!=null}">
						<c:choose>
							<c:when test="${fieldVO.fieldName=='creditCardExpiration'}">
								<p style="margin: 0;">
									<c:out value='${fieldVO.labelText}'/>: <fmt:formatDate pattern="MM/yyyy" value="${fieldVO.fieldValue}" />
								</p>
							</c:when>
							<c:when test="${fieldVO.fieldType == 'DATE'}">
								<p style="margin: 0;">
									<c:out value='${fieldVO.labelText}'/>:  <fmt:formatDate pattern="MM/dd/yyyy" value="${fieldVO.fieldValue}" />
								</p>
							</c:when>
							<c:otherwise>
								<p style="margin: 0;"><c:out value='${fieldVO.labelText}'/>:  <c:out value='${fieldVO.displayValue}'/></p>
							</c:otherwise>
						</c:choose>
					</c:if>
				</c:forEach>
				<a href="emailManagerEdit.htm?emailId=${row.id}&personId=${person.id}"">Edit</a>
				<a onclick="return(confirm('Are you sure you want to remove this email?'));" href="emailDelete.htm?emailId=${row.id}&personId=${person.id}"">Remove</a>
				<hr />
			</c:forEach>
		</c:forEach>
	</c:when>
	<c:otherwise>
		<div class="searchResultsHeader">
			<h4 class="searchResults">Emails</h4>
		</div>
		<p style="margin: 8px 0 6px 0;">No emails have been entered for
		this person.</p>
	</c:otherwise>
</c:choose>

<c:choose>
	<c:when test="${!empty currentEmails}">
		<div class="searchResultsHeader">
			<h4 class="searchResults">Current Emails</h4>
		</div>
		<mp:page pageName='email' />
		<c:forEach var="sectionDefinition" items="${sectionDefinitions}">
			<c:forEach items="${currentEmails}" var="row">
				<mp:section sectionDefinition="${sectionDefinition}" />
				<c:set var="totalFields" value="${sectionFieldCount}" />
				<c:forEach var="sectionField" items="${sectionFieldList}" varStatus="status">
					<mp:field sectionField='${sectionField}' sectionFieldList='${sectionFieldList}' model="${row}" />
					<c:if test="${fieldVO.fieldValue!=null}">
						<c:choose>
							<c:when test="${fieldVO.fieldName=='creditCardExpiration'}">
								<p style="margin: 0;">
									<c:out value='${fieldVO.labelText}'/>: <fmt:formatDate pattern="MM/yyyy" value="${fieldVO.fieldValue}" />
								</p>
							</c:when>
							<c:when test="${fieldVO.fieldType == 'DATE'}">
								<p style="margin: 0;">
									<c:out value='${fieldVO.labelText}'/>:  <fmt:formatDate pattern="MM/dd/yyyy" value="${fieldVO.fieldValue}" />
								</p>
							</c:when>
							<c:otherwise>
								<p style="margin: 0;"><c:out value='${fieldVO.labelText}'/>:  <c:out value='${fieldVO.displayValue}'/></p>
							</c:otherwise>
						</c:choose>
					</c:if>
				</c:forEach>
				<a href="emailManagerEdit.htm?emailId=${row.id}&personId=${person.id}"">Edit</a>
				<a onclick="return(confirm('Are you sure you want to remove this email?'));" href="emailDelete.htm?emailId=${row.id}&personId=${person.id}"">Remove</a>
				<hr />
			</c:forEach>
		</c:forEach>
	</c:when>
	<c:otherwise>
		<div class="searchResultsHeader">
			<h4 class="searchResults">Current Emails</h4>
		</div>
		<p style="margin: 8px 0 6px 0;">There are no current emails for this person.</p>
	</c:otherwise>
</c:choose>

<c:choose>
	<c:when test="${!empty currentCorrespondenceEmails}">
		<div class="searchResultsHeader">
			<h4 class="searchResults">Current Correspondence Emails</h4>
		</div>
		<mp:page pageName='email' />
		<c:forEach var="sectionDefinition" items="${sectionDefinitions}">
			<c:forEach items="${currentCorrespondenceEmails}" var="row">
				<mp:section sectionDefinition="${sectionDefinition}" />
				<c:set var="totalFields" value="${sectionFieldCount}" />
				<c:forEach var="sectionField" items="${sectionFieldList}" varStatus="status">
					<mp:field sectionField='${sectionField}' sectionFieldList='${sectionFieldList}' model="${row}" />
					<c:if test="${fieldVO.fieldValue!=null}">
						<c:choose>
							<c:when test="${fieldVO.fieldName=='creditCardExpiration'}">
								<p style="margin: 0;">
									<c:out value='${fieldVO.labelText}'/>: <fmt:formatDate pattern="MM/yyyy" value="${fieldVO.fieldValue}" />
								</p>
							</c:when>
							<c:when test="${fieldVO.fieldType == 'DATE'}">
								<p style="margin: 0;">
									<c:out value='${fieldVO.labelText}'/>:  <fmt:formatDate pattern="MM/dd/yyyy" value="${fieldVO.fieldValue}" />
								</p>
							</c:when>
							<c:otherwise>
								<p style="margin: 0;"><c:out value='${fieldVO.labelText}'/>:  <c:out value='${fieldVO.displayValue}'/></p>
							</c:otherwise>
						</c:choose>
					</c:if>
				</c:forEach>
				<a href="emailManagerEdit.htm?emailId=${row.id}&personId=${person.id}"">Edit</a>
				<a onclick="return(confirm('Are you sure you want to remove this email?'));" href="emailDelete.htm?emailId=${row.id}&personId=${person.id}"">Remove</a>
				<hr />
			</c:forEach>
		</c:forEach>
	</c:when>
	<c:otherwise>
		<div class="searchResultsHeader">
			<h4 class="searchResults">Current Correspondence Emails</h4>
		</div>
		<p style="margin: 8px 0 6px 0;">There are no current correspondence emails for this person.</p>
	</c:otherwise>
</c:choose>
