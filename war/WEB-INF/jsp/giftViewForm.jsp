<%@ include file="/WEB-INF/jsp/include.jsp" %>
<mp:page pageName='giftView'/>

<form:form method="post" commandName="gift">

	<c:forEach var="sectionDefinition" items="${sectionDefinitions}">
		<h1>
			<mp:sectionHeader sectionDefinition="${sectionDefinition}"/>
		</h1>
		<div class="searchSection">
			<%@ include file="/WEB-INF/jsp/snippets/fieldLayout.jsp" %>
		</div>
		<div class="formButtonFooter personFormButtons">
			<input type="submit" value="Refund Gift" class="saveButton" />
			<a href="giftView.htm?giftId=${gift.id}" class="newAccountButton">Create New Gift &raquo; </a>
		</div>
	</c:forEach>
	
</form:form>