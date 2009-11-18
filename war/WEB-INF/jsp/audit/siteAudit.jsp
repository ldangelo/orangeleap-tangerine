<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<page:applyDecorator name="form">
    <spring:message code='siteAudit' var="titleText" />
    <html>
        <head>
            <title><c:out value="${titleText}"/></title>
        </head>
        <body>
            <h1 class="x-hidden" id="auditSiteName"><security:authentication property="details.site"/></h1>
             <div id="auditHistoryGrid"></div>
        </body>
    </html>
    <page:param name="scripts">
        <script type="text/javascript" src="js/lists/auditList.js"></script>
    </page:param>
</page:applyDecorator>
