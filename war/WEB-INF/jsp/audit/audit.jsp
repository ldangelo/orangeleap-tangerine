<%@ include file="/WEB-INF/jsp/include.jsp"%>
<tiles:insertDefinition name="base">
    <tiles:putAttribute name="customHeaderContent" type="string">
        <script type="text/javascript" src="js/auditlist.js"></script>
    </tiles:putAttribute>
	<tiles:putAttribute name="browserTitle" value="Site Audit" />
	<tiles:putAttribute name="primaryNav" value="People" />
	<tiles:putAttribute name="secondaryNav" value="Edit" />
	<tiles:putAttribute name="sidebarNav" value="Audit" />
	<tiles:putAttribute name="mainContent" type="string">
		<div class="content760 mainForm">
		<c:set var="constituent" value="${constituent}" scope="request" />
		<c:if test="${constituent.id!=null}">
			<c:set var="viewingConstituent" value="true" scope="request" />
		</c:if>
		<jsp:include page="../snippets/constituentHeader.jsp">
			<jsp:param name="currentFunctionTitleText" value="Audit" />
		</jsp:include>
		<!--<div class="searchResultsHeader">
			<h4 class="searchResults">Audit History</h4>
		</div>-->

        <div id="auditHistoryGrid"></div>
        
        </div>
	</tiles:putAttribute>
</tiles:insertDefinition>