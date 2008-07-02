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
	
	<h3 style="color:#070;">This gift was entered on <fmt:formatDate value="${gift.giftEnteredDate}"/> at <fmt:formatDate value="${gift.giftEnteredDate}" type="time" />.</h3>
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
			<div class="formButtonFooter">
				<a class="actionLink" href="giftList.htm?personId=${person.id}">View gift history</a>
				<a class="actionLink" href="gift.htm?personId=${person.id}">Enter a new gift</a>
				<c:if test="${gift.refundable}">
					<a class="actionLink" href="giftRefund.htm?giftId=${gift.id}">Reverse this gift</a>
				</c:if>
			</div>
		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>