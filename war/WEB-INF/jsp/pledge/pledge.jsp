<%@ include file="/WEB-INF/jsp/include.jsp" %>
<page:applyDecorator name="form">
	<spring:message code='enterPledge' var="titleText" scope="request" />
	<spring:message code='submit' var="submitText" />
	<c:if test="${requestScope.canApplyPayment}">
		<spring:message code='applyPayment' var="clickText" />
	</c:if>

	<c:set var="headerText" value="${titleText}" scope="request"/>

	<html>
		<head>
			<title><c:out value="${titleText} - ${requestScope.constituent.firstLast}"/></title>
		</head>
		<body>
			<form:form method="post" commandName="${requestScope.commandObject}">
				<c:set var="topButtons" scope="request">
					<c:if test="${not empty clickText}">
						<input type="button" value="<c:out value='${clickText}'/>" class="saveButton" id="clickButtonTop"
						       onclick="OrangeLeap.gotoUrl('gift.htm?constituentId=${constituent.id}&selectedPledgeId=${form.domainObject.id}')" />
					</c:if>
					<input type="submit" value="<c:out value='${submitText}'/>" class="saveButton" id="submitButton"/>
				</c:set>

				<%@ include file="/WEB-INF/jsp/includes/formHeader.jsp"%>

				<tangerine:fields pageName="pledge"/>

				<div class="formButtonFooter constituentFormButtons">
					<c:if test="${not empty clickText}">
						<input type="button" value="<c:out value='${clickText}'/>" class="saveButton" id="clickButtonTop"
						       onclick="OrangeLeap.gotoUrl('gift.htm?constituentId=${constituent.id}&selectedPledgeId=${form.domainObject.id}" />
					</c:if>
					<input type="submit" value="<spring:message code='submit'/>" class="saveButton" />
					<c:if test="${pageAccess['/pledgeList.htm']!='DENIED'}">
						<input type="button" value="<spring:message code='cancel'/>" class="saveButton" onclick="OrangeLeap.gotoUrl('pledgeList.htm?constituentId=${constituent.id}')"/>
					</c:if>
					<c:if test="${form.domainObject.id > 0}">
						<a class="newAccountButton" href="pledge.htm?constituentId=${constituent.id}"><spring:message code='enterNew'/></a>
					</c:if>
				</div>
			</form:form>
			<page:param name="scripts">
				<script type="text/javascript" src="js/gift/recurringGiftCalc.js"></script>
				<script type="text/javascript" src="js/gift/distribution.js"></script>
			</page:param>
		</body>
	</html>
</page:applyDecorator>