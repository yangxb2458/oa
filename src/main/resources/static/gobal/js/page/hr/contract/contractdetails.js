$(function(){
	$.ajax({
		url : "/ret/hrget/getHrContractById",
		type : "post",
		dataType : "json",
		data:{contractId:contractId},
		success : function(data) {
			if(data.status=="200")
			{
				var contractInfo = data.list;
				for(var id in contractInfo)
				{
					if(id=="attach")
					{
						$("#hrattach").attr("data_value", contractInfo.attach);
						createAttach("hrattach", 1);
					}else if(id=="userId")
					{
						$("#"+id).html(getHrUserNameByStr(contractInfo[id]));
					}else if(id=="signType")
					{
						if(contractInfo[id]=="1")
						{
							$("#"+id).html("新签");
						}else if(contractInfo[id]=="2")
						{
							$("#"+id).html("补签");
						}else if(contractInfo[id]=="3")
						{
							$("#"+id).html("改签");
						}else if(contractInfo[id]=="4")
						{
							$("#"+id).html("续签");
						}
					}else if(id=="poolPosition")
					{
						$("#"+id).html(getHrClassCodeName('poolPosition',contractInfo[id]));
					}else if(id=="enterpries")
					{
						$("#"+id).html(getHrClassCodeName('enterpries',contractInfo[id]));
					}else if(id=="contractType")
					{
						$("#"+id).html(getHrClassCodeName('contractType',contractInfo[id]));
					}else if(id=="specialization")
					{
						if(contractInfo[id]=="1")
						{
							$("#"+id).html("固定期限");
						}else if(contractInfo[id]=="2")
						{
							$("#"+id).html("无固定期限");
						}else if(contractInfo[id]=="3")
						{
							$("#"+id).html("以完成一定工作任务为期限");
						}
					}else
					{
						$("#"+id).html(contractInfo[id]);
					}
				}
				$(".js-update-save").unbind("click").click(function(){
					updateHrContract(contractId);
				})
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else if(data.status=="500")
			{
				console.log(data.msg);
			}
		}
		})
});