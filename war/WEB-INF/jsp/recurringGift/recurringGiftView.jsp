<%@ include file="/WEB-INF/jsp/include.jsp" %>
<page:applyDecorator name="form">
	<spring:message code='viewRecurringGift' var="titleText" scope="request" />
	<spring:message code='submit' var="submitText" />

	<c:set var="headerText" value="${titleText}" scope="request"/>

	<html>
		<head>
			<title><c:out value="${titleText}"/></title>
			<script type="text/javascript" src="js/payment/paymentTypeReadOnly.js"></script>
			<script type="text/javascript">var PaymentTypeCommandObject = '<c:out value="${commandObject}"/>';</script>
		</head>
		<body>
			<form:form method="post" commandName="${requestScope.commandObject}">
				<c:set var="topButtons" scope="request">
					<input type="submit" value="<c:out value='${submitText}'/>" class="saveButton" id="submitButton"/>
				</c:set>

				<%@ include file="/WEB-INF/jsp/includes/formHeader.jsp"%>
				<h3 class="info"><spring:message code='thisRecurringGiftEntered'/> <fmt:formatDate value="${form.domainObject.createDate}"/>&nbsp;<spring:message code='at'/>&nbsp;<fmt:formatDate value="${form.domainObject.createDate}" type="time" />.</h3>

				<tangerine:fields pageName="recurringGiftView"/>

				<div class="formButtonFooter constituentFormButtons">
					<input type="submit" value="<spring:message code='submit'/>" class="saveButton" />
					<c:if test="${pageAccess['/giftList.htm']!='DENIED'}">
						<input type="button" value="<spring:message code='cancel'/>" class="saveButton" onclick="OrangeLeap.gotoUrl('recurringGiftList.htm?constituentId=${constituent.id}')"/>
					</c:if>
					<a class="newAccountButton" href="recurringGift.htm?constituentId=${constituent.id}"><spring:message code='enterANewRecurringGift'/></a>
				</div>
			</form:form>
		</body>
	</html>
</page:applyDecorator>