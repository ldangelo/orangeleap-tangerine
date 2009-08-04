<%@ include file="/WEB-INF/jsp/include.jsp" %>
<div class="iconHeader">
	<div class="column"><img src="images/dude2.gif" /></div>
	<div class="column">
		<c:choose>
			<c:when test="${viewingConstituent}">
				<h2 class="constituentEdit cluetip" rel="constituentView.htm?constituentId=<c:out value='${constituent.id}'/>" id="constituentTitle">
					<c:out value='${constituent.firstLast}'/><c:if test="${constituent.majorDonor}"><span class="majorDonor"><spring:message code='majorDonor'/></span></c:if>
				</h2>
			</c:when>
			<c:otherwise>
				<h2 class="constituentEdit"><spring:message code='newConstituent'/></h2>
			</c:otherwise>
		</c:choose>
		<h3 id="currentFunctionTitle" class="constituentEdit">
			<c:out value='${param.currentFunctionTitleText}'/>
			<c:set var="hasErrors" value="false" scope="page"/>
			<c:if test="${commandObject != null}"><spring:hasBindErrors name="${commandObject}"><c:set var="hasErrors" value="true" scope="page"/></spring:hasBindErrors></c:if>
			<c:if test="${!hasErrors && (saved || param.saved)}"><span id="savedMarker"><spring:message code='saved'/></span></c:if>
		</h3>
	</div>
	<c:if test="${not empty param.submitButtonText || (not empty param.routeButtonText && not empty param.routeUrl) || not empty param.clickButtonText}">
		<div class="columnRight">
			<c:if test="${not empty param.routeButtonText && not empty param.routeUrl}">
				<input type="button" value="<c:out value='${param.routeButtonText}'/>" class="saveButton" onclick="OrangeLeap.gotoUrl('<c:out value='${param.routeUrl}'/>')" id="routeButton"/>
			</c:if>
			<c:if test="${not empty param.clickButtonText}">
				<input type="button" value="<c:out value='${param.clickButtonText}'/>" class="saveButton" id="clickButtonTop"/>
			</c:if>
			<c:if test="${ not empty param.submitButtonText}">
				<input type="submit" value="<c:out value='${param.submitButtonText}'/>" class="saveButton" id="submitButton"/>
			</c:if>
		</div>
	</c:if>
	<div class="clearColumns"></div>
</div>