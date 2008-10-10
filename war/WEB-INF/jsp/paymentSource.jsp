<%@ include file="/WEB-INF/jsp/include.jsp" %>
		
		<mp:page pageName='paymentSource' />
		
			<c:choose>
				<c:when test="${!empty paymentSources}">

					<div class="searchResultsHeader">
						<h4 class="searchResults">Payment Sources</h4>
					</div>
	
					<mp:page pageName='paymentSource' />
					<c:forEach var="sectionDefinition" items="${sectionDefinitions}">
							<c:forEach items="${paymentSources}" var="row">
								<mp:section sectionDefinition="${sectionDefinition}"/>
								<c:set var="totalFields" value="${sectionFieldCount}"/>
								<c:forEach var="sectionField" items="${sectionFieldList}" varStatus="status">
								
									<mp:field sectionField='${sectionField}' sectionFieldList='${sectionFieldList}' model="${row}"/>
									<c:if test="${fieldVO.fieldValue!=null}">
										<p style="margin:0;">${fieldVO.labelText}: ${fieldVO.fieldValue}</p>
									</c:if>
								</c:forEach>
								<a href="paymentManager.htm?paymentSourceId=${row.id}&personId=${person.id}"">Edit</a>
								<a onclick="return(confirm('Are you sure you want to remove this payment source?'));" href="paymentSourceDelete.htm?paymentSourceId=${row.id}&personId=${person.id}"">Remove</a>
								<hr />

							</c:forEach>
					</c:forEach>
				</c:when>
				<c:when test="${paymentSources ne null}">
					<p style="margin:8px 0 6px 0;">No payment sources have been entered for this person.</p>
					<p>Would you like to <a href="paymentSource.htm?personId=${person.id}">create a new payment source</a>?</p>
				</c:when>
				<c:otherwise>
				</c:otherwise>
			</c:choose>

