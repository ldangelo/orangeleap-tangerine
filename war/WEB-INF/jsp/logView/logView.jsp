<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<tiles:insertDefinition name="base">
	<tiles:putAttribute name="customHeaderContent" type="string">
        <script type="text/javascript" src="js/logview.js"></script>
    </tiles:putAttribute>
    <tiles:putAttribute name="browserTitle" value="Log View" />
	<tiles:putAttribute name="primaryNav" value="Administration" />
	<tiles:putAttribute name="secondaryNav" value="Log View" />
	<tiles:putAttribute name="mainContent" type="string">
		<div class="content760 mainForm">

            <h1 class="x-hidden" id="auditSiteName"><security:authentication property="site"/></h1>


		    <div id="logViewGrid"></div>

        </div>
	</tiles:putAttribute>
</tiles:insertDefinition>