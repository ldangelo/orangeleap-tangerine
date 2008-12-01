<%@ include file="/WEB-INF/jsp/include.jsp"%>

<mp:page pageName='phone' />

<c:choose>
	<c:when test="${!empty phones}">
		<div class="searchResultsHeader">
			<h4 class="searchResults">Phones</h4>
		</div>
		<mp:page pageName='phone' />
		<c:forEach var="sectionDefinition" items="${sectionDefinitions}">
			<c:forEach items="${phones}" var="row">
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
							<c:otherwise>
								<p style="margin: 0;"><c:out value='${fieldVO.labelText}'/>:  <c:out value='${fieldVO.displayValue}'/></p>
							</c:otherwise>
						</c:choose>
					</c:if>
				</c:forEach>
				<a href="phoneManagerEdit.htm?phoneId=${row.id}&personId=${person.id}"">Edit</a>
				<a onclick="return(confirm('Are you sure you want to remove this phone?'));" href="phoneDelete.htm?phoneId=${row.id}&personId=${person.id}"">Remove</a>
				<hr />
			</c:forEach>
		</c:forEach>
	</c:when>
	<c:otherwise>
		<div class="searchResultsHeader">
			<h4 class="searchResults">Phones</h4>
		</div>
		<p style="margin: 8px 0 6px 0;">No phones have been entered for
		this person.</p>
	</c:otherwise>
</c:choose>

<c:choose>
	<c:when test="${!empty currentPhones}">
		<div class="searchResultsHeader">
			<h4 class="searchResults">Current Phones</h4>
		</div>
		<mp:page pageName='phone' />
		<c:forEach var="sectionDefinition" items="${sectionDefinitions}">
			<c:forEach items="${currentPhones}" var="row">
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
							<c:otherwise>
								<p style="margin: 0;"><c:out value='${fieldVO.labelText}'/>:  <c:out value='${fieldVO.displayValue}'/></p>
							</c:otherwise>
						</c:choose>
					</c:if>
				</c:forEach>
				<a href="phoneManagerEdit.htm?phoneId=${row.id}&personId=${person.id}"">Edit</a>
				<a onclick="return(confirm('Are you sure you want to remove this phone?'));" href="phoneDelete.htm?phoneId=${row.id}&personId=${person.id}"">Remove</a>
				<hr />
			</c:forEach>
		</c:forEach>
	</c:when>
	<c:otherwise>
		<div class="searchResultsHeader">
			<h4 class="searchResults">Current Phones</h4>
		</div>
		<p style="margin: 8px 0 6px 0;">There are no current phones for this person.</p>
	</c:otherwise>
</c:choose>

<c:choose>
	<c:when test="${!empty currentCorrespondencePhones}">
		<div class="searchResultsHeader">
			<h4 class="searchResults">Current Correspondence Phones</h4>
		</div>
		<mp:page pageName='phone' />
		<c:forEach var="sectionDefinition" items="${sectionDefinitions}">
			<c:forEach items="${currentCorrespondencePhones}" var="row">
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
							<c:otherwise>
								<p style="margin: 0;"><c:out value='${fieldVO.labelText}'/>:  <c:out value='${fieldVO.displayValue}'/></p>
							</c:otherwise>
						</c:choose>
					</c:if>
				</c:forEach>
				<a href="phoneManagerEdit.htm?phoneId=${row.id}&personId=${person.id}"">Edit</a>
				<a onclick="return(confirm('Are you sure you want to remove this phone?'));" href="phoneDelete.htm?phoneId=${row.id}&personId=${person.id}"">Remove</a>
				<hr />
			</c:forEach>
		</c:forEach>
	</c:when>
	<c:otherwise>
		<div class="searchResultsHeader">
			<h4 class="searchResults">Current Correspondence Phones</h4>
		</div>
		<p style="margin: 8px 0 6px 0;">There are no current correspondence phones for this person.</p>
	</c:otherwise>
</c:choose>
