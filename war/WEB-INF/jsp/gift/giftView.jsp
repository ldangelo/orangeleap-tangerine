<%@ include file="/WEB-INF/jsp/include.jsp" %>
<page:applyDecorator name="form">
	<spring:message code='viewGift' var="titleText" scope="request" />
	<spring:message code='submit' var="submitText" />
	<c:if test="${requestScope.hideAdjustGiftButton == false}">
        <spring:message code='adjust' var="clickText"  />
    </c:if>

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

				<tangerine:fields pageName="giftView"/>
				
				<div class="formButtonFooter constituentFormButtons">
					<input type="submit" value="<spring:message code='submit'/>" class="saveButton" />
					<c:if test="${pageAccess['/giftList.htm']!='DENIED'}">
						<input type="button" value="<spring:message code='cancel'/>" class="saveButton" onclick="OrangeLeap.gotoUrl('giftList.htm?constituentId=${constituent.id}')"/>
					</c:if>
				</div>
			</form:form>
			<page:param name="scripts">
				<script type="text/javascript" src="js/payment/paymentTypeReadOnly.js"></script>
				<script type="text/javascript">PaymentTypeCommandObject = '<c:out value="${requestScope.domainObjectName}"/>';</script>
				<script type="text/javascript" src="js/gift/distributionReadOnly.js"></script>
                <script type="text/javascript">
                    var ButtonPanel = Ext.extend(Ext.Panel, {
                        defaultType: 'button',
                        baseCls: 'x-plain',
                        cls: 'btn-panel',
                        renderTo: 'actions',
                        menu : {
                            items: [
                                { text: '<spring:message code='enterNew'/>', handler: function() { OrangeLeap.gotoUrl("gift.htm?constituentId=${requestScope.constituent.id}"); } }
                                <c:if test="${!requestScope.hideAdjustGiftButton}">
                                    ,
                                    { text: '<c:out value='${clickText}'/>', handler: function() { OrangeLeap.gotoUrl("giftAdjustment.htm?giftId=${requestScope.form.domainObject.id}&constituentId=${requestScope.constituent.id}"); } }
                                </c:if>
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
		</body>
	</html>
</page:applyDecorator>
