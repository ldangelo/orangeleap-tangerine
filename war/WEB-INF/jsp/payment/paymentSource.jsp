<%@ include file="/WEB-INF/jsp/include.jsp" %>
<mp:page pageName='paymentSource' />
<c:choose>
	<c:when test="${!empty paymentSources}">
		<div class="searchResultsHeader">
			<h4 class="searchResults"><spring:message code='paymentSources'/></h4>
		</div>
		<mp:page pageName='paymentSource' />
		<c:forEach var="sectionDefinition" items="${sectionDefinitions}">
			<c:forEach items="${paymentSources}" var="row">
				<mp:section sectionDefinition="${sectionDefinition}"/>
				<c:set var="totalFields" value="${sectionFieldCount}"/>
				<c:forEach var="sectionField" items="${sectionFieldList}" varStatus="status">
					<mp:field sectionField='${sectionField}' sectionFieldList='${sectionFieldList}' model="${row}"/>
					<c:if test="${fieldVO.fieldValue != null}">
						<c:choose>
							<c:when test="${fieldVO.fieldName == 'creditCardExpiration'}">
								<p style="margin:0;"><c:out value='${fieldVO.labelText}'/>: <fmt:formatDate pattern="MM/yyyy" value="${fieldVO.fieldValue}" /></p>
							</c:when>
							<c:otherwise>
								<p style="margin:0;"><c:out value='${fieldVO.labelText}'/>: <c:out value='${fieldVO.displayValue}'/></p>
							</c:otherwise>
						</c:choose>
					</c:if>
				</c:forEach>
				<a href="paymentManagerEdit.htm?paymentSourceId=${row.id}&personId=${person.id}""><spring:message code='edit'/></a>
				<a onclick="return(confirm('<spring:message code='confirmRemovePaymentSource'/>'));" href="paymentSourceDelete.htm?paymentSourceId=${row.id}&personId=${person.id}""><spring:message code='remove'/></a>
				<hr />

			</c:forEach>
		</c:forEach>
	</c:when>
	<c:when test="${paymentSources ne null}">
		<p style="margin:8px 0 6px 0;"><spring:message code='noPaymentSources'/></p>
	</c:when>
	<c:otherwise>
	</c:otherwise>
</c:choose>
