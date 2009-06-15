<%@ include file="/WEB-INF/jsp/include.jsp"%>
<tiles:insertDefinition name="base">
    <tiles:putAttribute name="customHeaderContent" type="string">
        <script type="text/javascript" src="js/paymentHistoryList.js"></script>
    </tiles:putAttribute>
	<tiles:putAttribute name="browserTitle" value="Payment History" />
	<tiles:putAttribute name="primaryNav" value="People" />
	<tiles:putAttribute name="secondaryNav" value="Edit" />
	<tiles:putAttribute name="sidebarNav" value="Payment History" />
	<tiles:putAttribute name="mainContent" type="string">
		<div class="content760 mainForm">
		<c:set var="constituent" value="${constituent}" scope="request" />
		<c:if test="${constituent.id!=null}">
			<c:set var="viewingConstituent" value="true" scope="request" />
		</c:if>
		<jsp:include page="../snippets/constituentHeader.jsp">
			<jsp:param name="currentFunctionTitleText" value="Payment History" />
		</jsp:include>
		<!--<div class="searchResultsHeader">
			<h4 class="searchResults">Payment History</h4>
		</div>-->

        <div id="paymentHistoryGrid"></div>
        
        </div>
	</tiles:putAttribute>
</tiles:insertDefinition>