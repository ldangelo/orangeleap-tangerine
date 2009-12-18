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
                     <option value="<c:out value='${ruleEventType.ruleEventTypeNameId}'/>" > <c:out value='${ruleEventType.ruleEventTypeDesc}'/></option>
                   </c:forEach>
                </select>
                
                <br/>
                <br/>
                
                
                <input type="button" class="button" value="Go" onclick="window.location = 'rules.htm?ruleEventType=' + $('#ruleEventType').val();  " />
                
             </form>
            
            </div>
           
        </body>
    </html>
</page:applyDecorator>
