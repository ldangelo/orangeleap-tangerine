<%@ include file="/WEB-INF/jsp/include.jsp"%>
<tiles:insertDefinition name="base">
	<tiles:putAttribute name="browserTitle" value="Manage Picklists" />
	<tiles:putAttribute name="primaryNav" value="Administration" />
	<tiles:putAttribute name="secondaryNav" value="Picklists" />
	<tiles:putAttribute name="mainContent" type="string">
		<div class="content760 mainForm">
	
		<c:set var="currentPicklistNameId" value="${param.picklistNameId!=null?param.picklistNameId:''}" />
		<div class="simplebox">
		<form method="get" action="picklistItems.htm">
			<h4>Picklist to Manage</h4>
			<select id="picklistNameId" name="picklistNameId" onchange="this.form.submit()">
		  	  <option value="" ${currentPicklistNameId==''?'selected':''}>Select...</option>
			   <c:forEach var="picklist" items="${picklists}">
			     <option value="<c:out value='${picklist.picklistNameId}'/>" ${currentPicklistNameId==picklist.picklistNameId?'selected':''}><c:out value='${picklist.picklistDesc}'/></option>
			     <c:if test="${currentPicklistNameId==picklist.picklistNameId}">
						<c:set var="currentPicklistDesc" value="${picklist.picklistDesc}" />
			     </c:if>
			   </c:forEach>
			</select>
 		   <c:url var="picklistCustomizeUrl" value="picklistCustomize.htm">
 	         <c:param name="picklistNameId" value="${currentPicklistNameId}" />
		     <c:param name="view" value="customize" />
		   </c:url>
		   <c:if test="${currentPicklistNameId != ''}">
		   &nbsp;&nbsp;&nbsp;<a class="action" href="${picklistCustomizeUrl}">Customize</a>
		   </c:if>
		</form>
		</div>
			<div class="picklistItemFilters simplebox">
				<input type="hidden" name="picklistNameId" value="<c:out value='${currentPicklistNameId}'/>" />
				<table>
					<tr>
						<td class="action">&nbsp;</td>
						<td class="codeValue"><span class="required">*</span>ID</td>
						<td class="codeDescription"><span class="required">*</span>Value</td>
					 	<td class="inactive">Inactive</td>  
					 	<td class="customize">&nbsp;Customize</td>  
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
						<td></td>
					</tr>
				</table>
			</div>
			<div class="picklistItemList simplebox">
				<jsp:include page="/picklistItemHelper.htm?picklistNameId=${currentPicklistNameId}&q=&view=table" />
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
				<jsp:include page="/picklistItem.htm?picklistNameId=${currentPicklistNameId}&view=newInPlace" />
				</tr>
				</table>
			</div>
		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>
