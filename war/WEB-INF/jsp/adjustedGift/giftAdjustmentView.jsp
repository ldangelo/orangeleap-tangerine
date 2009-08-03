<%@ include file="/WEB-INF/jsp/include.jsp" %>
<page:applyDecorator name="form">
	<spring:message code='viewAdjustment' var="titleText" scope="request" />
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
				<tangerine:fields pageName="adjustedGiftView"/>

				<div class="formButtonFooter constituentFormButtons">
					<input type="submit" value="<spring:message code='submit'/>" class="saveButton" />
					<c:if test="${pageAccess['/giftView.htm']!='DENIED'}">
						<input type="button" value="<spring:message code='cancel'/>" class="saveButton" onclick="OrangeLeap.gotoUrl('giftView.htm?constituentId=${constituent.id}&giftId=${form.domainObject.originalGiftId}')"/>
					</c:if>
					<c:if test="${!requestScope.hideAdjustGiftButton}">
						<a class="newAccountButton" href="giftAdjustment.htm?giftId=${form.domainObject.originalGiftId}&constituentId=${constituent.id}"><spring:message code='enterNew'/></a>
					</c:if>
				</div>
			</form:form>
			<page:param name="scripts">
				<script type="text/javascript" src="js/payment/paymentTypeReadOnly.js"></script>
				<script type="text/javascript">PaymentTypeCommandObject = '<c:out value="${requestScope.domainObjectName}"/>';</script>
				<script type="text/javascript" src="js/gift/distributionReadOnly.js"></script>
				<script type="text/javascript" src="js/gift/adjustedDistributionReadOnly.js"></script>
			</page:param>
		</body>
	</html>
</page:applyDecorator>
