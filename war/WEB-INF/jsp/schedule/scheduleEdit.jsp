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
                
                        <c:if test="${sourceEntity == 'recurringgift'}">
                        <h4>Edit Recurring Gift Payment Schedule</h4><br/>
                        </c:if>
                        
                        <c:if test="${sourceEntity == 'pledge'}">
                        <h4>Edit Pledge Schedule</h4><br/>
                        </c:if>
                        
                        <c:if test="${sourceEntity == 'scheduleditem'}">
                        <h4>Edit Reminder Schedule</h4><br/>
                        </c:if>

                        <table class="customFields" cellspacing="5" >
                        
                        <tr>
                        <th></th>
                        <th></th>
                        <th>Original Scheduled Date</th>
                        <th>Actual Scheduled Date</th>
                        
                        <c:if test="${sourceEntity == 'recurringgift' || sourceEntity == 'pledge'}">
                        <th>Gift Amount</th>
                        </c:if>
                        
                        <c:if test="${sourceEntity != 'scheduleditem'}">
                        <th>Reminders</th>
                        </c:if>
                        
                        
                        </tr>
                        
                        <c:forEach var="scheduledItem" varStatus="status" items="${scheduledItems}" >
                          <tr rowindex="${status.count}" scheduledItemId="${scheduledItem.id}" >
                          <c:choose>
                          
      						<c:when test="${scheduledItem.completed}">
                            <%-- Completed items are read-only --%>
      						
      						  <td>Completed <fmt:formatDate pattern='MM/dd/yyyy' value='${scheduledItem.completionDate}' /> </td>
                              <td></td>
                              <td><fmt:formatDate pattern='MM/dd/yyyy' value='${scheduledItem.originalScheduledDate}' /></td>
                              <td><fmt:formatDate pattern='MM/dd/yyyy' value='${scheduledItem.actualScheduledDate}' /></td>
                              <td>
                              <fmt:formatNumber type='number' maxFractionDigits='2' minFractionDigits='2' value='${scheduledItem.scheduledItemAmount}' />
                              </td>
                              
      						</c:when>
      						<c:otherwise>
                            <%-- Uncompleted items can be deleted, added, or can edit (only) the actualScheduledDate and custom fields (e.g. giftAmountOverride). --%>
                              <td>
<a href="#" onclick="update(this,'save');">Save</a>/<a href="#" onclick="update(this,'add');">Add</a>/<a href="#" onclick="update(this,'delete');">Delete</a>
                              <input type="hidden" name="id" value="${scheduledItem.id}"/>
                              <input type="hidden" name="sourceEntity" value="${scheduledItem.sourceEntity}"/>
                              <input type="hidden" name="sourceEntityId" value="${scheduledItem.sourceEntityId}"/>
                              </td>
                              <td></td>
                              <td><fmt:formatDate pattern='MM/dd/yyyy' value='${scheduledItem.originalScheduledDate}'/></td>
                              <td>
                              
                              <input id="actualScheduledDate-${status.count}" name="actualScheduledDate" value="<fmt:formatDate pattern='MM/dd/yyyy' value='${scheduledItem.actualScheduledDate}'  />"  class="text date" type="text" />
                              
			                     <script type="text/javascript">
			                        new Ext.form.DateField({
			                            applyTo: 'actualScheduledDate-${status.count}',
			                            id: "actualScheduledDate-${status.count}-wrapper",
			                            format: 'm/d/Y',
			                            width: 250
			                        });
			                     </script>
                              
                              </td>
                            
                        	  <c:if test="${sourceEntity == 'recurringgift' || sourceEntity == 'pledge'}">
                              <td>
                              <input name="scheduledItemAmount" value="<fmt:formatNumber type='number' maxFractionDigits='2' minFractionDigits='2' value='${scheduledItem.scheduledItemAmount}' />" />
                              </td>
                              </c:if>

      						</c:otherwise>

    					  </c:choose>

                      	      <c:if test="${sourceEntity != 'scheduleditem'}">
                              <td><a href="scheduleEdit.htm?sourceEntity=scheduleditem&sourceEntityId=${scheduledItem.id}" >Reminders</a></td>
                       	 	  </c:if>

                          </tr>
                        </c:forEach>
                        </table>
                        
                </div>
                
                <script>
                
                function update(a, action) {
                   var data = $(a).closest("tr").find("input").serialize() + "&action=" + action;
                   $.ajax({
                	   type: "POST",
                	   url: "scheduleEdit.htm",
                	   data: data,
                	   success: function(msg){
                	     window.location = "scheduleEdit.htm?sourceEntity=${sourceEntity}&sourceEntityId=${sourceEntityId}";
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
