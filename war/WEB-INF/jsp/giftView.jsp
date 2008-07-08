<%@ include file="/WEB-INF/jsp/include.jsp" %>
<tiles:insertDefinition name="base">
	<tiles:putAttribute name="browserTitle" value="View Gift" />
	<tiles:putAttribute name="primaryNav" value="People" />
	<tiles:putAttribute name="secondaryNav" value="Search" />
	<tiles:putAttribute name="sidebarNav" value="" />
	<tiles:putAttribute name="mainContent" type="string">
		<mp:page pageName='giftView'/>
		<c:set var="person" value="${gift.person}" scope="request" />
		<c:if test="${person.id!=null}">
			<c:set var="viewingPerson" value="true" scope="request" />
		</c:if>
		
		<div class="content760 mainForm">

<form:form method="post" commandName="gift">

	<jsp:include page="snippets/personHeader.jsp">
		<jsp:param name="currentFunctionTitleText" value="View Gift" />
	</jsp:include>
	
	<c:choose>
		<c:when test="${gift.originalGiftId == null}">
			<h3 style="color:#070;">This gift was entered on <fmt:formatDate value="${gift.transactionDate}"/> at <fmt:formatDate value="${gift.transactionDate}" type="time" />.</h3>
		</c:when>
		<c:otherwise>
			<h3 style="color:#070;">This is a refund of a <a href="giftView.htm?giftId=${gift.originalGiftId}">previously entered gift</a>, refunded on <fmt:formatDate value="${gift.transactionDate}"/> at <fmt:formatDate value="${gift.transactionDate}" type="time" />.</h3>
		</c:otherwise>
	</c:choose>
	<c:if test="${gift.refundGiftId != null}">
		<h3 style="color:#070;">This gift was <a href="giftView.htm?giftId=${gift.refundGiftId}">refunded</a> on <fmt:formatDate value="${gift.refundGiftTransactionDate}"/> at <fmt:formatDate value="${gift.refundGiftTransactionDate}" type="time" />.</h3>
	</c:if>
		<div class="columns">
			<c:forEach var="sectionDefinition" items="${sectionDefinitions}" begin="0" end="0">
					<%@ include file="/WEB-INF/jsp/snippets/fieldLayout.jsp" %>
			</c:forEach>
			<c:forEach var="sectionDefinition" items="${sectionDefinitions}" begin="1">
				<c:if test="${sectionDefinition.defaultLabel==gift.paymentType}">
					<%@ include file="/WEB-INF/jsp/snippets/fieldLayout.jsp" %>
				</c:if>
			</c:forEach>
			<div class="clearColumns"></div>
		</div>
</form:form>
			<div class="formButtonFooter">
				<a class="actionLink" href="giftList.htm?personId=${person.id}">View gift history</a>
				<a class="actionLink" href="gift.htm?personId=${person.id}">Enter a new gift</a>
				<c:if test="${gift.originalGiftId == null && gift.refundGiftId == null}">
					<a class="actionLink" onclick="return(confirm('Are you sure you want to refund this gift?'));" href="giftRefund.htm?giftId=${gift.id}">Refund this gift</a>
				</c:if>
			</div>
		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>