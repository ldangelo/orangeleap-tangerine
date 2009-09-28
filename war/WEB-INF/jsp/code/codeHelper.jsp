<%@ include file="/WEB-INF/jsp/include.jsp"%>
<div class="modalTopLeft">
	<div class="modalTopRight">
		<h4 class="dragHandle" id="modalTitle">
			<c:choose>
				<c:when test="${empty requestScope.modalTitle}"><spring:message code='lookup'/></c:when>
				<c:otherwise><c:out value="${requestScope.modalTitle}"/></c:otherwise>
			</c:choose>
		</h4>
		<a href="javascript:void(0)" class="jqmClose hideText"><spring:message code='close'/></a>
	</div>
</div>
<div class="modalContentWrapper">
	<div class="modalContent">
		<form method="POST" action="codeHelper.htm" id="codeHelperLookup" class="codeForm">
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
			<div id="queryResultsDiv">
               	<jsp:include page="codeHelperResults.jsp"/>		 
			</div>
	        <c:if test="${param.showOtherField}">
		        <div class="otherOptionDiv">
		        	<label for="otherOptionText"><spring:message code='orEnter'/></label>
		        	<input type="text" value="<spring:message code="description"/>" defaultValue="<spring:message code="description"/>" id="otherOptionText" name="otherOptionText" class="defaultText"/>
		        </div>
	        </c:if>
			<div class="buttonsDiv">
				<input type="button" value="<spring:message code='done'/>" class="saveButton" name="doneButton" id="doneButton" />
				<input type="button" value="<spring:message code='cancel'/>" class="button" name="cancelButton" id="cancelButton" />
			</div>
		</form>
	</div>
	<div class='modalSideRight'>&nbsp;</div>
</div>
<div class="modalBottomLeft">&nbsp;<div class="modalBottomRight">&nbsp;</div></div>
