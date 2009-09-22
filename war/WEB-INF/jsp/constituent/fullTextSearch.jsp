<%@ include file="/WEB-INF/jsp/include.jsp" %>
<page:applyDecorator name="form">
    <spring:message code='searchResults' var="titleText" />
    <html>
        <head>
            <title><c:out value="${titleText}"/></title>
        </head>
        <body>
            <div id="constituentGrid"></div>
        </body>
    </html>
    <page:param name="scripts">
        <tangerine:fields pageName="constituentSearchResults"/>
    </page:param>
</page:applyDecorator>
