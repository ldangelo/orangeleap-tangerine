$(document).ready(function() {
	$("#paymentType").bind("change", function() {
		PaymentEditable.filterPaymentTypes($(this), false);
	});	
	$("#ach-paymentSource-tangDot-id").bind("change", function() {
		PaymentEditable.filterAchPaymentSources($(this), false);
	});
	$("#creditCard-paymentSource-tangDot-id").bind("change", function() {
		PaymentEditable.filterCreditCardPaymentSources($(this), false);
	});
	PaymentEditable.filterPaymentTypes($("#paymentType"), true);
});
var PaymentEditable = {
	commandObject: null,
	
	filterPaymentTypes: function($paymentTypeElem, isLoad) {
		var paymentTypeVal = $paymentTypeElem.val();
		if (paymentTypeVal == "Cash" || paymentTypeVal == "Check") {
			$("#" + PaymentEditable.commandObject + "_ach").hide();
			$("#" + PaymentEditable.commandObject + "_creditCard").hide();
			$("#" + PaymentEditable.commandObject + "_editAch").hide();
			$("#" + PaymentEditable.commandObject + "_editCreditCard").hide();
		} 
		else if (paymentTypeVal == "Credit Card") {
			$("#ach-paymentSource-tangDot-id").hide();
			$("#" + PaymentEditable.commandObject + "_ach").hide();
			$("#" + PaymentEditable.commandObject + "_editAch").hide();

			PaymentEditable.filterCreditCardPaymentSources($("#creditCard-paymentSource-tangDot-id"), isLoad);
			$("#creditCard-paymentSource-tangDot-id").show();
		}
		else if (paymentTypeVal == "ACH") {
			$("#creditCard-paymentSource-tangDot-id").hide();
			$("#" + PaymentEditable.commandObject + "_editCreditCard").hide();
			$("#" + PaymentEditable.commandObject + "_creditCard").hide();

			PaymentEditable.filterAchPaymentSources($("#ach-paymentSource-tangDot-id"), isLoad);
			$("#ach-paymentSource-tangDot-id").show();
		}
	},
		
	filterAchPaymentSources: function($achPaymentSourceElem, isLoad) {
		var $option = $achPaymentSourceElem.find('option:selected');
		var optionVal = $option.val();
		$("#paymentSource-tangDot-id").val(optionVal);
		if (optionVal == "0") { // 0 is new
			$("#" + PaymentEditable.commandObject + "_editAch").hide();
			$("#" + PaymentEditable.commandObject + "_ach").show();
		}
		else if (PaymentEditable.hasId(optionVal)) {
			$("#" + PaymentEditable.commandObject + "_ach").hide();
			$("#" + PaymentEditable.commandObject + "_editAch").show();
			if (isLoad == false) {
				PaymentEditable.populatePaymentSourceAttributes($option);
			}
		}
	},
		
	filterCreditCardPaymentSources: function($creditCardPaymentSourceElem, isLoad) {
		var $option = $creditCardPaymentSourceElem.find('option:selected');
		var optionVal = $option.val(); 
		$("#paymentSource-tangDot-id").val(optionVal);
		if (optionVal == "0") {
			$("#" + PaymentEditable.commandObject + "_editCreditCard").hide();
			$("#" + PaymentEditable.commandObject + "_creditCard").show();
		}
		else if (PaymentEditable.hasId(optionVal)) {
			$("#" + PaymentEditable.commandObject + "_creditCard").hide();
			$("#" + PaymentEditable.commandObject + "_editCreditCard").show();
			if (isLoad == false) {
				PaymentEditable.populatePaymentSourceAttributes($option);
			}
		}
	},
		
	populatePaymentSourceAttributes: function($option) {
		if ($option.length && PaymentEditable.hasId($option.val())) {
			var addressId = $option.attr("address");
			var phoneId = $option.attr("phone");
			
			var $selectAddress = Picklist.setSelectedAddressPhoneByValue($("select#address-tangDot-id", "form"), addressId);
			var $selectPhone = Picklist.setSelectedAddressPhoneByValue($("select#phone-tangDot-id", "form"), phoneId);
			
			// ACH
			var achholder = $option.attr("achholder");
			if (achholder) {
				$("#" + PaymentEditable.commandObject + "_editAch div#paymentSource-tangDot-achHolderName", "form").text(achholder);
			}
			var routing = $option.attr("routing");
			if (routing) {
				$("#" + PaymentEditable.commandObject + "_editAch div#paymentSource-tangDot-achRoutingNumberDisplay", "form").text(routing);
			}
			var acct = $option.attr("acct");
			if (acct) {
				$("#" + PaymentEditable.commandObject + "_editAch div#paymentSource-tangDot-achAccountNumberDisplay", "form").text(acct);
			}
			
			// Credit Card
			var cardholder = $option.attr("cardholder");
			if (cardholder) {
				$("#" + PaymentEditable.commandObject + "_editCreditCard div#paymentSource-tangDot-creditCardHolderName", "form").text(cardholder);
			}
			var cardType = $option.attr("cardType");
			if (cardType) {
				$("#" + PaymentEditable.commandObject + "_editCreditCard div#paymentSource-tangDot-creditCardType", "form").text(cardType);
			}
			var number = $option.attr("number");
			if (number) {
				$("#" + PaymentEditable.commandObject + "_editCreditCard div#paymentSource-tangDot-creditCardNumberDisplay", "form").text(number);
			}
			var exp = $option.attr("exp");
			if (exp) {
				$("#" + PaymentEditable.commandObject + "_editCreditCard div#paymentSource-tangDot-creditCardExpiration", "form").text(exp);
			}
		}
		if ($selectAddress) {
//			$selectAddress.each(Picklist.togglePicklist);
		}
		if ($selectPhone) {
//			$selectPhone.each(Picklist.togglePicklist);
		}
	},
	
	hasId: function(val) {
		return ! isNaN(parseInt(val, 10)) && (parseInt(val, 10) > 0);
	}
};

