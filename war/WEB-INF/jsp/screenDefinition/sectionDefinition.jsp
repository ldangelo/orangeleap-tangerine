<%@ include file="/WEB-INF/jsp/include.jsp"%>
<page:applyDecorator name="form">
    <spring:message code='manageScreenDefinitions' var="titleText" />
    <html>
        <head>
            <title><c:out value="${titleText}"/></title>
        </head>
        <body>
            <div class="simplebox">
           
	           <form method="post" action="sectionDefinition.htm">
	           
	           	<input type="hidden" id="id" name="id" value="${id}" />
           
                <h4>Fields</h4><br/>
                <table>
                	<tr>
                	<th>Field Label</th>
                	<th>Field Name</th>
                	<th>Visible?</th>
                	<th>Move</th>
                	</tr>
                    <c:forEach var="field" items="${fieldList}">
		                <tr>
		                <td>
		                    <input id="description" name="description" value="<c:out value='${field.description}'/>"  onchange="update('${field.name}','changedescription', this);" />
		                </td>
		                <td>
		                    <c:out value='${field.longDescription}'/>
		                </td>
		                <td>
		                	<input type="checkbox" <c:if test="${field.visible}">checked</c:if> onchange="update('${field.name}','togglevisible');" />
		                </td>
		                <td>
		                	<a href="#" onclick="update('${field.name}','moveup');" >^</a>
		                </td>
		                </tr>
                    </c:forEach>
                </table>

             </form>
             
             <br/>
		     <a href="sectionDefinitions.htm?pageType=${pageType}">&laquo;Back</a>
             
             <script>
             	function update(field, action, input) {
                 	
                 	var parms = 'pageType=${pageType}&id='+$('#id').val();
             		var data = parms + '&fieldName=' + field + '&action=' + action;
             		if (action === 'changedescription') data = data + '&' + $(input).serialize();
                    $.ajax({
                 	   type: "POST",
                 	   url: "sectionDefinition.htm",
                 	   data: data,
                 	   success: function(msg){
                 	     if (action != 'changedescription') {
                     	     window.location = "sectionDefinition.htm?"+parms;
                 	     }
                 	   },
             	   	   error: function(){
                   	   }
                 	 });
                	 
             	}
             </script>
             
            
            </div>
           
        </body>
    </html>
</page:applyDecorator>
