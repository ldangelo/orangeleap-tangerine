<%@ include file="/WEB-INF/jsp/include.jsp"%>
<page:applyDecorator name="form">
    <spring:message code='manageRules' var="titleText" />
    <html>
        <head>
            <title><c:out value="${titleText}"/></title>
        </head>
        <body>
            <div class="simplebox">
           
	           <form method="post" action="revertRuleVersion.htm">
	           
	           	<input type="hidden" id="id" name="id" value="${id}" />
           
                <h4>Revert to Prior Version</h4><br/>
                
                 <c:if test="${fn:length(ruleVersions) == 0}" >
            
	             There are no previous versions for this rule.<br/><br/>
            
                 </c:if> 
                 <c:if test="${fn:length(ruleVersions) != 0}" >
            
	                <table cellspacing=5 >
	                	<tr>
	                	<th>Select</th>
	                	<th>Version</th>
	                	<th>Date</th>
	                	</tr>
	                    <c:forEach var="ruleVersion" items="${ruleVersions}">
	                	<tr>
			                <td>
			                    <a href="#" onclick="update(<c:out value='${ruleVersion.id}'/>,'revertRuleVersion',this); " >Revert</a>
			                </td>
			                <td>
			                	<c:out value='${ruleVersion.id}'/>
			                </td>
			                <td>
			                	<c:out value='${ruleVersion.updateDate}'/>
			                </td>
	                	</tr>
	                    </c:forEach>
	                </table>
            
                 </c:if> 
				
             </form>
             
             <br/>
		     <a href="#" onclick="window.location='rules.htm?ruleEventType=${ruleEventType}&id='+$('#id').val(); ">&laquo;Back</a>
             
             <script>
             	function update(ruleVersionId, action, input) {
                 	
                 	var parms = 'ruleEventType=${ruleEventType}&id='+$('#id').val();
             		var data = parms + '&ruleVersionId=' + ruleVersionId + '&action=' + action;
             		if (input) data = data + '&' + $(input).serialize();
                    $.ajax({
                 	   type: "POST",
                 	   url: "revertRuleVersion.htm",
                 	   data: data,
                 	   success: function(msg){
                 	     if (action == 'revertRuleVersion') {
                     	     alert('Rule reverted to version '+ruleVersionId+' (unpublished).');
                     	     window.location = "revertRuleVersion.htm?"+parms;
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
