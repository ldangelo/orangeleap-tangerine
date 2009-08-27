<%@ include file="/WEB-INF/jsp/include.jsp" %>
<page:applyDecorator name="form">
	<spring:message code='viewPledge' var="titleText" scope="request" />
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
				<h3 class="info"><spring:message code='thisPledgeEntered'/> <fmt:formatDate value="${form.domainObject.createDate}"/>&nbsp;<spring:message code='at'/>&nbsp;<fmt:formatDate value="${form.domainObject.createDate}" type="time" />.</h3>

				<tangerine:fields pageName="pledgeView"/>
				
				<c:if test="${form.domainObject.id > 0}">
						<strong><a class="action" href="scheduleEdit.htm?sourceEntity=pledge&sourceEntityId=${form.domainObject.id}">Payment Schedule&raquo;</a></strong>
				</c:if>

				<div class="formButtonFooter constituentFormButtons">
					<input type="submit" value="<spring:message code='submit'/>" class="saveButton" />
					<c:if test="${pageAccess['/pledgeList.htm']!='DENIED'}">
						<input type="button" value="<spring:message code='cancel'/>" class="saveButton" onclick="OrangeLeap.gotoUrl('pledgeList.htm?constituentId=${constituent.id}')"/>
					</c:if>
					<a class="newAccountButton" href="pledge.htm?constituentId=${constituent.id}"><spring:message code='enterNew'/></a>
				</div>
			</form:form>
            <page:param name="scripts">
                <script type="text/javascript">PaymentTypeCommandObject = '<c:out value="${requestScope.domainObjectName}"/>';</script>
                <script type="text/javascript" src="js/gift/distributionReadOnly.js"></script>
            </page:param>
		</body>
	</html>
</page:applyDecorator>