<%@ include file="/WEB-INF/jsp/include.jsp" %>
<div id="footer">
	<div class="container">
		<c:if test="${not empty lastLoginDate}">
			<p class="legal">
				<spring:message code="lastLogin"/> <fmt:formatDate value="${lastLoginDate}" pattern="h:mm a z, EEEE, MMMM d, yyyy"/> | <spring:message code="version"/>: ${build.time}
			</p>
		</c:if>
		<c:set var="copyrightStart" value="2008" />
		<fmt:formatDate var="copyrightEnd" value="${currentDate}" pattern="yyyy"  />
		<c:choose>
			<c:when test="${copyrightStart == copyrightEnd}">
				<c:set var="copyrightRange" value=" ${copyrightStart} " />
			</c:when>
			<c:otherwise>
				<c:set var="copyrightRange" value=" ${copyrightStart} - ${copyrightEnd} " />
			</c:otherwise>
		</c:choose>
		<p class="legal">
			<spring:message code="companyPhone"/> | <spring:message code="poweredBy"/> &#169; <spring:message code="copyright"/> <c:out value="${copyrightRange}"/> <spring:message code="companyRightsReserved"/>
		</p>
	</div>
</div>