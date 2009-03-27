<%@ include file="/WEB-INF/jsp/include.jsp" %>

<%-- THIS IS POORLY DONE; TODO: redo --%>

<spring:message code='viewGift' var="titleText" />
<tiles:insertDefinition name="base">
	<tiles:putAttribute name="browserTitle" value="${titleText}" />
	<tiles:putAttribute name="primaryNav" value="People" />
	<tiles:putAttribute name="secondaryNav" value="Search" />
	<tiles:putAttribute name="sidebarNav" value="Gifts" />
	<tiles:putAttribute name="mainContent" type="string">
		<div class="content760 mainForm">
			<mp:page pageName='giftView' />

			<c:set var="person" value="${gift.person}" scope="request" />
			<c:if test="${person.id != null}">
				<c:set var="viewingPerson" value="true" scope="request" />
			</c:if>
						
			<form:form method="post" commandName="gift">
				<spring:message code='submitGift' var="submitText" />
				<jsp:include page="../snippets/personHeader.jsp">
					<jsp:param name="currentFunctionTitleText" value="${titleText}" />
					<jsp:param name="submitButtonText" value="${submitText}" />
				</jsp:include>

				<jsp:include page="../snippets/standardFormErrors.jsp"/>
				
				<c:choose>
					<c:when test="${gift.originalGiftId == null}">
						<h3 class="info"><spring:message code='thisGiftEntered'/> <fmt:formatDate value="${gift.transactionDate}"/>&nbsp;<spring:message code='at'/>&nbsp;<fmt:formatDate value="${gift.transactionDate}" type="time" />.</h3>
					</c:when>
					<c:otherwise>
						<h3 class="info"><spring:message code='thisRefundOf'/> <a href="giftView.htm?giftId=${gift.originalGiftId}"><spring:message code='previouslyEnteredGift'/></a>, <spring:message code='refundedOn'/> <fmt:formatDate value="${gift.transactionDate}"/> <spring:message code='at'/> <fmt:formatDate value="${gift.transactionDate}" type="time" />.</h3>
					</c:otherwise>
				</c:choose>
<%--				
				<c:if test="${gift.refundGiftId != null}">
					<h3 class="info">This gift was <a href="giftView.htm?giftId=${gift.refundGiftId}">refunded</a> on <fmt:formatDate value="${gift.refundGiftTransactionDate}"/> at <fmt:formatDate value="${gift.refundGiftTransactionDate}" type="time" />.</h3>
				</c:if>
--%>

				
				<c:set var="gridCollectionName" value="distributionLines" />
				<c:set var="gridCollection" value="${gift.distributionLines}" />
				<c:set var="paymentSource" value="${gift.paymentSource}" />

				<c:forEach var="sectionDefinition" items="${columnSections}">
					<%-- Copy of fieldLayout.jsp with some bugs to fix; TODO: fix! --%>
					<mp:section sectionDefinition="${sectionDefinition}"/>
					<c:set var="totalFields" value="${sectionFieldCount}"/>
					<c:if test="${sectionDefinition.layoutType eq 'TWO_COLUMN'}">
						<h4 class="formSectionHeader"><mp:sectionHeader sectionDefinition="${sectionDefinition}" /></h4>
						<div class="columns">
							<div class="column">
								<ul class="formFields width385">
									<c:forEach var="sectionField" items="${sectionFieldList}" begin="0" end="${(totalFields div 2)+((totalFields%2)-1)}" varStatus="status">
										<mp:field sectionField='${sectionField}' sectionFieldList='${sectionFieldList}' />
										<%@ include file="/WEB-INF/jsp/snippets/input.jsp"%>
									</c:forEach>
									<li class="clear"></li>
								</ul>
							</div>
							<div class="column">
								<ul class="formFields width385">
									<c:forEach var="sectionField" items="${sectionFieldList}" begin="${(totalFields div 2)+(totalFields%2)}">
										<mp:field sectionField='${sectionField}' sectionFieldList='${sectionFieldList}' />
										<%@ include file="/WEB-INF/jsp/snippets/input.jsp"%>
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
							<div class="column <c:out value='${sectionDefinition.sectionHtmlName}'/>" id="<c:out value='${sectionDefinition.sectionHtmlName}'/>" 
								style=" singleColumn<c:if test="${sectionDefinition.layoutType eq 'ONE_COLUMN_HIDDEN'}"> display:none;</c:if>">
								<c:if test="${!empty sectionDefinition.defaultLabel}">
									<h4 class="formSectionHeader"><mp:sectionHeader sectionDefinition="${sectionDefinition}" /></h4>
								</c:if>
								<ul class="formFields width385">
									<c:forEach var="sectionField" items="${sectionFieldList}" varStatus="status">
										<mp:field sectionField='${sectionField}' sectionFieldList='${sectionFieldList}' />
										<%@ include file="/WEB-INF/jsp/snippets/input.jsp"%>
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
										<%@ include file="/WEB-INF/jsp/snippets/input.jsp"%>
									</c:forEach>
									<li class="clear"></li>
								</ul>
							</div>
						</c:if>
					</c:forEach>
					<div class="clearColumns"></div>
				</div>
				
				<%@ include file="/WEB-INF/jsp/gift/distributionLinesView.jsp"%>
				<div class="formButtonFooter personFormButtons">
					<input type="submit" value="<spring:message code='submitGift'/>" class="saveButton" />
					<c:if test="${pageAccess['/giftList.htm']!='DENIED'}">
						<input type="button" value="<spring:message code='cancel'/>" class="saveButton" onclick="OrangeLeap.gotoUrl('giftList.htm?personId=${person.id}')"/>
					</c:if>
					<a class="newAccountButton" href="gift.htm?personId=${person.id}"><spring:message code='enterNewGift'/></a>
				</div>
			</form:form>
		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>

<%--
<%@ include file="/WEB-INF/jsp/include.jsp" %>
<tiles:insertDefinition name="base">
	<tiles:putAttribute name="browserTitle" value="View Gift" />
	<tiles:putAttribute name="primaryNav" value="People" />
	<tiles:putAttribute name="secondaryNav" value="Search" />
	<tiles:putAttribute name="sidebarNav" value="" />
	<tiles:putAttribute name="mainContent" type="string">
		<div class="content760 mainForm">
			<mp:page pageName='giftView'/>
			<c:set var="person" value="${gift.person}" scope="request" />
			<c:if test="${person.id!=null}">
				<c:set var="viewingPerson" value="true" scope="request" />
			</c:if>
			<form:form method="post" commandName="gift">

				<jsp:include page="../snippets/personHeader.jsp">
					<jsp:param name="currentFunctionTitleText" value="View Gift" />
				</jsp:include>

				<c:choose>
					<c:when test="${gift.originalGiftId == null}">
						<h3 class="info">This gift was entered on <fmt:formatDate value="${gift.transactionDate}"/> at <fmt:formatDate value="${gift.transactionDate}" type="time" />.</h3>
					</c:when>
					<c:otherwise>
						<h3 class="info">This is a refund of a <a href="giftView.htm?giftId=${gift.originalGiftId}">previously entered gift</a>, refunded on <fmt:formatDate value="${gift.transactionDate}"/> at <fmt:formatDate value="${gift.transactionDate}" type="time" />.</h3>
					</c:otherwise>
				</c:choose>
				<c:if test="${gift.refundGiftId != null}">
					<h3 class="info">This gift was <a href="giftView.htm?giftId=${gift.refundGiftId}">refunded</a> on <fmt:formatDate value="${gift.refundGiftTransactionDate}"/> at <fmt:formatDate value="${gift.refundGiftTransactionDate}" type="time" />.</h3>
				</c:if>
				<div class="columns">
					<c:forEach var="sectionDefinition" items="${sectionDefinitions}" begin="0" end="0">
							<%@ include file="/WEB-INF/jsp/snippets/fieldLayout.jsp" %>
					</c:forEach>
					<c:forEach var="sectionDefinition" items="${sectionDefinitions}" begin="1" end="3">
						<c:if test="${sectionDefinition.defaultLabel==gift.paymentType}">
							<%@ include file="/WEB-INF/jsp/snippets/fieldLayout.jsp" %>
						</c:if>
					</c:forEach>
					<div class="clearColumns"></div>
				</div>
				<c:forEach var="sectionDefinition" items="${sectionDefinitions}" begin="4">
					<c:if test="${!empty sectionDefinition.defaultLabel}">
						<h4 class="gridSectionHeader"><mp:sectionHeader sectionDefinition="${sectionDefinition}" /></h4>
					</c:if>
					<table class="tablesorter" cellspacing="0" cellpadding="0">
						<thead>
							<c:forEach items="${gift.distributionLines}" var="row" begin="0" end="0">
								<tr>
									<%@ include file="/WEB-INF/jsp/snippets/gridResultsHeader.jsp" %>
								</tr>
							</c:forEach>
						</thead>
						<tbody>
							<c:forEach items="${gift.distributionLines}" var="row">
								<tr>
									<%@ include file="/WEB-INF/jsp/snippets/gridResults.jsp" %>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</c:forEach>

				<div class="formButtonFooter">
					<c:if test="${pageAccess['/giftList.htm']!='DENIED'}">
						<input type="button" value="<spring:message code='viewGiftHistory'/>" class="saveButton" onclick="OrangeLeap.gotoUrl('giftList.htm?personId=${person.id}')"/>
					</c:if>
					<input type="button" value="<spring:message code='enterNewGift'/>" class="saveButton" onclick="OrangeLeap.gotoUrl('gift.htm?personId=${person.id}')"/>
					<c:if test="${gift.originalGiftId == null && gift.refundGiftId == null}">
						<input type="button" value="<spring:message code='refundGift'/>" class="saveButton" onclick="OrangeLeap.confirmGoToUrl('giftRefund.htm?giftId=${gift.id}', '<spring:message code='confirmRefundGift'/>')"/>
					</c:if>
				</div>
			</form:form>
		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>
--%>