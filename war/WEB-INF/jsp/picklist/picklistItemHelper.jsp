<%@ include file="/WEB-INF/jsp/include.jsp"%>
<c:choose>
	<c:when test="${param.view=='table'}">
		<c:choose>
			<c:when test="${!empty picklistItems}">
				<table class="tablesorter">
					<c:forEach items="${picklistItems}" var="picklistItem">
						<tr>
 							   <c:url var="picklistItemUrl" value="picklistItem.htm">
 							     <c:param name="picklistId" value="${picklistItem.picklistId}" />
 							     <c:param name="picklistItemId" value="${picklistItem.id}" />
 							     <c:param name="itemName" value="${picklistItemName}" />
							      <c:param name="view" value="inPlace" />
							    </c:url>
							<td class="action"><a class="editInPlace" onclick="return editInPlace(this);" href="${picklistItemUrl}">Edit</a>
							<td class="codeValue"><c:out value='${picklistItem.itemName}'/></td>
							<td class="codeDescription"><c:out value='${picklistItem.defaultDisplayValue}'/></td>
						 	<td class="inactive"><input disabled="disabled" name="inactive" value="true" type="checkbox" ${picklistItem.inactive?'checked':''}/></td> 
 							   <c:url var="picklistItemCustomizeUrl" value="picklistItemCustomize.htm">
 							     <c:param name="picklistId" value="${picklistItem.picklistId}" />
 							     <c:param name="picklistItemId" value="${picklistItem.id}" />
 							     <c:param name="itemName" value="${picklistItemName}" />
							      <c:param name="view" value="customize" />
							    </c:url>
							<td class="action"><a class="editInPlace" href="${picklistItemCustomizeUrl}">+</a></td>
						</tr>
					</c:forEach>
				</table>
			</c:when>
			<c:otherwise>
				<p>No picklist items were found matching those criteria.</p>
			</c:otherwise>
		</c:choose>
	</c:when>
	<c:when test="${param.view=='popup'}">
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
				<div class="codeList" style="border:0"> <%-- TODO: move to stylesheet --%>
					<table style="width:100%" class="popupFilters">
						<tr>
							<td class="action"><input type="hidden" name="picklistId" value="<c:out value='${param.picklistId}'/>" /></td>
							<td class="codeValue"><input style="width:80%" name="value" /></td>
							<td class="codeDescription"><input style="width:80%" name="description" /></td>
						</tr>
					</table>
					<div class="filterReplace">
						<c:choose>
						<c:when test="${!empty picklistItems}">
							<table style="width:100%">
								<c:forEach items="${picklistItems}" var="picklistItem">
									<tr>
										<td class="action"><a class="editInPlace" onclick="Lookup.setCodeValue('<c:out value="${picklistItem.itemName}"/>')" href="#">Use</a>
										<td class="codeValue"><c:out value='${picklistItem.itemName}'/></td>
										<td class="codeDescription"><c:out value='${picklistItem.defaultDisplayValue}'/></td>
									</tr>
								</c:forEach>
							</table>
						</c:when>
							<c:otherwise>
								<p>No picklist items were found matching those criteria.</p>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
			</div>
			<div class='modalSideRight'>&nbsp;</div>
		</div>
		<div class="modalBottomLeft">&nbsp;<div class="modalBottomRight">&nbsp;</div></div>
		<script type="text/javascript">
			$(".popupFilters :input").bind("keyup",function(){
				var queryString = $(".popupFilters :input").serialize();
				$(".filterReplace").load("picklistItemHelper.htm?view=popupTable&"+queryString);
			});
		</script>
	</c:when>
	<c:when test="${param.view=='popupTable'}">
		<c:choose>
			<c:when test="${!empty picklistItems}">
				<table style="width:100%">
					<c:forEach items="${picklistItems}" var="picklistItem">
						<tr>
							<td class="action"><a class="editInPlace" onclick="Lookup.setCodeValue('<c:out value="${picklistItem.itemName}"/>')" href="#">Use</a>
							<td class="codeValue"><c:out value='${picklistItem.itemName}'/></td>
							<td class="codeDescription"><c:out value='${picklistItem.defaultDisplayValue}'/></td>
						</tr>
					</c:forEach>
				</table>
			</c:when>
			<c:otherwise>
				<p>No picklist items were found matching those criteria.</p>
			</c:otherwise>
		</c:choose>
	</c:when>
	<c:otherwise>
		<c:forEach items="${picklistItems}" var="picklistItem">
			<c:out value='${picklistItem.itemName}'/>|<c:out value='${picklistItem.defaultDisplayValue}'/>&nbsp;
		</c:forEach>
	</c:otherwise>
</c:choose>