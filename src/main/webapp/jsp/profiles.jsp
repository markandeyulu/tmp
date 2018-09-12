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
	<!-- <script src="js/location.js" ></script> -->
    <script src="js/project.js" ></script> 
	<script src="js/dropdown.js" ></script>
	<script src="js/profile.js" ></script>
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
var accountValues = ${accountValuesJson};
var profiledata=${profilesJson};
var profileSourceJson=${profileSourceJson};
var initialEvaluationResultJson=${initialEvaluationResultJson};
var customerInterviewStatusJson=${customerInterviewStatusJson};
var primarySkillJson=${primarySkillJson};
var projectValues = ${projectValuesJson};
var criticalJson=${criticalJson};
var requirementTypeJson=${requirementTypeJson};
var locationJson=${locationJson};
var intimationModeJson=${intimationModeJson};
var positionStatusJson=${positionStatusJson};
var opportunityStatusJson=${opportunityStatusJson};
var skillCategoryJson=${skillCategoryJson};
var accountListJson=${accountListJson};
var projectListJson=${projectListJson};
function adminRoleCheck() {
    if ($('#userRoleId').val() == "Admin") {
    	 $('#hidProfile').show();
    	 $('#updateBtn').show();
    }else{
    	$('#hidProfile').hide();
    	$('#updateBtn').hide();
    }
 }

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
<body onload="adminRoleCheck()" style="font-family:Verdana;">

<script type="text/x-handlebars-template" id="profileView">
{{#each profilesJson}}

  <tr>
		<td> <input type="checkbox" class="checkbox" name="" value=""></td>
		<td href="#" data-toggle="modal" data-target="#popup" onclick="loadDetail('{{id}}');"><a>{{id}}</a></td>
		<td>{{reqRefNo}}</td>
		<td>{{location}}</td>
		<td>{{primarySkill.configValue.value}}</td>
		
		<td>{{profileSource.configValue.value}}</td>
		<td>{{profileSharedBy}}</td>
		<td>{{name}}</td>
		<td>{{email}}</td>
		<td>{{contactNo}}</td>
		<td>{{currentCompany}}</td>
		<td>{{yearsOfExperience}}</td>
		<td>{{relevantExperience}}</td>
		<td>{{noticePeriod}}</td>
		<td>{{currentCTC}}</td>
		<td>{{expectedCTC}}</td>
		<td>{{internalEvaluationResultDate}}</td>
		<td>{{initialEvaluationResult.configValue.value}}</td>
		<td>{{profileSharedCustomer}}</td>
		<td>{{profileSharedCustomerDate}}</td>
		<td>{{customerInterviewStatus.configValue.value}}</td>
		<td>{{remarks}}</td>
		<td>{{profileSharedDatestr}}</td>
</tr>
{{/each}}

</script>
<!-- <td>{{profileSharedDate}}</td> --> 
 <script>
 function  validatereqid(){
	 var x = document.getElementById("reqRefNo").value;
		  
		
		$.ajax({
			 type: "GET",
			 dataType: 'json',
			 url: '/ResourceManagementApp/validateRefById.action',
			 data: { "id": x },
			 
			 success: function (data) {
				/*  alert(data) */
				 if(data == 1){
				 	document.getElementById("refback").innerHTML = "";
				 	 $("#addButton").attr("disabled",false); 
				 }
				 else{
					 document.getElementById("refback").innerHTML = "Invalid Ref Number"; 
					  $("#addButton").attr("disabled",true); 
				 }
			 },
			 failure: function(data){
				/*  alert('Inside Ajax call. data failed') */
				 
			 }
			 }); 
	}
 
 
 $(document).ready(function(){
 
	 $('#reqtable2 tbody').on('click', 'input[type="checkbox"]', function(e){
	        var row = $(this).closest('tr');
	        row.addClass('selected');
	   	});
	       
		    $("#deleteProfile").click(function(){
		    	 var dataArr = [];
				    $.each($("#reqtable2 tr.selected"),function(){
				       dataArr.push($(this).find('td').eq(1).text());
				    });
		  if(dataArr.length == 0){
			  alert("Please select atleast one Record to delete");
		  }else{
		        $.ajax({
		           
		            type: 'POST',
			   		 url: "/ResourceManagementApp/profileDelete.action",
			   		 data: { dataArr: dataArr},
		            success: function(e){      
                	 		$('#profileMsg').html(e);
                	 	$.each( dataArr, function( key, value ) {
        		    	   $( "tr:contains('" + value + "')").each(function() {
        		    		   $('#reqtable2').dataTable().fnDeleteRow(this);
        		    	   });
        		    	 });
                	 $("#checkall").prop("checked", false);
        			}
		        });
		  }
		   
		});

		    $("#reqtable2 #checkall").click(function () {
		        if ($("#reqtable2 #checkall").is(':checked')) {
		            $("#reqtable2 input[type=checkbox]").each(function () {
		                $(this).prop("checked", true);
		                $(this).parent().parent().addClass('selected');
		            });

		        } else {
		            $("#reqtable2 input[type=checkbox]").each(function () {
		                $(this).prop("checked", false);
		                $(this).parent().parent().removeClass('selected');
		            });
		        }
		    });
		    
		    $(".checkbox").change(function () {
				 if(this.checked == false){ //if this item is unchecked
				        $("#checkall")[0].checked = false; 
				        $(this).parent().parent().removeClass('selected');
				    }
				 
				 if ( $('input[type="checkbox"]:checked').length == table.profilesJson.length ){
				        $("#checkall").prop('checked', true);
				        $(this).parent().parent().addClass('selected');
				    }
				 
			 });
		    
		    $("[data-toggle=tooltip]").tooltip();
		    
		    
	    	$('#uploadFile').on('click', function(e){
	    		
	    	var filename = $('input[type=file]').val().split('\\').pop();
	    	    $.ajax({
	                type: 'POST',           
	       		 url: "/ResourceManagementApp/uploadFile.action",
	       		 data: { "file": filename },
	       		    success: function(e){ 
	       		    	$('#fileId').html(e); 
	     			},
	     			complete:function(){
	     				document.getElementById("file").value = null;
	     				//$("#uploadFile").attr("disabled",false);
	     				$("#fileId").fadeOut(5000);
	     				$("#uploadFile").prop("disabled", false);
	     			} 
	            });
	    });
	    	
	     
 var addMessage = ${addMessage};
	if (undefined != addMessage) {
		if (addMessage == 1) {
			$("#profileMsg").text('Successfully the profile has been created!!');
		} else if (addMessage == 0) {
			$("#profileMsg").text('Failure!! the profile has not been created!!');
		}
	}
 var updateMessage = ${updateMessage};
	      if (undefined != updateMessage) {
	            if (updateMessage == 1) {
	                  $("#profileMsg").text('Successfully the profile has been updated!!');
	                  //window.location.href = "profiles.html";
	            } else if (updateMessage == 0) {
	                  $("#profileMsg").text('Failure!! the profile has not been updated!!');
	            }
	      }
	      
	      $('#accountProfile').change(function() {
	  		var account_id=$('#accountProfile').val();
	  		$('#projectProfile option').remove();
	  		
	  		var projectListData=projectListJson.projectListJson;
	  		var g_projectListArray = [];	
	  	      $.each(projectListData, function(index) {
	  	      var g_item = [];
	  	      if ($("#accountProfile").val() == (projectListData[index].accountId)) {
	  	    	  g_item.push(projectListData[index].projectId);	 
	  		      g_item.push(projectListData[index].projectName);	
	  		      g_projectListArray.push(g_item);	
	  	      }
	  	       
	  	                   		
	  	     });
	  	      $("#projectProfile").append('<option value="">Select Project</option>');  
	  	      
	  	  	$.each(g_projectListArray, function(i) {
	  	  		var g_projectListItem = g_projectListArray[i];
	  	  		$("#projectProfile").append('<option id="' + g_projectListItem[0] + '" value="' + g_projectListItem[0] + '">' + g_projectListItem[1] + '</option>');
	  	  	}); 
	  	  	
	  	  	
	        
	      });
	      
});
 

 function validate(evt) {
 	  var theEvent = evt || window.event;
 	  var key = theEvent.keyCode || theEvent.which;
 	  key = String.fromCharCode( key );
 	  var regex = /[0-9]|\./;
 	  if( !regex.test(key) ) {
 	    theEvent.returnValue = false;
 	    if(theEvent.preventDefault) theEvent.preventDefault();
 	  }
 	}

 function changeTextBoxUpdate(){
		var e = document.getElementById("profileSharedCustomerProfile");
     
	    //var strSel = "The Value is: " + e.options[e.selectedIndex].value + " and text is: " + e.options[e.selectedIndex].text;
	var chkBoxValue = e.options[e.selectedIndex].value;
	
	
	//alert(chkBoxValue);

		if(chkBoxValue=="No"){
			document.getElementById("profileSharedCustomerDateProfile").disabled=true;
			document.getElementById("customerInterviewStatusProfile").disabled=true;
		}else{
			document.getElementById("profileSharedCustomerDateProfile").disabled=false;
			document.getElementById("customerInterviewStatusProfile").disabled=false;
		}
		}
 function changeTextBoxAdd(){
	    var f=document.getElementById("profileSharedCustomer");
 
	    //var strSel = "The Value is: " + e.options[e.selectedIndex].value + " and text is: " + e.options[e.selectedIndex].text;
	var chkValue = f.options[f.selectedIndex].value;
	
	
	//alert(chkValue);

		if(chkValue=="No"){
			document.getElementById("datepicker").disabled=true;
			document.getElementById("customerInterviewStatusAdd").disabled=true;
		}else{
			document.getElementById("datepicker").disabled=false;
			document.getElementById("customerInterviewStatusAdd").disabled=false;
		}
		}
 function validateEmail(event){
     var reg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
     if (reg.test(event.value) == false) 
     {
         alert('Invalid Email Address');
         document.getElementById("email").focus();
         return false;
         
     }

     return true;

}
 
 
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
						<li class="active"><a href="#" style="margin-right:0px;background-color:white;color:black;height:45px;">Profiles</a></li>
						<li><a id="black" href="reports.html" style="margin-right:0px;color:white;height:43px;">Reports</a></li>
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
<li data-toggle="collapse" data-target="#navbar-collapse.in"><a class="ddata" href="#">Profiles</a></li>
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
		
<div id="hidProfile" style="background-color: #f1f1f1; margin-top: 2%; display:none;">
	<button
					style="margin-left: 5%; background-color: #b30000; color: white;"
					type="button" class="btn btn-md button1" data-toggle="modal"
					data-target="#squarespaceModal">Add Profile</button>
	<Button name="submit" id="deleteProfile" class="btn btn-md button1" style="background-color:#b30000;color:white;" type="submit">Delete</Button>
	<button
					style="margin-left: 0%; background-color: #b30000; color: white;"
					type="button" class="btn btn-md button1" data-toggle="modal"
					data-target="#uploadModal">Upload Profile</button>
	<!-- <div id="addProfileMsg" style="display: inline-block; margin-left:10%; font-weight: bold">
					</div>
	<div id="deleteProfileMsg" style="display: inline-block; margin-left:10%; font-weight: bold">
			</div>
			<div id="updateProfileMsg" style="display: inline-block; margin-left:2%; font-weight: bold">
			</div> -->
			<div id="profileMsg" style="display: inline-block; margin-left:2%; font-weight: bold">
			</div>
			
			
		</div>
<div class="modal fade" id="uploadModal" tabindex="-1" role="dialog"
			aria-labelledby="modalLabel" aria-hidden="true">
			<div class="modal-dialog" style="width: 80%; font-size: 12px;">
				<div class="modal-content">
					<div class="modal-header" style="background-color: #b30000;">
						<button type="button" id="reload" class="close" data-dismiss="modal"
							style="background-color: white;">
							<!-- <span aria-hidden="true">×</span><span class="sr-only" onclick="window.location.reload()">Close</span> -->
							<a class="button" onclick="window.location.reload()" href="#">x</a>
							
						</button>
						<h3 class="modal-title" id="lineModalLabel" style="color: white;">Upload File</h3>
					</div>
					<div class="modal-body">
						<div class="container" id="modelreload">
							<div style="width: 60%; margin-left: 8%;">
								<div class="card-body" id="uploadDiv">
									<form enctype="multipart/form-data" >
										<div class="form-group">
											<div id="fileId" style="font-weight: bold; display: inline-block;"></div>
											<p>File to upload:<span style="color:red">*</span><input type="file" name="file" id="file"><br /></p>
											<tr>
												<td><Button type="button" id="uploadFile" name="Upload"
														class="btn btn-md button1"
														style="background-color: #cc0000; color: white; float: right; margin-right: 7%;"
														type="submit">Upload File</Button></td>
														
											</tr>

										</div>
									</form>
								</div>
							</div>
						
						</div>
					</div>
				</div>
			</div>
		</div>
			<div class="modal fade" id="squarespaceModal" tabindex="-1"
				role="dialog" aria-labelledby="modalLabel" aria-hidden="true">
				<div class="modal-dialog" style="width: 80%; font-size: 12px;">
					<div class="modal-content">
						<div class="modal-header" style="background-color: #b30000;">
							<button type="button" class="close" data-dismiss="modal"
								style="background-color: white;">
								<span aria-hidden="true">×</span><span class="sr-only">Close</span>
							</button>
							<h3 class="modal-title" id="lineModalLabel" style="color: white;">Add
								Profile</h3>
						</div>
						<div class="modal-body">
							<div class="container">
							<spring:form name='addProfileForm' action="addprofile"  
								method="POST" modelAttribute="profiles">
								<div class="row">
									<div class="col-25">
										<spring:label path="reqRefNo">Requirement Ref Number<span style="color:red">*</span></spring:label>
									</div>
									<div class="col-75">
										<spring:input class="form-control" path="reqRefNo" action="validaterefById"  oninvalid="this.setCustomValidity('Requirement Ref Number must not be empty')" 
											id="reqRefNo" type="text" required="required" oninput="this.setCustomValidity('')"  onchange="validatereqid()" 
											placeholder="Enter Requirement Ref Number.." />
									</div>
									<div id="refback" style="color:red"></div>
								</div>
								
								<div class="row">
									<div class="col-25">
										<spring:label path="name">Candidate Name<span style="color:red">*</span></spring:label>
									</div>
									<div class="col-75">
										<spring:input class="form-control" path="name" oninvalid="this.setCustomValidity('Candidate Name must not be empty')" 
											id="name" type="text" required="required" oninput="this.setCustomValidity('')" 
											placeholder="Enter Candidate Name.." />
									</div>
								</div>
								<div class="row">
									<div class="col-25">
										<spring:label path="email">Candidate Email ID<span style="color:red">*</span></spring:label>
									</div>
									<div class="col-75">
										<spring:input class="form-control" path="email" oninvalid="this.setCustomValidity('Candidate email id must not be empty')" 
											id="email" type="text" required="required" oninput="this.setCustomValidity('')" onChange="validateEmail(this);"
											placeholder="Enter Candidate Email ID.." />
									</div>
									<div id="refback" style="color:red"></div>
								</div>

								<div class="row">
									<div class="col-25">
										<spring:label path="location">Current Location<span style="color:red">*</span></spring:label>
									</div>
									<div class="col-75">
										<spring:input class="form-control" path="location" id="location" oninvalid="this.setCustomValidity('Location must not be empty')"
										type="text" placeholder="Enter Current Location.." required="required" oninput="this.setCustomValidity('')" 
											 />
									</div>
								</div>
								<div class="row">
									<div class="col-25">
										<spring:label path="primarySkillAdd">Primary Skill<span style="color:red">*</span></spring:label>
									</div>
									<div class="col-75">
										<spring:input class="form-control" path="primarySkillAdd"  
											id="primarySkillAdd" type="text" oninvalid="this.setCustomValidity('Primary skill must not be empty')"
											placeholder="Enter Primary Skill.." required="required"  oninput="this.setCustomValidity('')" />
									</div>
								</div>

								<div class="row">
									<div class="col-25">
										<spring:label path="contactNo">Contact No<span style="color:red">*</span></spring:label>
									</div>
									<div class="col-75">
										<spring:input type="text" class="form-control" oninvalid="this.setCustomValidity('Contact number must not be empty and please enter 10 digit number')"
											id="contactNo" path="contactNo" onkeypress='validate(event)'  maxlength="10" oninput="this.setCustomValidity('')" 
											placeholder="Enter Contact No.." required="required"/>
									</div>
								</div>
								
								<div class="row">
									<div class="col-25">
										<spring:label path="profileSourceAdd">Source of Profile<span style="color:red">*</span></spring:label>
									</div>
									<div class="col-75">
										<spring:select required="required" class="form-control" id="profileSource" path="profileSourceAdd" oninput="this.setCustomValidity('')"
										oninvalid="this.setCustomValidity('Please select Source of Profile')" ><!-- onchange="show(this.options[this.selectedIndex].value)" -->
										</spring:select>
									</div>
								</div>

								<div class="row">
									<div class="col-25">
										<spring:label path="profileSharedBy">Profile Shared By<span style="color:red">*</span></spring:label>
									</div>
									<div class="col-75">
										<spring:input class="form-control" type="text" oninput="this.setCustomValidity('')" 
											id="profileSharedBy" path="profileSharedBy" oninvalid="this.setCustomValidity('Profile shared by must not be empty')"
											placeholder="Enter Profile Shared By.." required="required"/>
									</div>
								</div>
								<!-- <div id="hiddenDiv"> -->
								<div class="row">
									<div class="col-25">
										<spring:label path="currentCompany">Current Company<span style="color:red">*</span></spring:label>
									</div>
									<div class="col-75">
										<spring:input class="form-control" type="text" oninvalid="this.setCustomValidity('Current company must not be empty')"
											id="currentCompany" path="currentCompany" oninput="this.setCustomValidity('')" 
											placeholder="Enter Current Company.." required="required"/>
									</div>
								</div>
								<div class="row">
									<div class="col-25">
										<spring:label path="yearsOfExperience">Total Experience(in years)<span style="color:red">*</span></spring:label>
									</div>
									<div class="col-75">
										<spring:input class="form-control" type="text" oninvalid="this.setCustomValidity('Total experience in years must not be empty')" 
										 oninput="this.setCustomValidity('')" id="yearsOfExperience" path="yearsOfExperience" onkeypress='validate(event)' 
											placeholder="Enter Total Experience(in years).." required="required"/>
									</div>
								</div>
								<div class="row">
									<div class="col-25">
										<spring:label path="relevantExperience">Relevant Experience(in years)<span style="color:red">*</span></spring:label>
									</div>
									<div class="col-75">
										<spring:input type="text" id="relevantExperience" class="form-control" path="relevantExperience" oninvalid="this.setCustomValidity('Relevant experience in years must not be empty')" 
										 oninput="this.setCustomValidity('')" placeholder="Enter Relevant Experience(in years).." onkeypress='validate(event)' required="required"	/>
									</div>
								</div>
								<div class="row">
									<div class="col-25">
										<spring:label path="noticePeriod">Notice Period(in days)</spring:label>
									</div>
									<div class="col-75">
										<spring:input type="text" class="form-control" id="noticePeriod" onkeypress='validate(event)' 
										path="noticePeriod" placeholder="Enter Notice Period(in days).." />
									</div>
								</div>

								<div class="row">
									<div class="col-25">
										<spring:label path="currentCTC">Current CTC</spring:label>
									</div>
									<div class="col-75">
										<spring:input type="text" class="form-control" id="currentCTC"  
										  path="currentCTC" placeholder="Enter Current CTC(in Lakhs).." />
									</div>
								</div>
								<div class="row">
									<div class="col-25">
										<spring:label path="expectedCTC">Excepted CTC</spring:label>
									</div>
									<div class="col-75">
										<spring:input type="text" class="form-control" 
										   id="expectedCTC" path="expectedCTC" 
											placeholder="Enter Excepted CTC(in Lakhs)..." />
									</div>
								</div>
								<!-- </div> -->
								<div class="row">
									<div class="col-25">
										<spring:label path="profileSharedDate">Profile Shared Date<span style="color:red">*</span></spring:label>
									</div>
									<div class="col-75">
										<spring:input class="form-control" path="profileSharedDate"
											id="start_datepicker"  type="text" 
											placeholder="Enter Profile Shared Date.." required="required"  />
									</div>
								</div>

								<div class="row">
									<div class="col-25">
										<spring:label path="internalEvaluationResultDate">Internal Evaluation result Date</spring:label>
									</div>
									<div class="col-75">
										<spring:input class="form-control" path="internalEvaluationResultDate"
											id="datepicker1"  type="text" 
											placeholder="Enter Internal Evaluation result Date.." />
									</div>
								</div>
								<div class="row">
									<div class="col-25">
										<spring:label path="initialEvaluationResultAdd">Initial Evaluation Result</spring:label>
									</div>
									<div class="col-75">
										<spring:select class="form-control" id="initialEvaluationResultAdd" path="initialEvaluationResultAdd">
											<!-- <option value="">Select Initial Evaluation Result</option>
											<option value="shortlisted">Shortlisted</option>
											<option value="rejected">Rejected</option>
											<option value="hold">Hold</option>
											<option value="didnotprocess">Did not process</option> -->
										</spring:select>
									</div>
								</div>
									<div class="row">
									<div class="col-25">
										<spring:label path="profileSharedCustomer">Profile Shared with Customer<span style="color:red">*</span></spring:label>
									</div>
									<div class="col-75">
										<spring:select class="form-control" onchange="changeTextBoxAdd();" id="profileSharedCustomer" path="profileSharedCustomer"
										oninvalid="this.setCustomValidity('Profile Shared Customer must not be empty')" 
											required="required" oninput="this.setCustomValidity('')">
											<option value="">Select Profile Shared with Customer</option>
											<option value="Yes">Yes</option>
											<option value="No">No</option>
										</spring:select>
									</div>
								</div>
								<div class="row">
									<div class="col-25">
										<spring:label path="profileSharedCustomerDate">Profile Shared with Customer Date<span style="color:red">*</span></spring:label>
									</div>
									<div class="col-75">
										<spring:input class="form-control" path="profileSharedCustomerDate" 
											id="datepicker"  type="text" placeholder="Enter Profile Shared with Customer Date.." 
											oninvalid="this.setCustomValidity('Profile Shared Customer Date must not be empty')" 
											required="required" oninput="this.setCustomValidity('')"
											/>
									</div>
								</div>
								<div class="row">
									<div class="col-25">
										<spring:label path="customerInterviewStatusAdd">Customer Interview Status<span style="color:red">*</span></spring:label>
									</div>
									<div class="col-75">
										<spring:select class="form-control" id="customerInterviewStatusAdd" path="customerInterviewStatusAdd"
										 oninvalid="this.setCustomValidity('Customer Interview Status must not be empty')" 
											required="required" oninput="this.setCustomValidity('')">
											<!-- <option value="">Select Customer Interview Status</option>
											<option value="selected">Selected</option>
											<option value="rejected">Rejected</option>
											<option value="hold">Hold</option> -->
										</spring:select>
									</div>
								</div>
								<div class="row">
									<div class="col-25">
										<spring:label path="remarks">Remarks</spring:label>
									</div>
									<div class="col-75">
										<spring:input type="text" class="form-control"
											id="remarks" path="remarks" 
											placeholder="Remarks.." />
									</div>
								</div>
																	
								<div class="row">
									<Button name="submit" id="addButton" class="btn btn-md button1" 
										style="background-color: #cc0000; color: white; float:right;margin-right:7%;" type="submit">Submit</Button>
								</div>
							</spring:form>
						</div>
						</div>
					</div>
				</div>
			</div>
		
		<spring:form  id="profileForm" name='profileForm' action="profiles" method='GET' >
		
		<div class="table" style="overflow:auto;margin-top:2%;" >
		
  <table id="reqtable2" class="display">
    <thead style="vertical-align:bottom;">
      <tr>
		 <th><input type="checkbox" name="checkall" value="1" id="checkall" />&nbsp; All</th>
					<th>Profile ID</th>
					<th>Requirement ID</th>
					<th>Current Location</th>
					<th>Primary Skill</th>
					<!-- <th>Profile shared date</th> -->
					<th>Source of Profile</th>
					<th>Profile shared by</th>
                    <th>Candidate Name</th>
                    <th>Candidate Email ID</th>
					<th>Contact Number</th>
					<th>Current Company</th>
					<th>Total Experience (in years)</th>
					<th>Relevant Experience (in years)</th>
					<th>Notice Period (in days)</th>
					<th>Current CTC (in Lakhs)</th>
					<th>Expected CTC (in Lakhs)</th>
					<th>Internal Evaluation result Date</th>
					<th>Initial Evaluation result</th>
					<th>Profile shared with customer</th>
					<th>Profile shared with customer date</th>
					<th>Customer interview status</th>
					<th>Remarks</th>
					<th>Profile shared date</th>
      </tr>
    </thead>
<tbody align="center" id="profileView_content">
      
    </tbody>
    </table>
</div>
</spring:form>
<div class="modal fade" id="popup" tabindex="-1"
				role="dialog" aria-labelledby="modalLabel" aria-hidden="true">
				<div class="modal-dialog" style="width: 90%;">
					<div class="modal-content">
						<div class="modal-header" style="background-color: #b30000;">
						<button type="button" id="reload" class="close" data-dismiss="modal"
							style="background-color: white;">
							<span aria-hidden="true">×</span><span class="sr-only">Close</span>
						</button>
							<h3 class="modal-title" id="lineModalLabel" style="color: white;">Update Profile</h3>
						</div>
						<div class="modal-body">
						<div class="container" style="overflow-x:scroll;width:100%;">
							<spring:form name='updateProfile' action="profileById"  
								method="POST" modelAttribute="profiles">
								
								 <div style="width:50%; height:100%" class="row1" >
								 <table width="80%">
								 <tr>
										<td><spring:label path="reqRefNo">Requirement Id</spring:label></td>
										<td><spring:input class="form-control" path="reqRefNo"
											id="requirementId" type="text" value=""  readonly="true"/></td>
										
								</tr>
								 <tr>
										<td><spring:label path="id">Profile Id</spring:label></td>
										<td><spring:input class="form-control" path="id"
											id="idProfile" type="text" value=""  readonly="true"/></td>
										
									</tr>
									<tr>
										<td><spring:label path="name">Candidate Name<span style="color:red">*</span></spring:label></td>
									
									
										<td><spring:input class="form-control" path="name"
											id="nameProfile" type="text" value="" oninvalid="this.setCustomValidity('Candidate Name must not be empty')" 
											required="required" oninput="this.setCustomValidity('')"
											placeholder="Enter Candidate Name.." /></td>
										
									</tr>
									<tr>
									<td><spring:label path="email">Email ID <span style="color:red">*</span></spring:label></td>
									
									
										<td><spring:input class="form-control" path="email" id="emailProfile"
											type="text" oninvalid="this.setCustomValidity('Email Id must not be empty')" 
											required="required" onchange="validateEmail(this);" oninput="this.setCustomValidity('')"/></td>
									</tr>
									<tr>
										<td><spring:label path="contactNo">Contact No<span style="color:red">*</span></spring:label></td>
									
									
										<td><spring:input class="form-control" path="contactNo" id="contactNoProfile"
											type="text" oninvalid="this.setCustomValidity('Contact number must not be empty and please enter 10 digit number')" 
											required="required" maxlength="10" oninput="this.setCustomValidity('')" /></td>
									</tr>
									<tr>
										<td><spring:label path="location">Current Location<span style="color:red">*</span></spring:label></td>
									
									
										<td><spring:input class="form-control" path="location" id="locationProfile"
											type="text" placeholder="Enter Current Location.." oninvalid="this.setCustomValidity('Current location must not be empty')" 
											required="required" oninput="this.setCustomValidity('')"/></td>
									</tr>
									
									<tr>
										<td><spring:label path="currentCompany">Current Company<span style="color:red">*</span></spring:label></td>
									
									
										<td><spring:input class="form-control" path="currentCompany"
											id="currentCompanyProfile" type="text" value="" oninvalid="this.setCustomValidity('Current company must not be empty')" 
											required="required" oninput="this.setCustomValidity('')"
											placeholder="Enter Current Company.." /></td>
										
									</tr>
									<tr>
										<td><spring:label path="primarySkill">Primary Skill <span style="color:red">*</span></spring:label></td>
									
									
										<td>
											<spring:select id="primarySkillProfile" multiple="false" path="primarySkill.id" class="form-control dropdown-toggle text-left " 
											oninvalid="this.setCustomValidity('Primary skill must not be empty')" 
											required="required" oninput="this.setCustomValidity('')">
						            </spring:select></td>
										
									</tr>
									<tr>
										<td><spring:label path="profileSharedDate">Profile Shared Date <span style="color:red">*</span></spring:label></td>
									
									
										<td><spring:input class="form-control" path="profileSharedDate"
											id="profileSharedDateProfile" type="text" value=""
											oninvalid="this.setCustomValidity('Profile shared date must not be empty')" 
											required="required" oninput="this.setCustomValidity('')"
											 /></td>
										
									</tr>
									<tr>
										<td><spring:label path="profileSource">Profile Source<span style="color:red">*</span></spring:label></td>
									
									
										<td>
											<spring:select id="profileSourceProfile" multiple="false" path="profileSource.id"   class="form-control dropdown-toggle text-left " 
											oninvalid="this.setCustomValidity('Profile source must not be empty')" 
											required="required" oninput="this.setCustomValidity('')"
											onchange="show(this.options[this.selectedIndex].value)">
						            </spring:select></td>
									</tr>
									<tr>
										<td><spring:label path="profileSharedBy">Profile Shared By<span style="color:red">*</span></spring:label></td>
									
									
										<td><spring:input class="form-control" path="profileSharedBy" id="profileSharedByProfile"
											type="text" oninvalid="this.setCustomValidity('Profile shared by must not be empty')" 
											required="required" oninput="this.setCustomValidity('')" /></td>
									</tr>
									<!-- <div id="hiddenDiv"> -->
										<tr>
											<td><spring:label path="yearsOfExperience">Years Of Experience <span
														style="color: red">*</span>
												</spring:label></td>


											<td><spring:input class="form-control"
													path="yearsOfExperience" id="yearsOfExperienceProfile"
													type="text" value=""
													oninvalid="this.setCustomValidity('Years of experience must not be empty')"
													required="required" oninput="this.setCustomValidity('')" /></td>

										</tr>
										<tr>
											<td><spring:label path="relevantExperience">Relevant Experience<span
														style="color: red">*</span>
												</spring:label></td>


											<td><spring:input class="form-control"
													path="relevantExperience" id="relevantExperienceProfile"
													type="text"
													oninvalid="this.setCustomValidity('Relevant experience must not be empty')"
													required="required" oninput="this.setCustomValidity('')" /></td>
										</tr>
										<tr>
											<td><spring:label path="currentCTC">CurrentCTC
												</spring:label></td>


											<td><spring:input class="form-control" path="currentCTC"
													id="currentCTCProfile" type="text" /></td>
										</tr>
										
										<tr>
											<td><spring:label path="noticePeriod">Notice Period
												</spring:label></td>


											<td><spring:input class="form-control"
													path="noticePeriod" id="noticePeriodProfile" type="text"/></td>

										</tr>
										<tr>
											<td><spring:label path="expectedCTC">ExpectedCTC</spring:label></td>


											<td><spring:input class="form-control"
													path="expectedCTC" id="expectedCTCProfile" type="text" /></td>
										</tr>
										
									<!-- </div> -->
									</table>
									</div>
								
									<div class="row1" style="width:40%;">
									<table>
									<tr>
										<td><spring:label path="account">Account</spring:label></td>
									<%-- 									
										<td><spring:select id="accountProfile" multiple="false" path="account.id" 
										class="form-control dropdown-toggle text-left " >
						            </spring:select></td> --%>
						            <td><spring:input class="form-control" path="account.id"
											id="accountProfile" type="text" value=""  readonly="true"/></td>
						           
									</tr>
									<tr>
										<td><spring:label path="project">Project</spring:label></td>
									 <td><spring:input class="form-control" path="project.id"
											id="projectProfile" type="text" value=""  readonly="true"/></td>
									
										<%-- <td>
											<spring:select id="projectProfile" multiple="false" path="project.id"   class="form-control dropdown-toggle text-left " >
						            </spring:select></td> --%>
									</tr>
									
									<tr>
										<td><spring:label path="isAllocated1">IsAllocated</spring:label></td>
									
									
										<td><spring:select class="form-control" id="isAllocatedProfile" path="isAllocated1">
											<option value="">Select isAllocated</ption>
											<option value="Yes">Yes</option>
											<option value="No">No</option>
										</spring:select></td>
									</tr>
									<tr>
										<td><spring:label path="allocationStartDate">Allocation Start Date</spring:label></td>
									
									
										<td><spring:input class="form-control" path="allocationStartDate"
											id="allocationStartDateProfile" type="text" value=""
											 /></td>
										
									</tr>
									<tr>
										<td><spring:label path="allocationEndDate">Allocation End Date</spring:label></td>
									
									
										<td><spring:input class="form-control" path="allocationEndDate" id="allocationEndDateProfile"
											type="text"  /></td>
									</tr>
									<tr>
										<td><spring:label path="createdBy">Created By</spring:label></td>
									
									
										<td><spring:input class="form-control" path="createdBy.name" id="createdByProfile"
											type="text"  readonly="true"/>
											</td>
									</tr>
									<tr>
										<td><spring:label path="createdOn">Created On</spring:label></td>
									
									
										<td><spring:input class="form-control" path="createdOn"
											id="createdOnProfile" type="text" value=""
											 readonly="true"/></td>
										
									</tr>
									<tr>
										<td><spring:label path="updatedBy">Updated By</spring:label></td>
									
									
										<td><spring:input class="form-control" path="updatedBy" id="updatedByProfile"
											type="text"  readonly="true"/></td>
									</tr>
									<tr>
										<td><spring:label path="updatedOn">updated On</spring:label></td>
									
									
										<td><spring:input class="form-control" path="updatedOn"
											id="updatedOnProfile" type="text" value=""
											 readonly="true"/></td>
										
									</tr>
									<tr>
										<td><spring:label path="internalEvaluationResultDate">Internal Evaluation Result Date</spring:label></td>
									
									
										<td><spring:input class="form-control" path="internalEvaluationResultDate"
											id="internalEvaluationResultDateProfile" type="text" value=""
											 /></td>
										
									</tr>
									<tr>
										<td><spring:label path="initialEvaluationResult">Initial Evaluation Result<span style="color:red">*</span></spring:label></td>
									
									
										<td>
											<spring:select id="initialEvaluationResultProfile" multiple="false" path="initialEvaluationResult.id"   class="form-control dropdown-toggle text-left " 
											oninvalid="this.setCustomValidity('Initial Evaluation Result must not be empty')" 
											required="required" oninput="this.setCustomValidity('')" >
						            </spring:select></td>
									</tr>
									<tr>
										<td><spring:label path="profileSharedCustomer">Profile Shared Customer<span style="color:red">*</span></spring:label></td>
									
										<td><spring:select class="form-control" onchange="changeTextBoxUpdate();" id="profileSharedCustomerProfile" path="profileSharedCustomer"
										oninvalid="this.setCustomValidity('Profile Shared Customer must not be empty')" 
											required="required" oninput="this.setCustomValidity('')" >
											<option value="">Select Profile Shared with Customer</option>
											<option value="Yes">Yes</option>
											<option value="No">No</option>
										</spring:select></td>
										
									</tr>
									<tr>
										<td><spring:label path="profileSharedCustomerDate">Profile Shared Customer Date<span style="color:red">*</span></spring:label></td>
									
									
										<td><spring:input class="form-control" path="profileSharedCustomerDate" id="profileSharedCustomerDateProfile"
											type="text" oninvalid="this.setCustomValidity('Profile Shared Customer Date must not be empty')" 
											required="required" oninput="this.setCustomValidity('')" /></td>
									</tr>
									<tr>
										<td><spring:label path="customerInterviewStatus">Customer Interview Status<span style="color:red">*</span></spring:label></td>
									
									
										<td>
											 <spring:select id="customerInterviewStatusProfile" multiple="false" path="customerInterviewStatus.id"   class="form-control dropdown-toggle text-left " 
											 oninvalid="this.setCustomValidity('Customer Interview Status must not be empty')" 
											required="required" oninput="this.setCustomValidity('')" >
						            </spring:select></td>
										
									</tr>
									
									<tr>
										<td><spring:label path="remarks">Remarks<span style="color:red">*</span></spring:label></td>
									
									
										<td><spring:input class="form-control" path="remarks" id="remarksProfile"
											type="text"  oninvalid="this.setCustomValidity('Remarks must not be empty')" 
											required="required" oninput="this.setCustomValidity('')"/></td>
									</tr>
									</table>
								</div>
								
								 <div style="background-color: #f1f1f1; margin-top: 2%; margin-left:90%;">
				<button
					style="margin-left: 5%; background-color: #b30000; color: white;"
					type="submit" class="btn btn-md button1" id="updateBtn" disabled>Update</button>
				
			</div>
							</spring:form>
						</div>
						</div>
				</div>
			</div>
			
			
	</div>
	</div>
<div class="w3-row" style="background-color:#b30000;text-align:center;padding:10px;margin-top:7px;color:white;height:38px;bottom:0px;align:bottom;">© Tech Mahindra Limited ©2018. All rights reserved.</div> 
		
</body>


</html>
