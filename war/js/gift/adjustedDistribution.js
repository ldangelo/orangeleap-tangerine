$(document).ready(function() {
	$("#adjustedAmount").each(function() {
		var $elem = $(this);
		/* Done on load for previously entered distributionLines */
		var val = $elem.val();
		if (OrangeLeap.isNum(val)) {
			AdjustedDistribution.enteredAmt = OrangeLeap.truncateFloat(parseFloat(val));
			AdjustedDistribution.reInitDistribution();
		}
	});
	
	$("#adjustedAmount").bind("keyup change", function(event) {
		var amtVal = $(this).val();
		if (OrangeLeap.isNum(amtVal)) {
			AdjustedDistribution.enteredAmt = amtVal;
			AdjustedDistribution.recalculateAmounts();
			AdjustedDistribution.updateTotals();
		}
	});
	$("input.amount, input.percentage", $("#adjustedGift_distribution")).bind("keyup", function(event) {
		if (event.keyCode != 9) { // ignore tab
			AdjustedDistribution.updateFields($(event.target));
		}
	});		
	$("a.treeNodeLink", $("#adjustedGift_distribution")).bind("click", function(event) {
		return OrangeLeap.expandCollapse(this);
	});
	
	$("#adjustedPaymentRequired").bind("change", function() {
		$("#paymentType").triggerHandler("change");
	});
});

	
var AdjustedDistribution = {
	oldPct: "",
	enteredAmt: 0,
	amtPctMap: { }, // hash of idPrefix (distributionLines-1) --> amount & percent
	
	reInitDistribution: function() {
		$("table.distributionLines tbody.gridRow input.amount", "form").each(function() {
			var $amtElem = $(this);
			var $pctElem = $("#" + $amtElem.attr('id').replace('amount', 'percentage'));
			var rowId = $amtElem.attr('id').replace('-amount', '');
			
			var amtVal = parseFloat($amtElem.val());
			var thisAmt = isNaN(amtVal) ? 0 : OrangeLeap.truncateFloat(amtVal);
			
			var pctVal = parseFloat($pctElem.val());
			var thisPct = isNaN(pctVal) ? 0 : OrangeLeap.truncateFloat(pctVal);
			
			var map = AdjustedDistribution.getMap(rowId);
			map.amount = thisAmt;
			map.percent = thisPct;	
		});
		AdjustedDistribution.updateTotals();
	},
	
	recalculateAmounts: function() {
		$("table.distributionLines tbody.gridRow input.percentage", "form").each(function(){
			AdjustedDistribution.calculateAmt($(this));
		});
	},
	
	updateFields: function($target) {
		AdjustedDistribution.updateCorrespondingAmtPct($target);
		AdjustedDistribution.updateTotals();
	},
	
	updateCorrespondingAmtPct: function($target) {		
		if ($target.attr("id") == "amount" || $target.hasClass("amount")) {
			AdjustedDistribution.calculatePct($target);
		}
		else {
			AdjustedDistribution.calculateAmt($target);
		}
	},
		
	updateTotals: function() {
		var subTotal = 0;
				
		for (var key in AdjustedDistribution.amtPctMap) {
			var map = AdjustedDistribution.amtPctMap[key];
			subTotal += parseFloat(map.amount);
		}
		subTotal = OrangeLeap.truncateFloat(subTotal);
		
		$("#subTotal").html(subTotal.toString());
		
		AdjustedDistribution.displayError(subTotal);
	},
	
	calculateAmt: function($elem) {
		var thisPct = $elem.val();
		if (isNaN(parseFloat(thisPct)) == false) {
			var rowId = $elem.attr('id').replace('-percentage', '');
			var amtElemId = $elem.attr('id').replace('percentage', 'amount');
			
			thisPct = OrangeLeap.truncateFloat(parseFloat(thisPct));
			
			var thisAmt = OrangeLeap.truncateFloat(parseFloat(AdjustedDistribution.enteredAmt * (thisPct / 100)));
			
			var map = AdjustedDistribution.getMap(rowId);
			map.amount = thisAmt;
			map.percent = thisPct;
			
			$("#" + amtElemId).val(thisAmt);
		}
	},
	
	calculatePct: function($elem) {
		var thisAmt = $elem.val();
		if (isNaN(parseFloat(thisAmt)) == false) {
			var rowId = $elem.attr('id').replace('-amount', '');
			var pctElemId = $elem.attr('id').replace('amount', 'percentage');
			
			thisAmt = OrangeLeap.truncateFloat(parseFloat(thisAmt));
			
			var thisPct = 0;
			
			if (thisAmt != 0 && AdjustedDistribution.enteredAmt != 0) {
				thisPct = OrangeLeap.truncateFloat(parseFloat((thisAmt / AdjustedDistribution.enteredAmt) * 100));
			}
			
			var map = AdjustedDistribution.getMap(rowId);
			map.amount = thisAmt;
			map.percent = thisPct;
			
			$("#" + pctElemId).val(thisPct);
		}
	},
	
	getMap: function(rowId) {
		var map = AdjustedDistribution.amtPctMap[rowId];
		if (!map) {
			AdjustedDistribution.amtPctMap[rowId] = { amount: 0, percent: 0 };
			map = AdjustedDistribution.amtPctMap[rowId];
		}
		return map;
	},
	
	displayError: function(subTotal) {
        var v = $('#adjustedAmount');

        if (parseFloat(subTotal) === parseFloat(v.val())) {
            $("#totalText").removeClass("warning");
			$("#amountsErrorSpan").hide();
        } 
        else {
            $("#totalText").addClass("warning");
			$("#amountsErrorSpan").show();
        }
	}
};
