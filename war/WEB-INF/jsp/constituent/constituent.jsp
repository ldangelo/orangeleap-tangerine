<%@ include file="/WEB-INF/jsp/include.jsp" %>
<page:applyDecorator name="form">
	<spring:message code='constituentProfile' var="titleText" scope="request" />
	<spring:message code='profileSummary' var="headerText" scope="request" />
	<spring:message code='submit' var="submitText" />

	<c:set var="headerText" value="${headerText}" scope="request"/>
	<html>
		<head>
			<title><c:out value="${titleText}"/><c:if test="${form.domainObject.id > 0}"><c:out value=" - ${requestScope.constituent.firstLast}"/></c:if></title>  
        </head>
        <body>
	        <form:form method="post" commandName="${requestScope.commandObject}" cssClass="disableForm">
		        <c:set var="topButtons" scope="request">
                    <table cellspacing="2">
                        <tr>
                            <td><div id="actions"></div></td>
                            <td><input type="submit" value="<c:out value='${submitText}'/>" class="saveButton" id="submitButton"/></td>
                        </tr>
                    </table>
		        </c:set>

		        <%@ include file="/WEB-INF/jsp/includes/formHeader.jsp"%>

		        <tangerine:fields pageName="constituent"/>

				<div class="formButtonFooter constituentFormButtons">
					<input type="submit" value="<spring:message code='submit'/>" class="saveButton" />
                    <c:if test="${pageAccess['/constituentList.htm']!='DENIED'}">
                        <input type="button" value="<spring:message code='cancel'/>" class="button" id="cancelButton"/>
                    </c:if>
				</div>
			</form:form>
	        <page:param name="scripts">
				<script type="text/javascript" src="js/contactinfo.js"></script>
				<script type="text/javascript">
                    $(function() {
                        $("#cancelButton").click(function() {
                            OrangeLeap.gotoUrl('constituentList.htm');
                        });
                    });
					<c:if test="${not empty requestScope.duplicateConstituentName}">
						$(function() {
							var duplicateConstituentName = '<c:out value="${requestScope.duplicateConstituentName}"/>';
							Ext.Msg.show({
								title: "Save Duplicate Constituent '" + duplicateConstituentName + "'?",
								msg: duplicateConstituentName + ' is a duplicate of another constituent.  Would you like to continue?',
								buttons: Ext.Msg.OKCANCEL,
								icon: Ext.MessageBox.WARNING,
								fn: function(btn, text) {
									if (btn == "ok") {
										$("div.mainForm form").eq(0).append("<input type='hidden' name='byPassDuplicateDetection' id='byPassDuplicateDetection' value='true'/>").submit();
									}
								}
							});
						});
					</c:if>
                    <c:if test="${requestScope.form.domainObject.id > 0}">
                        var ButtonPanel = Ext.extend(Ext.Panel, {
                            defaultType: 'button',
                            baseCls: 'x-plain',
                            cls: 'btn-panel',
                            renderTo: 'actions',
                            menu : {
                                items: [
                                    { text: '<spring:message code='enterNew'/>', handler: function() { OrangeLeap.gotoUrl("constituent.htm"); } }
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
                    </c:if>
				</script>
	        </page:param>
		</body>
	</html>
</page:applyDecorator>