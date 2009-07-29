<%@ include file="/WEB-INF/jsp/include.jsp" %>
<page:applyDecorator name="form">
	<spring:message code='adjustGift' var="titleText" scope="request" />
	<spring:message code='submit' var="submitText" />
	
	<c:set var="headerText" value="${titleText}" scope="request"/>

	<html>
		<head>
			<title><c:out value="${titleText}"/></title>
			<script type="text/javascript" src="js/gift/adjustedDistribution.js"></script>
		</head>
		<body>
			<form:form method="post" commandName="${requestScope.commandObject}">
				<c:set var="topButtons" scope="request">
					<input type="submit" value="<c:out value='${submitText}'/>" class="saveButton" id="submitButton"/>
				</c:set>

				<%@ include file="/WEB-INF/jsp/includes/formHeader.jsp"%>
				<tangerine:fields pageName="adjustedGift"/>

				<div class="formButtonFooter constituentFormButtons">
					<input type="submit" value="<spring:message code='submit'/>" class="saveButton" />
					<c:if test="${pageAccess['/giftView.htm']!='DENIED'}">
						<input type="button" value="<spring:message code='cancel'/>" class="saveButton" onclick="OrangeLeap.gotoUrl('giftView.htm?constituentId=${constituent.id}&giftId=${form.domainObject.originalGiftId}')"/>
					</c:if>
				</div>
			</form:form>
		</body>
	</html>
</page:applyDecorator>
<body>
