<%@ include file="/WEB-INF/jsp/include.jsp" %>
<page:applyDecorator name="form">
    <%--<spring:message code='editSchedule' var="titleText" scope="request" />--%>
	<spring:message code='submit' var="submitText" />
    <c:if test="${sourceEntity == 'recurringgift'}"><c:set var="titleText" value="Edit Recurring Gift Payment Schedule" scope="request"/></c:if>
    <c:if test="${sourceEntity == 'pledge'}"><c:set var="titleText" value="Edit Pledge Schedule" scope="request"/></c:if>
    <c:if test="${sourceEntity == 'scheduleditem'}"><c:set var="titleText" value="Edit Reminder Schedule" scope="request"/></c:if>
	<c:set var="headerText" value="${titleText}" scope="request"/>

    <html>
        <head>
            <title><c:out value="${titleText} - ${requestScope.constituent.firstLast}"/></title>
            <style type="text/css">
                table.tablesorter tbody tr td a { font-size: .75em; }
                #backLink { margin-top: 7px; margin-right: 33px; font-size: 1.1em; text-align: right; }
            </style>
        </head>
        <body>
            <%@ include file="/WEB-INF/jsp/includes/constituentHeader.jsp"%>
            <div>
                <div>
                    <table class="tablesorter" cellspacing="5">
                        <thead>
                            <tr>
                                <th class="header"></th>
                                <th class="header">Original Scheduled Date</th>
                                <th class="header">Actual Scheduled Date</th>

                                <th class="header"><c:if test="${sourceEntity == 'recurringgift' || sourceEntity == 'pledge'}">
                                Amount
                                </c:if></th>

                                <th class="header">Completion Date</th>
                                <th class="header"></th>
                            </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="scheduledItem" varStatus="status" items="${scheduledItems}" >
                          <tr rowindex="${status.count}" scheduledItemId="${scheduledItem.id}" >
                          <c:choose>
                          
      						<c:when test="${scheduledItem.completed}">
                            <%-- Completed items are read-only --%>
      						
      						  <td></td>
                              <td><fmt:formatDate pattern='MM/dd/yyyy' value='${scheduledItem.originalScheduledDate}' /></td>
                              <td><fmt:formatDate pattern='MM/dd/yyyy' value='${scheduledItem.actualScheduledDate}' /></td>
                              <td>
                        	      <c:if test="${sourceEntity == 'recurringgift' || sourceEntity == 'pledge'}">
                                  <fmt:formatNumber type='number' maxFractionDigits='2' minFractionDigits='2' value='${scheduledItem.scheduledItemAmount}' />
                                  </c:if>
                              </td>
                              <td><fmt:formatDate pattern='MM/dd/yyyy' value='${scheduledItem.completionDate}' /> </td>
                              <td>
                                  <c:if test="${sourceEntity == 'recurringgift' || sourceEntity == 'pledge'}">
                                  <c:if test="${scheduledItem.resultEntity == 'gift' }">
                                  <a href="giftPaid.htm?giftId=${scheduledItem.resultEntityId}&constituentId=${param.constituentId}">View Gift</a>
                                  </c:if>
                                  <c:if test="${scheduledItem.resultEntity == 'adjustedgift' }">
                                  <a href="adjustedGiftView.htm?adjustedGiftId=${scheduledItem.resultEntityId}&constituentId=${param.constituentId}">View Adjusted Gift</a>
                                  </c:if>
                                  </c:if>
                              </td>
      						</c:when>
      						<c:otherwise>
                            <%-- Uncompleted items can be deleted, added, or can edit (only) the actualScheduledDate and scheduledItemAmount. --%>
                              <td>
                                <a href="#" onclick="update(this,'save');">Save</a> | <a href="#" onclick="update(this,'add');">Add</a> | <a href="#" onclick="update(this,'delete');">Delete</a>
                                  <input type="hidden" name="id" value="${scheduledItem.id}"/>
                                  <input type="hidden" name="sourceEntity" value="${scheduledItem.sourceEntity}"/>
                                  <input type="hidden" name="sourceEntityId" value="${scheduledItem.sourceEntityId}"/>
                              </td>
                              <td>
                                  <fmt:formatDate pattern='MM/dd/yyyy' value='${scheduledItem.originalScheduledDate}'/>
                              </td>
                              <td>
                                  <input id="actualScheduledDate-${status.count}" name="actualScheduledDate" value="<fmt:formatDate pattern='MM/dd/yyyy' value='${scheduledItem.actualScheduledDate}'  />"  class="text date" type="text" />

                                     <script type="text/javascript">
                                        new Ext.form.DateField({
                                            applyTo: 'actualScheduledDate-${status.count}',
                                            id: "actualScheduledDate-${status.count}-wrapper",
                                            format: 'm/d/Y',
                                            width: 150
                                        });
                                     </script>
                              </td>
                        	  <td><c:if test="${sourceEntity == 'recurringgift' || sourceEntity == 'pledge'}">
                              <input name="scheduledItemAmount" value="<fmt:formatNumber type='number' maxFractionDigits='2' minFractionDigits='2' value='${scheduledItem.scheduledItemAmount}' />" />
                              </c:if></td>
                              <td>&nbsp;</td>
                      	      <td><c:if test="${sourceEntity != 'scheduleditem' && scheduledItem.id != null }">
                              <a href="scheduleEdit.htm?sourceEntity=scheduleditem&constituentId=${param.constituentId}&sourceEntityId=${scheduledItem.id}&originalSourceEntity=${sourceEntity}&originalSourceEntityId=${scheduledItem.sourceEntityId}">Reminders</a>
                       	 	  </c:if></td>


      						</c:otherwise>

    					  </c:choose>

                          </tr>
                        </c:forEach>
                        </tbody>
                        </table>
                        
                        
                        <div id="backLink">
                            <c:if test="${sourceEntity == 'recurringgift'}">
                            <a href="recurringGift.htm?recurringGiftId=${sourceEntityId}&constituentId=${param.constituentId}">&laquo;Back</a>
                            </c:if>
                            <c:if test="${sourceEntity == 'pledge'}">
                            <a href="pledge.htm?pledgeId=${sourceEntityId}&constituentId=${param.constituentId}">&laquo;Back</a>
                            </c:if>
                            <c:if test="${sourceEntity == 'scheduleditem'}">
                           <a href="scheduleEdit.htm?sourceEntity=${param.originalSourceEntity}&constituentId=${param.constituentId}&sourceEntityId=${param.originalSourceEntityId}&originalSourceEntity=${param.originalSourceEntity}&originalSourceEntityId=${param.originalSourceEntityId}">&laquo;Back</a>
                           </c:if>
                        </div>
                        
                        
                </div>
                
                <script>
                
                function update(a, action) {
                   var data = $(a).closest("tr").find("input").serialize() + "&action=" + action;
                   $.ajax({
                	   type: "POST",
                	   url: "scheduleEdit.htm",
                	   data: data,
                	   success: function(msg){
                	     window.location = "scheduleEdit.htm?sourceEntity=${sourceEntity}&constituentId=${param.constituentId}&sourceEntityId=${sourceEntityId}&originalSourceEntity=${param.originalSourceEntity}&originalSourceEntityId=${param.originalSourceEntityId}";
                	   },
            	   	   error: function(){
                  	     alert("Invalid input.");
                  	   }
                	 });
                }
                
                </script>
                
                
            </div>
        </body>
    </html>
</page:applyDecorator>
