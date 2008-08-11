<%@ include file="/WEB-INF/jsp/include.jsp" %>
<tiles:insertDefinition name="base">
	<tiles:putAttribute name="browserTitle" value="Enter Gift" />
	<tiles:putAttribute name="primaryNav" value="People" />
	<tiles:putAttribute name="secondaryNav" value="Edit" />
	<tiles:putAttribute name="sidebarNav" value="Enter Gift" />
	<tiles:putAttribute name="mainContent" type="string">
		<div class="content760 mainForm">
			<mp:page pageName='gift' />
				<c:set var="person" value="${gift.person}" scope="request" />
				<c:if test="${person.id!=null}">
					<c:set var="viewingPerson" value="true" scope="request" />
				</c:if>
				
				<form:form method="post" commandName="gift">
					<c:if test="${id != null}">
						<input type="hidden" name="id" value="${id}" />
					</c:if>
				
					<jsp:include page="snippets/personHeader.jsp">
						<jsp:param name="currentFunctionTitleText" value="Enter Gift" />
						<jsp:param name="submitButtonText" value="Submit Payment" />
					</jsp:include>
					
					<jsp:include page="snippets/standardFormErrors.jsp"/>
					
					<c:set var="gridCollectionName" value="distributionLines" />
					<c:set var="gridCollection" value="${gift.distributionLines}" />
					
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
							<a class="actionLink" onclick="addDistributionLine();return false;" href="#">New line</a>
						</c:if>
					</c:forEach>
					<div class="formButtonFooter personFormButtons"><input type="submit" value="Submit Payment" class="saveButton" /></div>
				</form:form>
				<script type="text/javascript">
					var elem=document.getElementById("paymentType");
					$("." + elem[elem.selectedIndex].getAttribute('reference')).show();
				</script>
		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>