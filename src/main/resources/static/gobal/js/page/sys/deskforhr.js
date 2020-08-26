$(function(){
	getDeskHrUserInfo();
	getHighsetShoolPie();
	getWorkTypeBar();
	getNativePlacePie();
	getDeskHrContractList();
});

function getDeskHrContractList()
{
	$.ajax({
		url : "/ret/hrget/getDeskHrContractList",
		type : "post",
		dataType : "json",
		success : function(data) {
			if(data.status=="200")
			{
				var html="";
				var recordInfo = data.list;
				for(var i=0;i<recordInfo.length;i++)
				{
					html+="<tr>";
					html+="<td>"+(i+1)+"</td>"
					html+="<td>"+recordInfo[i].contractCode+"</td>"
					if(recordInfo[i].userName==""||recordInfo[i].userName==null)
					{
						html+="<td>"+recordInfo[i].userIdUserName+"</td>"
					}else
					{
						html+="<td>"+recordInfo[i].userName+"</td>"
					}
					html+="<td>"+recordInfo[i].poolPositionValue+"</td>"
					html+="<td>"+recordInfo[i].contractTypeValue+"</td>"
					html+="<td>"+recordInfo[i].enterpriesValue+"</td>"
					html+="<td>"+recordInfo[i].signTime+"</td>"
					html+="<td>"+recordInfo[i].endTime+"</td>"
					html+="<tr>";
				}
				
				$("#contracttr").html(html);
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else
			{
				console.log(data.msg);
			}
		}
	});
}


function getDeskHrUserInfo()
{
	$.ajax({
		url : "/ret/hrget/getDeskHrUserInfo",
		type : "post",
		dataType : "json",
		success : function(data) {
			if(data.status=="200")
			{
				var userInfo = data.list;
				$("#ingWorkTotal").html(userInfo.ingWorkTotal);
				$("#attendTotal").html(userInfo.attendTotal);
				$("#levelType").html(userInfo.levelType);
				$("#careTotal").html(userInfo.careTotal)
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

function getNativePlacePie()
{
	$.ajax({
		url : "/ret/echartshrget/getNativePlacePie",
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

function getHighsetShoolPie()
{
	$.ajax({
		url : "/ret/echartshrget/getHighsetShoolPie",
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

function getWorkTypeBar()
{
	$.ajax({
		url : "/ret/echartshrget/getWorkTypeBar",
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