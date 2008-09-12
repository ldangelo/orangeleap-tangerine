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
				<a href="personSearch.htm" class="${primaryNav=='People'?'active':''}">People</a>
			</li>
			<li>
				<a href="giftSearch.htm" class="${primaryNav=='Gifts'?'active':''}">Gifts</a>
			</li>
			<li>
				<a href="codes.htm" class="${primaryNav=='Administration'?'active':''}">Administration</a>
			</li>
			<li>
				<a href="#">Analysis</a>
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
				<a href="person.htm" class="${secondaryNav=='Edit'?'active':''}">Edit</a>
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

</div>