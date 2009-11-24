<%@ include file="/WEB-INF/jsp/include.jsp" %>
<page:applyDecorator name="form">
    <spring:message code='giftSummary' var="titleText" />
    <html>
        <head>
            <title><c:out value="${titleText} - ${requestScope.constituent.firstLast}"/></title>
        </head>
        <body>
             <div id="giftSummaryTab"></div>
             <div id="giftSummaryGrid"></div>
        </body>
        <page:param name="scripts">
             <script type="text/javascript" src="js/lists/giftSummary.js"></script>
        </page:param>
        
    </html>
</page:applyDecorator>
