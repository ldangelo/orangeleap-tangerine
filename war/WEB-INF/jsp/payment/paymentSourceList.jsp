<%@ include file="/WEB-INF/jsp/include.jsp" %>
<c:choose>
<c:when test="${!empty paymentSources}">
	<div class="searchResultsHeader">
		<div class="pagination"><span class="disabled">« Previous</span> <span class="current">1</span> <a href="#">2</a> <a href="#">3</a> <a href="#">Next »</a></div>
		<h4 class="searchResults">Search Results <strong>1 - ${paymentSourcesListSize}</strong> of <strong>${paymentSourcesListSize}</strong></h4>
	</div>

	<mp:page pageName='paymentSourceList' />
	<c:forEach var="sectionDefinition" items="${sectionDefinitions}">
		<table id="myTable" class="tablesorter" cellspacing="0" cellpadding="0">
			<thead>
				<c:forEach items="${paymentSources}" var="row" begin="0" end="0">
					<tr>
						<th>&nbsp;</th>
						<%@ include file="/WEB-INF/jsp/snippets/gridResultsHeader.jsp" %>
					</tr>
				</c:forEach>
			</thead>
			<tbody>
				<c:forEach items="${paymentSources}" var="row">
					<tr>
						<%@ include file="/WEB-INF/jsp/snippets/gridResults.jsp" %>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</c:forEach>

</c:when>
<c:when test="${paymentSources ne null}">
	<p style="margin:8px 0 6px 0;">Your search returned no results.</p>
</c:when>
<c:otherwise>
</c:otherwise>
</c:choose>