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
			<a href="<c:url value='/logout'/>"><spring:message code="logout"/></a>
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
				<a href="#"><spring:message code="myWork"/></a>
			</li>
			<li>
				<a href="#"><spring:message code="history"/></a>
			</li>
			<li>
				<a href="#"><spring:message code="bookmarks"/></a>
			</li>
			<li>
				<a href="#"><spring:message code="menuCreateNew"/></a>
				<ul>
					<li>
						<a href="person.htm"><spring:message code="constituent"/></a>
					</li>
					<c:if test="${viewingPerson}">
						<li class="sectionTitle">For <c:out value="${person.firstLast}"/>...</li>
						<li>
							<a href="gift.htm?personId=${person.id}"><spring:message code="gift"/></a>
						</li>
						<li>
							<a href="addressManager.htm?personId=${person.id}"><spring:message code="address"/></a>
						</li>
						<li>
							<a href="recurringGift.htm?personId=${person.id}&type=recurringGift"><spring:message code="recurringGift"/></a>
						</li>
						<li>
							<a href="pledge.htm?personId=${person.id}&type=pledge"><spring:message code="pledge"/></a>
						</li>
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
						<a href="codes.htm"><spring:message code="manageCodes"/></a>
					</li>
			  		<li>
						<a href="picklistItems.htm"><spring:message code="managePicklistItems"/></a>
					</li> 
					<li>
						<a href="../jasperserver/login.html" target="_blank"><spring:message code="reporting"/></a>
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