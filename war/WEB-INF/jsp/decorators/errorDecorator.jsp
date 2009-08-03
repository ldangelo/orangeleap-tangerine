<%@ page isErrorPage="true" %>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<page:applyDecorator name="basic">
	<head>
		<title><spring:message code="error"/></title>
	</head>
	<body <decorator:getProperty property="body.class" writeEntireProperty="true"/> <decorator:getProperty property="body.style" writeEntireProperty="true"/> <decorator:getProperty property="body.id" writeEntireProperty="true"/>>
		<div>
			<div><decorator:body/></div>
			<div class="buttonRow">
				<html:mediumButton type="button" action="javascript:Utilities.goBack()" key="back"/>
			</div>
		</div>
	</body>
</page:applyDecorator>
