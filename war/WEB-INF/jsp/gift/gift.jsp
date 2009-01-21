<%@ include file="/WEB-INF/jsp/include.jsp" %>


<%-- THIS IS POORLY DONE; TODO: redo --%>


<tiles:insertDefinition name="base">
	<tiles:putAttribute name="browserTitle" value="New Gift" />
	<tiles:putAttribute name="primaryNav" value="People" />
	<tiles:putAttribute name="secondaryNav" value="Edit" />
	<tiles:putAttribute name="sidebarNav" value="New Gift" />
	<tiles:putAttribute name="mainContent" type="string">
		<div class="content760 mainForm">
			<mp:page pageName='gift' />
			<c:set var="person" value="${gift.person}" scope="request" />
			<c:if test="${person.id != null}">
				<c:set var="viewingPerson" value="true" scope="request" />
			</c:if>
						
			<form:form method="post" commandName="gift">
				<c:if test="${id != null}"><input type="hidden" name="id" value="<c:out value='${id}'/>" /></c:if>
			
				<jsp:include page="../snippets/personHeader.jsp">
					<jsp:param name="currentFunctionTitleText" value="Enter Gift" />
					<jsp:param name="submitButtonText" value="Submit Payment" />
				</jsp:include>
				
				<jsp:include page="../snippets/standardFormErrors.jsp"/>
				
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
								<ul class="formFields width375">
									<c:forEach var="sectionField" items="${sectionFieldList}" begin="0" end="${(totalFields div 2)+((totalFields%2)-1)}" varStatus="status">
										<mp:field sectionField='${sectionField}' sectionFieldList='${sectionFieldList}' />
										<%@ include file="/WEB-INF/jsp/snippets/input.jsp"%>
									</c:forEach>
									<li class="clear"></li>
								</ul>
							</div>
							<div class="column">
								<ul class="formFields width375">
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
								style="padding-right:8px;<c:if test="${sectionDefinition.layoutType eq 'ONE_COLUMN_HIDDEN'}"> display:none;</c:if>">
								<c:if test="${!empty sectionDefinition.defaultLabel}">
									<h4 class="formSectionHeader"><mp:sectionHeader sectionDefinition="${sectionDefinition}" /></h4>
								</c:if>
								<ul class="formFields width375">
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
							<div class="column <c:out value='${sectionDefinition.sectionHtmlName}'/>" id="<c:out value='${sectionDefinition.sectionHtmlName}'/>" 
								style="padding-right:8px;<c:if test="${sectionDefinition.layoutType eq 'ONE_COLUMN_HIDDEN'}"> display:none;</c:if>">
								<c:if test="${!empty sectionDefinition.defaultLabel}">
									<h4 class="formSectionHeader"><mp:sectionHeader sectionDefinition="${sectionDefinition}" /></h4>
								</c:if>
								<ul class="formFields width375">
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
				<c:forEach var="sectionDefinition" items="${gridSections}">
					<c:if test="${!empty sectionDefinition.defaultLabel}">
						<h4 class="gridSectionHeader"><mp:sectionHeader sectionDefinition="${sectionDefinition}" /></h4>
					</c:if>
					
					<table class="tablesorter" style="table-layout:fixed;" id="<c:out value='${sectionDefinition.sectionHtmlName}'/>" cellspacing="0" cellpadding="0"> 
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
								<c:if test="${row!=null}">
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
							
<%-- 
					<div class="columns">
						<c:forEach var="sectionDefinition" items="${sectionDefinitions}">
							<c:if test="${sectionDefinition.layoutType ne 'GRID'}">
								<%@ include file="/WEB-INF/jsp/snippets/fieldLayout.jsp"%>
							</c:if>
						</c:forEach>
						<div class="clearColumns"></div>
					</div>
					<c:forEach var="sectionDefinition" items="${sectionDefinitions}">
						<c:if test="${sectionDefinition.layoutType eq 'GRID'}">
							<%@ include file="/WEB-INF/jsp/snippets/fieldLayout.jsp"%>
							<p class="gridActions">&nbsp;<span id="subTotal">Total <span class="warningText">(must match gift value)</span> <span class="value">0</span></span></p>
						</c:if>
					</c:forEach>
					--%>
				<div class="formButtonFooter personFormButtons"><input type="submit" value="<spring:message code='submitPayment'/>" class="saveButton" /></div>
			</form:form>
		</div>
		<script type="text/javascript" src="js/gift/distribution.js"></script>
	</tiles:putAttribute>
</tiles:insertDefinition>