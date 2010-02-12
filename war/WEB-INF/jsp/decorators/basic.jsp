<%--
This is the basic decorator.
It includes the main style sheet, header, footer, and util javascripts.
No menus are included.
--%>
<%@ include file="/WEB-INF/jsp/include.jsp" %>

<c:set var="thisUrl" value='<%=pageContext.getRequest().getAttribute("javax.servlet.forward.servlet_path")%>' scope="request"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title><decorator:title default="Welcome"/> - <spring:message code="appName"/></title>
		<%@ include file="/WEB-INF/jsp/includes/headContent.jsp" %>
		<decorator:head/>
	</head>
	<body>
		<div class="bodyContent">

			<%@ include file="/WEB-INF/jsp/includes/navigation.jsp" %>
			<div class="clearBoth"></div>

			<page:param name="sidebarNav"><decorator:getProperty property="sidebarNav"/></page:param>
			<%@ include file="/WEB-INF/jsp/includes/sideContent.jsp" %>
			
			<decorator:body/>
			<div class="clearBoth"></div>

			<%@ include file="/WEB-INF/jsp/includes/footer.jsp" %>
		</div>
		<%@ include file="/WEB-INF/jsp/includes/modal.jsp" %>
		<decorator:getProperty property="scripts"/>
		<script type="text/javascript" src="js/sidebar.js"></script>
		<script type="text/javascript" src="js/history.js"></script>
		<c:if test="${requestScope.loadGoogle}">
			<%@ include file="/WEB-INF/jsp/includes/googleScripts.jsp" %>
		</c:if>
	</body>
</html>
