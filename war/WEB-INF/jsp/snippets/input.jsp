<%@ include file="/WEB-INF/jsp/include.jsp" %>
<c:choose>
	<c:when test="${fieldVO.fieldType == 'HIDDEN'}">
	    <input value="<c:out value='${fieldVO.fieldValue}'/>" class="<c:out value='${fieldVO.entityAttributes}'/>" name="<c:out value='${fieldVO.fieldName}'/>" id="<c:out value='${fieldVO.fieldId}'/>" type="hidden"/>
	</c:when>
	<c:otherwise>
		<li class="side <c:if test="${fieldVO.fieldType == 'MULTI_PICKLIST' || fieldVO.fieldType == 'MULTI_PICKLIST_ADDITIONAL' || fieldVO.fieldType == 'MULTI_PICKLIST_DISPLAY' || fieldVO.fieldType == 'MULTI_QUERY_LOOKUP' || fieldVO.fieldType == 'MULTI_CODE_ADDITIONAL'}">multiOptionLi</c:if><c:if test="${fieldVO.fieldType == 'QUERY_LOOKUP' || fieldVO.fieldType == 'QUERY_LOOKUP_OTHER'}">queryLookupLi</c:if>"
			id="li-<c:out value='${sectionDefinition.sectionHtmlName}'/>-<c:out value='${fieldVO.fieldId}'/>">
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
					    <form:input path="${fieldVO.fieldName}" size="16" maxlength="10" cssClass="text date ${fieldVO.entityAttributes}" cssErrorClass="textError date" />
					</div>
				</c:when>
				<c:when test="${fieldVO.fieldType == 'DATE_DISPLAY'}">
					<fmt:formatDate value="${fieldVO.fieldValue}" pattern="MM / dd / yyyy" var="formattedDate" />
					<div id="<c:out value='${fieldVO.fieldId}'/>" class="readOnlyField <c:out value='${fieldVO.entityAttributes}'/>"><c:choose><c:when test="${empty formattedDate}">&nbsp;</c:when><c:otherwise><c:out value='${formattedDate}'/></c:otherwise></c:choose></div>
				</c:when>
				<c:when test="${fieldVO.fieldType == 'CC_EXPIRATION_DISPLAY'}">
					<fmt:formatDate value="${fieldVO.fieldValue}" pattern="MM / yyyy" var="formattedDate" />
					<div id="<c:out value='${fieldVO.fieldId}'/>" class="readOnlyField <c:out value='${fieldVO.entityAttributes}'/>"><c:choose><c:when test="${empty formattedDate}">&nbsp;</c:when><c:otherwise><c:out value='${formattedDate}'/></c:otherwise></c:choose></div>
				</c:when>
				<c:when test="${fieldVO.fieldType == 'PAYMENT_SOURCE_PICKLIST'}">
					<input type="hidden" name="<c:out value='${fieldVO.fieldName}'/>" id="<c:out value='${fieldVO.fieldId}'/>" value="<c:out value='${fieldVO.model.selectedPaymentSource.id}'/>"/>
					<script type="text/javascript" src="js/payment/paymentEditable.js"></script>
					<script type="text/javascript">PaymentEditable.commandObject = '<c:out value="${commandObject}"/>';</script>
					<select name="ach-<c:out value='${fieldVO.fieldName}'/>" id="ach-<c:out value='${fieldVO.fieldId}'/>" class="<c:out value='${fieldVO.entityAttributes}'/>">
						<c:if test="${fieldVO.required != 'true'}">
							<option value="none"><spring:message code="none"/></option>
						</c:if>
						<option value="new" <c:if test='${fieldVO.model.paymentSource.userCreated}'>selected="selected"</c:if>><spring:message code="createNew"/></option>
						<c:if test="${not empty paymentSources['ACH']}">
							<optgroup label="<spring:message code='orChoose'/>">
						</c:if>
						<c:forEach var="opt" items="${paymentSources['ACH']}">
							<option value="${opt.id}" <c:if test='${!fieldVO.model.paymentSource.userCreated && opt.id == fieldVO.model.selectedPaymentSource.id}'>selected="selected"</c:if>
								address="${opt.selectedAddress.id}" phone="${opt.selectedPhone.id}" achholder="<c:out value='${opt.achHolderName}'/>" 
								routing="<c:out value='${opt.achRoutingNumberDisplay}'/>" acct="<c:out value='${opt.achAccountNumberDisplay}'/>"><c:out value='${opt.profile}'/></option>
						</c:forEach>
						<c:if test="${not empty paymentSources['ACH']}">
							</optgroup>
						</c:if>
					</select>
										
					<select name="creditCard-<c:out value='${fieldVO.fieldName}'/>" id="creditCard-<c:out value='${fieldVO.fieldId}'/>" class="<c:out value='${fieldVO.entityAttributes}'/>">
						<c:if test="${fieldVO.required != 'true'}">
							<option value="none"><spring:message code="none"/></option>
						</c:if>
						<option value="new" <c:if test='${fieldVO.model.paymentSource.userCreated}'>selected="selected"</c:if>><spring:message code="createNew"/></option>
						<c:if test="${not empty paymentSources['Credit Card']}">
							<optgroup label="<spring:message code='orChoose'/>">
						</c:if>
						<c:forEach var="opt" items="${paymentSources['Credit Card']}">
							<option value="${opt.id}" <c:if test='${!fieldVO.model.paymentSource.userCreated && opt.id == fieldVO.model.selectedPaymentSource.id}'>selected="selected"</c:if>  
								address="${opt.selectedAddress.id}" phone="${opt.selectedPhone.id}" cardholder="<c:out value='${opt.creditCardHolderName}'/>" 
								cardType="<c:out value='${opt.creditCardType}'/>" number="<c:out value='${opt.creditCardNumberDisplay}'/>" 
								exp="<fmt:formatDate value='${opt.creditCardExpiration}' pattern='MM / yyyy'/>"><c:out value='${opt.profile}'/></option>
						</c:forEach>
						<c:if test="${not empty paymentSources['Credit Card']}">
							</optgroup>
						</c:if>
					</select>
				</c:when>
				<c:when test="${fieldVO.fieldType == 'ADDRESS_PICKLIST'}">
					<select name="<c:out value='${fieldVO.fieldName}'/>" id="<c:out value='${fieldVO.fieldId}'/>" class="picklist <c:out value='${fieldVO.entityAttributes}'/>"
						references="li:has(:input[name^='address'])">
						<c:set var="selectedRef" value="" scope="page"/>
						<c:if test="${fieldVO.required != 'true'}">
							<option value="none"><spring:message code="none"/></option>
						</c:if>
						<option value="new" reference="li:has(:input[name^='address'])" <c:if test='${fieldVO.model.address.userCreated}'>selected="selected"</c:if>><spring:message code="createNew"/></option>
						<c:if test='${fieldVO.model.address.userCreated}'>
							<c:set var="selectedRef" value="li:has(:input[name^='address'])" scope="page"/>
						</c:if>
						<c:if test="${not empty addresses}">
							<optgroup label="<spring:message code='orChoose'/>">
						</c:if>
						<c:forEach var="opt" varStatus="status" items="${addresses}">
							<option value="${opt.id}" <c:if test='${!fieldVO.model.address.userCreated && opt.id == fieldVO.model.selectedAddress.id}'>selected="selected"</c:if>><c:out value='${opt.shortDisplay}'/><c:if test="${opt.inactive}">&nbsp;<spring:message code='inactive'/></c:if></option>
						</c:forEach>
						<c:if test="${not empty addresses}">
							</optgroup>
						</c:if>
					</select>
					<c:if test="${fieldVO.required && selectedRef eq ''}">
						<%--Default select to the reference to 'create new' if required and nothing previously selected --%>
						<c:set var="selectedRef" value="li:has(:input[name^='address'])" scope="page"/>
					</c:if>
					<div style="display:none" id="selectedRef-<c:out value='${fieldVO.fieldId}'/>"><c:out value='${selectedRef}'/></div>
				</c:when>
				<c:when test="${fieldVO.fieldType == 'PHONE_PICKLIST'}">
					<select name="<c:out value='${fieldVO.fieldName}'/>" id="<c:out value='${fieldVO.fieldId}'/>" class="picklist <c:out value='${fieldVO.entityAttributes}'/>"
						references="li:has(:input[name^='phone'])">
						<c:set var="selectedRef" value="" scope="page"/>
						<c:if test="${fieldVO.required != 'true'}">
							<option value="none"><spring:message code="none"/></option>
						</c:if>
						<option value="new" reference="li:has(:input[name^='phone'])" <c:if test='${fieldVO.model.phone.userCreated}'>selected="selected"</c:if>><spring:message code="createNew"/></option>
						<c:if test='${fieldVO.model.phone.userCreated}'>
							<c:set var="selectedRef" value="li:has(:input[name^='phone'])" scope="page"/>
						</c:if>
						<c:if test="${not empty phones}">
							<optgroup label="<spring:message code='orChoose'/>">
						</c:if>
						<c:forEach var="opt" varStatus="status" items="${phones}">
							<option value="${opt.id}" <c:if test='${!fieldVO.model.phone.userCreated && opt.id == fieldVO.model.selectedPhone.id}'>selected="selected"</c:if>><c:out value='${opt.number}'/><c:if test="${opt.inactive}">&nbsp;<spring:message code='inactive'/></c:if></option>
						</c:forEach>
						<c:if test="${not empty phones}">
							</optgroup>
						</c:if>
					</select>
					<c:if test="${fieldVO.required && selectedRef eq ''}">
						<%--Default select to the reference to 'create new' if required and nothing previously selected --%>
						<c:set var="selectedRef" value="li:has(:input[name^='phone'])" scope="page"/>
					</c:if>
					<div style="display:none" id="selectedRef-<c:out value='${fieldVO.fieldId}'/>"><c:out value='${selectedRef}'/></div>
				</c:when>
				<c:when test="${fieldVO.fieldType == 'EMAIL_PICKLIST'}">
					<select name="<c:out value='${fieldVO.fieldName}'/>" id="<c:out value='${fieldVO.fieldId}'/>" class="picklist <c:out value='${fieldVO.entityAttributes}'/>"
						references="li:has(:input[name^='email'])">
						<c:set var="selectedRef" value="" scope="page"/>
						<c:if test="${fieldVO.required != 'true'}">
							<option value="none"><spring:message code="none"/></option>
						</c:if>
						<option value="new" reference="li:has(:input[name^='email'])" <c:if test='${fieldVO.model.email.userCreated}'>selected="selected"</c:if>><spring:message code="createNew"/></option>
						<c:if test='${fieldVO.model.email.userCreated}'>
							<c:set var="selectedRef" value="li:has(:input[name^='email'])" scope="page"/>
						</c:if>
						<c:if test="${not empty emails}">
							<optgroup label="<spring:message code='orChoose'/>">
						</c:if>
						<c:forEach var="opt" varStatus="status" items="${emails}">
							<option value="${opt.id}" <c:if test='${!fieldVO.model.email.userCreated && opt.id == fieldVO.model.selectedEmail.id}'>selected="selected"</c:if>><c:out value='${opt.emailAddress}'/><c:if test="${opt.inactive}">&nbsp;<spring:message code='inactive'/></c:if></option>
						</c:forEach>
						<c:if test="${not empty emails}">
							</optgroup>
						</c:if>
					</select>
					<c:if test="${fieldVO.required && selectedRef eq ''}">
						<%--Default select to the reference to 'create new' if required and nothing previously selected --%>
						<c:set var="selectedRef" value="li:has(:input[name^='email'])" scope="page"/>
					</c:if>
					<div style="display:none" id="selectedRef-<c:out value='${fieldVO.fieldId}'/>"><c:out value='${selectedRef}'/></div>
				</c:when>
				<c:when test="${fieldVO.fieldType == 'PICKLIST'}">
					<select name="<c:out value='${fieldVO.fieldName}'/>" class="<c:if test="${fieldVO.cascading}">picklist </c:if><c:out value='${fieldVO.entityAttributes}'/>" id="<c:out value='${fieldVO.fieldId}'/>"
						references="<c:out value='${fieldVO.uniqueReferenceValues}'/>">
						<c:set var="selectedRef" value="" scope="page"/>
						<c:if test="${fieldVO.required != 'true'}">
							<option value="none"><spring:message code="none"/></option>
						</c:if>
						<c:forEach var="code" varStatus="status" items="${fieldVO.augmentedCodes}">
							<c:set var="reference" value="${fieldVO.referenceValues[status.index]}" scope="request" />
							<option <c:if test="${!empty reference}">reference="<c:out value='${fieldVO.referenceValues[status.index]}'/>"</c:if> value="<c:out value='${code}'/>" 
								<c:if test="${fieldVO.fieldValue eq code}">selected="selected"</c:if>>
								<c:out value='${fieldVO.displayValues[status.index]}'/>
							</option>
							<c:if test="${fieldVO.fieldValue eq code}">
								<c:set var="selectedRef" value="${fn:trim(fieldVO.referenceValues[status.index])}" scope="page"/>
							</c:if>
						</c:forEach>
					</select>
					<c:if test="${fieldVO.required && selectedRef eq ''}">
						<%--Default select to the reference to 'create new' if required and nothing previously selected --%>
						<c:set var="selectedRef" value="${not empty fieldVO.referenceValues ? fn:trim(fieldVO.referenceValues[0]) : ''}" scope="page"/>
					</c:if>
					<div style="display:none" id="selectedRef-<c:out value='${fieldVO.fieldId}'/>"><c:out value='${selectedRef}'/></div>
				</c:when>
				<c:when test="${fieldVO.fieldType == 'PREFERRED_PHONE_TYPES'}">
					<select name="<c:out value='${fieldVO.fieldName}'/>" class="<c:if test="${fieldVO.cascading}">picklist </c:if><c:out value='${fieldVO.entityAttributes}'/>" id="<c:out value='${fieldVO.fieldId}'/>"
						references="<c:out value='${fieldVO.uniqueReferenceValues}'/>">
						<c:set var="selectedRef" value="" scope="page"/>
						<c:if test="${fieldVO.required != 'true'}">
							<option value="none"><spring:message code="none"/></option>
						</c:if>
						<c:forEach var="code" varStatus="status" items="${fieldVO.codes}">
							<c:set var="reference" value="${fieldVO.referenceValues[status.index]}" scope="request" />
							<c:choose>
								<c:when test="${fieldVO.fieldValue eq code}">
									<c:set var="selected" value="selected" scope="page" />
									<c:set var="selectedRef" value="${fn:trim(fieldVO.referenceValues[status.index])}" scope="page"/>
								</c:when>
								<c:otherwise>
									<c:set var="selected" value="" scope="page"/>
									<c:set var="selectedRef" value="" scope="page"/>
								</c:otherwise>
							</c:choose>
							<option <c:if test="${!empty reference}">reference="<c:out value='${fieldVO.referenceValues[status.index]}'/>"</c:if>value="<c:out value='${code}'/>" <c:out value='${selected}'/>>
								<c:out value='${fieldVO.displayValues[status.index]}'/>
							</option>
						</c:forEach>
					</select>
					<c:if test="${fieldVO.required && selectedRef eq ''}">
						<%--Default select to the reference to 'create new' if required and nothing previously selected --%>
						<c:set var="selectedRef" value="${not empty fieldVO.referenceValues ? fn:trim(fieldVO.referenceValues[0]) : ''}" scope="page"/>
					</c:if>
					<div style="display:none" id="selectedRef-<c:out value='${fieldVO.fieldId}'/>"><c:out value='${selectedRef}'/></div>
				</c:when>
				<c:when test="${fieldVO.fieldType == 'MULTI_PICKLIST' || fieldVO.fieldType == 'MULTI_PICKLIST_ADDITIONAL'}">
					<div class="lookupScrollTop"></div>
					<div class="lookupScrollContainer">
					    <div class="multiPicklist multiLookupField <c:out value='${fieldVO.entityAttributes}'/>" id="<c:out value='${fieldVO.fieldId}'/>"
					    	references="<c:out value='${fieldVO.uniqueReferenceValues}'/>">
							<div class="lookupScrollLeft"></div>
							<c:set var="selectedRef" value="" scope="page"/>
							<c:forEach var="code" varStatus="status" items="${fieldVO.codes}">
								<c:set target="${fieldVO}" property="fieldToCheck" value="${code}"/>
								<div class='multiPicklistOption multiOption' style='<c:if test="${fieldVO.hasField == false}">display:none</c:if>' 
									id="option-<c:out value='${code}'/>" selectedId="<c:out value='${code}'/>" reference="<c:out value='${fieldVO.referenceValues[status.index]}'/>">
									<c:out value='${fieldVO.displayValues[status.index]}'/>
									<a href="javascript:void(0)" onclick="Lookup.deleteOption(this)" class="deleteOption"><img src="images/icons/deleteRow.png" alt="<spring:message code='removeThisOption'/>" title="<spring:message code='removeThisOption'/>"/></a>
								</div>
								<c:if test="${fieldVO.hasField == true}">
									<c:choose>
										<c:when test="${selectedRef == ''}">
											<c:set var="selectedRef" value="${fn:trim(fieldVO.referenceValues[status.index])}" scope="page"/>
										</c:when>
										<c:otherwise>
											<c:set var="selectedRef" value="${selectedRef},${fn:trim(fieldVO.referenceValues[status.index])}" scope="page"/>
										</c:otherwise>
									</c:choose>
								</c:if>
							</c:forEach>
							<c:if test="${fieldVO.fieldType == 'MULTI_PICKLIST_ADDITIONAL'}">
								<div id="div-additional-<c:out value='${fieldVO.fieldId}'/>" class="additionalOptions">
									<c:forEach var="additionalValue" items="${fieldVO.additionalDisplayValues}">
										<div class='multiPicklistOption multiOption' id=""> 
											<span><c:out value='${additionalValue}'/></span>
											<a href="javascript:void(0)" onclick="Lookup.deleteAdditionalOption(this)" class="deleteOption"><img src="images/icons/deleteRow.png" alt="<spring:message code='removeThisOption'/>" title="<spring:message code='removeThisOption'/>"/></a>
										</div>
									</c:forEach>
								</div>
								<div class='multiPicklistOption multiOption noDisplay clone' id=""> 
									<span></span>
									<a href="javascript:void(0)" onclick="Lookup.deleteAdditionalOption(this)" class="deleteOption"><img src="images/icons/deleteRow.png" alt="<spring:message code='removeThisOption'/>" title="<spring:message code='removeThisOption'/>"/></a>
								</div>
							</c:if>
					    	<input type='hidden' name='labelText' id='<c:out value="${fieldVO.fieldId}"/>-labelText' value="<c:out value='${fieldVO.labelText}'/>"/>
							<div class="lookupScrollRight"></div>
					    </div>
					    <%-- The following hidden field must not lie within the multiPicklist div above --%>
						<input type="hidden" name="<c:out value='${fieldVO.fieldName}'/>" id="<c:out value='${fieldVO.fieldId}'/>" value="<c:out value='${fieldVO.fieldValuesString}'/>" additionalFieldId="<c:out value='${fieldVO.additionalFieldId}'/>"/>
					</div>
					<div class="lookupScrollBottom"></div>
					<div style="display:none" id="selectedRef-<c:out value='${fieldVO.fieldId}'/>"><c:out value='${selectedRef}'/></div>
			        <a href="javascript:void(0)" <c:choose><c:when test="${fieldVO.fieldType == 'MULTI_PICKLIST_ADDITIONAL'}">onclick="Lookup.loadMultiPicklist(this, true)"</c:when><c:otherwise>onclick="Lookup.loadMultiPicklist(this)"</c:otherwise></c:choose> class="multiLookupLink hideText" alt="<spring:message code='lookup'/>" title="<spring:message code='lookup'/>"><spring:message code='lookup'/></a>
				</c:when>
				<c:when test="${fieldVO.fieldType == 'QUERY_LOOKUP'}">
					<div class="lookupWrapper">
					    <div class="lookupField <c:out value='${fieldVO.entityAttributes}'/>">
							<c:if test="${!empty fieldVO.id}">
								<c:url value="person.htm" var="entityLink" scope="page">  <%-- ${fieldVO.entityName} hard-coded to person; TODO: change --%>
									<c:param name="personId" value="${fieldVO.id}" />
								</c:url>
								<c:set var="thisVal" value="${fn:trim(fieldVO.displayValue)}"/>
								<div id="lookup-<c:out value='${thisVal}'/>" class="queryLookupOption" selectedId="<c:out value='${fieldVO.id}'/>">
									<a href="<c:out value='${entityLink}'/>" target="_blank" alt="<spring:message code='gotoLink'/>" title="<spring:message code='gotoLink'/>"><c:out value='${thisVal}'/></a>
									<a href="javascript:void(0)" onclick="Lookup.deleteOption(this)" class="deleteOption"><img src="images/icons/deleteRow.png" alt="<spring:message code='removeThisOption'/>" title="<spring:message code='removeThisOption'/>"/></a>
								</div>
								<c:remove var="entityLink" scope="page" />
							</c:if>
					        <a href="javascript:void(0)" onclick="Lookup.loadQueryLookup(this)" fieldDef="<c:out value='${sectionField.fieldDefinition.id}'/>" class="hideText" alt="<spring:message code='lookup'/>" title="<spring:message code='lookup'/>"><spring:message code='lookup'/></a>
					    </div>
						<input type="hidden" name="<c:out value='${fieldVO.fieldName}'/>" value="<c:out value='${fieldVO.id}'/>" id="<c:out value='${fieldVO.fieldId}'/>"/>
						<div class="queryLookupOption noDisplay clone">
							<a href="" target="_blank"></a>
							<a href="javascript:void(0)" onclick="Lookup.deleteOption(this)" class="deleteOption"><img src="images/icons/deleteRow.png" alt="<spring:message code='removeThisOption'/>" title="<spring:message code='removeThisOption'/>"/></a>
						</div>
					</div>
				</c:when>
				<c:when test="${fieldVO.fieldType == 'QUERY_LOOKUP_OTHER'}">
					<div class="lookupWrapper">
					    <div class="lookupField <c:out value='${fieldVO.entityAttributes}'/>">
							<c:set var="thisVal" value="${fn:trim(fieldVO.displayValue)}"/>
							<div id="lookup-<c:out value='${fieldVO.fieldId}'/>" class="queryLookupOption" selectedId="<c:out value='${fieldVO.id}'/>">
								<c:choose>
									<c:when test="${not empty fieldVO.id}">
										<c:url value="person.htm" var="entityLink" scope="page">  <%-- ${fieldVO.entityName} hard-coded to person; TODO: change --%>
											<c:param name="personId" value="${fieldVO.id}" />
										</c:url>
										<span><a href="<c:out value='${entityLink}'/>" target="_blank" alt="<spring:message code='gotoLink'/>" title="<spring:message code='gotoLink'/>"><c:out value='${thisVal}'/></a></span>
										<c:remove var="entityLink" scope="page" />
									</c:when>
									<c:otherwise>
										<span><c:out value='${thisVal}'/></span>
									</c:otherwise>
								</c:choose>
								<c:if test="${not empty thisVal}">
									<a href="javascript:void(0)" onclick="Lookup.deleteOption(this)" class="deleteOption"><img src="images/icons/deleteRow.png" alt="<spring:message code='removeThisOption'/>" title="<spring:message code='removeThisOption'/>"/></a>
								</c:if>
							</div>
					        <a href="javascript:void(0)" onclick="Lookup.loadQueryLookup(this, true)" fieldDef="<c:out value='${sectionField.fieldDefinition.id}'/>" class="hideText" alt="<spring:message code='lookup'/>" title="<spring:message code='lookup'/>"><spring:message code='lookup'/></a>
					    </div>
						<input type="hidden" name="<c:out value='${fieldVO.fieldName}'/>" value="<c:out value='${fieldVO.id}'/>" id="<c:out value='${fieldVO.fieldId}'/>" otherFieldId="<c:out value='${fieldVO.otherFieldId}'/>"/>
						<div class="queryLookupOption noDisplay clone">
							<span><a href="" target="_blank"></a></span>
							<a href="javascript:void(0)" onclick="Lookup.deleteOption(this)" class="deleteOption"><img src="images/icons/deleteRow.png" alt="<spring:message code='removeThisOption'/>" title="<spring:message code='removeThisOption'/>"/></a>
						</div>
					</div>
				</c:when>
				<c:when test="${fieldVO.fieldType == 'MULTI_QUERY_LOOKUP'}">
					<%-- TODO: move to tag library --%>
					<div class="lookupScrollTop"></div>
					<div class="lookupScrollContainer">
					    <div class="multiLookupField <c:out value='${fieldVO.entityAttributes}'/>">
							<div class="lookupScrollLeft"></div>
							<c:forEach var="val" varStatus="status" items="${fieldVO.displayValues}">
								<c:choose>
									<c:when test="${not empty fieldVO.ids[status.index]}">
										<c:url value="person.htm" var="entityLink" scope="page"> <%-- ${fieldVO.entityName} hard-coded to person; TODO: change --%>
											<c:param name="personId" value="${fieldVO.ids[status.index]}" />
										</c:url>
									</c:when>
									<c:otherwise>
										<c:set value="javascript:void(0)" var="entityLink" scope="page" />
									</c:otherwise>
								</c:choose>
								<c:set var="thisVal" value="${fn:trim(val)}"/>
								<div id="lookup-<c:out value='${thisVal}'/>" class="multiQueryLookupOption multiOption" selectedId="<c:out value='${fieldVO.ids[status.index]}'/>">
									<a href="<c:out value='${entityLink}'/>" target="_blank" alt="<spring:message code='gotoLink'/>" title="<spring:message code='gotoLink'/>"><c:out value='${thisVal}'/></a>
									<a href="javascript:void(0)" onclick="Lookup.deleteOption(this)" class="deleteOption"><img src="images/icons/deleteRow.png" alt="<spring:message code='removeThisOption'/>" title="<spring:message code='removeThisOption'/>"/></a>
								</div>
								<c:remove var="entityLink" scope="page" />
							</c:forEach>
							<div class="lookupScrollRight"></div>
					    </div>
						<input type="hidden" name="<c:out value='${fieldVO.fieldName}'/>" value="<c:out value='${fieldVO.idsString}'/>" id="<c:out value='${fieldVO.fieldId}'/>" />
						<div class="multiQueryLookupOption multiOption noDisplay clone" selectedId="">
							<a href="" target="_blank"></a>
							<a href="javascript:void(0)" onclick="Lookup.deleteOption(this)" class="deleteOption"><img src="images/icons/deleteRow.png" alt="<spring:message code='removeThisOption'/>" title="<spring:message code='removeThisOption'/>"/></a>
						</div>		
					</div>
					<div class="lookupScrollBottom"></div>
			        <a href="javascript:void(0)" onclick="Lookup.loadMultiQueryLookup(this)" fieldDef="<c:out value='${sectionField.fieldDefinition.id}'/>" class="multiLookupLink hideText" alt="<spring:message code='lookup'/>" title="<spring:message code='lookup'/>"><spring:message code='lookup'/></a>
				</c:when>
				<c:when test="${fieldVO.fieldType == 'CC_EXPIRATION'}">
					<select name="<c:out value='${fieldVO.fieldName}'/>Month" id="<c:out value='${fieldVO.fieldId}'/>Month" class="expMonth <c:out value='${fieldVO.entityAttributes}'/>">
						<c:forEach var="opt" varStatus="status" items="${paymentSource.expirationMonthList}">
							<c:set var="expirationMonth" scope="request" value="${paymentSource.creditCardExpirationMonthText}" />
							<c:if test="${empty expirationMonth}">
								<c:set var="now" value="<%=new java.util.Date()%>"/>
								<fmt:formatDate var="expirationMonth" scope="request" value="${now}" pattern="MM" />
							</c:if>
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
					<select name="<c:out value='${fieldVO.fieldName}'/>Year" id="<c:out value='${fieldVO.fieldId}'/>Year" class="expYear <c:out value='${fieldVO.entityAttributes}'/>">
						<c:forEach var="opt" varStatus="status" items="${paymentSource.expirationYearList}">
							<c:set var="expirationYear" scope="request" value="${paymentSource.creditCardExpirationYear}" />
							<c:if test="${empty expirationYear}">
								<c:set var="now" value="<%=new java.util.Date()%>"/>
								<fmt:formatDate var="expirationYear" scope="request" value="${now}" pattern="yyyy" />
							</c:if>
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
				<c:when test="${fieldVO.fieldType == 'CODE' || fieldVO.fieldType == 'CODE_OTHER'}">
					<div class="lookupWrapper">
						<input value="<c:out value='${fieldVO.displayValue}'/>" 
							<c:if test="${fieldVO.fieldType == 'CODE_OTHER'}">
								otherFieldId="<c:out value='${fieldVO.otherFieldId}'/>"
							</c:if> 
							class="text code <c:out value='${fieldVO.entityAttributes}'/> <c:out value=' ${errorClass}'/>" lookup="<c:out value='${fieldVO.fieldName}'/>" 
							codeType="<c:out value='${fieldVO.fieldName}'/>" name="display-<c:out value='${fieldVO.fieldName}'/>" id="display-<c:out value='${fieldVO.fieldId}'/>" />
						<input type="hidden" name="<c:out value='${fieldVO.fieldName}'/>" id="hidden-<c:out value='${fieldVO.fieldId}'/>" value="<c:out value='${fieldVO.fieldValue}'/>"/>
						<a class="lookupLink" href="javascript:void(0)" 
							<c:choose>
								<c:when test="${fieldVO.fieldType == 'CODE_OTHER'}">
									onclick="Lookup.loadCodePopup(this, true)" 
								</c:when>
								<c:otherwise>
									onclick="Lookup.loadCodePopup(this)" 
								</c:otherwise>
							</c:choose> 
							alt="<spring:message code='lookup'/>" title="<spring:message code='lookup'/>"><spring:message code='lookup'/></a>
					</div>
				</c:when>
				<c:when test="${fieldVO.fieldType == 'MULTI_CODE_ADDITIONAL'}">
					<div class="lookupScrollTop"></div>
					<div class="lookupScrollContainer">
					    <div class="multiCode multiLookupField <c:out value='${fieldVO.entityAttributes}'/>" id="<c:out value='${fieldVO.fieldId}'/>">
							<div class="lookupScrollLeft"></div>
							<c:forEach var="code" items="${fieldVO.displayValues}" varStatus="status">
								<div class='multiCodeOption multiOption' id="option-<c:out value='${fieldVO.fieldValues[status.index]}'/>" code="<c:out value='${fieldVO.fieldValues[status.index]}'/>">
									<span><c:out value='${code}'/></span>
									<a href="javascript:void(0)" onclick="Lookup.deleteCode(this)" class="deleteOption"><img src="images/icons/deleteRow.png" alt="<spring:message code='removeThisOption'/>" title="<spring:message code='removeThisOption'/>"/></a>
								</div>
							</c:forEach>
							<div id="div-additional-<c:out value='${fieldVO.fieldId}'/>" class="additionalOptions">
								<c:set var="counter" value="0"/>
								<c:forEach var="additionalValue" items="${fieldVO.additionalDisplayValues}">
									<div class='multiCodeOption multiOption' id="additional-<c:out value='${counter}'/>" code="<c:out value='${additionalValue}'/>"> 
										<span><c:out value='${additionalValue}'/></span>
										<a href="javascript:void(0)" onclick="Lookup.deleteCode(this)" class="deleteOption"><img src="images/icons/deleteRow.png" alt="<spring:message code='removeThisOption'/>" title="<spring:message code='removeThisOption'/>"/></a>
									</div>
									<c:set var="counter" value="${counter + 1}"/>
								</c:forEach>
							</div>
							<div class="lookupScrollRight"></div>
					    </div>
					    <%-- The following hidden field must not lie within the multiCode div above --%>
						<input type="hidden" name="<c:out value='${fieldVO.fieldName}'/>" id="<c:out value='${fieldVO.fieldId}'/>" value="<c:out value='${fieldVO.fieldValuesString}'/>" additionalFieldId="<c:out value='${fieldVO.additionalFieldId}'/>"/>
						<div class='multiCodeOption multiOption noDisplay clone' id="" code=""> 
							<span></span>
							<a href="javascript:void(0)" onclick="Lookup.deleteCode(this)" class="deleteOption"><img src="images/icons/deleteRow.png" alt="<spring:message code='removeThisOption'/>" title="<spring:message code='removeThisOption'/>"/></a>
						</div>
					</div>
					<div class="lookupScrollBottom"></div>
			        <a href="javascript:void(0)" onclick="Lookup.loadCodeAdditionalPopup(this)" class="multiLookupLink hideText" lookup="<c:out value='${fieldVO.fieldName}'/>" alt="<spring:message code='lookup'/>" title="<spring:message code='lookup'/>"><spring:message code='lookup'/></a>
				</c:when>
				<c:when test="${fieldVO.fieldType == 'CHECKBOX'}">
		            <input type="hidden" name="_<c:out value="${fieldVO.fieldName}"/>" value="visible" />
		            <input type="checkbox" value="true" 
		                   class="checkbox <c:out value='${fieldVO.entityAttributes}'/>" 
		                   name="<c:out value='${fieldVO.fieldName}'/>" 
		                   id="<c:out value='${fieldVO.fieldId}'/>"  
		                   <c:if test="${fieldVO.fieldValue}">checked</c:if> 
		            />
				</c:when>
				<c:when test="${fieldVO.fieldType == 'READ_ONLY_TEXT' || fieldVO.fieldType == 'PAYMENT_TYPE_READ_ONLY_TEXT'}">
					<c:if test="${fieldVO.fieldType == 'PAYMENT_TYPE_READ_ONLY_TEXT'}">
						<script type="text/javascript" src="js/payment/paymentTypeReadOnly.js"></script>
						<script type="text/javascript">var PaymentTypeCommandObject = '<c:out value="${commandObject}"/>';</script>
					</c:if>
					<div id="<c:out value='${fieldVO.fieldId}'/>" class="readOnlyField <c:out value='${fieldVO.entityAttributes}'/>"><c:choose><c:when test="${empty fieldVO.displayValue}">&nbsp;</c:when><c:otherwise><c:out value="${fieldVO.displayValue}"/></c:otherwise></c:choose></div>
				</c:when>
				<c:when test="${fieldVO.fieldType == 'TEXT'}">
					<input value="<c:out value='${fieldVO.fieldValue}'/>" class="text <c:out value='${fieldVO.entityAttributes}'/> <c:out value=' ${errorClass}'/>" name="<c:out value='${fieldVO.fieldName}'/>" id="<c:out value='${fieldVO.fieldId}'/>" type="text"/>
				</c:when>
				<c:when test="${fieldVO.fieldType == 'LONG_TEXT'}">
					<textarea rows="5" cols="30" class="text <c:out value='${fieldVO.entityAttributes}'/> <c:out value=' ${errorClass}'/>" name="<c:out value='${fieldVO.fieldName}'/>" id="<c:out value='${fieldVO.fieldId}'/>"><c:out value='${fieldVO.displayValue}'/></textarea>
				</c:when>
				<c:when test="${fieldVO.fieldType == 'LOOKUP'}">
					<input value="<c:out value='${fieldVO.fieldValue}'/>" size="16" class="text lookup <c:out value='${fieldVO.entityAttributes}'/>" name="<c:out value='${fieldVO.fieldName}'/>" id="<c:out value='${fieldVO.fieldId}'/>" type="text"/><a class="lookupLink jqModal" href="javascript:void(0)"><spring:message code='lookup'/></a>
				</c:when>
				<c:when test="${fieldVO.fieldType == 'DATE_TIME'}">
					<input value="<c:out value='${fieldVO.fieldValue}'/>" size="16" class="text <c:out value='${fieldVO.entityAttributes}'/>" name="<c:out value='${fieldVO.fieldName}'/>" id="<c:out value='${fieldVO.fieldId}'/>" type="text"/>
				</c:when>
				<c:when test="${fieldVO.fieldType == 'ADDRESS'}">
					<input value="<c:out value='${fieldVO.fieldValue}'/>" class="text <c:out value='${fieldVO.entityAttributes}'/> <c:out value=' ${errorClass}'/>" name="<c:out value='${fieldVO.fieldName}'/>" id="<c:out value='${fieldVO.fieldId}'/>" type="text"/>
				</c:when>
				<c:when test="${fieldVO.fieldType == 'PHONE'}">
					<input value="<c:out value='${fieldVO.fieldValue}'/>" class="text <c:out value='${fieldVO.entityAttributes}'/> <c:out value=' ${errorClass}'/>" name="<c:out value='${fieldVO.fieldName}'/>" id="<c:out value='${fieldVO.fieldId}'/>" type="text"/>
				</c:when>
				<c:when test="${fieldVO.fieldType == 'NUMBER'}">
				    <input value="<c:out value='${fieldVO.fieldValue}'/>" class="text number <c:out value='${fieldVO.entityAttributes}'/> <c:out value=' ${errorClass}'/>" name="<c:out value='${fieldVO.fieldName}'/>" id="<c:out value='${fieldVO.fieldId}'/>" type="text"/>
				</c:when>
				<c:when test="${fieldVO.fieldType == 'PERCENTAGE'}">
					<input value="<c:out value='${fieldVO.fieldValue}'/>" class="text percentage <c:out value='${fieldVO.entityAttributes}'/> <c:out value=' ${errorClass}'/>" name="<c:out value='${fieldVO.fieldName}'/>" id="<c:out value='${fieldVO.fieldId}'/>" type="text"/>
				</c:when>
				<c:when test="${fieldVO.fieldType == 'SPACER'}">
					&nbsp;
				</c:when>
				<c:when test="${fieldVO.fieldType == 'PICKLIST_DISPLAY' or fieldVO.fieldType == 'MULTI_PICKLIST_DISPLAY'}">
				    <div class="readOnlyField multiPicklist <c:out value='${fieldVO.entityAttributes}'/>" id="<c:out value='${fieldVO.fieldId}'/>"
				    	references="<c:out value='${fieldVO.uniqueReferenceValues}'/>">
						<c:set var="selectedRef" value="" scope="page"/>
						<c:forEach var="code" varStatus="status" items="${fieldVO.codes}">
							<c:set target="${fieldVO}" property="fieldToCheck" value="${code}"/>
							<div class='multiPicklistOption multiOption' style='<c:if test="${fieldVO.hasField == false}">display:none</c:if>' 
								id="option-<c:out value='${code}'/>" selectedId="<c:out value='${code}'/>" reference="<c:out value='${fieldVO.referenceValues[status.index]}'/>">
								<c:out value='${fieldVO.displayValues[status.index]}'/></a>
							</div>
							<c:if test="${fieldVO.hasField == true}">
								<c:choose>
									<c:when test="${selectedRef == ''}">
										<c:set var="selectedRef" value="${fn:trim(fieldVO.referenceValues[status.index])}" scope="page"/>
									</c:when>
									<c:otherwise>
										<c:set var="selectedRef" value="${selectedRef},${fn:trim(fieldVO.referenceValues[status.index])}" scope="page"/>
									</c:otherwise>
								</c:choose>
							</c:if>
						</c:forEach>
				    </div>
					<div style="display:none" id="selectedRef-<c:out value='${fieldVO.fieldId}'/>"><c:out value='${selectedRef}'/></div>
				</c:when>
				<c:otherwise>
					<c:out value="Field type ${fieldVO.fieldType} not yet implemented." />
				</c:otherwise>
			</c:choose>
		</li>
	</c:otherwise>
</c:choose>