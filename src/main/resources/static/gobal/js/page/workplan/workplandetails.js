$(function(){
	$.ajax({
		url : "/ret/workplanget/getWorkPlanById",
		type : "post",
		dataType : "json",
		data:{planId:planId},
		success : function(data) {
			if(data.status=="200")
			{
				var workplan = data.list;
				for(var id in workplan)
				{
					if(id=="attach")
					{
						$("#workplanattach").attr("data_value", workplan.attach);
						createAttach("workplanattach", 1);
					}else if(id=="userPriv"||id=="joinUser"||id=="holdUser"||id=="supUser")
					{
						$("#"+id).html(getUserNameByStr(workplan[id]));
					}else if(id=="deptPriv")
					{
						$("#"+id).html(getDeptNameByDeptIds(workplan[id]));
					}else if(id=="levelPriv")
					{
						$("#"+id).html(getUserLevelStr(workplan[id]));
					}else if(id=="planType")
					{
						$("#planType").html(getCodeClassName(workplan[id],"workplan"));
					}else if(id=="remark")
					{
						$("#remark").html(workplan[id]);
					}else
					{
						$("#"+id).html(workplan[id]);
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