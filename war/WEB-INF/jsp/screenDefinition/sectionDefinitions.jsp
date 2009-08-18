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
           	<input type="hidden" id="role" name="role" value="${role}" />
           
                <h4>Section Definition to Manage</h4>
                <select  id="sectionName" name="sectionName">
                   <c:forEach var="sectionName" items="${sectionNames}">
                     <option value="<c:out value='${sectionName.value}'/>" > <c:out value='${sectionName.key}'/></option>
                   </c:forEach>
                </select>
                
                <input type="button" onclick="window.location = 'sectionDefinition.htm?pageType=' + $('#pageType').val() + '&role='+$('#role').val() + '&sectionName='+$('#sectionName') ;  " />
                
             </form>
            
            </div>
           
        </body>
    </html>
</page:applyDecorator>
