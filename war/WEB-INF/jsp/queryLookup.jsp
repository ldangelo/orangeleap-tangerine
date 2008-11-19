<%@ include file="/WEB-INF/jsp/include.jsp"%>
<c:if test="${param.view!='table'}">
<div class="codeList" style="border:0">
</c:if>
<mp:page pageName='queryLookup' />
<c:forEach var="sectionDefinition" items="${sectionDefinitions}">
<c:if test="${sectionDefinition.sectionName==queryLookup.sectionName}">
<c:if test="${param.resultsOnly!='true'}">
	<table cellspacing="0" cellpadding="0" style="width:100%" class="alignLeft ${sectionDefinition.sectionName}"> 
		<thead> 
			<c:forEach items="${pagedListHolder.pageList}" var="row" begin="0" end="0">
				<tr>
					<th>&nbsp;</th>
					<%@ include file="/WEB-INF/jsp/snippets/gridResultsHeader.jsp" %>
				</tr>
			</c:forEach>
		</thead> 
		<tbody id="resultstablebody">
			<c:forEach items="${pagedListHolder.pageList}" var="row" begin="0" end="0">
				<tr class="filters">
					<td align="center"><img style="cursor: pointer;" class="refreshButton" src="images/icons/refresh.png" />
					<input name="fieldDef" type="hidden" value="${param.fieldDef}" /></td>
					<%@ include file="/WEB-INF/jsp/snippets/gridSearchHeader.jsp" %>
				</tr>
			</c:forEach>
</c:if>
			<c:forEach items="${pagedListHolder.pageList}" var="row">
				<c:choose>
					<c:when test="${!empty row.id && !empty row.entityName}">
						<c:url value="/${row.entityName}.htm" var="entityLink" scope="page">
							<c:param name="id" value="${row.id}" />
						</c:url>
					</c:when>
					<c:otherwise>
						<c:set value="#" var="entityLink" scope="page" />
					</c:otherwise>
				</c:choose>
				<tr class="resultrow">
					<td><a href="#" displayvalue="${row.displayValue}" gotourl="${entityLink}" onclick="useQueryLookup(this,'${row.id}');return false;">Use</a></td>
					<%@ include file="/WEB-INF/jsp/snippets/gridResults.jsp" %>
				</tr>
				<c:remove var="entityLink" scope="page" />
			</c:forEach>
<c:if test="${param.resultsOnly!='true'}"> 
		</tbody>
	</table>
<div id="holdresults" style="visibility:hidden"></div>
</c:if>
</c:if>
</c:forEach>
<c:if test="${param.view!='table'}">
</div>
</c:if>
<script type="text/javascript">
	$(".refreshButton").click(function(){
		var queryString = $(".filters :input").serialize();
		$("#resultstablebody .resultrow").remove();
		$("#holdresults").load("queryLookup.htm?view=table&resultsOnly=true&"+queryString,{},
		     function(){
				   $("#resultstablebody").append($("#holdresults tr"));
			 }
		 );
		
	});
</script>