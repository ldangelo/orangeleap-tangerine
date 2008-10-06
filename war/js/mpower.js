$(document).ready(function()
   {
	$("#giftListTable").tablesorter( { sortList: [[1,0]] , headers:{0:{sorter:false}} } );
	$("table.defaultSort").tablesorter();

	$("table.tablesorter tbody td input").focus(function() {
		$(this).parent().parent().addClass("focused");
	}).blur(function() {
		$(this).parent().parent().removeClass("focused");
	});

	$("ul.formFields li input, ul.formFields li select").change(function() {
       	$("#savedMarker").fadeOut("slow");
	});

	$("#paymentType").change(showSelectedSection);

	$("#personTitle").cluetip({
		cluetipClass:'default',
		showTitle: false,
		dropShadow: false,
		waitImage: false,
		fx: {
        	open:'fadeIn'
		}
	});

	///$("table#gift_distribution input.amount").bind("keyup change", updateTotals);

	$("form#gift input#value").bind("keyup change",function(){
		var amounts=$("table#gift_distribution input.amount");
		if(amounts.length == 1) {
			amounts.val($(this).val());
		}
		updateTotals();
	});

	distributionLineBuilder($("#gift_distribution tr"));
	rowCloner("#gift_distribution tr:last");
	$("#gift_distribution tr:last .deleteButton").hide();

	$('#dialog').jqm({overlay:10}).jqDrag($('.jqmWindow h4'));
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

	$(".filters :input").bind("keyup",function(){
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

   }
);

/* END DOCUMENT READY CODE */

function updateTotals() {
		var subTotal = 0;
		$("table#gift_distribution input.amount").each(function(){
			var rowVal=parseInt($(this).val());
			if(!isNaN(rowVal)) subTotal += rowVal;
		});
		$("#subTotal span.value").html(subTotal.toString());

		if (subTotal==parseInt($("input#value").val())) {
			$("#subTotal").removeClass("warning");
		} else {
			$("#subTotal").addClass("warning");
		}
}
function rowCloner(selector) {
	$(selector).bind("keyup",function(event){
		if(event.keyCode != 9) { // ignore tab
			addNewRow(distributionLineBuilder);
		}
		rowCloner(selector);
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
		$(this).focus(function(){
			$(this).parent().addClass("showLink");
			}).blur(function(){
				$(this).parent().removeClass("showLink");
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
function showSelectedSection(elemId) {
	if(typeof elemId == "string") {
		var elem=document.getElementById(elemId);
		//showSelectedSection.call(document.getElementById(elemId));
	} else {
		var elem=this;
	}
	for(i=0;i<elem.options.length;i++) {
		if(i==elem.selectedIndex) {
			$("." + elem[i].getAttribute('reference')).show();
		} else {
			$("." + elem[i].getAttribute('reference')).hide();
		}
	}
}
function getPage(elem) {
		var queryString = $("#person").find("input").serialize();
		var baseUrl = $(elem).attr("href");
		$.ajax({
			type: "POST",
			url: baseUrl,
			data: queryString+"&view=ajaxResults",
			success: function(html){
				$("#personSearchResults").html(html);
				return false;
			},
			error: function(html){
				alert("error");
				return false;
			}
		});
		return false;
}
function loadCodePopup(elem) {
	var lookupType = $(elem).eq(0).attr("lookup");
	window.lookupCaller = elem;
	$("#dialog .modalContent").load("codeHelper.htm?view=popup&type="+lookupType,function(){
		$("#dialog").jqmShow();
	});
}