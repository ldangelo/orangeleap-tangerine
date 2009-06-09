<%@ include file="/WEB-INF/jsp/include.jsp"%>
<tiles:insertDefinition name="base">
	<tiles:putAttribute name="browserTitle" value="Site Settings" />
	<tiles:putAttribute name="primaryNav" value="People" />
	<tiles:putAttribute name="secondaryNav" value="" />
	<tiles:putAttribute name="sidebarNav" value="" />
	<tiles:putAttribute name="mainContent" type="string">
		<div class="content760 mainForm">
	
		<div class="simplebox">
		
		 <form id="form" method="post" action="siteSettings.htm">
		 
		   <h4>Site Settings</h4>
		   <br />
		 
		   <h4><span style="color: green"><c:out value="${message}" /></span></h4>
		   <h4><span style="color: red"><c:out value="${errormessage}" /></span></h4>
		
			<jsp:include page="../snippets/standardFormErrors.jsp"/>
		 	<form:errors></form:errors>
		 	
		   <br/>
		 
  		   <h4>Payment Processing Section</h4>
  		   Merchant Account Number <input type="text" id="merchantNumber" name="merchantNumber" value="<c:out value='${site.merchantNumber}'/>"/><br/>   
  		   Merchant Bin Number <input type="text" id="merchantBin" name="merchantBin" value="<c:out value='${site.merchantBin}'/>"/><br/>   
  		   Merchant Terminal Id <input type="text" id="merchantTerminalId" name="merchantTerminalId" value="<c:out value='${site.merchantTerminalId}'/>"/><br/>   
  		   ACH Site Number <input type="text" id="achSiteNumber" name="achSiteNumber" value="<c:out value='${site.achSiteNumber}'/>"/><br/>   
  		   ACH Merchant ID <input type="text" id="achMerchantId" name="achMerchantId" value="<c:out value='${site.achMerchantId}'/>"/><br/>   
  		   ACH Rule Number <input type="text" id="achRuleNumber" name="achRuleNumber" value="<c:out value='${site.achRuleNumber}'/>"/><br/>   
  		   ACH Company Name <input type="text" id="achCompanyName" name="achCompanyName" value="<c:out value='${site.achCompanyName}'/>"/><br/>   
  		   ACH Test Mode <input type="text" id="achTestMode" name="achTestMode" value="<c:out value='${site.achTestMode}'/>"/><br/>
  		      
		 	
		   <br/>
		 
  		      
  		   <h4>Email Server Section</h4>
  		   SMTP Server Name <input type="text" id="smtpServerName" name="smtpServerName" value="<c:out value='${site.smtpServerName}'/>"/><br/>   
  		   SMTP User Name <input type="text" id="smtpAccountName" name="smtpAccountName" value="<c:out value='${site.smtpAccountName}'/>"/><br/>   
  		   SMTP Password <input type="text" id="smtpPassword" name="smtpPassword" value="<c:out value='${site.smtpPassword}'/>"/><br/>   
  		   SMTP From Address <input size="30" type="text" id="smtpFromAddress" name="smtpFromAddress" value="<c:out value='${site.smtpFromAddress}'/>"/><br/>   
  		      
		 	
		   <br/>
		 
  		   <h4>The Guru Connection</h4>
  		   User Name <input type="text" id="jasperUserId" name="jasperUserId" value="<c:out value='${site.jasperUserId}'/>"/><br/>   
  		   Password <input type="text" id="jasperPassword" name="jasperPassword" value="<c:out value='${site.jasperPassword}'/>"/><br/>   
		 	
		   <br/>
		 
 
  		   <h4>Other</h4>
  		   Major Donor Account Manager Id <input type="text" id="majorDonorAccountManagerId" name="majorDonorAccountManagerId" value="<c:out value='${site.majorDonorAccountManagerId}'/>"/><br/>  
  		      
		 	
		   <br/>
		 
		   <input type="submit" value="Save" />

		</form>
		
		</div>
		
		</div>
		
	</tiles:putAttribute>
</tiles:insertDefinition>
