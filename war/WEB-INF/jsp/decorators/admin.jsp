<%--
Admin decorator that inherits from the form layout.
--%>
<%@ include file="/WEB-INF/jsp/include.jsp" %>
<page:applyDecorator name="form">
	<html>
		<head>
			<title><decorator:title default="Welcome"/></title>
			<decorator:head/>
		</head>
		<body>
            <div id="managerGrid"></div>
            <decorator:body/>
			<page:param name="scripts">
                <script type="text/javascript" src="js/tangerine-admin.js.ycomp.js"></script>
                <decorator:getProperty property="scripts"/>
            </page:param>			
		</body>
	</html>
</page:applyDecorator>