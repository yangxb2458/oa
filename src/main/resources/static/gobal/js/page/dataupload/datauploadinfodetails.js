$(function(){
	$.ajax({
		url : "/ret/datauploadget/getDataUploadInfoById",
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
						$("#datainfoattach").attr("data_value", recordInfo.attach);
						createAttach("datainfoattach", 1);
					}else if(id=="fromAccountId"||id=="toUser"||id=="approvedUser")
					{
						$("#"+id).html(getUserNameByStr(recordInfo[id]));
					}else if(id=="deptId")
					{
						$("#"+id).html(getDeptNameByDeptIds(recordInfo[id]));
					}else if(id=="remark")
					{
						$("#remark").html(recordInfo[id]);
					}else if(id=="dataType")
					{
						$("#"+id).html(getCodeClassName(recordInfo[id],"dataType"));
					}else if(id=="approvedType")
					{
						if(recordInfo[id]=="1")
						{
							$("#"+id).html("线上审核");
						}else if(recordInfo[id]=="2")
						{
							$("#"+id).html("线下审核");
						}
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