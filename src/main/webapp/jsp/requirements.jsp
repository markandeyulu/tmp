<!DOCTYPE html>
<html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>
<meta charset="utf-8" name="viewport" content="width=device-width, initial-scale=1">
<meta name="viewport" content="width=device-width, initial-scale=1">

<script	src="js/jquery.min.js"></script>
<script	src="js/jquery.dataTables.min.js"></script>
<script src="js/jquery.form-validator.min.js"></script>
<script src="js/jquery.table2excel.min.js"></script>
<script src="js/jquery-ui.min.js"></script>
<script src="js/jquery.canvasjs.min.js"></script>
<script src="js/jquery.easing.min.js"></script>
<script src="js/raphael.js"></script>
<script src="js/Treant.js"></script>
<script	src="js/bootstrap3-typeahead.min.js"></script>
<script	src="js/bootstrap.min.js"></script>
<script	src="js/bootstrap-multiselect.js"></script>
<script src="js/handlebars-v4.0.11.js"></script>
<script src="js/table.js"></script>
<script src="js/sample.js"></script>
<script src="js/location.js"></script>
<script src="js/dropdown.js"></script>
<script src="js/requirement.js"></script>
<script src="js/charts.js"></script>

<link rel="stylesheet"	href="css/jquery.dataTables.min.css">
<link rel="stylesheet"	href="css/bootstrap-3.3.6.min.css" />
<link rel="stylesheet"	href="css/bootstrap-multiselect.css" />
<link rel="stylesheet"	href="css/w3.css"/>
<link rel="stylesheet" type="text/css" href="css/iautResource.css" />
<link rel="stylesheet"	href="css/Treant.css"/>
<link rel="stylesheet"	href="css/collapsable.css"/>
<link rel="stylesheet"	href="css/perfect-scrollbar.css"/>
<link href="css/jquery-ui.css" rel="stylesheet"/>
<link href="css/font-awesome.min.css" rel="stylesheet"/>

<%
	response.setHeader("Pragma","no-cache"); 
	response.setHeader("Cache-Control","no-store"); 
	response.setHeader("Expires","0"); 
	response.setDateHeader("Expires",-1);
	response.setDateHeader("Expires", 0); //Causes the proxy cache to see the page as "stale"
	%>
<script src="js/jquery.selectlistactions.js"></script>
  <link rel="stylesheet" href="css/site.css">
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
var table=${requirementtableJson};
var locationJson=${locationJson};
var accountValues = ${accountValuesJson};
var projectValues = ${projectValuesJson};

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
	
$(document).ready(function () {
	
	$('#account1').change(function() {
		var account_id=$('#account1').val();
		$('#projectAdd option').remove();
		var projectListData=projectValues.projectsJson;
		if(projectListData==undefined || projectListData==""){
			projectListData = projectValues.projectListJson;
		}
		var g_projectListArray = [];	
	      $.each(projectListData, function(index) {
	      var g_item = [];
	      if ($("#account1").val() == (projectListData[index].accountId)) {
	    	  g_item.push(projectListData[index].projectId);	 
		      g_item.push(projectListData[index].projectName);	
		      g_projectListArray.push(g_item);	
	      }
	       
	                   		
	     });
	      $("#projectAdd").append('<option value="">Select Project</option>');  
	      
	  	$.each(g_projectListArray, function(i) {
	  		var g_projectListItem = g_projectListArray[i];
	  		$("#projectAdd").append('<option id="' + g_projectListItem[0] + '" value="' + g_projectListItem[0] + '">' + g_projectListItem[1] + '</option>');
	  	}); 
	  	
	  	
      
    });
	
	$('#accountNew').change(function() {
		var account_id=$('#accountNew').val();
		$('#projectNew option').remove();
		
		var projectListData=projectValues.projectsJson;
		var g_projectListArray = [];	
	      $.each(projectListData, function(index) {
	      var g_item = [];
	      if ($("#accountNew").val() == (projectListData[index].accountId)) {
	    	  g_item.push(projectListData[index].projectId);	 
		      g_item.push(projectListData[index].projectName);	
		      g_projectListArray.push(g_item);	
	      }
	       
	                   		
	     });
	      $("#projectNew").append('<option value="">Select Project</option>');  
	      
	  	$.each(g_projectListArray, function(i) {
	  		var g_projectListItem = g_projectListArray[i];
	  		$("#projectNew").append('<option id="' + g_projectListItem[0] + '" value="' + g_projectListItem[0] + '">' + g_projectListItem[1] + '</option>');
	  	}); 
	  	
	  	
      
    });
	
	$('#reqtable3 tbody').on('click', 'input[type="checkbox"]', function(e){
  	 	var row = $(this).closest('tr');
		row.addClass('selected');
  	});
 
	$('#deleteReqRow').click(function (){
		    var dataArr = [];
		    $.each($("#reqtable3 tr.selected"),function(){
		       dataArr.push($(this).find('td').eq(1).text());
		      
		    });
		    if(dataArr.length == 0){
				  alert("Please select atleast one Record to delete");
			  }else{
		    $.ajax({
	            type: 'POST',
	   		 url: "/ResourceManagementApp/requirementDelete.action",
	   		 data: { dataArr:dataArr},
	            success: function(e){ 		         
         		$('#reqMsg').html(e); 
         		window.location.href = "requirements.html";
         		 $.each( dataArr, function( key, value ) {
   		    	   $( "tr:contains('" + value + "')").each(function() {
   		    		   $('#reqtable3').dataTable().fnDeleteRow(this);
   		    	   });
   		    	 });
         		 $("#checkall").prop("checked", false);
 			}
	        });
			  }
	 });
	 $("#oppurtunityNew").change(function(){
		 var dropdown_id = $('#oppurtunityNew').val();
		 if(dropdown_id=="32"){
				document.getElementById("statusNew").value="98";
		}else if(dropdown_id=="33"){
			document.getElementById("statusNew").value="97";
		}else if(dropdown_id=="96"){
			document.getElementById("statusNew").value="99";
		}
			
	 });
	 
	 $("#reqtable3 #checkall").click(function () {
	        if ($("#reqtable3 #checkall").is(':checked')) {
	            $("#reqtable3 input[type=checkbox]").each(function () {
	                $(this).prop("checked", true);
	                $(this).parent().parent().addClass('selected');
	            });

	        } else {
	            $("#reqtable3 input[type=checkbox]").each(function () {
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
		 
		 if ( $('input[type="checkbox"]:checked').length == table.requirementtableJson.length ){
		        $("#checkall").prop('checked', true);
		        $(this).parent().parent().addClass('selected');
		    }
		 
	 });
	 
	  $("[data-toggle=tooltip]").tooltip();
	   
	    var updateMessage = ${updateMessage};	    
	      if (undefined != updateMessage) {
	            if (updateMessage == 1) {
                  $("#reqMsg").text('Successfully the requirement has been updated!!');
                  $("#reqMsg").fadeOut(10000);
	            } else if (updateMessage == 0) {
                  $("#reqMsg").text('Failure!! the requirement has not been updated!!');
                  $("#reqMsg").fadeOut(10000);
                  updateMessage = undefined;
	            }
	      }
	      else
	  		$("#updateReqMsg").text('Test');
	      
	var addMessage = ${addMessage};
	if (undefined != addMessage) {
		if (addMessage == 1) {
			$("#reqMsg").text('Successfully the requirement has been created!!');
			$("#reqMsg").fadeOut(10000);
		} else if (addMessage == 0) {
			$("#reqMsg").text('Failure!! the requirement has not been created!!');
			$("#reqMsg").fadeOut(10000);
			
		}
	
	}
	
	
	 $( "#target" ).submit(function( event ) {
		  var x= document.getElementById("lstBox2");
		  var txt = "";
		    var i;
		    for (i = 0; i < x.length; i++) {
		        txt = txt + x.options[i].value+ ',';
		    }
		    txt=txt.substring(0,txt.length-1);		    
	        $('#lstBox3').val(txt);
		 
		  form.requirementProfile.action.value="/requirementProfile";
		});
	 
	 $('#uploadReqFile').on('click', function(e){
 		
	    	var filename = $('input[type=file]').val().split('\\').pop();
	    	    $.ajax({
	                type: 'POST',           
	       		 url: "/ResourceManagementApp/uploadReqFile.action",
	       		 data: { "file": filename },
	       		    success: function(e){ 
	       		    	$('#fileId').html(e); 
	     			},
	     			complete:function(){
	     				document.getElementById("file").value = null;
	     				//$("#uploadFile").attr("disabled",false);
	     				$("#fileId").fadeOut(5000);
	     				$("#uploadReqFile").prop("disabled", false);
	     			} 
	            });
	    });
	 
	 $('#scheduleEmailId').on('click', function(e){
	 		
 	    $.ajax({
             type: 'POST',           
    		 url: "/ResourceManagementApp/scheduleService.action",
    		    success: function(e){ 
    		    	$('#fileId1').text(e); 
  			},error: function(e){ 
    		    	$('#fileId1').text(e); 
  			},
  			complete:function(){
  				//$("#uploadFile").attr("disabled",false);
  				$("#fileId1").fadeOut(5000);
  				$("#scheduleEmailId").prop("disabled", false);
  			} 
         });
	 });
	 
	 /* $("#btnReset").bind("click", function () {
	            $("#projectNew")[0].selectedIndex = 0;
	            $("#accountNew")[0].selectedIndex = 0;
	            location.reload(true);
	        }); */
	   
});

function adminRoleCheck() {
    if ($('#userRoleId').val() == "Admin") {
    	 $('#hidReq').show();
    	 $('#updateBtn').show();
    	 $('#assignProfile').show();
    }else{
    	$('#hidReq').hide();
    	$('#updateBtn').hide();
    	$('#assignProfile').hide();
    }
 }
function validateEmail(event){
    var reg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
    if (reg.test(event.value) == false) 
    {
        alert('Invalid Email Address');
        document.getElementById("intimatorEmail").focus();
        document.getElementById("intimatorEmailNew").focus();
        return false;        
    }
    return true;
}

</script>

</head>
<body onload="adminRoleCheck()" style="font-family: Verdana;">
<script type="text/x-handlebars-template" id="SampleView">
{{#each requirementtableJson}}

		<tr>
		<td> <input type="checkbox" class="checkbox" name="" value=""></td>
       
		<td href="#" data-toggle="modal" data-target="#squarespaceModal1" onclick="loadDetail('{{id}}');"><a>{{id}}</a></td>
        
        <td>{{criticality1}}</td>
		<td>{{primarySkill1}}</td>
		<td>{{location2}}</td>
		<td>{{intimationDate}}</td>
		<td>{{expectedDOJ}}</td>
        <td href="#" data-toggle="modal" data-target="#squarespaceModal2" onclick="loadProfileDetail('{{id}}');"><a>{{profileshared}}</a></td>
        <td>{{internalEvaluation1}}</td>
        <td href="#" data-toggle="modal" data-target="#mappingmodel" onclick="loadShortlistedProfileDetail('{{id}}');loadShortlistedProfile('{{id}}')"><a>{{customershortlisted}}</a></td>
        <td>{{status1}}</td>
        <td>{{oppurtunitystatus}}</td>
		
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
				<ul class="nav navbar-nav"
					style="font-size: 16px; background-color: #b30000; width: 100%; height: 45px;">
					<li><a id="black" href="dashboard.html"
						style="margin-right: 0px; color: white; height: 43px;">Dashboard</a></li>
					<li class="active"><a href="#"
						style="margin-right: 0px; background-color: white; color: black; height: 45px;">Requirements</a></li>
					<li><a id="black" href="profiles.html"
						style="margin-right: 0px; color: white; height: 43px;">Profiles</a></li>
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

		<div class="mainsec2">
			<h2 style="margin-bottom: 1%; margin-left: 3%; font-size: 30px;">Requirements</h2>

			<div class="row col-md-12" style="">
			<spring:form name='RequirementForm' action="requirements" method='POST' modelAttribute="requirements">
				<div class="col-md-3 drop3">
					<div class="form-group">
						<spring:select id="framework" name="Location[]" multiple="true" path="location1" class="form-control">
						</spring:select>
					</div>
				</div>
				<div class="col-md-3 drop3">

					<div class="form-group">
						<spring:select id="framework1" name="Account[]" multiple="true" path="account1" class="form-control">

						</spring:select>
					</div>
				</div>
				<div class="col-md-2 form-group subbut1">
					<button id="clickfunction" style="background-color: #b30000; color: white;" type="submit" class="btn btn-md" name="submit" value="Submit" >Submit</button>
				</div>
				</spring:form>
				<br />
			</div>
			<!-- Icon Cards-->
			<div class=" row w3-container" id="blockref">

				<div id="rcorners5" class="w3-panel w3-card chcol_1"
					style="">
					<h6>Lead Generation</h6>
					<p></p>
					<!-- <div id="hoverview" class="w3-third w3-center w3-card" style="background:#9b0f23;"> 
      <button data-toggle="modal" data-target="#Modelleadgeneration" class="w3-button w3-black w3-round-xxlarge" style="margin-top:10px;">View</button> 
    </div> -->
					<div style="height: 50%;">
						<h1 id="dvalue1" style="float: right; margin-top: 18%;"></h1>
					</div>

				</div>
				<div id="rcorners5" class="w3-panel w3-card chcol_2" style="">
					<h6>Profile Sourcing</h6>
					<!-- <div id="hoverview" class="w3-third w3-center w3-card" style="background:#9b0f23;"> 
      <button data-toggle="modal" data-target="#Modelleadgeneration" class="w3-button w3-black w3-round-xxlarge" style="margin-top:10px;">View</button> 
    </div> -->
					<div>
						<h1 id="dvalue2" style="float: right; margin-top: 18%;"></h1>
					</div>
				</div>
				<div id="rcorners5" class="w3-panel w3-card chcol_3" style="">
					<h6>Technical Evaluations</h6>
					<p></p>
					<span>
						<h1 id="dvalue3" style="float: right; margin-top: 18%;"></h1>
					</span>
				</div>
				<div id="rcorners5" class="w3-panel w3-card chcol_4" style="">
					<h6>Customer Evaluations</h6>
					<p></p>
					<span>
						<h1 id="dvalue4" style="float: right; margin-top: 18%;"></h1>
					</span>
				</div>
				<div id="rcorners5" class="w3-panel w3-card chcol_5" style="">
					<h6>Offer Processing</h6>
					<p></p>
					<span>
						<h1 id="dvalue5" style="float: right; margin-top: 18%;"></h1>
					</span>
				</div>
				<div id="rcorners5" class="w3-panel w3-card chcol_6" style="">
					<h6>Onboarded</h6>
					<p></p>
					<span>
						<h1 id="dvalue6" style="float: right; margin-top: 18%;"></h1>
					</span>
				</div>
				<div id="rcorners5" class="w3-panel w3-card" style="display:inline-block;">
								<h6>Hold Opportunity</h6>
								<p></p>

								<span>
									<h1 id="dvalue7" style="float: right; margin-top: 18%;"></h1>
								</span>
							</div>
							<div id="rcorners5" class="w3-panel w3-card" style="display:inline-block;">
								<h6>Abandoned Opportunity</h6>
								<p></p>

								<span>
									<h1 id="dvalue8" style="float: right; margin-top: 18%;"></h1>
								</span>
							</div>
							<div id="rcorners5" class="w3-panel w3-card" style="display:inline-block;">
								<h6>Lost Opportunity</h6>
								<p></p>

								<span>
									<h1 id="dvalue10" style="float: right; margin-top: 18%;"></h1>
								</span>
							</div>
				<div id="rcorners5" class="w3-panel w3-card" style="background-color:white; color:#0873a5;">
					<h3>Total</h3>
					<p></p>
					<span>
						<h1 id="dvalue9" style="float: right; margin-top: 18%;"></h1>
					</span>
				</div>
			</div>
			
			<div id="hidReq" style="background-color: #f1f1f1; margin-top: 2%; display:none;">
				<button
					style="margin-left: 5%; background-color: #b30000; color: white;"
					type="button" class="btn btn-md button1" data-toggle="modal"
					data-target="#squarespaceModal">Add Requirement</button>
					<Button name="submit" id="deleteReqRow" class="btn btn-md button1" style="background-color:#b30000;color:white;" type="submit">Delete</Button>
					<button	style="margin-left: 0%; background-color: #b30000; color: white;"
					type="button" class="btn btn-md button1" data-toggle="modal"
					data-target="#uploadModal">Bulk Upload</button>
					
					<!-- <Button name="barChart" id="barChart" data-target="#barChartModal" data-toggle="modal" class="btn btn-md button1" style="background-color:#b30000;color:white;" type="button">Accounts-Head Count Bar Chart</Button> -->
					
					<!-- <Button name="pieChart" id="pieChart" data-target="#pieChartModal" data-toggle="modal" class="btn btn-md button1" style="background-color:#b30000;color:white;" type="button">Skill-set Pie Chart</Button>
					 -->
					<Button name="treeChart" id="treeChart" data-target="#treeChartModal" data-toggle="modal" class="btn btn-md button1"  style="background-color:#b30000;color:white;display:none" type="button">Associate-Hierarchy Tree Chart</Button>
					
					<button	style="margin-left: 0%; background-color: #b30000; color: white;"
					type="button" class="btn btn-md button1" data-toggle="modal"
					data-target="#scheduleModal">Scheduler</button>
					
					<!-- <div id="addReqMsg" style="display: inline-block; margin-left:10%; font-weight: bold">
                    </div>
					<div id="deleteReqMsg" style="display: inline-block; margin-left:10%; font-weight: bold">
					</div>
					<div id="updateReqMsg" style="display: inline-block; margin-left:2%; font-weight: bold">
					</div> -->
					<div id="reqMsg" style="display: inline-block; margin-left:10%; font-weight: bold">
                    </div>
				
				<div class="modal fade" id="treeChartModal" tabindex="-1" role="dialog"
					aria-labelledby="modalLabel" aria-hidden="true">
				<div class="modal-dialog" style="width: 1000px ; font-size: 12px;">
					<div class="modal-content">
						<div class="modal-header" style="background-color: #b30000;">
							<button type="button" class="close" data-dismiss="modal"
								style="background-color: white;" onClick="window.location.reload();">
								<span aria-hidden="true">×</span><span class="sr-only">Close</span>
							</button>
							<h3 class="modal-title" id="lineModalLabel" style="color: white;">TREE</h3>
						</div>
						<div class="modal-body">
							
									<div class="chart Treant Treant-loaded" id="collapsable-chart"> 
							
						</div>
					</div>
					</div>
				</div>	
				</div>
			<!-- <div class="modal fade" id="pieChartModal" tabindex="-1" role="dialog"
					aria-labelledby="modalLabel" aria-hidden="true">
				<div class="modal-dialog" style="height: 600px; width: 800px ; font-size: 12px;">
					<div class="modal-content">
						<div class="modal-header" style="background-color: #b30000;">
							<button type="button" class="close" data-dismiss="modal"
								style="background-color: white;" onClick="window.location.reload();">
								<span aria-hidden="true">×</span><span class="sr-only">Close</span>
							</button>
							<h3 class="modal-title" id="lineModalLabel" style="color: white;">PIE</h3>
						</div>
						<div class="modal-body">
								<div id="resizable">
									<div id="chartContainer" style="height: 400px; width: 700px;"></div> 
								</div>
						</div>
					</div>
			</div>
			</div> -->
			<!-- <div class="modal fade" id="barChartModal" tabindex="-1" role="dialog"
					aria-labelledby="modalLabel" aria-hidden="true">
				<div class="modal-dialog" style="height: 600px; width: 800px ; font-size: 12px;">
					<div class="modal-content">
						<div class="modal-header" style="background-color: #b30000;">
							<button type="button" class="close" data-dismiss="modal"
								style="background-color: white;" onClick="window.location.reload();">
								<span aria-hidden="true">×</span><span class="sr-only">Close</span>
							</button>
							<h3 class="modal-title" id="lineModalLabel" style="color: white;">BAR</h3>
						</div>
						<div class="modal-body">
								<div id="resizable">
									<div id="chartContainer1" style="height: 400px; width: 700px;"></div> 
								</div>
						</div>
					</div>
			</div>
			</div> -->
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
												<td><Button type="button" id="uploadReqFile" name="Upload"
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
		
		<div class="modal fade" id="scheduleModal" tabindex="-1" role="dialog"
			aria-labelledby="modalLabel" aria-hidden="true">
			<div class="modal-dialog" style="width: 80%; font-size: 12px;">
				<div class="modal-content">
					<div class="modal-header" style="background-color: #b30000;">
						<button type="button" id="reload" class="close" data-dismiss="modal"
							style="background-color: white;">
							<!-- <span aria-hidden="true">×</span><span class="sr-only" onclick="window.location.reload()">Close</span> -->
							<a class="button" onclick="window.location.reload()" href="#">x</a>
							
						</button>
						<h3 class="modal-title" id="lineModalLabel" style="color: white;">Scheduling Email</h3>
					</div>
					<div class="modal-body">
						<div class="container" id="modelreload">
							<div style="width: 60%; margin-left: 8%;">
								<div class="card-body">
									<form enctype="multipart/form-data" >
										<div class="form-group">
											<div id="fileId1"></div>
											<tr>
												<td><Button type="button" id="scheduleEmailId" name="schedule"
														class="btn btn-md button1"
														style="background-color: #cc0000; color: white; float: right; margin-right: 7%;"
														type="submit">Click to Schedule Email</Button></td>
														
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
		
		<div class="modal fade" id="mappingmodel" tabindex="-1" role="dialog" aria-labelledby="modalLabel" aria-hidden="true">
  <div class="modal-dialog" style="width:60%;">
	<div class="modal-content">
		<div class="modal-header" style="background-color: #b30000;">
						<button type="button" id="reload" class="close" data-dismiss="modal"
							style="background-color: white;">
							<span aria-hidden="true">×</span><span class="sr-only">Close</span>
						</button>
			<h3 class="modal-title" id="lineModalLabel" style="color: white;">Customer Shortlisted Profile(s)</h3>
		</div>
		<div class="modal-body">
            <!-- content goes here -->
         <spring:form name='requirementProfile'  id="target"  action="requirementProfile" method='POST' modelAttribute="requirements">  
			<div class="container" style="">			
       		<div class="row style-select">
       		
			<div class="col-md-12">
        
				<div class="subject-info-box-1">
				
					<label>Available Profiles</label>
					
					<spring:select  multiple="true" class="form-control" path="assignedProfiles" name="assignedProfiles" id="lstBox1" style="border-top-width:2px;padding-top:8px;padding-bottom:8px;">
					</spring:select>
				</div>

				<div class="subject-info-arrows text-center">
					<br /><br /><br />
					<input type='button' id='btnAllRight' value=">>" class="btn btn-default" /><br />
					<input type='button' id='btnRight' value=">" class="btn btn-default" /><br />
					<input type='button' id='btnLeft' value="<" class="btn btn-default" /><br />
					<input type='button' id='btnAllLeft' value="<<" class="btn btn-default" />
				</div>

				<div class="subject-info-box-2">
					<label>Profiles You Have Assigned</label>
					<spring:select  multiple="true" class="form-control" name="shortlistedProfiles" path="shortlistedProfiles" id="lstBox2" style="border-top-width:2px;padding-top:8px;padding-bottom:8px;">
					</spring:select> 
						<spring:input class="form-control" id="lstBox3" path="shortlistedProfiles" type="hidden"/>
				</div>

				<div class="clearfix">
				<spring:input class="form-control" id="requirementIdMapping" path="id" type="hidden"/>
				 
				    </div>
			</div>
		</div>
		
		 <div id="assignProfile" style="padding-left:320px">
	     <button id="clickfunction" style=" background-color:#b30000;color:white;margin-top:5%;" type="submit" class="btn btn-md button1" name="submit" value="Submit">Submit</button>
	     </div>

			</div>
			</spring:form>
	</div>
  </div>

</div>
	</div>
		<input type="hidden" id="userRoleId" name="userRoleId" value="${userRole}"/>
		
		<div class="modal fade" id="squarespaceModal" tabindex="-1"
				role="dialog" aria-labelledby="modalLabel" aria-hidden="true">
				<div class="modal-dialog" style="width: 80%; font-size: 12px;">
					<div class="modal-content">
						<div class="modal-header" style="background-color: #b30000;">
							<button type="button" class="close" data-dismiss="modal"
								style="background-color: white;" onClick="window.location.reload();">
								<span aria-hidden="true">×</span><span class="sr-only">Close</span>
							</button>
							<h3 class="modal-title" id="lineModalLabel" style="color: white;">Add
								Requirement</h3>
						</div>
						<div class="modal-body">
							<div class="container">
							<spring:form name='addRequirementForm' action="addrequirement"
								method="POST" modelAttribute="requirements">
									<div class="row">
										<div class="col-25">
										<spring:label path="band">Band<span style="color:red">*</span></spring:label>
										</div>
										<div class="col-75">
										<spring:select required="required" multiple="false" path="band" id="band"
											class="form-control" oninvalid="this.setCustomValidity('Please select band')" 
										oninput="this.setCustomValidity('')" >
											<option value="">Select Band</option>
											<option value="U1">U1</option>
											<option value="U2">U2</option>
											<option value="U3">U3</option>
											<option value="U4">U4</option>
											<option value="P1">P1</option>
											<option value="P2">P2</option>
											<option value="E1">E1</option>
										</spring:select>
										</div>
									</div>
									<div class="row">
										<div class="col-25">
										<spring:label path="account1">Customer Name<span style="color:red">*</span></spring:label>
										</div>
										<div class="col-75">
										<%-- <spring:select required="required" class="form-control" id="account1" path="account1"
										oninvalid="this.setCustomValidity('Please select Customer name')" 
										oninput="this.setCustomValidity('')" >
										</spring:select> --%>
										<spring:input type="text" class="form-control" id="account1" path="account1" placeholder="Enter Customer Name.." />	
										</div>
									</div>
									 <div class="row">
										<div class="col-25">
										<spring:label path="projectAdd">Project Name<span style="color:red">*</span></spring:label>
										</div>
										<div class="col-75">
										<%-- <spring:select required="required" class="form-control" id="projectAdd" path="projectAdd"
										oninvalid="this.setCustomValidity('Project name must not be empty')" 
										oninput="this.setCustomValidity('')" >
										
										</spring:select> --%>
										<spring:input type="text" class="form-control" id="projectAdd" path="projectAdd" placeholder="Enter Project Name.." />	
										</div>
										
										<%-- <div class="col-75">
										<spring:input class="form-control" path="projectAdd" id="projectAdd" oninvalid="this.setCustomValidity('Project name must not be empty')" 
										oninput="this.setCustomValidity('')"
											type="text" placeholder="Enter Project Name.." required="required"/>
										</div> --%>
									</div> 
									<div class="row">
										<div class="col-25">
										<spring:label path="quantity">Quantity<span style="color:red">*</span></spring:label>
										</div>
										<div class="col-75">
										<spring:input class="form-control" path="quantity" oninvalid="this.setCustomValidity('Quantity must not be empty')"
										 oninput="this.setCustomValidity('')"
											id="quantity" onkeypress='validate(event)' type="text"
											placeholder="Enter Quantity.." required="required"/>
										</div>
									</div>
									<div class="row">
										<div class="col-25">
										<spring:label path="expectedDOJ">Resource Required by Date<span style="color:red">*</span></spring:label>
										</div>
										<div class="col-75">
										<spring:input class="form-control" path="expectedDOJ"
											id="start_datepicker" type="text" 
											placeholder="Enter Expected DOJ.." required="required"/>
										</div>
									</div>
									<div class="row">
										<div class="col-25">
										<spring:label path="requirementTypeAdd">Type of Requirement<span style="color:red">*</span></spring:label>
										</div>
										<div class="col-75">
										<spring:select required="required" class="form-control" id="requirementTypeAdd" path="requirementTypeAdd"
										oninvalid="this.setCustomValidity('Please select Type of Requirement')" 
										oninput="this.setCustomValidity('')" >
											<!-- <option value="">Select Type of Requirement</option>
											<option value="pso">PSO</option>
											<option value="confirmed">Confirmed</option>
											<option value="replacement">Replacement</option>
											<option value="forecast">Forecast</option> -->
										</spring:select>
										</div>
									</div>
									<div class="row">
										<div class="col-25">
										<spring:label path="so">SO<span style="color:red">*</span></spring:label>
									</div>
									<div class="col-75">
										<spring:input type="text" class="form-control" oninvalid="this.setCustomValidity('SO must not be empty')" 
										oninput="this.setCustomValidity('')"
											id="so" path="so" onkeypress='validate(event)'
											placeholder="Enter SO.." required="required"/>
									</div>
								</div>
								<div class="row">
									<div class="col-25">
										<spring:label path="jo">JO<span style="color:red">*</span></spring:label>
										</div>
										<div class="col-75">
										<spring:input type="text" class="form-control" oninvalid="this.setCustomValidity('JO must not be empty')" 
										oninput="this.setCustomValidity('')"
											id="jo" path="jo" onkeypress='validate(event)'
											placeholder="Enter JO.." required="required"/>
									</div>
								</div>
								<div class="row">
									<div class="col-25">
										<spring:label path="location1">Location<span style="color:red">*</span></spring:label>
									</div>
									<div class="col-75">
										<spring:select required="required" class="form-control" id="location1"
											path="location1" oninvalid="this.setCustomValidity('Please select location')" 
										oninput="this.setCustomValidity('')" >
												<!-- <option value="">Select Location</option>
												<option value="onsite">Onsite</option>
												<option value="offshore">Offshore</option> -->
										</spring:select>
										</div>
									</div>
									<div class="row">
										<div class="col-25">
										<spring:label path="city">City<span style="color:red">*</span></spring:label>
										</div>
										<div class="col-75">
										<spring:input class="form-control" type="text" id="city" oninvalid="this.setCustomValidity('City must not be empty')" 
										oninput="this.setCustomValidity('')"
											path="city" placeholder="Enter City.." required="required"/>
										</div>
									</div>
									<div class="row">
										<div class="col-25">
										<spring:label path="primarySkill1">Competency/Domain<span style="color:red">*</span></spring:label>
									</div>
									<div class="col-75">
										<%-- <spring:input class="form-control" type="text" oninvalid="this.setCustomValidity('Competency/Domain must not be empty')" 
										oninput="this.setCustomValidity('')"
											id="primarySkill1" path="primarySkill1"
											placeholder="Enter Competency/Domain.." required="required"/> --%>
											
										<%--  <spring:select id="primarySkill1" multiple="false" path="primarySkill1"   class="form-control dropdown-toggle text-left " Style="width:100%"
                						  oninvalid="this.setCustomValidity('Please select Primary Skill')" required="required"	oninput="this.setCustomValidity('')">
										  </spring:select> --%>
										   	<spring:input type="text" class="form-control" id="primarySkill1" path="primarySkill1" placeholder="Enter Primary Skill.." />	
										</div>
								</div>
								<div class="row">
									<div class="col-25">
										<spring:label path="skillCategoryAdd1">Skill Category 1<span style="color:red">*</span></spring:label>
										</div>
										<div class="col-75">
										<%--  <spring:select id="skillCategoryAdd1" multiple="false" path="skillCategoryAdd1"   class="form-control dropdown-toggle text-left " Style="width:100%"
                						  oninvalid="this.setCustomValidity('Please select Skill Category')" required="required"	oninput="this.setCustomValidity('')">
										  </spring:select>	 --%>
										  
										  	<spring:input type="text" class="form-control" id="skillCategoryAdd1" path="skillCategoryAdd1" placeholder="Enter Skill Category.." />
										</div>
									</div>
									<div class="row">
										<div class="col-25">
										<spring:label path="skillCategoryAdd2">Skill Category 2</spring:label>
										</div>
										<div class="col-75">
										<spring:input type="text" class="form-control"
											id="skillCategoryAdd2" path="skillCategoryAdd2"
											placeholder="Enter Skill Category 2.." />
										</div>
									</div>
									<div class="row">
										<div class="col-25">
										<spring:label path="skillCategoryAdd3">Skill Category 3</spring:label>
										</div>
										<div class="col-75">
										<spring:input class="form-control" type="text"
											id="skillCategoryAdd3" path="skillCategoryAdd3"
											placeholder="Enter Skill Category 3.." />
										</div>
									</div>
									<div class="row">
										<div class="col-25">
										<spring:label path="skillCategoryAdd4">Skill Category 4</spring:label>
										</div>
										<div class="col-75">
										<spring:input class="form-control" type="text"
											id="skillCategoryAdd4" path="skillCategoryAdd4"
											placeholder="Enter Skill Category 4.." />
										</div>
									</div>
									<div class="row">
										<div class="col-25">
										<spring:label path="jobDescription">Job Description<span style="color:red">*</span></spring:label>
										</div>
										<div class="col-75">
										<spring:textarea id="jobDescription" class="form-control"
											path="jobDescription" required="required" oninvalid="this.setCustomValidity('Job description must not be empty')" 
										oninput="this.setCustomValidity('')"
												placeholder="Write Job Description here.."
											style="height:200px"></spring:textarea>
										</div>
									</div>
									<div class="row">
										<div class="col-25">
										<spring:label path="ibg_cdg">From IBG/CDG<span style="color:red">*</span></spring:label>
										</div>
										<div class="col-75">
										<spring:input type="text" class="form-control" id="ibg_cdg" oninvalid="this.setCustomValidity('IBG/CDG must not be empty')" 
										oninput="this.setCustomValidity('')"
											path="ibg_cdg" placeholder="Enter IBG/CDG.." required="required"/>
										</div>
									</div>
									<div class="row">
										<div class="col-25">
										<spring:label path="ibu_cdu">From IBU/CDU<span style="color:red">*</span></spring:label>
										</div>
										<div class="col-75">
										<spring:input type="text" class="form-control" id="ibu_cdu" oninvalid="this.setCustomValidity('IBU/CDU must not be empty')" 
										oninput="this.setCustomValidity('')"
											path="ibu_cdu" placeholder="Enter IBU/CDU.." required="required"/>
										</div>
									</div>
									<div class="row">
										<div class="col-25">
										<spring:label path="yearExperience">Years of Experience<span style="color:red">*</span></spring:label>
										</div>
										<div class="col-75">
										<spring:input type="text" class="form-control"
											id="yearExperience" path="yearExperience"
												onkeypress='validate(event)' oninvalid="this.setCustomValidity('Years of experience must not be empty')" 
										oninput="this.setCustomValidity('')"
											placeholder="Enter Years of exp..." required="required"/>
										</div>
									</div>
									<div class="row">
									<div class="col-25">
										<spring:label path="billingRate">Billing Rate<span style="color:red">*</span></spring:label>
									</div>
									<div class="col-75">
										<spring:input type="text" class="form-control" oninvalid="this.setCustomValidity('Billing rate must not be empty')" 
										oninput="this.setCustomValidity('')"
											id="billingRate" path="billingRate" onkeypress='validate(event)'
											placeholder="Enter Billing Rate in USD.." required="required" maxlength="6"/>
									</div>
								</div>
								<div class="row">
									<div class="col-25">
										<spring:label path="projectDuration">Project Duration in Months<span style="color:red">*</span></spring:label>
									</div>
									<div class="col-75">
										<spring:input type="text" class="form-control" oninvalid="this.setCustomValidity('Project duration must not be empty')" 
										oninput="this.setCustomValidity('')"
											id="projectDuration" path="projectDuration" onkeypress='validate(event)'
											placeholder="Enter Project Duration in Months.." required="required"/>
									</div>
								</div>
								<div class="row">
									<div class="col-25">
										<spring:label path="pid_crmid_so">PID / CRM ID to raise the SO<span style="color:red">*</span></spring:label>
									</div>
									<div class="col-75">
										<spring:input type="text" class="form-control" oninvalid="this.setCustomValidity('PID/CRM ID must not be empty')" 
										oninput="this.setCustomValidity('')" maxlength="15"
											id="pid_crmid_so" path="pid_crmid_so" 
											placeholder="Enter PID / CRM ID to raise the SO.." required="required"/>
									</div>
								</div>
								<div class="row">
									<div class="col-25">
										<spring:label path="criticality1">Criticality<span style="color:red">*</span></spring:label>
									</div>
									<div class="col-75">
										<spring:select required="required" class="form-control" id="criticality1" path="criticality1"
										oninvalid="this.setCustomValidity('Please select Criticality')" 
										oninput="this.setCustomValidity('')" >
											<!-- <option value="">Select Criticality</option>
											<option value="critical">Critical</option>
											<option value="high">High</option>
											<option value="medium">Medium</option>
											<option value="low">Low</option> -->
										</spring:select>
									</div>
								</div>
								<div class="row">
									<div class="col-25">
										<spring:label path="intimationDate">Intimation Date<span style="color:red">*</span></spring:label>
									</div>
									<div class="col-75">
										<spring:input type="text" class="form-control" 
											id="intimationDate" path="intimationDate"
											placeholder="Enter Intimation Date.." required="required"/>
									</div>
								</div>
								<div class="row">
									<div class="col-25">
										<spring:label path="intimatedBy">Intimation By<span style="color:red">*</span></spring:label>
									</div>
									<div class="col-75">
										<spring:input type="text" class="form-control"
											id="intimatedBy" path="intimatedBy" oninvalid="this.setCustomValidity('Initimated By must not be empty')" 
										oninput="this.setCustomValidity('')"
											placeholder="Enter Intimation By.." required="required"/>
									</div>
								</div>
								<div class="row">
									<div class="col-25">
										<spring:label path="intimatorEmail">EmailId of Intimator<span style="color:red">*</span></spring:label>
									</div>
									<div class="col-75">
										<spring:input type="text" class="form-control"
											id="intimatorEmail" path="intimatorEmail" oninvalid="this.setCustomValidity('Initimater email id must not be empty')" 
										oninput="this.setCustomValidity('')" onChange="validateEmail(this);"
											placeholder="Enter EmailId of Intimator.." required="required"/>
									</div>
								</div>
									<div class="row">
									<div class="col-25">
										<spring:label path="intimationModeAdd">Mode of Intimation<span style="color:red">*</span></spring:label>
									</div>
									<div class="col-75">
										<spring:select required="required" class="form-control" id="intimationModeAdd" path="intimationModeAdd"
										oninvalid="this.setCustomValidity('Please select Mode of Intimation')" 
										oninput="this.setCustomValidity('')" >
											<!-- <option value="">Select Mode of Intimation</option>
											<option value="mail">Mail</option>
											<option value="tool">Tool</option>
											<option value="telephonic">Telephonic</option> -->
										</spring:select>
									</div>
								</div>
									<%-- <div class="row">
									<div class="col-25">
										<spring:label path="actualClosureDate">Planned Closure Date</spring:label>
									</div>
									<div class="col-75">
										<spring:input type="text" class="form-control"
											id="actualClosureDate" path="actualClosureDate"
											placeholder="Enter Actual/Projected Closure Date.."/>
									</div>
								</div> --%>
								
								<div class="row">
									<Button name="submit" class="btn btn-md button1"
										style="background-color: #cc0000; color: white; float:right;margin-right:7%;" type="submit">Submit</Button>
									<!--  <button style="background-color:#b30000;color:white;float:right;margin-right:7%;" type="submit" class="btn btn-md button1">Submit</button> -->
								</div>
							</spring:form>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="modal fade" id="squarespaceModal1" tabindex="-1"
				role="dialog" aria-labelledby="modalLabel" aria-hidden="true">
				<div class="modal-dialog" style="width: 90%;">
					<div class="modal-content">
						<div class="modal-header" style="background-color: #b30000;">
						<button type="button" id="btnReset" class="close" data-dismiss="modal"
							style="background-color: white;">
							<span aria-hidden="true">×</span><span class="sr-only">Close</span>
						</button>
							<h3 class="modal-title" id="lineModalLabel" style="color: white;">Update Requirement</h3>
						</div>
						<div class="modal-body">
							<!-- content goes here -->
				<div class="container" style="overflow-x:scroll;">
				<spring:form name='RequirementForm1' action="requirementsById" method='POST' modelAttribute="requirements">
				 <div style="width:50%" class="row1">
                    <table>
                        <tr>
                          <td><spring:label path="id">Ref No#</spring:label> </td>
                          <td><spring:input class="form-control" id="id" path="id" type="text" readonly="true"/></td>
                        </tr>      
                       <tr>
                          <td><spring:label path="skillCategory">Skill Category<span style="color:red">*</span></spring:label></td>
                          <td>
                          <%-- <spring:select id="skillCategoryNew" multiple="false" path="skillCategory.id" class="form-control dropdown-toggle text-left " Style="width:100%"
                          oninvalid="this.setCustomValidity('Please select Skill Category')" 
						  required="required"	oninput="this.setCustomValidity('')" >
						</spring:select> --%>
						 	<spring:input type="text" class="form-control" id="skillCategoryNew" path="skillCategory" placeholder="Enter Skill Category.." />
						</td>
						
                    </tr>
                    <tr>
                          <td><spring:label path="jobDescription">Job Description<span style="color:red">*</span></spring:label> </td>
                          <td><spring:input class="form-control" id="jobDescriptionNew" path="jobDescription" type="text" oninvalid="this.setCustomValidity('Job description must not be empty')" 
							required="required"			oninput="this.setCustomValidity('')" /></td>
                        </tr>      
                       <tr>
                          <td><spring:label path="city">City<span style="color:red">*</span></spring:label></td>
                          <td><spring:input class="form-control" id="cityNew" path="city" type="text" 
                          oninvalid="this.setCustomValidity('City must not be empty')" 
								required="required"		oninput="this.setCustomValidity('')"/></td>
                    </tr>
                     <tr>
                          <td><spring:label path="intimationDate">Intimation Date<span style="color:red">*</span></spring:label> </td>
                          <td><spring:input class="form-control" id="intimationDateNew" path="intimationDate" type="text" required="required"/></td>
                        </tr>      
                       <tr>
                          <td><spring:label path="intimatorEmail">Intimator Email<span style="color:red">*</span></spring:label></td>
                          <td><spring:input class="form-control" id="intimatorEmailNew" path="intimatorEmail" type="text" 
                          oninvalid="this.setCustomValidity('Initimater email id must not be empty')" 
						required="required"		onChange="validateEmail(this);"	oninput="this.setCustomValidity('')"/></td>
                    </tr>
                    <tr>
                          <td><spring:label path="requirementType">Requirement Type<span style="color:red">*</span></spring:label> </td>
                          <td>
                          <spring:select id="requirementTypeNew" multiple="false" path="requirementType.id" class="form-control dropdown-toggle text-left " Style="width:100%"
                          oninvalid="this.setCustomValidity('Please select type of requirement')" 
								required="required"		oninput="this.setCustomValidity('')">
						</spring:select></td>
                        </tr>
                        <tr>
                          <td><spring:label path="plannedClosureDate">Planned Closure Date</spring:label></td>
                          <td><spring:input class="form-control" id="plannedClosureDate" path="plannedClosureDate" type="text"/></td>
                    </tr>      
                       <tr>
                          <td><spring:label path="actualClosureDate">Actual Closure Date</spring:label></td>
                          <td><spring:input class="form-control" id="actualClosureDateNew" path="actualClosureDate" type="text"/></td>
                    </tr>
                    <tr>
                  <td><spring:label path="expectedDOJ">Customer Expected DOJ<span style="color:red">*</span></spring:label></td>
                  <td><spring:input class="form-control" id="expectedDOJ" path="expectedDOJ" type="text" required="required"	/></td>
              </tr>
                     <%-- <tr>
                          <td><spring:label path="jo">JO<span style="color:red">*</span></spring:label> </td>
                          <td><spring:input class="form-control" id="joNew" path="jo" type="text" oninvalid="this.setCustomValidity('JO must not be empty')" 
								required="required"		oninput="this.setCustomValidity('')" onkeypress='validate(event)'/></td>
                        </tr>  --%>     
                       <tr>
                          <td><spring:label path="activityOwner">Activity Owner</spring:label></td>
                          <td><spring:input class="form-control" id="activityOwner" path="activityOwner" type="text" oninput="this.setCustomValidity('')"/></td>
                    </tr>
                    <tr>
                          <td><spring:label path="actualOwner">Actual Owner</spring:label> </td>
                          <td><spring:input class="form-control" id="actualOwner" path="actualOwner" type="text" oninput="this.setCustomValidity('')"/></td>
                        </tr>      
                       <tr>
                          <td><spring:label path="createdOn">Created On</spring:label></td>
                          <td><spring:input class="form-control" id="createdOn" path="createdOn" type="text" readonly="true"/></td>
                    </tr>
                      <tr>
                          <td><spring:label path="shortlistedProfile_id">Shortlisted Profile Id</spring:label></td>
                          <td><spring:input class="form-control" id="shortlistedProfile_idNew" path="shortlistedProfile_id.name" type="text"  readonly="true"/>
                          <spring:input class="form-control" id="shortlistedProfile_idNewId" path="shortlistedProfile_id.id" type="hidden" /></td>
                    </tr>
                     <tr>
                          <td><spring:label path="band">Band<span style="color:red">*</span></spring:label></td>
                          <td><spring:select required="required" multiple="false" path="band" id="bandNew" 
											class="form-control" oninvalid="this.setCustomValidity('Please select band')" 
										oninput="this.setCustomValidity('')" >
											<option value="">Select Band</option>
											<option value="U1">U1</option>
											<option value="U2">U2</option>
											<option value="U3">U3</option>
											<option value="U4">U4</option>
											<option value="P1">P1</option>
											<option value="P2">P2</option>
											<option value="E1">E1</option>
										</spring:select>
										</td>
                          
                    </tr>
                     <tr>
                          <td><spring:label path="quantity">Quantity<span style="color:red">*</span></spring:label></td>
                          <td><spring:input class="form-control" id="quantityNew" path="quantity" type="text" 
                          oninvalid="this.setCustomValidity('Quantity must not be empty')"
								required="required"			 oninput="this.setCustomValidity('')" onkeypress='validate(event)'/></td>
                    </tr>
                     <tr>
                          <td><spring:label path="projectDuration">Project Duration<span style="color:red">*</span></spring:label></td>
                          <td><spring:input class="form-control" id="projectDurationNew" path="projectDuration" type="text" 
                          oninvalid="this.setCustomValidity('Project duration must not be empty')" 
							required="required"			oninput="this.setCustomValidity('')" onkeypress='validate(event)'/></td>
                    </tr> 
                    <tr>
                          <td><spring:label path="updatedBy">Updated By</spring:label></td>
                          <td><spring:input class="form-control" id="updatedByNew" path="updatedBy" type="text" readonly="true"/></td>
                    </tr>
                     <tr>
                  <td><spring:label path="oppurtunityStatus">Opportunity Status<span style="color:red">*</span></spring:label></td>
                  <td><spring:select id="oppurtunityNew" multiple="false" path="oppurtunityStatus.id"   class="form-control dropdown-toggle text-left " Style="width:100%"
                  oninvalid="this.setCustomValidity('Opportunity Status must not be empty')" 
							required="required"	oninput="this.setCustomValidity('')">
						</spring:select></td>
					</tr>
                	<tr>
                    <td><spring:label path="createdBy">Created By</spring:label></td>
                    <td><spring:input class="form-control" id="createdByNew" path="createdBy.name" type="text" readonly="true"/>
                   </td>
                </tr>
                  </table>
                 
              </div>
          <div  class="row1">
          
           <table>
             <tr>
                  <td><spring:label path="criticality">Criticality<span style="color:red">*</span></spring:label></td>
                  <td>
                  <spring:select id="criticalityNew" multiple="false" path="criticality.id"   class="form-control dropdown-toggle text-left " Style="width:100%"
                  oninvalid="this.setCustomValidity('Please select Criticality')" 
								required="required"			oninput="this.setCustomValidity('')">
						</spring:select></td>
              </tr>
              <tr>
                  <td><spring:label path="primarySkill">Primary Skill<span style="color:red">*</span></spring:label></td>
                  <td>
                 <%--  <spring:select id="primarySkillNew" multiple="false" path="primarySkill.id"   class="form-control dropdown-toggle text-left " Style="width:100%"
                  oninvalid="this.setCustomValidity('Primary Skill must not be empty')" 
								required="required"			oninput="this.setCustomValidity('')">
						</spring:select> --%>
					<spring:input type="text" class="form-control" id="primarySkillNew" path="primarySkill" placeholder="Enter Skill Category.." />	
				  </td>
              </tr>
               <tr>
                  <td><spring:label path="location">Location<span style="color:red">*</span></spring:label></td>
                  <td>
                  <spring:select id="locationNew" multiple="false" path="location.id" class="form-control dropdown-toggle text-left " Style="width:100%"
                  oninvalid="this.setCustomValidity('Please select location')" 
									required="required"		oninput="this.setCustomValidity('')">
						</spring:select></td>
              </tr>
              <tr>
                  <td><spring:label path="billingRate">Billing Rate<span style="color:red">*</span></spring:label></td>
                  <td><spring:input class="form-control" id="billingRateNew" path="billingRate" type="text" oninvalid="this.setCustomValidity('Billing rate must not be empty')" 
							required="required"		maxlength="6"	onkeypress='validate(event)' oninput="this.setCustomValidity('')"/></td>
              </tr>
              <tr>
                  <td><spring:label path="intimatedBy">Intimated By<span style="color:red">*</span></spring:label></td>
                  <td><spring:input class="form-control" id="intimatedByNew" path="intimatedBy" type="text" oninvalid="this.setCustomValidity('Initimated By must not be empty')" 
							required="required"				oninput="this.setCustomValidity('')"/></td>
              </tr>
              <tr>
                  <td><spring:label path="intimationMode">Intimator Mode<span style="color:red">*</span></spring:label></td>
                  <td>
                  <spring:select id="intimationModeNew" multiple="false" path="intimationMode.id" class="form-control dropdown-toggle text-left " Style="width:100%"
                  oninvalid="this.setCustomValidity('Please select Mode of Intimation')" 
						required="required"				oninput="this.setCustomValidity('')">
						</spring:select></td>
              </tr>
              <tr>
                          <td><spring:label path="jo">JO<span style="color:red">*</span></spring:label> </td>
                          <td><spring:input class="form-control" id="joNew" path="jo" type="text" oninvalid="this.setCustomValidity('JO must not be empty')" 
								required="required"		oninput="this.setCustomValidity('')" onkeypress='validate(event)'/></td>
                        </tr>
               <%-- <tr>
                  <td><spring:label path="expectedDOJ">Customer Expected DOJ<span style="color:red">*</span></spring:label></td>
                  <td><spring:input class="form-control" id="expectedDOJ" path="expectedDOJ" type="text" required="required"	/></td>
              </tr> --%>
              <tr>
                  <td><spring:label path="so">SO<span style="color:red">*</span></spring:label></td>
                  <td><spring:input class="form-control" id="soNew" path="so" type="text" oninvalid="this.setCustomValidity('SO must not be empty')" 
							required="required"				oninput="this.setCustomValidity('')" onkeypress='validate(event)'/></td>
              </tr>
               <tr>
                   <td><spring:label path="status">Status</spring:label> </td>
                   <td>
                   <spring:select id="statusNew" multiple="false" path="status.id"   class="form-control dropdown-toggle text-left " Style="width:100%" >
						</spring:select></td>
               </tr>      
                <tr>
                    <td><spring:label path="activityOwnerEmail">Activity Owner Email</spring:label></td>
                    <td><spring:input class="form-control" id="activityOwnerEmail" path="activityOwnerEmail" type="text" onChange="validateEmail(this);"/></td>
                </tr>
                <tr>
                    <td><spring:label path="actualOwnerEmail">Actual Owner Email</spring:label> </td>
                    <td><spring:input class="form-control" id="actualOwnerEmail" path="actualOwnerEmail" type="text" onChange="validateEmail(this);"/></td>
                </tr>      
                <tr>
                   <td><spring:label path="account">Account</spring:label> </td>
                   <td>
                     <spring:input class="form-control" id="accountNew" path="account.accountId" type="text" readonly="true"/>
                   </td>
                </tr> 
                <tr>
                    <td><spring:label path="project">Project</spring:label> </td>
                    <td>
                     <spring:input class="form-control" id="projectNew" path="project.projectId" type="text" readonly="true"/>
                   </td>
                </tr>      
                <tr>
                    <td><spring:label path="updatedOn">Updated On</spring:label></td>
                    <td><spring:input class="form-control" id="updatedOn" path="updatedOn" type="text" readonly="true"/></td>
                </tr>
               <tr>
                    <td><spring:label path="ibg_cdg">IBG/CDG<span style="color:red">*</span></spring:label></td>
                    <td><spring:input class="form-control" id="ibg_cdgNew" path="ibg_cdg" type="text" oninvalid="this.setCustomValidity('IBG/CDG must not be empty')" 
								required="required"			oninput="this.setCustomValidity('')"/></td>
                </tr>
                <tr>
                    <td><spring:label path="ibu_cdu">IBU/CDU<span style="color:red">*</span></spring:label></td>
                    <td><spring:input class="form-control" id="ibu_cduNew" path="ibu_cdu" type="text" oninvalid="this.setCustomValidity('IBU/CDU must not be empty')" 
								required="required"			oninput="this.setCustomValidity('')"/></td>
                </tr>
                <tr>
                    <td><spring:label path="pid_crmid_so">PID<span style="color:red">*</span></spring:label></td>
                    <td><spring:input class="form-control" id="pid_crmid_soNew" path="pid_crmid_so" type="text" 
                    oninvalid="this.setCustomValidity('PID/CRM ID must not be empty')" maxlength="15" 
								required="required"	oninput="this.setCustomValidity('')" /></td>
                </tr>
                
               
                
                
                <tr>
                    <td><spring:label path="yearExperience">Years Of Experience<span style="color:red">*</span></spring:label></td>
                    <td><spring:input class="form-control" id="yearExperienceNew" path="yearExperience" type="text" oninvalid="this.setCustomValidity('Years of experience must not be empty')" 
								required="required" onkeypress='validate(event)' oninput="this.setCustomValidity('')"/></td>
                </tr>
                <tr>
                          <td><spring:label path="remarks">Remarks<span style="color:red">*</span></spring:label></td>
                          <td><spring:input class="form-control" id="remarks" path="remarks" type="text" oninvalid="this.setCustomValidity('Remarks must not be empty')" 
								required="required"		oninput="this.setCustomValidity('')"/></td>
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
              	<div class="modal fade" id="squarespaceModal2" tabindex="-1" role="dialog" aria-labelledby="modalLabel" aria-hidden="true">
  <div class="modal-dialog" style="width:60%;">
	<div class="modal-content">
		<div class="modal-header" style="background-color: #b30000;">
			<button type="button" id="reload" class="close" data-dismiss="modal"
							style="background-color: white;">
							<span aria-hidden="true">×</span><span class="sr-only">Close</span>
						</button>
			<h3 class="modal-title" id="lineModalLabel" style="color: white;">Shared Profile(s)</h3>
		</div>
		<div class="modal-body">
            <!-- content goes here -->
			<div class="container" style="">			
        <table class="table  table-sm">
            <thead class="table-info">
                <tr>
					<th>Name</th>
                    <th>Internal Evaluation Result</th>
					<th>Customer Interview Status</th>
					<th>Remarks</th>
                </tr>
            </thead>
            <tbody id="tableProfile_content">
               <!--  <tr>
					<td>harish</td>
                    <td>Shortlisted</td>
					<td>Hold</td>
							
                </tr> -->
            </tbody>
        </table>
			</div>
	</div>
  </div>
</div>
</div>
		<spring:form  name='RequirementForm2' action="requirements" method='GET' >

			<div class="table" style="overflow: auto; margin-top: 2%;">

				<table id="reqtable3" class="display">
					<thead style="vertical-align: bottom;">
						<tr>
							<th><input type="checkbox" id="checkall" />&nbsp; All</th>
							<th>Ref No#</th>
							
							<th>Criticality</th>
					        <th>Primary Skill</th>
					        <th>Location</th>
					        <th>Intimation Date</th>
					        <th>Customer Expected DOJ</th>
					        <th>Profiles Shared</th>
					        <th>Internal Evaluations in progress</th>
					        <th>Customer Shortlisted</th>
					        <th>Position Status</th>
					        <th>Opportunity Status</th>
						</tr>
					</thead>
					<tbody align="center" id="Demo">
						
					</tbody>
					
				</table>
				<!-- <button  id="btnExport1" style="background-color:#cc0000;color:white;float:right;margin-top:2%;" type="button" class="btn btn-md button1">Export to Excel</button> -->
			</div>
	</spring:form>
		</div>
		<div class="w3-row"
			style="background-color: #b30000; text-align: center; padding: 10px; margin-top: 7px; color: white; height: 38px; bottom: 0px; align: bottom;">©
			Tech Mahindra Limited ©2018. All rights reserved.</div>
		
		
		<script>
		 var table=${requirementtableJson};
		    var locationJson=${locationJson};
		     /* var accountValues = ${accountValuesJson}; */ 
		    var criticalJson=${criticalJson};
		    var intimationModeJson=${intimationModeJson};
		    var requirementTypeJson=${requirementTypeJson};
		    var positionStatusJson=${positionStatusJson};
		    var opportunityStatusJson=${opportunityStatusJson};
		    var skillCategoryJson=${skillCategoryJson};
		    var primarySkillJson=${primarySkillJson};
		    
</script>

		<script>
			var myObjObj = ${requirementblockJson};

			$("#dvalue1").text(myObjObj.leadcount);
			$("#dvalue2").text(myObjObj.profilecount);
			$("#dvalue3").text(myObjObj.techevalcount);
			$("#dvalue4").text(myObjObj.custevalcount);
			$("#dvalue5").text(myObjObj.offercount);
			$("#dvalue6").text(myObjObj.boardingcount);
			$("#dvalue9").text(myObjObj.totalcount);
			$("#dvalue7").text(myObjObj.holdOpporCount);
			$("#dvalue8").text(myObjObj.abandonedOpporCount);
			$("#dvalue10").text(myObjObj.lostOppurCount);
		</script> 
<script>
        $('#btnAvenger').click(function (e) {
            $('select').moveToList('#StaffList', '#PresenterList');
            e.preventDefault();
        });

        $('#btnRemoveAvenger').click(function (e) {
            $('select').removeSelected('#PresenterList');
            e.preventDefault();
        });

        $('#btnAvengerUp').click(function (e) {
            $('select').moveUpDown('#PresenterList', true, false);
            e.preventDefault();
        });

        $('#btnAvengerDown').click(function (e) {
            $('select').moveUpDown('#PresenterList', false, true);
            e.preventDefault();
        });

        $('#btnShield').click(function (e) {
            $('select').moveToList('#StaffList', '#ContactList');
            e.preventDefault();
        });

        $('#btnRemoveShield').click(function (e) {
            $('select').removeSelected('#ContactList');
            e.preventDefault();
        });

        $('#btnShieldUp').click(function (e) {
            $('select').moveUpDown('#ContactList', true, false);
            e.preventDefault();
        });

        $('#btnShieldDown').click(function (e) {
            $('select').moveUpDown('#ContactList', false, true);
            e.preventDefault();
        });

        $('#btnJusticeLeague').click(function (e) {
            $('select').moveToList('#StaffList', '#FacilitatorList');
            e.preventDefault();
        });

        $('#btnRemoveJusticeLeague').click(function (e) {
            $('select').removeSelected('#FacilitatorList');
            e.preventDefault();
        });

        $('#btnJusticeLeagueUp').click(function (e) {
            $('select').moveUpDown('#FacilitatorList', true, false);
            e.preventDefault();
        });

        $('#btnJusticeLeagueDown').click(function (e) {
            $('select').moveUpDown('#FacilitatorList', false, true);
            e.preventDefault();
        });
		
        $('#btnRight').click(function (e) {
            $('select').moveToListAndDelete('#lstBox1', '#lstBox2');
            e.preventDefault();
        });

        $('#btnAllRight').click(function (e) {
            $('select').moveAllToListAndDelete('#lstBox1', '#lstBox2');
            e.preventDefault();
        });

        $('#btnLeft').click(function (e) {
            $('select').moveToListAndDelete('#lstBox2', '#lstBox1');
            e.preventDefault();
        });

        $('#btnAllLeft').click(function (e) {
            $('select').moveAllToListAndDelete('#lstBox2', '#lstBox1');
            e.preventDefault();
        });
    </script>
</body>


</html>
