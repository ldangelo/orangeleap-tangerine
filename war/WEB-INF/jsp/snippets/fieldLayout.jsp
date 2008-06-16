<%@ include file="/WEB-INF/jsp/include.jsp" %>

<mp:section sectionDefinition="${sectionDefinition}"/>
<c:set var="totalFields" value="${sectionFieldCount}"/>
<c:choose>
	<c:when test="${sectionDefinition.layoutType eq 'TWO_COLUMN'}">
		<div class="columns">
			<div class="column">
				<ul class="formFields width375">
					<c:forEach var="sectionField" items="${sectionFieldList}" begin="0" end="${(totalFields div 2)+((totalFields%2)-1)}" varStatus="status">
						<mp:field sectionField='${sectionField}' sectionFieldList='${sectionFieldList}' />
						<%@ include file="/WEB-INF/jsp/snippets/input.jsp"%>
					</c:forEach>
					<li class="clear"></li>
				</ul>
			</div>
			<div class="column">
				<ul class="formFields width375">
					<c:forEach var="sectionField" items="${sectionFieldList}" begin="${(totalFields div 2)+(totalFields%2)}">
						<mp:field sectionField='${sectionField}' sectionFieldList='${sectionFieldList}' />
						<%@ include file="/WEB-INF/jsp/snippets/input.jsp"%>
					</c:forEach>
					<li class="clear"></li>
				</ul>
			</div>
			<div class="clearColumns"></div>
		</div>
	</c:when>
<c:otherwise>
</c:otherwise>
</c:choose>