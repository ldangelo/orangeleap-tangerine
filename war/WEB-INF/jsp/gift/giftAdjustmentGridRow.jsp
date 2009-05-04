<%@ include file="/WEB-INF/jsp/include.jsp" %>
<c:if test="${row != null}">
	<tbody class="<c:if test='${not empty hiddenGridRows}'>expandable</c:if> gridRow" 
		id="gridRow<c:out value='${counter}'/>">
		<mp:section sectionDefinition="${sectionDefinition}"/>
		<tr class="lineRow <c:if test='${not empty hiddenGridRows}'>expandableRow collapsed</c:if>">
			<c:if test='${not empty hiddenGridRows}'><td class="nodeLink"><a href="#" class="treeNodeLink plus" title="<spring:message code='clickShowHideExtended'/>" rowIndex="<c:out value='${counter}'/>"><spring:message code='clickShowHideExtended'/></a></td></c:if> 
			<%@ include file="/WEB-INF/jsp/snippets/gridForm.jsp"%>
		</tr>
		<c:if test='${not empty hiddenGridRows}'>
			<tr class="hiddenRow noDisplay">
				<td colspan="<c:out value='${sectionFieldCount}'/>">
					<table>
						<tr>
							<td>
								<c:forEach var="hiddenGridRow" items="${hiddenGridRows}">
									<div class="columns">
										<div class="column">
											<mp:section sectionDefinition="${hiddenGridRow}" />
											<c:set var="hiddenTotalFields" value="${sectionFieldCount}" scope="request"/>
											<ul class="formFields width350">
												<c:forEach var="sectionField" items="${sectionFieldList}" begin="0" end="${(hiddenTotalFields div 2)+((hiddenTotalFields%2)-1)}" varStatus="status">
													<mp:field sectionField='${sectionField}' sectionFieldList='${sectionFieldList}' model="${row}" />
													<c:set target="${fieldVO}" property="fieldName" value="${gridCollectionName}[${counter}].${fieldVO.fieldName}"/>
													<c:set var="sectionDefinition" value="${sectionDefinition}" scope="request"/>
													<c:set var="sectionField" value="${sectionField}" scope="request"/>
													<jsp:include page="../snippets/input.jsp"/>
												</c:forEach>
												<li class="clear"></li>
											</ul>
										</div>
										<div class="column">
											<ul class="formFields width350">
												<c:forEach var="sectionField" items="${sectionFieldList}" begin="${(hiddenTotalFields div 2)+(hiddenTotalFields%2)}">
													<mp:field sectionField='${sectionField}' sectionFieldList='${sectionFieldList}' model="${row}" />
													<c:set target="${fieldVO}" property="fieldName" value="${gridCollectionName}[${counter}].${fieldVO.fieldName}"/>
													<c:set var="sectionDefinition" value="${sectionDefinition}" scope="request"/>
													<c:set var="sectionField" value="${sectionField}" scope="request"/>
													<jsp:include page="../snippets/input.jsp"/>
												</c:forEach>
												<li class="clear"></li>
											</ul>
										</div>
										<div class="clearColumns"></div>
									</div>
								</c:forEach>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</c:if>
	</tbody>
</c:if>
