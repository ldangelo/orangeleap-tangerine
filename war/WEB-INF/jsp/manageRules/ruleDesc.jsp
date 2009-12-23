<%@ include file="/WEB-INF/jsp/include.jsp"%>
<page:applyDecorator name="form">
    <spring:message code='manageRules' var="titleText" />
    <html>
        <head>
            <title><c:out value="${titleText}"/></title>
        </head>
        <body>
            <div class="simplebox">
           
	           <form method="post" action="ruleDesc.htm">
	           
	           	<input type="hidden" id="id" name="id" value="${id}" />
	           	<input type="hidden" id="ruleEventType" name="ruleEventType" value="${ruleEventType}" />
           
                <h4>Enter Rule Description</h4><br/>
                
                <table>
                <tr>
                <td>Description</td><td>Active</td>
                </tr>
                <tr>
                <td>
                <input id="desc" name="desc" value="${rule.ruleDesc}"  size="60" />
                </td><td>
                <input id="active" name="active" type="checkbox" value="true" <c:if test="${rule.ruleIsActive == 'true'}" >checked</c:if> /> 
                </td>
                </tr>
                </table>
                <br />
                <br />
                <input type="submit" value="Save" class="saveButton" />

               </form>
            
               <br/>
		       <a href="#" onclick="window.location='rules.htm?ruleEventType=${ruleEventType}&id='+$('#id').val(); ">&laquo;Back</a>
			
            </div>
           
        </body>
    </html>
</page:applyDecorator>
