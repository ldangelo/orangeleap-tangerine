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
		<form method="POST" action="multiQueryLookup.htm">
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
					<div id="noResultsDiv" class="noDisplay noResults"><spring:message code="searchNoResults"/></div>
			        <table cellspacing="0" class="multiSelect">
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
				                    	<c:set var="showSelectedIds" value="false" scope="request"/>
				                    	<jsp:include page="multiQueryLookupResults.jsp"/>		 
				                    </ul>
				                </td>
			                    <td class="spacer">&nbsp;</td>
				                <td>
				                    <ul id="selectedOptions">
				                    	<c:set var="showSelectedIds" value="true" scope="request"/>
				                    	<jsp:include page="multiQueryLookupResults.jsp"/>		 
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
