<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	
	<%@ include file = "requirements.jsp" %>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">

<script	src="js/jquery.min.js"></script>
<script	src="js/jquery.dataTables.min.js"></script>
<script src="js/jquery.form-validator.min.js"></script>
<script src="js/jquery.table2excel.min.js"></script>
<script src="js/jquery-ui.min.js"></script>
<script	src="js/bootstrap3-typeahead.min.js"></script>
<script	src="js/bootstrap.min.js"></script>
<script	src="js/bootstrap-multiselect.js"></script>
<script src="js/handlebars-v4.0.11.js"></script>
<!-- <script src="js/table.js"></script> -->
<script src="js/sample.js"></script>
<script src="js/location.js"></script>
<script src="js/dropdown.js"></script>
<!-- <script src="js/requirement.js"></script> -->
<script src="js/dashjs.js" ></script>
<link rel="stylesheet"	href="css/jquery.dataTables.min.css">
<link rel="stylesheet"	href="css/bootstrap-3.3.6.min.css" />
<link rel="stylesheet"	href="css/bootstrap-multiselect.css" />
<link rel="stylesheet" href="css/w3.css">
<link rel="stylesheet" type="text/css" href="css/iautResource.css" />
<link href="css/jquery-ui.css" rel="stylesheet">
<link href="css/font-awesome.min.css" rel="stylesheet">

<script src="js/handlebar.js" ></script>


 <script type="text/javascript">
$(document).ready(function(){
    $("#tab2").click(function(){
    	alert("hii");
        $("#sec1").load("jsp/requirements.jsp");
    });
});
</script> 
</head>
<body style="font-family: Verdana;">
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
						<span><a id="fon" href="dashboard.html" style="">Home</a></span>
						<span><a id="fon" href="help.html" style="">Help</a></span> <span><a
							id="fon" href="contactus.html" style="">Contact Us</a></span>
					</div>
				</div>
			</div>

		</div>


		<div class="navii">
		<tr>
			<td style="width: 773px;">
				<div class="heading-left" style="text-align:right; background-color: #b30000;">
					<ul class="nav nav-tabs" style="font-size : 16px;color:white">
						<li class="active"><a data-toggle="tab" id="tab1" href="#" style="margin-right:0px;" >Dashboard</a></li>
						<li><a data-toggle="tab" id="sec" href="#tab2" style="margin-right:0px;" >Requirements</a></li>
						<li><a data-toggle="tab" href="#tab3" style="margin-right:0px;">Profiles</a></li>
						<li><a data-toggle="tab" id="sec4" href="#tab4" style="margin-right:0px;">Reports</a></li>
					</ul>
				</div>
			</td>
		</tr>	
	</div>

		<div class="mobnavii">
			<div class="row navbar"
				style="margin-bottom: 0px; border-top-width: 0px; background: none;">
				<nav class="navbar navbar-default" role="navigation">
					<div class="navbar-header" style="background-color: #b30000;">
						<button type="button"
							class="navbar-toggle x collapsed toggle but2"
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
								class="ddata" href="#">Requirements</a></li>
							<li data-toggle="collapse" data-target="#navbar-collapse.in"><a
								class="ddata" href="profiles.html">Profiles</a></li>
							<li data-toggle="collapse" data-target="#navbar-collapse.in"><a
								class="ddata" href="reports.html">Reports</a></li>
							<li data-toggle="collapse" data-target="#navbar-collapse.in"><a
								class="ddata" href="help.html">Help</a></li>
							<li data-toggle="collapse" data-target="#navbar-collapse.in"><a
								class="ddata" href="contactus.html">Contact Us</a></li>

						</ul>
					</div>
					<!-- .navbar-collapse -->
				</nav>
			</div>
		</div>
			<!-- ------------- Section 1 ---------------------- ---- ----------- -->
		<div id="sec1" >
		
		
		
		</div>

<!-- 	
	------------- Section 2 ---------------------- ---- -----------	
		<div id="include"> zxdfdgfth</div>
	
	------------- Section 4 ---------------------- ---- -----------	
		<div id="include1"> zxdfdgfth</div> -->
	
		<div class="w3-row"
			style="background-color: #b30000; text-align: center; padding: 10px; margin-top: 7px; color: white; height: 38px; bottom: 0px; align: bottom;">©
			Tech Mahindra Limited ©2018. All rights reserved.</div>
		
		

</body>


</html>
