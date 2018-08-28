<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Contact Us</title>
<script src="js/jquery.min.js"></script>
	<script src="js/jquery.dataTables.min.js"></script>
	<script src="js/bootstrap3-typeahead.min.js"></script>  
	<script src="js/bootstrap.min.js"></script>
	<script src="js/bootstrap-multiselect.js"></script>
	<script src="js/handlebars-v4.0.11.js" ></script>
	<script src="js/sample.js"></script> 
			
	<link href="css/font-awesome.min.css" rel="stylesheet">
	<link rel="stylesheet" href="css/jquery.dataTables.min.css">
	<link rel="stylesheet" href="css/bootstrap-3.3.6.min.css" />
	<link rel="stylesheet" href="css/bootstrap-multiselect.css" />
	<link rel="stylesheet" href="css/w3.css">
	<link rel="stylesheet" type="text/css" href="css/iautResource.css"/>
	
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
						class="btn btn-md button1 but3">Welcome ${userName}</button>
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
					<span><a id="fon" href="dashboard.html" style="">Home</a></span> <span><a
						id="fon" href="help.html" style="">Help</a></span> <span><a
						id="fon" href="contactus.html" style="">Contact Us</a></span>
				</div>
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
					style="margin-right: 0px; color: black; color: white; height: 43px;">Profiles</a></li>
				<li><a id="black" href="reports.html"
					style="margin-right: 0px; color: black; color: white; height: 43px;">Reports</a></li>
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
						class="ddata" href="reports.html">Reports</a></li>
					<li data-toggle="collapse" data-target="#navbar-collapse.in"><a
						class="ddata" href="help.html">Help</a></li>
					<li data-toggle="collapse" data-target="#navbar-collapse.in"><a
						class="ddata" href="#">Contact Us</a></li>

				</ul>
			</div>
			<!-- .navbar-collapse --> </nav>
		</div>
	</div>
	<div class="maincontact">
		<h2 style="margin-bottom: 1%; margin-left: 3%; font-size: 30px;">Contact
			Us</h2>
		<div style="width: 60%; margin-left: 8%;">


			<div class="box1">
				<h4>
					IAUT Resource SPOC
					<h4>
						<hr>
						<div style="font-size: 15px;">
							<p>Name 	   : Siva Subramanian</p>
							<P>Designation : Lead-Operations </p>
							<p>Email id    : Siva.Subramanian@TechMahindra.com </p>
							<p>Contact no  : 08978453939, 04466194000 </p>
						</div>
			</div>


		</div>
	</div>

	<footer class="foot">
	<div class="w3-row"
		style="background-color: #b30000; text-align: center; padding: 10px; margin-top: 7px; color: white; height: 38px;">©
		Tech Mahindra Limited ©2018. All rights reserved.</div>
	</footer>

</body>

</html>