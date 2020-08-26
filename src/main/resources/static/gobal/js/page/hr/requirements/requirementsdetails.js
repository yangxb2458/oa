$(function(){
	$.ajax({
		url : "/ret/hrget/getHrRecruitNeedsById",
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
					}else if(id=="deptId")
					{
						$("#"+id).html(getHrDeptNameByStr(recordInfo[id]));
					}else if(id=="occupation")
					{
						$("#"+id).html(getHrClassCodeName("occupation",recordInfo[id]));
					}else if(id=="highsetShool")
					{
						$("#"+id).html(getHrClassCodeName("highsetShool",recordInfo[id]));
					}else if(id=="workJob")
					{
						$("#"+id).html(getHrClassCodeName("workJob",recordInfo[id]));
					}else if(id=="planId")
					{
						$.ajax({
							url : "/ret/hrget/getHrRecruitPlanById",
							type : "post",
							dataType : "json",
							data:{planId:recordInfo[id]},
							success : function(res) {
								if(res.status=="200")
								{
									$("#planId").html(res.list.title);
								}else if(res.status=="100")
								{
									top.layer.msg(res.msg);
								}else if(res.status=="500")
								{
									console.log(res.msg);
								}
							}
						});
					}else if(id=="status")
					{
						if(recordInfo[id]=="0")
						{
							$("#"+id).html("审批中");
						}else if(recordInfo[id]=="1")
						{
							$("#"+id).html("已通过");
						}else if(recordInfo[id]=="2")
						{
							$("#"+id).html("未通过");
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