	<%@ include file="/WEB-INF/jsp/include.jsp" %>
		<link rel="stylesheet" type="text/css" media="screen, projection" href="css/screen.css" />
		<link rel="stylesheet" type="text/css" media="print" href="css/print.css" />
		<!--[if lte IE 6]>
			<link href="css/ie6_conditional.css" rel="stylesheet" type="text/css" />
		<![endif]-->
		
		<link rel="shortcut icon" type="image/ico" href="images/favicon.ico" />

		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/jquery.tablesorter.js"></script>
		<script type="text/javascript" src="js/jquery.tablesorter.pager.js"></script>
		<script type="text/javascript">
		$(document).ready(function()
	    {
	        $("#myTable").tablesorter( { sortList: [[2,0], [3,0]] , headers:{0:{sorter:false}} } );

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
			
	    }
		);
	</script>