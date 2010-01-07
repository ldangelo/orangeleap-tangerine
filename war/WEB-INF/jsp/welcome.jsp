<%@ include file="/WEB-INF/jsp/include.jsp"%>
<page:applyDecorator name="basic">
    <spring:message code='welcome' var="titleText" />
    <html>
        <head>
            <title><c:out value="${titleText}"/></title>
        </head>
        <body>
            <script>
              var contextPrefix = '<%= System.getProperty("contextPrefix") %>';
            </script>
            <c:set var="loadGoogle" value="true" scope="request" />
            <div class="content760 mainForm welcomePage">
                <h1><spring:message code="myDashboard"/></h1>
                <div id="dashboard"></div>
                    
            </div>
        </body>
    </html>
</page:applyDecorator>