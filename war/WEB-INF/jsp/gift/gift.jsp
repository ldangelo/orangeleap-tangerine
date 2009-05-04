<%@ include file="/WEB-INF/jsp/include.jsp" %>
<spring:message code='enterGift' var="titleText" />
<tiles:insertDefinition name="base">
	<tiles:putAttribute name="browserTitle" value="${titleText}" />
    <tiles:putAttribute name="customHeaderContent" type="string">
		<script type="text/javascript" src="js/payment/paymentEditable.js"></script>
		<script type="text/javascript">PaymentEditable.commandObject = '<c:out value="${commandObject}"/>';</script>
		<script type="text/javascript" src="js/gift/distribution.js"></script>
		<script type="text/javascript" src="js/gift/pledgeRecurringGiftSelector.js"></script>
    </tiles:putAttribute>
	<tiles:putAttribute name="primaryNav" value="People" />
	<tiles:putAttribute name="secondaryNav" value="Edit" />
	<tiles:putAttribute name="sidebarNav" value="New Gift" />
	<tiles:putAttribute name="mainContent" type="string">
		<div class="content760 mainForm">
			<mp:page pageName='gift' />
			<c:set var="person" value="${gift.person}" scope="request" />
			<input type="hidden" id="thisConstituentId" name="thisConstituentId" value="<c:out value='${person.id}'/>"/> 
			<c:if test="${person.id != null}">
				<c:set var="viewingPerson" value="true" scope="request" />
			</c:if>
			<c:choose>
				<c:when test="${associatedPledge != null}">
					<input type="hidden" id="thisAssociatedPledge" name="thisAssociatedPledge" value="<c:out value='${associatedPledge.shortDescription}'/>" pledgeId="<c:out value='${associatedPledge.id}'/>"/>
				</c:when>
				<c:when test="${associatedRecurringGift != null}">
					<input type="hidden" id="thisAssociatedRecurringGift" name="thisAssociatedRecurringGift" value="<c:out value='${associatedRecurringGift.shortDescription}'/>" recurringGiftId="<c:out value='${associatedRecurringGift.id}'/>"/>
				</c:when>
			</c:choose>
						
			<form:form method="post" commandName="gift">
				<c:if test="${id != null}"><input type="hidden" name="id" value="<c:out value='${id}'/>" /></c:if>
				<%@ include file="/WEB-INF/jsp/payment/checkConflictingPaymentSource.jsp"%>
			
				<spring:message code='submitGift' var="submitText" />
				<jsp:include page="../snippets/personHeader.jsp">
					<jsp:param name="currentFunctionTitleText" value="${titleText}" />
					<jsp:param name="submitButtonText" value="${submitText}" />
				</jsp:include>
				
				<jsp:include page="../snippets/standardFormErrors.jsp"/>
				
				<c:set var="gridCollectionName" value="mutableDistributionLines" scope="request" />
				<c:set var="gridCollection" value="${gift.distributionLines}" scope="request" />
				<c:set var="dummyGridCollection" value="${gift.dummyDistributionLines}" scope="request" />
				<c:set var="paymentSource" value="${gift.paymentSource}" scope="request" />

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
										<mp:field sectionField='${sectionField}' sectionFieldList='${sectionFieldList}' />
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
										<mp:field sectionField='${sectionField}' sectionFieldList='${sectionFieldList}' />
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
						<c:if test="${sectionDefinition.sectionHtmlName != 'gift_acknowledgment' && (sectionDefinition.layoutType eq 'ONE_COLUMN' || sectionDefinition.layoutType eq 'ONE_COLUMN_HIDDEN')}">
							<div class="column singleColumn <c:out value='${sectionDefinition.sectionHtmlName}'/>" id="<c:out value='${sectionDefinition.sectionHtmlName}'/>" 
								style="<c:if test="${sectionDefinition.layoutType eq 'ONE_COLUMN_HIDDEN'}"> display:none;</c:if>">
								<c:if test="${!empty sectionDefinition.defaultLabel}">
									<h4 class="formSectionHeader"><mp:sectionHeader sectionDefinition="${sectionDefinition}" /></h4>
								</c:if>
								<ul class="formFields width385">
									<c:forEach var="sectionField" items="${sectionFieldList}" varStatus="status">
										<mp:field sectionField='${sectionField}' sectionFieldList='${sectionFieldList}' />
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
				<div class="columns">
					<c:forEach var="sectionDefinition" items="${columnSections}">
						<mp:section sectionDefinition="${sectionDefinition}"/>
						<c:if test="${sectionDefinition.sectionHtmlName == 'gift_acknowledgment' && (sectionDefinition.layoutType eq 'ONE_COLUMN' || sectionDefinition.layoutType eq 'ONE_COLUMN_HIDDEN')}">
							<div class="column singleColumn <c:out value='${sectionDefinition.sectionHtmlName}'/>" id="<c:out value='${sectionDefinition.sectionHtmlName}'/>" 
								style="<c:if test="${sectionDefinition.layoutType eq 'ONE_COLUMN_HIDDEN'}"> display:none;</c:if>">
								<c:if test="${!empty sectionDefinition.defaultLabel}">
									<h4 class="formSectionHeader"><mp:sectionHeader sectionDefinition="${sectionDefinition}" /></h4>
								</c:if>
								<ul class="formFields width385">
									<c:forEach var="sectionField" items="${sectionFieldList}" varStatus="status">
										<mp:field sectionField='${sectionField}' sectionFieldList='${sectionFieldList}' />
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
				<div id="giftDistributionLinesDiv">
					<%@ include file="/WEB-INF/jsp/gift/distributionLines.jsp"%>
				</div>
 				<div class="formButtonFooter personFormButtons">
					<input type="submit" value="<spring:message code='submitGift'/>" class="saveButton" />
					<c:if test="${pageAccess['/giftList.htm']!='DENIED'}">
						<input type="button" value="<spring:message code='cancel'/>" class="saveButton" onclick="OrangeLeap.gotoUrl('giftList.htm?personId=${person.id}')"/>
					</c:if>
				</div>
			</form:form>
		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>