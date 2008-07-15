<%@ include file="/WEB-INF/jsp/include.jsp" %>

<mp:section sectionDefinition="${sectionDefinition}"/>
<c:set var="totalFields" value="${sectionFieldCount}"/>
<c:forEach var="sectionField" items="${sectionFieldList}" varStatus="status">
	<mp:field sectionField='${sectionField}' sectionFieldList='${sectionFieldList}' model="${row}"/>
	<td><c:choose>
	<c:when test="${empty fieldVO.fieldValue}">&nbsp;</c:when>
	<c:when test="${fieldVO.fieldType == 'DATE'}">
	<fmt:formatDate value="${fieldVO.fieldValue}" pattern="MM-dd-yy h:mm a" />
	</c:when>
	<c:otherwise>${fieldVO.fieldValue}</c:otherwise>
	</c:choose></td>
</c:forEach>