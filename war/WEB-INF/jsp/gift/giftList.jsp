<%@ include file="/WEB-INF/jsp/include.jsp" %>
<page:applyDecorator name="form">
    <spring:message code='gifts' var="titleText" />
    <html>
        <head>
            <title><c:out value="${titleText} - ${requestScope.constituent.firstLast}"/></title>
        </head>
        <body>
             <div id="giftGrid"></div>
        </body>
    </html>
    <page:param name="scripts">
        <tangerine:fields pageName="giftList"/>
    </page:param>
</page:applyDecorator>
