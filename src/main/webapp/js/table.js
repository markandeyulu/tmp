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


 function loadDetail(id){
 
	 $("#updateBtn").attr("disabled",true);
	 $.ajax({
		 type: "GET",
		 dataType: 'json',
		 url: '/ResourceManagementApp/requirementsById.action',
		 data: { "id": id },
		
		 success: function (data) {
			 $('#id').val(data.id);
			 $("#criticalityNew option:contains(" + data.criticality.configValue.value + ")").attr('selected', 'selected');
			 //$('#criticalityNew').val(data.criticality.configValue.value);
			 $("#skillCategoryNew option:contains(" + data.skillCategory.configValue.value + ")").attr('selected', 'selected');
			// $('#skillCategoryNew').val(data.skillCategory.configValue.value);
			 $("#primarySkillNew option:contains(" + data.primarySkill.configValue.value + ")").attr('selected', 'selected');
			// $('#primarySkill').val(data.primarySkill.configValue.value);
			 $('#jobDescriptionNew').val(data.jobDescription);
			 $("#locationNew option:contains(" + data.location.configValue.value + ")").attr('selected', 'selected');
			// $('#locationNew').val(data.location.configValue.value);
			 $('#cityNew').val(data.city);
			 $('#billingRateNew').val(data.billingRate);
			 $('#intimationDateNew').val(data.intimationDate);
			 $('#intimatedByNew').val(data.intimatedBy);
			 $('#intimatorEmailNew').val(data.intimatorEmail);
			 //$('#intimationModeNew').val(data.intimationMode.configValue.value);
			 $("#intimationModeNew option:contains(" + data.intimationMode.configValue.value + ")").attr('selected', 'selected');
			 $("#requirementTypeNew option:contains(" + data.requirementType.configValue.value + ")").attr('selected', 'selected');
			// $('#requirementTypeNew').val(data.requirementType.configValue.value);
			 $('#expectedDOJ').val(data.expectedDOJ);
			 $('#actualClosureDateNew').val(data.actualClosureDate);
			 $('#soNew').val(data.so);
			 $('#joNew').val(data.jo);
			 
			// $('#status').val(data.status.configValue.value);
			 $('#activityOwner').val(data.activityOwner);
			 $('#activityOwnerEmail').val(data.activityOwnerEmail);
			 $('#actualOwner').val(data.actualOwner);
			 $('#actualOwnerEmail').val(data.actualOwnerEmail);
			 $('#remarks').val(data.remarks);
			 $('#customerNameNew').val(data.customerName);
			// $('#accountNew').val(data.account.account.adminInfoValue.value);
			
			 //$('#projectNew').val(data.project.project.adminInfoValue.value);
			 
			 $('#createdOn').val(data.createdOn);
			 $('#updatedOn').val(data.updatedOn);
			 
			/*$('#criticalityid').val(data.criticality.id);
			$('#skillCategoryid').val(data.skillCategory.id);
			 $('#primarySkillid').val(data.primarySkill.id);
			 $('#locationNewid').val(data.location.id);
			 $('#intimationModeid').val(data.intimationMode.id);
			 $('#requirementTypeid').val(data.requirementType.id);
			 $('#statusid').val(data.status.configValue.id);
			 $('#accountNewId').val(data.account.id);*/
			
			 $('#shortlistedProfile_idNew').val(data.shortlistedProfile_id.name);
			 
			 $('#shortlistedProfile_idNewId').val(data.shortlistedProfile_id.id);
			 $('#bandNew').val(data.band);
			 $('#quantityNew').val(data.quantity);			
			 $('#projectDurationNew').val(data.projectDuration);
			 $('#ibg_cdgNew').val(data.ibg_cdg);
			 $('#ibu_cduNew').val(data.ibu_cdu);
			 $('#pid_crmid_soNew').val(data.pid_crmid_so);
			 $('#yearExperienceNew').val(data.yearExperience);
			 $('#updatedByNew').val(data.updatedBy.name);
			 $('#createdByNew').val(data.createdBy.name);			 			
			 $("#accountNew option:contains(" + data.account.account.adminInfoValue.value + ")").attr('selected', 'selected');
			 $("#projectNew option:contains(" + data.project.project.adminInfoValue.value + ")").attr('selected', 'selected');
			 if(data.status.configValue.value!=null && data.status.configValue.value!=""){
				 $("#statusNew option:contains(" + data.status.configValue.value + ")").attr('selected', 'selected');
				 }
			 if(data.oppurtunityStatus.configValue.value!=null && data.oppurtunityStatus.configValue.value!=""){
				 $("#oppurtunityNew option:contains(" + data.oppurtunityStatus.configValue.value + ")").attr('selected', 'selected');
				 }
		 },
		 failure: function(data){
			// alert('Inside Ajax call. data failed')
		 }
		 });
		 }
		 
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

