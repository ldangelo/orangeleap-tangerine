<%@ include file="/WEB-INF/jsp/include.jsp" %>
<page:applyDecorator name="form">
    <spring:message code='softGifts' var="titleText" />
    <html>
        <head>
            <title><c:out value="${titleText} - ${requestScope.constituent.firstLast}"/></title>
        </head>
        <body>
             <div id="softGiftGrid"></div>
        </body>
    </html>
    <page:param name="scripts">
        <tangerine:fields pageName="softGiftList" gridName="softGift" 
            entityUrl="gift.htm" entityIdKey="giftId" 
            entitySecFldName="aliasId"
            leafEntityUrl="adjustedGift.htm" leafEntityIdKey="adjustedGiftId" leafEntitySecFldName="aliasId"
        />
    </page:param>
</page:applyDecorator>
