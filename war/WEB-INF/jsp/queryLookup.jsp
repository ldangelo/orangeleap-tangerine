<%@ include file="/WEB-INF/jsp/include.jsp"%>
<div class="codeList" style="border:0">
<mp:page pageName='queryLookup' />
<c:forEach var="sectionDefinition" items="${sectionDefinitions}">
	<table cellspacing="0" cellpadding="0" style="width:100%" class="alignLeft ${sectionDefinition.sectionName}"> 
		<thead> 
			<c:forEach items="${pagedListHolder.pageList}" var="row" begin="0" end="0">
				<tr>
					<th>&nbsp;</th>
					<%@ include file="/WEB-INF/jsp/snippets/gridResultsHeader.jsp" %>
				</tr>
			</c:forEach>
		</thead> 
		<tbody> 
			<c:forEach items="${pagedListHolder.pageList}" var="row">
				<tr>
					<td><a href="#" onclick="window.lookupCaller.val('${row.firstName}&nbsp;${row.lastName}');window.lookupCaller.attr('objectId','${row.id}');$('#dialog').jqmHide();window.lookupCaller=null;return false;">Use</a></td>
					<%@ include file="/WEB-INF/jsp/snippets/gridResults.jsp" %>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</c:forEach>
</div>