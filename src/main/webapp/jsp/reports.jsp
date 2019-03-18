<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Reports</title>

	<script src="js/jquery.min.js"></script>
	<script src = "js/jquery-ui.min.js"></script>	
	<script src="js/bootstrap.min.js"></script>
	
	<link rel="stylesheet" href="css/bootstrap-3.3.6.min.css" />
	<link rel="stylesheet" href="css/w3.css">
	<link rel="stylesheet" type="text/css" href="css/iautResource.css"/>
    <link href = "css/jquery-ui.css" rel = "stylesheet">
    <%
	response.setHeader("Pragma","no-cache"); 
	response.setHeader("Cache-Control","no-store"); 
	response.setHeader("Expires","0"); 
	response.setDateHeader("Expires",-1);
	response.setDateHeader("Expires", 0); //Causes the proxy cache to see the page as "stale"
	%>
<script>

$(document).ready(function() {
    $("#start_datepicker").datepicker();
  });
$(document).ready(function() {
    $("#end_datepicker").datepicker();
  });

</script>
<style>
.custab {
	border: 1px solid #ccc;
	padding: 5px;
	margin: 5% 0;
	box-shadow: 3px 3px 2px #ccc;
	transition: 0.5s;
}
body {  background-image: url(img/02.png); } 
#black:hover {
	color: black !important;
}
</style>
</head>
<body style="font-family: Verdana; overflow: hidden;">

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
				<li class="active"><a href="#"
					style="margin-right: 0px; background-color: white; color: black; height: 45px;">Reports</a></li>
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
					data-toggle="collapse" data-target="#navbar-collapse-x">
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
<div class="mainsec3">
		<h2 style="margin-bottom: 1%; margin-left: 3%; font-size: 30px;">Reports</h2>
		<div style="width: 60%; margin-left: 8%;">
		<div class="card-body">
	  <spring:form  name='reportForm' action="report" method='POST' modelAttribute="reportModel">
		<fieldset>
		<div class="form-group">
			<p class="daterep" ><spring:label path="startdate">From Date:<span style="color:red">*</span> </spring:label>
			<spring:input class="form-control" path="startdate" required="required" type="text" id="start_datepicker" /></p>
			<p class="daterep" ><spring:label path="enddate">To Date:<span style="color:red">*</span> </spring:label>
			<spring:input class="form-control" path="enddate" required="required" type="text" id="end_datepicker"/></p>
			</div>
			<tr><td><Button name="submit" class="btn btn-md button1" style="background-color: #cc0000; color: white; float:right;margin-right:7%;" type="submit">Export to Excel</Button></td></tr>
		</fieldset>	
		</spring:form> 	 	</div>
</div>
</div>

<footer class="foot">
	<div class="w3-row"
		style="bottom: 0px; background-color: #b30000; text-align: center; padding: 10px; margin-top: 7px; color: white; height: 38px;">©
		Tech Mahindra Limited ©2018. All rights reserved.
		</div>
	</footer>
</body>
</html>
