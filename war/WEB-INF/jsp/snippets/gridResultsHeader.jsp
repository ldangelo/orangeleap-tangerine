<%@ include file="/WEB-INF/jsp/include.jsp" %>
<mp:section sectionDefinition="${sectionDefinition}"/>
<c:forEach var="sectionField" items="${sectionFieldList}" varStatus="status">
	<mp:field sectionField='${sectionField}' sectionFieldList='${sectionFieldList}' model="${row}" />
	<c:choose>
		<c:when test="${fieldVO.fieldType == 'HIDDEN'}">
			<th class="noDisplay">&nbsp;</th>
		</c:when>
		<c:when test="${fieldVO.fieldName==currentSort && currentAscending=='true'}">
			<th class="header headerSortDown">
				<c:if test="${fieldVO.required == 'true'}"><span class="required">*</span>&nbsp;</c:if>
				<a href="<c:out value='${sortLink}'/>&sort=<c:out value='${fieldVO.fieldName}'/>&ascending=false" onclick="return getPage(this)"><c:out value='${fieldVO.labelText}'/></a>
			</th>
		</c:when>
		<c:when test="${fieldVO.fieldName==currentSort && currentAscending=='false'}">
			<th class="header headerSortUp">
				<c:if test="${fieldVO.required == 'true'}"><span class="required">*</span>&nbsp;</c:if>
				<a href="<c:out value='${sortLink}'/>&sort=<c:out value='${fieldVO.fieldName}'/>" onclick="return getPage(this)"><c:out value='${fieldVO.labelText}'/></a>
			</th>
		</c:when>
		<c:otherwise>
			<th class="header">
				<c:if test="${fieldVO.required == 'true'}"><span class="required">*</span>&nbsp;</c:if>
				<a href="<c:out value='${sortLink}'/>&sort=<c:out value='${fieldVO.fieldName}'/>" onclick="return getPage(this)"><c:out value='${fieldVO.labelText}'/></a>
			</th>
		</c:otherwise>
	</c:choose>
</c:forEach>