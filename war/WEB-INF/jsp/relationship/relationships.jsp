<%@ include file="/WEB-INF/jsp/include.jsp"%>
<tiles:insertDefinition name="base">
	<tiles:putAttribute name="browserTitle" value="Manage Relationships" />
	<tiles:putAttribute name="primaryNav" value="Relationships" />
	<tiles:putAttribute name="secondaryNav" value="Relationships" />
	<tiles:putAttribute name="mainContent" type="string">
		<div class="content760 mainForm">

		<div class="simplebox">

		<div >
		<form:form method="post" commandName="map" >
		    <h4>Maintain relationships for <c:out value='${param.person.fullName}'/></h4><br/>
			<table class="relationships">
			  <tr >
				<th>Field Name</td>
				<th>Value</td>
				<th>Start Date</td>
				<th>End Date</td>
				<th>Customize</td>
			  </tr>
			 <c:forEach var="relationship" varStatus="status" items="${map}" >
			  <tr rowindex="${status.count}">
				<td><input id="relFieldName-${status.count}" name="relFieldName[${status.count}]" size="32" value="<c:out value='${relationship.fieldName}'/>"  /></td>
				<td><input id="relFieldValue-${status.count}" name="relFieldValue[${status.count}]" size="32" value="<c:out value='${relationship.fieldValue}'/>"  /></td>
				<td><input id="relStartDate-${status.count}" name="relStartDate[${status.count}]" size="32" value="<c:out value='${relationship.startDate}'/>"  /></td>
				<td><input id="relEndDate-${status.count}" name="relEndDate[${status.count}]" size="32" value="<c:out value='${relationship.endDate}'/>"  /></td>
				<td><a href="relationshipCustomize.html?id=${relationship.id}">+</a></td>
			  </tr>
			 </c:forEach>
			</table>
			<br/>
			<input type="button" value="Add" class="saveButton" onclick="PicklistCustomizer.addNewRow();" />
			<input type="submit" value="Save" class="saveButton" />
		</form:form>
		</div>

		</div>

		</div>
		
	</tiles:putAttribute>
</tiles:insertDefinition>
