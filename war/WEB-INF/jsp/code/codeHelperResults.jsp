<%@ include file="/WEB-INF/jsp/include.jsp"%>
<c:choose>
	<c:when test="${empty codes}">
		<div class="noResults"><spring:message code="noCodesFound"/></li>
	</c:when>
	<c:otherwise>
		<ul id="codeResultsUl" class="queryUl">
			<c:forEach items="${codes}" var="code">
				<li id="<c:out value='${code.itemName}'/>-li">
					<input type="radio" name="option" id="<c:out value='${code.defaultDisplayValue}'/>" title="<spring:message code='clickToSelect'/>" value="<c:out value='${code.defaultDisplayValue}'/>" displayvalue="<c:out value='${code.longDescription}'/>" />
					<c:out value='${code.valueDescription}'/>
				</li>
			</c:forEach>
		</ul>
	</c:otherwise>
</c:choose>