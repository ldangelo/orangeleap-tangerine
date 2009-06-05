<%@ include file="/WEB-INF/jsp/include.jsp"%>
<tiles:insertDefinition name="base">
	<tiles:putAttribute name="browserTitle" value="Custom Field Wizard" />
	<tiles:putAttribute name="primaryNav" value="People" />
	<tiles:putAttribute name="secondaryNav" value="" />
	<tiles:putAttribute name="sidebarNav" value="" />
	<tiles:putAttribute name="mainContent" type="string">
		<div class="content760 mainForm">
	
		<div class="simplebox">
		
		 <form id="form" method="post" action="customField.htm">
		 
		   <h4>Custom Field Wizard</h4>
		   <br />
		 
		   <h4><span style="color: green"><c:out value="${message}" /></span></h4>
		   <h4><span style="color: red"><c:out value="${errormessage}" /></span></h4>
		
			<jsp:include page="../snippets/standardFormErrors.jsp"/>
		 	<form:errors></form:errors>
		 	
		   <br/>
		 

		   <div id="selectEntityType">
  		      Entity Type
		      <select id="entityType" name="entityType" onchange="if (this.value == 'person') { $('#selectConstituentType').show(); } else { $('#selectConstituentType').hide();  };  ">
		  	    <option value="" >Select...</option>
			    <option value="person" >Constituent</option>
			    <option value="gift" >Gift</option>
			    <option value="communicationHistory" >Touch Point</option>
		      </select>
  	     	  <br/>
		   </div>

		   <div id="selectConstituentType"  style="display:none">
			  Display for constituent type:
		      <select id="constituentType" name="constituentType" ">
			    <option value="individual" >Individual</option>
			    <option value="organization" >Organization</option>
		      </select>
  	     	  <br/>
		   </div>

		   Custom Field Name: <input id="fieldName" name="fieldName" type="text" />		   
  	       <br/>
		   Display Label: <input id="label" name="label" type="text" />		   
		   <br/>
		    
		   Field Type:
		   <select id="fieldType" name="fieldType" onchange="if (this.value == 'TEXT') { $('#selectValidation').show(); } else { $('#selectValidation').hide();  } ">
			    <option value="TEXT" >Text</option>
			    <option value="DATE" >Date</option>
			    <option value="CHECKBOX" >Checkbox</option>
			    <option value="LONG_TEXT" >Comments</option>
			    <option value="PICKLIST" >Picklist</option>
		   </select>
		   <br/>

		   <div id="selectValidation"  >
			  
			  Validation Type:
		      <select id="validationType" name="validationType" onchange="if (this.value == 'regex') { $('#selectRegex').show(); } else { $('#selectRegex').hide();  } ">
			    <option value="" >None</option>
			    <option value="email" >Valid Email Format</option>
			    <option value="url" >Web address (URL)</option>
			    <option value="numeric" >Numbers Only</option>
			    <option value="alphanumeric" >Letters or Numbers Only</option>
			    <option value="regex" >Custom Regular Expression</option>
		      </select>

	    	  <div id="selectRegex"  style="display:none">
		     	  Custom Regular Expression Text:
			      <input type="text" id="regex" name="regex" value="" />
		      </div>
		      
		      <br /><br/>
		      
		   </div>

		   <input type="submit" value="Create Custom Field" />

			
		 </form>
    
    
    		</div>
		
		</div>
		
	</tiles:putAttribute>
</tiles:insertDefinition>
