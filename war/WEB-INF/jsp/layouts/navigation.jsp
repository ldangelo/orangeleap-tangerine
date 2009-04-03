<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<tiles:useAttribute name="primaryNav" />
<tiles:useAttribute name="secondaryNav" />
<div id="banner">
	<ol>
		<li>
			<span id="greeting"><spring:message code="loggedInto"/>&nbsp;<strong><security:authentication property="site" /></strong> <spring:message code="as"/>&nbsp;<strong><security:authentication property="principal.username" /></strong></span>
		</li>
		<li>
			<a href="account.htm"><spring:message code="myAccount"/></a>
		</li>
		<li>
			<a href="#"><spring:message code="help"/></a>
		</li>
		<li>
			<a href="logout"><spring:message code="logout"/></a>
		</li>
	</ol>
</div>

<div id="navmain">
	<div class="navLeftCap"></div>
	<div class="container">
		<ul class="primaryNav">
			<li>
				<a href="welcome.htm" class="${primaryNav=='Welcome'?'active':''}"><div class="homeIcon">&nbsp;</div></a>
			</li>
			<!--
			<li>
				<a href="#"><spring:message code="myWork"/></a>
			</li>
			-->
			<li>
				<a href="#"><spring:message code="history"/></a>
                <ul id="bookmarkHistory">
                    
                </ul>
			</li>
			<!--
            <li>
				<a href="#"><spring:message code="bookmarks"/></a>
			</li>
			-->
            <li>
                <a href="#"><spring:message code="viewMenu"/></a>
                <ul>
                <li><a href="personList.htm"><spring:message code="viewMenu.personList"/></a></li>
                <c:if test="${viewingPerson}">
					<li class="sectionTitle"><spring:message code='for'/> <c:out value="${person.firstLast}"/>...</li>
                    <li><a href="giftList.htm?personId=${person.id}"><spring:message code="viewMenu.gifts"/></a></li>
                    <li><a href="recurringGiftList.htm?personId=${person.id}"><spring:message code="viewMenu.recurringGifts"/></a></li>
                    <li><a href="pledgeList.htm?personId=${person.id}"><spring:message code="viewMenu.pledges"/></a></li>
                    <li><a href="giftInKindList.htm?personId=${person.id}"><spring:message code="viewMenu.giftsInKind"/></a></li>
                    <li><a href="paymentHistory.htm?personId=${person.id}"><spring:message code="viewMenu.paymentHistory"/></a></li>
                    <li><a href="communicationHistoryList.htm?personId=${person.id}"><spring:message code="viewMenu.touchPoints"/></a></li>
                </c:if>
                </ul>
            </li>
			<li>
				<a href="#"><spring:message code="menuCreateNew"/></a>
				<ul>
					<li>
						<a href="person.htm"><spring:message code="constituent"/></a>
					</li>
					<c:if test="${viewingPerson}">
						<li class="sectionTitle"><spring:message code='for'/> <c:out value="${person.firstLast}"/>...</li>
						<li><a href="gift.htm?personId=${person.id}"><spring:message code="gift"/></a></li>
						<li><a href="recurringGift.htm?personId=${person.id}"><spring:message code="recurringGift"/></a></li>
						<li><a href="pledge.htm?personId=${person.id}"><spring:message code="pledge"/></a></li>
						<li><a href="giftInKind.htm?personId=${person.id}"><spring:message code="giftInKind"/></a></li>
						<li><a href="paymentManager.htm?personId=${person.id}"><spring:message code="paymentMethod"/></a></li>
                    	<li><a href="communicationHistory.htm?personId=${person.id}"><spring:message code="touchPoint"/></a></li>
						<%-- Removed for BETA
						<li>
							<a href="membership.htm?personId=${person.id}&type=membership"><spring:message code="membership"/></a>
						</li>
						--%>
					</c:if>
				</ul>
			</li>
			<li>
				<a href="#"><spring:message code="menuGoTo"/></a>
				<ul>
			  		<li>
						<a href="picklistItems.htm"><spring:message code="managePicklistItems"/></a>
					</li> 
					<li>
					<%
					String contextPrefix = System.getProperty("contextPrefix");
					if (contextPrefix == null) contextPrefix = "";
					pageContext.setAttribute("contextPrefix",contextPrefix);
					%>
						<a href="../${contextPrefix}jasperserver/login.html" target="_blank"><spring:message code="reporting"/></a>
					</li>
					<li>
						<a href="siteAudit.htm"><spring:message code="siteAudit"/></a>
					</li>
					<li>
			            <c:if test="${pageAccess['/importexport.htm']=='ALLOWED'}">
						   <a href="importexport.htm"><spring:message code="importExport"/></a>
						</c:if>
					</li>
				</ul>
			</li>
		</ul>
		<div class="clearBoth"></div>
	</div>
	<div class="navRightCap"></div>
	<div class="searchBar">
		<form method="post" action="personSearch.htm" id="searchForm">
			<input size="30" name="lastName" type="text" />
			<select name="type" id="typeSearch">
				<option value="people"><spring:message code="constituents"/></option>
				<option value="gifts"><spring:message code="gifts"/></option>
			</select>
			<input type="submit" value="<spring:message code='search'/>" class="saveButton" />
		</form>
	</div>
</div>