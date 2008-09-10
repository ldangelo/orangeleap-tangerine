$(document).ready(function()
   {
	$("#myTable").tablesorter( { sortList: [[2,0], [3,0]] , headers:{0:{sorter:false}} } );
	//.tablesorterPager({container: $("#pager"),positionFixed: false})
	$("#giftListTable").tablesorter( { sortList: [[1,0]] , headers:{0:{sorter:false}} } );
	$("table.defaultSort").tablesorter();
	// Disabled because hover event is sticky on fast mouse movement
	//$("table.tablesorter tbody tr").hover(function() {
	//	$(this).addClass("highlight");
	//	}, function() {
	//		$(this).removeClass("highlight");
	//});

	$("table.tablesorter tbody td input").focus(function() {
		$(this).parent().parent().addClass("focused");
	}).blur(function() {
		$(this).parent().parent().removeClass("focused");
	});

	$("ul.formFields li input, ul.formFields li select").change(function() {
       	$("#savedMarker").fadeOut("slow");
	});

	//$(".newAccountButton").click(function() {
	//	var destination = $(this).attr("href");
	//	destination += "&" + $("#person").serialize();
	//	$(this).attr("href",destination);
	//});

	// $("#creditCardMonth").autocompleteArray(
	//	["01", "02", "03", "04", "05", "06", "07", "08",
	//		"09", "10", "11", "12"],
	//	{
	//		delay:10,
	//		minChars:1,
	//		autoFill:true,
	//		maxItemsToShow:20
	//	}
	// );

	// $("#creditCardYear").autocompleteArray(
	//	["2008", "2009", "2010", "2011", "2012", "2013", "2014", "2015",
	//		"2016", "2017", "2018"],
	//	{
	//		delay:10,
	//		minChars:1,
	//		autoFill:true,
	//		maxItemsToShow:20
	//	}
	// );

	$(".commitmentCode").autocomplete("codeHelper.htm?type=commitmentCode",
	{
		delay:10,
		minChars:0,
		maxItemsToShow:20,
		formatItem:formatItem,
		loadingClass:""
	}
	);

	$(".projectCode").autocomplete("codeHelper.htm?type=projectCode",
	{
		delay:10,
		minChars:0,
		maxItemsToShow:20,
		formatItem:formatItem,
		loadingClass:""
	}
	);

	$(".motivationCode").autocomplete("codeHelper.htm?type=motivationCode",
	{
		delay:10,
		minChars:0,
		maxItemsToShow:20,
		formatItem:formatItem,
		loadingClass:""
	}
	);

	$("#paymentType").change(function(){
		console.log("payment type changed");
		$("." + this.name + " .column").hide();
		$(".gift_info").show();
		$("." + this[this.selectedIndex].getAttribute('reference')).show();
	});

	$("#personTitle").cluetip({
		cluetipClass:'default',
		showTitle: false,
		dropShadow: false,
		waitImage: false,
		fx: {
        	open:'fadeIn'
		}
	});

//	 $(".personRow").cluetip({
//		cluetipClass:'default',
//		showTitle: false,
//		dropShadow: false,
//		waitImage: false
//	 });


	$("table#gift_distribution input.amount").bind("keyup change", updateTotals);

	$("form#gift input#value").bind("keyup change",function(){
		var amounts=$("table#gift_distribution input.amount");
		if(amounts.length == 1) {
			amounts.val($("input#value").val());
		}
		updateTotals();
	});

	rowCloner("#gift_distribution tr:last");
	$("#gift_distribution tr:last .deleteButton").hide();

	$("#gift_distribution td .deleteButton").click(function(){
		deleteRow($(this).parent().parent());
	});

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


	$(".codeLookup").click(function(){
		$("#dialog .modalContent").load($(this).attr("href"));
		$("#dialog").jqmShow();
		return false;
	});

	$(".filters :input").bind("keyup change",function(){
		var queryString = $(".filters :input").serialize();
		$(".codeList").load("codeHelper.htm?view=table&"+queryString);
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
	$(selector).one("keyup",function(event){
		if(event.keyCode != 9) { // ignore tab
			addNewRow(distributionLineBuilder);
		}
		rowCloner(selector);
	});
}
function callServer(name) {
    Hello.greet(name, callback);
}
function callback(data) {
    alert("AJAX Response:" + data);
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
	newRow.find("input.commitmentCode").autocomplete("codeHelper.htm?type=commitmentCode",
	{
		delay:10,
		minChars:0,
		maxItemsToShow:20,
		formatItem:formatItem,
		loadingClass:""
	});
	newRow.find("input.amount").bind("keyup change", updateTotals);
	newRow.find("input.projectCode").autocomplete("codeHelper.htm?type=projectCode",
	{
		delay:10,
		minChars:0,
		maxItemsToShow:20,
		formatItem:formatItem,
		loadingClass:""
	});
	newRow.find("input.motivationCode").autocomplete("codeHelper.htm?type=motivationCode",
	{
		delay:10,
		minChars:0,
		maxItemsToShow:20,
		formatItem:formatItem,
		loadingClass:""
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
function testDebugger() {
	var test="hello";
	console.log(test);
	console.log(test+" world");
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