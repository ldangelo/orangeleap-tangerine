<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<div id="banner">
	<ol>
		<li id="greeting">
			<spring:message code="loggedInto"/>&nbsp;<strong><security:authentication property="details.site" /></strong> <spring:message code="as"/>&nbsp;<strong><security:authentication property="details.userName" /></strong> |
		</li>
		<li>
			<a href="#" id="sec-changepwd"><spring:message code="changePassword"/></a> |
		</li>
		<%--
		<li>
			<a href="account.htm"><spring:message code="myAccount"/></a> |
		</li>
		<li>
			<a href="#"><spring:message code="help"/></a> |
		</li>
		--%>
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
				<a href="welcome.htm" class="<c:if test='${fn:contains(requestScope.thisUrl, "welcome.htm")}'>active</c:if>"><div class="homeIcon">&nbsp;</div></a>
			</li>
			<%--
			<li>
				<a href="#"><spring:message code="myWork"/></a>
			</li>
			--%>
			<li>
				<a href="#"><spring:message code="history"/></a>
                <ul id="bookmarkHistory">
                </ul>
			</li>
			<%--
            <li>
				<a href="#"><spring:message code="bookmarks"/></a>
			</li>
			--%>
            <li>
                <a href="#"><spring:message code="viewMenu"/></a>
                <ul>
                <li><a href="constituentList.htm"><spring:message code="viewMenu.constituentList"/></a></li>
                <c:if test="${requestScope.constituent.id != null}">
					<li class="sectionTitle"><spring:message code='for'/> <c:out value="${constituent.firstLast}"/>...</li>
                    <li><a href="addressList.htm?constituentId=${constituent.id}"><spring:message code="viewMenu.addressList"/></a></li>
                    <li><a href="phoneList.htm?constituentId=${constituent.id}"><spring:message code="viewMenu.phoneList"/></a></li>
                    <li><a href="emailList.htm?constituentId=${constituent.id}"><spring:message code="viewMenu.emailList"/></a></li>
                    <li><a href="relationships.htm?constituentId=${constituent.id}"><spring:message code="viewMenu.relationships"/></a></li>
                    <li><a href="giftList.htm?constituentId=${constituent.id}"><spring:message code="viewMenu.gifts"/></a></li>
                    <li><a href="recurringGiftList.htm?constituentId=${constituent.id}"><spring:message code="viewMenu.recurringGifts"/></a></li>
                    <li><a href="pledgeList.htm?constituentId=${constituent.id}"><spring:message code="viewMenu.pledges"/></a></li>
                    <li><a href="giftInKindList.htm?constituentId=${constituent.id}"><spring:message code="viewMenu.giftsInKind"/></a></li>
                    <li><a href="paymentSourceList.htm?constituentId=${constituent.id}"><spring:message code="viewMenu.paymentMethodList"/></a></li>
                    <li><a href="paymentHistory.htm?constituentId=${constituent.id}"><spring:message code="viewMenu.paymentHistory"/></a></li>
                    <li><a href="communicationHistoryList.htm?constituentId=${constituent.id}"><spring:message code="viewMenu.touchPoints"/></a></li>
                </c:if>
                </ul>
            </li>
			<li>
				<a href="#"><spring:message code="menuCreateNew"/></a>
				<ul>
					<li>
						<a href="constituent.htm"><spring:message code="constituent"/></a>
					</li>
					<c:if test="${requestScope.constituent.id != null}">
						<li class="sectionTitle"><spring:message code='for'/> <c:out value="${constituent.firstLast}"/>...</li>
                        <li><a href="addressManager.htm?constituentId=${constituent.id}"><spring:message code="address"/></a></li>
                        <li><a href="phoneManager.htm?constituentId=${constituent.id}"><spring:message code="phone"/></a></li>
                        <li><a href="emailManager.htm?constituentId=${constituent.id}"><spring:message code="email"/></a></li>
						<li><a href="gift.htm?constituentId=${constituent.id}"><spring:message code="gift"/></a></li>
						<li><a href="recurringGift.htm?constituentId=${constituent.id}"><spring:message code="recurringGift"/></a></li>
						<li><a href="pledge.htm?constituentId=${constituent.id}"><spring:message code="pledge"/></a></li>
						<li><a href="giftInKind.htm?constituentId=${constituent.id}"><spring:message code="giftInKind"/></a></li>
						<li><a href="paymentManager.htm?constituentId=${constituent.id}"><spring:message code="paymentMethod"/></a></li>
                    	<li><a href="communicationHistory.htm?constituentId=${constituent.id}"><spring:message code="touchPoint"/></a></li>
						<%-- Removed for BETA
						<li>
							<a href="membership.htm?constituentId=${constituent.id}&type=membership"><spring:message code="membership"/></a>
						</li>
						--%>
					</c:if>
				</ul>
			</li>
			<li>
				<a href="#"><spring:message code="menuGoTo"/></a>
				<ul>
					<li>
					<%
					String contextPrefix = System.getProperty("contextPrefix");
					if (contextPrefix == null) contextPrefix = "";
					pageContext.setAttribute("contextPrefix",contextPrefix);
					%>
						<a href="../${contextPrefix}jasperserver/login.html" target="_blank"><spring:message code="reporting"/></a>
					</li>
					<li>
			            <c:if test="${pageAccess['/importexport.htm']=='ALLOWED'}">
						   <a href="importexport.htm"><spring:message code="importExport"/></a>
						</c:if>
					</li>
                    <li>
                        <c:if test="${pageAccess['/postbatch.htm']=='ALLOWED'}">
                           <a href="postbatchs.htm"><spring:message code="postbatch"/></a>
                        </c:if>
                    </li>
                    <li>
                      <c:if test="${pageAccess['/logView.htm']=='ALLOWED'}">
                            <a href="logView.htm"><spring:message code="logView"/></a>
                      </c:if>
                    </li>

				</ul>
			</li>
		    <li>
			            <c:if test="${pageAccess['/picklistItems.htm']=='ALLOWED'}">
				<a href="#"><spring:message code="menuAdministration"/></a>
				<ul>
			  		<li>
			            <c:if test="${pageAccess['/picklistItems.htm']=='ALLOWED'}">
   						   <a href="picklistItems.htm"><spring:message code="managePicklistItems"/></a>
						</c:if>
					</li> 
			 		<li>
			            <c:if test="${pageAccess['/fieldRelationshipCustomize.htm']=='ALLOWED'}">
   						   <a href="relationshipList.htm"><spring:message code="manageRelationshipFields"/></a>
						</c:if>
					</li>
			  		<li>
			            <c:if test="${pageAccess['/customField.htm']=='ALLOWED'}">
   						   <a href="customField.htm"><spring:message code="customFieldWizard"/></a>
						</c:if>
					</li> 
			  		<li>
			            <c:if test="${pageAccess['/screenDefinition.htm']=='ALLOWED'}">
   						   <a href="pageTypes.htm"><spring:message code="manageScreenDefinitions"/></a>
						</c:if>
					</li> 
			  		<li>
			            <c:if test="${pageAccess['/siteSettings.htm']=='ALLOWED'}">
   						   <a href="siteDefaults.htm"><spring:message code="siteDefaults"/></a>
						</c:if>
					</li> 
					<li>
			            <c:if test="${pageAccess['/siteSettings.htm']=='ALLOWED'}">
							<a href="siteSettings.htm"><spring:message code="siteSettings"/></a>
						</c:if>
					</li>
					<li>
			            <c:if test="${pageAccess['/siteAudit.htm']=='ALLOWED'}">
							<a href="siteAudit.htm"><spring:message code="siteAudit"/></a>
						</c:if>
					</li>
				</ul>
						</c:if>
            </li>
		</ul>
		<div class="clearBoth"></div>
	</div>
	<div class="navRightCap"></div>
	<div class="searchBar">
		<form method="post" action="constituentSearch.htm" id="searchForm">
			<input size="30" name="lastName" type="text" />
			<select name="type" id="typeSearch">
				<option value="people"><spring:message code="constituents"/></option>
				<option value="gifts"><spring:message code="gifts"/></option>
			</select>
			<input type="submit" value="<spring:message code='search'/>" class="saveButton" />
		</form>
	</div>
</div>