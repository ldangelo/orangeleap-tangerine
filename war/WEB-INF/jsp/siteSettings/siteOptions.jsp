<%@ include file="/WEB-INF/jsp/include.jsp"%>
<spring:message code='siteSettings' var="titleText" />
<page:applyDecorator name="form">
    <html>
        <head>
            <title><c:out value="${titleText}"/></title>
        </head>
        <body>
            <div class="simplebox">


               <h4><span style="color: green"><c:out value="${message}" /></span></h4>
               <h4><span style="color: red"><c:out value="${errormessage}" /></span></h4>

               <br/>

                  <div>
                    <form:form method="post" commandName="map" >
                        <h4>Edit Site Options</h4><br/>
                        <table class="customFields">
                        <c:forEach var="field" varStatus="status" items="${map}" >
                          <tr rowindex="${status.count}">
                            <td><input id="cfname-${status.count}-key" name="cfname[${status.count}]" size="32" value="<c:out value='${field.key}'/>" /></td>
                            <td><input id="cfvalue-${status.count}-value" name="cfvalue[${status.count}]" size="60" value="<c:out value='${field.value}'/>"  /></td>
                          </tr>
                        </c:forEach>
                        </table>
                        <br/>
                        <input type="button" value="Add" class="button" onclick="GenericCustomizer.addNewRow();" />
                        <input type="submit" value="Save" class="saveButton" />
                    </form:form>
                </div>

                <br/>
                <strong><a class="action" href="siteSettings.htm">&laquo;Back</a></strong>

               <br/>


            </div>
        </body>
    </html>
</page:applyDecorator>
