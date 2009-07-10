<%--
Menu decorator that inherits from the basic layout.
Includes the menu.
--%>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<page:applyDecorator name="basic">
	<head>
		<title><decorator:title default="Welcome"/></title>
		<decorator:head/>
	</head>
	<body	<decorator:getProperty property="body.class" writeEntireProperty="true"/> <decorator:getProperty property="body.style" writeEntireProperty="true"/> <decorator:getProperty property="body.id" writeEntireProperty="true"/>>
		<page:param name="menu"><%@ include file="/WEB-INF/jsp/includes/menu.jsp" %></page:param>
		<page:param name="sectionMenu"><decorator:getProperty property="sectionMenu"/></page:param>
		<page:param name="repeatButtons"><decorator:getProperty property="repeatButtons"/></page:param>
		<decorator:body/>
	</body>
</page:applyDecorator>