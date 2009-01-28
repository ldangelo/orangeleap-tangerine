<%@ include file="/WEB-INF/jsp/include.jsp" %>
<tiles:insertDefinition name="base">
	<tiles:putAttribute name="browserTitle" value="Gifts" />
	<tiles:putAttribute name="primaryNav" value="People" />
	<tiles:putAttribute name="secondaryNav" value="Edit" />
	<tiles:putAttribute name="sidebarNav" value="Gifts" />
	<tiles:putAttribute name="mainContent" type="string">
		<mp:page pageName='giftList' />
		<c:set var="person" value="${person}" scope="request" />
		<c:if test="${person.id!=null}">
			<c:set var="viewingPerson" value="true" scope="request" />
		</c:if>
		<div class="content760 mainForm">

		<jsp:include page="../snippets/personHeader.jsp">
			<jsp:param name="currentFunctionTitleText" value="Gift History" />
		</jsp:include>
			
		<c:choose>
			<c:when test="${!empty giftList}">
				<div class="searchResultsHeader">
					<h4 class="searchResults">Gifts <strong>1 - ${giftListSize}</strong> of <strong>${giftListSize}</strong></h4>
				</div>

				<mp:page pageName='giftList' />
				<c:forEach var="sectionDefinition" items="${sectionDefinitions}">
					<table id="giftListTable" class="tablesorter" cellspacing="0" cellpadding="0">
						<thead>
							<c:forEach items="${giftList}" var="row" begin="0" end="0">
								<tr>
									<th>&nbsp;</th>
									<%@ include file="/WEB-INF/jsp/snippets/gridResultsHeader.jsp" %>
								</tr>
							</c:forEach>
						</thead>
						<tbody>
							<c:forEach items="${giftList}" var="row">
								<tr>
									<td><a href="giftView.htm?giftId=${row.id}&personId=${row.person.id}">View</a></td>
									<%@ include file="/WEB-INF/jsp/snippets/gridResults.jsp" %>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</c:forEach>
				<p style="padding-top:12px;text-align:right;"><a class="newLink" href="gift.htm?personId=${person.id}">Enter a New Gift � </a></p>
			</c:when>
			<c:when test="${giftList ne null}">
				<p style="margin:8px 0 6px 0;">No gifts have been entered for this person.</p>
				<p>Would you like to <a href="gift.htm?personId=${person.id}">create a new gift</a>?</p>
			</c:when>
			<c:otherwise>
			</c:otherwise>
		</c:choose>

		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>