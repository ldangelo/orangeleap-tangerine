<%@ include file="/WEB-INF/jsp/include.jsp" %>
<page:applyDecorator name="form">
    <spring:message code="exceptionHeading" var="titleText"/>
    <html>
        <head>
            <title><c:out value="${titleText}"/></title>
        </head>
        <body>
            <h1><c:out value="${titleText}"/></h1>
            <div class="errorDiv"><spring:message code="exceptionOccurred"/></div>
            <div class="noDisplay"><c:out value="${exceptionStackTrace}"/></div>
        </body>
    </html>
</page:applyDecorator>
