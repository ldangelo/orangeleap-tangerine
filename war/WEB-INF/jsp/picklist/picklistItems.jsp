<%@ include file="/WEB-INF/jsp/include.jsp"%>
<tiles:insertDefinition name="base">
	<tiles:putAttribute name="browserTitle" value="Manage Codes" />
	<tiles:putAttribute name="primaryNav" value="Administration" />
	<tiles:putAttribute name="secondaryNav" value="Codes" />
	<tiles:putAttribute name="mainContent" type="string">
		<div class="content760 mainForm">
	
		<c:set var="currentPicklistId" value="${param.picklistId!=null?param.picklistId:''}" />
		<div class="simplebox">
		<form method="get" action="picklistItems.htm">
			<h4>Picklist to Manage</h4>
			<select id="picklistId" name="picklistId" onchange="this.form.submit()">
			<option value="" ${currentPicklistId==''?'selected':''}>Select...</option>
			<c:forEach var="picklist" items="${picklists}">
			  <c:set var="edited" value="${picklist.persisted}" />
			  <option value="<c:out value='${picklist.id}'/>" ${currentPicklistId==picklist.id?'selected':''}><c:out value='${picklist.picklistViewName} ${edited?"*":""}'/></option>
			</c:forEach>
			</select>
		</form>
		</div>
			<div class="picklistItemFilters simplebox">
				<input type="hidden" name="picklistId" value="<c:out value='${currentPicklistId}'/>" />
				<table>
					<tr>
						<td class="action">&nbsp;</td>
						<td class="codeValue">Short Description</td>
						<td class="codeDescription">Long Description</td>
					 	<td class="inactive">Inactive</td>  
					</tr>
					<tr>
						<td><h4 style="margin:0;">Filters</h4></td>
						<td><input class="valueFilter" name="value" size="16" /></td>
						<td><input class="descriptionFilter" name="description" size="40" /></td>
					 	<td><select name="inactive">
						  <option value="all">All</option>
						  <option value="true">Only Inactive</option>
						  <option value="false">Only Active</option>
						</select></td> 
					</tr>
				</table>
			</div>
			<div class="picklistItemList simplebox">
				<jsp:include page="/picklistItemHelper.htm?picklistId=${currentPicklistId}&q=&view=table" />
			</div>
			<div class="justAdded simplebox">
			<h4>Recently Added</h4>
			<table class="tablesorter">
			</table>
			</div>
			<div class="newCode simplebox">
			<h4>Add a Picklist Item</h4>
			<table class="tablesorter">
			<tr>
				<jsp:include page="/picklistItem.htm?picklistId=${currentPicklistId}&view=newInPlace" />
				</tr>
				</table>
			</div>
		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>
