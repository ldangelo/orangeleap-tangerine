<%@ include file="/WEB-INF/jsp/include.jsp" %>
<page:applyDecorator name="form">
	<spring:message code='adjustGift' var="titleText" scope="request" />
	<spring:message code='submit' var="submitText" />
	
	<c:set var="headerText" value="${titleText}" scope="request"/>

	<html>
		<head>
			<title><c:out value="${titleText} - ${requestScope.constituent.firstLast}"/></title>
		</head>
		<body>
			<form:form method="post" commandName="${requestScope.commandObject}" cssClass="disableForm">
				<c:set var="topButtons" scope="request">
					<input type="submit" value="<c:out value='${submitText}'/>" class="saveButton" id="submitButton"/>
				</c:set>

				<%@ include file="/WEB-INF/jsp/includes/formHeader.jsp"%>
				<tangerine:fields pageName="adjustedGift"/>

				<div class="formButtonFooter constituentFormButtons">
					<input type="submit" value="<spring:message code='submit'/>" class="saveButton" />
					<c:if test="${pageAccess['/giftPaid.htm']!='DENIED'}">
                        <input type="button" value="<spring:message code='cancel'/>" class="button" id="cancelButton"/>
					</c:if>
				</div>
			</form:form>
			<page:param name="scripts">
                <script type="text/javascript">
                    $(function() {
                        $("#cancelButton").click(function() {
                            OrangeLeap.gotoUrl('giftPaid.htm?constituentId=${constituent.id}&giftId=${form.domainObject.originalGiftId}');
                        });
                    });
                </script>
				<script type="text/javascript" src="js/gift/adjustedDistribution.js"></script>
			</page:param>
		</body>
	</html>
</page:applyDecorator>
