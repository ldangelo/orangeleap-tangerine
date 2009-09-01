<%@ include file="/WEB-INF/jsp/include.jsp" %>
<page:applyDecorator name="form">
    <spring:message code='recurringGifts' var="titleText" />
    <html>
        <head>
            <title><c:out value="${titleText} - ${requestScope.constituent.firstLast}"/></title>
        </head>
        <body>
             <div id="recurringGiftGrid"></div>
        </body>
    </html>
    <page:param name="scripts">
        <tangerine:fields pageName="recurringGiftList"/>
    </page:param>
</page:applyDecorator>
