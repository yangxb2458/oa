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
		$("#echartTitle").html("工种分析");
	}else if(dataType=="2")
	{
		$("#echartTitle").html("学历分析");
	}else if(dataType=="3")
	{
		$("#echartTitle").html("职称分析");
	}else if(dataType=="4")
	{
		$("#echartTitle").html("籍贯分析");
	}else if(dataType=="5")
	{
		$("#echartTitle").html("工资级别分析");
	}else if(dataType=="6")
	{
		$("#echartTitle").html("政治面貌分析");
	}
	if(echartType=="1")
	{
		getBaseInfoPieForAnalysis(dataType,deptId);
	}else if(echartType=="2")
	{
		getBaseInfoBarForAnalysis(dataType,deptId);
	}else if(echartType=="3")
	{
		getBaseInfoTableForAnalysis(dataType,deptId)
	}
		
}

function getBaseInfoTableForAnalysis(dataType,deptId)
{
	$.ajax({
		url : "/ret/echartshrget/getBaseInfoTableForAnalysis",
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

function getBaseInfoPieForAnalysis(dataType,deptId)
{
	$.ajax({
		url : "/ret/echartshrget/getBaseInfoPieForAnalysis",
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

function getBaseInfoBarForAnalysis(dataType,deptId)
{
	$.ajax({
		url : "/ret/echartshrget/getBaseInfoBarForAnalysis",
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