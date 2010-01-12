<%@ include file="/WEB-INF/jsp/include.jsp"%>
<page:applyDecorator name="form">
    <spring:message code='manageRules' var="titleText" />
    <html>
        <head>
            <title><c:out value="${titleText}"/></title>
        </head>
        <body>
            <div class="simplebox">
           
	           <form method="post" action="editRuleSegments.htm">
	           
	           	<input type="hidden" id="id" name="id" value="${id}" />
           
                <h4>Conditions and Consequences for Rule "${param.desc}"</h4><br/>
                <table>
                	<tr>
                	<th>Condition or Consequence</th>
                	</tr>
                    <c:forEach var="ruleSegment" items="${ruleSegmentList}">
		                <tr>
		                <td>
		                <table>
		                <tr>
		                <td  colspan="5" >
		                 <select  id="ruleSegmentType" name="ruleSegmentType"  onchange="update('${ruleSegment.id}','changeRuleSegmentType', this);" >
                   			<c:forEach var="ruleSegmentType" items="${ruleSegmentTypes}">
                     			<option <c:if test='${ruleSegment.ruleSegmentTypeId == ruleSegmentType.id}'>selected</c:if> value="<c:out value='${ruleSegmentType.id}'/>" > <c:out value='${ruleSegmentType.ruleSegmentTypePhrase}'/></option>
                   			</c:forEach>
                		 </select>
		                </td>
		                </tr>

		                <tr>
		                <td>
		                    <input id="parm0" name="parm0" value="<c:out value='${ruleSegment.parms[0]}'/>"  onchange="update('${ruleSegment.id}','changeparm-0', this);" />
		                </td>
		                <td>
		                    <input id="parm1" name="parm1" value="<c:out value='${ruleSegment.parms[1]}'/>"  onchange="update('${ruleSegment.id}','changeparm-1', this);" />
		                </td>
		                <td>
		                    <input id="parm2" name="parm2" value="<c:out value='${ruleSegment.parms[2]}'/>"  onchange="update('${ruleSegment.id}','changeparm-2', this);" />
		                </td>
		                <td>
		                    <input id="parm3" name="parm3" value="<c:out value='${ruleSegment.parms[3]}'/>"  onchange="update('${ruleSegment.id}','changeparm-3', this);" />
		                </td>
		                <td>
		                	&nbsp;&nbsp;<a href="#" onclick="update('${ruleSegment.id}','moveup');" >Move Up</a>&nbsp;&nbsp;
		                </td>
		                <td>
		                	&nbsp;&nbsp;<a href="#" onclick="update('${ruleSegment.id}','delete');" >Delete</a>&nbsp;&nbsp;
		                </td>
		                </tr>
		                
		                </table>
		                </td>
		                </tr>
                    </c:forEach>
                </table>

				<br/><br/>
				
                <a href="#" onclick="update('0','add', this);" >Add new</a><br/>

             </form>
             
             <br/>
		     <a href="#" onclick="window.location='rules.htm?ruleEventType=${ruleEventType}&id=${param.id}'; ">&laquo;Back</a>
             
             <script>
             	function update(ruleSegmentId, action, input) {
                 	
                 	var parms = 'ruleEventType=${ruleEventType}&id=${param.id}&desc=${param.desc}';
             		var data = parms + '&ruleSegmentId=' + ruleSegmentId + '&action=' + action;
             		if (input) data = data + '&' + $(input).serialize();
                    $.ajax({
                 	   type: "POST",
                 	   url: "editRuleSegments.htm",
                 	   data: data,
                 	   success: function(msg){
                 	     if (action == 'moveup' || action == 'delete' || action == 'add') {
                     	     window.location = "editRuleSegments.htm?"+parms;
                 	     }
                 	   },
             	   	   error: function(){
                   	   }
                 	 });
                	 
             	}
             </script>
             
            
            </div>
           
        </body>
    </html>
</page:applyDecorator>
