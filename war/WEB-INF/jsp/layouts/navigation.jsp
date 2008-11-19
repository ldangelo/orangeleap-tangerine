<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
						<a href="person.htm">Person</a>
					</li>
					<c:if test="${viewingPerson}">
						<li class="sectionTitle">For ${person.firstName}&nbsp;${person.lastName} ...</li>
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
						<a href="personSearch.htm">Search People</a>
					</li>
					<li>
						<a href="giftSearch.htm">Search Gifts</a>
					</li>
					<li>
						<a href="codes.htm">Manage Codes</a>
					</li>
					<li>
						<a href="http://10.0.2.162:8080/jasperserver/login.html" target="_blank">Reporting</a>
					</li>
				</ul>
			</li>
		</ul>
		<div class="clearBoth"></div>
		<c:choose>
		<c:when test="${primaryNav=='People'}">
		<ul class="secondaryNav">
			<li>
				<a href="personSearch.htm" class="${secondaryNav=='Search'?'active':''}">Search</a>
			</li>
			<li>
				<a href="person.htm" class="${secondaryNav=='Edit'?'active':''}">${secondaryNav=='Edit'?'Edit':'New'}</a>
			</li>
			<li>
				<a href="#">Correspond</a>
			</li>
			<li>
				<a href="#">Distribution Lists</a>
			</li>
			<li>
				<a href="#">Query Builder</a>
			</li>
		</ul>
		</c:when>
		<c:when test="${primaryNav=='Gifts'}">
		<ul class="secondaryNav">
			<li>
				<a href="giftSearch.htm" class="${secondaryNav=='Search'?'active':''}">Search</a>
			</li>
		</ul>
		</c:when>
		<c:when test="${primaryNav=='Administration'}">
		<ul class="secondaryNav">
			<li>
				<a href="codes.htm" class="${secondaryNav=='Codes'?'active':''}">Codes</a>
			</li>
			<li>
				<a href="siteAudit.htm" class="${secondaryNav=='Auditing'?'active':''}">Auditing</a>
			</li>
			<li>
				<a href="#" class="${secondaryNav=='Users'?'active':''}">Users</a>
			</li>
		</ul>
		</c:when>
		<c:when test="${primaryNav=='Welcome'}">
		<ul class="secondaryNav">
			<li>
				<div class="navMessage">Please select one of the above modules to get started.</div>
			</li>
		</ul>
		</c:when>
		</c:choose>
		<div class="clearBoth"></div>
	</div>
	<div class="navRightCap"></div>
	<div class="searchBar">
		<form method="post" action="personSearch.htm">
			<input size="30" name="lastName" />
			<select name="type">
				<option value="people">People</option>
				<option value="people">Gifts</option>
			</select>
			<input type="image" src="images/searchBtn.gif" value="Search" class="searchBarBtn" />
		</form>
	</div>
</div>