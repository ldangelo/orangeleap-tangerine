<%@ include file="/WEB-INF/jsp/include.jsp" %>
<c:if test="${not empty conflictingNames}">
	<c:forEach var="thisName" items="${conflictingNames}" varStatus="status">
		<input type="hidden" name="source-${status.index}" id="source-${status.index}" value="<c:out value='${thisName}'/>" class="conflictingName"/>
	</c:forEach>
	<script type="text/javascript">
		$(function() {
			var names = "";
			var thisPayType = $("#paymentType").val();
			var thisTitle = (thisPayType == "ACH" ? "ACH Holder" : "Cardholder");
			$(".conflictingName").each(function() {
				names += $(this).val() + ", ";
			});
			
			if (names.length > 2) {
				names = names.substring(0, names.length - 2);
			}
			if (names.length > 0) {
				Ext.Msg.show({
					title: 'Use ' + thisTitle + ' Name?',
					msg: 'This ' + thisPayType + ' account is already in the system but associated with the ' + thisTitle + ' name(s) of ' + names + '.  Would you like to continue?',
					buttons: Ext.Msg.OKCANCEL,
					icon: Ext.MessageBox.WARNING,
					fn: function(btn, text) {
						if (btn == "ok") {
							$("div.mainForm form").eq(0).append("<input type='hidden' name='useConflictingName' id='useConflictingName' value='true'/>").submit();
						} 
					}
				});
			}
		});
	</script>
</c:if>
