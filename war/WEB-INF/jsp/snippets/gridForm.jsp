<%@ include file="/WEB-INF/jsp/include.jsp" %>
<mp:section sectionDefinition="${sectionDefinition}"/>
<c:forEach var="sectionField" items="${sectionFieldList}">
	<mp:field sectionField='${sectionField}' sectionFieldList='${sectionFieldList}' model='${row}' />
	<td><c:choose>
	<c:when test="${fieldVO.fieldType == 'DATE'}">
	<fmt:formatDate value="${fieldVO.fieldValue}" pattern="MM-dd-yy h:mm a" />
	</c:when>
	<c:when test="${fieldVO.fieldType == 'LOOKUP'}">
	<input value="${fieldVO.fieldValue}" class="text lookup ${fieldVO.fieldName}" name="${gridCollectionName}[${status.index}].${fieldVO.fieldName}" />
	<a class="lookupLink jqModal" href="#">Lookup</a>
	</c:when>
	<c:otherwise>
	<input value="${fieldVO.fieldValue}" class="text ${fieldVO.fieldName}" name="${gridCollectionName}[${status.index}].${fieldVO.fieldName}" />
	</c:otherwise>
	</c:choose></td>
</c:forEach>