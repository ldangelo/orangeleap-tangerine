<%@ include file="/WEB-INF/jsp/include.jsp" %>
<form:errors path="*">
	<div class="globalFormErrors">
		<h5><spring:message code='errorsPleaseCorrect'/></h5>
		<ul>
            <c:set var="previousMessages" scope="page" value=""/>
			<c:forEach items="${messages}" var="message">
                <c:set var="searchString" scope="page" value="|${message}|"/>
                <c:choose>
                    <c:when test="${fn:contains(previousMessages, searchString)}"></c:when>
                    <c:otherwise>
                        <li><c:out value='${message}'/></li>
                        <c:set var="previousMessages" scope="page" value="${previousMessages}${searchString}"/>
                    </c:otherwise>
                </c:choose>
			</c:forEach>
		</ul>
	</div>
</form:errors>