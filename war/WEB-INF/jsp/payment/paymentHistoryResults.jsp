<%@ include file="/WEB-INF/jsp/include.jsp" %>
<c:choose>
	<c:when test="${!empty paymentHistoryList}">
	
		<c:set var="basePaginationUrl" value="/paymentHistory.htm?personId=${person.id}" />
		<jsp:include page="../snippets/pagination.jsp"/>
	
		<mp:page pageName='paymentHistory' />
		<c:forEach var="sectionDefinition" items="${sectionDefinitions}">
			<table class="tablesorter" cellspacing="0" cellpadding="0"> 
				<thead> 
					<c:forEach items="${paymentHistoryList}" var="row" begin="0" end="0">
						<tr>
							<th>&nbsp;</th>
							<%@ include file="/WEB-INF/jsp/snippets/gridResultsHeader.jsp" %>
						</tr>
					</c:forEach>
				</thead> 
				<tbody> 
					<c:forEach items="${pagedListHolder.pageList}" var="row">
						<tr>
							<td></td>
							<%@ include file="/WEB-INF/jsp/snippets/gridResults.jsp" %>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			
		</c:forEach>
	</c:when>
	<c:when test="${paymentHistoryList ne null}">
		<p style="margin:8px 0 6px 0;">No payments have been entered for this person.</p>
	</c:when>
	<c:otherwise>
	</c:otherwise>
</c:choose>
