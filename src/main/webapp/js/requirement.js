
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
			 $( "#skillCategoryNew,#jobDescriptionNew,#cityNew,#primarySkillNew,#locationNew,#intimatedByNew,#ibu_cduNew,#ibg_cdgNew,#remarks" ).keypress(function(e) {
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


			