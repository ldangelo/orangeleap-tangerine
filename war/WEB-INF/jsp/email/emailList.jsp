<%@ include file="/WEB-INF/jsp/include.jsp" %>
<page:applyDecorator name="form">
    <spring:message code='emails' var="titleText" />
    <html>
        <head>
            <title><c:out value="${titleText} - ${requestScope.constituent.firstLast}"/></title>
        </head>
        <body>
             <div id="emailGrid"></div>
        </body>
    </html>
    <page:param name="scripts">
        <tangerine:fields pageName="emailList"/>
    </page:param>
</page:applyDecorator>