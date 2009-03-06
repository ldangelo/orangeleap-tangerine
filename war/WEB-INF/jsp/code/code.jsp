<%@ include file="/WEB-INF/jsp/include.jsp" %>
<c:choose>
<c:when test="${param.view=='inPlace'}">
		<td><a href="#" class="saveInPlace" onclick="return saveInPlace(this,'code.htm');">Save</a>
		<c:if test="${code.id!=null}">
			<input type="hidden" name="id" value="<c:out value='${code.id}'/>" />
		</c:if>
		<input type="hidden" name="codeType" value="<c:out value='${code.codeType.name}'/>" />
		</td>
		<td>
		<input name="value" size="16" value="<c:out value='${code.value}'/>" />
		</td><td>
		<input name="description" size="16" value="<c:out value='${code.description}'/>" />
		</td>
		<td>
        <input type="hidden" name="_inactive" value="visible" />
		<input name="inactive" value="true" type="checkbox" ${code.inactive?'checked':''}/>
		</td>
</c:when>
<c:when test="${param.view=='newInPlace'}">
		<td class="action"><a href="#" class="saveInPlace" onclick="return newInPlace(this,'code.htm');">Save</a>
		<c:if test="${code.id!=null}">
			<input type="hidden" name="id" value="<c:out value='${code.id}'/>" />
		</c:if>
		<input type="hidden" name="codeType" value="<c:out value='${code.codeType.name}'/>" />
		</td>
		<td class="codeValue">
		<input name="value" size="16" value="<c:out value='${code.value}'/>" />
		</td><td class="codeDescription">
		<input name="description" size="16" value="<c:out value='${code.description}'/>" />
		</td>
		<td><input name="inactive" value="true" type="checkbox" ${code.inactive?'checked':''}/></td>
</c:when>
<c:otherwise>
	<td class="action"><a class="editInPlace" onclick="return editInPlace(this);" href="code.htm?codeId=${code.id}&view=inPlace">Edit</a>
	<td class="codeValue"><c:out value='${code.value}'/></td>
	<td class="codeDescription"><c:out value='${code.description}'/></td>
	<td><input disabled="disabled" name="inactive" type="checkbox" ${code.inactive?'checked':''}/></td>
</c:otherwise>
</c:choose>
