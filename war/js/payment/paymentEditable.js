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
	$("#check-paymentSource-td-id").bind("change", function() {
		PaymentEditable.filterCheckPaymentSources($(this), false);
	});
	PaymentEditable.filterPaymentTypes($("#paymentType"), true);
});
var PaymentEditable = {
	commandObject: null,
	
	filterPaymentTypes: function($paymentTypeElem, isLoad) {
		var paymentTypeVal = $paymentTypeElem.val();
		if (paymentTypeVal == "Cash") {
			$("#" + PaymentEditable.commandObject + "_ach").hide();
			$("#" + PaymentEditable.commandObject + "_creditCard").hide();
			$("#" + PaymentEditable.commandObject + "_check").hide();
		}
		else if (paymentTypeVal == "Credit Card") {
			$("#ach-paymentSource-td-id").hide();
			$("#check-paymentSource-td-id").hide();
			$("#" + PaymentEditable.commandObject + "_ach").hide();
			$("#" + PaymentEditable.commandObject + "_check").hide();
			$("#" + PaymentEditable.commandObject + "_creditCard").show();

			PaymentEditable.filterCreditCardPaymentSources($("#creditCard-paymentSource-td-id"), isLoad);
			$("#creditCard-paymentSource-td-id").show();
		}
		else if (paymentTypeVal == "ACH") {
			$("#creditCard-paymentSource-td-id").hide();
			$("#check-paymentSource-td-id").hide();
			$("#" + PaymentEditable.commandObject + "_creditCard").hide();
			$("#" + PaymentEditable.commandObject + "_check").hide();
			$("#" + PaymentEditable.commandObject + "_ach").show();

			PaymentEditable.filterAchPaymentSources($("#ach-paymentSource-td-id"), isLoad);
			$("#ach-paymentSource-td-id").show();
		}
		else if (paymentTypeVal == "Check") {
			$("#ach-paymentSource-td-id").hide();
			$("#creditCard-paymentSource-td-id").hide();
			$("#" + PaymentEditable.commandObject + "_creditCard").hide();
			$("#" + PaymentEditable.commandObject + "_ach").hide();
			$("#" + PaymentEditable.commandObject + "_check").show();

			PaymentEditable.filterCheckPaymentSources($("#check-paymentSource-td-id"), isLoad);
			$("#check-paymentSource-td-id").show();
		}
		else {
			$("#" + PaymentEditable.commandObject + "_ach").hide();
			$("#" + PaymentEditable.commandObject + "_creditCard").hide();
			$("#" + PaymentEditable.commandObject + "_check").hide();
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
		
	filterCheckPaymentSources: function($checkPaymentSourceElem, isLoad) {
		var $option = $checkPaymentSourceElem.find('option:selected');
		var optionVal = $option.val();
		$("#paymentSource-td-id").val(optionVal);
		if (optionVal == "0") { // 0 is new
			$("li:has(.ea-existingCheck)").hide();
			$("li:has(.ea-newCheck)").show();
		}
		else if (PaymentEditable.hasId(optionVal)) {
			$("li:has(.ea-newCheck)").hide();
			$("li:has(.ea-existingCheck)").show();
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
			var achRouting = $option.attr("routing");
			if (achRouting) {
				$("#" + PaymentEditable.commandObject + "-paymentSource-achRoutingNumberReadOnly div#paymentSource-td-achRoutingNumberDisplay", "form").text(achRouting);
			}
			var achAcct = $option.attr("acct");
			if (achAcct) {
				$("#" + PaymentEditable.commandObject + "-paymentSource-achAccountNumberReadOnly div#paymentSource-td-achAccountNumberDisplay", "form").text(achAcct);
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

			// Check
			var checkholder = $option.attr("checkholder");
			if (checkholder) {
				$("#" + PaymentEditable.commandObject + "-paymentSource-checkHolderNameReadOnly div#paymentSource-td-checkHolderName", "form").text(checkholder);
			}
			var checkRouting = $option.attr("routing");
			if (checkRouting) {
				$("#" + PaymentEditable.commandObject + "-paymentSource-checkRoutingNumberReadOnly div#paymentSource-td-checkRoutingNumberDisplay", "form").text(checkRouting);
			}
			var checkAcct = $option.attr("acct");
			if (checkAcct) {
				$("#" + PaymentEditable.commandObject + "-paymentSource-checkAccountNumberReadOnly div#paymentSource-td-checkAccountNumberDisplay", "form").text(checkAcct);
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

