<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<tiles:insertDefinition name="base">
	<tiles:putAttribute name="customHeaderContent" type="string">
        <script type="text/javascript" src="js/auditlist.js"></script>
    </tiles:putAttribute>
    <tiles:putAttribute name="browserTitle" value="Site Audit" />
	<tiles:putAttribute name="primaryNav" value="Administration" />
	<tiles:putAttribute name="secondaryNav" value="Auditing" />
	<tiles:putAttribute name="mainContent" type="string">
		<div class="content760 mainForm">

            <h1 class="x-hidden" id="auditSiteName"><security:authentication property="details.site"/></h1>
		<div id="auditHistoryGrid"></div>
        </div>
	</tiles:putAttribute>
</tiles:insertDefinition>