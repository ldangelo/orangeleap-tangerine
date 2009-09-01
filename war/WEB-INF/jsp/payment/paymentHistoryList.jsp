<%@ include file="/WEB-INF/jsp/include.jsp" %>
<page:applyDecorator name="form">
    <spring:message code='paymentHistory' var="titleText" />
    <html>
        <head>
            <title><c:out value="${titleText} - ${requestScope.constituent.firstLast}"/></title>
        </head>
        <body>
             <div id="paymentHistoryGrid"></div>
        </body>
    </html>
    <page:param name="scripts">
        <tangerine:fields pageName="paymentHistoryList"/>
    </page:param>
</page:applyDecorator>
