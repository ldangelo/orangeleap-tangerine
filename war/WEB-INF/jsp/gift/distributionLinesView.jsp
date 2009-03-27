<%@ include file="/WEB-INF/jsp/include.jsp" %>
<script type="text/javascript">
$(document).ready(function() {
	$("a.treeNodeLink").bind("click", function(event) {
		return OrangeLeap.expandCollapse(this);
	});
});
</script>
<c:forEach var="sectionDefinition" items="${gridSections}">
	<c:if test="${!empty sectionDefinition.defaultLabel}">
		<h4 class="gridSectionHeader"><mp:sectionHeader sectionDefinition="${sectionDefinition}" /></h4>
	</c:if>
	<table class="tablesorter distributionLines" id="<c:out value='${sectionDefinition.sectionHtmlName}'/>" cellspacing="0"> 
		<col class="node"/> 
		<col class="number"/>
		<col class="number"/> 
		<col class="code"/>
		<col class="code"/> 
		<thead> 
			<c:forEach items="${gridCollection}" var="row" begin="0" end="0">
				<tr>
					<th class="actionColumn">&nbsp;</th>
					<%@ include file="/WEB-INF/jsp/snippets/gridResultsHeader.jsp" %>
				</tr>
			</c:forEach>
		</thead>

		<c:forEach items="${gridCollection}" var="row" varStatus="status">
			<%@ include file="/WEB-INF/jsp/snippets/gridRowView.jsp"%>
		</c:forEach>
	</table>
</c:forEach>
