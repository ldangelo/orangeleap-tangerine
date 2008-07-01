<%@ include file="/WEB-INF/jsp/include.jsp" %>
<tiles:insertDefinition name="base">
	<tiles:putAttribute name="browserTitle" value="View Gift" />
	<tiles:putAttribute name="primaryNav" value="People" />
	<tiles:putAttribute name="secondaryNav" value="Search" />
	<tiles:putAttribute name="sidebarNav" value="Profile" />
	<tiles:putAttribute name="mainContent" type="string">
		<div class="content760 mainForm test">
			<jsp:include page="viewGiftForm.jsp"/>
		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>