<%@ include file="/WEB-INF/jsp/include.jsp"%>
<c:choose>
	<c:when test="${param.view=='table'}">
		<c:choose>
			<c:when test="${!empty codes}">
				<table class="tablesorter">
					<c:forEach items="${codes}" var="code">
						<tr>
							<td class="action"><a class="editInPlace" onclick="return editInPlace(this);" href="code.htm?codeId=${code.id}&view=inPlace">Edit</a>
							<td class="codeValue"><c:out value='${code.value}'/></td>
							<td class="codeDescription"><c:out value='${code.description}'/></td>
							<td class="inactive"><input disabled="disabled" name="inactive" value="true" type="checkbox" ${code.inactive?'checked':''}/></td>
						</tr>
					</c:forEach>
				</table>
			</c:when>
			<c:otherwise>
				<p>No codes were found matching those criteria.</p>
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
							<td class="action"><input type="hidden" name="type" value="<c:out value='${param.type}'/>" /></td>
							<td class="codeValue"><input style="width:80%" name="value" /></td>
							<td class="codeDescription"><input style="width:80%" name="description" /></td>
						</tr>
					</table>
					<div class="filterReplace">
						<c:choose>
						<c:when test="${!empty codes}">
							<table style="width:100%">
								<c:forEach items="${codes}" var="code">
									<tr>
										<td class="action"><a class="editInPlace" onclick="Lookup.setCodeValue('<c:out value="${code.value}"/>')" href="#">Use</a>
										<td class="codeValue"><c:out value='${code.value}'/></td>
										<td class="codeDescription"><c:out value='${code.description}'/></td>
									</tr>
								</c:forEach>
							</table>
						</c:when>
							<c:otherwise>
								<p>No codes were found matching those criteria.</p>
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
				$(".filterReplace").load("codeHelper.htm?view=popupTable&"+queryString);
			});
		</script>
	</c:when>
	<c:when test="${param.view=='popupTable'}">
		<c:choose>
			<c:when test="${!empty codes}">
				<table style="width:100%">
					<c:forEach items="${codes}" var="code">
						<tr>
							<td class="action"><a class="editInPlace" onclick="Lookup.setCodeValue('<c:out value="${code.value}"/>')" href="#">Use</a>
							<td class="codeValue"><c:out value='${code.value}'/></td>
							<td class="codeDescription"><c:out value='${code.description}'/></td>
						</tr>
					</c:forEach>
				</table>
			</c:when>
			<c:otherwise>
				<p>No codes were found matching those criteria.</p>
			</c:otherwise>
		</c:choose>
	</c:when>
	<c:otherwise>
		<c:forEach items="${codes}" var="code">
			<c:out value='${code.value}'/>|<c:out value='${code.description}'/>&nbsp;
		</c:forEach>
	</c:otherwise>
</c:choose>