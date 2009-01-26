$(document).ready(function() {
	Distribution.distributionLineBuilder($("#gift_distribution tr"));
	Distribution.rowCloner("#gift_distribution tr:last");
	
	$("form#gift input#amount").bind("keyup change", function(event) {
		var amounts = $("table#gift_distribution input.amount");
		var amtVal = $(this).val();
		Distribution.enteredAmt = amtVal;
		 
		if (amounts.length == 1) {
			amounts.val(amtVal);
		}
		else {
			Distribution.recalculatePcts();
		}
		Distribution.updateFields(amounts);
	});
});

	
var Distribution = {
	oldPct: "",
	enteredAmt: 0,
	amtPctMap: { }, // hash of idPrefix (distributionLines-1) --> amount & percent
	
	isFloat: function(value){
	   if (isNaN(value) || value.toString().indexOf(".") < 0){
		   return false;
	   }
	   else {
	      if (parseFloat(value)) {
	    	  return true;
	      } 
	      else {
	    	  return false;
	      }
	   }
	},
	
	truncateFloat: function(val) {
		if (Distribution.isFloat(val)) {
			val = val.toFixed(2);
		}
		return val;
	},
	
	recalculatePcts: function() {
		$("table#gift_distribution input.amount").each(function(){
			Distribution.calculatePct($(this));
		});
	},
	
	updateFields: function($target) {
		Distribution.updateCorrespondingAmtPct($target);
		Distribution.updateTotals();
	},
	
	updateCorrespondingAmtPct: function($target) {		
		if ($target.attr("id") == "amount" || $target.hasClass("amount")) {
			Distribution.calculatePct($target);
		}
		else {
			Distribution.calculateAmt($target);
		}
	},
		
	updateTotals: function() {
		var subTotal = 0;
		var pctTotal = 0;
				
		for (var key in Distribution.amtPctMap) {
			var map = Distribution.amtPctMap[key];
			subTotal += parseFloat(map.amount);
			pctTotal += parseFloat(map.percent);
		}
		subTotal = Distribution.truncateFloat(subTotal);
		pctTotal = Distribution.truncateFloat(pctTotal);
		
		$("#subTotal").html(subTotal.toString());
		
		Distribution.displayError(subTotal, pctTotal);
	},
	
	calculateAmt: function($elem) {
		var thisPct = $elem.val();
		if (isNaN(parseFloat(thisPct)) == false) {
			var rowId = $elem.attr('id').replace('-percentage', '');
			var amtElemId = $elem.attr('id').replace('percentage', 'amount');
			
			thisPct = Distribution.truncateFloat(parseFloat(thisPct));
			
			var thisAmt = Distribution.truncateFloat(parseFloat(Distribution.enteredAmt * (thisPct / 100)));
			
			var map = Distribution.getMap(rowId);
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
			
			thisAmt = Distribution.truncateFloat(parseFloat(thisAmt));
			
			var thisPct = 0;
			
			if (thisAmt != 0 && Distribution.enteredAmt != 0) {
				thisPct = Distribution.truncateFloat(parseFloat((thisAmt / Distribution.enteredAmt) * 100));
			}
			
			var map = Distribution.getMap(rowId);
			map.amount = thisAmt;
			map.percent = thisPct;
			
			$("#" + pctElemId).val(thisPct);
		}
	},
	
	getMap: function(rowId) {
		var map = Distribution.amtPctMap[rowId];
		if (!map) {
			Distribution.amtPctMap[rowId] = { amount: 0, percent: 0 };
			map = Distribution.amtPctMap[rowId];
		}
		return map;
	},
	
	displayError: function(subTotal, pctTotal) {
		if (subTotal == parseFloat($("input#amount").val())) {
			$("#totalText").removeClass("warning");
			$("#amountsErrorSpan").hide();
		} 
		else {
			$("#totalText").addClass("warning");
			$("#amountsErrorSpan").show();
		}
	},

	rowCloner: function(selector) {
		$(selector).one("keyup",function(event){
			if (event.keyCode != 9) { // ignore tab
				Distribution.addNewRow(Distribution.distributionLineBuilder);
			}
			Distribution.rowCloner(selector); // Re-attach to the (new) last table row
		});
	},

	distributionLineBuilder: function(newRow) {
		newRow.find(".deleteButton").click(function(){
			Distribution.deleteRow($(this).parent().parent());
		}).hide();
		newRow.find("input.number, input.percentage").numeric();
		newRow.find("input.amount, input.percentage").bind("keyup change", function(event) {
			Distribution.updateFields($(event.target));
		});		
		
		newRow.find("input.code").each(function(){
			Lookup.codeAutoComplete($(this));
		});
		newRow.removeClass("focused");
	},
	
	addNewRow: function(builder) {
		var newRow = $(".tablesorter tr:last").clone(false);
		builder(newRow);
		var i = newRow.attr("rowindex");
		var j = parseInt(i, 10) + 1;
		newRow.attr("rowindex", j);
		newRow.find("input").each(function() {
				var field = $(this);
				field.attr('name', field.attr('name').replace(new RegExp("\\[" + i + "\\]","gi"), "[" + j + "]"));
				field.attr('id', field.attr('id').replace(new RegExp("\\-" + i + "\\-","gi"), "-" + j + "-"));
				field.val("");
			});
		$(".tablesorter tr:last .deleteButton").show();
		$(".tablesorter").append(newRow);
	},
	
	deleteRow: function(row) {
		if($(".tablesorter tbody tr").length > 1) {
			row.fadeOut("slow", function() {
				var $elem = $(this);
				var rowId = $elem.find("input.amount").attr('id').replace('-amount', '');
				delete Distribution.amtPctMap[rowId];
				$elem.remove();
				Distribution.updateTotals();
			});
		} 
		else {
			alert("Sorry, you cannot delete that row since it's the only remaining row.")
		};
	}
}
