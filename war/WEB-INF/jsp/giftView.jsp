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

<form:form method="post" commandName="gift">
	<div class="columns iconHeader">
		<div class="column">
			<img src="images/dude2.gif" />
		</div>
		<div class="column">
		<c:choose>
		<c:when test="${param.personId!=null || id != null || person.id != null}">
			<c:set scope="request" var="viewingAccount" value="true" />
			<h2 class="personEdit">
				${person.lastName}<c:if test="${!empty person.lastName && !empty person.firstName}">, </c:if>${person.firstName}<c:if test="${person.majorDonor}"><span class="majorDonor">(Major Donor)</span></c:if>
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
	<c:choose>
		<c:when test="${gift.originalGift == null}">
			<h3 style="color:#070;">This gift was entered on <fmt:formatDate value="${gift.giftEnteredDate}"/> at <fmt:formatDate value="${gift.giftEnteredDate}" type="time" />.</h3>
		</c:when>
		<c:otherwise>
			<h3 style="color:#070;">This is a refund of a <a href="giftView.htm?giftId=${gift.originalGift.id}">previously entered gift</a>, refunded on <fmt:formatDate value="${gift.giftEnteredDate}"/> at <fmt:formatDate value="${gift.giftEnteredDate}" type="time" />.</h3>
		</c:otherwise>
	</c:choose>
	<c:if test="${gift.refundGift != null}">
		<h3 style="color:#070;">This gift was <a href="giftView.htm?giftId=${gift.refundGift.id}">refunded</a> on <fmt:formatDate value="${gift.giftEnteredDate}"/> at <fmt:formatDate value="${gift.refundGift.giftEnteredDate}" type="time" />.</h3>
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
				<c:if test="${gift.originalGift == null && gift.refundGift == null}">
					<a class="actionLink" onclick="return(confirm('Are you sure you want to refund this gift?'));" href="giftRefund.htm?giftId=${gift.id}">Refund this gift</a>
				</c:if>
			</div>
		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>