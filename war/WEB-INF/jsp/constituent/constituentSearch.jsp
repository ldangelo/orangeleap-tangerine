<%@ include file="/WEB-INF/jsp/include.jsp" %>
<page:applyDecorator name="form">
    <spring:message code='searchConstituents' var="titleText" />
    <html>
        <head>
            <title><c:out value="${titleText}"/></title>
        </head>
        <body>
            <form method="POST" action="constituentSearch.json" id="constituentSearchForm" name="constituentSearchForm" class="searchForm">
                <div class="searchSection">
                    <tangerine:fields pageName="constituentSearch"/>
                    <div class="constituentFormButtons searchConstituentButtons">
                        <input class="searchButton" type="submit" value="<spring:message code='search'/>" id="constituentSearchButton" name="constituentSearchButton"/>
                    </div>
                </div>
            </form>
            <div id="constituentGrid"></div>
        </body>
    </html>
    <page:param name="scripts">
        <tangerine:fields pageName="constituentSearchResults"/>
    </page:param>
</page:applyDecorator>
