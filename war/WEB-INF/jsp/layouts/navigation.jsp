<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<tiles:useAttribute name="primaryNav" />
<tiles:useAttribute name="secondaryNav" />
<div id="banner">
	<ol>
		<li>
			<span id="greeting">Logged into <b><security:authentication property="site" /></b> as <b><security:authentication property="principal.username" /></b></span>
		</li>
		<li>
			<a href="account.htm">My Account</a>
		</li>
		<li>
			<a href="#">Help</a>
		</li>
		<li>
			<a href="<c:url value="/logout"/>">Logout</a>
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
			<li>
				<a href="#">My Work</a>
			</li>
			<li>
				<a href="#">Recent</a>
			</li>
			<li>
				<a href="#">Bookmarks</a>
			</li>
			<li>
				<a href="#">Create New...</a>
				<ul>
					<li>
						<a href="person.htm">Constituent</a>
					</li>
					<c:if test="${viewingPerson}">
						<li class="sectionTitle">For <c:out value="${person.firstName}"/>&nbsp;<c:out value="${person.lastName}"/> ...</li>
						<li>
							<a href="gift.htm?personId=${person.id}">Gift</a>
						</li>
						<li>
							<a href="addressManager.htm?personId=${person.id}">Address</a>
						</li>
						<li>
							<a href="recurringGift.htm?personId=${person.id}&type=recurringGift">Recurring Gift</a>
						</li>
						<li>
							<a href="pledge.htm?personId=${person.id}&type=pledge">Pledge</a>
						</li>
						<li>
							<a href="membership.htm?personId=${person.id}&type=membership">Membership</a>
						</li>
					</c:if>
				</ul>
			</li>
			<li>
				<a href="#">Go To...</a>
				<ul>
					<li>
						<a href="personSearch.htm">Search Constituents</a>
					</li>
					<li>
						<a href="giftSearch.htm">Search Gifts</a>
					</li>
					<li>
						<a href="codes.htm">Manage Codes</a>
					</li>
			<!--  		<li>
						<a href="picklistItems.htm">Manage Picklist Items</a>
					</li> -->
					<li>
						<a href="http://10.0.2.162:8080/jasperserver/login.html" target="_blank">Reporting</a>
					</li>
					<li>
						<a href="siteAudit.htm">Site Audit</a>
					</li>
					<li>
			            <c:if test="${pageAccess['/importexport.htm']=='ALLOWED'}">
						   <a href="importexport.htm">Import/Export</a>
						</c:if>
					</li>
				</ul>
			</li>
		</ul>
		<div class="clearBoth"></div>
	</div>
	<div class="navRightCap"></div>
	<div class="searchBar">
		<form method="post" action="personSearch.htm">
			<input size="30" name="lastName" />
			<select name="type">
				<option value="people">Constituents</option>
				<option value="people">Gifts</option>
			</select>
			<input type="submit" value="Search" class="saveButton" />
		</form>
	</div>
</div>