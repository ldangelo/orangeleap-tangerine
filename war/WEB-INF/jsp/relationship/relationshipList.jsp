<%@ include file="/WEB-INF/jsp/include.jsp"%>
<tiles:insertDefinition name="base">
	<tiles:putAttribute name="browserTitle" value="Manage Relationships" />
	<tiles:putAttribute name="primaryNav" value="People" />
	<tiles:putAttribute name="secondaryNav" value="Edit" />
	<tiles:putAttribute name="sidebarNav" value="Relationships" />
	<tiles:putAttribute name="mainContent" type="string">
		<div class="content760 mainForm">
		<c:set var="constituent" value="${constituent}" scope="request" />
	
		<c:if test="${constituent.id!=null}">
			<c:set var="viewingConstituent" value="true" scope="request" />
		    <jsp:include page="../snippets/constituentHeader.jsp">
			   <jsp:param name="currentFunctionTitleText" value="Manage Relationships" />
		    </jsp:include>
		</c:if>

		<div class="simplebox">
		
		 <form id="form" action="#">

  		   <c:choose>
      	   <c:when test="${constituent == null}">
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
			
		 </form>
		
		 <c:choose>
      	 <c:when test="${constituent == null}">
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
				if (id != '') {
					this.window.location="relationship.htm?fieldDefinitionId="+id+"&constituentId="+${constituent.id};
				}
			}
			</script>
     		<strong><a class="action" href="constituent.htm?constituentId=${constituent.id}">&laquo;Back</a></strong>
         </c:otherwise>
         </c:choose>

		</div>
		
		</div>
		
	</tiles:putAttribute>
</tiles:insertDefinition>
