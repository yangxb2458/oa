$(function(){
	$.ajax({
		url : "/ret/hrget/getHrCareRecordById",
		type : "post",
		dataType : "json",
		data:{recordId:recordId},
		success : function(data) {
			if(data.status=="200")
			{
				var recordInfo = data.list;
				for(var id in recordInfo)
				{
					if(id=="attach")
					{
						$("#hrattach").attr("data_value", recordInfo.attach);
						createAttach("hrattach", 1);
					}else if(id=="userId")
					{
						$("#"+id).html(getHrUserNameByStr(recordInfo[id]));
					}else if(id=="joinUser")
					{
						$("#"+id).html(getHrUserNameByStr(recordInfo[id]));
					}else if(id=="post")
					{
						$("#"+id).html(getHrUserLevelByStr(recordInfo[id]));
					}else if(id=="careType")
					{
						$("#"+id).html(getHrClassCodeName("careType",recordInfo[id]));
					}else
					{
						$("#"+id).html(recordInfo[id]);
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