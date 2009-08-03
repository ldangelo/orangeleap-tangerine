<%@ include file="/WEB-INF/jsp/include.jsp" %>
<page:applyDecorator name="form">
	<spring:message code='viewCommunicationHistory' var="titleText" scope="request" />
	<spring:message code='submit' var="submitText" />
	<c:set var="headerText" value="${titleText}" scope="request"/>

	<html>
		<head>
			<title><c:out value="${titleText} - ${requestScope.constituent.firstLast}"/></title>
		</head>
		<body>
			<form:form method="post" commandName="${requestScope.commandObject}">
				<c:set var="topButtons" scope="request">
					<input type="submit" value="<c:out value='${submitText}'/>" class="saveButton" id="submitButton"/>
				</c:set>
				<%@ include file="/WEB-INF/jsp/includes/formHeader.jsp"%>
				<h3 class="info"><spring:message code='thisCommunicationHistoryEntered'/> <fmt:formatDate value="${form.domainObject.updateDate}"/>&nbsp;<spring:message code='at'/>&nbsp;<fmt:formatDate value="${form.domainObject.updateDate}" type="time" />.</h3>

				<tangerine:fields pageName="communicationHistoryView"/>

				<div class="formButtonFooter constituentFormButtons">
					<input type="submit" value="<spring:message code='submit'/>" class="saveButton" />
					<c:if test="${pageAccess['/communicationHistoryList.htm']!='DENIED'}">
						<input type="button" value="<spring:message code='cancel'/>" class="saveButton" onclick="OrangeLeap.gotoUrl('communicationHistoryList.htm?constituentId=${constituent.id}')"/>
					</c:if>
					<a class="newAccountButton" href="communicationHistory.htm?constituentId=${constituent.id}"><spring:message code='enterNew'/></a>
				</div>
			</form:form>
		</body>
	</html>
</page:applyDecorator>
