$(document).ready(function() {

	var datasample = table;
	var temp2 = Handlebars.compile($("#SampleView").html());
	//console.log(temp2);
	var result2 = temp2(datasample);
	$("#Demo").html(result2);
	/*if (table.requirementtableJson[0].profileshared== 0) {
		
		
	}
	else{*/
	
	
	
//}
	$("#squarespaceModal1 .form-control").change(function(){
		
		$("#updateBtn").attr("disabled",false);
		
	});
	
});


 
		 
 function loadProfileDetail(id){
	 $('#tableProfile_content').empty();
	 $.ajax({
		 type: "GET",
		 dataType: 'json',
		 url: '/ResourceManagementApp/requirementProfileId.action',
		 data: { "id": id },
		 
		 success: function (data) {
			 
			 $.each(data, function (i, item) {
			 tr = $('<tr/>');
			 tr.append("<td>" + item.profileId.name + "</td>");
			 tr.append("<td>" + item.internalEvaluationResult.configValue.value + "</td>");
			 tr.append("<td>" + item.customerInterviewStatus.configValue.value + "</td>");
			 tr.append("<td>" + item.remarks + "</td>");
			 $('#tableProfile_content').append(tr);
			 });
			
			  
		 },
		 failure: function(data){
				// alert('Inside Ajax call. data failed')
			 }
			 });
	 
 }
 function loadShortlistedProfileDetail(id){
	 $("#lstBox1").empty();
	 $.ajax({
		 type: "GET",
		 dataType: 'json',
		 url: '/ResourceManagementApp/requirementShortlistedProfileId.action',
		 data: { "id": id },
		 success: function (data) {			 
			 var ProfileData=data;
				
             var g_ProfileDataArray = [];	
               $.each(ProfileData, function(index) {
	              var g_item = [];
	               g_item.push(ProfileData[index].profileId.name);
	               g_item.push(ProfileData[index].profileId.id);
	               g_ProfileDataArray.push(g_item);	             		
              });
               
$.each(g_ProfileDataArray, function(i) {
	var g_ProfileDataItem = g_ProfileDataArray[i];
	$("#lstBox1").append('<option id="' + g_ProfileDataItem[1] + '" value="' + g_ProfileDataItem[1] + '">' + g_ProfileDataItem[0] + '</option>');
});	

$('#requirementIdMapping').val(data[0].requirementId1);	 
		 },
		 failure: function(data){
				// alert('Inside Ajax call. data failed')
			 }
	 }); 
 }

 function loadShortlistedProfile(id){
	 $("#lstBox2").empty();
	 $.ajax({
		 type: "GET",
		 dataType: 'json',
		 url: '/ResourceManagementApp/requirementShortlistProfile.action',
		 data: { "id": id },
		 success: function (data) {			 
			 var ProfileName=data;
				
             var g_ProfileDataArray = [];	
             $.each(ProfileName, function(index) {
	              var g_item = [];
	               g_item.push(ProfileName[index].shortlistedProfiles);	   
	               g_item.push(ProfileName[index].shortlistProfileId);	  
	               g_ProfileDataArray.push(g_item);	             		
             });
               
             $.each(g_ProfileDataArray, function(i) {
	var g_ProfileDataItem = g_ProfileDataArray[i];
	$("#lstBox2").append('<option id="' + g_ProfileDataItem[1] + '" value="' + g_ProfileDataItem[1] + '">' + g_ProfileDataItem[0] + '</option>');	 
             });	
             },
		 failure: function(data){
				// alert('Inside Ajax call. data failed')
			 }
	 }); 
 }

