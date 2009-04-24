<%@ include file="/WEB-INF/jsp/include.jsp"%>
<tiles:insertDefinition name="base">
	<tiles:putAttribute name="browserTitle" value="Manage Relationships" />
	<tiles:putAttribute name="primaryNav" value="Relationships" />
	<tiles:putAttribute name="secondaryNav" value="Relationships" />
	<tiles:putAttribute name="mainContent" type="string">
		<div class="content760 mainForm">

		<div class="simplebox">
		    <h4>Maintain relationships for <c:out value='${person.fullName}'/></h4><br/>
		
		<form method="post" action="relationship.htm">
		   <input type=hidden name="personId" value="${person.id}" />
			<h4>Relationship to Manage</h4>
			<select id="fieldRelationshipId" name="fieldRelationshipId" onchange="if (this.form.fieldRelationshipId.value != '') $('#actions').show(); else $('#actions').hide();">
		  	  <option value="" >Select...</option>
			   <c:forEach var="fieldRelationship" items="${fieldRelationships}">
			     <option value="<c:out value='${fieldRelationship.id}'/>" ><c:out value='${fieldRelationship.defaultLabel}'/></option>
			   </c:forEach>
			</select>&nbsp;&nbsp;&nbsp;
		   <div id="actions" style="display:none">
		  	   <input type=submit value="Edit" />
			   <a class="action" href="#" onclick="this.form.action='fieldRelationshipCustomize.htm';this.form.submit();" >Customize</a>
		   </div>
		</form>


		</div>

		</div>
		
	</tiles:putAttribute>
</tiles:insertDefinition>
