<%@ include file="/WEB-INF/jsp/include.jsp" %>
<page:applyDecorator name="form">
    <spring:message code='backgroundEntries' var="titleText" />
    <html>
        <head>
            <title><c:out value="${titleText} - ${requestScope.constituent.firstLast}"/></title>
        </head>
        <body>
             <div id="backgroundGrid"></div>
        </body>
    </html>
    <page:param name="scripts">
        <tangerine:fields pageName="backgroundList"/>
    </page:param>
</page:applyDecorator>
