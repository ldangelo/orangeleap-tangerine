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
           
                <h4>Page Definition to Manage</h4>
                <select  id="pageType" name="pageType">
                  <option value="" >Select...</option>
                   <c:forEach var="pageType" items="${pageTypes}">
                     <option value="<c:out value='${pageType.value}'/>" > <c:out value='${pageType.key}'/></option>
                   </c:forEach>
                </select>
                
                <h4>For Role</h4>
                <select  id="role" name="role">
                   <c:forEach var="role" items="${roles}">
                     <option value="<c:out value='${role.value}'/>" > <c:out value='${role.key}'/></option>
                   </c:forEach>
                </select>
                
                <input type="button" onclick="window.location = 'sectionDefinitions.htm?pageType=' + $('#pageType').val() + '&role='+$('#role').val();  " />
                
             </form>
            
            </div>
           
        </body>
    </html>
</page:applyDecorator>
