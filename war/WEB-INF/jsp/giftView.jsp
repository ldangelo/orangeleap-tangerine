<%@ include file="/WEB-INF/jsp/include.jsp" %>
<tiles:insertDefinition name="base">
	<tiles:putAttribute name="browserTitle" value="View Gift" />
	<tiles:putAttribute name="primaryNav" value="People" />
	<tiles:putAttribute name="secondaryNav" value="Search" />
	<tiles:putAttribute name="sidebarNav" value="Profile" />
	<tiles:putAttribute name="mainContent" type="string">
		<div class="content760 mainForm">
			<mp:page pageName='giftView'/>
			<c:forEach var="sectionDefinition" items="${sectionDefinitions}">
				<h1>
					<mp:sectionHeader sectionDefinition="${sectionDefinition}"/>
				</h1>
				<div class="searchSection">
					<%@ include file="/WEB-INF/jsp/snippets/fieldLayout.jsp" %>
				</div>
				<div class="formButtonFooter personFormButtons">
				</div>
				<a href="giftRefund.htm?giftId=${gift.id}" class="newAccountButton">Refund Gift &raquo; </a>
			</c:forEach>
		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>