<%@ include file="/WEB-INF/jsp/include.jsp"%>
<tiles:insertDefinition name="base">
	<tiles:putAttribute name="browserTitle" value="Manage Codes" />
	<tiles:putAttribute name="primaryNav" value="Administration" />
	<tiles:putAttribute name="secondaryNav" value="Codes" />
	<tiles:putAttribute name="mainContent" type="string">
		<div class="content760 mainForm">
	
		<div class="simplebox">
		<form:form method="post" commandName="map" >
		    <h4>Edit custom fields for picklist item name &quot;${picklistItem.itemName}&quot;, value &quot;${picklistItem.defaultDisplayValue}&quot;</h4><br/>
			<table>
			<c:forEach var="field" varStatus="status" items="${map}" >
			  <tr>
				<td><input id="cfname${status.count}" name="cfname${status.count}" size="16" value="<c:out value='${field.key}'/>"  /></td>
				<td><input id="cfvalue${status.count}" name="cfvalue${status.count}" size="16" value="<c:out value='${field.value}'/>" /></td>
			  </tr>
			</c:forEach>
			</table>
			<br/>
			<input type="submit" value="Save" class="saveButton" />
		</form:form>
	</tiles:putAttribute>
</tiles:insertDefinition>
