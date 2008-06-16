<%@ include file="/WEB-INF/jsp/include.jsp" %>
<c:choose>
<c:when test="${!empty personList}">
	<div class="searchResultsHeader">
		<div class="pagination"><span class="disabled">« Previous</span> <span class="current">1</span> <a href="#">2</a> <a href="#">3</a> <a href="#">Next »</a></div>
		<h4 class="searchResults">Search Results <strong>1 - ${personListSize}</strong> of <strong>${personListSize}</strong></h4>
	</div>
	
	<mp:page pageName='personSearchResults' />
	<c:forEach var="sectionDefinition" items="${sectionDefinitions}">
		<table id="myTable" class="tablesorter" cellspacing="0" cellpadding="0"> 
			<thead> 
				<c:forEach items="${personList}" var="person" begin="0" end="0">
					<tr>
						<th>&nbsp;</th>
						<%@ include file="/WEB-INF/jsp/snippets/gridResultsHeader.jsp" %>
					</tr>
				</c:forEach>
			</thead> 
			<tbody> 
				<c:forEach items="${personList}" var="person">
					<tr>
						<td><a href="person.htm?personId=${person.id}">Edit</a></td>
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