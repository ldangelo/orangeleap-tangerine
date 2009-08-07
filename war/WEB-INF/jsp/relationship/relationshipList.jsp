<%@ include file="/WEB-INF/jsp/include.jsp"%>
<page:applyDecorator name="form">
    <spring:message code='manageRelationships' var="titleText" />
    <html>
        <head>
            <title><c:out value="${titleText}"/></title>
        </head>
        <body>
            <div class="simplebox">

                 <form id="form" action="#">

                   <c:choose>
                   <c:when test="${constituent == null}">
                      <h4>Manage Relationship Fields</h4>
                      <br/>
                   </c:when>
                   <c:otherwise>
                      <h4>Relationship to Manage</h4>
                   </c:otherwise>
                   </c:choose>

                   <select id="fieldDefinitionId" name="fieldDefinitionId" onchange="changeSelection(this.form.fieldDefinitionId.value);">
                      <option value="" >Select...</option>
                       <c:forEach var="fieldDefinition" items="${fieldDefinitions}">
                         <option value="<c:out value='${fieldDefinition.id}'/>" ><c:out value='${fieldDefinition.defaultLabel}'/></option>
                       </c:forEach>
                   </select>&nbsp;&nbsp;&nbsp;

                 </form>

                 <c:choose>
                 <c:when test="${constituent == null}">
                    <script>
                    function changeSelection(id) {
                        if (id != '') {
                            this.window.location="fieldRelationshipCustomize.htm?fieldDefinitionId="+id;
                        }
                    }
                    </script>
                 </c:when>
                 <c:otherwise>
                    <script>
                    function changeSelection(id) {
                        if (id != '') {
                            this.window.location="relationship.htm?fieldDefinitionId="+id+"&constituentId="+${constituent.id};
                        }
                    }
                    </script>
                    <strong><a class="action" href="constituent.htm?constituentId=${constituent.id}">&laquo;Back</a></strong>
                 </c:otherwise>
                 </c:choose>
            </div>
        </body>
    </html>
</page:applyDecorator>