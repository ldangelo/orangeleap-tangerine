<%@ include file="/WEB-INF/jsp/include.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">
	<c:set var="thisUrl" value='<%=pageContext.getRequest().getAttribute("javax.servlet.forward.servlet_path")%>' scope="request"/>
	<head>
		<title><tiles:getAsString name="browserTitle"/> - Orange Leap</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <tiles:insertAttribute name="headContent"/>
        <tiles:insertAttribute name="customHeaderContent" ignore="true"/>
	</head>
	<body>
		<div class="bodyContent">
			<tiles:useAttribute name="primaryNav" />
			<tiles:useAttribute name="secondaryNav" />
			<tiles:useAttribute name="sidebarNav" ignore="true" />
			<tiles:insertAttribute name="navigation">
				<tiles:putAttribute name="primaryNav" value="${primaryNav}" />
				<tiles:putAttribute name="secondaryNav" value="${secondaryNav}" />					
			</tiles:insertAttribute>
			<div class="clearBoth"></div>
			<tiles:insertAttribute name="sideContent">
				<tiles:putAttribute name="sidebarNav" value="${sidebarNav}" />
			</tiles:insertAttribute>
			<tiles:insertAttribute name="mainContent" />
			<div class="clearBoth"></div>
			<tiles:insertAttribute name="footer" />
		</div>
		<%@ include file="/WEB-INF/jsp/snippets/modal.jsp" %>
		<c:if test="${loadGoogle==true}">
			<%@ include file="/WEB-INF/jsp/snippets/googleScripts.jsp" %>
		</c:if>
	</body>
</html>