<%@ include file="/WEB-INF/jsp/include.jsp"%>
<page:applyDecorator name="form">
	<spring:message code='manageRelationships' var="titleText" scope="request" />
	<spring:message code='submit' var="submitText" />
    <c:set var="headerText" value="${titleText}" scope="request"/>

    <html>
        <head>
            <title><c:out value="${titleText} - ${requestScope.constituent.firstLast}"/></title>
            <link rel="stylesheet" type="text/css" href="css/relationships.css" />
        </head>
        <body>
			<div>
				<form name="relationshipsForm" id="relationshipsForm" action="relationships.htm" method="POST">
                    <c:set var="topButtons" scope="request">
                        <input type="submit" value="<c:out value='${submitText}'/>" class="saveButton" id="submitButton"/>
                    </c:set>

                    <c:if test="${id != null}"><input type="hidden" name="id" value="<c:out value='${id}'/>" /></c:if>
                    <%@ include file="/WEB-INF/jsp/includes/constituentHeader.jsp"%>

					<c:if test="${not empty requestScope.validationErrors}">
						<div class="globalFormErrors">
							<h5><spring:message code='errorsPleaseCorrect'/></h5>
							<ul>
								<c:forEach items="${requestScope.validationErrors}" var="message">
									<li><c:out value='${message.value}'/></li>
								</c:forEach>
							</ul>
						</div>
					</c:if>		
					
					<c:set var="thisConstituentId" value="${param.constituentId}"/>
					<c:if test="${empty thisConstituentId}">
						<c:set var="thisConstituentId" value="${requestScope.constituentId}"/>
					</c:if>
					<input type="hidden" name="constituentId" value="<c:out value='${thisConstituentId}'/>"/>
					<table cellspacing="0" class="customFieldTbl">
						<col class="plusMinus"/>
						<col class="reference"/>
						<col class="date"/>
						<col class="date"/>
						<col class="delete"/>
						<thead>
							<tr>
								<th>&nbsp;</th>
								<th><span class="required">*</span> <spring:message code="value"/></th>
								<th><spring:message code="startDate"/></th>
								<th><spring:message code="endDate"/></th>
								<th>&nbsp;</th>
							</tr>
						</thead>
						<c:forEach items="${relationships}" var="fieldRelationship" varStatus="fldStatus">
							<c:set var="numCustomFields" value="${fn:length(fieldRelationship.customFields)}" scope="page"/>
							<tbody <c:if test="${fldStatus.count % 2 == 0}">class="odd"</c:if> fieldName="<c:out value="${fieldRelationship.fieldName}"/>" relationshipType="<c:out value='${fieldRelationship.relationshipType}'/>" 
								hasCustomizations="<c:out value='${fieldRelationship.hasRelationshipCustomizations}'/>" nextCustomFieldIndex="${numCustomFields + 1}">
								
								<%-- Clonable rows --%>
								<tr class="collapsed noDisplay" id="<c:out value="${fieldRelationship.fieldName}"/>-cloneRow">
									<td>
										<a href="#" class="treeNodeLink plus noDisplay" title="<spring:message code='clickShowHideExtended'/>" rowIndex=""><spring:message code='clickShowHideExtended'/></a>
									</td>
									<td>
										<div class="lookupWrapper">
										    <div class="lookupField">
												<div class="queryLookupOption" selectedId="">
													<span></span>
												</div>
									        	<a href="javascript:void(0)" onclick="Lookup.loadRelationshipQueryLookup(this)" fieldDef="" class="hideText" alt="<spring:message code='lookup'/>" title="<spring:message code='lookup'/>"><spring:message code='lookup'/></a>
										    </div>
											<input type="hidden" name="<c:out value="${fieldRelationship.fieldName}"/>-clone-hidden" id="<c:out value="${fieldRelationship.fieldName}"/>-clone-hidden" />
											<div class="queryLookupOption noDisplay clone">
												<span><a href="" target="_blank"></a></span>
												<a href="javascript:void(0)" onclick="Lookup.deleteOption(this)" class="deleteOption"><img src="images/icons/deleteRow.png" alt="<spring:message code='removeThisOption'/>" title="<spring:message code='removeThisOption'/>"/></a>
											</div>
										</div>
									</td>
									<td>
										<input id="<c:out value="${fieldRelationship.fieldName}"/>-clone-startDate" name="<c:out value="${fieldRelationship.fieldName}"/>-clone-startDate" value="" type="text" size="10" maxlength="10" />
									</td>
									<td>
										<input id="<c:out value="${fieldRelationship.fieldName}"/>-clone-endDate" name="<c:out value="${fieldRelationship.fieldName}"/>-clone-endDate" value="" type="text" size="10" maxlength="10" />
									</td>
									<td>
										<a href="javascript:void(0)" onclick="Relationships.deleteRow(this)" class="deleteButton noDisplay"><img src="images/icons/deleteRow.png" alt="<spring:message code='removeThisOption'/>" title="<spring:message code='removeThisOption'/>"/></a>
									</td>
								</tr>
								<c:if test="${fieldRelationship.hasRelationshipCustomizations}">
									<tr class="hiddenRow noDisplay" id="<c:out value="${fieldRelationship.fieldName}"/>-hiddenCloneRow">
										<td>&nbsp;</td>
										<td colspan="3">
											<table cellspacing="5">
												<tbody>
													<c:forEach items="${fieldRelationship.defaultRelationshipCustomizations}" var="customizedRelationship" varStatus="customRelStatus">
														<c:if test="${customRelStatus.count % 2 == 1}">
															<tr>
														</c:if>
																<th><c:out value='${customizedRelationship.key}'/></th>
																<td><input type="text" name="<c:out value="${fieldRelationship.fieldName}"/>-clone-<c:out value='${customizedRelationship.key}'/>" id="<c:out value="${fieldRelationship.fieldName}"/>-clone-<c:out value='${customizedRelationship.key}'/>" 
																		value="<c:out value='${customizedRelationship.value}'/>" size="25" /></td>
														<c:if test="${customRelStatus.count % 2 == 0 || customRelStatus.count == fn:length(customField.relationshipCustomizations)}">
															</tr>
														</c:if>
													</c:forEach>
												</tbody>
											</table>
										</td>
										<td>&nbsp;</td>
									</tr>
								</c:if>
								<%-- END of Clonable rows --%>
	
								<c:set var="fieldNameDisplayed" value="false" scope="page"/>
								<c:forEach items="${fieldRelationship.customFields}" var="customField" varStatus="status">
									<c:if test="${fieldNameDisplayed eq 'false'}">
										<tr>
											<c:set var="fieldNameDisplayed" value="true" scope="page"/>
											<th>&nbsp;</th>
											<th colspan="3">
												<c:out value="${fieldRelationship.fieldLabel}"/>
											</th>
											<th>&nbsp;</th>
										</tr>
									</c:if>
									<tr class="collapsed row">
										<td>
											<a href="#" class="treeNodeLink plus <c:if test="${empty customField.fieldValue || fieldRelationship.hasRelationshipCustomizations == false}">noDisplay</c:if>" title="<spring:message code='clickShowHideExtended'/>" rowIndex="<c:out value='${status.count}'/>"><spring:message code='clickShowHideExtended'/></a>
											<input type="hidden" name="customFldId-${status.count}-<c:out value="${fieldRelationship.fieldName}"/>" name="customFldId-${status.count}-<c:out value="${fieldRelationship.fieldName}"/>" value="${customField.customFieldId}"/>
										</td>
										<td>
											<div class="lookupWrapper">
												<c:set var="validationKey" scope="page" value="${fieldRelationship.fieldName}-${customField.fieldValue}"/>
											    <div class="lookupField <c:if test='${not empty requestScope.validationErrors[validationKey]}'> textError</c:if>">
													<div id="<c:out value="${fieldRelationship.fieldName}"/>-lookupOption-${status.count}" class="queryLookupOption" selectedId="<c:out value='${customField.fieldValue}'/>">
														<c:choose>
															<c:when test="${not empty customField.fieldValue}">
																<c:url value="constituent.htm" var="entityLink" scope="page">
																	<c:param name="constituentId" value="${customField.fieldValue}" />
																</c:url>
																<span><a href="<c:out value='${entityLink}'/>" target="_blank" alt="<spring:message code='gotoLink'/>" title="<spring:message code='gotoLink'/>"><c:out value='${customField.constituentName}'/></a></span>
																<c:remove var="entityLink" scope="page" />
															</c:when>
															<c:otherwise>
																<span></span>
															</c:otherwise>
														</c:choose>
														<c:if test="${not empty customField.fieldValue}">
															<a href="javascript:void(0)" onclick="Lookup.deleteOption(this)" class="deleteOption"><img src="images/icons/deleteRow.png" alt="<spring:message code='removeThisOption'/>" title="<spring:message code='removeThisOption'/>"/></a>
														</c:if>
													</div>
										        	<a href="javascript:void(0)" onclick="Lookup.loadRelationshipQueryLookup(this)" fieldDef="<c:out value='${fieldRelationship.relationshipType}'/>" class="hideText" alt="<spring:message code='lookup'/>" title="<spring:message code='lookup'/>"><spring:message code='lookup'/></a>
											    </div>
												<input type="hidden" name="fldVal-${status.count}-<c:out value="${fieldRelationship.fieldName}"/>" value="<c:out value='${customField.fieldValue}'/>" id="fldVal-${status.count}-<c:out value="${fieldRelationship.fieldName}"/>"/>
												<div class="queryLookupOption noDisplay clone">
													<span><a href="" target="_blank"></a></span>
													<a href="javascript:void(0)" onclick="Lookup.deleteOption(this)" class="deleteOption"><img src="images/icons/deleteRow.png" alt="<spring:message code='removeThisOption'/>" title="<spring:message code='removeThisOption'/>"/></a>
												</div>
											</div>
										</td>
										<td>
											<c:set var="validationKey" scope="page" value="${fieldRelationship.fieldName}-${customField.startDate}"/>
											<input id="startDt-${status.count}-<c:out value="${fieldRelationship.fieldName}"/>" name="startDt-${status.count}-<c:out value="${fieldRelationship.fieldName}"/>" 
												value="<c:out value="${customField.displayStartDate}"/>" 
												class="date <c:if test='${not empty requestScope.validationErrors[validationKey]}'> textError</c:if>" 
												type="text" size="10" maxlength="10" />
                                            <script type="text/javascript">
                                                var name = 'startDt-${status.count}-<c:out value="${fieldRelationship.fieldName}"/>';
                                                var df = new Ext.form.DateField({
                                                    applyTo: name,
                                                    id: name + "-wrapper",
                                                    format: ('m/d/Y')
                                                });
                                            </script>
										</td>
										<td>
											<c:set var="validationKey" scope="page" value="${fieldRelationship.fieldName}-${customField.endDate}"/>
											<input id="endDt-${status.count}-<c:out value="${fieldRelationship.fieldName}"/>" name="endDt-${status.count}-<c:out value="${fieldRelationship.fieldName}"/>" 
												value="<c:out value="${customField.displayEndDate}"/>" 
												class="date <c:if test='${not empty requestScope.validationErrors[validationKey]}'> textError</c:if>" 
												type="text" size="10" maxlength="10" />
                                            <script type="text/javascript">
                                                var name = 'endDt-${status.count}-<c:out value="${fieldRelationship.fieldName}"/>';
                                                var df = new Ext.form.DateField({
                                                    applyTo: name,
                                                    id: name + "-wrapper",
                                                    format: ('m/d/Y')
                                                });
                                            </script>
										</td>
										<td>
											<a href="javascript:void(0)" onclick="Relationships.deleteRow(this)" class="deleteButton <c:if test="${empty customField.fieldValue && empty customField.startDate && empty customField.endDate}">noDisplay</c:if>"><img src="images/icons/deleteRow.png" title="<spring:message code='removeThisRow'/>" alt="<spring:message code='removeThisRow'/>"/></a>
										</td>
									</tr>
									<c:if test="${fieldRelationship.hasRelationshipCustomizations}">
										<tr class="hiddenRow noDisplay">
											<td>&nbsp;</td>
											<td colspan="3">
												<table cellspacing="5">
													<tbody>
														<c:forEach items="${customField.relationshipCustomizations}" var="customizedRelationship" varStatus="customRelStatus">
															<c:if test="${customRelStatus.count % 2 == 1}">
																<tr>
															</c:if>
																	<th><c:out value='${customizedRelationship.key}'/></th>
																	<td><input type="text" name="<c:out value="${fieldRelationship.fieldName}"/>-${status.count}-<c:out value='${customizedRelationship.key}'/>" id="<c:out value="${fieldRelationship.fieldName}"/>-${status.count}-<c:out value='${customizedRelationship.key}'/>" value="<c:out value='${customizedRelationship.value}'/>" size="25" /></td>
															<c:if test="${customRelStatus.count % 2 == 0 || customRelStatus.count == fn:length(customField.relationshipCustomizations)}">
																</tr>
															</c:if>
														</c:forEach>
													</tbody>
												</table>
											</td>
											<td>&nbsp;</td>
										</tr>
									</c:if>
								</c:forEach>
							</tbody>
						</c:forEach>
					</table>
	 				<div class="formButtonFooter constituentFormButtons">
						<input type="submit" value="<c:out value='${submitText}'/>" class="saveButton" />
					</div>
				</form>
			</div>
            <page:param name="scripts">
                <script type="text/javascript" src="js/relationship/relationships.js"></script>
            </page:param>
        </body>
    </html>
</page:applyDecorator>