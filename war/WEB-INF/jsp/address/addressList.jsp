<%@ include file="/WEB-INF/jsp/include.jsp" %>
<page:applyDecorator name="form">
    <spring:message code='addresses' var="titleText" />
    <html>
        <head>
            <title><c:out value="${titleText} - ${requestScope.constituent.firstLast}"/></title>
        </head>
        <body>
             <div id="addressListGrid"></div>
        </body>
    </html>
    <page:param name="scripts">
        <script type="text/javascript" src="js/lists/addressList.js"></script>
    </page:param>
</page:applyDecorator>