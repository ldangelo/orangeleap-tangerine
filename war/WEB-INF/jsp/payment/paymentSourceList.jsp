<%@ include file="/WEB-INF/jsp/include.jsp" %>
<page:applyDecorator name="form">
    <spring:message code='paymentMethods' var="titleText" />
    <html>
        <head>
            <title><c:out value="${titleText} - ${requestScope.constituent.firstLast}"/></title>
        </head>
        <body>
             <div id="paymentSourceGrid"></div>
        </body>
    </html>
    <page:param name="scripts">
        <tangerine:fields pageName="paymentSourceList" entityUrl="paymentManagerEdit.htm"/>
    </page:param>
</page:applyDecorator>