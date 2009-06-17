<%@ include file="/WEB-INF/jsp/include.jsp"%>
<spring:message code="exceptionHeading" var="titleText"/>
<tiles:insertDefinition name="base">
	<tiles:putAttribute name="browserTitle" value="${titleText}" />
	<tiles:putAttribute name="primaryNav" value="" />
	<tiles:putAttribute name="secondaryNav" value="" />
	<tiles:putAttribute name="mainContent" type="string">
		<div class="content760 mainForm">
			<h1><c:out value="${titleText}"/></h1>
			<div class="errorDiv"><spring:message code="exceptionOccurred"/></div>
			<div class="noDisplay"><c:out value="${exceptionStackTrace}"/></div>
		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>