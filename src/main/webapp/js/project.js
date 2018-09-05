$(document).ready(function(){
var sourceOfProfileData=profileSourceJson.locationJson;		
var g_sourceOfProfileArray = [];
	                                          	                   	             	
	$.each(sourceOfProfileData, function(index) {
	    var g_item = [];
	        g_item.push(sourceOfProfileData[index].configValue.value);
	        g_item.push(sourceOfProfileData[index].id);
	        g_sourceOfProfileArray.push(g_item);
	                                          	                   	             		
  });	                                          	                   	             	
	 $("#profileSourceProfile,#profileSource").append('<option value="0">Select Profile Source</option>');                    	                   	             	
	     $.each(g_sourceOfProfileArray, function(i) {
	         var g_sourceOfProfileItem = g_sourceOfProfileArray[i];
	             $("#profileSourceProfile,#profileSource").append('<option id="' + g_sourceOfProfileItem[1] + '" value="' + g_sourceOfProfileItem[1] + '">' + g_sourceOfProfileItem[0] + '</option>');
	            });	
var initialEvaluationResultData=initialEvaluationResultJson.locationJson;	            	   
	 var g_initialEvaluationResultArray = [];	
	  $.each(initialEvaluationResultData, function(index) {
	   var g_item = [];
	    g_item.push(initialEvaluationResultData[index].configValue.value);
	    g_item.push(initialEvaluationResultData[index].id);	             		
	    g_initialEvaluationResultArray.push(g_item);	             		
 });
	    $("#initialEvaluationResultProfile,#initialEvaluationResultAdd").append('<option value="">Select Initial Evaluation Result</option>'); 
	      $.each(g_initialEvaluationResultArray, function(i) {
	          var g_initialEvaluationResultItem = g_initialEvaluationResultArray[i];
	          $("#initialEvaluationResultProfile,#initialEvaluationResultAdd").append('<option id="' + g_initialEvaluationResultItem[1] + '" value="' + g_initialEvaluationResultItem[1] + '">' + g_initialEvaluationResultItem[0] + '</option>');
	            });	    
var customerEvaluationResultData=customerInterviewStatusJson.locationJson;	             	 	    	    		
var g_customerEvaluationResultArray = [];	
	 $.each(customerEvaluationResultData, function(index) {
	    var g_item = [];
	        g_item.push(customerEvaluationResultData[index].configValue.value);
	        g_item.push(customerEvaluationResultData[index].id);	             		
	        g_customerEvaluationResultArray.push(g_item);	             		
	  });
	       $("#customerInterviewStatusProfile,#customerInterviewStatusAdd").append('<option value="">Select Customer Interview Status</option>'); 
	       $.each(g_customerEvaluationResultArray, function(i) {
	       var g_customerEvaluationResultItem = g_customerEvaluationResultArray[i];
	       $("#customerInterviewStatusProfile,#customerInterviewStatusAdd").append('<option id="' + g_customerEvaluationResultItem[1] + '" value="' + g_customerEvaluationResultItem[1] + '">' + g_customerEvaluationResultItem[0] + '</option>');
	       });	
});