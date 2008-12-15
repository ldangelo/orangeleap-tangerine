<%@ include file="/WEB-INF/jsp/include.jsp"%>
<c:if test="${param.view != 'resultsOnly'}">
	<form method="POST" action="multiQueryLookup.htm">
</c:if>
<mp:page pageName='queryLookup' />
<c:forEach var="sectionDefinition" items="${sectionDefinitions}">
	<c:if test="${sectionDefinition.sectionName eq queryLookup.sectionName}">
		<c:if test="${param.view != 'resultsOnly'}">
	        <div class="modalSearch">
	        	<label for="searchText">Search For</label>
				<input type="hidden" name="fieldDef" id="fieldDef" value="<c:out value='${param.fieldDef}'/>" />
	        	<input type="text" value="" id="searchText" name="searchText"/>
	        	<select name="searchOption" id="searchOption">
					<c:forEach items="${pagedListHolder.pageList}" var="row" begin="0" end="0">
						<mp:section sectionDefinition="${sectionDefinition}"/>
						
						<c:forEach var="sectionField" items="${sectionFieldList}" varStatus="status">
							<mp:field sectionField='${sectionField}' sectionFieldList='${sectionFieldList}' model="${row}" />
							<option value="<c:out value='${fieldVO.fieldName}'/>"><c:out value='${fieldVO.labelText}'/></option>
						</c:forEach>	
					</c:forEach>
				</select>        	
	        	<input type="button" id="findButton" name="findButton" value="Find" class="saveButton" />
	        </div>
	        <table cellspacing="0" class="multiSelect">
	            <thead>
	                <tr>
	                    <th><input type="checkbox" alt="Select/Unselect All" title="Select/Unselect All" selection="available" id="availableAll"/><strong>Available</strong></th>
	                    <th class="arrows">&nbsp;</th>
	                    <th><input type="checkbox" alt="Select/Unselect All" title="Select/Unselect All" selection="selected" id="selectedAll"/><strong>Selected</strong></th>
	                </tr>
	            </thead>
	            <tbody>
		            <tr>
		                <td>
		                    <ul id="availableOptions">			 
		</c:if>

<%--
		<c:if test="${empty pagedListHolder.pageList}">
			<div><span class="noResults">No results were found for your query.</span></div>
		</c:if>
--%>
		<c:set var="counter" value="0"/>
		<c:forEach items="${pagedListHolder.pageList}" var="row">
			<c:set var="counter" value="${counter + 1}"/>
			<c:set target="${requestScope.selectedIds}" property="idToCheck" value="${row.id}"/>
			<c:if test="${requestScope.selectedIds.checkSelectedId == false}">
				<c:choose>
					<c:when test="${!empty row.id && !empty row.entityName}">
						<c:url value="/${row.entityName}.htm" var="entityLink" scope="page">
							<c:param name="id" value="${row.id}" />
						</c:url>
					</c:when>
					<c:otherwise>
						<c:set value="javascript:void(0)" var="entityLink" scope="page" />
					</c:otherwise>
				</c:choose>
				<ol>
					<input type="checkbox" name="option${counter}" id="${row.id}"></input>
					<a href="<c:out value='${entityLink}'/>" target="_blank">
						<%@ include file="/WEB-INF/jsp/snippets/unformattedSectionFields.jsp" %>
					</a>
				</ol>
				<c:remove var="entityLink" scope="page" />
			</c:if>
		</c:forEach>
		
		
		<c:if test="${param.view!='resultsOnly'}">
		                    </ul>
		                </td>
		                <td class="arrows">
		                	<a href="javascript:void(0)" class="hideText rightArrow">Right</a>
		                	<a href="javascript:void(0)" class="hideText leftArrow">Left</a>
		                </td>
		                <td>
		                    <ul id="selectedOptions">
		                    	<c:forEach items="${pagedListHolder.pageList}" var="row">
									<c:set var="counter" value="${counter + 1}"/>
									<c:set target="${requestScope.selectedIds}" property="idToCheck" value="${row.id}"/>
									<c:if test="${requestScope.selectedIds.checkSelectedId == true}">
										<c:choose>
											<c:when test="${!empty row.id && !empty row.entityName}">
												<c:url value="/${row.entityName}.htm" var="entityLink" scope="page">
													<c:param name="id" value="${row.id}" />
												</c:url>
											</c:when>
											<c:otherwise>
												<c:set value="javascript:void(0)" var="entityLink" scope="page" />
											</c:otherwise>
										</c:choose>
										<ol>
											<input type="checkbox" name="option${counter}" id="${row.id}"></input>
											<a href="<c:out value='${entityLink}'/>" target="_blank">
												<%@ include file="/WEB-INF/jsp/snippets/unformattedSectionFields.jsp" %>
											</a>
										</ol>
										<c:remove var="entityLink" scope="page" />
									</c:if>
								</c:forEach>
		                    </ul>
		                </td>
		            </tr>
	            </tbody>
	        </table>
		</c:if>
	</c:if>
</c:forEach>
<c:if test="${param.view!='resultsOnly'}">
		<div class="buttonsDiv">
			<input type="button" value="Done" class="saveButton" name="doneButton" id="doneButton" />
			<input type="button" value="Cancel" class="saveButton" name="cancelButton" id="cancelButton" />
		</div>
	</form>
</c:if>
