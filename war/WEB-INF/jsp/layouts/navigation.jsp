<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<tiles:useAttribute name="primaryNav" />
<tiles:useAttribute name="secondaryNav" />
<div id="banner">
	<ol>
		<li>
			<span id="greeting">Logged in as RSMITH</span>
		</li>
		<li>
			<a href="#">Your Account</a>
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
				<a href="personSearch.htm" class="${primaryNav=='People'?'active':''}">People</a>
			</li>
			<li>
				<a href="giftSearch.htm" class="${primaryNav=='Gifts'?'active':''}">Gifts</a>
			</li>
			<li>
				<a href="#">Administration</a>
			</li>
			<li>
				<a href="#">Analysis</a>
			</li>
		</ul>
		<div class="clearBoth"></div>
		<ul class="secondaryNav">
			<li>
				<a href="personSearch.htm" class="${secondaryNav=='Search'?'active':''}">Search</a>
			</li>
			<li>
				<a href="person.htm" class="${secondaryNav=='Edit'?'active':''}">Edit</a>
			</li>
			<li>
				<a href="#">Codes</a>
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
		<div class="clearBoth"></div>
	</div>
	<div class="navRightCap"></div>

</div>