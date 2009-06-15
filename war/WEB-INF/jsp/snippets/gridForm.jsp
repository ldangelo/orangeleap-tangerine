<%@ include file="/WEB-INF/jsp/include.jsp" %>
<mp:section sectionDefinition="${sectionDefinition}"/>
<c:forEach var="sectionField" items="${sectionFieldList}">
	<mp:field sectionField='${sectionField}' sectionFieldList='${sectionFieldList}' model='${row}' />
	<c:if test='${empty counter}'>
		<c:set var="counter" value="${status.index}" scope="request"/>
	</c:if>
	<c:choose>
		<c:when test="${fieldVO.fieldType == 'HIDDEN'}">
			<td class="noDisplay">
				<input type="hidden" name="<c:out value='${gridCollectionName}'/>[<c:out value='${counter}'/>].<c:out value='${fieldVO.fieldName}'/>" value="<c:out value='${fieldVO.fieldValue}'/>" id="<c:out value='${gridCollectionName}'/>-<c:out value='${counter}'/>-<c:out value='${fieldVO.fieldId}'/>" />
			</td>
		</c:when>
		<c:otherwise>
			<td>
				<c:choose>
					<c:when test="${fieldVO.fieldType == 'DATE'}">
						<fmt:formatDate value="${fieldVO.fieldValue}" pattern="MM-dd-yy h:mm a" />
					</c:when>
					<c:when test="${fieldVO.fieldType == 'CODE' || fieldVO.fieldType == 'CODE_OTHER'}">
						<div class="lookupWrapper">
							<input value="<c:out value='${fieldVO.displayValue}'/>" class="text <c:out value='${fieldVO.fieldName}'/> code" lookup="<c:out value='${fieldVO.fieldName}'/>" 
								id="display-<c:out value='${gridCollectionName}'/>-<c:out value='${counter}'/>-<c:out value='${fieldVO.fieldId}'/>"
								<c:if test="${fieldVO.fieldType == 'CODE_OTHER'}">
									otherFieldId="<c:out value='${gridCollectionName}'/>-<c:out value='${counter}'/>-<c:out value='${fieldVO.otherFieldId}'/>"
								</c:if> 
								type="text"
								codeType="<c:out value='${fieldVO.fieldName}'/>" name="display-<c:out value='${gridCollectionName}'/>[<c:out value='${counter}'/>].<c:out value='${fieldVO.fieldName}'/>" />
							<input value="<c:out value='${fieldVO.fieldValue}'/>" 
								id="hidden-<c:out value='${gridCollectionName}'/>-<c:out value='${counter}'/>-<c:out value='${fieldVO.fieldId}'/>"
								type="hidden"
								name="<c:out value='${gridCollectionName}'/>[<c:out value='${counter}'/>].<c:out value='${fieldVO.fieldName}'/>" />
							<a class="lookupLink" href="#"
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
					<c:when test="${fieldVO.fieldType == 'PICKLIST'}">
						<select name="<c:out value='${gridCollectionName}'/>[<c:out value='${counter}'/>].<c:out value='${fieldVO.fieldName}'/>" class="<c:if test="${fieldVO.cascading}">picklist </c:if><c:out value='${fieldVO.entityAttributes}'/>" 
							id="<c:out value='${gridCollectionName}'/>-<c:out value='${counter}'/>-<c:out value='${fieldVO.fieldId}'/>">
							<option value="none"><spring:message code="none"/></option>
							<c:forEach var="code" varStatus="codeStatus" items="${fieldVO.augmentedCodes}">
								<option value="<c:out value='${code}'/>" 
									<c:if test="${fieldVO.fieldValue eq code}">selected="selected"</c:if>>
									<c:out value='${fieldVO.displayValues[codeStatus.index]}'/>
								</option>
							</c:forEach>
						</select>
					</c:when>
					<c:when test="${fieldVO.fieldType == 'CHECKBOX'}">
			            <input type="hidden" name="<c:out value='${gridCollectionName}'/>[<c:out value='${counter}'/>]._<c:out value="${fieldVO.fieldName}"/>" id="<c:out value='${gridCollectionName}'/>-<c:out value='${counter}'/>-<c:out value='${fieldVO.fieldId}'/>" value="visible" />
			            <input type="checkbox" value="true" 
			                   class="checkbox <c:out value='${fieldVO.entityAttributes}'/>" 
			                   name="<c:out value='${gridCollectionName}'/>[<c:out value='${counter}'/>].<c:out value='${fieldVO.fieldName}'/>" 
			                   id="<c:out value='${gridCollectionName}'/>-<c:out value='${counter}'/>-<c:out value='${fieldVO.fieldId}'/>"  
			                   <c:if test="${fieldVO.fieldValue}">checked="checked"</c:if>/>
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
								<div id="lookup-<c:out value='${gridCollectionName}'/>-<c:out value='${counter}'/>-<c:out value='${fieldVO.fieldId}'/>" class="queryLookupOption" selectedId="<c:out value='${fieldVO.id}'/>">
									<c:choose>
										<c:when test="${not empty fieldVO.id}">
											<c:url value="${fieldVO.referenceType}.htm" var="entityLink" scope="page">
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
							<input type="hidden" name="<c:out value='${gridCollectionName}'/>[<c:out value='${counter}'/>].<c:out value='${fieldVO.fieldName}'/>" value="<c:out value='${fieldVO.id}'/>" id="<c:out value='${gridCollectionName}'/>-<c:out value='${counter}'/>-<c:out value='${fieldVO.fieldId}'/>" <c:if test="${fieldVO.fieldType == 'QUERY_LOOKUP_OTHER'}">otherFieldId="<c:out value='${gridCollectionName}'/>-<c:out value='${counter}'/>-<c:out value='${fieldVO.otherFieldId}'/>"</c:if>/>
							<c:if test="${fieldVO.fieldType != 'ASSOCIATION_DISPLAY'}">
								<div class="queryLookupOption noDisplay clone">
									<span><a href="" target="_blank"></a></span>
									<a href="javascript:void(0)" onclick="<c:out value='${pageScope.clickHandler}'/>" class="deleteOption"><img src="images/icons/deleteRow.png" alt="<spring:message code='removeThisOption'/>" title="<spring:message code='removeThisOption'/>"/></a>
								</div>
							</c:if>
						</div>
					</c:when>
					<c:when test="${fieldVO.fieldType == 'QUERY_LOOKUP_DISPLAY'}">
						<c:forEach var="val" varStatus="status" items="${fieldVO.displayValues}">
							<c:set var="thisVal" value="${fn:trim(val)}"/>
							<c:choose>
								<c:when test="${not empty fieldVO.ids[status.index]}">
									<c:url value="${fieldVO.referenceType}.htm" var="entityLink" scope="page">
										<c:param name="${fieldVO.referenceType}Id" value="${fieldVO.ids[status.index]}" />
										<c:if test="${fieldVO.referenceType != 'constituent'}">
											<c:param name="constituentId" value="${param.constituentId}" />
										</c:if>
									</c:url>
									<a href="<c:out value='${entityLink}'/>" target="_blank" alt="<spring:message code='gotoLink'/>" title="<spring:message code='gotoLink'/>"><c:out value='${thisVal}'/></a>
									<c:remove var="entityLink" scope="page" />
								</c:when>
								<c:otherwise>
									<span><c:out value='${thisVal}'/></span>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</c:when>
					<c:when test="${fieldVO.fieldType == 'CODE_OTHER_DISPLAY'}">
						<c:out value='${fieldVO.displayValue}'/>
					</c:when>
					<c:otherwise>
						<input value="<c:out value='${fieldVO.fieldValue}'/>" class="text <c:out value='${fieldVO.fieldName}'/> <c:if test="${fieldVO.fieldType == 'NUMBER'}"> number</c:if><c:if test="${fieldVO.fieldType == 'PERCENTAGE'}"> percentage</c:if>" 
							type="text" 
							name="<c:out value='${gridCollectionName}'/>[<c:out value='${counter}'/>].<c:out value='${fieldVO.fieldName}'/>"
							id="<c:out value='${gridCollectionName}'/>-<c:out value='${counter}'/>-<c:out value='${fieldVO.fieldId}'/>"/>
					</c:otherwise>
				</c:choose>
			</td>
		</c:otherwise>
	</c:choose>
</c:forEach>