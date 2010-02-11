<%@ include file="/WEB-INF/jsp/include.jsp"%>
<c:choose>
	<c:when test="${empty requestScope.availableCodes}">
		<script type="text/javascript">
			$("#codeNoResultsDiv").removeClass("noDisplay");
		</script>
	</c:when>
	<c:otherwise>
		<c:forEach items="${requestScope.availableCodes}" var="code">
			<li id="<c:out value='${code.itemName}'/>-li" title="<c:out value='${code.valueDescription}'/>">
				<input type="checkbox" name="<c:out value='${code.defaultDisplayValue}'/>" id="<c:out value='${code.defaultDisplayValue}'/>" title="<spring:message code='clickToSelect'/>" value="<c:out value='${code.itemName}'/>" displayvalue="<c:out value='${code.valueDescription}'/>" />
				<c:out value='${code.valueDescription}'/>
			</li>
		</c:forEach>
		<script type="text/javascript">
			$("#codeNoResultsDiv").addClass("noDisplay");
		</script>
	</c:otherwise>
</c:choose>