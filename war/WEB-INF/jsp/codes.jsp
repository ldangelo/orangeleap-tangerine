<%@ include file="/WEB-INF/jsp/include.jsp"%>
<tiles:insertDefinition name="base">
	<tiles:putAttribute name="browserTitle" value="Search People" />
	<tiles:putAttribute name="primaryNav" value="People" />
	<tiles:putAttribute name="secondaryNav" value="Codes" />
	<tiles:putAttribute name="mainContent" type="string">
		<div class="content760 mainForm">
		<c:set var="currentCodeType" value="${param.codeType!=null?param.codeType:'projectCode'}" />
		<div class="simplebox">
		<form method="get" action="codes.htm">
			<h4>Code Type to Manage</h4>
			<select id="codeType" name="codeType" onchange="this.form.submit()">
			  <option value="projectCode" ${currentCodeType=='projectCode'?'selected':''}>Project Code</option>
			  <option value="motivationCode" ${currentCodeType=='motivationCode'?'selected':''}>Motivation Code</option>
			</select>
		</form>
		</div>
			<div class="filters simplebox"> 
				<input type="hidden" name="type" value="${currentCodeType}" />
				<table>
					<tr>
						<td class="action">&nbsp;</td>
						<td class="codeValue">Value</td>
						<td class="codeDescription">Description</td>
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
			<div class="codeList simplebox">
				<jsp:include page="/codeHelper.htm?type=${currentCodeType}&q=&view=table" />
			</div>
			<div class="justAdded simplebox">
			<h4>Recently Added</h4>
			<table class="tablesorter">
			</table>
			</div>
			<div class="newCode simplebox">
			<h4>Add a Code</h4>
			<table class="tablesorter">
			<tr>
				<jsp:include page="/code.htm?codeType=${currentCodeType}&view=newInPlace" />
				</tr>
				</table>
			</div>
		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>
