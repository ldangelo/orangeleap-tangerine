<%@ include file="/WEB-INF/jsp/include.jsp" %>
<page:applyDecorator name="form">
    <spring:message code='communicationHistoryEntries' var="titleText" />
    <html>
        <head>
            <title><c:out value="${titleText} - ${requestScope.constituent.firstLast}"/></title>
        </head>
        <body>
             <div id="communicationHistoryGrid"></div>
        </body>
    </html>
    <page:param name="scripts">
        <tangerine:fields pageName="communicationHistoryList"/>
    </page:param>
</page:applyDecorator>
