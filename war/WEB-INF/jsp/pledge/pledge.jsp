<%@ include file="/WEB-INF/jsp/include.jsp" %>
<page:applyDecorator name="form">
	<spring:message code='enterPledge' var="titleText" scope="request" />
	<spring:message code='submit' var="submitText" />
	<c:if test="${requestScope.canApplyPayment}">
		<spring:message code='applyPayment' var="clickText" />
	</c:if>

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

				<tangerine:fields pageName="pledge"/>
				
				<div class="formButtonFooter constituentFormButtons">
					<input type="submit" value="<spring:message code='submit'/>" class="saveButton" />
					<c:if test="${pageAccess['/pledgeList.htm']!='DENIED'}">
						<input type="button" value="<spring:message code='cancel'/>" class="button" id="cancelButton"/>
					</c:if>
				</div>
			</form:form>
			<page:param name="scripts">
				<script type="text/javascript" src="js/gift/recurringGiftCalc.js"></script>
				<script type="text/javascript" src="js/gift/distribution.js"></script>
                <script type="text/javascript" src="js/pledge/pledge.js"></script>
                <script type="text/javascript">
                    $(function() {
                        $("#cancelButton").click(function() {
                            OrangeLeap.gotoUrl('pledgeList.htm?constituentId=${constituent.id}');
                        });
                    });
                    <c:if test="${requestScope.form.domainObject.id > 0}">
                        var ButtonPanel = Ext.extend(Ext.Panel, {
                            defaultType: 'button',
                            baseCls: 'x-plain',
                            cls: 'btn-panel',
                            renderTo: 'actions',
                            menu : {
                                items: [
                                    { text: '<spring:message code='enterNew'/>', handler: function() { OrangeLeap.gotoUrl("pledge.htm?constituentId=${requestScope.constituent.id}"); } },
                                    <c:if test="${not empty clickText}">
                                        { text: '<c:out value="${clickText}"/>', handler: function() { OrangeLeap.gotoUrl("gift.htm?constituentId=${requestScope.constituent.id}&selectedPledgeId=${requestScope.form.domainObject.id}"); } },
                                    </c:if>
                                    { text: '<spring:message code="paymentSchedule"/>', handler: function() { OrangeLeap.gotoUrl("scheduleEdit.htm?sourceEntity=pledge&constituentId=${param.constituentId}&sourceEntityId=${requestScope.form.domainObject.id}"); } }
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