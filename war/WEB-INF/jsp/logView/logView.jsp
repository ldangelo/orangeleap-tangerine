<%@ include file="/WEB-INF/jsp/include.jsp" %>
<page:applyDecorator name="form">
    <spring:message code='logView' var="titleText" />
    <html>
        <head>
            <title><c:out value="${titleText}"/></title>
        </head>
        <body>
            <h1 class="x-hidden" id="auditSiteName"><security:authentication property="details.site"/></h1>
            <div id="logViewGrid"></div>
        </body>
    </html>
    <page:param name="scripts">
        <script type="text/javascript" src="js/logview.js"></script>
    </page:param>
</page:applyDecorator>
