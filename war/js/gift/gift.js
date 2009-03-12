$(document).ready(function() {
	$("#paymentType").bind("change", function() {
		Gift.filterPaymentTypes($(this));
	});	
	$("#ach-selectedPaymentSource").bind("change", function() {
		Gift.filterAchPaymentSources($(this));
	});
	$("#creditCard-selectedPaymentSource").bind("change", function() {
		Gift.filterCreditCardPaymentSources($(this));
	});
		
	Gift.filterPaymentTypes($("#paymentType"));
});

var Gift = {
	commandObject: null,
	
	filterPaymentTypes: function($paymentTypeElem) {
		var paymentTypeVal = $paymentTypeElem.val();
		if (paymentTypeVal == "Cash" || paymentTypeVal == "Check") {
			$("#" + Gift.commandObject + "_ach").hide();
			$("#" + Gift.commandObject + "_creditCard").hide();
			$("#" + Gift.commandObject + "_editAch").hide();
			$("#" + Gift.commandObject + "_editCreditCard").hide();
		} 
		else if (paymentTypeVal == "Credit Card") {
			$("#ach-selectedPaymentSource").hide();
			$("#" + Gift.commandObject + "_ach").hide();
			$("#" + Gift.commandObject + "_editAch").hide();

			Gift.filterCreditCardPaymentSources($("#creditCard-selectedPaymentSource"));
			$("#creditCard-selectedPaymentSource").show();
		}
		else if (paymentTypeVal == "ACH") {
			$("#creditCard-selectedPaymentSource").hide();
			$("#" + Gift.commandObject + "_editCreditCard").hide();
			$("#" + Gift.commandObject + "_creditCard").hide();

			Gift.filterAchPaymentSources($("#ach-selectedPaymentSource"));
			$("#ach-selectedPaymentSource").show();
		}
	},
		
	filterAchPaymentSources: function($achPaymentSourceElem) {
		var $option = $achPaymentSourceElem.find('option:selected');
		var optionVal = $option.val();
		$("#selectedPaymentSource").val(optionVal); 
		if (optionVal == "new") {
			$("#" + Gift.commandObject + "_editAch").hide();
			$("#" + Gift.commandObject + "_ach").show();
		}
		else if (Gift.hasId(optionVal)) {
			$("#" + Gift.commandObject + "_ach").hide();
			$("#" + Gift.commandObject + "_editAch").show();
			Gift.populatePaymentSourceAttributes($option);
		}
	},
		
	filterCreditCardPaymentSources: function($creditCardPaymentSourceElem) {
		var $option = $creditCardPaymentSourceElem.find('option:selected');
		var optionVal = $option.val(); 
		$("#selectedPaymentSource").val(optionVal); 
		if (optionVal == "new") {
			$("#" + Gift.commandObject + "_editCreditCard").hide();
			$("#" + Gift.commandObject + "_creditCard").show();
		}
		else if (Gift.hasId(optionVal)) {
			$("#" + Gift.commandObject + "_creditCard").hide();
			$("#" + Gift.commandObject + "_editCreditCard").show();
			Gift.populatePaymentSourceAttributes($option);
		}
	},
		
	populatePaymentSourceAttributes: function($option) {
		if ($option.length && Gift.hasId($option.val())) {
			var addressId = $option.attr("address");
			var phoneId = $option.attr("phone");
			
			var $selectAddress = Picklist.setSelectedAddressPhoneByValue($("select#selectedAddress", "form"), addressId);
			var $selectPhone = Picklist.setSelectedAddressPhoneByValue($("select#selectedPhone", "form"), phoneId);
			
			// ACH
			var achholder = $option.attr("achholder");
			if (achholder) {
				$("#" + Gift.commandObject + "_editAch div#selectedPaymentSource_achHolderName", "form").text(achholder);
			}
			var routing = $option.attr("routing");
			if (routing) {
				$("#" + Gift.commandObject + "_editAch div#selectedPaymentSource_achRoutingNumberDisplay", "form").text(routing);
			}
			var acct = $option.attr("acct");
			if (acct) {
				$("#" + Gift.commandObject + "_editAch div#selectedPaymentSource_achAccountNumberDisplay", "form").text(acct);
			}
			
			// Credit Card
			var cardholder = $option.attr("cardholder");
			if (cardholder) {
				$("#" + Gift.commandObject + "_editCreditCard div#selectedPaymentSource_creditCardHolderName", "form").text(cardholder);
			}
			var cardType = $option.attr("cardType");
			if (cardType) {
				$("#" + Gift.commandObject + "_editCreditCard div#selectedPaymentSource_creditCardType", "form").text(cardType);
			}
			var number = $option.attr("number");
			if (number) {
				$("#" + Gift.commandObject + "_editCreditCard div#selectedPaymentSource_creditCardNumberDisplay", "form").text(number);
			}
			var exp = $option.attr("exp");
			if (exp) {
				$("#" + Gift.commandObject + "_editCreditCard div#selectedPaymentSource_creditCardExpiration", "form").text(exp);
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
		return isNaN(parseInt(val, 10)) == false;
	}
}