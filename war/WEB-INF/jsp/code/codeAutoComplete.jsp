<%@ include file="/WEB-INF/jsp/include.jsp"%>
<c:forEach items="${codes}" var="code">
	<c:out value='${code.defaultDisplayValue}'/>|<c:out value='${code.longDescription}'/>&nbsp;
</c:forEach>