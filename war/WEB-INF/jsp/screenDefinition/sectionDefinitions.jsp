<%@ include file="/WEB-INF/jsp/include.jsp"%>
<page:applyDecorator name="form">
    <spring:message code='manageScreenDefinitions' var="titleText" />
    <html>
        <head>
            <title><c:out value="${titleText}"/></title>
        </head>
        <body>
            <div class="simplebox">
           
           <form>
           
           	<input type="hidden" id="pageType" name="pageType" value="${pageType}" />
           	
            <c:if test="${fn:length(sectionNames) == 0}" >
            
            There are no editable sections for this page type.<br/>
            
            </c:if>
            
            <c:if test="${fn:length(sectionNames) > 0}" >
           
                <h4>Section Definition to Manage</h4><br/>
                <select  id="id" name="id">
                   <c:forEach var="sectionName" items="${sectionNames}">
                     <option value="<c:out value='${sectionName.value}'/>" > <c:out value='${sectionName.key}'/></option>
                   </c:forEach>
                </select>
                
                <br/>
                <br/>
                
                <input type="button" value="Edit Fields" onclick="window.location = 'sectionDefinition.htm?id='+$('#id').val() ;  " />
                <input type="button" value="Edit Roles"  onclick="window.location = 'sectionDefinitionRoles.htm?id='+$('#id').val() ;  " />
                <%-- <input type="button" value="Create Copy" onclick="copySection();  " />  --%>

            </c:if>
                
             </form>

            <br/>
			<a href="pageTypes.htm">&laquo;Back</a>
            
            </div>
            
             <script>
             	function copySection() {
                 	var pageType = '${pageType}';
                 	var data = 'id='+$('#id').val();
                    $.ajax({
                 	   type: "POST",
                 	   url: "sectionDefinitionCopy.htm",
                 	   data: data,
                 	   success: function(msg){
                 	     window.location = "sectionDefinitions.htm?pageType="+pageType;
                 	   },
             	   	   error: function(){
                   	     alert("Invalid input.");
                   	   }
                 	 });
             	}
             </script>
            
           
        </body>
    </html>
</page:applyDecorator>
