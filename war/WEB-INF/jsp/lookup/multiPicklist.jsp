<%@ include file="/WEB-INF/jsp/include.jsp"%>
<div class="modalTopLeft">
	<div class="modalTopRight">
		<h4 class="dragHandle" id="modalTitle">
			<c:choose>
				<c:when test="${empty requestScope.modalTitle}"><spring:message code="selectOptions"/></c:when>
				<c:otherwise><c:out value="${requestScope.modalTitle}"/></c:otherwise>
			</c:choose>
		</h4>
		<a href="javascript:void(0)" class="jqmClose hideText"><spring:message code="close"/></a>
	</div>
</div>
<div class="modalContentWrapper">
	<div class="modalContent">
		<form method="POST" action="multiPicklist.htm">
			<table cellspacing="0" class="multiSelect">
				<thead>
			    	<tr>
	                    <th><input type="checkbox" title="<spring:message code='selectUnselectAll'/>" selection="available" id="availableAll"/><strong><spring:message code='available'/></strong></th>
			            <th class="arrows">&nbsp;</th>
	                    <th><input type="checkbox" title="<spring:message code='selectUnselectAll'/>" selection="selected" id="selectedAll"/><strong><spring:message code='selected'/></strong></th>
			        </tr>
			    </thead>
			    <tbody>
					<tr>
						<td>
							<ul id="availableOptions">
								<c:forEach items="${requestScope.picklistOptions}" var="option">
									<c:if test="${!option.selected}">		 
										<li>
											<input type="checkbox" name="<c:out value='${option.code}'/>" id="<c:out value='${option.code}'/>" reference="<c:out value='${option.reference}'/>"></input> <c:out value="${option.displayValue}"/>
										</li>
									</c:if>
								</c:forEach>
			                </ul>
			            </td>
			            <td class="arrows">
			            	<a href="javascript:void(0)" class="hideText rightArrow">Right</a>
			            	<a href="javascript:void(0)" class="hideText leftArrow">Left</a>
			            </td>
			            <td>
			                <ul id="selectedOptions">
								<c:forEach items="${requestScope.picklistOptions}" var="option">
									<c:if test="${option.selected}">		 
										<li>
											<input type="checkbox" name="<c:out value='${option.code}'/>" id="<c:out value='${option.code}'/>" reference="<c:out value='${option.reference}'/>"></input> <c:out value="${option.displayValue}"/>
										</li>
									</c:if>
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
			<input type="hidden" name="modalTitleText" id="modalTitleText" value="<c:out value='${requestScope.modalTitle}'/>"/>
		</form>
	</div>
	<div class='modalSideRight'>&nbsp;</div>
</div>
<div class="modalBottomLeft">&nbsp;<div class="modalBottomRight">&nbsp;</div></div>