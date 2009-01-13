<%@ include file="/WEB-INF/jsp/include.jsp" %>
<tiles:useAttribute name="sidebarNav" ignore="true" />
<div class="sideBar">
	<div class="wrapper">
		<div class="innerContent" style="height:600px;">
			<c:if test="${viewingPerson}">
				<h3>
					<c:out value='${person.displayValue}'/>:
				</h3>
				
				<%-- TODO: Refactor the way sidebarNav works!!!! --%>
				
				<div class="accountOptions">
					<div class="navGroup">
						<a class="groupHeader" href="#" onclick="this.blur();return false;"><spring:message code="profile"/></a>
						<span class="secondary ${sidebarNav == 'Profile' ? 'active' : ''}">
							<a class="${sidebarNav == 'Profile' ? 'active' : ''}" href="person.htm?personId=${person.id}"><spring:message code="contactInformation"/></a>
							<a class="${sidebarNav == 'Address Manager' ? 'active' : ''}" href="addressManager.htm?personId=${person.id}"><spring:message code="addresses"/></a>
							<a class="${sidebarNav == 'Email Manager' ? 'active' : ''}" href="emailManager.htm?personId=${person.id}"><spring:message code="emails"/></a>
							<a class="${sidebarNav == 'Phone Manager' ? 'active' : ''}" href="phoneManager.htm?personId=${person.id}"><spring:message code="phoneNumbers"/></a>
						</span>
					</div>
					<div class="navGroup">
						<a class="${sidebarNav == 'Payment Manager' ? 'active' : ''}" href="paymentManager.htm?personId=${person.id}"><spring:message code="paymentManager"/></a>
					</div>
					<div class="navGroup">
						<a class="groupHeader" href="#" onclick="this.blur();return false;"><spring:message code="giftManager"/></a>
			            <span class="secondary ${sidebarNav=='Gifts'?'active':''}">
				            <a class="${sidebarNav=='New Gift'?'active':''}" href="gift.htm?personId=${person.id}"><spring:message code="newGift"/></a>
							<c:if test="${pageAccess['/giftList.htm']!='DENIED'}">
			            		<a class="${sidebarNav=='Gifts'?'active':''}" href="giftList.htm?personId=${person.id}"><spring:message code="gifts"/></a>
							</c:if>
							<a class="${sidebarNav=='New Recurring Gift'?'active':''}" href="recurringGift.htm?personId=${person.id}&type=recurringGift"><spring:message code="newRecurringGift"/></a>
				            <c:if test="${pageAccess['/recurringGiftList.htm']!='DENIED'}">
								<a class="${sidebarNav=='Recurring Gifts'?'active':''}" href="recurringGiftList.htm?personId=${person.id}&type=recurringGift"><spring:message code="recurringGifts"/></a>
				            </c:if>
							<a class="${sidebarNav=='New Pledge'?'active':''}" href="pledge.htm?personId=${person.id}&type=pledge"><spring:message code="newPledge"/></a>
				            <c:if test="${pageAccess['/pledgeList.htm']!='DENIED'}">
								<a class="${sidebarNav=='Pledges'?'active':''}" href="pledgeList.htm?personId=${person.id}&type=pledge"><spring:message code="pledges"/></a>
				            </c:if>
				            <%--
							<a class="${sidebarNav=='New Membership'?'active':''}" href="membership.htm?personId=${person.id}&type=membership"><spring:message code="newMembership"/></a>
				            <c:if test="${pageAccess['/membershipList.htm']!='DENIED'}">
								<a class="${sidebarNav=='Memberships'?'active':''}" href="membershipList.htm?personId=${person.id}&type=membership"><spring:message code="memberships"/></a>
				            </c:if>
				            --%>
			            </span>
		            </div>
					<a class="${sidebarNav=='Audit'?'active':''}" href="audit.htm?object=person&id=${person.id}">Audit</a>
				</div>
			</c:if>
			<h3>
				My Tasks
			</h3>
			<div class="myTasks">
				<a href="#">Due Today <span href="#" class="taskCount">(1)</span></a>
				<a href="#">New Tasks <span href="#" class="taskCount">(3)</span></a>
				<a href="#">All Tasks (9)</a>
			</div>
		</div>
	</div>
</div>