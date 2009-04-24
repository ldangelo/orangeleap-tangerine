<%@ include file="/WEB-INF/jsp/include.jsp"%>
<tiles:insertDefinition name="base">
	<tiles:putAttribute name="browserTitle" value="Customize Relationship" />
	<tiles:putAttribute name="primaryNav" value="Relationships" />
	<tiles:putAttribute name="secondaryNav" value="Relationships" />
	<tiles:putAttribute name="mainContent" type="string">
		<div class="content760 mainForm">
	
		<div class="simplebox">

		<div >
		<form:form method="post" commandName="map" >
		    <h4>Edit custom fields for relationship &quot;<c:out value='${param.relationship.fieldName}'/>&quot;, value &quot;<c:out value='${relationship.fieldValue}'/>&quot;</h4><br/>
			<table class="customFields">
			<c:forEach var="field" varStatus="status" items="${map}" >
			  <tr rowindex="${status.count}">
				<td><input id="cfname-${status.count}-key" name="cfname[${status.count}]" size="32" value="<c:out value='${field.key}'/>"  /></td>
				<td><input id="cfvalue-${status.count}-value" name="cfvalue[${status.count}]" size="60" value="<c:out value='${field.value}'/>"  /></td>
			  </tr>
			</c:forEach>
			</table>
			<br/>
			<input type="button" value="Add" class="saveButton" onclick="GenericCustomizer.addNewRow();" />
			<input type="submit" value="Save" class="saveButton" />
		</form:form>
		</div>
		
		<br/>
 		<c:url var="relationshipListUrl" value="relationships.htm">
 			<c:param name="personId" value="${relationship.personId}" />
		</c:url>
		<strong><a class="action" href="${relationshipListUrl}">&laquo;Back</a></strong>

		</div>
		
		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>
