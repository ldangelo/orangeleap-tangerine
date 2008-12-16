$(document).ready(function() {
	$(".picklist, .multiPicklist").each(MPower.toggleReferencedElements);
	$(".picklist").change(MPower.toggleReferencedElements);

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

	$('#dialog').jqm({overlay: 50, onShow: MPower.centerDialog}).jqDrag($('.jqmWindow h4'));
	
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
	$('input.date').datePicker({startDate:'01/01/1996'});
	
	// <a href="javascript:void(0)" class="delete"><img src="images/icons/deleteRow.png" alt="Remove this option" title="Remove this option"/></a>
	
//	$("div.multiPicklistOption, div.queryLookupOption, div.multiQueryLookupOption").bind("mouseover", function(event) {
//		var $target = $(event.target);
//		$target.addClass("selected");		
//	});
//	$("div.multiPicklist :text, div.multiLookupField :text").bind("blur", function(event) {
//		var $target = $(event.target);
//		$target.removeClass("selected");
//	});
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
	toggleReferencedElements: function () {
		var elem = this;
		var $toBeShown;
		var $toBeHidden;
		var $toBeToggled;
		var $toBeHiddenNested;
		
		var $options = null;
		var isMultiPicklist = $(elem).hasClass("multiPicklist");
		if (isMultiPicklist) {
			$options = $(elem).children("div.multiPicklistOption");
		}
		else {
			$options = $(elem).children("option");
		}
		
		$options.each(function() {
			var selector = this.getAttribute('reference');
			if (selector != null && selector.length) {
				var $target = $(selector);
				var $picklists = $(selector).filter(".picklist");
				var $nested = $(selector).find(".picklist");
				$picklists = $picklists.add($nested);
				if ((isMultiPicklist === true && $(this).hasClass("noDisplay") === false) || 
					(isMultiPicklist === false && this.selected)) {
					$toBeShown = $toBeShown ? $toBeShown.add($target) : $target;
					$toBeToggled = $toBeToggled ? $toBeToggled.add($picklists) : $picklists;
				} 
				else {
					$toBeHidden = $toBeHidden ? $toBeHidden.add($target) : $target;
					$toBeHiddenNested = $toBeHiddenNested ? $toBeHiddenNested.add($picklists) : $picklists;
				}
			}
		});

		if (typeof $toBeHidden != "undefined") { 
			$toBeHidden.hide(); 
		}
		if (typeof $toBeShown != "undefined") { 
			$toBeShown.show(); 
		}
		if (typeof $toBeHiddenNested != "undefined") { 
			$toBeHiddenNested.each(MPower.hideAllReferencedElements); 
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
			var selector = this.getAttribute('reference');
			if (selector != null && selector.length) {
				var $target = $(selector);
				var $picklists = $(selector).filter(".picklist");
				var $nested = $(selector).find(".picklist");
				$picklists = $picklists.add($nested);
				$target.hide();
				$picklists.each(MPower.hideAllReferencedElements);
			}
		});
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
				queryString += counter++ + "=" + escape($elem.attr("code")) + "|" + escape($.trim($elem.text())) + "|" + escape($elem.attr("reference")) + "|" + ($elem.hasClass("noDisplay") ? "false" : "true") + "&";
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
		
		var $cloned = Lookup.lookupCaller.parent().find("div.clone").clone();
		$cloned.attr("id", "lookup-" + displayVal);
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
			$("ul#selectedOptions ol").each(function() {
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
				var $cloned = $toBeCloned.clone();
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
			$("ul#selectedOptions ol").each(function() {
				var $chkBox = $(this).children("input[type=checkbox]").eq(0);
				var thisId = $chkBox.attr("id");
				idsStr += thisId + ",";
				selectedNames[$.trim($chkBox.attr("name"))] = true;
			});
			idsStr = (idsStr.length > 0 ? idsStr.substring(0, idsStr.length - 1) : idsStr); // remove the trailing comma

			Lookup.lookupCaller.parent().children("input[type=hidden]").eq(0).val(idsStr);
			
			Lookup.lookupCaller.children("div.multiPicklistOption").each(function() {
				if (selectedNames[$.trim($(this).attr("code"))] === true) {
					$(this).removeClass("noDisplay");
				}
				else {
					$(this).addClass("noDisplay");
				}
			});
			$(Lookup.lookupCaller).each(MPower.toggleReferencedElements);
			$("#dialog").jqmHide();	
		});			
	},
	
	multiCommonBindings: function() {
		$("table.multiSelect tbody").bind("click", function(event) {
			var $target = $(event.target);
			if ($target.is("ol,input[type=checkbox]")) { 
				if ($target.is("input[type=checkbox]")) {
					$target = $target.parent("ol").eq(0);
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
			if ($target.is("ol")) { 
				var optionType = ($target.parent().attr("id") === "selectedOptions" ? "selected" : "available");
				MultiSelect.moveOption($target, optionType);
			}
		});
		$("table.multiSelect thead input[type=checkbox]").bind("click", function() {
			var isChecked = $(this).attr("checked");
			var tdOrder = $(this).attr("selection") === "available" ? "first" : "last";
			$("table.multiSelect tbody td:" + tdOrder + " ol").each(function() {
				if (isChecked) {
					MultiSelect.check($(this));
				}
				else {
					MultiSelect.uncheck($(this));
				}
			});
		});
		$("table.multiSelect tbody a.rightArrow").bind("click", function() {
			$("ul#availableOptions ol:has(':checkbox[checked]')").each(function() {
				MultiSelect.moveOption(this, "available");
			});
		});
		$("table.multiSelect tbody a.leftArrow").bind("click", function() {
			$("ul#selectedOptions ol:has(':checkbox[checked]')").each(function() {
				MultiSelect.moveOption(this, "selected");
			});
		});
		$("div.modalContent input#cancelButton").bind("click", function() {
			$("#dialog").jqmHide();					
		});
//		$("table.multiSelect tbody ul ol").draggable({ containment: $("table.multiSelect tbody ul") });
//		$("table.multiSelect tbody ul").droppable();
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


// Create a placeholder console object in case Firebug is not present.
if (typeof console == "undefined" || typeof console.log == "undefined") var console = { log: function() {} };
// Initialize the console (workaround for current Firebug defect)
console.log();