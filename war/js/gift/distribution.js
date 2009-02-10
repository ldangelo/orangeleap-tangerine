$(document).ready(function() {
	$("#amount, #amountPerGift, #amountTotal").each(function() {
		Distribution.reInitializeOnReady(this);
	});
	Distribution.distributionLineBuilder($("table.distributionLines tr", "form"));
	Distribution.rowCloner("table.distributionLines tr:last");
	$("table.distributionLines tr:last .deleteButton", "form").hide();
	
	$("#amount, #amountPerGift, #amountTotal").bind("keyup change", function(event) {
		var amounts = $("table.distributionLines input.amount", "form");
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
	
	$("#recurring").bind("change", function() {
		if ($(this).val() == "true") {
			$("#amountPerGift").change();
		}
		else {
			$("#amountTotal").change();
		}
	});
});

	
var Distribution = {
	oldPct: "",
	enteredAmt: 0,
	amtPctMap: { }, // hash of idPrefix (distributionLines-1) --> amount & percent
	
	reInitializeOnReady: function(aElem) {
		var $elem = $(aElem);
		if ($elem.parent().is(":visible")) {
			/* Done on load for previously entered distributionLines */
			var val = $elem.val();
			if (isNaN(parseFloat(val)) == false) {
				Distribution.enteredAmt = Distribution.truncateFloat(parseFloat(val));
				Distribution.addNewRow();
				
				$("table.distributionLines input.amount", "form").each(function() {
					var $amtElem = $(this);
					var $pctElem = $("#" + $amtElem.attr('id').replace('amount', 'percentage'));
					var rowId = $amtElem.attr('id').replace('-amount', '');
					
					var amtVal = parseFloat($amtElem.val());
					var thisAmt = isNaN(amtVal) ? 0 : Distribution.truncateFloat(amtVal);
					
					var pctVal = parseFloat($pctElem.val());
					var thisPct = isNaN(pctVal) ? 0 : Distribution.truncateFloat(pctVal);
					
					var map = Distribution.getMap(rowId);
					map.amount = thisAmt;
					map.percent = thisPct;	
				});
				Distribution.updateTotals();
			}
		}
	},
	
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
		$("table.distributionLines input.amount", "form").each(function(){
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
		var v = $("#amountPerGift").val();
        if(subTotal === parseFloat(v) && pctTotal === 100) {
            $("#totalText").removeClass("warning");
			$("#amountsErrorSpan").hide();
        } else {
            $("#totalText").addClass("warning");
			$("#amountsErrorSpan").show();
        }
	},

	rowCloner: function(selector) {
		$(selector, "form").one("keyup",function(event){
			if (event.keyCode != 9) { // ignore tab
				Distribution.addNewRow();
			}
			Distribution.rowCloner(selector); // Re-attach to the (new) last table row
		});
	},

	distributionLineBuilder: function($newRow) {
		$newRow.each(function() {
			var $row = $(this);
			$row.find(".deleteButton").click(function(){
				Distribution.deleteRow($(this).parent().parent());
			}).show();
			$row.find("input.number, input.percentage").numeric();
			$row.find("input.amount, input.percentage").bind("keyup change", function(event) {
				Distribution.updateFields($(event.target));
			});		
			
			$row.find("input.code").each(function(){
//				Lookup.codeAutoComplete($(this)); // TODO: add back
			});
			$row.removeClass("focused");
		});
	},
	
	addNewRow: function() {
		var $newRow = $("table.distributionLines tr:last", "form").clone(false);
		var i = $newRow.attr("rowindex");
		var j = parseInt(i, 10) + 1;
		$newRow.attr("rowindex", j);
		$newRow.find("input").each(function() {
				var $field = $(this);
				$field.attr('name', $field.attr('name').replace(new RegExp("\\[\\d+\\]","g"), "[" + j + "]"));
				$field.attr('id', $field.attr('id').replace(new RegExp("\\-\\d+\\-","g"), "-" + j + "-"));
				if ($field.attr('otherFieldId')) {
					$field.attr('otherFieldId', $field.attr('otherFieldId').replace(new RegExp("\\-\\d+\\-","g"), "-" + j + "-"));
				}
				$field.val("");
				$field.removeClass("focused");
			});
		$("table.distributionLines tr:last .deleteButton", "form").show(); // show the previous last row's delete button
		Distribution.distributionLineBuilder($newRow);
		$newRow.find(".deleteButton").hide();
		$("table.distributionLines", "form").append($newRow);
	},
	
	deleteRow: function(row) {
		if ($("table.distributionLines tbody tr", "form").length > 1) {
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
