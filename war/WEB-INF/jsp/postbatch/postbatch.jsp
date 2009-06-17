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
		<form:form method="post" commandName="map" >

            <h4>Edit Gift Posting Batch</h4>
            <br/>

            <input type="hidden" name="id" value="${postbatch.id}"  />

            Batch Description: <input type="text" size="60" name="description" value="<c:out value='${postbatch.description}'/>"  /><br />

            <br />
		    <h4>Edit batch selection criteria:</h4><br/>
			<table class="customFields">
                <tr >
                    <th>Field</th>
                    <th>Value To Match</th>
                </tr>
                
			<c:forEach var="field" varStatus="status" items="${postbatch.whereConditions}" >
			  <tr rowindex="${status.count}">
				<td><input id="cfname-${status.count}-key" name="cfname[${status.count}]" size="32" value="<c:out value='${field.key}'/>"  /></td>
				<td><input id="cfvalue-${status.count}-value" name="cfvalue[${status.count}]" size="60" value="<c:out value='${field.value}'/>"  /></td>
			  </tr>
			</c:forEach>

			</table>
			<br/>

			<input type="button" value="Add" class="saveButton" onclick="GenericCustomizer.addNewRow();" />   <br/><br/>
			<input type="submit" value="Generate Batch Record Set" class="saveButton" />

     		<br/><br/>

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

            <h4>Edit batch field update values:</h4><br/>
            <table class="customFields">
                <tr >
                    <th>Field</th>
                    <th>Value To Set</th>
                </tr>

            <c:forEach var="field" varStatus="status" items="${postbatch.updateFields}" >
              <tr rowindex="${status.count}">
                <td><input id="cfname-${status.count}-key" name="cfname[${status.count}]" size="32" value="<c:out value='${field.key}'/>"  /></td>
                <td><input id="cfvalue-${status.count}-value" name="cfvalue[${status.count}]" size="60" value="<c:out value='${field.value}'/>"  /></td>
              </tr>
            </c:forEach>

            </table>
            <br/>

            <input type="button" value="Add" class="saveButton" onclick="GenericCustomizer.addNewRow();" />   <br/><br/>
            <input type="button" id="post" name="post" disabled="true" value="Post Batch" class="saveButton" />

        </form:form>

        </div>
		</div>
		</div>
        
	</tiles:putAttribute>
</tiles:insertDefinition>
