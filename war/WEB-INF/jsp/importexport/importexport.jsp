<%@ include file="/WEB-INF/jsp/include.jsp" %>
<tiles:insertDefinition name="base">
	<tiles:putAttribute name="browserTitle" value="Import Export" />
	<tiles:putAttribute name="primaryNav" value="Admin" />
	<tiles:putAttribute name="secondaryNav" value="Import Export" />
	<tiles:putAttribute name="sidebarNav" value="Import Export" />
	<tiles:putAttribute name="mainContent" type="string">
		<div class="content760 mainForm">
		    
		    <script>
		    function Export_hideShow(val) {
		    	if ( val === 'person' || val === 'address' )  { $('#nameRange').show(); } else { $('#nameRange').hide(); }
		    	if ( val === 'gift' || val === 'distributionLine' )  { $('#dateRange').show(); } else { $('#dateRange').hide(); }
		    }
		    </script>
		
		
			
			<h1>Export a CSV file</h1>
	        <form method="post" action="export.htm" >
	        
		        <select id="exportEntity" name="entity"  onchange="Export_hideShow( $('#exportEntity').val() );"  >
		          <option value="">Select...</option>
		          <option value="person">Constituents</option>
		          <option value="address">NCOA Addresses</option>   
		          <option value="gift">Gift Summary</option> 
		          <!--  <option value="distributionLine">Gift Detail</option>    -->
		        </select>    
		                
		        <input type="submit" value="Export" onclick="if ( $('#exportEntity').val() === '' ) return false; else return true; "/>

		        <br/>
		        <br/>
		        

		        <div id="nameRange" style="display:none">
		         <div>
					  From constituent account number: <input id="fromId" name="fromId" size="16" maxlength="10" type="text" />
					  &nbsp;&nbsp;&nbsp;To constituent account number: <input id="toId" name="toId" size="16" maxlength="10" type="text" />
		         </div>
		        </div>


		        <div id="dateRange" style="display:none">
		         <div>
					  From date: <input id="fromDate" name="fromDate" size="16" maxlength="10" class="text date" type="text" />
		         </div>
		         <div>
					  To date: <input id="toDate" name="toDate" size="16" maxlength="10" class="text date" type="text" />
		         </div>
                    <script type="text/javascript">
                        new Ext.form.DateField({
                            applyTo: 'fromDate',
                            id: "fromDate-wrapper",
                            format: 'm/d/Y',
                            width: 250
                        });
                        new Ext.form.DateField({
                            applyTo: 'toDate',
                            id: "toDate-wrapper",
                            format: 'm/d/Y',
                            width: 250
                        });
                    </script>
		        </div>

		        
	        </form>
	        
	        <c:out value="${param.exportmessage}" />
	        
	        <br/>
	        <br/>
	        <br/>
	
			<h1>Import a CSV file</h1>
			
		    <script>
		    function Import_hideShow(val) {
		    	if ( val === 'address' )  { $('#importDates').show(); } else { $('#importDates').hide(); }
		    }
		    </script>
			
			
	        <form method="post" action="import.htm" enctype="multipart/form-data">
		        <select id="importEntity" name="entity" onchange="Import_hideShow( $('#importEntity').val() );" >
		          <option value="">Select...</option>
		          <option value="person">Constituents</option>
 		          <option value="gift">Gifts</option> 
 		          <option value="address">NCOA Addresses</option>   
		        </select>            
	            <input type="file" name="file"/>
	            <input type="submit" value="Import" onclick="$('#importResult').html(''); return true;" />
	            
	            <br/>
		        <br/>
		        
	            
	           <div id="importDates" style="display:none">
		         <div>
					  NCOA date: <input id="ncoaDate" name="ncoaDate" size="16" maxlength="10" class="text date" type="text" />
		         </div>
		         <div>
					  CASS date: <input id="cassDate" name="cassDate" size="16" maxlength="10" class="text date" type="text" />
		         </div>
                    <script type="text/javascript">
                        new Ext.form.DateField({
                            applyTo: 'ncoaDate',
                            id: "ncoaDate-wrapper",
                            format: 'm/d/Y',
                            width: 250
                        });
                        new Ext.form.DateField({
                            applyTo: 'cassDate',
                            id: "cassDate-wrapper",
                            format: 'm/d/Y',
                            width: 250
                        });
                    </script>
		        </div>

	            
	            
	        </form>
	        
        	<div id="importResult">
                <c:forEach var="line" items="${importResult}">
			    	<c:out value="${line}"/><br/>
         	    </c:forEach>
     		</div>
        	
		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>