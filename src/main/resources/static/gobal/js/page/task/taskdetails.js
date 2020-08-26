$(function(){
	 getTaskInfo();
});

function getTaskInfo()
{
	$.ajax({
		url : "/ret/taskget/getTaskById",
		type : "post",
		dataType : "json",
		data:{taskId:taskId},
		success : function(data) {
			if(data.status=="500")
			{
			console.log(data.msg);
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else
				{
				for(var name in data.list)
				{
				if(name=="attach")
					{
					$("#taskattach").attr("data_value",data.list.attach);
					createAttach("taskattach",data.list.attachPriv);
					}else if(name=="userPriv")
					{
						$("#"+name).html(getUserNameByStr(data.list.userPriv));
					}else if(name=="deptPriv")
					{
						$("#"+name).html(getDeptNameByDeptIds(data.list.deptPriv));
					}else if(name=="leavePriv")
					{
						$("#"+name).html(getUserLevelStr(data.list.leavePriv));
					}else if(name=="chargeAccountId")
					{
						$("#"+name).html(getUserNameByStr(data.list.chargeAccountId));
					}else if(name=="participantAccountId")
					{
						$("#"+name).html(getUserNameByStr(data.list.participantAccountId));
					}else if(name=="supervisorAccountId")
					{
						$("#"+name).html(getUserNameByStr(data.list.supervisorAccountId));
					}else if(name=="isTop")
					{
						if(data.list.isTop=="0")
						{
							$("#"+name).html("顺序");
						}else if(data.list.isTop=="1")
						{
							$("#"+name).html("置顶");
						}
					}else if(name=="taskType")
					{
						$("#"+name).html(getCodeClassName(data.list.taskType,"task"));
					}else
						{
						$("#"+name).html(data.list[name]);
						}
				}
				}
		}
	})
}