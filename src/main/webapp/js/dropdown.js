$(document).ready(function(){
	/*var accountData=[
	{"name":"Ford","id":"1"},
	{"name":"Ford Direct","id":"2"},
	{"name":"Mazda","id":"3"},
	{"name":"TRW","id":"4"}
	]*/
	
	var accountData=accountValues.accountsJson;
	var g_accountArray = [];
	$.each(accountData, function(index) {
		var g_item = [];
		g_item.push(accountData[index].userAccountName.adminInfoValue.value);
		g_item.push(accountData[index].userAccount.accountId);
		g_accountArray.push(g_item);
	});
	 $("#accountNew,#accountProfile").append('<option value="">Select Account</option>'); 
	$.each(g_accountArray, function(i) {
		var g_proItem = g_accountArray[i];
		$("#framework1,#accountNew,#accountProfile").append('<option id="' + g_proItem[1] + '" value="' + g_proItem[1] + '">' + g_proItem[0] + '</option>');
	});
	
	var primarySkillData=primarySkillJson.locationJson;
	
    	                var g_primarySkillArray = [];	
                          $.each(primarySkillData, function(index) {
    		              var g_item = [];
    		               g_item.push(primarySkillData[index].configValue.value);
    		               g_item.push(primarySkillData[index].id);	             		
    		               g_primarySkillArray.push(g_item);	             		
    	                 });
                          $("#primarySkillNew,#primarySkillProfile").append('<option value="">Select Primary Skill</option>');   
    	$.each(g_primarySkillArray, function(i) {
    		var g_primarySkillItem = g_primarySkillArray[i];
    		$("#primarySkillNew,#primarySkillProfile").append('<option id="' + g_primarySkillItem[1] + '" value="' + g_primarySkillItem[1] + '">' + g_primarySkillItem[0] + '</option>');
    	});	
	
/*    	var projectData=[
	    	             	{"name":"NVO","id":"1"},
	    	             	{"name":"OWS","id":"2"},
	    	             	{"name":"GPIRS","id":"3"},
	    	             	{"name":"NGP","id":"4"}
	    	             	]*/
    	var projectData = projectValues.projectsJson;
	    	             	var g_projectArray = [];
	    	             	
	    	             	$.each(projectData, function(index) {
	    	             		var g_item = [];
	    	             		g_item.push(projectData[index].userProjectName.adminInfoValue.value);
	    	             		g_item.push(projectData[index].userProject.id);
	    	             		//if ($.inArray(g_LocationItem, g_LocationArray) == -1) {
	    	             		g_projectArray.push(g_item);
	    	             		//}
	    	             	});

	    	             	//console.log("g_projectArray --> "+g_projectArray);
	    	             	$("#projectNew,#projectProfile").append('<option value="">Select Project</option>'); 
	    	             	$.each(g_projectArray, function(i) {
	    	             		var g_projectItem = g_projectArray[i];
	    	             		$("#projectNew,#projectProfile").append('<option id="' + g_projectItem[1] + '" value="' + g_projectItem[1] + '">' + g_projectItem[0] + '</option>');
	    	             	});	
	    	             	var criticalityData=criticalJson.locationJson;
	
	             	var g_criticalityArray = [];	             	
	             	$.each(criticalityData, function(index) {
	             		var g_item = [];
	             		g_item.push(criticalityData[index].configValue.value);
	             		g_item.push(criticalityData[index].id);	             		
	             		g_criticalityArray.push(g_item);	             		
	             	});	
	             	 $("#criticalityNew,#criticality1").append('<option value="">Select Criticality</option>');  
	             	$.each(g_criticalityArray, function(i) {
	             		var g_criticalityItem = g_criticalityArray[i];
	             		$("#criticalityNew,#criticality1").append('<option id="' + g_criticalityItem[1] + '" value="' + g_criticalityItem[1] + '">' + g_criticalityItem[0] + '</option>');
	             	});
	             	  
	  
var requirementTypeData=requirementTypeJson.locationJson;
	
	                var g_requirementTypeArray = [];	
                      $.each(requirementTypeData, function(index) {
		              var g_item = [];
		               g_item.push(requirementTypeData[index].configValue.value);
		               g_item.push(requirementTypeData[index].id);	             		
		               g_requirementTypeArray.push(g_item);	             		
	                 });
                      $("#requirementTypeNew,#requirementTypeAdd").append('<option value="">Select Requirement Type</option>');  
	$.each(g_requirementTypeArray, function(i) {
		var g_requirementTypeItem = g_requirementTypeArray[i];
		$("#requirementTypeNew,#requirementTypeAdd").append('<option id="' + g_requirementTypeItem[1] + '" value="' + g_requirementTypeItem[1] + '">' + g_requirementTypeItem[0] + '</option>');
	});
	
	var skillCategoryData=skillCategoryJson.locationJson;
		
	    	                var g_skillCategoryArray = [];	
	                          $.each(skillCategoryData, function(index) {
	    		              var g_item = [];
	    		               g_item.push(skillCategoryData[index].configValue.value);
	    		               g_item.push(skillCategoryData[index].id);	             		
	    		               g_skillCategoryArray.push(g_item);	             		
	    	                 });	
	                          $("#skillCategoryNew,#skillCategoryAdd1").append('<option value="">Select Skill Category</option>');  
	    	$.each(g_skillCategoryArray, function(i) {
	    		var g_skillCategoryItem = g_skillCategoryArray[i];
	    		$("#skillCategoryNew,#skillCategoryAdd1").append('<option id="' + g_skillCategoryItem[1] + '" value="' + g_skillCategoryItem[1] + '">' + g_skillCategoryItem[0] + '</option>');
	    	});		 	    	    		    	    	             	
var intimatorModeData=intimationModeJson.locationJson
	 	    	    		    	    	             		
var g_intimatorModeArray = [];
	$.each(intimatorModeData, function(index) {
	var g_item = [];
	g_item.push(intimatorModeData[index].configValue.value);
	g_item.push(intimatorModeData[index].id);
    g_intimatorModeArray.push(g_item);
	 	    	    		    	    	              	             		
	});

	 	    	    		    	    	              	             	
 $("#intimationModeNew,#intimationModeAdd").append('<option value="">Select Intimation Mode</option>');  
	$.each(g_intimatorModeArray, function(i) {
var g_intimatorModeItem = g_intimatorModeArray[i];
$("#intimationModeNew,#intimationModeAdd").append('<option id="' + g_intimatorModeItem[1] + '" value="' + g_intimatorModeItem[1] + '">' + g_intimatorModeItem[0] + '</option>');
});  
var statusData=positionStatusJson.locationJson;
var g_statusArray = [];
	$.each(statusData, function(index) {
	 	 var g_item = [];
	 	 g_item.push(statusData[index].configValue.value);
	 	 g_item.push(statusData[index].id);
	 	 g_statusArray.push(g_item);
	});

	 	    	    		    	    	              	                   	             	
 $("#statusNew").append('<option value="">Select Status</option>');                 	             	
$.each(g_statusArray, function(i) {
	 var g_statusItem = g_statusArray[i];
	 $("#statusNew").append('<option id="' + g_statusItem[1] + '" value="' + g_statusItem[1] + '">' + g_statusItem[0] + '</option>');
 });   
	 	    	    		    	    	              	  
var oppurtunityStatusData=opportunityStatusJson.locationJson;
var g_oppurtunityStatusArray = [];
$.each(oppurtunityStatusData, function(index) {
	 var g_item = [];
	 	 g_item.push(oppurtunityStatusData[index].configValue.value);
	 	 g_item.push(oppurtunityStatusData[index].id);
	 	 g_oppurtunityStatusArray.push(g_item);
	 	    	    		    	    	                       	                   	             		
	 });	 	    	    		    	    	                       	                   	             	
 $("#oppurtunityNew").append('<option value="">Select Opportunity Status</option>');                	             	
	 $.each(g_oppurtunityStatusArray, function(i) {
	 var g_oppurtunityStatusItem = g_oppurtunityStatusArray[i];
	 $("#oppurtunityNew").append('<option id="' + g_oppurtunityStatusItem[1] + '" value="' + g_oppurtunityStatusItem[1] + '">' + g_oppurtunityStatusItem[0] + '</option>');
 });                	  
	 	    	    		    	    	                       	  
	 	    	    		    	  
});

	  
