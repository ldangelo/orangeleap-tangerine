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
	        	<select name="searchOption" id="searchOption">
        			<c:if test='${requestScope.relationshipType eq "individual" or requestScope.relationshipType eq "both"}'>
						<option value='individual'><spring:message code='acctIndividual'/></option>
					</c:if>
					<c:if test='${requestScope.relationshipType eq "organization" or requestScope.relationshipType eq "both"}'>
						<option value='organization'><spring:message code='acctOrganization'/></option>
					</c:if>
					<option value='fullText'><spring:message code='textFields'/></option>
				</select>
				<input type="hidden" name="fieldDef" id="fieldDef" value="<c:out value='${requestScope.fieldDef}'/>" />
				<input type="text" id="accountNumber" name="accountNumber" size="12" value="<spring:message code='accountNumber'/>" defaultValue="<spring:message code='accountNumber'/>" class="defaultText entry number"/>
				<c:if test='${requestScope.relationshipType eq "individual" or requestScope.relationshipType eq "both"}'>
					<input type="text" id="lastName" name="lastName" size="12" value="<spring:message code='lastName'/>" defaultValue="<spring:message code='lastName'/>" class="defaultText noDisplay entry individualEntry"/>
					<input type="text" id="firstName" name="firstName" size="12" value="<spring:message code='firstName'/>" defaultValue="<spring:message code='firstName'/>" class="defaultText noDisplay entry individualEntry"/>
				</c:if>
				<c:if test='${requestScope.relationshipType eq "organization" or requestScope.relationshipType eq "both"}'>
					 <input type="text" id="organizationName" name="organizationName" size="29" value="<spring:message code='organizationName'/>" defaultValue="<spring:message code='organizationName'/>" class="defaultText noDisplay entry organizationEntry"/>
				</c:if>
				<input type="text" id="fullText" name="fullText" size="46" value="<spring:message code='enterValue'/>" defaultValue="<spring:message code='enterValue'/>" class="defaultText noDisplay"/>
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

<script type="text/javascript">
	$('div.modalContent form #searchOption').change(function() {
		var thisVal = $(this).val();
		if (thisVal == 'fullText') {
			$('div.modalContent form input.entry').addClass('noDisplay');
			$('div.modalContent form #fullText').removeClass('noDisplay');
		}
		else {
			$('div.modalContent form #fullText').addClass('noDisplay');
			$('div.modalContent form input#accountNumber').removeClass('noDisplay');
			if (thisVal == 'individual') {
				$('div.modalContent form input.organizationEntry').addClass('noDisplay');
				$('div.modalContent form input.individualEntry').removeClass('noDisplay');
			}
			else {
				$('div.modalContent form input.individualEntry').addClass('noDisplay');
				$('div.modalContent form input.organizationEntry').removeClass('noDisplay');
			}
		}
	});
	$('div.modalContent form #searchOption').change();
</script>

