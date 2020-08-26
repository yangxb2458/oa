$(function(){
	$.ajax({
		url : "/ret/projectbuildget/contract/getProjectBuildContractById",
		type : "post",
		dataType : "json",
		data:{contractId:contractId},
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
					$("#projectbuildattach").attr("data_value",data.list.attach);
					createAttach("projectbuildattach","1");
					}else if(name=="type")
					{
						if(data.list.type=="1")
						{
							$("#"+name).html("材料采购合同");
						}else if(data.list.type=="2")
						{
							$("#"+name).html("工程项目合同");
						}else if(data.list.type=="3")
						{
							$("#"+name).html("服务合同");
						}
					}else if(name=="signUser")
					{
						$("#"+name).html(getUserNameByStr(data.list.signUser));
					}else if(name=="sortId")
					{
						getSortName(data.list.sortId);
					}else
					{
						$("#"+name).html(data.list[name]);
					}
				}
				}
		}
	})
});
function getSortName(sortId)
{
$.ajax({
	url : "/ret/projectbuildget/contract/getProjectBuildContractSortById",
	type : "post",
	dataType : "json",
	data:{sortId:sortId},
	success : function(data) {
		if(data.status=="500")
		{
		console.log(data.msg);
		}else if(data.status=="100")
		{
			top.layer.msg(data.msg);
		}else
			{
			$("#sortId").html(data.list.sortName);
			}
	}
})
}

