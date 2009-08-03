<%@ include file="/WEB-INF/jsp/include.jsp" %>
<page:applyDecorator name="form">
	<spring:message code='enterGift' var="titleText" scope="request" />
	<spring:message code='submit' var="submitText" />
	<c:if test="${requestScope.allowReprocess}">
		<spring:message code='reprocess' var="clickText"  />
	</c:if>

	<c:set var="headerText" value="${titleText}" scope="request"/>

	<html>
		<head>
			<title><c:out value="${titleText} - ${requestScope.constituent.firstLast}"/></title>
		</head>
		<body>
			<c:choose>
				<c:when test="${requestScope.associatedPledge != null}">
					<input type="hidden" id="thisAssociatedPledge" name="thisAssociatedPledge" value="<c:out value='${requestScope.associatedPledge.shortDescription}'/>" pledgeId="<c:out value='${requestScope.associatedPledge.id}'/>"/>
				</c:when>
				<c:when test="${requestScope.associatedRecurringGift != null}">
					<input type="hidden" id="thisAssociatedRecurringGift" name="thisAssociatedRecurringGift" value="<c:out value='${requestScope.associatedRecurringGift.shortDescription}'/>" recurringGiftId="<c:out value='${requestScope.associatedRecurringGift.id}'/>"/>
				</c:when>
			</c:choose>

			<form:form method="post" commandName="${requestScope.commandObject}">
				<c:set var="topButtons" scope="request">
					<c:if test="${not empty clickText}">
						<input type="button" value="<c:out value='${clickText}'/>" class="saveButton" id="clickButtonTop"/>
					</c:if>
					<input type="submit" value="<c:out value='${submitText}'/>" class="saveButton" id="submitButton"/>
				</c:set>

				<%@ include file="/WEB-INF/jsp/includes/formHeader.jsp"%>
				<%@ include file="/WEB-INF/jsp/payment/checkConflictingPaymentSource.jsp"%>

				<tangerine:fields pageName="gift"/>

				<div class="formButtonFooter constituentFormButtons">
					<c:if test="${requestScope.allowReprocess}">
						<input type="button" value="<c:out value='${clickText}'/>" class="saveButton" id="clickButtonBottom" />
					</c:if>
					<input type="submit" value="<spring:message code='submit'/>" class="saveButton" />
					<c:if test="${pageAccess['/giftList.htm']!='DENIED'}">
						<input type="button" value="<spring:message code='cancel'/>" class="saveButton" onclick="OrangeLeap.gotoUrl('giftList.htm?constituentId=${constituent.id}')"/>
					</c:if>
					<c:if test="${form.domainObject.id > 0}">
						<a class="newAccountButton" href="gift.htm?constituentId=${constituent.id}"><spring:message code='enterNew'/></a>
					</c:if>
				</div>
			</form:form>

			<page:param name="scripts">
				<script type="text/javascript" src="js/payment/paymentEditable2.js"></script>
				<script type="text/javascript">PaymentEditable.commandObject = '<c:out value="${requestScope.domainObjectName}"/>';</script>
				<script type="text/javascript" src="js/gift/distribution2.js"></script>
				<script type="text/javascript" src="js/gift/pledgeRecurringGiftSelector.js"></script>
				<script type="text/javascript">
					$(document).ready(function() {
						$("#clickButtonTop, #clickButtonBottom").click(function() {
							$("div.mainForm form").eq(0).append("<input type='hidden' name='doReprocess' id='doReprocess' value='true'/>").submit();
						});
					});
				</script>
			</page:param>
		</body>
	</html>
</page:applyDecorator>