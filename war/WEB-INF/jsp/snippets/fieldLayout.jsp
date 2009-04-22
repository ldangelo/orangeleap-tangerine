<%@ include file="/WEB-INF/jsp/include.jsp" %>

<mp:section sectionDefinition="${sectionDefinition}"/>
<c:set var="totalFields" value="${sectionFieldCount}"/>
<c:choose>
	<c:when test="${sectionDefinition.layoutType eq 'TWO_COLUMN'}">
		<div class="columns">
			<div class="column">
				<ul class="formFields width385">
					<c:forEach var="sectionField" items="${sectionFieldList}" begin="0" end="${(totalFields div 2)+((totalFields%2)-1)}" varStatus="status">
						<mp:field sectionField='${sectionField}' sectionFieldList='${sectionFieldList}' />
						<jsp:include page="../snippets/input.jsp"/>
					</c:forEach>
					<li class="clear"></li>
				</ul>
			</div>
			<div class="column">
				<ul class="formFields width385">
					<c:forEach var="sectionField" items="${sectionFieldList}" begin="${(totalFields div 2)+(totalFields%2)}">
						<mp:field sectionField='${sectionField}' sectionFieldList='${sectionFieldList}' />
						<jsp:include page="../snippets/input.jsp"/>
					</c:forEach>
					<li class="clear"></li>
				</ul>
			</div>
			<div class="clearColumns"></div>
		</div>
	</c:when>
	<c:when test="${sectionDefinition.layoutType eq 'ONE_COLUMN'}">
		<div class="column singleColumn <c:out value='${sectionDefinition.sectionHtmlName}'/>" id="<c:out value='${sectionDefinition.sectionHtmlName}'/>">
			<c:if test="${!empty sectionDefinition.defaultLabel}">
				<h4 class="formSectionHeader"><mp:sectionHeader sectionDefinition="${sectionDefinition}" /></h4>
			</c:if>
			<ul class="formFields width385">
				<c:forEach var="sectionField" items="${sectionFieldList}" varStatus="status">
					<mp:field sectionField='${sectionField}' sectionFieldList='${sectionFieldList}' />
					<jsp:include page="../snippets/input.jsp"/>
				</c:forEach>
				<li class="clear"></li>
			</ul>
		</div>
	</c:when>
	<c:when test="${sectionDefinition.layoutType eq 'ONE_COLUMN_HIDDEN'}">
		<div class="column singleColumn <c:out value='${sectionDefinition.sectionHtmlName}'/>" id="<c:out value='${sectionDefinition.sectionHtmlName}'/>">
			<c:if test="${!empty sectionDefinition.defaultLabel}">
				<h4 class="formSectionHeader"><mp:sectionHeader sectionDefinition="${sectionDefinition}" /></h4>
			</c:if>
			<ul class="formFields width385">
				<c:forEach var="sectionField" items="${sectionFieldList}" varStatus="status">
					<mp:field sectionField='${sectionField}' sectionFieldList='${sectionFieldList}' />
					<jsp:include page="../snippets/input.jsp"/>
				</c:forEach>
				<li class="clear"></li>
			</ul>
		</div>
	</c:when>
	<c:when test="${sectionDefinition.layoutType eq 'GRID'}">
		<c:if test="${!empty sectionDefinition.defaultLabel}">
			<h4 class="gridSectionHeader"><mp:sectionHeader sectionDefinition="${sectionDefinition}" /></h4>
		</c:if>
		
		<table class="tablesorter" style="table-layout:fixed;" id="<c:out value='${sectionDefinition.sectionHtmlName}'/>" cellspacing="0" cellpadding="0"> 
			<thead> 
				<c:forEach items="${gridCollection}" var="row" begin="0" end="0">
					<tr>
						<%@ include file="/WEB-INF/jsp/snippets/gridResultsHeader.jsp" %>
						<th class="actionColumn">&nbsp;</th>
					</tr>
				</c:forEach>
			</thead>
			<tbody>
				<c:forEach items="${gridCollection}" var="row" varStatus="status">
					<c:if test="${row!=null}">
						<tr rowindex="<c:out value='${status.index}'/>">
							<%@ include file="/WEB-INF/jsp/snippets/gridForm.jsp"%>
							<td><img style="cursor: pointer;" class="deleteButton" src="images/icons/deleteRow.png" /></td>
						</tr>
					</c:if>
				</c:forEach>
			</tbody>
		</table>
	</c:when>
<c:otherwise>
</c:otherwise>
</c:choose>