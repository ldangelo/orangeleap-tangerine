<%@ include file="/WEB-INF/jsp/include.jsp" %>
<c:choose>
<c:when test="${param.view=='ajaxResults'}">
<jsp:include page="constituentSearchResults.jsp"/>
</c:when>
<c:otherwise>
<tiles:insertDefinition name="base">
	<tiles:putAttribute name="browserTitle" value="Search Constituents" />
	<tiles:putAttribute name="primaryNav" value="People" />
	<tiles:putAttribute name="secondaryNav" value="Search" />
	<tiles:putAttribute name="mainContent" type="string">
		<div class="content760 mainForm">
			<mp:page pageName='constituentSearch'/>

			<form:form method="post" commandName="constituent" cssClass="searchForm">
				<c:forEach var="sectionDefinition" items="${sectionDefinitions}">
					<h1>
						<mp:sectionHeader sectionDefinition="${sectionDefinition}"/>
					</h1>
					<div class="searchSection">
						<%@ include file="/WEB-INF/jsp/snippets/fieldLayout.jsp" %>
					    <div class="constituentFormButtons searchConstituentButtons">
							<input class="searchButton" type="submit" value="Search" />
							<a href="constituent.htm" class="newAccountButton">Add New Constituent &raquo; </a>
						</div>
					</div>
				</c:forEach>
			</form:form>
			<div id="searchResults">
			<jsp:include page="constituentSearchResults.jsp"/>
			</div>
		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>
</c:otherwise>
</c:choose>
