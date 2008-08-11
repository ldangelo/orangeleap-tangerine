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
	
	$("table.tablesorter tbody td").hover(function() {
		$(this).parent().addClass("highlight");
		}, function() {
			$(this).parent().removeClass("highlight");
	});
	
	$("table.tablesorter tbody td input").focus(function() {
		$(this).parent().parent().addClass("focused");console.log("focused");
	});
	
	$("table.tablesorter tbody td input").blur(function() {
		$(this).parent().parent().removeClass("focused");console.log("blurred");
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

   }
);

function callServer() {
    Server.echo("DWR", callback);
}
function callback(data) {
    alert("Hi from " + data);
}
function addDistributionLine() {
	var newRow = $(".tablesorter tr:last").clone(true);
	var newRowHtml = newRow.html();
	var i = newRow.attr("rowindex");
	var j = parseInt(i) + 1;
	newRow.attr("rowindex",j);
	var findString = new RegExp("\\["+i+"\\]","gi");
	newRowHtml = newRowHtml.replace(findString, "["+j+"]");
	newRow.html(newRowHtml);
	//newRow.css("display","none");
	$(".tablesorter").append(newRow);
	//newRow.fadeIn("slow");
}