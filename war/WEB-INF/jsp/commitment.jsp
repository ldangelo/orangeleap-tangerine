<%@ include file="/WEB-INF/jsp/include.jsp" %>
<tiles:insertDefinition name="base">
	<tiles:putAttribute name="browserTitle" value="Enter Commitment" />
	<tiles:putAttribute name="primaryNav" value="People" />
	<tiles:putAttribute name="secondaryNav" value="Edit" />
	<tiles:putAttribute name="sidebarNav" value="Enter Commitment" />
	<tiles:putAttribute name="mainContent" type="string">
		<div class="content760 mainForm">
			<mp:page pageName='commitment' />
				<c:set var="person" value="${commitment.person}" scope="request" />
				<c:if test="${person.id!=null}">
					<c:set var="viewingPerson" value="true" scope="request" />
				</c:if>

				<form:form method="post" commandName="commitment">
					<c:if test="${id != null}">
						<input type="hidden" name="id" value="${id}" />
					</c:if>

					<jsp:include page="snippets/personHeader.jsp">
						<jsp:param name="currentFunctionTitleText" value="Enter Commitment" />
						<jsp:param name="submitButtonText" value="Save Commitment" />
					</jsp:include>

					<jsp:include page="snippets/standardFormErrors.jsp"/>

					<div class="columns">
						<c:forEach var="sectionDefinition" items="${sectionDefinitions}">
							<c:if test="${sectionDefinition.layoutType ne 'GRID'}">
								<div class="paymentType">
									<%@ include file="/WEB-INF/jsp/snippets/fieldLayout.jsp"%>
								</div>
							</c:if>
						</c:forEach>
						<div class="clearColumns"></div>
					</div>
					<c:forEach var="sectionDefinition" items="${sectionDefinitions}">
						<c:if test="${sectionDefinition.layoutType eq 'GRID'}">
							<%@ include file="/WEB-INF/jsp/snippets/fieldLayout.jsp"%>
							<p class="gridActions">&nbsp;<span id="subTotal">Total <span class="warningText">(must match commitment value)</span> <span class="value">0</span></span></p>
						</c:if>
					</c:forEach>
					<div class="formButtonFooter personFormButtons"><input type="submit" value="Save Commitment" class="saveButton" /></div>
				</form:form>
				<script type="text/javascript">
					var elem=document.getElementById("paymentType");
					$("." + elem[elem.selectedIndex].getAttribute('reference')).show();
				</script>
		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>