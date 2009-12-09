<%@ include file="/WEB-INF/jsp/include.jsp" %>
<page:applyDecorator name="form">
    <spring:message code='phones' var="titleText" />
    <html>
        <head>
            <title><c:out value="${titleText} - ${requestScope.constituent.firstLast}"/></title>
        </head>
        <body>
             <div id="phoneGrid"></div>
        </body>
    </html>
    <page:param name="scripts">
        <tangerine:fields pageName="phoneList" entityUrl="phoneManagerEdit.htm"/>
    </page:param>
</page:applyDecorator>