<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>IAUT Dashboard</title>

	<script src="js/jquery.min.js"></script>
	<script src="js/jquery.dataTables.min.js"></script>
	<script src="js/bootstrap3-typeahead.min.js"></script>  
	<script src="js/bootstrap.min.js"></script>
	<script src="js/bootstrap-multiselect.js"></script>
	<script src="js/handlebars-v4.0.11.js" ></script>
	<script src="js/handlebar.js" ></script>
	<script src="js/sample.js"></script> 
	<script src="js/location.js" ></script>
	<script src="js/dropdown.js" ></script>
		
	<link href="css/font-awesome.min.css" rel="stylesheet">
	<link rel="stylesheet" href="css/jquery.dataTables.min.css">
	<link rel="stylesheet" href="css/bootstrap-3.3.6.min.css" />
	<link rel="stylesheet" href="css/bootstrap-multiselect.css" />
	<link rel="stylesheet" href="css/w3.css">
	<link rel="stylesheet" type="text/css" href="css/iautResource.css"/>
	<%
	response.setHeader("Pragma","no-cache"); 
	response.setHeader("Cache-Control","no-store"); 
	response.setHeader("Expires","0"); 
	response.setDateHeader("Expires",-1);
	response.setDateHeader("Expires", 0); //Causes the proxy cache to see the page as "stale"
	
	%>
<style>

body {  background-image: url(img/02.png); } 
#black:hover {
	color: black !important;
}
</style>
</head>
<body style="font-family: Verdana;">

<script type="text/x-handlebars-template" id="CaseView">

{{#each dashboardRequirementsJson}}

  <tr>
		
		
		<td ><a id="reqtab">{{customerName}}</a></td>
		

		<td>{{leadGeneration}}</td>
		<td>{{profileSourcing}}</td>
		<td>{{internalEvaluation}}</td>
		
		
		<td>{{customerEvaluation}}</td>
		<td>{{offerProcessing}}</td>
		<td>{{onboarded}}</td>
		<td>{{positionsLost}}</td>
		<td>{{totalPositions}}</td>
		<td>{{totalActive}}</td>
		
</tr>
{{/each}}


</script>
<script type="text/javascript">

$(document).ready(function(){
$('#logout').click(function () {
	$.ajax({
        url : "logout",
        method : "GET",
        success : function(e) { 
            window.location.href = "login.html";
        }
	
    });

});

$("#reqtable tr").each(function() {
	$(this).find('td').find("a").each (function( column, tag) {
		var accountName = $(tag).text();
		var newURL = "requirement.html?name="+encodeURIComponent(accountName);
		$(tag).attr("href", newURL);
	}); 
});

});
</script>
		<div class="header" style="padding-bottom: 0px;">
			<div class="logo">
				<img src="img\tmlogo.png" class="techm" align="left">
			</div>
			<span class="heading1"><p>
					<b>IAUT Resource Management</b>
				</p> </span>
			<div class="header-right">
				<div class="btn-group btn-group-primary">
					<button
						style="display: inline-block; background-color: #b30000; color: white;"
						class="btn btn-md button1 but3">Welcome ${displayName}</button>
					<button id="logout"
						style="display: inline-block; background-color: #b30000; color: white"
						class="btn btn-md button1 but3" type="submit">Logout</button>
					<ul class="dropdown-menu">
						<li>Logout</a></li>
					</ul>
				</div>

			</div>
			<div class="righttab">
				<div>
					<div class="rtab">
						<span><a id="fon" href="dashboard.html" style="">Home</a></span>
						<span><a id="fon" href="help.html" style="">Help</a></span> <span><a
							id="fon" href="contactus.html" style="">Contact Us</a></span>
					</div>
				</div>
			</div>

		</div>

	<div class="navii">
		<div>
			<ul class="nav navbar-nav navbar-default" id=""
				style="font-size: 16px; background-color: #b30000; width: 100%; height: 45px;">
				<li class="active"><a href="#"
					style="margin-right: 0px; background-color: white; color: black; padding-bottom: 10px;">Dashboard</a></li>
				<li><a id="black" href="requirements.html"
					style="margin-right: 0px; color: white; height: 43px;">Requirements</a></li>
				<li><a id="black" href="profiles.html"
					style="margin-right: 0px; color: white; height: 43px;">Profiles</a></li>
				<li><a id="black" href="offerStatus.html"
					style="margin-right: 0px; color: white; height: 43px;">OfferStatus</a></li>	
				<li><a id="black" href="reports.html"
					style="margin-right: 0px; color: white; height: 43px;">Reports</a></li>
				<li><a id="black" href="charts.html"
					style="margin-right: 0px; color: white; height: 43px;">Charts</a></li>
			</ul>
		</div>
	</div>
	<div class="mobnavii">
		<div class="row navbar"
			style="margin-bottom: 0px; border-top-width: 0px; background: none;">
			<nav class="navbar navbar-default" role="navigation">
			<div class="navbar-header" style="background-color: #b30000;">
				<button type="button" class="navbar-toggle x collapsed toggle but2"
					style="margin-right: 6%;" data-toggle="collapse"
					data-target="#navbar-collapse-x">
					<span class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand heading2" href="#top" style="color: white;">Menu</a>
			</div>

			<div class="collapse navbar-collapse" id="navbar-collapse-x">
				<ul class="nav navbar-nav navbar-right dropdata" style="">
					<li data-toggle="collapse" data-target="#navbar-collapse.in"><a
						class="ddata" href="#top">Dashboard</a></li>
					<li data-toggle="collapse" data-target="#navbar-collapse.in"><a
						class="ddata" href="requirement.html">Requirements</a></li>
					<li data-toggle="collapse" data-target="#navbar-collapse.in"><a
						class="ddata" href="Profiles.html">Profiles</a></li>
					<li data-toggle="collapse" data-target="#navbar-collapse.in"><a
						class="ddata" href="offerStatus.html">Offer Status</a></li>	
					<li data-toggle="collapse" data-target="#navbar-collapse.in"><a
						class="ddata" href="reports.html">Reports</a></li>
					<li data-toggle="collapse" data-target="#navbar-collapse.in"><a
						class="ddata" href="help.html">Help</a></li>
					<li data-toggle="collapse" data-target="#navbar-collapse.in"><a
						class="ddata" href="contactus.html">Contact Us</a></li>

				</ul>
			</div>
			<!-- .navbar-collapse --> </nav>
		</div>
	</div> 
	<div id="tab1" >
	<div class="row w3-row">
		<div class="col-md-12">
			<div class="col-md-9" style="padding-right: 0px;">
				<h2 style="margin-bottom: 1%; margin-left: 3%; font-size: 30px;">Dashboard</h2>

				<spring:form name='dashboardForm' action="dashboard" method='POST'
					modelAttribute="dashboardRequirement">
					<div class="col-md-4 drop1" style="margin-top: 3.2%;">

						<div class="form-group">
							<spring:select id="framework" name="location" multiple="true" path="location"
								class="form-control" />

						</div>


					</div>
					<div class="col-md-4 drop1">
						<div class="form-group">
							<spring:select id="framework1" name="account" multiple="true" path="account"
								class="form-control" />

						</div>


					</div>

					<div class="col-md-2 form-group subbut">
						<button id="clickfunction"
						style="background-color: #b30000; color: white; margin-top: 5%;"
							type="submit" class="btn btn-md button1" name="submit"
							value="Submit">Submit</button>
					</div>
				</spring:form>
				<br />

				<!-- Icon Cards-->

				<div class="row col-md-12" style="margin-bottom: 3%;">
					<div>
						<div class=" row w3-container">
						<div id="dash1" style="margin-bottom: 3%; display:inline-block; width:100%">

							<div id="rcorners3" class="w3-panel w3-card" style="display:inline-block;">
								<h6>Lead Generation</h6>
								<p></p>
								<!-- <div id="hoverview" class="w3-third w3-center w3-card" style="background:#9b0f23;"> 
      <button data-toggle="modal" data-target="#Modelleadgeneration" class="w3-button w3-black w3-round-xxlarge" style="margin-top:10px;">View</button> 
    </div> -->
								<div style="height: 50%;">
									<h1 id="dvalue1" style="float: right; margin-top: 8%;"></h1>
								</div>

							</div>
							<div id="rcorners3" class="w3-panel w3-card" style="margin-left: 2%; display:inline-block;">
								<h6>Profile Sourcing</h6>

								<div>
									<h1 id="dvalue2" style="float: right; margin-top: 7%;"></h1>
								</div>
							</div>
							<div id="rcorners3" class="w3-panel w3-card" style="margin-left: 2%; display:inline-block;">
								<h6>Technical Evaluations</h6>
								<p></p>

								<span>
									<h1 id="dvalue3" style="float: right; margin-top: -5%;"></h1>
								</span>
							</div>
							<div id="rcorners3" class="w3-panel w3-card" style="margin-left: 2%; display:inline-block;">
								<h6>Customer Evaluations</h6>
								<p></p>

								<span>
									<h1 id="dvalue4" style="float: right; margin-top: -4%;"></h1>
								</span>
							</div>
							<div id="rcorners3" class="w3-panel w3-card" style="margin-left: 2%; display:inline-block;">
								<h6>Offer Processing</h6>
								<p></p>

								<span>
									<h1 id="dvalue5" style="float: right; margin-top: 8%;"></h1>
								</span>
							</div>
							</div>
							<div id="dash2" style="margin-bottom: 3%;">
							<div id="rcorners3" class="w3-panel w3-card" style="display:inline-block;">
								<h6>Onboarded</h6>
								<p></p>

								<span>
									<h1 id="dvalue6" style="float: right; margin-top: 8%;"></h1>
								</span>
							</div>
							<div id="rcorners3" class="w3-panel w3-card" style="margin-left: 2%; display:inline-block;">
								<h6>Hold Opportunity</h6>
								<p></p>

								<span>
									<h1 id="dvalue7" style="float: right; margin-top: 8%;"></h1>
								</span>
							</div>
							<div id="rcorners3" class="w3-panel w3-card" style="margin-left: 2%; display:inline-block;">
								<h6>Abandoned Opportunity</h6>
								<p></p>

								<span>
									<h1 id="dvalue8" style="float: right; margin-top: -4%;"></h1>
								</span>
							</div>
							<div id="rcorners3" class="w3-panel w3-card" style="margin-left: 2%; display:inline-block;">
								<h6>Lost Opportunity</h6>
								<p></p>

								<span>
									<h1 id="dvalue10" style="float: right; margin-top: 8%;"></h1>
								</span>
							</div>
							<!-- <div class="col-md-2"> -->
						<!-- <div id="rcorners2" class="w3-panel w3-card" style="display: inline-block;"> -->
						<div id="rcorners2" class="w3-panel w3-card" style="margin-bottom:1%; margin-left: 2%; display:inline-block; width:17%">
							<h3 class="tot" style="font-weight:bold; margin-left: 3%; margin-top: -19%; ">Total</h3>
							<p></p>

							<span>
								<h2 class="totval" id="dvalue9" style="font-weight:bold"></h2>
							</span>
						</div>
					<!-- </div> -->
							</div>
						</div>
					</div>

					<!-- <div class="col-md-2">
						<div id="rcorners2" class="w3-panel w3-card" style="display: inline-block;">
							<h3 class="tot" style="">Total</h3>
							<p></p>

							<span>
								<h1 class="totval" id="dvalue9" style=""></h1>
							</span>
						</div>
					</div> -->
				</div>
				<div class="modal fade" id="Modelleadgeneration" tabindex="-1"
					role="dialog" aria-labelledby="modalLabel" aria-hidden="true">
					<div class="modal-dialog" style="width: 70%;">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal">
									<span aria-hidden="true">×</span><span class="sr-only">Close</span>
								</button>
								<h3 class="modal-title" id="lineModalLabel">Profile</h3>
							</div>
							<div class="modal-body">
								<!-- content goes here -->
								<div class="container" style="">
									<table class="table  table-sm">
										<thead class="table-info">
											<tr>
												<th>S.no</th>
												<th>Location</th>
												<th>Customer name</th>
												<th>Stage Owner</th>
												<th>Stage & Description</th>
											</tr>
										</thead>
										<tbody>
											<tr>
												<td>1</td>
												<td>Onsite</td>
												<td>Ford</td>
												<td>Sales / CDM</td>
												<td>CRM ID in P3 & above</td>
											</tr>
											<tr>
												<td>2</td>
												<td>Offshore</td>
												<td>Mazda</td>
												<td>RMG SPOC</td>
												<td>SO creation</td>
											</tr>
											<tr>
												<td>3</td>
												<td></td>
												<td>Ford Direct</td>
												<td>TMG Team</td>
												<td>JO creation</td>
											</tr>
											<tr>
												<td>4</td>
												<td></td>
												<td>TRW</td>
												<td>Sales / CDM</td>
												<td>Position on Hold / Closed</td>
											</tr>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
				</div>

				<div class="main" style="margin-top: 3%; padding-right: 0px;">

					<div class="table" style="overflow: auto; margin-top: 2%;">

						<table id="reqtable"
							class="table table-bordred table-striped custab">
							<thead style="vertical-align: bottom;">
								<tr>

									<!-- <th>Ref No#</th> -->
									<th>Customer Name</th>
									<th>Lead Generation</th>
									<th>Profile Sourcing</th>
									<th>Internal Evaluation</th>
									<th>Customer Evaluation</th>
									<th>Offer Processing</th>
									<th>Onboarded</th>
									<th>Positions Lost</th>
									<th>Total Positions</th>
									<th>Total Active</th>
								</tr>
							</thead>
							<tbody id="listView_content" align="center">

							</tbody>
						<!--  	<tfoot style="vertical-align: top;">
								<tr>
									<th>Customer Name</th>
									<th>Lead Generation</th>
									<th>Profile Sourcing</th>
									<th>Internal Evaluation</th>
									<th>Customer Evaluation</th>
									<th>Offer Processing</th>
									<th>Onboarded</th>
									<th>Positions Lost</th>
									<th>Total Positions</th>
									<th>Total Active</th>
								</tr>
							</tfoot> -->
						</table>

					</div>
				</div>
				<br> <br>
			</div>
			<div class="col-md-3" style="padding-left: 0px;">

				<div class="col-md-12">
					<div class="box">
						<h4>
							Recent Activities
							<h4>
								<hr>
								<div>
									<p>This is a paragraph. We can use this section for any
										recent activities.</p>
								</div>
					</div>
					<div class="box">
						<h4>
							Recent Activities
							<h4>
								<hr>
								<div>
									<p>This is a paragraph. We can use this section for any
										recent activities.</p>
								</div>
					</div>
				</div>

			</div>
		</div>
	</div>
	</div>

	 <footer class="">
	<div class="w3-row"
		style="background-color: #b30000; text-align: center; padding: 10px; margin-top: 7px; color: white; height: 38px;">©
		Tech Mahindra Limited ©2018. All rights reserved.</div>
	</footer>

<script>
var dashboardRequirements = ${dashboardRequirementsJson};
var accountValues = ${accountValuesJson};
var locationJson=${locationJson};
var projectValues = ${projectValuesJson};
var primarySkillJson=${primarySkillJson};
var criticalJson=${criticalJson};
var intimationModeJson=${intimationModeJson};
var requirementTypeJson=${requirementTypeJson};
var positionStatusJson=${positionStatusJson};
var opportunityStatusJson=${opportunityStatusJson};
var skillCategoryJson=${skillCategoryJson};
var primarySkillJson=${primarySkillJson};
</script>
<script>



	var myObj = ${dashboardblockJson};      
	
	
	$("#dvalue1").text(myObj.leadcount);
	$("#dvalue2").text(myObj.profilecount);
	$("#dvalue3").text(myObj.techevalcount);
	$("#dvalue4").text(myObj.custevalcount);
	$("#dvalue5").text(myObj.offercount);
	$("#dvalue6").text(myObj.boardingcount);
	$("#dvalue7").text(myObj.holdOpportunityCount);
	$("#dvalue8").text(myObj.abandonedOpportunityCount);
	$("#dvalue10").text(myObj.lostOpportunityCount);
	$("#dvalue9").text(myObj.totalcount);
	
	
</script>

<script src="js/dashjs.js" ></script>

<link rel="stylesheet" type="text/css" href="css/iautResource.css"/>
</body>

</html>
