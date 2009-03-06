<%@ include file="/WEB-INF/jsp/include.jsp"%>
<c:choose>
	<c:when test="${!empty codes}">
		<table class="tablesorter">
			<c:forEach items="${codes}" var="code">
				<tr>
					<td class="action"><a class="editInPlace" onclick="return editInPlace(this);" href="code.htm?id=${code.id}&view=inPlace"><spring:message code='edit'/></a>
					<td class="codeValue"><c:out value='${code.value}'/></td>
					<td class="codeDescription"><c:out value='${code.description}'/></td>
					<td class="inactive"><input disabled="disabled" name="inactive" value="true" type="checkbox" <c:if test="${code.inactive}">checked="checked"</c:if>/></td>
				</tr>
			</c:forEach>
		</table>
	</c:when>
	<c:otherwise>
		<p><spring:message code='noCodesFound'/></p>
	</c:otherwise>
</c:choose>
