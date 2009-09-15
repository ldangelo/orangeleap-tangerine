<%@ include file="/WEB-INF/jsp/include.jsp" %>
<c:choose>
	<c:when test="${fieldVO.fieldType == 'HIDDEN'}">
	    <input value="<c:out value='${fieldVO.fieldValue}'/>" class="<c:out value='${fieldVO.entityAttributes}'/>" name="<c:out value='${fieldVO.fieldName}'/>" id="<c:out value='${fieldVO.fieldId}'/>" type="hidden"/>
	</c:when>
	<c:otherwise>
		<li class="side <c:if test="${fieldVO.fieldType == 'MULTI_PICKLIST' || fieldVO.fieldType == 'MULTI_PICKLIST_ADDITIONAL' || fieldVO.fieldType == 'MULTI_PICKLIST_DISPLAY' || fieldVO.fieldType == 'MULTI_PICKLIST_ADDITIONAL_DISPLAY' || fieldVO.fieldType == 'MULTI_QUERY_LOOKUP' || fieldVO.fieldType == 'MULTI_CODE_ADDITIONAL' || fieldVO.fieldType == 'SELECTION' || fieldVO.fieldType == 'SELECTION_DISPLAY'}">multiOptionLi</c:if><c:if test="${fieldVO.fieldType == 'QUERY_LOOKUP' || fieldVO.fieldType == 'MULTI_QUERY_LOOKUP' || fieldVO.fieldType == 'QUERY_LOOKUP_OTHER' || fieldVO.fieldType == 'QUERY_LOOKUP_DISPLAY'}"> queryLookupLi</c:if><c:if test="${(fieldVO.fieldType == 'ASSOCIATION' || fieldVO.fieldType == 'ASSOCIATION_DISPLAY') && empty fieldVO.fieldValue}"> noDisplay</c:if>"
			id="li-<c:out value='${sectionDefinition.sectionHtmlName}'/>-<c:out value='${fieldVO.fieldId}'/>">
			<c:remove var="errorClass" scope="page" />
			<c:if test="${commandObject != null}">
				<spring:hasBindErrors name="${commandObject}">
					<c:forEach items="${errors.fieldErrors}" var="error">
						<c:choose>
							<c:when test="${fn:startsWith(error.field, 'customFieldMap[') && fn:endsWith(error.field, ']')}">
								<c:set var="thisError" value="${error.field}.value" scope="page"/>
							</c:when>
							<c:otherwise>
								<c:set var="thisError" value="${error.field}" scope="page"/>
							</c:otherwise>
						</c:choose>
						<c:if test="${thisError == fieldVO.fieldName}"><c:set scope="page" var="errorClass" value="textError" /></c:if>
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
                        <form:input path="${fieldVO.fieldName}" size="16" maxlength="10"
                                    cssClass="text ${fieldVO.entityAttributes}" cssErrorClass="textError"/>

                        <script type="text/javascript">
                            var name = '${fieldVO.fieldName}';
                            var seasonal = (name.indexOf('seasonal') > -1);
                            // remove brackets from IDs
                            name = name.replace('[','').replace(']','');
                            new Ext.form.DateField({
                                applyTo: name,
                                id: name + "-wrapper",
                                format: (seasonal ? 'F-j' : 'm/d/Y'),
                                width: 250
                            });
                        </script>
                    </div>
                </c:when>
				<c:when test="${fieldVO.fieldType == 'DATE_DISPLAY' || fieldVO.fieldType == 'PAYMENT_SOURCE_PICKLIST' || 
								fieldVO.fieldType == 'CC_EXPIRATION' || fieldVO.fieldType == 'CC_EXPIRATION_DISPLAY' || 
								fieldVO.fieldType == 'LONG_TEXT' ||
								fieldVO.fieldType == 'CHECKBOX' || 
								fieldVO.fieldType == 'ADDRESS_PICKLIST' || fieldVO.fieldType == 'EXISTING_ADDRESS_PICKLIST' ||
								fieldVO.fieldType == 'PHONE_PICKLIST' || fieldVO.fieldType == 'EXISTING_PHONE_PICKLIST' || 
								fieldVO.fieldType == 'EMAIL_PICKLIST' || fieldVO.fieldType == 'EXISTING_EMAIL_PICKLIST' ||  
								fieldVO.fieldType == 'PICKLIST' ||
								fieldVO.fieldType == 'SPACER' ||  
								fieldVO.fieldType == 'TEXT' || fieldVO.fieldType == 'LOOKUP' || fieldVO.fieldType == 'DATE_TIME' || 
								fieldVO.fieldType == 'ADDRESS' || fieldVO.fieldType == 'PHONE' || fieldVO.fieldType == 'NUMBER' || 
								fieldVO.fieldType == 'PERCENTAGE' ||
								fieldVO.fieldType == 'PREFERRED_PHONE_TYPES' ||
								fieldVO.fieldType == 'CODE' || fieldVO.fieldType == 'CODE_OTHER' || fieldVO.fieldType == 'CODE_OTHER_DISPLAY' || 
								fieldVO.fieldType == 'READ_ONLY_TEXT' || fieldVO.fieldType == 'PAYMENT_TYPE_READ_ONLY_TEXT' || 
								fieldVO.fieldType == 'ADJUSTED_GIFT_PAYMENT_SOURCE_PICKLIST' || fieldVO.fieldType == 'ADJUSTED_GIFT_PAYMENT_TYPE_PICKLIST'}">
					<mp:input field="${fieldVO}"/>
				</c:when>
				<c:when test="${fieldVO.fieldType == 'MULTI_PICKLIST' || fieldVO.fieldType == 'MULTI_PICKLIST_ADDITIONAL' || fieldVO.fieldType == 'MULTI_PICKLIST_DISPLAY' || fieldVO.fieldType == 'MULTI_PICKLIST_ADDITIONAL_DISPLAY'}">
					<div class="lookupScrollTop<c:out value=' ${errorClass}'/>"></div>
					<div class="lookupScrollContainer <c:if test="${fieldVO.fieldType eq 'MULTI_PICKLIST_ADDITIONAL_DISPLAY' || fieldVO.fieldType == 'MULTI_PICKLIST_DISPLAY'}">readOnly</c:if><c:out value=' ${errorClass}'/>">
					    <div class="multiPicklist multiLookupField <c:out value='${fieldVO.entityAttributes}'/>" id="<c:out value='${fieldVO.fieldId}'/>"
					    	references="<c:out value='${fieldVO.uniqueReferenceValues}'/>">
							<div class="lookupScrollLeft"></div>
							<c:set var="selectedRef" value="" scope="page"/>
							<c:forEach var="code" varStatus="status" items="${fieldVO.codes}">
								<c:set target="${fieldVO}" property="fieldToCheck" value="${code}"/>
								<div class='multiPicklistOption multiOption' style='<c:if test="${fieldVO.hasField == false}">display:none</c:if>' 
									id="option-<c:out value='${code}'/>" selectedId="<c:out value='${code}'/>" reference="<c:out value='${fieldVO.referenceValues[status.index]}'/>">
									<c:out value='${fieldVO.displayValues[status.index]}'/>
									<c:if test="${fieldVO.fieldType != 'MULTI_PICKLIST_ADDITIONAL_DISPLAY' && fieldVO.fieldType != 'MULTI_PICKLIST_DISPLAY'}">
										<a href="javascript:void(0)" onclick="Lookup.deleteOption(this)" class="deleteOption"><img src="images/icons/deleteRow.png" alt="<spring:message code='removeThisOption'/>" title="<spring:message code='removeThisOption'/>"/></a>
									</c:if>
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
							<c:if test="${fieldVO.fieldType == 'MULTI_PICKLIST_ADDITIONAL' || fieldVO.fieldType == 'MULTI_PICKLIST_ADDITIONAL_DISPLAY'}">
								<div id="div-additional-<c:out value='${fieldVO.fieldId}'/>" class="additionalOptions">
									<c:forEach var="additionalValue" items="${fieldVO.additionalDisplayValues}">
										<div class='multiPicklistOption multiOption' id=""> 
											<span><c:out value='${additionalValue}'/></span>
											<c:if test="${fieldVO.fieldType != 'MULTI_PICKLIST_ADDITIONAL_DISPLAY'}">
												<a href="javascript:void(0)" onclick="Lookup.deleteAdditionalOption(this)" class="deleteOption"><img src="images/icons/deleteRow.png" alt="<spring:message code='removeThisOption'/>" title="<spring:message code='removeThisOption'/>"/></a>
											</c:if>
										</div>
									</c:forEach>
								</div>
								<c:if test="${fieldVO.fieldType != 'MULTI_PICKLIST_ADDITIONAL_DISPLAY' && fieldVO.fieldType != 'MULTI_PICKLIST_DISPLAY'}">
									<div class='multiPicklistOption multiOption noDisplay clone' id=""> 
										<span></span>
										<a href="javascript:void(0)" onclick="Lookup.deleteAdditionalOption(this)" class="deleteOption"><img src="images/icons/deleteRow.png" alt="<spring:message code='removeThisOption'/>" title="<spring:message code='removeThisOption'/>"/></a>
									</div>
								</c:if>
							</c:if>
					    	<input type='hidden' name='labelText' id='<c:out value="${fieldVO.fieldId}"/>-labelText' value="<c:out value='${fieldVO.labelText}'/>"/>
							<div class="lookupScrollRight"></div>
					    </div>
					    <%-- The following hidden field must not lie within the multiPicklist div above --%>
						<input type="hidden" name="<c:out value='${fieldVO.fieldName}'/>" id="<c:out value='${fieldVO.fieldId}'/>" value="<c:out value='${fieldVO.fieldValuesString}'/>" additionalFieldId="<c:out value='${fieldVO.additionalFieldId}'/>"/>
					</div>
					<div class="lookupScrollBottom<c:out value=' ${errorClass}'/>"></div>
					<div style="display:none" id="selectedRef-<c:out value='${fieldVO.fieldId}'/>"><c:out value='${selectedRef}'/></div>
					<c:if test="${fieldVO.fieldType != 'MULTI_PICKLIST_ADDITIONAL_DISPLAY' && fieldVO.fieldType != 'MULTI_PICKLIST_DISPLAY'}">
				        <a href="javascript:void(0)" <c:choose><c:when test="${fieldVO.fieldType == 'MULTI_PICKLIST_ADDITIONAL'}">onclick="Lookup.loadMultiPicklist(this, true)"</c:when><c:otherwise>onclick="Lookup.loadMultiPicklist(this)"</c:otherwise></c:choose> class="multiLookupLink hideText" alt="<spring:message code='lookup'/>" title="<spring:message code='lookup'/>"><spring:message code='lookup'/></a>
				    </c:if>
				</c:when>
				<c:when test="${fieldVO.fieldType == 'QUERY_LOOKUP' || fieldVO.fieldType == 'QUERY_LOOKUP_OTHER' || fieldVO.fieldType == 'ASSOCIATION' || fieldVO.fieldType == 'ASSOCIATION_DISPLAY'}">
					<c:choose>
						<c:when test="${fieldVO.fieldType == 'ASSOCIATION'}">
							<c:set var="clickHandler" value="Lookup.deleteAssociation(this)" scope="page"/>
						</c:when>
						<c:otherwise>
							<c:set var="clickHandler" value="Lookup.deleteOption(this)" scope="page"/>
						</c:otherwise>
					</c:choose>
					<div class="lookupWrapper<c:if test="${fieldVO.fieldType == 'ASSOCIATION_DISPLAY'}"> readOnly</c:if>">
					    <div class="lookupField <c:out value='${fieldVO.entityAttributes}'/>">
							<c:set var="thisVal" value="${fn:trim(fieldVO.displayValue)}"/>
							<div id="lookup-<c:out value='${fieldVO.fieldId}'/>" class="queryLookupOption" selectedId="<c:out value='${fieldVO.id}'/>">
								<c:choose>
									<c:when test="${not empty fieldVO.id}">
										<%--  TODO: remove this when automatic routing to the view page is implemented --%>
										<c:set var="thisUrl" value="${fieldVO.referenceType}.htm" scope="page"/>
										<c:url value="${thisUrl}" var="entityLink" scope="page">
											<c:param name="${fieldVO.referenceType}Id" value="${fieldVO.id}" />
											<c:if test="${fieldVO.referenceType != 'constituent'}">
												<c:param name="constituentId" value="${param.constituentId}" />
											</c:if>
										</c:url>
										<span><a href="<c:out value='${entityLink}'/>" target="_blank" alt="<spring:message code='gotoLink'/>" title="<spring:message code='gotoLink'/>"><c:out value='${thisVal}'/></a></span>
										<c:remove var="entityLink" scope="page" />
									</c:when>
									<c:otherwise>
										<span><c:out value='${thisVal}'/></span>
									</c:otherwise>
								</c:choose>
								<c:if test="${fieldVO.fieldType != 'ASSOCIATION_DISPLAY' && not empty thisVal}">
									<a href="javascript:void(0)" onclick="<c:out value='${pageScope.clickHandler}'/>" class="deleteOption"><img src="images/icons/deleteRow.png" alt="<spring:message code='removeThisOption'/>" title="<spring:message code='removeThisOption'/>"/></a>
								</c:if>
							</div>
							<c:if test="${fieldVO.fieldType != 'ASSOCIATION' && fieldVO.fieldType != 'ASSOCIATION_DISPLAY'}">
					        	<a href="javascript:void(0)" onclick="Lookup.loadQueryLookup(this<c:if test="${fieldVO.fieldType == 'QUERY_LOOKUP_OTHER'}">, true</c:if>)" fieldDef="<c:out value='${sectionField.fieldDefinition.id}'/>" class="hideText" alt="<spring:message code='lookup'/>" title="<spring:message code='lookup'/>"><spring:message code='lookup'/></a>
					        </c:if>
					    </div>
						<input type="hidden" name="<c:out value='${fieldVO.fieldName}'/>" value="<c:out value='${fieldVO.id}'/>" id="<c:out value='${fieldVO.fieldId}'/>" <c:if test="${fieldVO.fieldType == 'QUERY_LOOKUP_OTHER'}">otherFieldId="<c:out value='${fieldVO.otherFieldId}'/>"</c:if>/>
						<c:if test="${fieldVO.fieldType != 'ASSOCIATION_DISPLAY'}">
							<div class="queryLookupOption noDisplay clone">
								<span><a href="" target="_blank"></a></span>
								<a href="javascript:void(0)" onclick="<c:out value='${pageScope.clickHandler}'/>" class="deleteOption"><img src="images/icons/deleteRow.png" alt="<spring:message code='removeThisOption'/>" title="<spring:message code='removeThisOption'/>"/></a>
							</div>
						</c:if>
						 
						<c:if test="${fieldVO.hierarchy}">
				       		 <a href="javascript:void(0)" onclick="Lookup.loadTreeView(this);" divid="treeview-${fieldVO.fieldId}" constituentid="${param.constituentId}" fieldDef="<c:out value='${fieldVO.fieldName}'/>" >View hierarchy</a>
				       		 <div id="treeview-${fieldVO.fieldId}" ></div>
				    	</c:if>
				    	 
					</div>
				</c:when>
				<c:when test="${fieldVO.fieldType == 'MULTI_QUERY_LOOKUP' || fieldVO.fieldType == 'QUERY_LOOKUP_DISPLAY'}">
					<div class="lookupScrollTop"></div>
					<div class="lookupScrollContainer <c:if test="${fieldVO.fieldType == 'QUERY_LOOKUP_DISPLAY'}">readOnly</c:if>">
					    <div class="multiLookupField <c:out value='${fieldVO.entityAttributes}'/>">
							<div class="lookupScrollLeft"></div>
							<c:forEach var="val" varStatus="status" items="${fieldVO.displayValues}">
								<c:set var="thisVal" value="${fn:trim(val)}"/>
								<div id="lookup-<c:out value='${thisVal}'/>" class="multiQueryLookupOption multiOption" selectedId="<c:out value='${fieldVO.ids[status.index]}'/>">
									<c:choose>
										<c:when test="${not empty fieldVO.ids[status.index]}">
											<c:url value="${fieldVO.referenceType}.htm" var="entityLink" scope="page">
												<c:param name="${fieldVO.referenceType}Id" value="${fieldVO.ids[status.index]}" />
												<c:if test="${fieldVO.referenceType != 'constituent'}">
													<c:param name="constituentId" value="${param.constituentId}" />
												</c:if>
											</c:url>
											<a href="<c:out value='${entityLink}'/>" target="_blank" alt="<spring:message code='gotoLink'/>" title="<spring:message code='gotoLink'/>"><c:out value='${thisVal}'/></a>
										</c:when>
										<c:otherwise>
											<span><c:out value='${thisVal}'/></span>
										</c:otherwise>
									</c:choose>
									<c:if test="${fieldVO.fieldType == 'MULTI_QUERY_LOOKUP'}">
										<a href="javascript:void(0)" onclick="Lookup.deleteOption(this)" class="deleteOption"><img src="images/icons/deleteRow.png" alt="<spring:message code='removeThisOption'/>" title="<spring:message code='removeThisOption'/>"/></a>
									</c:if>
								</div>
								<c:remove var="entityLink" scope="page" />
							</c:forEach>
							<div class="lookupScrollRight"></div>
					    </div>
						<input type="hidden" name="<c:out value='${fieldVO.fieldName}'/>" value="<c:out value='${fieldVO.idsString}'/>" id="<c:out value='${fieldVO.fieldId}'/>" />
						<c:if test="${fieldVO.fieldType == 'MULTI_QUERY_LOOKUP'}">
							<div class="multiQueryLookupOption multiOption noDisplay clone" selectedId="">
								<a href="" target="_blank"></a>
								<a href="javascript:void(0)" onclick="Lookup.deleteOption(this)" class="deleteOption"><img src="images/icons/deleteRow.png" alt="<spring:message code='removeThisOption'/>" title="<spring:message code='removeThisOption'/>"/></a>
							</div>
						</c:if>		
					</div>
					<div class="lookupScrollBottom"></div>
					<c:if test="${fieldVO.fieldType == 'MULTI_QUERY_LOOKUP'}">
				        <a href="javascript:void(0)" onclick="Lookup.loadMultiQueryLookup(this)" fieldDef="<c:out value='${sectionField.fieldDefinition.id}'/>" class="multiLookupLink hideText" alt="<spring:message code='lookup'/>" title="<spring:message code='lookup'/>"><spring:message code='lookup'/></a>
				    </c:if>
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
				<c:when test="${fieldVO.fieldType == 'PICKLIST_DISPLAY'}">
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
				<c:when test="${fieldVO.fieldType == 'SELECTION' || fieldVO.fieldType == 'SELECTION_DISPLAY'}">
					<div class="lookupScrollTop"></div>
					<div class="lookupScrollContainer <c:if test="${fieldVO.fieldType == 'SELECTION_DISPLAY'}">readOnly</c:if>">
					    <div class="multiLookupField <c:out value='${fieldVO.entityAttributes}'/>">
							<div class="lookupScrollLeft"></div>
							<c:forEach var="val" varStatus="status" items="${fieldVO.displayValues}">
								<div id="lookup-<c:out value='${fieldVO.ids[status.index]}'/>" class="multiQueryLookupOption multiOption" selectedId="<c:out value='${fieldVO.ids[status.index]}'/>">
									<c:set var="thisVal" value="${fn:trim(val)}"/>
									<c:choose>
										<%--  TODO: remove this when automatic routing to the view page is implemented --%>
										<c:when test="${fieldVO.referenceType == 'adjustedGift'}"><c:set var="thisUrl" value="adjustedGiftView.htm" scope="page"/></c:when>
										<c:otherwise><c:set var="thisUrl" value="${fieldVO.referenceType}.htm" scope="page"/></c:otherwise>
									</c:choose>
									<c:url value="${thisUrl}" var="entityLink" scope="page">
										<c:param name="${fieldVO.referenceType}Id" value="${fieldVO.ids[status.index]}" />
										<c:param name="constituentId" value="${constituent.id}" />
									</c:url>
									<a href="<c:out value='${entityLink}'/>" target="_blank" alt="<spring:message code='gotoLink'/>" title="<spring:message code='gotoLink'/>"><c:out value='${thisVal}'/></a>
									<c:if test="${fieldVO.fieldType != 'SELECTION_DISPLAY'}">
										<a href="javascript:void(0)" onclick="PledgeRecurringGiftSelector.deleteThis(this)" class="deleteOption"><img src="images/icons/deleteRow.png" alt="<spring:message code='removeThisOption'/>" title="<spring:message code='removeThisOption'/>"/></a>
									</c:if>
								</div>
								<c:remove var="entityLink" scope="page" />
							</c:forEach>
							<div class="lookupScrollRight"></div>
					    </div>
						<c:if test="${fieldVO.fieldType != 'SELECTION_DISPLAY'}">
							<input type="hidden" name="<c:out value='${fieldVO.fieldName}'/>" value="<c:out value='${fieldVO.idsString}'/>" id="<c:out value='${fieldVO.fieldId}'/>" />
							<div class="multiQueryLookupOption multiOption noDisplay clone" selectedId="">
								<a href="" target="_blank"></a>
								<a href="javascript:void(0)" onclick="PledgeRecurringGiftSelector.deleteThis(this)" class="deleteOption"><img src="images/icons/deleteRow.png" alt="<spring:message code='removeThisOption'/>" title="<spring:message code='removeThisOption'/>"/></a>
							</div>
						</c:if>		
					</div>
					<div class="lookupScrollBottom"></div>
					<c:if test="${fieldVO.fieldType != 'SELECTION_DISPLAY'}">
				        <a href="javascript:void(0)" fieldDef="<c:out value='${sectionField.fieldDefinition.id}'/>" class="multiLookupLink hideText selectorLookup" alt="<spring:message code='lookup'/>" title="<spring:message code='lookup'/>"><spring:message code='lookup'/></a>
				    </c:if>
				</c:when>
				<c:otherwise>
					<c:out value="Field type ${fieldVO.fieldType} not yet implemented." />
				</c:otherwise>
			</c:choose>
		</li>
	</c:otherwise>
</c:choose>