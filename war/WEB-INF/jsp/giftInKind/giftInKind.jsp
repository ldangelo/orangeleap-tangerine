<%@ include file="/WEB-INF/jsp/include.jsp" %>
<page:applyDecorator name="form">
	<spring:message code='enterGiftInKind' var="titleText" scope="request" />
	<spring:message code='submit' var="submitText" />
	<c:set var="headerText" value="${titleText}" scope="request"/>

	<html>
		<head>
			<title><c:out value="${titleText} - ${requestScope.constituent.firstLast}"/></title>
		</head>
		<body>
			<form:form method="post" commandName="${requestScope.commandObject}">
				<c:set var="topButtons" scope="request">
                    <table cellspacing="2">
                        <tr>
                            <td><div id="actions"></div></td>
                            <td><input type="submit" value="<c:out value='${submitText}'/>" class="saveButton" id="submitButton"/></td>
                        </tr>
                    </table>
				</c:set>
				<%@ include file="/WEB-INF/jsp/includes/formHeader.jsp"%>

				<tangerine:fields pageName="giftInKind"/>

				<div class="formButtonFooter constituentFormButtons">
					<input type="submit" value="<spring:message code='submit'/>" class="saveButton" />
					<c:if test="${pageAccess['/giftInKindList.htm']!='DENIED'}">
						<input type="button" value="<spring:message code='cancel'/>" class="saveButton" onclick="OrangeLeap.gotoUrl('giftInKindList.htm?constituentId=${constituent.id}')"/>
					</c:if>
				</div>
			</form:form>
			<page:param name="scripts">
				<script type="text/javascript" src="js/gift/giftInKindDetails.js"></script>
                <c:if test="${requestScope.form.domainObject.id > 0}">
                    <script type="text/javascript">
                        var ButtonPanel = Ext.extend(Ext.Panel, {
                            defaultType: 'button',
                            baseCls: 'x-plain',
                            cls: 'btn-panel',
                            renderTo: 'actions',
                            menu : {
                                items: [
                                    { text: '<spring:message code='enterNew'/>', handler: function() { OrangeLeap.gotoUrl("giftInKind.htm?constituentId=${requestScope.constituent.id}"); } }
                                ]
                            },
                            split: false,

                            constructor: function(buttons){
                                // apply test configs
                                for(var i = 0, b; b = buttons[i]; i++){
                                    b.menu = this.menu;
                                    b.enableToggle = this.enableToggle;
                                    b.split = this.split;
                                    b.arrowAlign = this.arrowAlign;
                                }
                                var items = [{
                                    xtype: 'box'
                                }].concat(buttons);

                                ButtonPanel.superclass.constructor.call(this, {
                                    items: buttons
                                });
                            }
                        });
                        new ButtonPanel([
                                { text: 'Actions' }
                        ]);
                    </script>
                </c:if>
			</page:param>
		</body>
	</html>
</page:applyDecorator>
