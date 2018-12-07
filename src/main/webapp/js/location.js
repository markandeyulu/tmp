$(document).ready(function(){

var locdata=locationJson.locationJson;
	/*[
{"locname":"Onsite","id":"5"},
{"locname":"Offshore","id":"6"}

]*/
var g_LocationArray = [];

$.each(locdata, function(key,value) {
	var g_LocationItem = [];
	g_LocationItem.push(key);
	g_LocationItem.push(value);
	//if ($.inArray(g_LocationItem, g_LocationArray) == -1) {
		g_LocationArray.push(g_LocationItem);
	//}
});
$("#locationNew,#location1").append('<option value="project">Select Location</option>'); 
	$.each(g_LocationArray, function(i) {
		var g_LocationItem = g_LocationArray[i];
		$("#framework,#locationNew,#location1").append('<option id="' + g_LocationItem[0] + '" value="' + g_LocationItem[0] + '">' + g_LocationItem[1] + '</option>');
	});

});       	             	
