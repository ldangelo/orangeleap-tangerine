<%@ include file="/WEB-INF/jsp/include.jsp"%>
<div class="modalTopLeft">
	<div class="modalTopRight">
		<h4 class="dragHandle" id="modalTitle">
			<c:choose>
				<c:when test="${empty requestScope.modalTitle}"><spring:message code="lookup"/></c:when>
				<c:otherwise><c:out value="${requestScope.modalTitle}"/></c:otherwise>
			</c:choose>
		</h4>
		<a href="javascript:void(0)" class="jqmClose hideText"><spring:message code="close"/></a>
	</div>
</div>
<div class="modalContentWrapper">
	<div class="modalContent">
		<form method="POST" action="relationshipQueryLookup.htm" id="queryLookupForm">
	        <div class="modalSearch">
	        	<label for="searchText"><spring:message code="searchBy"/></label>
	        	<select name="searchOption" id="searchOption">
        			<c:if test='${requestScope.relationshipType eq "individual" or requestScope.relationshipType eq "both"}'>
						<option value="lastName"><spring:message code='lastName'/></option>
						<option value="firstName"><spring:message code='firstName'/></option>
					</c:if>
					<c:if test='${requestScope.relationshipType eq "organization" or requestScope.relationshipType eq "both"}'>
						<option value="organizationName"><spring:message code='organizationName'/></option>
					</c:if>
				</select>        	
				<input type="hidden" name="fieldDef" id="fieldDef" value="<c:out value='${requestScope.fieldDef}'/>" />
	        	<input type="text" value="" id="searchText" name="searchText"/>
	        	<input type="button" id="findButton" name="findButton" value="<spring:message code='find'/>" class="button" />
	        </div>
        	<div class="noDisplay" id="queryResultsDiv">			 
			</div>
			<div class="buttonsDiv">
				<input type="button" value="<spring:message code='done'/>" class="saveButton" name="doneButton" id="doneButton" />
				<input type="button" value="<spring:message code='cancel'/>" class="button" name="cancelButton" id="cancelButton" />
			</div>
		</form>
	</div>
	<div class='modalSideRight'>&nbsp;</div>
</div>
<div class="modalBottomLeft">&nbsp;<div class="modalBottomRight">&nbsp;</div></div>
