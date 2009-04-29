<%@ include file="/WEB-INF/jsp/include.jsp"%>
<tiles:insertDefinition name="base">
	<tiles:putAttribute name="browserTitle" value="Manage Relationships" />
	<tiles:putAttribute name="primaryNav" value="Relationships" />
	<tiles:putAttribute name="secondaryNav" value="Relationships" />
	<tiles:putAttribute name="mainContent" type="string">
		<div class="content760 mainForm">

		<div class="simplebox">
		    <h4>Maintain relationships for <c:out value='${person.fullName}'/></h4><br/>
		
		<form method="post" action="relationships.htm">
		   <input type=hidden name="personId" value="${person.id}" />
		   <input type=hidden id="customize" name="customize" value="" />
			<h4>Relationship to Manage</h4>
			<select id="fieldDefinitionId" name="fieldDefinitionId" onchange="if (this.form.fieldDefinitionId.value != '') $('#actions').show(); else $('#actions').hide();">
		  	  <option value="" >Select...</option>
			   <c:forEach var="fieldDefinition" items="${fieldDefinitions}">
			     <option value="<c:out value='${fieldDefinition.id}'/>" ><c:out value='${fieldDefinition.defaultLabel}'/></option>
			   </c:forEach>
			</select>&nbsp;&nbsp;&nbsp;
		   <span id="actions" style="display:none">
		  	   <input type=submit value="Edit" />
			   &nbsp;&nbsp;&nbsp;<!-- <a class="action" href="#" onclick="this.form.customize='true';this.form.submit();" >Customize</a> -->
		   </span>
		</form>


		</div>

		</div>
		
	</tiles:putAttribute>
</tiles:insertDefinition>
