<%@ include file="/WEB-INF/jsp/include.jsp"%>
<tiles:insertDefinition name="base">
    <tiles:putAttribute name="customHeaderContent" type="string">
        <script type="text/javascript" src="js/giftList.js"></script>
    </tiles:putAttribute>
	<tiles:putAttribute name="browserTitle" value="Touch Point Entries" />
	<tiles:putAttribute name="primaryNav" value="People" />
	<tiles:putAttribute name="secondaryNav" value="Edit" />
	<tiles:putAttribute name="sidebarNav" value="Gifts" />
	<tiles:putAttribute name="mainContent" type="string">
		<div class="content760 mainForm">
		<c:set var="person" value="${person}" scope="request" />
		<c:if test="${person.id!=null}">
			<c:set var="viewingPerson" value="true" scope="request" />
		</c:if>
		<jsp:include page="../snippets/personHeader.jsp">
			<jsp:param name="currentFunctionTitleText" value="Gifts" />
		</jsp:include>
		<!--<div class="searchResultsHeader">
			<h4 class="searchResults">Communication History</h4>
		</div>-->

        <div id="giftsGrid"></div>
        
        </div>
	</tiles:putAttribute>
</tiles:insertDefinition>