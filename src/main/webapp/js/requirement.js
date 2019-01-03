
  $(document).ready(function() {
    $("#datepicker").datepicker();
  });
  $(document).ready(function() {
    $("#datepicker1").datepicker();
  });
	  $(document).ready(function() {
    $("#start_datepicker").datepicker({
  	  dateFormat: "yy-mm-dd"
    });
  });
   
	 

	  $(document).ready(function() {
		    $("#expectedDOJ").datepicker({
		    	  dateFormat: "yy-mm-dd"
		    });
		    $("#intimationDateNew,#intimationDate").datepicker({
		    	  dateFormat: "yy-mm-dd"
		    });
		    $("#actualClosureDateNew,#actualClosureDate").datepicker({
		    	  dateFormat: "yy-mm-dd"
		    });
		  });

	  
	  $(document).ready(function() {
			$('.multiselect-ui').multiselect({
				includeSelectAllOption: true
			});
			 $( "#location1,#city,#primarySkill1,#skillCategoryAdd1,#jobDescription,#ibg_cdg,#ibu_cdu,#intimatedBy" ).keypress(function(e) {
	               var key = e.keyCode;
	               if (key >= 48 && key <= 57) {
	                   e.preventDefault();
	               }
	           });
			 $( "#skillCategoryNew,#jobDescriptionNew,#cityNew,#primarySkillNew,#locationNew,#intimatedByNew,#ibu_cduNew,#ibg_cdgNew,#remarks,#activityOwner,#actualOwner" ).keypress(function(e) {
	               var key = e.keyCode;
	               if (key >= 48 && key <= 57) {
	                   e.preventDefault();
	               }
	           });
		});
		$(document).ready(function(){
			$('#framework').multiselect({
				includeSelectAllOption: true,
				nonSelectedText: 'Select Location',
				enableCaseInsensitiveFiltering: false,
				buttonWidth:''
			});

		});

		$(document).ready(function(){
			$('#framework1').multiselect({
				includeSelectAllOption: true,
				nonSelectedText: 'Select Account',
				enableCaseInsensitiveFiltering: false,
				buttonWidth:''
			});
			

		});
		$(function() {
			$('.multiselect-ui').multiselect({
				includeSelectAllOption: true
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
			$("option:selected").removeAttr("selected");
			$('#id').val(data.id);
			$("#criticalityNew option:contains(" + data.criticality.configValue.value + ")").attr('selected', 'selected');
			//$('#criticalityNew').val(data.criticality.configValue.value);
			//$("#skillCategoryNew option:contains(" + data.skillCategory.configValue.value + ")").attr('selected', 'selected');
			 //$('#skillCategoryNew').val(data.skillCategory.configValue.value);
			 $('#skillCategoryNew').val(data.skillCategory);
			//$("#primarySkillNew option:contains(" + data.primarySkill.configValue.value + ")").attr('selected', 'selected');
			// $('#primarySkillNew').val(data.primarySkill.configValue.value);
			 $('#primarySkillNew').val(data.primarySkill);
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
			 if(null!=data.account.accountName){
				 $('#accountNew').val(data.account.accountName);
			 }
			 if(null!=data.project.projectName){
				 $('#projectNew').val(data.project.projectName);	 
			 }
			
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
$(document).ready(function() {
    $("#reqtable3").DataTable( {
        initComplete: function () {
            this.api().columns().every( function () {
                var column = this;
                var select = $('<select><option value=""></option></select>')
                    .appendTo( $(column.header()) )
                    .on( 'change', function () {
                        var val = $.fn.dataTable.util.escapeRegex(
                            $(this).val()
                        );
 
                        column
                            .search( val ? '^'+val+'$' : '', true, false )
                            .draw();
                    } );
 
                column.data().unique().sort().each( function ( d, j ) {
                    select.append( '<option value="'+d+'">'+d+'</option>' )
                } );
            } );
        }
    } );
} );


			