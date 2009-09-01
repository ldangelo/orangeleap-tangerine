<%@ include file="/WEB-INF/jsp/include.jsp" %>
<page:applyDecorator name="form">
    <spring:message code='pledges' var="titleText" />
    <html>
        <head>
            <title><c:out value="${titleText} - ${requestScope.constituent.firstLast}"/></title>
        </head>
        <body>
             <div id="pledgeGrid"></div>
        </body>
    </html>
    <page:param name="scripts">
        <tangerine:fields pageName="pledgeList"/>
    </page:param>
</page:applyDecorator>
