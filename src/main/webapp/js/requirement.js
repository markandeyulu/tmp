
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

$(document).ready(function(){
$("#reqtable3 #checkall").click(function () {
        if ($("#reqtable3 #checkall").is(':checked')) {
            $("#reqtable3 input[type=checkbox]").each(function () {
                $(this).prop("checked", true);
            });

        } else {
            $("#reqtable3 input[type=checkbox]").each(function () {
                $(this).prop("checked", false);
            });
        }
    });
    
    $("[data-toggle=tooltip]").tooltip();
});

			