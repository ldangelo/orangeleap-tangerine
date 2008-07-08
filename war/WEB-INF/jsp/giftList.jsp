<%@ include file="/WEB-INF/jsp/include.jsp" %>
<tiles:insertDefinition name="base">
	<tiles:putAttribute name="browserTitle" value="Gift History" />
	<tiles:putAttribute name="primaryNav" value="People" />
	<tiles:putAttribute name="secondaryNav" value="Edit" />
	<tiles:putAttribute name="sidebarNav" value="Gift History" />
	<tiles:putAttribute name="mainContent" type="string">
		<mp:page pageName='giftList' />
		<c:set var="person" value="${person}" scope="request" />
		<c:if test="${person.id!=null}">
			<c:set var="viewingPerson" value="true" scope="request" />
		</c:if>
		<div class="content760 mainForm">

			
						
				
	<div class="columns iconHeader">
		<div class="column">
			<img src="images/dude2.gif" />
		</div>
		<div class="column">
		<c:choose>
		<c:when test="${viewingPerson}">
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
				Gift History
			</h3>
		</div>
		<div class="clearColumns"></div>
	</div>
			
			
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
									<td><a href="giftView.htm?giftId=${row.id}">View</a></td>
									<%@ include file="/WEB-INF/jsp/snippets/gridResults.jsp" %>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</c:forEach>
				<p style="padding-top:12px;text-align:right;"><a class="newLink" href="gift.htm?personId=${person.id}">Enter a New Gift » </a></p>
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