$(document).ready(function() {
	$("#paymentType").bind("change", function() {
		PaymentEditable.filterPaymentTypes($(this));
	});	
	$("#ach-paymentSource-tangDot-id").bind("change", function() {
		PaymentEditable.filterAchPaymentSources($(this));
	});
	$("#creditCard-paymentSource-tangDot-id").bind("change", function() {
		PaymentEditable.filterCreditCardPaymentSources($(this));
	});
	PaymentEditable.filterPaymentTypes($("#paymentType"));
});
var PaymentEditable = {
	commandObject: null,
	
	filterPaymentTypes: function($paymentTypeElem) {
		var paymentTypeVal = $paymentTypeElem.val();
		if (paymentTypeVal == "Cash" || paymentTypeVal == "Check") {
			$("#" + PaymentEditable.commandObject + "_ach").hide();
			$("#" + PaymentEditable.commandObject + "_creditCard").hide();
		} 
		else if (paymentTypeVal == "Credit Card") {
			$("#ach-paymentSource-tangDot-id").hide();
			$("#" + PaymentEditable.commandObject + "_ach").hide();
			$("#" + PaymentEditable.commandObject + "_creditCard").show();

			PaymentEditable.filterCreditCardPaymentSources($("#creditCard-paymentSource-tangDot-id"));
			$("#creditCard-paymentSource-tangDot-id").show();
		}
		else if (paymentTypeVal == "ACH") {
			$("#creditCard-paymentSource-tangDot-id").hide();
			$("#" + PaymentEditable.commandObject + "_creditCard").hide();
			$("#" + PaymentEditable.commandObject + "_ach").show();

			PaymentEditable.filterAchPaymentSources($("#ach-paymentSource-tangDot-id"));
			$("#ach-paymentSource-tangDot-id").show();
		}
		else {
			// Other type, hide everything else TODO:
			$("#" + PaymentEditable.commandObject + "_ach").hide();
			$("#" + PaymentEditable.commandObject + "_creditCard").hide();
		}
	},
		
	filterAchPaymentSources: function($achPaymentSourceElem) {
		var $option = $achPaymentSourceElem.find('option:selected');
		var optionVal = $option.val();
		$("#paymentSource-tangDot-id").val(optionVal);
		if (optionVal == "0") { // 0 is new
			$("li:has(.ea-existingAch)").hide();
			$("li:has(.ea-newAch)").show();
		}
		else if (PaymentEditable.hasId(optionVal)) {
			$("li:has(.ea-newAch)").hide();
			$("li:has(.ea-existingAch)").show();
			
			PaymentEditable.populatePaymentSourceAttributes($option);
		}
	},
		
	filterCreditCardPaymentSources: function($creditCardPaymentSourceElem) {
		var $option = $creditCardPaymentSourceElem.find('option:selected');
		var optionVal = $option.val(); 
		$("#paymentSource-tangDot-id").val(optionVal);

		if (optionVal == "0") {
			$("li:has(.ea-existingCredit)").hide();
			$("li:has(.ea-newCredit)").show();
		}
		else if (PaymentEditable.hasId(optionVal)) {
			$("li:has(.ea-newCredit)").hide();
			$("li:has(.ea-existingCredit)").show();

			PaymentEditable.populatePaymentSourceAttributes($option);
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
				$("#" + PaymentEditable.commandObject + "-paymentSource-achHolderNameReadOnly div#paymentSource-tangDot-achHolderName", "form").text(achholder);
			}
			var routing = $option.attr("routing");
			if (routing) {
				$("#" + PaymentEditable.commandObject + "-paymentSource-achRoutingNumberReadOnly div#paymentSource-tangDot-achRoutingNumberDisplay", "form").text(routing);
			}
			var acct = $option.attr("acct");
			if (acct) {
				$("#" + PaymentEditable.commandObject + "-paymentSource-achAccountNumberReadOnly div#paymentSource-tangDot-achAccountNumberDisplay", "form").text(acct);
			}
			
			// Credit Card
			var cardholder = $option.attr("cardholder");
			if (cardholder) {
				$("#" + PaymentEditable.commandObject + "-paymentSource-creditCardHolderNameReadOnly div#paymentSource-tangDot-creditCardHolderName", "form").text(cardholder);
			}
			var cardType = $option.attr("cardType");
			if (cardType) {
				$("#" + PaymentEditable.commandObject + "-paymentSource-creditCardTypeReadOnly div#paymentSource-tangDot-creditCardType", "form").text(cardType);
			}
			var number = $option.attr("number");
			if (number) {
				$("#" + PaymentEditable.commandObject + "-paymentSource-creditCardNumberReadOnly div#paymentSource-tangDot-creditCardNumberDisplay", "form").text(number);
			}
			var exp = $option.attr("exp");
			if (exp) {
				$("#" + PaymentEditable.commandObject + "-paymentSource-creditCardExpirationDisplay div#paymentSource-tangDot-creditCardExpiration", "form").text(exp);
			}
		}
		if ($selectAddress) {
			$selectAddress.each(Picklist.togglePicklist);
		}
		if ($selectPhone) {
			$selectPhone.each(Picklist.togglePicklist);
		}
	},
	
	hasId: function(val) {
		return ! isNaN(parseInt(val, 10)) && (parseInt(val, 10) > 0);
	}
};

