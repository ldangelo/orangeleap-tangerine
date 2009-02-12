<%@ include file="/WEB-INF/jsp/include.jsp" %>
<c:choose>
	<c:when test="${!empty personList}">
	
		<c:set var="basePaginationUrl" value="personSearch.htm" />
		<jsp:include page="../snippets/pagination.jsp"/>
	
		<mp:page pageName='personSearchResults' />
		<c:forEach var="sectionDefinition" items="${sectionDefinitions}">
			<table class="tablesorter" cellspacing="0" cellpadding="0"> 
				<thead> 
					<c:forEach items="${personList}" var="row" begin="0" end="0">
						<tr>
							<th>&nbsp;</th>
							<%@ include file="/WEB-INF/jsp/snippets/gridResultsHeader.jsp" %>
						</tr>
					</c:forEach>
				</thead> 
				<tbody> 
					<c:forEach items="${pagedListHolder.pageList}" var="row">
						<tr>
							<td><a href="person.htm?personId=${row.id}"><spring:message code="view"/></a></td>
							<%@ include file="/WEB-INF/jsp/snippets/gridResults.jsp" %>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			
		</c:forEach>
	</c:when>
	<c:when test="${personList ne null}">
		<p style="margin:8px 0 6px 0;">Your search returned no results.</p>
		<p>Would you like to <a href="person.htm">create a new account</a>?</p>
	</c:when>
	<c:otherwise>
	</c:otherwise>
</c:choose>