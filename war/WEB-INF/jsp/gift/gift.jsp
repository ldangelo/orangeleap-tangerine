<%@ include file="/WEB-INF/jsp/include.jsp" %>
<page:applyDecorator name="form">
	<spring:message code='enterGift' var="titleText" scope="request" />
	<spring:message code='submit' var="submitText" />
	<c:if test="${requestScope.allowReprocess}">
		<spring:message code='reprocess' var="clickText"  />
	</c:if>

	<c:set var="headerText" value="${titleText}" scope="request"/>

	<html>
		<head>
			<title><c:out value="${titleText} - ${requestScope.constituent.firstLast}"/></title>
		</head>
		<body>
			<c:choose>
				<c:when test="${requestScope.associatedPledge != null}">
					<input type="hidden" id="thisAssociatedPledge" name="thisAssociatedPledge" value="<c:out value='${requestScope.associatedPledge.shortDescription}'/>" pledgeId="<c:out value='${requestScope.associatedPledge.id}'/>"/>
				</c:when>
				<c:when test="${requestScope.associatedRecurringGift != null}">
					<input type="hidden" id="thisAssociatedRecurringGift" name="thisAssociatedRecurringGift" value="<c:out value='${requestScope.associatedRecurringGift.shortDescription}'/>" recurringGiftId="<c:out value='${requestScope.associatedRecurringGift.id}'/>"/>
				</c:when>
			</c:choose>

			<form:form method="post" commandName="${requestScope.commandObject}" id="giftForm" name="giftForm">
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

				<tangerine:fields pageName="gift"/>

				<div class="formButtonFooter constituentFormButtons">
					<input type="submit" value="<spring:message code='submit'/>" class="saveButton" />
					<c:if test="${pageAccess['/giftList.htm']!='DENIED'}">
						<input type="button" value="<spring:message code='cancel'/>" class="saveButton" onclick="OrangeLeap.gotoUrl('giftList.htm?constituentId=${constituent.id}')"/>
					</c:if>
				</div>
			</form:form>

			<page:param name="scripts">
				<script type="text/javascript" src="js/payment/paymentEditable.js"></script>
				<script type="text/javascript">PaymentEditable.commandObject = '<c:out value="${requestScope.domainObjectName}"/>';</script>
				<script type="text/javascript" src="js/gift/distribution.js"></script>
				<script type="text/javascript" src="js/gift/pledgeRecurringGiftSelector.js"></script>
				<script type="text/javascript">
                    <c:if test="${requestScope.form.domainObject.id > 0}">
                        var ButtonPanel = Ext.extend(Ext.Panel, {
                            defaultType: 'button',
                            baseCls: 'x-plain',
                            cls: 'btn-panel',
                            renderTo: 'actions',
                            menu : {
                                items: [
                                    <c:if test="${requestScope.allowReprocess}">
                                       { text: '<c:out value='${clickText}'/>', handler: function() { $("div.mainForm form").eq(0).append("<input type='hidden' name='doReprocess' id='doReprocess' value='true'/>").submit(); } },
                                    </c:if>
                                    { text: '<spring:message code='enterNew'/>', handler: function() { OrangeLeap.gotoUrl("gift.htm?constituentId=${requestScope.constituent.id}"); } }
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