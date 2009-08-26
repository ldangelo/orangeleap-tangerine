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
                   <c:forEach var="pageType" items="${pageTypes}">
                     <option value="<c:out value='${pageType.value}'/>" > <c:out value='${pageType.key}'/></option>
                   </c:forEach>
                </select>
                
                <br/>
                <br/>
                
                
                <input type="button" value="Go" onclick="window.location = 'sectionDefinitions.htm?pageType=' + $('#pageType').val();  " />
                
             </form>
            
            </div>
           
        </body>
    </html>
</page:applyDecorator>
