<%@ include file="/WEB-INF/jsp/include.jsp" %>

<mp:section sectionDefinition="${sectionDefinition}"/>
<c:forEach var="sectionField" items="${sectionFieldList}" varStatus="status">
	<mp:field sectionField='${sectionField}' sectionFieldList='${sectionFieldList}' model="${row}"/>
	<c:if test="${fieldVO.fieldType ne 'HIDDEN'}">
		<td>
			<c:choose>
				<c:when test="${empty fieldVO.displayValue}">&nbsp;</c:when>
				<c:when test="${fieldVO.fieldType == 'DATE'}">
					<fmt:formatDate value="${fieldVO.displayValue}" pattern="MM-dd-yy h:mm a" />
				</c:when>
				<c:otherwise><c:out value='${fieldVO.displayValue}'/></c:otherwise>
			</c:choose>
		</td>
	</c:if>
</c:forEach>