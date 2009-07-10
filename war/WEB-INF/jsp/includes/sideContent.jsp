<%@ include file="/WEB-INF/jsp/include.jsp" %>

<decorator:usePage id="basicPage"/>

<div class="sideBar">
	<div class="wrapper">
		<div class="innerContent">
			<c:if test="${requestScope.constituent.id > 0}">
				<h3>
					<c:out value='${requestScope.constituent.displayValue}'/>:
				</h3>

				<div class="accountOptions">
					<div class="navGroup">
						<a class="groupHeader"><spring:message code="profile"/></a>
						<span class="secondary ${requestScope.thisUrl eq '/constituent.htm' ? 'active' : ''}">
							<a class="${requestScope.thisUrl eq '/constituent.htm' ? 'active' : ''}" href="constituent.htm?constituentId=${requestScope.constituent.id}"><spring:message code="summary"/></a>
							<a class="${requestScope.thisUrl eq '/addressManager.htm' ? 'active' : ''}" href="addressManager.htm?constituentId=${requestScope.constituent.id}"><spring:message code="addresses"/></a>
							<a class="${requestScope.thisUrl eq '/emailManager.htm' ? 'active' : ''}" href="emailManager.htm?constituentId=${requestScope.constituent.id}"><spring:message code="emails"/></a>
							<a class="${requestScope.thisUrl eq '/phoneManager.htm' ? 'active' : ''}" href="phoneManager.htm?constituentId=${requestScope.constituent.id}"><spring:message code="phoneNumbers"/></a>
         				    <a class="${requestScope.thisUrl eq '/relationships.htm'?'active' : ''}" href="relationships.htm?constituentId=${requestScope.constituent.id}"><spring:message code="relationships"/></a>
						</span>
					</div>
					<div class="navGroup">
						<a class="groupHeader"><spring:message code="giftManager"/></a>
			            <span class="secondary ${requestScope.thisUrl eq '/gift.htm' ? 'active' : ''}">
				            <a class="${requestScope.thisUrl eq '/gift.htm' ? 'active' : ''}" href="gift.htm?constituentId=${requestScope.constituent.id}"><spring:message code="newGift"/></a>
							<c:if test="${pageAccess['/giftList.htm']!='DENIED'}">
			            		<a class="${requestScope.thisUrl eq '/giftList.htm' ? 'active' : ''}" href="giftList.htm?constituentId=${requestScope.constituent.id}"><spring:message code="gifts"/></a>
							</c:if>
							<a class="${requestScope.thisUrl eq '/recurringGift.htm' ? 'active' : ''}" href="recurringGift.htm?constituentId=${requestScope.constituent.id}"><spring:message code="newRecurringGift"/></a>
				            <c:if test="${pageAccess['/recurringGiftList.htm']!='DENIED'}">
								<a class="${requestScope.thisUrl eq '/recurringGiftList.htm' ? 'active' : ''}" href="recurringGiftList.htm?constituentId=${requestScope.constituent.id}"><spring:message code="recurringGifts"/></a>
				            </c:if>
							<a class="${requestScope.thisUrl eq '/pledge.htm' ? 'active' : ''}" href="pledge.htm?constituentId=${requestScope.constituent.id}"><spring:message code="newPledge"/></a>
				            <c:if test="${pageAccess['/pledgeList.htm']!='DENIED'}">
								<a class="${requestScope.thisUrl eq '/pledgeList.htm' ? 'active' : ''}" href="pledgeList.htm?constituentId=${requestScope.constituent.id}"><spring:message code="pledges"/></a>
				            </c:if>
				            <%--
							<a class="${requestScope.thisUrl eq '/membership.htm' ? 'active' : ''}" href="membership.htm?constituentId=${requestScope.constituent.id}&type=membership"><spring:message code="newMembership"/></a>
				            <c:if test="${pageAccess['/membershipList.htm']!='DENIED'}">
								<a class="${requestScope.thisUrl eq '/membershipList.htm' ? 'active' : ''}" href="membershipList.htm?constituentId=${requestScope.constituent.id}&type=membership"><spring:message code="memberships"/></a>
				            </c:if>
				            --%>
							<a class="${requestScope.thisUrl eq '/giftInKind.htm' ? 'active' : ''}" href="giftInKind.htm?constituentId=${requestScope.constituent.id}"><spring:message code="newGiftInKind"/></a>
				            <c:if test="${pageAccess['/giftInKindList.htm']!='DENIED'}">
								<a class="${requestScope.thisUrl eq '/giftInKindList.htm' ? 'active' : ''}" href="giftInKindList.htm?constituentId=${requestScope.constituent.id}"><spring:message code="giftsInKind"/></a>
				            </c:if>
			            </span>
		            </div>
					<div class="navGroup">
						<a class="groupHeader"><spring:message code="paymentManager"/></a>
						<span class="secondary ${requestScope.thisUrl eq '/paymentManager.htm' ? 'active' : ''}">
							<a class="${requestScope.thisUrl eq '/paymentManager.htm' ? 'active' : ''}" href="paymentManager.htm?constituentId=${requestScope.constituent.id}"><spring:message code="paymentMethods"/></a>
							<a class="${requestScope.thisUrl eq '/paymentHistory.htm' ? 'active' : ''}" href="paymentHistory.htm?constituentId=${requestScope.constituent.id}"><spring:message code="paymentHistory"/></a>
						</span>
					</div>					
					<div class="navGroup">
						<a class="groupHeader"><spring:message code="touchPoints"/></a>
						<span class="secondary ${requestScope.thisUrl eq '/communicationHistory.htm' ? 'active' : ''}">
				            <a class="${requestScope.thisUrl eq '/communicationHistory.htm' ? 'active' : ''}" href="communicationHistory.htm?constituentId=${requestScope.constituent.id}"><spring:message code="enterNewCommunicationHistory"/></a>
			            	<a class="${requestScope.thisUrl eq '/communicationHistoryList.htm' ? 'active' : ''}" href="communicationHistoryList.htm?constituentId=${requestScope.constituent.id}"><spring:message code="communicationHistoryEntries"/></a>
						</span>
					</div>					
					<c:if test="${pageAccess['/audit.htm'] eq 'ALLOWED'}">
						<a class="${requestScope.thisUrl eq '/audit.htm' ? 'active' : ''}" href="audit.htm?object=constituent&id=${requestScope.constituent.id}"><spring:message code='audit'/></a>
				    </c:if>
				</div>
			</c:if>
            <h3><spring:message code="myAccounts"/></h3>
            <div class="myTasks">
                <a href="#" id="sbAllAccountsLink"><spring:message code="allAccounts"/>&nbsp;<span class="taskCount" id="sbAllAccounts"></span></a>
            </div>
		</div>
	</div>
</div>