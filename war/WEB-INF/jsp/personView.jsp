<%@ include file="/WEB-INF/jsp/include.jsp" %>
<c:choose>
<c:when test="${numberOfGifts == 0}">
${person.firstName} has not yet made a donation.
</c:when>
<c:when test="${numberOfGifts == 1}">
${person.firstName} has given one gift of <fmt:formatNumber value="${totalGiving}" type="currency"/>. 
</c:when>
<c:when test="${numberOfGifts > 1}">
${person.firstName} has given ${numberOfGifts} times<br />
for a total of <fmt:formatNumber value="${totalGiving}" type="currency"/>.
</c:when>
</c:choose>
