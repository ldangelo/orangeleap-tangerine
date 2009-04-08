$(document).ready(function() {
	/* Decorator pattern in JS - check if a pledge needs to be dis-associated based on pledgeId */
	var ddrFunct = Distribution.deleteRow;
	Distribution.deleteRow = function(row) {
		var pledgeId;
		$(".ea-pledge div.queryLookupOption", $(row).siblings("tr.hiddenRow")).each(function() {
			var $elem = $(this);
			if ($elem.attr("id").indexOf("associatedPledgeId") > -1) {
				pledgeId = $elem.attr("selectedId");
			}
		});
		if (pledgeId) {
			PledgeSelector.deleteAssociatedPledge(pledgeId);
		}
		ddrFunct(row);
	}
	
	/* Decorator pattern in JS - check if a pledge needs to be dis-associated based on pledgeId */
	var ldaFunct = Lookup.deleteAssociation;
	Lookup.deleteAssociation = function(elem) {
		var pledgeId = $(elem).parent().attr("selectedId");
		if (pledgeId) {
			PledgeSelector.deleteAssociatedPledge(pledgeId);
		}
		ldaFunct(elem);
	}
});

var PledgeSelector = {
	loadPledgeSelector: function(elem) {
		this.lookupCaller = $(elem).siblings(".lookupScrollContainer").children(".multiLookupField");
		var queryString = "constituentId=" + $("#thisConstituentId").val() + "&selectedPledgeIds=" + $("#associatedPledgeIds").val();

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
			
			var giftDistElem = Ext.get("giftDistributionLinesDiv");
			giftDistElem.mask("Updating Distribution Lines...")
			
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
					Distribution.reInitDistribution();
					Distribution.rowCloner("table.distributionLines tbody.gridRow:last");
					giftDistElem.unmask();
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
	
	deleteAssociatedPledge: function(pledgeId) {
		var numLinesWithPledge = 0;
		$("div.ea-pledge").siblings("input[type=hidden]").each(function() {
			var $elem = $(this);
			if ($elem.attr("id").indexOf("associatedPledgeId") > -1 && $elem.val() == pledgeId) {
				numLinesWithPledge++;
			}
		});
		// If only 1 distribution line associated with this pledge is left, remove the associated pledge
		if (numLinesWithPledge === 1) {
			var $associatedPledgeIdsElem = $("#associatedPledgeIds");
			$associatedPledgeIdsElem.siblings(".multiLookupField").children(".multiOption").each(function() {
				var $elem = $(this);
				if ($elem.attr("selectedId") == pledgeId) {
					Lookup.removeSelectedVal($associatedPledgeIdsElem, pledgeId);
					$elem.fadeOut("fast", function() {
						$(this).remove();
					});
				}
			});
		}
	},
	
	deletePledge: function(elem) {
		var $pledgeElem = $(elem).parent();
		var pledgeId = $pledgeElem.attr("selectedId");
		if (pledgeId) {
			Lookup.removeSelectedVal($("#associatedPledgeIds"), pledgeId);

			$("div.ea-pledge div.queryLookupOption").each(function() {
				var $elem = $(this);
				if ($elem.attr("id").indexOf("associatedPledgeId") > -1 && $elem.attr("selectedId") == pledgeId) {
					var $lineRowElem = $elem.parents("tbody.gridRow").children("tr.lineRow");
					Distribution.deleteRow($lineRowElem);
				}
			});
			$pledgeElem.fadeOut("fast", function() {
				$(this).remove();
			});
		} 
	}	
}