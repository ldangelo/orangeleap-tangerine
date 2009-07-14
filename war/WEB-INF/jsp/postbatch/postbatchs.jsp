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

            <h4>Gift Posting Batchs</h4>


            <span style="color:#ff0000">${errormessage}</span>  <br />

            <br/>

    		<form id="batchform" name="batchform" method="post" action="postbatchs.htm" >

			<table cellspacing="5" cellpadding="5" >
			<tr>
			<th>Batch Id</th>
			<th>Type</th>
			<th>Size</th>
			<th>Description</th>
			</tr>
			<c:forEach var="postbatch" varStatus="status" items="${postbatchs}" >
			  <tr >
				<td>${postbatch.id}</td>
            	<td>${postbatch.entity}</td>
            	<td>${postbatch.reviewSetSize}</td>
            	<td><c:out value="${postbatch.postBatchDesc}" /></td>
            	<c:if test="${!postbatch.batchUpdated}">
				<td><a href="postbatch.htm?id=${postbatch.id}">Edit Selection Criteria</a></td>
				<td><a href="#" onclick="$('#update').val('true');$('#id').val('${postbatch.id}');$('#batchform').submit();">Update Batch</a></td>
				<td><a href="#" onclick="$('#delete').val('true');$('#id').val('${postbatch.id}');$('#batchform').submit();">Delete</a></td>
				</c:if>
            	<c:if test="${postbatch.batchUpdated}">
				<td><a href="postbatch.htm?id=${postbatch.id}">View Updated Records</a></td>
				<td></td>
				<td></td>
				</c:if>
              </tr>	
			</c:forEach>
			</table>
			
			<input type="hidden" id="id" name="id" value="" />
			<input type="hidden" id="update" name="update" value="false" />
			<input type="hidden" id="delete" name="delete" value="false" />
			
			</form>

            <br/>
            <br/>

            <input type="button" value="New" class="saveButton" onclick="window.location.href='postbatch.htm';" />   <br/><br/>

            <br/>


        </div>
		</div>
		</div>
        
	</tiles:putAttribute>
</tiles:insertDefinition>
