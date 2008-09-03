<%@ include file="/WEB-INF/jsp/include.jsp"%>
<c:choose>
<c:when test="${param.view=='table'}">
	<c:choose>
		<c:when test="${!empty codes}">
			<table class="tablesorter">
				<c:forEach items="${codes}" var="code">
					<tr>
						<td class="action"><a class="editInPlace" onclick="return editInPlace(this);" href="code.htm?codeId=${code.id}&view=inPlace">Edit</a>
						<td class="codeValue">${code.value}</td>
						<td class="codeDescription">${code.description}</td>
						<td class="inactive"><input disabled="disabled" name="inactive" value="true" type="checkbox" ${code.inactive?'checked':''}/></td>
					</tr>
				</c:forEach>
			</table>
		</c:when>
		<c:otherwise>
			<p>No codes were found matching those criteria.</p>
		</c:otherwise>
	</c:choose>
</c:when>
<c:otherwise>
<c:forEach items="${codes}" var="code">
${code.value}|${code.description}
</c:forEach>
</c:otherwise>
</c:choose>