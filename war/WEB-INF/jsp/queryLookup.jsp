<%@ include file="/WEB-INF/jsp/include.jsp"%>
<div class="codeList" style="border:0">
<mp:page pageName='queryLookup' />
<c:forEach var="sectionDefinition" items="${sectionDefinitions}">
<c:if test="${sectionDefinition.sectionName==queryLookup.sectionName}">
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
				<c:set var="displayStr">${row.firstName}${" "}${row.lastName}</c:set>
				<tr>
					<td><a href="#" displayvalue="${displayStr}" onclick="useQueryLookup(this,'${row.id}');return false;">Use</a></td>
					<%@ include file="/WEB-INF/jsp/snippets/gridResults.jsp" %>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</c:if>
</c:forEach>
</div>