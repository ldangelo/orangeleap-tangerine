<%@ include file="/WEB-INF/jsp/include.jsp" %>
<c:choose>
	<c:when test="${!empty constituentList}">
	
		<c:set var="basePaginationUrl" value="constituentSearch.htm" />
		<jsp:include page="../snippets/pagination.jsp"/>
	
		<mp:page pageName='constituentSearchResults' />
		<c:forEach var="sectionDefinition" items="${sectionDefinitions}">
			<table class="tablesorter" cellspacing="0" cellpadding="0"> 
				<thead> 
					<c:forEach items="${constituentList}" var="row" begin="0" end="0">
						<tr>
							<th>&nbsp;</th>
							<%@ include file="/WEB-INF/jsp/snippets/gridResultsHeader.jsp" %>
						</tr>
					</c:forEach>
				</thead> 
				<tbody> 
					<c:forEach items="${pagedListHolder.pageList}" var="row">
						<tr>
							<td><a href="constituent.htm?constituentId=${row.id}"><spring:message code="view"/></a></td>
							<%@ include file="/WEB-INF/jsp/snippets/gridResults.jsp" %>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			
		</c:forEach>
	</c:when>
	<c:when test="${constituentList ne null}">
		<p style="margin:8px 0 6px 0;">Your search returned no results.</p>
		<p>Would you like to <a href="constituent.htm">create a new account</a>?</p>
	</c:when>
	<c:otherwise>
	</c:otherwise>
</c:choose>