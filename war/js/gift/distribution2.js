$(document).ready(function() {
	var $gridRows = $("table.distributionLines tbody.gridRow");
	Distribution.index = $gridRows.length;

	$("#amount, #amountPerGift, #amountTotal").each(function() {
		var $elem = $(this);
		if ($elem.parent().is(":visible")) {
			/* Done on load for previously entered distributionLines */
			var val = $elem.val();
			if (OrangeLeap.isNum(val)) {
				Distribution.enteredAmt = OrangeLeap.truncateFloat(parseFloat(val));
				Distribution.reInitDistribution();
			}
		}
	});
	Distribution.distributionLineBuilder($gridRows);
	Distribution.rowCloner("table.distributionLines tbody.gridRow:last");
	
	$("#amount, #amountPerGift, #amountTotal").bind("keyup change", function(event) {
		var amounts = $("table.distributionLines tbody.gridRow :input[id$='-amount']", "form");
		var amtVal = $(this).val();
		if (OrangeLeap.isNum(amtVal)) {
			Distribution.enteredAmt = amtVal;
			 
			if (amounts.length == 1) {
				amounts.val(amtVal);
			}
			else {
				Distribution.recalculatePcts();
			}
			Distribution.updateFields(amounts);
		}
	});
	
	$("#recurring").bind("change", function() {
		if ($(this).val() == "true") {
			$("#amountPerGift").change();
		}
		else {
			$("#amountTotal").change();
		}
	});
	$(".distributionLines").bind("click", function(event) {
		var $target = $(event.target);
		Distribution.hideShowAnonymous($target);
	});
	$(".distributionLines :checkbox").each(function() { // onload
		Distribution.hideShowAnonymous($(this));
	});
});

	
var Distribution = {
	oldPct: "",
	enteredAmt: 0,
	amtPctMap: { }, // hash of idPrefix (distributionLines-1) --> amount & percent
	index: 1,
	
	reInitDistribution: function() {
		$("table.distributionLines tbody.gridRow input[id$='-amount']", "form").each(function() {
			var $amtElem = $(this);
			var $pctElem = $("#" + $amtElem.attr('id').replace('amount', 'percentage'));
			var rowId = $amtElem.attr('id').replace('-amount', '');
			
			var amtVal = parseFloat($amtElem.val());
			var thisAmt = isNaN(amtVal) ? 0 : OrangeLeap.truncateFloat(amtVal);
			
			var pctVal = parseFloat($pctElem.val());
			var thisPct = isNaN(pctVal) ? 0 : OrangeLeap.truncateFloat(pctVal);
			
			var map = Distribution.getMap(rowId);
			map.amount = thisAmt;
			map.percent = thisPct;	
		});
		Distribution.updateTotals();
		Distribution.addNewRow();
	},
	
	hideShowAnonymous: function($target) {
		var thisId = $target.attr("id");
		if (thisId && thisId.indexOf("anonymous") > -1) {
			var recognitionSelector = "li:has(#" + thisId.replace("anonymous", "recognitionName") + ")";
			OrangeLeap.hideShowRecognition($target, recognitionSelector);
		} 				
	},
	
	recalculatePcts: function() {
		$("table.distributionLines tbody.gridRow input[id$='-amount']", "form").each(function(){
			Distribution.calculatePct($(this));
		});
	},
	
	updateFields: function($target) {
		Distribution.updateCorrespondingAmtPct($target);
		Distribution.updateTotals();
	},
	
	updateCorrespondingAmtPct: function($target) {		
		if ($target.attr("id") == "amount" || $target.hasClass("number")) {
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
		subTotal = OrangeLeap.truncateFloat(subTotal);
		pctTotal = OrangeLeap.truncateFloat(pctTotal);
		
		$("#subTotal").html(subTotal.toString());
		
		Distribution.displayError(subTotal, pctTotal);
	},
	
	calculateAmt: function($elem) {
		var thisPct = $elem.val();
		if (isNaN(parseFloat(thisPct)) == false) {
			var rowId = $elem.attr('id').replace('-percentage', '');
			var amtElemId = $elem.attr('id').replace('percentage', 'amount');
			
			thisPct = OrangeLeap.truncateFloat(parseFloat(thisPct));
			
			var thisAmt = OrangeLeap.truncateFloat(parseFloat(Distribution.enteredAmt * (thisPct / 100)));
			
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
			
			thisAmt = OrangeLeap.truncateFloat(parseFloat(thisAmt));
			
			var thisPct = 0;
			
			if (thisAmt != 0 && Distribution.enteredAmt != 0) {
				thisPct = OrangeLeap.truncateFloat(parseFloat((thisAmt / Distribution.enteredAmt) * 100));
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
		$(".deleteButton", $newRow).click(function() {
			Distribution.deleteRow($(this).parent().parent());
			return false;
		}).show();
		$("input.number, input[id$='-amount'], input.percentage", $newRow).numeric();
		$("input.number, input[id$='-amount'], input.percentage", $newRow).bind("keyup", function(event) {
			if (event.keyCode != 9) { // ignore tab
				Distribution.updateFields($(event.target));
			}
		});		
		$("input.number, input[id$='-amount'], input.percentage", $newRow).bind("change", function(event) {
			Distribution.updateFields($(event.target));
		});		
		$("input.code", $newRow).each(function(){
			Lookup.codeAutoComplete($(this));
		});
		$("a.treeNodeLink", $newRow).bind("click", function(event) {
			return OrangeLeap.expandCollapse(this);                                                                      
		});
	},
	
	addNewRow: function() {
		var $newRow = $("#gridCloneRow").clone(false);
		$newRow.attr("id", "gridRow" + Distribution.index);
		if ($.browser.msie) {
			OrangeLeap.changeIdsNamesIE($newRow, Distribution.index);
		}
		else {
			$newRow.html($newRow.html().replace(new RegExp("\\[0\\]","g"), "[" + Distribution.index + "]").replace(new RegExp("\\-0\\-","g"), "-" + Distribution.index + "-").
				replace(new RegExp('rowIndex="0"',"gi"), 'rowIndex="' + Distribution.index + '"').replace(new RegExp("tangDummy\\-", "g"), ""));
		}
		$("table.distributionLines tbody.gridRow:last .deleteButton", "form").removeClass("noDisplay"); // show the previous last row's delete button
		$("table.distributionLines", "form").append($newRow);
		Distribution.distributionLineBuilder($newRow);
		$newRow.removeClass("noDisplay").addClass("gridRow");
		Distribution.index++;
	},
	
	deleteRow: function(row) {
		if ($("table.distributionLines tbody.gridRow", "form").length > 1) {
			row.parent().fadeOut("fast", function() {
				var $elem = $(this);
				var rowId = row.find("input[id$='-amount']").attr('id').replace('-amount', '');
				delete Distribution.amtPctMap[rowId];
				$elem.remove();
				Distribution.updateTotals();
			});
		} 
		else {
			alert("Sorry, you cannot delete that row since it's the only remaining row.")
		};
	}
};
