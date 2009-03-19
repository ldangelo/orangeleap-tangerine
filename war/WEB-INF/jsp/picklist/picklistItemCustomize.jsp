<%@ include file="/WEB-INF/jsp/include.jsp"%>
<tiles:insertDefinition name="base">
	<tiles:putAttribute name="browserTitle" value="Manage Codes" />
	<tiles:putAttribute name="primaryNav" value="Administration" />
	<tiles:putAttribute name="secondaryNav" value="Codes" />
	<tiles:putAttribute name="mainContent" type="string">
		<div class="content760 mainForm">
	
		<div class="simplebox">

		<div >
		<form:form method="post" commandName="map" >
		    <h4>Edit custom fields for picklist &quot;<c:out value='${param.picklistDesc}'/>&quot;, item name &quot;<c:out value='${picklistItem.itemName}'/>&quot;, value &quot;<c:out value='${picklistItem.defaultDisplayValue}'/>&quot;</h4><br/>
			<table class="customFields">
			<c:forEach var="field" varStatus="status" items="${map}" >
			  <tr rowindex="${status.count}">
				<td><input id="cfname-${status.count}-key" name="cfname[${status.count}]" size="32" value="<c:out value='${field.key}'/>" <c:if test="${fn:startsWith(field.key, 'AccountString')}">readonly style="background-color:#DDDDDD" </c:if> /></td>
				<td><input id="cfvalue-${status.count}-value" name="cfvalue[${status.count}]" size="60" value="<c:out value='${field.value}'/>" <c:if test="${fn:startsWith(field.key, 'AccountString')}">readonly style="background-color:#DDDDDD" </c:if> /></td>
			  </tr>
			</c:forEach>
			</table>
			<br/>
			<input type="button" value="Add" class="saveButton" onclick="PicklistCustomizer.addNewRow();" />
			<input type="submit" value="Save" class="saveButton" />
		</form:form>
		</div>
		
		<br/>
 		<c:url var="picklistItemUrl" value="picklistItems.htm">
 			<c:param name="picklistNameId" value="${picklist.picklistNameId}" />
		</c:url>
		<strong><a class="action" href="${picklistItemUrl}">&laquo;Back</a></strong>

		</div>
		
		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>
