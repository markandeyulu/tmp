$(document).ready(function(){
	
	var barChartContent=[];
	var pieChartContent=[];

	//Tree charts variables
	var result;
	var treeChartContent=new Object();
	var chart_config ;
	var chart;

	$('#barChart').click(function (){
		$.ajax({
			type : "GET",
			dataType : "json",
			url : "/ResourceManagementApp/getBarChartDataJson.action",
			complete : function(data) {

				var obj1 = JSON.stringify(data);
				var obj2 = JSON.parse(obj1);
				var obj3 = obj2.responseJSON;
				console.log("getBarChartDataJson : "+obj3);
				$.each(obj3, function(i, object) {
					barChartContent.push({
						x : parseInt(object.x),
						y : parseInt(object.y),
						label : object.label
					});
				});

				if (barChartContent != null && barChartContent != undefined) {
					buildBarGraph();
				}

			}
		});
	});

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
				$("#chartContainer1").CanvasJSChart(options1);
			},
			resize : function(event, ui) {
				//Update chart size according to its container size.
				$("#chartContainer1").CanvasJSChart().render();
			}
		});
	}

	$('#pieChart').click(function (){
		$.ajax({
			type : "GET",
			dataType : "json",
			url : "/ResourceManagementApp/getPieChartDataJson.action",
			complete : function(data) {

				var obj1 = JSON.stringify(data);
				var obj2 = JSON.parse(obj1);
				var obj3 = obj2.responseJSON;
				console.log("getPieChartDataJson : "+obj3);
				$.each(obj3, function(i, object) {
					pieChartContent.push({
						x : parseInt(object.x),
						y : parseInt(object.y),
						label : object.label
					});
				});

				if (pieChartContent != null && pieChartContent != undefined) {
					buildPieGraph();
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
});