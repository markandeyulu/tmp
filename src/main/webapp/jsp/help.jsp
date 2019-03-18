<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring"
	uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Help</title>
<script src="js/jquery.min.js"></script>
<script src="js/jquery.dataTables.min.js"></script>
<script src="js/bootstrap3-typeahead.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/bootstrap-multiselect.js"></script>
<script src="js/handlebars-v4.0.11.js"></script>
<script src="js/sample.js"></script>

<link href="css/font-awesome.min.css" rel="stylesheet">
<link rel="stylesheet" href="css/jquery.dataTables.min.css">
<link rel="stylesheet" href="css/bootstrap-3.3.6.min.css" />
<link rel="stylesheet" href="css/bootstrap-multiselect.css" />
<link rel="stylesheet" href="css/w3.css">
<link rel="stylesheet" type="text/css" href="css/iautResource.css" />

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
		});
	</script>
	<div class="header" style="padding-bottom: 0px;">
		<div class="logo">
			<img src="img\tmlogo.png" class="techm" align="left">
		</div>
		<span class="heading1"> <b>IAUT Resource Management</b>
		</span>
		<div class="header-right">
			<div class="btn-group btn-group-primary">
				<button
					style="display: inline-block; background-color: #b30000; color: white;"
					class="btn btn-md button1 but3">Welcome ${displayName}</button>
				<button id="logout"
					style="display: inline-block; background-color: #b30000; color: white"
					class="btn btn-md button1 but3" type="submit">Logout</button>
				<ul class="dropdown-menu">
					<li>Logout</li>
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
					style="margin-right: 0px; color: black; color: white; height: 43px;">profiles</a></li>
				<li><a id="black" href="offerStatus.html"
					style="margin-right: 0px; color: black; color: white; height: 43px;">OfferStatus</a></li>
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
						class="ddata" href="#">Help</a></li>
					<li data-toggle="collapse" data-target="#navbar-collapse.in"><a
						class="ddata" href="contactus.html">Contact Us</a></li>

				</ul>
			</div>
			<!-- .navbar-collapse --> </nav>
		</div>
	</div>
	<div class="maincontact">
		<h2 style="margin-bottom: 1%; margin-left: 3%; font-size: 30px;">Help</h2>
		<div style="width: 80%; margin-left: 8%;">

			<div class="box1">
				<h4>
					Frequently Asked Questions <span class="fa fa-question-circle" aria-hidden="true"></span>
					<h4>
						<hr>
						<div style="font-size: 15px;">
							<li>1. IAUT resource management portal, is a management
								portal which will serve as a complete results in dashboard
								screen. It will display all the requirements and profiles in
								their respective screens. Admin user only will access to
								manupulate the records. Other users will have only view access
								and all the users can download excel report. </li> 
								<br />
								<br />
								<li>2. Users needs to give Username and Password for accessing this
								application.</li>
								<br />
								<br />
								<li>3. Please enable browser Pop-ups,if
								the page refresh when you click on any of the link on login page
								of this application.</li>
								<br />
								<br />
								<li>4. Default session timeout is
								20 min in case there is no activity from the users. </li>
								<br />
								<br />
								<li>5. As a security measure users need to lock their desktops/laptops
								when not in use.</li>
								<br />
								<br />
								<li>7. Remember to logout of this
								application at the end of your transaction. Closing the window
								without logout will prevent your next entry till the session
								ends (up to 20 minutes).</li>
								<br />
								<br />
								<li>8. FireFox users - 8.1)Add
								links which is asking Trusted username and password in firefox
								configuration. Following are steps given for adding URL in
								firefox configuration. a. Open Firefox window-> Type
								"about:config" in Address Bar and press enter button. b. Press
								"I'll be careful,I promise!" button which appear with warranty
								message. c. Find "network.automatic-ntlm-auth.trusted-uris"
								preference Name(string) in given list and modify with right
								click menu. d. Add URL(s) seperated with comma. </li>
								<br />
								<br />
								<li>8.2)For
								closing the firefox window after logout from IAUT resource
								management, when IAUT resource management is directly accessed
								from the browser. Following are steps given for firefox
								configuration. a. Open Firefox window-> Type "about:config" in
								Address Bar and press enter button. b. Press "I'll be careful,I
								promise!" button which appear with warranty message. c. Find
								"dom.allow_scripts_to_close_windows" and set the value to
								"true". </li>
								<br />
								<br />
								<li>9. Google Chrome Users - Google chrome is
								exceptional case for windows authetication, please add User Name
								and password for trusted connection.</li>
								<br />
								<br />
								<li>10. The form
								marked with "*" symbol requires are mandatory field.</li>
								<br />
								<br />
								<li>11. To Remove Security warning message.Kindly follow below steps: a.
								Open Internet Explorer -> Tools -> Internet Option -> Security
								Tab -> Custom Level -> Access data sources across domains ->
								Enable </li>
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
