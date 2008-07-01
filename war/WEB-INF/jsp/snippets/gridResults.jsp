<%@ include file="/WEB-INF/jsp/include.jsp" %>

<mp:section sectionDefinition="${sectionDefinition}"/>
<c:set var="totalFields" value="${sectionFieldCount}"/>
<c:forEach var="sectionField" items="${sectionFieldList}" varStatus="status">
	<mp:field sectionField='${sectionField}' sectionFieldList='${sectionFieldList}' model="${row}"/>
	<td><c:choose><c:when test="${!empty fieldVO.fieldValue}">${fieldVO.fieldValue}</c:when><c:otherwise>&nbsp;</c:otherwise></c:choose></td>
</c:forEach>