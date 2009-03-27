$(document).ready(function() {
	$("#fairMarketValue").each(function() {
		GiftInKindDetails.reInitializeOnReady(this);
	});
	GiftInKindDetails.detailsBuilder($("table.giftInKindDetails tr", "form"));
	GiftInKindDetails.rowCloner("table.giftInKindDetails tr:last");
	$("table.giftInKindDetails tr:last .deleteButton", "form").hide();
	
	$("#fairMarketValue").bind("keyup", function(event) {
		var fairMarketVal = $(this).val();
		GiftInKindDetails.setEnteredFmv(fairMarketVal);
		var values = $("table.giftInKindDetails input.detailFairMarketValue", "form");
		 
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
	
	setEnteredFmv: function(val) {
		this.enteredFmv = OrangeLeap.truncateFloat(parseFloat(val));
	},
	
	reInitializeOnReady: function(aElem) {
		var $elem = $(aElem);

		/* Done on load for previously entered giftInKindDetails */
		var val = $elem.val();
		if (isNaN(parseFloat(val)) == false) {
			GiftInKindDetails.setEnteredFmv(val);
			GiftInKindDetails.addNewRow();
			GiftInKindDetails.updateTotals();
		}
	},
	
	updateTotals: function() {
		var totalValue = parseFloat(0);
		$("table.giftInKindDetails input.detailFairMarketValue", "form").each(function() {
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
		$newRow.each(function() {
			var $row = $(this);
			$row.find(".deleteButton").click(function(){
				GiftInKindDetails.deleteRow($(this).parent().parent());
			}).show();
			$row.find("input.number").numeric();
			$row.find("input.detailFairMarketValue").bind("keyup change", function(event) {
				GiftInKindDetails.updateTotals();
			});		
			$row.find("input.code").each(function(){
				Lookup.codeAutoComplete($(this));
			});
			$row.removeClass("focused");
		});
	},
	
	addNewRow: function() {
		var $newRow = $("table.giftInKindDetails tr:last", "form").clone(false);
		var i = $newRow.attr("rowindex");
		var j = parseInt(i, 10) + 1;
		$newRow.attr("rowindex", j);
		$newRow.find("input, select").each(function() {
				var $field = $(this);
				$field.attr('name', $field.attr('name').replace(new RegExp("\\[\\d+\\]","g"), "[" + j + "]"));
				$field.attr('id', $field.attr('id').replace(new RegExp("\\-\\d+\\-","g"), "-" + j + "-"));
				if ($field.is(":checkbox")) {
					$field.removeAttr("checked");
				}
				else if ($field.is(":text") || $field.is(":hidden")) {
					$field.val("");
				}
				else if ($field.is(":select")) {
					$field.get(0).options[0].selected = true;
				}
				$field.removeClass("focused");
			});
		$("table.giftInKindDetails tr:last .deleteButton", "form").show(); // show the previous last row's delete button
		GiftInKindDetails.detailsBuilder($newRow);
		$newRow.find(".deleteButton").hide();
		$("table.giftInKindDetails", "form").append($newRow);
	},
	
	deleteRow: function(row) {
		if ($("table.giftInKindDetails tbody tr", "form").length > 1) {
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
