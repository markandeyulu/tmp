<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring"
	uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Charts</title>

<script src="js/jquery.min.js"></script>
<script src="js/jquery-ui.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/charts.js"></script>
<script	src="js/bootstrap-multiselect.js"></script>
<script src="js/jquery.canvasjs.min.js"></script>

<link rel="stylesheet" href="css/bootstrap-3.3.6.min.css" />
<link rel="stylesheet" href="css/w3.css">
<link rel="stylesheet" type="text/css" href="css/iautResource.css" />
<link href="css/jquery-ui.css" rel="stylesheet">
<link rel="stylesheet"	href="css/bootstrap-multiselect.css" />
<link rel="stylesheet"	href="css/perfect-scrollbar.css"/>
<%
	response.setHeader("Pragma", "no-cache");
	response.setHeader("Cache-Control", "no-store");
	response.setHeader("Expires", "0");
	response.setDateHeader("Expires", -1);
	response.setDateHeader("Expires", 0); //Causes the proxy cache to see the page as "stale"
%>
<script>
var accountValues = ${accountValuesJson};






</script>
<style>
.custab {
	border: 1px solid #ccc;
	padding: 5px;
	margin: 5% 0;
	box-shadow: 3px 3px 2px #ccc;
	transition: 0.5s;
}

body {
	background-image: url(img/02.png);
}

#black:hover {
	color: black !important;
}
.multiselect-container{
    height: 240px;
    overflow-y: scroll;
}
</style>
</head>
<body style="font-family: Verdana; overflow: hidden;">

	<script type="text/javascript">
		$(document).ready(function() {
			$('#logout').click(function() {
				$.ajax({
					url : "logout",
					method : "GET",
					success : function(e) {
						window.location.href = "login.html";
					}

				});

			});
			$('#pieChart').on('click', function(e){
				 $('#errorMsg').text("");
				 $('#locationErrorMsg').text("");
				if( $('#searchBy').val() == ""){
						$('#errorMsg').text("SearchbBy cannot be Empty")
						return false;
				 }
				if( $('#location').val() == ""){
					$('#locationErrorMsg').text("Location cannot be Empty")
					return false;
			 }
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

			<div class="rtab">
				<span><a id="fon" href="dashboard.html" style="">Home</a></span> <span><a
					id="fon" href="help.html" style="">Help</a></span> <span><a
					id="fon" href="contactus.html" style="">Contact Us</a></span>
			</div>

		</div>

	</div>

	<div class="navii">
		<div>
			<ul class="nav navbar-nav"
				style="font-size: 16px; background-color: #b30000; width: 100%; height: 45px;">
				<li><a id="black" href="dashboard.html"
					style="margin-right: 0px; color: white; height: 43px;">Dashboard</a></li>
				<li><a id="black" href="requirements.html"
					style="margin-right: 0px; color: white; height: 43px;">Requirements</a></li>
				<li><a id="black" href="profiles.html"
					style="margin-right: 0px; color: white; height: 43px;">Profiles</a></li>
					<li><a id="black" href="offerStatus.html"
					style="margin-right: 0px; color: white; height: 43px;">OfferStatus</a></li>
				<li id="black"><a href="reports.html"
					style="margin-right: 0px;  color: white; height: 45px;">Reports</a></li>
				<li><a class="active" href="#"
					style="margin-right: 0px; background-color: white; color: black;height: 43px;">Charts</a></li>
			</ul>
		</div>
	</div>

	<div class="mobnavii">
		<div class="row navbar"
			style="margin-bottom: 0px; border-top-width: 0px; background: none;">
			<nav class="navbar navbar-default" role="navigation">
			<div class="navbar-header" style="background-color: #b30000;">
				<button type="button" class="navbar-toggle x collapsed toggle but2"
					data-toggle="collapse"   data-target="#navbar-collapse-x">
					<span class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand heading2" href="#top" style="color: white;">Menu</a>
			</div>

			<div class="collapse navbar-collapse" id="navbar-collapse-x">
				<ul class="nav navbar-nav navbar-right dropdata" style="">
					<li data-toggle="collapse" data-target="#navbar-collapse.in"><a
						class="ddata" href="dashboard.html">Dashboard</a></li>
					<li data-toggle="collapse" data-target="#navbar-collapse.in"><a
						class="ddata" href="requirements.html">Requirements</a></li>
					<li data-toggle="collapse" data-target="#navbar-collapse.in"><a
						class="ddata" href="profiles.html">Profiles</a></li>
						<li data-toggle="collapse" data-target="#navbar-collapse.in"><a
						class="ddata" href="offerStatus.html">OfferStatus</a></li>
					<li data-toggle="collapse" data-target="#navbar-collapse.in"><a
						class="ddata" href="#">Reports</a></li>
					<li data-toggle="collapse" data-target="#navbar-collapse.in"><a
						class="ddata" href="help.html">Help</a></li>
					<li data-toggle="collapse" data-target="#navbar-collapse.in"><a
						class="ddata" href="contactus.html">Contact Us</a></li>

				</ul>
			</div>
			</nav>
		</div>
	</div>
	<div class="modal-body">
		<div class="container">
			<spring:form name='chartsForm' id = 'chartsForm'  method="GET"
				modelAttribute="charts">
				<div class="row">
						<spring:label cssClass="control-label col-lg-2" path="searchBy">SearchBy<span
								style="color: red">*</span>
						</spring:label>
						<div class="col-lg-5">
						<spring:select required="required" multiple="false"
							path="searchBy" id="searchBy" name="searchBy" class="form-control chart-dropdown"
							oninvalid="this.setCustomValidity('Please select searchBy')"
							oninput="this.setCustomValidity('')">
							<option value="">SearchBy skill/Account</option>
							<option value="skill">Skill</option>
							<option value="account">Account</option>
						</spring:select>
						</div>
						<p class="col-md-5" id="errorMsg"></p>
				</div>
				<div class="row">
						<spring:label cssClass="control-label col-lg-2" path="location">Location
						</spring:label>
						<div class="col-lg-5">
						<spring:select multiple="false"
							path="location" id="location" name="location" class="form-control chart-dropdown"
							oninvalid="this.setCustomValidity('Please select location')"
							oninput="this.setCustomValidity('')">
							<option value="">Select Location</option>
							<option value="Onsite">Onsite</option>
							<option value="OffShore">OffShore</option>
						</spring:select>
						</div>
						<p class="col-md-5" id="locationErrorMsg"></p>
						 
				</div>
				<%-- <div class="row">
				
						<spring:label cssClass="control-label col-lg-2" path="accountName">Account<span
								style="color: red">*</span>
						</spring:label>
						<div class="col-lg-10">
						<spring:select required="required" multiple="true"
							path="accountName" id="framework1" class="form-control chart-dropdown">
							
						</spring:select>
						</div>
				</div> --%>
				<div class="row">
						<spring:label cssClass="control-label col-lg-2" path="reportType">ReportType<span
								style="color: red">*</span>
						</spring:label>
						<div class="col-lg-10">
						<input type="radio" id="pie" name="chartType" value="pie" > PIE
						<input type="radio" id="bar" name="chartType" value="bar">BAR
						</div>
				</div>
				<div class="row">
					<div class="col-md-6 form-group subbut">
						<button id="pieChart"
							style="background-color: #b30000; color: white; margin-top: 5%;"
							 class="btn btn-md button1" data-target="#chartModal" data-toggle="modal" data-backdrop="static" data-keyboard="false"
							>Submit</button>
							
							<!-- <Button name="pieChart" id="pieChart" data-target="#pieChartModal" data-toggle="modal" 
							class="btn btn-md button1" style="background-color:#b30000;color:white;" type="button">
							Skill-set Pie Chart</Button> -->
					</div>
				</div>
				<div class="modal fade" id="chartModal" tabindex="-1" role="dialog"
					aria-labelledby="modalLabel" aria-hidden="true">
				<div class="modal-dialog" style="height: 600px; width: 800px ; font-size: 12px;">
					<div class="modal-content">
						<div class="modal-header" style="background-color: #b30000;">
							<button type="button" class="close" data-dismiss="modal" data-backdrop="static" data-keyboard="false"
								style="background-color: white;" onClick="window.location.reload();">
								<span aria-hidden="true">×</span><span class="sr-only">Close</span>
							</button>
							<h3 class="modal-title" id="lineModalLabel" style="color: white;">Chart</h3>
						</div>
						<div class="modal-body">
								<div id="resizable">
									<div id="chartContainer" style="height: 400px; width: 700px;"></div> 
								</div>
						</div>
					</div>
			</div>
			</div>
			</spring:form>
		</div>
	</div>
	
	<br />
	<footer class="foot">
	<div class="w3-row"
		style="bottom: 0px; background-color: #b30000; text-align: center; padding: 10px; margin-top: 7px; color: white; height: 38px;">©
		Tech Mahindra Limited ©2018. All rights reserved.</div>
	</footer>
</body>
</html>
