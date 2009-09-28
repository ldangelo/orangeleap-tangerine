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
		<form method="POST" action="queryLookup.htm" id="queryLookupForm">
            <div class="modalSearch">
                <label for="searchText"><spring:message code="searchBy"/></label>
                <select name="searchOption" id="searchOption">
                    <c:set var="allLabelText" value="" scope="page"/>
                    <c:forEach var="fldMap" items="${requestScope.fieldMap}" varStatus="status">
                        <option value="<c:out value='${fldMap.key}'/>"><c:out value='${fldMap.value}'/></option>
                        <c:set var="allLabelText" value="${allLabelText} ${fldMap.value}," scope="page"/>
                    </c:forEach>
                </select>
                <input type="hidden" name="fieldDef" id="fieldDef" value="<c:out value='${fieldDef}'/>" />
                <input type="text" value="" id="searchText" name="searchText"/>
                <input type="button" id="findButton" name="findButton" value="<spring:message code='find'/>" class="button" />
            </div>
            <div class="noDisplay" id="queryResultsDiv">
            </div>
            <c:if test="${showOtherField}">
                <div class="otherOptionDiv">
                    <label for="otherOptionText"><spring:message code='orEnter'/></label>
                    <c:set var="labelTextLen" value="${fn:length(allLabelText) - 1}" scope="page"/>
                    <c:set var="allLabelText" value='${fn:trim(fn:substring(allLabelText, 0, labelTextLen))}'/>
                    <input type="text" value="<c:out value='${allLabelText}'/>" defaultValue="<c:out value='${allLabelText}'/>" id="otherOptionText" name="otherOptionText" class="defaultText"/>
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
