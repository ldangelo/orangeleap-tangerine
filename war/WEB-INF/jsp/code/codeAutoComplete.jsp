<%@ include file="/WEB-INF/jsp/include.jsp"%>
<c:forEach items="${codes}" var="code">
	<c:out value='${code.value}'/>|<c:out value='${code.description}'/>&nbsp;
</c:forEach>