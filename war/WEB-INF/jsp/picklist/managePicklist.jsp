<%@ include file="/WEB-INF/jsp/include.jsp"%>
<page:applyDecorator name="form">
    <spring:message code='manageCodes' var="titleText" />
    <html>
        <head>
            <title><c:out value="${titleText}"/></title>
        </head>
        <body>
            <div id="managerGrid"></div>
        </body>
    </html>
    <page:param name="scripts">
        <script type="text/javascript" src="js/extjs/ux/CheckColumn.js"></script>
        <script type="text/javascript" src="js/extjs/ux/SelectBox.js"></script>
        <script type="text/javascript" src="js/extjs/ux/filters/GridFilters.js"></script>
        <script type="text/javascript" src="js/extjs/ux/filters/Filter.js"></script>
        <script type="text/javascript" src="js/extjs/ux/filters/StringFilter.js"></script>
        <script type="text/javascript" src="js/extjs/ux/filters/BooleanFilter.js"></script>
        <script type="text/javascript" src="js/extjs/ux/GridDragDropRowOrder.js"></script>
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
