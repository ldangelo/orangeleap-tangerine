<%@ include file="/WEB-INF/jsp/include.jsp"%>
<spring:message code='communicationHistoryEntries' var='titleText'/>
<tiles:insertDefinition name="base">
    <tiles:putAttribute name="customHeaderContent" type="string">
        <script type="text/javascript" src="js/communicationHistoryList.js"></script>
    </tiles:putAttribute>
	<tiles:putAttribute name="browserTitle" value="${titleText}" />
	<tiles:putAttribute name="primaryNav" value="People" />
	<tiles:putAttribute name="secondaryNav" value="Edit" />
	<tiles:putAttribute name="sidebarNav" value="communicationHistoryList" />
	<tiles:putAttribute name="mainContent" type="string">
		<div class="content760 mainForm">
		<c:set var="constituent" value="${constituent}" scope="request" />
		<c:if test="${constituent.id!=null}">
			<c:set var="viewingConstituent" value="true" scope="request" />
		</c:if>
		<jsp:include page="../snippets/constituentHeader.jsp">
			<jsp:param name="currentFunctionTitleText" value="Touch Points" />
		</jsp:include>
        <div id="communicationHistoryGrid"></div>
        
        </div>
	</tiles:putAttribute>
</tiles:insertDefinition>