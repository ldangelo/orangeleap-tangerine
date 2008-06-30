<%@ include file="/WEB-INF/jsp/include.jsp" %>
<mp:section sectionDefinition="${sectionDefinition}"/>
<c:set var="totalFields" value="${sectionFieldCount}"/>

<c:forEach var="sectionField" items="${sectionFieldList}" varStatus="status">
	<mp:field sectionField='${sectionField}' sectionFieldList='${sectionFieldList}' model="${gift}" />	
	<th>${fieldVO.labelText}</th>
</c:forEach>