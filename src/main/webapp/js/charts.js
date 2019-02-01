$(document).ready(function(){
	$('.multiselect-ui').multiselect({
		includeSelectAllOption: true
	});
	
	
	
	var accountData=accountValues.accountListJson;
	var g_accountArray = [];
	$.each(accountData, function(index) {
		var g_item = [];
		g_item.push(accountData[index].accountName);
		g_item.push(accountData[index].accountId);
		g_accountArray.push(g_item);
	});
	$.each(g_accountArray, function(i) {
		var g_proItem = g_accountArray[i];
		$("#framework1").append('<option id="' + g_proItem[1] + '" value="' + g_proItem[1] + '">' + g_proItem[0] + '</option>');
	});
	
	$('#framework1').multiselect({
		includeSelectAllOption: true,
		nonSelectedText: 'Select Account',
		enableCaseInsensitiveFiltering: false,
		buttonWidth:''
	});
	var barChartContent=[];
	var pieChartContent=[];

	//Tree charts variables
	var result;
	var treeChartContent=new Object();
	var chart_config ;
	var chart;



	function buildBarGraph() {
		var options1 = {
				animationEnabled : true,
				title : {
					text : "Resource Allocation"
				},
				data : [ {
					type : "column", //change it to line, area, bar, pie, etc
					legendText : "As of December, 2018",
					showInLegend : true,
					dataPoints : barChartContent

				} ]
		};
		
		$("#resizable").resizable({
			create : function(event, ui) {
				//Create chart.
				$("#chartContainer").CanvasJSChart(options1);
			},
			resize : function(event, ui) {
				//Update chart size according to its container size.
				$("#chartContainer").CanvasJSChart().render();
			}
		});
	}
	
	

	$('#pieChart').click(function (e){
	
		
		
		var form = $('#chartsForm')[0];
        var formData = new FormData(form);
        
          var formAddNewRowObject = {};
          formData.forEach(function(value, key){
                 formAddNewRowObject[key] = value;
          });
         
          e.preventDefault();
		$.ajax({
			 type: "GET",
		     url: "/ResourceManagementApp/getChartData.action",
		     data: formAddNewRowObject, 
			complete : function(data) {

				var obj1 = JSON.stringify(data);
				var obj2 = JSON.parse(obj1);
				var obj3 = obj2.responseJSON;
				console.log("getPieChartDataJson : "+obj3);
				
				if (formAddNewRowObject.chartType == "pie" ) {
				$.each(obj3, function(i, object) {
					
					pieChartContent.push({
						x : parseInt(object.x),
						y : parseInt(object.y),
						label : object.label
					});
					
				});
				}else{
					$.each(obj3, function(i, object) {
						barChartContent.push({
							x : parseInt(object.x),
							y : parseInt(object.y),
							label : object.label
						});
					});
				}
				
				if(pieChartContent != null && pieChartContent != undefined){
				if (formAddNewRowObject.chartType == "bar" ) {
					buildBarGraph();
				}else  {
					buildPieGraph();
				}
				}
			}
		});

	});

	function buildPieGraph()
	{
		$('#chartContainer div').empty();
		var options = {
				title: {
					text: "Resource Allocation"
				},
				subtitles: [{
					text: "As of December, 2018"
				}],
				animationEnabled: true,
				data: [{
					type: "pie",
					startAngle: 40,
					toolTipContent: "<b>{label}</b>: {y}%",
					showInLegend: "true",
					legendText: "{label}",
					indexLabelFontSize: 16,
					indexLabel: "{label} - {y}%",
					dataPoints: pieChartContent
				}]
		};
		$("#chartContainer").CanvasJSChart(options);
		//setTimeout(function(){getData()},5000);
	}

	$('#treeChart').click(function (){
		
		$.ajax({
			type : "GET",
			dataType : "json",
			url : "/ResourceManagementApp/getTreeChartDataJson.action",
			complete : function(data) {
				var obj1 = JSON.stringify(data);
				var obj2 = JSON.parse(obj1);
				var obj3 = obj2.responseJSON;
				console.log("getTreeChartDataJson : "+obj3);
				if(obj3!=null && obj3!=undefined)
				{
					var arry=[];
					$.each(obj3, function(i, object) {
						if(object.collapsed=="true"){
							arry.push({ Id: object.id,innerHTML: object.innerHTML, Parent:object.parent,collapsed:true });
						}else{
							arry.push({ Id: object.id,innerHTML: object.innerHTML, Parent:object.parent });
						}
					});
					createStructure(arry);
					buildTreeGraph();
				}
			}
		});
		
	});

	function buildTreeGraph() {
		chart_config = {
				chart: {
					container: "#collapsable-chart",

					animateOnInit: true,

					node: {
						collapsable: true
					},
					animation: {
						nodeAnimation: "easeOutBounce",
						nodeSpeed: 700,
						connectorsAnimation: "bounce",
						connectorsSpeed: 700
					}
				},
				nodeStructure:result[0]
		};

		tree = new Treant( chart_config );
	}

	function createStructure(array)	{

		var map = {};
		for(var i = 0; i < array.length; i++)
		{
			var obj =array[i] ;
			obj.children= [];

			map[obj.Id] = obj;

			var parent = obj.Parent || '-';
			if(!map[parent]){
				map[parent] = {
						children: []
				};
			}
			map[parent].children.push(obj);
		}
		result= map['-'].children;

	}
	
	$(function() {
	    var $radios = $('input:radio[name=chartType]');
	    if($radios.is(':checked') === false) {
	        $radios.filter('[value=pie]').prop('checked', true);
	    }
	});
});