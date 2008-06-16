<%@ include file="/WEB-INF/jsp/include.jsp" %>
<tiles:insertDefinition name="base">
	<tiles:putAttribute name="browserTitle" value="Person" />
	<tiles:putAttribute name="primaryNav" value="People" />
	<tiles:putAttribute name="secondaryNav" value="Edit" />
	<tiles:putAttribute name="mainContent" type="string">
		<div class="content760 mainForm">
			<jsp:include page="personEditForm.jsp"/>
		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>