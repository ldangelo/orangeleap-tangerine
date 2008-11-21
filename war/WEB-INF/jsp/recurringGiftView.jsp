<%@ include file="/WEB-INF/jsp/include.jsp" %>
<tiles:insertDefinition name="base">
	<tiles:putAttribute name="browserTitle" value="View Recurring Gift" />
	<tiles:putAttribute name="primaryNav" value="People" />
	<tiles:putAttribute name="secondaryNav" value="Search" />
	<tiles:putAttribute name="sidebarNav" value="" />
	<tiles:putAttribute name="mainContent" type="string">
		<div class="content760 mainForm">
			<mp:page pageName='recurringGiftView'/>
			<c:set var="person" value="${commitment.person}" scope="request" />
			<c:if test="${person.id!=null}">
				<c:set var="viewingPerson" value="true" scope="request" />
			</c:if>
			<form:form method="post" commandName="commitment">

				<jsp:include page="snippets/personHeader.jsp">
					<jsp:param name="currentFunctionTitleText" value="View Recurring Gift" />
				</jsp:include>

				<h3 style="color:#070;">This recurring gift was entered on <fmt:formatDate value="${commitment.createDate}"/> at <fmt:formatDate value="${commitment.createDate}" type="time" />.</h3>
					<div class="columns">
						<c:forEach var="sectionDefinition" items="${sectionDefinitions}">
								<%@ include file="/WEB-INF/jsp/snippets/fieldLayout.jsp" %>
						</c:forEach>
						<div class="clearColumns"></div>
					</div>
				<div class="formButtonFooter personFormButtons">
					<input type="submit" value="Save Changes" class="saveButton" />
				</div>
			</form:form>

			<div class="formButtonFooter">
				<c:if test="${pageAccess['/recurringGiftList.htm']!='DENIED'}">
					<a class="actionLink" href="recurringGiftList.htm?personId=${person.id}">View recurring gift history</a>
				</c:if>
				<a class="actionLink" href="recurringGift.htm?personId=${person.id}">Enter a new recurring gift</a>
			</div>
			<c:forEach var="gift" items="${gifts}">
			${gift.transactionDate} ... ${gift.amount}<br />
			</c:forEach>
			Sum:${giftSum}
		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>