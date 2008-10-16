<%@ include file="/WEB-INF/jsp/include.jsp" %>
<tiles:useAttribute name="sidebarNav" ignore="true" />
<div class="sideBar">
	<div class="wrapper">
		<div class="innerContent" style="height:600px;">
			<h3>
				Search People
			</h3>
			<form method="post" action="personSearch.htm">
				<input size="18" id="sidebarsearch" name="lastName" />
				<input type="submit" value="Go" />
			</form>
			<a style="font-size:10px;" href="personSearch.htm">Advanced Search</a>
			<br />
			<br />
			<c:if test="${viewingPerson}">
				<h3>
					Person Options
				</h3>
				<div class="accountOptions">
					<a class="${sidebarNav=='Profile'?'active':''}" href="person.htm?personId=${person.id}">Profile</a>
					<a class="${sidebarNav=='Enter Gift'?'active':''}" href="gift.htm?personId=${person.id}">Enter Gift</a>
		            <c:if test="${pageAccess['/giftList.htm']!='DENIED'}">
						<a class="${sidebarNav=='Gift History'?'active':''}" href="giftList.htm?personId=${person.id}">Gift History</a>
		            </c:if>
					<a class="${sidebarNav=='Enter Commitment'?'active':''}" href="commitment.htm?personId=${person.id}">Enter Commitment</a>
		            <c:if test="${pageAccess['/commitmentList.htm']!='DENIED'}">
						<a class="${sidebarNav=='Commitments'?'active':''}" href="commitmentList.htm?personId=${person.id}">Commitments</a>
		            </c:if>
					<a href="#">Addresses</a>
					<a href="#">Payment Methods</a>
					<a class="${sidebarNav=='Payment Manager'?'active':''}" href="paymentManager.htm?personId=${person.id}">Payment Manager</a>
					<a href="#">Account Statement</a>
					<a href="#">Notes</a>
					<a href="#">Dates</a>
					<a href="#">Relationships</a>
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