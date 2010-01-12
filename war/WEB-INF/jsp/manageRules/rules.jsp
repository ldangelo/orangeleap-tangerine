<%@ include file="/WEB-INF/jsp/include.jsp"%>
<page:applyDecorator name="form">
    <spring:message code='manageRules' var="titleText" />
    <html>
        <head>
            <title><c:out value="${titleText}"/></title>
        </head>
        <body>
            <div class="simplebox">
           
            <form>
           
           	<input type="hidden" id="ruleEventType" name="ruleEventType" value="${ruleEventType}" />
	        <h4>Rules Definition to Manage</h4><br/>
           	
            <c:if test="${fn:length(rules) == 0}" >
            
	            There are no rules defined for this event type.<br/><br/>
            
            </c:if>
            
            <table cellspacing=10 cellpadding=10 >
	        <c:forEach var="rule" items="${rules}">
			<tr>
				<td><c:out value='${rule.ruleDesc}'/></td>
	            <td><a href="#" onclick="window.location = 'ruleDesc.htm?ruleEventType=${ruleEventType}&id=${rule.id}' ;  " >Description / Activate</a></td>
	            <td><a href="#" onclick="window.location = 'editRuleSegments.htm?ruleEventType=${ruleEventType}&id=${rule.id}';  " >Conditions / Consequences</a></td>
	            <td><a href="#" onclick="window.location = 'revertRuleVersion.htm?ruleEventType=${ruleEventType}&id=${rule.id}';  " >Revert to Prior Version</a></td>
			</tr>
            </c:forEach>
            </table>
            
            <br/><br/>
            <a href="#" onclick="createRule();  " >Create New Rule</a><br/>
           
            </form>
             
            <br/>
			<a href="#" onclick="window.location = 'ruleEventTypes.htm?ruleEventType=${ruleEventType}';  " >&laquo;Back</a>
            
            </div>
            
             <script>
             	function createRule() {
                 	var ruleEventType = '${ruleEventType}';
                 	var params = 'ruleEventType='+ruleEventType;
                    $.ajax({
                 	   type: "POST",
                 	   url: "createRule.htm",
                 	   data: params,
                 	   success: function(msg){
                 	     window.location = "rules.htm?ruleEventType="+ruleEventType;
                 	   },
             	   	   error: function(){
                   	     window.location = "rules.htm?ruleEventType="+ruleEventType;
                   	   }
                 	 });
             	}
             </script>
            
           
        </body>
    </html>
</page:applyDecorator>
