$(function(){
	$(".js-createcontract").unbind("click").click(function(){
		top.openNewTabs("/app/core/contract/create","创建合同");
	});
	$(".js-managecontract").unbind("click").click(function(){
		top.openNewTabs("/app/core/contract/create?view=manage","合同管理");
	});
	$(".js-bill").unbind("click").click(function(){
		top.openNewTabs("/app/core/contract/bill?view=manage","票据管理");
	});
	$(".js-receivables").unbind("click").click(function(){
		top.openNewTabs("/app/core/contract/receivables?view=manage","应收款管理");
	});
	$(".js-payable").unbind("click").click(function(){
		top.openNewTabs("/app/core/contract/payable?view=manage","应付款管理");
	});
	$(".js-total").unbind("click").click(function(){
		top.openNewTabs("/app/core/contract/contracttotal","财务汇总");
	});
	getARAPOptionConfig();
	getDeskReceivablesList();
	getDeskPayableList();
	getPayReceivTotalData();
	getDeskBillList();
})

function getARAPOptionConfig()
{
	$.ajax({
		url : "/ret/echartsfinanceget/getARAPOptionConfig",
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
function getPayReceivTotalData()
{
	$.ajax({
		url : "/ret/echartsfinanceget/getPayReceivTotalData",
		type : "post",
		dataType : "json",
		success : function(data) {
			if(data.status=="200")
			{
				var record = data.list;
				$("#payTotal").html(record.payTotal);
				$("#receTotal").html(record.receTotal);
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

function getDeskBillList()
{
	$.ajax({
		url : "/ret/contractget/getDeskBillList",
		type : "post",
		dataType : "json",
		success : function(data) {
			if(data.status=="200")
			{
				var html="";
				var recordInfo = data.list;
				console.log(recordInfo);
				for(var i=0;i<recordInfo.length;i++)
				{
					html+="<tr>";
					html+="<td>"+(i+1)+"</td>";
					html+="<td>"+recordInfo[i].billCode+"</td>";
					html+="<td>"+recordInfo[i].customerName+"</td>";
					html+="<td>"+recordInfo[i].title+"</td>";
					html+="<td>";
					if(recordInfo[i].billType=="1")
					{
						html+="普票";
					}else if(recordInfo[i].billType=="2")
					{
						html+="增票";
					}else if(recordInfo[i].billType=="3")
					{
						html+="服务票";
					}else if(recordInfo[i].billType=="4")
					{
						html+="电子票";
					}
					html+="</td>";
					html+="<td>"+recordInfo[i].billTime+"</td>";
					html+="<td>";
						if(recordInfo[i].isOpen=="1")
						{
							html+="收票";
						}else if(recordInfo[i].isOpen=="2")
						{
							html+="开票";
						}
						html+="</td>";
					html+="<td>"+recordInfo[i].total+"</td>";
					html+="<tr>";
				}
				
				$("#billtr").html(html);
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


function getDeskPayableList()
{
	$.ajax({
		url : "/ret/contractget/getDeskPayableList",
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
					html+="<td>"+recordInfo[i].title+"</td>"
					html+="<td>"+recordInfo[i].payableTime+"</td>"
					html+="<td>"+recordInfo[i].unPayabled+"</td>"
					html+="<td>"+recordInfo[i].payabled+"</td>"
					html+="<td>"+recordInfo[i].payabledTime+"</td>"
					html+="<td>"+recordInfo[i].userPrivName+"</td>"
					html+="<tr>";
				}
				
				$("#paytr").html(html);
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

function getDeskReceivablesList()
{
	$.ajax({
		url : "/ret/contractget/getDeskReceivablesList",
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
					html+="<td>"+recordInfo[i].title+"</td>"
					html+="<td>"+recordInfo[i].receivablesTime+"</td>"
					html+="<td>"+recordInfo[i].unReceived+"</td>"
					html+="<td>"+recordInfo[i].received+"</td>"
					html+="<td>"+recordInfo[i].receivedTime+"</td>"
					html+="<td>"+recordInfo[i].userPrivName+"</td>"
					html+="<tr>";
				}
				
				$("#receivedtr").html(html);
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


function getContractBillList()
{
	
}