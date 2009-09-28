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
		<form method="POST" action="codeHelperAdditional.htm" id="codeHelperAdditionalForm" class="codeForm">
	        <div class="modalSearch">
	        	<label for="searchText"><spring:message code="searchBy"/></label>
	        	<select name="searchOption" id="searchOption">
					<option value="value"><spring:message code="code"/></option>
					<option value="description"><spring:message code="description"/></option>
				</select>        	
				<input type="hidden" name="type" value="<c:out value='${param.type}'/>" id="type" />
	        	<input type="text" value="" id="searchText" name="searchText"/>
	        	<input type="button" id="findButton" name="findButton" value="<spring:message code='find'/>" class="button" />
	        </div>
	        <div id="codeNoResultsDiv" class="noDisplay noResults"><spring:message code="searchNoResults"/></div>
			<table cellspacing="0" class="multiSelect" id="codeResultsTable">
				<thead>
			    	<tr>
	                    <th><input type="checkbox" title="<spring:message code='selectAllOptions'/>" selection="available" id="availableAll"/><strong><spring:message code='available'/></strong></th>
	                    <th class="spacer">&nbsp;</th>
	                    <th><input type="checkbox" title="<spring:message code='selectAllOptions'/>" selection="selected" id="selectedAll"/><strong><spring:message code='selected'/></strong></th>
			        </tr>
			    </thead>
			    <tbody>
					<tr>
						<td id="">
		                    <ul id="availableOptions">	
	               				<jsp:include page="codeAdditionalFieldsResults.jsp"/>		 
		                    </ul>
			            </td>
	                    <td class="spacer">&nbsp;</td>
			            <td>
			                <ul id="selectedOptions">
								<c:forEach items="${requestScope.selectedCodes}" var="code">
									
									<li id="<c:out value='${code.value}'/>-li">
										<input type="checkbox" name="<c:out value='${code.defaultDisplayValue}'/>" id="<c:out value='${code.defaultDisplayValue}'/>" title="<spring:message code='clickToSelect'/>" value="<c:out value='${code.defaultDisplayValue}'/>" displayvalue="<c:out value='${code.longDescription}'/>" /> 
										<c:out value="${code.valueDescription}"/>
									</li>
								</c:forEach>
			                </ul>
			            </td>
					</tr>
				</tbody>
			</table>
	        <div class="additionalMultiOptionDiv">
	        	<div><label for="additionalCodesText"><spring:message code='enterMulti'/></label></div>
	        	<div>
					<textarea id="additionalCodesText" name="additionalCodesText" rows="5" cols="60"><c:forEach items="${requestScope.additionalCodes}" var="addCode"><c:out value="${addCode}"/></c:forEach></textarea>
	        	</div>
	        </div>
			<div class="buttonsDiv">
				<input type="button" value="<spring:message code='done'/>" class="saveButton" name="doneButton" id="doneButton" />
				<input type="button" value="<spring:message code='cancel'/>" class="button" name="cancelButton" id="cancelButton" />
			</div>
			<input type="hidden" name="modalTitleText" id="modalTitleText" value="<c:out value='${requestScope.modalTitle}'/>"/>
		</form>
	</div>
	<div class='modalSideRight'>&nbsp;</div>
</div>
<div class="modalBottomLeft">&nbsp;<div class="modalBottomRight">&nbsp;</div></div>