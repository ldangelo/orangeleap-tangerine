<%@ include file="/WEB-INF/jsp/include.jsp" %>
<tiles:insertDefinition name="base">
	<tiles:putAttribute name="browserTitle" value="Search People" />
	<tiles:putAttribute name="primaryNav" value="People" />
	<tiles:putAttribute name="secondaryNav" value="Search" />
	<tiles:putAttribute name="mainContent" type="string">
		<div class="content760 mainForm">
			<mp:page pageName='personSearch'/>

			<form:form method="post" commandName="person">
				<c:forEach var="sectionDefinition" items="${sectionDefinitions}">
					<h1>
						<mp:sectionHeader sectionDefinition="${sectionDefinition}"/>
					</h1>
					<div class="searchSection">
						<%@ include file="/WEB-INF/jsp/snippets/fieldLayout.jsp" %>
					    <div class="personFormButtons searchPersonButtons">
							<input class="searchButton" type="submit" value="Search" />
							<a href="person.htm" class="newAccountButton">Add New Person &raquo; </a>
						</div>
					</div>
				</c:forEach>
			</form:form>
			<jsp:include page="personSearchResults.jsp"/>
		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>
