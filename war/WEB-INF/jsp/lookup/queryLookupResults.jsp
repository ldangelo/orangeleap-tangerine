<%@ include file="/WEB-INF/jsp/include.jsp"%>

<c:choose>
	<c:when test="${empty results}">
		<div class="noResults"><spring:message code="searchNoResults"/></div>
	</c:when>
	<c:otherwise>
		<ul class="queryUl" id="queryResultsUl">
            <c:forEach items="${results}" var="row">
                <c:choose>
                    <c:when test="${!empty row['id'] && !empty row['entityName']}">
                        <c:url value="${row['entityName']}.htm" var="entityLink" scope="page">
                            <c:param name="id" value="${row['id']}" />
                        </c:url>
                    </c:when>
                    <c:otherwise>
                        <c:set value="javascript:void(0)" var="entityLink" scope="page" />
                    </c:otherwise>
                </c:choose>
                <li id="<c:out value='${row["id"]}'/>-li">
                    <input type="radio" name="option" id="${row['id']}" title="<spring:message code='clickToSelect'/>" value="<c:out value='${row["id"]}'/>" displayvalue="<c:out value='${row["displayValue"]}'/>" />
                    <c:out value="${row['accountName']}"/> <a href="<c:out value='${entityLink}'/>" target="_blank"><img src="images/icons/link.png" alt="<spring:message code='gotoLink'/>" title="<spring:message code='gotoLink'/>"/></a>
                </li>
                <c:remove var="entityLink" scope="page" />
            </c:forEach>
		</ul>
	</c:otherwise>
</c:choose>