<%@ include file="/WEB-INF/jsp/include.jsp" %>
<mp:section sectionDefinition="${sectionDefinition}"/>
<c:forEach var="sectionField" items="${sectionFieldList}">
	<mp:field sectionField='${sectionField}' sectionFieldList='${sectionFieldList}' model='${row}' />
	<td><c:choose>
	<c:when test="${fieldVO.fieldType == 'DATE'}">
	<fmt:formatDate value="${fieldVO.fieldValue}" pattern="MM-dd-yy h:mm a" />
	</c:when>
	<c:when test="${fieldVO.fieldType == 'CODE'}">
	<div class="lookupWrapper">
		<input value="${fieldVO.fieldValue}" class="text ${fieldVO.fieldName} code" lookup="${fieldVO.fieldName}" codeType="${fieldVO.fieldName}" name="${gridCollectionName}[${status.index}].${fieldVO.fieldName}" />
		<a tabindex="-1" style="margin:0;position:absolute;top:1px;right:-3px" class="lookupLink" href="#" onclick="loadCodePopup($(this).prev('input'));return false;">Lookup</a>
	</div>
	</c:when>
	<c:otherwise>
	<input value="${fieldVO.fieldValue}" class="text ${fieldVO.fieldName}" name="${gridCollectionName}[${status.index}].${fieldVO.fieldName}" />
	</c:otherwise>
	</c:choose></td>
</c:forEach>