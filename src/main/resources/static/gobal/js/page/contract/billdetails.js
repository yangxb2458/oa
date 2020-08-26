$(function(){
	$.ajax({
		type : "post",
		url : "/ret/contractget/getContractBillById",
		data : {
			billId : billId
		},
		async : false,
		success : function(data) {
			if (data.status == "200") {
				console.log(data.list);
				var recordInfo=data.list;
				for(var id in recordInfo)
					{
						if(id=="contractId")
						{
							$.ajax({
								url : "/ret/contractget/getContractById",
								type : "post",
								dataType : "json",
								data:{contractId:recordInfo[id]},
								success : function(res) {
									if(res.status=="200")
									{
										$("#contractId").html(res.list.title);
									}else if(res.status=="100")
									{
										top.layer.msg(res.msg);
									}else if(res.status=="500")
									{
										console.log(res.msg);
									}
								}
							});
						}else if(id=="billType")
						{
							if(recordInfo[id]=="1")
							{
								$("#"+id).html("普票");
							}else if(recordInfo[id]=="2")
							{
								$("#"+id).html("增票");
							}else if(recordInfo[id]=="3")
							{
								$("#"+id).html("服务票");
							}else if(recordInfo[id]=="4")
							{
								$("#"+id).html("电子票");
							}
						}else if(id=="isOpen")
						{
							if(recordInfo[id]=="1")
							{
								$("#"+id).html("开票");
							}else if(recordInfo[id]=="2")
							{
								$("#"+id).html("收票");
							}
						}else if(id=="attach")
						{
							$("#contractattach").attr("data_value", recordInfo.attach);
							createAttach("contractattach", 1);
						}else
						{
							$("#"+id).html(recordInfo[id]);
						}
					}
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else
			{
				console.log(data.msg);
			}
			}
	});
	
})