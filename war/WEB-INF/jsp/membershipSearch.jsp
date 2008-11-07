<%@ include file="/WEB-INF/jsp/include.jsp" %>
<tiles:insertDefinition name="base">
	<tiles:putAttribute name="browserTitle" value="Search Memberships" />
	<tiles:putAttribute name="primaryNav" value="Memberships" />
	<tiles:putAttribute name="secondaryNav" value="Search" />
	<tiles:putAttribute name="mainContent" type="string">
		<div class="content760 mainForm">
			<mp:page pageName='membershipSearch'/>
			<form:form method="post" commandName="commitment">
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
			<jsp:include page="membershipSearchResults.jsp"/>
		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>
