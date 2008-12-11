<%@ include file="/WEB-INF/jsp/include.jsp" %>

<mp:section sectionDefinition="${sectionDefinition}"/>
<c:set var="totalFields" value="${sectionFieldCount}"/>
<c:set var="thisValue" value=" "/>
<c:forEach var="sectionField" items="${sectionFieldList}" varStatus="status">
	<mp:field sectionField='${sectionField}' sectionFieldList='${sectionFieldList}' model="${row}"/>
	<c:choose>
		<c:when test="${fieldVO.fieldType == 'DATE'}">
			<fmt:formatDate value="${fieldVO.fieldValue}" pattern="MM-dd-yy h:mm a" var="dateValue"/>
			<c:set var="thisValue" value="${thisValue} ${dateValue}"/>
		</c:when>
		<c:otherwise><c:set var="thisValue" value="${thisValue} ${fieldVO.fieldValue}"/></c:otherwise>
	</c:choose>
</c:forEach>
<c:set var="thisValue" value='${fn:trim(thisValue)}'/>
<c:if test="${empty thisValue}">
	&nbsp;
</c:if>
<c:out value='${thisValue}'/>
