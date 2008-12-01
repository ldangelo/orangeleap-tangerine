<%@ include file="/WEB-INF/jsp/include.jsp" %>
<c:url value="${basePaginationUrl}" var="pagedLink" scope="request">
	<c:param name="sort" value="${currentSort}" />
	<c:param name="ascending" value="${currentAscending}" />
    <c:param name="page" value="" />
</c:url>
<c:url value="${basePaginationUrl}" var="sortLink" scope="request">
    <c:param name="page" value="${pagedListHolder.page}" />
</c:url>
<div class="searchResultsHeader">
	<div class="pagination">
		<c:if test="${pagedListHolder.pageCount > 1}">
		    <c:if test="${!pagedListHolder.firstPage}">
		        <a href="<c:out value='${pagedLink}'/>${pagedListHolder.page-1}" onclick="return getPage(this)">« Previous</a>
		    </c:if>
		    <c:if test="${pagedListHolder.firstPage}">
		        <span class="disabled">« Previous</span>
		    </c:if>
		    <c:if test="${pagedListHolder.firstLinkedPage > 0}">
		        <a href="<c:out value='${pagedLink}'/>0" onclick="return getPage(this)">1</a>
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
		                <a href="<c:out value='${pagedLink}'/>${i}" onclick="return getPage(this)">${i+1}</a>
		            </c:otherwise>
		        </c:choose>
		    </c:forEach>
		    <c:if test="${pagedListHolder.lastLinkedPage < pagedListHolder.pageCount - 2}">
		        <span class="pageDots">...</span>
		    </c:if>
		    <c:if test="${pagedListHolder.lastLinkedPage < pagedListHolder.pageCount - 1}">
		        <a href="<c:out value='${pagedLink}'/>${pagedListHolder.pageCount-1}" onclick="return getPage(this)">${pagedListHolder.pageCount}</a>
		    </c:if>
		    <c:if test="${!pagedListHolder.lastPage}">
		       <a href="<c:out value='${pagedLink}'/>${pagedListHolder.page+1}" onclick="return getPage(this)">Next »</a>
		    </c:if>
		    <c:if test="${pagedListHolder.lastPage}">
		       <span class="disabled">Next »</span>
		    </c:if>
		</c:if>
	</div>
	<h4 class="searchResults">Search Results <strong>${pagedListHolder.firstElementOnPage+1} - ${pagedListHolder.lastElementOnPage+1}</strong> of <strong>${pagedListHolder.nrOfElements}</strong></h4>
</div>