$(document).ready(function() {
	PaymentTypeReadOnly.showHide();
});
var PaymentTypeReadOnly = {
	showHide: function() {
		var paymentTypeVal = jQuery.trim($("#paymentType div.multiPicklistOption:visible").text());
		if (paymentTypeVal == "Cash") {
			$("#" + PaymentTypeCommandObject + "_ach").hide();
			$("#" + PaymentTypeCommandObject + "_creditCard").hide();
			$("#" + PaymentTypeCommandObject + "_check").hide();
		} 
		else if (paymentTypeVal == "Check") {
			$("#" + PaymentTypeCommandObject + "_ach").hide();
			$("#" + PaymentTypeCommandObject + "_creditCard").hide();
			$("#" + PaymentTypeCommandObject + "_check").show();
		}
		else if (paymentTypeVal == "Credit Card") {
			$("#" + PaymentTypeCommandObject + "_ach").hide();
			$("#" + PaymentTypeCommandObject + "_check").hide();
			$("#" + PaymentTypeCommandObject + "_creditCard").show();
		}
		else if (paymentTypeVal == "ACH") {
			$("#" + PaymentTypeCommandObject + "_creditCard").hide();
			$("#" + PaymentTypeCommandObject + "_check").hide();
			$("#" + PaymentTypeCommandObject + "_ach").show();
		}
	}
};

