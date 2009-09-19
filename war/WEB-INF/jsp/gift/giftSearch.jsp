<%@ include file="/WEB-INF/jsp/include.jsp" %>
<page:applyDecorator name="form">
    <spring:message code='searchGifts' var="titleText" />
    <html>
        <head>
            <title><c:out value="${titleText}"/></title>
        </head>
        <body>
            <form method="POST" action="giftSearch.json" id="giftSearchForm" name="giftSearchForm" class="searchForm">
                <div class="searchSection">
                    <tangerine:fields pageName="giftSearch"/>
                    <div class="constituentFormButtons searchConstituentButtons">
                        <input class="searchButton" type="submit" value="<spring:message code='search'/>" id="giftSearchButton" name="giftSearchButton"/>
                    </div>
                </div>
            </form>
            <div id="giftGrid"></div>
        </body>
    </html>
    <page:param name="scripts">
        <tangerine:fields pageName="giftSearchResults"/>
    </page:param>
</page:applyDecorator>
