<%@ include file="/WEB-INF/jsp/include.jsp" %>
<tiles:insertDefinition name="base">
	<tiles:putAttribute name="browserTitle" value="Search Recurring Gifts" />
	<tiles:putAttribute name="primaryNav" value="Recurring Gifts" />
	<tiles:putAttribute name="secondaryNav" value="Search" />
	<tiles:putAttribute name="mainContent" type="string">
		<div class="content760 mainForm">
			<mp:page pageName='recurringGiftSearch'/>
			<form:form method="post" commandName="recurringGift">
				<c:forEach var="sectionDefinition" items="${sectionDefinitions}">
					<h1>
						<mp:sectionHeader sectionDefinition="${sectionDefinition}"/>
					</h1>
					<div class="searchSection">
						<%@ include file="/WEB-INF/jsp/snippets/fieldLayout.jsp" %>
					    <div class="constituentFormButtons searchConstituentButtons">
							<input class="searchButton" type="submit" value="Search" />
						</div>
					</div>
				</c:forEach>
			</form:form>
			<jsp:include page="recurringGiftSearchResults.jsp"/>
		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>
