$(function() {
	getBiCustomerAreaPie();
	getBiCustomerIndustryPie();
	getBiCustomerKeepUserPie();
	getBiCustomerLevelPie();
});
function getBiCustomerIndustryPie()
{
	$.ajax({
		url : "/ret/echartscrmget/getBiCustomerIndustryPie",
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

function getBiCustomerKeepUserPie()
{
	
	$.ajax({
		url : "/ret/echartscrmget/getBiCustomerKeepUserPie",
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

function getBiCustomerAreaPie()
{
	
	$.ajax({
		url : "/ret/echartscrmget/getBiCustomerIndustryPie",
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

function getBiCustomerLevelPie()
{
	$.ajax({
		url : "/ret/echartscrmget/getBiCustomerLevelPie",
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