<%--
	Decorator that inherits from the basic menu layout.
	To be used on detailed view pages that are handled 
	by CrestWizardController in the back end.  
	
	Since this refers to document.forms[0], the page being decorated 
	should have its main form as the first one on the page.  
--%>
<%@ include file="/WEB-INF/jsp/includes/include.jsp" %>
<page:applyDecorator name="formDecorator">
	<head>
		<title><decorator:title default="details"/></title>
		<decorator:head/>
        <script type="text/javascript">
            var Details = {};
            Details.hiliteRules = {
                'ol li': function(elem) {
                    elem.onmouseover = function() {
								this.style.backgroundColor = "#FFFF00";
                    },
                    elem.onmouseout = function() {
                        this.style.backgroundColor = "#FFFFFF";
                    },
                    elem = null;
                }
            };

            Behaviour.register(Details.hiliteRules);
        </script>
        <link rel="stylesheet" type="text/css" href="css/details/tabs.css"/>
		<!--[if lte IE 6]><link rel="stylesheet" type="text/css" href="css/details/tabs_IE6.css"/><![endif]-->
<%--
		<style type="text/css">
			.entryTable div {
				position: relative;
			}
			.entryTable input[type=text], .entryTable textarea, .entryTable select {
				border-top: 2px solid transparent;
				border-left: 2px solid transparent;
				border-bottom: 1px solid transparent;
				border-right: 1px solid transparent;
			}

			.entryTable div select {
				position: absolute;
				top: -10px;
				left: 0;
				clip: rect(auto, auto, auto, -22);
			}

			.entryTable input[type=text]:active, .entryTable input[type=text]:focus, .entryTable input[type=text]:hover,
				.entryTable textarea:active, .entryTable textarea:focus, .entryTable textarea:hover,
				.entryTable select:active, .entryTable select:focus, .entryTable select:hover {
				border-top: 2px inset #C0C0C0;
				border-left: 2px inset #C0C0C0;
				border-bottom: 1px inset #C0C0C0;
				border-right: 1px inset #C0C0C0;
			}

			.entryTable div select:active, .entryTable div select:focus, .entryTable div select:hover {
				position: absolute;
				top: -10px;
				left: 0;
				clip: rect(auto, auto, auto, auto);
			}
		</style>
--%>
	</head>
	<body <decorator:getProperty property="body.class" writeEntireProperty="true"/> <decorator:getProperty property="body.style" writeEntireProperty="true"/> <decorator:getProperty property="body.id" writeEntireProperty="true"/>>
		<page:param name="repeatButtons"><decorator:getProperty property="repeatButtons"/></page:param>
		<div class="tabs">
			<ul>
				<li><a href="javascript:void(0)" class="selectedTab"><c:choose><c:when test="${formBean.dObjId < 1}"><spring:message code="createNew"/> <c:out value="${formBean.dObj.type}"/></c:when><c:otherwise><c:out value="${formBean.dObj.type}: ${formBean.dObj.name}"/><c:if test="${!formBean.dObj.isActive}"> <spring:message code="deactivated"/></c:if></c:otherwise></c:choose></a></li>
				<c:if test="${formBean.dObjId > 0}">
					<%-- Show only if this is an existing object with existing history --%>
					<li><a href="javascript:WizardForm.navigate('PAGE_AUDIT', document.forms[0].id)" title="<spring:message code='clickAuditHistory'/>"><spring:message code="auditHistory"/> (<c:out value="${fn:length(formBean.dObj.auditHistory)}"/>)</a></li>
				</c:if>
			</ul>
		</div>
		<div class="tabsSpacer"><div class="tabsBorderWrapper"><div class="tabsBorderLeft"></div><div class="tabsBorderRight"></div></div></div>
		<div class="tabsWrapper">
			<div class="tabsWrapperLeft"></div>
			<div class="tabsWrapperRight"></div>
			<div class="tabContent">
				<decorator:body/>
			</div>
		</div>
	</body>
</page:applyDecorator>
