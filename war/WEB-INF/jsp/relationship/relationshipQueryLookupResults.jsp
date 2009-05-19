<%@ include file="/WEB-INF/jsp/include.jsp"%>

<c:choose>
	<c:when test="${empty results}">
		<div class="noResults"><spring:message code="searchNoResults"/></li>
	</c:when>
	<c:otherwise>
		<ul class="queryUl" id="queryResultsUl">
			<c:forEach items="${results}" var="row">
				<li id="<c:out value='${row.id}'/>-li">
					<input type="radio" name="option" id="${row.id}" title="<spring:message code='clickToSelect'/>" value="<c:out value='${row.id}'/>" displayvalue="<c:out value='${row.displayValue}'/>" />
					<c:out value='${row.displayValue}'/> <a href="person.htm?personId=${row.id}" target="_blank"><img src="images/icons/link.png" alt="<spring:message code='gotoLink'/>" title="<spring:message code='gotoLink'/>"/></a>
				</li>
				<c:remove var="entityLink" scope="page" />
			</c:forEach>
		</ul>
	</c:otherwise>
</c:choose>