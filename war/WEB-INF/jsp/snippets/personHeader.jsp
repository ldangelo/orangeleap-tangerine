<%@ include file="/WEB-INF/jsp/include.jsp" %>
<div class="columns iconHeader">
	<div class="column">
		<img src="images/dude2.gif" />
	</div>
	<div class="column">
	<c:choose>
	<c:when test="${viewingPerson}">
		<h2 class="personEdit cluetip" rel="personView.htm?personId=<c:out value='${person.id}'/>" id="personTitle">
			<c:out value='${person.lastName}'/><c:if test="${!empty person.lastName && !empty person.firstName}">, </c:if><c:out value='${person.firstName}'/><c:if test="${person.majorDonor}"><span class="majorDonor">(Major Donor)</span></c:if>
		</h2>
	</c:when>
	<c:otherwise>
		<h2 class="personEdit">
			New Constituent
		</h2>
	</c:otherwise>
	</c:choose>
		<h3 id="currentFunctionTitle" class="personEdit">
			<c:out value='${param.currentFunctionTitleText}'/><c:if test="${saved}"><span id="savedMarker">Saved</span></c:if>
		</h3>
	</div>
	<c:if test="${param.submitButtonText!=null}">
		<div class="columnRight" style="padding:19px 19px 0 0;">
			<input type="submit" value="<c:out value='${param.submitButtonText}'/>" class="saveButton" />
		</div>
	</c:if>
	<div class="clearColumns"></div>
</div>