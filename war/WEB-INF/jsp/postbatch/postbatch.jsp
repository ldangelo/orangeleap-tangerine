<%@ include file="/WEB-INF/jsp/include.jsp"%>
<tiles:insertDefinition name="base">
	<tiles:putAttribute name="browserTitle" value="Create Gift Posting Batch" />
	<tiles:putAttribute name="primaryNav" value="Gifts" />
	<tiles:putAttribute name="secondaryNav" value="Edit" />
	<tiles:putAttribute name="sidebarNav" value="" />
	<tiles:putAttribute name="mainContent" type="string">
		<div class="content760 mainForm">

		<div class="simplebox">

        
		<div >

        <c:if test="${postbatch.batchUpdated}">
            Batch Id: ${postbatch.id} <br />
            Batch Description: <c:out value='${postbatch.postBatchDesc}'  /><br />
            Batch Type: <c:if test="${postbatch.entity == 'gift'}">Gift</c:if> <c:if test="${postbatch.entity == 'adjustedgift'}">Adjusted Gift</c:if> <br />
            <br />
        </c:if>

        <c:if test="${!postbatch.batchUpdated}">

		<form:form method="post" commandName="postbatch" >

            <h4>Edit Gift Posting Batch</h4>
		    <br /><strong><a class="action" href="postbatchs.htm">&laquo;Back</a></strong><br/>

            <span style="color:#ff0000">${errormessage}</span>  <br />

            <br/>

            <input type="hidden" name="id" value="${postbatch.id}"  />
            Batch Id: ${postbatch.id} <br />
            Batch Description: <input type="text" size="60" name="postBatchDesc" value="<c:out value='${postbatch.postBatchDesc}'/>"  /><br />
            Batch Type: 
            <select id="entity" name="entity"   >
                <option <c:if test="${postbatch.entity == 'gift'}">selected</c:if> value="gift" />Gift</option>
                <option <c:if test="${postbatch.entity == 'adjustedgift'}">selected</c:if> value="adjustedgift" />Adjusted Gift</option>
            </select>
            <br />

            <br />
		    <h4>Batch selection criteria:</h4>
			<table class="selectFieldsTable">
                <tr >
                    <th>Field</th>
                    <th>Value To Match</th>
                </tr>

			<c:forEach var="field" varStatus="status" items="${postbatch.whereConditions}" >
			  <tr rowindex="${status.count}">
				<td>
                    <select id="sfname-${status.count}-key" name="sfname[${status.count}]"   >
                    <c:forEach var="fieldname" items="${allowedSelectFields}" >
                        <option <c:if test="${field.key == fieldname.key}">selected</c:if> value="<c:out value='${fieldname.key}'/>"  ><c:out value='${fieldname.value}'/></option>
                    </c:forEach>
                    </select>
                </td>
				<td><input id="sfvalue-${status.count}-value" name="sfvalue[${status.count}]" size="60" value="<c:out value='${field.value}'/>"  /></td>
			  </tr>
			</c:forEach>

			</table>
            <input type="button" value="Add" class="saveButton" onclick="BatchCustomizer.addNewRow('selectFieldsTable');" />   <br/><br/>

            <br/>
            Letter values are case-sensitive.  &gt;, =, and &lt; can be used with numeric and date selection values.
            <br/><br/>

			<br/>
            <h4>Batch field update values:</h4>
            <table class="updateFieldsTable">
                <tr >
                    <th>Field</th>
                    <th>Value To Set</th>
                </tr>

            <c:forEach var="field" varStatus="status" items="${postbatch.updateFields}" >
              <tr rowindex="${status.count}">
                <td>
                    <select id="ufname-${status.count}-key" name="ufname[${status.count}]"   >
                    <c:forEach var="fieldname" items="${allowedUpdateFields}" >
                        <option <c:if test="${field.key == fieldname.key}">selected</c:if>  value="<c:out value='${fieldname.key}'/>"  ><c:out value='${fieldname.value}'/></option>
                    </c:forEach>
                    </select>
                </td>
                <td><input id="ufvalue-${status.count}-value" name="ufvalue[${status.count}]" size="60" value="<c:out value='${field.value}'/>"  /></td>
              </tr>
            </c:forEach>

            </table>
            <input type="button" value="Add" class="saveButton" onclick="BatchCustomizer.addNewRow('updateFieldsTable');" />   
            <br/>

            <br/><br/>

			<input type="button" value="Save criteria and select records for update" class="saveButton" onclick="submit();" />
			
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			


        </form:form>

		</c:if>

        <hr/>
            
            <table cellspacing="8" border="0" >
                <tr>
                  <th>Id</th>
                  <th>Constituent Id</th>
                  <th>Description</th>
                  <th>TX Reference Number</th>
                </tr>
            <c:forEach var="gift" varStatus="status" items="${gifts}" >
              <tr rowindex="${status.count}">
                <td><c:out value='${gift.id}'/></td>
                <td><c:out value='${gift.constituent.id}'/></td>
                <td><c:out value='${gift.shortDescription}'/></td>
                <td><c:out value='${gift.txRefNum}'/></td>
              </tr>
            </c:forEach>
            </table >



        </div>
		</div>
		</div>
        <script>

    var BatchCustomizer = {
	addNewRow : function(tablestyle) {
		var $newRow = $("table."+tablestyle+" tr:last", "form").clone(false);
		var i = $newRow.attr("rowindex");
		var j = parseInt(i, 10) + 1;
		$newRow.attr("rowindex", j);
		$newRow.find("input").each(function() {
				var $field = $(this);
				$field.attr('name', $field.attr('name').replace(new RegExp("\\[\\d+\\]","g"), "[" + j + "]"));
				$field.attr('id', $field.attr('id').replace(new RegExp("\\-\\d+\\-","g"), "-" + j + "-"));
				$field.val("");
				$field.attr("style", "");
				$field.attr("class", "");
				$field.removeAttr("readonly");
			});
        $newRow.find("select").each(function() {
                var $field = $(this);
                $field.attr('name', $field.attr('name').replace(new RegExp("\\[\\d+\\]","g"), "[" + j + "]"));
                $field.attr('id', $field.attr('id').replace(new RegExp("\\-\\d+\\-","g"), "-" + j + "-"));
            });
		$("table."+tablestyle, "form").append($newRow);
    }
	};


   </script>
        
	</tiles:putAttribute>
</tiles:insertDefinition>
