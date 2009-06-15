<%@ include file="/WEB-INF/jsp/include.jsp" %>
<tiles:insertDefinition name="base">
	<tiles:putAttribute name="browserTitle" value="View Pledge" />
	<tiles:putAttribute name="primaryNav" value="People" />
	<tiles:putAttribute name="secondaryNav" value="Search" />
	<tiles:putAttribute name="sidebarNav" value="Pledges" />
	<tiles:putAttribute name="mainContent" type="string">
		<div class="content760 mainForm">
			<mp:page pageName='pledgeView'/>
			<c:set var="constituent" value="${pledge.constituent}" scope="request" />
			<c:if test="${constituent.id != null}">
				<c:set var="viewingConstituent" value="true" scope="request" />
			</c:if>
			
			<form:form method="post" commandName="pledge">
				<spring:message code='viewPledge' var="titleText" />
				<spring:message code='submitPledge' var="submitText" />
				<c:if test="${requestScope.canApplyPayment}">
					<spring:message var="applyPaymentText" code="applyPayment"/>
				</c:if>
				<jsp:include page="../snippets/constituentHeader.jsp">
					<jsp:param name="currentFunctionTitleText" value="${titleText}" />
					<jsp:param name="submitButtonText" value="${submitText}" />
					<jsp:param name="routeButtonText" value="${applyPaymentText}" />
					<jsp:param name="routeUrl" value="gift.htm?constituentId=${constituent.id}&selectedPledgeId=${pledge.id}" />
				</jsp:include>
				
			    <c:set var="gridCollectionName" value="distributionLines" scope="request" />
				<c:set var="gridCollection" value="${pledge.distributionLines}" scope="request" />
				<c:set var="paymentSource" value="${pledge.paymentSource}" scope="request" />

				<h3 class="info"><spring:message code="thisPledgeEntered"/> <fmt:formatDate value="${pledge.createDate}"/>&nbsp;<spring:message code='at'/>&nbsp;<fmt:formatDate value="${pledge.createDate}" type="time" />.</h3>
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
						<c:if test="${sectionDefinition.sectionHtmlName != 'pledge_acknowledgment' && (sectionDefinition.layoutType eq 'ONE_COLUMN' || sectionDefinition.layoutType eq 'ONE_COLUMN_HIDDEN')}">
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
						<c:if test="${sectionDefinition.sectionHtmlName == 'pledge_acknowledgment' && (sectionDefinition.layoutType eq 'ONE_COLUMN' || sectionDefinition.layoutType eq 'ONE_COLUMN_HIDDEN')}">
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
				
				<%@ include file="/WEB-INF/jsp/gift/distributionLinesView.jsp"%>
				<div class="formButtonFooter constituentFormButtons">
					<c:if test="${requestScope.canApplyPayment}">
						<input type="button" value="<c:out value='${applyPaymentText}'/>" class="saveButton" onclick="OrangeLeap.gotoUrl('gift.htm?constituentId=${constituent.id}&selectedPledgeId=${pledge.id}')"/>
					</c:if>
					<input type="submit" value="<spring:message code='submitPledge'/>" class="saveButton" />
		            <c:if test="${pageAccess['/pledgeList.htm']!='DENIED'}">
						<input type="button" value="<spring:message code='cancel'/>" class="saveButton" onclick="OrangeLeap.gotoUrl('pledgeList.htm?constituentId=${constituent.id}')"/>
					</c:if>
					<a class="newAccountButton" href="pledge.htm?constituentId=${constituent.id}"><spring:message code='enterNewPledge'/></a>
				</div>
			</form:form>
		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>