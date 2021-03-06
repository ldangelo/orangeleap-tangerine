<%@ include file="/WEB-INF/jsp/include.jsp"%>

<c:choose>
	<c:when test="${empty results}">
		<script type="text/javascript">
			$("#multiQueryLookupNoResultsDiv").removeClass("noDisplay");
		</script>
	</c:when>
	<c:otherwise>
        <c:set var="counter" value="0"/>
        <c:forEach items="${results}" var="row">
            <c:set target="${requestScope.selectedIds}" property="idToCheck" value="${row['id']}"/>
            <c:if test="${!requestScope.selectedIds.checkSelectedId}">
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
                <li id="<c:out value='${row["id"]}'/>-li" title='<c:out value='${row["displayValue"]}'/>'>
                    <input type="checkbox" name="option${counter}" id="${row['id']}" title="<spring:message code='clickToSelect'/>" displayvalue="<c:out value='${row["displayValue"]}'/>" />
                    <c:out value="${row['displayValue']}"/> 
                    <a href="<c:out value='${entityLink}'/>" target="_blank"><img src="images/icons/link.png" alt="<spring:message code='gotoLink'/>" title="<spring:message code='gotoLink'/>"/></a>
                </li>
                <c:remove var="entityLink" scope="page" />
                <c:set var="counter" value="${counter + 1}"/>
            </c:if>
        </c:forEach>
        <script type="text/javascript">
            $("#multiQueryLookupNoResultsDiv").addClass("noDisplay");
            $("#multiQueryLookupResultsTable").removeClass("noDisplay");
        </script>
	</c:otherwise>
</c:choose>