<%@ include file="/WEB-INF/jsp/include.jsp" %>
<c:choose>
<c:when test="${param.view=='inPlace'}">
		<td><a href="#" class="saveInPlace" onclick="return saveInPlace(this,'code.htm');">Save</a>
		<c:if test="${code.id!=null}">
			<input type="hidden" name="id" value="${code.id}" />
		</c:if>
		<input type="hidden" name="codeType" value="${code.type.name}" />
		</td>
		<td>
		<input name="value" size="16" value="${code.value}" />
		</td><td>
		<input name="description" size="16" value="${code.description}" />
		</td>
		<td><input name="inactive" value="true" type="checkbox" ${code.inactive?'checked':''}/></td>
</c:when>
<c:when test="${param.view=='newInPlace'}">
		<td class="action"><a href="#" class="saveInPlace" onclick="return newInPlace(this,'code.htm');">Save</a>
		<c:if test="${code.id!=null}">
			<input type="hidden" name="id" value="${code.id}" />
		</c:if>
		<input type="hidden" name="codeType" value="${code.type.name}" />
		</td>
		<td class="codeValue">
		<input name="value" size="16" value="${code.value}" />
		</td><td class="codeDescription">
		<input name="description" size="16" value="${code.description}" />
		</td>
		<td><input name="inactive" value="true" type="checkbox" ${code.inactive?'checked':''}/></td>
</c:when>
<c:when test="${param.view=='form'}">
	<form:form method="post" commandName="code" action="code.htm">
		<c:if test="${code.id!=null}">
			<input type="hidden" name="id" value="${code.id}" />
		</c:if>
		<input type="hidden" name="codeType" value="${code.type.name}" />
		value<form:input path="value" size="16" cssClass="text" cssErrorClass="textError" />
		description<form:input path="description" size="16" cssClass="text" cssErrorClass="textError" />
		<input type="submit" value="Save Changes" class="saveButton" />
	</form:form>
</c:when>
<c:otherwise>
	<td class="action"><a class="editInPlace" onclick="return editInPlace(this);" href="code.htm?codeId=${code.id}&view=inPlace">Edit</a>
	<td class="codeValue">${code.value}</td>
	<td class="codeDescription">${code.description}</td>
	<td><input disabled="disabled" name="inactive" type="checkbox" ${code.inactive?'checked':''}/></td>
</c:otherwise>
</c:choose>
