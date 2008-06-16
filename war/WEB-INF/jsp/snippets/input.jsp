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
<c:when test="${fieldVO.fieldType == 'TEXT'}">
    <form:input path="${fieldVO.fieldName}" size="16" cssClass="text" />
</c:when>
<c:when test="${fieldVO.fieldType == 'READ_ONLY_TEXT'}">
	  <input value="${fieldVO.fieldValue}" size="16" class="text" name="${fieldVO.fieldName}" id="${fieldVO.fieldName}" readonly="readonly"/>
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
	<select name="${fieldVO.fieldName}">
       <c:forEach var="code" varStatus="status" items="${fieldVO.codes}">
           <c:choose>
               <c:when test="${fieldVO.fieldValue eq code}">
                   <c:set var="selected" value="selected" scope="page"/>
               </c:when>
               <c:otherwise>
                   <c:set var="selected" value="" scope="page"/>
               </c:otherwise>
           </c:choose>
		   <option value="${code}" ${selected}>
			   ${fieldVO.displayValues[status.index]}
		   </option>
      </c:forEach>
	</select>
</c:when>
<c:otherwise>
	<c:out value="Field type ${fieldVO.fieldType} not yet implemented." />
</c:otherwise>
</c:choose>
	<form:errors path="${fieldVO.fieldName}" cssClass="error"/>
</li>