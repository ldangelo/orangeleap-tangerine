$(document).ready(function()
   {
	$("#myTable").tablesorter( { sortList: [[2,0], [3,0]] , headers:{0:{sorter:false}} } );
	//.tablesorterPager({container: $("#pager"),positionFixed: false})
	$("#giftListTable").tablesorter( { sortList: [[1,0]] , headers:{0:{sorter:false}} } );

      	//$(".accountOptions a").click(function() {
	//	this.blur();
    //	$(".accountOptions a").removeClass("active");
	//	$(this).addClass("active");
	//	$("#currentFunctionTitle").html($(this).html());
	//	return false;
	//});
	
	// Disabled because hover event is sticky on fast mouse movement
	//$("table.tablesorter tbody tr").hover(function() {
	//	$(this).addClass("highlight");
	//	}, function() {
	//		$(this).removeClass("highlight");
	//});
	
	$("table.tablesorter tbody td input").focus(function() {
		$(this).parent().parent().addClass("focused");
	});
	
	$("table.tablesorter tbody td input").blur(function() {
		$(this).parent().parent().removeClass("focused");
	});
	
	//$(".secondaryNav li a").click(function() {
	//	this.blur();
       //	$(".secondaryNav li a").removeClass("active");
	//	$(this).addClass("active");
	//	return false;
	//});
	
	//$(".primaryNav li a").click(function() {
	//	this.blur();
       //	$(".primaryNav li a").removeClass("active");
	//	$(this).addClass("active");
	//	return false;
	//});
	
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
	
	$("#paymentType").change(function(){
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

//	$("table#gift_distribution input.amount").keydown(function(){
//		console.log("yo");
//	});
	
	$("table#gift_distribution input.amount").bind("keyup change", updateTotals);
	
	$("form#gift input#value").bind("keyup change",function(){
		var amounts=$("table#gift_distribution input.amount");
		if(amounts.length == 1) {
			amounts.val($("input#value").val());
		}
		updateTotals();
	});
	
	rowCloner("#gift_distribution tr:last");
	
	$("#gift_distribution td .deleteButton").click(function(){
		deleteRow($(this).parent().parent());
	});

   }
);
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
			addNewRow();
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
function addNewRow() {
	$(".tablesorter tr:last .deleteButton").show();
	var newRow = $(".tablesorter tr:last").clone(true);
	newRow.find(".deleteButton").hide();
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
	newRow.removeClass("focused highlight");
	$(".tablesorter").append(newRow);
}
function deleteRow(row) {
	if($(".tablesorter tbody tr").length > 1) {
		row.fadeOut("slow",function(){$(this).remove();updateTotals();})
	} else {
		alert("Sorry, you cannot delete that row since it's the only remaining row.")
	};
}