$(function(){
	getReceivRecordInMonth();
	getPayableRecordTop();
	getContractTop();
	getContractBillTop();
})
function getPayableRecordTop()
{
	$.ajax({
		url : "/ret/contractget/getPayableRecordTop",
		type : "post",
		dataType : "json",
		success : function(data) {
			if (data.status == 200) {
				var recordInfo = data.list;
				var html="";
				for(var i=0;i<recordInfo.length;i++)
				{
					html+="<tr>";
					html+="<td>"+(i+1)+"</td>";
					html+="<td>"+recordInfo[i].title+"</td>";
					html+="<td>"+recordInfo[i].payableeeUserName+"</td>";
					html+="<td>"+recordInfo[i].payableTime+"</td>";
					html+="<td>"+recordInfo[i].amount+"</td>";
					html+="</tr>";
				}
				$("#payTr").html(html);
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
function getReceivRecordInMonth()
{
	$.ajax({
		url : "/ret/contractget/getReceivRecordTop",
		type : "post",
		dataType : "json",
		success : function(data) {
			if (data.status == 200) {
				var recordInfo = data.list;
				var html="";
				for(var i=0;i<recordInfo.length;i++)
				{
					html+="<tr>";
					html+="<td>"+(i+1)+"</td>";
					html+="<td>"+recordInfo[i].title+"</td>";
					html+="<td>"+recordInfo[i].payeeUserName+"</td>";
					html+="<td>"+recordInfo[i].payeeTime+"</td>";
					html+="<td>"+recordInfo[i].amount+"</td>";
					html+="</tr>";
				}
				$("#receivTr").html(html);
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

function getContractBillTop()
{
	$.ajax({
		url : "/ret/contractget/getContractBillTop",
		type : "post",
		dataType : "json",
		success : function(data) {
			if (data.status == 200) {
				var recordInfo = data.list;
				var html="";
				for(var i=0;i<recordInfo.length;i++)
				{
					html+="<tr>";
					html+="<td>"+(i+1)+"</td>";
					html+="<td>"+recordInfo[i].title+"</td>";
					html+="<td>"+recordInfo[i].customerName+"</td>";
					if(recordInfo[i].billType=="1")
					{
						html+="<td>普票</td>";
					}else if(recordInfo[i].billType=="2")
					{
						html+="<td>增票</td>";
					}else if(recordInfo[i].billType=="3")
					{
						html+="<td>服务票</td>";
					}else if(recordInfo[i].billType=="4")
					{
						html+="<td>电子票</td>";
					}else
					{
						html+="<td>未知</td>";
					}
					html+="<td>"+recordInfo[i].billTime+"</td>";
					html+="<td>"+recordInfo[i].total+"</td>";
					html+="</tr>";
				}
				$("#billTr").html(html);
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

function getContractTop()
{
	$.ajax({
		url : "/ret/contractget/getContractTop",
		type : "post",
		dataType : "json",
		success : function(data) {
			if (data.status == 200) {
				var recordInfo = data.list;
				var html="";
				for(var i=0;i<recordInfo.length;i++)
				{
					html+="<tr>";
					html+="<td>"+(i+1)+"</td>";
					html+="<td>"+recordInfo[i].title+"</td>";
					html+="<td>"+recordInfo[i].customerName+"</td>";
					if(recordInfo[i].contractType=="1")
					{
						html+="<td>销售合同</td>";
					}else if(recordInfo[i].contractType=="2")
					{
						html+="<td>采购合同</td>";
					}else if(recordInfo[i].contractType=="3")
					{
						html+="<td>服务合同</td>";
					}else
					{
						html+="<td>未知</td>";
					}
					html+="<td>"+recordInfo[i].signTime+"</td>";
					html+="<td>"+recordInfo[i].total+"</td>";
					html+="</tr>";
				}
				$("#contractTr").html(html);
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