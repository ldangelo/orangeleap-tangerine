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
                
                <input id="desc" name="desc" value="${desc}"  size="60" />
                <input id="active" name="active" type="checkbox" value="true" <c:if test="${active == 'true'}" >checked</c:if> /> 
                
                <br />
                <br />
                <input type="submit" value="Save" class="saveButton" />

               </form>
            
               <br/>
		       <a href="rules.htm?ruleEventType=${ruleEventType}">&laquo;Back</a>
			
            </div>
           
        </body>
    </html>
</page:applyDecorator>
