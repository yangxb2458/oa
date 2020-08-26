$(function(){
	$.ajax({
		url : "/ret/hrget/getHrTrainRecordById",
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
					}else if(id=="joinUser"|| id=="approvedUser"|| id=="chargePerson")
					{
						$("#"+id).html(getUserNameByStr(recordInfo[id]));
					}else if(id=="joinDept"|| id=="holdDept")
					{
						$("#"+id).html(getDeptNameByDeptIds(recordInfo[id]));
					}else if(id=="joinUserLevel")
					{
						$("#"+id).html(getUserLevelStr(recordInfo[id]));
					}else if(id=="remark")
					{
						$("#remark").html(recordInfo[id]);
					}else if(id=="channel")
					{
						$("#"+id).html(getHrClassCodeName("channel",recordInfo[id]));
					}else if(id=="courseType")
					{
						$("#"+id).html(getHrClassCodeName("courseType",recordInfo[id]));
					}else if(id=="status")
					{
						if(recordInfo[id]==null)
						{
							$("#"+id).html("未提交审批");
						}else if(recordInfo[id]=="0")
						{
							$("#"+id).html("审批中");
						}else if(recordInfo[id]=="1")
						{
							$("#"+id).html("审批通过");
						}else if(recordInfo[id]=="2")
						{
							$("#"+id).html("审批未通过");
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