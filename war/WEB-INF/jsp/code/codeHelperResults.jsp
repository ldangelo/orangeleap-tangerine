<%@ include file="/WEB-INF/jsp/include.jsp"%>
<c:choose>
	<c:when test="${empty codes}">
		<div class="noResults"><spring:message code="noCodesFound"/></li>
	</c:when>
	<c:otherwise>
		<ul id="codeResultsUl" class="queryUl">
			<c:forEach items="${codes}" var="code">
				<li id="<c:out value='${code.value}'/>-li">
					<input type="radio" name="option" id="<c:out value='${code.value}'/>" title="<spring:message code='clickToSelect'/>" value="<c:out value='${code.value}'/>" displayvalue="<c:out value='${code.description}'/>" />
					<c:out value='${code.value}'/> - <c:out value='${code.description}'/>
				</li>
			</c:forEach>
		</ul>
	</c:otherwise>
</c:choose>