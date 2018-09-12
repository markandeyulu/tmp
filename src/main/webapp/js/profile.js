$(document).ready(function(){
	
	var reqRefNumber=reqRefNosJson.requirementRefNoJson;
	var g_reqRefNumberArray = [];
	$.each(reqRefNumber, function(index) {
		var g_item = [];
		g_item.push(reqRefNumber[index].reqRefNo);
		g_reqRefNumberArray.push(g_item);
	});
	 $("#reqRefNo").append('<option value="">Select Requirement Ref No</option>'); 
	$.each(g_reqRefNumberArray, function(i) {
		var g_proItem = g_reqRefNumberArray[i];
		$("#reqRefNo").append('<option id="' + g_proItem[0] + '" value="' + g_proItem[0] + '">' + g_proItem[0] + '</option>');
	});
	
	   var profiledata1=profiledata;
	   
	   var profile  = Handlebars.compile($("#profileView").html());
	   
	   var result=profile(profiledata1);
	   $("#profileView_content").html(result); 
	   
	   $("#popup .form-control").change(function(){
			
			$("#updateBtn").attr("disabled",false);
			
		});
	   
	   $( "#name,#profileSharedBy,#currentCompany,#location" ).keypress(function(e) {
               var key = e.keyCode;
               if (key >= 48 && key <= 57) {
                   e.preventDefault();
               }
           });
	   $( "#nameProfile,#locationProfile,#currentCompanyProfile,#primarySkillProfile,#profileSharedBy,#createdByProfile,#updatedByProfile,#remarksProfile" ).keypress(function(e) {
           var key = e.keyCode;
           if (key >= 48 && key <= 57) {
               e.preventDefault();
           }
       });
	   $("#currentCTC,#expectedCTC,#currentCTCProfile,#expectedCTCProfile").keypress(function(e) {
           var key = e.keyCode;
           if (!((key >= 48 && key <= 57) || (e.key>= 96 && e.key <= 105))) { 
        	 e.preventDefault();
        }
       });
})
 

function loadDetail(id){
 
	 $("#updateBtn").attr("disabled",true);
	 
	 $.ajax({
		 type: "GET",
		 dataType: 'json',
		 url: '/ResourceManagementApp/profileById.action',
		 data: { "id": id },
		 success: function (data) {

			 $('#requirementId').val(data.reqRefNo);
			 $('#idProfile').val(data.id);
			 $('#emailProfile').val(data.email);
			 $('#currentCompanyProfile').val(data.currentCompany);
			 $("#primarySkillProfile option:contains(" + data.primarySkill.configValue.value+ ")").attr('selected', 'selected');
			 $('#primarySkillId').val(data.primarySkill.id);
			 $('#profileSharedDateProfile').val(data.profileSharedDate);
			 $('#yearsOfExperienceProfile').val(data.yearsOfExperience);
			 $('#noticePeriodProfile').val(data.noticePeriod);
			 $('#expectedCTCProfile').val(data.expectedCTC);
			 $('#allocationStartDateProfile').val(data.allocationStartDate);
			 $('#createdOnProfile').val(data.createdOn);
			 $('#updatedOnProfile').val(data.updatedOn);
			 $('#internalEvaluationResultDateProfile').val(data.internalEvaluationResultDate);
			 $("#profileSharedCustomerProfile option:contains(" + data.profileSharedCustomer + ")").attr('selected', 'selected');
			 $("#customerInterviewStatusProfile option:contains(" + data.customerInterviewStatus.configValue.value + ")").attr('selected', 'selected');
			 $('#nameProfile').val(data.name);
			 $('#contactNoProfile').val(data.contactNo);
			 $('#locationProfile').val(data.location);
			 $("#profileSourceProfile option:contains(" + data.profileSource.configValue.value+ ")").attr('selected', 'selected');
			 $('#profileSourceId').val(data.profileSource.id);
			 $('#profileSharedByProfile').val(data.profileSharedBy);
			 $('#relevantExperienceProfile').val(data.relevantExperience);
			 $('#currentCTCProfile').val(data.currentCTC);
			 if(null!=data.isAllocated.configValue){
				 $("#isAllocatedProfile option:contains(" + data.isAllocated.configValue.value+ ")").attr('selected', 'selected');
			 }
			 $('#allocationEndDateProfile').val(data.allocationEndDate);
			 $('#createdByProfile').val(data.createdBy.name);
			 $('#updatedByProfile').val(data.updatedBy.name);
			 $("#initialEvaluationResultProfile option:contains(" + data.initialEvaluationResult.configValue.value + ")").attr('selected', 'selected');
			 /*$("#accountProfile option:contains(" + data.account.adminInfoValue.value + ")").attr('selected', 'selected');
			 $("#projectProfile option:contains(" + data.project.project.adminInfoValue.value + ")").attr('selected', 'selected');*/
			 $('#profileSharedCustomerDateProfile').val(data.profileSharedCustomerDate);
			 $('#remarksProfile').val(data.remarks);
			 
			//("#profileSharedCustomerDateProfile).attr("disabled","disabled");
			 if($('#profileSharedCustomerProfile').val()=="No"){
				 $("#profileSharedCustomerDateProfile").attr('disabled','disabled');
				 $("#customerInterviewStatusProfile").attr('disabled','disabled');
			 } else{
				 $("#profileSharedCustomerDateProfile").removeAttr('disabled');
				 $("#customerInterviewStatusProfile").removeAttr('disabled');
			 }
			 if(null!=data.account && null!=data.account.account.adminInfoValue.value){
				 $('#accountProfile').val(data.account.account.adminInfoValue.value);
			 }
			 if(null!=data.project && null != data.project.project.adminInfoValue.value){
				 $('#projectProfile').val(data.project.project.adminInfoValue.value);	 
			 }
			
		 },
		 failure: function(data){
			// alert('Inside Ajax call. data failed')
		 }
		 });
		 }
