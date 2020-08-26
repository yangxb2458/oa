$(function() {
	getBiBpmFlowByDeptPie();
	getBiBpmFlowPie();
	getBiBpmFlowByAccountPie();
	getBiBpmFlowByMonthLine();
});
function getBiBpmFlowPie()
{
	$.ajax({
		url : "/ret/echartsbpmget/getBiBpmFlowPie",
		type : "post",
		dataType : "json",
		success : function(data) {
			if(data.status=="200")
			{
				var option = data.list;
				var myChart = echarts.init(document.getElementById('main2'));
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

function getBiBpmFlowByAccountPie()
{
	$.ajax({
		url : "/ret/echartsbpmget/getBiBpmFlowByAccountPie",
		type : "post",
		dataType : "json",
		success : function(data) {
			if(data.status=="200")
			{
				var option = data.list;
				var myChart = echarts.init(document.getElementById('main3'));
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

function getBiBpmFlowByDeptPie()
{
	$.ajax({
		url : "/ret/echartsbpmget/getBiBpmFlowByDeptPie",
		type : "post",
		dataType : "json",
		success : function(data) {
			if(data.status=="200")
			{
				var option = data.list;
				var myChart = echarts.init(document.getElementById('main1'));
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

function getBiBpmFlowByMonthLine()
{
	$.ajax({
		url : "/ret/echartsbpmget/getBiBpmFlowByMonthLine",
		type : "post",
		dataType : "json",
		success : function(data) {
			if(data.status=="200")
			{
				var option = data.list;
				var myChart = echarts.init(document.getElementById('main4'));
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