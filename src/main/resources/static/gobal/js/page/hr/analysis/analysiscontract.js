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
		$("#echartTitle").html("合同类型");
	}else if(dataType=="2")
	{
		$("#echartTitle").html("签约公司");
	}else if(dataType=="3")
	{
		$("#echartTitle").html("签约方式");
	}else if(dataType=="4")
	{
		$("#echartTitle").html("应聘岗位");
	}
	if(echartType=="1")
	{
		getContractPieForAnalysis(dataType,deptId);
	}else if(echartType=="2")
	{
		getContractBarForAnalysis(dataType,deptId);
	}else if(echartType=="3")
	{
		getContractTableForAnalysis(dataType,deptId)
	}
		
}

function getContractTableForAnalysis(dataType,deptId)
{
	$.ajax({
		url : "/ret/echartshrget/getContractTableForAnalysis",
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

function getContractPieForAnalysis(dataType,deptId)
{
	$.ajax({
		url : "/ret/echartshrget/getContractPieForAnalysis",
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

function getContractBarForAnalysis(dataType,deptId)
{
	$.ajax({
		url : "/ret/echartshrget/getContractBarForAnalysis",
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