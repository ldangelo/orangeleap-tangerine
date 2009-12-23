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
                
               
                
                <select  id="ruleEventType" name="ruleEventType">
                   <c:forEach var="ruleEventType" items="${ruleEventTypes}">
                     <option <c:if test='${param.ruleEventType == ruleEventType.ruleEventTypeNameId}'>selected</c:if>  value="<c:out value='${ruleEventType.ruleEventTypeNameId}'/>" > <c:out value='${ruleEventType.ruleEventTypeDesc}'/></option>
                   </c:forEach>
                </select>
                
                <b><c:out value="${message}"/></b><br/>
                <br/>
                <br/>
                
                <a href="#" onclick="window.location = 'rules.htm?ruleEventType=' + $('#ruleEventType').val();  ">Edit Rules</a>&nbsp;&nbsp;

                <a href="#"  onclick="window.location = 'ruleEventTypes.htm?action=publish&ruleEventType=' + $('#ruleEventType').val();  " >Publish Rules</a><br/>
                
             </form>
            
            </div>
           
        </body>
    </html>
</page:applyDecorator>
