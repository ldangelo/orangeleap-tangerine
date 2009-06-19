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
		<form:form method="post" commandName="postbatch" >

            <h4>Edit Gift Posting Batch</h4>


            <span style="font-color:red">${errormessage}</span>

            <br/>

            <input type="hidden" name="id" value="${postbatch.id}"  />

            Batch Description: <input type="text" size="60" name="description" value="<c:out value='${postbatch.description}'/>"  /><br />

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

            <br/>
            Letter values are case-sensitive.  &gt;, =, and &lt; can be used with numeric and date selection values.   
            <br/><br/>

			<input type="button" value="Mark records for update" class="saveButton" onclick="$('#post').val('false'); submit();" />
            <input type="hidden" id="post" name="post" value="false"  />
            
            <!-- TODO add 'Are you sure' confirmation: -->
            <input type="button" value="Post Batch" class="saveButton" onclick="  $('#post').val('true'); submit();" />



     		<hr/>

            <table >
            <c:forEach var="gift" varStatus="status" items="${gifts}" >
              <tr rowindex="${status.count}">
                <td><c:out value='${gift.id}'/></td>
                <td><c:out value='${gift.constituent.id}'/></td>
                <td><c:out value="<c:out value='${gift.shortDescription}'/>"/></td>
                <td><c:out value='${gift.txRefNum}'/></td>
              </tr>
            </c:forEach>
            </table >


        </form:form>

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
