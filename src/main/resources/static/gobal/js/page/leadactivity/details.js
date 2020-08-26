$(function(){
	$.ajax({
		url : "/ret/oaget/getLeadActivityById",
		type : "post",
		dataType : "json",
		data:{recordId:recordId},
		success : function(data) {
			if(data.status=="200")
			{
				var recordInfo = data.list;
				for(var id in recordInfo)
				{
					if(id=="leader")
					{
						$("#"+id).html(getUserNameByStr(recordInfo[id]));
					}else if(id=="userPriv")
					{
						$("#"+id).html(getUserNameByStr(recordInfo[id]));
					}else if(id=="deptPriv")
					{
						$("#deptPriv").html(getDeptNameByDeptIds(recordInfo[id]));
					}else if(id=="levelPriv")
					{
						$("#levelPriv").html(getUserLevelStr(recordInfo[id]));
					}else
					{
						$("#"+id).html(recordInfo[id]);
					}
				}
				$(".js-update-save").unbind("click").click(function(){
					updateLeadActivity(recordId);
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
})