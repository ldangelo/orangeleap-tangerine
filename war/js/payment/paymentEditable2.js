$(document).ready(function() {
	$("#paymentType").bind("change", function() {
		PaymentEditable.filterPaymentTypes($(this), false);
	});	
	$("#ach-paymentSource-td-id").bind("change", function() {
		PaymentEditable.filterAchPaymentSources($(this), false);
	});
	$("#creditCard-paymentSource-td-id").bind("change", function() {
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
		} 
		else if (paymentTypeVal == "Credit Card") {
			$("#ach-paymentSource-td-id").hide();
			$("#" + PaymentEditable.commandObject + "_ach").hide();
			$("#" + PaymentEditable.commandObject + "_creditCard").show();

			PaymentEditable.filterCreditCardPaymentSources($("#creditCard-paymentSource-td-id"), isLoad);
			$("#creditCard-paymentSource-td-id").show();
		}
		else if (paymentTypeVal == "ACH") {
			$("#creditCard-paymentSource-td-id").hide();
			$("#" + PaymentEditable.commandObject + "_creditCard").hide();
			$("#" + PaymentEditable.commandObject + "_ach").show();

			PaymentEditable.filterAchPaymentSources($("#ach-paymentSource-td-id"), isLoad);
			$("#ach-paymentSource-td-id").show();
		}
		else {
			// Other type, hide everything else TODO:
			$("#" + PaymentEditable.commandObject + "_ach").hide();
			$("#" + PaymentEditable.commandObject + "_creditCard").hide();
		}
	},
		
	filterAchPaymentSources: function($achPaymentSourceElem, isLoad) {
		var $option = $achPaymentSourceElem.find('option:selected');
		var optionVal = $option.val();
		$("#paymentSource-td-id").val(optionVal);
		if (optionVal == "0") { // 0 is new
			$("li:has(.ea-existingAch)").hide();
			$("li:has(.ea-newAch)").show();
		}
		else if (PaymentEditable.hasId(optionVal)) {
			$("li:has(.ea-newAch)").hide();
			$("li:has(.ea-existingAch)").show();
			PaymentEditable.populatePaymentSourceAttributes($option, isLoad);
		}
	},
		
	filterCreditCardPaymentSources: function($creditCardPaymentSourceElem, isLoad) {
		var $option = $creditCardPaymentSourceElem.find('option:selected');
		var optionVal = $option.val(); 
		$("#paymentSource-td-id").val(optionVal);

		if (optionVal == "0") {
			$("li:has(.ea-existingCredit)").hide();
			$("li:has(.ea-newCredit)").show();
		}
		else if (PaymentEditable.hasId(optionVal)) {
			$("li:has(.ea-newCredit)").hide();
			$("li:has(.ea-existingCredit)").show();
			PaymentEditable.populatePaymentSourceAttributes($option, isLoad);
		}
	},
		
	populatePaymentSourceAttributes: function($option, isLoad) {
		if ($option.length && PaymentEditable.hasId($option.val())) {

			// ACH
			var achholder = $option.attr("achholder");
			if (achholder) {
				$("#" + PaymentEditable.commandObject + "-paymentSource-achHolderNameReadOnly div#paymentSource-td-achHolderName", "form").text(achholder);
			}
			var routing = $option.attr("routing");
			if (routing) {
				$("#" + PaymentEditable.commandObject + "-paymentSource-achRoutingNumberReadOnly div#paymentSource-td-achRoutingNumberDisplay", "form").text(routing);
			}
			var acct = $option.attr("acct");
			if (acct) {
				$("#" + PaymentEditable.commandObject + "-paymentSource-achAccountNumberReadOnly div#paymentSource-td-achAccountNumberDisplay", "form").text(acct);
			}

			// Credit Card
			var cardholder = $option.attr("cardholder");
			if (cardholder) {
				$("#" + PaymentEditable.commandObject + "-paymentSource-creditCardHolderNameReadOnly div#paymentSource-td-creditCardHolderName", "form").text(cardholder);
			}
			var cardType = $option.attr("cardType");
			if (cardType) {
				$("#" + PaymentEditable.commandObject + "-paymentSource-creditCardTypeReadOnly div#paymentSource-td-creditCardType", "form").text(cardType);
			}
			var number = $option.attr("number");
			if (number) {
				$("#" + PaymentEditable.commandObject + "-paymentSource-creditCardNumberReadOnly div#paymentSource-td-creditCardNumberDisplay", "form").text(number);
			}
			var exp = $option.attr("exp");
			if (exp) {
				$("#" + PaymentEditable.commandObject + "-paymentSource-creditCardExpirationDisplay div#paymentSource-td-creditCardExpiration", "form").text(exp);
			}

			if (!isLoad) {
				var addressId = $option.attr("address");
				var phoneId = $option.attr("phone");

				var $selectAddress = Picklist.setSelectedAddressPhoneByValue($("select#address-td-id", "form"), addressId);
				var $selectPhone = Picklist.setSelectedAddressPhoneByValue($("select#phone-td-id", "form"), phoneId);
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

