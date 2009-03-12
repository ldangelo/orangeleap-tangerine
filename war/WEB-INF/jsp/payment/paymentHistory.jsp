<%@ include file="/WEB-INF/jsp/include.jsp" %>
<c:choose>
<c:when test="${param.view=='ajaxResults'}">
<jsp:include page="paymentHistoryResults.jsp"/>
</c:when>
<c:otherwise>
<tiles:insertDefinition name="base">
	<tiles:putAttribute name="browserTitle" value="Payment History" />
	<tiles:putAttribute name="primaryNav" value="People" />
	<tiles:putAttribute name="secondaryNav" value="Payment History" />
	<tiles:putAttribute name="sidebarNav" value="Payment History"/>
	<tiles:putAttribute name="mainContent" type="string">
		<mp:page pageName='paymentHistory'/>
		<c:set var="person" value="${person}" scope="request" />
		<c:if test="${person.id!=null}">
			<c:set var="viewingPerson" value="true" scope="request" />
		</c:if>
		<div class="content760 mainForm">
			<jsp:include page="../snippets/personHeader.jsp">
				<jsp:param name="currentFunctionTitleText" value="Payment History" />
			</jsp:include>
				
			<div id="searchResults">
               <jsp:include page="paymentHistoryResults.jsp"/>
			</div>
		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>
</c:otherwise>
</c:choose>
