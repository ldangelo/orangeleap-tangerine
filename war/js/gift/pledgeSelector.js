var PledgeSelector = {
	loadPledgeSelector: function(elem) {
		this.lookupCaller = $(elem).siblings(".lookupScrollContainer").children(".multiLookupField");
		var queryString = "constituentId=" + $("#thisConstituentId").val() + "&selectedPledgeIds=" + $("#pledges").val();

		$.ajax({
			type: "POST",
			url: "pledgeSelector.htm?personId=" + $("#thisConstituentId").val(),
			data: queryString,
			success: function(html) {
				$("#dialog").html(html);
				PledgeSelector.selectorBindings();
				Lookup.multiCommonBindings();
				$("#dialog").jqmShow();
			},
			error: function(html){
				// TODO: improve error handling
				alert("The server was not available.  Please try again.");
			}
		});
	},
	
	selectorBindings: function() {		
		// First, serialize all fields in the current distribution lines
		$("#pledgeSelectorForm #doneButton").bind("click", function() {
			var queryString = PledgeSelector.serializeDistributionLines();
			var idsStr = "";
			var ids = new Array();
			var displayNames = new Array();
			$("ul#selectedOptions :checkbox", $("#dialog")).each(function() {
				var $chkboxElem = $(this);
				var thisId = $chkboxElem.attr("name");
				idsStr += thisId + ",";
				ids.push(thisId);
				displayNames.push($chkboxElem.val());
			});
			idsStr = (idsStr.length > 0 ? idsStr.substring(0, idsStr.length - 1) : idsStr); // remove the trailing comma
			queryString += "&selectedPledgeIds=" + idsStr + "&amount=" + $("#amount").val();
			
			$.ajax({
				type: "POST",
				url: "giftPledgeLines.htm?personId=" + $("#thisConstituentId").val(),
				data: queryString,
				success: function(html) {
					$("tbody.gridRow", $("#gift_distribution")).each(function() {
						$(this).remove();
					});
					Distribution.amtPctMap = {};
					$("#gift_distribution").append(html);
					var $gridRows = $("table.distributionLines tbody.gridRow");
					Distribution.index = $gridRows.length + 1;
					Distribution.distributionLineBuilder($gridRows);
//					Distribution.recalculatePcts();
					Distribution.updateTotals();
					Distribution.addNewRow();
					Distribution.rowCloner("table.distributionLines tbody.gridRow:last");
				},
				error: function(html){
					// TODO: improve error handling
					alert("The server was not available.  Please try again.");
				}
			});

			PledgeSelector.lookupCaller.siblings("input[type=hidden]").eq(0).val(idsStr);
			
			PledgeSelector.lookupCaller.children("div.multiQueryLookupOption").remove();
			
			var $toBeCloned = PledgeSelector.lookupCaller.parent().find("div.clone");
			
			for (var x = ids.length - 1; x >= 0; x--) {
				var $cloned = $toBeCloned.clone(true);
				var $popLink = $cloned.find("a[target='_blank']");
				$cloned.attr("id", "lookup-" + ids[x]);
				$cloned.attr("selectedId", ids[x]);
				$popLink.attr("href", "pledge.htm?pledgeId=" + ids[x] + "&personId=" + $("#thisConstituentId").val());
				$popLink.text(displayNames[x]);
				
				$cloned.removeClass("clone").removeClass("noDisplay");
				$cloned.prependTo(PledgeSelector.lookupCaller);
				$cloned.vkfade(true);
			} 
			$("#dialog").jqmHide();					
		});		
	},
	
	serializeDistributionLines: function() {
		var queryString = "";
		$(":text, :radio, :checkbox, input[type=hidden]", $("tbody.gridRow")).each(function() {
			var $elem = $(this);
			queryString += $elem.attr("name") + "=" + escape($elem.val()) + "&";
		});
		return queryString;
	},
	
	deletePledge: function() {
		
	}	
}