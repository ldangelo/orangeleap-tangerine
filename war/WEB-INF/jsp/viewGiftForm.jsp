<%@ include file="/WEB-INF/jsp/include.jsp" %>
<mp:page pageName='viewGift'/>

<form:form method="post" commandName="gift">

	<c:forEach var="sectionDefinition" items="${sectionDefinitions}">
		<h1>
			<mp:sectionHeader sectionDefinition="${sectionDefinition}"/>
		</h1>
		<div class="searchSection">
			<%@ include file="/WEB-INF/jsp/snippets/fieldLayout.jsp" %>
		</div>
	</c:forEach>

</form:form>