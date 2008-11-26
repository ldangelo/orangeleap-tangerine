<%@ include file="/WEB-INF/jsp/include.jsp" %>
<tiles:useAttribute name="sidebarNav" ignore="true" />
<div class="sideBar">
	<div class="wrapper">
		<div class="innerContent" style="height:600px;">
			<c:if test="${viewingPerson}">
				<h3>
					${person.displayValue}:
				</h3>
				<div class="accountOptions">
					<div class="navGroup">
						<a class="groupHeader ${sidebarNav=='Profile'?'active':''}" href="person.htm?personId=${person.id}">Profile</a>
						<span class="secondary ${sidebarNav=='Profile'?'active':''}">
							<a class="${sidebarNav=='Address Manager'?'active':''}" href="addressManager.htm?personId=${person.id}">Addresses</a>
							<a class="${sidebarNav=='Email Manager'?'active':''}" href="emailManager.htm?personId=${person.id}">Emails</a>
							<a class="${sidebarNav=='Phone Manager'?'active':''}" href="phoneManager.htm?personId=${person.id}">Phone Numbers</a>
							<a class="${sidebarNav=='Payment Manager'?'active':''}" href="paymentManager.htm?personId=${person.id}">Payment Methods</a>
						</span>
					</div>
					<div class="navGroup">
		            <c:if test="${pageAccess['/giftList.htm']!='DENIED'}">
						<a class="groupHeader ${sidebarNav=='Gift History'?'active':''}" href="giftList.htm?personId=${person.id}">Gifts</a>
		            </c:if>
		            <span class="secondary ${sidebarNav=='Gift History'?'active':''}">
			            <a class="${sidebarNav=='Enter Gift'?'active':''}" href="gift.htm?personId=${person.id}">New Gift</a>
						<a class="${sidebarNav=='Enter Recurring Gift'?'active':''}" href="recurringGift.htm?personId=${person.id}&type=recurringGift">Enter Recurring Gift</a>
			            <c:if test="${pageAccess['/recurringGiftList.htm']!='DENIED'}">
							<a class="${sidebarNav=='Recurring Gifts'?'active':''}" href="recurringGiftList.htm?personId=${person.id}&type=recurringGift">Recurring Gifts</a>
			            </c:if>
						<a class="${sidebarNav=='Enter Pledge'?'active':''}" href="pledge.htm?personId=${person.id}&type=pledge">Enter Pledge</a>
			            <c:if test="${pageAccess['/pledgeList.htm']!='DENIED'}">
							<a class="${sidebarNav=='Pledges'?'active':''}" href="pledgeList.htm?personId=${person.id}&type=pledge">Pledges</a>
			            </c:if>
						<a class="${sidebarNav=='Enter Membership'?'active':''}" href="membership.htm?personId=${person.id}&type=membership">Enter Membership</a>
			            <c:if test="${pageAccess['/membershipList.htm']!='DENIED'}">
							<a class="${sidebarNav=='Memberships'?'active':''}" href="membershipList.htm?personId=${person.id}&type=membership">Memberships</a>
			            </c:if>
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