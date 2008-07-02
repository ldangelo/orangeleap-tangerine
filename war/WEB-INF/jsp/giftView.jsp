<%@ include file="/WEB-INF/jsp/include.jsp" %>
<tiles:insertDefinition name="base">
	<tiles:putAttribute name="browserTitle" value="View Gift" />
	<tiles:putAttribute name="primaryNav" value="People" />
	<tiles:putAttribute name="secondaryNav" value="Search" />
	<tiles:putAttribute name="sidebarNav" value="" />
	<tiles:putAttribute name="mainContent" type="string">
		<div class="content760 mainForm">
			<mp:page pageName='giftView'/>
			<c:set var="person" value="${gift.person}" scope="request" />
			
	<div class="columns iconHeader">
		<div class="column">
			<img src="images/dude2.gif" />
		</div>
		<div class="column">
		<c:choose>
		<c:when test="${param.personId!=null || id != null || person.id != null}">
			<c:set scope="request" var="viewingAccount" value="true" />
			<h2 class="personEdit">
				${person.lastName}<c:if test="${!empty person.lastName && !empty person.firstName}">, </c:if>${person.firstName}
			</h2>
		</c:when>
		<c:otherwise>
			<h2 class="personEdit">
				New Person
			</h2>
		</c:otherwise>
		</c:choose>
			<h3 id="currentFunctionTitle" class="personEdit">
				View Gift
			</h3>
		</div>

		<div class="clearColumns"></div>
	</div>
			
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