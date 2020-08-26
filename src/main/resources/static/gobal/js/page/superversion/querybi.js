$(function() {
	getBiSuperversionStatusTypePie();
	getBiSuperversionTypePie();
	getBiSuperversionByLeadPie();
	getBiSuperversionByMonthLine();
});
function getBiSuperversionTypePie()
{
	$.ajax({
		url : "/ret/echartssuperversionget/getBiSuperversionTypePie",
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

function getBiSuperversionByLeadPie()
{
	$.ajax({
		url : "/ret/echartssuperversionget/getBiSuperversionByLeadPie",
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

function getBiSuperversionStatusTypePie()
{
	$.ajax({
		url : "/ret/echartssuperversionget/getBiSuperversionStatusTypePie",
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

function getBiSuperversionByMonthLine()
{
	$.ajax({
		url : "/ret/echartssuperversionget/getBiSuperversionByMonthLine",
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