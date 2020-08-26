$(function(){
	$.ajax({
		url : "/ret/hrget/getHrLearnRecordById",
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
					}else if(id=="remark")
					{
						$("#remark").html(recordInfo[id]);
					}else if(id=="highsetDegree")
					{
						$("#"+id).html(getHrClassCodeName("highsetDegree",recordInfo[id]));
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