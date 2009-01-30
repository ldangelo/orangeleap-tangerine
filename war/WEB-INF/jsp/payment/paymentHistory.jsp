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
	<tiles:putAttribute name="mainContent" type="string">
		<div class="content760 mainForm">
			<div id="searchResults">
               <jsp:include page="paymentHistoryResults.jsp"/>
			</div>
		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>
</c:otherwise>
</c:choose>
