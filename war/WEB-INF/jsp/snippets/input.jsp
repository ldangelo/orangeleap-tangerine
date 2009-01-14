<%@ include file="/WEB-INF/jsp/include.jsp" %>
<li class="side">
	<c:remove var="errorClass" scope="page" />
	<c:if test="${commandObject != null}">
		<spring:hasBindErrors name="${commandObject}">
			<c:forEach items="${errors.fieldErrors}" var="error">
				<c:if test="${error.field == fieldVO.fieldName}"><c:set scope="page" var="errorClass" value="textError" /></c:if>
			</c:forEach>
		</spring:hasBindErrors>
	</c:if>
	<label for="<c:out value='${fieldVO.fieldName}'/>" class="desc">
		<c:if test="${fieldVO.fieldType != 'SPACER'}">
			<c:if test="${fieldVO.helpAvailable == 'true'}"><a class="helpLink"><img src="images/icons/questionGreyTransparent.gif" /></a><span class="helpText"><c:out value="${fieldVO.helpText}" /></span></c:if>
	    	<c:if test="${fieldVO.required == 'true'}"><span class="required">*</span>&nbsp;</c:if>
		  	<c:out value="${fieldVO.labelText}" />
		</c:if>
	</label>
	<c:choose>
		<c:when test="${fieldVO.fieldType == 'DATE'}">
			<div class="lookupWrapper">
			    <form:input path="${fieldVO.fieldName}" size="16" cssClass="text date ${fieldVO.entityAttributes}" cssErrorClass="textError date" />
			</div>
		</c:when>
		<c:when test="${fieldVO.fieldType == 'DATE_DISPLAY'}">
			<fmt:formatDate value="${fieldVO.fieldValue}" pattern="MM / dd / yyyy" var="formattedDate" />
			<div id="<c:out value='${fieldVO.fieldName}'/>" class="readOnlyField <c:out value='${fieldVO.entityAttributes}'/>"><c:choose><c:when test="${empty formattedDate}">&nbsp;</c:when><c:otherwise><c:out value='${formattedDate}'/></c:otherwise></c:choose></div>
		</c:when>
		<c:when test="${fieldVO.fieldType == 'CC_EXPIRATION_DISPLAY'}">
			<fmt:formatDate value="${fieldVO.fieldValue}" pattern="MM / yyyy" var="formattedDate" />
			<div id="<c:out value='${fieldVO.fieldName}'/>" class="readOnlyField <c:out value='${fieldVO.entityAttributes}'/>"><c:choose><c:when test="${empty formattedDate}">&nbsp;</c:when><c:otherwise><c:out value='${formattedDate}'/></c:otherwise></c:choose></div>
		</c:when>
		<c:when test="${fieldVO.fieldType == 'PAYMENT_SOURCE_PICKLIST'}">
			<select name="<c:out value='${fieldVO.fieldName}'/>" id="<c:out value='${fieldVO.fieldName}'/>" class="picklist <c:out value='${fieldVO.entityAttributes}'/>">
				<c:if test="${fieldVO.required != 'true'}">
					<option value=""><spring:message code="none"/></option>
				</c:if>
				<option value="new" reference="li:has(#paymentType)" <c:if test='${fieldVO.model.paymentSource.userCreated}'>selected="selected"</c:if>><spring:message code="createNew"/></option>
				<c:if test="${not empty paymentSources}">
					<optgroup label="<spring:message code='orChoose'/>">
				</c:if>
				<c:forEach var="opt" varStatus="status" items="${paymentSources}">
					<option value="${opt.id}" <c:if test='${opt.id == fieldVO.model.paymentSource.id}'>selected="selected"</c:if> reference="li:has(#selectedAddress), li:has(#selectedPhone)"><c:out value='${opt.profile}'/></option>
				</c:forEach>
				<c:if test="${not empty paymentSources}">
					</optgroup>
				</c:if>
			</select>
		</c:when>
		<c:when test="${fieldVO.fieldType == 'ADDRESS_PICKLIST'}">
			<select name="<c:out value='${fieldVO.fieldName}'/>" id="<c:out value='${fieldVO.fieldName}'/>" class="picklist <c:out value='${fieldVO.entityAttributes}'/>">
				<c:if test="${fieldVO.required != 'true'}">
					<option value=""><spring:message code="none"/></option>
				</c:if>
				<option value="new" reference="li:has(:input[name^='address'])" <c:if test='${fieldVO.model.address.userCreated}'>selected="selected"</c:if>><spring:message code="createNew"/></option>
				<c:if test="${not empty addresses}">
					<optgroup label="<spring:message code='orChoose'/>">
				</c:if>
				<c:forEach var="opt" varStatus="status" items="${addresses}">
					<option value="${opt.id}" <c:if test='${opt.id == fieldVO.model.address.id}'>selected="selected"</c:if>><c:out value='${opt.shortDisplay}'/></option>
				</c:forEach>
				<c:if test="${not empty addresses}">
					</optgroup>
				</c:if>
			</select>
		</c:when>
		<c:when test="${fieldVO.fieldType == 'PHONE_PICKLIST'}">
			<select name="<c:out value='${fieldVO.fieldName}'/>" id="<c:out value='${fieldVO.fieldName}'/>" class="picklist <c:out value='${fieldVO.entityAttributes}'/>">
				<c:if test="${fieldVO.required != 'true'}">
					<option value=""><spring:message code="none"/></option>
				</c:if>
				<option value="new" reference="li:has(:input[name^='phone'])" <c:if test='${fieldVO.model.phone.userCreated}'>selected="selected"</c:if>><spring:message code="createNew"/></option>
				<c:if test="${not empty phones}">
					<optgroup label="<spring:message code='orChoose'/>">
				</c:if>
				<c:forEach var="opt" varStatus="status" items="${phones}">
					<option value="${opt.id}" <c:if test='${opt.id == fieldVO.model.phone.id}'>selected="selected"</c:if>><c:out value='${opt.number}'/></option>
				</c:forEach>
				<c:if test="${not empty phones}">
					</optgroup>
				</c:if>
			</select>
		</c:when>
		<c:when test="${fieldVO.fieldType == 'EMAIL_PICKLIST'}">
			<select name="<c:out value='${fieldVO.fieldName}'/>" id="<c:out value='${fieldVO.fieldName}'/>" class="picklist <c:out value='${fieldVO.entityAttributes}'/>">
				<c:if test="${fieldVO.required != 'true'}">
					<option value=""><spring:message code="none"/></option>
				</c:if>
				<option value="new" reference="li:has(:input[name^='email'])" <c:if test='${fieldVO.model.email.userCreated}'>selected="selected"</c:if>><spring:message code="createNew"/></option>
				<c:if test="${not empty emails}">
					<optgroup label="<spring:message code='orChoose'/>">
				</c:if>
				<c:forEach var="opt" varStatus="status" items="${emails}">
					<option value="${opt.id}" <c:if test='${opt.id == fieldVO.model.email.id}'>selected="selected"</c:if>><c:out value='${opt.emailAddress}'/></option>
				</c:forEach>
				<c:if test="${not empty emails}">
					</optgroup>
				</c:if>
			</select>
		</c:when>
		<c:when test="${fieldVO.fieldType == 'PICKLIST'}">
			<select name="<c:out value='${fieldVO.fieldName}'/>" class="<c:if test="${fieldVO.cascading}">picklist </c:if><c:out value='${fieldVO.entityAttributes}'/>" id="<c:out value='${fieldVO.fieldName}'/>">
				<c:if test="${fieldVO.required != 'true'}">
					<option value=""><spring:message code="none"/></option>
				</c:if>
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
					<option <c:if test="${!empty reference}">reference="<c:out value='${fieldVO.referenceValues[status.index]}'/>"</c:if>value="<c:out value='${code}'/>" <c:out value='${selected}'/>>
						<c:out value='${fieldVO.displayValues[status.index]}'/>
					</option>
				</c:forEach>
			</select>
		</c:when>
		<c:when test="${fieldVO.fieldType == 'PREFERRED_PHONE_TYPES'}">
			<select name="<c:out value='${fieldVO.fieldName}'/>" class="<c:if test="${fieldVO.cascading}">picklist </c:if><c:out value='${fieldVO.entityAttributes}'/>" id="<c:out value='${fieldVO.fieldName}'/>">
				<c:if test="${fieldVO.required != 'true'}">
					<option value=""><spring:message code="none"/></option>
				</c:if>
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
					<option <c:if test="${!empty reference}">reference="<c:out value='${fieldVO.referenceValues[status.index]}'/>"</c:if>value="<c:out value='${code}'/>" <c:out value='${selected}'/>>
						<c:out value='${fieldVO.displayValues[status.index]}'/>
					</option>
				</c:forEach>
			</select>
		</c:when>
		<c:when test="${fieldVO.fieldType == 'MULTI_PICKLIST'}">
			<%-- TODO: move to tag library --%>
			<div class="lookupWrapper">
			    <div class="multiPicklist multiLookupField <c:out value='${fieldVO.entityAttributes}'/>">
					<c:forEach var="code" varStatus="status" items="${fieldVO.codes}">
						<c:set target="${fieldVO}" property="fieldToCheck" value="${code}"/>
						<div class='multiPicklistOption' style='<c:if test="${fieldVO.hasField == false}">display:none</c:if>' 
							id="option-<c:out value='${code}'/>" selectedId="<c:out value='${code}'/>" reference="<c:out value='${fieldVO.referenceValues[status.index]}'/>">
							<c:out value='${fieldVO.displayValues[status.index]}'/>
							<a href="javascript:void(0)" onclick="Lookup.deleteOption(this)" class="deleteOption noDisplay"><img src="images/icons/deleteRow.png" alt="Remove this option" title="Remove this option"/></a>
						</div>
					</c:forEach>
			        &nbsp;
			    	<input type='hidden' name='labelText' id='<c:out value="${fieldVO.fieldName}"/>-labelText' value="<c:out value='${fieldVO.labelText}'/>"/>
			        <a href="javascript:void(0)" onclick="Lookup.loadMultiPicklist(this)" class="hideText">Lookup</a>
			    </div>
			    <%-- The following hidden field must not lie within the multiPicklist div above --%>
				<input type="hidden" name="<c:out value='${fieldVO.fieldName}'/>" id="<c:out value='${fieldVO.fieldName}'/>" value="<c:out value='${fieldVO.fieldValuesString}'/>" />
			</div>
		</c:when>
		<c:when test="${fieldVO.fieldType == 'QUERY_LOOKUP'}">
			<div class="lookupWrapper">
			    <div class="lookupField <c:out value='${fieldVO.entityAttributes}'/>">
					<c:if test="${!empty fieldVO.id}">
						<c:url value="/person.htm" var="entityLink" scope="page">  <%-- ${fieldVO.entityName} hard-coded to person; TODO: change --%>
							<c:param name="personId" value="${fieldVO.id}" />
						</c:url>
						<c:set var="thisVal" value="${fn:trim(fieldVO.displayValue)}"/>
						<div id="lookup-<c:out value='${thisVal}'/>" class="queryLookupOption" selectedId="<c:out value='${fieldVO.id}'/>">
							<a href="<c:out value='${entityLink}'/>" target="_blank"><c:out value='${thisVal}'/></a>
							<a href="javascript:void(0)" onclick="Lookup.deleteOption(this)" class="deleteOption noDisplay"><img src="images/icons/deleteRow.png" alt="Remove this option" title="Remove this option"/></a>
						</div>
						<c:remove var="entityLink" scope="page" />
					</c:if>
			        &nbsp;
			        <a href="javascript:void(0)" onclick="Lookup.loadQueryLookup(this)" fieldDef="<c:out value='${sectionField.fieldDefinition.id}'/>" class="hideText">Lookup</a>
			    </div>
				<input type="hidden" name="<c:out value='${fieldVO.fieldName}'/>" value="<c:out value='${fieldVO.id}'/>" id="<c:out value='${fieldVO.fieldName}'/>" />		

				<div class="queryLookupOption noDisplay clone">
					<a href="" target="_blank"></a>
					<a href="javascript:void(0)" onclick="Lookup.deleteOption(this)" class="deleteOption noDisplay"><img src="images/icons/deleteRow.png" alt="Remove this option" title="Remove this option"/></a>
				</div>
			</div>
		</c:when>
		<c:when test="${fieldVO.fieldType == 'MULTI_QUERY_LOOKUP'}">
			<%-- TODO: move to tag library --%>
			<div class="lookupWrapper">
			    <div class="multiLookupField <c:out value='${fieldVO.entityAttributes}'/>">
					<c:forEach var="val" varStatus="status" items="${fieldVO.displayValues}">
						<c:choose>
							<c:when test="${not empty fieldVO.ids[status.index]}">
								<c:url value="/person.htm" var="entityLink" scope="page"> <%-- ${fieldVO.entityName} hard-coded to person; TODO: change --%>
									<c:param name="personId" value="${fieldVO.ids[status.index]}" />
								</c:url>
							</c:when>
							<c:otherwise>
								<c:set value="javascript:void(0)" var="entityLink" scope="page" />
							</c:otherwise>
						</c:choose>
						<c:set var="thisVal" value="${fn:trim(val)}"/>
						<div id="lookup-<c:out value='${thisVal}'/>" class="multiQueryLookupOption" selectedId="<c:out value='${fieldVO.ids[status.index]}'/>">
							<a href="<c:out value='${entityLink}'/>" target="_blank"><c:out value='${thisVal}'/></a>
							<a href="javascript:void(0)" onclick="Lookup.deleteOption(this)" class="deleteOption noDisplay"><img src="images/icons/deleteRow.png" alt="Remove this option" title="Remove this option"/></a>
						</div>
						<c:remove var="entityLink" scope="page" />
					</c:forEach>
			        &nbsp;
			        <a href="javascript:void(0)" onclick="Lookup.loadMultiQueryLookup(this)" fieldDef="<c:out value='${sectionField.fieldDefinition.id}'/>" class="hideText">Lookup</a>
			    </div>
				<input type="hidden" name="<c:out value='${fieldVO.fieldName}'/>" value="<c:out value='${fieldVO.idsString}'/>" id="<c:out value='${fieldVO.fieldName}'/>" />
				
				<div class="multiQueryLookupOption noDisplay clone" selectedId="">
					<a href="" target="_blank"></a>
					<a href="javascript:void(0)" onclick="Lookup.deleteOption(this)" class="deleteOption noDisplay"><img src="images/icons/deleteRow.png" alt="Remove this option" title="Remove this option"/></a>
				</div>		
			</div>
		</c:when>
		<c:when test="${fieldVO.fieldType == 'CC_EXPIRATION'}">
			<select name="<c:out value='${fieldVO.fieldName}'/>Month" id="<c:out value='${fieldVO.fieldName}'/>Month" class="expMonth <c:out value='${fieldVO.entityAttributes}'/>">
				<c:forEach var="opt" varStatus="status" items="${paymentSource.expirationMonthList}">
					<c:set var="expirationMonth" scope="request" value="${paymentSource.creditCardExpirationMonthText}" />
					<c:choose>
						<c:when test="${opt == expirationMonth}">
							<option value="<c:out value='${opt}'/>" selected="selected"><c:out value='${opt}'/></option>
						</c:when>
						<c:otherwise>
							<option value="<c:out value='${opt}'/>"><c:out value='${opt}'/></option>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</select>
			<select name="<c:out value='${fieldVO.fieldName}'/>Year" id="<c:out value='${fieldVO.fieldName}'/>Year" class="expYear <c:out value='${fieldVO.entityAttributes}'/>">
				<c:forEach var="opt" varStatus="status" items="${paymentSource.expirationYearList}">
					<c:set var="expirationYear" scope="request" value="${paymentSource.creditCardExpirationYear}" />
					<c:choose>
						<c:when test="${opt == expirationYear}">
							<option value="<c:out value='${opt}'/>" selected="selected"><c:out value='${opt}'/></option>
						</c:when>
						<c:otherwise>
							<option value="<c:out value='${opt}'/>"><c:out value='${opt}'/></option>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</select>
		</c:when>
		<c:when test="${fieldVO.fieldType == 'DUAL_PCT_AMT'}">
			<input value="<c:out value='${fieldVO.fieldValue}'/>" class="text <c:out value='${fieldVO.entityAttributes}'/> <c:out value='${errorClass}'/>" name="<c:out value='${fieldVO.fieldName}'/>" id="<c:out value='${fieldVO.fieldName}'/>" />
		</c:when>
		<c:when test="${fieldVO.fieldType == 'TEXT'}">
			<input value="<c:out value='${fieldVO.fieldValue}'/>" class="text <c:out value='${fieldVO.entityAttributes}'/> <c:out value='${errorClass}'/>" name="<c:out value='${fieldVO.fieldName}'/>" id="<c:out value='${fieldVO.fieldName}'/>" />
		</c:when>
		<c:when test="${fieldVO.fieldType == 'CODE'}">
		<div class="lookupWrapper">
			<input value="<c:out value='${fieldVO.fieldValue}'/>" class="text code <c:out value='${fieldVO.entityAttributes}'/> <c:out value='${errorClass}'/>" lookup="<c:out value='${fieldVO.fieldName}'/>" 
				codeType="<c:out value='${fieldVO.fieldName}'/>" name="<c:out value='${fieldVO.fieldName}'/>" id="<c:out value='${fieldVO.fieldName}'/>" />
			<a style="margin:0;position:absolute;top:3px;right:-7px" class="lookupLink" href="javascript:void(0)" onclick="Lookup.loadCodePopup($(this).prev('input'))">Lookup</a>
		</div>
		</c:when>
		<c:when test="${fieldVO.fieldType == 'CHECKBOX'}">
            <input type="hidden" name="_<c:out value="${fieldVO.fieldName}"/>" value="visible" />
            <input type="checkbox" value="true" 
                   class="checkbox <c:out value='${fieldVO.entityAttributes}'/>" 
                   name="<c:out value='${fieldVO.fieldName}'/>" 
                   id="<c:out value='${fieldVO.fieldName}'/>"  
                   <c:if test="${fieldVO.fieldValue}">checked</c:if> 
            />
		</c:when>
		<c:when test="${fieldVO.fieldType == 'READ_ONLY_TEXT'}">
			<div id="<c:out value='${fieldVO.fieldName}'/>" class="readOnlyField <c:out value='${fieldVO.entityAttributes}'/>"><c:choose><c:when test="${empty fieldVO.displayValue}">&nbsp;</c:when><c:otherwise><c:out value="${fieldVO.displayValue}"/></c:otherwise></c:choose></div>
		</c:when>
		<c:when test="${fieldVO.fieldType == 'LOOKUP'}">
			<input value="<c:out value='${fieldVO.fieldValue}'/>" size="16" class="text lookup <c:out value='${fieldVO.entityAttributes}'/>" name="<c:out value='${fieldVO.fieldName}'/>" id="<c:out value='${fieldVO.fieldName}'/>" /><a class="lookupLink jqModal" href="javascript:void(0)">Lookup</a>
		</c:when>
		<c:when test="${fieldVO.fieldType == 'DATE_TIME'}">
			<input value="<c:out value='${fieldVO.fieldValue}'/>" size="16" class="text <c:out value='${fieldVO.entityAttributes}'/>" name="<c:out value='${fieldVO.fieldName}'/>" id="<c:out value='${fieldVO.fieldName}'/>" />
		</c:when>
		<c:when test="${fieldVO.fieldType == 'ADDRESS'}">
			<input value="<c:out value='${fieldVO.fieldValue}'/>" class="text <c:out value='${fieldVO.entityAttributes}'/> <c:out value='${errorClass}'/>" name="<c:out value='${fieldVO.fieldName}'/>" id="<c:out value='${fieldVO.fieldName}'/>" />
		</c:when>
		<c:when test="${fieldVO.fieldType == 'PHONE'}">
			<input value="<c:out value='${fieldVO.fieldValue}'/>" class="text <c:out value='${fieldVO.entityAttributes}'/> <c:out value='${errorClass}'/>" name="<c:out value='${fieldVO.fieldName}'/>" id="<c:out value='${fieldVO.fieldName}'/>" />
		</c:when>
		<c:when test="${fieldVO.fieldType == 'LONG_TEXT'}">
			<textarea rows="2" cols="30" class="text <c:out value='${fieldVO.entityAttributes}'/> <c:out value='${errorClass}'/>" name="<c:out value='${fieldVO.fieldName}'/>" id="<c:out value='${fieldVO.fieldName}'/>"></textarea>
		</c:when>
		<c:when test="${fieldVO.fieldType == 'NUMBER'}">
		    <input value="<c:out value='${fieldVO.fieldValue}'/>" class="text <c:out value='${fieldVO.entityAttributes}'/> <c:out value='${errorClass}'/>" name="<c:out value='${fieldVO.fieldName}'/>" id="<c:out value='${fieldVO.fieldName}'/>" />
		</c:when>
		<c:when test="${fieldVO.fieldType == 'HIDDEN'}">
		    <input value="<c:out value='${fieldVO.fieldValue}'/>" class="<c:out value='${fieldVO.entityAttributes}'/>" name="<c:out value='${fieldVO.fieldName}'/>" id="<c:out value='${fieldVO.fieldName}'/>" type="hidden"/>
		</c:when>
		<c:when test="${fieldVO.fieldType == 'SPACER'}">
			&nbsp;
		</c:when>
		<c:when test="${fieldVO.fieldType == 'PICKLIST_DISPLAY' or fieldVO.fieldType == 'MULTI_PICKLIST_DISPLAY'}">
		    <div class="readOnlyField multiPicklist <c:out value='${fieldVO.entityAttributes}'/>">
				<c:forEach var="code" varStatus="status" items="${fieldVO.codes}">
					<c:set target="${fieldVO}" property="fieldToCheck" value="${code}"/>
					<div class='multiPicklistOption' style='<c:if test="${fieldVO.hasField == false}">display:none</c:if>' 
						id="option-<c:out value='${code}'/>" selectedId="<c:out value='${code}'/>" reference="<c:out value='${fieldVO.referenceValues[status.index]}'/>">
						<c:out value='${fieldVO.displayValues[status.index]}'/></a>
					</div>
				</c:forEach>
		    </div>
		</c:when>
		<c:otherwise>
			<c:out value="Field type ${fieldVO.fieldType} not yet implemented." />
		</c:otherwise>
	</c:choose>
</li>