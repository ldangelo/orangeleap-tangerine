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
                	<th>Field Name</th>
                	<th>Visible?</th>
                	<th>Move</th>
                	</tr>
                    <c:forEach var="field" items="${fieldList}">
		                <tr>
		                <td>
		                    <c:out value='${field.description}'/>
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
             
             <script>
             	function update(field, action) {
                 	var parms = 'id='+$('#id').val();
             		var data = parms + '&fieldName=' + field + '&action=' + action;
                    $.ajax({
                 	   type: "POST",
                 	   url: "sectionDefinition.htm",
                 	   data: data,
                 	   success: function(msg){
                 	     window.location = "sectionDefinition.htm?"+parms;
                 	   },
             	   	   error: function(){
                   	     alert("Invalid input.");
                   	   }
                 	 });
             	}
             </script>
             
            
            </div>
           
        </body>
    </html>
</page:applyDecorator>
