<%@ include file="/WEB-INF/jsp/include.jsp" %>
<div class="columns iconHeader">
	<div class="column">
		<img src="images/dude2.gif" />
	</div>
	<div class="column">
	<c:choose>
	<c:when test="${viewingPerson}">
		<h2 class="personEdit">
			${person.lastName}<c:if test="${!empty person.lastName && !empty person.firstName}">, </c:if>${person.firstName}<c:if test="${person.majorDonor}"><span class="majorDonor">(Major Donor)</span></c:if>
		</h2>
	</c:when>
	<c:otherwise>
		<h2 class="personEdit">
			New Person
		</h2>
	</c:otherwise>
	</c:choose>
		<h3 id="currentFunctionTitle" class="personEdit">
			${param.currentFunctionTitleText}<c:if test="${saved}"><span id="savedMarker">Saved</span></c:if>
		</h3>
	</div>
	<c:if test="${param.submitButtonText!=null}">
		<div class="columnRight" style="padding:19px 19px 0 0;">
			<input type="submit" value="${param.submitButtonText}" />
		</div>
	</c:if>
	<div class="clearColumns"></div>
</div>