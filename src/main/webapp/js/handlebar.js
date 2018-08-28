$(document).ready(function(){
	var data = dashboardRequirements;   
var temp  = Handlebars.compile($("#CaseView").html());
var result=temp(data);
if (dashboardRequirements.dashboardRequirementsJson.length == 0) {
	
	var html = "No Data found for your search..!!!"
		$('#listView_content').append(html);
	
}

else{
	
	$("#listView_content").html(result);
}
}) 


