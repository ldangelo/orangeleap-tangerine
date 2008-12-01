<%@ include file="/WEB-INF/jsp/include.jsp" %>
<mp:section sectionDefinition="${sectionDefinition}"/>
<c:forEach var="sectionField" items="${sectionFieldList}" varStatus="status">
<mp:field sectionField='${sectionField}' sectionFieldList='${sectionFieldList}' model="${row}" />	
<td class="header headerSortDown">
<input type="text" name="<c:out value='${fieldVO.fieldName}'/>" value="<c:out value='${param[fieldVO.fieldName]}'/>" />
</td>
</c:forEach>