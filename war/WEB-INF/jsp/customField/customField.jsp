<%@ include file="/WEB-INF/jsp/include.jsp"%>
<page:applyDecorator name="form">
    <html>
        <head><title><spring:message code="customFieldWizard"/></title></head>
        <body>
		<div class="simplebox">

		 <form id="form" method="post" action="customField.htm">

		   <h4><spring:message code="customFieldWizard"/></h4>
		   <br />

		   <h4><span style="color: green"><c:out value="${message}" /></span></h4>
		   <h4><span style="color: red"><c:out value="${errormessage}" /></span></h4>

             <%@ include file="/WEB-INF/jsp/includes/standardFormErrors.jsp"%>
		 	<form:errors></form:errors>

		   <br/>


		   <div id="selectEntityType">
  		      Entity Type
		      <select id="entityType" name="entityType" onchange="if (this.value == 'constituent') { $('#selectConstituentType').show(); } else { $('#selectConstituentType').hide();  };  ">
		  	    <option value="" >Select...</option>
			    <option value="constituent" >Constituent</option>
			    <option value="gift" >Gift</option>
			    <option value="recurringGift" >Recurring Gift</option>
                <option value="pledge" >Pledge</option>
                <option value="giftInKind" >Gift In Kind</option>
			    <option value="communicationHistory" >Touch Point</option>
		      </select>
  	     	  <br/>
		   </div>

		   <div id="selectConstituentType"  style="display:none">
			  Display for constituent type:
		      <select id="constituentType" name="constituentType" >
			    <option value="individual" >Individual</option>
               <option value="organization" >Organization</option>
               <option value="both" >Both</option>
		      </select>
  	     	  <br/>
			  Maintain relationships between this field and:
		      <select id="relateToField" name="relateToField" >
		  	  <option value="" >None</option>
		  	  <option value="_self" >This field</option>
			   <c:forEach var="fieldDefinition" items="${fieldDefinitions}">
			     <option value="<c:out value='${fieldDefinition.id}'/>" ><c:out value='${fieldDefinition.defaultLabel}'/></option>
			   </c:forEach>
		      </select>
  	     	  <br/>

		   </div>

		   Custom Field Name: <input id="fieldName" name="fieldName" type="text" />
  	       <br/>
		   Display Label: <input id="label" name="label" type="text" />
		   <br/>

		   <script>
		   function changeFieldType(selectedValue) {
			   if (selectedValue == 'TEXT') { $('#selectValidation').show(); } else { $('#selectValidation').hide();  }
			   if (selectedValue == 'QUERY_LOOKUP' || selectedValue == 'MULTI_QUERY_LOOKUP') { $('#selectReferenceConstituentType').show(); } else { $('#selectReferenceConstituentType').hide();  }
		   }
		   </script>

		   Field Type:
		   <select id="fieldType" name="fieldType" onchange="changeFieldType(this.value);">
			    <option value="TEXT" >Text</option>
			    <option value="DATE" >Date</option>
			    <option value="CHECKBOX" >Checkbox</option>
			    <option value="LONG_TEXT" >Comments</option>
			    <option value="PICKLIST" >Picklist</option>
			    <option value="MULTI_PICKLIST" >Multiple Selection Picklist</option>
			    <option value="QUERY_LOOKUP" >Constituent Lookup</option>
			    <option value="MULTI_QUERY_LOOKUP" >Multiple Selection Constituent Lookup</option>
		   </select>
		   <br/>

		   <div id="selectReferenceConstituentType"  style="display:none">
			  Reference constituent type:
		      <select id="referenceConstituentType" name="referenceConstituentType" >
			    <option value="individual" >Individual</option>
			    <option value="organization" >Organization</option>
			    <option value="both" >Both</option>
		      </select>
  	     	  <br/>
     	    </div>


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
        </body>
    </html>
</page:applyDecorator>