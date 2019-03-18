<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<html>
<head>
<meta charset="utf-8" name="viewport" content="width=device-width, initial-scale=1">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	
	<script src="js/jquery.min.js"></script>
	<script src="js/jquery.dataTables.min.js"></script>
	
	<script src="js/bootstrap3-typeahead.min.js"></script>	
	<script src="js/bootstrap.min.js"></script>
	<script src="js/handlebars-v4.0.11.js" ></script>
	<script src="js/sample.js"></script> 
	 <script src="js/location.js" ></script> 
    <script src="js/project.js" ></script> 
	<script src="js/dropdown.js" ></script>
	<script src="js/offer.js" ></script>
	<script src="js/iautResource.js" ></script>
	<script src="js/sample.js"></script>  
	<script src="js/jquery.table2excel.min.js"></script>
	<script src ="js/jquery-ui.min.js"></script>	
	<script src ="js/jquery.validate.js"></script>
	<script src ="js/jquery.form-validator.min.js"></script>
    <script	src="js/bootstrap-multiselect.js"></script>
	<script src="js/location.js"></script> 
      
    <link href="css/font-awesome.min.css" rel="stylesheet">	
	<link rel="stylesheet" href="css/jquery.dataTables.min.css">
	<link rel="stylesheet" href="css/bootstrap-3.3.6.min.css" />
	<link rel="stylesheet" href="css/bootstrap-multiselect.css" /> 
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
<style>
.custab {
	border: 1px solid #ccc;
	padding: 5px;
	margin: 5% 0;
	box-shadow: 3px 3px 2px #ccc;
	transition: 0.5s;
}
.row1 {
    display: inline-block;
}
body {  background-image: url(img/02.png); } 
#black:hover {
	color: black !important;
}
.error {
	color: #ff0000;
	font-style: italic;
	font-weight: bold;
}
</style>

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
	
<script>

var reqRefNosJson = ${requirementRefNum};
var profiledata=${profilesJson};
var initialEvaluationResultJson=${initialEvaluationResultJson};
var customerInterviewStatusJson=${customerInterviewStatusJson};
var offerProcessingStatus = ${offerProcessingstatusJson};  
var positionStatusJson=${positionStatusJson};
var opportunityStatusJson=${opportunityStatusJson};



</script>
	
<style>
	.custab{
    border: 1px solid #ccc;
    padding: 5px;
    margin: 5% 0;
    box-shadow: 3px 3px 2px #ccc;
    transition: 0.5s;
    }
body {  background-image: url(img/02.png); } 	
#black:hover{
	color:black !important;
}

table.dataTable.select tbody tr,
table.dataTable thead th:first-child {
  cursor: pointer;
}
.row1 {
    display: inline-block;
}
</style>	


</head>
<body style="font-family:Verdana;">

<script type="text/x-handlebars-template" id="offerView">
{{#each profilesJson}}

  <tr>
		
		<td>{{id}}</td>
		<td>{{reqRefNo}}</td>
		<td>{{name}}</td>
		<td>{{initialEvaluationResult.configValue.value}}</td>
		<td>{{profileSharedCustomer}}</td>
		<td>{{customerInterviewStatus.configValue.value}}</td>
		<td>{{offerProcessingStatusStr}}</td>

</tr>
{{/each}}

</script>


<script type="text/javascript">

function show(aval) {
    if (aval == "20") {
    	$('#hiddenDiv').hide();
    } 
    else{
    	$('#hiddenDiv').show();
    }
 }
  
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
<div class="header" style="padding-bottom:0px;">
  <div class="logo" >
  <img src="img\tmlogo.png" class="techm" align="left">
  </div>
  <span class="heading1"><p><b>IAUT Resource Management</b></p> </span>
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
						<span><a id="fon" href="dashboard.html" style="" >Home</a></span>
						<span><a id="fon" href="help.html" style="">Help</a></span>
						<span><a id="fon" href="contactus.html" style="">Contact Us</a></span>
					</div>
				</div>
	</div>
 
</div>


<div class="navii">
				<div>
					<ul class="nav navbar-nav" style="font-size:16px;background-color:#b30000;width:100%;height:45px;" >
						<li><a id="black" href="dashboard.html" style="margin-right:0px;color:white;height:43px;">Dashboard</a></li>
						<li><a id="black" href="requirements.html" style="margin-right:0px;color:white;height:43px;">Requirements</a></li>
						<li id="black"><a href="profiles.html" style="margin-right:0px;color:white;height:43px;">Profiles</a></li>
						<li class="active"><a href="#" style="margin-right:0px;background-color:white;color:black;height:45px;">OfferStatus</a></li>
						<li><a id="black" href="reports.html" style="margin-right:0px;color:white;height:43px;">Reports</a></li>
						<li><a id="black" href="charts.html" style="margin-right: 0px; color: white; height: 43px;">Charts</a></li>
					</ul>
				</div>
	</div>
	
	<div class="mobnavii">
	<div class="row navbar" style="margin-bottom:0px;border-top-width:0px;background:none;">
		<nav class="navbar navbar-default" role="navigation">
<div class="navbar-header" style="background-color:#b30000;">
<button type="button" class="navbar-toggle x collapsed toggle but2" data-toggle="collapse" data-target="#navbar-collapse-x">
<span class="icon-bar"></span>
<span class="icon-bar"></span>
<span class="icon-bar"></span>
</button>
<a class="navbar-brand heading2" href="#top" style="color:white;">Menu</a>
</div>

<div class="collapse navbar-collapse" id="navbar-collapse-x">
<ul class="nav navbar-nav navbar-right dropdata" style="">
<li data-toggle="collapse" data-target="#navbar-collapse.in"><a class="ddata" href="dashboard.html">Dashboard</a></li>
<li data-toggle="collapse" data-target="#navbar-collapse.in"><a class="ddata" href="requirements.html">Requirements</a></li>
<li data-toggle="collapse" data-target="#navbar-collapse.in"><a class="ddata" href="profiles.html">Profiles</a></li>
<li data-toggle="collapse" data-target="#navbar-collapse.in"><a class="ddata" href="#">OfferStatus</a></li>
<li data-toggle="collapse" data-target="#navbar-collapse.in"><a class="ddata" href="reports.html">Reports</a></li>
<li data-toggle="collapse" data-target="#navbar-collapse.in"><a class="ddata" href="help.html">Help</a></li>
<li data-toggle="collapse" data-target="#navbar-collapse.in"><a class="ddata" href="contactus.html">Contact Us</a></li>

</ul>
</div><!-- .navbar-collapse -->
</nav>
</div>
</div>

			<div class="mainsec2">
			<h2 style="margin-bottom:1%;margin-left:3%;font-size:30px;">Profiles</h2>
			
	      <!-- Icon Cards-->
<input type="hidden" id="userRoleId" name="userRoleId" value="${userRole}"/>
		
			
		
		<spring:form  id="profileForm" name='profileForm' action="profiles" method='GET' >
		
		<div class="table" style="overflow:auto;margin-top:2%;" >
		
  <table id="reqtable2" class="display">
    <thead style="vertical-align:bottom;">
      <tr>
					<th>Profile ID</th>
					<th>Requirement ID</th>
                    <th>Candidate Name</th>
					<th>Initial Evaluation result</th>
					<th>Profile shared with customer</th>
					<th>Customer interview status</th>
					<th>Offer Processing Status</th>
      </tr>
    </thead>
<tbody align="center" id="offerView_content">
      
    </tbody>
    </table>
</div>
</spring:form>

	</div>
<div class="w3-row" style="background-color:#b30000;text-align:center;padding:10px;margin-top:7px;color:white;height:38px;bottom:0px;align:bottom;">© Tech Mahindra Limited ©2018. All rights reserved.</div> 
		
</body>


</html>
