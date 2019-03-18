$(document).ready(function(){
	

	   var profiledata1=profiledata;
	   
	   var profile  = Handlebars.compile($("#offerView").html());
	   
	   var result=profile(profiledata1);
	   $("#offerView_content").html(result); 
	   

})
 

