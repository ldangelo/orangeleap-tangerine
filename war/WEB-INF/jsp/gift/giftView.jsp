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
			<c:if test="${person.id!=null}">
				<c:set var="viewingPerson" value="true" scope="request" />
			</c:if>
			<form:form method="post" commandName="gift">

				<jsp:include page="../snippets/personHeader.jsp">
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
					<c:forEach var="sectionDefinition" items="${sectionDefinitions}" begin="1" end="3">
						<c:if test="${sectionDefinition.defaultLabel==gift.paymentType}">
							<%@ include file="/WEB-INF/jsp/snippets/fieldLayout.jsp" %>
						</c:if>
					</c:forEach>
					<div class="clearColumns"></div>
				</div>
				<c:forEach var="sectionDefinition" items="${sectionDefinitions}" begin="4">
					<c:if test="${!empty sectionDefinition.defaultLabel}">
						<h4 class="gridSectionHeader"><mp:sectionHeader sectionDefinition="${sectionDefinition}" /></h4>
					</c:if>
					<table class="tablesorter" cellspacing="0" cellpadding="0">
						<thead>
							<c:forEach items="${gift.distributionLines}" var="row" begin="0" end="0">
								<tr>
									<%@ include file="/WEB-INF/jsp/snippets/gridResultsHeader.jsp" %>
								</tr>
							</c:forEach>
						</thead>
						<tbody>
							<c:forEach items="${gift.distributionLines}" var="row">
								<tr>
									<%@ include file="/WEB-INF/jsp/snippets/gridResults.jsp" %>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</c:forEach>

				<div class="formButtonFooter">
					<c:if test="${pageAccess['/giftList.htm']!='DENIED'}">
						<input type="button" value="<spring:message code='viewGiftHistory'/>" class="saveButton" onclick="MPower.gotoUrl('giftList.htm?personId=${person.id}')"/>
					</c:if>
					<input type="button" value="<spring:message code='enterNewGift'/>" class="saveButton" onclick="MPower.gotoUrl('gift.htm?personId=${person.id}')"/>
					<c:if test="${gift.originalGiftId == null && gift.refundGiftId == null}">
						<input type="button" value="<spring:message code='refundGift'/>" class="saveButton" onclick="MPower.confirmGoToUrl('giftRefund.htm?giftId=${gift.id}', '<spring:message code='confirmRefundGift'/>')"/>
					</c:if>
				</div>
			</form:form>
		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>