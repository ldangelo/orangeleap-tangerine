<%@ include file="/WEB-INF/jsp/include.jsp" %>
<c:choose>
<c:when test="${!empty giftList}">
	<c:set var="basePaginationUrl" value="giftSearch.htm" />
	<jsp:include page="../snippets/pagination.jsp"/>

	<mp:page pageName='giftSearchResults' />
	<c:forEach var="sectionDefinition" items="${sectionDefinitions}">
		<table class="tablesorter" cellspacing="0" cellpadding="0">
			<thead>
				<c:forEach items="${giftList}" var="row" begin="0" end="0">
					<tr>
						<th>&nbsp;</th>
						<%@ include file="/WEB-INF/jsp/snippets/gridResultsHeader.jsp" %>
					</tr>
				</c:forEach>
			</thead>
			<tbody>
				<c:forEach items="${pagedListHolder.pageList}" var="row">
					<tr>
						<td><a href="giftView.htm?giftId=${row.id}&constituentId=${row.constituent.id}"><spring:message code="view"/></a></td>
						<%@ include file="/WEB-INF/jsp/snippets/gridResults.jsp" %>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</c:forEach>

</c:when>
<c:when test="${giftList ne null}">
	<p style="margin:8px 0 6px 0;"><spring:message code='searchNoResults'/></p>
</c:when>
<c:otherwise>
</c:otherwise>
</c:choose>