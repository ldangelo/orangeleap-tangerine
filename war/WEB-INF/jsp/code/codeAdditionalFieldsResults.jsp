<%@ include file="/WEB-INF/jsp/include.jsp"%>
<c:choose>
	<c:when test="${empty requestScope.availableCodes}">
		<script type="text/javascript">
			$("codeNoResultsDiv").removeClass("noDisplay");
		</script>
	</c:when>
	<c:otherwise>
		<c:forEach items="${requestScope.availableCodes}" var="code">
			<li id="<c:out value='${code.value}'/>-li">
				<input type="checkbox" name="<c:out value='${code.value}'/>" id="<c:out value='${code.value}'/>" title="<spring:message code='clickToSelect'/>" value="<c:out value='${code.value}'/>" displayvalue="<c:out value='${code.description}'/>" />
				<c:out value='${code.value}'/> - <c:out value='${code.description}'/>
			</li>
		</c:forEach>
		<script type="text/javascript">
			$("#codeNoResultsDiv").addClass("noDisplay");
		</script>
	</c:otherwise>
</c:choose>