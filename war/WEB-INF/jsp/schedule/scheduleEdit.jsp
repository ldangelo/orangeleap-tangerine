<%@ include file="/WEB-INF/jsp/include.jsp"%>
<page:applyDecorator name="form">
    <spring:message code='editSchedule' var="titleText" />
    <html>
        <head>
            <title><c:out value="${titleText}"/></title>
        </head>
        <body>
            <div class="simplebox">

                <div>
                
                        <h4>Edit Recurring Gift Payment Schedule</h4><br/>
                        <table class="customFields">
                        
                        <tr>
                        <th></th>
                        <th></th>
                        <th>Original Scheduled Date</th>
                        <th>Actual Scheduled Date</th>
                        
                        <c:if test="${sourceEntity == 'recurringgift'}">
                        <th>Gift Amount Override</th>
                        </c:if>
                        
                        </tr>
                        
                        <c:forEach var="scheduledItem" varStatus="status" items="${scheduledItems}" >
                          <tr rowindex="${status.count}" scheduledItemId="${scheduledItem.id}" >
                          <c:choose>
                          
      						<c:when test="${scheduledItem.completed}">
                            <%-- Completed items are read-only --%>
      						
      						  <td>Completed <c:out value='${scheduledItem.completionDate}'/> <c:out value='${scheduledItem.completionStatus}'/></td>
                              <td>
                              </td>
                              <td><c:out value='${scheduledItem.originalScheduledDate}'/></td>
                              <td><c:out value='${scheduledItem.actualScheduledDate}'/></td>
                              
      						</c:when>
      						<c:otherwise>
                            <%-- Uncompleted items can be deleted, or can edit only the actualScheduledDate and custom fields (e.g. giftAmountOverride). --%>

                              <td><a href="#" onclick="ScheduleEdit.deleteItem(this);">Delete</a>/<a href="#" onclick="ScheduleEdit.addItem(this);">Add</a>
                              <input type="hidden" name="scheduledItemId" value="${scheduledItem.id}"/>
                              <input type="hidden" name="sourceEntity" value="${scheduledItem.sourceEntity}"/>
                              <input type="hidden" name="sourceEntityId" value="${scheduledItem.sourceEntityId}"/>
                              </td>
                              <td><a href="#" onclick="ScheduleEdit.saveItem(this);">Save</a></td>
                              <td><c:out value='${scheduledItem.originalScheduledDate}'/></td>
                              <td><input name="actualScheduledDate" value="<c:out value='${scheduledItem.actualScheduledDate}'/>"/></td><%-- calendar popup --%>
                              <c:if test="${sourceEntity == 'recurringgift'}">
                              <td><input name="giftAmountOverride" value="<c:out value='${scheduledItem.customFieldMap["giftAmountOverride"].value}'/>"/></td>
                              </c:if>
                              
      						</c:otherwise>
      						
    					  </c:choose>
                          </tr>
                        </c:forEach>
                        </table>
                        
                </div>
                
                
            </div>
        </body>
    </html>
</page:applyDecorator>
