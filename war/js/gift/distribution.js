$(document).ready(function() {

	$("form#gift input#amount").bind("keyup change", function() {
		Distribution.updateTotals();
	});

	Distribution.distributionLineBuilder($("#gift_distribution tr"));
	Distribution.rowCloner("#gift_distribution tr:last");
	$("#gift_distribution tr:last .deleteButton").hide();

});

	
var Distribution = {

	updateTotals: function() {
		var subTotal = 0;
		$("table#gift_distribution input.amount").each(function(){
			var rowVal = parseFloat($(this).val());
			if (!isNaN(rowVal)) {
				subTotal += rowVal;
			}
		});
		$("#subTotal span.value").html(subTotal.toString());
		if (subTotal == parseFloat($("input#amount").val())) {
			$("#subTotal").removeClass("warning");
		} 
		else {
			$("#subTotal").addClass("warning");
		}
	},

	rowCloner: function(selector) {
		$(selector).one("keyup",function(event){
			if(event.keyCode != 9) { // ignore tab
				addNewRow(distributionLineBuilder);
			}
			rowCloner(selector); // Re-attach to the (new) last table row
		});
	},

	distributionLineBuilder: function(newRow) {
		newRow.find(".deleteButton").click(function(){
			deleteRow($(this).parent().parent());
		}).hide();
		newRow.find("input").focus(function() {
			$(this).parent().parent().addClass("focused");
				}).blur(function() {
					$(this).parent().parent().removeClass("focused");
				}).removeClass("textError");
		newRow.find("input.amount").bind("keyup change", updateTotals);
		
		newRow.find("input.code").each(function(){
			var codeType=$(this).attr("codeType");
			$(this).autocomplete("codeHelper.htm?type="+codeType, {
				delay:10,
				minChars:0,
				maxItemsToShow:20,
				formatItem:formatItem,
				loadingClass:""
			});
		});
		newRow.removeClass("focused");
	},
	
	addNewRow: function(builder) {
		var newRow = $(".tablesorter tr:last").clone(false);
		builder(newRow);
		var i = newRow.attr("rowindex");
		var j = parseInt(i) + 1;
		newRow.attr("rowindex",j);
		var findExp = new RegExp("\\["+i+"\\]","gi");
		newRow.find("input").each(function() {
				var field = $(this);
				var nameString = field.attr('name').replace(findExp, "["+j+"]");
				field.attr('name',nameString);
				field.val("");
			});
		$(".tablesorter tr:last .deleteButton").show();
		$(".tablesorter").append(newRow);
	},
	
	deleteRow: function(row) {
		if($(".tablesorter tbody tr").length > 1) {
			row.fadeOut("slow",function(){$(this).remove();updateTotals();})
		} 
		else {
			alert("Sorry, you cannot delete that row since it's the only remaining row.")
		};
	}
}
