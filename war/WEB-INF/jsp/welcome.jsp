<%@ include file="/WEB-INF/jsp/include.jsp"%>
<tiles:insertDefinition name="base">
	<tiles:putAttribute name="browserTitle" value="Welcome" />
	<tiles:putAttribute name="primaryNav" value="Welcome" />
	<tiles:putAttribute name="secondaryNav" value="" />
	<tiles:putAttribute name="mainContent" type="string">

	<c:set var="loadGoogle" value="true" scope="request" />
	<div class="content760 mainForm welcomePage">
		
		<h1>My Dashboard</h1>
		
		<div id="dashboard" class="columns" >
		</div>
		
		<!-- 
		
		$${build.version}  <br/>
		$${build.time}     <br/>
		<br/>
		
<%= com.orangeleap.tangerine.util.DiagUtil.getMemoryStats() %>
		
		 -->
		
	</div>
	</tiles:putAttribute>
</tiles:insertDefinition>