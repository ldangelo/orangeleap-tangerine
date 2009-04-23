<%@ include file="/WEB-INF/jsp/include.jsp" %>
<c:forEach var="sectionDefinition" items="${gridSections}">
	<c:if test="${!empty sectionDefinition.defaultLabel}">
		<h4 class="gridSectionHeader"><mp:sectionHeader sectionDefinition="${sectionDefinition}" /></h4>
	</c:if>
	
	<table class="tablesorter distributionLines" id="<c:out value='${sectionDefinition.sectionHtmlName}'/>" cellspacing="0">
		<c:if test='${not empty hiddenGridRows}'><col class="node"/></c:if> 
		<col class="number"/>
		<col class="pct"/> 
		<col class="code"/>
		<col class="code"/> 
		<col class="reference"/> 
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
			<span class="warningText" id="amountsErrorSpan"><spring:message code='mustMatchGiftValue'/></span> 
		</div>
		<div class="value" id="subTotal">0</div>
        <span id="totalContributionInfo">
            <div id="totalContributionText">
            <spring:message code="totalContribution"/>
            </div>
            <div class="value" id="totalContribution">0</div>
        </span>
        <script type="text/javascript">
            Ext.fly('totalContributionInfo').hide();
        </script>
	</div>
</c:forEach>