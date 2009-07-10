<%@ include file="/WEB-INF/jsp/include.jsp" %>
<form:errors path="*">
	<div class="globalFormErrors">
		<h5><spring:message code='errorsPleaseCorrect'/></h5>
		<ul>
			<c:forEach items="${messages}" var="message">
				<li><c:out value='${message}'/></li>
			</c:forEach>
		</ul>
	</div>
</form:errors>