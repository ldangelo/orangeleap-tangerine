<%@ include file="/WEB-INF/jsp/include.jsp" %>

<spring:message code='adjustGift' var="titleText" />
<tiles:insertDefinition name="base">
	<tiles:putAttribute name="browserTitle" value="${titleText}" />
	<tiles:putAttribute name="primaryNav" value="People" />
	<tiles:putAttribute name="secondaryNav" value="Search" />
    <tiles:putAttribute name="customHeaderContent" type="string">
		<script type="text/javascript" src="js/payment/paymentEditable.js"></script>
		<script type="text/javascript">PaymentEditable.commandObject = '<c:out value="${commandObject}"/>';</script>
		<script type="text/javascript" src="js/gift/adjustedDistribution.js"></script>
    </tiles:putAttribute>
	<tiles:putAttribute name="sidebarNav" value="Gifts" />
	<tiles:putAttribute name="mainContent" type="string">
		<div class="content760 mainForm">
			<mp:page pageName='adjustedGift' />

			<c:set var="person" value="${adjustedGift.person}" scope="request" />
			<c:if test="${person.id != null}">
				<c:set var="viewingPerson" value="true" scope="request" />
			</c:if>

			<form:form method="post" commandName="adjustedGift">
				<spring:message code='adjustGift' var="submitText" />
				<jsp:include page="../snippets/personHeader.jsp">
					<jsp:param name="currentFunctionTitleText" value="${titleText}" />
					<jsp:param name="submitButtonText" value="${submitText}" />
				</jsp:include>

				<jsp:include page="../snippets/standardFormErrors.jsp"/>

				<c:set var="gridCollectionName" value="distributionLines" scope="request" />
				<c:set var="gridCollection" value="${adjustedGift.distributionLines}" scope="request" />
				<c:set var="paymentSource" value="${adjustedGift.paymentSource}" scope="request" />

				<c:forEach var="sectionDefinition" items="${columnSections}">
					<%-- Copy of fieldLayout.jsp with some bugs to fix; TODO: fix! --%>
					<mp:section sectionDefinition="${sectionDefinition}"/>
					<c:set var="totalFields" value="${sectionFieldCount}" scope="request"/>
					<c:if test="${sectionDefinition.layoutType eq 'TWO_COLUMN'}">
						<h4 class="formSectionHeader"><mp:sectionHeader sectionDefinition="${sectionDefinition}" /></h4>
						<div class="columns">
							<div class="column">
								<ul class="formFields width385">
									<c:forEach var="sectionField" items="${sectionFieldList}" begin="0" end="${(totalFields div 2)+((totalFields%2)-1)}" varStatus="status">
										<mp:field sectionField='${sectionField}' sectionFieldList='${sectionFieldList}' model="${adjustedGift}"/>
										<c:set var="sectionDefinition" value="${sectionDefinition}" scope="request"/>
										<c:set var="sectionField" value="${sectionField}" scope="request"/>
										<jsp:include page="../snippets/input.jsp"/>
									</c:forEach>
									<li class="clear"></li>
								</ul>
							</div>
							<div class="column">
								<ul class="formFields width385">
									<c:forEach var="sectionField" items="${sectionFieldList}" begin="${(totalFields div 2)+(totalFields%2)}">
										<mp:field sectionField='${sectionField}' sectionFieldList='${sectionFieldList}' model="${adjustedGift}"/>
										<c:set var="sectionDefinition" value="${sectionDefinition}" scope="request"/>
										<c:set var="sectionField" value="${sectionField}" scope="request"/>
										<jsp:include page="../snippets/input.jsp"/>
									</c:forEach>
									<li class="clear"></li>
								</ul>
							</div>
 							<div class="clearColumns"></div>
						</div>
					</c:if>
				</c:forEach>
				<div class="columns">
					<c:forEach var="sectionDefinition" items="${columnSections}">
						<mp:section sectionDefinition="${sectionDefinition}"/>
						<c:if test="${sectionDefinition.layoutType eq 'ONE_COLUMN' || sectionDefinition.layoutType eq 'ONE_COLUMN_HIDDEN'}">
							<div class="column <c:out value='${sectionDefinition.sectionHtmlName}'/>" id="<c:out value='${sectionDefinition.sectionHtmlName}'/>"
								style=" singleColumn<c:if test="${sectionDefinition.layoutType eq 'ONE_COLUMN_HIDDEN'}"> display:none;</c:if>">
								<c:if test="${!empty sectionDefinition.defaultLabel}">
									<h4 class="formSectionHeader"><mp:sectionHeader sectionDefinition="${sectionDefinition}" /></h4>
								</c:if>
								<ul class="formFields width385">
									<c:forEach var="sectionField" items="${sectionFieldList}" varStatus="status">
										<mp:field sectionField='${sectionField}' sectionFieldList='${sectionFieldList}' model="${adjustedGift}"/>
										<c:set var="sectionDefinition" value="${sectionDefinition}" scope="request"/>
										<c:set var="sectionField" value="${sectionField}" scope="request"/>
										<jsp:include page="../snippets/input.jsp"/>
									</c:forEach>
									<li class="clear"></li>
								</ul>
							</div>
						</c:if>
					</c:forEach>
					<div class="clearColumns"></div>
				</div>

				<%@ include file="/WEB-INF/jsp/gift/giftAdjustmentDistributionLines.jsp"%>
				<div class="formButtonFooter personFormButtons">
					<input type="submit" value="<spring:message code='adjustGift'/>" class="saveButton" />
					<c:if test="${pageAccess['/giftList.htm']!='DENIED'}">
						<input type="button" value="<spring:message code='cancel'/>" class="saveButton" onclick="OrangeLeap.gotoUrl('giftList.htm?personId=${person.id}')"/>
					</c:if>
					<a class="newAccountButton" href="gift.htm?personId=${person.id}"><spring:message code='enterNewGift'/></a>
				</div>
			</form:form>
		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>
