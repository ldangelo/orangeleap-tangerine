<%@ include file="/WEB-INF/jsp/include.jsp"%>
<c:forEach items="${codes}" var="code">
	<c:out value='${code.defaultDisplayValue}' escapeXml="false"/>|<c:out value='${code.longDescription}' escapeXml="false"/>|<c:out value='${code.itemName}' escapeXml="false"/>&nbsp;
</c:forEach>