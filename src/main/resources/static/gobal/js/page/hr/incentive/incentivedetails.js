$(function(){
	$.ajax({
		url : "/ret/hrget/getHrIncentiveById",
		type : "post",
		dataType : "json",
		data:{incentiveId:incentiveId},
		success : function(data) {
			if(data.status=="200")
			{
				var incentiveInfo = data.list;
				for(var id in incentiveInfo)
				{
					if(id=="attach")
					{
						$("#hrattach").attr("data_value", incentiveInfo.attach);
						createAttach("hrattach", 1);
					}else if(id=="userId")
					{
						$("#"+id).html(getHrUserNameByStr(incentiveInfo[id]));
					}else if(id=="remark")
					{
						$("#remark").html(incentiveInfo[id]);
					}else if(id=="incentiveType")
					{
						if(incentiveInfo[id]=="0")
						{
							$("#"+id).html("奖励");
						}else if(incentiveInfo[id]=="1")
						{
							$("#"+id).html("惩罚");
						}
					}else if(id=="incentiveItem")
					{
						$("#"+id).html(getHrClassCodeName("incentiveItem",incentiveInfo[id]));
					}else
					{
						$("#"+id).html(incentiveInfo[id]);
					}
				}
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else if(data.status=="500")
			{
				console.log(data.msg);
			}
		}
		})
})