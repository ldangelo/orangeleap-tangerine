<%@ include file="/WEB-INF/jsp/include.jsp" %>
<tiles:insertDefinition name="base">
	<tiles:putAttribute name="browserTitle" value="Search Gifts" />
	<tiles:putAttribute name="primaryNav" value="Gifts" />
	<tiles:putAttribute name="secondaryNav" value="Search" />
	<tiles:putAttribute name="mainContent" type="string">
		<div class="content760 mainForm">
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
			<jsp:include page="giftSearchResults.jsp"/>
		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>
