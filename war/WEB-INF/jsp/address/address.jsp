<%@ include file="/WEB-INF/jsp/include.jsp"%>

<mp:page pageName='address' />

<c:choose>
	<c:when test="${!empty addresses}">
		<div class="searchResultsHeader">
			<h4 class="searchResults">Addresses</h4>
		</div>
		<mp:page pageName='address' />
		<c:forEach var="sectionDefinition" items="${sectionDefinitions}">
			<c:forEach items="${addresses}" var="row">
				<mp:section sectionDefinition="${sectionDefinition}" />
				<c:set var="totalFields" value="${sectionFieldCount}" />
				<c:forEach var="sectionField" items="${sectionFieldList}" varStatus="status">
					<mp:field sectionField='${sectionField}' sectionFieldList='${sectionFieldList}' model="${row}" />
					<p style="margin: 0;">
						<c:if test="${fieldVO.fieldValue!=null}">
							<c:choose>
								<c:when test="${fieldVO.fieldName=='creditCardExpiration'}">
									<c:out value='${fieldVO.labelText}'/>: <fmt:formatDate pattern="MM/yyyy" value="${fieldVO.fieldValue}" />
								</c:when>
								<c:when test="${fieldVO.fieldType == 'DATE'}">
									<c:out value='${fieldVO.labelText}'/>:  <fmt:formatDate pattern="MM/dd/yyyy" value="${fieldVO.fieldValue}" />
								</c:when>
								<c:otherwise>
									<c:out value='${fieldVO.labelText}'/>:  <c:out value='${fieldVO.displayValue}'/>
								</c:otherwise>
							</c:choose>
						</c:if>
					</p>
				</c:forEach>
				<a href="addressManagerEdit.htm?addressId=${row.id}&personId=${person.id}"">Edit</a>
				<a onclick="return(confirm('Are you sure you want to remove this address?'));" href="addressDelete.htm?addressId=${row.id}&personId=${person.id}"">Remove</a>
				<hr />
			</c:forEach>
		</c:forEach>
	</c:when>
	<c:otherwise>
		<div class="searchResultsHeader">
			<h4 class="searchResults">Addresses</h4>
		</div>
		<p style="margin: 8px 0 6px 0;">No addresses have been entered for
		this person.</p>
	</c:otherwise>
</c:choose>

<c:choose>
	<c:when test="${!empty currentAddresses}">
		<div class="searchResultsHeader">
			<h4 class="searchResults">Current Addresses</h4>
		</div>
		<mp:page pageName='address' />
		<c:forEach var="sectionDefinition" items="${sectionDefinitions}">
			<c:forEach items="${currentAddresses}" var="row">
				<mp:section sectionDefinition="${sectionDefinition}" />
				<c:set var="totalFields" value="${sectionFieldCount}" />
				<c:forEach var="sectionField" items="${sectionFieldList}" varStatus="status">
					<mp:field sectionField='${sectionField}' sectionFieldList='${sectionFieldList}' model="${row}" />
					<c:if test="${fieldVO.fieldValue!=null}">
						<c:choose>
							<c:when test="${fieldVO.fieldName=='creditCardExpiration'}">
								<div>
									<c:out value='${fieldVO.labelText}'/>: <fmt:formatDate pattern="MM/yyyy" value="${fieldVO.fieldValue}" />
								</div>
							</c:when>
							<c:when test="${fieldVO.fieldType == 'DATE'}">
								<div><c:out value='${fieldVO.labelText}'/>:  <fmt:formatDate pattern="MM/dd/yyyy" value="${fieldVO.fieldValue}" /></div>
							</c:when>
							<c:otherwise>
								<div><c:out value='${fieldVO.labelText}'/>:  <c:out value='${fieldVO.displayValue}'/></div>
							</c:otherwise>
						</c:choose>
					</c:if>
				</c:forEach>
				<a href="addressManagerEdit.htm?addressId=${row.id}&personId=${person.id}"">Edit</a>
				<a onclick="return(confirm('Are you sure you want to remove this address?'));" href="addressDelete.htm?addressId=${row.id}&personId=${person.id}"">Remove</a>
				<hr />
			</c:forEach>
		</c:forEach>
	</c:when>
	<c:otherwise>
		<div class="searchResultsHeader">
			<h4 class="searchResults">Current Addresses</h4>
		</div>
		<p style="margin: 8px 0 6px 0;">There are no current addresses for this person.</p>
	</c:otherwise>
</c:choose>

<c:choose>
	<c:when test="${!empty currentCorrespondenceAddresses}">
		<div class="searchResultsHeader">
			<h4 class="searchResults">Current Correspondence Addresses</h4>
		</div>
		<mp:page pageName='address' />
		<c:forEach var="sectionDefinition" items="${sectionDefinitions}">
			<c:forEach items="${currentCorrespondenceAddresses}" var="row">
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
				<a href="addressManagerEdit.htm?addressId=${row.id}&personId=${person.id}"">Edit</a>
				<a onclick="return(confirm('Are you sure you want to remove this address?'));" href="addressDelete.htm?addressId=${row.id}&personId=${person.id}"">Remove</a>
				<hr />
			</c:forEach>
		</c:forEach>
	</c:when>
	<c:otherwise>
		<div class="searchResultsHeader">
			<h4 class="searchResults">Current Correspondence Addresses</h4>
		</div>
		<p style="margin: 8px 0 6px 0;">There are no current correspondence addresses for this person.</p>
	</c:otherwise>
</c:choose>
