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
           	
            <c:if test="${fn:length(sectionNames) == 0}" >
            
            There are no editable sections for this specific role and page type.<br/>
            
            </c:if>
            
            <c:if test="${fn:length(sectionNames) > 0}" >
           
                <h4>Section Definition to Manage</h4>
                <select  id="sectionName" name="sectionName">
                   <c:forEach var="sectionName" items="${sectionNames}">
                     <option value="<c:out value='${sectionName.value}'/>" > <c:out value='${sectionName.key}'/></option>
                   </c:forEach>
                </select>
                
                <input type="button" value="Go" onclick="window.location = 'sectionDefinition.htm?pageType=' + $('#pageType').val() + '&role='+$('#role').val() + '&sectionName='+$('#sectionName').val() ;  " />

            </c:if>
                
             </form>

            <br/>
			<a href="pageTypes.htm">&laquo;Back</a>
            
            </div>
           
        </body>
    </html>
</page:applyDecorator>
