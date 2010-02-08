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
           
                <h4>Select Event Type Rules to Manage</h4><br/>
                
                <table cellspacing=10 cellpadding=10 >
                <c:forEach var="ruleEventType" items="${ruleEventTypes}">
                <tr>
                <td><c:out value='${ruleEventType.ruleEventTypeDesc}'/></td>
                <td><a href="#" onclick="window.location = 'rules.htm?ruleEventType=${ruleEventType.ruleEventTypeNameId}';  ">Edit Rules</a></td>
                <td><a href="#"  onclick="window.location = 'ruleEventTypes.htm?action=publish&ruleEventType=${ruleEventType.ruleEventTypeNameId}';" >Publish Rules</a></td>
                <td>Published Date: <c:out value="${ruleEventType.lastPublishedDate}"/></td>
                <td>By: <c:out value="${ruleEventType.lastPublishedBy}"/></td>
                </tr>
          	    </c:forEach>
          	    </table>
                
                <b><c:out value="${message}"/></b><br/>

             </form>
            
            </div>
           
        </body>
    </html>
</page:applyDecorator>
