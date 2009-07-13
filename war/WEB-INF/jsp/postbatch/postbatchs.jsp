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


            <span style="color:#ff0000">${errormessage}</span>  <br />

            <br/>

			<table cellspacing="5" cellpadding="5">
			<tr>
			<th>Batch Id</th>
			<th>Type</th>
			<th>Description</th>
			</tr>
			<c:forEach var="postbatch" varStatus="status" items="${postbatchs}" >
			  <tr >
				<td><a href="postbatch.htm?id=${postbatch.id}">${postbatch.id}</a></td>
            	<td>${postbatch.entity}</td>
            	<td><c:out value="${postbatch.postBatchDesc}" /></td>
              </tr>	
			</c:forEach>
			</table>

            <br/>
            <br/>

            <input type="button" value="New" class="saveButton" onclick="window.location.href='postbatch.htm';" />   <br/><br/>

            <br/>

        </form:form>

        </div>
		</div>
		</div>
        
	</tiles:putAttribute>
</tiles:insertDefinition>
