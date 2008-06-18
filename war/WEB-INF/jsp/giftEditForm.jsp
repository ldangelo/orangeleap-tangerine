<%@ include file="/WEB-INF/jsp/include.jsp"%>

<mp:page pageName='gift' />

<form:form method="post" commandName="gift">
	<c:if test="${id != null}">
		<input type="hidden" name="id" value="${id}" />
	</c:if>

	<div class="columns iconHeader">
		<div class="column">
			<img src="images/dude2.gif" />
		</div>
		<div class="column">
		<c:choose>
		<c:when test="${param.personId!=null || id != null}">
			<c:set scope="request" var="viewingAccount" value="true" />
			<h2 class="personEdit">
				${person.lastName}<c:if test="${!empty person.lastName && !empty person.firstName}">, </c:if>${person.firstName}
			</h2>
		</c:when>
		<c:otherwise>
			<h2 class="personEdit">
				New Person
			</h2>
		</c:otherwise>
		</c:choose>
			<h3 id="currentFunctionTitle" class="personEdit">
				Profile<c:if test="${saved}"><span id="savedMarker">Saved</span></c:if>
			</h3>
		</div>
		<div class="columnRight" style="padding:19px 19px 0 0;">
			<input type="submit" value="Save Changes" />
		</div>
		<div class="clearColumns"></div>
	</div>

	<br />

	<c:forEach var="sectionDefinition" items="${sectionDefinitions}">
		<h4 class="formSectionHeader"><mp:sectionHeader sectionDefinition="${sectionDefinition}" /></h4>
		<%@ include file="/WEB-INF/jsp/snippets/fieldLayout.jsp" %>
	</c:forEach>
	<div class="formButtonFooter personFormButtons">
		<input type="submit" value="Save Changes" class="saveButton" />
		<a class="newAccountButton" href="person.htm">Create Another Person » </a>
	</div>
</form:form>