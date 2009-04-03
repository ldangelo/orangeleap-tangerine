var PledgeSelector = {
	loadPledgeSelector: function(elem) {
		this.lookupCaller = $(elem).siblings(".lookupScrollContainer").children(".multiLookupField");
		var queryString = "constituentId=" + $("#thisConstituentId").val() + "&selectedPledgeIds=" + $("#pledges").val();

		$.ajax({
			type: "POST",
			url: "pledgeSelector.htm",
			data: queryString,
			success: function(html){
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
			var idsStr = "";
			var names = new Array();
			var ids = new Array();
			var hrefs = new Array();
			$("ul#selectedOptions :checkbox", $("#dialog")).each(function() {
				var $chkboxElem = $(this);
				var thisId = $chkboxElem.attr("id");
				idsStr += thisId + ",";
				ids[ids.length] = thisId;
				names[names.length] = $.trim($chkboxElem.parent("li").text());
				hrefs[hrefs.length] = $chkboxElem.siblings("a[href]").attr("href");
			});
			idsStr = (idsStr.length > 0 ? idsStr.substring(0, idsStr.length - 1) : idsStr); // remove the trailing comma

			Lookup.lookupCaller.parent().children("input[type=hidden]").eq(0).val(idsStr);
			
			Lookup.lookupCaller.children("div.multiQueryLookupOption").remove();
			
			var $toBeCloned = Lookup.lookupCaller.parent().find("div.clone");
			
			for (var x = names.length - 1; x >= 0; x--) {
				var $cloned = $toBeCloned.clone(true);
				var $popLink = $cloned.find("a[target='_blank']");
				$cloned.attr("id", "lookup-" + names[x]);
				$cloned.attr("selectedId", ids[x]);
				$popLink.attr("href", hrefs[x]);
				$popLink.text(names[x]);
				
				$cloned.removeClass("clone").removeClass("noDisplay");
				$cloned.prependTo(Lookup.lookupCaller);
				$cloned.vkfade(true);
			} 
			$("#dialog").jqmHide();					
		});		
	}	
}