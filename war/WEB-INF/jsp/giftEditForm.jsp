<%@ include file="/WEB-INF/jsp/include.jsp"%>

<mp:page pageName='gift' />
<c:set var="person" value="${gift.person}" scope="request" />

<form:form method="post" commandName="gift">
	<c:if test="${id != null}">
		<input type="hidden" name="id" value="${id}" />
	</c:if>

	<div class="columns iconHeader">
		<div class="column">
			<img src="images/dude2.gif" />
		</div>
		<div class="column">
		<c:choose>
		<c:when test="${param.personId!=null || id != null}">
			<c:set scope="request" var="viewingAccount" value="true" />
			<h2 class="personEdit">
				${person.lastName}<c:if test="${!empty person.lastName && !empty person.firstName}">, </c:if>${person.firstName}
			</h2>
		</c:when>
		<c:otherwise>
			<h2 class="personEdit">
				New Person
			</h2>
		</c:otherwise>
		</c:choose>
			<h3 id="currentFunctionTitle" class="personEdit">
				Enter Gift
			</h3>
		</div>
		<div class="columnRight" style="padding:19px 19px 0 0;">
			<input type="submit" value="Submit Payment" />
		</div>
		<div class="clearColumns"></div>
	</div>

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
	$("." + $('#paymentType').attr('value')).show();
</script>