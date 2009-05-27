<%@ include file="/WEB-INF/jsp/include.jsp"%>
<tiles:insertDefinition name="base">
	<tiles:putAttribute name="browserTitle" value="Create Custom Field" />
	<tiles:putAttribute name="primaryNav" value="People" />
	<tiles:putAttribute name="secondaryNav" value="" />
	<tiles:putAttribute name="sidebarNav" value="" />
	<tiles:putAttribute name="mainContent" type="string">
		<div class="content760 mainForm">
	
		<div class="simplebox">
		
		 <form id="form" method="post" action="customField.htm">
		 
		 
		   <script>
		    function populateSectionsForEntity() {
			    <%-- 
			    Use json controller to populate sections and/or section fields dropdown for entity type.
			    
			    On submit, need to flush section field caches on all tomcat instances via CACHE_GROUP table update.
			    --%>
		   </script>


		   <div id="selectEntityType">
  		      <h4>Entity Type</h4>
		      <select id="entityType" name="entityType" onchange="if (this.value == 'person') { $('#selectConstituentType').show(); } else { ('#selectConstituentType').hide();  }; populateSectionsForEntity(); ">
		  	    <option value="" >Select...</option>
			    <option value="person" >Constituent</option>
			    <option value="gift" >Gift</option>
			    <option value="communicationHistory" >Touch Point</option>
		      </select>
		   </div>

		   <div id="selectConstituentType"  style="display:none">
			  <h4>Display for constituent type:</h4>
		      <select id="constituentType" name="constituentType" ">
			    <option value="individual" >Individual</option>
			    <option value="organization" >Organization</option>
		      </select>
		   </div>

		   <div id="selectSection"  style="display:none">
			  <h4>Place in section name:</h4>
		      <select id="section" name="section" >
		  	    <option value="" >Select...</option>
		      </select>
		   </div>

		   Custom Field Name: <input id="fieldName" name="fieldName" type="text" />		   
		   Display Label: <input id="label" name="label" type="text" />		   
		    
		   <h4>Field Type:</h4>
		   <select id="fieldType" name="fieldType" onchange="if (this.value == 'TEXT') { $('#selectValidation').show(); } else { ('#selectValidation').hide();  } ">
			    <option value="TEXT" >Text</option>
			    <option value="DATE" >Date</option>
			    <option value="CHECKBOX" >Checkbox</option>
			    <option value="LONG_TEXT" >Comments</option>
		   </select>
		    
		   <div id="selectValidation"  style="display:none">
			  
			  <h4>Validation Type:</h4>
		      <select id="validation" name="validation" onchange="if (this.value == 'regex') { $('#selectRegex').show(); } else { ('#selectRegex').hide();  } ">
			    <option value="" >None</option>
			    <option value="email" >Valid Email Format</option>
			    <option value="url" >Web address (URL)</option>
			    <option value="numeric" >Numbers Only</option>
			    <option value="alphanumeric" >Letters or Numbers Only</option>
			    <option value="regex" >Custom Regular Expression</option>
		      </select>

	    	  <div id="selectRegex"  style="display:none">
		     	  <h4>Custom Regular Expression Text:</h4>
			      <input type="text" id="regex" name="regex" value="" />
		      </div>

		   </div>

			
		 </form>
    
    
    		</div>
		
		</div>
		
	</tiles:putAttribute>
</tiles:insertDefinition>
