$(document).ready(function() {
	var $gridRows = $("table.distributionLines tbody.gridRow");
	AdjustedDistribution.index = $gridRows.length + 1;

	$("#amount").each(function() {
		var $elem = $(this);
		/* Done on load for previously entered distributionLines */
		var val = $elem.val();
		if (isNaN(parseFloat(val)) == false) {
			AdjustedDistribution.enteredAmt = OrangeLeap.truncateFloat(parseFloat(val));
			AdjustedDistribution.reInitDistribution();
		}
	});
	
	$("#amount").bind("keyup change", function(event) {
		var amounts = $("table.distributionLines tbody.gridRow input.amount", "form");
		var amtVal = $(this).val();
		AdjustedDistribution.enteredAmt = amtVal;
		 
		if (amounts.length == 1) {
			amounts.val(amtVal);
		}
		else {
			AdjustedDistribution.recalculatePcts();
		}
		AdjustedDistribution.updateFields(amounts);
	});
});

	
var AdjustedDistribution = {
	oldPct: "",
	enteredAmt: 0,
	amtPctMap: { }, // hash of idPrefix (distributionLines-1) --> amount & percent
	index: 1,
	
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
	
	recalculatePcts: function() {
		$("table.distributionLines tbody.gridRow input.amount", "form").each(function(){
			AdjustedDistribution.calculatePct($(this));
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
		var pctTotal = 0;
				
		for (var key in AdjustedDistribution.amtPctMap) {
			var map = AdjustedDistribution.amtPctMap[key];
			subTotal += parseFloat(map.amount);
			pctTotal += parseFloat(map.percent);
		}
		subTotal = OrangeLeap.truncateFloat(subTotal);
		pctTotal = OrangeLeap.truncateFloat(pctTotal);
		
		$("#subTotal").html(subTotal.toString());
		
		AdjustedDistribution.displayError(subTotal, pctTotal);
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
	
	displayError: function(subTotal, pctTotal) {
        var v = null;
        var rec = $('#recurring');
        if (rec.length) {
	        v = (rec.val() == "true" ? $('#amountPerGift') : $('#amountTotal'));
        }
        else {
        	v = $('#amount');
        }

        if (parseFloat(subTotal) === parseFloat(v.val())) {
            $("#totalText").removeClass("warning");
			$("#amountsErrorSpan").hide();
        } 
        else {
            $("#totalText").addClass("warning");
			$("#amountsErrorSpan").show();
        }
	}
}
