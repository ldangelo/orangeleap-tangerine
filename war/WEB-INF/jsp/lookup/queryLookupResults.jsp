<%@ include file="/WEB-INF/jsp/include.jsp"%>

<mp:page pageName='queryLookup' />
<c:choose>
	<c:when test="${empty results}">
		<div class="noResults"><spring:message code="searchNoResults"/></li>
	</c:when>
	<c:otherwise>
		<ul class="queryUl" id="queryResultsUl">
			<c:forEach var="sectionDefinition" items="${sectionDefinitions}">
				<c:if test="${sectionDefinition.sectionName eq queryLookup.sectionName}">
					<c:forEach items="${results}" var="row">
						<c:choose>
							<c:when test="${!empty row.id && !empty row.entityName}">
								<c:url value="/${row.entityName}.htm" var="entityLink" scope="page">
									<c:param name="id" value="${row.id}" />
								</c:url>
							</c:when>
							<c:otherwise>
								<c:set value="javascript:void(0)" var="entityLink" scope="page" />
							</c:otherwise>
						</c:choose>
						<li>
							<input type="radio" name="option" id="${row.id}" title="<spring:message code='clickToSelect'/>" value="<c:out value='${row.id}'/>" displayvalue="<c:out value='${row.displayValue}'/>" />
							<%@ include file="/WEB-INF/jsp/snippets/unformattedSectionFields.jsp" %> <a href="<c:out value='${entityLink}'/>" target="_blank"><img src="images/icons/link.png" alt="<spring:message code='gotoLink'/>" title="<spring:message code='gotoLink'/>"/></a>
						</li>
						<c:remove var="entityLink" scope="page" />
					</c:forEach>
				</c:if>
			</c:forEach>
		</ul>
	</c:otherwise>
</c:choose>