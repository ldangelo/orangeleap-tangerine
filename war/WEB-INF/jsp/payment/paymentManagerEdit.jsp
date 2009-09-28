<%@ include file="/WEB-INF/jsp/include.jsp" %>
<page:applyDecorator name="form">
	<spring:message code='paymentMethods' var="titleText" scope="request" />
	<spring:message code='submit' var="submitText" />

	<c:set var="headerText" value="${titleText}" scope="request"/>

	<html>
		<head>
			<title><c:out value="${titleText} - ${requestScope.constituent.firstLast}"/></title>
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
				<%@ include file="/WEB-INF/jsp/payment/checkConflictingPaymentSource.jsp"%>

				<tangerine:fields pageName="paymentManagerEdit"/>

				<div class="formButtonFooter constituentFormButtons">
					<input type="submit" value="<spring:message code='submit'/>" class="saveButton" />
					<c:if test="${form.domainObject.id > 0}">
                        <input type="button" value="<spring:message code='cancel'/>" class="button" id="cancelButton"/>
					</c:if>
				</div>
			</form:form>
		</body>
	</html>
    <page:param name="scripts">
        <script type="text/javascript">
            $(function() {
                $("#cancelButton").click(function() {
                    OrangeLeap.gotoUrl('paymentSourceList.htm?constituentId=${constituent.id}');
                });
            });
            var ButtonPanel = Ext.extend(Ext.Panel, {
                defaultType: 'button',
                baseCls: 'x-plain',
                cls: 'btn-panel',
                renderTo: 'actions',
                menu : {
                    items: [
                        { text: '<spring:message code='enterNew'/>', handler: function() { OrangeLeap.gotoUrl("paymentManager.htm?constituentId=${requestScope.constituent.id}"); } }
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
    </page:param>
</page:applyDecorator>
