<%@ include file="/WEB-INF/jsp/include.jsp" %>
<c:forEach var="sectionDefinition" items="${gridSections}">
	<c:if test="${!empty sectionDefinition.defaultLabel}">
		<h4 class="gridSectionHeader"><mp:sectionHeader sectionDefinition="${sectionDefinition}" /></h4>
	</c:if>
	
	<table class="tablesorter giftInKindDetails" id="<c:out value='${sectionDefinition.sectionHtmlName}'/>" cellspacing="0">
		<c:if test='${not empty hiddenGridRows}'><col class="node"/></c:if> 
		<col class="number"/>
		<col class="text"/> 
		<col class="code"/>
		<col class="checkbox"/> 
		<col class="button"/>
		<thead> 
			<c:forEach items="${gridCollection}" var="row" begin="0" end="0">
				<tr>
					<c:if test='${not empty hiddenGridRows}'><th class="actionColumn">&nbsp;</th></c:if>
					<%@ include file="/WEB-INF/jsp/snippets/gridResultsHeader.jsp" %>
					<th class="actionColumn">&nbsp;</th>
				</tr>
			</c:forEach>
		</thead>
		<c:forEach items="${dummyGridCollection}" var="row" varStatus="status">
			<c:set var="counter" value="0" scope="request"/>
			<%@ include file="/WEB-INF/jsp/snippets/gridRow.jsp"%>
		</c:forEach>
		<c:forEach items="${gridCollection}" var="row" varStatus="status">
			<c:set var="counter" value="${status.index + 1}" scope="request"/>
			<%@ include file="/WEB-INF/jsp/snippets/gridRow.jsp"%>
		</c:forEach>
	</table>
	<div class="gridActions">
		<div id="totalText">
			<spring:message code='total'/>&nbsp;
			<span class="warningText" id="valueErrorSpan"><spring:message code='mustMatchFairMarketValue'/></span> 
		</div>
		<div class="value" id="subTotal">0</div>
	</div>
</c:forEach>
