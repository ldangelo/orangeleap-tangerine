<%@ include file="/WEB-INF/jsp/include.jsp" %>
<li class="side">
  <label for="${fieldVO.fieldName}" id="${fieldVO.fieldName}.label" class="desc">
	<c:if test="${fieldVO.fieldType != 'SPACER'}">
		<c:if test="${fieldVO.helpAvailable == 'true'}"><a class="helpLink"><img src="images/icons/questionGreyTransparent.gif" /></a><span class="helpText"><c:out value="${fieldVO.helpText}" /></span></c:if>
	  	<c:out value="${fieldVO.labelText}" />
    	<c:if test="${fieldVO.required == 'true'}"><span class="required">&nbsp;*</span></c:if>
	</c:if>
  </label>
<c:choose>
<c:when test="${fieldVO.fieldType == 'DATE'}">
    <form:input path="${fieldVO.fieldName}" size="16" cssClass="text date" />
</c:when>
<c:when test="${fieldVO.fieldType == 'DATE_DISPLAY'}">
	<fmt:formatDate value="${fieldVO.fieldValue}" pattern="MM / dd / yyyy" var="formattedDate" />
	<input value="${formattedDate}" size="16" class="text" name="${fieldVO.fieldName}" id="${fieldVO.fieldName}" readonly="readonly" />
</c:when>
<c:when test="${fieldVO.fieldType == 'CC_EXPIRATION_DISPLAY'}">
	<fmt:formatDate value="${fieldVO.fieldValue}" pattern="MM / yyyy" var="formattedDate" />
	<input value="${formattedDate}" size="16" class="text" name="${fieldVO.fieldName}" id="${fieldVO.fieldName}" readonly="readonly" />
</c:when>
<c:when test="${fieldVO.fieldType == 'QUERIED_PICKLIST'}">
		<select name="${fieldVO.fieldName}" id="${fieldVO.fieldName}">
			<c:forEach var="opt" varStatus="status" items="${paymentSources}">
				<c:if test="${opt.type == 'ACH'}">
					<option value="${opt}">${opt.type} - ${opt.achAccountNumber}</option>
				</c:if>
				<c:if test="${opt.type == 'Credit Card'}">
					<option value="${opt}">${opt.creditCardType} &nbsp ${opt.creditCardNumberDisplay}</option>
				</c:if>
			</c:forEach>
			<option value="new" selected="selected">Create New...</option>
		</select>
</c:when>
<c:when test="${fieldVO.fieldType == 'CC_EXPIRATION'}">
		<select name="${fieldVO.fieldName}Month" id="${fieldVO.fieldName}Month" class="expMonth">
			<c:forEach var="opt" varStatus="status" items="${paymentSource.expirationMonthList}">
				<c:set var="expirationMonth" scope="request" value="${paymentSource.creditCardExpirationMonthText}" />
				<c:choose>
					<c:when test="${opt == expirationMonth}">
						<option month="{expirationMonth}" value="${opt}" selected="selected">${opt}</option>
					</c:when>
					<c:otherwise>
						<option month=${expirationMonth}" value="${opt}">${opt}</option>
					</c:otherwise>
				</c:choose>
			</c:forEach>
		</select>
		<select name="${fieldVO.fieldName}Year" id="${fieldVO.fieldName}Year" class="expYear">
			<c:forEach var="opt" varStatus="status" items="${paymentSource.expirationYearList}">
				<c:set var="expirationYear" scope="request" value="${paymentSource.creditCardExpirationYear }" />
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
	<input value="${fieldVO.fieldValue}" class="text code" lookup="${fieldVO.fieldName}" codeType="${fieldVO.fieldName}" name="${fieldVO.fieldName}" />
	<a tabindex="-1" style="margin:0;position:absolute;top:3px;right:4px" class="lookupLink" href="#" onclick="loadCodePopup($(this).prev('input'));return false;">Lookup</a>
</div>
</c:when>
<c:when test="${fieldVO.fieldType == 'CHECKBOX'}">
	<form:checkbox path="${fieldVO.fieldName}" cssClass="checkbox" value="${fieldVO.fieldValue}"/>
</c:when>
<c:when test="${fieldVO.fieldType == 'READ_ONLY_TEXT'}">
	<div id="${fieldVO.fieldName}" class="readOnlyField">${empty fieldVO.fieldValue?'&nbsp;':fieldVO.fieldValue}</div>
</c:when>
<c:when test="${fieldVO.fieldType == 'LOOKUP'}">
	<input value="${fieldVO.fieldValue}" size="16" class="text lookup" name="${fieldVO.fieldName}" id="${fieldVO.fieldName}" /><a class="lookupLink jqModal" href="#">Lookup</a>
</c:when>
<c:when test="${fieldVO.fieldType == 'DATE_TIME'}">
	<input value="${fieldVO.fieldValue}" size="16" class="text" name="${fieldVO.fieldName}" id="${fieldVO.fieldName}" />
</c:when>
<c:when test="${fieldVO.fieldType == 'ADDRESS'}">
	<input value="${fieldVO.fieldValue}" size="16" class="text" name="${fieldVO.fieldName}" id="${fieldVO.fieldName}" />
</c:when>
<c:when test="${fieldVO.fieldType == 'PHONE'}">
    <form:input path="${fieldVO.fieldName}" size="16" cssClass="text" />
</c:when>
<c:when test="${fieldVO.fieldType == 'LONG_TEXT'}">
	<textarea rows="2" cols="30" class="text" name="${fieldVO.fieldName}" id="${fieldVO.fieldName}"></textarea>
</c:when>
<c:when test="${fieldVO.fieldType == 'NUMBER'}">
	<input value="${fieldVO.fieldValue}" size="16" class="text" name="${fieldVO.fieldName}" id="${fieldVO.fieldName}" />
</c:when>
<c:when test="${fieldVO.fieldType == 'SPACER'}">
	&nbsp;
</c:when>
<c:when test="${fieldVO.fieldType == 'PICKLIST' or fieldVO.fieldType == 'PREFERRED_PHONE_TYPES'}">
	<select name="${fieldVO.fieldName}" id="${fieldVO.fieldName}">
       <c:forEach var="code" varStatus="status" items="${fieldVO.codes}">
           <c:choose>
               <c:when test="${fieldVO.fieldValue eq code}">
                   <c:set var="selected" value="selected" scope="page" />
                   <c:set var="reference" value="${fieldVO.referenceValues[status.index]}" scope="request" />
               </c:when>
               <c:otherwise>
                   <c:set var="selected" value="" scope="page"/>
               </c:otherwise>
           </c:choose>
		   <option reference="${fieldVO.referenceValues[status.index]}" value="${code}" ${selected}>
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