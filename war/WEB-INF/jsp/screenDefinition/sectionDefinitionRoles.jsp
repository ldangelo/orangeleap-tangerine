<%@ include file="/WEB-INF/jsp/include.jsp"%>
<page:applyDecorator name="form">
    <spring:message code='manageScreenDefinitions' var="titleText" />
    <html>
        <head>
            <title><c:out value="${titleText}"/></title>
        </head>
        <body>
            <div class="simplebox">
           
	           <form method="post" action="sectionDefinitionRoles.htm">
	           
	           	<input type="hidden" id="id" name="id" value="${id}" />
	           	<input type="hidden" id="pageType" name="pageType" value="${pageType}" />
           
                <h4>Enter Role</h4><br/>
                
                <input id="roles" name="roles" value="${roles}"  size="60" />
                
                <select  id=availableRoles name="availableRoles" onchange="$('#roles').val($('#availableRoles').val())" >
                   <option value="" >Or select from these roles...</option>
                   <c:forEach var="availableRole" items="${availableRoleList}">
                     <option value="<c:out value='${availableRole}'/>" > <c:out value='${availableRole}'/></option>
                   </c:forEach>
                </select>
                
                <br />
                <br />
                <input type="submit" value="Save" class="saveButton" />

               </form>
            
               <br/>
		       <a href="sectionDefinitions.htm?pageType=${pageType}">&laquo;Back</a>
			
            </div>
           
        </body>
    </html>
</page:applyDecorator>
