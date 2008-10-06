<%@ include file="/WEB-INF/jsp/include.jsp"%>
<tiles:insertDefinition name="base">
	<tiles:putAttribute name="browserTitle" value="Welcome" />
	<tiles:putAttribute name="primaryNav" value="Welcome" />
	<tiles:putAttribute name="secondaryNav" value="" />
	<tiles:putAttribute name="mainContent" type="string">
	<c:set var="loadGoogle" value="true" scope="request" />
	<div class="content760 mainForm welcomePage">
		
		<h1>My Dashboard</h1>
		
		<div class="columns">
		
			<div class="column googleWidget">
				<h5>Gifts By Motivation</h5>
				<div id="chart_div"></div>
			</div>
	
			<div class="column googleWidget">
				<h5>Gifts Over Past 7 Days</h5>
				<div id="barchart"></div>
			</div>
	
			<div class="clearColumns"></div>
			
			<div class="column googleWidget">
				<h5>From The MPower Blog</h5>
				<div id="feed"></div>
			</div>
			<div class="column googleWidget">
				<h5>Donor Trends</h5>
				<div id="chart_div2"></div>
			</div>
			 
			<div class="clearColumns"></div>

		</div>
		<iframe src="http://www.google.com/calendar/hosted/credera.com/embed?src=usa__en%40holiday.calendar.google.com&ctz=America/Chicago&mode=AGENDA" style="border: 0" width="166" height="120" frameborder="0" scrolling="no"></iframe>
	</div>
	</tiles:putAttribute>
</tiles:insertDefinition>