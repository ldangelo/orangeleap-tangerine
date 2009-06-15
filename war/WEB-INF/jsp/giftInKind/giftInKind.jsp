<%@ include file="/WEB-INF/jsp/include.jsp" %>


<%-- THIS IS POORLY DONE; TODO: redo --%>

<spring:message code='enterGiftInKind' var="titleText" />

<tiles:insertDefinition name="base">
	<tiles:putAttribute name="browserTitle" value="${titleText}" />
	<tiles:putAttribute name="primaryNav" value="People" />
	<tiles:putAttribute name="secondaryNav" value="Edit" />
	<tiles:putAttribute name="sidebarNav" value="New Gift-In-Kind" />
	<tiles:putAttribute name="mainContent" type="string">
		<div class="content760 mainForm">
			<mp:page pageName='giftInKind' />
			<c:set var="constituent" value="${giftInKind.constituent}" scope="request" />
			<c:if test="${constituent.id != null}">
				<c:set var="viewingConstituent" value="true" scope="request" />
			</c:if>
						
			<form:form method="post" commandName="giftInKind">
			
				<spring:message code='submitGiftInKind' var="submitText" />
				<jsp:include page="../snippets/constituentHeader.jsp">
					<jsp:param name="currentFunctionTitleText" value="${titleText}" />
					<jsp:param name="submitButtonText" value="${submitText}" />
				</jsp:include>
				
				<jsp:include page="../snippets/standardFormErrors.jsp"/>
				
				<c:set var="gridCollectionName" value="mutableDetails" scope="request"/>
				<c:set var="gridCollection" value="${giftInKind.details}" scope="request" />
				<c:set var="dummyGridCollection" value="${giftInKind.dummyDetails}" scope="request" />

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
						<c:if test="${sectionDefinition.layoutType eq 'ONE_COLUMN' || sectionDefinition.layoutType eq 'ONE_COLUMN_HIDDEN'}">
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
				<%@ include file="/WEB-INF/jsp/giftInKind/giftInKindDetails.jsp"%>

				<div class="formButtonFooter constituentFormButtons">
					<input type="submit" value="<spring:message code='submitGiftInKind'/>" class="saveButton" />
					<c:if test="${pageAccess['/giftInKindList.htm']!='DENIED'}">
						<input type="button" value="<spring:message code='cancel'/>" class="saveButton" onclick="OrangeLeap.gotoUrl('giftInKindList.htm?constituentId=${constituent.id}')"/>
					</c:if>
					<c:if test="${param.giftInKindId > 0}">
						<a class="newAccountButton" href="giftInKind.htm?constituentId=${constituent.id}"><spring:message code='enterNewGiftInKind'/></a>
					</c:if>
				</div>
			</form:form>
		</div>
		<script type="text/javascript" src="js/gift/giftInKindDetails.js"></script>
	</tiles:putAttribute>
</tiles:insertDefinition>