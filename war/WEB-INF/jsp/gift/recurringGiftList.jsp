<%@ include file="/WEB-INF/jsp/include.jsp" %>
<spring:message code='recurringGifts' var="titleText" />

<tiles:insertDefinition name="base">
	<tiles:putAttribute name="browserTitle" value="${titleText}" />
	<tiles:putAttribute name="primaryNav" value="People" />
	<tiles:putAttribute name="secondaryNav" value="Edit" />
	<tiles:putAttribute name="sidebarNav" value="Recurring Gifts" />
	<tiles:putAttribute name="mainContent" type="string">
		<mp:page pageName='recurringGiftList' />
		<c:set var="person" value="${person}" scope="request" />
		<c:if test="${person.id!=null}">
			<c:set var="viewingPerson" value="true" scope="request" />
		</c:if>
		<div class="content760 mainForm">

		<jsp:include page="../snippets/personHeader.jsp">
			<jsp:param name="currentFunctionTitleText" value="${titleText}" />
		</jsp:include>

		<c:choose>
			<c:when test="${!empty list}">
				<div class="searchResultsHeader">
					<h4 class="searchResults"><spring:message code='recurringGifts'/>&nbsp;<strong>1 - ${listSize}</strong>&nbsp;<spring:message code='of'/>&nbsp;<strong>${listSize}</strong></h4>
				</div>

				<mp:page pageName='recurringGiftList' />
				<c:forEach var="sectionDefinition" items="${sectionDefinitions}">
					<table id="recurringGiftListTable" class="tablesorter" cellspacing="0" cellpadding="0">
						<thead>
							<c:forEach items="${list}" var="row" begin="0" end="0">
								<tr>
									<th>&nbsp;</th>
									<%@ include file="/WEB-INF/jsp/snippets/gridResultsHeader.jsp" %>
								</tr>
							</c:forEach>
						</thead>
						<tbody>
							<c:forEach items="${list}" var="row">
								<c:choose>
									<c:when test="${empty row.gifts}">
										<tr>
											<td><a href="recurringGift.htm?commitmentId=${row.id}&personId=${row.person.id}"><spring:message code='view'/></a></td>
											<%@ include file="/WEB-INF/jsp/snippets/gridResults.jsp" %>
										</tr>
									</c:when>
									<c:otherwise>
										<tr>
											<td><a href="recurringGiftView.htm?commitmentId=${row.id}&personId=${row.person.id}"><spring:message code='view'/></a></td>
											<%@ include file="/WEB-INF/jsp/snippets/gridResults.jsp" %>
										</tr>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</tbody>
					</table>
				</c:forEach>
				<p style="padding-top:12px;text-align:right;"><a class="newLink" href="recurringGift.htm?personId=${person.id}"><spring:message code='enterANewRecurringGift'/></a></p>
			</c:when>
			<c:when test="${list ne null}">
				<p style="margin:8px 0 6px 0;"><spring:message code='noRecurringGiftsEntered'/></p>
				<p><spring:message code='wouldYouLikeTo'/> <a href="recurringGift.htm?personId=${person.id}"><spring:message code='createNewRecurringGift'/></a>?</p>
			</c:when>
			<c:otherwise>
			</c:otherwise>
		</c:choose>

		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>