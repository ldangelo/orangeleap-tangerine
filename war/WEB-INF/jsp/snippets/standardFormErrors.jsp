<%@ include file="/WEB-INF/jsp/include.jsp" %>
<form:errors path="*">
	<div class="globalFormErrors">
		<h5>Please correct the following errors on this page:</h5>
		<ul>
		<c:forEach items="${messages}" var="message">
			<li>${message}</li>
		</c:forEach>
		</ul>
	</div>
</form:errors>