<%@ include file="/WEB-INF/jsp/include.jsp" %>
<page:applyDecorator name="form">
    <spring:message code='addresses' var="titleText" />
    <html>
        <head>
            <title><c:out value="${titleText} - ${requestScope.constituent.firstLast}"/></title>
        </head>
        <body>
             <div id="addressGrid"></div>
        </body>
    </html>
    <page:param name="scripts">
        <tangerine:fields pageName="addressList" entityUrl="addressManagerEdit.htm" />
    </page:param>
</page:applyDecorator>