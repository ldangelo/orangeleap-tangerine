<%@ include file="/WEB-INF/jsp/include.jsp"%>
<div class="modalTopLeft">
	<div class="modalTopRight">
		<h4 class="dragHandle" id="modalTitle"><spring:message code="selectPledgesToApplyToGift"/></h4>
		<a href="javascript:void(0)" class="jqmClose hideText"><spring:message code="close"/></a>
	</div>
</div>
<div class="modalContentWrapper">
	<div class="modalContent">
		<form method="POST" action="pledgeSelector.htm" id="pledgeSelectorForm">
			<c:if test="${empty notSelectedPledges}">
				<div class="noResults"><spring:message code="noPledgesAvailable"/></div>
			</c:if>
			<table cellspacing="0" class="multiSelect">
				<thead>
			    	<tr>
	                    <th><input type="checkbox" title="<spring:message code='selectAllOptions'/>" selection="available" id="availableAll"/><strong><spring:message code='available'/></strong></th>
	                    <th class="spacer">&nbsp;</th>
	                    <th><input type="checkbox" title="<spring:message code='selectAllOptions'/>" selection="selected" id="selectedAll"/><strong><spring:message code='selected'/></strong></th>
			        </tr>
			    </thead>
			    <tbody>
					<tr>
						<td>
							<ul id="availableOptions">
								<c:forEach items="${notSelectedPledges}" var="pledge">
									<li id="<c:out value='${pledge.id}'/>-li">
										<input type="checkbox" name="<c:out value='${pledge.id}'/>" id="pledge-<c:out value='${pledge.id}'/>"></input> <c:out value="${pledge.pledgeShortDescription}"/>
									</li>
								</c:forEach>
			                </ul>
			            </td>
	                    <td class="spacer">&nbsp;</td>
			            <td>
			                <ul id="selectedOptions">
								<c:forEach items="${selectedPledges}" var="pledge">
									<li id="<c:out value='${pledge.id}'/>-li">
										<input type="checkbox" name="<c:out value='${pledge.id}'/>" id="pledge-<c:out value='${pledge.id}'/>"></input> <c:out value="${pledge.pledgeShortDescription}"/>
									</li>
								</c:forEach>
			                </ul>
			            </td>
					</tr>
				</tbody>
			</table>
			<div class="buttonsDiv">
				<input type="button" value="<spring:message code='done'/>" class="saveButton" name="doneButton" id="doneButton" />
				<input type="button" value="<spring:message code='cancel'/>" class="saveButton" name="cancelButton" id="cancelButton" />
			</div>
		</form>
	</div>
	<div class='modalSideRight'>&nbsp;</div>
</div>
<div class="modalBottomLeft">&nbsp;<div class="modalBottomRight">&nbsp;</div></div>