<%@ include file="/WEB-INF/jsp/include.jsp"%>
<div class="modalTopLeft">
	<div class="modalTopRight">
		<h4 class="dragHandle" id="modalTitle">
			<c:choose>
				<c:when test="${empty requestScope.modalTitle}"><spring:message code="selectOptions"/></c:when>
				<c:otherwise><c:out value="${requestScope.modalTitle}"/></c:otherwise>
			</c:choose>
		</h4>
		<a href="javascript:void(0)" class="jqmClose hideText"><spring:message code="close"/></a>
	</div>
</div>
<div class="modalContentWrapper">
	<div class="modalContent">
		<form method="POST" action="multiQueryLookup.htm" id="multiQueryLookupForm">
			<mp:page pageName='queryLookup' />
			<c:forEach var="sectionDefinition" items="${sectionDefinitions}">
				<c:if test="${sectionDefinition.sectionName eq queryLookup.sectionName}">
			        <div class="modalSearch">
			        	<label for="searchText"><spring:message code="searchBy"/></label>
			        	<select name="searchOption" id="searchOption">
							<mp:section sectionDefinition="${sectionDefinition}"/>
							
							<c:forEach var="sectionField" items="${sectionFieldList}" varStatus="status">
								<mp:field sectionField='${sectionField}' sectionFieldList='${sectionFieldList}' />
								<option value="<c:out value='${fieldVO.fieldName}'/>"><c:out value='${fieldVO.labelText}'/></option>
							</c:forEach>	
						</select>        	
						<input type="hidden" name="fieldDef" id="fieldDef" value="<c:out value='${fieldDef}'/>" />
			        	<input type="text" value="" id="searchText" name="searchText"/>
			        	<input type="button" id="findButton" name="findButton" value="<spring:message code='find'/>" class="saveButton" />
			        </div>
			        <table cellspacing="0" class="multiSelect noDisplay" id="multiQueryLookupResultsTable">
			            <thead>
			                <tr>
			                    <th><input type="checkbox" title="<spring:message code='selectAllOptions'/>" selection="available" id="availableAll"/><strong><spring:message code='available'/></strong></th>
			                    <th class="spacer">&nbsp;</th>
			                    <th><input type="checkbox" title="<spring:message code='selectAllOptions'/>" selection="selected" id="selectedAll"/><strong><spring:message code='selected'/></strong></th>
			                </tr>
			            </thead>
			            <tbody>
				            <tr>
				                <td>
				                    <ul id="availableOptions">	
				                    </ul>
				                </td>
			                    <td class="spacer">&nbsp;</td>
				                <td>
				                    <ul id="selectedOptions">
										<c:set var="counter" value="0" scope="page"/>
										<c:forEach items="${requestScope.selectedIds.ids}" var="myId" varStatus="status">
											<c:choose>
												<c:when test="${!empty myId && !empty requestScope.selectedIds.names[status.index]}">
													<c:url value="person.htm" var="entityLink" scope="page"> <%--  TODO: fix hard coding of person.htm --%>
														<c:param name="id" value="${myId}" />
													</c:url>
												</c:when>
												<c:otherwise>
													<c:set value="javascript:void(0)" var="entityLink" scope="page" />
												</c:otherwise>
											</c:choose>
											<li id="<c:out value='${myId}'/>-li">
												<input type="checkbox" name="option${counter}" id="${myId}" title="<spring:message code='clickToSelect'/>" displayvalue="<c:out value='${requestScope.selectedIds.names[status.index]}'/>" />
												<c:out value='${requestScope.selectedIds.names[status.index]}'/>
												<a href="<c:out value='${entityLink}'/>" target="_blank"><img src="images/icons/link.png" alt="<spring:message code='gotoLink'/>" title="<spring:message code='gotoLink'/>"/></a>
											</li>
											<c:remove var="entityLink" scope="page" />
											<c:set var="counter" value="${counter + 1}"/>
										</c:forEach>
				                    </ul>
				                </td>
				            </tr>
			            </tbody>
			        </table>
				</c:if>
			</c:forEach>
			<div class="buttonsDiv">
				<input type="button" value="<spring:message code='done'/>" class="saveButton" name="doneButton" id="doneButton" />
				<input type="button" value="<spring:message code='cancel'/>" class="saveButton" name="cancelButton" id="cancelButton" />
			</div>
		</form>
	</div>
	<div class='modalSideRight'>&nbsp;</div>
</div>
<div class="modalBottomLeft">&nbsp;<div class="modalBottomRight">&nbsp;</div></div>
