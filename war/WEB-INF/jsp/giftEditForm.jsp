<%@ include file="/WEB-INF/jsp/include.jsp"%>

<mp:page pageName='gift' />
<c:set var="person" value="${gift.person}" scope="request" />
<c:if test="${person.id!=null}">
	<c:set var="viewingPerson" value="true" scope="request" />
</c:if>

<form:form method="post" commandName="gift">
	<c:if test="${id != null}">
		<input type="hidden" name="id" value="${id}" />
	</c:if>

	<jsp:include page="snippets/personHeader.jsp">
		<jsp:param name="currentFunctionTitleText" value="Enter Gift" />
		<jsp:param name="submitButtonText" value="Submit Payment" />
	</jsp:include>
	
	<jsp:include page="snippets/standardFormErrors.jsp"/>

	<div class="columns">
		<c:forEach var="sectionDefinition" items="${sectionDefinitions}">
			<div class="paymentType">
				<%@ include file="/WEB-INF/jsp/snippets/fieldLayout.jsp"%>
			</div>
		</c:forEach>
		<div class="clearColumns"></div>
	</div>
	<div class="formButtonFooter personFormButtons"><input type="submit" value="Submit Payment" class="saveButton" /></div>
</form:form>
<script type="text/javascript">
	var elem=document.getElementById("paymentType");
	$("." + elem[elem.selectedIndex].getAttribute('reference')).show();
</script>