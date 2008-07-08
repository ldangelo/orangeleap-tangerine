<%@ include file="/WEB-INF/jsp/include.jsp" %>
<tiles:insertDefinition name="base">
	<tiles:putAttribute name="browserTitle" value="Search People" />
	<tiles:putAttribute name="primaryNav" value="People" />
	<tiles:putAttribute name="secondaryNav" value="Search" />
	<tiles:putAttribute name="mainContent" type="string">
		<div class="content760 mainForm">
			<jsp:include page="personSearchForm.jsp"/>
			<jsp:include page="personSearchResults.jsp"/>
		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>
