<%@ include file="/WEB-INF/jsp/include.jsp"%>
<tiles:insertDefinition name="base">
	<tiles:putAttribute name="browserTitle" value="Customize Relationship" />
	<tiles:putAttribute name="primaryNav" value="People" />
	<tiles:putAttribute name="secondaryNav" value="Edit" />
	<tiles:putAttribute name="sidebarNav" value="Relationships" />
	<tiles:putAttribute name="mainContent" type="string">
		<div class="content760 mainForm">
		
		<c:set var="constituent" value="${constituent}" scope="request" />
		<c:if test="${constituent.id!=null}">
			<c:set var="viewingConstituent" value="true" scope="request" />
		</c:if>
		<jsp:include page="../snippets/constituentHeader.jsp">
			<jsp:param name="currentFunctionTitleText" value="Manage Relationship" />
		</jsp:include>
	
		<div class="simplebox">

		<div >
		<form:form method="post" commandName="map" >
		    <h4>Edit constituent custom fields for relationship &quot;<c:out value='${fieldDefinition.defaultLabel}'/>&quot;,  &quot;<c:out value='${refvalue}'/>&quot;</h4><br/>
			<table class="customFields">
			<c:forEach var="field" varStatus="status" items="${map}" >
			  <tr rowindex="${status.count}">
				<td><input id="cfname-${status.count}-key" name="cfname[${status.count}]" size="32" value="<c:out value='${field.key}'/>"  /></td>
				<td><input id="cfvalue-${status.count}-value" name="cfvalue[${status.count}]" size="60" value="<c:out value='${field.value}'/>"  /></td>
			  </tr>
			</c:forEach>
			</table>
			<br/>
			<input type="hidden" name="id" value="${constituentCustomFieldRelationship.id}"  />
			<input type="hidden" name="constituentId" value="${constituent.id}"  />
			<input type="hidden" name="fieldDefinitionId" value="${fieldDefinition.id}"  />
			<input type="hidden" name="customFieldId" value="${customField.id}"  />
			<input type="hidden" name="refvalue" value="<c:out value='${refvalue}'/>"  />
			<input type="submit" value="Save" class="saveButton" />
		</form:form>
		</div>
		
		<br/>

		</div>
     
		<strong><a class="action" href="relationship.htm?constituentId=${constituent.id}&fieldDefinitionId=${fieldDefinition.id}">&laquo;Back</a></strong>
	
		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>
