<%@ include file="/WEB-INF/jsp/include.jsp"%>
<page:applyDecorator name="form">
    <spring:message code='manageRelationship' var='titleText'/>
    <html>
        <head>
            <title><c:out value="${titleText}"/></title>
            <style type="text/css">
                .message { color: red; }
                table.customFields th, table.customFields td { padding: 0 4px; }
                table.customFields th { white-space: nowrap; }
                table.customFields { margin-bottom: 15px; }
                table.customFields div.lookupField { min-height: 18px; padding: 0; }
                table.customFields a.lookupLink, table.customFields div.lookupField a.hideText { padding: 1px; }
                form#form { padding-bottom: 15px; }
                col.lookup { width: 225px; }
            </style>
        </head>
        <body>
            <%@ include file="/WEB-INF/jsp/includes/formHeader.jsp"%>
			<div class="simplebox">
			    <h4><c:out value='${form.fieldLabel}'/></h4>

				<div>
					<span class="message"><c:out value="${message}" /></span>
					
					<form id="form" method="post" action="relationship.htm">
					    <input type="hidden" name="constituentId" value="${form.constituent.id}" />
				        <input type="hidden" name="fieldDefinitionId" value="${form.fieldDefinition.id}" />
						<table class="customFields">
							<col class="lookup"></col>
							<col class="date"></col>
							<col class="date"></col>
							<col class="link"></col>
							<thead>
								<tr>
									<th><spring:message code='value'/></th>
									<th><spring:message code='startDate'/></th>
									<th><spring:message code='endDate'/></th>
									<th><spring:message code='customize'/></th>
								</tr>
							</thead>
							<tbody>
							 	<c:forEach var="customField" varStatus="status" items="${form.customFieldList}" >
							  		<tr rowindex="${status.count}">
										<td>
											<div class="lookupWrapper">
											    <div class="lookupField">
													<div id="lookup-cfFieldValue-${status.count}-" class="queryLookupOption" selectedId="<c:out value='${customField.value}'/>">
														<c:choose>
															<c:when test="${not empty customField.value}">
																<c:url value="constituent.htm" var="entityLink" scope="page">
																	<c:param name="constituentId" value="${customField.value}" />
																</c:url>
																<span><a href="<c:out value='${entityLink}'/>" target="_blank" alt="<spring:message code='gotoLink'/>" title="<spring:message code='gotoLink'/>"><c:out value='${form.relationshipNames[status.index]}'/></a></span>
																<c:remove var="entityLink" scope="page" />
															</c:when>
															<c:otherwise>
																<span></span>
															</c:otherwise>
														</c:choose>
														<c:if test="${not empty customField.value}">
															<a href="javascript:void(0)" onclick="Lookup.deleteOption(this)" class="deleteOption"><img src="images/icons/deleteRow.png" alt="<spring:message code='removeThisOption'/>" title="<spring:message code='removeThisOption'/>"/></a>
														</c:if>
													</div>
										        	<a href="javascript:void(0)" onclick="Lookup.loadRelationshipQueryLookup(this)" fieldDef="<c:out value='${form.fieldType}'/>" class="hideText" alt="<spring:message code='lookup'/>" title="<spring:message code='lookup'/>"><spring:message code='lookup'/></a>
											    </div>
												<input type="hidden" name="cfFieldValue[${status.count}]" value="<c:out value='${customField.value}'/>" id="cfFieldValue-${status.count}-"/>
												<div class="queryLookupOption noDisplay clone">
													<span><a href="" target="_blank"></a></span>
													<a href="javascript:void(0)" onclick="Lookup.deleteOption(this)" class="deleteOption"><img src="images/icons/deleteRow.png" alt="<spring:message code='removeThisOption'/>" title="<spring:message code='removeThisOption'/>"/></a>
												</div>
											</div>
										</td>
										<td>
											<input id="cfStartDate-${status.count}-" name="cfStartDate[${status.count}]" value="<c:out value='${customField.displayStartDate}'/>" dateinput="true"  />
										</td>
										<td>
											<input id="cfEndDate-${status.count}-" name="cfEndDate[${status.count}]" value="<c:out value='${customField.displayEndDate}'/>" dateinput="true" />
										</td>
										<td>
											<input id="cfId-${status.count}-" name="cfId[${status.count}]" value="<c:out value='${customField.id}'/>" type="hidden" />
											<a href="#" onclick="$('#customizeIndex').val($(this).prev().attr('id'));$('#form').submit();return false">+</a> 
										</td>
							 		</tr>
							 	</c:forEach>
							</tbody>
						</table>
						<div>
							<input id="customizeIndex" type="hidden" name="customizeIndex" value="" />
							<input type="button" value="<spring:message code='add'/>" class="button" onclick="GenericCustomizer.addNewRow(); decorateDateInputs();" />
							<input type="button" value="<spring:message code='save'/>" class="saveButton" onclick="$('#customizeIndex').val('');$('#form').submit();" />
						</div>
					</form>
					<script type='text/javascript'>
						function decorateDateInputs() {
						   $("input[dateinput]:not(.done)").each(
								function(i) {
									//alert('decorating '+this.id);
									$(this).parent().children("img").remove();
									
									new Ext.form.DateField({
				 			          applyTo: this.id,
				  			          id: name + "-wrapper",
				  			          format: ('m/d/Y')
				  				    });
				
									$(this).addClass('done');
								}
						   )
						};
				
						decorateDateInputs();
					</script>
					<strong><a class="action" href="relationships.htm?constituentId=${constituent.id}">&laquo;<spring:message code='back'/></a></strong>
				</div>
			</div>	
        </body>
    </html>
</page:applyDecorator>