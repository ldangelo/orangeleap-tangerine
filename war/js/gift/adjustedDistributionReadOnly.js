$(document).ready(function() {
	if ($.trim($("#adjustedPaymentRequired .multiOption:visible").eq(0).attr("selectedId")) == "false") {
		$("#adjustedGift_check").hide();
		$("#adjustedGift_ach").hide();
		$("#adjustedGift_creditCard").hide();
	}
});