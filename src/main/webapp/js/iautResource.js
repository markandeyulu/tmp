
  $(document).ready(function() {
    $("#datepicker").datepicker({
  	  dateFormat: "yy-mm-dd"
    });
  });
  $(document).ready(function() {
    $("#datepicker1").datepicker({
  	  dateFormat: "yy-mm-dd"
    });
  });
	  $(document).ready(function() {
    $("#start_datepicker ").datepicker({
  	  dateFormat: "yy-mm-dd"
    });
  });
   
	  $(document).ready(function() {
		    $("#allocationEndDateProfile ").datepicker({
		    	  dateFormat: "yy-mm-dd"
		    });
		    $("#internalEvaluationResultDateProfile ").datepicker({
		    	  dateFormat: "yy-mm-dd"
		    });
		    $("#profileSharedCustomerDateProfile ").datepicker({
		    	  dateFormat: "yy-mm-dd"
		    });
		    $("#allocationStartDateProfile ").datepicker({
		    	  dateFormat: "yy-mm-dd"
		    });
		    $("#profileSharedDateProfile ").datepicker({
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

 $('#framework_form').on('submit', function(event){
  event.preventDefault();
  var form_data = $(this).serialize();
    $('#framework option:selected').each(function(){
     $(this).prop('selected', false);
    });
    $('#framework').multiselect('refresh');
    alert(data);
 });
});

$(document).ready(function(){
 $('#framework1').multiselect({
  includeSelectAllOption: true,
  nonSelectedText: 'Select Account',
  enableCaseInsensitiveFiltering: false,
  buttonWidth:''
 });

 $('#framework_form').on('submit', function(event){
  event.preventDefault();
  var form_data = $(this).serialize(); 
    $('#framework option:selected').each(function(){
     $(this).prop('selected', false);
    });
    $('#framework').multiselect('refresh');
    alert(data);
 });
});
$(document).ready(function(){
	 $('#framework2').multiselect({
	  includeSelectAllOption: true,
	  nonSelectedText: 'Select Location',
	  enableCaseInsensitiveFiltering: false,
	  buttonWidth:''
	 });

	 $('#framework_form').on('submit', function(event){
	  event.preventDefault();
	  var form_data = $(this).serialize();   
	    $('#framework option:selected').each(function(){
	     $(this).prop('selected', false);
	    });
	    $('#framework').multiselect('refresh');
	    alert(data);
	 });
	});

$(document).ready(function() {
    $('#reqtable1').DataTable( {
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
$("#reqtable1 #checkall").click(function () {
        if ($("#reqtable1 #checkall").is(':checked')) {
            $("#reqtable1 input[type=checkbox]").each(function () {
                $(this).prop("checked", true);
            });

        } else {
            $("#reqtable1 input[type=checkbox]").each(function () {
                $(this).prop("checked", false);
            });
        }
    });
    
    $("[data-toggle=tooltip]").tooltip();
});


$(document).ready(function(){
   
				$('#reqtable1 th:nth-child(3)').hide();
                $('#reqtable1 td:nth-child(3)').hide();
                $('#reqtable1 th:nth-child(4)').hide();
                $('#reqtable1 td:nth-child(4)').hide();
				$('#reqtable1 th:nth-child(5)').hide();
                $('#reqtable1 td:nth-child(5)').hide();
				$('#reqtable1 th:nth-child(6)').hide();
                $('#reqtable1 td:nth-child(6)').hide();
				$('#reqtable1 th:nth-child(7)').hide();
                $('#reqtable1 td:nth-child(7)').hide();
				$('#reqtable1 th:nth-child(8)').hide();
                $('#reqtable1 td:nth-child(8)').hide();
						
   
});
$(document).ready(function(){
       $('.chcol_1').click(function() {
				 $('html, body').animate({
						scrollTop: $("div.table").offset().top
							}, 1000)
				$('#reqtable1 th:nth-child(3)').toggle();
                $('#reqtable1 td:nth-child(3)').toggle();                
       });
});
$(document).ready(function(){
       $('.chcol_2').click(function() {
				$('html, body').animate({
						scrollTop: $("div.table").offset().top
							}, 1000)
				$('#reqtable1 th:nth-child(4)').toggle();
                $('#reqtable1 td:nth-child(4)').toggle();                
       });
});
$(document).ready(function(){
       $('.chcol_3').click(function() {
				$('html, body').animate({
						scrollTop: $("div.table").offset().top
							}, 1000)
				$('#reqtable1 th:nth-child(5)').toggle();
                $('#reqtable1 td:nth-child(5)').toggle();                
       });
});
$(document).ready(function(){
       $('.chcol_4').click(function() {
				$('html, body').animate({
						scrollTop: $("div.table").offset().top
							}, 1000)
				$('#reqtable1 th:nth-child(6)').toggle();
                $('#reqtable1 td:nth-child(6)').toggle();                
       });
});
$(document).ready(function(){
       $('.chcol_5').click(function() {
				$('html, body').animate({
						scrollTop: $("div.table").offset().top
							}, 1000)
				$('#reqtable1 th:nth-child(7)').toggle();
                $('#reqtable1 td:nth-child(7)').toggle();                
       });
});
$(document).ready(function(){
       $('.chcol_6').click(function() {
				$('html, body').animate({
						scrollTop: $("div.table").offset().top
							}, 1000)
				$('#reqtable1 th:nth-child(8)').toggle();
                $('#reqtable1 td:nth-child(8)').toggle();                
       });
});



 $(function() {
    $('.multiselect-ui').multiselect({
        includeSelectAllOption: true
    });
});




$(document).ready(function() {
    $('#reqtable2').DataTable( {
    	
        "zeroRecords": "No records available - Got it?",
    	
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
$("#reqtable2 #checkall").click(function () {
        if ($("#reqtable2 #checkall").is(':checked')) {
            $("#reqtable2 input[type=checkbox]").each(function () {
                $(this).prop("checked", true);
            });

        } else {
            $("#reqtable2 input[type=checkbox]").each(function () {
                $(this).prop("checked", false);
            });
        }
    });
    
    $("[data-toggle=tooltip]").tooltip();
});

$(document).ready(function() {
    $('#reqtable3').DataTable( {
    	
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

			