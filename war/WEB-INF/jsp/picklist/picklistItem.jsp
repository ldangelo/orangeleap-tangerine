<%@ include file="/WEB-INF/jsp/include.jsp" %>
<c:choose>
<c:when test="${param.view=='inPlace'}">
		<td><a href="#" class="saveInPlace" onclick="return saveInPlace(this,'picklistItem.htm');">Save</a>
		<c:if test="${picklistItem.id!=null}">
			<input type="hidden" name="picklistItemId" value="<c:out value='${picklistItem.id}'/>" />
		</c:if>
		<input type="hidden" name="picklistId" value="<c:out value='${picklistItem.picklist.id}'/>" />
		</td>
		<td>
		<input name="itemName" size="16" value="<c:out value='${picklistItem.itemName}'/>" />
		</td><td>
		<input name="defaultDisplayValue" size="16" value="<c:out value='${picklistItem.defaultDisplayValue}'/>" />
		</td>
		<td><input name="inactive" value="true" type="checkbox" ${picklistItem.inactive?'checked':''}/></td> 
</c:when>
<c:when test="${param.view=='newInPlace'}">
		<td class="action"><a href="#" class="saveInPlace" onclick="return newInPlace(this,'picklistItem.htm');">Save</a>
		<c:if test="${picklistItem.id!=null}">
			<input type="hidden" name="picklistItemId" value="<c:out value='${picklistItem.id}'/>" />
		</c:if>
		<input type="hidden" name="picklistId" value="<c:out value='${picklistItem.picklist.id}'/>" />
		</td>
		<td class="codeValue">
		<input name="itemName" size="16" value="<c:out value='${picklistItem.itemName}'/>" />
		</td><td class="codeDescription">
		<input name="defaultDisplayValue" size="16" value="<c:out value='${picklistItem.defaultDisplayValue}'/>" />
		</td>
		<td><input name="inactive" value="true" type="checkbox" ${picklistItem.inactive?'checked':''}/></td>
</c:when>
<c:otherwise>
	<td class="action"><a class="editInPlace" onclick="return editInPlace(this);" href="picklistItem.htm?picklistId=${picklistItem.picklist.id}&picklistItemId=${picklistItem.id}&itemName=<c:out value='${picklistItem.itemName}'/>&view=inPlace">Edit</a>
	<td class="codeValue"><c:out value='${picklistItem.itemName}'/></td>
	<td class="codeDescription"><c:out value='${picklistItem.defaultDisplayValue}'/></td>
    <td><input disabled="disabled" name="inactive" type="checkbox" ${picklistItem.inactive?'checked':''}/></td> 
</c:otherwise>
</c:choose>
