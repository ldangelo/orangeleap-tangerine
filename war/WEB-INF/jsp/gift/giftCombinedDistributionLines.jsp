<%@ include file="/WEB-INF/jsp/include.jsp" %>
<mp:page pageName='gift' />
<c:set var="gridCollectionName" value="mutableDistributionLines" />
<c:set var="gridCollection" value="${combinedDistributionLines}" />

<c:forEach var="sectionDefinition" items="${gridSections}">
	<c:forEach items="${gridCollection}" var="row" varStatus="status">
		<c:set var="counter" value="${status.index + 1}" scope="request"/>
		<%@ include file="/WEB-INF/jsp/snippets/gridRow.jsp"%>
	</c:forEach>
</c:forEach>
