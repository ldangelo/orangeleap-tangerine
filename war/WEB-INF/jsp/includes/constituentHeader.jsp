<%@ include file="/WEB-INF/jsp/include.jsp" %>

<c:set var="hasErrors" value="false" scope="page"/>

<input type="hidden" id="thisConstituentId" name="thisConstituentId" value="<c:out value='${requestScope.constituent.id}'/>"/>

<div class="iconHeader">
	<div class="column">
		<c:choose>
			<c:when test="${requestScope.constituent.id != null}">
				<h2 class="constituentEdit cluetip" rel="constituentView.htm?constituentId=<c:out value='${requestScope.constituent.id}'/>" id="constituentTitle">
					<c:out value='${requestScope.constituent.firstLast}'/><c:if test="${requestScope.constituent.majorDonor}"><span class="majorDonor"><spring:message code='majorDonor'/></span></c:if>
				</h2>
			</c:when>
			<c:otherwise>
				<h2 class="constituentEdit"><spring:message code='newConstituent'/></h2>
			</c:otherwise>
		</c:choose>
		<h3 id="currentFunctionTitle" class="constituentEdit">
			<c:out value='${requestScope.headerText}'/>
			<c:if test="${requestScope.commandObject != null}"><spring:hasBindErrors name="${requestScope.commandObject}"><c:set var="hasErrors" value="true" scope="page"/></spring:hasBindErrors></c:if>
			<c:if test="${!hasErrors && (requestScope.saved || param.saved)}"><span id="savedMarker"><spring:message code='saved'/></span></c:if>
		</h3>
	</div>
	<c:if test="${not empty requestScope.topButtons}">
		<div class="columnRight">
			<c:out value="${requestScope.topButtons}" escapeXml="false"/>
		</div>
	</c:if>
	<div class="clearColumns"></div>
</div>