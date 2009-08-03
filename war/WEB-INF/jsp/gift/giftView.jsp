<%@ include file="/WEB-INF/jsp/include.jsp" %>
<page:applyDecorator name="form">
	<spring:message code='viewGift' var="titleText" scope="request" />
	<spring:message code='submit' var="submitText" />
	<c:if test="${requestScope.hideAdjustGiftButton == false}">
        <spring:message code='adjust' var="clickText"  />
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
						       onclick="OrangeLeap.gotoUrl('giftAdjustment.htm?giftId=${form.domainObject.id}&constituentId=${constituent.id}')" />
					</c:if>
					<input type="submit" value="<c:out value='${submitText}'/>" class="saveButton" id="submitButton"/>
				</c:set>

				<%@ include file="/WEB-INF/jsp/includes/formHeader.jsp"%>

				<tangerine:fields pageName="giftView"/>
				
				<div class="formButtonFooter constituentFormButtons">
					<c:if test="${!requestScope.hideAdjustGiftButton}">
						<input type="button" value="<c:out value='${clickText}'/>" class="saveButton" id="clickButtonBottom"
						       onclick="OrangeLeap.gotoUrl('giftAdjustment.htm?giftId=${form.domainObject.id}&constituentId=${constituent.id}')"/>
					</c:if>
					<input type="submit" value="<spring:message code='submit'/>" class="saveButton" />
					<c:if test="${pageAccess['/giftList.htm']!='DENIED'}">
						<input type="button" value="<spring:message code='cancel'/>" class="saveButton" onclick="OrangeLeap.gotoUrl('giftList.htm?constituentId=${constituent.id}')"/>
					</c:if>
					<a class="newAccountButton" href="gift.htm?constituentId=${constituent.id}"><spring:message code='enterNew'/></a>
				</div>
			</form:form>
			<page:param name="scripts">
				<script type="text/javascript" src="js/payment/paymentTypeReadOnly.js"></script>
				<script type="text/javascript">PaymentTypeCommandObject = '<c:out value="${requestScope.domainObjectName}"/>';</script>
				<script type="text/javascript" src="js/gift/distributionReadOnly.js"></script>
			</page:param>
		</body>
	</html>
</page:applyDecorator>
