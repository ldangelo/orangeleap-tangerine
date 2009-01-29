<%@ include file="/WEB-INF/jsp/include.jsp" %>
<tiles:insertDefinition name="base">
	<tiles:putAttribute name="browserTitle" value="New Recurring Gift" />
	<tiles:putAttribute name="primaryNav" value="People" />
	<tiles:putAttribute name="secondaryNav" value="Edit" />
	<tiles:putAttribute name="sidebarNav" value="New Recurring Gift" />
	<tiles:putAttribute name="mainContent" type="string">
		<div class="content760 mainForm">
			<mp:page pageName='recurringGift' />
			<c:set var="person" value="${commitment.person}" scope="request" />
			<c:if test="${person.id != null}">
				<c:set var="viewingPerson" value="true" scope="request" />
			</c:if>
			
			<form:form method="post" commandName="commitment">
				<c:if test="${id != null}"><input type="hidden" name="id" value="<c:out value='${id}'/>" /></c:if>

				<spring:message code='enterRecurringGift' var="titleText" />
				<spring:message code='submitRecurringGift' var="submitText" />
				<jsp:include page="../snippets/personHeader.jsp">
					<jsp:param name="currentFunctionTitleText" value="${titleText}" />
					<jsp:param name="submitButtonText" value="${submitText}" />
				</jsp:include>

				<jsp:include page="../snippets/standardFormErrors.jsp"/>

				<c:set var="gridCollectionName" value="distributionLines" />
				<c:set var="gridCollection" value="${commitment.distributionLines}" />
				<c:set var="paymentSource" value="${commitment.paymentSource}" />

				<div class="columns">
					<c:forEach var="sectionDefinition" items="${columnSections}">
						<%@ include file="/WEB-INF/jsp/snippets/fieldLayout.jsp"%>
					</c:forEach>
					<div class="clearColumns"></div>
				</div>
				<c:forEach var="sectionDefinition" items="${gridSections}">
				<%-- TODO: the following is cut and paste code --%>
					<c:if test="${!empty sectionDefinition.defaultLabel}">
						<h4 class="gridSectionHeader"><mp:sectionHeader sectionDefinition="${sectionDefinition}" /></h4>
					</c:if>
					
					<table class="tablesorter distributionLines" style="table-layout:fixed;" id="<c:out value='${sectionDefinition.sectionHtmlName}'/>" cellspacing="0"> 
						<thead> 
							<c:forEach items="${gridCollection}" var="row" begin="0" end="0">
								<tr>
									<%@ include file="/WEB-INF/jsp/snippets/gridResultsHeader.jsp" %>
									<th class="actionColumn">&nbsp;</th>
								</tr>
							</c:forEach>
						</thead>
						<tbody>
							<c:forEach items="${gridCollection}" var="row" varStatus="status">
								<c:if test="${row != null}">
									<tr rowindex="<c:out value='${status.index}'/>">
										<%@ include file="/WEB-INF/jsp/snippets/gridForm.jsp"%>
										<td><img style="cursor: pointer; display: none;" class="deleteButton" src="images/icons/deleteRow.png" /></td>
									</tr>
								</c:if>
							</c:forEach>
						</tbody>
					</table>
					<div class="gridActions">
						<div id="totalText">
							<spring:message code='total'/>&nbsp;
							<span class="warningText" id="amountsErrorSpan"><spring:message code='mustMatchGiftValue'/></span> 
						</div>
						<div class="value" id="subTotal">0</div>
					</div>
				</c:forEach>
				<div class="formButtonFooter personFormButtons"><input type="submit" value="<spring:message code='submitRecurringGift'/>" class="saveButton" /></div>
			</form:form>
		</div>
		<script type="text/javascript" src="js/gift/distribution.js"></script>
	</tiles:putAttribute>
</tiles:insertDefinition>