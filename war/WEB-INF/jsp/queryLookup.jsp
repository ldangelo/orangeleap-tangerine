<%@ include file="/WEB-INF/jsp/include.jsp"%>
<c:if test="${param.view!='resultsOnly'}">
<div class="codeList" style="border:0">
</c:if>
<mp:page pageName='queryLookup' />
<c:forEach var="sectionDefinition" items="${sectionDefinitions}">
<c:if test="${sectionDefinition.sectionName==queryLookup.sectionName}">
<c:if test="${param.view!='resultsOnly'}">
	<table cellspacing="0" cellpadding="0" style="width:100%" class="alignLeft ${sectionDefinition.sectionName}"> 
		<thead> 
			<c:forEach items="${pagedListHolder.pageList}" var="row" begin="0" end="0">
				<tr>
					<th>&nbsp;</th>
					<mp:section sectionDefinition="${sectionDefinition}"/>
					<c:forEach var="sectionField" items="${sectionFieldList}" varStatus="status">
						<mp:field sectionField='${sectionField}' sectionFieldList='${sectionFieldList}' model="${row}" />	
						<th class="header">
							<a href="#" onclick="toggleQueryLookupSortFields($(this).parent(),'${fieldVO.fieldName}');$('.refreshButton').click();return false;">${fieldVO.labelText}</a>
						</th>
					</c:forEach>	
				</tr>
			</c:forEach>
		</thead> 
		<tbody id="resultstablebody">
			<c:forEach items="${pagedListHolder.pageList}" var="row" begin="0" end="0">
				<tr class="filters">
					<td style="text-align:center"><img style="cursor: pointer;" class="refreshButton" src="images/icons/refresh.png" />
					<input name="fieldDef" type="hidden" value="${param.fieldDef}" />
					<input id="sort" name="sort" type="hidden" value="${currentSort}" />
					<input id="ascending" name="ascending" type="hidden" value="${currentAscending}" />
					</td>
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
			<c:if test="${empty pagedListHolder.pageList}">
				<tr class='resultrow'><td></td><td>No results.</td></tr>
			</c:if>
<c:if test="${param.view!='resultsOnly'}">
		</tbody>
	</table>
<div id="holdresults" style="visibility:hidden"></div>
</c:if>
</c:if>
</c:forEach>
<c:if test="${param.view!='resultsOnly'}">
</div>
</c:if>
<c:if test="${param.view!='resultsOnly'}">

<script type="text/javascript">

	$(".refreshButton").click(function(){
		var queryString = "queryLookup.htm?view=resultsOnly" 
			+ "&" + $(".filters :input").serialize();
		$.ajax({
			type: "GET",
			url: queryString,
			success: function(html){
				$("#resultstablebody .resultrow").remove();
				$("#resultstablebody").append(html);
			}
		});
	});

	function toggleQueryLookupSortFields(aheader, sortFieldName) {

		aheader.siblings().andSelf().removeClass("mpHeaderSortUp mpHeaderSortDown");
		if ( $(".filters #ascending").val() != "true" || $(".filters #sort").val() != sortFieldName) {
			aheader.addClass("mpHeaderSortUp");
			$(".filters #ascending").val("true");
		} else {
			aheader.addClass("mpHeaderSortDown");
			$(".filters #ascending").val("false");
		}
		
		$('.filters #sort').val(sortFieldName);
	}

	
</script>
</c:if>
