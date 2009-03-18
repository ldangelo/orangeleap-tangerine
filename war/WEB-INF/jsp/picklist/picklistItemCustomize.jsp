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
			<table class="customFields">
			<c:forEach var="field" varStatus="status" items="${map}" >
			  <tr rowindex="${status.count}">
				<td><input id="cfname-${status.count}-key" name="cfname[${status.count}]" size="16" value="<c:out value='${field.key}'/>"  /></td>
				<td><input id="cfvalue-${status.count}-value" name="cfvalue[${status.count}]" size="16" value="<c:out value='${field.value}'/>" /></td>
			  </tr>
			</c:forEach>
			</table>
			<br/>
			<input type="button" value="Add" class="saveButton" onclick="addNewRow();" />
			<input type="submit" value="Save" class="saveButton" />
		</form:form>
		<script>
		var addNewRow = function() {
			var $newRow = $("table.customFields tr:last", "form").clone(false);
			var i = $newRow.attr("rowindex");
			var j = parseInt(i, 10) + 1;
			$newRow.attr("rowindex", j);
			$newRow.find("input").each(function() {
					var $field = $(this);
					$field.attr('name', $field.attr('name').replace(new RegExp("\\[\\d+\\]","g"), "[" + j + "]"));
					$field.attr('id', $field.attr('id').replace(new RegExp("\\-\\d+\\-","g"), "-" + j + "-"));
					$field.val("");
				});
			$("table.customFields", "form").append($newRow);
		};
			
		
		
		</script>
	</tiles:putAttribute>
</tiles:insertDefinition>
