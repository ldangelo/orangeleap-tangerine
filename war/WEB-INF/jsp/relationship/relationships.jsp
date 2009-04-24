<%@ include file="/WEB-INF/jsp/include.jsp"%>
<tiles:insertDefinition name="base">
	<tiles:putAttribute name="browserTitle" value="Manage Relationships" />
	<tiles:putAttribute name="primaryNav" value="Relationships" />
	<tiles:putAttribute name="secondaryNav" value="Relationships" />
	<tiles:putAttribute name="mainContent" type="string">
		<div class="content760 mainForm">

		<div class="simplebox">
		    <h4>Maintain relationships for <c:out value='${param.person.fullName}'/></h4><br/>
		
		<form method="get" action="relationship.htm">
			<h4>Relationship to Manage</h4>
			<select id="fieldRelationshipId" name="fieldRelationshipId" >
		  	  <option value="" ${currentFieldRelationshipId==''?'selected':''}>Select...</option>
			   <c:forEach var="fieldRelationship" items="${fieldRelationships}">
			     <option value="<c:out value='${fieldRelationship.id}'/>" ${currentFieldRelationshipId==fieldRelationship.id?'selected':''}><c:out value='${fieldRelationship.defaultLabel}'/></option>
			     <c:if test="${currentFieldRelationshipId==fieldRelationship.id}">
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
		   <input type=hidden name="personId" value="${person.id}" />
		   <input type=submit value="Edit" />
		</form>


		</div>

		</div>
		
	</tiles:putAttribute>
</tiles:insertDefinition>
