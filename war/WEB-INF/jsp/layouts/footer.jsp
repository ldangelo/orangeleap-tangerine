<%@ include file="/WEB-INF/jsp/include.jsp" %>
<div id="footer">
	<div class="container">
		<p style="margin-bottom:0" class="legal">
		Last login <c:out value="${lastLoginDate}"/>
		</p>
		<c:set var="copyrightStart" value="2008" />
		<fmt:formatDate var="copyrightEnd" value="${currentDate}" pattern="yyyy"  />
		<c:choose>
			<c:when test="${copyrightStart==copyrightEnd}">
				<c:set var="copyrightRange" value="${copyrightStart}" />
			</c:when>
			<c:otherwise>
				<c:set var="copyrightRange" value="${copyrightStart} - ${copyrightEnd}" />
			</c:otherwise>
		</c:choose>
		<p class="legal">
			(800) 562-5150 | &#169; Copyright <c:out value="${copyrightRange}"/> MPower Open. All Rights Reserved. |
			<a href="#">Articles</a> |
			<a href="#">Resources</a>
		</p>
	</div>
</div>