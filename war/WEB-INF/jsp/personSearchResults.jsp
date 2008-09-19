<%@ include file="/WEB-INF/jsp/include.jsp" %>
<c:choose>
<c:when test="${!empty personList}">
	<c:url value="/personSearch.htm" var="pagedLink" scope="request"> 
	    <c:param name="page" value=""/> 
	</c:url> 

	<div class="searchResultsHeader">
	<div class="pagination">
		<c:if test="${pagedListHolder.pageCount > 1}"> 
		    <c:if test="${!pagedListHolder.firstPage}"> 
		        <a href="${pagedLink}${pagedListHolder.page-1}" onclick="return getPage(this)">« Previous</a>
		    </c:if>
		    <c:if test="${pagedListHolder.firstPage}"> 
		        <span class="disabled">« Previous</span>
		    </c:if> 
		    <c:if test="${pagedListHolder.firstLinkedPage > 0}"> 
		        <a href="${pagedLink}0" onclick="return getPage(this)">1</a> 
		    </c:if> 
		    <c:if test="${pagedListHolder.firstLinkedPage > 1}"> 
		        <span class="pageDots">...</span> 
		    </c:if> 
		    <c:forEach begin="${pagedListHolder.firstLinkedPage}" end="${pagedListHolder.lastLinkedPage}" var="i"> 
		        <c:choose> 
		            <c:when test="${pagedListHolder.page == i}"> 
		                <span class="current">${i+1}</span> 
		            </c:when> 
		            <c:otherwise> 
		                <a href="${pagedLink}${i}" onclick="return getPage(this)">${i+1}</a>
		            </c:otherwise> 
		        </c:choose> 
		    </c:forEach> 
		    <c:if test="${pagedListHolder.lastLinkedPage < pagedListHolder.pageCount - 2}"> 
		        <span class="pageDots">...</span> 
		    </c:if> 
		    <c:if test="${pagedListHolder.lastLinkedPage < pagedListHolder.pageCount - 1}"> 
		        <a href="${pagedLink}${pagedListHolder.pageCount-1}" onclick="return getPage(this)">${pagedListHolder.pageCount}</a>
		    </c:if> 
		    <c:if test="${!pagedListHolder.lastPage}"> 
		       <a href="${pagedLink}${pagedListHolder.page+1}" onclick="return getPage(this)">Next »</a>
		    </c:if>
		    <c:if test="${pagedListHolder.lastPage}"> 
		       <span class="disabled">Next »</span>
		    </c:if> 
		</c:if>
	</div>
	<h4 class="searchResults">Search Results <strong>${pagedListHolder.firstElementOnPage+1} - ${pagedListHolder.lastElementOnPage+1}</strong> of <strong>${pagedListHolder.nrOfElements}</strong></h4>
	</div>
	
	<mp:page pageName='personSearchResults' />
	<c:forEach var="sectionDefinition" items="${sectionDefinitions}">
		<table id="myTable" class="tablesorter" cellspacing="0" cellpadding="0"> 
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
					<tr class="personRow" rel="personView.htm?personId=${row.id}">
						<td><a href="person.htm?personId=${row.id}">Edit</a></td>
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