<%@ include file="/WEB-INF/jsp/include.jsp"%>

<mp:page pageName='phone' />

<c:choose>
	<c:when test="${!empty phones}">
		<div class="searchResultsHeader">
			<h4 class="searchResults"><spring:message code='phones'/></h4>
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
							<c:when test="${fieldVO.fieldType == 'DATE'}">
								 <c:choose>
                                    <c:when test="${fieldVO.fieldName =='seasonalStartDate' || fieldVO.fieldName == 'seasonalEndDate'}">
                                        <p style="margin: 0;">
                                            <c:out value='${fieldVO.labelText}'/>: <fmt:formatDate pattern="MMM-dd" value="${fieldVO.fieldValue}" />
                                        </p>
                                    </c:when>
                                    <c:otherwise>
                                         <p style="margin: 0;">
									        <c:out value='${fieldVO.labelText}'/>:  <fmt:formatDate pattern="MM/dd/yyyy" value="${fieldVO.fieldValue}" />
								        </p>
                                    </c:otherwise>
								</c:choose>
							</c:when>
							<c:otherwise>
								<p style="margin: 0;"><c:out value='${fieldVO.labelText}'/>:  <c:out value='${fieldVO.displayValue}'/></p>
							</c:otherwise>
						</c:choose>
					</c:if>
				</c:forEach>
				<a href="phoneManagerEdit.htm?phoneId=${row.id}&personId=${person.id}""><spring:message code='edit'/></a>
				<hr />
			</c:forEach>
		</c:forEach>
	</c:when>
	<c:otherwise>
		<div class="searchResultsHeader">
			<h4 class="searchResults"><spring:message code='phones'/></h4>
		</div>
		<p style="margin: 8px 0 6px 0;"><spring:message code='noPhonesEntered'/></p>
	</c:otherwise>
</c:choose>

<c:choose>
	<c:when test="${!empty currentPhones}">
		<div class="searchResultsHeader">
			<h4 class="searchResults"><spring:message code='currentPhones'/></h4>
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
							<c:when test="${fieldVO.fieldType == 'DATE'}">
								<c:choose>
                                    <c:when test="${fieldVO.fieldName =='seasonalStartDate' || fieldVO.fieldName == 'seasonalEndDate'}">
                                        <p style="margin: 0;">
                                            <c:out value='${fieldVO.labelText}'/>: <fmt:formatDate pattern="MMM-dd" value="${fieldVO.fieldValue}" />
                                        </p>
                                    </c:when>
                                    <c:otherwise>
                                         <p style="margin: 0;">
									        <c:out value='${fieldVO.labelText}'/>:  <fmt:formatDate pattern="MM/dd/yyyy" value="${fieldVO.fieldValue}" />
								        </p>
                                    </c:otherwise>
								</c:choose>
							</c:when>
							<c:otherwise>
								<p style="margin: 0;"><c:out value='${fieldVO.labelText}'/>:  <c:out value='${fieldVO.displayValue}'/></p>
							</c:otherwise>
						</c:choose>
					</c:if>
				</c:forEach>
				<a href="phoneManagerEdit.htm?phoneId=${row.id}&personId=${person.id}""><spring:message code='edit'/></a>
				<hr />
			</c:forEach>
		</c:forEach>
	</c:when>
	<c:otherwise>
		<div class="searchResultsHeader">
			<h4 class="searchResults"><spring:message code='currentPhones'/></h4>
		</div>
		<p style="margin: 8px 0 6px 0;"><spring:message code='noCurrentPhonesEntered'/></p>
	</c:otherwise>
</c:choose>

<c:choose>
	<c:when test="${!empty currentCorrespondencePhones}">
		<div class="searchResultsHeader">
			<h4 class="searchResults"><spring:message code='currentCorrespondencePhones'/></h4>
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
				<a href="phoneManagerEdit.htm?phoneId=${row.id}&personId=${person.id}""><spring:message code='edit'/></a>
				<hr />
			</c:forEach>
		</c:forEach>
	</c:when>
	<c:otherwise>
		<div class="searchResultsHeader">
			<h4 class="searchResults"><spring:message code='currentCorrespondencePhones'/></h4>
		</div>
		<p style="margin: 8px 0 6px 0;"><spring:message code='noCurrentCorrespondencePhonesEntered'/></p>
	</c:otherwise>
</c:choose>
