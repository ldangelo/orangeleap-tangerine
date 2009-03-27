$(document).ready(function() {
	var $gridRows = $("table.giftInKindDetails tbody.gridRow");
	GiftInKindDetails.index = $gridRows.length + 1;

	$("#fairMarketValue").each(function() {
		var $elem = $(this);

		/* Done on load for previously entered giftInKindDetails */
		var val = $elem.val();
		if (isNaN(parseFloat(val)) == false) {
			GiftInKindDetails.setEnteredFmv(val);
			GiftInKindDetails.addNewRow();
			GiftInKindDetails.updateTotals();
		}
	});
	GiftInKindDetails.detailsBuilder($gridRows);
	GiftInKindDetails.rowCloner("table.giftInKindDetails tbody.gridRow:last");
	
	$("#fairMarketValue").bind("keyup", function(event) {
		var fairMarketVal = $(this).val();
		GiftInKindDetails.setEnteredFmv(fairMarketVal);
		var values = $("table.giftInKindDetails tbody.gridRow input.detailFairMarketValue", "form");
		 
		if (values.length == 1) {
			values.val(fairMarketVal);
		}
		GiftInKindDetails.updateTotals();
	});
	
	$("#anonymous").bind("click", function(event) {
		OrangeLeap.hideShowRecognition($(this), "li:has(#recognitionName)");
	});
	$("#anonymous").triggerHandler("click");
});

	
var GiftInKindDetails = {
	enteredFmv: 0,
	index: 1,
	
	setEnteredFmv: function(val) {
		this.enteredFmv = OrangeLeap.truncateFloat(parseFloat(val));
	},
	
	updateTotals: function() {
		var totalValue = parseFloat(0);
		$("table.giftInKindDetails tbody.gridRow input.detailFairMarketValue", "form").each(function() {
			var $fmvElem = $(this);
			
			var fairMarketVal = parseFloat($fmvElem.val());
			var thisFmv = isNaN(fairMarketVal) ? parseFloat(0) : OrangeLeap.truncateFloat(fairMarketVal);
			totalValue = OrangeLeap.truncateFloat(parseFloat(thisFmv) + parseFloat(totalValue));
		});
		$("#subTotal").html(totalValue);
		GiftInKindDetails.displayError(totalValue);
	},
	
	displayError: function(totalValue, pctTotal) {
        if (OrangeLeap.truncateFloat(parseFloat(totalValue)) === GiftInKindDetails.enteredFmv) {
            $("#totalText").removeClass("warning");
			$("#valueErrorSpan").hide();
        } 
        else {
            $("#totalText").addClass("warning");
			$("#valueErrorSpan").show();
        }
	},

	rowCloner: function(selector) {
		$(selector, "form").one("keyup",function(event){
			if (event.keyCode != 9) { // ignore tab
				GiftInKindDetails.addNewRow();
			}
			GiftInKindDetails.rowCloner(selector); // Re-attach to the (new) last table row
		});
	},

	detailsBuilder: function($newRow) {
		$(".deleteButton", $newRow).click(function(){
			GiftInKindDetails.deleteRow($(this).parent().parent().parent());
			return false;
		}).show();
		$("input.number", $newRow).numeric();
		$("input.detailFairMarketValue", $newRow).bind("keyup change", function(event) {
			GiftInKindDetails.updateTotals();
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
		$newRow.attr("id", "gridRow" + GiftInKindDetails.index);
		$newRow.html($newRow.html().replace(new RegExp("\\[0]","g"), "[" + GiftInKindDetails.index + "]").replace(new RegExp("\\-0-","g"), "-" + GiftInKindDetails.index + "-").
			replace(new RegExp('rowIndex="0"',"gi"), 'rowIndex="' + GiftInKindDetails.index + '"'));
		$("table.giftInKindDetails tbody.gridRow:last .deleteButton", "form").removeClass("noDisplay"); // show the previous last row's delete button
		$("table.giftInKindDetails", "form").append($newRow);
		GiftInKindDetails.detailsBuilder($newRow);
		$newRow.removeClass("noDisplay").addClass("gridRow");
		GiftInKindDetails.index++;
	},
	
	deleteRow: function(row) {
		if ($("table.giftInKindDetails tbody.gridRow", "form").length > 1) {
			row.fadeOut("slow", function() {
				$(this).remove();
				GiftInKindDetails.updateTotals();
			});
		} 
		else {
			alert("Sorry, you cannot delete that row since it's the only remaining row.")
		};
	}
}
