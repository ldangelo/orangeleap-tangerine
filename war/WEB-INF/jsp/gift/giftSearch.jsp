<%@ include file="/WEB-INF/jsp/include.jsp" %>
<c:choose>
<c:when test="${param.view=='ajaxResults'}">
<jsp:include page="giftSearchResults.jsp"/>
</c:when>
<c:otherwise>
<tiles:insertDefinition name="base">
	<tiles:putAttribute name="browserTitle" value="Search Gifts" />
	<tiles:putAttribute name="primaryNav" value="Gifts" />
	<tiles:putAttribute name="secondaryNav" value="Search" />
	<tiles:putAttribute name="mainContent" type="string">
		<div class="content760 mainForm">
			<mp:page pageName='giftSearch'/>
			<form:form method="post" commandName="gift" cssClass="searchForm">
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
			<div id="searchResults">
			<jsp:include page="giftSearchResults.jsp"/>
			</div>
		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>
</c:otherwise>
</c:choose>
