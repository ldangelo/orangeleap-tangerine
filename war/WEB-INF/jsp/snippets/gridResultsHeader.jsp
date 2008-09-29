<%@ include file="/WEB-INF/jsp/include.jsp" %>
<mp:section sectionDefinition="${sectionDefinition}"/>
<c:forEach var="sectionField" items="${sectionFieldList}" varStatus="status">
	<mp:field sectionField='${sectionField}' sectionFieldList='${sectionFieldList}' model="${row}" />	
<c:choose>
	<c:when test="${fieldVO.fieldName==currentSort && currentAscending=='true'}">
	<th class="header headerSortDown">
	<a href="${sortLink}&sort=${fieldVO.fieldName}&ascending=false" onclick="return getPage(this)">${fieldVO.labelText}</a>
	</th>
	</c:when>
	<c:when test="${fieldVO.fieldName==currentSort && currentAscending=='false'}">
	<th class="header headerSortUp">
	<a href="${sortLink}&sort=${fieldVO.fieldName}" onclick="return getPage(this)">${fieldVO.labelText}</a>
	</th>
	</c:when>
	<c:otherwise>
	<th class="header">
	<a href="${sortLink}&sort=${fieldVO.fieldName}" onclick="return getPage(this)">${fieldVO.labelText}</a>
	</th>
	</c:otherwise>
</c:choose>
</c:forEach>