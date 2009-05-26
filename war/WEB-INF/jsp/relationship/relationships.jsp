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
		    <jsp:include page="../snippets/personHeader.jsp">
			   <jsp:param name="currentFunctionTitleText" value="Manage Relationships" />
		    </jsp:include>
		</c:if>

		<div class="simplebox">
		
		<form id="form" method="post" action="relationships.htm">
		   <input type=hidden name="personId" value="${person.id}" />
		   <input type=hidden id="customize" name="customize" value="" />

		 <c:choose>
      	 <c:when test="${person == null}">
			<h4>Select Master Constituent Record Field</h4>
			<br/>
         </c:when>
         <c:otherwise>
			<h4>Relationship to Manage</h4>
         </c:otherwise>
         </c:choose>


			<select id="fieldDefinitionId" name="fieldDefinitionId" onchange="changeSelection(this.form.fieldDefinitionId.value);">
		  	  <option value="" >Select...</option>
			   <c:forEach var="fieldDefinition" items="${fieldDefinitions}">
			     <option value="<c:out value='${fieldDefinition.id}'/>" ><c:out value='${fieldDefinition.defaultLabel}'/></option>
			   </c:forEach>
			</select>&nbsp;&nbsp;&nbsp;
		   <span id="actions" style="display:none">
		  	   <input id="editbutton" type=submit value="Edit" />
		   </span>
		</form>
		
		 <c:choose>
      	 <c:when test="${person == null}">
			<script>
			function changeSelection(id) {
				if (id != '') {
					this.window.location="fieldRelationshipCustomize.htm?fieldDefinitionId="+id;
				}
			}
			</script>
         </c:when>
         <c:otherwise>
			<script>
			function changeSelection(id) {
				if (id != '') { $('#actions').show(); } else $('#actions').hide();
			}
			</script>
     		<strong><a class="action" href="person.htm?personId=${person.id}">&laquo;Back</a></strong>
         </c:otherwise>
         </c:choose>

		</div>
		
		</div>
		
	</tiles:putAttribute>
</tiles:insertDefinition>
