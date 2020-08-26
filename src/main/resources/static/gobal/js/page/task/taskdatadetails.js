$(function(){
	 getTaskInfo();
});

function getTaskInfo()
{
	$.ajax({
		url : "/ret/taskget/getTaskGanttDataById",
		type : "post",
		dataType : "json",
		data:{taskDataId:taskDataId},
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
				if(name=="userPriv")
					{
						$("#"+name).html(getUserNameByStr(data.list.userPriv));
					}else if(name=="progress")
					{
						$("#"+name).html((data.list[name]*100)+"%");
					}else
						{
						$("#"+name).html(data.list[name]);
						}
				}
				}
		}
	})
}