<%@ include file="/WEB-INF/jsp/include.jsp" %>

<mp:section sectionDefinition="${sectionDefinition}"/>
<c:forEach var="sectionField" items="${sectionFieldList}" varStatus="status">
	<mp:field sectionField='${sectionField}' sectionFieldList='${sectionFieldList}' model="${row}"/>
	<c:if test="${fieldVO.fieldType ne 'HIDDEN'}">
		<td>
			<c:choose>
				<c:when test="${empty fieldVO.displayValue}">&nbsp;</c:when>
				<c:when test="${fieldVO.fieldType == 'DATE'}">
					<fmt:formatDate value="${fieldVO.displayValue}" pattern="MM-dd-yy h:mm a" />
				</c:when>
				<c:when test="${fieldVO.fieldType == 'QUERY_LOOKUP_DISPLAY'}">
						<c:forEach var="val" varStatus="status" items="${fieldVO.displayValues}">
						<c:set var="thisVal" value="${fn:trim(val)}"/>
						<c:choose>
							<c:when test="${not empty fieldVO.ids[status.index]}">
								<c:url value="${fieldVO.referenceType}.htm" var="entityLink" scope="page">
									<c:param name="${fieldVO.referenceType}Id" value="${fieldVO.ids[status.index]}" />
									<c:if test="${fieldVO.referenceType != 'person'}">
										<c:param name="personId" value="${param.personId}" />
									</c:if>
								</c:url>
								<a href="<c:out value='${entityLink}'/>" target="_blank" alt="<spring:message code='gotoLink'/>" title="<spring:message code='gotoLink'/>"><c:out value='${thisVal}'/></a>
								<c:remove var="entityLink" scope="page" />
							</c:when>
							<c:otherwise>
								<span><c:out value='${thisVal}'/></span>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</c:when>
				<c:when test="${fieldVO.fieldType == 'PICKLIST' or fieldVO.fieldType == 'PICKLIST_DISPLAY'}">
					<c:forEach var="code" varStatus="status" items="${fieldVO.codes}">
						<c:set target="${fieldVO}" property="fieldToCheck" value="${code}"/>
						<c:if test="${fieldVO.hasField}">
							<c:out value='${fieldVO.displayValues[status.index]}'/>
						</c:if>
					</c:forEach>
				</c:when>
				<c:when test="${fieldVO.multiLine}">
					<c:forEach var="line" items="${fieldVO.splitLineValues}">
						<c:out value='${line}'/><br/>
					</c:forEach>
				</c:when>
				<c:otherwise><c:out value='${fieldVO.displayValue}'/></c:otherwise>
			</c:choose>
		</td>
	</c:if>
</c:forEach>