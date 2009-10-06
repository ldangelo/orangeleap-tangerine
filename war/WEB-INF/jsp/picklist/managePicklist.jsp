<%@ include file="/WEB-INF/jsp/include.jsp"%>
<page:applyDecorator name="admin">
    <html>
        <head>
            <title><spring:message code='managePicklists'/></title>
        </head>
    </html>
    <page:param name="scripts">
        <script type="text/javascript">
            Ext.ns("OrangeLeap");
            OrangeLeap.Picklists = [
                <c:set var="counter" value="0"/>
                <c:set var="length" value="${fn:length(requestScope.picklists)}"/>
                <c:forEach items="${requestScope.picklists}" var="picklist">
                    <c:set var="counter" value="${counter + 1}"/>
                    [ '<c:out value="${picklist.picklistNameId}"/>', '<c:out value="${picklist.picklistDesc}"/>' ]<c:if test="${counter < length}">,</c:if>
                </c:forEach>
            ];
        </script>
        <script type="text/javascript" src="js/admin/managePicklists.js"></script>
    </page:param>
</page:applyDecorator>
