<%@ include file="/WEB-INF/jsp/include.jsp" %>
<mp:section sectionDefinition="${sectionDefinition}"/>
<c:forEach var="sectionField" items="${sectionFieldList}">
	<mp:field sectionField='${sectionField}' sectionFieldList='${sectionFieldList}' model='${row}' />
	<c:choose>
		<c:when test="${fieldVO.fieldType == 'HIDDEN'}">
			<input type="hidden" name="<c:out value='${gridCollectionName}'/>[<c:out value='${status.index}'/>].<c:out value='${fieldVO.fieldName}'/>" value="<c:out value='${fieldVO.fieldValue}'/>"/>
		</c:when>
		<c:otherwise>
			<td>
				<c:choose>
					<c:when test="${fieldVO.fieldType == 'DATE'}">
						<fmt:formatDate value="${fieldVO.fieldValue}" pattern="MM-dd-yy h:mm a" />
					</c:when>
					<c:when test="${fieldVO.fieldType == 'CODE' || fieldVO.fieldType == 'CODE_OTHER'}">
						<div class="lookupWrapper">
							<input value="<c:out value='${fieldVO.fieldValue}'/>" class="text <c:out value='${fieldVO.fieldName}'/> code" lookup="<c:out value='${fieldVO.fieldName}'/>" 
								codeType="<c:out value='${fieldVO.fieldName}'/>" name="display-<c:out value='${gridCollectionName}'/>[<c:out value='${status.index}'/>].<c:out value='${fieldVO.fieldName}'/>" />
							<a tabindex="-1" style="margin:0;position:absolute;top:1px;right:-3px" class="lookupLink" href="#" onclick="Lookup.loadCodePopup(this)" alt="<spring:message code='lookup'/>" title="<spring:message code='lookup'/>"><spring:message code='lookup'/></a>
						</div>
					</c:when>
					<c:otherwise>
						<input value="<c:out value='${fieldVO.fieldValue}'/>" class="text <c:out value='${fieldVO.fieldName}'/> <c:if test="${fieldVO.fieldType == 'NUMBER'}"> number</c:if><c:if test="${fieldVO.fieldType == 'PERCENTAGE'}"> percentage</c:if>" 
							type="text" 
							name="<c:out value='${gridCollectionName}'/>[<c:out value='${status.index}'/>].<c:out value='${fieldVO.fieldName}'/>"
							id="<c:out value='${gridCollectionName}'/>-<c:out value='${status.index}'/>-<c:out value='${fieldVO.fieldName}'/>"/>
					</c:otherwise>
				</c:choose>
			</td>
		</c:otherwise>
	</c:choose>
</c:forEach>