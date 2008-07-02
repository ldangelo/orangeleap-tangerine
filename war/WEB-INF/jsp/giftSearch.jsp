<%@ include file="/WEB-INF/jsp/include.jsp" %>
<tiles:insertDefinition name="base">
	<tiles:putAttribute name="browserTitle" value="Search Gifts" />
	<tiles:putAttribute name="primaryNav" value="Gifts" />
	<tiles:putAttribute name="secondaryNav" value="Search" />
	<tiles:putAttribute name="mainContent" type="string">
		<div class="content760 mainForm">
			<jsp:include page="giftSearchForm.jsp"/>
			<jsp:include page="giftSearchResults.jsp"/>
		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>
