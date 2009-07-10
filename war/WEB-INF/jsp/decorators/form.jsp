<%--
Form decorator that inherits from the basic layout.
--%>
<%@ include file="/WEB-INF/jsp/include.jsp" %>
<page:applyDecorator name="basic">
	<html>
		<head>
			<title><decorator:title default="Welcome"/></title>
			<decorator:head/>
		</head>
		<body>
			<div class="content760 mainForm">

				<decorator:body/>

			</div>
		</body>
	</html>
</page:applyDecorator>