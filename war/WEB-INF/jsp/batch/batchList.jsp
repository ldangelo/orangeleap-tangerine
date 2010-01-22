<%@ include file="/WEB-INF/jsp/include.jsp" %>
<page:applyDecorator name="admin">
    <html>
        <head>
            <title><spring:message code='batches'/></title>
        </head>
    </html>
    <page:param name="scripts">
        <script type="text/javascript">
            Ext.ns('OrangeLeap');
            OrangeLeap.allowCreate = <c:out value="${requestScope.allowCreate}"/>;
            OrangeLeap.allowExecute = <c:out value="${requestScope.allowExecute}"/>;
            OrangeLeap.allowDelete = <c:out value="${requestScope.allowDelete}"/>;
        </script>
        <script type="text/javascript" src="js/extjs/ux/grid/DynamicPropertyGrid.js"></script>
        <script type="text/javascript" src="js/extjs/ux/group/GroupTab.js"></script>
        <script type="text/javascript" src="js/extjs/ux/group/GroupTabPanel.js"></script>
        <script type="text/javascript" src="js/extjs/ux/form/QueryLookup.js"></script>
        <script type="text/javascript" src="js/lists/batchList.js"></script>
    </page:param>
</page:applyDecorator>
