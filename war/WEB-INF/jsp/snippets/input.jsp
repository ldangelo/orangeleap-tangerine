<%@ include file="/WEB-INF/jsp/include.jsp" %>
<li class="side">
<c:remove var="errorClass" scope="page" />
<c:if test="${commandObject!=null}">
<spring:hasBindErrors name="${commandObject}">
<c:forEach items="${errors.fieldErrors}" var="error">
<c:if test="${error.field==fieldVO.fieldName}"><c:set scope="page" var="errorClass" value="textError" /></c:if>
</c:forEach>
</spring:hasBindErrors>
</c:if>
  <label for="${fieldVO.fieldName}" id="${fieldVO.fieldName}.label" class="desc">
	<c:if test="${fieldVO.fieldType != 'SPACER'}">
		<c:if test="${fieldVO.helpAvailable == 'true'}"><a class="helpLink"><img src="images/icons/questionGreyTransparent.gif" /></a><span class="helpText"><c:out value="${fieldVO.helpText}" /></span></c:if>
	  	<c:out value="${fieldVO.labelText}" />
    	<c:if test="${fieldVO.required == 'true'}"><span class="required">&nbsp;*</span></c:if>
	</c:if>
  </label>
<c:choose>
<c:when test="${fieldVO.fieldType == 'DATE'}">
<div class="lookupWrapper">
    <form:input path="${fieldVO.fieldName}" size="16" cssClass="text date" cssErrorClass="textError date" />
</div>
</c:when>
<c:when test="${fieldVO.fieldType == 'DATE_DISPLAY'}">
	<fmt:formatDate value="${fieldVO.fieldValue}" pattern="MM / dd / yyyy" var="formattedDate" />
	<div id="${fieldVO.fieldName}" class="readOnlyField">${empty formattedDate?'&nbsp;':formattedDate}</div>
</c:when>
<c:when test="${fieldVO.fieldType == 'CC_EXPIRATION_DISPLAY'}">
	<fmt:formatDate value="${fieldVO.fieldValue}" pattern="MM / yyyy" var="formattedDate" />
	<div id="${fieldVO.fieldName}" class="readOnlyField">${empty formattedDate?'&nbsp;':formattedDate}</div>
</c:when>
<c:when test="${fieldVO.fieldType == 'PAYMENT_SOURCE_PICKLIST'}">
	<select name="${fieldVO.fieldName}" id="${fieldVO.fieldName}" class="picklist">
		<option value="new" reference="li:has(#paymentType)">Create New...</option>
		<c:forEach var="opt" varStatus="status" items="${paymentSources}">
			<c:if test="${opt.type == 'ACH'}">
				<c:choose>
					<c:when test="${opt.id == commitment.paymentSource.id}">
						<option value="${opt.id}" selected="selected" reference=".gift_editCreditCard, li:has(#selectedAddress), li:has(#selectedPhone)">${opt.type}&nbsp;${opt.achAccountNumberDisplay}</option>
					</c:when>
					<c:otherwise>
						<option value="${opt.id}" reference="li:has(#selectedAddress), li:has(#selectedPhone)">${opt.type}&nbsp;${opt.achAccountNumberDisplay}</option>
					</c:otherwise>
				</c:choose>
			</c:if>
			<c:if test="${opt.type == 'Credit Card'}">
				<c:choose>
					<c:when test="${opt.id == commitment.paymentSource.id}">
						<option value="${opt.id}" selected="selected" reference=".gift_editCreditCard, li:has(#selectedAddress), li:has(#selectedPhone)">${opt.creditCardType}&nbsp;${opt.creditCardNumberDisplay}&nbsp;Exp.&nbsp;${opt.creditCardExpirationMonth}/${opt.creditCardExpirationYear}</option>
					</c:when>
					<c:otherwise>
						<option value="${opt.id}" reference="li:has(#selectedAddress), li:has(#selectedPhone)">${opt.creditCardType}&nbsp;${opt.creditCardNumberDisplay}&nbsp;Exp.&nbsp;${opt.creditCardExpirationMonth}/${opt.creditCardExpirationYear}</option>
					</c:otherwise>
				</c:choose>
			</c:if>
		</c:forEach>
	</select>
</c:when>
<c:when test="${fieldVO.fieldType == 'ADDRESS_PICKLIST'}">
	<select name="${fieldVO.fieldName}" id="${fieldVO.fieldName}" class="picklist">
		<option value="new" reference="li:has(:input[name^='address'])">Create New...</option>
		<c:forEach var="opt" varStatus="status" items="${addresses}">
			<c:choose>
				<c:when test="${opt.id == commitment.address.id}">
					<option value="${opt.id}" selected="selected">${opt.shortDisplay}</option>
				</c:when>
				<c:otherwise>
					<option value="${opt.id}">${opt.shortDisplay}</option>
				</c:otherwise>
			</c:choose>
		</c:forEach>
	</select>
</c:when>
<c:when test="${fieldVO.fieldType == 'PHONE_PICKLIST'}">
	<select name="${fieldVO.fieldName}" id="${fieldVO.fieldName}" class="picklist">
		<option value="new" reference="li:has(:input[name^='phone'])">Create New...</option>
		<c:forEach var="opt" varStatus="status" items="${phones}">
			<c:choose>
				<c:when test="${opt.id == commitment.phone.id}">
					<option value="${opt.id}" selected="selected">${opt.number}</option>
				</c:when>
				<c:otherwise>
					<option value="${opt.id}">${opt.number}</option>
				</c:otherwise>
			</c:choose>
		</c:forEach>
	</select>
</c:when>
<c:when test="${fieldVO.fieldType == 'QUERY_LOOKUP'}">
	<c:choose>
		<c:when test="${!empty fieldVO.id}">
			<c:url value="/${fieldVO.entityName}.htm" var="entityLink" scope="page">
				<c:param name="id" value="${fieldVO.id}" />
			</c:url>
		</c:when>
		<c:otherwise>
			<c:set value="#" var="entityLink" scope="page" />
		</c:otherwise>
	</c:choose>
<div class="lookupWrapper">
	<div style="float:left;" class="text lookupField" fieldDef="${sectionField.fieldDefinition.id}"><a target="_blank" href="${entityLink}">${fieldVO.displayValue}</a>&nbsp;</div>
	<a tabindex="-1" style="margin:0;position:absolute;top:3px;right:-7px" class="lookupLink" href="#" onclick="loadQueryLookup($(this).prev('div'));return false;">Lookup</a>
	<input type="hidden" name="${fieldVO.fieldName}" value="${fieldVO.id}" />
</div>
<c:remove var="entityLink" scope="page" />
</c:when>
<c:when test="${fieldVO.fieldType == 'CC_EXPIRATION'}">
	<select name="${fieldVO.fieldName}Month" id="${fieldVO.fieldName}Month" class="expMonth">
		<c:forEach var="opt" varStatus="status" items="${paymentSource.expirationMonthList}">
			<c:set var="expirationMonth" scope="request" value="${paymentSource.creditCardExpirationMonthText}" />
			<c:choose>
				<c:when test="${opt == expirationMonth}">
					<option value="${opt}" selected="selected">${opt}</option>
				</c:when>
				<c:otherwise>
					<option value="${opt}">${opt}</option>
				</c:otherwise>
			</c:choose>
		</c:forEach>
	</select>
	<select name="${fieldVO.fieldName}Year" id="${fieldVO.fieldName}Year" class="expYear">
		<c:forEach var="opt" varStatus="status" items="${paymentSource.expirationYearList}">
			<c:set var="expirationYear" scope="request" value="${paymentSource.creditCardExpirationYear}" />
			<c:choose>
				<c:when test="${opt == expirationYear}">
					<option value="${opt}" selected="selected">${opt}</option>
				</c:when>
				<c:otherwise>
					<option value="${opt}">${opt}</option>
				</c:otherwise>
			</c:choose>
		</c:forEach>
	</select>
</c:when>
<c:when test="${fieldVO.fieldType == 'TEXT'}">
    <form:input path="${fieldVO.fieldName}" size="16" cssClass="text" cssErrorClass="textError" />
</c:when>
<c:when test="${fieldVO.fieldType == 'CODE'}">
<div class="lookupWrapper">
	<input value="${fieldVO.fieldValue}" class="text code ${errorClass}" lookup="${fieldVO.fieldName}" codeType="${fieldVO.fieldName}" name="${fieldVO.fieldName}" />
	<a tabindex="-1" style="margin:0;position:absolute;top:3px;right:-7px" class="lookupLink" href="#" onclick="loadCodePopup($(this).prev('input'));return false;">Lookup</a>
</div>
</c:when>
<c:when test="${fieldVO.fieldType == 'CHECKBOX'}">
	<c:choose>
		<c:when test="${fieldVO.fieldValue}">
			<input type="checkbox" value="true" class="checkbox" name="${fieldVO.fieldName}" id="${fieldVO.fieldName}" checked="checked" />
		</c:when>
		<c:otherwise>
			<input type="checkbox" value="true" class="checkbox" name="${fieldVO.fieldName}" id="${fieldVO.fieldName}" />
		</c:otherwise>
	</c:choose>
	<!-- 	<form:checkbox path="${fieldVO.fieldName}" cssClass="checkbox" value="${fieldVO.fieldValue}"/> ${fieldVO.fieldValue?checked:''}-->
</c:when>
<c:when test="${fieldVO.fieldType == 'READ_ONLY_TEXT' or fieldVO.fieldType == 'PICKLIST_DISPLAY'}">
	<div id="${fieldVO.fieldName}" class="readOnlyField">${empty fieldVO.displayValue?'&nbsp;':fieldVO.displayValue}</div>
</c:when>
<c:when test="${fieldVO.fieldType == 'LOOKUP'}">
	<input value="${fieldVO.fieldValue}" size="16" class="text lookup" name="${fieldVO.fieldName}" id="${fieldVO.fieldName}" /><a class="lookupLink jqModal" href="#">Lookup</a>
</c:when>
<c:when test="${fieldVO.fieldType == 'DATE_TIME'}">
	<input value="${fieldVO.fieldValue}" size="16" class="text" name="${fieldVO.fieldName}" id="${fieldVO.fieldName}" />
</c:when>
<c:when test="${fieldVO.fieldType == 'ADDRESS'}">
	<input value="${fieldVO.fieldValue}" class="text ${errorClass}" name="${fieldVO.fieldName}" />
</c:when>
<c:when test="${fieldVO.fieldType == 'PHONE'}">
	<input value="${fieldVO.fieldValue}" class="text ${errorClass}" name="${fieldVO.fieldName}" />
</c:when>
<c:when test="${fieldVO.fieldType == 'LONG_TEXT'}">
	<textarea rows="2" cols="30" class="text ${errorClass}" name="${fieldVO.fieldName}" id="${fieldVO.fieldName}"></textarea>
</c:when>
<c:when test="${fieldVO.fieldType == 'NUMBER'}">
    <form:input path="${fieldVO.fieldName}" size="16" cssClass="text" cssErrorClass="textError" />
</c:when>
<c:when test="${fieldVO.fieldType == 'SPACER'}">
	&nbsp;
</c:when>
<c:when test="${fieldVO.fieldType == 'PICKLIST' or fieldVO.fieldType == 'PREFERRED_PHONE_TYPES'}">
	<select name="${fieldVO.fieldName}" <c:if test="${fieldVO.cascading}">class="picklist"</c:if>id="${fieldVO.fieldName}">
       <c:forEach var="code" varStatus="status" items="${fieldVO.codes}">
           <c:set var="reference" value="${fieldVO.referenceValues[status.index]}" scope="request" />
           <c:choose>
               <c:when test="${fieldVO.fieldValue eq code}">
                   <c:set var="selected" value="selected" scope="page" />
               </c:when>
               <c:otherwise>
                   <c:set var="selected" value="" scope="page"/>
               </c:otherwise>
           </c:choose>
		   <option <c:if test="${!empty reference}">reference="${fieldVO.referenceValues[status.index]}"</c:if>value="${code}" ${selected}>
			${fieldVO.displayValues[status.index]}
		   </option>
      </c:forEach>
	</select>
</c:when>
<c:otherwise>
	<c:out value="Field type ${fieldVO.fieldType} not yet implemented." />
</c:otherwise>
</c:choose>
</li>