<%@ include file="/WEB-INF/jsp/include.jsp"%>
<spring:message code='manageRelationships' var='titleText'/>
<spring:message code='submit' var='submitText'/>
<tiles:insertDefinition name="base">
	<tiles:putAttribute name="browserTitle" value="${titleText}" />
	<tiles:putAttribute name="primaryNav" value="People" />
	<tiles:putAttribute name="secondaryNav" value="Edit" />
	<tiles:putAttribute name="sidebarNav" value="Relationships" />
	<tiles:putAttribute name="mainContent" type="string">
		<div class="content760 mainForm">
			<c:set var="constituent" value="${constituent}" scope="request" />
		
			<c:if test="${constituent.id != null}">
				<c:set var="viewingConstituent" value="true" scope="request" />
			</c:if>
			
			<style type="text/css">
				table.customFieldTbl { padding: 2px; font-family: arial; margin: 0 auto; }
				table.customFieldTbl thead th { background-color:#E6EEEE; color:#525F95; font-size: 10px; font-weight: bold; padding:4px 4px 2px; }
				table.customFieldTbl tbody th { vertical-align: top; text-align: left; font-size: 1.1em; padding: 8px 0 4px; }
				table.customFieldTbl tbody .treeNodeLink { margin: 0 5px; }
				table.customFieldTbl tbody td div.lookupWrapper div.queryLookupOption { width: 99%; }
				table.customFieldTbl tbody td div.lookupWrapper { width: 97%; }
				table.customFieldTbl tbody td div.lookupField { padding: 1px; }
				table.customFieldTbl tbody td div.lookupField a.hideText { padding: 2px; }
				table.customFieldTbl tbody tr.collapsed td, table.customFieldTbl tbody tr.expanded td  { padding-bottom: 12px; }
				table.customFieldTbl tbody.odd { background-color: #EFEFEF; }
				table.customFieldTbl tbody tr.hiddenRow th { padding-left: 20px; } 
				table.customFieldTbl tbody tr.hiddenRow td table { padding: 0 35px 25px 0; margin: 0 auto; }
				table.customFieldTbl tbody tr.hiddenRow td table td { padding: 0 5px; }
				col.reference { width: 450px; }
				col.date { width: 105px; }
				col.plusMinus { width: 25px; }
				col.delete { width: 30px; }
			</style>
			<script type="text/javascript">
				$(function() {
					$("table.customFieldTbl a.treeNodeLink").bind("click", function(event) {
						return OrangeLeap.expandCollapse(this);
					});
	
					var fnQueryLookup = Lookup.useQueryLookup;
					Lookup.useQueryLookup = function() {
						var row = Lookup.lookupCaller.parent().parent().parent("tr");
						var $row = $(row);
						fnQueryLookup();
						
						var $thisField = Lookup.lookupCaller.parent().children(":hidden").eq(0);
						if ($thisField.val()) {
							if ($row.parent("tbody").attr("hasCustomizations") == "true") {
								$row.children("td").children(".treeNodeLink").removeClass("noDisplay");
							}
							$row.children("td").children(".deleteButton").removeClass("noDisplay");
							
							if ($row.nextAll("tr.row").length == 0) {
								Relationships.cloneRow($row.parent("tbody"));
							}
							var startDtElem = $("#" + OrangeLeap.escapeIdCharacters($thisField.attr("id").replace("fldVal", "startDt")));
							if ($.trim(startDtElem.val()) == '') {
								startDtElem.val(new Date().asString());
							}
						}
					};
					
					var fnDeleteOption = Lookup.deleteOption;
					Lookup.deleteOption = function(elem) {
						fnDeleteOption(elem);
						
						if ($(elem).parents("tbody").attr("hasCustomizations") == "true") {
							$(elem).parents("tr.row").children("td").children(".treeNodeLink").addClass("noDisplay").removeClass("minus").addClass("plus");
							$(elem).parents("tr.row").next("tr.hiddenRow").addClass("noDisplay");
						}
					};
					
					$("input.date").each(function() {
						var df = new Ext.form.DateField({
		 			        applyTo: this.id,
		  			        id: name + "-wrapper",
		  			        format: ('m/d/Y')
	  				    });
	  				});
				});
				
				var Relationships = function() {
					return {
						deleteRow: function(link) {
							var $row = $(link).parent().parent("tr");
							if ($("tr.collapsed, tr.expanded", $row.parent("tbody")).length > 1) {
								$row.next(".hiddenRow").fadeOut("fast", function() {
									$(this).remove();
								});
								$row.fadeOut("fast", function() {
									$(this).remove();
								});
							}
							else {
								alert("Sorry, you cannot delete that row since it's the only remaining row.")
							}
						},
						
						cloneRow: function(tbody) {
							var $tbody = $(tbody);
							var nextIndex = parseInt($tbody.attr("nextCustomFieldIndex"), 10) + 1;
							$tbody.attr("nextCustomFieldIndex", nextIndex);
							
							var fieldName = $tbody.attr("fieldName")
							var selectorFieldName = OrangeLeap.escapeIdCharacters(fieldName);
						
							var $newRow = $("#" + selectorFieldName + "-cloneRow").clone(true);
							$newRow.addClass("row").attr("id", "");
							$newRow.children("td").children("div").children(".treeNodeLink").attr("rowIndex", nextIndex);
							
							var newFldValId = "fldVal-" + nextIndex + "-" + fieldName;
							$newRow.children("td").children(".lookupWrapper").children("input[type=hidden]").attr("id", newFldValId).attr("name", newFldValId);
							
							var newStartDtId = "startDt-" + nextIndex + "-" + fieldName; 
							$newRow.children("td").children("#" + selectorFieldName + "-clone-startDate").attr("id", newStartDtId).attr("name", newStartDtId).addClass("date");
							
							var newEndDtId = "endDt-" + nextIndex + "-" + fieldName; 
							$newRow.children("td").children("#" + selectorFieldName + "-clone-endDate").attr("id", newEndDtId).attr("name", newEndDtId).addClass("date");
							
							$newRow.children("td").children(".lookupWrapper").children(".lookupField").children(".hideText").attr("fieldDef", $tbody.attr("relationshipType"));
	
							$tbody.append($newRow);
	
							var $newHiddenRow = $("#" + selectorFieldName + "-hiddenCloneRow").clone(true);
							$newHiddenRow.attr("id", "");
							$newHiddenRow.find("input").each(function() {
								var $elem = $(this);
								$elem.attr("name", $elem.attr("name").replace("clone", nextIndex));
								$elem.attr("id", $elem.attr("id").replace("clone", nextIndex));
							});
							$tbody.append($newHiddenRow);
							
							$newRow.removeClass("noDisplay"); 
							
							$("input.date", $newRow).each(function() {
								var df = new Ext.form.DateField({
				 			        applyTo: this.id,
				  			        id: name + "-wrapper",
				  			        format: ('m/d/Y')
			  				    });
			  				});
						}
					};
				}();
			</script>
			<div>
				<form name="relationshipsForm" id="relationshipsForm" action="relationships.htm" method="POST">
				    <jsp:include page="../snippets/constituentHeader.jsp">
						<jsp:param name="currentFunctionTitleText" value="${titleText}" />
						<jsp:param name="submitButtonText" value="${submitText}" />
				    </jsp:include>
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
										<%-- 
										<input type="hidden" name="customFldId-${status.count}-<c:out value="${fieldRelationship.fieldName}"/>" name="customFldId-${status.count}-<c:out value="${fieldRelationship.fieldName}"/>" value="${customField.customFieldId}"/>
										--%>
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
												value="<fmt:formatDate value="${customField.startDate}" pattern="MM/dd/yyyy"/>" 
												class="date <c:if test='${not empty requestScope.validationErrors[validationKey]}'> textError</c:if>" 
												type="text" size="10" maxlength="10" />
										</td>
										<td>
											<c:set var="validationKey" scope="page" value="${fieldRelationship.fieldName}-${customField.endDate}"/>
											<input id="endDt-${status.count}-<c:out value="${fieldRelationship.fieldName}"/>" name="endDt-${status.count}-<c:out value="${fieldRelationship.fieldName}"/>" 
												value="<fmt:formatDate value="${customField.endDate}" pattern="MM/dd/yyyy"/>" 
												class="date <c:if test='${not empty requestScope.validationErrors[validationKey]}'> textError</c:if>" 
												type="text" size="10" maxlength="10" />
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
 		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>
