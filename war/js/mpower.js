$(document).ready(function() {
	(function() {
//		console.time("buildTree");
		$("li.side:has(.picklist), li.side:has(.multiPicklist)").each(MPower.buildPicklistTree);
//		console.timeEnd("buildTree");
//		console.time("cascade");
		MPower.cascadeElementsRoot();
//		console.timeEnd("cascade");
	})();
//		console.time("cascade");
//	$(".picklist, .multiPicklist").each(MPower.toggleReferencedElements);
//		console.timeEnd("cascade");

	$(".picklist").change(MPower.toggleReferencedElements);
	$(".paymentSourcePicklist").change(MPower.populatePaymentSourceAttributes);
	
	
	$("table.tablesorter tbody td input").focus(function() {
		$(this).parents("tr:first").addClass("focused");
	}).blur(function() {
		$(this).parents("tr:first").removeClass("focused");
	});

	$("ul.formFields li input, ul.formFields li select").change(function() {
       	$("#savedMarker").fadeOut("slow");
	});

	$(".cluetip[rel]").cluetip({
		cluetipClass:'default',
		showTitle: false,
		dropShadow: false,
		waitImage: false,
		fx: {
        	open:'fadeIn'
		}
	});
	
	$("span.secondary:has('a.active')").each(function(){
		$(this).addClass("active").prev(".groupHeader").addClass("groupActive");
	});
	
	$("div.navGroup:not(:has('a.active'))").hoverIntent({
		sensitivity: 7,
		interval: 100,
		over: function(){
			$(this).find("span.secondary").filter(":hidden").slideDown();
		},
		out: function() {},
		timeout: 1000
	 });
	 
	 $("div.accountOptions").hoverIntent({
		sensitivity: 7,
		interval: 100,
		timeout: 1000,
		over: function() {},
		out: function(){
			$(this).find("span.secondary:not('.active')").filter(":visible").slideUp();
		}
	 });
	 

	 
	 $(".primaryNav li ul").each(function(){
	 	var btnWidth = $(this).prev("a").outerWidth() + 20;
	 	var selfWidth = $(this).outerWidth();
	 	if (btnWidth > selfWidth) {
	 		$(this).width(btnWidth);
	 	}
	 });
	 
	 $(".primaryNav li:has(ul)").hover(function(){
			$(this).find("ul").show().prev("a").addClass("bactive");
		},function(){
			$(this).find("ul").hide().prev("a").removeClass("bactive");
		});


	$("form#gift input#amount").bind("keyup change",function(){
		var amounts=$("table#gift_distribution input.amount");
		if(amounts.length == 1) {
			amounts.val($(this).val());
		}
		updateTotals();
	});

	distributionLineBuilder($("#gift_distribution tr"));
	rowCloner("#gift_distribution tr:last");
	$("#gift_distribution tr:last .deleteButton").hide();

	$('#dialog').jqm({overlay: 50, onShow: MPower.centerDialog}).jqDrag('.dragHandle');
	
//	$("#newCodeForm").submit(function(){
//		$.ajax({
//			type: "POST",
//			url: "code.htm",
//			data: $(this).serialize(),
//			success: function(html){
//				$("#dialog .modalContent").html(html);
//				$("#dialog").jqmShow();
//				return false;
//			},
//			error: function(html){
//				$("#dialog .modalContent").html("didn't work");
//				$("#dialog").jqmShow();
//				return false;
//			}
//		});
//		return false;
//	});

	$(".filters :input").bind("keyup change",function(){
		var queryString = $(".filters :input").serialize();
		$(".codeList").load("codeHelper.htm?view=table&"+queryString);
	});

	$("input.code").each(function(){
		if(typeof $(this).attr("autocomplete") == "undefined") {
			$(this).autocomplete("codeHelper.htm?type="+$(this).attr("codeType"),
			{
				delay:10,
				minChars:0,
				maxItemsToShow:20,
				formatItem:formatItem,
				loadingClass:""
			});
		}
	});
	
	Date.format = 'mm/dd/yyyy';
	$('input.date').datePicker({startDate:'01/01/1900'});
	
	$("div.multiPicklistOption, div.queryLookupOption, div.multiQueryLookupOption").hover(
		function() {
			$(this).find("a.deleteOption").removeClass("noDisplay");		
		},
		function() {
			$(this).find("a.deleteOption").addClass("noDisplay");		
		}
	);
	
	$(":input, select, textbox").bind("focus", function(event) {
		if ($.browser.msie) {
			$(this).addClass("focused");
		}
		var target = $(this); 
		if ($(this).parent("div.lookupWrapper").length) { 
			target = $(this).parent("div.lookupWrapper");
		}
		target.prevAll("label.desc").addClass("inFocus");
	});
	$(":input, select, textbox").bind("blur", function() {
		if ($.browser.msie) {
			$(this).removeClass("focused");
		}
		var target = $(this); 
		if ($(this).parent("div.lookupWrapper").length) { 
			target = $(this).parent("div.lookupWrapper");
		}
		target.prevAll("label.desc").removeClass("inFocus");
	});
});

/* END DOCUMENT READY CODE */

function updateTotals() {
		var subTotal = 0;
		$("table#gift_distribution input.amount").each(function(){
			var rowVal=parseFloat($(this).val());
			if(!isNaN(rowVal)) subTotal += rowVal;
		});
		$("#subTotal span.value").html(subTotal.toString());
		if (subTotal==parseFloat($("input#amount").val())) {
			$("#subTotal").removeClass("warning");
		} else {
			$("#subTotal").addClass("warning");
		}
}
function rowCloner(selector) {
	$(selector).one("keyup",function(event){
		if(event.keyCode != 9) { // ignore tab
			addNewRow(distributionLineBuilder);
		}
		rowCloner(selector); // Re-attach to the (new) last table row
	});
}
function distributionLineBuilder(newRow) {
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
		$(this).autocomplete("codeHelper.htm?type="+codeType,
			{
				delay:10,
				minChars:0,
				maxItemsToShow:20,
				formatItem:formatItem,
				loadingClass:""
			});
	});
	newRow.removeClass("focused");
}
function addNewRow(builder) {
	var newRow = $(".tablesorter tr:last").clone(false);
	builder(newRow);
	var i = newRow.attr("rowindex");
	var j = parseInt(i) + 1;
	newRow.attr("rowindex",j);
	var findExp = new RegExp("\\["+i+"\\]","gi");
	newRow.find("input").each(function(){
			var field = $(this);
			var nameString = field.attr('name').replace(findExp, "["+j+"]");
			field.attr('name',nameString);
			field.val("");
		});
	$(".tablesorter tr:last .deleteButton").show();
	$(".tablesorter").append(newRow);
}
function deleteRow(row) {
	if($(".tablesorter tbody tr").length > 1) {
		row.fadeOut("slow",function(){$(this).remove();updateTotals();})
	} else {
		alert("Sorry, you cannot delete that row since it's the only remaining row.")
	};
}
function formatItem(row) {
	return row[0] + "<span style=\"font-size:10px;\"> - " + row[1] + "</span>";
}

function saveInPlace(elem, baseUrl) {
	var queryString = $(elem).parent().parent().find("input").serialize();
	$.ajax({
		type: "POST",
		url: baseUrl,
		data: queryString,
		success: function(html){
			$(elem).parent().parent().html(html);
			return false;
		},
		error: function(html){
			alert("Code could not be saved. Please ensure that the code has a unique value.");
			return false;
		}
	});
	return false;
}
function newInPlace(elem, baseUrl) {
	var queryString = $(elem).parent().parent().find("input").serialize();
	$.ajax({
		type: "POST",
		url: baseUrl,
		data: queryString,
		success: function(html){
			var newRow=document.createElement("tr");
			$(newRow).html(html);
			$(".justAdded table").append(newRow);
			return false;
		},
		error: function(html){
			alert("Code could not be saved. Please ensure that the code has a unique value.");
			return false;
		}
	});
	return false;
}
function editInPlace(elem) {
	$(elem).parent().parent().load($(elem).attr("href"));
	return false;
}

function getPage(elem) {
		var queryString = $(".searchForm").find("input").serialize();
		var baseUrl = $(elem).attr("href");
		$.ajax({
			type: "POST",
			url: baseUrl,
			data: queryString+"&view=ajaxResults",
			success: function(html){
				$("#searchResults").html(html);
				//return false;
			},
			error: function(html){
				alert("An error has occurred.  Please refresh the page and try again.");
				//return false;
			}
		});
		return false;
}

var MPower = {
	rootTrees: {},
	blarg: null,
	
	gotoUrl: function(url) {
		window.location.href = url;
	},
	
	confirmGoToUrl: function(url, msg) {
		if (confirm(msg)) {
			this.gotoUrl(url);
		}
	},
	
	/** When the document is ready, build the tree(s) of items that cascade each other */
	buildPicklistTree: function() {
		var $parentNode = $(this); // this may be an li, or div
		var tree = MPower.getTree($parentNode);
		
		if (tree.parents.length == 0) {
			tree.isRoot = true; // assume this is the root, will be reset in 'setParentForChild' if not
		}
		var $targets = null;

		var $picklists = $parentNode.filter(".picklist"); // either THIS is a picklist
		var $nested = $parentNode.find(".picklist"); // or it COULD have immediate descendent picklists
		$picklists = $picklists.add($nested); // these are all the children of this node
		$picklists.each(function() {
			var $myPicklist = $(this);
			var $options = MPower.findOptions($myPicklist);
			if ($options) {
				var isMultiPicklist = $myPicklist.hasClass("multiPicklist");
				
				$options.each(function() {
					var $optElem = $(this);
					var selector = $optElem.attr('reference');
					if (selector != null && selector.length) {
						var optionSelected = false;
						if ((isMultiPicklist === true && $optElem.css("display") != "none") || 
							(isMultiPicklist === false && $optElem.attr("selected"))) {
							 optionSelected = true;
						}
						$targets = $targets ? $targets.add($(selector)) : $(selector);
						$targets.each(function() {
							MPower.setChildForParent($parentNode, $(this));
							MPower.setParentForChild($(this), $parentNode, optionSelected);
						});
					}
				});		
			}
		});	
		if (tree.isRoot) {
			var thisId = $parentNode.attr("id");
			tree.isSelected = true;
			MPower.rootTrees[thisId] = tree;				
		}				
		if ($targets) {
			$targets.each(MPower.buildPicklistTree);
		}
	},
	
	setParentForChild: function($childNode, $parentNode, optionSelected) {
		var tree = MPower.getTree($childNode);
		var parentId = $parentNode.attr("id");
		if (!tree.parentIds[parentId]) {
			tree.parents.push($parentNode);
			tree.parentIds[parentId] = true;
			tree.isRoot = false;
			tree.isSelected = tree.isSelected | optionSelected;
			
			var thisId = $childNode.attr("id");
			delete MPower.rootTrees[thisId];
		}
	},
	
	setChildForParent: function($parentNode, $childNode) {
		var tree = MPower.getTree($parentNode);
		var childId = $childNode.attr("id");
		if (!tree.childIds[childId]) {
			tree.children.push($childNode);
			tree.childIds[childId] = true;
		}
	},
	
	getTree: function($elem) {
		var tree = $elem.data("tree");
		if (!tree) {
			tree = { node: $elem, 
				parents: new Array(), 
				children: new Array(), 
				parentIds: new Object(), 
				childIds: new Object(), 
				isRoot: false, 
				isSelected: false, 
				isInit: false,
				isParentSelected: function() {
					var pSel = false;
					for (var p in this.parents) {
						if ($(this.parents[p]).data("tree").isSelected) {
							pSel = true;
							break;
						}
					}
					return pSel;
				} 
			};
			$elem.data("tree", tree);
		}
		return tree;
	},
	
	findOptions: function($elem) {
		var $options = null;
		var isMultiPicklist = $elem.hasClass("multiPicklist");
		if (isMultiPicklist) {
			$options = $elem.children("div.multiPicklistOption");
		}
		else {
			$options = $elem.find("option");
		}
		return $options;
	},
		
	cascadeElementsRoot: function () {
		var cascaders = { shown: null, hidden: null, ids: {} };
		for (var treeId in MPower.rootTrees) {
			var tree = MPower.rootTrees[treeId];
			var $elem = tree.node;

			cascaders.shown = cascaders.shown ? cascaders.shown.add($elem) : $elem; // all root elements are expected to be visible
			cascaders = MPower.cascadeElementsChildren($(tree.children), cascaders);												
		}		
		if (cascaders.hidden) { 
			cascaders.hidden.hide(); 
		}
		if (cascaders.shown) { 
			cascaders.shown.show(); 
		}
	},
	
	cascadeElementsChildren: function($children, cascaders) {
		var $nextLevelChildren = null;
		if ($children) {
			$children.each(function() {
				var $child = $(this);
				var tree = $child.data("tree"); 
				if (tree.isSelected && tree.isParentSelected()) { // if the child node is selected, check the parent(s) to make sure at least one is selected
					cascaders.shown = cascaders.shown ? cascaders.shown.add($child) : $child;
				}
				else {
					cascaders.hidden = cascaders.hidden ? cascaders.hidden.add($child) : $child;
				}
				$nextLevelChildren = $nextLevelChildren ? $nextLevelChildren.add($(tree.children)): $(tree.children);
			});
			cascaders = MPower.cascadeElementsChildren($nextLevelChildren, cascaders);
		}
		return cascaders;
	},
	
	toggleReferencedElements: function () {
		var elem = this;
		var $toBeShown;
		var $toBeHidden;
		var $toBeToggled;
		var $toBeHiddenNested;
		
		var $options = MPower.findOptions($(elem));
		var isMultiPicklist = $(elem).hasClass("multiPicklist");
		
		$options.each(function() {
			var selector = this.getAttribute('reference');
			if (selector != null && selector.length) {
				var $target = $(selector);
				var $picklists = $(selector).filter(".picklist"); // filter if THIS is a picklist
				var $nested = $(selector).find(".picklist"); // find all descendent picklists
				$picklists = $picklists.add($nested);

				if ((isMultiPicklist === true && $(this).css("display") != "none") || 
					(isMultiPicklist === false && this.selected)) {
					$toBeShown = $toBeShown ? $toBeShown.add($target) : $target;
					$toBeToggled = $toBeToggled ? $toBeToggled.add($picklists) : $picklists;
				} 
				else {
					$toBeHidden = $toBeHidden ? $toBeHidden.add($target) : $target;
					$toBeHiddenNested = $toBeHiddenNested ? $toBeHiddenNested.add($nested) : $nested;
				}
			}
		});

		if (typeof $toBeHidden != "undefined") { 
			$toBeHidden.hide(); 
		}
		if (typeof $toBeHiddenNested != "undefined") { 
			$toBeHiddenNested.each(MPower.hideAllReferencedElements); 
		}
		if (typeof $toBeShown != "undefined") { 
			$toBeShown.show(); 
		}
		if (typeof $toBeToggled != "undefined") { 
			$toBeToggled.each(MPower.toggleReferencedElements); 
		}
	},
	
	hideAllReferencedElements: function() {
		var elem = this;
		var $options = null;
		var isMultiPicklist = $(elem).hasClass("multiPicklist");
		if (isMultiPicklist) {
			$options = $(elem).children("div.multiPicklistOption");
		}
		else {
			$options = $(elem).children("option");
		}
		
		$options.each(function() {
			MPower.hideOptionElement(this);
		});
	},
	
	hideOptionElement: function(elem) {
		var selector = elem.getAttribute('reference');
		if (selector != null && selector.length) {
			var $target = $(selector);
			var $picklists = $(selector).filter(".picklist");
			var $nested = $(selector).find(".picklist");
			$picklists = $picklists.add($nested);
			$target.hide();
			$picklists.each(MPower.hideAllReferencedElements);
		}
	},
	
	centerDialog: function($hash) {
		var $dialog = $hash.w;
		var x = "-" + ($dialog.width() / 2) + "px";
		var y = "-" + ($dialog.height() / 2) + "px";
		$dialog.css("margin-left", x);
		$dialog.css("margin-top", y);
		$dialog.show();
	},
	
	populatePaymentSourceAttributes: function() {
		var $option = $(this).find("option:selected");
		if ($option.length) {
			var addressId = $option.attr("address");
			var phoneId = $option.attr("phone");
			
			$("select#selectedAddress").resetToFirstOption(); // reset to first before setting to the right value, just in case that value does not exist
			$("select#selectedPhone").resetToFirstOption(); // reset to first before setting to the right value, just in case that value does not exist
			if (addressId) {
				$("select#selectedAddress").selectOptions(addressId);
			}
			if (phoneId) {
				$("select#selectedPhone").selectOptions(phoneId);
			}
			
			// ACH
			var achholder = $option.attr("achholder");
			if (achholder) {
				$("div.gift_editAch div#paymentSource\\.achHolderName").text(achholder);
			}
			var routing = $option.attr("routing");
			if (routing) {
				$("div.gift_editAch div#paymentSource\\.achRoutingNumber").text(routing);
			}
			var acct = $option.attr("acct");
			if (acct) {
				$("div.gift_editAch div#paymentSource\\.achAccountNumber").text(acct);
			}
			
			// Credit Card
			var cardholder = $option.attr("cardholder");
			if (cardholder) {
				$("div.gift_editCreditCard div#paymentSource\\.creditCardHolderName").text(cardholder);
			}
			var cardType = $option.attr("cardType");
			if (cardType) {
				$("div.gift_editCreditCard div#paymentSource\\.creditCardType").text(cardType);
			}
			var number = $option.attr("number");
			if (number) {
				$("div.gift_editCreditCard div#paymentSource\\.creditCardNumber").text(number);
			}
			var exp = $option.attr("exp");
			if (exp) {
				$("div.gift_editCreditCard div#paymentSource\\.creditCardExpiration").text(exp);
			}
		}
	}	
}

// TODO: move below to individual JS
var Lookup = {
	lookupCaller: null,
	
	loadCodePopup: function(elem) {
		var lookupType = $(elem).eq(0).attr("lookup");
		this.lookupCaller = elem;
		$("#dialog").load("codeHelper.htm?view=popup&type=" + lookupType, function(){
			$("#dialog").jqmShow();
		});
		return false;
	},
	
	loadQueryLookupCommon: function(elem) {
		this.lookupCaller = $(elem).parent();		
		return $(elem).eq(0).attr("fieldDef");
	},
	
	loadQueryLookup: function(elem) {
		$.ajax({
			type: "POST",
			url: "queryLookup.htm",
			data: "fieldDef=" + Lookup.loadQueryLookupCommon(elem),
			success: function(html){
				$("#dialog").html(html);
				$("#dialog").jqmShow();
			},
			error: function(html) {
				// TODO: improve error handling
				alert("The server was not available.  Please try again.");
			}
		});
	},
	
	loadMultiQueryLookup: function(elem) {
		var fieldDef = this.loadQueryLookupCommon(elem);		
		var queryString = this.serializeMultiQueryLookup(this.lookupCaller.children("div.multiQueryLookupOption"));
		$.ajax({
			type: "POST",
			url: "multiQueryLookup.htm",
			data: queryString + "fieldDef=" + fieldDef,
			success: function(html){
				$("#dialog").html(html);
				Lookup.multiCommonBindings();
				Lookup.multiQueryLookupBindings();
				$("#dialog").jqmShow();
			},
			error: function(html) {
				// TODO: improve error handling
				alert("The server was not available.  Please try again.");
			}
		});
	},
	
	/* For previously selected options, create a query string from the attribute 'selectedIds' on each text box.  The queryString is the format selectedIds=selectedId1&selectedId=selectedId2&... */
	serializeMultiQueryLookup: function(options) {
		var queryString = "";
		$(options).each(function() {
			var $elem = $(this);
			queryString += "selectedIds=" + escape($elem.attr("selectedId")) + "&";
		});
		return queryString;
	},
	
	loadMultiPicklist: function(elem) {
		this.lookupCaller = $(elem).parent();
		var queryString = this.serializeMultiPicklist(this.lookupCaller.children());
		$.ajax({
			type: "POST",
			url: "multiPicklist.htm",
			data: queryString,
			success: function(html){
				$("#dialog").html(html);
				Lookup.multiCommonBindings();
				Lookup.multiPicklistBindings();
				$("#dialog").jqmShow();
			},
			error: function(html){
				// TODO: improve error handling
				alert("The server was not available.  Please try again.");
			}
		});
	},
	
	/* For previously selected options, create a query string in the format 1=code1|displayValue1|reference1|selected1&2=code2|displayValue2|reference2|selected2& ... */
	serializeMultiPicklist: function(options) {
		var queryString = "";
		var counter = 1;
		$(options).each(function() {
			var $elem = $(this);
			if ($elem.hasClass("multiPicklistOption")) {
				queryString += counter++ + "=" + escape($elem.attr("selectedId")) + "|" + escape($.trim($elem.text())) + "|" + escape($elem.attr("reference")) + "|" + ($elem.css("display") == "none" ? "false" : "true") + "&";
			}
			else if ($elem.attr("type") == "hidden"){
				queryString += $elem.serialize();
			}
		});
		return queryString;
	},
	
	useQueryLookup: function(elem, value) {
		Lookup.lookupCaller.parent().children(":hidden").eq(0).val(value);

		var displayVal = $(elem).attr('displayvalue');
		Lookup.lookupCaller.children("div.queryLookupOption").remove();
		
		var $cloned = Lookup.lookupCaller.parent().find("div.clone").clone(true);
		$cloned.attr("id", "lookup-" + displayVal);
		$cloned.attr("selectedId", value);
		var $popLink = $cloned.find("a[target='_blank']");
		$popLink.attr("href", $(elem).attr('gotourl'));
		$popLink.text(displayVal);
		$cloned.removeClass("clone").removeClass("noDisplay");
		$cloned.prependTo(Lookup.lookupCaller);

		$('#dialog').jqmHide();
	},
	
	setCodeValue: function(val) {
		this.lookupCaller.val(val);
		$('#dialog').jqmHide();
		this.lookupCaller = null;
		return false;
	},
	
	doMultiQuery: function() {
		var queryString = $("#searchOption").val() + "=" + $("#searchText").val() + "&fieldDef=" + $("#fieldDef").val();
		$("#availableOptions").load("multiQueryLookup.htm?view=resultsOnly&" + queryString);
	},
	
	multiQueryLookupBindings: function() {
		$(".modalSearch input[type=text]").bind("focus", function() {
			if ($(this).attr("defaultText") == $(this).val()) {
				$(this).removeClass("defaultText");
				$(this).val("");
			}
		});
		$(".modalSearch input[type=text]").bind("blur", function() {
			if ($(this).val() == "") {
				$(this).addClass("defaultText");
				$(this).val($(this).attr("defaultText"));
			}
		});
		$(".modalSearch :input").bind("keyup", function(){
			Lookup.doMultiQuery();
		});
		$(".modalSearch input#findButton").bind("click", function(){
			Lookup.doMultiQuery();
		});		
		$("div.modalContent input#doneButton").bind("click", function() {
			var idsStr = "";
			var names = new Array();
			var ids = new Array();
			var hrefs = new Array();
			$("ul#selectedOptions li").each(function() {
				var $chkBox = $(this).children("input[type=checkbox]").eq(0);
				var thisId = $chkBox.attr("id");
				idsStr += thisId + ",";
				ids[ids.length] = thisId;
				var $thisLink = $(this).children("a[href]").eq(0);
				names[names.length] = $.trim($thisLink.text());
				hrefs[hrefs.length] = $thisLink.attr("href");
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
			} 
			$("#dialog").jqmHide();					
		});		
	},
	
	multiPicklistBindings: function() {
		$("div.modalContent input#doneButton").bind("click", function() {
			var idsStr = "";
			var selectedNames = new Object();
			$("ul#selectedOptions li").each(function() {
				var $chkBox = $(this).children("input[type=checkbox]").eq(0);
				var thisId = $chkBox.attr("id");
				idsStr += thisId + ",";
				selectedNames[$.trim($chkBox.attr("name"))] = true;
			});
			idsStr = (idsStr.length > 0 ? idsStr.substring(0, idsStr.length - 1) : idsStr); // remove the trailing comma

			Lookup.lookupCaller.parent().children("input[type=hidden]").eq(0).val(idsStr);
			
			Lookup.lookupCaller.children("div.multiPicklistOption").each(function() {
				if (selectedNames[$.trim($(this).attr("selectedId"))] === true) {
					$(this).css("display", "");
				}
				else {
					$(this).css("display", "none");
				}
			});
			$(Lookup.lookupCaller).each(MPower.toggleReferencedElements);
			$("#dialog").jqmHide();	
		});			
	},
	
	multiCommonBindings: function() {
		$("table.multiSelect tbody").bind("click", function(event) {
			var $target = $(event.target);
			if ($target.is("li,input[type=checkbox]")) { 
				if ($target.is("input[type=checkbox]")) {
					$target = $target.parent("li").eq(0);
				}
				if ($target.hasClass("picked")) {
					MultiSelect.uncheck($target);
				}
				else {
					MultiSelect.check($target);
				}
			}
		});
		$("table.multiSelect tbody").bind("dblclick", function(event) {
			var $target = $(event.target);
			if ($target.is("li")) { 
				var optionType = ($target.parent().attr("id") === "selectedOptions" ? "selected" : "available");
				MultiSelect.moveOption($target, optionType);
			}
		});
		$("table.multiSelect thead input[type=checkbox]").bind("click", function() {
			var isChecked = $(this).attr("checked");
			var tdOrder = $(this).attr("selection") === "available" ? "first" : "last";
			$("table.multiSelect tbody td:" + tdOrder + " li").each(function() {
				if (isChecked) {
					MultiSelect.check($(this));
				}
				else {
					MultiSelect.uncheck($(this));
				}
			});
		});
		$("table.multiSelect tbody a.rightArrow").bind("click", function() {
			$("ul#availableOptions li:has(':checkbox[checked]')").each(function() {
				MultiSelect.moveOption(this, "available");
			});
		});
		$("table.multiSelect tbody a.leftArrow").bind("click", function() {
			$("ul#selectedOptions li:has(':checkbox[checked]')").each(function() {
				MultiSelect.moveOption(this, "selected");
			});
		});
		$("div.modalContent input#cancelButton").bind("click", function() {
			$("#dialog").jqmHide();					
		});
//		$("table.multiSelect tbody ul li").draggable({ 
//			containment: $("table.multiSelect tbody"),
//			zIndex: 3001//,
//			snapMode: "outer",
//			snap: "table.multiSelect tbody ul"
//		});
//		$("table.multiSelect tbody ul").droppable({ 
//			accept: "table.multiSelect tbody ul li",
//			drop: function(event, ui) { 
//				var $droppable = $(this);
//				var $clone = ui.draggable.clone();
//				$clone.css();
//				ui.draggable.fadeOut("fast", function() {
//					$(this).remove();
//					$clone.appendTo($droppable);
//					$clone.fadeIn();
//				});
//				
//		    } 
//		});
	},
	
	deleteOption: function(elem) {
		var $parent = $(elem).parent();
		var selectedId = $parent.attr("selectedId");
		var idsElem = $parent.parent().parent().children(":hidden").eq(0);
		var idsValues = idsElem.val().split(",");
		
		var newIdsValues = "";
		for (var x = 0; x < idsValues.length; x++) {
			if (idsValues[x] != selectedId) {
				newIdsValues += idsValues[x] + ",";
			}
		}
		idsElem.val(newIdsValues.substring(0, newIdsValues.length - 1));
		 
		$parent.fadeOut("fast", function() {
			if ($(this).hasClass("multiQueryLookupOption") || $(this).hasClass("queryLookupOption")) {
				// For query lookups, remove the node
				$(this).remove();
			}
			else {
				MPower.hideOptionElement(this);
				
				// For multi-picklists, don't remove, just hide
				$(this).css("display", "none");
			}
		});
	}
}

// TODO: move below to individual JS
var MultiSelect = {
	moveOption: function(optionElem, thisOptionType) {
		var otherOptionType = (thisOptionType === "selected" ? "available" : "selected");
		var $clone = $(optionElem).clone();
		this.uncheck($clone);
		$(optionElem).remove();
		$("input#" + thisOptionType + "All").removeAttr("checked");
		$clone.appendTo("ul#" + otherOptionType + "Options");
	},
	
	check: function(elem) {
		elem.children("input[type=checkbox]").eq(0).attr("checked", "true");
		elem.addClass("picked");
	},
	
	uncheck: function(elem) {
		elem.children("input[type=checkbox]").eq(0).removeAttr("checked");
		elem.removeClass("picked");
	}
}

/*
// Create a placeholder console object in case Firebug is not present.
if (typeof console == "undefined" || typeof console.log == "undefined") var console = { log: function() {} };
// Initialize the console (workaround for current Firebug defect)
console.log();
*/