<%@ include file="/WEB-INF/jsp/include.jsp" %>
<mp:page pageName='giftSearch'/>

<form:form method="post" commandName="gift">
	<c:forEach var="sectionDefinition" items="${sectionDefinitions}">
		<h1>
			<mp:sectionHeader sectionDefinition="${sectionDefinition}"/>
		</h1>
		<div class="searchSection">
			<%@ include file="/WEB-INF/jsp/snippets/fieldLayout.jsp" %>
		    <div class="personFormButtons searchPersonButtons">
				<input class="searchButton" type="submit" value="Search" />
			</div>
		</div>
	</c:forEach>
</form:form>


