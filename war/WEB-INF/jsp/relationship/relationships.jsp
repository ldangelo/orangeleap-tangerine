<%@ include file="/WEB-INF/jsp/include.jsp"%>
<tiles:insertDefinition name="base">
	<tiles:putAttribute name="browserTitle" value="Manage Relationships" />
	<tiles:putAttribute name="primaryNav" value="Relationships" />
	<tiles:putAttribute name="secondaryNav" value="Relationships" />
	<tiles:putAttribute name="mainContent" type="string">
		<div class="content760 mainForm">

		<div class="simplebox">
		    <h4>Maintain relationships for <c:out value='${param.person.fullName}'/></h4><br/>
		
		<form method="get" action="customFieldList.htm">
			<h4>Relationship to Manage</h4>
			<select id="fieldRelationshipId" name="fieldRelationshipId" onchange="this.form.submit()">
		  	  <option value="" ${currentFieldRelationshipId==''?'selected':''}>Select...</option>
			   <c:forEach var="fieldRelationship" items="${fieldRelationships}">
			     <option value="<c:out value='${fieldRelationship.fieldRelationshipId}'/>" ${currentFieldRelationshipId==fieldRelationship.fieldRelationshipId?'selected':''}><c:out value='${fieldRelationship.defaultLabel}'/></option>
			     <c:if test="${currentFieldRelationshipId==fieldRelationship.fieldRelationshipId}">
						<c:set var="currentFieldRelationship" value="${fieldRelationship.defaultLabel}" />
			     </c:if>
			   </c:forEach>
			</select>
 		   <c:url var="fieldRelationshipCustomizeUrl" value="fieldRelationshipCustomize.htm">
 	         <c:param name="fieldRelationshipId" value="${currentFieldRelationshipId}" />
		     <c:param name="view" value="customize" />
		   </c:url>
		   <c:if test="${currentFieldRelationshipId != ''}">
		   &nbsp;&nbsp;&nbsp;<a class="action" href="${fieldRelationshipCustomizeUrl}">Customize</a>
		   </c:if>
		</form>


		<div >
		<form:form method="post" commandName="map" >
			<table class="relationships">
			  <tr >
				<th nowrap>Field Name</td>
				<th nowrap>Value</td>
				<th nowrap>Start Date</td>
				<th nowrap>End Date</td>
				<th nowrap>Custom Fields</td>
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
