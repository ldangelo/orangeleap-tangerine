<%@ include file="/WEB-INF/jsp/include.jsp"%>
<c:if test="${param.view != 'resultsOnly'}">
	<div class="modalTopLeft">
		<div class="modalTopRight">
			<h4 class="dragHandle" id="modalTitle">
				<c:choose>
					<c:when test="${empty requestScope.modalTitle}">Lookup</c:when>
					<c:otherwise><c:out value="${requestScope.modalTitle}"/></c:otherwise>
				</c:choose>
			</h4>
			<a href="javascript:void(0)" class="jqmClose hideText">Close</a>
		</div>
	</div>
	<div class="modalContentWrapper">
		<div class="modalContent">
</c:if>
<mp:page pageName='queryLookup' />
<c:forEach var="sectionDefinition" items="${sectionDefinitions}">
	<c:if test="${sectionDefinition.sectionName eq queryLookup.sectionName}">
		<c:if test="${param.view != 'resultsOnly'}">
			<table cellspacing="0" cellpadding="0" style="width:100%" class="alignLeft <c:out value='${sectionDefinition.sectionName}'/>"> 
				<thead> 
					<c:forEach items="${pagedListHolder.pageList}" var="row" begin="0" end="0">
						<tr>
							<th>&nbsp;</th>
							<mp:section sectionDefinition="${sectionDefinition}"/>
							<c:forEach var="sectionField" items="${sectionFieldList}" varStatus="status">
								<mp:field sectionField='${sectionField}' sectionFieldList='${sectionFieldList}' model="${row}" />	
								<th class="header">
									<a href="#" onclick="QueryLookup.toggleQueryLookupSortFields($(this).parent(),'<c:out value="${fieldVO.fieldName}"/>')"><c:out value='${fieldVO.labelText}'/></a>
								</th>
							</c:forEach>	
						</tr>
					</c:forEach>
					<c:forEach items="${pagedListHolder.pageList}" var="row" begin="0" end="0">
						<tr class="filters">
							<td>
							<input name="fieldDef" type="hidden" value="<c:out value='${param.fieldDef}'/>" />
							<input id="sort" name="sort" type="hidden" value="<c:out value='${currentSort}'/>" />
							<input id="ascending" name="ascending" type="hidden" value="<c:out value='${currentAscending}'/>" />
							</td>
							<%@ include file="/WEB-INF/jsp/snippets/gridSearchHeader.jsp" %>
						</tr>
					</c:forEach>
				</thead> 
				<tbody id="resultstablebody">
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
				<td><a href="#" displayvalue="<c:out value='${row.displayValue}'/>" gotourl="<c:out value='${entityLink}'/>" onclick="Lookup.useQueryLookup(this,'${row.id}')">Use</a></td>
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
<c:if test="${param.view != 'resultsOnly'}">
			</div>
		<div class='modalSideRight'>&nbsp;</div>
	</div>
	<div class="modalBottomLeft">&nbsp;<div class="modalBottomRight">&nbsp;</div></div>

	<script type="text/javascript">
		$(".filters :input").bind("keyup", function(){
			QueryLookup.doQuery();
		});
		
		var QueryLookup = {
			doQuery: function() {
				var queryString = $(".filters :input").serialize();
				$("#resultstablebody").load("queryLookup.htm?view=resultsOnly&" + queryString);
			},
			
			toggleQueryLookupSortFields: function(aheader, sortFieldName) {
				aheader.siblings().andSelf().removeClass("mpHeaderSortUp mpHeaderSortDown");
				if ( $(".filters #ascending").val() != "true" || $(".filters #sort").val() != sortFieldName) {
					aheader.addClass("mpHeaderSortUp");
					$(".filters #ascending").val("true");
				} else {
					aheader.addClass("mpHeaderSortDown");
					$(".filters #ascending").val("false");
				}
				
				$('.filters #sort').val(sortFieldName);
				
				this.doQuery();
				return false;
			}
		}
	</script>
</c:if>
