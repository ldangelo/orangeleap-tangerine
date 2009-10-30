<%@ include file="/WEB-INF/jsp/include.jsp"%>
<page:applyDecorator name="admin">
    <html>
        <head>
            <title><spring:message code='manageDashboard'/></title>
        </head>
    </html>
    <page:param name="scripts">
        <script type="text/javascript" src="js/admin/manageDashboard.js"></script>
    </page:param>
</page:applyDecorator>
