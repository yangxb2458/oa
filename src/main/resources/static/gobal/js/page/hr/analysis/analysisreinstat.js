var myChart;
$(function(){
	$(".js-query").unbind("click").click(function(){
		createeCharts();
	})
})
function createeCharts()
{
	if(myChart!=undefined)
	{
		myChart.clear();
	}
	$("#dataDiv").html("<div id=\"main1\" style=\"height: 400px;\" class=\"col-sm-12\"></div>")
	var dataType=$("input:radio[name='dataType']:checked").val();
	var echartType=$("input:radio[name='echartType']:checked").val()
	var deptId=$("#deptId").attr("data-value");
	if(dataType=="1")
	{
		$("#echartTitle").html("复职类型");
	}else if(dataType=="2")
	{
		$("#echartTitle").html("人员部门");
	}else if(dataType=="3")
	{
		$("#echartTitle").html("人员职务");
	}
	if(echartType=="1")
	{
		getReinstatPieForAnalysis(dataType,deptId);
	}else if(echartType=="2")
	{
		getReinstatBarForAnalysis(dataType,deptId);
	}else if(echartType=="3")
	{
		getReinstatTableForAnalysis(dataType,deptId)
	}
		
}

function getReinstatTableForAnalysis(dataType,deptId)
{
	$.ajax({
		url : "/ret/echartshrget/getReinstatTableForAnalysis",
		type : "post",
		dataType : "json",
		data:{dataType:dataType,deptId:deptId},
		success : function(data) {
			if(data.status=="200")
			{
				var recordInfo = data.list;
				var html="<table class=\"table table-hover table-striped table-bordered\">";
				for(var i=0;i<recordInfo.length;i++)
				{
					html+="<tr>";
					html+="<td width='200px;'>"+(recordInfo[i].name==undefined?("other"+i):recordInfo[i].name)+"</td>";
					html+="<td>"+recordInfo[i].value+"</td>";
					html+="</tr>";
				}
				html+="</table>";
				$("#main1").html(html);
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else
			{
				console.log(data.list);
			}
		}
	})
}

function getReinstatPieForAnalysis(dataType,deptId)
{
	$.ajax({
		url : "/ret/echartshrget/getReinstatPieForAnalysis",
		type : "post",
		dataType : "json",
		data:{dataType:dataType,deptId:deptId},
		success : function(data) {
			if(data.status=="200")
			{
				var option = data.list;
				myChart = echarts.init(document.getElementById('main1'));
				myChart.setOption(option);
				window.addEventListener("resize",function(){
				    myChart.resize();
				});
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else
			{
				console.log(data.list);
			}
		}
	})
}
function getReinstatBarForAnalysis(dataType,deptId)
{
	$.ajax({
		url : "/ret/echartshrget/getReinstatBarForAnalysis",
		type : "post",
		dataType : "json",
		data:{dataType:dataType,deptId:deptId},
		success : function(data) {
			if(data.status=="200")
			{
				var option = data.list;
				myChart = echarts.init(document.getElementById('main1'));
				myChart.setOption(option);
				window.addEventListener("resize",function(){
				    myChart.resize();
				});
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else
			{
				console.log(data.list);
			}
		}
	})
}