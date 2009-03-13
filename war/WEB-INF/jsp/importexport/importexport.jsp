<%@ include file="/WEB-INF/jsp/include.jsp" %>
<tiles:insertDefinition name="base">
	<tiles:putAttribute name="browserTitle" value="Import Export" />
	<tiles:putAttribute name="primaryNav" value="Admin" />
	<tiles:putAttribute name="secondaryNav" value="Import Export" />
	<tiles:putAttribute name="sidebarNav" value="Import Export" />
	<tiles:putAttribute name="mainContent" type="string">
		<div class="content760 mainForm">
			
			<h1>Export a CSV file</h1>
	        <form method="post" action="export.htm" >
	        
		        <select id="entity" name="entity"  onchange="if ( $('#entity').val() === 'gift' )  { $('#dateRange').show(); } else { $('#dateRange').hide(); }"  >
		          <option value="">Select...</option>
		          <option value="person">Constituents</option>
		          <option value="gift">Gifts</option> 
		        </select>    
		                
		        <input type="submit" value="Export" onclick="if ( $('#entity').val() === '' ) return false; else return true; "/>

		        <br/>
		        <br/>
		        
		        <div id="dateRange">
		         <div class="lookupWrapper">
					  From date: <input id="fromDate" name="fromDate" size="16" maxlength="10" class="text date" type="text" />
		         </div>
		         <div class="lookupWrapper">
					  &nbsp;&nbsp;&nbsp;To date: <input id="toDate" name="toDate" size="16" maxlength="10" class="text date" type="text" />
		         </div>
		        </div>
		        <script type="text/javascript">$('#dateRange').hide();</script>
		        
	        </form>
	        
	        <br/>
	        <br/>
	        <br/>
	
			<h1>Import a CSV file</h1>
			
	        <form method="post" action="import.htm" enctype="multipart/form-data">
		        <select name="entity">
		          <option value="">Select...</option>
		          <option value="person">Constituents</option>
 		          <option value="gift">Gifts: Cash and Checks</option> 
		        </select>            
	            <input type="file" name="file"/>
	            <input type="submit" value="Import"  />
	        </form>
        	
            <c:forEach var="line" items="${importResult}">
				<c:out value="${line}"/><br/>
       	    </c:forEach>
        	
		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>