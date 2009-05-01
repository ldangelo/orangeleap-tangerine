<%@ include file="/WEB-INF/jsp/include.jsp"%>
<tiles:insertDefinition name="base">
	<tiles:putAttribute name="browserTitle" value="Manage Relationships" />
	<tiles:putAttribute name="primaryNav" value="People" />
	<tiles:putAttribute name="secondaryNav" value="Edit" />
	<tiles:putAttribute name="sidebarNav" value="Relationships" />
	<tiles:putAttribute name="mainContent" type="string">
		<div class="content760 mainForm">
		<c:set var="person" value="${person}" scope="request" />
		<c:if test="${person.id!=null}">
			<c:set var="viewingPerson" value="true" scope="request" />
		</c:if>
		<jsp:include page="../snippets/personHeader.jsp">
			<jsp:param name="currentFunctionTitleText" value="Manage Relationships" />
		</jsp:include>

		<div class="simplebox">
		
		<form method="post" action="relationships.htm">
		   <input type=hidden name="personId" value="${person.id}" />
		   <input type=hidden id="customize" name="customize" value="" />
			<h4>Relationship to Manage</h4>
			<select id="fieldDefinitionId" name="fieldDefinitionId" onchange="changeSelection(this.form.fieldDefinitionId.value);">
		  	  <option value="" >Select...</option>
			   <c:forEach var="fieldDefinition" items="${fieldDefinitions}">
			     <option value="<c:out value='${fieldDefinition.id}'/>" ><c:out value='${fieldDefinition.defaultLabel}'/></option>
			   </c:forEach>
			</select>&nbsp;&nbsp;&nbsp;
		   <span id="actions" style="display:none">
		  	   <input type=submit value="Edit" />
			   &nbsp;&nbsp;&nbsp;
			   <a id="customizeLink" href="#"  >+</a> 
		   </span>
		</form>
		
		<script>
		function changeSelection(id) {
			if (id != '') { $('#actions').show(); setLink(id); } else $('#actions').hide();
		}
		function setLink(id) {
			$("#customizeLink").attr("href", "fieldRelationshipCustomize.htm?personId=${person.id}&fieldDefinitionId="+id);
		}
		</script>

		</div>

		</div>
		
	</tiles:putAttribute>
</tiles:insertDefinition>
