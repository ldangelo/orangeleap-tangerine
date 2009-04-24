<%@ include file="/WEB-INF/jsp/include.jsp"%>
<tiles:insertDefinition name="base">
	<tiles:putAttribute name="browserTitle" value="Manage Relationships" />
	<tiles:putAttribute name="primaryNav" value="Relationships" />
	<tiles:putAttribute name="secondaryNav" value="Relationships" />
	<tiles:putAttribute name="mainContent" type="string">
		<div class="content760 mainForm">

		<div class="simplebox">
		    <h4>Maintain relationships for <c:out value='${form.person.fullName}'/>: <c:out value='${form.fieldLabel}'/></h4><br/>
		
		<div >
		<form method="post" action="relationship.htm">
			<table class="customFields">
			  <tr >
				<th nowrap>Value</td>
				<th nowrap>Start Date</td>
				<th nowrap>End Date</td>
				<th nowrap>&nbsp;&nbsp;Customize</td>
			  </tr>
			 <c:forEach var="customField" varStatus="status" items="${form.relationshipList}" >
			  <tr rowindex="${status.count}">
				<td><input id="relFieldValue-${status.count}-" name="relFieldValue[${status.count}]" size="32" value="<c:out value='${customField.value}'/>"  /></td>
				<td><input id="relStartDate-${status.count}-" name="relStartDate[${status.count}]" size="16" value="<c:out value='${customField.displayStartDate}'/>"  /></td>
				<td><input id="relEndDate-${status.count}-" name="relEndDate[${status.count}]" size="16" value="<c:out value='${customField.displayEndDate}'/>"  /></td>
				<td>&nbsp;&nbsp;<a href="relationshipCustomize.html?id=${form.fieldRelationship.id}">+</a></td>
			  </tr>
			 </c:forEach>
			</table>
			<br/>
			<input type="button" value="Add" class="saveButton" onclick="GenericCustomizer.addNewRow();" />
			<input type="submit" value="Save" class="saveButton" />
		</form>
		</div>

		</div>

		</div>
		
	</tiles:putAttribute>
</tiles:insertDefinition>
