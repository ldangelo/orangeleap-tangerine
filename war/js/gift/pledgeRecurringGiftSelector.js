$(document).ready(function() {
	var pledgeIdsVal = $("#associatedPledgeIds").val(); 
	var recurringGiftIdsVal = $("#associatedRecurringGiftIds").val();
	
	if (pledgeIdsVal == '' && recurringGiftIdsVal == '') {
		$("a.selectorLookup").bind("click", function() {
			PledgeRecurringGiftSelector.loadSelector(this);
		});
	}
	else if (pledgeIdsVal.length > 0) {
		$("#associatedPledgeIds").parent().siblings("a.selectorLookup").eq(0).bind("click", function() {
			PledgeRecurringGiftSelector.loadSelector(this);
		});
		PledgeRecurringGiftSelector.disable($('#associatedRecurringGiftIds'));
	} 
	else if (recurringGiftIdsVal.length > 0) {
		$("#associatedRecurringGiftIds").parent().siblings("a.selectorLookup").eq(0).bind("click", function() {
			PledgeRecurringGiftSelector.loadSelector(this);
		});
		PledgeRecurringGiftSelector.disable($('#associatedPledgeIds'));
	} 
	
	/* Decorator pattern in JS - check if a pledge needs to be dis-associated based on pledgeId/recurringGiftId */
	var ddrFunct = Distribution.deleteRow;
	Distribution.deleteRow = function(row) {
		var pledgeId;
		var recurringGiftId;
		$(".ea-pledge div.queryLookupOption, .ea-recurringGift div.queryLookupOption", $(row).siblings("tr.hiddenRow")).each(function() {
			var $elem = $(this);
			if ($elem.attr("id").indexOf("associatedPledgeId") > -1) {
				pledgeId = $elem.attr("selectedId");
			}
			if ($elem.attr("id").indexOf("associatedRecurringGiftId") > -1) {
				recurringGiftId = $elem.attr("selectedId");
			}
		});
		var thisType;
		if (pledgeId && pledgeId != '') {
			PledgeRecurringGiftSelector.deleteAssociated(pledgeId, "pledge");
		}
		else if (recurringGiftId && recurringGiftId != '') {
			PledgeRecurringGiftSelector.deleteAssociated(recurringGiftId, "recurringGift");
		}
		ddrFunct(row);
	};
	
	/* Decorator pattern in JS - check if a pledge needs to be dis-associated based on pledgeId/recurringGiftId */
	var ldaFunct = Lookup.deleteAssociation;
	Lookup.deleteAssociation = function(elem) {
		var $parent = $(elem).parent();
		var thisId = $parent.attr("selectedId");
		if (thisId) {
			var $parentParent = $parent.parent();
			var thisType;
			if ($parentParent.hasClass("ea-pledge")) {
				thisType = "pledge";
			}
			else if ($parentParent.hasClass("ea-recurringGift")) {
				thisType = "recurringGift";
			}
			PledgeRecurringGiftSelector.deleteAssociated(thisId, thisType);
		}
		ldaFunct(elem);
	};
	
	$("#amount").one("blur", function(event) {
		var value = $(this).val(); 
		if (value && value != "" && $("#associatedPledgeIds").val() == '' && $("#associatedRecurringGiftIds").val() == '') {
			// should be either an associated pledge OR associated recurringGift, not both
			$("#thisAssociatedPledge, #thisAssociatedRecurringGift").each(function() {
				var $elem = $(this);
				var thisName = $elem.val();
				var isPledge = false;
				if ($elem.attr("id") == "thisAssociatedPledge") {
					var thisId = $elem.attr("pledgeId");
					var queryString = "selectedPledgeIds=" + thisId + "&amount=" + $("#amount").val();
					var $associatedIdsElem = $("#associatedPledgeIds");
					PledgeRecurringGiftSelector.disable($('#associatedRecurringGiftIds'));
					isPledge = true;
				}
				else {
					var thisId = $elem.attr("recurringGiftId");
					var queryString = "selectedRecurringGiftIds=" + thisId + "&amount=" + $("#amount").val();
					var $associatedIdsElem = $("#associatedRecurringGiftIds");
					PledgeRecurringGiftSelector.disable($('#associatedPledgeIds'));
					isPledge = false;
				}
				
				PledgeRecurringGiftSelector.updateDistribution(queryString);
				
				$associatedIdsElem.val(thisId);
				PledgeRecurringGiftSelector.lookupCaller = $associatedIdsElem.siblings(".multiLookupField");
				PledgeRecurringGiftSelector.doClone(thisId, thisName, $associatedIdsElem.siblings(".clone"), isPledge);
			});
		}
	});
});

var PledgeRecurringGiftSelector = {
	loadSelector: function(elem) {
		this.lookupCaller = $(elem).siblings(".lookupScrollContainer").children(".multiLookupField");
		var queryString = "constituentId=" + $("#thisConstituentId").val();
		if (this.lookupCaller.hasClass("ea-pledge")) {
			queryString += "&selectedPledgeIds=" + $("#associatedPledgeIds").val();
			var baseUrl = "pledgeSelector.htm";
		}
		else {
			queryString += "&selectedRecurringGiftIds=" + $("#associatedRecurringGiftIds").val();
			var baseUrl = "recurringGiftSelector.htm";
		}

		$.ajax({
			type: "POST",
			url: baseUrl + "?constituentId=" + $("#thisConstituentId").val(),
			data: queryString,
			success: function(html) {
				$("#dialog").html(html);
				PledgeRecurringGiftSelector.selectorBindings();
				Lookup.multiCommonBindings();
				$("#dialog").jqmShow();
			}
		});
	},
	
	disable: function($elem) {
		$elem.val('');
		var $parent = $elem.parent();
		$parent.addClass('disabled');
		$parent.siblings('a.selectorLookup').eq(0).unbind("click");
	},
	
	enable: function($elem) {
		var $parent = $elem.parent();
		$parent.removeClass('disabled');
		$parent.siblings('a.selectorLookup').eq(0).bind("click", function() {
			PledgeRecurringGiftSelector.loadSelector(this);
		});
	},
	
	selectorBindings: function() {		
		$("#selectorForm #doneButton").bind("click", function() {
			var queryString = PledgeRecurringGiftSelector.serializeDistributionLines();
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
			
			var isPledge = false;
			if ($("#selectorForm").attr("action").indexOf("pledge") > -1) {
				queryString += "&selectedPledgeIds=";
				isPledge = true;	
			}
			else {
				queryString += "&selectedRecurringGiftIds=";
			}
			queryString += idsStr + "&amount=" + $("#amount").val();
			
			PledgeRecurringGiftSelector.updateDistribution(queryString);
			PledgeRecurringGiftSelector.lookupCaller.siblings("input[type=hidden]").eq(0).val(idsStr);
			PledgeRecurringGiftSelector.lookupCaller.children("div.multiQueryLookupOption").remove();
			
			var $toBeCloned = PledgeRecurringGiftSelector.lookupCaller.parent().find("div.clone");
			
			for (var x = ids.length - 1; x >= 0; x--) {
				PledgeRecurringGiftSelector.doClone(ids[x], displayNames[x], $toBeCloned, isPledge);
			}
			if (isPledge) {
				if (idsStr == '') {
					PledgeRecurringGiftSelector.enable($('#associatedRecurringGiftIds'));
				}
				else {
					PledgeRecurringGiftSelector.disable($('#associatedRecurringGiftIds'));
				}
			}
			else {
				if (idsStr == '') {
					PledgeRecurringGiftSelector.enable($('#associatedPledgeIds'));
				}
				else {
					PledgeRecurringGiftSelector.disable($('#associatedPledgeIds'));
				}
			} 
			$("#dialog").jqmHide();					
		});		
	},
	
	updateDistribution: function(queryString) {
		var giftDistElem = Ext.get("giftDistributionLinesDiv");
		giftDistElem.mask("Updating Distribution Lines...");
		
		$.ajax({
			type: "POST",
			url: "giftCombinedDistributionLines.htm?constituentId=" + $("#thisConstituentId").val(),
			data: queryString,
			success: function(html) {
				var $giftDistributionLinesDiv = $("#giftDistributionLinesDiv");
				$giftDistributionLinesDiv.empty();
				Distribution.amtPctMap = {};
				$giftDistributionLinesDiv.append(html);
				var $gridRows = $("table.distributionLines tbody.gridRow");
				Distribution.index = $gridRows.length + 1;
				Distribution.distributionLineBuilder($gridRows);
				Distribution.reInitDistribution();
				Distribution.rowCloner("table.distributionLines tbody.gridRow:last");
				giftDistElem.unmask();
			}
		});
	},
	
	doClone: function(thisId, thisName, $toBeCloned, isPledge) {
		var $cloned = $toBeCloned.clone(true);
		var $popLink = $cloned.find("a[target='_blank']");
		$cloned.attr("id", "lookup-" + thisId);
		$cloned.attr("selectedId", thisId);
		var linkStr = isPledge ? "pledge.htm?pledgeId=" + thisId : "recurringGift.htm?recurringGiftId=" + thisId;
		$popLink.attr("href", linkStr + "&constituentId=" + $("#thisConstituentId").val());
		$popLink.text(thisName);
		
		$cloned.removeClass("clone").removeClass("noDisplay");
		$cloned.prependTo(PledgeRecurringGiftSelector.lookupCaller);
		$cloned.vkfade(true);
	},
	
	serializeDistributionLines: function() {
		var queryString = "";
		$(":text, :radio, :checkbox, input[type=hidden]", $("tbody.gridRow")).each(function() {
			var $elem = $(this);
			var elemType = $elem.attr("type").toLowerCase();
			if (elemType == "checkbox" || elemType == "radio") {
				if ($elem.attr("checked")) {
					queryString += $elem.attr("name") + "=" + escape($elem.val()) + "&";
				}
			}
			else {
				queryString += $elem.attr("name") + "=" + escape($elem.val()) + "&";
			}
		});
		return queryString;
	},
	
	deleteAssociated: function(id, thisType) {
		var numLines = 0;
		var capsType = thisType.substring(0, 1).toUpperCase() + thisType.substring(1); 
		$("div.ea-" + thisType, $("tr.hiddenRow")).siblings("input[type=hidden]").each(function() {
			var $elem = $(this);
			if ($elem.attr("id").indexOf("associated" + capsType + "Id") > -1 && $elem.val() == id) {
				numLines++;
			}
		});
		// If only 1 distribution line associated with this pledge/recurringGift is left, remove the associated pledge/recurringGift
		if (numLines === 1) {
			var $associatedIdsElem = $("#associated" + capsType + "Ids");
			$associatedIdsElem.siblings(".multiLookupField").children(".multiOption").each(function() {
				var $elem = $(this);
				if ($elem.attr("selectedId") == id) {
					Lookup.removeSelectedVal($associatedIdsElem, id);
					$elem.fadeOut("fast", function() {
						$(this).remove();
						if (thisType == 'recurringGift') {
							var otherId = '#associatedPledgeIds';
						}
						else {
							var otherId = '#associatedRecurringGiftIds';
						}
						PledgeRecurringGiftSelector.enable($(otherId));
					});
				}
			});
		}
	},
	
	deleteThis: function(elem) {
		var $parent = $(elem).parent();
		var thisId = $parent.attr("selectedId");
		if (thisId) {
			var $parentParent = $parent.parent();
			if ($parentParent.hasClass("ea-pledge")) {
				var multiIdsElem = 'associatedPledgeIds';
				var singleIdElem = 'associatedPledgeId';
				var thisClass = 'ea-pledge';
				var otherId = '#associatedRecurringGiftIds';
			}
			else {
				var multiIdsElem = 'associatedRecurringGiftIds';
				var singleIdElem = 'associatedRecurringGiftId';
				var thisClass = 'ea-recurringGift';
				var otherId = '#associatedPledgeIds';
			}
			Lookup.removeSelectedVal($("#" + multiIdsElem), thisId);

			$("div." + thisClass + " div.queryLookupOption", $("tr.hiddenRow")).each(function() {
				var $elem = $(this);
				if ($elem.attr("id").indexOf(singleIdElem) > -1 && $elem.attr("selectedId") == thisId) {
					var $lineRowElem = $elem.parents("tbody.gridRow").children("tr.lineRow");
					Distribution.deleteRow($lineRowElem);
				}
			});
			$parent.fadeOut("fast", function() {
				$(this).remove();
				PledgeRecurringGiftSelector.enable($(otherId));
			});
		} 
	}	
}