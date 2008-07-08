<%@ include file="/WEB-INF/jsp/include.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">


<mp:page pageName='person'/>

<form:form method="post" commandName="person">
  <c:forEach var="item" items="${sectionDefinitions}">
    <div style="position: relative;">
      <div class="content760 mainForm">
        <div class="bodyContent">
          <h4 class="formSectionHeader">
            <mp:sectionHeader messageType="SECTION_HEADER" sectionDefinition="${item}"/>
          </h4>
          <div class="columns">

            <div class="column">
              <ul class="formFields width375">
                <c:forEach var="sectionField" items="${item.sectionDefinition.sectionFields}" begin="0" end="9">
                  <mp:field sectionField='${sectionField}' pageDefinition='${pageDefinition}'/>
                  <%@ include file="/WEB-INF/jsp/snippets/input.jsp" %>
                </c:forEach>
		        <li class="clear"></li>
              </ul>
            </div>

            <div class="column">
              <ul class="formFields width375">
                <c:forEach var="sectionField" items="${item.sectionDefinition.sectionFields}" begin="10">
                  <mp:field sectionField='${sectionField}' pageDefinition='${pageDefinition}'/>
                  <%@ include file="/WEB-INF/jsp/snippets/input.jsp" %>
                </c:forEach>
                <li class="clear"/>
              </ul>
            </div>

            <div class="clearColumns"></div>
          </div>
        </div>
      </div>
    </div>
  </c:forEach>
</form:form>

<div style="clear: both;"/>


<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">
	<head>
		<title>MPower Open Prototype</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

		<link rel="stylesheet" type="text/css" media="screen, projection" href="css/screen.css" />
		<link rel="stylesheet" type="text/css" media="print" href="css/print.css" />

		<link rel="shortcut icon" type="image/ico" href="images/favicon.ico" />

		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/jquery.tablesorter.js"></script>
		<script type="text/javascript">
		$(document).ready(function()
	    {
	        $("#myTable").tablesorter( {sortList: [[0,0], [1,0]]} );

			$(".accountOptions a").click(function() {
				this.blur();
	        	$(".accountOptions a").removeClass("active");
				$(this).addClass("active");
				$("#currentFunctionTitle").html($(this).html());
				return false;
			});
	    }
		);
	</script>

	</head>

	<body>
		<div class="bodyContent">
			<div id="banner">
				<ol>
					<li>
						<span id="greeting">Logged in as RSMITH</span>
					</li>
					<li>
						<a href="#">Your Account</a>
					</li>
					<li>
						<a href="#">Help</a>
					</li>
					<li>
						<a href="#">Logout</a>
					</li>
				</ol>
			</div>

			<div id="navmain">
				<div class="navLeftCap"></div>
				<div class="container">
					<ul>
						<li>
							<strong><a href="#">People</a></strong>
						</li>
						<li>
							<a href="test.htm">Transactions</a>
						</li>
						<li>
							<a href="#">Administrator</a>
						</li>
						<li>
							<a href="#">Analysis</a>
						</li>
					</ul>
					<div class="clearBoth"></div>
					<ul class="secondaryNav">
						<li>
							<strong><a href="#">View / Change</a></strong>
						</li>
						<li>
							<a href="#">Add Account</a>
						</li>
						<li>
							<a href="#">Codes</a>
						</li>
						<li>
							<a href="#">Correspond</a>
						</li>
						<li>
							<a href="#">Distribution Lists</a>
						</li>
						<li>
							<a href="#">Query Builder</a>
						</li>
					</ul>
					<div class="clearBoth"></div>
				</div>
				<div class="navRightCap"></div>

			</div>
			<div class="clearBoth"></div>
			<div style="position:relative;">
				<div class="sideBar">
					<div class="wrapper">
						<div class="innerContent" style="height:600px;">
							<h3>
								Quick Search
							</h3>
							<input value="" size="18" id="sidebarsearch" />
							<input type="submit" value="Go" />
							<br />
							<a style="font-size:10px;" href="personSearch.htm">Advanced Search</a>
							<br />
							<br />
							<h3>
								Account Options
							</h3>
							<div class="accountOptions">
								<a class="active" href="#">Profile</a>
								<a href="#">Enter Gift</a>
								<a href="#">Billing</a>
								<a href="#">Account Statement</a>
								<a href="#">Codes</a>
								<a href="#">Notes</a>
								<a href="#">Dates</a>
								<a href="#">Pledges</a>
								<a href="#">Relationships</a>
								<a href="#">Subscriptions</a>
							</div>
						</div>
					</div>
				</div>
				<div class="content760 mainForm">
					<jsp:include page="helloForm.jsp"/>
				</div>

			</div>
			<div class="clearBoth"></div>
			<div id="footer">
				<div class="container">
					<p class="legal">
						(800) 562-5150 | &#169; Copyright 2008 MPower Open. All Rights Reserved. |
						<a href="#">Articles</a> |
						<a href="#">Resources</a>
					</p>
				</div>
			</div>

		</div>
	</body>
</html>
