$(document).ready(function() {
    //eliminate these
	$("#giftListTable").tablesorter( { sortList: [[1,0]] , headers:{0:{sorter:false}} } );

	$("table.tablesorter tbody td input").focus(function() {
		$(this).parents("tr:first").addClass("focused");
	}).blur(function() {
		$(this).parents("tr:first").removeClass("focused");
	});

	$("ul.formFields li input, ul.formFields li select").change(function() {
       	$("#savedMarker").fadeOut("slow");
	});

	$(".picklist").each(MPower.toggleReferencedElements);
	$(".picklist").change(MPower.toggleReferencedElements);

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
		timeout: 1000
	 });
	 
	 $("div.accountOptions").hoverIntent({
		sensitivity: 7,
		interval: 100,
		timeout: 1000,
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

	$('#dialog').jqm({overlay:50}).jqDrag($('.jqmWindow h4'));
	
	$("#newCodeForm").submit(function(){
		$.ajax({
			type: "POST",
			url: "code.htm",
			data: $(this).serialize(),
			success: function(html){
				$("#dialog .modalContent").html(html);
				$("#dialog").jqmShow();
				return false;
			},
			error: function(html){
				$("#dialog .modalContent").html("didn't work");
				$("#dialog").jqmShow();
				return false;
			}
		});
		return false;
	});

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
	
	/* TODO: move to a forms.js */
	$("div.linkableLookupField").bind("click", function(event) {
		var $target = $(event.target);
		if ($target.is("input[href]")) { 
			var linkHref = $target.attr("href");
			window.open(linkHref, "_blank");
		}
	});

   }
);

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
		
/*		
		var recursive = function() {
			var elem = this;
			for (var i = 0;i < elem.options.length; i++) {
				var selector = elem[i].getAttribute('reference');
				if (selector != null && selector.length) {
					var $target = $(selector);
					var $picklists = $(selector).filter(".picklist");
					var $nested = $(selector).find(".picklist");
					$picklists = $picklists.add($nested);
					if (elem.options[i].selected) {
						$toBeShown = $toBeShown ? $toBeShown.add($target) : $target;
						$toBeToggled = $toBeToggled ? $toBeToggled.add($picklists) : $picklists;
					} else {
						$toBeHidden = $toBeHidden ? $toBeHidden.add($target) : $target;
						$toBeHiddenNested = $toBeHiddenNested ? $toBeHiddenNested.add($picklists) : $picklists;
					}
				}
			}
			
		}
		*/
		for (var i = 0;i < elem.options.length; i++) {
			var selector = elem[i].getAttribute('reference');
			if (selector != null && selector.length) {
				var $target = $(selector);
				var $picklists = $(selector).filter(".picklist");
				var $nested = $(selector).find(".picklist");
				$picklists = $picklists.add($nested);
				if (elem.options[i].selected) {
					$toBeShown = $toBeShown ? $toBeShown.add($target) : $target;
					$toBeToggled = $toBeToggled ? $toBeToggled.add($picklists) : $picklists;
				} else {
					$toBeHidden = $toBeHidden ? $toBeHidden.add($target) : $target;
					$toBeHiddenNested = $toBeHiddenNested ? $toBeHiddenNested.add($picklists) : $picklists;
				}
			}
		}
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
		for(var i = 0; i < elem.options.length;i++) {
			var selector = elem[i].getAttribute('reference');
			if(selector != null && selector.length) {
				var $target = $(selector);
				var $picklists = $(selector).filter(".picklist");
				var $nested = $(selector).find(".picklist");
				$picklists = $picklists.add($nested);
				$target.hide();
				$picklists.each(MPower.hideAllReferencedElements);
			}
		}
		/*
		*/
	}	
}

// TODO: move below to individual JS
var Lookup = {
	lookupCaller: null,
	
	loadCodePopup: function(elem) {
		var lookupType = $(elem).eq(0).attr("lookup");
		this.lookupCaller = elem;
		$("#dialog .modalContent").load("codeHelper.htm?view=popup&type="+lookupType,function(){
			$("#dialog").jqmShow();
		});
		return false;
	},
	
	loadQueryLookup: function(elem) {
		var fieldDef = $(elem).eq(0).attr("fieldDef");
		this.lookupCaller = elem;
		$("#dialog .modalContent").load("queryLookup.htm?fieldDef="+fieldDef,function(){
			$("#dialog").jqmShow();
		});
	},
	
	loadMultiQueryLookup: function(elem) {
		var fieldDef = $(elem).eq(0).attr("fieldDef");
		this.lookupCaller = $(elem).parent();
		
		$("#dialog .modalContent").load("multiQueryLookup.htm?fieldDef=" + fieldDef, function() {
			Lookup.multiCommonBindings();
			Lookup.multiQueryLookupBindings();
			$("#dialog").jqmShow();
		});
	},
	
	loadMultiPicklist: function(elem) {
		this.lookupCaller = $(elem).parent();
		var queryString = this.lookupCaller.children("input[type=hidden]").serialize();
		$.ajax({
			type: "POST",
			url: "multiPicklist.htm",
			data: queryString,
			success: function(html){
				$("#dialog .modalContent").html(html);
				$("#dialog h4#modalTitle").text($("#dialog input#modalTitleText").val());
				Lookup.multiCommonBindings();
				Lookup.multiPicklistBindings();
				$("#dialog").jqmShow();
			},
			error: function(html){
				$("#dialog .modalContent").html("The request was not available.  Please try again.");
				$("#dialog").jqmShow();
			}
		});
	},
	
	useQueryLookup: function(elem, value) {
		this.lookupCaller.find("a").attr("href",$(elem).attr('gotourl')).html($(elem).attr('displayvalue'));
		this.lookupCaller.nextAll(':hidden').val(value);
		$('#dialog').jqmHide();
		this.lookupCaller=null;
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
				names[names.length] = $.trim($(this).children("a[href='javascript:void(0)']").eq(0).text());
				hrefs[hrefs.length] = $chkBox.attr("href");
			});
			idsStr = (idsStr.length > 0 ? idsStr.substring(0, idsStr.length - 1) : idsStr); // remove the trailing comma

			Lookup.lookupCaller.parent().children("input[type=hidden]").eq(0).val(idsStr);
			
			Lookup.lookupCaller.children("input[type=text]").remove();
			for (var x = names.length - 1; x >= 0; x--) {
				Lookup.lookupCaller.prepend("<input type='text' name='picked-" + names[x] + "' id='picked-" + names[x] + "' value='" + names[x] + "' href='" + hrefs[x] + "'></input>");
			} 
			$("#dialog").jqmHide();					
		});		
	},
	
	multiCommonBindings: function() {
		$("table.multiSelect tbody").bind("click", function(event) {
			var $target = $(event.target);
			if ($target.is("ol,input[type=checkbox],a[href='javascript:void(0)']")) { 
				if ($target.is("input[type=checkbox],a[href='javascript:void(0)']")) {
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
				var $clone = $(this).clone();
				MultiSelect.uncheck($clone);
				$(this).remove();
				$("input#availableAll").removeAttr("checked");
				$clone.appendTo("ul#selectedOptions");
			});
		});
		$("table.multiSelect tbody a.leftArrow").bind("click", function() {
			$("ul#selectedOptions ol:has(':checkbox[checked]')").each(function() {
				var $clone = $(this).clone();
				MultiSelect.uncheck($clone);
				$(this).remove();
				$("input#selectedAll").removeAttr("checked");
				$clone.appendTo("ul#availableOptions");
			});
		});
		$("div.modalContent input#cancelButton").bind("click", function() {
			$("#dialog").jqmHide();					
		});
	},
	
	multiPicklistBindings: function() {
		$("div.modalContent input#doneButton").bind("click", function() {
			var idsStr = "";
			var names = new Array();
			var ids = new Array();
			var refs = new Array();
			$("ul#selectedOptions ol").each(function() {
				var $chkBox = $(this).children("input[type=checkbox]").eq(0);
				var thisId = $chkBox.attr("id");
				idsStr += thisId + ",";
				ids[ids.length] = thisId;
				names[names.length] = $.trim($(this).text());
				refs[refs.length] = $chkBox.attr("reference");
			});
			idsStr = (idsStr.length > 0 ? idsStr.substring(0, idsStr.length - 1) : idsStr); // remove the trailing comma

			Lookup.lookupCaller.parent().children("input[type=hidden]").eq(0).val(idsStr);
			
			Lookup.lookupCaller.children("input[type=text]").remove();
			for (var x = names.length - 1; x >= 0; x--) {
				Lookup.lookupCaller.prepend("<input type='text' name='picked-" + names[x] + "' id='picked-" + names[x] + "' value='" + names[x] + "' reference='" + refs[x] + "'></input>");
			} 
			$("#dialog").jqmHide();					
		});		
	}
}

// TODO: move below to individual JS
var MultiSelect = {
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