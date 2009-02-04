$(document).ready(function() {
	(function() {
//		console.profile("buildTree");
//		console.time("buildTree");
		$("li.side:has(.picklist), li.side:has(.multiPicklist)").each(Picklist.buildPicklistTree);
//		console.timeEnd("buildTree");
//		console.profileEnd();
//		console.time("cascade");
		Picklist.cascadeElementsRoot();
//		console.timeEnd("cascade");
	})();
	$(".picklist:not(.paymentSourcePicklist)").bind("change", Picklist.togglePicklist);
	$(".paymentSourcePicklist").bind("change", Picklist.populatePaymentSourceAttributes);	
	
	$("table.tablesorter tbody td input").focus(function() {
		$(this).parents("tr:first").addClass("focused");
	}).blur(function() {
		$(this).parents("tr:first").removeClass("focused");
	});

	$("#searchForm #typeSearch").bind("change", function() {
		if ($(this).val() == "gifts") {
			$("#searchForm").attr("action", "giftSearch.htm");	
		}
		else {
			// assume people search
			$("#searchForm").attr("action", "personSearch.htm");	
		}
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

	$("input.number, input.percentage").numeric();

	$('#dialog').jqm({overlay: 50, onShow: MPower.centerDialog}).jqDrag('.dragHandle');

	$(".filters :input").bind("keyup change",function(){
		var queryString = $(".filters :input").serialize();
		$(".codeList").load("codeHelper.htm?view=table&"+queryString);
	});

	$(".picklistItemFilters :input").bind("keyup change",function(){
		var queryString = $(".picklistItemFilters :input").serialize();
		$(".picklistItemList").load("picklistItemHelper.htm?view=table&"+queryString);
	});

	$("input.code").each(function() {
		var $elem = $(this);
		if (typeof $elem.attr("autocomplete") == "undefined") {
			Lookup.codeAutoComplete($elem);
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

var Picklist = {	
	rootTrees: {},
	
	/** When the document is ready, build the tree(s) of items that cascade each other */
	buildPicklistTree: function() {
		var $parentNode = $(this); // this may be an li, or div
		var tree = Picklist.getTree($parentNode);
		
		if (tree.children.length == 0) {
			if (tree.parents.length == 0) {
				tree.isRoot = true; // assume this is the root, will be reset in 'setParentForChild' if not
			}
			var $recursiveTargets = null;
	
			var $picklists = $parentNode.filter(".picklist, .multiPicklist"); // either THIS is a picklist
			var $nested = $parentNode.find(".picklist, .multiPicklist"); // or it COULD have immediate descendent picklists
			$picklists = $picklists.add($nested); // these are all the children of this node
			$picklists.each(function() {
				var $myPicklist = $(this);
				
				var selectors = $myPicklist.attr("references");
				
				var refs = new Array();
				var selectedRefs = new Array();
				if (selectors) {
					refs = selectors.split(",");
				}
				selectedRefs = $("div#selectedRef-" + $myPicklist.attr("id")).text().replace(new RegExp(" ","g"), '').split(",");
	
				for (var x = 0, len = refs.length; x < len; x++) {
					var optionSelected = false;
					var thisRef = jQuery.trim(refs[x]);
					if (jQuery.inArray(thisRef, selectedRefs) > -1) {
						optionSelected = true;
					}
					var $targets = $(thisRef);
					$targets.each(function() {
						var $myTarget = $(this);
						var parentChildSet = false;
						parentChildSet |= Picklist.setChildForParent($parentNode, $myTarget);
						parentChildSet |= Picklist.setParentForChild($myTarget, $parentNode, optionSelected);
						if (parentChildSet) {
							// To prevent the same node from being evaluated again and again, check if a change was made; if so, add to recursive targets, else, no need to recursively evaluate further
							$recursiveTargets = $recursiveTargets ? $recursiveTargets.add($myTarget) : $myTarget;
						}
					});
				}		
			});	
			if (tree.isRoot) {
				var thisId = $parentNode.attr("id");
				tree.isSelected = true;
				Picklist.rootTrees[thisId] = tree;				
			}				
			if ($recursiveTargets) {
				$recursiveTargets.each(Picklist.buildPicklistTree);
			}
		}
	},
	
	setChildForParent: function($parentNode, $childNode) {
		var parentTree = Picklist.getTree($parentNode);
		var childId = $childNode.attr("id");

		var parentChildSet = false;
		if (!parentTree.isExistingChild(childId)) {
			parentTree.children.push($childNode);
			parentTree.childIds[childId] = true;
			parentChildSet = true;
		}
		return parentChildSet;
	},
		
	setParentForChild: function($childNode, $parentNode, optionSelected) {
		var childTree = Picklist.getTree($childNode);
		var parentId = $parentNode.attr("id");
		
		Picklist.setIsSelected(childTree, optionSelected, $parentNode, parentId);
		var parentChildSet = false;
		if (!childTree.isExistingParent(parentId)) {
			childTree.parents.push($parentNode);
			childTree.parentIds[parentId] = true;
			childTree.isRoot = false;
			
			var thisId = $childNode.attr("id");
			delete Picklist.rootTrees[thisId];
			parentChildSet = true;
		}
		return parentChildSet;
	},
	
	setIsSelected: function(childTree, optionSelected, $parentNode, parentId) {
		if (childTree.isSelected != optionSelected) {
			var parentTree = Picklist.getTree($parentNode);
			if (childTree.parents.length == 1) {
				if (childTree.parentIds[parentId]) {
					// If the parent of this child points to the child more than once, use an OR condition
					childTree.isSelected = childTree.isSelected | optionSelected;
				}
				else {
					childTree.isSelected = optionSelected;
				} 
			}
			else {
				/* Try to determine if THIS parentNode is also a child of the childNode's OTHER parentNodes; 
				 * if it is a child and THIS parentNode is selected, override the previous 'optionSelected' value with the one from THIS parentNode;
				 * otherwise, if THIS parentNode is not a child of the childNode's OTHER parentNodes, use an OR condition
				 */
				var isChild = Picklist.isChildOf(childTree.parents, parentId);
				if (isChild) {
					if (parentTree.isSelected) {
						childTree.isSelected = optionSelected; 
					}
				}
				else {
					childTree.isSelected = childTree.isSelected | optionSelected;
				}
			}
		}
	},
	
	isChildOf: function(parents, parentId) {
		var theParentTree = null;
		var parentsParents = new Array();
		for (var i = 0, len = parents.length; i < len; i++) {
			var $myParent = $(parents[i]);
			if ($myParent.attr("id") != parentId) {
				var parentTree = Picklist.getTree($myParent);
				if (parentTree.isExistingChild(parentId)) {
					theParentTree = parentTree;
					break;
				}
				else {
					parentsParents.push(parentTree.parents);
				}
			}
		}
		if (theParentTree == null && parentsParents.length > 0) {
			theParentTree = Picklist.isChildOf(parentsParents);
		}
		return theParentTree;
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
				isAParentSelected: function() {
					var pSel = false;
					for (var i = 0, len = this.parents.length; i < len; i++) {
						if ($(this.parents[i]).data("tree").isSelected) {
							pSel = true;
							break;
						}
					}
					return pSel;
				},
				isExistingParent: function(aParentId) {
					return this.parentIds[aParentId];
				},
				isExistingChild: function(id) {
					return this.childIds[id];
				}
			};
			$elem.data("tree", tree);
		}
		return tree;
	},
	
	isMultiPicklist: function($elem) {
		return $elem.hasClass("multiPicklist");
	},
	
	findOptions: function($elem) {
		var $options = null;
		var isMultiPicklist = Picklist.isMultiPicklist($elem);
		if (isMultiPicklist) {
			$options = $elem.children("div.multiPicklistOption");
		}
		else {
			$options = $elem.find("option");
		}
		return $options;
	},
	
	getCascaders: function() {
		return { shown: null, hidden: null, ids: {} };
	},
	
	hideShowCascaders: function(cascaders) {
		if (cascaders.hidden) { 
			cascaders.hidden.hide(); 
		}
		if (cascaders.shown) { 
			cascaders.shown.show(); 
		}
	},
		
	cascadeElementsRoot: function () {
		var cascaders = Picklist.getCascaders();
		for (var treeId in Picklist.rootTrees) {
			var tree = Picklist.rootTrees[treeId];
			var $elem = tree.node;

			cascaders.shown = cascaders.shown ? cascaders.shown.add($elem) : $elem; // all root elements are expected to be visible
			cascaders = Picklist.cascadeElementsChildren($(tree.children), cascaders);												
		}
		Picklist.hideShowCascaders(cascaders);		
	},
	
	cascadeElementsChildren: function($children, cascaders) {
		var $nextLevelChildren = null;
		if ($children) {
			$children.each(function() {
				var $child = $(this);
				var tree = Picklist.getTree($child); 
				if (tree.isSelected && tree.isAParentSelected()) { // if the child node is selected, check the parent(s) to make sure at least one is selected
					cascaders.shown = cascaders.shown ? cascaders.shown.add($child) : $child;
				}
				else {
					cascaders.hidden = cascaders.hidden ? cascaders.hidden.add($child) : $child;
				}
				$nextLevelChildren = $nextLevelChildren ? $nextLevelChildren.add($(tree.children)): $(tree.children);
			});
			cascaders = Picklist.cascadeElementsChildren($nextLevelChildren, cascaders);
		}
		return cascaders;
	},
	
	togglePicklist: function() {
		var $elem = $(this);

		var $containerElem = $elem.parents("li.side"); // get this picklist's container element
		var tree = Picklist.getTree($containerElem);
		tree.isSelected = true;
		
		var cascaders = Picklist.getCascaders();
		cascaders.shown = cascaders.shown ? cascaders.shown.add($elem) : $elem;
		
		var pickedSelector = "";
		var isMultiPicklist = Picklist.isMultiPicklist($elem);
		if (isMultiPicklist) {
			var $options = Picklist.findOptions($elem);
			$options.each(function() {
				var $optElem = $(this);
				if ($optElem.is(":visible")) {
					var myRef = $optElem.attr('reference');
					pickedSelector += myRef + ",";
				}
			});
			if (pickedSelector && pickedSelector.length > 0) {
				pickedSelector = pickedSelector.substring(0, pickedSelector.length - 1);
			}
		}
		else {
			pickedSelector = $(this.options[this.selectedIndex]).attr("reference");
			if (!pickedSelector) {
				pickedSelector = "";
			}
		}
		for (var y = 0, len = tree.children.length; y < len; y++) {
			var $child = $(tree.children[y]);
			var isPicked =  $child.is(pickedSelector);
			var childTree = Picklist.getTree($child);
			var id = $child.attr("id");
			
			if (isPicked != childTree.isSelected) {
				var theParentTree = Picklist.isChildOf(tree.children, id);
				if (theParentTree && theParentTree.isSelected) {
					var innerSelect = theParentTree.node.find(".picklist");
					var $innerPicklist = $(innerSelect);
					var isInnerMultiPicklist = Picklist.isMultiPicklist($innerPicklist);
					var $innerPicklistOptions = Picklist.findOptions($innerPicklist);
					
					var innerPicked = false;					
					if (isInnerMultiPicklist) {
						$innerPicklistOptions.each(function() {
							var $innerOptElem = $(this);
							if ($innerOptElem.is(":visible")) {
								var innerRef = $innerOptElem.attr('reference');
								innerPicked |= $child.is(innerRef);
							}
						});
					}
					else {
						var select = innerSelect.get(0);
						var innerRef = $(select.options[select.selectedIndex]).attr("reference");
						innerPicked |= $child.is(innerRef);
					}
					childTree.isSelected = innerPicked;
				}
				else {
					childTree.isSelected = isPicked;
				}
			}
		}
   		cascaders = Picklist.cascadeElementsChildren($(tree.children), cascaders);
		Picklist.hideShowCascaders(cascaders);					
	},
	
	setSelectedAddressPhoneByValue: function($select, value) {
		var tree = Picklist.getTree($select.parents("li.side"));
		
		// If no numeric ID selected, use "none"
		if (isNaN(parseInt(value, 10)) || $select.containsOption(value) == false) {
			value = "none";
		}

		var $options = Picklist.findOptions($select);
		
		$options.each(function() {
			var $optElem = $(this);
			var optionSelected = false;
			if (value == $optElem.val()) {
				optionSelected = true;
				$optElem.attr("selected", "true");
			}
			var selector = $optElem.attr('reference');
			if (selector != null && selector.length) {
				var $targets = $(selector);
				$targets.each(function() {
					var $myTarget = $(this);
					Picklist.getTree($myTarget).isSelected = optionSelected;
				});
			}
		});		
	},
	
	populatePaymentSourceAttributes: function() {
		var $option = $(this).find("option:selected");
		if ($option.length && isNaN(parseInt($option.val(), 10)) == false) {
			var addressId = $option.attr("address");
			var phoneId = $option.attr("phone");
			
			Picklist.setSelectedAddressPhoneByValue($("select#selectedAddress"), addressId);
			Picklist.setSelectedAddressPhoneByValue($("select#selectedPhone"), phoneId);
			
			// ACH
			var achholder = $option.attr("achholder");
			if (achholder) {
				$("div.gift_editAch div#paymentSource_achHolderName, div.commitment_editAch div#paymentSource_achHolderName").text(achholder);
			}
			var routing = $option.attr("routing");
			if (routing) {
				$("div.gift_editAch div#paymentSource_achRoutingNumberDisplay, div.commitment_editAch div#paymentSource_achRoutingNumberDisplay").text(routing);
			}
			var acct = $option.attr("acct");
			if (acct) {
				$("div.gift_editAch div#paymentSource_achAccountNumberDisplay, div.commitment_editAch div#paymentSource_achAccountNumberDisplay").text(acct);
			}
			
			// Credit Card
			var cardholder = $option.attr("cardholder");
			if (cardholder) {
				$("div.gift_editCreditCard div#paymentSource_creditCardHolderName, div.commitment_editCreditCard div#paymentSource_creditCardHolderName").text(cardholder);
			}
			var cardType = $option.attr("cardType");
			if (cardType) {
				$("div.gift_editCreditCard div#paymentSource_creditCardType, div.commitment_editCreditCard div#paymentSource_creditCardType").text(cardType);
			}
			var number = $option.attr("number");
			if (number) {
				$("div.gift_editCreditCard div#paymentSource_creditCardNumberDisplay, div.commitment_editCreditCard div#paymentSource_creditCardNumberDisplay").text(number);
			}
			var exp = $option.attr("exp");
			if (exp) {
				$("div.gift_editCreditCard div#paymentSource_creditCardExpiration, div.commitment_editCreditCard div#paymentSource_creditCardExpiration").text(exp);
			}
		}
		$(this).each(Picklist.togglePicklist);
	}
}
var MPower = {
	gotoUrl: function(url) {
		window.location.href = url;
	},
	
	confirmGoToUrl: function(url, msg) {
		if (confirm(msg)) {
			this.gotoUrl(url);
		}
	},
	
	centerDialog: function($hash) {
		var $dialog = $hash.w;
		var x = "-" + ($dialog.width() / 2) + "px";
		var y = "-" + ($dialog.height() / 2) + "px";
		$dialog.css("margin-left", x);
		$dialog.css("margin-top", y);
		$dialog.show();
	}	
}

// TODO: move below to individual JS
var Lookup = {
	lookupCaller: null,
	
	hiliteAndFade: function($elem) {
		$elem.animate({ backgroundColor: "yellow"}, "slow").animate({backgroundColor: "#FFF"}, "slow");
	},
	
	removeNbsp: function(itemSelected) {
		return itemSelected.extra[0].replace("&nbsp;", "");
	},
	
	codeAutoComplete: function($elem) {
		/* Temporarily disable auto-completion for BETA */
		$elem.bind("focus", function() {
			this.blur(); 
		});
		
		/*
		var oldVal = $elem.val();
		$elem.autocomplete("codeHelper.htm?view=autoComplete&type=" + $elem.attr("codeType"), {
			//delay:10,
			minChars:0,
			max:20,
			width: 185,
//			formatItem:formatItem,
//			loadingClass:"",
			mustMatch: true//,
//			onItemSelect: function(itemSelected) {
//				Lookup.codeAutoCompleteCallback(itemSelected, $elem); // TODO: put back
//			}
		});
		*/ 
	},
	
	codeAutoCompleteCallback: function(itemSelected, $input) {
		$("#" + $input.attr("otherFieldId")).val("");
		$input.siblings("input:hidden").val(itemSelected.selectValue);
//		var text = Lookup.removeNbsp(itemSelected);
//		$input.val(itemSelected.selectValue + " - " + text);
//		$input.siblings("input:hidden").val(itemSelected.selectValue);
	},
	
	loadCodePopup: function(elem, showOtherField) {
		var $elem = $(elem).siblings(':text');
		var lookupType = $elem.attr("lookup");
		this.lookupCaller = $elem;

		if (!showOtherField) {
			showOtherField = false;
		}
		$.ajax({
			type: "GET",
			url: "codeHelper.htm",
			data: "type=" + lookupType + "&showOtherField=" + showOtherField,
			success: function(html){
				$("#dialog").html(html);
				Lookup.singleCommonBindings();
				Lookup.codeLookupBindings();
				Lookup.radioClickEventHandler();
				$("#dialog").jqmShow();
			},
			error: function(html) {
				// TODO: improve error handling
				alert("The server was not available.  Please try again.");
			}
		});
		return false;
	},
	
	codeLookupBindings: function() {
		$("div.modalContent input#doneButton").bind("click", function() {
			Lookup.useCode();
			$("#dialog").jqmHide();	
		});
		$(".modalSearch input[type=text]").bind("keyup", function(){
			Lookup.doQuery(Lookup.getCodeData);
		});
		$(".modalSearch input#findButton").bind("click", function(){
			Lookup.doQuery(Lookup.getCodeData);
		});		
	},
	
	getCodeData: function() {
		return { view: "resultsOnly", type: $("div.modalSearch input#type").val() };
	},
	
	useCode: function() {
		var $elem = $('#codeResultsUl input[name=option]:checked');
		var selectedVal = $elem.val();
		
		if (selectedVal) {
			$("#" + Lookup.lookupCaller.attr("otherFieldId")).val("");
			Lookup.lookupCaller.siblings("input:hidden").val(selectedVal);
			Lookup.lookupCaller.val(selectedVal);
			Lookup.lookupCaller.vkfade(true);
		}
		else {
			var $optionElem = $("div.modalContent input#otherOptionText");
			var typedVal = $optionElem.val();
			if (typedVal != $optionElem.attr("defaultValue")) {
				Lookup.lookupCaller.siblings("input:hidden").val("");
				$("#" + Lookup.lookupCaller.attr("otherFieldId")).val(typedVal);
				Lookup.lookupCaller.val(typedVal);
				Lookup.lookupCaller.vkfade(true);
			}
		}
	},
	
	loadQueryLookupCommon: function(elem) {
		this.lookupCaller = $(elem).parent();		
		return $(elem).eq(0).attr("fieldDef");
	},
	
	loadQueryLookup: function(elem, showOtherField) {
		if (!showOtherField) {
			showOtherField = false;
		}
		$.ajax({
			type: "GET",
			url: "queryLookup.htm",
			data: "fieldDef=" + Lookup.loadQueryLookupCommon(elem) + "&showOtherField=" + showOtherField,
			success: function(html){
				$("#dialog").html(html);
				Lookup.singleCommonBindings();
				Lookup.queryLookupBindings();
				$("#dialog").jqmShow();
			},
			error: function(html) {
				// TODO: improve error handling
				alert("The server was not available.  Please try again.");
			}
		});
	},
	
	singleCommonBindings: function() {
		$("div.modalContent input#cancelButton").bind("click", function() {
			$("#dialog").jqmHide();					
		});
		$("div.modalContent form").bind("submit", function() {
			return false;
		});
		$("input.defaultText").bind("focus", function() {
			var $elem = $(this);
			if ($elem.val() == $elem.attr("defaultValue")) {
				$elem.removeClass("defaultText");
				$elem.val("");
			}
		});
		$("input.defaultText").bind("blur", function() {
			var $elem = $(this);
			if ($elem.val() == "") {
				$elem.addClass("defaultText");
				$elem.val($elem.attr("defaultValue"));
			}
		});
		$("input.defaultText").bind("keyup", function(event) {
			if (event.keyCode == 13) { // return key
				$("div.modalContent input#doneButton").click();
			}
		});
	},
	
	queryLookupBindings: function() {
		$("div.modalContent input#doneButton").bind("click", function() {
			Lookup.useQueryLookup();
			$("#dialog").jqmHide();	
		});
		$(".modalSearch input[type=text]").bind("keyup", function(){
			Lookup.doQuery(Lookup.getQueryData);
		});
		$(".modalSearch input#findButton").bind("click", function(){
			Lookup.doQuery(Lookup.getQueryData);
		});		
	},
	
	getQueryData: function() {
		return { view: "resultsOnly", fieldDef: $("div.modalSearch input#fieldDef").val(), searchOption: $("#searchOption").val() };
	},
	
	radioClickEventHandler: function() {
		var $prevElem = null;
		$("div.modalContent ul.queryUl :radio").bind("click", function() {
			if ($prevElem) {
				$prevElem.parent("li").removeClass("selected");
			} 
			var $elem = $(this);
			if ($elem.get(0).checked) {
				$elem.parent("li").addClass("selected"); 
			}
			else {
				$elem.parent("li").removeClass("selected"); 
			}
			$prevElem = $elem;
		});		
		$("#queryResultsDiv").removeClass("noDisplay");
	},
	
	doQuery: function(dataFunction) {
		var queryString = $("#searchOption").val() + "=" + $("#searchText").val();
		var url = $("div.modalContent form").attr("action");
		$("#queryResultsDiv").load(url + "?" + queryString, dataFunction(), Lookup.radioClickEventHandler);
	},
	
	loadMultiQueryLookup: function(elem) {
		var fieldDef = this.loadQueryLookupCommon(elem);		
		var queryString = this.serializeMultiQueryLookup(this.lookupCaller.children("div.multiQueryLookupOption"));
		$.ajax({
			type: "GET",
			url: "multiQueryLookup.htm",
			data: queryString + "&fieldDef=" + fieldDef,
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
	
	/* For previously selected options, create a query string from the attribute 'selectedIds' on each text box.  The queryString is the format selectedIds=selectedId,selectedId2,... */
	serializeMultiQueryLookup: function(options) {
		var queryString = "selectedIds=";
		$(options).each(function() {
			var $elem = $(this);
			queryString += escape($elem.attr("selectedId")) + ",";
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
	
	useQueryLookup: function() {
		var $elem = $('#queryResultsUl input[name=option]:checked');
		var selectedVal = $elem.val();
		
		if (selectedVal) {
			Lookup.lookupCaller.parent().children(":hidden").eq(0).val(selectedVal);
			var displayVal = $elem.attr('displayvalue');
			Lookup.lookupCaller.children("div.queryLookupOption").remove();
			var $cloned = Lookup.lookupCaller.parent().find("div.clone").clone(true);
			$("#" + Lookup.lookupCaller.parent().children(":hidden").attr("otherFieldId")).val("");
			$cloned.attr("selectedId", selectedVal);
			$cloned.attr("id", "lookup-" + selectedVal);
			var $popLink = $cloned.find("a[target='_blank']");
			$popLink.attr("href", $elem.siblings("a[target='_blank']").attr("href"));
			$popLink.text(displayVal);
			$cloned.removeClass("clone").removeClass("noDisplay");
			$cloned.prependTo(Lookup.lookupCaller);
			$cloned.vkfade(true);
		}
		else {
			var $optionElem = $("div.modalContent input#otherOptionText");
			var typedVal = $optionElem.val();
			if (typedVal != $optionElem.attr("defaultValue")) {
				Lookup.lookupCaller.children("div.queryLookupOption").remove();
				Lookup.lookupCaller.parent().children(":hidden").eq(0).val(""); // reset the corresponding field to nothing
				var $cloned = Lookup.lookupCaller.parent().find("div.clone").clone(true);
				$("#" + Lookup.lookupCaller.parent().children(":hidden").attr("otherFieldId")).val(typedVal);
				$cloned.attr("id", "lookup-" + typedVal);
				var $popLink = $cloned.find("a[target='_blank']");
				$popLink.remove();
				$cloned.find("span").text(typedVal);
				$cloned.removeClass("clone").removeClass("noDisplay");
				$cloned.prependTo(Lookup.lookupCaller);
				$cloned.vkfade(true);
			}
		}
	},
	
	/** Deprecated, to be removed */
	setCodeValue: function(val, text) {
//		if (text) {
//			this.lookupCaller.val(val + " - " + text);
//			this.lookupCaller.siblings("input:hidden").val(val);
//		}
//		else {
			this.lookupCaller.val(val);
			
//			this.lookupCaller.bind("focus", function() {
//				Lookup.hiliteAndFade($(this));
//			});
//			this.lookupCaller.focus();
//		}
		$('#dialog').jqmHide();
		this.lookupCaller = null;
		return false;
	},
	
	doMultiQuery: function() {
		var queryString = $("#searchOption").val() + "=" + $("#searchText").val();
		var selectedIdsStr = "";
		$("ul#selectedOptions li :checkbox").each(function() {
			selectedIdsStr += $(this).attr("id") + ",";
		});
		if (selectedIdsStr.length > 0) {
			selectedIdsStr = selectedIdsStr.substring(0, selectedIdsStr.length - 1); // remove the last ','
		}
		
		$("#availableOptions").load("multiQueryLookup.htm?" + queryString, { fieldDef: $("#fieldDef").val(), selectedIds: selectedIdsStr, searchOption: $("#searchOption").val() });
	},
	
	multiQueryLookupBindings: function() {
		$(".modalSearch :input").bind("keyup", function(){
			Lookup.doMultiQuery();
		});
		$(".modalSearch input#findButton").bind("click", function(){
			Lookup.doMultiQuery();
		});		
		$("div.modalContent form").bind("submit", function() {
			return false;
		});
		$("div.modalContent input#doneButton").bind("click", function() {
			var idsStr = "";
			var names = new Array();
			var ids = new Array();
			var hrefs = new Array();
			$("ul#selectedOptions :checkbox").each(function() {
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
					$(this).css("display", "").vkfade(true);
				}
				else {
					$(this).css("display", "none");
				}
			});
			$(Lookup.lookupCaller).each(Picklist.togglePicklist);
			$("#dialog").jqmHide();	
		});			
	},
	
	multiCommonBindings: function() {
		$("table.multiSelect thead input[type=checkbox]").bind("click", function() {
			var isChecked = $(this).attr("checked");
			var tdOrder = $(this).attr("selection") === "available" ? "first" : "last";
			var isFirst = true;
			$("table.multiSelect tbody td:" + tdOrder + " li").each(function() {
				$target = $(this);
				var optionType = ($target.parent("ul").attr("id") === "selectedOptions" ? "available" : "selected");
				var $clone = MultiSelect.moveOption($target, optionType);
				if (isFirst) {
					var ulElem = $("ul#" + optionType + "Options").get(0);
					ulElem.scrollTop = ulElem.scrollHeight - $clone.height();
					isFirst = false;
				}
			});
		});
		$("table.multiSelect tbody").bind("click", function(event) {
			var $target = $(event.target);
			if ($target.is("input[type=checkbox]") && $target.attr("checked")) { 
				$target = $target.parent("li");
				var optionType = ($target.parent("ul").attr("id") === "selectedOptions" ? "available" : "selected");
				var $clone = MultiSelect.moveOption($target, optionType);
				var ulElem = $("ul#" + optionType + "Options").get(0);
				ulElem.scrollTop = ulElem.scrollHeight - $clone.height();
			}
		});
		$("div.modalContent input#cancelButton").bind("click", function() {
			$("#dialog").jqmHide();					
		});
	},
	
	deleteOption: function(elem) {
		var $parent = $(elem).parent();
		var $hiddenElems = $parent.parent().parent().children("input[type=hidden]");
		
		$hiddenElems.each(function() {
			var $hiddenElem = $(this);
			var otherFieldId = $hiddenElem.attr("otherFieldId");
			if (otherFieldId) { 
				$("#" + otherFieldId).val("");
			}
			var selectedId = $parent.attr("selectedId");
			var idsValues = $hiddenElem.val().split(",");
			
			var newIdsValues = "";
			for (var x = 0; x < idsValues.length; x++) {
				if (idsValues[x] != selectedId) {
					newIdsValues += idsValues[x] + ",";
				}
			}
			$hiddenElem.val(newIdsValues.substring(0, newIdsValues.length - 1));
		});
		 
		$parent.fadeOut("fast", function() {
			if ($(this).hasClass("multiQueryLookupOption") || $(this).hasClass("queryLookupOption")) {
				// For query lookups, remove the node
				$(this).remove();
			}
			else {
				// For multi-picklists, don't remove, just hide
				$(this).css("display", "none");
				$parent.parent("div.multiPicklist").each(Picklist.togglePicklist);
			}
		});
	}
}

// TODO: move below to individual JS
var MultiSelect = {
	moveOption: function(optionElem, thisOptionType) {
		var prevOptionType = (thisOptionType === "selected" ? "available" : "selected");
		var $clone = $(optionElem).clone();
		this.uncheck($clone);
		$(optionElem).remove();
		$("input#" + prevOptionType + "All").removeAttr("checked");
		$clone.appendTo("ul#" + thisOptionType + "Options");
		$clone.vkfade();
		return $clone;
	},
		
	check: function(elem) {
		elem.children("input[type=checkbox]").eq(0).attr("checked", "true");
	},
	
	uncheck: function(elem) {
		elem.children("input[type=checkbox]").eq(0).removeAttr("checked");
	}
}


// Create a placeholder console object in case Firebug is not present.
if (typeof console == "undefined" || typeof console.log == "undefined") var console = { log: function() {} };
// Initialize the console (workaround for current Firebug defect)
console.log();
